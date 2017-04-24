package com.baizhitong.resource.common.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

/**
 * 接口基类
 * 
 * @author creator Cuidc 2015/12/08
 * @author updater
 */
public abstract class BaseService {
    public Logger log = LoggerFactory.getLogger(this.getClass());
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
     * 获取http响应对象
     * 
     * @return http响应对象
     */
    protected HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        if (requestAttributes != null) {
            return ((ServletRequestAttributes) requestAttributes).getResponse();
        } else {
            return null;
        }
    }

    /**
     * 获取ip地址
     * 
     * @param request 请求
     * @return
     * @author zhangqiang
     * @date 2015年12月22日 上午10:55:34
     */
    protected String getIp(HttpServletRequest request) {
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
    
    /**
     * 获取机构编码
     * ()<br>
     * @return
     */
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
     * 获取域名
     * ()<br>
     * @return
     */
    public String getDomainURL(){
        return  BeanHelper.getDomianURL(getRequest());
    }
    /**
     * 获取机构学年编码 ()<br>
     * 
     * @param request
     * @return
     */
    public static String getStudyYearCode() {
       return BeanHelper.getStudyYearCode();
    }
}
