package com.baizhitong.resource.manage.log.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.manage.log.service.ILoginLogService;

@Controller
@RequestMapping("/manage/loginLog")
public class LoginLogAction extends BaseAction {
    @Autowired
    ILoginLogService loginLogService;

    /**
     * 跳转到登录日志页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/toLoginLog.html")
    public String toLoginLog() {
        return "/manage/log/loginLog.html";
    }

    /**
     * 查询登录日志 ()<br>
     * 
     * @param orgName
     * @param userName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/list.html")
    @ResponseBody
    public Page getList(String startDate, String endDate, String deviceType, Integer loginSuccess, Integer userRole,
                    String orgName, String userName, Integer page, Integer rows) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgName", orgName);
        sqlParam.put("userName", userName);
        sqlParam.put("userRole", userRole);
        sqlParam.put("deviceType", deviceType);
        sqlParam.put("loginSuccess", loginSuccess);
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        return loginLogService.getLoginLog(sqlParam, page, rows);
    }

    /**
     * 导出 ()<br>
     * 
     * @param request 请求
     * @param response 相应
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param deviceType 设备类型
     * @param loginSuccess 登录成功？
     * @param userRole 用户角色
     * @param orgName 机构名称
     * @param userName 用户姓名
     * @return 不返回
     */
    @RequestMapping(value = "/export.html",
                    produces = "text/html; charset=UTF-8")
    public String export(HttpServletRequest request, HttpServletResponse response, String startDate, String endDate,
                    String deviceType, Integer loginSuccess, Integer userRole, String orgName, String userName) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgName", orgName);
        sqlParam.put("userName", userName);
        sqlParam.put("userRole", userRole);
        sqlParam.put("deviceType", deviceType);
        sqlParam.put("loginSuccess", loginSuccess);
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        Page page = loginLogService.getLoginLog(sqlParam, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = page != null ? page.getRows() : null;
        String msg = "";
        if (dataList == null || dataList.size() <= 0) {
            msg = "没有数据";
        } else if (dataList.size() > 2000) {
            msg = "数据 太多请输入查询条件进行过滤";
        }
        /*
         * try {
         * response.getWriter().print("<script>window.top.downloadInfo(\""+msg+"\");</script>");
         * return null; } catch (IOException e1) { log.error("下载数据异常"); }
         */

        Map<String, String> exportParam = new LinkedHashMap<String, String>();
        exportParam.put("机构名称", "orgName");
        exportParam.put("用户姓名", "userName");
        exportParam.put("角色", "userRoleName");
        exportParam.put("年级", "gradeName");
        exportParam.put("登录类型", "loginTypeStr");
        exportParam.put("登录时间", "loginTime");
        exportParam.put("登录ip", "loginIP");
        exportParam.put("设备类型", "deviceTypeStr");
        exportParam.put("浏览器信息", "browserInfo");
        exportParam.put("应用信息", "appVerInfo");
        exportParam.put("是否成功", "loginSuccess");
        loginLogService.format(dataList);
        response.setContentType("application/x-msdownload");
        try {
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String("登录日志统计.xls".getBytes("utf-8"), "ISO8859-1"));
            HSSFWorkbook wbHssfWorkbook = ExcelUtils.getWb(dataList, exportParam, null);
            wbHssfWorkbook.write(response.getOutputStream());
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}
