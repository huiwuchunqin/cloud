package com.baizhitong.resource.manage.authority.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.vo.authority.AuthRoleVo;

/**
 * 角色信息接口
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午2:19:29
 */
public interface RoleService {

    /**
     * 分页获取角色信息
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午2:55:02
     */
    public Page<AuthRole> getRolePageInfo(Map<String, Object> param) throws Exception;

    /**
     * 根据角色ID获取角色
     * 
     * @param id 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午3:25:35
     */
    public AuthRole getRoleById(Integer id) throws Exception;

    /**
     * 根据角色名称获取角色
     * 
     * @param name 角色名称
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午3:45:36
     */
    public AuthRole getRoleByName(String name) throws Exception;

    /**
     * 添加角色
     * 
     * @param role 角色实体
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午3:52:35
     */
    public boolean addRole(AuthRole role) throws Exception;

    /**
     * 删除角色
     * 
     * @param id 角色ID
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月17日 下午4:33:44
     */
    public int deleteRole(String id) throws Exception;

    /**
     * 获取所有未删除的角色列表信息
     * 
     * @return
     * @throws Exception
     * @author zhangqiang
     * @date 2015年12月18日 下午1:00:32
     */
    public List<AuthRoleVo> getRoleInfoList() throws Exception;

}
