package com.baizhitong.resource.manage.report.action;

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

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.manage.report.service.SubjectReportService;
import com.baizhitong.resource.manage.section.service.SectionService;

/**
 * 
 * 学科使用情况统计Action
 * 
 * @author creator ZhangQiang 2016年9月8日 下午1:34:51
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/subjectUsageReport")
public class SubjectUsageReportAction extends BaseAction {

    /** 学段Service */
    @Autowired
    private SectionService       sectionService;
    /** 学科统计Service */
    @Autowired
    private SubjectReportService subjectReportService;

    /**
     * 
     * (跳转到学科使用情况统计页面)<br>
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String jumpToSubjectUsageReportPage(HttpServletRequest request, ModelMap model) {
        try {
            model.put("sectionList", sectionService.selectSectionList());
        } catch (Exception e) {
            log.error("获取学段信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/report/subjectUsageReport.html";
    }

    /**
     * 
     * (查询学科使用情况统计信息)<br>
     * 
     * @param request
     * @param sectionCode
     * @param subjectCode
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public List<Map<String, Object>> getSubjectReportInfo(HttpServletRequest request, String sectionCode,
                    String subjectCode, String startDate, String endDate) {
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("sectionCode", sectionCode);
            param.put("subjectCode", subjectCode);
            return subjectReportService.querySubjectUsageReportInfo(param);
        } catch (Exception e) {
            log.error("查询学科统计信息失败！", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * (查询学科使用情况总数)<br>
     * 
     * @param request
     * @param startDate
     * @param endDate
     * @param sectionCode
     * @param subjectCode
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> searchTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String sectionCode, String subjectCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        return subjectReportService.queryTotalUsageInfo(param);
    }

    /**
     * 
     * (数据以Excel形式导出)<br>
     * 
     * @param request
     * @param response
     * @param sectionCode
     * @param subjectCode
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String sectionCode,
                    String subjectCode, String startDate, String endDate, String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = subjectReportService.getUsageExcel(sectionCode, subjectCode, startDate, endDate);
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
