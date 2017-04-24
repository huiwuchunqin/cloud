package com.baizhitong.resource.manage.report.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.manage.report.service.UserOperateResService;

/**
 * 用户资源使用情况 UserOperateResAction TODO
 * 
 * @author creator gaow 2016年12月8日 下午4:50:15
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/userOperateRes")
public class UserOperateResAction extends BaseAction {
    /**
     * 用户资源操作
     */
    @Autowired
    UserOperateResService operateResService;

    /**
     * 跳转到教师资源使用情况 ()<br>
     * 
     * @return html
     */
    @RequestMapping(value = "/toTeacherResOperate.html")
    public String toTeacherResOperate() {
        return "/manage/report/teacherOperateRes.html";
    }

    /**
     * 跳转到学生资源使用情况 ()<br>
     * 
     * @return html
     */
    @RequestMapping(value = "/toStudentResOperate.html")
    public String toStudentResOperate() {
        return "/manage/report/studentOperateRes.html";
    }

    /**
     * 查询老师资源使用情况 ()<br>
     * 
     * @param endTime 结束日期
     * @param beginTime 开始日期
     * @param orgCode 机构编码
     * @param userName 用户姓名
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    @RequestMapping(value = "/teacherList")
    @ResponseBody
    public Page getTeacherResOperate(String endTime, String beginTime, String userName, String orgName, Integer page,
                    Integer rows) {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 50;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);
        param.put("orgName", orgName);
        param.put("userName", userName);
        return operateResService.teacherOperateResList(param, page, rows);
    }

    /**
     * 查询学生资源使用情况 ()<br>
     * 
     * @param endTime 结束日期
     * @param beginTime 开始日期
     * @param orgName 机构名称
     * @param userName 用户姓名
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    @RequestMapping(value = "/studentList")
    @ResponseBody
    public Page getStudentResOperate(String endTime, String beginTime, String userName, String orgName, Integer page,
                    Integer rows) {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 50;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);
        param.put("orgName", orgName);
        param.put("userName", userName);
        return operateResService.studentOperateResList(param, page, rows);
    }

    /**
     * 导出数据 ()<br>
     * 
     * @param endTime 结束日期
     * @param beginTime 开始日期
     * @param orgName 机构编码
     * @param role 1学生 2老师 角色
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    @RequestMapping(value = "/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, Integer role,
                    String endTime, String beginTime, String userName, String orgName, Integer page, Integer rows,
                    String fileName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("beginTime", beginTime);
        param.put("endTime", endTime);
        param.put("orgName", orgName);
        param.put("userName", userName);
        Map<String, String> exportParam = new LinkedHashMap<String, String>();
        Map<String, Integer> fontSizeMap = new HashMap<String, Integer>();
        List<String> countColumn = new ArrayList<String>();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (role == 1) {
            Page pageData = operateResService.studentOperateResList(param, 1, Integer.MAX_VALUE);
            if (pageData != null) {
                list = pageData.getRows();
            }
        } else {
            Page pageData = operateResService.teacherOperateResList(param, 1, Integer.MAX_VALUE);
            if (pageData != null) {
                list = pageData.getRows();
            }
        }
        exportParam.put("姓名", "userName");
        exportParam.put("机构名称", "orgName");
        if (role == 2) {
            exportParam.put("引用", "reference");
            countColumn.add("reference");
        }
        exportParam.put("浏览", "browser");
        exportParam.put("点赞", "good");
        exportParam.put("评论", "comment");
        if (role == 2) {
            exportParam.put("收藏", "favourite");
            countColumn.add("favourite");
        }

        exportParam.put("下载", "download");
        exportParam.put("总数", "total");

        countColumn.add("browser");
        countColumn.add("good");
        countColumn.add("comment");
        countColumn.add("download");
        countColumn.add("total");
        fontSizeMap.put("orgName", 20);
        response.setContentType("application/x-msdownload ");
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HSSFWorkbook excel = ExcelUtils.getWb(list, exportParam, fontSizeMap, countColumn);
        if (excel == null) {
            return null;
        }
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
