package com.baizhitong.resource.manage.adminClass.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassService;
/**
 * 行政班级学生
 */
import com.baizhitong.resource.manage.adminClass.service.IAdminClassStudentService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

@RequestMapping("/manage/adminClassStudent")
@Controller
public class AdminClassStudentAction extends BaseAction {
    private @Autowired IAdminClassStudentService adminClassStudentService;
    @Autowired
    private IAdminClassService                   adminClassService;

    /**
     * 删除行政班级学生 ()<br>
     * 
     * @param userCode
     * @param adminClassCode
     * @return
     */
    @RequestMapping("/deleteAdminClassStudent.html")
    @ResponseBody
    public ResultCodeVo deleteAdminClassStudent(HttpServletRequest request, String userCode, String adminClassCode) {
        return adminClassStudentService.deleteAdminClassStudent(userCode, adminClassCode);
    }

    /**
     * 批量删除行政班级学生 ()<br>
     * 
     * @param teachingClassCode
     * @param useCodes
     * @return
     */
    @RequestMapping(value = "/deleteClassStudents.html")
    @ResponseBody
    public ResultCodeVo addClassStudents(String adminClassCode, @RequestParam("userCode[]") String[] userCodes) {
        return adminClassStudentService.deleteAdminClassStudents(adminClassCode, userCodes);
    }

    /**
     * 新增行政班级学生 ()<br>
     * 
     * @param adminClassCode
     * @param useCode
     * @return
     */
    @RequestMapping(value = "/addClassStudent.html")
    @ResponseBody
    public ResultCodeVo addClassStudent(String adminClassCode, @RequestParam("userCode[]") String[] userCode,
                    @RequestParam("userName[]") String[] userName) {
        return adminClassStudentService.addClassStudent(adminClassCode, userCode, userName);
    }

    /**
     * 跳转到行政班级学生页面 ()<br>
     * 
     * @param gid 行政班级主键
     * @param url 地址
     * @return
     */
    @RequestMapping("/toAdminClassStudent.html")
    public String toAdminClassStudent(ModelMap model, String gid, String gradeCode) {
        ShareAdminClass adminClass = adminClassService.getAdminClass(gid);
        model.put("adminClass", adminClass);
        model.put("gradeCode", gradeCode);
        model.put("adminClassCode", adminClass.getClassCode());
        model.put("classList", JSONArray.toJSONString(
                        adminClassService.getAdminClassList(adminClass.getOrgCode(), adminClass.getGradeCode())));
        return "/manage/adminClass/adminClassStudent.html";
    }
}
