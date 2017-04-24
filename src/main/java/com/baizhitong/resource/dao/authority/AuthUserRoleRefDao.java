package com.baizhitong.resource.dao.authority;

import java.util.List;

import com.baizhitong.resource.model.authority.AuthUserRoleRef;

/**
 * 用户角色关联信息数据接口
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午4:39:47
 */
public interface AuthUserRoleRefDao {

    /**
     * 根据角色ID获取用户角色关联信息集合
     * 
     * @param roleID 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午4:46:36
     */
    public List<AuthUserRoleRef> getUserRoleRefListByRoleID(String roleID) throws Exception;

    /**
     * 获取所有的用户角色信息
     * 
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午2:15:00
     */
    public List<AuthUserRoleRef> getAllUserRoleInfo() throws Exception;

    /**
     * 根据用户查询拥有的角色
     * 
     * @param userCode 用户代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午2:59:26
     */
    public List<AuthUserRoleRef> getRoleListByUserCode(String userCode) throws Exception;

    /**
     * 删除用户角色关联信息集合（批量删除）
     * 
     * @param list 集合
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午4:21:33
     */
    public int deleteUserRoleInfoList(List<AuthUserRoleRef> list) throws Exception;

    /**
     * 添加一个用户与角色关联信息
     * 
     * @param authUserRole 实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午4:30:13
     */
    public boolean addUserRoleResRef(AuthUserRoleRef authUserRole) throws Exception;

    /**
     * 删除一个用户与角色关联信息
     * 
     * @param authUserRole 实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午4:35:11
     */
    public int deleteAuthUserRoleRef(AuthUserRoleRef authUserRole) throws Exception;
}
