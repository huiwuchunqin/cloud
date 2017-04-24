package com.baizhitong.resource.common.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.CookiesUtil;
import com.baizhitong.utils.StringUtils;

/**
 * 拦截器
 * 
 * @author creator cuidc 2015/12/08
 * @author updater
 */

public class GlobalHandlerInterceptorAdapter extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                    throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        String sessionId = CookiesUtil.loadCookie(BeanHelper.ADMIN_COOKIE_KEY, request);
        if (sessionId == null) {
            CookiesUtil.write(BeanHelper.ADMIN_COOKIE_KEY, request.getSession().getId(), response);
        }
        String requestURI = request.getRequestURI();
        if (requestURI.contains("error.html") || requestURI.startsWith("/check")) {
            return true;
        }
        UserInfoVo adminUserInfoVo = BeanHelper.getAdminUserFromCookie(request);
        // cas登录
        if (SystemConfig.casEnable) {
            if (requestURI.contains("login") || requestURI.contains("sessionOut")
                            || requestURI.contains("adminGlobalLogin")) {
                return true;
            }
            UserInfoVo userInfoVo = BeanHelper.getGlobalUserFromRedis(CookiesUtil.loadCookie(BeanHelper.GLOBAL_USER_KEY, request));
            if(adminUserInfoVo==null){
                response.sendRedirect("/manage/sessionOut.html");
                return false;
            }
            if(userInfoVo==null){
                 //后台自己偷偷登录不走域登录可以放行
                if(CoreConstants.LOGIN_TYPE_BACK.equals(adminUserInfoVo.getLoginType())){
                    return true;
                }else{
                    response.sendRedirect("/manage/sessionOut.html");
                    return false; 
                }
            }else{
                if(userInfoVo.getUserCode().equals(adminUserInfoVo.getUserCode())){
                    return true;
                }else{//前台与后台登录信息不一样了重新登录
                    response.sendRedirect("/manage/adminGlobalLogin.html?token="
                                    + CookiesUtil.loadCookie(BeanHelper.GLOBAL_USER_KEY, request));
                    return false;  
                }
            }
        } else {
                //不允许域登录
            if (requestURI.contains("/manage/adminGlobalLogin.html")) {
                response.sendRedirect("/manage/login.html");
                return false;
            }
            if (adminUserInfoVo == null) {
                if (requestURI.contains("adminGlobalLogin") || requestURI.contains("login")|| requestURI.contains("sessionOut")||requestURI.contains("getDomain")) {
                    return true;
                } else {
                    response.sendRedirect("/manage/sessionOut.html");
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    /**
     * 后处理（调用了Service并返回ModelAndView，但未进行页面渲染）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    ModelAndView modelAndView) throws Exception {
        if (null != modelAndView) {
            String viewName = modelAndView.getViewName();
            ModelMap map = modelAndView.getModelMap();
            viewName = StringUtils.trim(viewName);
            if (viewName.startsWith("redirect:") || viewName.startsWith("forward:"))
                return;
            map.put("imgHost", SystemConfig.getImgHost());
        }
    }
}
