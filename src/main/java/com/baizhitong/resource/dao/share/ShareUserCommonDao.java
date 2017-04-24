package com.baizhitong.resource.dao.share;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareUserCommon;
import com.baizhitong.resource.model.vo.share.ShareUserCommonVo;

/**
 * 用户用户通用数据接口
 * 
 * @author creator Cuidc 2015/12/15
 * @author updater
 */
public interface ShareUserCommonDao {
    /**
     * 获取用户信息
     * 
     * @param loginAccount 登录账号
     * @param password 登录密码
     * @return 信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectUserCommon(String loginAccount, String password) throws Exception;

    /**
     * 获取用户分页信息
     * 
     * @param userRole 用户身份
     * @param loginAccount 登录账号
     * @param userName 用户姓名
     * @param page 页码
     * @param rows 页面大小
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月16日 下午1:23:35
     */
    public Page<ShareUserCommonVo> queryUserPageInfo(String userRole, String loginAccount, String userName,
                    Integer page, Integer rows) throws Exception;

    /**
     * 获取用户信息
     * 
     * @param userCode 用户code
     * @return 对象
     */
    public Map<String, Object> getShareUserCommon(String userCode);

    /**
     * 查询用户信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午1:23:59
     */
    public Page<Map<String, Object>> listLoginUsers(Map<String, Object> param) throws Exception;

    /**
     * 根据用户代码获取用户信息
     * 
     * @param userCode 用户代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午2:49:25
     */
    public ShareUserCommon getUserInfoByUserCode(String userCode) throws Exception;
}
