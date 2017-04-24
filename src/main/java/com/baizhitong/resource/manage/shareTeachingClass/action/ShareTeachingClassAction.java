package com.baizhitong.resource.manage.shareTeachingClass.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassService;
import com.baizhitong.resource.manage.studyYear.service.IOrgYearTermService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

@Controller
@RequestMapping("/manage/teachingClass")
public class ShareTeachingClassAction extends BaseAction {

    /**
     * 课程班级接口
     */
    private @Autowired IShareTeachingClassService shareTeachingClassService;
    /**
     * 机构学年学期接口
     */
    private @Autowired IOrgYearTermService        orgYearTermService;
    /** 老师接口 */
    private @Autowired ITeacherService            teacherService;
    private @Autowired ICompanyService            companyService;

    /**
     * 跳转到课程班级页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toTeachingClass.html")
    public String toTeachingClass(HttpServletRequest request, ModelMap model) {
        CompanyInfoVo company =  getCompanyInfo();
        // model.put("termList",
        // JSON.toJSONString(orgYearTermService.getOrgYearTermList(company.getOrgCode())));
        model.put("teacherList", JSON.toJSONString(teacherService.getTeacherList()));
        model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(company.getOrgCode())));
        return "/manage/teachingClass/teachingClass.html";
    }

    /**
     * 查询课程班级页面 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/list.html")
    @ResponseBody
    public Page listTeachingClass(HttpServletRequest request, Integer page, String teacherName, Integer rows,
                    String teachingClassName, String subjectCode, String gradeCode, String sectionCode) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("teachingClassName", teachingClassName);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("teacherName", teacherName);
        param.put("orgCode", companyInfoVo.getOrgCode());
        param.put("sectionCode", sectionCode);
        return shareTeachingClassService.list(param, page, rows);
    }

    /**
     * 新增或修改课程班级 ()<br>
     * ty6u78+
     * 
     * @param teachingClass
     * @return
     */
    @RequestMapping(value = "/saveOrUpdate.html")
    @ResponseBody
    public ResultCodeVo addTeachingClass(ShareTeachingClass teachingClass, String startTimeStr, String endTimeStr,
                    String teacherCode, String termCode, String teachingClassSubjectGid, String teacherName) {
        if (StringUtils.isNotEmpty(startTimeStr)) {
            teachingClass.setStartTime(new Timestamp(DateUtils.getDateTime(startTimeStr, "yyyy-MM-dd").getTime()));
        }
        if (StringUtils.isNotEmpty(endTimeStr)) {
            teachingClass.setEndTime(new Timestamp(DateUtils.getDateTime(endTimeStr, "yyyy-MM-dd").getTime()));
        }
        return shareTeachingClassService.addOrUpdate(teachingClass, teacherCode, teacherName, termCode,
                        teachingClassSubjectGid);
    }

    /**
     * 导入默认的课程班级 课程班级学生，课程班级学科 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/importDefault.html")
    @ResponseBody
    public ResultCodeVo importDefault() {
        return shareTeachingClassService.importDefault();
    }

}
