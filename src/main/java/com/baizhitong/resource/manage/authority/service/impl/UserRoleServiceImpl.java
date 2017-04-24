package com.baizhitong.resource.manage.authority.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.authority.AuthRoleDao;
import com.baizhitong.resource.dao.authority.AuthUserRoleRefDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.manage.authority.service.UserRoleService;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.authority.AuthUserRoleRef;

/**
 * 用户角色接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月18日 上午11:26:53
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

    /** 用户通用信息Dao */
    private @Autowired ShareUserLoginDao  shareUserLoginDao;
    /** 角色信息Dao */
    private @Autowired AuthRoleDao        authRoleDao;
    /** 用户角色关联信息Dao */
    private @Autowired AuthUserRoleRefDao authUserRoleRefDao;

    /**
     * 查询用户信息
     */
    @Override
    public Page<Map<String, Object>> listLoginUsers(String roleId, String userName, String loginAccount, String orgName,
                    String orgCode, Integer pageSize, Integer pageNo) throws Exception {
        return shareUserLoginDao.getLoginList(roleId, userName, loginAccount, orgName, orgCode, pageSize, pageNo);
    }

    /**
     * 得到带角色的用户分页信息
     */
    @Override
    public Page<Map<String, Object>> getUserInfoAddRole(Page<Map<String, Object>> pageInfo) throws Exception {
        // 得到所有未删除的角色信息
        List<AuthRole> allRoleList = authRoleDao.getRoleInfoList();
        // 得到所有的用户角色关联信息
        List<AuthUserRoleRef> authUserRoleRefList = this.getAllUserRoleInfo();
        Map<Integer, Object> roleMap = new HashMap<Integer, Object>();// 角色键值对
        if (allRoleList != null && allRoleList.size() > 0) {
            for (int i = 0; i < allRoleList.size(); i++) {
                roleMap.put(allRoleList.get(i).getId(), allRoleList.get(i).getName());
            }
        }
        List<Map<String, Object>> mapList = pageInfo.getRows();
        // 遍历，if判断，累加角色roleMap.get(id)；
        for (int i = 0; i < mapList.size(); i++) {
            for (AuthUserRoleRef userRole : authUserRoleRefList) {
                if (userRole.getUserCode().equals(mapList.get(i).get("userCode").toString())) {
                    String role = MapUtils.getString(mapList.get(i), "role");
                    role = role == null ? "" : role;
                    if (StringUtils.isNotEmpty(role)) {
                        role = role + "," + roleMap.get(userRole.getRoleId());
                    } else {
                        role = role + roleMap.get(userRole.getRoleId());
                    }

                    mapList.get(i).put("role", role);
                }
            }
        }
        pageInfo.setRows(mapList);
        return pageInfo;
    }

    /**
     * 得到所有的用户角色信息
     */
    @Override
    public List<AuthUserRoleRef> getAllUserRoleInfo() throws Exception {
        return authUserRoleRefDao.getAllUserRoleInfo();
    }

    /**
     * 根据用户查询拥有的角色
     */
    @Override
    public List<AuthUserRoleRef> getRoleListByUserCode(String userCode) throws Exception {
        return authUserRoleRefDao.getRoleListByUserCode(userCode);
    }

    /**
     * 删除用户角色关联信息集合（批量删除）
     */
    @Override
    public int deleteUserRoleInfoList(List<AuthUserRoleRef> list) throws Exception {
        return authUserRoleRefDao.deleteUserRoleInfoList(list);
    }

    /**
     * 添加一个用户与角色关联信息
     */
    @Override
    public boolean addUserRoleResRef(AuthUserRoleRef authUserRole) throws Exception {
        return authUserRoleRefDao.addUserRoleResRef(authUserRole);
    }

    /**
     * 删除一个用户与角色关联信息
     */
    @Override
    public int deleteUserRoleResRef(AuthUserRoleRef authUserRole) throws Exception {
        return authUserRoleRefDao.deleteAuthUserRoleRef(authUserRole);
    }

}
