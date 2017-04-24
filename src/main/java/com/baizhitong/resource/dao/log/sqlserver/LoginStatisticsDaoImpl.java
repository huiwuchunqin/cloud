package com.baizhitong.resource.dao.log.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.log.LoginStatisticsDao;
import com.baizhitong.utils.StringUtils;

@Repository
public class LoginStatisticsDaoImpl extends BaseSqlServerDao implements LoginStatisticsDao {

    @Override
    public Page getLoginStatistics(String orgName, Integer role, String startTime, String endTime, Integer pageNo,
                    Integer pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT");
        sql.append("        so.orgName AS 'orgName'");
        sql.append("        ,ll.orgCode AS 'orgCode'");
        sql.append("        ,SUM (   CASE deviceType   ");
        sql.append("            WHEN 10 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS PC,  SUM (   CASE deviceType   ");
        sql.append("            WHEN 20 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS 'androidPad',  SUM (   CASE deviceType   ");
        sql.append("            WHEN 21 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS 'ipad',  SUM (   CASE deviceType   ");
        sql.append("            WHEN 30 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS 'androidPhone',  SUM (   CASE deviceType   ");
        sql.append("            WHEN 31 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS 'iphone',  COUNT (*) AS total ");
        sql.append("    FROM");
        sql.append("        login_log ll ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org so ");
        sql.append("            ON so.orgCode = ll.orgCode ");
        sql.append("    WHERE");
        sql.append("        flagSuccessful=1 and  userCode IS NOT NULL ");
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        and orgName like :orgName ");
            params.put("orgName", "%" + orgName + "%");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        and ll.loginTime > :startTime ");
            params.put("startTime", startTime);
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        and ll.loginTime < :endTime ");
            params.put("endTime", endTime);
        }

        if (role != null) {
            if (role == 10) {
                sql.append("        AND (ll.userRole = 10 OR ll.userRole = 30) ");
            } else {
                sql.append("        AND ll.userRole = :role ");
                params.put("role", role);
            }  
        }

        sql.append("    GROUP BY");
        sql.append("        orgName");
        sql.append("        ,ll.orgCode ");
        sql.append("    order by COUNT(*) desc, orgName   ");

        return this.queryDistinctPage(sql.toString(), params, pageNo, pageSize);
    }

    @Override
    public Page getLoginStatisticsDetail(String orgCode, Integer role, String startTime, String endTime, Integer pageNo,
                    Integer pageSize) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        params.put("orgCode", orgCode);

        sql.append("SELECT");
        sql.append("        userName AS userName");
        sql.append("        ,userCode AS userCode");
        sql.append("        ,SUM (   CASE deviceType   ");
        sql.append("            WHEN 10 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS PC,  SUM (   CASE deviceType   ");
        sql.append("            WHEN 20 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS 'androidPad',  SUM (   CASE deviceType   ");
        sql.append("            WHEN 21 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS 'ipad',  SUM (   CASE deviceType   ");
        sql.append("            WHEN 30 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS 'androidPhone',  SUM (   CASE deviceType   ");
        sql.append("            WHEN 31 ");
        sql.append("        THEN");
        sql.append("            1   ");
        sql.append("            ELSE    0   ");
        sql.append("        END  ) AS 'iphone', COUNT(*) as total ");
        sql.append("    FROM");
        sql.append("        login_log ll ");
        sql.append("    WHERE");
        sql.append("        flagSuccessful = 1 ");
        sql.append("        and orgCode = :orgCode ");

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        and loginTime > :startTime ");
            params.put("startTime", startTime);
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        and loginTime < :endTime ");
            params.put("endTime", endTime);
        }

        if (role != null) {
            if (role == 10) {
                sql.append("        AND (ll.userRole = 10 OR ll.userRole = 30) ");
            } else {
                sql.append("        AND ll.userRole = :role ");
                params.put("role", role);
            }  
        }

        sql.append("        ");
        sql.append("    GROUP BY");
        sql.append("        userCode");
        sql.append("        ,userName ");
        sql.append("        ");
        sql.append("    ORDER BY");
        sql.append("        COUNT(*) desc ");

        return this.queryDistinctPage(sql.toString(), params, pageNo, pageSize);
    }

    @Override
    public Long getLoginStatisticsTotal(String orgName, Integer role, String startTime, String endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT");
        sql.append("        COUNT (*) AS 'total' ");
        sql.append("    FROM");
        sql.append("        login_log ll ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org so ");
        sql.append("            ON so.orgCode = ll.orgCode ");
        sql.append("    WHERE");
        sql.append("        flagSuccessful = 1 ");

        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        and orgName like :orgName ");
            params.put("orgName", "%" + orgName + "%");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        and ll.loginTime > :startTime ");
            params.put("startTime", startTime);
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        and ll.loginTime < :endTime ");
            params.put("endTime", endTime);
        }

        if (role != null) {
            if (role == 10) {
                sql.append("        AND (ll.userRole = 10 OR ll.userRole = 30) ");
            } else {
                sql.append("        AND ll.userRole = :role ");
                params.put("role", role);
            }  
        }

        return this.queryCount(sql.toString(), params);
    }

    @Override
    public Long getLoginStatisticsDetailTotal(String orgCode, Integer role, String startTime, String endTime) {
        Map<String, Object> params = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        params.put("orgCode", orgCode);

        sql.append("SELECT");
        sql.append("        COUNT (*) AS total ");
        sql.append("    FROM");
        sql.append("        login_log ");
        sql.append("    WHERE");
        sql.append("        flagSuccessful = 1 ");
        sql.append("        and orgCode = :orgCode");

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        and loginTime > :startTime ");
            params.put("startTime", startTime);
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        and loginTime < :endTime ");
            params.put("endTime", endTime);
        }

        if (role != null) {
            if (role == 10) {
                sql.append("        AND (userRole = 10 OR userRole = 30) ");
            } else {
                sql.append("        AND userRole = :role ");
                params.put("role", role);
            }  
        }

        return this.queryCount(sql.toString(), params);
    }

}
