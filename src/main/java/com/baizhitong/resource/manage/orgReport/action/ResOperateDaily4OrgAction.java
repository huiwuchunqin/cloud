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
import com.baizhitong.resource.manage.orgReport.service.ResOperateDaily4OrgService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

/**
 * 
 * 机构-使用情况统计Action
 * 
 * @author creator ZhangQiang 2016年11月22日 下午6:50:34
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/resOperateDaily4org")
public class ResOperateDaily4OrgAction extends BaseAction {

    @Autowired
    private ResOperateDaily4OrgService resOperateDaily4OrgService;

    /**
     * 
     * (跳转到机构使用情况统计页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String jumpToUsageReportPage(ModelMap model, HttpServletRequest request) {
        try {
            CompanyInfoVo orgInfo = getCompanyInfo();
            model.put("orgCode", orgInfo.getOrgCode());
        } catch (Exception e) {
            log.error("获取登录者机构信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/report/orgReport/orgResOperateDaily.html";
    }

    /**
     * 
     * (查询机构使用情况统计)<br>
     * 
     * @param startDate
     * @param endDate
     * @param orgCode
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public List<Map<String, Object>> getUsageReportInfo(String startDate, String endDate, String orgCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("orgCode", orgCode);
        return resOperateDaily4OrgService.queryUsageReportInfo(param);
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param request
     * @param startDate
     * @param endDate
     * @param orgCode
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String orgCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("orgCode", orgCode);
        return resOperateDaily4OrgService.queryTotalInfo(param);
    }

    /**
     * 
     * (数据以Excel形式导出)<br>
     * 
     * @param request
     * @param response
     * @param orgCode
     * @param baseDateStart
     * @param baseDateEnd
     * @return
     */
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String orgCode,
                    String baseDateStart, String baseDateEnd, String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = resOperateDaily4OrgService.getExcel(orgCode, baseDateStart, baseDateEnd);
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
