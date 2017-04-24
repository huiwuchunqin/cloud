package com.baizhitong.resource.manage.log.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.UserInfoVo;

public interface ILoginLogService {
    /**
     * 插叙登录日志 ()<br>
     * 
     * @param sqlParam
     * @param page
     * @param row
     * @return
     */
    Page getLoginLog(Map<String, Object> sqlParam, Integer page, Integer row);

    /**
     * 格式化 ()<br>
     * 
     * @param list
     * @return
     */
    void format(List<Map<String, Object>> list);
    
}
