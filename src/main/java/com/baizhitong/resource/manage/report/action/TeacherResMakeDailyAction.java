package com.baizhitong.resource.manage.report.action;

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
import com.baizhitong.resource.manage.report.service.TeacherResMakeDailyService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 作者统计Action
 * 
 * @author creator zhangqiang 2016年7月18日 下午1:16:00
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/teacherResMakeDaily")
public class TeacherResMakeDailyAction extends BaseAction {

    /** 学段Service */
    @Autowired
    private SectionService             sectionService;
    @Autowired
    private TeacherResMakeDailyService teacherResMakeDailyService;

    /**
     * 
     * (跳转到作者统计页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String jumpToAuthorReportPage(ModelMap model, HttpServletRequest request) {
        try {
            model.put("sectionList", sectionService.selectSectionList());
        } catch (Exception e) {
            log.error("获取学段信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/report/teacherResMakeDaily.html";
    }

    /**
     * 
     * (查询作者统计信息)<br>
     * 
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @param orgName 机构名称
     * @param sectionCode 学段编码
     * @param userName 作者姓名
     * @param subjectCode 学科编码
     * @param page 当前页数
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page<Map<String, Object>> getAuthorReportInfo(String startDate, String endDate, String orgName,
                    String sectionCode, String subjectCode, String userName, String shareLevel, Integer page,
                    Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        param.put("subjectCode", subjectCode);
        param.put("userName", userName);
        param.put("shareLevel", shareLevel);
        return teacherResMakeDailyService.queryAuthorReportInfo(param, page, rows);
    }

    /**
     * 
     * (数据导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param sectionCode 学段编码
     * @param subjectCode 学科名称
     * @param orgName 机构名称
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @param userName 作者姓名
     * @param shareLevel 共享级别
     * @return
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String sectionCode,
                    String subjectCode, String orgName, String startDate, String endDate, String userName,
                    Integer shareLevel, String fileName) {
        try {
            // 避免中文乱码
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
            HSSFWorkbook wb = teacherResMakeDailyService.getExcel(sectionCode, subjectCode, orgName, startDate,
                            endDate, userName, shareLevel);
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
     * @param orgName 机构名称
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param userName 作者姓名
     * @param shareLevel 分享级别
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> searchTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String orgName, String sectionCode, String subjectCode, String userName, String shareLevel) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        param.put("userName", userName);
        param.put("subjectCode", subjectCode);
        param.put("shareLevel", shareLevel);
        return teacherResMakeDailyService.queryTotalInfo(param);
    }
}
