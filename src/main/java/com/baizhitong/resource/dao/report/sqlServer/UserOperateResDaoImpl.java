package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.UserOperateResDao;
import com.baizhitong.resource.model.report.TeacherResMakeDaily;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * UserOperateResDaoImpl 用户使用资源的情况
 * 
 * @author creator gaow 2016年12月8日 上午10:56:14
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class UserOperateResDaoImpl extends BaseSqlServerDao<TeacherResMakeDaily> implements UserOperateResDao {

    /**
     * 查询老师使用资源情况 ()<br>
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 记录数
     * @return page
     */
    public Page selectTeacherOp(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String orgCode = MapUtils.getString(param, "orgCode");
        String startTime = MapUtils.getString(param, "beginTime");
        String endTime = MapUtils.getString(param, "endTime");
        String userName = MapUtils.getString(param, "userName");
        String orgName = MapUtils.getString(param, "orgName");

        sql.append("SELECT");
        sql.append("        tb.* ");
        sql.append("    FROM");
        sql.append("        (   SELECT");
        sql.append("            b.userName");
        sql.append("            ,c.orgName");
        sql.append("            ,a.*");
        sql.append("            ,(     a.comment + a.good + a.favourite + a.browser + a.reference  + a.download    ) total   ");
        sql.append("        FROM");
        sql.append("            (     SELECT");
        sql.append("                operatorCode");
        sql.append("                ,SUM (       CASE operateType       ");
        sql.append("                    WHEN '10' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) good,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '70' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) comment,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '30' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) favourite,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '40' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) browser,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '50' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) reference,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '60' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) download     ");
        sql.append("            FROM");
        sql.append("                res_operate_log    where 1=1 ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append(" and  operateTime>=:startTime");
            sqlParam.put("startTime", startTime + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append(" and operateTime<=:endTime");
            sqlParam.put("endTime", endTime + " 23:59:59");
        }
        sql.append("            GROUP BY");
        sql.append("                operatorCode    ) a   ");
        sql.append("            ");
        sql.append("        INNER JOIN");
        sql.append("            share_user_teacher b ");
        sql.append("                ON b.teacherCode = a.operatorCode   ");
        sql.append("                ");
        sql.append("        INNER JOIN");
        sql.append("            share_org c ");
        sql.append("                ON b.orgCode = c.orgCode   ");
        sql.append("        WHERE");
        sql.append("            b.status = 1  ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  b.orgCode=:orgCode");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and  b.userName like '%" + userName + "%'");
            sqlParam.put("userName", userName);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and  c.orgName like '%" + orgName + "%'");
        }
        sql.append("        ) tb ");
        sql.append("        ");
        sql.append("    ORDER BY");
        sql.append("        tb.total DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 
     * 学生资源使用情况
     * 
     * @param param 参数
     * @param page 页码
     * @param rows 每页记录数
     * @return page
     */
    public Page selectStudentOp(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String orgCode = MapUtils.getString(param, "orgCode");
        String startTime = MapUtils.getString(param, "beginTime");
        String endTime = MapUtils.getString(param, "endTime");
        String userName = MapUtils.getString(param, "userName");
        String orgName = MapUtils.getString(param, "orgName");

        sql.append("SELECT");
        sql.append("        tb.* ");
        sql.append("    FROM");
        sql.append("        (   SELECT");
        sql.append("            b.userName");
        sql.append("            ,c.orgName");
        sql.append("            ,a.*");
        sql.append("            ,(     a.comment + a.good  + a.browser + a.reference + a.download    ) total   ");
        sql.append("        FROM");
        sql.append("            (     SELECT");
        sql.append("                operatorCode");
        sql.append("                ,SUM (       CASE operateType       ");
        sql.append("                    WHEN '10' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) good,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '70' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) comment,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '30' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) favourite,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '40' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) browser,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '50' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) reference,      SUM (       CASE operateType       ");
        sql.append("                    WHEN '60' ");
        sql.append("                THEN");
        sql.append("                    1       ");
        sql.append("                    ELSE        0       ");
        sql.append("                END      ) download     ");
        sql.append("            FROM");
        sql.append("                res_operate_log    where 1=1 ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append(" and  operateTime>=:startTime");
            sqlParam.put("startTime", startTime + " 00:00:00");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append(" and operateTime<=:endTime");
            sqlParam.put("endTime", endTime + " 23:59:59");
        }
        sql.append("            GROUP BY");
        sql.append("                operatorCode    ) a   ");
        sql.append("            ");
        sql.append("        INNER JOIN");
        sql.append("            share_user_student b ");
        sql.append("                ON b.studentCode = a.operatorCode   ");
        sql.append("                ");
        sql.append("        INNER JOIN");
        sql.append("            share_org c ");
        sql.append("                ON b.orgCode = c.orgCode   ");
        sql.append("        WHERE");
        sql.append("            b.status = 1  ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  b.orgCode=:orgCode");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and  b.userName like '%" + userName + "%'");
            sqlParam.put("userName", userName);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and  c.orgName like '%" + orgName + "%'");
        }
        sql.append("        ) tb ");
        sql.append("        ");
        sql.append("    ORDER BY");
        sql.append("        tb.total DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

}
