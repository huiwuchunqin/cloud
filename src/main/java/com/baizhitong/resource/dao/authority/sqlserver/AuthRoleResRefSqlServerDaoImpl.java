package com.baizhitong.resource.dao.authority.sqlserver;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.authority.AuthRoleResRefDao;
import com.baizhitong.resource.model.authority.AuthRoleResRef;

/**
 * 角色资源关联信息数据接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午7:54:14
 */
@Service("authRoleResRefSqlServerDao")
public class AuthRoleResRefSqlServerDaoImpl extends BaseSqlServerDao<AuthRoleResRef> implements AuthRoleResRefDao {

    /**
     * 根据资源ID获取资源角色关系集合
     */
    @Override
    public List<AuthRoleResRef> getRoleResListInfoByResID(Integer resID) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("resId", resID);
        return super.find(qr);
    }

    /**
     * 根据角色ID获取分配给该角色的资源ID集合
     */
    @Override
    public List<AuthRoleResRef> getResIDListByRoleId(String roleId) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("roleId", roleId);
        return super.find(qr);
    }

    /**
     * 添加一个角色资源关联信息
     */
    @Override
    public boolean addAuthRoleResRef(AuthRoleResRef authRoleResRef) throws Exception {
        return super.saveOne(authRoleResRef);
    }

    /**
     * 删除一个角色资源关联信息
     */
    @Override
    public int delAuthRoleResRef(AuthRoleResRef authRoleResRef) throws Exception {
        return super.delete(authRoleResRef);
    }

    /**
     * 获取当前角色的资源信息
     */
    @Override
    public List<Map<String, Object>> getRoleResourceList(String roleCode) throws Exception {
        String sql = "select ar.id,ar.pid,ar.uri,ar.param,ar.name from auth_resource ar,auth_role_res_ref arrr,auth_role arole"
                        + " where ar.id=arrr.resId and arole.id=arrr.roleId and arole.code=? and ar.type=1 and ar.flagDelete=0 order by ar.dispOrder";
        return super.findBySql(sql, new Object[] { roleCode });
    }

}
