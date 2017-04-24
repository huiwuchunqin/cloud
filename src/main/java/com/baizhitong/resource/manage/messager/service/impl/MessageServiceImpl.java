package com.baizhitong.resource.manage.messager.service.impl;

import java.net.SocketException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.baizhitong.core.mvc.commons.message.BztJmsTemplate;
import com.baizhitong.core.mvc.commons.message.BztMessage;
import com.baizhitong.core.mvc.commons.message.BztMessageListener;
import com.baizhitong.core.mvc.commons.message.activemq.P2pJmsTemplate;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.dao.res.ResShareCheckDao;
import com.baizhitong.resource.dao.share.ISysMessageDao;
import com.baizhitong.resource.model.res.ResShareCheck;
import com.baizhitong.resource.model.share.SysMessage;
import com.baizhitong.utils.IpUtil;

/**
 * 
 * MessageServiceImpl 消息服务实现类
 * 
 * @author creator xuyj 2016年3月3日 下午6:44:15
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "messageService")
public class MessageServiceImpl extends BaseService implements BztMessageListener {

    /** 发送消息模版 */
    @Autowired
    @Qualifier("topicJmsTemplate")
    private BztJmsTemplate   jmsTemplate;
    @Autowired
    @Lazy
    private P2pJmsTemplate   p2pJmsTemplate;

    /** 资源共享审核 */
    @Autowired
    private ResShareCheckDao resShareCheckDao;
    @Autowired
    private ISysMessageDao   sysMessageDao;

    /**
     * 
     * 发送消息
     * 
     * @param shareCheckStatus 分享审核状态
     * @param resCode 资源编码
     * @param resTypeL1 资源一级分类
     * @param shareCheckLevel 分享审核级别
     * @return
     */
    public boolean sendMessage(Integer shareCheckStatus, String resCode, Integer resTypeL1, Integer shareCheckLevel,
                    String userCode, String applierOrgCode, String applierCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resCode", resCode);
        map.put("shareCheckStatus", shareCheckStatus);
        map.put("resTypeL1", resTypeL1);
        map.put("shareCheckLevel", shareCheckLevel);
        map.put("userCode", userCode);
        map.put("applierOrgCode", applierOrgCode);
        map.put("applierCode", applierCode);
        String messagerType = "";
        if (shareCheckLevel.intValue() == CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY.intValue()) {
            if (resTypeL1 == CoreConstants.RES_TYPE_MEDIA || resTypeL1 == CoreConstants.RES_TYPE_DOC
                            || resTypeL1 == CoreConstants.RES_TYPE_QUESTION
                            || resTypeL1 == CoreConstants.RES_TYPE_SPECIAL_MEDIA
                            || resTypeL1 == CoreConstants.RES_TYPE_FLASH||resTypeL1 == CoreConstants.RES_TYPE_EXERCISE) { 
                messagerType = "BACK000001";
            }else if (resTypeL1 == CoreConstants.RES_TYPE_COURSE) {
                messagerType = "BACK000003";
            }
        }
        if (shareCheckLevel.intValue() == CoreConstants.RESOURCE_SHARE_LEVEL_TOWN.intValue()) {
            if (resTypeL1 == CoreConstants.RES_TYPE_MEDIA || resTypeL1 == CoreConstants.RES_TYPE_DOC
                            || resTypeL1 == CoreConstants.RES_TYPE_EXERCISE
                            || resTypeL1 == CoreConstants.RES_TYPE_SPECIAL_MEDIA
                            || resTypeL1 == CoreConstants.RES_TYPE_QUESTION
                            || resTypeL1 == CoreConstants.RES_TYPE_FLASH) {
                messagerType = "BACK000002";
            } else if (resTypeL1 == CoreConstants.RES_TYPE_COURSE) {
                messagerType = "BACK000004";
            }
        }

        p2pJmsTemplate.send(messagerType, map);
        return true;
    }

    /**
     * 
     * (发送积分变动消息)<br>
     * 
     * @param actionCode 动作编码
     * @param orgCode 机构编码
     * @param userCode 用户编码
     * @param flag 标识
     * @return 发送结果
     */
    public boolean sendPointMessage(Integer actionCode, String orgCode, String userCode, Integer flag) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgCode", orgCode);
        map.put("userCode", userCode);
        map.put("actionCode", actionCode);
        map.put("flag", flag);
        jmsTemplate.send("APPS000001", map); 
        return true;
    }

    /**
     * (接收消息)<br>
     * 
     * @param resTypeL1 消息数据对象
     * @return 是否成功
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onMessage(BztMessage message) {
        String messageType = message.getMessageType();
        addMessage(messageType,message.getMessageId());
        Map<String, Object> map = JSON.parseObject(message.getData(), Map.class);
        String resCode = MapUtils.getString(map, "resCode"); // 资源编码
        if ("LESN000008".equals(messageType) || "RESC000009".equals(messageType) || "RESC000010".equals(messageType)) {
            resShareCheckDao.delete(resCode);
        } else {

            /** --------------------------- 入参规则校验与处理 ------------------------- */
            // 请求类型
            // Assert.notNull(MapUtils.getString(map, "operateType"), " 请求类型为必填项~ ");
            // 资源一级分类为必填项
            Assert.notNull(MapUtils.getInteger(map, "resTypeL1"), " 资源一级分类为必填项~ ");
            // 资源编码为必填项
            Assert.notNull(MapUtils.getString(map, "resCode"), " 资源编码为必填项~ ");
            // 资源访问路径为必填项
            Assert.notNull(MapUtils.getString(map, "resAccessPath"), " 资源访问路径为必填项~ ");
            // 审核级别不能为空
            Assert.notNull(MapUtils.getString(map, "shareCheckLevel"), " 审核级别不能为空~ ");

            /** --------------------------- 业务处理 ------------------------- */
            Integer resTypeL1 = MapUtils.getInteger(map, "resTypeL1"); // 资源一级分类
            String resTypeL2 = MapUtils.getString(map, "resTypeL2"); // 资源二级分类
            String resName = MapUtils.getString(map, "resName"); // 资源名称
            if (resTypeL1 == 50) {
                resName = "题目内容";
            }
            String resDesc = MapUtils.getString(map, "resDesc"); // 资源描述
            // String srcDesc =MapUtils.getString(map, "srcDesc"); //来源描述
            String resAccessPath = MapUtils.getString(map, "resAccessPath"); // 资源查看路径
            String subjectCode = MapUtils.getString(map, "subjectCode"); // 学科编码
            Integer shareLevel = MapUtils.getInteger(map, "shareLevel"); // 分享级别
            Integer shareCheckLevel = MapUtils.getInteger(map, "shareCheckLevel"); // 分享审核中级别
            String applierCode = MapUtils.getString(map, "applierCode"); // 申请人代码
            String applierName = MapUtils.getString(map, "applierName"); // 申请人姓名
            String applierOrgCode = MapUtils.getString(map, "applierOrgCode"); // 申请人机构编码
            String applierOrgName = MapUtils.getString(map, "applierOrgName"); // 申请人机构名称
            String applyReason = MapUtils.getString(map, "applyReason"); // 申请原因
            String sectionCode = MapUtils.getString(map, "sectionCode"); // 学段编码
            String gradeCode = MapUtils.getString(map, "gradeCode"); // 年级编码

            ResShareCheck resShareCheck = new ResShareCheck();
            resShareCheck.setResCode(resCode); // 编码
            resShareCheck.setResName(resName); // 资源名称
            resShareCheck.setResTypeL1(resTypeL1); // 资源一级分类
            resShareCheck.setResTypeL2(resTypeL2); // 资源一级分类
            resShareCheck.setResDesc(resDesc); // 资源描述
            resShareCheck.setResAccessPath(resAccessPath); // 资源查看路径
            resShareCheck.setSubjectCode(subjectCode); // 学科编码
            resShareCheck.setShareLevel(shareLevel); // 分享级别
            resShareCheck.setShareCheckLevel(shareCheckLevel);// 分享审核中级别
            resShareCheck.setShareCheckStatus(CoreConstants.CHECK_STATUS_CHECKING);// 分享审核中状态
            resShareCheck.setApplierCode(applierCode); // 申请人代码
            resShareCheck.setApplierName(applierName); // 申请人姓名
            resShareCheck.setApplierOrgCode(applierOrgCode);// 申请人机构代码
            resShareCheck.setApplyReason(applyReason); // 申请理由
            resShareCheck.setApplierOrgName(applierOrgName); // 申请人机构名称
            resShareCheck.setApplyTime(new Timestamp(new Date().getTime())); // 申请时间
            resShareCheck.setCheckComments(""); // 审核意见
            resShareCheck.setCheckerCode(""); // 审核人代码
            resShareCheck.setCheckerName(""); // 审核人姓名
            resShareCheck.setCheckerOrgCode(""); // 审核人机构代码
            resShareCheck.setCheckerOrgName(""); // 审核人机构名称
            resShareCheck.setCheckTime(null); // 审核时间
            resShareCheck.setFlagDelete(0);
            resShareCheck.setMemo("");
            resShareCheck.setModifier("");
            resShareCheck.setModifierIP("");
            resShareCheck.setSectionCode(sectionCode);
            resShareCheck.setGradeCode(gradeCode);
            resShareCheck.setModifyTime(new Timestamp(new Date().getTime()));
            List<ResShareCheck> resShareCheckExist = resShareCheckDao.getResByCodeAndType(resTypeL1, resCode);
            try {
                // 数据库里面有相同资源的待审核记录就不再新增
                if (resShareCheckExist == null || resShareCheckExist.size() <= 0) {
                    resShareCheckDao.save(resShareCheck);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addMessage(String messageType, String messageID) {
            try {
                SysMessage newMessage = new SysMessage();
                newMessage.setCreatePgm("backendMessageService");
                newMessage.setCreateTime(new Timestamp(new Date().getTime()));
                try {
                    newMessage.setCreatorIP(IpUtil.getLocalIp());
                } catch (SocketException e) {
                    newMessage.setCreatorIP("localhost-back");
                }
                newMessage.setMessageId(messageID);
                newMessage.setMessageType(messageType);
                sysMessageDao.insert(newMessage);
            } catch (Exception e) {
                log.error("消息记录插入重复",e);
                e.printStackTrace();
            }
    }

    @Override
    public boolean isSupported(String messageType, String messageID) {
            SysMessage message = sysMessageDao.getMessage(messageType, messageID);
            if (message == null) {
                if ("RESC000002".equals(messageType)) {
                    return true;
                } else if ("LESN000002".equals(messageType)) {
                    return true;
                } else if ("TEST000001".equals(messageType)) {
                    return true;
                } else if ("RESC000003".equals(messageType)) {
                    return true;
                } else if ("LESN000003".equals(messageType)) {
                    return true;
                } else if ("TEST000002".equals(messageType)) {
                    return true;
                } else if ("LESN000008".equals(messageType)) {
                    return true;
                } else if ("RESC000009".equals(messageType)) {
                    return true;
                } else if ("RESC000010".equals(messageType)) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;
    }

    @Override
    public Set<String> subCategoriesOfQueueConsumer() {
        String[] consumer = { "RESC000002", "LESN000002", "TEST000001", "RESC000003", "LESN000003", "TEST000002",
                        "LESN000008", "RESC000009", "RESC000010" };
        Set set = new HashSet<String>();
        set.addAll(Arrays.asList(consumer));
        return set;

    }
}