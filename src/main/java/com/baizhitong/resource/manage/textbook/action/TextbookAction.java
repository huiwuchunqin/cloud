package com.baizhitong.resource.manage.textbook.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.manage.textbook.service.TextbookService;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.resource.model.vo.share.ShareTextbookVo;

/**
 * 
 * 教材控制类
 * 
 * @author creator gaowei 2016年1月12日 上午10:59:38
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/textbook")
public class TextbookAction extends BaseAction {
    /** 教材章节信息接口 */
    @Autowired
    private TextbookChapterService textbookChapterService;
    /** 教材接口 */
    @Autowired
    private TextbookService        textbookService;
    /** 学段信息接口 */
    @Autowired
    private SectionService         sectionService;

    /**
     * 保存教材
     * 
     * @param request
     * @param textbookvo
     */
    @RequestMapping("/saveTextbook.html")
    @ResponseBody
    public ResultCodeVo saveTextbooK(HttpServletRequest request, ShareTextbookVo textbookvo) {
        try {
            return textbookService.saveTextbook(textbookvo);
        } catch (Exception e) {
            log.error("教材保存失败", e);
            return ResultCodeVo.errorCode("教材保存失败");
        }
    }

    /**
     * 
     * 教材新增页面
     * 
     * @param request
     * @return
     */
    @RequestMapping("/textbookAdd.html")
    public String toTextBookAdd(HttpServletRequest request, ModelMap map, String tbCode) {
        try {
            map.put("tbCode", tbCode);
            List<ShareCodeSection> sectionList = sectionService.selectSectionList();
            map.addAttribute("sectionList", sectionList);
            return "/manage/textbook/textbookAdd.html";
        } catch (Exception e) {
            log.error("跳转到教材新增页面失败！", e);
            return "/manage/textbook/textbookAdd.html";
        }

    }

    /**
     * 
     * 跳转到教材修改页面 @param request 请求 @param gid 章节主键 @param map map @return @exception
     */
    @RequestMapping(value = "/toTextbookEdit.html")
    public String jumpToChapterEditPage(HttpServletRequest request, String gid, ModelMap map) {
        try {
            // 获取教材信息
            ShareTextbookVo textbook = textbookService.getShareTextbookVo(gid);
            map.put("textbook", textbook);
        } catch (Exception e) {
            log.error("跳转到教材章节修改页面异常！", e);
        }
        return "/manage/textbook/textbookEdit.html";
    }

    /**
     * 
     * 修改教材章节信息<br> (这里描述这个方法适用条件 – 可选)
     * 
     * @param request 请求 @param gid 系统ID @param name 章节名称 @param description
     * 章节描述信息 @return @exception
     */
    @RequestMapping(value = "/updateTextbook.html")
    public @ResponseBody ResultCodeVo updateTextbookInfo(HttpServletRequest request, String gid, String tbName,
                    String memo) {
        try {
            return textbookService.updateTextbook(tbName, memo, gid);
        } catch (Exception e) {
            log.error("修改教材章节信息失败！", e);
            return ResultCodeVo.errorCode("修改教材章节信息失败！");
        }
    }

    /**
     * 删除教材 ()<br>
     * 
     * @param gid 教材id
     * @return
     */
    @RequestMapping("/deleteTextbook.html")
    public @ResponseBody ResultCodeVo delTextbook(String gid) {
        return textbookService.delTextbook(gid);
    }

    /**
     * 
     * 查询教材列表
     * 
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 教材版本编码
     * @param sectionCode 学段编码
     * @return
     */
    @RequestMapping(value = "/getTextbookList.html")
    @ResponseBody
    public List<ShareTextbookVo> getTextbook(String gradeCode, String subjectCode, String versionCode,
                    String sectionCode) {
        try {
            return textbookService.getTextbookList(sectionCode, gradeCode, subjectCode, versionCode, "", "");
        } catch (Exception e) {
            return null;
        }
    }
}
