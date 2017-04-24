package com.baizhitong.resource.manage.user.service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareUserCommon;
import com.baizhitong.resource.model.vo.share.ShareUserCommonVo;
import com.baizhitong.resource.model.vo.share.ShareUserStudentVo;
import com.baizhitong.resource.model.vo.share.ShareUserTeacherVo;

/**
 * 用户信息查询接口
 * 
 * @author zhangqiang
 * @date 2015年12月16日 上午10:53:52
 */
public interface UserQueryService {

    /**
     * 获取用户分页信息
     * 
     * @param userRole
     * @param loginAccount
     * @param userName
     * @param rows
     * @param page
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月16日 下午1:19:50
     */
    public Page<ShareUserCommonVo> queryUserPageInfo(String userRole, String loginAccount, String userName,
                    Integer rows, Integer page) throws Exception;

    /**
     * 根据教师代码获取教师信息
     * 
     * @param teacherCode 教师代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月16日 下午3:08:52
     */
    public ShareUserTeacherVo getTeacherByTeacherCode(String teacherCode) throws Exception;

    /**
     * 根据学生代码获取学生信息
     * 
     * @param studentCode 学生代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月16日 下午5:22:59
     */
    public ShareUserStudentVo getStudentByStudentCode(String studentCode) throws Exception;

    /**
     * 根据userCode获取用户信息
     * 
     * @param userCode 用户代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午2:45:10
     */
    public ShareUserCommon getUserInfoByUserCode(String userCode) throws Exception;

}
