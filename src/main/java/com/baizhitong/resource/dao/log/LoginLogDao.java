package com.baizhitong.resource.dao.log;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.model.log.LoginLog;

public interface LoginLogDao {
    /**
     * 查询登录日志 ()<br>
     * 
     * @param param
     * @param page
     * @param row
     * @return
     */
    Page getLoginLogPage(Map<String, Object> param, Integer page, Integer row);
    /**
     * 登录日志
     * ()<br>
     * @param userInfo
     * @param browserInfo
     * @param ip
     */
 void insert(UserInfoVo userInfo,String browserInfo, String ip) ;

}
