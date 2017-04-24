package com.baizhitong.resource.manage.courseReport.action;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
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
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.courseReport.service.StudentUseService;
import com.baizhitong.resource.manage.studyYear.service.IStudyYearTermService;
import com.baizhitong.utils.StringUtils;

/**
 * 学生使用情况 StudentUseAction
 * 
 * @author creator gaow 2017年1月3日 下午2:41:20
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/course/studentUse")
@Controller
public class StudentUseAction extends BaseAction {

    /** 学年学期Service */
    @Autowired
    private IStudyYearTermService studyYearTermService;
    /** 学生使用情况统计Service */
    @Autowired
    private StudentUseService     studentUseService;

    /**
     * 
     * (跳转到学生使用统计页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String jumpToUsageReportPage(ModelMap model) {
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            model.put("yearTermList", studyYearTermService.getTermList(param));
            model.put("currentYearTermCode", BeanHelper.getYearTermCode());
        } catch (Exception e) {
            log.error("获取学年学期信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/courseReport/studentUse.html";
    }

    /**
     * 
     * (分页获取学校使用统计信息)<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param orgName 学段编码
     * @param sectionCode 学校名称
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page<Map<String, Object>> getStudentUseReportPage(String studyYearTermCode, String orgName, String userName,
                    Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("studyYearTermCode", studyYearTermCode);
        param.put("userName", userName);
        param.put("orgName", orgName);
        return studentUseService.getStudentLessonUse(page, rows, param);
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param request
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param orgName 学校名称
     * @param sectionCode 学段编码
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String studyYearTermCode, String orgName,
                    String userName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("studyYearTermCode", studyYearTermCode);
        param.put("userName", userName);
        param.put("orgName", orgName);
        return studentUseService.getStudentLessonUseTotal(param);
    }

    /**
     * 
     * (数据导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param sectionCode 学段编码
     * @param orgName 机构名称
     * @param termCode 学期编码
     * @param studyYearCode 学年编码
     * @return
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String userName,
                    String orgName, String studyYearTermCode, String fileName) {
        try {
            // 解密机构名称，避免中文乱码
            if (StringUtils.isNotEmpty(orgName)) {
                orgName = URLDecoder.decode(orgName);
            }
            if (StringUtils.isNotEmpty(userName)) {
                userName = URLDecoder.decode(userName);
            }
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = studentUseService.getExcel(userName, orgName, studyYearTermCode);
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
