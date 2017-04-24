package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.OrgResOperateDailyDao;
import com.baizhitong.resource.model.report.OrgResOperateDaily;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 机构_日次_资源操作统计DAO实现
 * 
 * @author creator zhangqiang 2016年7月26日 下午2:09:08
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class OrgResOperateDailyDaoImpl extends BaseSqlServerDao<OrgResOperateDaily> implements OrgResOperateDailyDao {

    /**
     * 
     * (查询机构使用情况统计)<br>
     * 
     * @param param 查询参数
     * @param page 当前页数
     * @param rows 每页大小
     * @return
     */
    public Page getOrgDaily(Map<String, Object> param, Integer page, Integer rows) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        makerOrgName AS orgName");
        sql.append("        ,makerOrgCode AS orgCode");
        sql.append(" ,isNull(sectionCode,'') AS sectionCode");
        sql.append(" ,isNull(sectionName,'无') AS sectionName");
        sql.append("        ,isNull(SUM (browseCount),0)  AS browseCount");
        sql.append("        ,isNull(SUM (downloadCount),0)  AS downloadCount");
        sql.append("        ,isNull(SUM (referCount),0)  AS referCount");
        sql.append("        ,isNull(SUM (favoriteCount),0)  AS favoriteCount");
        sql.append("        ,isNull( SUM (goodCount),0) AS goodCount");
        sql.append("        ,isNull(SUM (commentCount) ,0) AS commentCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,SUM (rd.browseCount) AS browseCount");
        sql.append("            ,SUM (rd.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rd.referCount) AS referCount");
        sql.append("            ,SUM (rd.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rd.goodCount) AS goodCount");
        sql.append("            ,SUM (rd.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        // sql.append(" LEFT JOIN share_org_section sos ON sos.orgCode = rd.makerOrgCode ");
        sql.append(" LEFT JOIN");
        sql.append(" rel_res_section rrs ");
        sql.append(" ON rd.resCode = rrs.resCode ");
        sql.append(" LEFT JOIN");
        sql.append(" share_code_section scs ");
        sql.append(" ON rrs.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        sql.append("            AND rd.resStatus = 20 ");

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND rrs.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND rd.makerOrgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
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
        sql.append("            ,rrs.sectionCode");
        sql.append(" ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append(" ,scs.name AS sectionName");
        sql.append("            ,SUM (rm.browseCount) AS browseCount");
        sql.append("            ,SUM (rm.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rm.referCount) AS referCount");
        sql.append("            ,SUM (rm.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rm.goodCount) AS goodCount");
        sql.append("            ,SUM (rm.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        // sql.append(" LEFT JOIN share_org_section sos ON sos.orgCode = rm.makerOrgCode ");
        sql.append(" LEFT JOIN");
        sql.append(" rel_res_section rrs ");
        sql.append(" ON rm.resCode = rrs.resCode ");
        sql.append(" LEFT JOIN");
        sql.append(" share_code_section scs ");
        sql.append(" ON rrs.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        sql.append("            AND rm.resStatus = 20 ");

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND rrs.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND rm.makerOrgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append(" ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,res.sectionCode");
        sql.append(" ,scs.name AS sectionName");
        sql.append("            ,SUM (e.browseCount) AS browseCount");
        sql.append("            ,SUM (e.downloadCount) AS downloadCount");
        sql.append("            ,SUM (e.referCount) AS referCount");
        sql.append("            ,SUM (e.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (e.goodCount) AS goodCount");
        sql.append("            ,SUM (e.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        // sql.append(" LEFT JOIN share_org_section sos ON sos.orgCode = e.makerOrgCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section res ");
        sql.append("                ON res.testCode = e.testCode ");
        sql.append(" LEFT JOIN");
        sql.append(" share_code_section scs ");
        sql.append(" ON res.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        sql.append("           AND e.scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND res.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND e.makerOrgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate ");
        }

        sql.append("        GROUP BY");
        sql.append("            e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,res.sectionCode");
        sql.append(" ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,rrse.sectionCode");
        sql.append(" ,scs.name AS sectionName");
        sql.append("            ,SUM (q.browseCount) AS browseCount");
        sql.append("            ,SUM (q.downloadCount) AS downloadCount");
        sql.append("            ,SUM (q.referCount) AS referCount");
        sql.append("            ,SUM (q.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (q.goodCount) AS goodCount");
        sql.append("            ,SUM (q.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        // sql.append(" LEFT JOIN share_org_section sos ON sos.orgCode = q.makerOrgCode ");
        sql.append(" LEFT JOIN");
        sql.append(" rel_question_section rrse ");
        sql.append(" ON rrse.questionCode = q.questionCode ");
        sql.append(" LEFT JOIN");
        sql.append(" share_code_section scs ");
        sql.append(" ON rrse.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        sql.append("           AND q.status = 10 ");
        sql.append("    and (q.questionSource=10 or q.questionSource=20 or q.questionSource=30 or q.questionSource=40)");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND rrse.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND q.makerOrgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,rrse.sectionCode");
        sql.append(" ,scs.name ");
        sql.append("    ) res ");
        sql.append("    ");
        sql.append("GROUP BY");
        sql.append("    makerOrgName");
        sql.append("    ,makerOrgCode");
        sql.append("    ,sectionCode");
        sql.append(" ,sectionName");
        sql.append(" ORDER BY res.makerOrgName ASC ");
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
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        COUNT (1) AS schoolTotal");
        sql.append("        ,SUM (browseCount) AS browseCountTotal");
        sql.append("        ,SUM (downloadCount) AS downloadCountTotal");
        sql.append("        ,SUM (referCount) AS referCountTotal");
        sql.append("        ,SUM (favoriteCount) AS favoriteCountTotal");
        sql.append("        ,SUM (goodCount) AS goodCountTotal");
        sql.append("        ,SUM (commentCount) AS commentCountTotal ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("        makerOrgName AS orgName");
        sql.append("        ,makerOrgCode AS orgCode");
        sql.append("        ,sectionCode");
        sql.append(" ,isNull(sectionName,'无') AS sectionName");
        sql.append("        ,isNull(SUM (browseCount),0)  AS browseCount");
        sql.append("        ,isNull(SUM (downloadCount),0)  AS downloadCount");
        sql.append("        ,isNull(SUM (referCount),0)  AS referCount");
        sql.append("        ,isNull(SUM (favoriteCount),0)  AS favoriteCount");
        sql.append("        ,isNull( SUM (goodCount),0) AS goodCount");
        sql.append("        ,isNull(SUM (commentCount) ,0) AS commentCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append(" ,scs.name AS sectionName");
        sql.append("            ,SUM (rd.browseCount) AS browseCount");
        sql.append("            ,SUM (rd.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rd.referCount) AS referCount");
        sql.append("            ,SUM (rd.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rd.goodCount) AS goodCount");
        sql.append("            ,SUM (rd.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        // sql.append(" LEFT JOIN share_org_section sos ON sos.orgCode = rd.makerOrgCode ");
        sql.append(" LEFT JOIN");
        sql.append(" rel_res_section rrs ");
        sql.append(" ON rd.resCode = rrs.resCode ");
        sql.append(" LEFT JOIN");
        sql.append(" share_code_section scs ");
        sql.append(" ON rrs.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        sql.append("            AND rd.resStatus = 20 ");

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND rrs.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND rd.makerOrgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
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
        sql.append("            ,rrs.sectionCode");
        sql.append(" ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append(" ,scs.name AS sectionName");
        sql.append("            ,SUM (rm.browseCount) AS browseCount");
        sql.append("            ,SUM (rm.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rm.referCount) AS referCount");
        sql.append("            ,SUM (rm.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rm.goodCount) AS goodCount");
        sql.append("            ,SUM (rm.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        // sql.append(" LEFT JOIN share_org_section sos ON sos.orgCode = rm.makerOrgCode ");
        sql.append(" LEFT JOIN");
        sql.append(" rel_res_section rrs ");
        sql.append(" ON rm.resCode = rrs.resCode ");
        sql.append(" LEFT JOIN");
        sql.append(" share_code_section scs ");
        sql.append(" ON rrs.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        sql.append("            AND rm.resStatus = 20 ");

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND rrs.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND rm.makerOrgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append(" ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,res.sectionCode");
        sql.append(" ,scs.name AS sectionName");
        sql.append("            ,SUM (e.browseCount) AS browseCount");
        sql.append("            ,SUM (e.downloadCount) AS downloadCount");
        sql.append("            ,SUM (e.referCount) AS referCount");
        sql.append("            ,SUM (e.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (e.goodCount) AS goodCount");
        sql.append("            ,SUM (e.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        // sql.append(" LEFT JOIN share_org_section sos ON sos.orgCode = e.makerOrgCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section res ");
        sql.append("                ON res.testCode = e.testCode ");
        sql.append(" LEFT JOIN");
        sql.append(" share_code_section scs ");
        sql.append(" ON res.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        sql.append("           AND e.scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND res.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND e.makerOrgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate ");
        }

        sql.append("        GROUP BY");
        sql.append("            e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,res.sectionCode");
        sql.append(" ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,rrse.sectionCode");
        sql.append(" ,scs.name AS sectionName");
        sql.append("            ,SUM (q.browseCount) AS browseCount");
        sql.append("            ,SUM (q.downloadCount) AS downloadCount");
        sql.append("            ,SUM (q.referCount) AS referCount");
        sql.append("            ,SUM (q.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (q.goodCount) AS goodCount");
        sql.append("            ,SUM (q.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        // sql.append(" LEFT JOIN share_org_section sos ON sos.orgCode = q.makerOrgCode ");
        sql.append(" LEFT JOIN");
        sql.append(" rel_question_section rrse ");
        sql.append(" ON rrse.questionCode = q.questionCode ");
        sql.append(" LEFT JOIN");
        sql.append(" share_code_section scs ");
        sql.append(" ON rrse.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        sql.append("           AND q.status = 10 ");
        sql.append("    and (q.questionSource=10 or q.questionSource=20 or q.questionSource=30 or q.questionSource=40)");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND rrse.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND q.makerOrgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,rrse.sectionCode");
        sql.append(" ,scs.name ");
        sql.append("    ) res ");
        sql.append("GROUP BY");
        sql.append("    makerOrgName");
        sql.append("    ,makerOrgCode");
        sql.append("    ,sectionCode");
        sql.append(" ,sectionName");
        sql.append("    ) res ");
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * (根据机构编码查询机构使用情况统计)<br>
     * 
     * @param param
     * @return
     */
    @Override
    public List<Map<String, Object>> getOrgDailyByOrgCode(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String orgCode = MapUtils.getString(param, "orgCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("orgCode", orgCode);
        sql.append("SELECT");
        sql.append("        makerOrgName AS orgName");
        sql.append("        ,makerOrgCode AS orgCode");
        sql.append(" ,isNull(sectionCode,'') AS sectionCode");
        sql.append(" ,isNull(scs.name,'无') AS sectionName");
        sql.append("        ,isNull(SUM (browseCount),0)  AS browseCount");
        sql.append("        ,isNull(SUM (downloadCount),0)  AS downloadCount");
        sql.append("        ,isNull(SUM (referCount),0)  AS referCount");
        sql.append("        ,isNull(SUM (favoriteCount),0)  AS favoriteCount");
        sql.append("        ,isNull( SUM (goodCount),0) AS goodCount");
        sql.append("        ,isNull(SUM (commentCount) ,0) AS commentCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,SUM (rd.browseCount) AS browseCount");
        sql.append("            ,SUM (rd.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rd.referCount) AS referCount");
        sql.append("            ,SUM (rd.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rd.goodCount) AS goodCount");
        sql.append("            ,SUM (rd.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("      LEFT JOIN rel_res_section rrs ON rrs.resCode = rd.resCode   ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        sql.append("            AND rd.resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND rd.makerOrgCode = :orgCode ");
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
        sql.append("            ,rrs.sectionCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,SUM (rm.browseCount) AS browseCount");
        sql.append("            ,SUM (rm.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rm.referCount) AS referCount");
        sql.append("            ,SUM (rm.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rm.goodCount) AS goodCount");
        sql.append("            ,SUM (rm.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("      LEFT JOIN rel_res_section rrs ON rrs.resCode = rm.resCode   ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        sql.append("            AND rm.resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND rm.makerOrgCode = :orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,rrs.sectionCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,res.sectionCode");
        sql.append("            ,SUM (e.browseCount) AS browseCount");
        sql.append("            ,SUM (e.downloadCount) AS downloadCount");
        sql.append("            ,SUM (e.referCount) AS referCount");
        sql.append("            ,SUM (e.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (e.goodCount) AS goodCount");
        sql.append("            ,SUM (e.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("          LEFT JOIN rel_exercise_section res ON res.testCode = e.testCode   ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        sql.append("           AND e.scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND e.makerOrgCode = :orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,res.sectionCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,rqs.sectionCode");
        sql.append("            ,SUM (q.browseCount) AS browseCount");
        sql.append("            ,SUM (q.downloadCount) AS downloadCount");
        sql.append("            ,SUM (q.referCount) AS referCount");
        sql.append("            ,SUM (q.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (q.goodCount) AS goodCount");
        sql.append("            ,SUM (q.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("      LEFT JOIN rel_question_section rqs ON rqs.questionCode = q.questionCode   ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        sql.append("    AND q.status = 10 ");
        sql.append("    AND (");
        sql.append("        q.questionSource = 10 ");
        sql.append("        OR q.questionSource = 20 ");
        sql.append("        OR q.questionSource = 30 ");
        sql.append("        OR q.questionSource = 40 ");
        sql.append("    )");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND q.makerOrgCode = :orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,rqs.sectionCode");
        sql.append("    ) res ");
        sql.append(" LEFT JOIN share_code_section scs ON scs.code = res.sectionCode ");
        sql.append("GROUP BY");
        sql.append("    makerOrgName");
        sql.append("    ,makerOrgCode");
        sql.append("    ,sectionCode");
        sql.append("    ,scs.name");
        sql.append(" ORDER BY res.makerOrgName ASC ");
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * (查询总数-机构管理员)<br>
     * 
     * @param param
     * @return
     */
    @Override
    public Map<String, Object> selectByOrgCode(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String orgCode = MapUtils.getString(param, "orgCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("orgCode", orgCode);
        sql.append("SELECT");
        sql.append("        COUNT (DISTINCT makerOrgCode) AS schoolTotal");
        sql.append("        ,SUM (browseCount) AS browseCountTotal");
        sql.append("        ,SUM (downloadCount) AS downloadCountTotal");
        sql.append("        ,SUM (referCount) AS referCountTotal");
        sql.append("        ,SUM (favoriteCount) AS favoriteCountTotal");
        sql.append("        ,SUM (goodCount) AS goodCountTotal");
        sql.append("        ,SUM (commentCount) AS commentCountTotal ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rd.makerOrgName");
        sql.append("            ,rd.makerOrgCode");
        sql.append("            ,SUM (rd.browseCount) AS browseCount");
        sql.append("            ,SUM (rd.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rd.referCount) AS referCount");
        sql.append("            ,SUM (rd.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rd.goodCount) AS goodCount");
        sql.append("            ,SUM (rd.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("      LEFT JOIN rel_res_section rrs ON rrs.resCode = rd.resCode   ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        sql.append("            AND rd.resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND rd.makerOrgCode = :orgCode ");
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
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("            ,SUM (rm.browseCount) AS browseCount");
        sql.append("            ,SUM (rm.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rm.referCount) AS referCount");
        sql.append("            ,SUM (rm.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rm.goodCount) AS goodCount");
        sql.append("            ,SUM (rm.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("      LEFT JOIN rel_res_section rrs ON rrs.resCode = rm.resCode   ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        sql.append("            AND rm.resStatus = 20 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND rm.makerOrgCode = :orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.makerOrgName");
        sql.append("            ,rm.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("            ,SUM (e.browseCount) AS browseCount");
        sql.append("            ,SUM (e.downloadCount) AS downloadCount");
        sql.append("            ,SUM (e.referCount) AS referCount");
        sql.append("            ,SUM (e.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (e.goodCount) AS goodCount");
        sql.append("            ,SUM (e.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("      LEFT JOIN rel_exercise_section res ON res.testCode = e.testCode   ");
        // sql.append(" LEFT JOIN");
        // sql.append(" share_code_section scs ");
        // sql.append(" ON e.sectionCode = scs.code ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        sql.append("           AND e.scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND e.makerOrgCode = :orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            e.makerOrgName");
        sql.append("            ,e.makerOrgCode");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("            ,SUM (q.browseCount) AS browseCount");
        sql.append("            ,SUM (q.downloadCount) AS downloadCount");
        sql.append("            ,SUM (q.referCount) AS referCount");
        sql.append("            ,SUM (q.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (q.goodCount) AS goodCount");
        sql.append("            ,SUM (q.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("      LEFT JOIN rel_question_section rqs ON rqs.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND q.makerOrgCode = :orgCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate ");
        }
        sql.append("        GROUP BY");
        sql.append("            q.makerOrgName");
        sql.append("            ,q.makerOrgCode");
        sql.append("    ) res ");
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

}
