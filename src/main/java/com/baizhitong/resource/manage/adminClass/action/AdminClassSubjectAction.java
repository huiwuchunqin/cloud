package com.baizhitong.resource.manage.adminClass.action;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassService;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassSubjectService;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.studyYear.service.IOrgYearTermService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.model.share.ShareAdminClassSubject;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.ObjectUtils;

@RequestMapping("/manage/adminClassSubject")
@Controller
public class AdminClassSubjectAction extends BaseAction {
    @Autowired
    private IAdminClassSubjectService adminClassSubejctService;
    @Autowired
    private IAdminClassService        adminClassService;
    /** 教师接口 */
    @Autowired
    private ITeacherService           teacherService;
    @Autowired
    private ICompanyService           companyService;          // 机构接口
    @Autowired
    private IOrgYearTermService       orgYearTermService;      // 机构学期接口

    /**
     * 查询行政班级学科 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/pageList.html")
    @ResponseBody
    public Page getAdminClassSubjectList(HttpServletRequest request, Integer page, Integer rows, String teacherCode) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        Map<String, Object> param = new HashMap<String, Object>();
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        param.put("teacherCode", teacherCode);
        param.put("orgCode", companyInfoVo.getOrgCode());
        return adminClassSubejctService.getAdminClassSubject(param, page, rows);
    }

    /**
     * 保存行政班级学科 ()<br>
     * 
     * @param adminClassSubject
     * @return
     */
    @RequestMapping("/saveAdminClassSubject.html")
    @ResponseBody
    public ResultCodeVo addAdminClassSubject(ShareAdminClassSubject adminClassSubject, String beginTimeStr,
                    String endTimeStr) {
        if (StringUtils.isNotEmpty(beginTimeStr)) {
            adminClassSubject.setBeginTime(new Timestamp(DateUtils.getDateTime(beginTimeStr, "yyyy-MM-dd").getTime()));
        }
        if (StringUtils.isNotEmpty(endTimeStr)) {
            adminClassSubject.setEndTime(new Timestamp(DateUtils.getDateTime(endTimeStr, "yyyy-MM-dd").getTime()));
        }
        return adminClassSubejctService.saveAdminClassSubject(adminClassSubject);
    }

    /**
     * 跳转行政班级学科页面<br>
     * 
     * @return
     */
    @RequestMapping("/toAdminClassSubject.html")
    public String toAdminClassSubject(String teacherCode, HttpServletRequest request, ModelMap model) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        ShareOrgYearTerm orgYearTerm = orgYearTermService.getCurrentOrgYearTerm();
        if (ObjectUtils.isNotNull(orgYearTerm)) {
            model.put("beginTime", orgYearTerm.getStartDate());
            model.put("endTime", orgYearTerm.getEndDate());
        }
        model.put("teacherList", JSON.toJSONString(teacherService.getTeacherList()));
        model.put("teacherCode", teacherCode);
        model.put("adminClassList",
                        JSON.toJSONString(adminClassService.getAdminClassList(companyInfoVo.getOrgCode(), "")));
        model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(companyInfoVo.getOrgCode())));
        return "/manage/teacher/adminClassSubject.html";
    }

    /**
     * 删除行政班级学科 ()<br>
     * 
     * @param gid
     * @return
     */
    @RequestMapping("/deleteAdminClassSubject.html")
    @ResponseBody
    public ResultCodeVo deleteAdminClassSubject(String gid) {
        return adminClassSubejctService.delete(gid);
    }
}
