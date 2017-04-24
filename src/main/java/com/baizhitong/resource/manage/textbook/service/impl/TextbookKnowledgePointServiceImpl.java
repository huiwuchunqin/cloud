package com.baizhitong.resource.manage.textbook.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareKnowledgePointDao;
import com.baizhitong.resource.dao.share.ShareKnowledgeSerialDao;
import com.baizhitong.resource.dao.share.ShareRelTextbookKpsDao;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.share.ShareKnowledgePoint;
import com.baizhitong.resource.model.share.ShareKnowledgePointSerial;
import com.baizhitong.resource.model.share.ShareRelTextbookKps;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareKnowledgePointVo;
import com.baizhitong.resource.model.vo.share.ZtreeHelpVo;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 教材知识点接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月15日 下午8:27:53
 */
@Service("textbookKnowledgePointService")
public class TextbookKnowledgePointServiceImpl extends BaseService implements TextbookKnowledgePointService {
    /** 教材知识点Dao */
    private @Autowired ShareKnowledgePointDao textbookKnowledgePointDao;
    /** 教材知识点体系关联Dao */
    @Autowired
    private ShareRelTextbookKpsDao            shareRelTextBookKpsDao;
    @Autowired
    private ShareKnowledgeSerialDao           shareKnowledgeSerialDao;
    @Autowired
    private ISysCodeService                   sysCodeService;
    /** 学段dao */
    @Autowired
    private ShareCodeSectionDao               shareCodeSectionDao;
    /** 学科dao */
    @Autowired
    private ShareCodeSubjectDao               shareCodeSubjectDao;

    /**
     * 
     * 查询知识点体系信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编吗
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param textbookCode 教材编码
     * @param termCode 学期编码
     * @return
     * @throws Exception
     */
    @Override
    public List<NodeTreeVo> getNodeTreeList(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception {
        List<ShareRelTextbookKps> shareRelTextbookKpsList = shareRelTextBookKpsDao.getRelTextBookKps(sectionCode,
                        gradeCode, subjectCode, textbookVerCode, textbookCode, termCode);
        List<String> kpSerialCodeList = new ArrayList<String>();
        List<ShareKnowledgePointSerial> shareKnowledgePointSerialList = new ArrayList<ShareKnowledgePointSerial>();
        if (shareRelTextbookKpsList == null || shareRelTextbookKpsList.size() <= 0)
            return new ArrayList<NodeTreeVo>();
        for (ShareRelTextbookKps kps : shareRelTextbookKpsList) {
            kpSerialCodeList.add(kps.getKpSerialCode());
        }
        shareKnowledgePointSerialList = shareKnowledgeSerialDao.getKnowledgeSerialList(kpSerialCodeList);
        List<NodeTreeVo> nodeTreeList = NodeTreeVo.serialListToNodeTreeList(shareKnowledgePointSerialList);
        // 判断体系下面有没有子节点控制 前台 展现形式
        if (nodeTreeList != null && nodeTreeList.size() > 0) {
            for (NodeTreeVo vo : nodeTreeList) {
                ShareCodeSubject subject = shareCodeSubjectDao.getSubject(vo.getSubjectCode());
                ShareCodeSection section = shareCodeSectionDao.getSection(vo.getSectionCode());
                if (section != null)
                    vo.setSectionName(section.getName());
                if (subject != null)
                    vo.setSubjectName(subject.getName());
                List<ShareKnowledgePoint> knowledgePointList = textbookKnowledgePointDao.getKnowledgeList(vo.getCode());
                if (knowledgePointList == null || knowledgePointList.size() <= 0) {
                    vo.setState(null);
                }
            }
        }
        return nodeTreeList;
    }

    /**
     * 查询体系下面的顶级知识点
     * 
     * @param serialCode 知识体系
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getKnowledgeTopNodeTreeList(String serialCode) throws Exception {
        List<String> kpSerialCodeList = new ArrayList<String>();
        kpSerialCodeList.add(serialCode);
        List<ShareKnowledgePoint> knowledgePointList = textbookKnowledgePointDao.getTopKnowledgeList(kpSerialCodeList);
        List<ShareKnowledgePointVo> voList = ShareKnowledgePointVo.getVoList(knowledgePointList);
        List<NodeTreeVo> nodeTreeList = NodeTreeVo.getKnowledgeTree(voList, "");
        if (nodeTreeList != null && nodeTreeList.size() > 0) {
            for (NodeTreeVo vo : nodeTreeList) {
                List<ShareKnowledgePoint> childKnowledgePointList = textbookKnowledgePointDao
                                .getContentKnowLedge(vo.getCode());
                if (childKnowledgePointList == null || childKnowledgePointList.size() <= 0) {
                    vo.setState(null);
                } else {
                    vo.setParent(true);
                }
            }
        }
        return nodeTreeList;
    }

    /**
     * 查询知识点子节点 ()<br>
     * 
     * @param code 父节点编码
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getKnowledgeChildNodeTreeList(String code) throws Exception {
        List<ShareKnowledgePoint> knowledgePointList = textbookKnowledgePointDao.getchildKnowledgeList(code);
        List<ShareKnowledgePointVo> voList = ShareKnowledgePointVo.getVoList(knowledgePointList);
        List<NodeTreeVo> nodeTreeList = NodeTreeVo.getKnowledgeTree(voList, code);
        if (nodeTreeList != null && nodeTreeList.size() > 0) {
            for (NodeTreeVo vo : nodeTreeList) {
                List<ShareKnowledgePoint> childKnowledgePointList = textbookKnowledgePointDao
                                .getContentKnowLedge(vo.getCode());
                if (childKnowledgePointList == null || childKnowledgePointList.size() <= 0) {
                    vo.setState(null);
                } else {
                    vo.setParent(true);
                }
            }
        }
        return nodeTreeList;
    }

    /**
     * 删除知识点
     * 
     * @param gid @throws Exception
     * 
     * @exception
     */
    public ResultCodeVo delKnowLedge(String gid) {
        try {
            ShareKnowledgePoint knowledgePointTextbook = textbookKnowledgePointDao.getByGid(gid);
            // 删除子节点
            List<ShareKnowledgePoint> knowledgeList = textbookKnowledgePointDao
                            .getchildKnowledgeList(knowledgePointTextbook.getCode());
            if (knowledgeList != null && knowledgeList.size() > 0) {
                for (ShareKnowledgePoint knowledge : knowledgeList) {
                    knowledge.setFlagDelete(1);
                    textbookKnowledgePointDao.saveShareKnowledgePoint(knowledge);
                }
            }
            knowledgePointTextbook.setFlagDelete(1);
            textbookKnowledgePointDao.saveShareKnowledgePoint(knowledgePointTextbook);
            return ResultCodeVo.rightCode("知识点删除成功");
        } catch (Exception e) {
            return ResultCodeVo.rightCode("知识点删除失败");
        }
    }

    /**
     * 
     * 查询全名
     * 
     * @param chapter
     * @return
     */
    public String getFullName(ShareKnowledgePoint knowledge) {
        ZtreeHelpVo vo = ZtreeHelpVo.knowToVO(knowledge, "");
        ZtreeHelpVo processVo = genertList(vo);
        String name = "";
        if (processVo != null) {
            name = processVo.getName();
            while (processVo.getChild() != null) {
                processVo = processVo.getChild();
                name = name + ">" + processVo.getName();
            }
        }
        return name;

    }

    /**
     * 保存知识点 @param knowLedgeVo @throws Exception
     * 
     * @exception
     */
    public ResultCodeVo saveKnowLedge(ShareKnowledgePointVo knowLedgeVo) throws Exception {
        List<ShareKnowledgePoint> sameNameList = textbookKnowledgePointDao.getSameNameKnowledgeList(
                        knowLedgeVo.getName().trim(), knowLedgeVo.getKpSerialCode(), knowLedgeVo.getPcode(), "");
        if (sameNameList != null && sameNameList.size() > 0)
            return ResultCodeVo.errorCode("同体系下存在同名的知识点");
       
        UserInfoVo userInfoVo =getUserInfoVo();
        ShareKnowledgePoint knowledgePointTextbook = textbookKnowledgePointDao
                        .getMaxCodeShareKnowLedgePointTextBook(knowLedgeVo.getPcode());
        ShareKnowledgePoint knowledge = ShareKnowledgePointVo.voToEntity(knowLedgeVo);
        knowledge.setModifyIP(getIp());
        knowledge.setModifyPgm(userInfoVo.getUserName());
        knowledge.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        knowledge.setGid(UUID.randomUUID().toString());
        knowledge.setSysVer(1);
        String fullName = getFullName(knowledge);
        knowledge.setFullName(fullName);
        List<ShareRelTextbookKps> ShareRelTextBookKpsVoList = shareRelTextBookKpsDao
                        .getRelTextBookKps(knowledge.getKpSerialCode());
        if (knowledgePointTextbook == null) {
            knowledge.setDispOrder(1);
        } else {
            Integer orderInteger = knowledgePointTextbook.getDispOrder() == null ? 1
                            : knowledgePointTextbook.getDispOrder() + 1;
            knowledge.setDispOrder(orderInteger);
        }
        knowledge.setFlagDelete(0);
        if (ShareRelTextBookKpsVoList != null && ShareRelTextBookKpsVoList.size() > 0) {
            knowledge.setSectionCode(ShareRelTextBookKpsVoList.get(0).getSectionCode());
            knowledge.setSubjectCode(ShareRelTextBookKpsVoList.get(0).getSubjectCode());
        }
        knowledge.setCode(sysCodeService.getCode(SysCodeConstants.KP_CODE, "subjectCode", knowledge.getSubjectCode(),
                        "kpCode", knowledge.getPcode()));
        // 保存
        textbookKnowledgePointDao.saveShareKnowledgePoint(knowledge);
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        nodeTreeVo.setGid(knowledge.getGid());
        nodeTreeVo.setDescription(knowledge.getDescription());
        nodeTreeVo.setCode(knowledge.getCode());
        nodeTreeVo.setPcode(knowledge.getPcode());
        nodeTreeVo.setState(null);
        nodeTreeVo.setKpSerialCode(knowledge.getKpSerialCode());
        nodeTreeVo.setText(knowledge.getName());
        nodeTreeVo.setLevel(knowledge.getLevel());
        nodeTreeVo.setNodeType("knowledge");
        return ResultCodeVo.rightCode("知识点保存成功", nodeTreeVo);

    }

    /**
     * 
     * 更新知识点 @param name 名称 @param description 描述 @param gid 知识点id @throws Exception
     * 
     * @exception
     */
    public ResultCodeVo updateTextBookKnowLedge(String name, String description, String gid) throws Exception {
        ShareKnowledgePoint knowledgePoint = textbookKnowledgePointDao.getByGid(gid);
        if (knowledgePoint == null)
            return ResultCodeVo.errorCode(StringUtils.format("没有查询到知识点{0}", gid));
        // 重名检查
        List<ShareKnowledgePoint> sameNameList = textbookKnowledgePointDao.getSameNameKnowledgeList(name,
                        knowledgePoint.getKpSerialCode(), knowledgePoint.getPcode(), gid);
        if (sameNameList != null && sameNameList.size() > 0) {
            return ResultCodeVo.errorCode("同体系下存在同名的知识点");
        }
        /*************************** 更新 *********************************/
        knowledgePoint.setDescription(description);
        knowledgePoint.setName(name);
        knowledgePoint.setSysVer(knowledgePoint.getSysVer() + 1);
       
        UserInfoVo userInfoVo =getUserInfoVo();
        knowledgePoint.setModifyIP(getIp());
        knowledgePoint.setModifyPgm(userInfoVo.getUserName());
        knowledgePoint.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        /********************* 全名修改 ********************************/
        String fullName = getFullName(knowledgePoint);
        /*
         * int index=fullName.lastIndexOf(">"); if(index>0){ fullName=fullName.substring(index);
         * fullName=fullName+name; }
         */
        knowledgePoint.setFullName(fullName);
        textbookKnowledgePointDao.saveShareKnowledgePoint(knowledgePoint);
        /************************ 修改所有的子节点的全名 ******************/
        List<ShareKnowledgePoint> siblings = textbookKnowledgePointDao.getchildKnowledgeList(knowledgePoint.getCode());
        if (siblings != null && siblings.size() > 0) {
            for (ShareKnowledgePoint knowledge : siblings) {
                String childFullName = getFullName(knowledge);
                knowledge.setFullName(childFullName);
                textbookKnowledgePointDao.saveShareKnowledgePoint(knowledge);
            }
        }
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        nodeTreeVo.setGid(knowledgePoint.getGid());
        nodeTreeVo.setCode(knowledgePoint.getCode());
        nodeTreeVo.setPcode(knowledgePoint.getPcode());
        nodeTreeVo.setState(null);
        nodeTreeVo.setKpSerialCode(knowledgePoint.getKpSerialCode());
        nodeTreeVo.setText(knowledgePoint.getName());
        nodeTreeVo.setLevel(knowledgePoint.getLevel());
        nodeTreeVo.setDescription(description);
        nodeTreeVo.setNodeType("knowledge");
        return ResultCodeVo.rightCode("更新知识点成功", nodeTreeVo);
    }

    /**
     * 根据id查询知识点 @param gid 知识点id @return @throws Exception
     * 
     * @exception
     */
    public ShareKnowledgePointVo getKnowLedgePointTextBook(String gid) throws Exception {
        ShareKnowledgePoint knowledgePointTextbook = textbookKnowledgePointDao.getByGid(gid);
        ShareKnowledgePointVo vo = new ShareKnowledgePointVo(knowledgePointTextbook);
        return vo;
    }

    /**
     * 获取资源相关知识点
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:31:26
     */
    public List<ZtreeHelpVo> getResRelateKnowLedgeZtree(Integer resId, String sectionCode, String subjectCode,
                    Integer resTypeL1) throws Exception {
        List<Map<String, Object>> mapList = textbookKnowledgePointDao.getResRelateKnowLedge(resId, sectionCode,
                        subjectCode, resTypeL1);
        List<ZtreeHelpVo> voList = ZtreeHelpVo.mapListToVOList(mapList);
        return voList;
    }

    public List<List<ZtreeHelpVo>> getKnowledgeZtree(Integer resId, Integer resTypeL1) throws Exception {
        List<Map<String, Object>> mapList = textbookKnowledgePointDao.getResRelateKnowLedge(resId, "", "", resTypeL1);
        List<ZtreeHelpVo> voList = ZtreeHelpVo.mapListToVOList(mapList);
        if (voList == null || voList.size() <= 0)
            return null;
        List<List<ZtreeHelpVo>> list = new ArrayList<List<ZtreeHelpVo>>();
        for (ZtreeHelpVo helpVo : voList) {
            List<ZtreeHelpVo> empty = new ArrayList<ZtreeHelpVo>();
            empty.add(helpVo);
            list.add(getParentList(helpVo, empty));
        }
        return list;
    }

    public List<ZtreeHelpVo> getParentList(ZtreeHelpVo helpVo, List<ZtreeHelpVo> list) throws Exception {
        if (!StringUtils.isEmpty(helpVo.getPcode())) {
            ShareKnowledgePoint knowledge = textbookKnowledgePointDao.getByCode(helpVo.getPcode());
            ZtreeHelpVo zVo = ZtreeHelpVo.knowToVO(knowledge, "");
            list.add(0, zVo);
            return getParentList(zVo, list);
        } else {
            if (!StringUtils.isEmpty(helpVo.getKpSerialCode())) {
                helpVo.setPcode("top" + helpVo.getKpSerialCode());
                List<String> kpSerialCodelist2 = new ArrayList<String>();
                kpSerialCodelist2.add(helpVo.getKpSerialCode());
                List<ShareKnowledgePointSerial> knowledgeSerialList = shareKnowledgeSerialDao
                                .getKnowledgeSerialList(kpSerialCodelist2);
                if (knowledgeSerialList != null && knowledgeSerialList.size() > 0) {
                    ZtreeHelpVo zVo = ZtreeHelpVo.serialToVO(knowledgeSerialList.get(0));
                    list.add(0, zVo);
                }

            }
            return list;
        }
    }

    /**
     * 查询资源相关知识点的父节点 ()<br>
     * 
     * @param resId
     * @param sectionCode
     * @param subjectCode
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getResRelateKnowLedgeParent(Integer resId, String sectionCode, String subjectCode,
                    Integer resTypeL1) throws Exception {
        List<ZtreeHelpVo> orgionVoList = getResRelateKnowLedgeZtree(resId, sectionCode, subjectCode, resTypeL1);
        if (orgionVoList == null || orgionVoList.size() <= 0)
            return null;
        List<ZtreeHelpVo> voList = new ArrayList<ZtreeHelpVo>();
        List<ZtreeHelpVo> finalList = new ArrayList<ZtreeHelpVo>();
        for (ZtreeHelpVo vo : orgionVoList) {
            voList.add(genertList(vo));
        }
        if (voList == null || voList.size() <= 0)
            return null;
        for (ZtreeHelpVo vo : voList) {
            String name = "";
            List<String> kpSerialCodelist2 = new ArrayList<String>();
            kpSerialCodelist2.add(vo.getKpSerialCode());
            List<ShareKnowledgePointSerial> knowledgeSerialList = shareKnowledgeSerialDao
                            .getKnowledgeSerialList(kpSerialCodelist2);
            if (knowledgeSerialList != null && knowledgeSerialList.size() > 0) {
                name = knowledgeSerialList.get(0).getName();
            }
            name = name + ">" + vo.getName();
            ZtreeHelpVo finalVo = new ZtreeHelpVo();
            while (vo.getChild() != null) {
                vo = vo.getChild();
                name = name + ">" + vo.getName();
            }
            finalVo.setName(name);
            finalVo.setCode(vo.getCode());
            finalList.add(finalVo);
        }
        return finalList;
    }

    /**
     * 
     * 查询初次加载数据
     * 
     * @param subjectCode 学科编码
     * @param sectionCode 学段编码
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getKnowledgeTreeRoot(String subjectCode, String sectionCode, Integer resId)
                    throws Exception {
        // 根节点
        List<ShareRelTextbookKps> shareRelTextbookKpsList = shareRelTextBookKpsDao.getRelTextBookKps(sectionCode, "",
                        subjectCode, "", "", "");
        List<String> kpSerialCodeList = new ArrayList<String>();
        if (shareRelTextbookKpsList == null || shareRelTextbookKpsList.size() <= 0)
            return null;
        for (ShareRelTextbookKps kps : shareRelTextbookKpsList) {
            kpSerialCodeList.add(kps.getKpSerialCode());
        }
        List<ShareKnowledgePointSerial> shareKnowledgePointSerialList = shareKnowledgeSerialDao
                        .getKnowledgeSerialList(kpSerialCodeList);
        List<ZtreeHelpVo> allZtreeVoList = ZtreeHelpVo.serialListToVO(shareKnowledgePointSerialList);// 体系列表
        for (ZtreeHelpVo vo : allZtreeVoList) {
            vo.setPcode("root");
        }
        /******************* 增加根节点知识点 ***************************/

        ZtreeHelpVo root = new ZtreeHelpVo();
        root.setName("知识点");
        root.setCode("root");
        root.setNodeType("knowledgeSerial");
        root.setIsParent(true);
        /******************* 给知识体系下面加一层知识点 ***************************/
        if (shareKnowledgePointSerialList != null && shareKnowledgePointSerialList.size() > 0) { // 查询体系对用的知识点
            for (ShareKnowledgePointSerial serial : shareKnowledgePointSerialList) {
                List<String> kpSerialCodelist2 = new ArrayList<String>();
                kpSerialCodelist2.add(serial.getCode());
                List<ShareKnowledgePoint> knowledgeList = textbookKnowledgePointDao
                                .getTopKnowledgeList(kpSerialCodelist2);
                List<ZtreeHelpVo> ztreeVoList = ZtreeHelpVo.knowListToVOList(knowledgeList, "top-" + serial.getCode());
                if (ztreeVoList != null && ztreeVoList.size() > 0) {
                    allZtreeVoList.addAll(ztreeVoList);
                }
            }
        }
        isParentOperate(allZtreeVoList);// 是否有子节点判断
        return allZtreeVoList;
        /*
         * //资源已有的知识点 List<Map<String,Object>> list=
         * textbookKnowledgePointDao.getResRelateKnowLedge(resId, sectionCode, subjectCode);
         * List<ZtreeHelpVo> selectedVoList=ZtreeHelpVo.mapListToVOList(list); List<ZtreeHelpVo>
         * selectedReleatedVoList= new ArrayList<ZtreeHelpVo>();
         * if(selectedVoList!=null&&selectedVoList.size()>0){ for(ZtreeHelpVo
         * ztreeVo:selectedVoList){
         * selectedReleatedVoList.addAll(getAllParentAndSiblingNode(ztreeVo)); } }
         * if(selectedReleatedVoList!=null&&selectedReleatedVoList.size()>0){ //合并根节点和已有节点
         * allZtreeVoList.addAll(selectedReleatedVoList); }
         * openAll(allZtreeVoList,selectedReleatedVoList); return allZtreeVoList;
         */
    }

    public List<ZtreeHelpVo> getAll(String subjectCode, String sectionCode, Integer resId, String gradeCode,
                    String textbookVerCode, String termCode) throws Exception {
        // 根节点
        List<ShareRelTextbookKps> shareRelTextbookKpsList = shareRelTextBookKpsDao.getRelTextBookKps(sectionCode,
                        gradeCode, subjectCode, textbookVerCode, "", termCode);
        List<String> kpSerialCodeList = new ArrayList<String>();
        if (shareRelTextbookKpsList == null || shareRelTextbookKpsList.size() <= 0)
            return null;
        for (ShareRelTextbookKps kps : shareRelTextbookKpsList) {
            kpSerialCodeList.add(kps.getKpSerialCode());
        }
        List<ShareKnowledgePointSerial> shareKnowledgePointSerialList = shareKnowledgeSerialDao
                        .getKnowledgeSerialList(kpSerialCodeList);
        List<ZtreeHelpVo> allZtreeVoList = ZtreeHelpVo.serialListToVO(shareKnowledgePointSerialList);// 体系列表
        for (ZtreeHelpVo vo : allZtreeVoList) {
            vo.setPcode(null);
        }
        /******************* 增加根节点知识点 ***************************/

        ZtreeHelpVo root = new ZtreeHelpVo();
        root.setName("知识点");
        root.setCode("root");
        root.setNodeType("knowledgeSerial");
        root.setIsParent(true);
        if (shareKnowledgePointSerialList != null && shareKnowledgePointSerialList.size() > 0) { // 查询体系对用的知识点
            /******************* 给知识体系下面加一层知识点 ***************************/
            for (ShareKnowledgePointSerial serial : shareKnowledgePointSerialList) {
                List<String> kpSerialCodelist2 = new ArrayList<String>();
                kpSerialCodelist2.add(serial.getCode());
                List<ShareKnowledgePoint> knowledgeList = textbookKnowledgePointDao
                                .getTopKnowledgeList(kpSerialCodelist2);
                List<ShareKnowledgePoint> leftKnowledgeList = textbookKnowledgePointDao
                                .getNotTopKnowledgeList(kpSerialCodelist2);
                List<ZtreeHelpVo> ztreeVoList = ZtreeHelpVo.knowListToVOList(knowledgeList, "top-" + serial.getCode());
                List<ZtreeHelpVo> leftZtreeVoList = ZtreeHelpVo.knowListToVOList(leftKnowledgeList, "");
                if (ztreeVoList != null && ztreeVoList.size() > 0) {
                    allZtreeVoList.addAll(ztreeVoList);
                }
                if (leftZtreeVoList != null && leftZtreeVoList.size() > 0) {
                    allZtreeVoList.addAll(leftZtreeVoList);
                }
            }
        }
        /*
         * isParentOperate(allZtreeVoList);//是否有子节点判断
         */ return allZtreeVoList;
    }

    // 展开节点
    public List<ZtreeHelpVo> openAll(List<ZtreeHelpVo> voList, List<ZtreeHelpVo> selectedVoList) {
        if (voList == null || voList.size() <= 0 || selectedVoList == null || selectedVoList.size() <= 0) {
            return null;
        }
        for (ZtreeHelpVo vo : voList) {
            for (ZtreeHelpVo voOther : voList) {
                if (vo.getCode().equals(voOther.getPcode()) && !"knowledgeSerial".equals(vo.getNodeType())) {
                    vo.setOpen(true);
                }
            }
        }
        for (ZtreeHelpVo vo : voList) { // 子节点打开或者子节点被选中的情况打开
            for (ZtreeHelpVo voOther : voList) {
                boolean iselected = selectedVoList.contains(voOther);
                if (vo.getCode().equals(voOther.getPcode()) && (voOther.isOpen() || iselected)) {
                    vo.setOpen(true);
                }
            }
        }
        return voList;
    }

    // 找到自己的兄弟 和父节点
    public List<ZtreeHelpVo> getAllParentAndSiblingNode(ZtreeHelpVo ztteeVo) {
        List<ZtreeHelpVo> voList = new ArrayList<ZtreeHelpVo>();
        // 查询兄弟
        if (StringUtils.isEmpty(ztteeVo.getPcode()))
            return voList;// 顶级节点就不需要找兄弟了
        List<ShareKnowledgePoint> knowledgeList = textbookKnowledgePointDao.getchildKnowledgeList(ztteeVo.getPcode());
        voList = ZtreeHelpVo.knowListToVOList(knowledgeList, "");
        isParentOperate(voList);
        // 查询父亲
        ShareKnowledgePoint closestParentKnow = textbookKnowledgePointDao.getByCode(ztteeVo.getPcode());
        ZtreeHelpVo ztreeVo = ZtreeHelpVo.knowToVO(closestParentKnow, "");
        // 如果不是顶级节点 继续查询兄弟
        if (closestParentKnow != null && StringUtils.isNotEmpty(closestParentKnow.getPcode())) {
            voList.addAll(getAllParentAndSiblingNode(ztreeVo));
        }
        return voList;
    }

    /**
     * 查询子节点
     * 
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本
     * @param pcCode 父节点编码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 上午10:10:00
     */
    public List<ZtreeHelpVo> getContentKnowLedge(String pcCode) throws Exception {
        List<ShareKnowledgePoint> contentKnowledgeList = textbookKnowledgePointDao.getContentKnowLedge(pcCode);
        List<ZtreeHelpVo> ztreeVoList = ZtreeHelpVo.knowListToVOList(contentKnowledgeList, "");
        isParentOperate(ztreeVoList);
        return ztreeVoList;
    }

    // 是否有子节点判断
    public List<ZtreeHelpVo> isParentOperate(List<ZtreeHelpVo> voList) {
        if (voList == null || voList.size() <= 0)
            return null;
        for (ZtreeHelpVo ZtreeHelpVo : voList) {
            Map<String, Object> childCount = textbookKnowledgePointDao.getChildCount(ZtreeHelpVo.getCode());
            if (childCount != null) {
                Integer count = MapUtils.getInteger(childCount, "count");
                if (count > 0) {
                    ZtreeHelpVo.setParent(true);
                }
            }
        }
        return voList;
    }

    /**
     * 得到所有父节点编码
     * 
     * @param code
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月24日 下午4:33:14
     */
    public ZtreeHelpVo getTopParent(ZtreeHelpVo helpVo, String code) throws Exception {
        ShareKnowledgePoint closestParentKnow = textbookKnowledgePointDao.getByCode(code);
        ZtreeHelpVo vo = ZtreeHelpVo.knowToVO(closestParentKnow, "");
        if (vo == null)
            return null;
        vo.setChild(helpVo);
        return genertList(vo);

    }

    public ZtreeHelpVo genertList(ZtreeHelpVo vo) {
        if (vo == null || StringUtils.isEmpty(vo.getPcode()))
            return vo;
        ShareKnowledgePoint closestParentKnow = textbookKnowledgePointDao.getByCode(vo.getPcode());
        ZtreeHelpVo parentVo = ZtreeHelpVo.knowToVO(closestParentKnow, "");
        if (parentVo == null)
            return vo;
        parentVo.setChild(vo);
        return genertList(parentVo);
    }

    /**
     * 获取资源相关知识点
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:31:26
     */
    public List<ZtreeHelpVo> getResRelateKnowLedgeZtrees(Integer resId, Integer resTypeL1) throws Exception {
        List<Map<String, Object>> list = textbookKnowledgePointDao.getResRelateKnowLedge(resId, "", "", resTypeL1);
        List<ZtreeHelpVo> voList = ZtreeHelpVo.mapListToVOList(list);
        if (voList == null || voList.size() <= 0)
            return null;
        for (ZtreeHelpVo helpVo : voList) {
            ZtreeHelpVo vo = new ZtreeHelpVo();
            vo.setCode(helpVo.getCode());
            ZtreeHelpVo topParent = getTopParent(vo, helpVo.getPcode());
            helpVo.setTopParent(topParent);
        }
        return voList;
    }

    /**
     * 根据关系查询 教材知识点关系列表 @param kpSerialName @return @throws Exception
     * 
     * @exception
     */
    public List<ShareRelTextbookKps> getRelTextBookKps(String kpSerialCode) throws Exception {
        return shareRelTextBookKpsDao.getRelTextBookKps(kpSerialCode);
    }

    /**
     * 更新顺序 ()<br>
     * 
     * @param gid
     * @param type 移动类型 1 升序2降序
     * @return
     */
    public ResultCodeVo updateDispOrder(String kpSerialCode, String gid, Integer type) {
        // 得到按排序将序排序的知识点列表
        List<Map<String, Object>> knowledgeList = textbookKnowledgePointDao.getSiblingKnowledgeList(kpSerialCode, gid);
        List<ShareKnowledgePointVo> knowledgePointList = ShareKnowledgePointVo.getMapToVoList(knowledgeList);
        if (type == 1) {
            // 已经在最上面了
            if (gid.equals(knowledgePointList.get(0).getGid())) {
                return ResultCodeVo.errorCode("已经在最上面了");
            }
            int i = 1;
            // 重新给予顺序 4 6 8 10 12 14 并且 上升者减3就可以直接直接下降一级
            for (ShareKnowledgePointVo vo : knowledgePointList) {
                if (gid.equals(vo.getGid())) {
                    textbookKnowledgePointDao.updateDispOrder(vo.getGid(), i - 3);
                } else {
                    textbookKnowledgePointDao.updateDispOrder(vo.getGid(), i);
                }
                i = i + 2;
            }
        }
        if (type == 2) {
            // 已经在最下面了
            if (gid.equals(knowledgePointList.get(knowledgePointList.size() - 1).getGid())) {
                return ResultCodeVo.errorCode("已经在最下面了");
            }
            int i = 10;
            // 重新给予顺序 0 2 4 6 8 10 12并且 上升者加3就可以直接直接下降一级
            for (ShareKnowledgePointVo vo : knowledgePointList) {
                if (gid.equals(vo.getGid())) {
                    textbookKnowledgePointDao.updateDispOrder(vo.getGid(), i + 3);
                } else {
                    textbookKnowledgePointDao.updateDispOrder(vo.getGid(), i);
                }
                i = i + 2;
            }
        }
        return ResultCodeVo.rightCode("更新成功");
    }
}
