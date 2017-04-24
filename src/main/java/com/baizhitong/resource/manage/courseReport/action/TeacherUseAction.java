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
import com.baizhitong.resource.manage.courseReport.service.TeacherUseService;
import com.baizhitong.utils.StringUtils;

/**
 * 教师使用情况 TeacherUseAction
 * 
 * @author creator gaow 2017年1月3日 下午2:42:32
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/course/teacherUse")
@Controller
public class TeacherUseAction extends BaseAction {
    /** 教师使用情况统计Service */
    @Autowired
    private TeacherUseService teacherUseService;

    /**
     * 
     * (跳转到教师使用统计页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String jumpToUsageReportPage(ModelMap model) {
        return "/manage/courseReport/teacherUse.html";
    }

    /**
     * 
     * (分页获取教师使用统计信息)<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param teacherName 教师姓名
     * @param orgName 学校名称
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page<Map<String, Object>> getTeacherUseReportPage(String startDate, String endDate, String teacherName,
                    String orgName, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        return teacherUseService.getTeacherLessonUse(page, rows, param);
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param request
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param teacherName 教师姓名
     * @param orgName 学校名称
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String teacherName, String orgName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        return teacherUseService.getTeacherLessonUseTotal(param);
    }

    /**
     * 
     * (数据以Excel形式导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param teacherName 教师姓名
     * @param orgName 学校名称
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param fileName 导出文件名字
     * @return 操作结果
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String teacherName,
                    String orgName, String startDate, String endDate, String fileName) {
        try {
            // 解密机构名称，避免中文乱码
            if (StringUtils.isNotEmpty(orgName)) {
                orgName = URLDecoder.decode(orgName);
            }
            if (StringUtils.isNotEmpty(teacherName)) {
                teacherName = URLDecoder.decode(teacherName);
            }
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = teacherUseService.getExcel(teacherName, orgName, startDate, endDate);
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
