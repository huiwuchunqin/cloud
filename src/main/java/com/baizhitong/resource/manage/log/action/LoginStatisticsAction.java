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
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.manage.log.service.impl.LoginStatisticsServiceImpl;

/**
 * LoginStatisticsAction 登录统计action
 * 
 * @author creator ZOUKAI 2017年2月27日 下午5:03:11
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/loginStatistics")
public class LoginStatisticsAction extends BaseAction {

    @Autowired
    private LoginStatisticsServiceImpl loginStatisticsService;

    @RequestMapping(method = RequestMethod.GET,
                    value = "/list.html")
    public String toPage() {
        return "/manage/log/loginStatistics.html";
    }

    @RequestMapping("/search.html")
    @ResponseBody
    public Page getLoginStatistics(Integer page, Integer rows, String orgName, Integer role, String startTime,
                    String endTime) {
        if (orgName != null) {
            orgName = orgName.trim();
        }

        return loginStatisticsService.getLoginStatistics(orgName, role, startTime, endTime, page, rows);
    }

    @RequestMapping(value = "/export.html",
                    produces = "text/html; charset=UTF-8")
    public void exportData(HttpServletRequest request, HttpServletResponse response, String orgName, Integer role,
                    String startTime, String endTime) {

        if (orgName != null) {
            orgName = orgName.trim();
        }

        List<Map<String, Object>> dataList = loginStatisticsService
                        .getLoginStatistics(orgName, role, startTime, endTime, 1, Integer.MAX_VALUE).getRows();

        Map<String, String> exportParam = new LinkedHashMap<String, String>();
        exportParam.put("学校", "orgName");
        exportParam.put("PC", "PC");
        exportParam.put("安卓平板", "androidPad");
        exportParam.put("iPad", "ipad");
        exportParam.put("安卓手机", "androidPhone");
        exportParam.put("iPhone", "iphone");
        exportParam.put("总计(次数)", "total");

        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String("学校登录次数统计.xls".getBytes("utf-8"), "ISO8859-1"));
            HSSFWorkbook wbHssfWorkbook = ExcelUtils.getWb(dataList, exportParam, null);
            wbHssfWorkbook.write(response.getOutputStream());
        } catch (IOException e) {
            log.error("导出失败!", e);
        }
    }

    @RequestMapping("/total.html")
    @ResponseBody
    public ResultVo getLoginStatisticsTotal(String orgName, Integer role, String startTime, String endTime) {
        if (orgName != null) {
            orgName = orgName.trim();
        }
        Long total = loginStatisticsService.getLoginStatisticsTotal(orgName, role, startTime, endTime);
        return ResultVo.successCode(total);
    }
}
