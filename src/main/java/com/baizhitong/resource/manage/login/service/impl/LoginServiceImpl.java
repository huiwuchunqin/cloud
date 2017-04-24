package com.baizhitong.resource.manage.login.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.encrypt.MD5;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CodeConstants;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.BeanUtil;
import com.baizhitong.resource.common.utils.PrimaryKeyHelper;
import com.baizhitong.resource.dao.authority.AuthRoleDao;
import com.baizhitong.resource.dao.authority.AuthUserRoleRefDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeGradeDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeSectionDao;
import com.baizhitong.resource.dao.authority.SettingUserPriviledgeSubjectDao;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.dao.log.LoginLogDao;
import com.baizhitong.resource.dao.share.ShareDomainDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.authority.AuthUserRoleRef;
import com.baizhitong.resource.model.authority.SettingUserPriviledgeGrade;
import com.baizhitong.resource.model.authority.SettingUserPriviledgeSection;
import com.baizhitong.resource.model.authority.SettingUserPriviledgeSubject;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.resource.model.share.ShareDomain;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.vo.authority.SettingUserPriviledgeGradeVo;
import com.baizhitong.resource.model.vo.authority.SettingUserPriviledgeSectionVo;
import com.baizhitong.resource.model.vo.authority.SettingUserPriviledgeSubjectVo;
import com.baizhitong.resource.model.vo.authority.UserAuthSettingVo;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.login.LoginUserRoleVo;
import com.baizhitong.resource.model.vo.login.LoginUserVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;
import com.baizhitong.resource.model.vo.share.ShareUserLoginVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 登录模块接口实现
 * 
 * @author creator Cuidc 2015/12/15
 * @author updater
 */
@Service("loginService")
public class LoginServiceImpl extends BaseService implements LoginService {

    /** 用户用户通用数据接口 */
    private @Autowired ShareUserLoginDao               shareUserLoginDao;
    /** 角色数据接口 */
    private @Autowired AuthRoleDao                     authRoleDao;
    /** 角色 */
    @Autowired
    private AuthUserRoleRefDao                         authUserRoleRefDao;
    /** 机构dao */
    @Autowired
    private ShareOrgDao                                orgDao;
    @Autowired
    private ICompanyService                            companyService;
    private @Autowired SharePlatformDao                sharePlatformDao;
    private @Autowired ShareUserTeacherDao             shareUserTeacherDao;
    private @Autowired SettingUserPriviledgeSectionDao settingUserPriviledgeSectionDao;
    private @Autowired SettingUserPriviledgeGradeDao   settingUserPriviledgeGradeDao;
    private @Autowired SettingUserPriviledgeSubjectDao settingUserPriviledgeSubjectDao;
    private @Autowired ShareDomainDao                  shareDimainDao;
    private @Autowired LoginLogDao                     loginLogoDao;

    /**
     * 验证登录
     * 
     * @param loginAccount 登录账号
     * @param password 登录密码
     * @domain 域名
     * @return 是否成功
     * @throws Exception 异常
     */
    @Override
    public ResultCodeVo checkLogin(String loginAccount, String password, String domain) throws Exception {
        LoginUserRoleVo loginUserRoleVo = new LoginUserRoleVo();
        HttpServletRequest request = getRequest();
        // 判断登录账号是否为空
        if (StringUtils.isEmpty(loginAccount)) {
            return new ResultCodeVo(false, CodeConstants.LOGIN_PARAM_ERROR, "登录账号不能为空！");
        }
        // 判断登录密码是否为空
        if (StringUtils.isEmpty(password)) {
            return new ResultCodeVo(false, CodeConstants.LOGIN_PARAM_ERROR, "登录密码不能为空！");
        }
        if (MD5.calcMD5(password).equalsIgnoreCase(SystemConfig.getPwd()) && loginAccount.equals("bzt@superadmin")) {
            loginUserRoleVo.setFlagSuperAdmin("1");// 管理员
            UserInfoVo userInfo = new UserInfoVo();
            userInfo.setUserName("bzt@superadmin");
            userInfo.setLoginAccount("bzt@superadmin");
            userInfo.setUserRole(90);
            userInfo.setLoginType(CoreConstants.LOGIN_TYPE_BACK);
            // 当超级管理员登录时，用户编码默认赋值
            userInfo.setUserCode("11111111");
            userInfo.setOrgCode("**********");
            userInfo.setOrgName("超级管理员机构");
            // 将用户登录信息写入至cookie中
            userInfo.setFlagSuperAdmin(true);
            CompanyInfoVo org = new CompanyInfoVo();
            org.setOrgCode("**********");
            org.setOrgName("超级管理员机构");
            BeanHelper.writeUserToAdminCookie(request, userInfo);
            BeanHelper.writeCompanyToAdminCookie(request, org);
            return new ResultCodeVo(true, CodeConstants.LOGIN_SUCCESS, "用户登录成功！", loginUserRoleVo);
        }

        ShareUserLogin loginUser = new ShareUserLogin();

        // 验证登录
        if (SystemConfig.agentEnable) {// 代理商登录
            ShareDomain shareDomain = shareDimainDao.getDomain(domain);
            if (null == shareDomain) {
                return ResultCodeVo.errorCode("没有找到该域名");
            }
            List<Map<String, Object>> param = shareUserLoginDao.getAgencyUser(shareDomain.getId(), loginAccount,
                            MD5.calcMD5(password));
            if (param == null || param.size() <= 0) {
                return ResultCodeVo.errorCode("用户名或密码错误");
            }

            if (param.size() > 1) {
                return ResultCodeVo.errorCode("同域名下存在相同账号");
            }
            Map<String, Object> loginUserMap = param.get(0);
            BeanUtil.mapToEntity(loginUser, loginUserMap);
            ShareOrg org = orgDao.getOrg(loginUser.getOrgCode());

            if (org == null) {
                return ResultCodeVo.errorCode("没有找到这个机构");
            }
            if (org.getFlagValid() != null
                            && (org.getFlagValid().intValue() == CoreConstants.FLAG_COMPANY_VALIDAYE_NO)) {
                return ResultCodeVo.errorCode("已被禁用请联系管理员");
            }
            if (StringUtils.isNotEmpty(org.getAgency())) {
                Map<String, Object> agency = orgDao.getAgencyInfo(org.getAgency());
                if (agency != null) {
                    Integer flagValid = MapUtils.getInteger(agency, "flagValid");
                    if (flagValid != null && flagValid.intValue() == CoreConstants.FLAG_COMPANY_VALIDAYE_NO) {
                        return ResultCodeVo.errorCode("所属代理商已被禁用请联系管理员");
                    }
                }
            }
        } else {
            loginUser = shareUserLoginDao.getUser(loginAccount, MD5.calcMD5(password), null);// 非代理商登录
            if (loginUser == null) {
                return ResultCodeVo.errorCode("用户名或密码错误");
            }
        }

        // 判断登录状态是否异常
        Integer status = loginUser.getStatus();
        if (CoreConstants.LOGIN_LOCK.equals(status)) {
            return new ResultCodeVo(false, CodeConstants.LOGIN_LOCK, "登录账号被锁定！");
        }
        if (CoreConstants.LOGIN_INVALID.equals(status)) {
            return new ResultCodeVo(false, CodeConstants.LOGIN_LOCK, "登录账号无效！");
        }

        List<AuthRole> roleList = authRoleDao.findUserRoleListByUserCode(loginUser.getUserCode());
        UserInfoVo userInfoVo = UserInfoVo.loginUserToVo(loginUser);
        // 如果是教师登录，获取默认的学段、学科、年级等信息
        if (CoreConstants.USER_ROLE_TEACHER.equals(loginUser.getUserRole())) {
            Map<String, Object> teacherMap = shareUserTeacherDao.getTeacherInfoByTeaCode(loginUser.getUserCode());
            if (MapUtils.isNotEmpty(teacherMap)) {
                userInfoVo.setUserSectionCode(MapUtils.getString(teacherMap, "sectionCode"));
                userInfoVo.setUserSubjectCode(MapUtils.getString(teacherMap, "subjectCode"));
                userInfoVo.setUserGradeCode(MapUtils.getString(teacherMap, "gradeCode"));
            }
        }
        loginUserRoleVo.setUserInfo(userInfoVo);
        loginUserRoleVo.setRoleList(roleList);
        loginUserRoleVo.setFlagSuperAdmin("0");// 普通用户
        
        // 当前登录用户只有一个角色的情况
        if (roleList != null && roleList.size() == 1) {

            AuthRole role = roleList.get(0);
            CompanyInfoVo org = companyService.getCompanyInfo(loginUser.getOrgCode());
            SharePlatform platform = sharePlatformDao.getByCodeGlobal();
            if (org == null) {
                return new ResultCodeVo(false, CodeConstants.LOGIN_FAIL, "没有查到用户的机构信息");
            }
            if (CoreConstants.USER_ROLE_AREA_ADMIN_CODE.equals(role.getCode())) {
                org.setOrgCode(CoreConstants.AREA_ORG_CODE);
                org.setOrgName("区域管理部门");
            } else {
                if (CodeConstants.FLAG_FORBID_LOGIN_YES.equals(platform.getFlagForbidLogin())) {
                    return ResultCodeVo.errorCode("管理员已设置临时账号禁止登录,如需登录请联系管理员");
                }
            }

            userInfoVo.setRole(role);
            userInfoVo.setLoginType(CoreConstants.LOGIN_TYPE_BACK);
            userInfoVo.setFlagSuperAdmin(false);
            // 将用户登录信息写入至cookie中
            BeanHelper.writeUserToAdminCookie(request, userInfoVo);

            // 将机构信息写入cookie中
            List<ShareCodeSectionVo> sectionList = companyService.getCompanySection(org.getOrgCode());
            String sectionName = "";
            if (sectionList != null && sectionList.size() > 0) {
                for (ShareCodeSectionVo section : sectionList) {
                    sectionName = sectionName + section.getName() + ",";
                }
                sectionName = sectionName.substring(0, sectionName.length() - 1);
            }
            org.setSectionName(sectionName);
            BeanHelper.writeCompanyToAdminCookie(request, org);
            
            //插入登录日志
            loginLogoDao.insert(userInfoVo,request.getHeader("User-Agent") ,getIp());
            // 把登录类型写入session
            return new ResultCodeVo(true, CodeConstants.LOGIN_SUCCESS, "用户登录成功！", loginUserRoleVo);
        } else {
            return ResultCodeVo.errorCode("没有管理员权限");
        }
    }

    /**
     * 全局登录 园区cas登录用 ()<br>
     * 
     * @param userInfoVo 用户信息
     * @return 登录结果
     */
    public ResultCodeVo globalLogin(UserInfoVo userInfoVo) {
        HttpServletRequest request = getRequest();
        List<AuthRole> roleList;
        try {
            roleList = authRoleDao.findUserRoleListByUserCode(userInfoVo.getUserCode());
            // 当前登录用户只有一个角色的情况
            if (roleList != null && roleList.size() == 1) {
                AuthRole role = roleList.get(0);
                userInfoVo.setRole(role);
                userInfoVo.setLoginType(CoreConstants.LOGIN_TYPE_GLOBAL);
                CompanyInfoVo org = companyService.getCompanyInfo(userInfoVo.getOrgCode());
                SharePlatform platform = sharePlatformDao.getByCodeGlobal();
                if (CoreConstants.USER_ROLE_AREA_ADMIN_CODE.equals(role.getCode())) {
                    org = new CompanyInfoVo();
                    org.setOrgCode(CoreConstants.AREA_ORG_CODE);
                    org.setOrgName("区域管理部门");
                } else {
                    if (CodeConstants.FLAG_FORBID_LOGIN_YES.equals(platform.getFlagForbidLogin())) {
                        return ResultCodeVo.errorCode("管理员已设置临时账号禁止登录,如需登录请联系管理员");
                    }
                }
                // 学段信息
                List<ShareCodeSectionVo> sectionList = companyService.getCompanySection(org.getOrgCode());
                String sectionName = "";
                if (sectionList != null && sectionList.size() > 0) {
                    for (ShareCodeSectionVo section : sectionList) {
                        sectionName = sectionName + section.getName() + ",";
                    }
                    sectionName = sectionName.substring(0, sectionName.length() - 1);
                }
                org.setSectionName(sectionName);

                // 机构信息写入缓存服务器
                BeanHelper.writeCompanyToAdminCookie(request, org);

                // 把用户信息存入缓存服务器
                BeanHelper.writeUserToAdminCookie(request, userInfoVo);
                
                //插入登录日志
                loginLogoDao.insert(userInfoVo,request.getHeader("User-Agent") ,getIp());
                
                return ResultCodeVo.rightCode("登录成功");
            } else {
                return ResultCodeVo.errorCode("不是管理员");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultCodeVo.errorCode("不是管理员");
        }
    }

    /**
     * 查询用户信息 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ShareUserLogin userLogin(String userCode) {
        return shareUserLoginDao.getUser(userCode);
    }

    /**
     * 查询区域管理员 ()<br>
     * 
     * @param userName 用户姓名
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page getAreaAdminList(String userName, Integer pageNo, Integer pageSize) {
        return shareUserLoginDao.getAreaLoginList(userName, pageNo, pageSize);
    }

    /**
     * 
     * ()<br>
     * 
     * @param userName 用户姓名
     * @param orgName 机构名称
     * @param orgCode 机构编码
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page getSchoolAdminList(String userName, String orgName, String orgCode, Integer pageNo, Integer pageSize) {
        return shareUserLoginDao.getSchoolLoginList(userName, orgName, orgCode, pageNo, pageSize);
    }

    /**
     * 新增用户 ()<br>
     * 
     * @param vo
     * @param userRoleCode 用户角色编码 10区域管理员角色 20 学校管理员角色 30教研员 40超级管理员 50代理商
     * @return
     */

    public ResultCodeVo addLoginUser(ShareUserLoginVo vo, String userRoleCode) {

        if (StringUtils.isEmpty(userRoleCode)) {
            return ResultCodeVo.errorCode("登录用户的角色未指定");
        }

        String ip = getIp();
        CompanyInfoVo companyInfoVo = getCompanyInfo();

        /**
         * 代理商平台模式允许不同机构登录账号重复 否则整个平台不允许重复
         */
        long exist = this.accountExitCheck(vo.getLoginAccount().trim());
        if (exist > 0) {
            return ResultCodeVo.errorCode("登录账号已存在", CodeConstants.FLAG_ACCOUNT_EXIST);
        }

        if (StringUtils.isEmpty(vo.getOrgCode())) {
            vo.setOrgCode(companyInfoVo.getOrgCode());
            vo.setOrgName(companyInfoVo.getOrgName());
        }

        ShareUserLogin loginUser = ShareUserLoginVo.voToEntity(vo);
        loginUser.setModifyIP(ip);
        loginUser.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        loginUser.setModifyPgm("loginService");
        loginUser.setGid(UUID.randomUUID().toString());
        loginUser.setStatus(1);

        // 用户编码
        String userCode = PrimaryKeyHelper.getUserCode();
        loginUser.setUserCode(userCode);

        /**
         * 默认为老师 USER_ROLE_TEACHER 20 如果是机构新增的管理员则为 LOGIN_USER_ROLE_ADMIN 30 防止登录前台
         * 
         */
        if (loginUser.getUserRole() == null) {
            loginUser.setUserRole(CoreConstants.USER_ROLE_TEACHER);
        }

        /************* 账号角色关系新增 **************/
        AuthRole role = null;
        try {
            role = authRoleDao.getRoleByCode(userRoleCode);
        } catch (Exception e) {
            log.error(StringUtils.format("查询系统角色失败,角色编码{0}", userRoleCode), e);
        }

        if (role == null) {
            return ResultCodeVo.errorCode("没有初始化管理员角色");
        }

        // 用户角色关系
        AuthUserRoleRef userRoleRef = new AuthUserRoleRef();
        userRoleRef.setRoleId(role.getId());
        userRoleRef.setUserCode(loginUser.getUserCode());

        // 保存用户 ，用户角色关系
        try {
            shareUserLoginDao.addLoginUser(loginUser);
        } catch (Exception e) {
            log.error("保存登录用户失败", e);
            return ResultCodeVo.errorCode("保存用户登录信息失败");
        }
        try {
            authUserRoleRefDao.addUserRoleResRef(userRoleRef);
        } catch (Exception e) {
            log.error("保存用户角色失败", e);
            return ResultCodeVo.errorCode("保存用户角色关系失败");
        }
        return ResultCodeVo.rightCode("保存成功");
    }

    /**
     * 删除用户信息 ()<br>
     * 
     * @param gids
     * @return
     */
    public ResultCodeVo deleteLoginUser(String[] gids) {

        String ip = getIp();
        UserInfoVo userInfoVo = getUserInfoVo();
        ShareUserLogin currentUser = shareUserLoginDao.getUser(userInfoVo.getUserCode());
        for (String gid : gids) {
            if (gid.equals(currentUser.getGid())) {
                return ResultCodeVo.errorCode("不能删除当前用户: " + userInfoVo.getUserName());
            }
        }
        try {
            if (gids != null && gids.length > 0) {
                for (String gid : gids) {
                    shareUserLoginDao.deleteLoginUser(gid);
                }

            }
            return ResultCodeVo.rightCode("删除成功");
        } catch (Exception e) {
            log.error("删除登录用户失败", e);
            return ResultCodeVo.errorCode("删除用户信息失败");
        }
    }

    /**
     * 修改登录用户信息 ()<br>
     * 
     * @param vo
     * @return
     */
    public ResultCodeVo updateLoginUser(String userName, String phone, String mail, String userCode) {

        String ip = getIp();
        ShareUserLogin loginUser = shareUserLoginDao.getUser(userCode);
        if (loginUser == null)
            return ResultCodeVo.errorCode("没有查到用户信息,更新失败");
        loginUser.setUserName(userName);
        loginUser.setMobilePhone(phone);
        loginUser.setMail(mail);
        loginUser.setModifyIP(ip);
        loginUser.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
        loginUser.setModifyPgm("loginService ");
        try {
            shareUserLoginDao.updateLoginUser(loginUser);
            return ResultCodeVo.rightCode("更新登录用户信息成功");
        } catch (Exception e) {
            return ResultCodeVo.errorCode("更新登录用户信息失败");
        }
    }

    /**
     * 修改密码 ()<br>
     * 
     * @param password 新密码
     * @param oldPwd 旧密码
     * @param userCodes
     * @return
     */
    public ResultCodeVo updatePwd(String password, String oldPwd, String userCodes) {

        String ip = getIp();
        String newPwd = MD5.calcMD5(password);
        /*
         * if(!loginUser.getOrgCode().equals(CoreConstants.AreaOrgCode)){
         * if(!loginUser.getLoginPwd().equals(MD5.calcMD5(oldPwd)))return
         * ResultCodeVo.errorCode("原始密码输入错误"); }
         */
        try {
            String newCodes = "";
            if (StringUtils.isNotEmpty(userCodes)) {
                String codes[] = userCodes.split(",");
                for (String userCode : codes) {
                    newCodes = newCodes + "'" + userCode + "',";
                }
                newCodes = newCodes.substring(0, newCodes.length() - 1);
                shareUserLoginDao.updatePwd(newPwd, newCodes);
            }

            return ResultCodeVo.rightCode("修改成功！");
        } catch (Exception e) {
            return ResultCodeVo.errorCode("修改失败！");
        }

    }

    /**
     * 
     * ()<br>
     * 
     * @param userCodes
     * @param adminAddType 10区域 20校内 30 教研员
     * @return
     */
    public ResultCodeVo addAdmin(String[] userCodes, String adminAddType) {
        try {
            AuthRole role = authRoleDao.getRoleByCode(adminAddType);
            if (userCodes != null && userCodes.length > 0) {
                for (String userCode : userCodes) {
                    AuthUserRoleRef userRoleRef = new AuthUserRoleRef();
                    userRoleRef.setRoleId(role.getId());
                    userRoleRef.setUserCode(userCode);
                    authUserRoleRefDao.addUserRoleResRef(userRoleRef);
                }
            }
            return ResultCodeVo.rightCode("保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 更新用户状态 ()<br>
     * 
     * @param gid
     * @param state
     * @return
     */
    public ResultCodeVo updateState(String userCode, Integer status) {
        if (StringUtils.isEmpty(userCode))
            return ResultCodeVo.errorCode("请先选择用户！");

        UserInfoVo userInfoVo = getUserInfoVo();
        if (userInfoVo != null && userInfoVo.getUserCode().equals(userCode) && 0 == status.intValue())
            return ResultCodeVo.errorCode("用户已登录不可修改！");
        try {
            shareUserLoginDao.updateState(userCode, status);
            return ResultCodeVo.rightCode("更新成功！");
        } catch (Exception e) {
            log.error("更新用户状态失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("更新失败！");
        }
    }

    /**
     * 撤销管理员 ()<br>
     * 
     * @param userCode
     * @param type
     * @return
     */
    public ResultCodeVo reCallAdmin(String userCode, String type) {
        int count = shareUserLoginDao.recalAdmin(userCode, type);
        return 0 < count ? ResultCodeVo.rightCode("撤销成功！") : ResultCodeVo.errorCode("撤销失败！");
    }

    /**
     * 
     * (分页查询教研员列表信息)<br>
     * 
     * @param userName 姓名
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryEduStaffList(String userName, Integer page, Integer rows) throws Exception {
        return shareUserLoginDao.selectEduStaffList(userName, page, rows);
    }

    /**
     * 
     * (查询登录用户已设置的审核权限信息)<br>
     * 
     * @param userCode
     * @param loginAccount
     * @return
     * @throws Exception
     */
    @Override
    public UserAuthSettingVo querySettingInfo(String userCode, String loginAccount) throws Exception {
        UserAuthSettingVo settingVo = new UserAuthSettingVo();
        List<Map<String, Object>> mapSectionList = settingUserPriviledgeSectionDao.selectListByUserCode(userCode);
        List<SettingUserPriviledgeSectionVo> voSectionList = new ArrayList<SettingUserPriviledgeSectionVo>();
        if (mapSectionList != null && mapSectionList.size() > 0) {
            for (Map<String, Object> map : mapSectionList) {
                SettingUserPriviledgeSectionVo vo = SettingUserPriviledgeSectionVo.mapToVo(map);
                voSectionList.add(vo);
            }
            settingVo.setPriviledgeSectionVoList(voSectionList);
        }
        List<Map<String, Object>> mapGradeList = settingUserPriviledgeGradeDao.selectListByUserCode(userCode);
        List<SettingUserPriviledgeGradeVo> voGradeList = new ArrayList<SettingUserPriviledgeGradeVo>();
        if (mapGradeList != null && mapGradeList.size() > 0) {
            for (Map<String, Object> map : mapGradeList) {
                SettingUserPriviledgeGradeVo vo = SettingUserPriviledgeGradeVo.mapToVo(map);
                voGradeList.add(vo);
            }
            settingVo.setPriviledgeGradeVoList(voGradeList);
        }
        List<Map<String, Object>> mapSubjectList = settingUserPriviledgeSubjectDao.selectListByUserCode(userCode);
        List<SettingUserPriviledgeSubjectVo> voSubjectList = new ArrayList<SettingUserPriviledgeSubjectVo>();
        if (mapSubjectList != null && mapSubjectList.size() > 0) {
            for (Map<String, Object> map : mapSubjectList) {
                SettingUserPriviledgeSubjectVo vo = SettingUserPriviledgeSubjectVo.mapToVo(map);
                voSubjectList.add(vo);
            }
            settingVo.setPriviledgeSubjectVoList(voSubjectList);
        }
        settingVo.setUserCode(userCode);
        settingVo.setLoginAccount(loginAccount);
        return settingVo;
    }

    /**
     * 
     * (教研员审核权限保存)<br>
     * 
     * @param loginAccount
     * @param priviledgeSectionCodes
     * @param priviledgeGradeCodes
     * @param priviledgeSubjectCodes
     * @param userCode
     * @return
     * @throws Exception
     */
    @Override
    public ResultCodeVo saveEduStaffAuthSet(String loginAccount, String priviledgeSectionCodes,
                    String priviledgeGradeCodes, String priviledgeSubjectCodes, String userCode) throws Exception {

        UserInfoVo userInfo = getUserInfoVo();
        String ip = getIp();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        if (StringUtils.isNotEmpty(priviledgeSectionCodes)) {
            String[] sectionCodes = priviledgeSectionCodes.split(",");
            // 先做删除操作
            settingUserPriviledgeSectionDao.deleteByUserCode(userCode);
            int i = 0;
            for (String sectionCode : sectionCodes) {
                i++;
                SettingUserPriviledgeSection entity = new SettingUserPriviledgeSection();
                entity.setUserCode(userCode);
                entity.setLoginAccount(loginAccount);
                entity.setSectionCode(sectionCode);
                entity.setDispOrder(i);
                entity.setCreator(userInfo.getUserName());
                entity.setCreateTime(currentTime);
                entity.setCreatorIP(ip);
                entity.setModifier(userInfo.getUserName());
                entity.setModifyTime(currentTime);
                entity.setModifierIP(ip);
                settingUserPriviledgeSectionDao.insert(entity);
            }
        }
        if (StringUtils.isNotEmpty(priviledgeSubjectCodes)) {
            String[] subjectCodes = priviledgeSubjectCodes.split(",");
            // 先做删除操作
            settingUserPriviledgeSubjectDao.deleteByUserCode(userCode);
            int i = 0;
            for (String subjectCode : subjectCodes) {
                i++;
                SettingUserPriviledgeSubject entity = new SettingUserPriviledgeSubject();
                entity.setUserCode(userCode);
                entity.setLoginAccount(loginAccount);
                String sectionCode = subjectCode.substring(0, 1);
                entity.setSectionCode(sectionCode);
                entity.setSubjectCode(subjectCode);
                entity.setDispOrder(i);
                entity.setCreator(userInfo.getUserName());
                entity.setCreateTime(currentTime);
                entity.setCreatorIP(ip);
                entity.setModifier(userInfo.getUserName());
                entity.setModifyTime(currentTime);
                entity.setModifierIP(ip);
                settingUserPriviledgeSubjectDao.insert(entity);
            }
        }
        if (StringUtils.isNotEmpty(priviledgeGradeCodes)) {
            String[] gradeCodes = priviledgeGradeCodes.split(",");
            // 先做删除操作
            settingUserPriviledgeGradeDao.deleteByUserCode(userCode);
            int i = 0;
            for (String gradeCode : gradeCodes) {
                i++;
                SettingUserPriviledgeGrade entity = new SettingUserPriviledgeGrade();
                entity.setUserCode(userCode);
                entity.setLoginAccount(loginAccount);
                entity.setGradeCode(gradeCode);
                String sectionCode = gradeCode.substring(0, 1);
                entity.setSectionCode(sectionCode);
                entity.setDispOrder(i);
                entity.setModifier(userInfo.getUserName());
                entity.setModifyTime(currentTime);
                entity.setModifierIP(ip);
                settingUserPriviledgeGradeDao.insert(entity);
            }
        }
        return ResultCodeVo.rightCode("保存成功！");
    }

    /**
     * 查询登录信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<LoginUserVo> getLoginList(String orgCode) {
        List<Map<String, Object>> mapList = shareUserLoginDao.getSimpleLoginList(orgCode);
        List<LoginUserVo> voList = LoginUserVo.mapListToVoList(mapList);
        return voList;
    }

    /**
     * 根据代理商的机构编码 查询代理商 和他下一级代理商的用户信息 ()<br>
     * 
     * @param orgCode 代理商机构编码
     * @return page
     */
    public Page getAgencyUserInfo(String orgCode, Integer page, Integer rows, Map<String, Object> param) {
        return shareUserLoginDao.getAgencyAdminInfo(orgCode, page, rows, param);
    }

    /**
     * 验证账号是否存在 ()<br>
     * 
     * @param account
     * @return
     */
    public long accountExitCheck(String account) {

        CompanyInfoVo company = getCompanyInfo();
        long exist = 0;
        if (SystemConfig.agentEnable) {
            exist = shareUserLoginDao.checkAgencyAccountExit(account, company.getDomainID());
        } else {
            exist = shareUserLoginDao.checkAccountExit(account);
        }
        return exist;
    }
}
