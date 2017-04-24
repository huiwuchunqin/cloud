package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareUserCommonDao;
import com.baizhitong.resource.model.share.ShareUserCommon;
import com.baizhitong.resource.model.vo.share.ShareUserCommonVo;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 用户用户通用数据接口实现
 * 
 * @author creator Cuidc 2015/12/15
 * @author updater
 */
@Service("ShareUserCommonDao")
public class ShareUserCommonSqlServerDaoImpl extends BaseSqlServerDao<ShareUserCommon> implements ShareUserCommonDao {

    /**
     * 获取用户信息
     * 
     * @param loginAccount 登录账号
     * @param password 登录密码
     * @return 信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectUserCommon(String loginAccount, String password) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT suc.gid, suc.platformCode, suc.orgCode, suc.userCode, suc.userName, suc.loginAccount, ");
        sql.append(" suc.uniqueCodeOrg, suc.academyCode, suc.majorCode, suc.userRole, suc.status, suc.gender, ");
        sql.append(" suc.introduction, suc.avatarsImg, suc.idTypeCode, suc.idNo, suc.idValidDate, suc.birthday, ");
        sql.append(" suc.birthPlace, suc.nativePlaceCode, suc.nationalityCode, suc.countryCode, suc.marriageStatusCode, ");
        sql.append(" suc.politicsStatusCode, suc.healthStatusCode, suc.religionCode, suc.bloodTypeCode, suc.address, ");
        sql.append(" suc.phone, suc.urgentPhone, suc.addressResidence, suc.residenceTypeCode, suc.flagFloatPopulation, ");
        sql.append(" suc.strength, suc.addressContact, suc.postcode, suc.homepageUrl, suc.englishName, suc.nameUsed, ");
        sql.append(" suc.email, suc.mobile, suc.qq, suc.modifyPgm, suc.modifyTime, suc.modifyIP, suc.sysVer ");
        sql.append(" FROM share_user_common suc ");
        sql.append(" WHERE suc.loginAccount = :loginAccount ");
        sql.append(" AND suc.password = :password ");

        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("loginAccount", loginAccount);
        sqlParam.put("password", password);

        // SQL执行
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 获取用户分页信息
     */
    @Override
    public Page<ShareUserCommonVo> queryUserPageInfo(String userRole, String loginAccount, String userName,
                    Integer page, Integer rows) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" select userCode,userName,userRole,loginAccount, ");
        sql.append(" gender,birthday,mobile,status ");
        sql.append(" FROM share_user_common ");
        sql.append(" where 1=1 ");
        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(userRole)) {
            sql.append(" and userRole=:userRole ");
            sqlParam.put("userRole", userRole);
        }
        if (!StringUtils.isEmpty(loginAccount)) {
            sql.append(" and loginAccount like '%" + loginAccount.trim() + "%' ");
        }
        if (!StringUtils.isEmpty(userName)) {
            sql.append(" and userName like '%" + userName.trim() + "%' ");
        }
        sql.append(" order by userCode asc ");
        return super.queryDistinctPage(sql.toString(), new RowMapperUtils(), sqlParam, page, rows);
    }

    /**
     * 获取用户信息
     * 
     * @param userCode 用户code
     * @return 对象
     */
    @Override
    public Map<String, Object> getShareUserCommon(String userCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT suc.gid, suc.platformCode, suc.orgCode, suc.userCode, suc.userName, suc.loginAccount, ");
        sql.append(" suc.uniqueCodeOrg, suc.academyCode, suc.majorCode, suc.userRole, suc.status, suc.gender, ");
        sql.append(" suc.introduction, suc.avatarsImg, suc.idTypeCode, suc.idNo, suc.idValidDate, suc.birthday, ");
        sql.append(" suc.birthPlace, suc.nativePlaceCode, suc.nationalityCode, suc.countryCode, suc.marriageStatusCode, ");
        sql.append(" suc.politicsStatusCode, suc.healthStatusCode, suc.religionCode, suc.bloodTypeCode, suc.address, ");
        sql.append(" suc.phone, suc.urgentPhone, suc.addressResidence, suc.residenceTypeCode, suc.flagFloatPopulation, ");
        sql.append(" suc.strength, suc.addressContact, suc.postcode, suc.homepageUrl, suc.englishName, suc.nameUsed, ");
        sql.append(" suc.email, suc.mobile, suc.qq, suc.modifyPgm, suc.modifyTime, suc.modifyIP, suc.sysVer ");
        sql.append(" FROM share_user_common suc ");

        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(userCode)) {
            sql.append(" WHERE suc.userCode = :userCode ");
            sqlParam.put("userCode", userCode);
        }
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询用户信息
     */
    @Override
    public Page<Map<String, Object>> listLoginUsers(Map<String, Object> param) throws Exception {
        // 从map中获取查询参数
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        String loginAccount = MapUtils.getString(param, "loginAccount");
        String roleId = MapUtils.getString(param, "roleId");
        String userName = MapUtils.getString(param, "userName");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // 拼接SQL语句
        StringBuilder sqlStr = new StringBuilder();
        sqlStr.append(" select suc.userName,suc.loginAccount,suc.status,suc.gender,suc.birthday ");
        sqlStr.append(" ,suc.mobile,suc.userCode ");
        sqlStr.append(" from share_user_common suc ");
        if (!StringUtils.isEmpty(roleId) && !"0".equals(roleId)) {
            sqlStr.append(" inner join auth_user_role_ref aurr on aurr.userCode=suc.userCode and aurr.roleId=:roleId ");
            sqlParam.put("roleId", Integer.parseInt(roleId));
        }
        sqlStr.append(" where suc.status=1 ");// 有效用户
        if (!StringUtils.isEmpty(loginAccount)) {
            sqlStr.append(" and suc.loginAccount like '%" + loginAccount.trim() + "%' ");
        }
        if (!StringUtils.isEmpty(userName)) {
            sqlStr.append(" and suc.userName like '%" + userName.trim() + "%' ");
        }
        sqlStr.append(" order by suc.userCode asc ");
        return super.queryDistinctPage(sqlStr.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 根据用户代码获取用户信息
     */
    @Override
    public ShareUserCommon getUserInfoByUserCode(String userCode) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("userCode", userCode);
        qr.andEqual("status", 1);// 有效
        return super.findUnique(qr);
    }
}
