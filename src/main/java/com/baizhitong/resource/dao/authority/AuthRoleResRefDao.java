package com.baizhitong.resource.dao.authority;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.authority.AuthRoleResRef;

/**
 * 角色资源关联信息数据接口
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午7:53:02
 */
public interface AuthRoleResRefDao {

    /**
     * 根据资源ID获取资源角色关系集合
     * 
     * @param resID 资源ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午7:58:34
     */
    public List<AuthRoleResRef> getRoleResListInfoByResID(Integer resID) throws Exception;

    /**
     * 根据角色ID获取分配给该角色的资源ID集合
     * 
     * @param roleId 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 上午10:25:15
     */
    public List<AuthRoleResRef> getResIDListByRoleId(String roleId) throws Exception;

    /**
     * 添加一个角色资源关联信息
     * 
     * @param authRoleResRef 实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 上午10:32:20
     */
    public boolean addAuthRoleResRef(AuthRoleResRef authRoleResRef) throws Exception;

    /**
     * 删除一个角色资源关联信息
     * 
     * @param authRoleResRef 实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 上午10:39:17
     */
    public int delAuthRoleResRef(AuthRoleResRef authRoleResRef) throws Exception;

    /**
     * 获取当前角色的资源信息
     * 
     * @param roleId 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月19日 上午11:48:57
     */
    public List<Map<String, Object>> getRoleResourceList(String roleId) throws Exception;
}
