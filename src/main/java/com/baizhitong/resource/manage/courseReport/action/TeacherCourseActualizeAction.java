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
import com.baizhitong.resource.manage.courseReport.service.TeacherCourseActualizeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.studyYear.service.IStudyYearTermService;

/**
 * 老师课程实施情况 TeacherCourseActualizeAction TODO
 * 
 * @author creator gaow 2017年1月3日 下午2:42:17
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/teacherCourseActualize")
@Controller
public class TeacherCourseActualizeAction extends BaseAction {
    /** 学段Service */
    @Autowired
    private SectionService                sectionService;
    /** 学年学期 */
    @Autowired
    private IStudyYearTermService         studyYearTermService;
    /** 教师实施情况 */
    @Autowired
    private TeacherCourseActualizeService teacherCourseActualizeService;

    /**
     * 
     * 跳转到老师课程实施情况 ()<br>
     * 
     * @param model 数据模型
     * @return list
     */
    @RequestMapping(value = "/toTeacherCourseActualize.html")
    public String toTeacherCourseActualize(ModelMap model) {
        try {
            model.put("sectionList", JSON.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        model.put("yearTermList", JSON.toJSONString(studyYearTermService.getTermList(new HashMap<String, Object>())));
        return "/manage/courseReport/teacherCourseActualize.html";
    }

    /**
     * 跳转到老师课程实施详情 ()<br>
     * 
     * @return 详情页面
     */
    @RequestMapping(value = "/toDetail.html")
    public String toDetail() {
        return "/manage/courseReport/teacherCourseActualizeDetail.html";
    }

    /**
     * 
     * (查询实施课程数量汇总统计信息)<br>
     * 
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page getDetailInfo(String yearTermCode, String startDate, String endDate, String publishStartDate,
                    String publishEndDate, String sectionCode, String subjectCode, String teacherName, String orgName,
                    Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        param.put("yearTermCode", yearTermCode);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("publishStartDate", publishStartDate);
        param.put("publishEndDate", publishEndDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        return teacherCourseActualizeService.queryTeacherCourseActualizeInfo(param, page, rows);
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
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String yearTermCode,
                    String startDate, String endDate, String publishStartDate, String publishEndDate,
                    String sectionCode, String subjectCode, String teacherName, String orgName, String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = teacherCourseActualizeService.getExcel(startDate, endDate, publishStartDate,
                            publishEndDate, sectionCode, subjectCode, yearTermCode, teacherName, orgName);
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
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String yearTermCode, String startDate,
                    String endDate, String publishStartDate, String publishEndDate, String sectionCode,
                    String subjectCode, String teacherName, String orgName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("yearTermCode", yearTermCode);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("publishStartDate", publishStartDate);
        param.put("publishEndDate", publishEndDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        return teacherCourseActualizeService.queryTotalInfo(param);
    }

    /**
     * 
     * (查询实施课程数量汇总统计信息)<br>
     * 
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/search/detail.html")
    @ResponseBody
    public Page getTeacherDetailInfo(String teacherCode, String orgCode, String yearTermCode, String startDate,
                    String endDate, String publishStartDate, String publishEndDate, String sectionCode,
                    String subjectCode, String teacherName, String orgName, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        param.put("teacherCode", teacherCode);
        param.put("orgCode", orgCode);
        param.put("yearTermCode", yearTermCode);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("publishStartDate", publishStartDate);
        param.put("publishEndDate", publishEndDate);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        return teacherCourseActualizeService.queryTeacherCourseActualizeDetailInfo(param, page, rows);
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
    @RequestMapping("/export/detail.html")
    public ModelAndView exportDetailData(HttpServletRequest request, HttpServletResponse response, String teacherCode,
                    String orgCode, String yearTermCode, String startDate, String endDate, String publishStartDate,
                    String publishEndDate, String sectionCode, String subjectCode, String teacherName, String orgName,
                    String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = teacherCourseActualizeService.getExcelDetail(teacherCode, orgCode, startDate, endDate,
                            publishStartDate, publishEndDate, sectionCode, subjectCode, yearTermCode, teacherName,
                            orgName);
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
