package com.baizhitong.resource.manage.teacher.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.RegexUtil;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserTeacher;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * 教师信息控制类
 * 
 * @author creator gaow 2016年1月23日 下午1:42:23
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/teacher")
public class TeacherAction extends BaseAction {

    /** 教师接口 */
    @Autowired
    private ITeacherService   teacherService;
    /** 学段接口 */
    @Autowired
    private SectionService    sectionService;
    /** 机构接口 */
    @Autowired
    private ICompanyService   companyService;
    /** 登录DAO */
    @Autowired
    private ShareUserLoginDao loginDao;

    /**
     * (跳转到教师信息页面 )<br>
     * 
     * @Param type 10区域 20校内 30教研员
     * @return
     */
    @RequestMapping("/toTeacherInfo.html")
    public String toTeacherInfo(ModelMap map, Integer type) {
        try {
            map.put("sectionList", sectionService.selectSectionList());
            map.put("type", type);
        } catch (Exception e) {
            log.error("查询学段信息失败", e);
            e.printStackTrace();
        }
        return "/manage/teacher/teacherInfo.html";
    }

    /**
     * 查询老师列表 ()<br>
     * 
     * @return
     */
    @RequestMapping("/getTeacherList.html")
    @ResponseBody
    public List<ShareUserTeacher> getTeacherList(String orgCode) {
        return teacherService.getTeacherList(orgCode);
    }

    /**
     * 查询教师信息 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param orgName 机构名称
     * @param userName 用户姓名
     * @param loginAccount 登录账号
     * @param userRole 用户角色
     * @param rows 每页记录数
     * @param pageNo 页码
     * @Param type 10区域 20校内
     * @return
     */
    @RequestMapping("/teacherPageInfo.html")
    @ResponseBody
    public Page teacherInfo(String sectionCode, String subjectCode, String orgName, String orgCode, String userName,
                    String loginAccount, Integer userRole, Integer rows, Integer page, Integer type) {
        if (rows == null)
            rows = 50;
        if (page == null)
            page = 1;
        return teacherService.getTeacherInfo(sectionCode, subjectCode, orgName, orgCode, userName, loginAccount,
                        userRole, rows, page, type);
    }

    /**
     * 跳转到老师新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toTeacherAdd.html")
    public String toTeacherAdd(HttpServletRequest request, ModelMap model) {
        CompanyInfoVo company =  getCompanyInfo();
        model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(company.getOrgCode())));
        return "/manage/company/companyTeacherAdd.html";

    }

    /**
     * 跳转到老师修改页面 ()<br>
     * 
     * @param teacherCode
     * @return
     */
    @RequestMapping("/toTeacherEdit.html")
    public String toTeacherEdit(HttpServletRequest request, ModelMap model, String teacherCode) {
        CompanyInfoVo company =  getCompanyInfo();
        model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(company.getOrgCode())));
        model.put("teacher", JSON.toJSONString(teacherService.getTeacher(teacherCode)));
        model.put("loginUser", JSON.toJSONString(loginDao.getUser(teacherCode)));
        return "/manage/company/companyTeacherEdit.html";
    }

    /**
     * 保存老师信息 ()<br>
     * 
     * @param teacher
     * @param workFirstDayStr
     * @param userLogin
     * @return
     */
    @RequestMapping("/saveTeacher.html")
    @ResponseBody
    public ResultCodeVo saveOrUpdateTeacher(ShareUserTeacher teacher, String workFirstDayStr,
                    ShareUserLogin userLogin) {
        if (!RegexUtil.valideLength(userLogin.getMail(), 0, 50)) {
            return ResultCodeVo.errorCode("邮箱长度不能超过50位");
        }
        if (StringUtils.isNotEmpty(workFirstDayStr)) {
            teacher.setWorkFirstDay(new Timestamp(DateUtils.getDateTime(workFirstDayStr, "yyyy-MM-dd").getTime()));
        }
        return teacherService.saveOrUpdateTeacher(teacher, userLogin);
    }

    /**
     * 删除老师 ()<br>
     * 
     * @param userCode
     * @return
     */
    @RequestMapping("/deleteTeacher.html")
    @ResponseBody
    public ResultCodeVo deletTeacher(String userCode) {
        return teacherService.deleteTeacher(userCode);
    }

    /**
     * 导入老师 ()<br>
     * 
     * @param file
     * @return
     */
    @RequestMapping("/importTeacher.html")
    @ResponseBody
    public ResultCodeVo importTeacher(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultCodeVo.errorCode("请选择文件");
        }
        try {
            InputStream is = file.getInputStream();
            Workbook wb = null;
            if (file.getOriginalFilename().endsWith(".xls")) { // 97-2003
                wb = new HSSFWorkbook(is);
            } else if (file.getOriginalFilename().endsWith(".xlsx")) { // 2007-2010
                wb = new XSSFWorkbook(is);
            }
            return teacherService.importTeacher(wb.getSheetAt(0));

        } catch (IOException e1) {
            e1.printStackTrace();
            return ResultCodeVo.errorCode("导入失败");
        }
    }

    /**
     * 下载模板 ()<br>
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/downLoadTemp.html")
    public ModelAndView downLoadTemp(HttpServletRequest request, HttpServletResponse response) {
        String path = request.getRealPath("/老师导入模板.xlsx");
        File file = new File(path);
        String fileName = file.getName();
        if (file != null) {
            try {
                InputStream stream = new FileInputStream(file);
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-disposition",
                                "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
                IOUtils.copy(stream, response.getOutputStream());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
