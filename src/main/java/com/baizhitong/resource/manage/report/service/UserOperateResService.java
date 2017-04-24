package com.baizhitong.resource.manage.report.service;

import java.util.Map;

import com.baizhitong.common.Page;

public interface UserOperateResService {
    /**
     * 老师资源使用情况 ()<br>
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    Page teacherOperateResList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 学生资源使用情况 ()<br>
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    Page studentOperateResList(Map<String, Object> param, Integer page, Integer rows);
}
