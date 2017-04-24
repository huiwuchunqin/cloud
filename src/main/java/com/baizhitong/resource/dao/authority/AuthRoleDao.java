package com.baizhitong.resource.dao.authority;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.authority.AuthRole;

/**
 * 角色信息数据接口
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午2:41:10
 */
public interface AuthRoleDao {

    /**
     * 获取分页角色信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午2:59:04
     */
    public Page<AuthRole> getRolePageInfo(Map<String, Object> param) throws Exception;

    /**
     * 根据角色ID获取角色
     * 
     * @param id 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午3:27:58
     */
    public AuthRole getRoleByID(Integer id) throws Exception;

    /**
     * 根据角色名称获取角色信息
     * 
     * @param name 角色名称
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午3:47:39
     */
    public AuthRole getRoleByName(String name) throws Exception;

    /**
     * 根据角色编码获取角色 ()<br>
     * 
     * @param code 角色编码
     * @return
     * @throws Exception
     */
    public AuthRole getRoleByCode(String code) throws Exception;

    /**
     * 添加角色
     * 
     * @param role 角色实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午3:59:30
     */
    public boolean addRole(AuthRole role) throws Exception;

    /**
     * 删除角色
     * 
     * @param id 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午4:55:52
     */
    public int deleteRole(String id) throws Exception;

    /**
     * 获取所有未删除的角色列表信息
     * 
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午1:03:26
     */
    public List<AuthRole> getRoleInfoList() throws Exception;

    /**
     * 根据用户代码获取用户的所有角色
     * 
     * @param userCode 用户代码
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午5:51:35
     */
    public List<AuthRole> findUserRoleListByUserCode(String userCode) throws Exception;

}
