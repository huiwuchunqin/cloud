package com.baizhitong.resource.manage.courseReport.action;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
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
import com.baizhitong.resource.manage.courseReport.service.CourseChapterReportService;
import com.baizhitong.resource.manage.section.service.SectionService;

/**
 * 
 * CourseChapterReportAction 按章节统计
 * 
 * @author creator gaow 2017年1月3日 下午2:37:27
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/courseChapter")
@Controller
public class CourseChapterReportAction extends BaseAction {
    @Autowired
    private SectionService             sectionService;
    @Autowired
    private CourseChapterReportService courseChapterReportService;

    /**
     * 跳转到按章节统计 ()<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/list.html")
    public String toChapter(ModelMap model) {
        try {
            model.put("sectionList", JSON.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("获取学段失败", e);
            return "/manage/courseReport/courseChapterReport.html";
        }
        return "/manage/courseReport/courseChapterReport.html";
    }

    /**
     * 跳转到章节详情页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/detailList.html")
    public String toChapterDetail() {
        return "/manage/courseReport/courseChapterDetailReport.html";
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
    public Page getDetailInfo(String sectionCode, String subjectCode, String gradeCode, String tbvCode, Integer page,
                    Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("tbvCode", tbvCode);
        return courseChapterReportService.queryChapterReportInfo(param, page, rows);
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
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String sectionCode,
                    String subjectCode, String gradeCode, String tbvCode, String fileName) {
        try {
            // fileName = "123.xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = courseChapterReportService.getExcel(sectionCode, subjectCode, gradeCode, tbvCode);
            wb.write(stream);
            byte[] buffer = stream.toByteArray();
            response.getOutputStream().write(buffer);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
