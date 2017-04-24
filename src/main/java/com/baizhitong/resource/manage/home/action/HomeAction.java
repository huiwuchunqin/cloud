package com.baizhitong.resource.manage.home.action;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.manage.platform.service.ISharePlatformService;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.utils.CookiesUtil;
import com.baizhitong.utils.StringUtils;

/**
 * 主页模块控制器
 * 
 * @author creator Cuidc 2015/12/14
 * @author updater
 */
@Controller
@RequestMapping("/manage")
public class HomeAction extends BaseAction {

    /** 平台信息接口 */
    @Autowired
    private ISharePlatformService          sharePlatformService;
    /** 教师数据接口 */
    private @Autowired ShareUserTeacherDao shareUserTeacherDao;

    private @Autowired ShareOrgDao         orgDao;

    /**
     * 获取后台主页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月19日 上午10:52:11
     */
    @RequestMapping("/index.html")
    public String main(HttpServletRequest request, ModelMap map) {
        UserInfoVo user =getUserInfoVo();
        String orgCode = getOrgCode() ;
        String token = CookiesUtil.loadCookie(BeanHelper.GLOBAL_USER_KEY, request);
        if (StringUtils.isNotEmpty(token)) {
            UserInfoVo userInfoVo = BeanHelper.getGlobalUserFromRedis(token);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            map.put("adminUser", userInfoVo);
        }
        map.addAttribute("role", BeanHelper.getAdminRoleFromSession(request));
        map.addAttribute("flagSuperAdmin", user.isFlagSuperAdmin());
        if (SystemConfig.agentEnable) {
            Map<String, Object> agency = new HashMap<String, Object>();
            agency = orgDao.getAgencyInfo(orgCode);
            map.put("agency", agency);
            return "/manage/agencyIndex.html";
        } else {
            try {
                SharePlatform platform = sharePlatformService.queryPlatformInfo();
                map.addAttribute("platform", platform);
            } catch (Exception e) {
                log.error("获取平台信息失败！", e);
                e.printStackTrace();
            }
            return "/manage/index.html";
        }

    }

    /**
     * 退出 ()<br>
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/exit.html")
    @ResponseBody
    public ResultCodeVo exit(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session == null) {
            CookiesUtil.removeAll(response, request);
            if (session != null) {
                session.invalidate();
            }
            if (SystemConfig.casEnable) {
                String formLogoutUrl = SystemConfig.plsServerUrl + "/logout";
                return ResultCodeVo.rightCode(formLogoutUrl, SystemConfig.casHost + "/cas/logout?service="
                                + URLEncoder.encode(SystemConfig.plsServerUrl + "/login/jumpToLogin?goto="));
            } else {
                return ResultCodeVo.rightCode("退出成功", "/manage/login.html");
            }
        } else {
            if (SystemConfig.casEnable) {
                BeanHelper.removeRedisUserInfo(request);
                CookiesUtil.removeAll(response, request);
                session.invalidate();
                String formLogoutUrl = SystemConfig.plsServerUrl + "/logout";
                return ResultCodeVo.rightCode(formLogoutUrl, SystemConfig.casHost + "/cas/logout?service="
                                + URLEncoder.encode(SystemConfig.plsServerUrl + "/login/jumpToLogin?goto="));
            } else {
                BeanHelper.removeRedisUserInfo(request);
                CookiesUtil.removeAll(response, request);
                session.invalidate();
                return ResultCodeVo.rightCode("退出成功", "/manage/login.html");
            }
        }
    }

    @RequestMapping("/inValidate.html")
    public void sesstionOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession sesstion = request.getSession();
        BeanHelper.removeRedisUserInfo(request);
        sesstion.invalidate();
        CookiesUtil.removeAll(response, request);
    }

    @RequestMapping("/toTest.html")
    public String toTest() {
        return "/manage/requireLogin.html";
    }
}