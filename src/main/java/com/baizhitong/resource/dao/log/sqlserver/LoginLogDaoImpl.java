package com.baizhitong.resource.dao.log.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CodeConstants;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.dao.log.LoginLogDao;
import com.baizhitong.resource.model.log.LoginLog;
import com.baizhitong.utils.StringUtils;

/**
 * 登录日志接口 LoginLogDaoImpl TODO
 * 
 * @author creator BZT 2016年9月28日 下午2:53:10
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class LoginLogDaoImpl extends BaseSqlServerDao<LoginLog> implements LoginLogDao {
    /**
     * 查询登录日志 ()<br>
     * 
     * @param param
     * @param page
     * @param row
     * @return
     */
    public Page getLoginLogPage(Map<String, Object> param, Integer page, Integer row) {
        String orgName = MapUtils.getString(param, "orgName");
        String userName = MapUtils.getString(param, "userName");
        Integer userRole = MapUtils.getInteger(param, "userRole");
        Integer loginSuccess = MapUtils.getInteger(param, "loginSuccess");
        String deviceType = MapUtils.getString(param, "deviceType");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        a.id");
        sql.append("        ,a.orgCode");
        sql.append("        ,a.userRole");
        sql.append("        ,a.loginAccount");
        sql.append("        ,a.loginType");
        sql.append("        ,a.userCode");
        sql.append("        ,a.userName");
        sql.append("        ,a.sectionCode");
        sql.append("        ,a.gradeCode");
        sql.append("        ,a.loginTime");
        sql.append("        ,a.loginIP");
        sql.append("        ,a.deviceType");
        sql.append("        ,a.browserInfo");
        sql.append("        ,a.appVerInfo");
        sql.append("        ,a.deviceBrand");
        sql.append("        ,a.deviceOsVer");
        sql.append("        ,a.deviceOther");
        sql.append("        ,a.geoInfo");
        sql.append("        ,a.flagSuccessful");
        sql.append("        ,a.memo");
        sql.append("        ,a.modifier");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.modifierIP");
        sql.append("        ,d.orgName");
        sql.append("        ,b.name AS gradeName");
        sql.append("        ,c.name AS sectionName");
        sql.append("        ,CASE userRole ");
        sql.append("            WHEN '10' ");
        sql.append("        THEN");
        sql.append("            '老师' ");
        sql.append("            WHEN '20' ");
        sql.append("        THEN");
        sql.append("            '学生' ");
        sql.append("        END as userRoleName ");
        sql.append("        ,CASE flagSuccessful ");
        sql.append("            WHEN '1' ");
        sql.append("        THEN");
        sql.append("            '成功' ");
        sql.append("            else  ");
        sql.append("            '失败' ");
        sql.append("        END as loginSuccess ");
        sql.append("    FROM");
        sql.append("        login_log a ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade b ");
        sql.append("            ON a.gradeCode = b.code ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section c ");
        sql.append("            ON c.code = a.sectionCode");
        sql.append("    LEFT JOIN");
        sql.append("        share_org d ");
        sql.append("            ON a.orgCode = d.orgCode");
        sql.append(" where 1=1    ");
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("  and  d.orgName like '%" + orgName + "%'    ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("  and  a.userName like '%" + userName + "%'    ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and a.loginTime>=:startDate");
            sqlParam.put("startDate", startDate + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and a.loginTime<=:endDate ");
            sqlParam.put("endDate", endDate + "  23:59:59");
        }
        if (userRole != null) {
            if (userRole == 10) {
                sql.append(" and (a.userRole=10 or a.userRole=30) ");
            } else {
                sql.append(" and a.userRole=:userRole ");
                sqlParam.put("userRole", userRole);
            }
        }
        if (loginSuccess != null) {
            sql.append(" and a.flagSuccessful=:flagSuccessful ");
            sqlParam.put("flagSuccessful", loginSuccess);
        }
        if (StringUtils.isNotEmpty(deviceType)) {
            sql.append(" and a.deviceType=:deviceType ");
            sqlParam.put("deviceType", deviceType);
        }
        sql.append(" order by loginTime desc ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, row);
    }

       /**
        * 登录日志
        * ()<br>
        * @param userInfo
        * @param browserInfo
        * @param ip
        */
    public void insert(UserInfoVo userInfo,String browserInfo, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String,Object> sqlParam=new HashMap<String,Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        sharedb.dbo.login_log");
        sql.append("        ( orgCode, userRole, loginAccount, loginType, userCode, userName, sectionCode, gradeCode, loginTime, loginIP, deviceType, browserInfo, appVerInfo, deviceBrand, deviceOsVer, deviceOther, geoInfo, flagSuccessful, memo, modifier, modifyTime, modifierIP ) ");
        sql.append("    VALUES");
        sql.append("        ( :orgCode, :userRole, :loginAccount, :loginType, :userCode, :userName, :sectionCode, :gradeCode, :loginTime, :loginIP, :deviceType, :browserInfo, :appVerInfo, :deviceBrand, :deviceOsVer, :deviceOther, :geoInfo, :flagSuccessful, :memo, :modifier, :modifyTime, :modifierIP );");
        sqlParam.put("orgCode",userInfo.getOrgCode());
        sqlParam.put("userRole",userInfo.getUserRole());
        sqlParam.put("loginAccount",userInfo.getLoginAccount());
        sqlParam.put("loginType",CodeConstants.LOGIN_TYPE_ACCOUNT_PASSWORD);
        sqlParam.put("userCode",userInfo.getUserCode());
        sqlParam.put("userName",userInfo.getUserName());
        sqlParam.put("sectionCode",userInfo.getUserSectionCode());
        sqlParam.put("gradeCode",userInfo.getUserGradeCode());
        sqlParam.put("loginTime",new Timestamp(new Date().getTime()));
        sqlParam.put("loginIP",ip);
        sqlParam.put("deviceType",10);
        sqlParam.put("browserInfo",browserInfo);
        sqlParam.put("appVerInfo","");
        sqlParam.put("deviceBrand","");
        sqlParam.put("deviceOsVer","");
        sqlParam.put("deviceOther",null);
        sqlParam.put("geoInfo",null);
        sqlParam.put("memo","");
        sqlParam.put("flagSuccessful",1);
        sqlParam.put("modifier","System");
        sqlParam.put("modifyTime",new Timestamp(new Date().getTime()));
        sqlParam.put("modifierIP",ip);
        super.update(sql.toString(),sqlParam);
    }
}
