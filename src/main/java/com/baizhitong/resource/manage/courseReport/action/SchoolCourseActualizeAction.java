package com.baizhitong.resource.manage.courseReport.action;

import java.io.ByteArrayOutputStream;
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
import com.baizhitong.resource.manage.courseReport.service.SchoolCourseActualizeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.studyYear.service.IStudyYearTermService;

/**
 * 学校课程实施情况统计Action
 * 
 * @author creator gaow 2017年1月3日 下午2:39:40
 * @author updater zhangqiang
 *
 * @version 1.0.0
 */
@RequestMapping(value = "/manage/course/schoolCourseActualize")
@Controller
public class SchoolCourseActualizeAction extends BaseAction {

    /** 学段Service */
    @Autowired
    private SectionService               sectionService;
    /** 学年学期 */
    @Autowired
    private IStudyYearTermService        studyYearTermService;
    /** 学校实施情况 */
    @Autowired
    private SchoolCourseActualizeService schoolCourseActualizeService;

    /**
     * 
     * 跳转到学校课程实施情况统计页面
     * 
     * @param model 数据模型
     * @return list
     */
    @RequestMapping(value = "/list.html")
    public String toTeacherCourseActualize(ModelMap model) {
        Map<String, Object> param = new HashMap<String, Object>();
        try {
            model.put("sectionList", sectionService.selectSectionList());
            model.put("yearTermList", studyYearTermService.getTermList(param));
            model.put("currentYearTermCode", BeanHelper.getYearTermCode());
        } catch (Exception e) {
            log.error("跳转到学校课程实施情况统计页面失败！", e);
            e.printStackTrace();
        }
        return "/manage/courseReport/schoolCourseActualize.html";
    }

    /**
     * 
     * (查询实施课程数量汇总统计信息)<br>
     * 
     * @param endDate 结束基准日
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/search.html")
    @ResponseBody
    public Page<Map<String, Object>> getDetailInfo(String yearTermCode, Integer endDate, String sectionCode,
                    String orgName, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        param.put("yearTermCode", yearTermCode);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        return schoolCourseActualizeService.querySchoolCourseActualizeInfo(param, page, rows);
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param request 请求
     * @param startDate 开始基准日
     * @param endDate 结束基准日
     * @return
     */
    @RequestMapping("/searchTotal.html")
    @ResponseBody
    public Map<String, Object> getTotalInfo(HttpServletRequest request, String yearTermCode, Integer endDate,
                    String sectionCode, String orgName) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("yearTermCode", yearTermCode);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        return schoolCourseActualizeService.queryTotalInfo(param);
    }

    /**
     * 
     * (数据导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     */
    @RequestMapping("/export.html")
    public ModelAndView exportData(HttpServletRequest request, HttpServletResponse response, String yearTermCode,
                    Integer endDate, String sectionCode, String orgName, String fileName) {
        try {
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = schoolCourseActualizeService.getExcel(endDate, sectionCode, yearTermCode, orgName);
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
