package com.baizhitong.resource.manage.adminClass.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.basic.ShareAdminClassDao;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassService;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.companyGrade.service.ICompanyGradeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;

/**
 * 
 * 行政班级控制类
 * 
 * @author creator gaowei 2016年1月11日 下午7:06:49
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/adminClass")
public class AdminClassAction extends BaseAction {

    @Autowired
    private IAdminClassService   adminClassService;
    @Autowired
    private ShareAdminClassDao   adminClassDao;
    /** 教师接口 */
    @Autowired
    private ITeacherService      teacherService;
    /** 机构年级接口 */
    @Autowired
    private ICompanyGradeService companyGradeService;
    @Autowired
    private ICompanyService      companyService;

    /**
     * 
     * (跳转到行政班级列表页面)<br>
     * 
     * @param map
     * @return
     */
    @RequestMapping("/toAdminClass.html")
    public String toAdminClassList(HttpServletRequest request, ModelMap map) {
        try {
            map.put("gradeList", JSONArray.toJSONString(companyGradeService.getGradeList()));
            map.put("teacherList", JSONArray.toJSONString(teacherService.getTeacherList()));
            map.put("sectionList",JSONArray.toJSONString(companyService.getCompanySection(getOrgCode())));
        } catch (Exception e) {
            log.error("跳转到行政班级信息页面失败！", e);
        }
        return "/manage/adminClass/adminClass.html";
    }

    /**
     * 查询行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    @RequestMapping("/getAdminClass.html")
    @ResponseBody
    public ShareAdminClass getAdminClass(String adminClassCode) {
        return adminClassDao.getByClassCode(adminClassCode);
    }

    /**
     * 查询行政班级列表 ()<br>
     * 
     * @param gradeCode
     * @return
     */
    @RequestMapping("/getAdminClassList")
    @ResponseBody
    public List<ShareAdminClass> getAdminClassList(HttpServletRequest request, String gradeCode) {
        CompanyInfoVo company =  getCompanyInfo();
        return adminClassService.getAdminClassList(company.getOrgCode(), gradeCode);
    }

    /**
     * 删除行政班级 ()<br>
     * 
     * @param gid
     * @return
     */
    @RequestMapping("/deleteAdminClass.html")
    @ResponseBody
    public ResultCodeVo deleteAdminClass(String gid) {
        return adminClassService.deleteAdminClass(gid);
    }

    /**
     * 查询行政班级列表
     * 
     * @param className 班级名称
     * @param gradeCode 年级编码
     * @param teacherName 老师名称
     * @param pageNo 页码
     * @param rows 每页记录数
     * @return
     */
    @RequestMapping("/listClass.html")
    @ResponseBody
    public Page<Map<String, Object>> getAdminClassList(String className, String gradeCode, String sectionCode,
                    String teacherName, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 10;
        return adminClassService.getAdminClassList(className, gradeCode, sectionCode, teacherName, page, rows);
    }

    /**
     * 保存行政班级 ()<br>
     * 
     * @param adminClass
     * @return
     */
    @RequestMapping("/addOrUpdateAdminClass.html")
    @ResponseBody
    public ResultCodeVo saveAdminClass(ShareAdminClass adminClass) {
        return adminClassService.addOrUpdateAdminClass(adminClass);
    }

    /**
     * 
     * (跳转到查看行政班级任教信息页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/teachingInfo.html")
    public String jumpToAdminClassTeachInfoPage(ModelMap model, String orgCode, String classCode, String gradeCode) {
        // 获取机构的学段信息
        List<ShareCodeSectionVo> sectionList = companyService.getCompanySection(orgCode);
        model.put("sectionCode", sectionList.get(0).getCode());
        model.put("orgCode", orgCode);
        model.put("classCode", classCode);
        model.put("gradeCode", gradeCode);
        return "/manage/adminClass/teachingInfo.html";
    }

    /**
     * 
     * (分页查询行政班级任教信息)<br>
     * 
     * @param orgCode
     * @param adminClassCode
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/teachingInfo/search.html")
    @ResponseBody
    public List<Map<String, Object>> searchAdminClassTeachInfo(String orgCode, String adminClassCode, Integer page,
                    Integer rows, String gradeCode) {
        return adminClassService.queryAdminClassTeachInfo(orgCode, adminClassCode, page, rows, gradeCode);
    }

    /**
     * 
     * (跳转到查看任教信息，修改教师页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/teachingInfo/modifyTeacher.html")
    public String jumpToModifyTeacherPage(ModelMap model) {
        return "/manage/adminClass/modifyTeacher.html";
    }

    /**
     * 
     * (修改教师信息)<br>
     * 
     * @param request
     * @param orgCode
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param teacherName
     * @param adminClassCode
     * @return
     */
    @RequestMapping("/teachingInfo/update.html")
    @ResponseBody
    public ResultCodeVo updateTeacherInfo(HttpServletRequest request, String orgCode, String subjectCode,
                    String gradeCode, String teacherCode, String teacherName, String adminClassCode, String beginTime,
                    String endTime, String oldTeacherCode) {
        try {
            return adminClassService.updateTeacherInfo(orgCode, subjectCode, gradeCode, teacherCode, teacherName,
                            adminClassCode, beginTime, endTime, oldTeacherCode);
        } catch (Exception e) {
            log.error("修改教师信息失败！", e);
            return ResultCodeVo.errorCode("修改失败！");
        }
    }

    /**
     * 
     * (新增行政班级学科信息)<br>
     * 
     * @param request
     * @param orgCode
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param teacherName
     * @param adminClassCode
     * @param beginTimeStr
     * @param endTimeStr
     * @return
     */
    @RequestMapping("/addAdminClassSubject.html")
    @ResponseBody
    public ResultCodeVo addAdminClassSubject(HttpServletRequest request, String orgCode, String subjectCode,
                    String gradeCode, String teacherCode, String teacherName, String adminClassCode,
                    String beginTimeStr, String endTimeStr) {
        try {
            return adminClassService.addAdminClassSubject(orgCode, subjectCode, gradeCode, teacherCode, teacherName,
                            adminClassCode, beginTimeStr, endTimeStr);
        } catch (Exception e) {
            log.error("新增行政班级学科信息失败！", e);
            return ResultCodeVo.errorCode("新增失败！");
        }
    }

    /**
     * 跳转到行政班级修改页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toAdminClassEdit.html")
    public String toAdminClassEdit(HttpServletRequest request, ModelMap model, String adminClassCode) {
        String orgCode = getOrgCode() ;
        List<ShareCodeSectionVo> sectionList = companyService.getCompanySection(orgCode);
        model.put("sectionCode", sectionList.get(0).getCode());
        model.put("gradeList", JSONArray.toJSONString(companyGradeService.getGradeList()));
        model.put("teacherList", JSONArray.toJSONString(teacherService.getTeacherList()));
        model.put("adminClassCode", adminClassCode);
        return "/manage/adminClass/adminClassEdit.html";
    }

    /**
     * 跳转到行政班级新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toAdminClassAdd.html")
    public String toAdminClassEdit(HttpServletRequest request, ModelMap model) {
        String orgCode = getOrgCode() ;
        List<ShareCodeSectionVo> sectionList = companyService.getCompanySection(orgCode);
        model.put("sectionCode", sectionList.get(0).getCode());
        model.put("gradeList", JSONArray.toJSONString(companyGradeService.getGradeList()));
        model.put("teacherList", JSONArray.toJSONString(teacherService.getTeacherList()));
        return "/manage/adminClass/adminClassEdit.html";
    }
}
