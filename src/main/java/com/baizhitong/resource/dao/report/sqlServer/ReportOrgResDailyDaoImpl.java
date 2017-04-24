package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.ReportOrgResDailyDao;
import com.baizhitong.resource.model.report.OrgResMakeDaily;

@Service
public class ReportOrgResDailyDaoImpl extends BaseSqlServerDao<OrgResMakeDaily> implements ReportOrgResDailyDao {

    /**
     * 查询机构资源每日统计 ()<br>
     * 
     * @param page
     * @param rows
     * @param sqlParam
     * @return
     */
    public Page getReportOrgResDaily(Integer page, Integer rows, Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String orgName = MapUtils.getString(param, "orgName");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        res.makerOrgCode AS orgCode");
        sql.append("        ,res.makerOrgName AS orgName");
        sql.append("        ,res.sectionCode");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,SUM (res.rmCount) AS mediaTotal");
        sql.append("        ,SUM (res.rdCount) AS docTotal");
        sql.append("        ,SUM (res.testCount) AS exerciseTotal");
        sql.append("        ,SUM (res.qCount)  AS  questionTotal");
        sql.append("        ,(  SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount)  ) AS resTotal ");
        sql.append("    FROM");
        sql.append("        (  SELECT");
        sql.append("            rm.makerOrgCode");
        sql.append("            ,rm.makerOrgName");
        sql.append("            ,se.sectionCode");
        sql.append("            ,COUNT (rm.resCode) rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount  ");
        sql.append("        FROM");
        sql.append("            res_media rm  ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rm.resCode  ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0  ");
        sql.append("           AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate  ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate  ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and rm.makerOrgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }

        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rm.makerOrgCode");
        sql.append("            ,rm.makerOrgName  ");
        sql.append("            ,se.sectionCode");
        sql.append("        UNION");
        sql.append("        ALL  SELECT");
        sql.append("            rd.makerOrgCode");
        sql.append("            ,rd.makerOrgName");
        sql.append("            ,se.sectionCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,COUNT (rd.resCode) rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount  ");
        sql.append("        FROM");
        sql.append("            res_doc rd  ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rd.resCode  ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0  ");
        sql.append("           AND resStatus = 20 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startDate   ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endDate   ");
        }

        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and rd.makerOrgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rd.makerOrgCode");
        sql.append("            ,rd.makerOrgName  ");
        sql.append("            ,se.sectionCode");
        sql.append("        UNION");
        sql.append("        ALL  SELECT");
        sql.append("            e.makerOrgCode");
        sql.append("            ,e.makerOrgName");
        sql.append("            ,se.sectionCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,COUNT (e.testCode) testCount");
        sql.append("            ,0 AS qCount  ");
        sql.append("        FROM");
        sql.append("            exercise e  ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON se.testCode = e.testCode  ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0  ");
        sql.append("           AND e.scenarioType in(10,20,40)  ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate   ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate   ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and e.makerOrgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            e.makerOrgCode");
        sql.append("            ,e.makerOrgName  ");
        sql.append("            ,se.sectionCode");
        sql.append("        UNION");
        sql.append("        ALL  SELECT");
        sql.append("            q.makerOrgCode");
        sql.append("            ,q.makerOrgName");
        sql.append("            ,se.sectionCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,COUNT (q.questionCode) qCount  ");
        sql.append("        FROM");
        sql.append("            question q  ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section se ");
        sql.append("                ON se.questionCode = q.questionCode  ");
        sql.append("        WHERE");
        sql.append("               flagDelete = 0 ");
        sql.append("           and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        sql.append("           AND status = 10 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate   ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate   ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and q.makerOrgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and se.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            q.makerOrgCode");
        sql.append("            ,q.makerOrgName  ");
        sql.append("            ,se.sectionCode");
        sql.append("    ) res ");
        sql.append(" LEFT JOIN share_code_section scs  ON res.sectionCode =  scs.code  ");
        sql.append("GROUP BY");
        sql.append("    res.makerOrgCode");
        sql.append("    ,res.makerOrgName");
        sql.append(" ,res.sectionCode");
        sql.append(" ,scs.name");
        //
        sql.append("  ORDER by (  SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount)  ) DESC ");

        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 查询文档列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getDocList(Map<String, Object> param, Integer page, Integer rows) {

        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String textbookVerCode = MapUtils.getString(param, "textbookVerCode");
        String chapterCode = MapUtils.getString(param, "chapterCode");
        String kpCode = MapUtils.getString(param, "kpCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT rd.id,rd.resCode,rd.resName, rd.suffix, rd.coverPath, rd.browseCount, rd.downloadCount, rd.referCount,rd.shareLevel, ");
        sql.append(" rd.favoriteCount, rd.goodCount, rd.badCount,  rd.commentCount, rd.resDesc, rd.makeTime ,rd.makerName,rd.makerOrgName, ");
        sql.append(" scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName,st2.name as resTypeL2Name ");
        sql.append(",(case rd.shareLevel when 10 then '个人私有' when 20 then '校内共享' when 30 then '区内共享' end ) as shareLevelName ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN");
        sql.append("    share_res_type_l2 st2 ");
        sql.append("        on st2.code=rd.resTypeL2");
        // sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rd.resTypeL1
        // AND rrk.resId =
        // rd.id ");
        // sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON
        // skpt.textbookVerCode =
        // sctv.code AND skpt.code = rrk.kpCode ");
        // sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 =
        // rd.resTypeL1 AND rrt2.resId =
        // rd.id ");
        // sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode
        // = sctv.code AND
        // stc.code = rrt2.tbcCode ");
        sql.append(" WHERE   rd.flagDelete=0 and  rd.resStatus=20 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" AND rrg.gradeCode = :gradeCode ");
        }
        
          // 判断学科编码是否为空
          if (!StringUtils.isEmpty(subjectCode)) { 
          sql.append("AND rrs.subjectCode = :subjectCode "); 
          }
         
        // 判断版本编码是否为空
        if (!StringUtils.isEmpty(textbookVerCode)) {
            sql.append(" AND rrt.tbvCode = :versionCode ");
        }
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND rd.resTypeL2 = :resTypeL2 ");
        }

        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(chapterCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND rd.id IN(SELECT resId FROM rel_res_tbc WHERE resTypeL1 = 20 AND chapterCode LIKE '"
                            + chapterCode + "%') ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
            sql.append(" AND rd.id IN(SELECT resId FROM rel_res_kp WHERE resTypeL1 = 20 AND kpCode LIKE '" + kpCode
                            + "%') ");
        }
        // 分享级别
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append(" AND rd.shareLevel = :shareLevel ");
        }
        // 机构
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rd.makerOrgCode=:orgCode ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and rd.makeTime >=:startDate ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and rd.makeTime <=:endDate ");
        }
        sql.append(" ORDER BY rd.makeTime DESC ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", textbookVerCode);
        map.put("tbcCode", chapterCode);
        map.put("kpCode", kpCode);
        map.put("resTypeL2", resTypeL2);
        map.put("shareLevel", shareLevel);
        map.put("orgCode", orgCode);
        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, page, rows);
    }

    /**
     * 查询文档列表 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getDocSum(Map<String, Object> param) {

        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String textbookVerCode = MapUtils.getString(param, "textbookVerCode");
        String chapterCode = MapUtils.getString(param, "chapterCode");
        String kpCode = MapUtils.getString(param, "kpCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT count( *) as count,sum(favoriteCount)as favoriteCount,sum(goodCount)as goodCount,sum(badCount)as badCount,sum(commentCount)as commentCount ,sum(downloadCount) as downloadCount,sum(browseCount) as browseCount,sum(referCount) as referCount ");
        sql.append(" FROM res_doc rd ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rd.resTypeL1 AND rrse.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rd.resTypeL1 AND rrs.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rd.resTypeL1 AND rrg.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rd.resTypeL1 AND rrt.resId = rd.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN");
        sql.append("    share_res_type_l2 st2 ");
        sql.append("        on st2.code=rd.resTypeL2");
        // sql.append(" LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rd.resTypeL1
        // AND rrk.resId =
        // rd.id ");
        // sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON
        // skpt.textbookVerCode =
        // sctv.code AND skpt.code = rrk.kpCode ");
        // sql.append(" LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 =
        // rd.resTypeL1 AND rrt2.resId =
        // rd.id ");
        // sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.textBookCode
        // = sctv.code AND
        // stc.code = rrt2.tbcCode ");
        sql.append(" WHERE   rd.flagDelete=0 and  rd.resStatus=20 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" AND rrg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode)) { 
        sql.append("AND rrs.subjectCode = :subjectCode "); 
        }
        // 判断版本编码是否为空
        if (!StringUtils.isEmpty(textbookVerCode)) {
            sql.append(" AND rrt.tbvCode = :versionCode ");
        }
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND rd.resTypeL2 = :resTypeL2 ");
        }

        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(chapterCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND rd.id IN(SELECT resId FROM rel_res_tbc WHERE resTypeL1 = 20 AND chapterCode LIKE '"
                            + chapterCode + "%') ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
            sql.append(" AND rd.id IN(SELECT resId FROM rel_res_kp WHERE resTypeL1 = 20 AND kpCode LIKE '" + kpCode
                            + "%') ");
        }
        // 分享级别
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append(" AND rd.shareLevel = :shareLevel ");
        }
        // 机构
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rd.makerOrgCode=:orgCode ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and rd.makeTime >=:startDate ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and rd.makeTime <=:endDate ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", textbookVerCode);
        map.put("tbcCode", chapterCode);
        map.put("kpCode", kpCode);
        map.put("resTypeL2", resTypeL2);
        map.put("shareLevel", shareLevel);
        map.put("orgCode", orgCode);
        // 执行SQL
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 查询视频列表 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getMediaSum(Map<String, Object> param) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String textbookVerCode = MapUtils.getString(param, "textbookVerCode");
        String chapterCode = MapUtils.getString(param, "chapterCode");
        String kpCode = MapUtils.getString(param, "kpCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        sql.append(" SELECT count(*) as count ,sum(favoriteCount)as favoriteCount,sum(goodCount)as goodCount,sum(badCount)as badCount,sum(commentCount)as commentCount ,sum(downloadCount) as downloadCount,sum(browseCount) as browseCount,sum(referCount) as referCount ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN");
        sql.append("    share_res_type_l1 st1 ");
        sql.append("        on st1.code=rm.resTypeL1  ");
        sql.append(" LEFT JOIN");
        sql.append("    share_res_type_l2 st2 ");
        sql.append("        on st2.code=rm.resTypeL2");
        /*
         * sql.append(
         * " LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rm.resTypeL1 AND rrk.resId = rm.id " );
         * sql.append(
         * " LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode "
         * ); sql.append(
         * " LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rm.resTypeL1 AND rrt2.resId = rm.id " );
         * sql.append(
         * " LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode "
         * );
         */
        sql.append("  where 1=1  and rm.flagDelete=0   AND rm.resStatus=20 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" AND rrg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }
        /*
         * // 判断版本编码是否为空 if (!StringUtils.isEmpty(textbookVerCode)) { sql.append(
         * " AND rrt.tbvCode = :versionCode "); }
         */
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND rm.resTypeL2 = :resTypeL2 ");
        }

        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(chapterCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND rm.id IN(SELECT resId FROM rel_res_tbc WHERE resTypeL1 = 10 AND chapterCode LIKE '"
                            + chapterCode + "%') ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
            sql.append(" AND rm.id IN(SELECT resId FROM rel_res_kp WHERE resTypeL1 = 10 AND kpCode LIKE '" + kpCode
                            + "%') ");
        }
        // 分享级别
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append(" AND rm.shareLevel = :shareLevel ");
        }
        // 机构
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rm.makerOrgCode=:orgCode ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and rm.makeTime >=:startDate ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and rm.makeTime <=:endDate ");
        }
        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("shareLevel", shareLevel);
        map.put("orgCode", orgCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", textbookVerCode);
        map.put("tbcCode", chapterCode);
        map.put("kpCode", kpCode);
        map.put("resTypeL2", resTypeL2);

        // 执行SQL
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 查询视频列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getMediaList(Map<String, Object> param, Integer page, Integer rows) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String textbookVerCode = MapUtils.getString(param, "textbookVerCode");
        String chapterCode = MapUtils.getString(param, "chapterCode");
        String kpCode = MapUtils.getString(param, "kpCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        sql.append(" SELECT  rm.id, rm.resCode,rm.resName, rm.mediaDurationMS, rm.thumbnailPath, rm.trialTimeRate, rm.referCount,rm.browseCount, rm.favoriteCount,rm.downloadCount, rm.goodCount, rm.shareLevel,");
        sql.append(" rm.commentCount, rm.makeTime,rm.makerName,rm.makerOrgName, scse.name AS sectionName, scs.name AS subjectName, scg.name AS gradeName, sctv.name AS textbookVerName ");
        sql.append(",st2.name as resTypeL2Name");
        sql.append(",(case rm.shareLevel when 10 then '个人私有' when 20 then '校内共享' when 30 then '区内共享' end ) as shareLevelName ");
        sql.append(" FROM res_media rm ");
        sql.append(" LEFT JOIN rel_res_section rrse ON rrse.resTypeL1 = rm.resTypeL1 AND rrse.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_section scse ON scse.code = rrse.sectionCode ");
        sql.append(" LEFT JOIN rel_res_subject rrs ON rrs.resTypeL1 = rm.resTypeL1 AND rrs.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
        sql.append(" LEFT JOIN rel_res_grade rrg ON rrg.resTypeL1 = rm.resTypeL1 AND rrg.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" LEFT JOIN rel_res_tbv rrt ON rrt.resTypeL1 = rm.resTypeL1 AND rrt.resId = rm.id ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" LEFT JOIN");
        sql.append("    share_res_type_l1 st1 ");
        sql.append("        on st1.code=rm.resTypeL1  ");
        sql.append(" LEFT JOIN");
        sql.append("    share_res_type_l2 st2 ");
        sql.append("        on st2.code=rm.resTypeL2");
        /*
         * sql.append(
         * " LEFT JOIN rel_res_kp rrk ON rrk.resTypeL1 = rm.resTypeL1 AND rrk.resId = rm.id " );
         * sql.append(
         * " LEFT JOIN share_knowledge_point_textbook skpt ON skpt.textbookVerCode = sctv.code AND skpt.code = rrk.kpCode "
         * ); sql.append(
         * " LEFT JOIN rel_res_tbc rrt2 ON rrt2.resTypeL1 = rm.resTypeL1 AND rrt2.resId = rm.id " );
         * sql.append(
         * " LEFT JOIN share_textbook_chapter stc ON stc.textBookCode = sctv.code AND stc.code = rrt2.tbcCode "
         * );
         */
        sql.append("  where 1=1  and rm.flagDelete=0   AND rm.resStatus=20 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rrse.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" AND rrg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" AND rrs.subjectCode = :subjectCode ");
        }
        /*
         * // 判断版本编码是否为空 if (!StringUtils.isEmpty(textbookVerCode)) { sql.append(
         * " AND rrt.tbvCode = :versionCode "); }
         */
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND rm.resTypeL2 = :resTypeL2 ");
        }

        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(chapterCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND rm.id IN(SELECT resId FROM rel_res_tbc WHERE resTypeL1 = 10 AND chapterCode LIKE '"
                            + chapterCode + "%') ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
            sql.append(" AND rm.id IN(SELECT resId FROM rel_res_kp WHERE resTypeL1 = 10 AND kpCode LIKE '" + kpCode
                            + "%') ");
        }
        // 分享级别
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append(" AND rm.shareLevel = :shareLevel ");
        }
        // 机构
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rm.makerOrgCode=:orgCode ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and rm.makeTime >=:startDate ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and rm.makeTime <=:endDate ");
        }
        sql.append(" ORDER BY rm.makeTime DESC ");
        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("shareLevel", shareLevel);
        map.put("orgCode", orgCode);
        map.put("subjectCode", subjectCode);
        map.put("versionCode", textbookVerCode);
        map.put("tbcCode", chapterCode);
        map.put("kpCode", kpCode);
        map.put("resTypeL2", resTypeL2);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, page, rows);
    }

    /**
     * 查询题目列表 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getQuestionSum(Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String chapterCode = MapUtils.getString(param, "chapterCode");
        String kpCode = MapUtils.getString(param, "kpCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        sql.append(" SELECT count( *) as count,sum(favoriteCount)as favoriteCount,sum(goodCount)as goodCount,sum(badCount)as badCount,sum(commentCount)as commentCount ,sum(downloadCount) as downloadCount,sum(browseCount) as browseCount,sum(referCount) as referCount ");
        sql.append("    FROM");
        sql.append("        question a   ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_question_grade rqg ");
        sql.append("            on rqg.questionCode=a.questionCode   ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            on scg.code=rqg.gradeCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_question_section rqs ");
        sql.append("            on rqs.questionCode=a.questionCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            on scs.code=rqs.sectionCode  ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsubject ");
        sql.append("            on scsubject.code=a.subjectCode  ");
        sql.append("    LEFT JOIN");
        sql.append("       share_question_type qt on qt.code=a.questionType ");
        sql.append("    WHERE");
        sql.append("        a.flagDelete=0 ");
        sql.append("        AND a.questionSource IN(10,20,30,40) ");
        sql.append("        AND a.status = 10 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rqs.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" AND rqg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" AND a.subjectCode = :subjectCode ");
        }
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND rm.resTypeL2 = :resTypeL2 ");
        }

        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(chapterCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND a.questionCode IN(SELECT questionCode FROM rel_question_chapter WHERE  chapterCode LIKE '"
                            + chapterCode + "%') ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
            sql.append(" AND a.questionCode IN(SELECT questionCode FROM rel_question_kp WHERE kpCode LIKE '" + kpCode
                            + "%') ");
        }
        // 分享级别
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append(" AND a.shareLevel = :shareLevel ");
        }
        // 机构
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and a.makerOrgCode=:orgCode ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and a.makeTime >= :startDate ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and a.makeTime <= :endDate ");
        }
        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("shareLevel", shareLevel);
        map.put("orgCode", orgCode);
        map.put("subjectCode", subjectCode);
        map.put("tbcCode", chapterCode);
        map.put("kpCode", kpCode);
        // 执行SQL
        return super.findBySql(sql.toString(), map);

    }

    /**
     * 查询试卷列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public List<Map<String, Object>> getExerciseSum(Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String chapterCode = MapUtils.getString(param, "chapterCode");
        String kpCode = MapUtils.getString(param, "kpCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        sql.append(" SELECT count( *) as count,sum(favoriteCount)as favoriteCount,sum(goodCount)as goodCount,sum(badCount)as badCount,sum(commentCount)as commentCount ,sum(downloadCount) as downloadCount,sum(browseCount) as browseCount,sum(referCount) as referCount ");
        sql.append("    FROM");
        sql.append("        test a   ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_exercise_grade reg ");
        sql.append("            on a.testCode=reg.testCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            on scg.code=reg.gradeCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsubject ");
        sql.append("            on scsubject.code=a.subjectCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_exercise_section res ");
        sql.append("            on a.testCode=res.testCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            on scs.code=res.sectionCode   ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l2 st2 ");
        sql.append("            on st2.code=a.resTypeL2");
        sql.append(" where 1=1 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND a.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" AND reg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" AND a.subjectCode = :subjectCode ");
        }

        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND a.resTypeL2 = :resTypeL2 ");
        }

        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND a.testCode IN(SELECT testCode FROM rel_exercise_kp WHERE kpCode LIKE '" + kpCode + "%') ");
        }
        if (!StringUtils.isEmpty(chapterCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND a.testCode IN(SELECT testCode FROM rel_exercise_chapter WHERE chapterCode LIKE '"
                            + chapterCode + "%') ");
        }
        // 分享级别
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append(" AND a.shareLevel = :shareLevel ");
        }
        // 机构
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and a.makerOrgCode=:orgCode ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and a.makeTime >=:startDate ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and a.makeTime <=:endDate ");
        }
        sql.append("    AND a.flagDelete = 0 ");
        sql.append("    AND a.scenarioType in (10,20,40)");
        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("shareLevel", shareLevel);
        map.put("orgCode", orgCode);
        map.put("subjectCode", subjectCode);
        map.put("tbcCode", chapterCode);
        map.put("kpCode", kpCode);
        map.put("resTypeL2", resTypeL2);

        // 执行SQL
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 查询题目列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getQuestionList(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String chapterCode = MapUtils.getString(param, "chapterCode");
        String kpCode = MapUtils.getString(param, "kpCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        sql.append("SELECT");
        sql.append("        '题目' as resName");
        sql.append("        ,a.id");
        sql.append("        ,a.subjectCode");
        sql.append("        ,a.questionCode");
        sql.append("        ,a.questionCode as resCode ");
        sql.append("        ,a.questionBizCode");
        sql.append("        ,a.questionType");
        sql.append("        ,a.shareLevel");
        sql.append("        ,a.shareCheckStatus");
        sql.append("        ,a.makeTime");
        sql.append("        ,a.makerOrgCode");
        sql.append("        ,a.makerOrgName");
        sql.append("        ,a.makerCode");
        sql.append("        ,a.makerName");
        sql.append("        ,a.flagDelete");
        sql.append("        ,a.browseCount");
        sql.append("        ,a.downloadCount");
        sql.append("        ,a.referCount");
        sql.append("        ,a.favoriteCount");
        sql.append("        ,a.goodCount");
        sql.append("        ,a.badCount");
        sql.append("        ,a.commentCount");
        sql.append("        ,a.answerCount");
        sql.append("        ,a.creator");
        sql.append("        ,a.createTime");
        sql.append("        ,a.creatorIP");
        sql.append("        ,a.modifier");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.bizVersion");
        sql.append("        ,a.shareSysVer");
        sql.append("        ,a.sysVersion");
        sql.append("        ,a.modifierIP");
        sql.append("        ,a.status");
        sql.append("        ,a.questionTypeSubject");
        sql.append("        ,a.canRandomGroup");
        sql.append("        ,a.shareTime");
        sql.append("        ,a.lockStatus");
        sql.append("        ,a.questionTypeSubjectOrg");
        sql.append("        ");
        sql.append("        ,scg.name as gradeName");
        sql.append("        ,scs.name as sectionName  ");
        sql.append("        ,qt.name as resTypeL2Name  ");
        sql.append("        ,scsubject.name as subjectName  ");
        sql.append(",(case a.shareLevel when 10 then '个人私有' when 20 then '校内共享' when 30 then '区内共享' end ) as shareLevelName ");
        sql.append("    FROM");
        sql.append("        question a   ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_question_grade rqg ");
        sql.append("            on rqg.questionCode=a.questionCode   ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            on scg.code=rqg.gradeCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_question_section rqs ");
        sql.append("            on rqs.questionCode=a.questionCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            on scs.code=rqs.sectionCode  ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsubject ");
        sql.append("            on scsubject.code=a.subjectCode  ");
        sql.append("    LEFT JOIN");
        sql.append("       share_question_type qt on qt.code=a.questionType ");
        sql.append("    WHERE");
        sql.append("        a.flagDelete=0 ");
        sql.append("        AND a.questionSource IN(10,20,30,40) ");
        sql.append("        AND a.status = 10 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND rqs.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" AND rqg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" AND a.subjectCode = :subjectCode ");
        }
        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND rm.resTypeL2 = :resTypeL2 ");
        }

        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(chapterCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND a.questionCode IN(SELECT questionCode FROM rel_question_chapter WHERE  chapterCode LIKE '"
                            + chapterCode + "%') ");
        }
        // 判断知识点编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrk.kpCode LIKE '" + kpCode + "%' ");
            sql.append(" AND a.questionCode IN(SELECT questionCode FROM rel_question_kp WHERE kpCode LIKE '" + kpCode
                            + "%') ");
        }
        // 分享级别
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append(" AND a.shareLevel = :shareLevel ");
        }
        // 机构
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and a.makerOrgCode=:orgCode ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and a.makeTime >=:startDate ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and a.makeTime <=:endDate ");
        }
        sql.append(" ORDER BY a.makeTime DESC ");
        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("shareLevel", shareLevel);
        map.put("orgCode", orgCode);
        map.put("subjectCode", subjectCode);
        map.put("tbcCode", chapterCode);
        map.put("kpCode", kpCode);
        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, page, rows);

    }

    /**
     * 查询试卷列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getExerciseList(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        String resTypeL2 = MapUtils.getString(param, "resTypeL2");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String chapterCode = MapUtils.getString(param, "chapterCode");
        String kpCode = MapUtils.getString(param, "kpCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        sql.append("SELECT");
        sql.append("        a.id");
        sql.append("        ,a.testCode ");
        sql.append("        ,a.testCode as resCode ");
        sql.append("        ,a.testName as resName ");
        sql.append("        ,a.sectionCode");
        sql.append("        ,a.subjectCode");
        sql.append("        ,a.gradeCode");
        sql.append("        ,a.yearTermCode");
        sql.append("        ,a.resTypeL1");
        sql.append("        ,a.resTypeL2");
        sql.append("        ,a.resDesc");
        sql.append("        ,a.makerType");
        sql.append("        ,a.makerOrgCode");
        sql.append("        ,a.makerOrgName");
        sql.append("        ,a.makerCode");
        sql.append("        ,a.makerName");
        sql.append("        ,a.makeTime");
        sql.append("        ,a.orgCode");
        sql.append("        ,a.userCode");
        sql.append("        ,a.userName");
        sql.append("        ,a.status");
        sql.append("        ,a.flagDelete");
        sql.append("        ,a.creator");
        sql.append("        ,a.createTime");
        sql.append("        ,a.shareLevel");
        sql.append("        ,a.creatorIP");
        sql.append("        ,a.modifier");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.modifierIP");
        sql.append("        ,a.bizVersion");
        sql.append("        ,a.shareSysVer");
        sql.append("        ,a.sysVersion");
        sql.append("        ,a.scenarioType");
        sql.append("        ,a.browseCount");
        sql.append("        ,a.downloadCount");
        sql.append("        ,a.referCount");
        sql.append("        ,a.favoriteCount");
        sql.append("        ,a.goodCount");
        sql.append("        ,a.badCount");
        sql.append("        ,a.commentCount");
        sql.append("        ,a.answerCount");
        sql.append("        ,a.studyYearCode");
        sql.append("        ,a.shareTime");
        sql.append("        ,a.lockStatus");
        sql.append("        ,scg.name as gradeName");
        sql.append("        ,scs.name as sectionName");
        sql.append("        ,st2.name AS resTypeL2Name");
        sql.append("        ,scsubject.name AS subjectName");
        sql.append(",(case a.shareLevel when 10 then '个人私有' when 20 then '校内共享' when 30 then '区内共享' end ) as shareLevelName ");
        sql.append("    FROM");
        sql.append("        test a   ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_exercise_grade reg ");
        sql.append("            on a.testCode=reg.testCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            on scg.code=reg.gradeCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsubject ");
        sql.append("            on scsubject.code=a.subjectCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_exercise_section res ");
        sql.append("            on a.testCode=res.testCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            on scs.code=res.sectionCode   ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_res_type_l2 st2 ");
        sql.append("            on st2.code=a.resTypeL2");
        sql.append(" where 1=1 ");
        // 判断学段编码是否为空
        if (!StringUtils.isEmpty(sectionCode)) {
            sql.append(" AND a.sectionCode = :sectionCode ");
        }
        // 判断年级编码是否为空
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" AND reg.gradeCode = :gradeCode ");
        }
        // 判断学科编码是否为空
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" AND a.subjectCode = :subjectCode ");
        }

        // 判断资源分类(二级)是否为空
        if (!StringUtils.isEmpty(resTypeL2)) {
            sql.append(" AND a.resTypeL2 = :resTypeL2 ");
        }

        // 判断教材章节编码是否为空
        if (!StringUtils.isEmpty(kpCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND a.testCode IN(SELECT testCode FROM rel_exercise_kp WHERE kpCode LIKE '" + kpCode + "%') ");
        }
        if (!StringUtils.isEmpty(chapterCode)) {
            // sql.append(" AND rrt2.tbcCode LIKE '" + tbcCode + "%' ");
            sql.append(" AND a.testCode IN(SELECT testCode FROM rel_exercise_chapter WHERE chapterCode LIKE '"
                            + chapterCode + "%') ");
        }
        // 分享级别
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append(" AND a.shareLevel = :shareLevel ");
        }
        // 机构
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and a.makerOrgCode=:orgCode ");
        }
        // 开始时间
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" and a.makeTime >=:startDate ");
        }
        // 结束时间
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" and a.makeTime <=:endDate ");
        }
        sql.append("    AND a.flagDelete = 0 ");
        sql.append("    AND a.scenarioType in (10,20,40)");
        sql.append(" ORDER BY a.makeTime DESC ");
        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("gradeCode", gradeCode);
        map.put("shareLevel", shareLevel);
        map.put("orgCode", orgCode);
        map.put("subjectCode", subjectCode);
        map.put("tbcCode", chapterCode);
        map.put("kpCode", kpCode);
        map.put("resTypeL2", resTypeL2);

        // 执行SQL
        return super.queryDistinctPage(sql.toString(), map, page, rows);
    }
}
