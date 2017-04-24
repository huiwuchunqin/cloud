package com.baizhitong.resource.manage.company.action;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.FtpHelper;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassService;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.companyGrade.service.ICompanyGradeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.ftp.FtpClientProxy;
import com.baizhitong.utils.ftp.FtpConPoolManager;

/**
 * 机构控制类 CompanyAction
 * 
 * @author creator BZT 2016年1月22日 上午11:38:55
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/company")
public class CompanyAction extends BaseAction {
    /**
     * 机构接口
     */
    private @Autowired ICompanyService      companyService;
    /**
     * 学段接口
     */
    private @Autowired SectionService       sectionService;

    /** 机构年级接口 */
    private @Autowired ICompanyGradeService companyGradeService;

    /** 行政班级接口 */
    private @Autowired IAdminClassService   adminClassService;

    /**
     * 
     * 跳转到机构信息页面
     * 
     * @return
     */
    @RequestMapping("/toCompanyInfo.html")
    public String toCompanyInfo(ModelMap model) {
        try {
            model.put("sectionList", sectionService.selectSectionList());
            model.put("sectionJsonList", JSON.toJSONString(sectionService.selectSectionList()));
            model.put("imgPath", SystemConfig.getImgHost());
        } catch (Exception e) {
            log.error("查询学段信息失败", e);
        }
        return "/manage/company/companyInfo.html";
    }

    /**
     * 
     * 通过机构名称查询机构信息
     * 
     * @param orgName 机构名称
     * @return
     */
    @RequestMapping("/getOrgByName.html")
    @ResponseBody
    public ResultCodeVo getCompanyByName(String orgName, Integer isLike) {
        if (StringUtils.isEmpty(orgName))
            return ResultCodeVo.errorCode(null);
        try {
            List<ShareOrg> orgList = companyService.getCompanyByName(orgName, isLike);
            return ResultCodeVo.rightCode("查询成功", JSONArray.toJSONString(orgList));
        } catch (Exception e) {
            log.error("查询机构信息失败", e);
            return ResultCodeVo.errorCode("查询信息失败");
        }
    }

    /**
     * 机构信息 ()<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/getSchoolInfo.html")
    public String toCompany(HttpServletRequest request, ModelMap model) {
        try {
            CompanyInfoVo sessionCompanyInfoVo =  getCompanyInfo();
            CompanyInfoVo companyInfoVo = companyService.getCompanyInfo(sessionCompanyInfoVo.getOrgCode());
            companyInfoVo.setSectionName(sessionCompanyInfoVo.getSectionName());
            model.put("org", JSON.toJSONString(companyInfoVo));
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            model.put("imageHost", SystemConfig.getImgHost());
        } catch (Exception e) {
            log.error("查询学段信息失败", e);
            e.printStackTrace();
        }
        return "/manage/company/schoolInfo.html";
    }

    /**
     * 跳转到机构学生信息 ()<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/toSchoolStudentInfo.html")
    public String toSchoolStudentnfo(HttpServletRequest request, ModelMap model, String adminClassCode,
                    String gradeCode, String teachingClassCode, Integer studentChoose) {
        try {
            CompanyInfoVo companyInfoVo =  getCompanyInfo();
            model.put("adminClassCode", adminClassCode);
            model.put("teachingClassCode", teachingClassCode);
            model.put("orgCode", companyInfoVo.getOrgCode());
            model.put("gradeCode", gradeCode);
            model.put("studentChoose", studentChoose);
            // 年级列表
            model.put("gradeList", JSONArray.toJSONString(companyGradeService.getGradeList()));
            // 行政班级列表
            model.put("adminClassList", JSONArray.toJSONString(adminClassService.getList(companyInfoVo.getOrgCode())));
            model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(companyInfoVo.getOrgCode())));
        } catch (Exception e) {
            log.error("查询学段信息失败", e);
        }
        return "/manage/company/companyStudent.html";
    }

    /**
     * 跳转到机构老师信息 ()<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/toSchoolTeacherInfo.html")
    public String toSchoolTeacherInfo(HttpServletRequest request, ModelMap model, Integer type) {
        try {
            CompanyInfoVo companyInfoVo =  getCompanyInfo();
            model.put("orgCode", companyInfoVo.getOrgCode());
            model.put("sectionList", JSON.toJSONString(companyService.getCompanySection(companyInfoVo.getOrgCode())));
            model.put("type", type);
            return "/manage/company/companyTeacher.html";
        } catch (Exception e) {
            log.error("查询学段信息失败", e);
            return "/manage/company/companyTeacher.html";
        }
    }

    /**
     * 查询机构信息 ()<br>
     * 
     * @param orgName 机构名称
     * @param sectionCode 学段编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    @RequestMapping("/companyPageInfo.html")
    @ResponseBody
    public Page getCompanyPageInfo(String orgName, String sectionCode, Integer page, Integer rows) {
        if (rows == null)
            rows = 50;
        if (page == null)
            page = 1;
        return companyService.getOrgPageInfo(orgName, sectionCode, rows, page);

    }

    /**
     * 保存机构信息 ()<br>
     * 
     * @param org
     * @return
     */
    @RequestMapping("/saveCompany.html")
    @ResponseBody
    public ResultCodeVo saveCompany(ShareOrg org, String sectionCodes, String validDateStartStr,
                    String validDateEndStr) {
        if (StringUtils.isNotEmpty(validDateStartStr)) {
            org.setValidDateStart(new Timestamp(DateUtils.getDateTime(validDateStartStr, "yyyy-MM-dd").getTime()));
        }
        if (StringUtils.isNotEmpty(validDateEndStr)) {
            org.setValidDateEnd(new Timestamp(DateUtils.getDateTime(validDateEndStr, "yyyy-MM-dd").getTime()));
        }
        return companyService.saveCompany(org, sectionCodes, null, null, CoreConstants.USER_ROLE_SCHOOL_ADMIN_CODE);
    }

    /**
     * 更新机构信息 ()<br>
     * 
     * @param orgCode
     * @param orgName
     * @param orgNameShort
     * @param phone
     * @param sectionCode
     * @param logoUrl
     * @param introduction
     * @return
     */
    @RequestMapping("/updateOrg.html")
    @ResponseBody
    public ResultCodeVo updateOrg(String orgCode, String orgName, String mail, String orgNameShort, String phone,
                    String sectionCode, String logoUrl, String introduction, String topName) {
        return companyService.updateCompany(orgCode, orgName, mail, orgNameShort, phone, sectionCode, logoUrl,
                        introduction, topName);
    }

    /**
     * 
     * (ftp上传图片)<br>
     * 
     * @param file
     * @param fileType
     * @return
     */
    @RequestMapping("/uploadImage.html")
    @ResponseBody
    public ResultCodeVo uploadImage(@RequestParam(value = "file") MultipartFile file, String fileType) {
        try {
            if (file.isEmpty()){
                return ResultCodeVo.errorCode("没有文件"); 
            }
            FtpClientProxy proxy = FtpConPoolManager.getConnection();
            InputStream is =FtpHelper.compressPic(file);
            String fileName = FtpHelper.getFileName(file.getOriginalFilename());
            String filePath = "";
            if ("company".equals(fileType)) {
                filePath = CoreConstants.FTP_COMPANY + "/" + fileName;
            } else if ("user".equals(fileType)) {
                filePath = CoreConstants.FTP_USER + "/" + fileName;
            } else if ("gift".equals(fileType)) {
                filePath = CoreConstants.FTP_GIFT + "/" + fileName;
            } else if ("cover".equals(fileType)) {
                filePath = CoreConstants.FTP_RES_HOME_COVER + "/" + fileName;
            } else if ("thumbnail".equals(fileType)) {
                filePath = CoreConstants.FTP_RES_HOME_THUMBNAIL + "/" + fileName;
            } else if ("platform".equals(fileType)) {
                filePath = CoreConstants.FTP_PLATFORM_LOGO + "/" + fileName;
            } else if ("media".equals(fileType)) {
                String date = DateUtils.getDate("YYYYMMdd");
                filePath = CoreConstants.MEDIA_COVER + "/" + date.substring(0, 4) + "/" + date.substring(4, 6) + "/"
                                + date.substring(6, 8) + "/" + fileName;
            }
            proxy.uploadFile(filePath, is);
            proxy.release();
            return ResultCodeVo.rightCode("文件上传成功！", filePath);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping("/sectionOrg.html")
    @ResponseBody
    public List<Map<String, Object>> getOrgList(String sectionCode) {
        return companyService.getSectionCompany(sectionCode);
    }

}
