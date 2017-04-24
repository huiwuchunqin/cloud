package com.baizhitong.resource.manage.textbook.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareKnowledgeSerialDao;
import com.baizhitong.resource.dao.share.ShareRelTextbookKpsDao;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointSerialService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.share.ShareKnowledgePointSerial;
import com.baizhitong.resource.model.share.ShareRelTextbookKps;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareRelTextbookKpsVo;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 教材知识体系关联接口实现
 * 
 * @author creator gaow 2016年1月13日 下午5:14:40
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class TextbookKnowledgePointSerialServiceImpl extends BaseService
                implements TextbookKnowledgePointSerialService {
    // 教材知识体系关系dao
    @Autowired
    private ShareRelTextbookKpsDao  shareRelTextbookKpsDao;
    // 知识体系dao
    @Autowired
    private ShareKnowledgeSerialDao shareKnowledgeSerialDao;
    /** 教材知识点体系关联Dao */
    @Autowired
    private ShareRelTextbookKpsDao  shareRelTextBookKpsDao;
    @Autowired
    private ISysCodeService         sysCodeService;
    /** 学段dao */
    @Autowired
    private ShareCodeSectionDao     shareCodeSectionDao;
    /** 学科dao */
    @Autowired
    private ShareCodeSubjectDao     shareCodeSubjectDao;

    /**
     * 保存教材知识体系关系 和知识体系 ()<br>
     * 
     * @param relTextbookKps
     * @param name 体系名称
     * @param memo 备注
     * @return
     */
    public ResultCodeVo addTextbookKnowledgePointSerial(ShareRelTextbookKpsVo relTextbookKpsVo, String name,
                    String memo) throws Exception {
        List<ShareKnowledgePointSerial> sameNameList = shareKnowledgeSerialDao.getSameNameKnowledgeSerialList(name,
                        relTextbookKpsVo.getSubjectCode(), relTextbookKpsVo.getSectionCode(), "");
        if (sameNameList != null && sameNameList.size() > 0)
            return ResultCodeVo.errorCode("同学科下存在同名的体系");
        ShareRelTextbookKps relTextbookKps = ShareRelTextbookKpsVo.voToEntity(relTextbookKpsVo);
       
        UserInfoVo userInfoVo =getUserInfoVo();
        String kpSerialCode = sysCodeService.getCode(SysCodeConstants.KPSERIAL_CODE, "subjectCode",
                        relTextbookKpsVo.getSubjectCode());
        // 在已有的体系上新增体系 把原有体系的关系给新增的体系 以后一个体系可能会有多个教材关系所以这里考虑查出来是个数组
        if (StringUtils.isNotEmpty(relTextbookKpsVo.getKpSerialCode())) {
            List<ShareRelTextbookKps> ShareRelTextBookKpsVoList = shareRelTextBookKpsDao
                            .getRelTextBookKps(relTextbookKpsVo.getKpSerialCode());
            if (ShareRelTextBookKpsVoList != null && ShareRelTextBookKpsVoList.size() > 0) {
                for (ShareRelTextbookKps shareRelTextbookKps : ShareRelTextBookKpsVoList) {
                    relTextbookKps.setModifyIP(getIp());
                    relTextbookKps.setModifyPgm(userInfoVo.getUserName());
                    relTextbookKps.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
                    relTextbookKps.setGid(UUID.randomUUID().toString());
                    relTextbookKps.setKpSerialCode(kpSerialCode);
                    relTextbookKps.setSysVer(0);
                    relTextbookKps.setGradeCode(shareRelTextbookKps.getGradeCode());
                    relTextbookKps.setSectionCode(shareRelTextbookKps.getSectionCode());
                    relTextbookKps.setSubjectCode(shareRelTextbookKps.getSubjectCode());
                    relTextbookKps.setTextbookVerCode(shareRelTextbookKps.getTextbookVerCode());
                    relTextbookKps.setTermCode(shareRelTextbookKps.getTermCode());
                    relTextbookKps.setTextbookCode(shareRelTextbookKps.getTextbookCode());
                    shareRelTextbookKpsDao.saveRelTextBookKps(relTextbookKps);
                }
            }
        } else { // 直接新增一个体系与学科等关系在前台页面选择
            relTextbookKps.setModifyIP(getIp());
            relTextbookKps.setModifyPgm(userInfoVo.getUserName());
            relTextbookKps.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            relTextbookKps.setGid(UUID.randomUUID().toString());
            relTextbookKps.setKpSerialCode(kpSerialCode);
            relTextbookKps.setSysVer(0);
            shareRelTextbookKpsDao.saveRelTextBookKps(relTextbookKps);
        }
        // 保存知识体系
        ShareKnowledgePointSerial shareKnowledgePointSerial = new ShareKnowledgePointSerial();
        shareKnowledgePointSerial.setName(name);
        shareKnowledgePointSerial.setGid(UUID.randomUUID().toString());
        shareKnowledgePointSerial.setCode(kpSerialCode);
        shareKnowledgePointSerial.setModifyIP(getIp());
        shareKnowledgePointSerial.setModifyPgm(userInfoVo.getUserName());
        shareKnowledgePointSerial.setSectionCode(relTextbookKpsVo.getSectionCode());
        shareKnowledgePointSerial.setSubjectCode(relTextbookKpsVo.getSubjectCode());
        shareKnowledgePointSerial.setMemo(memo);
        shareKnowledgePointSerial.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        shareKnowledgePointSerial.setSysVer(0);
        shareKnowledgeSerialDao.saveKnowledgeSerial(shareKnowledgePointSerial);
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        ShareCodeSubject subject = shareCodeSubjectDao.getSubject(shareKnowledgePointSerial.getSubjectCode());
        ShareCodeSection section = shareCodeSectionDao.getSection(shareKnowledgePointSerial.getSectionCode());
        if (section != null)
            nodeTreeVo.setSectionName(section.getName());
        if (subject != null)
            nodeTreeVo.setSubjectName(subject.getName());
        nodeTreeVo.setGid(shareKnowledgePointSerial.getGid());
        nodeTreeVo.setCode(shareKnowledgePointSerial.getCode());
        nodeTreeVo.setPcode(null);
        nodeTreeVo.setState(null);
        nodeTreeVo.setNodeType("knowledgeSerial");
        nodeTreeVo.setKpSerialCode(shareKnowledgePointSerial.getCode());
        nodeTreeVo.setSectionCode(shareKnowledgePointSerial.getSectionCode());
        nodeTreeVo.setSubjectCode(shareKnowledgePointSerial.getSubjectCode());
        nodeTreeVo.setText(shareKnowledgePointSerial.getName());
        nodeTreeVo.setDescription(memo);
        return ResultCodeVo.rightCode("知识体系保存成功", nodeTreeVo);
    }

    /**
     * 更新知识体系
     * 
     * @param name 体系名称
     * @param memo 备注
     * @param gid 主键
     * @return
     * @throws Exception
     */
    public ResultCodeVo updateKnowledgePointSerial(String name, String memo, String gid) throws Exception {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        ShareKnowledgePointSerial shareKnowledgePointSerial = shareKnowledgeSerialDao.getKnowledgeSerial(gid);
        // 同名检查
        List<ShareKnowledgePointSerial> sameNameList = shareKnowledgeSerialDao.getSameNameKnowledgeSerialList(name,
                        shareKnowledgePointSerial.getSubjectCode(), shareKnowledgePointSerial.getSectionCode(), gid);
        if (sameNameList != null && sameNameList.size() > 0)
            return ResultCodeVo.errorCode("同学科下存在同名的体系");

        /*************************** 更新 *********************************/
        shareKnowledgePointSerial.setMemo(memo);
        shareKnowledgePointSerial.setName(name);
        shareKnowledgePointSerial.setModifyIP(getIp());
        shareKnowledgePointSerial.setModifyPgm(userInfoVo.getUserName());
        shareKnowledgePointSerial.setSysVer(shareKnowledgePointSerial.getSysVer() + 1);
        shareKnowledgePointSerial.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        ShareCodeSubject subject = shareCodeSubjectDao.getSubject(shareKnowledgePointSerial.getSubjectCode());
        ShareCodeSection section = shareCodeSectionDao.getSection(shareKnowledgePointSerial.getSectionCode());
        if (section != null)
            nodeTreeVo.setSectionName(section.getName());
        if (subject != null)
            nodeTreeVo.setSubjectName(subject.getName());
        nodeTreeVo.setGid(shareKnowledgePointSerial.getGid());
        nodeTreeVo.setCode(shareKnowledgePointSerial.getCode());
        nodeTreeVo.setPcode(null);
        nodeTreeVo.setState(null);
        nodeTreeVo.setKpSerialCode(shareKnowledgePointSerial.getCode());
        nodeTreeVo.setNodeType("knowledgeSerial");
        nodeTreeVo.setDescription(memo);
        nodeTreeVo.setText(shareKnowledgePointSerial.getName());
        if (shareKnowledgeSerialDao.saveKnowledgeSerial(shareKnowledgePointSerial))
            return ResultCodeVo.rightCode("更新成功", nodeTreeVo);
        return ResultCodeVo.errorCode("更新失败");

    }

    /**
     * 查询知识体系
     * 
     * @param gid 主键
     * @return
     * @throws Exception
     */
    public ShareKnowledgePointSerial getKnowledgeSerial(String gid) throws Exception {
        return shareKnowledgeSerialDao.getKnowledgeSerial(gid);
    }

    /**
     * 
     * 删除知识体系
     * 
     * @param gid
     * @throws Exception
     */
    public ResultCodeVo delKnowledgeSerial(String gid) throws Exception {
        ShareKnowledgePointSerial shareKnowledgePointSerial = shareKnowledgeSerialDao.getKnowledgeSerial(gid);
        if (shareKnowledgePointSerial == null)
            return ResultCodeVo.errorCode("删除失败,没有查询到");
        shareKnowledgePointSerial.setFlagDelete(1);
        shareKnowledgeSerialDao.saveKnowledgeSerial(shareKnowledgePointSerial);
        return ResultCodeVo.rightCode("删除成功");
    }

    /**
     * 根据学科查询只是体系 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public List<ShareKnowledgePointSerial> getKnowledgePointSerialBySubjectCode(String subjectCode) {
        // TODO Auto-generated method stub
        return shareKnowledgeSerialDao.getKnowledgePointSerialBySubjectCode(subjectCode);
    }
}
