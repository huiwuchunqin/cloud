package com.baizhitong.resource.manage.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.share.ShareUserCommonDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.manage.user.service.UserQueryService;
import com.baizhitong.resource.model.share.ShareUserCommon;
import com.baizhitong.resource.model.vo.share.ShareUserCommonVo;
import com.baizhitong.resource.model.vo.share.ShareUserStudentVo;
import com.baizhitong.resource.model.vo.share.ShareUserTeacherVo;

/**
 * 用户信息查询接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月16日 上午10:55:45
 */
@Service("userQueryService")
public class UserQueryServiceImpl implements UserQueryService {

    /** 用户通用信息Dao */
    private @Autowired ShareUserCommonDao  userDao;
    /** 教师信息Dao */
    private @Autowired ShareUserTeacherDao teacherDao;
    /** 学生信息Dao */
    private @Autowired ShareUserStudentDao studentDao;

    /**
     * 获取用户分页信息
     */
    @Override
    public Page<ShareUserCommonVo> queryUserPageInfo(String userRole, String loginAccount, String userName,
                    Integer rows, Integer page) throws Exception {
        return userDao.queryUserPageInfo(userRole, loginAccount, userName, page, rows);
    }

    /**
     * 根据教师代码获取教师信息
     */
    @Override
    public ShareUserTeacherVo getTeacherByTeacherCode(String teacherCode) throws Exception {
        Map<String, Object> map = teacherDao.getTeacherInfoByTeaCode(teacherCode);
        ShareUserTeacherVo teacher = new ShareUserTeacherVo();
        return teacher;
    }

    /**
     * 根据学生代码获取学生信息
     */
    @Override
    public ShareUserStudentVo getStudentByStudentCode(String studentCode) throws Exception {
        Map<String, Object> map = studentDao.getStudentByStudentCode(studentCode);
        ShareUserStudentVo student = new ShareUserStudentVo();
        return student;
    }

    /**
     * 根据用户代码获取用户信息
     */
    @Override
    public ShareUserCommon getUserInfoByUserCode(String userCode) throws Exception {
        return userDao.getUserInfoByUserCode(userCode);
    }

}
