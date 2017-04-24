package com.baizhitong.resource.manage.shareTeachingClass.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.shareTeachingClass.service.IShareTeachingClassStudentService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

@Controller
@RequestMapping("/manage/teachingClassStudent")
public class ShareTeachingClassStudentAction extends BaseAction {
    @Autowired
    IShareTeachingClassStudentService classStudentService;
    @Autowired
    ICompanyService                   companyService;

    /**
     * 跳转到课程班级学生页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toClassStudent.html")
    public String toClassStudent(HttpServletRequest request, String gradeCode, String adminClassCode,
                    String teachingClassCode, ModelMap model) {
        CompanyInfoVo company =  getCompanyInfo();
        model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(company.getOrgCode())));
        model.put("teachingClassCode", teachingClassCode);
        model.put("gradeCode", gradeCode);
        model.put("adminClassCode", adminClassCode);
        return "/manage/teachingClass/teachingClassStudent.html";
    }

    /**
     * 查询班级学生 ()<br>
     * 
     * @param teachingClassCode
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/listClassStudent.html")
    @ResponseBody
    public Page getClassStudent(HttpServletRequest request, String teachingClassCode, String studentName,
                    String subjectCode, String gradeCode, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        CompanyInfoVo company =  getCompanyInfo();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("teachingClassCode", teachingClassCode);
        param.put("orgCode", company.getOrgCode());
        param.put("studentName", studentName);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        return classStudentService.getTeachingClassStudentList(param, page, rows);
    }

    /**
     * 新增教学班级学生 ()<br>
     * 
     * @param teachingClassCode
     * @param useCode
     * @return
     */
    @RequestMapping(value = "/addClassStudent.html")
    @ResponseBody
    public ResultCodeVo addClassStudent(String teachingClassCode, @RequestParam("userCode[]") String[] userCode,
                    @RequestParam("userName[]") String[] userName) {
        return classStudentService.addClassStudent(teachingClassCode, userCode, userName);
    }

    /**
     * 删除班级学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/deleteStudent.html")
    @ResponseBody
    public ResultCodeVo deleteStudent(String userCode, String teachingClassCode) {
        return classStudentService.deleteStudent(userCode, teachingClassCode);
    }
}
