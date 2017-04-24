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
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.dao.share.ShareCodeTextbookVerDao;
import com.baizhitong.resource.dao.share.ShareTextbookChapterDao;
import com.baizhitong.resource.dao.share.ShareTextbookDao;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.model.share.ShareCodeGrade;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.resource.model.share.ShareCodeTextbookVer;
import com.baizhitong.resource.model.share.ShareTextbook;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareKnowledgePointVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookChapterVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookVo;
import com.baizhitong.resource.model.vo.share.ZtreeHelpVo;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DataUtils;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 教材章节接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月15日 下午1:53:34
 */
@Service("textbookChapterService")
public class TextbookChapterServiceImpl extends BaseService implements TextbookChapterService {
    /** 教材章节 DAO */
    private @Autowired ShareTextbookChapterDao textbookChapterDao;
    /** 教材dao */
    private @Autowired ShareTextbookDao        textbookDao;
    /** 教材service */
    private @Autowired TextbookServiceImpl     textbookService;
    /** 主键生成接口 */
    @Autowired
    private ISysCodeService                    sysCodeService;
    /** 学段dao */
    @Autowired
    private ShareCodeSectionDao                shareCodeSectionDao;
    /** 学科dao */
    @Autowired
    private ShareCodeSubjectDao                shareCodeSubjectDao;
    /** 年级dao */
    @Autowired
    private ShareCodeGradeDao                  shareCodeGradeDao;
    /** 教材版本dao */
    @Autowired
    private ShareCodeTextbookVerDao            shareCodeTextbookVerDao;

    /**
     * 
     * 查询教材章节信息
     * 
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getNodeTreeList(String subjectCode, String textbookVerCode, String textbookCode,
                    String sectionCode, String gradeCode, String termCode) throws Exception {
        // 查询教材
        List<ShareTextbookVo> textBookVoList = textbookService.getTextbookList(sectionCode, gradeCode, subjectCode,
                        textbookVerCode, textbookCode, termCode);
        if (textBookVoList == null || textBookVoList.size() <= 0)
            return new ArrayList<NodeTreeVo>();
        List<NodeTreeVo> nodeTreeList = new ArrayList<NodeTreeVo>();
        nodeTreeList = NodeTreeVo.textbookListToNodeTreeList(textBookVoList);
        // 判断教材下面有没有章节 控制前台页面 节点展现形式 1文件夹 2文件
        if (nodeTreeList != null && nodeTreeList.size() > 0) {
            for (NodeTreeVo vo : nodeTreeList) {
                ShareCodeSubject subject = shareCodeSubjectDao.getSubject(vo.getSubjectCode());
                ShareCodeSection section = shareCodeSectionDao.getSection(vo.getSectionCode());
                ShareCodeGrade grade = shareCodeGradeDao.getGrade(vo.getGradeCode());
                ShareCodeTextbookVer textbookVer = shareCodeTextbookVerDao.getTextbookVer(vo.getTextbookVerCode());
                if (section != null)
                    vo.setSectionName(section.getName());
                if (subject != null)
                    vo.setSubjectName(subject.getName());
                if (grade != null)
                    vo.setGradeName(grade.getName());
                if (textbookVer != null)
                    vo.setTextbookVerName(textbookVer.getName());
                List<ShareTextbookChapterVo> textChildrenChapterList = getChapterList(vo.getCode());
                if (textChildrenChapterList == null || textChildrenChapterList.size() <= 0) {
                    vo.setState(null);
                }
            }
        }
        return nodeTreeList;
    }

    /**
     * 查询教材下面的章节 ()<br>
     * 
     * @param textbookCode 教材编码
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getChapterTopNodeTreeList(String textbookCode, String ChapterName) throws Exception {
        // 查询章节的顶级节点
        List<ShareTextbookChapter> textChildrenChapterList = textbookChapterDao.getTopChapterList(textbookCode,
                        ChapterName);
        List<ShareTextbookChapterVo> voList = ShareTextbookChapterVo.entityListToVo(textChildrenChapterList);
        List<NodeTreeVo> nodeTreeList = NodeTreeVo.getNodeTree(voList, "");
        // 判断章节是否有子节点
        if (nodeTreeList != null && nodeTreeList.size() > 0) {
            for (NodeTreeVo vo : nodeTreeList) {
                List<ShareTextbookChapter> chapterList = textbookChapterDao.getListInfoByPcode(vo.getCode(), "");
                if (chapterList == null || chapterList.size() <= 0) {
                    vo.setState(null);
                } else {
                    vo.setParent(true);
                }
            }
        }
        return nodeTreeList;
    }

    /**
     * 查询章节的子节点 ()<br>
     * 
     * @param pcode 父节点编码
     * @return
     * @throws Exception
     */
    public List<NodeTreeVo> getChapterChildrenNodeTreeList(String pcode, String chapterName) throws Exception {
        // 查询章节子节点
        List<ShareTextbookChapter> textChildrenChapterList = textbookChapterDao.getListInfoByPcode(pcode, chapterName);
        List<ShareTextbookChapterVo> voList = ShareTextbookChapterVo.entityListToVo(textChildrenChapterList);
        List<NodeTreeVo> nodeTreeList = NodeTreeVo.getNodeTree(voList, pcode);
        // 判断章节是否有子节点
        if (nodeTreeList != null && nodeTreeList.size() > 0) {
            for (NodeTreeVo vo : nodeTreeList) {
                List<ShareTextbookChapter> chapterList = textbookChapterDao.getListInfoByPcode(vo.getCode(), "");
                if (chapterList == null || chapterList.size() <= 0) {
                    vo.setState(null);
                } else {
                    vo.setParent(true);
                }
            }
        }
        return nodeTreeList;
    }

    /**
     * 
     * 查询首次加载数据
     * 
     * @param resId 资源id
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCodes 教材版本编码
     * @param textbookCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getVersionTreeRoot(Integer resId, String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode) throws Exception {
        // 章节列表
        List<ShareTextbook> textBookVoList = textbookDao.getTextbookList(sectionCode, gradeCode, subjectCode,
                        textbookVerCode, textbookCode, "");
        List<ZtreeHelpVo> ztreeVoAllList = new ArrayList<ZtreeHelpVo>();
        if (textBookVoList == null || textBookVoList.size() <= 0)
            return null;
        ztreeVoAllList = ZtreeHelpVo.textbookListToVO(textBookVoList);
        for (ZtreeHelpVo vo : ztreeVoAllList) {
            vo.setPcode("root");
        }
        /***************** 增加顶级节点 章节 *********************************/
        ZtreeHelpVo root = new ZtreeHelpVo();
        root.setName("章节");
        root.setCode("root");
        root.setNodeType("textbook");
        root.setParent(true);
        ztreeVoAllList.add(root);
        /***************** 给教材下面添加一层章节 *********************************/
        for (ShareTextbook textbook : textBookVoList) { // 查询教材对应章节
            List<ShareTextbookChapter> textChildrenChapterList = textbookChapterDao
                            .getTopChapterList(textbook.getTbCode(), "");
            List<ZtreeHelpVo> ztreeVoList = new ArrayList<ZtreeHelpVo>();
            ztreeVoList = ZtreeHelpVo.chapterListToVOList(textChildrenChapterList, "top-" + textbook.getTbCode());
            if (ztreeVoList != null && ztreeVoList.size() > 0) {
                ztreeVoAllList.addAll(ztreeVoList);
            }
        }
        isParentOperate(ztreeVoAllList); // 是否是父节点判断

        return ztreeVoAllList;
    }

    /**
     * 查询所有的教材和章节 ()<br>
     * 
     * @param resId
     * @param sectionCode
     * @param gradeCode
     * @param subjectCode
     * @param textbookVerCode
     * @param textbookCode
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getAll(Integer resId, String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception {
        // 章节列表
        List<ShareTextbook> textBookVoList = textbookDao.getTextbookList(sectionCode, gradeCode, subjectCode,
                        textbookVerCode, textbookCode, termCode);
        List<ZtreeHelpVo> ztreeVoAllList = new ArrayList<ZtreeHelpVo>();
        if (textBookVoList == null || textBookVoList.size() <= 0)
            return null;
        ztreeVoAllList = ZtreeHelpVo.textbookListToVO(textBookVoList);
        for (ZtreeHelpVo vo : ztreeVoAllList) {
            vo.setPcode(null);
        }
        /***************** 增加顶级节点 章节 *********************************/
        /*
         * ZtreeHelpVo root=new ZtreeHelpVo(); root.setName("章节"); root.setCode("root");
         * root.setNodeType("textbook"); root.setParent(true); ztreeVoAllList.add(root);
         */

        for (ShareTextbook textbook : textBookVoList) { // 查询教材对应章节
            /***************** 给教材下面添加一层章节 *********************************/
            List<ShareTextbookChapter> textChildrenChapterList = textbookChapterDao
                            .getTopChapterList(textbook.getTbCode(), "");
            List<ZtreeHelpVo> ztreeVoList = new ArrayList<ZtreeHelpVo>();
            ztreeVoList = ZtreeHelpVo.chapterListToVOList(textChildrenChapterList, "top-" + textbook.getTbCode());
            if (ztreeVoList != null && ztreeVoList.size() > 0) {
                ztreeVoAllList.addAll(ztreeVoList);
            }
            /***************** 加上剩余的章节 *********************************/
            List<ZtreeHelpVo> leftZtreeVoList = new ArrayList<ZtreeHelpVo>();
            List<ShareTextbookChapter> chapterList = textbookChapterDao.getNotTopChapterList(textbook.getTbCode());
            leftZtreeVoList = ZtreeHelpVo.chapterListToVOList(chapterList, "");
            if (leftZtreeVoList != null && leftZtreeVoList.size() > 0) {
                ztreeVoAllList.addAll(leftZtreeVoList);
            }
        }
        return ztreeVoAllList;
    }

    // 展开节点
    public List<ZtreeHelpVo> openAll(List<ZtreeHelpVo> voList, List<ZtreeHelpVo> selectedVoList) {
        if (voList == null || voList.size() <= 0) {
            return null;
        }
        for (ZtreeHelpVo vo : voList) {
            for (ZtreeHelpVo voOther : voList) {
                if (vo.getCode().equals(voOther.getPcode()) && !"textbook".equals(vo.getNodeType())) {
                    vo.setOpen(true);
                }
            }
        }
        for (ZtreeHelpVo vo : voList) {// 子节点打开或者子节点被选中的情况打开
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
        List<ShareTextbookChapter> chapterList = textbookChapterDao.getListInfo(ztteeVo.getPcode());
        voList = ZtreeHelpVo.chapterListToVOList(chapterList, "");
        isParentOperate(voList);
        // 查询父亲
        ShareTextbookChapter parentChapter = textbookChapterDao.getByCode(ztteeVo.getPcode());
        ZtreeHelpVo ztreeVo = ZtreeHelpVo.chapterToVO(parentChapter, "");
        // 如果不是顶级节点 继续查询兄弟
        if (ztreeVo != null && StringUtils.isNotEmpty(ztreeVo.getPcode())) {
            voList.addAll(getAllParentAndSiblingNode(ztreeVo));
        }
        return voList;
    }

    /**
     * 查询教材章节的子节点
     * 
     * @param pcCode 父节点编码
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getContentVersion(String pcCode) throws Exception {
        List<Map<String, Object>> mapList = textbookChapterDao.getContentVersion(pcCode);
        List<ZtreeHelpVo> voList = ZtreeHelpVo.mapListToVOList(mapList);
        isParentOperate(voList);
        return voList;
    }

    // 是否有父节点操作
    public List<ZtreeHelpVo> isParentOperate(List<ZtreeHelpVo> voList) {
        if (voList == null || voList.size() <= 0)
            return null;
        for (ZtreeHelpVo ztreeVo : voList) {
            Map<String, Object> childCount = textbookChapterDao.getChildCount(ztreeVo.getCode());
            if (childCount != null) {
                Integer count = MapUtils.getInteger(childCount, "count");
                if (count > 0) {
                    ztreeVo.setParent(true);
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
        ShareTextbookChapter closestParentChapter = textbookChapterDao.getByCode(code);
        ZtreeHelpVo vo = ZtreeHelpVo.chapterToVO(closestParentChapter, "");
        if (vo == null)
            return null;
        vo.setChild(helpVo);
        return genertList(vo);

    }

    /**
     * 
     * 把节点和其父节点组成一个对象
     * 
     * @param vo
     * @return
     */
    public ZtreeHelpVo genertList(ZtreeHelpVo vo) {
        if (vo == null || StringUtils.isEmpty(vo.getPcode()))
            return vo;
        ShareTextbookChapter closestParentChapter = textbookChapterDao.getByCode(vo.getPcode());
        ZtreeHelpVo parentVo = ZtreeHelpVo.chapterToVO(closestParentChapter, "");
        if (parentVo == null)
            return vo;
        parentVo.setChild(vo);
        return genertList(parentVo);
    }

    /**
     * 获取资源相关章节
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:27:20
     */
    public List<ZtreeHelpVo> getResRelateChapterZtree(Integer resId, String sectionCode, String subjectCode,
                    String gradeCode, String textbookVerCode, String tbCode, Integer resTypeL1) throws Exception {
        List<Map<String, Object>> mapList = textbookChapterDao.getResRelateChapter(resId, sectionCode, subjectCode,
                        gradeCode, textbookVerCode, tbCode, resTypeL1);
        List<ZtreeHelpVo> voList = ZtreeHelpVo.mapListToVOList(mapList);
        return voList;
    }

    /**
     * 查询选中的节点和其父节点
     * 
     * @param resId
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param textbookVerCode
     * @param tbCode
     * @return
     * @throws Exception
     */
    public List<ZtreeHelpVo> getResRelateChapterAndParent(Integer resId, String sectionCode, String subjectCode,
                    String gradeCode, String textbookVerCode, String tbCode, Integer resTypeL1) throws Exception {
        List<ZtreeHelpVo> orgionVoList = getResRelateChapterZtree(resId, sectionCode, subjectCode, gradeCode,
                        textbookVerCode, tbCode, resTypeL1);
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
            ShareTextbook textbook = textbookDao.getShareTextbookByTbCode(vo.getTextbookCode());
            String name = vo.getName();
            ZtreeHelpVo finalVo = new ZtreeHelpVo();
            if (textbook != null)
                name = textbook.getTbName();
            name = name + ">" + vo.getName();
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

    public List<List<ZtreeHelpVo>> getChapterZtree(Integer resId, Integer resTypeL1) throws Exception {
        List<Map<String, Object>> mapList = textbookChapterDao.getResRelateChapter(resId, "", "", "", "", "",
                        resTypeL1);
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
            ShareTextbookChapter chapter = textbookChapterDao.getByCode(helpVo.getPcode());
            ZtreeHelpVo zVo = ZtreeHelpVo.chapterToVO(chapter, "");
            list.add(0, zVo);
            return getParentList(zVo, list);
        } else {
            if (!StringUtils.isEmpty(helpVo.getTextbookCode())) {
                helpVo.setPcode("top" + helpVo.getTextbookCode());
                ShareTextbook textbook = textbookDao.getShareTextbookByTbCode(helpVo.getTextbookCode());
                ZtreeHelpVo zVo = ZtreeHelpVo.textbooktoVo(textbook);
                list.add(0, zVo);
            }
            return list;
        }
    }

    /**
     * 获取资源相关章节
     * 
     * @param resId 资源id
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月19日 下午3:27:20
     */
    public List<ZtreeHelpVo> getResRelateChapterZtree(Integer resId, Integer resTypeL1) throws Exception {
        List<Map<String, Object>> mapList = textbookChapterDao.getResRelateChapter(resId, "", "", "", "", "",
                        resTypeL1);
        List<ZtreeHelpVo> voList = ZtreeHelpVo.mapListToVOList(mapList);
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
     * 
     * 删除教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param request 请求 @param gid 教材id @return @exception
     */
    @Override
    public ResultCodeVo deleteChapter(String gid) {
        try {
            ShareTextbookChapter chapter = textbookChapterDao.getChapterByGid(gid);
            if (chapter == null)
                return ResultCodeVo.errorCode("没有查询到节点！");
            List<ShareTextbookChapter> childList = textbookChapterDao.getListInfoByPcode(chapter.getCode(), "");
            // 该章节下有子章节的情况
            if (childList != null && childList.size() > 0) {
                for (ShareTextbookChapter textbookChapter : childList) {
                    textbookChapter.setFlagDelete(1);
                    textbookChapterDao.addChapterInfo(textbookChapter);
                }
            }
            chapter.setFlagDelete(1);
            textbookChapterDao.addChapterInfo(chapter);
        } catch (Exception e) {
            return ResultCodeVo.rightCode("删除章节失败");
        }
        return ResultCodeVo.rightCode("删除章节成功");

    }

    /**
     * 
     * 查询全名
     * 
     * @param chapter
     * @return
     */
    public String getFullName(ShareTextbookChapter chapter) {
        ZtreeHelpVo vo = ZtreeHelpVo.chapterToVO(chapter, "");
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
     * 
     * 添加教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param chapter 教材章节信息 @return 添加是否成功 @throws Exception @exception
     */
    public ResultCodeVo addChapterInfo(ShareTextbookChapter chapter) throws Exception {
        List<ShareTextbookChapter> sameNameList = textbookChapterDao.getSameNameChapterList(chapter.getName().trim(),
                        chapter.getTextbookCode(), chapter.getPcode(), "");
        if (sameNameList != null && sameNameList.size() > 0)
            return ResultCodeVo.errorCode("同教材下存在重名的章节");
       
        UserInfoVo userInfoVo =getUserInfoVo();
        String name = getFullName(chapter);
        chapter.setModifyIP(getIp());
        chapter.setFullName(name);
        chapter.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        chapter.setModifyPgm(userInfoVo.getUserName());
        chapter.setCode(sysCodeService.getCode(SysCodeConstants.CHAPTER_CODE, "textbookCode", chapter.getTextbookCode(),
                        "chapterCode", chapter.getPcode()));
        chapter.setSysVer(1);
        chapter.setFlagDelete(0);
        chapter.setGid(UUID.randomUUID().toString());
        List<ShareTextbookChapter> siblings = textbookChapterDao.getListInfo(chapter.getPcode());
        if (siblings == null || siblings.size() <= 0) {
            chapter.setDispOrder(1);
        } else {
            chapter.setDispOrder(siblings.get(0).getDispOrder() + 1);
        }
        textbookChapterDao.addChapterInfo(chapter);
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        nodeTreeVo.setGid(chapter.getGid());
        nodeTreeVo.setCode(chapter.getCode());
        nodeTreeVo.setPcode(chapter.getPcode());
        nodeTreeVo.setState(null);
        nodeTreeVo.setTbCode(chapter.getTextbookCode());
        nodeTreeVo.setLevel(chapter.getLevel());
        nodeTreeVo.setText(chapter.getName());
        nodeTreeVo.setNodeType("chapter");
        nodeTreeVo.setDescription(chapter.getDescription());
        return ResultCodeVo.rightCode("章节保存成功", nodeTreeVo);
    }

    /**
     * 
     * 根据相关查询条件获取教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param code 章节编码 @param subjectCode 学科编码 @param textbookCode 教材版本编码 @return @throws
     *        Exception @exception
     */
    @Override
    public ShareTextbookChapterVo getChapterInfo(String code, String subjectCode, String textbookCode)
                    throws Exception {
        ShareTextbookChapter entity = textbookChapterDao.getChapter(subjectCode, textbookCode, code);
        ShareTextbookChapterVo vo = new ShareTextbookChapterVo();
        DataUtils.copySimpleObject(entity, vo);
        return vo;
    }

    /**
     * 
     * 修改教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param gid 系统ID @param name 章节名称 @param description 章节描述信息 @return @throws
     *        Exception @exception
     */
    @Override
    public ResultCodeVo updateChapterInfo(String gid, String name, String description) throws Exception {

        // 获取http请求对象
       
        // 从cookie中获取用户登录信息
        UserInfoVo userInfo =getUserInfoVo();
        // 校验章节名称
        if (StringUtils.isEmpty(name)) {
            return ResultCodeVo.errorCode("章节名称不能为空！");
        }
        // 获取修改前的教材章节信息
        ShareTextbookChapter oldChapter = textbookChapterDao.getChapterByGid(gid);

        // 重名检查
        List<ShareTextbookChapter> sameNameList = textbookChapterDao.getSameNameChapterList(name,
                        oldChapter.getTextbookCode(), oldChapter.getPcode(), gid);
        if (sameNameList != null && sameNameList.size() > 0)
            return ResultCodeVo.errorCode("同教材下存在重名的章节");

        /*************************** 更新 *********************************/
        if (oldChapter != null && oldChapter.getSysVer() != null) {
            // 更新时，需将系统版本号+1
            oldChapter.setSysVer(oldChapter.getSysVer() + 1);
        }
        oldChapter.setName(name);// 章节名称
        oldChapter.setDescription(description);// 章节描述
        // 修改者信息
        oldChapter.setModifyPgm(userInfo.getUserName());
        oldChapter.setModifyIP(getIp());
        oldChapter.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        /********************* 全名修改 ********************************/
        String fullName = getFullName(oldChapter);
        /*
         * int index=fullName.lastIndexOf(">"); if(index>0){ fullName=fullName.substring(index);
         * fullName=fullName+name; }
         */
        oldChapter.setFullName(fullName);
        // 保存
        boolean updateResult = textbookChapterDao.addChapterInfo(oldChapter);
        /************************ 修改所有的子节点的全名 ******************/
        List<ShareTextbookChapter> siblings = textbookChapterDao.getListInfoByPcode(oldChapter.getCode(), "");
        if (siblings != null) {
            for (ShareTextbookChapter chapter : siblings) {
                String childFullName = getFullName(chapter);
                chapter.setFullName(childFullName);
                textbookChapterDao.addChapterInfo(chapter);
            }
        }
        NodeTreeVo nodeTreeVo = new NodeTreeVo();
        nodeTreeVo.setGid(oldChapter.getGid());
        nodeTreeVo.setDescription(description);
        nodeTreeVo.setCode(oldChapter.getCode());
        nodeTreeVo.setPcode(oldChapter.getPcode());
        nodeTreeVo.setState(null);
        nodeTreeVo.setTbCode(oldChapter.getTextbookCode());
        nodeTreeVo.setLevel(oldChapter.getLevel());
        nodeTreeVo.setNodeType("chapter");
        nodeTreeVo.setText(oldChapter.getName());
        if (updateResult) {
            return ResultCodeVo.rightCode("修改成功！", nodeTreeVo);
        } else {
            return ResultCodeVo.errorCode("修改失败！");
        }
    }

    /**
     * 
     * 查询章节信息
     * 
     * @param gid 章节id
     * @return
     * @throws Exception
     */
    public ShareTextbookChapterVo getChapter(String gid) throws Exception {
        // TODO Auto-generated method stub
        ShareTextbookChapter chapter = textbookChapterDao.getChapterByGid(gid);
        return ShareTextbookChapterVo.entityToVo(chapter);
    }

    /**
     * 
     * 根据教材查询教材章节
     * 
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapterVo> getChapterList(String tbCode) throws Exception {
        List<ShareTextbookChapter> chapterList = textbookChapterDao.getChapterList(tbCode);
        return ShareTextbookChapterVo.entityListToVo(chapterList);
    }

    /**
     * 
     * 根据教材查询教材章节
     * 
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getList(String tbCode) throws Exception {
        List<ShareTextbookChapter> chapterList = textbookChapterDao.getChapterList(tbCode);
        return chapterList;
    }

    /**
     * 更新顺序 ()<br>
     * 
     * @param gid
     * @param textbookCode 教材
     * @param type 移动类型 1 升序2降序
     * @return
     */
    public ResultCodeVo updateDispOrder(String textbookCode, String gid, Integer type) {
        // 得到按排序将序排序的知识点列表
        List<Map<String, Object>> mapList = textbookChapterDao.getTextBookSiblingList(textbookCode, gid);
        List<ShareTextbookChapterVo> chapterList = ShareTextbookChapterVo.getMapToVoList(mapList);
        if (type == 1) {
            // 已经在最上面了
            if (gid.equals(chapterList.get(0).getGid())) {
                return ResultCodeVo.errorCode("已经在最上面了");
            }
            int i = 1;
            // 重新给予顺序 4 6 8 10 12 14 并且 上升者减3就可以直接直接下降一级
            for (ShareTextbookChapterVo chapter : chapterList) {
                if (gid.equals(chapter.getGid())) {
                    textbookChapterDao.updateDispOrder(chapter.getGid(), i - 3);
                } else {
                    textbookChapterDao.updateDispOrder(chapter.getGid(), i);
                }
                i = i + 2;
            }
        }
        if (type == 2) {
            // 已经在最下面了
            if (gid.equals(chapterList.get(chapterList.size() - 1).getGid())) {
                return ResultCodeVo.errorCode("已经在最下面了");
            }
            int i = 10;
            // 重新给予顺序 0 2 4 6 8 10 12并且 上升者加3就可以直接直接下降一级
            for (ShareTextbookChapterVo chapter : chapterList) {
                if (gid.equals(chapter.getGid())) {
                    textbookChapterDao.updateDispOrder(chapter.getGid(), i + 3);
                } else {
                    textbookChapterDao.updateDispOrder(chapter.getGid(), i);
                }
                i = i + 2;
            }
        }
        return ResultCodeVo.rightCode("更新成功");
    }

}
