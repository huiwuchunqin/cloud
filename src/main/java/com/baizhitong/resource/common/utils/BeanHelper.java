package com.baizhitong.resource.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baizhitong.encrypt.DESStaticKey;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.CookiesUtil;
import com.baizhitong.utils.JsonUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 一些对Session 或 Cookie 操作的方法
 */
public class BeanHelper {
    /** 本地session对应的cookie的值 */
    public final static String      ADMIN_COOKIE_KEY          = "BACKJSESSIONID";
    /** 全局用户信息key */
    public final static String      GLOBAL_USER_KEY           = "GLOBAL_USER_KEY";
    /**平台学期版本*/
    public final static String      PLATFORM_VER_KEY           = "platformInfoVer";
    /** Session加密key */
    public static final String      SESSION_ENCRY_KEY         = "!;+#%}~&/@?i^&-n";
    /** 用户信息 */
    public static final String      ADMIN_SESSION_USER_KEY    = "UserInfo";
    /** 机构信息信息 */
    public static final String      ADMIN_SESSION_COMPANY_KEY = "AccountCompanyInfo";
    /** 登录类型 */
    public static final String      LOGIN_TYPE                = "LOGIN_TYPE";
    /** cookie加密用户登录信息key */
    public static final String      COOKIE_USER_SECRET_KEY    = ")>}@A*:'!:`$1,{*";
    /** cookie 生命周期（30天） */
    public static final int         COOKIE_USER_SAVE_TIME     = 60 * 60 * 24 * 30;

    /*
     * private final static String SESSION_CACHE_REDIS_CLIENT = "SESSION_CACHE_REDIS_CLIENT";
     * private final static StringCacheClient redisClient = new
     * StringCacheClient(SESSION_CACHE_REDIS_CLIENT);
     */
    /**
     * 缓存连接池
     */
    private final static RediesUtil redisClient               = RediesUtil.getInstance();

    /**
     * 清空缓存服务器上的用户信息 ()<br>
     * 
     * @param key
     */
    public static void removeRedisUserInfo(HttpServletRequest request) {
        String key = CookiesUtil.loadCookie(GLOBAL_USER_KEY, request);
        String cookieId = CookiesUtil.loadCookie(ADMIN_COOKIE_KEY, request);
        if (StringUtils.isNotEmpty(key)) {
            redisClient.del(key);
        }
        redisClient.del(cookieId + "|" + ADMIN_SESSION_USER_KEY);
        redisClient.del(cookieId + "|" + ADMIN_SESSION_COMPANY_KEY);
    }

    /**
     * 把用户信息放入后台Cookie
     * 
     * @param request 请求
     * @param user 用户信息
     */
    public static void writeUserToAdminCookie(HttpServletRequest request, UserInfoVo user) {
        String cookieId = CookiesUtil.loadCookie(ADMIN_COOKIE_KEY, request);
        if (StringUtils.isEmpty(cookieId))
            cookieId = request.getSession().getId();
        writeUserToCookie(user, cookieId);
    }

    public static void writeCompanyToAdminCookie(HttpServletRequest request, CompanyInfoVo company) {
        String cookieId = CookiesUtil.loadCookie(ADMIN_COOKIE_KEY, request);
        if (StringUtils.isEmpty(cookieId))
            cookieId = request.getSession().getId();
        writeCompanyToCookie(company, cookieId);
    }

    /**
     * 把机构信息写入cookie ()<br>
     * 
     * @param user
     * @param cookieId
     */
    public static void writeUserToCookie(UserInfoVo user, String cookieId) {
        redisClient.set(cookieId + "|" + ADMIN_SESSION_USER_KEY,
                        DESStaticKey.encrypt(JsonUtils.ObjectToJson(user), SESSION_ENCRY_KEY));
    }

    public static void writeCompanyToCookie(CompanyInfoVo company, String cookieId) {
        redisClient.set(cookieId + "|" + ADMIN_SESSION_COMPANY_KEY,
                        DESStaticKey.encrypt(JsonUtils.ObjectToJson(company), SESSION_ENCRY_KEY));
    }

    /**
     * 从后台Cookie中获取用户信息
     * 
     * @param request 请求
     * @return
     */
    public static UserInfoVo getAdminUserFromCookie(HttpServletRequest request) {
        String cookieId = CookiesUtil.loadCookie(ADMIN_COOKIE_KEY, request);
        if (cookieId == null)
            return null;
        String value = redisClient.get(cookieId + "|" + ADMIN_SESSION_USER_KEY);
        if (value == null || cookieId == null) {
            return null;
        }
        UserInfoVo user = (UserInfoVo) JsonUtils.JsonToObject(DESStaticKey.decrypt(value, SESSION_ENCRY_KEY),
                        UserInfoVo.class);
        return user;
    }

    /**
     * 从后台Cookie中获取用户信息
     * 
     * @param request 请求
     * @return
     */
    public static UserInfoVo getGlobalUserFromRedis(String key) {
        if (StringUtils.isEmpty(key))
            return null;
        String value = redisClient.get(key);
        if (value == null) {
            return null;
        }
        UserInfoVo user = (UserInfoVo) JsonUtils.JsonToObject(value, UserInfoVo.class);
        return user;
    }

    /**
     * 从后台Cookie中获取机构信息
     * 
     * @param request 请求
     * @return
     */
    public static CompanyInfoVo getAdminCompanyFromCookie(HttpServletRequest request) {
        String cookieId = CookiesUtil.loadCookie(ADMIN_COOKIE_KEY, request);
        String value = redisClient.get(cookieId + "|" + ADMIN_SESSION_COMPANY_KEY);
        if (value == null || cookieId == null) {
            return null;
        }

        CompanyInfoVo company = (CompanyInfoVo) JsonUtils.JsonToObject(DESStaticKey.decrypt(value, SESSION_ENCRY_KEY),
                        CompanyInfoVo.class);
        return company;
    }

    /**
     * 获取机构编码 ()<br>
     * 
     * @param request
     * @return
     */
    public static String getOrgCode(HttpServletRequest request) {
        CompanyInfoVo companyInfoVo = getAdminCompanyFromCookie(request);
        return companyInfoVo == null ? "" : companyInfoVo.getOrgCode();
    }

    /**
     * 获取机构域名 ()<br>
     * 
     * @param request
     * @return
     */
    public static String getDomianURL(HttpServletRequest request) {
        CompanyInfoVo companyInfoVo = getAdminCompanyFromCookie(request);
        return companyInfoVo == null ? "" : companyInfoVo.getDomainURL();
    }

    /**
     * 获取机构域名id ()<br>
     * 
     * @param request
     * @return
     */
    public static Integer getDomianID(HttpServletRequest request) {
        CompanyInfoVo companyInfoVo = getAdminCompanyFromCookie(request);
        return companyInfoVo == null ? null : companyInfoVo.getDomainID();
    }

    /**
     * 获取请求信息 ()<br>
     * 
     * @return
     */
    private static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        } else {
            return null;
        }
    }

    /**
     * 获取机构学年编码 ()<br>
     * 
     * @param request
     * @return
     */
    public static String getStudyYearCode() {
        CompanyInfoVo companyInfoVo = getAdminCompanyFromCookie(getRequest());
        return companyInfoVo == null ? "" : companyInfoVo.getCurrentStudyYearCode();
    }

    /**
     * 获学年学期 ()<br>
     * 
     * @param request
     * @return
     */
    public static String getYearTermCode() {
        CompanyInfoVo companyInfoVo = getAdminCompanyFromCookie(getRequest());
        return companyInfoVo == null ? "" : companyInfoVo.getCurrentYearTermCode();
    }

    /**
     * 获取学期 ()<br>
     * 
     * @param request
     * @return
     */
    public static String getTermCode() {
        CompanyInfoVo companyInfoVo = getAdminCompanyFromCookie(getRequest());
        return companyInfoVo == null ? "" : companyInfoVo.getCurrentTermCode();
    }

    /**
     * 从session中获取角色信息
     * 
     * @param request 请求
     * @return
     */
    public static AuthRole getAdminRoleFromSession(HttpServletRequest request) {
        UserInfoVo user = getAdminUserFromCookie(request);
        AuthRole role = user.getRole();
        return role;
    }

    /**
     * 判断是否是区域管理员 ()<br>
     * 
     * @param request
     * @return
     */
    public static boolean isAreaAdmin(HttpServletRequest request) {
        UserInfoVo user = getAdminUserFromCookie(request);
        AuthRole role = user.getRole();
        if (role == null)
            return false;
        if (CoreConstants.USER_ROLE_AREA_ADMIN_CODE.equals(role.getCode())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否是校内管理员 ()<br>
     * 
     * @param request
     * @return
     */
    public static boolean isSchoolAdmin(HttpServletRequest request) {
        UserInfoVo user = getAdminUserFromCookie(request);
        AuthRole role = user.getRole();
        if (role == null)
            return false;
        if (CoreConstants.USER_ROLE_SCHOOL_ADMIN_CODE.equals(role.getCode())) {
            return true;
        } else {
            return false;
        }
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip != null && ip.indexOf(',') > 7)
            ip = ip.split(",")[0];
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 
     * (将平台信息写入Cookie)<br>
     * 
     * @param platformInfoVer
     */
    public static void writePlatformToCookie(String platformInfoVer) {
        redisClient.set(PLATFORM_VER_KEY, platformInfoVer);
    }

    /**
     * 
     * (判断是否是教研员登录)<br>
     * 
     * @param request
     * @return
     */
    public static boolean isEduStaff(HttpServletRequest request) {
        UserInfoVo user = getAdminUserFromCookie(request);
        AuthRole role = user.getRole();
        if (role == null)
            return false;
        if (CoreConstants.USER_ROLE_EDU_STAFF_CODE.equals(role.getCode())) {
            return true;
        } else {
            return false;
        }
    }
}
