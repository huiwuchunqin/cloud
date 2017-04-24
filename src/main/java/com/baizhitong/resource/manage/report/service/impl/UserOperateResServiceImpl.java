package com.baizhitong.resource.manage.report.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.report.UserOperateResDao;
import com.baizhitong.resource.manage.report.service.UserOperateResService;

/**
 * 用户资源使用情况 UserOperateResServiceImpl TODO
 * 
 * @author creator gaow 2016年12月8日 下午4:43:17
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class UserOperateResServiceImpl implements UserOperateResService {
    private @Autowired UserOperateResDao userOperateResDao;

    /**
     * 查询老师资源使用情况 ()<br>
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    public Page teacherOperateResList(Map<String, Object> param, Integer page, Integer rows) {
        // TODO Auto-generated method stub
        return userOperateResDao.selectTeacherOp(param, page, rows);
    }

    /**
     * 查询学生资源使用情况 ()<br>
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    public Page studentOperateResList(Map<String, Object> param, Integer page, Integer rows) {
        // TODO Auto-generated method stub
        return userOperateResDao.selectStudentOp(param, page, rows);
    }

}
