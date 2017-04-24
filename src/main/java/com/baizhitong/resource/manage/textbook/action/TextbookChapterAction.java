package com.baizhitong.resource.manage.textbook.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;
import com.baizhitong.resource.model.vo.share.ShareTextbookChapterVo;
import com.baizhitong.resource.model.vo.share.ZtreeHelpVo;

/**
 * 教材章节控制器
 * 
 * @author zhangqiang
 * @date 2015年12月14日 下午7:19:52
 */
@Controller
@RequestMapping("/manage/textbook")
public class TextbookChapterAction extends BaseAction {
    /** 教材章节信息接口 */
    private @Autowired TextbookChapterService textbookChapterService;
    /** 学段信息接口 */
    private @Autowired SectionService         sectionService;

    /**
     * 跳转到教材章节页面
     * 
     * @param request
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月14日 下午8:01:20
     */
    @RequestMapping(value = "/chapter.html")
    public String jumpToTextbookChapterPage(HttpServletRequest request, ModelMap map) {
        try {
            List<ShareCodeSection> sectionList = sectionService.selectSectionList();
            map.addAttribute("sectionList", sectionList);
            UserInfoVo userInfo =getUserInfoVo();
            map.addAttribute("sectionCode", userInfo.getUserSectionCode());
            map.addAttribute("subjectCode", userInfo.getUserSubjectCode());
        } catch (Exception e) {
            log.error("跳转到教材章节页面失败！", e);
        }
        return "/manage/textbook/textbook_chapter.html";
    }

    /**
     * 获取教材章节树集合
     * 
     * @param request 请求
     * @param subjectCode 学科编码
     * @param textBookCode 教材版本编码
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @return
     */
    @RequestMapping(value = "/getChapterTree.html")
    public @ResponseBody List<NodeTreeVo> getNodeTree(HttpServletRequest request, String chapterName, String nodeType,
                    String code, String id, String subjectCode, String textbookCode, String textbookVerCode,
                    String sectionCode, String gradeCode, String termCode) {
        try {
            if ("textbook".equals(nodeType)) {
                return textbookChapterService.getChapterTopNodeTreeList(code, chapterName);
            } else if ("chapter".equals(nodeType)) {
                return textbookChapterService.getChapterChildrenNodeTreeList(code, chapterName);
            } else if (StringUtils.isEmpty(nodeType)) {
                return textbookChapterService.getNodeTreeList(subjectCode, textbookVerCode, textbookCode, sectionCode,
                                gradeCode, termCode);
            }
            return null;
        } catch (Exception e) {
            log.error("获取教材章节树信息异常！", e);
            return null;
        }
    }

    /**
     * 查询根节点
     * 
     * @param subjectCodes 学科码
     * @param textbookVerCodes 版本码
     * @return
     * @author gaow
     * @date:2015年12月18日 上午10:14:31
     */
    @RequestMapping("/getVersionRoot.html")
    public @ResponseBody List<ZtreeHelpVo> getVersionRoot(Integer resId, String subjectCode, String sectionCode,
                    String gradeCode, String textbookVerCode, String textbookCode) {
        try {
            return textbookChapterService.getVersionTreeRoot(resId, sectionCode, gradeCode, subjectCode,
                            textbookVerCode, textbookCode);
        } catch (Exception e) {
            log.error("获取根节点异常！", e);
            return null;
        }
    }

    /**
     * 查询根节点
     * 
     * @param subjectCodes 学科码
     * @param textbookVerCodes 版本码
     * @return
     * @author gaow
     * @date:2015年12月18日 上午10:14:31
     */
    @RequestMapping("/getChapterAll.html")
    public @ResponseBody ResultCodeVo getAllRoot(Integer resId, String subjectCode, String sectionCode,
                    String gradeCode, String textbookVerCode, String textbookCode, String termCode) {
        try {
            return ResultCodeVo.rightCode("查询成功", textbookChapterService.getAll(resId, sectionCode, gradeCode,
                            subjectCode, textbookVerCode, textbookCode, termCode));
        } catch (Exception e) {
            log.error("获取根节点异常！", e);
            return null;
        }
    }

    /**
     * 
     * 选中的章节的所有父层级名称体现形式 例子：{name:父1→父2→被选中节点,code:选中节点code}
     * 
     * @param resId
     * @param subjectCode
     * @param sectionCode
     * @param gradeCode
     * @param textbookVerCode
     * @param textbookCode
     * @return
     */
    @RequestMapping("/getSelectedChapter.html")
    public @ResponseBody List<ZtreeHelpVo> getSelectedChapterList(Integer resId, String subjectCode, String sectionCode,
                    String gradeCode, String textbookVerCode, String textbookCode, Integer resTypeL1) {
        try {
            return textbookChapterService.getResRelateChapterAndParent(resId, sectionCode, subjectCode, gradeCode,
                            textbookVerCode, textbookCode, resTypeL1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 查询子节点
     * 
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本
     * @param code 父节点编码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 上午10:10:00
     */
    @RequestMapping("/getContentVersion.html")
    public @ResponseBody List<ZtreeHelpVo> getContentVersion(String code) {
        try {
            return textbookChapterService.getContentVersion(code);
        } catch (Exception e) {
            log.error("获取子节点异常！", e);
            return null;
        }
    }

    /**
     * 
     * 删除教材章节信息<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param request 请求 @param gid 教材章节id @return @exception
     */
    @RequestMapping(value = "/chapter/delete.html")
    public @ResponseBody ResultCodeVo deleteChapter(HttpServletRequest request, String gid) {
        try {
            return textbookChapterService.deleteChapter(gid);
        } catch (Exception e) {
            log.error("删除教材章节信息异常！", e);
            return ResultCodeVo.errorCode("删除教材章节信息异常！");
        }
    }

    /**
     * 
     * 跳转到教材章节子节点新增页面<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param request 请求 @param pcode 父章节编码 @param gid 教材id @param level 层级 @return 新增页面 @exception
     */
    @RequestMapping(value = "/chapter/toChapterAdd.html")
    public String jumpToChildNodeAddPage(HttpServletRequest request, ModelMap map, String pcode, String tbCode,
                    Integer level) {
        try {
            // 父章节编号
            map.put("pcode", pcode);
            // 教材编码
            map.put("tbCode", tbCode);
            // 层级
            map.put("level", level);
        } catch (Exception e) {
            log.error("获取教材章节子节点新增子页面异常！", e);
        }
        return "/manage/textbook/textbook_chapter_add.html";
    }

    /**
     * 
     * 添加教材章节信息<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param request 请求 @param chapter 教材章节信息 @param tbCode 教材编码
     * 
     * @return 添加是否成功 @exception
     */
    @RequestMapping(value = "/chapter/addChapter.html")
    public @ResponseBody ResultCodeVo addChapterInfo(HttpServletRequest request, ShareTextbookChapter chapter) {
        try {
            if (chapter.getLevel() > 5) {
                return ResultCodeVo.rightCode("最多只能五层");
            }
            return textbookChapterService.addChapterInfo(chapter);
        } catch (Exception e) {
            log.error("添加教材章节信息失败！", e);
            return ResultCodeVo.errorCode("添加教材章节信息失败！");
        }
    }

    /**
     * 
     * 跳转到教材章节修改页面<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param request 请求 @param gid 章节主键 @param map map @return @exception
     */
    @RequestMapping(value = "/chapter/toChapterEdit.html")
    public String jumpToChapterEditPage(HttpServletRequest request, String gid, ModelMap map) {
        try {
            // 获取教材章节信息
            ShareTextbookChapterVo chapter = textbookChapterService.getChapter(gid);
            map.put("chapter", chapter);
        } catch (Exception e) {
            log.error("跳转到教材章节修改页面异常！", e);
        }
        return "/manage/textbook/textbook_chapter_edit.html";
    }

    /**
     * 
     * 修改教材章节信息<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param request 请求 @param gid 系统ID @param name 章节名称 @param description
     * 章节描述信息 @return @exception
     */
    @RequestMapping(value = "/chapter/updateChapter.html")
    public @ResponseBody ResultCodeVo updateChapterInfo(HttpServletRequest request, String gid, String name,
                    String description) {
        try {
            return textbookChapterService.updateChapterInfo(gid, name, description);
        } catch (Exception e) {
            log.error("修改教材章节信息失败！", e);
            return ResultCodeVo.errorCode("修改教材章节信息失败！");
        }
    }

    /**
     * 更新节点顺序 ()<br>
     * 
     * @param gid 主键
     * @param textbook 教材
     * @param type 移动类型 1 上移动 2下移
     * @return
     */
    @RequestMapping("/updateTextBookDispOrder.html")
    @ResponseBody
    public ResultCodeVo updateDispOrder(String textbookCode, String gid, Integer type) {
        return textbookChapterService.updateDispOrder(textbookCode, gid, type);
    }
}
