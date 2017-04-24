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

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.manage.courseReport.service.SubjectUseService;
import com.baizhitong.resource.manage.section.service.SectionService;

/**
 * 学科使用情况 SubjectUseAction
 * 
 * @author creator gaow 2017年1月3日 下午2:42:02
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/course/subjectUse")
@Controller
public class SubjectUseAction extends BaseAction {
    /** 学科使用情况统计Service */
    @Autowired
    private SubjectUseService subjectUseService;
    /** 学段Service */
    @Autowired
    private SectionService    sectionService;

    /**
     * 
     * (跳转到学科使用统计页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String jumpToUsageReportPage(ModelMap model) {
        try {
            model.put("sectionList", sectionService.selectSectionList());
        } catch (Exception e) {
            log.error("获取学段信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/courseReport/subjectUse.html";
    }

    /**
     * 
     * (分页获取学科使用统计信息)<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param subjectCode 学段编码
     * @param sectionCode 学科编码
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page<Map<String, Object>> getSubjectUseReportPage(String startDate, String endDate, String subjectCode,
                    String sectionCode, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        return subjectUseService.getSubjectLessonUse(page, rows, param);
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param request
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param subjectCode 学科编码
     * @param sectionCode 学段编码
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String subjectCode, String sectionCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        return subjectUseService.getSubjectLessonUseTotal(param);
    }

    /**
     * 
     * (数据导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     */
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String sectionCode,
                    String subjectCode, String baseDateStart, String baseDateEnd, String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = subjectUseService.getExcel(sectionCode, subjectCode, baseDateStart, baseDateEnd);
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
