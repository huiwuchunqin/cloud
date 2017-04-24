package com.baizhitong.resource.manage.login.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.encrypt.MD5;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CodeConstants;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.CookiesUtil;
import com.baizhitong.resource.common.utils.PinYinHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.manage.authority.service.RoleService;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.manage.platform.service.ISharePlatformService;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.vo.authority.UserAuthSettingVo;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareUserLoginVo;

/**
 * 登录模块控制器
 * 
 * @author creator Cuidc 2015/12/14
 * @author updater
 */
@Controller
@RequestMapping("/manage")
public class LoginAction extends BaseAction {

    /** 登录模块接口 */
    private @Autowired LoginService          loginService;
    /** 角色信息接口 */
    private @Autowired RoleService           roleService;
    private @Autowired ShareOrgDao           shareOrgDao;
    /** 平台信息接口 */
    private @Autowired ISharePlatformService sharePlatformService;
    /** 教师数据接口 */
    private @Autowired ShareUserTeacherDao   shareUserTeacherDao;

    /**
     * 
     * (跳转到登录页面)<br>
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/login.html")
    public String toLogin(HttpServletRequest request, ModelMap model) {
        try {
            SharePlatform sharePlatform = sharePlatformService.queryPlatformInfo();
            model.put("sharePlatform", sharePlatform);
            model.put("agentEnable", SystemConfig.agentEnable);
        } catch (Exception e) {
            log.error("获取平台信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/login/login.html";
    }

    /**
     * session过期 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/sessionOut.html")
    public String SessionOut(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        SharePlatform sharePlatform;
        try {
            sharePlatform = sharePlatformService.queryPlatformInfo();
            model.put("sharePlatform", sharePlatform);
        } catch (Exception e) {
            log.error("获取平台信息错误", e);
            e.printStackTrace();
        }
        response.setHeader("sesstionOut", "1");
        if (SystemConfig.casEnable) {
            response.setHeader("url", SystemConfig.plsServerUrl + "/login/jumpToLogin?goto=");
            model.put("url", SystemConfig.plsServerUrl + "/login/jumpToLogin?goto=");
        } else {
            model.put("url", "/manage/login.html");
            response.setHeader("url", "/manage/login.html");
        }
        return "/manage/common/sessionOut.html";
    }

    /**
     * 全局登录 ()<br>
     * 
     * @param request
     * @param response
     * @param token
     * @return
     */
    @RequestMapping(value = "/adminGlobalLogin.html")
    public String globalLogin(ModelMap model, HttpServletRequest request, HttpServletResponse response, String token) {
        // 取得缓存服务器的用户信息
        UserInfoVo userInfoVo = null;
        if (StringUtils.isNotEmpty(token)) {
            userInfoVo = BeanHelper.getGlobalUserFromRedis(token);
            // 如果是教师登录，获取默认的学段、学科、年级等信息
            if (userInfoVo != null && CoreConstants.USER_ROLE_TEACHER.equals(userInfoVo.getUserRole())) {
                try {
                    Map<String, Object> teacherMap = shareUserTeacherDao
                                    .getTeacherInfoByTeaCode(userInfoVo.getUserCode());
                    if (MapUtils.isNotEmpty(teacherMap)) {
                        userInfoVo.setUserSectionCode(MapUtils.getString(teacherMap, "sectionCode"));
                        userInfoVo.setUserSubjectCode(MapUtils.getString(teacherMap, "subjectCode"));
                        userInfoVo.setUserGradeCode(MapUtils.getString(teacherMap, "gradeCode"));
                    }
                    CookiesUtil.write(BeanHelper.GLOBAL_USER_KEY, token, response);
                    model.put("adminUser", userInfoVo);
                    SharePlatform platform = sharePlatformService.queryPlatformInfo();
                    model.put("platform", platform);
                    ResultCodeVo result = loginService.globalLogin(userInfoVo);
                    if (result.getSuccess()) {
                        response.sendRedirect("/manage/index.html");
                        return "";
                    } else {
                        return "/manage/login/loginFail.html";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    String bakHost = "http://" + request.getHeader("host");
                    response.sendRedirect(SystemConfig.plsServerUrl + "/login/jumpToLogin?goto="
                                    + URLEncoder.encode(bakHost + "/manage/adminGlobalLogin.html"));
                    return "";
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 验证登录
     * 
     * @param loginAccount 登录账号
     * @param password 登录密码
     * @param domain 域名
     * @return 是否成功
     */
    @RequestMapping("/login/check.html")
    public @ResponseBody ResultCodeVo checkLogin(HttpServletRequest request, String loginAccount, String password,
                    String domain) {
        try {
            return loginService.checkLogin(loginAccount, password, domain);
        } catch (Exception e) {
            log.error("登录异常！", e);
            return new ResultCodeVo(false, CodeConstants.LOGIN_ERROR, "登录异常！");
        }
    }

    /**
     * 当用户有多个角色登录时,执行此方法
     * 
     * @param request 请求
     * @param roleId 角色ID
     * @return
     */
    @RequestMapping(value = "/multiRoleLogin.html")
    public @ResponseBody ResultCodeVo multiRoleChooseLogin(HttpServletRequest request, String roleId) {
        ResultCodeVo vo = new ResultCodeVo();
        try {
            AuthRole role = roleService.getRoleById(Integer.parseInt(roleId));
            if (role != null) {
                HttpSession session = request.getSession();
                session.setAttribute("role", role);
                vo.setSuccess(true);
                vo.setMsg(null);
            }
        } catch (Exception ex) {
            log.error("获取角色信息失败！", ex);
            return ResultCodeVo.errorCode(null);
        }
        return vo;
    }

    /**
     * 跳转到区域管理员页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/toAreaAdmin.html")
    public String toAreaAdmin(ModelMap map) {
        map.put("type", 10);
        return "/manage/administrator/areaAdmin.html";
    }

    /**
     * 
     * (跳转到教研员管理页面)<br>
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/toEduStaff.html")
    public String jumpToEduStaffManagePage(ModelMap map) {
        map.put("type", 30);
        return "/manage/administrator/eduStaff.html";
    }

    /**
     * 跳转到区域管理员新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/areaAdminAdd.html")
    public String areaAdminAdd(ModelMap map) {
        map.put("orgCode", CoreConstants.AREA_ORG_CODE);
        return "/manage/administrator/areaOrCompanyAdminAdd.html";
    }

    /**
     * 跳转到校内管理员新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/companyAdminAdd.html")
    public String areaCompanyAdd() {
        return "/manage/administrator/areaOrCompanyAdminAdd.html";
    }

    /**
     * 跳转到校内管理员新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/schoolAdminAdd.html")
    public String areaSchoolAdd(ModelMap map) {
        map.put("orgList", shareOrgDao.getOrgList());
        return "/manage/administrator/schoolAdminAdd.html";
    }

    /**
     * 跳转到学校管理员页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/toSchoolAdmin.html")
    public String toSchoolAdmin(ModelMap map) {
        map.put("type", 20);
        return "/manage/administrator/schoolAdmin.html";
    }

    /**
     * 跳转到机构管理员页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/toCompanyAdmin.html")
    public String toCompanyAdmin(HttpServletRequest request, ModelMap map) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        if (companyInfoVo != null) {
            map.put("orgCode", companyInfoVo.getOrgCode());
        }
        map.put("type", 20);
        return "/manage/administrator/companyAdmin.html";
    }

    /**
     * 跳转到管理员信息修改 ()<br>
     * 
     * @param userCode
     * @param map
     * @return
     */
    @RequestMapping(value = "/toLoginUserEdit.html")
    public String toAdminEdit(String userCode, ModelMap map) {
        map.put("loginUser", JSON.toJSONString(loginService.userLogin(userCode)));
        return "/manage/administrator/adminEdit.html";
    }

    /**
     * 跳转到密码修改页面 ()<br>
     * 
     * @param userCodes
     * @param map
     * @return
     */
    @RequestMapping(value = "/toPwdEdit.html")
    public String toPwdEdit(HttpServletRequest request, String userCodes, ModelMap map) {
        /*
         * ShareUserLogin userLogin = loginService.userLogin(userCode); if (userLogin != null &&
         * BeanHelper.isAreaAdmin(request)) { map.put("isArea", true); }
         */
        map.put("userCodes", userCodes);
        return "/manage/administrator/pwdModify.html";
    }

    /**
     * 
     * 查询区域管理员信息
     * 
     * @param userName 用户姓名
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    @RequestMapping("/areaAdmin.html")
    @ResponseBody
    public Page getAreaAdminList(String userName, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        return loginService.getAreaAdminList(userName, page, rows);
    }

    /**
     * 
     * (分页查询教研员列表信息)<br>
     * 
     * @param userName 姓名
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/eduStaff/search.html")
    @ResponseBody
    public Page<Map<String, Object>> getEduStaffList(String userName, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        try {
            return loginService.queryEduStaffList(userName, page, rows);
        } catch (Exception e) {
            log.error("查询教研员信息失败！", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询学校管理员信息 ()<br>
     * 
     * @param request
     * @param userName
     * @param orgName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/schoolAdmin.html")
    @ResponseBody
    public Page getShoolAdminList(HttpServletRequest request, String userName, String orgName, Integer page,
                    Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        return loginService.getSchoolAdminList(userName, orgName, "", page, rows);

    }

    /**
     * 查询本校管理员信息 ()<br>
     * 
     * @param request
     * @param userName
     * @param orgName
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/companyAdmin.html")
    @ResponseBody
    public Page getCompanyAdminList(HttpServletRequest request, String userName, String orgName, Integer page,
                    Integer rows) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        if (companyInfoVo == null)
            return null;
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        return loginService.getSchoolAdminList(userName, orgName, companyInfoVo.getOrgCode(), page, rows);
    }

    /**
     * 新增区域管理登录用户 ()<br>
     * 
     * @param request
     * @param loginUser
     */
    @RequestMapping("/areaLoginUserAdd.html")
    @ResponseBody
    public ResultCodeVo addAreaLoginUser(ShareUserLoginVo loginUserVo) {
        loginUserVo.setLoginPwd(MD5.calcMD5(loginUserVo.getLoginPwd()));
        return loginService.addLoginUser(loginUserVo, CoreConstants.USER_ROLE_AREA_ADMIN_CODE.toString());
    }

    /**
     * 新增学校管理登录用户 ()<br>
     * 
     * @param request
     * @param loginUser
     */
    @RequestMapping("/schoolLoginUserAdd.html")
    @ResponseBody
    public ResultCodeVo addSchoolLoginUser(ShareUserLoginVo loginUserVo) {
        loginUserVo.setLoginPwd(MD5.calcMD5(loginUserVo.getLoginPwd()));
        return loginService.addLoginUser(loginUserVo, CoreConstants.USER_ROLE_SCHOOL_ADMIN_CODE.toString());
    }

    /**
     * 更新登录用户信息 ()<br>
     * 
     * @param userName 用户姓名
     * @param mobilePhone 手机号
     * @param mail 邮箱
     * @param loginPwd 登录密码
     * @param userCode
     * @return
     */
    @RequestMapping("/updateLoginUser.html")
    @ResponseBody
    public ResultCodeVo updateLoginUser(String userName, String mobilePhone, String mail, String userCode) {
        return loginService.updateLoginUser(userName, mobilePhone, mail, userCode);

    }

    /**
     * 修改密码 ()<br>
     * 
     * @param password
     * @param oldPwd
     * @param userCodes
     * @return
     */
    @RequestMapping("/updatePwd.html")
    @ResponseBody
    public ResultCodeVo updatePwd(String password, String oldPwd, String userCodes) {
        return loginService.updatePwd(password, oldPwd, userCodes);

    }

    /**
     * 删除登录用户 ()<br>
     * 
     * @param gids
     * @return
     */
    @RequestMapping("/deleteLoginUser.html")
    @ResponseBody
    public ResultCodeVo deleteLoginUser(@RequestParam(value = "gids[]") String[] gids) {
        return loginService.deleteLoginUser(gids);
    }

    /**
     * 更新登录用户状态 ()<br>
     * 
     * @param userCode
     * @param status
     * @return
     */
    @RequestMapping("/updateState.html")
    @ResponseBody
    public ResultCodeVo updateState(String userCode, Integer status) {
        return loginService.updateState(userCode, status);
    }

    /**
     * 新增管理员 ()<br>
     * 
     * @param userCodes
     * @param type 10区域 20校内 30 教研员
     * @return
     */
    @RequestMapping("/adminAdd.html")
    @ResponseBody
    public ResultCodeVo addAreaAdmin(@RequestParam(value = "userCodes[]") String[] userCodes, String type) {
        return loginService.addAdmin(userCodes, type);
    }

    /**
     * (撤销角色)<br>
     * 
     * @param userCodes
     * @param type 10区域 20校内 30教研员
     * @return
     */
    @RequestMapping("/recallAdmin.html")
    @ResponseBody
    public ResultCodeVo recallAdmin(String userCode, String type) {
        return loginService.reCallAdmin(userCode, type);
    }

    /**
     * 
     * (跳转到教研员分配审核权限页面)<br>
     * 
     * @param userCode
     * @param loginAccount
     * @param model
     * @return
     */
    @RequestMapping("/eduStaff/authSet.html")
    public String jumpToUserPriviledgePage(String userCode, String loginAccount, ModelMap model) {
        try {
            UserAuthSettingVo settingVo = loginService.querySettingInfo(userCode, loginAccount);
            model.addAttribute("settingVo", JSONArray.toJSONString(settingVo));
        } catch (Exception e) {
            log.error("查询教研员已分配的审核权限信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/administrator/eduStaffAuthSet.html";
    }

    /**
     * 把机构简称装换为管理员账号 ()<br>
     * 
     * @param orgShortName
     * @return
     */
    @RequestMapping("/transformAccount.html")
    @ResponseBody
    public String tansformAccount(String orgShortName) {
        if (StringUtils.isEmpty(orgShortName)) {
            return "";
        } else {
            return PinYinHelper.getCompanyAccount(orgShortName);
        }

    }

    /**
     * 
     * (教研员审核权限保存)<br>
     * 
     * @param request
     * @param loginAccount
     * @param priviledgeSectionCodes
     * @param priviledgeGradeCodes
     * @param priviledgeSubjectCodes
     * @param userCode
     * @return
     */
    @RequestMapping("/eduStaff/authSet/save.html")
    @ResponseBody
    public ResultCodeVo saveEduStaffAuthSet(HttpServletRequest request, String loginAccount,
                    String priviledgeSectionCodes, String priviledgeGradeCodes, String priviledgeSubjectCodes,
                    String userCode) {
        try {
            return loginService.saveEduStaffAuthSet(loginAccount, priviledgeSectionCodes, priviledgeGradeCodes,
                            priviledgeSubjectCodes, userCode);
        } catch (Exception e) {
            log.error("教研员审核权限保存失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 修改密码 ()<br>
     * 
     * @param newpwd
     * @return
     */
    @RequestMapping("/modifyPwd")
    @ResponseBody
    public ResultCodeVo modifyPwd(HttpServletRequest request, String newpwd) {
        try {
            /*
             * System.out.println(request.getRealPath("/"));
             * System.out.println(request.getSession().getServletContext().getRealPath("/"));
             * System.out.println(LoginAction.class.getClassLoader().getSystemResource("/"));
             * System.out.println(LoginAction.class.getClassLoader().getResource(
             * "settings.properties")); return null;
             * LoginAction.class.getClassLoader().getResource("/").getPath();
             */
            FileReader reader = new FileReader(
                            LoginAction.class.getClassLoader().getResource("/").getPath() + "settings.properties");
            File newFile = new File(
                            LoginAction.class.getClassLoader().getResource("/").getPath() + "settings.properties");
            if (!newFile.createNewFile()) {
                ResultCodeVo.errorCode("文件创建失败");
            }
            ;
            FileWriter writer = new FileWriter(newFile);
            BufferedWriter bWriter = new BufferedWriter(writer);
            BufferedReader bReader = new BufferedReader(reader);
            String line;
            while ((line = bReader.readLine()) != null) {
                if (line.startsWith("pwd")) {
                    bWriter.write("pwd=" + MD5.calcMD5(newpwd) + "\n");
                } else {
                    bWriter.write(line + "\n");
                }
            }
            bReader.close();
            bWriter.flush();
            bWriter.close();
            return ResultCodeVo.rightCode("修改成功");
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("修改失败");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("修改失败");
        }

    }
}