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
import com.baizhitong.resource.manage.report.service.OrgResOperateDailyService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 使用情况统计Action
 * 
 * @author creator zhangqiang 2016年7月19日 下午4:18:02
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/orgResOperateDaily")
public class OrgResOperateDailyAction extends BaseAction {

    /** 学段Service */
    @Autowired
    private SectionService            sectionService;
    @Autowired
    private OrgResOperateDailyService orgResOperateDailyService;

    /**
     * 
     * (跳转到使用情况统计页面)<br>
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
        return "/manage/report/orgResOperateDaily.html";
    }

    /**
     * 
     * (查询机构使用情况统计信息)<br>
     * 
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @param orgName 机构名称
     * @param sectionCode 学段编码
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page getUsageReportInfo(String startDate, String endDate, String orgName, String sectionCode, Integer page,
                    Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        return orgResOperateDailyService.queryUsageReportInfo(param, page, rows);
    }

    /**
     * 
     * (数据导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param sectionCode 学段编码
     * @param orgName 机构名称
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @return
     */
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String sectionCode,
                    String orgName, String startDate, String endDate, String fileName) {
        try {
            // 解密机构名称，避免中文乱码
            if (StringUtils.isNotEmpty(orgName)) {
                orgName = URLDecoder.decode(orgName);
            }
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = orgResOperateDailyService.getExcel(sectionCode, orgName, startDate, endDate);
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
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String orgName, String sectionCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        return orgResOperateDailyService.queryTotalInfo(param);
    }
}
