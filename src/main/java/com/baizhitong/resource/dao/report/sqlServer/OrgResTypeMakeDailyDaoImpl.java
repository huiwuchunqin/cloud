package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.OrgResTypeMakeDailyDao;
import com.baizhitong.resource.model.report.OrgRestypeMakeDaily;

@Service
public class OrgResTypeMakeDailyDaoImpl extends BaseSqlServerDao<OrgRestypeMakeDaily>
                implements OrgResTypeMakeDailyDao {
    /**
     * 查询机构资源类别统计 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getRestypeMakeDaily(Map<String, Object> param) {
        String orgCode = MapUtils.getString(param, "orgCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        if (sectionCode.equals("null")) {
            sectionCode = null;
        }
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        sql.append("SELECT");
        sql.append("         res.resTypeL1");
        sql.append("        ,res.resTypeL2");
        sql.append("        ,srtl1.name AS level1Name");
        sql.append("        ,srtl2.name AS level2Name");
        sql.append("        ,res.privateCount AS privateNum");
        sql.append("        ,res.orgCount AS orgNum");
        sql.append("        ,res.areaCount AS areaNum");
        sql.append("        ,res.totalCount AS totalNum ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rm.resTypeL1");
        sql.append("            ,rm.resTypeL2");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rm1.id) ");
        sql.append("            FROM");
        sql.append("                res_media rm1 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rm1.resCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 10 ");
        sql.append("          AND  flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("          AND resStatus = 20 ");
        sql.append("                AND rm1.resTypeL2 = rm.resTypeL2 ) AS privateCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rm2.id) ");
        sql.append("            FROM");
        sql.append("                res_media rm2 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rm2.resCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 20 ");
        sql.append("          AND  flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("          AND resStatus = 20 ");
        sql.append("                AND rm2.resTypeL2 = rm.resTypeL2 ) AS orgCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rm3.id) ");
        sql.append("            FROM");
        sql.append("                res_media rm3 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rm3.resCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 30 ");
        sql.append("         AND   flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("          AND resStatus = 20 ");
        sql.append("                AND rm3.resTypeL2 = rm.resTypeL2 ) AS areaCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rm4.id) ");
        sql.append("            FROM");
        sql.append("                res_media rm4 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rm4.resCode  ");
        sql.append("            WHERE");
        sql.append("  rm4.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("                     AND  rm4.resTypeL2 = rm.resTypeL2 AND rm4.resStatus = 20 ) AS totalCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rm.resCode  ");
        sql.append("        WHERE");
        sql.append("            rm.resTypeL1 = 10 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  rm.makerOrgCode=:orgCode ");
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rm.resTypeL1");
        sql.append("            ,resTypeL2 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rd.resTypeL1");
        sql.append("            ,rd.resTypeL2");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rd1.id) ");
        sql.append("            FROM");
        sql.append("                res_doc rd1 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rd1.resCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 10 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("           AND flagDelete = 0 ");
        sql.append("          AND resStatus = 20 ");
        sql.append("                AND rd1.resTypeL2 = rd.resTypeL2 ) AS privateCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rd2.id) ");
        sql.append("            FROM");
        sql.append("                res_doc rd2 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rd2.resCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 20 ");
        sql.append("           AND flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("          AND resStatus = 20 ");
        sql.append("                AND rd2.resTypeL2 = rd.resTypeL2 ) AS orgCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rd3.id) ");
        sql.append("            FROM");
        sql.append("                res_doc rd3 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rd3.resCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 30 ");
        sql.append("          AND resStatus = 20 ");
        sql.append("           AND flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("                AND rd3.resTypeL2 = rd.resTypeL2 ) AS areaCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rd4.id) ");
        sql.append("            FROM");
        sql.append("                res_doc rd4 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rd4.resCode  ");
        sql.append("            WHERE");
        sql.append("            rd4.flagDelete = 0 ");
        sql.append("          AND rd4.resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  rd4.makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd4.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd4.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("           AND     rd4.resTypeL2 = rd.resTypeL2 AND rd4.resStatus = 20 ) AS totalCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rd.resCode  ");
        sql.append("        WHERE");
        sql.append("            rd.resTypeL1 = 20 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  rd.makerOrgCode=:orgCode ");
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rd.resTypeL1");
        sql.append("            ,resTypeL2 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.resTypeL1");
        sql.append("            ,e.resTypeL2");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (e1.id) ");
        sql.append("            FROM");
        sql.append("                exercise e1 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON se.testCode = e1.testCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 10 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        sql.append("                AND e1.resTypeL2 = e.resTypeL2 ) AS privateCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (e2.id) ");
        sql.append("            FROM");
        sql.append("                exercise e2 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON se.testCode = e2.testCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        sql.append("                AND e2.resTypeL2 = e.resTypeL2 ) AS orgCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (e3.id) ");
        sql.append("            FROM");
        sql.append("                exercise e3 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON se.testCode = e3.testCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 30 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("                AND e3.resTypeL2 = e.resTypeL2 ) AS areaCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (e4.id) ");
        sql.append("            FROM");
        sql.append("                exercise e4 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON se.testCode = e4.testCode  ");
        sql.append("            WHERE");
        sql.append("              flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" AND  e4.makerOrgCode=:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e4.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e4.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("        AND        e4.resTypeL2 = e.resTypeL2  ) AS totalCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON se.testCode = e.testCode  ");
        sql.append("        WHERE");
        sql.append("            e.resTypeL1 = 30 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  e.makerOrgCode=:orgCode ");
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            e.resTypeL1");
        sql.append("            ,e.resTypeL2 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            50 AS resTypeL1");
        sql.append("            ,'' AS resTypeL2");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (q1.id) ");
        sql.append("            FROM");
        sql.append("                question q1 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section se ");
        sql.append("                ON se.questionCode = q1.questionCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 10 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND status = 10 ");
        sql.append("    and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and q1.makerOrgCode=:orgCode ");
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("                AND q1.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("                AND q1.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("             ) AS privateCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (q2.id) ");
        sql.append("            FROM");
        sql.append("                question q2 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section se ");
        sql.append("                ON se.questionCode = q2.questionCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 20 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND status = 10 ");
        sql.append("    and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  q2.makerOrgCode=:orgCode ");
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("                AND q2.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("                AND q2.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("             ) AS orgCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (q3.id) ");
        sql.append("            FROM");
        sql.append("                question q3 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section se ");
        sql.append("                ON se.questionCode = q3.questionCode  ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 30 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  q3.makerOrgCode=:orgCode ");
        }
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND status = 10 ");
        sql.append("    and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("                AND q3.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("                AND q3.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("             ) AS areaCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (q4.id) ");
        sql.append("            FROM");
        sql.append("                question q4 ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section se ");
        sql.append("                ON se.questionCode = q4.questionCode  ");
        sql.append("            WHERE");
        sql.append("                1 = 1 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND status = 10 ");
        sql.append("    and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  q4.makerOrgCode=:orgCode ");
        }

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("                AND q4.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("                AND q4.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("             ) AS totalCount ) res ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_res_type_l1 srtl1 ");
        sql.append("                ON srtl1.code = res.resTypeL1 ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_res_type_l2 srtl2 ");
        sql.append("                ON srtl2.code = res.resTypeL2");
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("endTime", endDate);
        sqlParam.put("startTime", startDate);
        sqlParam.put("orgCode", orgCode);
        return super.findBySql(sql.toString(), sqlParam);

    }

    /**
     * 查询资源汇总统计 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getAllCompanyResTypeMakeDaily(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String orgCode = MapUtils.getString(param, "orgCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("         res.resTypeL1");
        sql.append("        ,res.resTypeL2");
        sql.append("        ,srtl1.name AS level1Name");
        sql.append("        ,srtl2.name AS level2Name");
        sql.append("        ,res.privateCount AS privateNum");
        sql.append("        ,res.orgCount AS orgNum");
        sql.append("        ,res.areaCount AS areaNum");
        sql.append("        ,res.totalCount AS totalNum ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rm.resTypeL1");
        sql.append("            ,rm.resTypeL2");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rm1.id) ");
        sql.append("            FROM");
        sql.append("                res_media rm1 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 10 ");
        sql.append("          AND  flagDelete = 0 ");
        sql.append("          AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("                AND rm1.resTypeL2 = rm.resTypeL2 ) AS privateCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rm2.id) ");
        sql.append("            FROM");
        sql.append("                res_media rm2 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 20 ");
        sql.append("          AND  flagDelete = 0 ");
        sql.append("          AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("                AND rm2.resTypeL2 = rm.resTypeL2 ) AS orgCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rm3.id) ");
        sql.append("            FROM");
        sql.append("                res_media rm3 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 30 ");
        sql.append("          AND  flagDelete = 0 ");
        sql.append("          AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("                AND rm3.resTypeL2 = rm.resTypeL2 ) AS areaCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rm4.id) ");
        sql.append("            FROM");
        sql.append("                res_media rm4 ");
        sql.append("            WHERE");
        sql.append("                rm4.resTypeL2 = rm.resTypeL2    ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("                AND  flagDelete = 0 ");
        sql.append("               AND resStatus = 20 ) AS totalCount");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        WHERE");
        sql.append("            resTypeL1 = 10 ");
        sql.append("           AND resStatus = 20 ");
        sql.append("           AND flagDelete = 0 ");

        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endTime ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            resTypeL1");
        sql.append("            ,resTypeL2 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            resTypeL1");
        sql.append("            ,resTypeL2");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rd1.id) ");
        sql.append("            FROM");
        sql.append("                res_doc rd1 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 10 ");
        sql.append("          AND  flagDelete = 0 ");
        sql.append("          AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("                AND rd1.resTypeL2 = rd.resTypeL2 ) AS privateCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rd2.id) ");
        sql.append("            FROM");
        sql.append("                res_doc rd2 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("          AND  flagDelete = 0 ");
        sql.append("          AND resStatus = 20 ");
        sql.append("                AND rd2.resTypeL2 = rd.resTypeL2 ) AS orgCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rd3.id) ");
        sql.append("            FROM");
        sql.append("                res_doc rd3 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 30 ");
        sql.append("          AND  flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("          AND resStatus = 20 ");
        sql.append("                AND rd3.resTypeL2 = rd.resTypeL2 ) AS areaCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (rd4.id) ");
        sql.append("            FROM");
        sql.append("                res_doc rd4 ");
        sql.append("            WHERE");
        sql.append("                rd4.resTypeL2 = rd.resTypeL2   ");
        sql.append("        AND  flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("        AND resStatus = 20 ) AS totalCount");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        WHERE");
        sql.append("            resTypeL1 = 20 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endTime ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            resTypeL1");
        sql.append("            ,resTypeL2 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            resTypeL1");
        sql.append("            ,resTypeL2");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (e1.id) ");
        sql.append("            FROM");
        sql.append("                exercise e1 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 10 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("          AND  flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        sql.append("                AND e1.resTypeL2 = e.resTypeL2 ) AS privateCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (e2.id) ");
        sql.append("            FROM");
        sql.append("                exercise e2 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("          AND  flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        sql.append("                AND e2.resTypeL2 = e.resTypeL2 ) AS orgCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (e3.id) ");
        sql.append("            FROM");
        sql.append("                exercise e3 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 30 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("          AND  flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        sql.append("                AND e3.resTypeL2 = e.resTypeL2 ) AS areaCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (e4.id) ");
        sql.append("            FROM");
        sql.append("                exercise e4 ");
        sql.append("            WHERE");
        sql.append("                e4.resTypeL2 = e.resTypeL2   ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND createTime <= :endTime ");
        }
        sql.append("        AND  flagDelete = 0 ");
        sql.append("         AND scenarioType IN (10,20,40) ) AS totalCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        WHERE");
        sql.append("            resTypeL1 = 30 ");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endTime ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            resTypeL1");
        sql.append("            ,resTypeL2 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            50 AS resTypeL1");
        sql.append("            ,'' AS resTypeL2");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (q1.id) ");
        sql.append("            FROM");
        sql.append("                question q1 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 10 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        sql.append("       and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND status = 10 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("                AND q1.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("                AND q1.createTime <= :endTime ");
        }
        sql.append("             ) AS privateCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (q2.id) ");
        sql.append("            FROM");
        sql.append("                question q2 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        sql.append("       and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND status = 10 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("                AND q2.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("                AND q2.createTime <= :endTime ");
        }
        sql.append("             ) AS orgCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (q3.id) ");
        sql.append("            FROM");
        sql.append("                question q3 ");
        sql.append("            WHERE");
        sql.append("                shareLevel = 30 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        sql.append("           AND flagDelete = 0 ");
        sql.append("           AND status = 10 ");
        sql.append("          and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("                AND q3.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("                AND q3.createTime <= :endTime ");
        }
        sql.append("             ) AS areaCount");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (q4.id) ");
        sql.append("            FROM");
        sql.append("                question q4 ");
        sql.append("            WHERE ");
        sql.append("            flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("          AND makerOrgCode =:orgCode ");
        }
        sql.append("           AND status = 10 ");
        sql.append("    and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("                AND q4.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("                AND q4.createTime <= :endTime ");
        }
        sql.append("             ) AS totalCount ) res ");
        sql.append("            ");
        sql.append("        inner JOIN");
        sql.append("            share_res_type_l1 srtl1 ");
        sql.append("                ON srtl1.code = res.resTypeL1 ");
        sql.append("                ");
        sql.append("        inner JOIN");
        sql.append("            share_res_type_l2 srtl2 ");
        sql.append("                ON srtl2.code = res.resTypeL2");

        sqlParam.put("endTime", endDate);
        sqlParam.put("startTime", startDate);
        sqlParam.put("orgCode", orgCode);
        return super.findBySql(sql.toString(), sqlParam);
    }
}
