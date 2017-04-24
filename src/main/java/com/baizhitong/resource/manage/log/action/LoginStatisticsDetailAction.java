package com.baizhitong.resource.manage.log.action;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.common.vo.ResultVo;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.manage.log.service.impl.LoginStatisticsServiceImpl;
import com.baizhitong.utils.StringUtils;

/**
 * LoginStatisticsDetailAction 登录统计详情
 * 
 * @author creator ZOUKAI 2017年2月28日 上午9:35:12
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/loginStatisticsDetail")
public class LoginStatisticsDetailAction extends BaseAction {

    @Autowired
    private LoginStatisticsServiceImpl loginStatisticsService;

    @RequestMapping(method = RequestMethod.GET,
                    value = "/list.html")
    public String toPage(HttpServletRequest request, String orgCode, Integer role, String startTime, String endTime,String orgName,
                    Map<String, Object> model) {

        if (StringUtils.isEmpty(orgCode)) {
            orgCode = getOrgCode() ;
            model.put("showForm", true);
        }

        model.put("orgCode", orgCode);
        model.put("role", role);
        model.put("startTime", startTime);
        model.put("endTime", endTime);
        model.put("orgName",orgName);
        return "/manage/log/loginStatisticsDetail.html";
    }

    @RequestMapping("/search.html")
    @ResponseBody
    public Page getLoginStatisticsDetail(String orgCode, Integer role, String startTime, String endTime, Integer page,
                    Integer rows) {
        return loginStatisticsService.getLoginStatisticsDetail(orgCode, role, startTime, endTime, page, rows);
    }

    @RequestMapping(value = "/export.html",
                    produces = "text/html; charset=UTF-8")
    public void exportData(HttpServletRequest request, HttpServletResponse response, String orgCode, Integer role,
                    String startTime, String endTime,String orgName) {

        List<Map<String, Object>> dataList = loginStatisticsService
                        .getLoginStatisticsDetail(orgCode, role, startTime, endTime, 1, Integer.MAX_VALUE).getRows();

        Map<String, String> exportParam = new LinkedHashMap<String, String>();
        exportParam.put("姓名", "userName");
        exportParam.put("PC", "PC");
        exportParam.put("安卓平板", "androidPad");
        exportParam.put("iPad", "ipad");
        exportParam.put("安卓手机", "androidPhone");
        exportParam.put("iPhone", "iphone");
        exportParam.put("总计(次数)", "total");

        try {
            response.setContentType("application/x-msdownload"); 
            if(StringUtils.isNotEmpty(orgName)){
                response.setHeader("Content-disposition",
                                "attachment;fileName=" + new String((orgName+"-学校登录次数统计详情.xls").getBytes("utf-8"), "ISO8859-1"));
            }else{
                response.setHeader("Content-disposition",
                                "attachment;fileName=" + new String(("学校登录次数统计详情.xls").getBytes("utf-8"), "ISO8859-1"));
            }
            HSSFWorkbook wbHssfWorkbook = ExcelUtils.getWb(dataList, exportParam, null);
            wbHssfWorkbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error("导出失败!", e);
        }
    }

    @RequestMapping("/total.html")
    @ResponseBody
    public ResultVo getLoginStatisticsDetailTotal(String orgCode, Integer role, String startTime, String endTime) {
        Long total = loginStatisticsService.getLoginStatisticsDetailTotal(orgCode, role, startTime, endTime);
        return ResultVo.successCode(total);
    }
}
