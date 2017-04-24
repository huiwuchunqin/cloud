package com.baizhitong.resource.manage.orgReport.action;

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
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.companySubject.service.ICompanySubjectSerivce;
import com.baizhitong.resource.manage.report.service.SubjectReportService;
import com.baizhitong.resource.manage.section.service.SectionService;

@Controller
@RequestMapping(value = "/manage/orgSubjectReport")
public class OrgSubjectReportAction extends BaseAction {
    /** 学科统计Service */
    @Autowired
    private SubjectReportService   subjectReportService;
    /** 机构学科接口 */
    @Autowired
    private ICompanySubjectSerivce companySubjectService;

    /**
     * 
     * (跳转到学科统计页面)<br>
     * 
     * @param request 请求
     * @param model 数据模型
     * @return 页面
     */
    @RequestMapping("/toOrgSubejctReport.html")
    public String jumpToOrgSubjectReportPage(HttpServletRequest request, ModelMap model) {
        try {
            model.put("subjectList", companySubjectService.getCompanySubjectList(""));
        } catch (Exception e) {
            log.error("获取学段信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/report/orgReport/orgSubjectReport.html";
    }

    /**
     * 
     * (查询学科统计信息)<br>
     * 
     * @param request 请求
     * @param subjectCode 学科
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param shareLevel 分享级别
     * @return 数据列表
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public List<Map<String, Object>> getSubjectReportInfo(HttpServletRequest request, String subjectCode,
                    String startDate, String endDate, String shareLevel) {
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("subjectCode", subjectCode);
            param.put("shareLevel", shareLevel);
            param.put("orgCode", getOrgCode() );
            return subjectReportService.querySubjectReportInfo(param);
        } catch (Exception e) {
            log.error("查询学科统计信息失败！", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param request 请求
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param sectionCode 学段
     * @param subjectCode 学科
     * @param shareLevel 分享级别
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> searchTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String sectionCode, String subjectCode, String shareLevel) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("shareLevel", shareLevel);
        param.put("orgCode", getOrgCode() );
        return subjectReportService.queryTotalInfo(param);
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
                    String subjectCode, String startDate, String endDate, String shareLevel, String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = subjectReportService.getExcel(sectionCode, subjectCode, startDate, endDate, shareLevel);
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
