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

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.manage.courseReport.service.CourseAuthorReportService;
import com.baizhitong.resource.manage.section.service.SectionService;

/**
 * 
 * CourseAuthorReportAction 课程作者统计
 * 
 * @author creator gaow 2017年1月3日 下午1:20:20
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/courseAuthor")
@Controller
public class CourseAuthorReportAction extends BaseAction {
    @Autowired
    /** 作者统计Service */
    private CourseAuthorReportService courseAuthorReportService;
    /** 学段Service */
    @Autowired
    private SectionService            sectionService;

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
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("获取学段信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/courseReport/courseAuthorReport.html";
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
    public Page getDetailInfo(String startDate, String endDate, String sectionCode, String subjectCode,
                    String shareLevel, String teacherName, String orgName, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("shareLevel", shareLevel);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        return courseAuthorReportService.queryAuthorReportInfo(param, page, rows);
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
                    String endDate, String sectionCode, String subjectCode, String shareLevel, String teacherName,
                    String orgName, String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = courseAuthorReportService.getExcel(startDate, endDate, sectionCode, subjectCode,
                            shareLevel, teacherName, orgName);
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
                    String sectionCode, String subjectCode, String shareLevel, String teacherName, String orgName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("shareLevel", shareLevel);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        return courseAuthorReportService.queryTotalInfo(param);
    }
}
