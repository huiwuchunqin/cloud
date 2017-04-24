package com.baizhitong.resource.manage.authority.service;

/**
 * 
 * 权限共通Service
 * 
 * @author creator ZhangQiang 2016年9月28日 下午3:17:55
 * @author updater
 *
 * @version 1.0.0
 */
public interface CommonService {

    /**
     * 
     * (获取用户可以审核的学段集合)<br>
     * 处理说明：拼接返回字符串，用逗号分隔
     * 
     * @param userCode 用户编码
     * @return
     */
    String queryUserAuthSectionCodes(String userCode);

    /**
     * 
     * (获取用户可以审核的学科集合)<br>
     * 处理说明：拼接返回字符串，用逗号分隔
     * 
     * @param userCode 用户编码
     * @return
     */
    String queryUserAuthSubjectCodes(String userCode);

    /**
     * 
     * (获取用户可以审核的年级集合)<br>
     * 处理说明：拼接返回字符串，用逗号分隔
     * 
     * @param userCode 用户编码
     * @return
     */
    String queryUserAuthGradeCodes(String userCode);
}
