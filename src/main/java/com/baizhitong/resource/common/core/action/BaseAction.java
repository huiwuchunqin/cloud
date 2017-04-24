package com.baizhitong.resource.common.core.action;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.StringUtils;

/**
 * 控制器基类
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
public abstract class BaseAction {
    /** 日志 */
    public Logger              log              = LoggerFactory.getLogger(this.getClass());
    protected SystemConfig     systemConfig     = SystemConfig.getInstance();
    /** Request获取请求之前的URL地址 */
    public static final String URL_SOURCE_WEB   = "web";                                   // 前台
    public static final String URL_SOURCE_ADMIN = "admin";                                 // 后台

    public String getIp(HttpServletRequest request) {
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

    public String getHostByRequest(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    }

    public String getHostNoPortByRequest(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName();
    }

    /**
     * 获取当前请求来源(前台还是后台)
     * 
     * @param request
     * @return
     */
    public String getUrlSource(HttpServletRequest request) {
        String url = request.getHeader("Referer");
        String source = "";
        url = url.replace(getHostByRequest(request), "").replace(getHostNoPortByRequest(request), "");
        source = url.substring(1, url.indexOf("/", 1));
        return source;
    }

    /**
     * 获取当前登陆用户，放入Object中(前后台通用)
     * 
     * @param request
     * @return
     *//*
       * public Object getUserObjectByRequest(HttpServletRequest request){ String
       * url_source=getUrlSource(request); if(url_source.equals(URL_SOURCE_WEB)){ return
       * BeanHelper.getWebUserFromCookie(request); }else if(url_source.equals(URL_SOURCE_ADMIN)){
       * return BeanHelper.getAdminUserFromCookie(request); } return null; }
       */

    /**
     * 共用异常打印
     * 
     * @param request request对象
     * @param msg 信息
     * @param e 异常对象
     */
    public void logError(HttpServletRequest request, String msg, Exception e) {
        log.error(StringUtils.format("{0}\n url:{1},params:{2}", msg, request.getRequestURI(),
                        JSONObject.toJSONString(request.getParameterMap())), e, e.getMessage());
    }
    /*	*//**
           * 跳转——额外携带上传必须参数
           * 
           * @param viewName 视图名
           * @param request 请求对象
           * @param mp model
           * @return
           *//*
             * public ModelAndView jumpToViewWithFileUploadParam(String viewName, HttpServletRequest
             * request , ModelMap mp){ UserInfoVo userInfo=BeanHelper.getWebUserFromCookie(request);
             * 
             * // if(userInfo != null){userInfo.getUserCode()
             * FileAboutHelper.addFileParams2Model("00000001",mp,true); //}
             * 
             * return jumpToViewWithDomainConfig(viewName,request,mp); }
             */

    /**
     * 跳转——额外携带DomainConfig
     * 
     * @param viewName 视图名
     * @param request 请求对象
     * @param mp model
     * @return
     */
    public ModelAndView jumpToViewWithDomainConfig(String viewName, HttpServletRequest request, ModelMap mp) {
        mp.addAttribute("datadepotHost", SystemConfig.getDatadepotHost());
        mp.addAttribute("videoHost", SystemConfig.getVideoHost());
        mp.addAttribute("docHost", SystemConfig.getDocHost());
        mp.addAttribute("imgHost", SystemConfig.getImgHost());
        mp.addAttribute("videoConvertHost", SystemConfig.getConvertHost());
        mp.addAttribute("webUrl", SystemConfig.getWebUrl());
        return new ModelAndView(viewName, mp);
    }
    /**
     * 获取http请求对象
     * 
     * @return http请求对象
     */
    protected HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getRequest();
        } else {
            return null;
        }
    }
    /**
     * 查询用户信息
     * ()<br>
     * @return
     */
    public UserInfoVo getUserInfoVo(){
        return BeanHelper.getAdminUserFromCookie(getRequest());
    }
    
    /**
     * 查询机构信息
     * ()<br>
     * @return
     */
    public CompanyInfoVo getCompanyInfo(){
        return BeanHelper.getAdminCompanyFromCookie(getRequest());
    }
    /**
     * 获取ip
     * ()<br>
     * @return
     */
    public String getIp(){
        return getIp(getRequest());
    }
    
    public String getOrgCode(){
        return BeanHelper.getOrgCode(getRequest());
    }
    /**
     * 是否是教研员
     * ()<br>
     * @return
     */
    public boolean isEduStaff(){
        return BeanHelper.isEduStaff(getRequest());
    }

    /**
     * 
     * (获取机构学年编码)<br>
     * @return
     */
    public String getStudyYearCode() {
       return BeanHelper.getStudyYearCode(); 
    }

}
