package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.TeacherResMakeDailyDao;
import com.baizhitong.resource.model.report.TeacherResMakeDaily;

/**
 * 
 * 作者统计DAO实现
 * 
 * @author creator zhangqiang 2016年7月19日 下午12:46:54
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class TeacherResMakeDailyDaoImpl extends BaseSqlServerDao<TeacherResMakeDaily>
                implements TeacherResMakeDailyDao {

    @Override
    public Page select(Integer page, Integer rows, Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String startTime = MapUtils.getString(param, "startDate");
        String endTime = MapUtils.getString(param, "endDate");
        String orgName = MapUtils.getString(param, "orgName");
        String userName = MapUtils.getString(param, "userName");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        if (StringUtils.isNotEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
        }
        endTime = endTime + " 23:59:59";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        res.makerName AS userName");
        sql.append("        ,res.makerCode ");
        sql.append("        ,res.makerOrgName AS orgName");
        sql.append("        ,res.makerOrgCode AS orgCode");
        sql.append("        ,SUM (res.rmCount) mediaTotal");
        sql.append("        ,SUM (res.rdCount) docTotal");
        sql.append("        ,SUM (res.testCount) exerciseTotal");
        sql.append("        ,SUM (res.qCount) AS questionTotal");
        sql.append("        ,( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) AS resTotal ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,COUNT (rm.resCode) rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrse ");
        sql.append("                ON rrse.resCode = rm.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.rescode = rm.resCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = :flagDelete ");
        sql.append("            AND rm.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("            AND rm.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("            AND rm.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("            AND rm.makerName LIKE :makerName ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rm.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND rm.makerOrgName LIKE :makerOrgName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName ");
        sql.append("            ,rm.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,COUNT (rd.resCode) rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrse ");
        sql.append("                ON rrse.resCode = rd.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.rescode = rd.resCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = :flagDelete ");
        sql.append("            AND rd.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("            AND rd.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("            AND rd.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("            AND rd.makerName LIKE :makerName ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rd.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND rd.makerOrgName LIKE :makerOrgName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName ");
        sql.append("            ,rd.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,COUNT (e.testCode) testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = :flagDelete ");
        sql.append("           AND e.scenarioType in(10,20,40)  ");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("        AND e.makerName LIKE :makerName ");
        }

        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND e.shareLevel = :shareLevel ");
        }

        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        AND e.makerOrgName LIKE :makerOrgName ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND e.sectionCode = :sectionCode ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND e.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        AND e.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        AND e.createTime <= :endTime ");
        }
        sql.append("        GROUP BY");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName ");
        sql.append("            ,e.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,COUNT (q.questionCode) qCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section rqs ");
        sql.append("                ON rqs.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = :flagDelete ");
        sql.append("            AND q.questionSource IN (10,20,30,40) ");
        sql.append("            AND q.status = :status");
        sqlParam.put("status", 10);// 可用
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND q.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(userName)) {
            sql.append("        AND q.makerName LIKE :makerName ");
        }

        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND q.shareLevel = :shareLevel ");
        }

        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        AND q.makerOrgName LIKE :makerOrgName ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rqs.sectionCode = :sectionCode ");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        AND q.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        AND q.createTime <= :endTime ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName ");
        sql.append("            ,q.makerOrgCode");
        sql.append("    ) res ");
        sql.append("    ");
        sql.append("GROUP BY");
        sql.append("    res.makerName");
        sql.append("    ,res.makerCode");
        sql.append("    ,res.makerOrgName ");
        sql.append("    ,res.makerOrgCode ");
        sql.append(" ORDER BY ( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) DESC ");

        sqlParam.put("flagDelete", 0);
        sqlParam.put("resStatus", 20);
        sqlParam.put("startTime", startTime);
        sqlParam.put("endTime", endTime);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("makerOrgName", "%" + orgName.trim() + "%");
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("makerName", "%" + userName.trim() + "%");
        sqlParam.put("subjectCode", subjectCode);

        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

        // StringBuffer sql = new StringBuffer();
        // String startDate = MapUtils.getString(param, "startDate");
        // String endDate = MapUtils.getString(param, "endDate");
        // String orgName = MapUtils.getString(param, "orgName");
        // String userName = MapUtils.getString(param, "userName");
        // String sectionCode = MapUtils.getString(param, "sectionCode");
        // String subjectCode = MapUtils.getString(param, "subjectCode");
        // if(StringUtils.isEmpty(startDate)){
        // startDate="00000000";
        // }
        // Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sqlParam.put("startDate", startDate);
        // sqlParam.put("endDate", endDate);
        // sql.append("SELECT");
        // sql.append(" trma.userName");
        // sql.append(" ,trma.orgName");
        // sql.append(" ,trma.userCode");
        // sql.append(" ,trma.orgCode");
        // sql.append(" ,( SUM (trma.shareLevelPrivate) + SUM (trma.shareLevelOrg) + SUM
        // (trma.shareLevelArea) -isNull(SUM(b.shareLevelPrivate)");
        // sql.append(" ,0) -isNull(SUM(b.shareLevelOrg)");
        // sql.append(" ,0) -isNull(SUM(b.shareLevelArea)");
        // sql.append(" ,0) ) AS resTotal");
        // sql.append(" ,( SUM (trma.shareLevelPrivate) -isNull(SUM(b.shareLevelPrivate)");
        // sql.append(" ,0) ) AS shareLevelPrivate");
        // sql.append(" ,( SUM (trma.shareLevelOrg) -isNull(SUM(b.shareLevelOrg)");
        // sql.append(" ,0) ) AS shareLevelOrg");
        // sql.append(" ,( SUM (trma.shareLevelArea) -isNull(SUM(b.shareLevelArea)");
        // sql.append(" ,0) ) AS shareLevelArea");
        // sql.append(" ,( SUM (trma.resMediaNumPrivate) + SUM (trma.resMediaNumOrg) + SUM
        // (trma.resMediaNumArea) -isNull( SUM (b.resMediaNumPrivate)");
        // sql.append(" ,0 ) -isNull(SUM(b.resMediaNumOrg)");
        // sql.append(" ,0) -isNull(SUM(b.resMediaNumArea)");
        // sql.append(" ,0) ) AS mediaTotal");
        // sql.append(" ,( SUM (trma.resDocNumPrivate) + SUM (trma.resDocNumOrg) + SUM
        // (trma.resDocNumArea) -isNull(SUM(b.resDocNumPrivate)");
        // sql.append(" ,0) -isNull(SUM(b.resDocNumOrg)");
        // sql.append(" ,0) -isNull(SUM(b.resDocNumArea)");
        // sql.append(" ,0) ) AS docTotal");
        // sql.append(" ,( SUM (trma.resExerciseNumPrivate) + SUM (trma.resExerciseNumOrg) + SUM
        // (trma.resExerciseNumArea) -isNull( SUM (b.resExerciseNumPrivate)");
        // sql.append(" ,0 ) -isNull(SUM(b.resExerciseNumOrg)");
        // sql.append(" ,0) -isNull( SUM (b.resExerciseNumArea)");
        // sql.append(" ,0 ) ) AS exerciseTotal");
        // sql.append(" ,( SUM (trma.resQuestionNumPrivate) + SUM (trma.resQuestionNumOrg) + SUM
        // (trma.resQuestionNumArea) -isNull( SUM (b.resQuestionNumPrivate)");
        // sql.append(" ,0 ) -isNull(SUM(b.resQuestionNumOrg)");
        // sql.append(" ,0) -isNull( SUM (b.resQuestionNumArea)");
        // sql.append(" ,0 ) ) AS questionTotal ");
        // sql.append(" FROM");
        // sql.append(" teacher_res_make_daily trma ");
        // sql.append(" LEFT JOIN");
        // sql.append(" teacher_res_make_daily b ");
        // sql.append(" ON trma.userCode = b.userCode ");
        // sql.append(" AND trma.orgCode = b.orgCode ");
        // sql.append(" AND b.baseDate = :startDate ");
        // sql.append(" INNER JOIN");
        // sql.append(" share_user_teacher sut ");
        // sql.append(" ON trma.userCode = sut.teacherCode ");
        // sql.append(" WHERE");
        // sql.append(" trma.baseDate = :endDate ");
        // if(StringUtils.isNotEmpty(orgName)){
        // sql.append(" AND trma.orgName LIKE :orgName");
        // sqlParam.put("orgName", "%"+orgName.trim()+"%");
        // }
        // if(StringUtils.isNotEmpty(userName)){
        // sql.append(" AND trma.userName LIKE :userName");
        // sqlParam.put("userName", "%"+userName.trim()+"%");
        // }
        // if(StringUtils.isNotEmpty(sectionCode)){
        // sql.append(" AND sut.sectionCode = :sectionCode ");
        // sqlParam.put("sectionCode", sectionCode);
        // }
        // if(StringUtils.isNotEmpty(subjectCode)){
        // sql.append(" AND sut.subjectCode = :subjectCode ");
        // sqlParam.put("subjectCode", subjectCode);
        // }
        // sql.append(" GROUP BY");
        // sql.append(" trma.userName");
        // sql.append(" ,trma.orgName");
        // sql.append(" ,trma.orgCode");
        // sql.append(" ,trma.userCode");
        // sql.append(" ,b.userName");
        // sql.append(" ,b.orgName");
        // //根据资源数降序排序
        // sql.append(" ORDER BY (SUM (trma.shareLevelPrivate) + SUM (trma.shareLevelOrg) + SUM
        // (trma.shareLevelArea) - isNull(SUM(b.shareLevelPrivate), 0) -
        // isNull(SUM(b.shareLevelOrg), 0) - isNull(SUM(b.shareLevelArea), 0)) DESC ");
        // return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> select(Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String startTime = MapUtils.getString(param, "startDate");
        String endTime = MapUtils.getString(param, "endDate");
        String orgName = MapUtils.getString(param, "orgName");
        String userName = MapUtils.getString(param, "userName");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        if (StringUtils.isNotEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
        }
        endTime = endTime + " 23:59:59";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT(c.userCode) AS authorTotal");
        sql.append("        ,SUM(c.mediaCount) AS mediaTotal");
        sql.append("        ,SUM(c.docCount) AS docTotal");
        sql.append("        ,SUM(c.testCount) AS exerciseTotal");
        sql.append("        ,SUM(c.questionCount) AS questionTotal ");
        sql.append("        ,SUM(c.resCount) AS resTotal ");
        sql.append("    FROM");
        sql.append("       ( ");
        sql.append("SELECT");
        sql.append("        res.makerName AS userName");
        sql.append("        ,res.makerCode AS userCode");
        sql.append("        ,res.makerOrgName AS orgName");
        sql.append("        ,res.makerOrgCode AS orgCode");
        sql.append("        ,SUM (res.rmCount) mediaCount");
        sql.append("        ,SUM (res.rdCount) docCount");
        sql.append("        ,SUM (res.testCount) testCount");
        sql.append("        ,SUM (res.qCount) AS questionCount");
        sql.append("        ,( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) AS resCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,COUNT (rm.resCode) rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrse ");
        sql.append("                ON rrse.resCode = rm.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.rescode = rm.resCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = :flagDelete ");
        sql.append("            AND rm.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("            AND rm.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("            AND rm.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("            AND rm.makerName LIKE :makerName ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rm.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND rm.makerOrgName LIKE :makerOrgName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName ");
        sql.append("            ,rm.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,COUNT (rd.resCode) rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrse ");
        sql.append("                ON rrse.resCode = rd.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.rescode = rd.resCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = :flagDelete ");
        sql.append("            AND rd.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("            AND rd.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("            AND rd.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("            AND rd.makerName LIKE :makerName ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rd.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND rd.makerOrgName LIKE :makerOrgName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName ");
        sql.append("            ,rd.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,COUNT (e.testCode) testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = :flagDelete ");
        sql.append("           AND e.scenarioType in(10,20,40)  ");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("        AND e.makerName LIKE :makerName ");
        }

        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND e.shareLevel = :shareLevel ");
        }

        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        AND e.makerOrgName LIKE :makerOrgName ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND e.sectionCode = :sectionCode ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND e.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        AND e.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        AND e.createTime <= :endTime ");
        }

        sql.append("        GROUP BY");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName ");
        sql.append("            ,e.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,COUNT (q.questionCode) qCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section rqs ");
        sql.append("                ON rqs.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = :flagDelete ");
        sql.append("            AND q.questionSource IN (10,20,30,40) ");
        sql.append("            AND q.status = :status");
        sqlParam.put("status", 10);// 可用
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND q.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(userName)) {
            sql.append("        AND q.makerName LIKE :makerName ");
        }

        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND q.shareLevel = :shareLevel ");
        }

        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        AND q.makerOrgName LIKE :makerOrgName ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rqs.sectionCode = :sectionCode ");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        AND q.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        AND q.createTime <= :endTime ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName ");
        sql.append("            ,q.makerOrgCode");
        sql.append("    ) res ");
        sql.append("    ");
        sql.append("GROUP BY");
        sql.append("    res.makerName");
        sql.append("    ,res.makerOrgName , res.makerCode, res.makerOrgCode )c ");

        sqlParam.put("flagDelete", 0);
        sqlParam.put("resStatus", 20);
        sqlParam.put("startTime", startTime);
        sqlParam.put("endTime", endTime);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("makerOrgName", "%" + orgName.trim() + "%");
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("makerName", "%" + userName.trim() + "%");
        sqlParam.put("subjectCode", subjectCode);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @Override
    public Page<Map<String, Object>> selectByOrg(Integer page, Integer rows, Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String startTime = MapUtils.getString(param, "startDate");
        String endTime = MapUtils.getString(param, "endDate");
        String orgCode = MapUtils.getString(param, "orgCode");
        String userName = MapUtils.getString(param, "userName");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        if (StringUtils.isNotEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
        }
        endTime = endTime + " 23:59:59";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        res.makerName AS userName");
        sql.append("        ,res.makerCode ");
        sql.append("        ,res.makerOrgName AS orgName");
        sql.append("        ,res.makerOrgCode AS orgCode");
        sql.append("        ,SUM (res.rmCount) mediaTotal");
        sql.append("        ,SUM (res.rdCount) docTotal");
        sql.append("        ,SUM (res.testCount) exerciseTotal");
        sql.append("        ,SUM (res.qCount) AS questionTotal");
        sql.append("        ,( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) AS resTotal ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,COUNT (rm.resCode) rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrse ");
        sql.append("                ON rrse.resCode = rm.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.rescode = rm.resCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = :flagDelete ");
        sql.append("            AND rm.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("            AND rm.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("            AND rm.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("            AND rm.makerName LIKE :makerName ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rm.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("            AND rm.makerOrgCode = :orgCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName ");
        sql.append("            ,rm.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,COUNT (rd.resCode) rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrse ");
        sql.append("                ON rrse.resCode = rd.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.rescode = rd.resCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = :flagDelete ");
        sql.append("            AND rd.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("            AND rd.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("            AND rd.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("            AND rd.makerName LIKE :makerName ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rd.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("            AND rd.makerOrgCode = :orgCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName ");
        sql.append("            ,rd.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,COUNT (e.testCode) testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = :flagDelete ");
        sql.append("           AND e.scenarioType in(10,20,40)  ");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("        AND e.makerName LIKE :makerName ");
        }

        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND e.shareLevel = :shareLevel ");
        }

        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND e.makerOrgCode = :orgCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND e.sectionCode = :sectionCode ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND e.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        AND e.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        AND e.createTime <= :endTime ");
        }
        sql.append("        GROUP BY");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName ");
        sql.append("            ,e.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,COUNT (q.questionCode) qCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section rqs ");
        sql.append("                ON rqs.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = :flagDelete ");
        sql.append("            AND q.questionSource IN (10,20,30,40) ");
        sql.append("            AND q.status = :status");
        sqlParam.put("status", 10);// 可用
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND q.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(userName)) {
            sql.append("        AND q.makerName LIKE :makerName ");
        }

        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND q.shareLevel = :shareLevel ");
        }

        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND q.makerOrgCode = :orgCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rqs.sectionCode = :sectionCode ");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        AND q.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        AND q.createTime <= :endTime ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName ");
        sql.append("            ,q.makerOrgCode");
        sql.append("    ) res ");
        sql.append("GROUP BY");
        sql.append("    res.makerName");
        sql.append("    ,res.makerCode");
        sql.append("    ,res.makerOrgName ");
        sql.append("    ,res.makerOrgCode ");
        sql.append(" ORDER BY ( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) DESC ");

        sqlParam.put("flagDelete", 0);
        sqlParam.put("resStatus", 20);
        sqlParam.put("startTime", startTime);
        sqlParam.put("endTime", endTime);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("shareLevel", shareLevel);
        if(StringUtils.isNotEmpty(userName)){
            sqlParam.put("makerName", "%" + userName.trim() + "%");
        }
        sqlParam.put("subjectCode", subjectCode);
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectByOrg(Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String startTime = MapUtils.getString(param, "startDate");
        String endTime = MapUtils.getString(param, "endDate");
        String orgCode = MapUtils.getString(param, "orgCode");
        String userName = MapUtils.getString(param, "userName");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        if (StringUtils.isNotEmpty(startTime)) {
            startTime = startTime + " 00:00:00";
        }
        endTime = endTime + " 23:59:59";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT(c.userCode) AS authorTotal");
        sql.append("        ,SUM(c.mediaCount) AS mediaTotal");
        sql.append("        ,SUM(c.docCount) AS docTotal");
        sql.append("        ,SUM(c.testCount) AS exerciseTotal");
        sql.append("        ,SUM(c.questionCount) AS questionTotal ");
        sql.append("        ,SUM(c.resCount) AS resTotal ");
        sql.append("    FROM");
        sql.append("       ( ");
        sql.append("SELECT");
        sql.append("        res.makerName AS userName");
        sql.append("        ,res.makerCode AS userCode");
        sql.append("        ,res.makerOrgName AS orgName");
        sql.append("        ,res.makerOrgCode AS orgCode");
        sql.append("        ,SUM (res.rmCount) mediaCount");
        sql.append("        ,SUM (res.rdCount) docCount");
        sql.append("        ,SUM (res.testCount) testCount");
        sql.append("        ,SUM (res.qCount) AS questionCount");
        sql.append("        ,( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) AS resCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,COUNT (rm.resCode) rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrse ");
        sql.append("                ON rrse.resCode = rm.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.rescode = rm.resCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = :flagDelete ");
        sql.append("            AND rm.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("            AND rm.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("            AND rm.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("            AND rm.makerName LIKE :makerName ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rm.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("            AND rm.makerOrgCode = :orgCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName ");
        sql.append("            ,rm.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,COUNT (rd.resCode) rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrse ");
        sql.append("                ON rrse.resCode = rd.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.rescode = rd.resCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = :flagDelete ");
        sql.append("            AND rd.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("            AND rd.createTime >= :startTime ");
        }
        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("            AND rd.createTime <= :endTime ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("            AND rd.makerName LIKE :makerName ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rd.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("            AND rd.makerOrgCode = :orgCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName ");
        sql.append("            ,rd.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,COUNT (e.testCode) testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = :flagDelete ");
        sql.append("           AND e.scenarioType in(10,20,40)  ");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("        AND e.makerName LIKE :makerName ");
        }

        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND e.shareLevel = :shareLevel ");
        }

        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND e.makerOrgCode = :orgCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND e.sectionCode = :sectionCode ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND e.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        AND e.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        AND e.createTime <= :endTime ");
        }

        sql.append("        GROUP BY");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName ");
        sql.append("            ,e.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,COUNT (q.questionCode) qCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section rqs ");
        sql.append("                ON rqs.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = :flagDelete ");
        sql.append("            AND q.questionSource IN (10,20,30,40) ");
        sql.append("            AND q.status = :status");
        sqlParam.put("status", 10);// 可用
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND q.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(userName)) {
            sql.append("        AND q.makerName LIKE :makerName ");
        }

        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND q.shareLevel = :shareLevel ");
        }

        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND q.makerOrgCode = :orgCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rqs.sectionCode = :sectionCode ");
        }

        if (StringUtils.isNotEmpty(startTime)) {
            sql.append("        AND q.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endTime)) {
            sql.append("        AND q.createTime <= :endTime ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName ");
        sql.append("            ,q.makerOrgCode");
        sql.append("    ) res ");
        sql.append("GROUP BY");
        sql.append("    res.makerName");
        sql.append("    ,res.makerOrgName , res.makerCode, res.makerOrgCode )c ");

        sqlParam.put("flagDelete", 0);
        sqlParam.put("resStatus", 20);
        sqlParam.put("startTime", startTime);
        sqlParam.put("endTime", endTime);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("makerName", "%" + userName.trim() + "%");
        sqlParam.put("subjectCode", subjectCode);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

}
