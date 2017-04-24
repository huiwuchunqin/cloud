package com.baizhitong.resource.dao.share.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.vo.share.ShareUserLoginVo;
import com.baizhitong.utils.StringUtils;

/**
 * 查询用户信息 ShareUserLoginDaoImpl
 * 
 * @author creator BZT 2016年1月22日 下午2:36:50
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ShareUserLoginDaoImpl extends BaseSqlServerDao<ShareUserLogin> implements ShareUserLoginDao {
    /**
     * 查询用户信息 ()<br>
     * 
     * @param loginAccount 登录名
     * @param loginPwd 登录密码
     * @param orgCode 机构编码
     * @return
     */
    public ShareUserLogin getUser(String loginAccount, String loginPwd, String orgCode) {
        /*
         * String sql=
         * "select * from share_login_user where loginAccount COLLATE Chinese_PRC_CS_AI =:loginAccount and loginPwd=:loginPwd"
         * ; Map<String, Object> sqlParam=new HashMap<String,Object>(); sqlParam.put("loginAccount",
         * loginAccount); sqlParam.put("loginPwd", loginPwd); return
         * super.findUniqueBySql(ShareUserLogin.class, sql, sqlParam);
         */
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("loginAccount", loginAccount);
        qr.andEqual("loginPwd", loginPwd);
        qr.andEqual("status", CoreConstants.USER_STATUS_EFFECTIVE);// 状态：有效
        if (StringUtils.isNotEmpty(orgCode)) {
            qr.andEqual("orgCode", orgCode);
        }
        return super.findUnique(qr);
    }

    /**
     * 查询账号信息 ()<br>
     * 
     * @param domainID 域id
     * @param loginAccount 登录账号
     * @param loginPwd 登录密码
     * @return
     */
    public List<Map<String, Object>> getAgencyUser(Integer domainID, String loginAccount, String loginPwd) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        sul.*  ");
        sql.append("    FROM");
        sql.append("        share_user_login sul  ");
        sql.append("    WHERE");
        sql.append("        sul.loginAccount =:loginAccount  ");
        sql.append("        AND sul.loginPwd =:loginPwd  ");
        sql.append("        AND sul.orgCode ");
        sql.append("        ");
        sql.append("    IN (");
        sql.append("        SELECT");
        sql.append("            orgCode   FROM");
        sql.append("                share_org   ");
        sql.append("            WHERE");
        sql.append("                domainID =:domainID  ");
        sql.append("        )");
        param.put("loginAccount", loginAccount);
        param.put("loginPwd", loginPwd);
        param.put("domainID", domainID);
        return super.findBySql(sql.toString(), param);

    }

    /**
     * 修改登录账号机构名称 ()<br>
     * 
     * @param orgCode
     * @param orgName
     * @return
     */
    public int updateSchoolName(String orgCode, String orgName) {
        String sql = "update share_user_login set orgName=:orgName where orgCode=:orgCode";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("orgName", orgName);
        return super.update(sql, sqlParam);
    }

    /**
     * 查询老师用户登录信息 ()<br>
     * 
     * @param roleId 用户角色
     * @param userName 用户姓名
     * @param loginAccount 登录账户
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page<Map<String, Object>> getLoginList(String roleId, String userName, String loginAccount, String orgName,
                    String orgCode, Integer pageSize, Integer pageNo) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        b.userName");
        sql.append("        ,b.gender");
        sql.append("        ,b.userCode");
        sql.append("        ,sul.loginAccount");
        sql.append("        ,sul.mobilePhone");
        sql.append("        ,sul.mail");
        sql.append("        ,sul.status ");
        sql.append("        ,sul.userRole ");
        sql.append("        ,so.orgName ");
        sql.append("    FROM");
        sql.append("        (SELECT");
        sql.append("            sut.userName");
        sql.append("            ,sut.status");
        sql.append("            ,sut.gender");
        sql.append("            ,sut.teacherCode as userCode ");
        sql.append("        FROM");
        sql.append("            share_user_teacher sut ");
        sql.append("        WHERE");
        sql.append("            sut.status=1 ");
        /*
         * sql.append("        UNION ALL "); sql.append("        select"); sql.append(
         * "            sus.userName"); sql.append( "            ,sus.status"); sql.append(
         * "            ,sus.gender"); sql.append("            ,sus.studentCode as userCode ");
         * sql.append( "        FROM"); sql.append("            share_user_student sus ");
         * sql.append("        WHERE"); sql.append("            sus.status=1");
         */
        sql.append("    ) b ");
        sql.append("    ");
        sql.append(" INNER JOIN ");
        sql.append("    share_user_login sul ");
        sql.append("        on b.userCode=sul.userCode");
        sql.append(" INNER JOIN ");
        sql.append("  share_org  so on sul.orgCode=so.orgCode  ");
        if (StringUtils.isNotEmpty(roleId) && !"0".equals(roleId)) {
            sql.append(" inner join auth_user_role_ref aurr on aurr.userCode=b.userCode and aurr.roleId=:roleId ");
            param.put("roleId", roleId);
        }
        sql.append(" where 1=1 ");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and b.userName like :userName");
            param.put("userName", "%" + userName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName  ");
            param.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and so.orgCode = :orgCode  ");
            param.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(loginAccount)) {
            sql.append(" and sul.loginAccount like :loginAccount");
            param.put("loginAccount", "%" + loginAccount.trim() + "%");
        }
        sql.append(" order by b.userName desc ");
        return super.queryDistinctPage(sql.toString(), param, pageNo, pageSize);

    }

    /**
     * 查询区域管理员 ()<br>
     * 
     * @param userName 登录名称
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page<Map<String, Object>> getAreaLoginList(String userName, Integer pageNo, Integer pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        sul.orgCode");
        sql.append("        ,sul.mail");
        sql.append("        ,sul.mobilePhone");
        sql.append("        ,sul.userCode");
        sql.append("        ,sul.userName");
        sql.append("        ,sul.userRole");
        sql.append("        ,sul.loginAccount");
        sql.append("        ,sul.status");
        sql.append("        ,sul.gid");
        sql.append("        ,sul.gender");
        sql.append("        ,sul.avatarsImg");
        sql.append("        ,sul.orgName");
        sql.append("        ,sul.modifyTime ");
        sql.append("        ,sut.userName as teacherName ");
        sql.append("    FROM");
        sql.append("        share_user_login AS sul ");
        sql.append("    INNER JOIN");
        sql.append("        auth_user_role_ref aurr ");
        sql.append("            ON aurr.userCode = sul.userCode ");
        sql.append("    INNER JOIN");
        sql.append("        auth_role arole ");
        sql.append("            ON arole.id = aurr.roleId ");
        sql.append(" left join share_user_teacher sut on sut.teacherCode=sul.userCode ");
        sql.append("    WHERE");
        sql.append("        arole.code = :roleCode and sul.status=1 ");
        param.put("roleCode", CoreConstants.USER_ROLE_AREA_ADMIN_CODE);
        /* param.put("userRole", CoreConstants.LOGIN_USER_ROLE_ADMIN); */
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and sul.userName like :userName ");
            param.put("userName", "%" + userName.trim() + "%");
        }
        sql.append(" order by sul.userName ");
        return super.queryDistinctPage(sql.toString(), param, pageNo, pageSize);
    }

    /**
     * 查询学校管理员 ()<br>
     * 
     * @param userName 用户姓名
     * @param orgName 机构名称
     * @param orgCode 机构编码
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     */
    public Page<Map<String, Object>> getSchoolLoginList(String userName, String orgName, String orgCode, Integer pageNo,
                    Integer pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        sul.orgCode");
        sql.append("        ,sul.mail");
        sql.append("        ,sul.mobilePhone");
        sql.append("        ,sul.userCode");
        sql.append("        ,sul.userName");
        sql.append("        ,sul.userRole");
        sql.append("        ,sul.gid");
        sql.append("        ,sul.loginAccount");
        sql.append("        ,sul.status");
        sql.append("        ,sul.gender");
        sql.append("        ,sul.avatarsImg");
        sql.append("        ,sul.orgName");
        sql.append("        ,sul.modifyTime");
        sql.append("        ,sut.userName as teacherName");
        /* sql.append("        ,scs.name as sectionName "); */
        sql.append("    FROM");
        sql.append("        share_user_login AS sul ");
        sql.append("        ");
        sql.append("    INNER JOIN");
        sql.append("        auth_user_role_ref aurr ");
        sql.append("            ON aurr.userCode = sul.userCode ");
        sql.append("            ");
        sql.append("    INNER JOIN");
        sql.append("        auth_role arole ");
        sql.append("            ON arole.id = aurr.roleId ");
        sql.append("            ");
        sql.append("    INNER JOIN");
        sql.append("        share_org so ");
        sql.append("            on so.orgCode=sul.orgCode ");
        sql.append("            ");
        sql.append("left join share_user_teacher sut on sut.teacherCode=sul.userCode ");
        /*
         * sql.append("    LEFT JOIN"); sql.append( "        share_org_section sos "); sql.append(
         * "            on sos.orgCode=so.orgCode "); sql.append("            " ); sql.append(
         * "    LEFT JOIN"); sql.append( "        share_code_section scs "); sql.append(
         * "            on scs.code=sos.sectionCode "); sql.append(
         * "            and scs.flagDelete=0 ");
         */
        sql.append("    WHERE");
        sql.append("        arole.code =:roleCode  and sul.status=1 ");
        param.put("roleCode", CoreConstants.USER_ROLE_SCHOOL_ADMIN_CODE);
        /* param.put("userRole", CoreConstants.LOGIN_USER_ROLE_ADMIN); */
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and so.orgCode=:orgCode ");
            param.put("orgCode", orgCode);
        }
        // 机构名称
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName ");
            param.put("orgName", "%" + orgName.trim() + "%");
        }
        // 用户姓名
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and sul.userName like :userName ");
            param.put("userName", "%" + userName.trim() + "%");
        }
        sql.append(" order by sul.orgName,sul.userName ");
        return super.queryDistinctPage(sql.toString(), param, pageNo, pageSize);

    }

    @Override
    public ShareUserLogin getUser(String userCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("userCode", userCode);
        return super.findUnique(qr);
    }

    /**
     * 新增登录用户 ()<br>
     * 
     * @param user
     */
    public boolean addLoginUser(ShareUserLogin user) {
        try {
            return super.saveOne(user);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改登录用户信息 ()<br>
     * 
     * @param loginUser
     */
    public void updateLoginUser(ShareUserLogin loginUser) throws Exception {
        super.update(loginUser);
    }

    /**
     * 查询登录用户 ()<br>
     * 
     * @param loginAccount
     * @param orgCode
     * @return
     */
    public List<ShareUserLogin> getLoginUser(String loginAccount, String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("loginAccount", loginAccount);
        qr.andEqual("status", 1);
        if (StringUtils.isNotEmpty(orgCode)) {
            qr.andEqual("orgCode", orgCode);
        }
        return super.find(qr);
    }

    /**
     * 删除登录用户 ()<br>
     * 
     * @param gid
     */
    public void deleteLoginUser(String gid) {
        String sql = "update  share_user_login set status=0  where gid=?";
        super.update(sql, gid);
    }

    /**
     * 更新登录用户状态 ()<br>
     * 
     * @param userCode
     * @param status
     * @throws Exception
     */
    public void updateState(String userCode, Integer status) throws Exception {
        String sql = "update share_user_login set status=:status where userCode=:userCode";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("userCode", userCode);
        sqlParam.put("status", status);
        super.update(sql, sqlParam);
    }

    /**
     * 查询登录账户相同的账号 ()<br>
     * 
     * @param orgCode
     * @param loginAccount
     * @return
     */
    public List<Map<String, Object>> getSameAccount(String orgCode, String loginAccount) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        * ");
        sql.append("    FROM");
        sql.append("        share_user_login ");
        sql.append("    WHERE");
        sql.append("        loginAccount=:loginAccount");
        sqlParam.put("loginAccount", loginAccount.toLowerCase());
        sql.append("    and status=1 ");
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询机构所有的登录名 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getOrgAccountName(String orgCode) {
        String sql = "select loginAccount from share_user_login where orgCode=:orgCode and  status=1";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgCode", orgCode);
        return super.findBySql(sql, param);
    }

    /**
     * 查询机构所有的登录名 ()<br>
     * 
     * @param domainID 域名id
     * @return
     */
    public List<Map<String, Object>> getSameDomainAccountName(Integer domainID) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        A.loginAccount ");
        sql.append("    FROM");
        sql.append("        share_user_login a ");
        sql.append("    WHERE");
        sql.append("        a.orgCode ");
        sql.append("        ");
        sql.append("    IN (");
        sql.append("        SELECT");
        sql.append("            orgCode   FROM");
        sql.append("                share_org   ");
        sql.append("            WHERE");
        sql.append("                domainID =:domainID  ");
        sql.append("        )");
        sql.append(" and    status=1 ");
        param.put("domainID", domainID);
        return super.findBySql(sql.toString(), param);
    }

    /**
     * 删除登录信息 ()<br>
     * 
     * @param userCode
     * @return
     */
    public int deleteLoginUserByUserCode(String userCode) {
        String sql = "update  share_user_login set status=0  where userCode=?";
        return super.update(sql, userCode);
    }

    /**
     * 撤销管理员 ()<br>
     * 
     * @param userCode
     * @param type
     */
    public int recalAdmin(String userCode, String type) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append(" DELETE  ");
        sql.append("    FROM");
        sql.append("        auth_user_role_ref  ");
        sql.append("    WHERE");
        sql.append("        userCode =:userCode  ");
        sql.append("        AND roleId = (");
        sql.append("            SELECT");
        sql.append("                id   ");
        sql.append("            FROM");
        sql.append("                auth_role   ");
        sql.append("            WHERE");
        sql.append("                code = :type  ");
        sql.append("        )");
        param.put("userCode", userCode);
        param.put("type", type);
        return super.update(sql.toString(), param);
    }

    /**
     * 通过guid查询登录信息 ()<br>
     * 
     * @param guid
     * @return
     */
    public ShareUserLogin getByGuid(String guid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("cd_guid", guid);
        qr.andEqual("status", 1);
        return super.findUnique(qr);
    }

    /**
     * 查询机构登录用户信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserLogin> getOrgLoginList(String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(orgCode)) {
            qr.andEqual("orgCode", orgCode);
        }
        return super.find(qr);
    }

    /**
     * 保存或新增登录信息 ()<br>
     * 
     * @param loginList
     * @return
     */
    public int saveLoginList(List<ShareUserLogin> loginList) {
        try {
            return super.saveAll(loginList);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过guid更新登录用户信息 ()<br>
     * 
     * @param userLoginVo
     * @return
     */
    public int updateByCdguid(ShareUserLoginVo userLoginVo) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("update");
        sql.append("        share_user_login  ");
        sql.append("    SET");
        /* sql.append("        loginAccount=:loginAccount, "); */
        sql.append("        orgCode=:orgCode");
        sql.append("        ,orgName=:orgName");
        sql.append("        ,gender=:gender");
        sql.append("        ,userName=:userName");
        sql.append("        ,modifyIP=:modifyIP");
        sql.append("        ,modifyPgm=:modifyPgm");
        sql.append("        ,modifyTime=:modifyTime");
        sql.append("        ,userRole=:userRole");
        sql.append("        ,avatarsImg=:avatarsImg  ");
        sql.append("    WHERE");
        sql.append("        cd_guid=:cd_guid");
        sqlParam.put("loginAccount", userLoginVo.getLoginAccount());
        sqlParam.put("orgCode", userLoginVo.getOrgCode());
        sqlParam.put("orgName", userLoginVo.getOrgName());
        sqlParam.put("gender", userLoginVo.getGender());
        sqlParam.put("userName", userLoginVo.getUserName());
        sqlParam.put("modifyIP", userLoginVo.getModifyIP());
        sqlParam.put("modifyPgm", userLoginVo.getModifyPgm());
        sqlParam.put("modifyTime", userLoginVo.getModifyTime());
        sqlParam.put("userRole", userLoginVo.getUserRole());
        sqlParam.put("avatarsImg", userLoginVo.getAvatarsImg());
        sqlParam.put("cd_guid", userLoginVo.getCd_guid());
        return super.update(sql.toString(), sqlParam);

    }

    /**
     * 通过guid更新登录用户信息 ()<br>
     * 
     * @param userLoginVo
     * @return
     */
    public int updateTeacherLoginByCdguid(ShareUserLoginVo userLoginVo) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("update");
        sql.append("        share_user_login  ");
        sql.append("    SET");
        sql.append("        loginAccount=:loginAccount ");
        /* sql.append("        ,userCode=:userCode"); */
        sql.append("        ,orgCode=:orgCode");
        sql.append("        ,orgName=:orgName");
        sql.append("        ,gender=:gender");
        sql.append("        ,userName=:userName");
        sql.append("        ,modifyIP=:modifyIP");
        sql.append("        ,modifyPgm=:modifyPgm");
        sql.append("        ,modifyTime=:modifyTime");
        sql.append("        ,userRole=:userRole");
        sql.append("        ,avatarsImg=:avatarsImg  ");
        sql.append("        ,mail=:mail  ");
        sql.append("    WHERE");
        sql.append("        cd_guid=:cd_guid");
        sqlParam.put("userCode", userLoginVo.getUserCode());
        sqlParam.put("loginAccount", userLoginVo.getLoginAccount());
        sqlParam.put("orgCode", userLoginVo.getOrgCode());
        sqlParam.put("orgName", userLoginVo.getOrgName());
        sqlParam.put("gender", userLoginVo.getGender());
        sqlParam.put("mail", userLoginVo.getMail());
        sqlParam.put("userName", userLoginVo.getUserName());
        sqlParam.put("modifyIP", userLoginVo.getModifyIP());
        sqlParam.put("modifyPgm", userLoginVo.getModifyPgm());
        sqlParam.put("modifyTime", userLoginVo.getModifyTime());
        sqlParam.put("userRole", userLoginVo.getUserRole());
        sqlParam.put("avatarsImg", userLoginVo.getAvatarsImg());
        sqlParam.put("cd_guid", userLoginVo.getCd_guid());
        return super.update(sql.toString(), sqlParam);

    }

    /**
     * 通过guid更新登录用户信息 ()<br>
     * 
     * @param userLoginVo
     * @return
     */
    public int updateStudentLoginByCdguid(ShareUserLoginVo userLoginVo) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("update");
        sql.append("        share_user_login  ");
        sql.append("    SET");
        sql.append("        loginAccount=:loginAccount ");
        /* sql.append("        ,userCode=:userCode"); */
        sql.append("        ,mail=:mail");
        sql.append("        ,orgCode=:orgCode");
        sql.append("        ,orgName=:orgName");
        sql.append("        ,gender=:gender");
        sql.append("        ,userName=:userName");
        sql.append("        ,modifyIP=:modifyIP");
        sql.append("        ,modifyPgm=:modifyPgm");
        sql.append("        ,modifyTime=:modifyTime");
        sql.append("        ,userRole=:userRole");
        sql.append("        ,avatarsImg=:avatarsImg  ");
        sql.append("    WHERE");
        sql.append("        cd_guid=:cd_guid");
        sqlParam.put("loginAccount", userLoginVo.getLoginAccount());
        sqlParam.put("userCode", userLoginVo.getUserCode());
        sqlParam.put("orgCode", userLoginVo.getOrgCode());
        sqlParam.put("orgName", userLoginVo.getOrgName());
        sqlParam.put("gender", userLoginVo.getGender());
        sqlParam.put("userName", userLoginVo.getUserName());
        sqlParam.put("modifyIP", userLoginVo.getModifyIP());
        sqlParam.put("modifyPgm", userLoginVo.getModifyPgm());
        sqlParam.put("modifyTime", userLoginVo.getModifyTime());
        sqlParam.put("userRole", userLoginVo.getUserRole());
        sqlParam.put("avatarsImg", userLoginVo.getAvatarsImg());
        sqlParam.put("cd_guid", userLoginVo.getCd_guid());
        sqlParam.put("mail", userLoginVo.getMail());
        return super.update(sql.toString(), sqlParam);

    }

    /**
     * 学生插入登录表 ()<br>
     * 
     * @param shareUserLogin
     */
    public void insertStudentShareUserLogin(ShareUserLogin shareUserLogin) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("       share_user_login");
        sql.append("        (    gid,    orgCode,    orgName,    OrgCodeOut,    certificateType,    certificateNo,    mail,    mobilePhone,    userCode,    userName,    uniqueCodeOrg,    userRole,    loginAccount,    loginPwd,    status,    gender,    avatarsImg,      modifyPgm,    modifyTime,    modifyIP,    cd_guid   )   values (");
        sql.append("            :gid");
        sql.append("            ,:orgCode");
        sql.append("            ,:orgName");
        sql.append("            ,:OrgCodeOut");
        sql.append("            ,:certificateType");
        sql.append("            ,:certificateNo");
        sql.append("            ,:mail");
        sql.append("            ,:mobilePhone");
        sql.append("            ,:studentCode ");
        sql.append("           ,:userName,    :uniqueCodeOrg,    :userRole,    :loginAccount,    :loginPwd,    :status,    :gender,    :avatarsImg,    :modifyPgm,    :modifyTime,    :modifyIP,    :cd_guid");
        sql.append("            )");
        sqlParam.put("gid", UUID.randomUUID().toString());
        sqlParam.put("orgCode", shareUserLogin.getOrgCode());
        sqlParam.put("orgName", shareUserLogin.getOrgName());
        sqlParam.put("OrgCodeOut", "");
        sqlParam.put("certificateType", "");
        sqlParam.put("certificateNo", "");
        sqlParam.put("mail", shareUserLogin.getMail());
        sqlParam.put("mobilePhone", shareUserLogin.getMobilePhone());
        sqlParam.put("userName", shareUserLogin.getUserName());
        sqlParam.put("uniqueCodeOrg", "");
        sqlParam.put("userRole", CoreConstants.USER_ROLE_STUDENT);
        sqlParam.put("loginAccount", shareUserLogin.getLoginAccount());
        sqlParam.put("loginPwd", shareUserLogin.getLoginPwd());
        sqlParam.put("status", 1);
        sqlParam.put("studentCode", shareUserLogin.getUserCode());
        sqlParam.put("gender", shareUserLogin.getGender());
        sqlParam.put("avatarsImg", shareUserLogin.getAvatarsImg());
        sqlParam.put("modifyPgm", shareUserLogin.getModifyPgm());
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifyIP", shareUserLogin.getModifyIP());
        sqlParam.put("cd_guid", shareUserLogin.getCd_guid());
        super.update(sql.toString(), sqlParam);
    }

    /**
     * 学生插入登录表 ()<br>
     * 
     * @param shareUserLogin
     */
    public void insertTeacherShareUserLogin(ShareUserLoginVo shareUserLogin) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("       share_user_login");
        sql.append("        (    gid,    orgCode,    orgName,    OrgCodeOut,    certificateType,    certificateNo,    mail,    mobilePhone,    userCode,    userName,    uniqueCodeOrg,    userRole,    loginAccount,    loginPwd,    status,    gender,    avatarsImg,      modifyPgm,    modifyTime,    modifyIP,    cd_guid   )   values(");
        sql.append("            :gid");
        sql.append("            ,:orgCode");
        sql.append("            ,:orgName");
        sql.append("            ,:OrgCodeOut");
        sql.append("            ,:certificateType");
        sql.append("            ,:certificateNo");
        sql.append("            ,:mail");
        sql.append("            ,:mobilePhone");
        sql.append("            ,:teacherCode ");
        sql.append("           ,:userName,    :uniqueCodeOrg,    :userRole,    :loginAccount,    :loginPwd,    :status,    :gender,    :avatarsImg,    :modifyPgm,    :modifyTime,    :modifyIP,    :cd_guid");
        sql.append("            )");
        sqlParam.put("gid", UUID.randomUUID().toString());
        sqlParam.put("orgCode", shareUserLogin.getOrgCode());
        sqlParam.put("orgName", shareUserLogin.getOrgName());
        sqlParam.put("OrgCodeOut", "");
        sqlParam.put("certificateType", "");
        sqlParam.put("certificateNo", "");
        sqlParam.put("mail", shareUserLogin.getMail());
        sqlParam.put("mobilePhone", shareUserLogin.getMobilePhone());
        sqlParam.put("userName", shareUserLogin.getUserName());
        sqlParam.put("uniqueCodeOrg", "");
        sqlParam.put("teacheCode", shareUserLogin.getUserCode());
        sqlParam.put("userRole", CoreConstants.USER_ROLE_TEACHER);
        sqlParam.put("loginAccount", shareUserLogin.getLoginAccount());
        sqlParam.put("loginPwd", shareUserLogin.getLoginPwd());
        sqlParam.put("status", 1);
        sqlParam.put("teacherCode", shareUserLogin.getUserCode());
        sqlParam.put("gender", shareUserLogin.getGender());
        sqlParam.put("avatarsImg", shareUserLogin.getAvatarsImg());
        sqlParam.put("modifyPgm", shareUserLogin.getModifyPgm());
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifyIP", shareUserLogin.getModifyIP());
        sqlParam.put("cd_guid", shareUserLogin.getCd_guid());
        super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (查询教研员列表信息)<br>
     * 
     * @param userName 姓名
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Map<String, Object>> selectEduStaffList(String userName, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        sul.orgCode");
        sql.append("        ,sul.mail");
        sql.append("        ,sul.mobilePhone");
        sql.append("        ,sul.userCode");
        sql.append("        ,sul.userName");
        sql.append("        ,sul.userRole");
        sql.append("        ,sul.loginAccount");
        sql.append("        ,sul.status");
        sql.append("        ,sul.gid");
        sql.append("        ,sul.gender");
        sql.append("        ,sul.avatarsImg");
        sql.append("        ,sul.orgName");
        sql.append("        ,sul.modifyTime ");
        sql.append("    FROM");
        sql.append("        share_user_login AS sul ");
        sql.append("    INNER JOIN");
        sql.append("        auth_user_role_ref aurr ");
        sql.append("            ON aurr.userCode = sul.userCode ");
        sql.append("    INNER JOIN");
        sql.append("        auth_role arole ");
        sql.append("            ON arole.id = aurr.roleId ");
        sql.append("    WHERE");
        sql.append("        arole.code = :roleCode and status=1  ");
        param.put("roleCode", CoreConstants.USER_ROLE_EDU_STAFF_CODE);
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and sul.userName like :userName ");
            param.put("userName", "%" + userName.trim() + "%");
        }
        sql.append(" order by sul.userName asc ");
        return super.queryDistinctPage(sql.toString(), param, page, rows);
    }

    /**
     * 批量更新 ()<br>
     * 
     * @param shareUserLoginList
     * @return
     */
    public int addLonginUserList(List<ShareUserLogin> shareUserLoginList) {
        try {
            return super.saveAll(shareUserLoginList);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询简单的登录列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getSimpleLoginList(String orgCode) {
        String sql = "select cd_guid,userCode,userName from share_user_login";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(orgCode)) {
            sql = sql + " where orgCode=:orgCode";
            sqlParam.put("orgCode", orgCode);
        }
        return super.findBySql(sql, sqlParam);
    }

    /**
     * 
     * (根据邮箱查询登录信息列表)<br>
     * 
     * @param mail
     * @return
     */
    @Override
    public List<ShareUserLogin> selectListByMail(String mail, String userCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("mail", mail);
        qr.andEqual("status", CoreConstants.USER_STATUS_EFFECTIVE);
        if (StringUtils.isNotEmpty(userCode)) {
            qr.andNotEqual("userCode", userCode);
        }
        return super.find(qr);
    }

    /**
     * 
     * (根据手机号查询登录信息列表)<br>
     * 
     * @param mobilePhone
     * @return
     */
    @Override
    public List<ShareUserLogin> selectListByMobilePhone(String mobilePhone, String userCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("mobilePhone", mobilePhone);
        qr.andEqual("status", CoreConstants.USER_STATUS_EFFECTIVE);
        if (StringUtils.isNotEmpty(userCode)) {
            qr.andNotEqual("userCode", userCode);
        }
        return super.find(qr);
    }

    /**
     * 更新密码 ()<br>
     * 
     * @param newPwd 新的密码
     * @param userCodes 改密码的用户
     */
    public void updatePwd(String newPwd, String userCodes) {
        String sql = "update share_user_login set loginPwd=:loginPwd,modifyTime=GETDATE() where userCode in ("
                        + userCodes + ")";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("userCodes", userCodes);
        sqlParam.put("loginPwd", newPwd);
        super.update(sql, sqlParam);
    }

    /**
     * 根据代理商的机构编码 查询代理商 和他下一级代理商的用户信息 ()<br>
     * 
     * @param orgCode 代理商机构编码
     * @return page
     */
    public Page getAgencyAdminInfo(String orgCode, Integer page, Integer rows, Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String orgName = MapUtils.getString(param, "orgName");
        String loginAccount = MapUtils.getString(param, "loginAccount");
        String userName = MapUtils.getString(param, "userName");
        sql.append("SELECT");
        sql.append("       c.*  ");
        sql.append("    FROM");
        sql.append("        (    SELECT");
        sql.append("            a.orgCode");
        sql.append("            ,org.orgName");
        sql.append("            ,a.mail");
        sql.append("            ,a.mobilePhone");
        sql.append("            ,a.userCode");
        sql.append("            ,a.userName");
        sql.append("            ,a.loginAccount");
        sql.append("            ,a.loginPwd");
        sql.append("            ,a.status");
        sql.append("            ,a.gender");
        sql.append("            ,a.loginFailedCount");
        sql.append("            ,a.nextLoginPermitTime");
        sql.append("            ,a.loginDeniedReson");
        sql.append("            ,a.modifyPgm");
        sql.append("            ,a.modifyTime");
        sql.append("            ,a.modifyIP");
        sql.append("            ,a.flagMailValidate");
        sql.append("            ,a.flagPhoneValidate    ");
        sql.append("            ,sd.domainURL    ");
        sql.append("        FROM");
        sql.append("            share_user_login a    ");
        sql.append("LEFT JOIN");
        sql.append("    share_org org ");
        sql.append("        ON org.orgCode = a.orgCode    ");
        sql.append("        ");
        sql.append("LEFT JOIN");
        sql.append("    share_domain sd ");
        sql.append("        ON sd.id = org.domainID");
        sql.append("        WHERE");
        sql.append("            a.orgCode =:orgCode    ");
        sql.append("            AND a.status = :stateEffective and a.userRole=:roleAdmin   ");
        sql.append(" 		AND (a.standbyAccount!=:standbyAccount or a.standbyAccount is null) "); // 不查备用账号
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and a.userName like '%" + userName + "%'");
        }
        if (StringUtils.isNotEmpty(loginAccount)) {
            sql.append(" and a.loginAccount like '%" + loginAccount + "%'");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and a.orgName like '%" + orgName + "%'");
        }

        sql.append("        UNION");
        sql.append("        ALL     SELECT");
        sql.append("            b.orgCode");
        sql.append("            ,c.orgName");
        sql.append("            ,b.mail");
        sql.append("            ,b.mobilePhone");
        sql.append("            ,b.userCode");
        sql.append("            ,b.userName");
        sql.append("            ,b.loginAccount");
        sql.append("            ,b.loginPwd");
        sql.append("            ,b.status");
        sql.append("            ,b.gender");
        sql.append("            ,b.loginFailedCount");
        sql.append("            ,b.nextLoginPermitTime");
        sql.append("            ,b.loginDeniedReson");
        sql.append("            ,b.modifyPgm");
        sql.append("            ,b.modifyTime");
        sql.append("            ,b.modifyIP");
        sql.append("            ,b.flagMailValidate");
        sql.append("            ,b.flagPhoneValidate     ");
        sql.append("            ,sd.domainURL    ");
        sql.append("        FROM");
        sql.append("            share_user_login b     ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_org c ");
        sql.append("                ON b.orgCode = c.orgCode     ");
        sql.append("		LEFT JOIN");
        sql.append("    share_domain sd ");
        sql.append("        ON sd.id = c.domainID");
        sql.append("        WHERE");
        sql.append("            c.agency =:orgCode   and b.userRole=:roleAdmin   ");
        sql.append("            AND b.status = :stateEffective   ");

        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and b.userName like '%" + userName + "%'");
        }
        if (StringUtils.isNotEmpty(loginAccount)) {
            sql.append(" and b.loginAccount like '%" + loginAccount + "%'");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and b.orgName like '%" + orgName + "%'");
        }
        sql.append("    ) c  ");
        sql.append("    ");
        sql.append("ORDER BY");
        sql.append("    c.modifyTime  desc ");

        sqlParam.put("orgCode", orgCode);
        sqlParam.put("stateEffective", CoreConstants.USER_STATUS_EFFECTIVE);
        sqlParam.put("roleAdmin", CoreConstants.LOGIN_USER_ROLE_ADMIN);
        sqlParam.put("standbyAccount", CoreConstants.STANDBY_ACCOUNT_YES);
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 验证代理商模式下账户是否存在 ()<br>
     * 
     * @param account
     * @param domainID/
     * @return
     */
    public long checkAgencyAccountExit(String account, Integer domainID) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (1) ");
        sql.append("    FROM");
        sql.append("        share_user_login a ");
        sql.append("    WHERE");
        sql.append("        a.orgCode ");
        sql.append("        ");
        sql.append("    IN (");
        sql.append("        SELECT");
        sql.append("            orgCode   FROM");
        sql.append("                share_org   ");
        sql.append("            WHERE");
        sql.append("                domainID =:domainID  ");
        sql.append("        ) ");
        sql.append("        AND a.loginAccount =:loginAccount");
        sql.append("     and a.status=:status");
        param.put("loginAccount", account);
        param.put("domainID", domainID);
        param.put("status", CoreConstants.USER_STATUS_EFFECTIVE);
        return this.queryCount(sql.toString(), param);
    }

    /**
     * 验证账号在整个平台是否存在 ()<br>
     * 
     * @param account
     * @return
     */
    public long checkAccountExit(String account) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (1) ");
        sql.append("    FROM");
        sql.append("        share_user_login a ");
        sql.append("    WHERE");
        sql.append("        a.loginAccount =:loginAccount");
        sql.append("     and a.status=:status");
        param.put("loginAccount", account);
        param.put("status", CoreConstants.USER_STATUS_EFFECTIVE);
        return this.queryCount(sql.toString(), param);
    }

    public List<String> getMobileList() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        mobilePhone ");
        sql.append("    FROM");
        sql.append("        share_user_login ");
        sql.append("    WHERE");
        sql.append("        mobilePhone IS NOT NULL ");
        sql.append("        AND mobilePhone <> ''");
        sql.append("     and status="+CoreConstants.USER_STATUS_EFFECTIVE);
        return super.jdbcTemplateReadOnly().query(sql.toString(), new RowMapper<String>() {

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                // TODO Auto-generated method stub
                return rs.getString("mobilePhone");
            }

        });

    }

    public List<String> getEmailList() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        email ");
        sql.append("    FROM");
        sql.append("        share_user_login ");
        sql.append("    WHERE");
        sql.append("        mobilePhone IS NOT NULL ");
        sql.append("        AND mobilePhone <> ''");
        sql.append("     and status="+CoreConstants.USER_STATUS_EFFECTIVE);
        return super.jdbcTemplateReadOnly().query(sql.toString(), new RowMapper<String>() {

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                // TODO Auto-generated method stub
                return rs.getString("email");
            }

        });

    }
}
