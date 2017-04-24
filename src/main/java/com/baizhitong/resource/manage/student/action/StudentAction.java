package com.baizhitong.resource.manage.student.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.RegexUtil;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassService;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.companyGrade.service.ICompanyGradeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.student.service.IStudentService;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserStudent;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.ftp.FtpClientProxy;
import com.baizhitong.utils.ftp.FtpConPoolManager;

/**
 * 学生信息控制类 StudentAction
 * 
 * @author creator gaow 2016年1月23日 下午1:42:44
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/student")
public class StudentAction extends BaseAction {
    /** 学生接口 */
    @Autowired
    private IStudentService      studentService;
    /** 学段接口 */
    @Autowired
    private SectionService       sectionService;
    @Autowired
    private ShareUserStudentDao  shareUserStudentDao;
    /** 机构年级接口 */
    @Autowired
    private ICompanyGradeService companyGradeService;
    /** 机构接口 */
    @Autowired
    private ICompanyService      companyService;
    /** 行政班级接口 */
    @Autowired
    private IAdminClassService   adminClassService;
    /** 登录接口 */
    @Autowired
    private ShareUserLoginDao    shareUserLoginDao;

    /**
     * 跳转到学生信息页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toStudentInfo.html")
    public String toStudentInfo(ModelMap map, String adminClassCode) {
        try {
            map.put("sectionList", sectionService.selectSectionList());
            map.put("adminClassCode", adminClassCode);
        } catch (Exception e) {
            log.error("查询学段信息失败", e);
            e.printStackTrace();
        }
        return "/manage/student/studentInfo.html";
    }

    /**
     * 查询学生分页信息 ()<br>
     * 
     * @param loginAccount 登录账号
     * @param studentChoose 是否为选择学生1是 0否
     * @param type 1行政班级学生 0课程班级学生
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param userName 学生姓名
     * @param orgName 机构名称
     * @param studentNumber 学号
     * @param rows 每页记录数
     * @param pageNo 页码
     * @return
     */
    @RequestMapping("/studentPageInfo.html")
    @ResponseBody
    public Page getStudentInfo(boolean noAdminClass, String studentNumber, String loginAccount, Integer type,
                    Integer studentChoose, String teachingClassCode, String adminClassCode, String sectionCode,
                    String gradeCode, String userName, String orgName, String orgCode, Integer rows, Integer page) {
        if (rows == null)
            rows = 50;
        if (page == null)
            page = 1;
        if (studentChoose == null)
            studentChoose = 0;
        return studentService.getStudentPageInfo(noAdminClass, studentNumber, loginAccount, type, studentChoose,
                        teachingClassCode, adminClassCode, sectionCode, gradeCode, userName, orgName, orgCode, rows,
                        page);
    }

    /**
     * 跳转到学生新增页面 ()<br>
     * 
     * @param adminClassCode
     * @param gradeCode
     * @return
     */
    @RequestMapping("/toStudentAdd.html")
    public String toStudentAdd(HttpServletRequest request, ModelMap model, String adminClassCode, String gradeCode) {
        CompanyInfoVo company =  getCompanyInfo();
        // 行政班级列表
        model.put("adminClassList", JSONArray.toJSONString(adminClassService.getList(company.getOrgCode())));
        model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(company.getOrgCode())));
        // 年级列表
        model.put("gradeList", JSONArray.toJSONString(companyGradeService.getGradeList()));
        model.put("adminClassCode", adminClassCode);
        model.put("gradeCode", gradeCode);
        return "/manage/company/companyStudentAdd.html";
    }

    /**
     * 跳转到学生修改页面 ()<br>
     * 
     * @param gradeCode
     * @return
     */
    @RequestMapping("/toStudentEdit.html")
    public String toStudentEdit(HttpServletRequest request, String studentCode, ModelMap model, String adminClassCode,
                    String gradeCode) {
        CompanyInfoVo company =  getCompanyInfo();
        // 行政班级列表
        model.put("adminClassList", JSONArray.toJSONString(adminClassService.getList(company.getOrgCode())));
        // 年级列表
        model.put("gradeList", JSONArray.toJSONString(companyGradeService.getGradeList()));
        model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(company.getOrgCode())));
        model.put("student", JSON.toJSONString(shareUserStudentDao.getStudentByCode(studentCode)));
        model.put("loginUser", JSON.toJSONString(shareUserLoginDao.getUser(studentCode)));
        model.put("adminClassCode", adminClassCode);
        model.put("gradeCode", gradeCode);
        return "/manage/company/companyStudentEdit.html";
    }

    /**
     * 新增或更新学生 ()<br>
     * 
     * @param student
     * @return
     */
    @RequestMapping("/saveOrUpdateStudent.html")
    @ResponseBody
    public ResultCodeVo saveOrUpdate(HttpServletRequest request, ShareUserStudent student, String enterSchoolDateStr,
                    ShareUserLogin loginUser) {
        CompanyInfoVo company =  getCompanyInfo();
        student.setOrgCode(company.getOrgCode());
        student.setAdminClassCode(URLDecoder.decode(student.getAdminClassCode()));
        if (StringUtils.isNotEmpty(enterSchoolDateStr)) {
            student.setEnterSchoolDate(
                            new Timestamp(DateUtils.getDateTime(enterSchoolDateStr, "yyyy-MM-dd").getTime()));
        } else {
            student.setEnterSchoolDate((Timestamp) (null));
        }
        if (!RegexUtil.valideLength(loginUser.getMail(), 0, 50)) {
            return ResultCodeVo.errorCode("邮箱长度不能超过50位");
        }
        return studentService.addOrUpdateStudent(student, loginUser);
    }

    /**
     * 上传学生图片 ()<br>
     * 
     * @param file
     * @return
     */
    @RequestMapping("/uploadStudentImage.html")
    @ResponseBody
    public ResultCodeVo uploadImage(@RequestParam(value = "file") MultipartFile file) {
        try {
            FtpClientProxy proxy = FtpConPoolManager.getConnection();
            InputStream is = file.getInputStream();
            String fileName = file.getOriginalFilename();
            fileName = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf(".") + 1, fileName.length());
            String filePath = CoreConstants.FTP_USER + fileName;
            proxy.uploadFile(filePath, is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 导入学生信息 ()<br>
     * 
     * @param file
     * @return
     */
    @RequestMapping("/importStudent.html")
    @ResponseBody
    public ResultCodeVo importStudent(@RequestParam(value = "file") MultipartFile file) {
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
            return studentService.importStudent(wb.getSheetAt(0));
        } catch (Exception e) {
            e.printStackTrace();
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
        String path = request.getRealPath("/学生导入模板.xlsx");
        File file = new File(path);
        String fileName = file.getName();
        if (file != null) {
            try {
                InputStream stream = new FileInputStream(file);
                response.setContentType("application/x-msdownload");
                response.setHeader("Content-disposition",
                                "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
                IOUtils.copy(stream, response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 删除学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    @RequestMapping("/deleteStudent.html")
    @ResponseBody
    public ResultCodeVo deleteStudent(String userCode) {
        return studentService.deleteStudent(userCode);
    }
}
