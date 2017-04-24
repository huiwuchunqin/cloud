package com.baizhitong.resource.manage.courseReport.action;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.dao.share.ShareTextbookChapterDao;
import com.baizhitong.resource.manage.courseReport.service.CourseCountAggregateDetailService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.share.ShareTextbookChapter;

/**
 * 汇总详情 CourseCountAggregateDetailAction TODO
 * 
 * @author creator gaow 2017年1月3日 下午2:38:32
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/courseAggregateDetail")
@Controller
public class CourseCountAggregateDetailAction extends BaseAction {
    @Autowired
    private CourseCountAggregateDetailService courseCountAggregateDetailService;
    /** 学段Service */
    @Autowired
    private SectionService                    sectionService;

    /**
     * 
     * (跳转数量汇总统计页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String jumpToUsageReportPage(ModelMap model) {
        try {
            model.put("sectionList", JSON.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("获取学段信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/courseReport/courseCountAggregateDetail.html";
    }

    /**
     * 
     * (查询课程数量汇总统计信息)<br>
     * 
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page getDetailInfo(String startDate, String endDate, String shareLevel, String lessonMode,
                    String sectionCode, String subjectCode, String gradeCode, String tbcCode, String orgName,
                    String chapterCode, String lessonName, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("shareLevel", shareLevel);
        param.put("lessonMode", lessonMode);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("tbcCode", tbcCode);
        param.put("orgName", orgName);
        param.put("chapterCode", chapterCode);
        param.put("lessonName", lessonName);
        return courseCountAggregateDetailService.queryCourseCountAggregateDetailReportInfo(param, page, rows);
    }

    /**
     * 
     * (数据导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     */
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String startDate,
                    String endDate, String shareLevel, String lessonMode, String sectionCode, String subjectCode,
                    String gradeCode, String tbcCode, String orgName, String chapterCodes, String lessonName,
                    String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = courseCountAggregateDetailService.getExcel(startDate, endDate, shareLevel, lessonMode,
                            sectionCode, subjectCode, gradeCode, tbcCode, orgName, chapterCodes, lessonName);
            wb.write(stream);
            byte[] buffer = stream.toByteArray();
            response.getOutputStream().write(buffer);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param request 请求
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String shareLevel, String lessonMode, String sectionCode, String subjectCode, String gradeCode,
                    String tbcCode, String orgName, String chapterCode, String lessonName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("shareLevel", shareLevel);
        param.put("lessonMode", lessonMode);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("tbcCode", tbcCode);
        param.put("orgName", orgName);
        param.put("chapterCode", chapterCode);
        param.put("lessonName", lessonName);
        return courseCountAggregateDetailService.queryTotalInfo(param);
    }

    // @Autowired
    // ShareTextbookChapterDao shareTextbookChapterDao;
    //
    // @RequestMapping("/searchTotal1.html")
    // @ResponseBody
    // public void mainTest() {
    // List<ShareTextbookChapter> chapters =
    // shareTextbookChapterDao.getAllChildNode("441323001E001001001");
    // String cha = "";
    // for (ShareTextbookChapter shareTextbookChapter : chapters) {
    // List<ShareTextbookChapter> a =
    // shareTextbookChapterDao.getListInfo(shareTextbookChapter.getCode());
    // if (a.size() < 1) {
    // cha += shareTextbookChapter.getCode() + ",";
    // }
    // }
    // System.out.println(cha.substring(0, cha.length() - 1));
    // }
    @RequestMapping("/searchChapterCodes.html")
    @ResponseBody
    public String getChapterCodes(String code) {
        String chapterCodes = courseCountAggregateDetailService.getChapterCodes(code);
        return chapterCodes;
    }
}
