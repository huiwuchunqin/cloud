package com.baizhitong.resource.dao.authority.sqlserver;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.authority.AuthUserRoleRefDao;
import com.baizhitong.resource.model.authority.AuthUserRoleRef;

/**
 * 用户角色关联信息数据接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午4:42:42
 */
@Service("authUserRoleRefSqlServerDao")
public class AuthUserRoleRefSqlServerDaoImpl extends BaseSqlServerDao<AuthUserRoleRef> implements AuthUserRoleRefDao {

    /**
     * 根据角色ID获取用户角色关联信息集合
     */
    @Override
    public List<AuthUserRoleRef> getUserRoleRefListByRoleID(String roleID) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("roleId", roleID);
        return super.find(qr);
    }

    /**
     * 获取所有的用户角色信息
     */
    @Override
    public List<AuthUserRoleRef> getAllUserRoleInfo() throws Exception {
        return super.getAll();
    }

    /**
     * 根据用户查询拥有的角色
     */
    @Override
    public List<AuthUserRoleRef> getRoleListByUserCode(String userCode) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("userCode", userCode);
        return super.find(qr);
    }

    /**
     * 删除用户角色关联信息集合（批量删除）
     */
    @Override
    public int deleteUserRoleInfoList(List<AuthUserRoleRef> list) throws Exception {
        return super.deleteAll(list);
    }

    /**
     * 添加一个用户与角色关联信息
     */
    @Override
    public boolean addUserRoleResRef(AuthUserRoleRef authUserRole) throws Exception {
        return super.saveOne(authUserRole);
    }

    /**
     * 删除一个用户与角色关联信息
     */
    @Override
    public int deleteAuthUserRoleRef(AuthUserRoleRef authUserRole) throws Exception {
        return super.delete(authUserRole);
    }
}
