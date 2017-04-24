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
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.manage.report.service.UserResOperateDailyService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.company.ShareOrg;

/**
 * 
 * 使用情况详情操作统计Action
 * 
 * @author creator zhangqiang 2016年7月20日 下午1:38:37
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/userResOperateDaily")
public class UserResOperateDailyAction extends BaseAction {

    /** 学段Service */
    @Autowired
    private SectionService             sectionService;
    /** 用户_日次_资源操作统计Service */
    @Autowired
    private UserResOperateDailyService userResOperateDailyService;
    /** 机构dao */
    @Autowired
    private ShareOrgDao                shareOrgDao;

    /**
     * 
     * (跳转到使用情况统计详细页面)<br>
     * 
     * @param request 请求
     * @param model
     * @return
     */
    @RequestMapping("/list.html")
    public String jumpToUsageReportDetailPage(HttpServletRequest request, ModelMap model, String sectionCode,
                    String orgCode, String startDate, String endDate) {
        try {
            model.put("sectionList", sectionService.selectSectionList());
            model.put("startDate", startDate);
            model.put("endDate", endDate);
            model.put("sectionCode", sectionCode);
            model.put("orgCode", orgCode);
        } catch (Exception e) {
            log.error("获取学段信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/report/userResOperateDaily.html";
    }

    /**
     * 
     * (分页查询统计详情数据)<br>
     * 
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @param orgCode 机构编码
     * @param sectionCode 学段编码
     * @param userRole 角色
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page getUsageReportDetailInfo(String startDate, String endDate, String orgCode, String sectionCode,
                    Integer userRole, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgCode", orgCode);
        param.put("userRole", userRole);
        return userResOperateDailyService.queryUsageReportDetailInfo(param, page, rows);
    }

    /**
     * 
     * (数据导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param sectionCode 学段编码
     * @param orgCode 机构编码
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     */
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String sectionCode,
                    String orgCode, String baseDateStart, String baseDateEnd, String fileName) {
        try {
            ShareOrg org = shareOrgDao.getOrg(orgCode);
            fileName = URLDecoder.decode(fileName) + "-" + org.getOrgName() + ".xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = userResOperateDailyService.getExcel(sectionCode, orgCode, baseDateStart, baseDateEnd);
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
     * @param orgCode 机构编码
     * @param sectionCode 学段编码
     * @param userRole 用户身份
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String orgCode, String sectionCode, Integer userRole) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgCode", orgCode);
        param.put("userRole", userRole);
        return userResOperateDailyService.queryTotalInfo(param);
    }
}
