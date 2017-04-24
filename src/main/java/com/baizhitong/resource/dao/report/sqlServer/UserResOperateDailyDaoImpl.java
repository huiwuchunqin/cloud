package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.UserResOperateDailyDao;
import com.baizhitong.resource.model.report.UserResOperateDaily;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 使用情况详细DAO实现
 * 
 * @author creator zhangqiang 2016年7月20日 下午3:22:30
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class UserResOperateDailyDaoImpl extends BaseSqlServerDao<UserResOperateDaily>
                implements UserResOperateDailyDao {

    /**
     * 
     * (分页查询使用情况详细统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @Override
    public Page select(Map<String, Object> param, Integer page, Integer rows) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        makerName AS userName");
        sql.append("        ,makerCode AS userCode");
        // sql.append(" ,isNull(userRole,0) AS userRole");
        sql.append("        ,makerOrgName AS orgName");
        sql.append("        ,makerOrgCode");
        // sql.append(" ,isNull(sectionCode,0) AS sectionCode");
        // sql.append(" ,isNull(sectionName,'无') AS sectionName");
        sql.append("        ,isNull(SUM (browseCount),0)  AS browseCount");
        sql.append("        ,isNull(SUM (downloadCount),0)  AS downloadCount");
        sql.append("        ,isNull(SUM (referCount),0)  AS referCount");
        sql.append("        ,isNull(SUM (favoriteCount),0)  AS favoriteCount");
        sql.append("        ,isNull(SUM (goodCount),0) AS goodCount");
        sql.append("        ,isNull(SUM (commentCount) ,0) AS commentCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,SUM (rd.browseCount) AS browseCount");
        sql.append("            ,SUM (rd.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rd.referCount) AS referCount");
        sql.append("            ,SUM (rd.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rd.goodCount) AS goodCount");
        sql.append("            ,SUM (rd.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rd.resCode = rrs.resCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        sql.append("            AND rd.resStatus = 20 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rrs.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }else{
            sql.append("        AND rrs.sectionCode is null ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND rd.makerOrgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,rd.makerName ");
        sql.append("            ,rd.makerCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,SUM (rm.browseCount) AS browseCount");
        sql.append("            ,SUM (rm.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rm.referCount) AS referCount");
        sql.append("            ,SUM (rm.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rm.goodCount) AS goodCount");
        sql.append("            ,SUM (rm.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rm.resCode = rrs.resCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        sql.append("            AND rm.resStatus = 20 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rrs.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }else{
            sql.append("        AND rrs.sectionCode is null ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND rm.makerOrgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,SUM (e.browseCount) AS browseCount");
        sql.append("            ,SUM (e.downloadCount) AS downloadCount");
        sql.append("            ,SUM (e.referCount) AS referCount");
        sql.append("            ,SUM (e.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (e.goodCount) AS goodCount");
        sql.append("            ,SUM (e.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section res ");
        sql.append("                ON res.testCode = e.testCode ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        sql.append("           AND e.scenarioType in(10,20,40)  ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND res.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }else{
            sql.append("        AND res.sectionCode is null ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND e.makerOrgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,SUM (q.browseCount) AS browseCount");
        sql.append("            ,SUM (q.downloadCount) AS downloadCount");
        sql.append("            ,SUM (q.referCount) AS referCount");
        sql.append("            ,SUM (q.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (q.goodCount) AS goodCount");
        sql.append("            ,SUM (q.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section rrse ");
        sql.append("                ON rrse.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        sql.append("            AND q.questionSource IN (10,20,30,40) ");
        sql.append("            AND q.status = 10 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rrse.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }else{
            sql.append("        AND rrse.sectionCode is null ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND q.makerOrgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("    ) res ");
        sql.append("GROUP BY");
        sql.append("    makerName");
        sql.append("    ,makerCode");
        sql.append("    ,makerOrgName");
        sql.append("    ,makerOrgCode");
        sql.append("    ORDER BY");
        sql.append("        res.makerName ASC ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @Override
    public Map<String, Object> select(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);

        sql.append("SELECT");
        sql.append("        COUNT (DISTINCT makerCode) AS peopleTotal");
        sql.append("        ,SUM (browseCount) AS browseCountTotal");
        sql.append("        ,SUM (downloadCount) AS downloadCountTotal");
        sql.append("        ,SUM (referCount) AS referCountTotal");
        sql.append("        ,SUM (favoriteCount) AS favoriteCountTotal");
        sql.append("        ,SUM (goodCount) AS goodCountTotal");
        sql.append("        ,SUM (commentCount) AS commentCountTotal ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rd.makerName");
        sql.append("            ,rd.makerCode");
        sql.append("            ,rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,SUM (rd.browseCount) AS browseCount");
        sql.append("            ,SUM (rd.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rd.referCount) AS referCount");
        sql.append("            ,SUM (rd.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rd.goodCount) AS goodCount");
        sql.append("            ,SUM (rd.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rd.resCode = rrs.resCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        sql.append("            AND rd.resStatus = 20 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rrs.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }else{
            sql.append("        AND rrs.sectionCode is null ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND rd.makerOrgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,rd.makerName ");
        sql.append("            ,rd.makerCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,SUM (rm.browseCount) AS browseCount");
        sql.append("            ,SUM (rm.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rm.referCount) AS referCount");
        sql.append("            ,SUM (rm.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rm.goodCount) AS goodCount");
        sql.append("            ,SUM (rm.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rm.resCode = rrs.resCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        sql.append("            AND rm.resStatus = 20 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rrs.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }else{
            sql.append("        AND rrs.sectionCode is null ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND rm.makerOrgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.makerName");
        sql.append("            ,rm.makerCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,SUM (e.browseCount) AS browseCount");
        sql.append("            ,SUM (e.downloadCount) AS downloadCount");
        sql.append("            ,SUM (e.referCount) AS referCount");
        sql.append("            ,SUM (e.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (e.goodCount) AS goodCount");
        sql.append("            ,SUM (e.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section res ");
        sql.append("                ON res.testCode = e.testCode ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        sql.append("           AND e.scenarioType in(10,20,40)  ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND res.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }else{
            sql.append("        AND res.sectionCode is null ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND e.makerOrgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            e.makerName");
        sql.append("            ,e.makerCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,SUM (q.browseCount) AS browseCount");
        sql.append("            ,SUM (q.downloadCount) AS downloadCount");
        sql.append("            ,SUM (q.referCount) AS referCount");
        sql.append("            ,SUM (q.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (q.goodCount) AS goodCount");
        sql.append("            ,SUM (q.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section rrse ");
        sql.append("                ON rrse.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        sql.append("            AND q.questionSource IN (10,20,30,40) ");
        sql.append("            AND q.status = 10 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND rrse.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }else{
            sql.append("        AND rrse.sectionCode is null ");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("        AND q.makerOrgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerName");
        sql.append("            ,q.makerCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("    ) res ");
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

}
