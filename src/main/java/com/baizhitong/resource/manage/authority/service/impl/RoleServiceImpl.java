package com.baizhitong.resource.manage.authority.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.authority.AuthRoleDao;
import com.baizhitong.resource.dao.authority.AuthUserRoleRefDao;
import com.baizhitong.resource.manage.authority.service.RoleService;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.authority.AuthUserRoleRef;
import com.baizhitong.resource.model.vo.authority.AuthRoleVo;

/**
 * 角色信息接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午2:20:45
 */
@Service("roleService")
public class RoleServiceImpl implements RoleService {

    /** 角色信息Dao */
    private @Autowired AuthRoleDao        authRoleDao;
    /** 用户角色关联信息Dao */
    private @Autowired AuthUserRoleRefDao authUserRoleRefDao;

    /**
     * 分页查询角色信息
     */
    @Override
    public Page<AuthRole> getRolePageInfo(Map<String, Object> param) throws Exception {
        return authRoleDao.getRolePageInfo(param);
    }

    /**
     * 根据角色ID获取角色信息
     */
    @Override
    public AuthRole getRoleById(Integer id) throws Exception {
        return authRoleDao.getRoleByID(id);
    }

    /**
     * 根据角色名称获取角色信息
     */
    @Override
    public AuthRole getRoleByName(String name) throws Exception {
        return authRoleDao.getRoleByName(name);
    }

    /**
     * 添加角色
     */
    @Override
    public boolean addRole(AuthRole role) throws Exception {
        return authRoleDao.addRole(role);
    }

    /**
     * 删除角色
     * 
     * @param id 角色ID
     * @return 0:删除出错 1:删除成功 2:该角色下存在用户，无法删除
     */
    @Override
    public int deleteRole(String id) throws Exception {
        List<AuthUserRoleRef> list = authUserRoleRefDao.getUserRoleRefListByRoleID(id);
        try {
            if (list == null || list.size() == 0) {// 该角色下没有用户
                int delSum = authRoleDao.deleteRole(id);
                if (delSum == 1) {
                    return 1;
                } else {
                    return 0;
                }
            } else {
                return 2;
            }
        } catch (Exception ex) {
            return 0;
        }
    }

    /**
     * 获取所有未删除的角色列表信息
     */
    @Override
    public List<AuthRoleVo> getRoleInfoList() throws Exception {
        List<AuthRoleVo> voList = new ArrayList<AuthRoleVo>();
        List<AuthRole> entityList = authRoleDao.getRoleInfoList();
        for (AuthRole entity : entityList) {
            AuthRoleVo vo = new AuthRoleVo(entity);
            voList.add(vo);
        }
        return voList;
    }

}
