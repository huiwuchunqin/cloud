package com.baizhitong.resource.dao.authority.sqlserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.authority.AuthRoleDao;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.utils.RowMapperUtils;

/**
 * 角色信息数据接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午2:43:27
 */
@Service("authRoleSqlServerDao")
public class AuthRoleSqlServerDaoImpl extends BaseSqlServerDao<AuthRole> implements AuthRoleDao {

    /**
     * 获取分页角色信息
     */
    @Override
    @SuppressWarnings({"unchecked"})
    public Page<AuthRole> getRolePageInfo(Map<String, Object> param) throws Exception {
        // 从map中获取查询参数
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        String name = MapUtils.getString(param, "name");
        StringBuffer sql=new StringBuffer();
        Map<String,Object> sqlParam=new HashMap<String, Object>();
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        sql.append(" select id,name,sysRole,memo ");
        sql.append(" from auth_role where flagDelete=:flagDelete "); 
        if (StringUtils.isNotEmpty(name)) {
            sql.append(" and name like :name ");
            sqlParam.put("name", "%"+name.trim()+"%");
        }
        sql.append(" order by id asc ");
        // 执行SQL语句
        return super.queryDistinctPage(sql.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 根据角色ID获取角色
     */
    @Override
    public AuthRole getRoleByID(Integer id) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", id);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 根据角色名称获取角色信息
     */
    @Override
    public AuthRole getRoleByName(String name) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("name", name.trim());
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 添加角色
     */
    @Override
    public boolean addRole(AuthRole role) throws Exception {
        return super.saveOne(role);
    }

    /**
     * 删除角色
     */
    @Override
    public int deleteRole(String id) throws Exception {
        String sql = "update auth_role set flagDelete=:flagDelete where id=:id";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("id", id);
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_YES);
        return super.update(sql, sqlParam);
    }

    /**
     * 获取所有未删除的角色列表信息
     */
    @Override
    public List<AuthRole> getRoleInfoList() throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.find(qr);
    }

    /**
     * 根据用户代码获取用户的所有角色
     */
    @Override
    public List<AuthRole> findUserRoleListByUserCode(String userCode) throws Exception {
        String sql = " select ar.id,ar.name,ar.code from auth_role ar,auth_user_role_ref aurr,share_user_login sul "
                        + " where aurr.roleId=ar.id and aurr.userCode=sul.userCode and sul.userCode=? and sul.status=1 ";
        List<Map<String, Object>> mapList = super.findBySql(sql, new Object[] { userCode });
        List<AuthRole> roleList = new ArrayList<AuthRole>();
        for (Map<String, Object> map : (List<Map<String, Object>>) mapList) {
            AuthRole role = new AuthRole();
            String id = MapUtils.getString(map, "id");
            String name = MapUtils.getString(map, "name");
            String code = MapUtils.getString(map, "code");
            role.setId(Integer.parseInt(id));
            role.setName(name);
            role.setCode(code);
            roleList.add(role);
        }
        return roleList;
    }

    /**
     * 根据角色编码查询角色 ()<br>
     * 
     * @param code
     * @return
     * @throws Exception
     */
    public AuthRole getRoleByCode(String code) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        qr.andEqual("code", code);
        return super.findUnique(qr);
    }

}
