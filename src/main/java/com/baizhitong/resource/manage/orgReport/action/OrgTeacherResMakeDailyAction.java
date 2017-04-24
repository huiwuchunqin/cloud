package com.baizhitong.resource.manage.orgReport.action;

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
import com.baizhitong.resource.manage.orgReport.service.OrgTeacherResMakeDailyService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 作者统计-机构管理员查看Action
 * 
 * @author creator ZhangQiang 2016年11月22日 上午10:39:38
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/orgTeacherResMakeDaily")
public class OrgTeacherResMakeDailyAction extends BaseAction {

    @Autowired
    private OrgTeacherResMakeDailyService orgTeacherResMakeDailyService;
    @Autowired
    private SectionService                sectionService;

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
            CompanyInfoVo orgInfo =  getCompanyInfo();
            model.put("orgCode", orgInfo.getOrgCode());
        } catch (Exception e) {
            log.error("获取学段信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/report/orgReport/orgTeacherResMakeDaily.html";
    }

    /**
     * 
     * (分页查询作者统计信息)<br>
     * 
     * @param startDate
     * @param endDate
     * @param orgCode
     * @param sectionCode
     * @param subjectCode
     * @param userName
     * @param shareLevel
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page<Map<String, Object>> getAuthorReportInfo(String startDate, String endDate, String orgCode,
                    String sectionCode, String subjectCode, String userName, String shareLevel, Integer page,
                    Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgCode", orgCode);
        param.put("subjectCode", subjectCode);
        param.put("userName", userName);
        param.put("shareLevel", shareLevel);
        return orgTeacherResMakeDailyService.queryAuthorReportInfo(param, page, rows);
    }

    /**
     * 
     * (查询总数-机构管理员)<br>
     * 
     * @param request
     * @param startDate
     * @param endDate
     * @param orgCode
     * @param sectionCode
     * @param subjectCode
     * @param userName
     * @param shareLevel
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> searchTotalInfo(HttpServletRequest request, String startDate, String endDate,
                    String orgCode, String sectionCode, String subjectCode, String userName, String shareLevel) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgCode", orgCode);
        param.put("userName", userName);
        param.put("subjectCode", subjectCode);
        param.put("shareLevel", shareLevel);
        return orgTeacherResMakeDailyService.queryTotalInfo(param);
    }

    /**
     * 
     * (数据以Excel形式导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param orgCode 机构编码
     * @param baseDateStart 开始日期
     * @param baseDateEnd 结束日期
     * @param userName 作者名称
     * @param shareLevel 共享级别
     * @return excel文档
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String sectionCode,
                    String subjectCode, String orgCode, String startDate, String endDate, String userName,
                    Integer shareLevel, String fileName) {
        try {
            if (StringUtils.isNotEmpty(userName)) {
                userName = URLDecoder.decode(userName);
            }
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = orgTeacherResMakeDailyService.getExcel(sectionCode, subjectCode, orgCode, startDate,
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
}
