package com.baizhitong.resource.manage.authority.service;

import java.util.List;

import com.baizhitong.resource.model.authority.AuthRoleResRef;
import com.baizhitong.resource.model.vo.authority.TreeNodeVo;

/**
 * 角色资源接口
 * 
 * @author zhangqiang
 * @date 2015年12月18日 上午9:33:37
 */
public interface RoleResourceService {

    /**
     * 获取角色的资源信息列表
     * 
     * @param roleId 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 上午9:59:39
     */
    public List<TreeNodeVo> getResourceListByRoleID(String roleId) throws Exception;

    /**
     * 根据角色查询分配给该角色的资源ID
     * 
     * @param roleId 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 上午10:20:26
     */
    public List<AuthRoleResRef> getResourceInfoListByRoleId(String roleId) throws Exception;

    /**
     * 添加一个角色资源关联信息
     * 
     * @param authRoleResRef 实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 上午10:30:11
     */
    public boolean addAuthRoleResRef(AuthRoleResRef authRoleResRef) throws Exception;

    /**
     * 删除一个角色资源关联信息
     * 
     * @param authRoleResRef 实体
     * @return
     * @author zhangqiang
     * @date 2015年12月18日 上午10:35:23
     */
    public int delAuthRoleResRef(AuthRoleResRef authRoleResRef) throws Exception;

    /**
     * 获取普通登录用户的菜单资源
     * 
     * @param roleCode 角色Code
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月19日 上午11:42:38
     */
    public List<TreeNodeVo> getUserMenuInfo(String roleCode) throws Exception;

}
