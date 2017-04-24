package com.baizhitong.resource.manage.authority.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.authority.AuthUserRoleRef;

/**
 * 用户角色接口
 * 
 * @author zhangqiang
 * @date 2015年12月18日 上午11:25:10
 */
public interface UserRoleService {

    /**
     * 查询用户分页信息
     * 
     * @param param 查询参数
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 下午1:19:13
     */

    public Page<Map<String, Object>> listLoginUsers(String roleId, String userName, String loginAccount, String orgName,
                    String orgCode, Integer pageSize, Integer pageNo) throws Exception;

    /**
     * 得到带角色的用户分页信息
     * 
     * @param pageInfo page信息（不含角色）
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午2:00:29
     */
    public Page<Map<String, Object>> getUserInfoAddRole(Page<Map<String, Object>> pageInfo) throws Exception;

    /**
     * 得到所有的用户角色信息
     * 
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午2:08:34
     */
    public List<AuthUserRoleRef> getAllUserRoleInfo() throws Exception;

    /**
     * 根据用户查询拥有的角色
     * 
     * @param userCode 用户代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午2:55:20
     */
    public List<AuthUserRoleRef> getRoleListByUserCode(String userCode) throws Exception;

    /**
     * 删除用户角色关联信息集合（批量删除）
     * 
     * @param list
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午4:18:23
     */
    public int deleteUserRoleInfoList(List<AuthUserRoleRef> list) throws Exception;

    /**
     * 添加一个用户与角色关联信息
     * 
     * @param authUserRole 实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午4:27:38
     */
    public boolean addUserRoleResRef(AuthUserRoleRef authUserRole) throws Exception;

    /**
     * 删除一个用户与角色关联信息
     * 
     * @param authUserRole
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午4:32:21
     */
    public int deleteUserRoleResRef(AuthUserRoleRef authUserRole) throws Exception;

}
