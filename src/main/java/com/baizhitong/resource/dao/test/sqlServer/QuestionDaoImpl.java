package com.baizhitong.resource.dao.test.sqlServer;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.test.QuestionDao;
import com.baizhitong.resource.model.test.Question;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

@Component
public class QuestionDaoImpl extends BaseSqlServerDao<Question> implements QuestionDao {
    /**
     * 根据题目编码查询题目 ()<br>
     * 
     * @param questionCode
     * @return
     */
    public Question findbyQuestionCode(String questionCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("questionCode", questionCode);
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.findUnique(qr);
    }

    /**
     * 
     * 改变题目审核状态
     * 
     * @param questionCode 题目编码
     * @param checkStatus 审核状态
     * @param modifierIP 更新者IP
     * @param modifier 更新者姓名
     */
    public int updateCheckStatus(String questionCode, String checkStatus, String modifierIP, String modifier) {
        Timestamp currentTime=DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        StringBuffer sql=new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        question ");
        sql.append("    SET");
        sql.append("        shareCheckStatus = :shareCheckStatus");
        //审核通过
        if (CoreConstants.CHECK_STATUS_CHECKED.toString().equals(checkStatus)) {
            sql.append("        ,shareLevel = shareCheckLevel");
        }
        sql.append("        ,shareCheckTime = :shareCheckTime");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifyTime = :modifyTime");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,sysVersion = sysVersion + 1 ");
        sql.append("    WHERE");
        sql.append("        questionCode = :questionCode ");
        //SQL参数
        sqlParam.put("questionCode", questionCode);
        sqlParam.put("shareCheckStatus", checkStatus);
        sqlParam.put("shareCheckTime", currentTime);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifyTime", currentTime);
        sqlParam.put("modifierIP", modifierIP);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 查询题目信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getQuestionPage(Map<String, Object> param, Integer page, Integer rows) {
        // 查询条件
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        Integer shareCheckStatus = MapUtils.getInteger(param, "shareCheckStatus");
        String uploadTimeStart = MapUtils.getString(param, "uploadTimeStart");
        String uploadTimeEnd = MapUtils.getString(param, "uploadTimeEnd");
        String checkTimeStart = MapUtils.getString(param, "checkTimeStart");
        String checkTimeEnd = MapUtils.getString(param, "checkTimeEnd");
        Integer shareLevel = MapUtils.getInteger(param, "shareLevel");
        Integer tbcTagStatus = MapUtils.getInteger(param, "tbcTagStatus");
        Integer kpTagStatus = MapUtils.getInteger(param, "kpTagStatus");
        String shareTimeStart = MapUtils.getString(param, "shareTimeStart");
        String shareTimeEnd = MapUtils.getString(param, "shareTimeEnd");
        String userName = MapUtils.getString(param, "userName");
        String orgName = MapUtils.getString(param, "orgName");
        String orgCode = MapUtils.getString(param, "orgCode");
        String resName = MapUtils.getString(param, "resName");
        String questionType = MapUtils.getString(param, "questionType");
        String difficultyType = MapUtils.getString(param, "difficultyType");
        Integer shareCheckLevel = MapUtils.getInteger(param, "shareCheckLevel");
        String orderType = MapUtils.getString(param, "orderType");
        String sectionCodes = MapUtils.getString(param, "sectionCodes");
        String gradeCodes = MapUtils.getString(param, "gradeCodes");
        String subjectCodes = MapUtils.getString(param, "subjectCodes");
        StringBuffer sql = new StringBuffer();
        sql.append("select b.* from");
        sql.append("(SELECT");
        sql.append("		a.id			");
        sql.append("        ,a.questionCode");
        sql.append("        ,a.questionCode as resCode");
        sql.append("        ,a.questionType");
        sql.append("        ,a.shareLevel");
        sql.append("        ,a.shareCheckLevel");
        sql.append("        ,a.shareCheckStatus");
        sql.append("        ,a.shareCheckTime");
        sql.append("        ,a.makeTime");
        sql.append("        ,a.makerOrgCode");
        sql.append("        ,a.makerOrgName");
        sql.append("        ,a.makerCode");
        sql.append("        ,a.makerName");
        sql.append("        ,a.downloadCount");
        sql.append("        ,a.browseCount");
        sql.append("        ,a.referCount");
        sql.append("        ,isNULL(a.favoriteCount,0) as favoriteCount");
        sql.append("        ,a.goodCount");
        sql.append("        ,a.badCount");
        sql.append("        ,a.commentCount");
        sql.append("        ,a.status");
        sql.append("        ,a.shareTime");
        sql.append("        ,sqts.name as questionName");
        sql.append("        ,scg.name as gradeName");
        sql.append("        ,scsec.name as sectionName");
        sql.append("        ,scs.name as subjectName");
        sql.append("        ,scd.name as  difficultyName");
        sql.append("        ,(select");
        sql.append("            count (*) ");
        sql.append("        FROM");
        sql.append("            rel_question_chapter rqc ");
        sql.append("        WHERE");
        sql.append("            rqc.questionCode=a.questionCode ) as tbcCount   ");
        sql.append("       ,(select count (*)");
        sql.append("		FROM");
        sql.append("		rel_question_kp rqk");
        sql.append("		 where");
        sql.append("		 rqk.questionCode=a.questionCode) as kpCount        ");
        sql.append("    FROM");
        sql.append("        question a  ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_question_section rqs ");
        sql.append("            on rqs.questionCode=a.questionCode  ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scsec ");
        sql.append("            ON scsec.code = rqs.sectionCode ");
        sql.append("            and scsec.flagDelete=0  ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            ON scs.code = a.subjectCode ");
        sql.append("            and scs.flagDelete=0  ");
        sql.append("    LEFT JOIN");
        sql.append("        rel_question_grade rqg ");
        sql.append("            on rqg.questionCode=a.questionCode  ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = rqg.gradeCode ");
        sql.append("            and scg.flagDelete=0   ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org so ");
        sql.append("            ON so.orgCode = a.makerOrgCode  ");
        sql.append("    LEFT JOIN");
        sql.append("        share_question_type_subject sqts ");
        sql.append("            ON sqts.code = a.questionTypeSubject   ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_difficulty scd ");
        sql.append("        on scd.code=a.difficultyCode");
        sql.append("    WHERE");
        sql.append("        a.flagDelete=0   AND a.status = 10 and (questionSource=10 or questionSource=20 or questionSource=30 or questionSource=40)");
        // 查询条件参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(sectionCodes)) {
            if (sectionCodes.contains(",")) {
                sectionCodes = "'" + sectionCodes.replace(",", "','") + "'";
            } else {
                sectionCodes = "'" + sectionCodes + "'";
            }
            sql.append(" AND rqs.sectionCode IN (" + sectionCodes + ") ");
        }
        if (StringUtils.isNotEmpty(subjectCodes)) {
            if (subjectCodes.contains(",")) {
                subjectCodes = "'" + subjectCodes.replace(",", "','") + "'";
            } else {
                subjectCodes = "'" + subjectCodes + "'";
            }
            sql.append(" AND a.subjectCode IN (" + subjectCodes + ") ");
        }
        if (StringUtils.isNotEmpty(gradeCodes)) {
            if (gradeCodes.contains(",")) {
                gradeCodes = "'" + gradeCodes.replace(",", "','") + "'";
            } else {
                gradeCodes = "'" + gradeCodes + "'";
            }
            sql.append(" AND rqg.gradeCode IN (" + gradeCodes + ") ");
        }
        if (!StringUtils.isEmpty(gradeCode) && !"-1".equals(gradeCode)) {
            sql.append(" and  rqg.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sql.append(" and a.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (!StringUtils.isEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sql.append(" and rqs.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and so.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and a.makerName like :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(resName)) {
            sql.append(" and   a.questionCode like :resName ");
            sqlParam.put("resName", "%" + resName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(questionType) && !"-1".equals(questionType)) {
            sql.append(" and a.questionTypeSubject = :questionType ");
            sqlParam.put("questionType", questionType);
        }
        if (StringUtils.isNotEmpty(difficultyType) && !"-1".equals(difficultyType)) {
            sql.append(" and a.difficultyCode = :difficultyType ");
            sqlParam.put("difficultyType", difficultyType);
        }
        if (shareCheckStatus != null && shareCheckStatus.intValue() != -1) {
            if (shareCheckStatus == 60) {
                sql.append(" and a.shareCheckStatus is null ");
            } else {
                sql.append(" and a.shareCheckStatus=:shareCheckStatus ");
            }
            sqlParam.put("shareCheckStatus", shareCheckStatus);
        }
        if (shareCheckLevel != null) {
            sql.append(" and a.shareCheckLevel =:shareCheckLevel ");
            sqlParam.put("shareCheckLevel", shareCheckLevel);
        }
        if (!StringUtils.isEmpty(orgCode)) {
            sql.append(" and a.makerOrgCode =:orgCode");
            sqlParam.put("orgCode", orgCode);
        }
        if (!StringUtils.isEmpty(uploadTimeStart)) {
            sql.append(" and a.makeTime >=:uploadTimeStart ");
            sqlParam.put("uploadTimeStart", uploadTimeStart);
        }
        if (!StringUtils.isEmpty(uploadTimeEnd)) {
            sql.append(" and a.makeTime<=:uploadTimeEnd ");
            sqlParam.put("uploadTimeEnd", uploadTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(checkTimeStart)) {
            sql.append(" and a.shareCheckTime >=:checkTimeStart ");
            sqlParam.put("checkTimeStart", checkTimeStart);
        }
        if (!StringUtils.isEmpty(checkTimeEnd)) {
            sql.append(" and a.shareCheckTime<=:checkTimeEnd ");
            sqlParam.put("checkTimeEnd", checkTimeEnd + " 23:59:59");
        }
        if (!StringUtils.isEmpty(shareTimeStart)) {
            sql.append(" and a.shareTime >=:shareTimeStart ");
            sqlParam.put("shareTimeStart", shareTimeStart);
        }
        if (!StringUtils.isEmpty(shareTimeEnd)) {
            sql.append(" and a.shareTime<=:shareTimeEnd ");
            sqlParam.put("shareTimeEnd", shareTimeEnd + " 23:59:59");
        }
        if (shareLevel != null && shareLevel != -1) {
            sql.append(" and a.shareLevel =:shareLevel ");
            sqlParam.put("shareLevel", shareLevel);
        }
        sql.append(" )b where 1=1  ");
        if (kpTagStatus != null) {
            if (kpTagStatus == 1) {
                sql.append(" and b.kpCount >0 ");
            } else if (kpTagStatus == 2) {
                sql.append(" and b.kpCount =0 ");
            }
        }
        if (tbcTagStatus != null) {
            if (tbcTagStatus == 1) {
                sql.append(" and b.tbcCount >0 ");
            } else if (tbcTagStatus == 2) {
                sql.append(" and b.tbcCount =0 ");
            }
        }
        if (StringUtils.isNotEmpty(orderType)) {
            sql.append("  order by b.makeTime desc");
        } else if (shareCheckStatus == 10) {
            sql.append("  order by b.shareTime desc");
        } else {
            sql.append("  order by b.shareCheckTime desc ");
        }
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 删除题目 ()<br>
     * 
     * @param ids
     * @return
     */
    public int deleteQuestion(String ids, String modifier, String modifierIP) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        question ");
        sql.append("    SET");
        sql.append("        flagDelete = :flagDelete");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id ");
        sql.append("    IN (" + ids + ") ");
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_YES);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifierIP", modifierIP);
        sqlParam.put("modifyTime", currentTime);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (查询学科统计信息)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @Override
    public List<Map<String, Object>> selectSubjectReportInfo(Map<String, Object> param) {
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String orgCode = MapUtils.getString(param, "orgCode");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
        }
        sql.append("SELECT");
        sql.append("        scs2.name AS sectionName");
        sql.append("        ,scs.name AS subjectName");
        sql.append("        ,res.shareLevelName");
        sql.append("        ,SUM (res.rmCount) mediaCount");
        sql.append("        ,SUM (res.rdCount) docCount");
        sql.append("        ,SUM (res.testCount) testCount");
        sql.append("        ,SUM (res.qCount) AS questionCount");
        sql.append("        ,( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) AS totalCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rm.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,su.subjectCode");
        sql.append(",(case rm.shareLevel when 10 then '个人私有' when 20 then '校内共享' when 30 then '区域共享' end ) as shareLevelName ");
        sql.append("            ,COUNT (rm.resCode) rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rm.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject su ");
        sql.append("                ON su.resCode = rm.resCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rm.makerOrgCode=:orgCode");
        }
        sql.append("            AND rm.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rm.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("   AND se.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("   AND su.subjectCode = :subjectCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,su.subjectCode ");
        sql.append("            ,rm.shareLevel ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rd.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,su.subjectCode");
        sql.append(",(case rd.shareLevel when 10 then '个人私有' when 20 then '校内共享' when 30 then '区域共享' end ) as shareLevelName ");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,COUNT (rd.resCode) rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rd.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject su ");
        sql.append("                ON su.resCode = rd.resCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rd.makerOrgCode=:orgCode");
        }
        sql.append("            AND rd.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rd.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("   AND se.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("   AND su.subjectCode = :subjectCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            rd.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,su.subjectCode ");
        sql.append("            ,rd.shareLevel ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,e.subjectCode");
        sql.append(",(case e.shareLevel when 10 then '个人私有' when 20 then '校内共享' when 30 then '区域共享' end ) as shareLevelName ");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,COUNT (e.testCode) testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON se.testCode = e.testCode ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and e.makerOrgCode=:orgCode");
        }
        sql.append("            AND e.scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND e.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("   AND se.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("   AND e.subjectCode = :subjectCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            e.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,e.subjectCode ");
        sql.append("            ,e.shareLevel ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            50 AS resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,q.subjectCode");
        sql.append(",(case q.shareLevel when 10 then '个人私有' when 20 then '校内共享' when 30 then '区域共享' end ) as shareLevelName ");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,COUNT (q.questionCode) qCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section se ");
        sql.append("                ON se.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and q.makerOrgCode=:orgCode");
        }
        sql.append("            AND q.questionSource IN (10,20,30,40) ");
        sql.append(" AND q.status = 10");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND q.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("   AND se.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("   AND q.subjectCode = :subjectCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            se.sectionCode");
        sql.append("            ,q.subjectCode ");
        sql.append("            ,q.shareLevel ");
        sql.append("    ) res ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_section scsec ");
        sql.append("        ON res.sectionCode = scsec.code ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_subject scs ");
        sql.append("        ON res.subjectCode = scs.code ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_section scs2 ");
        sql.append("        ON res.sectionCode = scs2.code ");
        sql.append("GROUP BY");
        sql.append("    res.sectionCode");
        sql.append("    ,res.subjectCode");
        sql.append("    ,scs.name");
        sql.append("    ,scs2.name");
        sql.append("    ,shareLevelName");
        sql.append(" ORDER BY res.sectionCode ASC , ( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) ASC ");
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("orgCode", orgCode);
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @Override
    public Map<String, Object> selectSubjectReportTotalNum(Map<String, Object> param) {
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String orgCode = MapUtils.getString(param, "orgCode");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
        }
        sql.append("SELECT");
        sql.append("        COUNT (temp.subjectName) AS subjectTotal");
        sql.append("        ,SUM (temp.mediaCount) AS mediaTotal");
        sql.append("        ,SUM (temp.docCount) AS docTotal");
        sql.append("        ,SUM (temp.testCount) AS testTotal");
        sql.append("        ,SUM (temp.questionCount) AS questionTotal");
        sql.append("        ,SUM (temp.totalCount) AS resTotal");
        sql.append(" FROM (SELECT");
        sql.append("        scs2.name AS sectionName");
        sql.append("        ,scs.name AS subjectName");
        sql.append("        ,SUM (res.rmCount) mediaCount");
        sql.append("        ,SUM (res.rdCount) docCount");
        sql.append("        ,SUM (res.testCount) testCount");
        sql.append("        ,SUM (res.qCount) AS questionCount");
        sql.append("        ,( SUM (res.rmCount) + SUM (res.rdCount) + SUM (res.testCount) + SUM (res.qCount) ) AS totalCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            rm.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,su.subjectCode");
        sql.append("            ,COUNT (rm.resCode) rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rm.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject su ");
        sql.append("                ON su.resCode = rm.resCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rm.makerOrgCode=:orgCode");
        }
        sql.append("            AND rm.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rm.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("   AND se.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("   AND su.subjectCode = :subjectCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            rm.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,su.subjectCode ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            rd.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,su.subjectCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,COUNT (rd.resCode) rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section se ");
        sql.append("                ON se.resCode = rd.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject su ");
        sql.append("                ON su.resCode = rd.resCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rd.makerOrgCode=:orgCode");
        }
        sql.append("            AND rd.resStatus = :resStatus ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND rd.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("   AND se.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("   AND su.subjectCode = :subjectCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            rd.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,su.subjectCode ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            e.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,e.subjectCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,COUNT (e.testCode) testCount");
        sql.append("            ,0 AS qCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_exercise_section se ");
        sql.append("                ON se.testCode = e.testCode ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and e.makerOrgCode=:orgCode");
        }
        sql.append("            AND e.scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND e.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("   AND se.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("   AND e.subjectCode = :subjectCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            e.resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,e.subjectCode ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            50 AS resTypeL1");
        sql.append("            ,se.sectionCode");
        sql.append("            ,q.subjectCode");
        sql.append("            ,0 AS rmCount");
        sql.append("            ,0 AS rdCount");
        sql.append("            ,0 AS testCount");
        sql.append("            ,COUNT (q.questionCode) qCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section se ");
        sql.append("                ON se.questionCode = q.questionCode ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and q.makerOrgCode=:orgCode");
        }
        sql.append("            AND q.questionSource IN (10,20,30,40) ");
        sql.append(" AND q.status = 10");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND q.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("   AND se.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("   AND q.subjectCode = :subjectCode ");
        }
        sql.append("        GROUP BY");
        sql.append("            se.sectionCode");
        sql.append("            ,q.subjectCode ");
        sql.append("    ) res ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_subject scs ");
        sql.append("        ON res.subjectCode = scs.code ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_section scs2 ");
        sql.append("        ON res.sectionCode = scs2.code ");
        sql.append("GROUP BY");
        sql.append("    res.sectionCode");
        sql.append("    ,res.subjectCode");
        sql.append("    ,scs.name");
        sql.append("    ,scs2.name");
        sql.append(" ) temp ");
        sqlParam.put("startDate", startDate);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("orgCode", orgCode);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * (批量操作题目)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public int updateFlagDeleteBatch(String ids, Integer operateType, String userName, String ip) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sqlParam.put("modifier", userName);
        sqlParam.put("modifierIP", ip);
        sqlParam.put("modifyTime", currentTime);
        sql.append("UPDATE");
        sql.append("        question ");
        sql.append("    SET");
        sql.append("        flagDelete = :flagDelete");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :modifyTime ");
        sql.append("    WHERE");
        sql.append("        id ");
        sql.append("    IN (" + ids + ") ");
        // 删除
        if (1 == operateType.intValue()) {
            sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_PACED);
        } else if (2 == operateType.intValue()) {
            // 恢复
            sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        }
        return super.update(sql.toString(), sqlParam);
    }

    @Override
    public List<Map<String, Object>> selectSubjectUsageReportInfo(Map<String, Object> param) {
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String orgCode = MapUtils.getString(param, "orgCode");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
        }
        sql.append("SELECT");
        sql.append("        subjectName");
        sql.append("        ,subjectCode");
        sql.append("        ,sectionName");
        sql.append("        ,sectionCode");
        sql.append("        ,SUM (browseCount) AS browseCount");
        sql.append("        ,SUM (downloadCount) AS downloadCount");
        sql.append("        ,SUM (referCount) AS referCount");
        sql.append("        ,SUM (favoriteCount) AS favoriteCount");
        sql.append("        ,SUM (goodCount) AS goodCount");
        sql.append("        ,SUM (commentCount) AS commentCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,rrsu.subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rrs.sectionCode");
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
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrs.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.resCode = rd.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = rrsu.subjectCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rd.makerOrgCode=:orgCode");
        }
        sql.append("            AND rd.resStatus = 20 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endTime ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrs.sectionCode = :sectionCode ");
        }

        sql.append("        GROUP BY");
        sql.append("            rrsu.subjectCode");
        sql.append("            ,scsu.name");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,rrsu.subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rrs.sectionCode");
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
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrs.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.resCode = rm.resCode ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = rrsu.subjectCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rm.makerOrgCode=:orgCode");
        }
        sql.append("            AND rm.resStatus = 20 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endTime ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrs.sectionCode = :sectionCode ");
        }

        sql.append("        GROUP BY");
        sql.append("            rrsu.subjectCode");
        sql.append("            ,scsu.name");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,e.subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,e.sectionCode");
        sql.append("            ,SUM (e.browseCount) AS browseCount");
        sql.append("            ,SUM (e.downloadCount) AS downloadCount");
        sql.append("            ,SUM (e.referCount) AS referCount");
        sql.append("            ,SUM (e.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (e.goodCount) AS goodCount");
        sql.append("            ,SUM (e.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON e.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON e.subjectCode = scsu.code ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        sql.append("            AND e.scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and e.makerOrgCode=:orgCode");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endTime ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND e.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND e.sectionCode = :sectionCode ");
        }

        sql.append("        GROUP BY");
        sql.append("            scsu.name");
        sql.append("            ,e.subjectCode");
        sql.append("            ,e.sectionCode");
        sql.append("            ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,q.subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rrse.sectionCode");
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
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrse.sectionCode = scs.code ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON q.subjectCode = scsu.code ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and q.makerOrgCode=:orgCode");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endTime ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND q.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }

        sql.append("        GROUP BY");
        sql.append("            scsu.name");
        sql.append("            ,q.subjectCode");
        sql.append("            ,rrse.sectionCode");
        sql.append("            ,scs.name ");
        sql.append("    ) res ");
        sql.append("GROUP BY");
        sql.append("    subjectName");
        sql.append("    ,subjectCode");
        sql.append("    ,sectionCode");
        sql.append("    ,sectionName ");
        sql.append("ORDER BY");
        sql.append(" sectionCode ASC ");

        sqlParam.put("startTime", startDate);
        sqlParam.put("endTime", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("orgCode", orgCode);
        return super.findBySql(sql.toString(), sqlParam);
    }

    @Override
    public Map<String, Object> selectSubjectUsageReportTotalNum(Map<String, Object> param) {
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String orgCode = MapUtils.getString(param, "orgCode");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resStatus", CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS);
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        if (StringUtils.isNotEmpty(endDate)) {
            endDate = endDate + " 23:59:59";
        }
        sql.append("SELECT");
        sql.append("        COUNT (c.subjectCode) AS subjectTotal");
        sql.append("        ,SUM (browseCount) AS browseCountTotal");
        sql.append("        ,SUM (downloadCount) AS downloadCountTotal");
        sql.append("        ,SUM (referCount) AS referCountTotal");
        sql.append("        ,SUM (favoriteCount) AS favoriteCountTotal");
        sql.append("        ,SUM (goodCount) AS goodCountTotal");
        sql.append("        ,SUM (commentCount) AS commentCountTotal ");
        sql.append("    FROM ");
        sql.append("        ( ");
        sql.append(" SELECT ");
        sql.append("        subjectName");
        sql.append("        ,subjectCode");
        sql.append("        ,sectionName");
        sql.append("        ,sectionCode");
        sql.append("        ,SUM (browseCount) AS browseCount");
        sql.append("        ,SUM (downloadCount) AS downloadCount");
        sql.append("        ,SUM (referCount) AS referCount");
        sql.append("        ,SUM (favoriteCount) AS favoriteCount");
        sql.append("        ,SUM (goodCount) AS goodCount");
        sql.append("        ,SUM (commentCount) AS commentCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,rrsu.subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,SUM (rd.browseCount) AS browseCount");
        sql.append("            ,SUM (rd.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rd.referCount) AS referCount");
        sql.append("            ,SUM (rd.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rd.goodCount) AS goodCount");
        sql.append("            ,SUM (rd.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_doc rd ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rd.resCode = rrs.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrs.sectionCode = scs.code ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.resCode = rd.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = rrsu.subjectCode ");
        sql.append("        WHERE");
        sql.append("            rd.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rd.makerOrgCode=:orgCode");
        }
        sql.append("            AND rd.resStatus = 20 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rd.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rd.createTime <= :endTime ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrs.sectionCode = :sectionCode ");
        }

        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rrsu.subjectCode");
        sql.append("            ,scsu.name");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,rrsu.subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,SUM (rm.browseCount) AS browseCount");
        sql.append("            ,SUM (rm.downloadCount) AS downloadCount");
        sql.append("            ,SUM (rm.referCount) AS referCount");
        sql.append("            ,SUM (rm.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (rm.goodCount) AS goodCount");
        sql.append("            ,SUM (rm.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            res_media rm ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_section rrs ");
        sql.append("                ON rm.resCode = rrs.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrs.sectionCode = scs.code ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_res_subject rrsu ");
        sql.append("                ON rrsu.resCode = rm.resCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = rrsu.subjectCode ");
        sql.append("        WHERE");
        sql.append("            rm.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and rm.makerOrgCode=:orgCode");
        }
        sql.append("            AND rm.resStatus = 20 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND rm.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND rm.createTime <= :endTime ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND rrsu.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrs.sectionCode = :sectionCode ");
        }

        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            rrsu.subjectCode");
        sql.append("            ,scsu.name");
        sql.append("            ,rrs.sectionCode");
        sql.append("            ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,e.subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,e.sectionCode");
        sql.append("            ,SUM (e.browseCount) AS browseCount");
        sql.append("            ,SUM (e.downloadCount) AS downloadCount");
        sql.append("            ,SUM (e.referCount) AS referCount");
        sql.append("            ,SUM (e.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (e.goodCount) AS goodCount");
        sql.append("            ,SUM (e.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            exercise e ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON e.sectionCode = scs.code ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON e.subjectCode = scsu.code ");
        sql.append("        WHERE");
        sql.append("            e.flagDelete = 0 ");
        sql.append("            AND e.scenarioType IN (10,20,40) ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and e.makerOrgCode=:orgCode");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND e.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND e.createTime <= :endTime ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND e.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND e.sectionCode = :sectionCode ");
        }

        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            scsu.name");
        sql.append("            ,e.subjectCode");
        sql.append("            ,e.sectionCode");
        sql.append("            ,scs.name ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,q.subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,rrse.sectionCode");
        sql.append("            ,SUM (q.browseCount) AS browseCount");
        sql.append("            ,SUM (q.downloadCount) AS downloadCount");
        sql.append("            ,SUM (q.referCount) AS referCount");
        sql.append("            ,SUM (q.favoriteCount) AS favoriteCount");
        sql.append("            ,SUM (q.goodCount) AS goodCount");
        sql.append("            ,SUM (q.commentCount) AS commentCount ");
        sql.append("        FROM");
        sql.append("            question q ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            rel_question_section rrse ");
        sql.append("                ON rrse.questionCode = q.questionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON rrse.sectionCode = scs.code ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON q.subjectCode = scsu.code ");
        sql.append("        WHERE");
        sql.append("            q.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and q.makerOrgCode=:orgCode");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND q.createTime >= :startTime ");
        }

        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND q.createTime <= :endTime ");
        }

        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND q.subjectCode = :subjectCode ");
        }

        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND rrse.sectionCode = :sectionCode ");
        }

        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            scsu.name");
        sql.append("            ,q.subjectCode");
        sql.append("            ,rrse.sectionCode");
        sql.append("            ,scs.name ");
        sql.append("    ) res ");
        sql.append("    ");
        sql.append("GROUP BY");
        sql.append("    subjectName");
        sql.append("    ,subjectCode");
        sql.append("    ,sectionCode");
        sql.append("    ,sectionName ");
        sql.append("    ");
        sql.append("    ) c");
        sqlParam.put("startTime", startDate);
        sqlParam.put("endTime", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("orgCode", orgCode);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 
     * (更新题目的共享级别)<br>
     * @param questionCode 题目编码
     * @param shareLevel 共享级别
     * @param shareCheckStatus 共享审核状态
     * @param modifier 更新者
     * @param modifierIP 更新者IP
     * @return 更新记录数
     */
    @Override
    public int updateShareLevel(String questionCode, Integer shareLevel, Integer shareCheckStatus, String modifier,
                    String modifierIP) {
        StringBuffer sql=new StringBuffer();
        Map<String,Object> sqlParam=new HashMap<String, Object>();
        Timestamp currentTime=DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        question ");
        sql.append("    SET");
        sql.append("        shareLevel = :shareLevel");
        sql.append("        ,shareCheckLevel = :shareLevel");
        sql.append("        ,shareCheckStatus = :shareCheckStatus");
        sql.append("        ,shareCheckTime = :currentTime");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ,modifyTime = :currentTime ");
        sql.append("        ,sysVersion=sysVersion+1 ");// 系统版本号+1
        sql.append("    WHERE");
        sql.append("        questionCode = :questionCode");        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("currentTime", currentTime);
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifierIP", modifierIP);
        sqlParam.put("questionCode", questionCode);
        return super.update(sql.toString(), sqlParam);
    }
    
}
