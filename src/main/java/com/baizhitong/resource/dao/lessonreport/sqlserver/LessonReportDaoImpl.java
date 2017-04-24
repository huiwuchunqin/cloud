package com.baizhitong.resource.dao.lessonreport.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.lessonreport.LessonReportDao;
import com.baizhitong.resource.model.lesson.Lesson;
import com.baizhitong.utils.ObjectUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * LessonReportDaoImpl 课时统计dao实现
 * 
 * @author creator zhanglzh 2017年1月3日 上午9:50:50
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class LessonReportDaoImpl extends BaseSqlServerDao<Lesson> implements LessonReportDao {

    @Override
    public Map<String, Object> selectSubjectUsedTotal(Map<String, Object> param) {

        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (1) as subjectTotal");
        sql.append("        ,SUM (referCount) as referCountTotal  ");
        sql.append("    FROM");
        sql.append("        (");
        sql.append("SELECT");
        sql.append("        subjectName");
        sql.append("        ,sectionName");
        sql.append("        ,referCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (lr.id) ");
        sql.append("            FROM");
        sql.append("                lesson_refer lr ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                lesson l1 ");
        sql.append("                    ON l1.lessonCode = lr.lessonCode ");
        sql.append("            WHERE");
        sql.append("                l1.subjectCode = l.subjectCode ");
        sql.append("                AND l1.sectionCode = l.sectionCode  ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND lr.modifyTime >:startDate ");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND lr.modifyTime <:endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("    ) AS referCount   ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = l.subjectCode ");
        sql.append("                ");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND subjectCode =:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        sql.append("        GROUP BY");
        sql.append("            l.sectionCode");
        sql.append("            ,l.subjectCode");
        sql.append("            ,scs.name");
        sql.append("            ,scsu.name ) a ");
        sql.append("        WHERE");
        sql.append("            a.referCount > 0 ) b ");

        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> selectPageSubjectUsed(Integer page, Integer rows, Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        subjectName");
        sql.append("        ,sectionName");
        sql.append("        ,referCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            scsu.name AS subjectName");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,( SELECT");
        sql.append("                COUNT (lr.id) ");
        sql.append("            FROM");
        sql.append("                lesson_refer lr ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                lesson l1 ");
        sql.append("                    ON l1.lessonCode = lr.lessonCode ");
        sql.append("            WHERE");
        sql.append("                l1.subjectCode = l.subjectCode ");
        sql.append("                AND l1.sectionCode = l.sectionCode ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND lr.modifyTime >:startDate ");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND lr.modifyTime <:endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("    ) AS referCount   ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = l.subjectCode ");
        sql.append("                ");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND subjectCode =:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        sql.append("        GROUP BY");
        sql.append("            l.sectionCode");
        sql.append("            ,l.subjectCode");
        sql.append("            ,scs.name");
        sql.append("            ,scsu.name ) a ");
        sql.append("        WHERE");
        sql.append("            a.referCount > 0 ");
        sql.append("            ");
        sql.append("        ORDER BY");
        sql.append("            a.referCount DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectOrgUsedTotal(Map<String, Object> param) {
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
        sql.append("SELECT");
        sql.append("        COUNT (a.orgCode) AS orgTotal");
        sql.append("        ,SUM (referCount) AS referTotal");
        sql.append("        ,SUM (referedCount) AS referedTotal ");
        sql.append("    FROM");
        sql.append("(SELECT");
        sql.append("        soc.orgCode");
        sql.append("        ,so.orgName");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,SUM (referCount) AS referCount");
        sql.append("        ,( SELECT");
        sql.append("            COUNT (1) ");
        sql.append("        FROM");
        sql.append("            lesson_refer lr ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_org_section soc1 ");
        sql.append("                ON soc1.orgCode = lr.teacherOrgCode ");
        sql.append("        WHERE");
        sql.append("            teacherOrgCode = l.orgCode ");
        sql.append("            AND soc1.sectionCode = soc.sectionCode ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND lr.modifyTime > :startDate ");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND lr.modifyTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("               ) AS referedCount ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org_section soc ");
        sql.append("            ON soc.orgcode = l.orgCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON soc.sectionCode = scs.code ");
        sql.append("    LEFT JOIN share_org so on so.orgCode = soc.orgCode");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND soc.sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("             AND so.orgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        // if (StringUtils.isNotEmpty(startDate)) {
        // sql.append(" AND l.createTime > :startDate ");
        // sqlParam.put("startDate", startDate);
        // }
        // if (StringUtils.isNotEmpty(endDate)) {
        // sql.append(" AND l.createTime < :endDate ");
        // sqlParam.put("endDate", endDate);
        // }
        sql.append("        ");
        sql.append("    GROUP BY");
        sql.append("        soc.sectionCode");
        sql.append("        ,soc.orgCode");
        sql.append("        ,l.orgCode");
        sql.append("        ,so.orgName");
        sql.append("        ,scs.name)a  WHERE    a.referedCount > 0 ");

        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> selectPageOrgUsed(Integer page, Integer rows, Map<String, Object> param) {
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
        sql.append("SELECT");
        sql.append("        orgCode");
        sql.append("        ,orgName");
        sql.append("        ,sectionName");
        sql.append("        ,referCount");
        sql.append("        ,referedCount ");
        sql.append("    FROM");
        sql.append("        (");
        sql.append("SELECT");
        sql.append("        soc.orgCode");
        sql.append("        ,so.orgName");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,SUM (referCount) AS referCount");
        sql.append("        ,( SELECT");
        sql.append("            COUNT (1) ");
        sql.append("        FROM");
        sql.append("            lesson_refer lr ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_org_section soc1 ");
        sql.append("                ON soc1.orgCode = lr.teacherOrgCode ");
        sql.append("        WHERE");
        sql.append("            teacherOrgCode = l.orgCode ");
        sql.append("            AND soc1.sectionCode = soc.sectionCode ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND lr.modifyTime > :startDate ");
            sqlParam.put("startDate", startDate);
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND lr.modifyTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("               ) AS referedCount ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_org_section soc ");
        sql.append("            ON soc.orgcode = l.orgCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON soc.sectionCode = scs.code ");
        sql.append("    LEFT JOIN share_org so on so.orgCode = soc.orgCode");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND soc.sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("             AND so.orgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        // if (StringUtils.isNotEmpty(startDate)) {
        // sql.append(" AND l.createTime > :startDate ");
        // sqlParam.put("startDate", startDate);
        // }
        // if (StringUtils.isNotEmpty(endDate)) {
        // sql.append(" AND l.createTime < :endDate ");
        // sqlParam.put("endDate", endDate);
        // }
        sql.append("        ");
        sql.append("    GROUP BY");
        sql.append("        soc.sectionCode");
        sql.append("        ,soc.orgCode");
        sql.append("        ,l.orgCode");
        sql.append("        ,so.orgName");
        sql.append("        ,scs.name ) a  WHERE    a.referedCount > 0  ORDER ");
        sql.append("    BY");
        sql.append("       referedCount DESC,referCount DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getCourseCountAggregate(Map<String, Object> param, Integer page, Integer rows) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        (case lessonMode ");
        sql.append("            WHEN 10 ");
        sql.append("        THEN");
        sql.append("            '翻转课堂模式' ");
        sql.append("            WHEN 0 ");
        sql.append("        THEN");
        sql.append("            '自主创建模式' ");
        sql.append("        END");
        sql.append("    ) AS lessonMode, SUM (privateNum) AS privateNum, SUM (orgNum) AS orgNum, SUM (areaNum) AS areaNum, SUM (totalNum) AS totalNum ");
        sql.append("FROM");
        sql.append("    ( SELECT");
        sql.append("        l.lessonMode");
        sql.append("        ,COUNT (id) AS privateNum");
        sql.append("        ,0 AS orgNum");
        sql.append("        ,0 AS areaNum");
        sql.append("        ,0 AS totalNum ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate  ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("        AND l.shareLevel = 10 ");
        sql.append("        ");
        sql.append("    GROUP BY ");
        sql.append("        l.lessonMode ");
        sql.append("    UNION");
        sql.append("    ALL SELECT");
        sql.append("        l.lessonMode");
        sql.append("        ,0 AS privateNum");
        sql.append("        ,COUNT (id) AS orgNum");
        sql.append("        ,0 AS areaNum");
        sql.append("        ,0 AS totalNum ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate  ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("        AND l.shareLevel = 20 ");
        sql.append("        ");
        sql.append("    GROUP BY ");
        sql.append("        l.lessonMode ");
        sql.append("    UNION");
        sql.append("    ALL SELECT");
        sql.append("        l.lessonMode");
        sql.append("        ,0 AS privateNum");
        sql.append("        ,0 AS orgNum");
        sql.append("        ,COUNT (id) AS areaNum");
        sql.append("        ,0 AS totalNum ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate  ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("        AND l.shareLevel = 30 ");
        sql.append("        ");
        sql.append("    GROUP BY ");
        sql.append("        l.lessonMode ");
        sql.append("    UNION");
        sql.append("    ALL SELECT");
        sql.append("        l.lessonMode");
        sql.append("        ,0 AS privateNum");
        sql.append("        ,0 AS orgNum");
        sql.append("        ,0 AS areaNum");
        sql.append("        ,COUNT (id) AS totalNum ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("        ");
        sql.append("    GROUP BY ");
        sql.append("        l.lessonMode ");
        sql.append(") a ");
        sql.append("");
        sql.append(" GROUP BY ");
        sql.append(" lessonMode ");
        sql.append("");
        sql.append(" ORDER BY ");
        sql.append(" lessonMode DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectCourseCountAggregateTotal(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        (SUM(autonomousLessonNum) + SUM(flipLessonNum)) AS totalLesson");
        sql.append("        ,SUM(flipLessonNum) AS flipLessonTotal");
        sql.append("        ,SUM(autonomousLessonNum) AS autonomousLessonTotal");
        sql.append("        ,SUM(privateNum) AS privateTotal");
        sql.append("        ,SUM(orgNum) AS orgTotal");
        sql.append("        ,SUM(areaNum) AS areaTotal ");
        sql.append("    FROM");
        sql.append("        (SELECT");
        sql.append("            COUNT(1) AS autonomousLessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,0 AS privateNum");
        sql.append("            ,0 AS orgNum");
        sql.append("            ,0 AS areaNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("            AND l.lessonMode = 0 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            0 AS autonomousLessonNum");
        sql.append("            ,COUNT(1) AS flipLessonNum");
        sql.append("            ,0 AS privateNum");
        sql.append("            ,0 AS orgNum");
        sql.append("            ,0 AS areaNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("            AND l.lessonMode = 10 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            0 AS autonomousLessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,COUNT(1) AS privateNum");
        sql.append("            ,0 AS orgNum");
        sql.append("            ,0 AS areaNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("            AND l.shareLevel = 10 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            0 AS autonomousLessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,0 AS privateNum");
        sql.append("            ,COUNT(1) AS orgNum");
        sql.append("            ,0 AS areaNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("            AND l.shareLevel = 20 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            0 AS autonomousLessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,0 AS privateNum");
        sql.append("            ,0 AS orgNum");
        sql.append("            ,COUNT(1) AS areaNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND l.createTime > :startDate ");
        }
        sql.append("            AND l.shareLevel = 30");
        sql.append("    )a");

        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @Override
    public Map<String, Object> selectStudentUsedTotal(Map<String, Object> param) {
        String studyYearTermCode = MapUtils.getString(param, "studyYearTermCode");
        String orgName = MapUtils.getString(param, "orgName");
        String userName = MapUtils.getString(param, "userName");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (userCode) AS studentNum");
        sql.append("        ,SUM (uncompletedCoureseNum) AS noCompletedLessonNum");
        sql.append("        ,SUM (completedCoureseNum)  AS completedLessonNum");
        sql.append("        ,(SUM (uncompletedCoureseNum) + SUM (completedCoureseNum))AS lessonNum ");
        sql.append("    FROM");
        sql.append("        platform_course_use_student");
        sql.append(" WHERE ");
        sql.append("    1 = 1 ");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" AND  userName LIKE :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" AND  orgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(studyYearTermCode)) {
            sql.append("        AND yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", studyYearTermCode);
        }
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> selectPageStudentUsed(Integer page, Integer rows, Map<String, Object> param) {
        String studyYearTermCode = MapUtils.getString(param, "studyYearTermCode");
        String orgName = MapUtils.getString(param, "orgName");
        String userName = MapUtils.getString(param, "userName");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        orgName");
        sql.append("        ,orgCode");
        sql.append("        ,userName");
        sql.append("        ,userCode");
        sql.append("        ,Round(SUM(studyTotalTime),2) AS studyTotalTime");
        sql.append("        ,SUM(completedCoureseNum) AS completedCoureseNum ");
        sql.append("        ,SUM(uncompletedCoureseNum) AS uncompletedCoureseNum");
        sql.append("        ,(SUM(completedCoureseNum)+SUM(uncompletedCoureseNum)) AS lessonNum ");
        sql.append("    FROM");
        sql.append("        platform_course_use_student");
        sql.append(" WHERE ");
        sql.append("    1 = 1 ");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" AND  userName LIKE :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" AND  orgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(studyYearTermCode)) {
            sql.append("        AND yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", studyYearTermCode);
        }
        sql.append(" GROUP BY    ");
        sql.append("        orgName");
        sql.append("        ,orgCode");
        sql.append("        ,userName");
        sql.append("        ,userCode");
        sql.append(" ORDER BY");
        sql.append("    SUM(studyTotalTime) DESC");

        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectTeacherUsedTotal(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String orgName = MapUtils.getString(param, "orgName");
        String teacherName = MapUtils.getString(param, "teacherName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        COUNT (a.teacherCode) AS teacherNum");
        sql.append("        ,SUM (referCount) AS referCount");
        sql.append("        ,SUM (referedCount) AS referedCount ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            SUM (l.referCount) AS referCount");
        sql.append("            ,l.teacherCode");
        sql.append("            ,l.orgCode");
        sql.append("            ,ISNULL( ( SELECT");
        sql.append("                COUNT (1) ");
        sql.append("            FROM");
        sql.append("                lesson_refer ");
        sql.append("            WHERE");
        sql.append("                teacherCode = l.teacherCode and teacherOrgCode = l.orgCode");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND modifyTime <:endDate ");
            sqlParam.put("endDate", endDate);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND modifyTime > :startDate ");
            sqlParam.put("startDate", startDate);
        }
        sql.append("                 )");
        sql.append("            ,0 ) AS referedCount ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append(" AND  l.teacherName LIKE :teacherName ");
            sqlParam.put("teacherName", "%" + teacherName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" AND  l.orgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        // if (StringUtils.isNotEmpty(endDate)) {
        // sql.append(" AND l.createTime <:endDate ");
        // sqlParam.put("endDate", endDate);
        // }
        // if (StringUtils.isNotEmpty(startDate)) {
        // sql.append(" AND l.createTime > :startDate ");
        // sqlParam.put("startDate", startDate);
        // }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            l.teacherCode");
        sql.append("            ,l.orgCode ) a  WHERE    a.referedCount > 0");
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> selectPageTeacherUsed(Integer page, Integer rows, Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String orgName = MapUtils.getString(param, "orgName");
        String teacherName = MapUtils.getString(param, "teacherName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sql.append("SELECT");
        sql.append("        a.teacherCode");
        sql.append("        ,a.orgCode");
        sql.append("        ,a.referedCount");
        sql.append("        ,a.referCount");
        sql.append("        ,sul.loginAccount");
        sql.append("        ,sul.userName AS teacherName");
        sql.append("        ,sul.orgName ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            SUM (l.referCount) AS referCount");
        sql.append("            ,l.teacherCode");
        sql.append("            ,l.orgCode");
        sql.append("            ,ISNULL( ( SELECT");
        sql.append("                COUNT (1) ");
        sql.append("            FROM");
        sql.append("                lesson_refer ");
        sql.append("            WHERE");
        sql.append("                teacherCode = l.teacherCode and teacherOrgCode = l.orgCode");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("        AND modifyTime <:endDate ");
            sqlParam.put("endDate", endDate);
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("        AND modifyTime > :startDate ");
            sqlParam.put("startDate", startDate);
        }
        sql.append("          )  ,0 ) AS referedCount ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append(" AND  l.teacherName LIKE :teacherName ");
            sqlParam.put("teacherName", "%" + teacherName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" AND  l.orgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        // if (StringUtils.isNotEmpty(endDate)) {
        // sql.append(" AND l.createTime < :endDate ");
        // }
        // if (StringUtils.isNotEmpty(startDate)) {
        // sql.append(" AND l.createTime > :startDate ");
        // }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            l.teacherCode");
        sql.append("            ,l.orgCode ) a ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_user_login sul ");
        sql.append("                ON sul.userCode = a.teacherCode ");
        sql.append("                ");
        sql.append("   WHERE    a.referedCount > 0          ");
        sql.append("        ORDER BY");
        sql.append("            a.referedCount DESC,a.referCount DESC");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getCourseAuthorReport(Map<String, Object> param, Integer page, Integer rows) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String teacherName = MapUtils.getString(param, "teacherName");
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("teacherName", "%" + teacherName + "%");
        sqlParam.put("orgName", "%" + orgName + "%");
        sql.append("SELECT");
        sql.append("        a.teacherName");
        sql.append("        ,a.orgName");
        sql.append("        ,SUM (lessonNum) AS lessonNum");
        sql.append("        ,SUM (flipLessonNum) AS flipLessonNum");
        sql.append("        ,SUM (autonomousLessonNum) AS autonomousLessonNum ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            l.teacherCode");
        sql.append("            ,l.teacherName");
        // sql.append(" ,scsu.name AS subjectName");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name AS sectionName");
        // sql.append(" ,l.sectionCode");
        sql.append("            ,l.orgName");
        sql.append("            ,COUNT (id) AS lessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,0 AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = l.subjectCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("            AND l.teacherName LIKE :teacherName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            l.teacherCode");
        sql.append("            ,l.teacherName");
        // sql.append(" ,scsu.name");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name");
        // sql.append(" ,l.sectionCode");
        sql.append("            ,l.orgName ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            l.teacherCode");
        sql.append("            ,l.teacherName");
        // sql.append(" ,scsu.name AS subjectName");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name AS sectionName");
        // sql.append(" ,l.sectionCode");
        sql.append("            ,l.orgName");
        sql.append("            ,0 AS lessonNum");
        sql.append("            ,COUNT (id) AS flipLessonNum");
        sql.append("            ,0 AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = l.subjectCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        sql.append("            AND l.lessonMode = 10 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("            AND l.teacherName LIKE :teacherName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            l.teacherCode");
        sql.append("            ,l.teacherName");
        // sql.append(" ,scsu.name");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name");
        // sql.append(" ,l.sectionCode");
        sql.append("            ,l.orgName ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            l.teacherCode");
        sql.append("            ,l.teacherName");
        // sql.append(" ,scsu.name AS subjectName");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name AS sectionName");
        // sql.append(" ,l.sectionCode");
        sql.append("            ,l.orgName");
        sql.append("            ,0 AS lessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,COUNT (id) AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = l.subjectCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        sql.append("            AND l.lessonMode = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("            AND l.teacherName LIKE :teacherName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            l.teacherCode");
        sql.append("            ,l.teacherName");
        // sql.append(" ,scsu.name");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name");
        // sql.append(" ,l.sectionCode");
        sql.append("            ,l.orgName ");
        sql.append("    ) a ");
        sql.append("    ");
        sql.append(" GROUP BY ");
        sql.append("    teacherCode");
        sql.append("    ,teacherName");
        // sql.append(" ,subjectCode");
        // sql.append(" ,subjectName");
        // sql.append(" ,sectionName");
        sql.append("    ,orgName");
        // sql.append(" ,sectionCode ");
        sql.append("    ");
        sql.append(" ORDER BY ");
        sql.append("    teacherName");
        sql.append("    ,orgName");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectCourseAuthorReportTotal(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String teacherName = MapUtils.getString(param, "teacherName");
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("teacherName", "%" + teacherName + "%");
        sqlParam.put("orgName", "%" + orgName + "%");
        sql.append(" SELECT ");
        sql.append("        COUNT (1) AS teacherTotal");
        sql.append("        ,SUM (lessonNum) AS lessonTotal");
        sql.append("        ,SUM (flipLessonNum) AS flipLessonTotal");
        sql.append("        ,SUM (autonomousLessonNum) AS autonomousLessonTotal FROM( SELECT");
        sql.append("            a.teacherName");
        sql.append("            ,a.orgName");
        sql.append("            ,SUM (lessonNum) AS lessonNum");
        sql.append("            ,SUM (flipLessonNum) AS flipLessonNum");
        sql.append("            ,SUM (autonomousLessonNum) AS autonomousLessonNum FROM ( SELECT");
        sql.append("                l.teacherCode");
        sql.append("                ,l.teacherName");
        // sql.append(" ,scsu.name AS subjectName");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name AS sectionName");
        // sql.append(" ,l.sectionCode");
        sql.append("                ,l.orgName");
        sql.append("                ,COUNT (id) AS lessonNum");
        sql.append("                ,0 AS flipLessonNum");
        sql.append("                ,0 AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode ");
        sql.append("                    ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_subject scsu ");
        sql.append("                    ON scsu.code = l.subjectCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("            AND l.teacherName LIKE :teacherName ");
        }
        sql.append("                ");
        sql.append("            GROUP BY ");
        sql.append("                l.teacherCode");
        sql.append("                ,l.teacherName");
        // sql.append(" ,scsu.name");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name");
        // sql.append(" ,l.sectionCode");
        sql.append("                ,l.orgName ");
        sql.append("            UNION ");
        sql.append("            ALL SELECT");
        sql.append("                l.teacherCode");
        sql.append("                ,l.teacherName");
        // sql.append(" ,scsu.name AS subjectName");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name AS sectionName");
        // sql.append(" ,l.sectionCode");
        sql.append("                ,l.orgName");
        sql.append("                ,0 AS lessonNum");
        sql.append("                ,COUNT (id) AS flipLessonNum");
        sql.append("                ,0 AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode LEFT ");
        sql.append("            JOIN");
        sql.append("                share_code_subject scsu ");
        sql.append("                    ON scsu.code = l.subjectCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        sql.append("                AND l.lessonMode = 10 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("            AND l.teacherName LIKE :teacherName ");
        }
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                l.teacherCode");
        sql.append("                ,l.teacherName");
        // sql.append(" ,scsu.name");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name");
        // sql.append(" ,l.sectionCode");
        sql.append("                ,l.orgName ");
        sql.append("            UNION");
        sql.append("            ALL SELECT");
        sql.append("                l.teacherCode");
        sql.append("                ,l.teacherName");
        // sql.append(" ,scsu.name AS subjectName");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name AS sectionName");
        // sql.append(" ,l.sectionCode");
        sql.append("                ,l.orgName");
        sql.append("                ,0 AS lessonNum");
        sql.append("                ,0 AS flipLessonNum");
        sql.append("                ,COUNT (id) AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode ");
        sql.append("                    ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_subject scsu ");
        sql.append("                    ON scsu.code = l.subjectCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        sql.append("                AND l.lessonMode = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("            AND l.teacherName LIKE :teacherName ");
        }
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                l.teacherCode");
        sql.append("                ,l.teacherName");
        // sql.append(" ,scsu.name");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scs.name");
        // sql.append(" ,l.sectionCode");
        sql.append("                ,l.orgName ) a GROUP ");
        sql.append("        BY");
        sql.append("            teacherCode");
        sql.append("            ,teacherName");
        // sql.append(" ,subjectCode");
        // sql.append(" ,subjectName");
        // sql.append(" ,sectionName");
        sql.append("            ,orgName");
        // sql.append(" ,sectionCode ");
        sql.append("             ) b");

        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getCourseSchoolReport(Map<String, Object> param, Integer page, Integer rows) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("orgName", "%" + orgName + "%");
        sql.append("SELECT");
        sql.append("        a.orgName");
        sql.append("        ,a.sectionName");
        sql.append("        ,SUM (lessonNum) AS lessonNum");
        sql.append("        ,SUM (flipLessonNum) AS flipLessonNum");
        sql.append("        ,SUM (autonomousLessonNum) AS autonomousLessonNum ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            scs.name AS sectionName");
        sql.append("            ,l.sectionCode");
        sql.append("            ,l.orgName");
        sql.append("            ,COUNT (id) AS lessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,0 AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            scs.name");
        sql.append("            ,l.sectionCode");
        sql.append("            ,l.orgName ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scs.name AS sectionName");
        sql.append("            ,l.sectionCode");
        sql.append("            ,l.orgName");
        sql.append("            ,0 AS lessonNum");
        sql.append("            ,COUNT (id) AS flipLessonNum");
        sql.append("            ,0 AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        sql.append("            AND l.lessonMode = 10 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            scs.name");
        sql.append("            ,l.sectionCode");
        sql.append("            ,l.orgName ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scs.name AS sectionName");
        sql.append("            ,l.sectionCode");
        sql.append("            ,l.orgName");
        sql.append("            ,0 AS lessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,COUNT (id) AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        sql.append("            AND l.lessonMode = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        sql.append("            ");
        sql.append("        GROUP BY ");
        sql.append("            scs.name");
        sql.append("            ,l.sectionCode");
        sql.append("            ,l.orgName ");
        sql.append("    ) a ");
        sql.append("    ");
        sql.append(" GROUP BY ");
        sql.append("    sectionName");
        sql.append("    ,orgName");
        sql.append("    ,sectionCode ");
        sql.append("    ");
        sql.append(" ORDER BY ");
        sql.append("    sectionCode");
        sql.append("    ,orgName");

        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectCourseSchoolReportTotal(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("orgName", "%" + orgName + "%");
        sql.append("SELECT");
        sql.append("        COUNT (1) AS orgTotal");
        sql.append("        ,SUM (lessonNum) AS lessonTotal");
        sql.append("        ,SUM (flipLessonNum) AS flipLessonTotal");
        sql.append("        ,SUM (autonomousLessonNum) AS autonomousLessonTotal FROM ( SELECT");
        sql.append("            a.orgName");
        sql.append("            ,a.sectionName");
        sql.append("            ,SUM (lessonNum) AS lessonNum");
        sql.append("            ,SUM (flipLessonNum) AS flipLessonNum");
        sql.append("            ,SUM (autonomousLessonNum) AS autonomousLessonNum FROM ( SELECT");
        sql.append("                scs.name AS sectionName");
        sql.append("                ,l.sectionCode");
        sql.append("                ,l.orgName");
        sql.append("                ,COUNT (id) AS lessonNum");
        sql.append("                ,0 AS flipLessonNum");
        sql.append("                ,0 AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN ");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                scs.name");
        sql.append("                ,l.sectionCode");
        sql.append("                ,l.orgName ");
        sql.append("            UNION");
        sql.append("            ALL SELECT");
        sql.append("                scs.name AS sectionName");
        sql.append("                ,l.sectionCode");
        sql.append("                ,l.orgName");
        sql.append("                ,0 AS lessonNum");
        sql.append("                ,COUNT (id) AS flipLessonNum");
        sql.append("                ,0 AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN ");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        sql.append("                AND l.lessonMode = 10 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        sql.append("                ");
        sql.append("            GROUP BY ");
        sql.append("                scs.name");
        sql.append("                ,l.sectionCode");
        sql.append("                ,l.orgName ");
        sql.append("            UNION");
        sql.append("            ALL SELECT ");
        sql.append("                scs.name AS sectionName");
        sql.append("                ,l.sectionCode");
        sql.append("                ,l.orgName");
        sql.append("                ,0 AS lessonNum");
        sql.append("                ,0 AS flipLessonNum");
        sql.append("                ,COUNT (id) AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        sql.append("                AND l.lessonMode = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND l.orgName LIKE :orgName ");
        }
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                scs.name");
        sql.append("                ,l.sectionCode");
        sql.append("                ,l.orgName ) a GROUP ");
        sql.append("        BY");
        sql.append("            sectionName");
        sql.append("            ,orgName");
        sql.append("            ,sectionCode ) b");

        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getCourseSubjectReport(Map<String, Object> param, Integer page, Integer rows) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("subjectCode", subjectCode);
        sql.append("SELECT");
        sql.append("        a.sectionName");
        sql.append("        ,a.subjectName");
        sql.append("        ,SUM (lessonNum) AS lessonNum");
        sql.append("        ,SUM (flipLessonNum) AS flipLessonNum");
        sql.append("        ,SUM (autonomousLessonNum) AS autonomousLessonNum ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            scs.name AS sectionName");
        sql.append("            ,l.sectionCode");
        sql.append("            ,scsu.name AS subjectName");
        sql.append("            ,l.subjectCode");
        sql.append("            ,COUNT (id) AS lessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,0 AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = l.subjectCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            scs.name");
        sql.append("            ,l.sectionCode");
        sql.append("            ,scsu.name");
        sql.append("            ,l.subjectCode ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scs.name AS sectionName");
        sql.append("            ,l.sectionCode");
        sql.append("            ,scsu.name AS subjectName");
        sql.append("            ,l.subjectCode");
        sql.append("            ,0 AS lessonNum");
        sql.append("            ,COUNT (id) AS flipLessonNum");
        sql.append("            ,0 AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = l.subjectCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        sql.append("            AND l.lessonMode = 10 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            scs.name");
        sql.append("            ,l.sectionCode");
        sql.append("            ,scsu.name");
        sql.append("            ,l.subjectCode ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            scs.name AS sectionName");
        sql.append("            ,l.sectionCode");
        sql.append("            ,scsu.name AS subjectName");
        sql.append("            ,l.subjectCode");
        sql.append("            ,0 AS lessonNum");
        sql.append("            ,0 AS flipLessonNum");
        sql.append("            ,COUNT (id) AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            lesson l ");
        sql.append("            ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_section scs ");
        sql.append("                ON scs.code = l.sectionCode ");
        sql.append("                ");
        sql.append("        LEFT JOIN");
        sql.append("            share_code_subject scsu ");
        sql.append("                ON scsu.code = l.subjectCode ");
        sql.append("        WHERE");
        sql.append("            l.flagDelete = 0 ");
        sql.append("            AND l.lessonMode = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            scs.name");
        sql.append("            ,l.sectionCode");
        sql.append("            ,scsu.name");
        sql.append("            ,l.subjectCode ");
        sql.append("    ) a ");
        sql.append("    ");
        sql.append("GROUP BY");
        sql.append("    sectionName");
        sql.append("    ,subjectName");
        sql.append("    ,sectionCode");
        sql.append("    ,subjectcode ");
        sql.append("    ");
        sql.append("ORDER BY");
        sql.append("    sectionCode");
        sql.append("    ,subjectCode");

        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectCourseSubjectReportTotal(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("subjectCode", subjectCode);
        sql.append("SELECT");
        sql.append("        COUNT (1) AS subjectTotal");
        sql.append("        ,SUM (lessonNum) AS lessonTotal");
        sql.append("        ,SUM (flipLessonNum) AS flipLessonTotal");
        sql.append("        ,SUM (autonomousLessonNum) AS autonomousLessonTotal FROM ( SELECT");
        sql.append("            a.sectionName");
        sql.append("            ,a.subjectName");
        sql.append("            ,SUM (lessonNum) AS lessonNum");
        sql.append("            ,SUM (flipLessonNum) AS flipLessonNum");
        sql.append("            ,SUM (autonomousLessonNum) AS autonomousLessonNum ");
        sql.append("        FROM");
        sql.append("            ( SELECT");
        sql.append("                scs.name AS sectionName");
        sql.append("                ,l.sectionCode");
        sql.append("                ,scsu.name AS subjectName");
        sql.append("                ,l.subjectCode");
        sql.append("                ,COUNT (id) AS lessonNum");
        sql.append("                ,0 AS flipLessonNum");
        sql.append("                ,0 AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode ");
        sql.append("                    ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_subject scsu ");
        sql.append("                    ON scsu.code = l.subjectCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                scs.name");
        sql.append("                ,l.sectionCode");
        sql.append("                ,scsu.name");
        sql.append("                ,l.subjectCode ");
        sql.append("            UNION");
        sql.append("            ALL SELECT");
        sql.append("                scs.name AS sectionName");
        sql.append("                ,l.sectionCode");
        sql.append("                ,scsu.name AS subjectName");
        sql.append("                ,l.subjectCode");
        sql.append("                ,0 AS lessonNum");
        sql.append("                ,COUNT (id) AS flipLessonNum");
        sql.append("                ,0 AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode ");
        sql.append("                    ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_subject scsu ");
        sql.append("                    ON scsu.code = l.subjectCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        sql.append("                AND l.lessonMode = 10 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                scs.name");
        sql.append("                ,l.sectionCode");
        sql.append("                ,scsu.name");
        sql.append("                ,l.subjectCode ");
        sql.append("            UNION");
        sql.append("            ALL SELECT");
        sql.append("                scs.name AS sectionName");
        sql.append("                ,l.sectionCode");
        sql.append("                ,scsu.name AS subjectName");
        sql.append("                ,l.subjectCode");
        sql.append("                ,0 AS lessonNum");
        sql.append("                ,0 AS flipLessonNum");
        sql.append("                ,COUNT (id) AS autonomousLessonNum ");
        sql.append("            FROM");
        sql.append("                lesson l ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_section scs ");
        sql.append("                    ON scs.code = l.sectionCode ");
        sql.append("                    ");
        sql.append("            LEFT JOIN");
        sql.append("                share_code_subject scsu ");
        sql.append("                    ON scsu.code = l.subjectCode ");
        sql.append("            WHERE");
        sql.append("                l.flagDelete = 0 ");
        sql.append("                AND l.lessonMode = 0 ");
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("            AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("            AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("            AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("            AND l.subjectCode = :subjectCode ");
        }
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                scs.name");
        sql.append("                ,l.sectionCode");
        sql.append("                ,scsu.name");
        sql.append("                ,l.subjectCode ) a ");
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            sectionName");
        sql.append("            ,subjectName");
        sql.append("            ,sectionCode");
        sql.append("            ,subjectcode ) b");
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getCourseCountAggregateDetail(Map<String, Object> param, Integer page,
                    Integer rows) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String lessonMode = MapUtils.getString(param, "lessonMode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String tbcCode = MapUtils.getString(param, "tbcCode");
        String orgName = MapUtils.getString(param, "orgName");
        String chapterCodes = MapUtils.getString(param, "chapterCodes");
        String lessonName = MapUtils.getString(param, "lessonName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(chapterCodes) && chapterCodes.contains(",")) {
            chapterCodes = "'" + chapterCodes.replace(",", "','") + "'";
        } else if (StringUtils.isEmpty(chapterCodes)) {
            chapterCodes = null;
        } else {
            chapterCodes = "'" + chapterCodes + "'";
        }
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("lessonMode", lessonMode);
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("tbcCode", tbcCode);
        sqlParam.put("orgName", "%" + orgName + "%");
        sqlParam.put("chapterCodes", chapterCodes);
        sqlParam.put("lessonName", "%" + lessonName + "%");
        sql.append("SELECT");
        sql.append("        l.lessonName");
        sql.append("        ,(case l.shareLevel WHEN 10 THEN '个人私有' WHEN 20 THEN '校内共享' WHEN 30 THEN '区域共享' END) AS shareLevel");
        sql.append("        ,(case l.lessonMode WHEN 10 THEN '翻转课堂模式' WHEN 0 THEN '自主创建模式' END) AS lessonMode");
        sql.append("        ,l.sectionCode");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,l.subjectCode");
        sql.append("        ,scsu.name AS subjectName");
        sql.append("        ,l.gradeCode");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,l.createTime");
        sql.append("        ,l.teacherName");
        sql.append("        ,l.orgName");
        sql.append("        ,l.referCount ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = l.sectionCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsu ");
        sql.append("            ON scsu.code = l.subjectCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = l.gradeCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        lesson_chapter lc ");
        sql.append("            ON l.id = lc.lessonId ");
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(lessonMode)) {
            sql.append("        AND l.lessonMode = :lessonMode ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append("        AND l.gradeCode = :gradeCode ");
        }
        // if (StringUtils.isNotEmpty(tbcCode)) {
        // sql.append(" AND l.textbookVerCode = :tbcCode ");
        // }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(lessonName)) {
            sql.append("        AND l.lessonName LIKE :lessonName ");
        }
        if (StringUtils.isNotEmpty(chapterCodes)) {
            sql.append("        AND lc.chapterCode ");
            sql.append("        ");
            sql.append(" IN (" + chapterCodes + ")");
            // sql.append(" IN (:chapterCodes)");
        }
        sql.append("");
        sql.append(" GROUP BY ");
        sql.append("    l.lessonName");
        sql.append("    ,l.shareLevel");
        sql.append("    ,l.lessonMode");
        sql.append("    ,l.sectionCode");
        sql.append("    ,scs.name");
        sql.append("    ,l.subjectCode");
        sql.append("    ,scsu.name");
        sql.append("    ,l.gradeCode");
        sql.append("    ,scg.name");
        sql.append("    ,l.createTime");
        sql.append("    ,l.teacherName");
        sql.append("    ,l.orgName");
        sql.append("    ,l.referCount");
        sql.append("    ORDER BY l.sectionCode");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectCourseCountAggregateDetailTotal(Map<String, Object> param) {
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String shareLevel = MapUtils.getString(param, "shareLevel");
        String lessonMode = MapUtils.getString(param, "lessonMode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String tbcCode = MapUtils.getString(param, "tbcCode");
        String orgName = MapUtils.getString(param, "orgName");
        String chapterCodes = MapUtils.getString(param, "chapterCodes");
        String lessonName = MapUtils.getString(param, "lessonName");
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(chapterCodes) && chapterCodes.contains(",")) {
            chapterCodes = "'" + chapterCodes.replace(",", "','") + "'";
        } else if (StringUtils.isEmpty(chapterCodes)) {
            chapterCodes = null;
        } else {
            chapterCodes = "'" + chapterCodes + "'";
        }
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("lessonMode", lessonMode);
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("tbcCode", tbcCode);
        sqlParam.put("orgName", "%" + orgName + "%");
        sqlParam.put("lessonName", "%" + lessonName + "%");
        sqlParam.put("chapterCodes", chapterCodes);
        sql.append("SELECT");
        sql.append("        (SUM(pt) + SUM(fz)) AS lessonTotal ");
        sql.append("        ,SUM(pt) AS autonomousLessonTotal");
        sql.append("        ,SUM(fz) AS flipLessonTotal ");
        sql.append("        ,SUM(referCount) AS referCount ");
        sql.append("    FROM");
        sql.append("        (SELECT");
        sql.append("            COUNT(1) AS pt");
        sql.append("            ,0 AS fz");
        sql.append("            ,SUM(referCount) AS referCount ");
        sql.append("        FROM");
        sql.append("            ( SELECT ");
        sql.append("        l.lessonName");
        sql.append("        ,l.shareLevel");
        sql.append("        ,l.lessonMode");
        sql.append("        ,l.sectionCode");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,l.subjectCode");
        sql.append("        ,scsu.name AS subjectName");
        sql.append("        ,l.gradeCode");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,l.createTime");
        sql.append("        ,l.teacherName");
        sql.append("        ,l.orgCode");
        sql.append("        ,l.referCount ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = l.sectionCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsu ");
        sql.append("            ON scsu.code = l.subjectCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = l.gradeCode ");
        sql.append("            ");
        if (StringUtils.isNotEmpty(chapterCodes)) {
            sql.append("    INNER JOIN");
            sql.append("        lesson_chapter lc ");
            sql.append("            ON l.id = lc.lessonId ");
        }
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(lessonMode)) {
            sql.append("        AND l.lessonMode = :lessonMode ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append("        AND l.gradeCode = :gradeCode ");
        }
        // if (StringUtils.isNotEmpty(tbcCode)) {
        // sql.append(" AND l.textbookVerCode = :tbcCode ");
        // }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(lessonName)) {
            sql.append("        AND l.lessonName LIKE :lessonName ");
        }
        if (StringUtils.isNotEmpty(chapterCodes)) {
            sql.append("        AND lc.chapterCode ");
            sql.append("        ");
            sql.append(" IN (" + chapterCodes + ")");
        }
        sql.append("");
        // sql.append(" GROUP BY ");
        // sql.append(" l.lessonName");
        // sql.append(" ,l.shareLevel");
        // sql.append(" ,l.lessonMode");
        // sql.append(" ,l.sectionCode");
        // sql.append(" ,scs.name");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scsu.name");
        // sql.append(" ,l.gradeCode");
        // sql.append(" ,scg.name");
        // sql.append(" ,l.createTime");
        // sql.append(" ,l.teacherName");
        // sql.append(" ,l.orgCode");
        // sql.append(" ,l.referCount");
        sql.append("                ) a ");
        sql.append("        WHERE");
        sql.append("            a.lessonMode = 0 ");
        sql.append("        UNION");
        sql.append("        ALL SELECT");
        sql.append("            0 AS pt");
        sql.append("            ,COUNT(1) AS fz");
        sql.append("            ,SUM(referCount) AS referCount ");
        sql.append("        FROM");
        sql.append("            (");
        sql.append("SELECT");
        sql.append("        l.lessonName");
        sql.append("        ,l.shareLevel");
        sql.append("        ,l.lessonMode");
        sql.append("        ,l.sectionCode");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,l.subjectCode");
        sql.append("        ,scsu.name AS subjectName");
        sql.append("        ,l.gradeCode");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,l.createTime");
        sql.append("        ,l.teacherName");
        sql.append("        ,l.orgCode");
        sql.append("        ,l.referCount ");
        sql.append("    FROM");
        sql.append("        lesson l ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = l.sectionCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsu ");
        sql.append("            ON scsu.code = l.subjectCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = l.gradeCode ");
        sql.append("            ");
        if (StringUtils.isNotEmpty(chapterCodes)) {
            sql.append("    INNER JOIN");
            sql.append("        lesson_chapter lc ");
            sql.append("            ON l.id = lc.lessonId ");
        }
        sql.append("    WHERE");
        sql.append("        l.flagDelete = 0 ");
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND l.createTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND l.createTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(shareLevel)) {
            sql.append("        AND l.shareLevel = :shareLevel ");
        }
        if (StringUtils.isNotEmpty(lessonMode)) {
            sql.append("        AND l.lessonMode = :lessonMode ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append("        AND l.gradeCode = :gradeCode ");
        }
        if (StringUtils.isNotEmpty(tbcCode)) {
            sql.append("        AND l.tbCode = :tbcCode ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("        AND l.orgName LIKE :orgName ");
        }
        if (StringUtils.isNotEmpty(lessonName)) {
            sql.append("        AND l.lessonName LIKE :lessonName ");
        }
        if (StringUtils.isNotEmpty(chapterCodes)) {
            sql.append("        AND lc.chapterCode ");
            sql.append("        ");
            sql.append(" IN (" + chapterCodes + ")");
        }
        sql.append("");
        // sql.append(" GROUP BY ");
        // sql.append(" l.lessonName");
        // sql.append(" ,l.shareLevel");
        // sql.append(" ,l.lessonMode");
        // sql.append(" ,l.sectionCode");
        // sql.append(" ,scs.name");
        // sql.append(" ,l.subjectCode");
        // sql.append(" ,scsu.name");
        // sql.append(" ,l.gradeCode");
        // sql.append(" ,scg.name");
        // sql.append(" ,l.createTime");
        // sql.append(" ,l.teacherName");
        // sql.append(" ,l.orgCode");
        // sql.append(" ,l.referCount");
        sql.append("  ) a ");
        sql.append("        WHERE");
        sql.append("            a.lessonMode = 10");
        sql.append("        )c");

        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getTeacherCourseActualize(Map<String, Object> param, Integer page, Integer rows) {
        String yearTermCode = MapUtils.getString(param, "yearTermCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String publishStartDate = MapUtils.getString(param, "publishStartDate");
        String publishEndDate = MapUtils.getString(param, "publishEndDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String teacherName = MapUtils.getString(param, "teacherName");
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(publishStartDate)) {
            publishStartDate = publishStartDate + " 00:00:00";
        }
        if (StringUtils.isNotEmpty(publishEndDate)) {
            publishEndDate = publishEndDate + " 23:59:59";
        }
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("yearTermCode", yearTermCode);
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("publishStartDate", publishStartDate);
        sqlParam.put("publishEndDate", publishEndDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("teacherName", "%" + teacherName + "%");
        sqlParam.put("orgName", "%" + orgName + "%");
        sql.append("SELECT");
        sql.append("        teacherCode");
        sql.append("        ,orgCode");
        sql.append("        ,teacherName");
        sql.append("        ,orgName");
        sql.append("        ,COUNT (DISTINCT lessonId) AS lessonNum");
        sql.append("        ,( SELECT");
        sql.append("            COUNT (DISTINCT lessonId) ");
        sql.append("        FROM");
        sql.append("            platform_lesson_implement_detail ");
        sql.append("        WHERE");
        sql.append("            lessonMode = 10 ");
        sql.append("            AND teacherCode = a.teacherCode ");
        sql.append("            AND orgCode = a.orgCode ");
        sql.append("            AND teacherName = a.teacherName ");
        sql.append("            AND orgName = a.orgName   ");
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("                    AND yearTermCode = :yearTermCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND lessonCreateTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND lessonCreateTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(publishStartDate)) {
            sql.append(" AND starTime > :publishStartDate ");
        }
        if (StringUtils.isNotEmpty(publishEndDate)) {
            sql.append(" AND starTime < :publishEndDate ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("                    AND sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("                    AND subjectCode = :subjectCode ");
        }
        sql.append("        ) AS flipLessonNum");
        sql.append("        ,( SELECT");
        sql.append("            COUNT (DISTINCT lessonId) ");
        sql.append("        FROM");
        sql.append("            platform_lesson_implement_detail ");
        sql.append("        WHERE");
        sql.append("            lessonMode = 0 ");
        sql.append("            AND teacherCode = a.teacherCode ");
        sql.append("            AND orgCode = a.orgCode ");
        sql.append("            AND teacherName = a.teacherName ");
        sql.append("            AND orgName = a.orgName ");
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("                    AND yearTermCode = :yearTermCode ");
        }
        if (StringUtils.isNotEmpty(publishStartDate)) {
            sql.append(" AND starTime > :publishStartDate ");
        }
        if (StringUtils.isNotEmpty(publishEndDate)) {
            sql.append(" AND starTime < :publishEndDate ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND lessonCreateTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND lessonCreateTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("                    AND sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("                    AND subjectCode = :subjectCode ");
        }
        sql.append("           )AS autonomousLessonNum");
        sql.append("        ,( SUM ( CASE ");
        sql.append("            WHEN teachingClassCode IS NOT NULL ");
        sql.append("        THEN");
        sql.append("            1 ");
        sql.append("            ELSE 0 ");
        sql.append("        END ) ");
        sql.append("    ) AS publishNum, (");
        sql.append("        SUM ( CASE ");
        sql.append("            WHEN flagImplement = 1 ");
        sql.append("        THEN");
        sql.append("            1 ");
        sql.append("            ELSE 0 ");
        sql.append("        END ) ");
        sql.append("    ) AS finishNum ");
        sql.append("FROM");
        sql.append("    platform_lesson_implement_detail a ");
        sql.append("    ");
        sql.append("    WHERE");
        sql.append("        1 = 1 ");
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("                    AND a.yearTermCode = :yearTermCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND a.lessonCreateTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND a.lessonCreateTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(publishStartDate)) {
            sql.append(" AND a.starTime > :publishStartDate ");
        }
        if (StringUtils.isNotEmpty(publishEndDate)) {
            sql.append(" AND a.starTime < :publishEndDate ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("                    AND a.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("                    AND a.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("                    AND a.teacherName LIKE :teacherName ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("                    AND a.orgName LIKE :orgName ");
        }
        sql.append("GROUP BY");
        sql.append("    teacherCode");
        sql.append("    ,orgCode");
        sql.append("    ,teacherName");
        sql.append("    ,orgName");
        sql.append("    ORDER BY orgName,teacherName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectTeacherCourseActualizeTotal(Map<String, Object> param) {
        String yearTermCode = MapUtils.getString(param, "yearTermCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String publishStartDate = MapUtils.getString(param, "publishStartDate");
        String publishEndDate = MapUtils.getString(param, "publishEndDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String teacherName = MapUtils.getString(param, "teacherName");
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(publishStartDate)) {
            publishStartDate = publishStartDate + " 00:00:00";
        }
        if (StringUtils.isNotEmpty(publishEndDate)) {
            publishEndDate = publishEndDate + " 23:59:59";
        }
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("yearTermCode", yearTermCode);
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("publishStartDate", publishStartDate);
        sqlParam.put("publishEndDate", publishEndDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("teacherName", "%" + teacherName + "%");
        sqlParam.put("orgName", "%" + orgName + "%");
        sql.append("SELECT");
        sql.append("        COUNT (1) AS teacherTotal");
        sql.append("        ,SUM (totalNum) AS lessonTotal");
        sql.append("        ,SUM (publishNum) AS publishTotal");
        sql.append("        ,SUM (finishNum) AS finishTotal ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            teacherCode");
        sql.append("            ,orgCode");
        sql.append("            ,teacherName");
        sql.append("            ,orgName");
        sql.append("            ,COUNT (DISTINCT lessonId) AS totalNum");
        sql.append("            ,( SUM ( CASE ");
        sql.append("                WHEN teachingClassCode IS NOT NULL ");
        sql.append("            THEN");
        sql.append("                1 ");
        sql.append("                ELSE 0 ");
        sql.append("            END ) ");
        sql.append("        ) AS publishNum, (");
        sql.append("            SUM ( CASE ");
        sql.append("                WHEN flagImplement = 1 ");
        sql.append("            THEN");
        sql.append("                1 ");
        sql.append("                ELSE 0 ");
        sql.append("            END ) ");
        sql.append("        ) AS finishNum ");
        sql.append("    FROM");
        sql.append("        platform_lesson_implement_detail a ");
        sql.append("        ");
        sql.append("    WHERE");
        sql.append("        1 = 1 ");
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("                    AND a.yearTermCode = :yearTermCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append(" AND a.lessonCreateTime > :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append(" AND a.lessonCreateTime < :endDate ");
        }
        if (StringUtils.isNotEmpty(publishStartDate)) {
            sql.append(" AND a.starTime > :publishStartDate ");
        }
        if (StringUtils.isNotEmpty(publishEndDate)) {
            sql.append(" AND a.starTime < :publishEndDate ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("                    AND a.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("                    AND a.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("                    AND a.teacherName LIKE :teacherName ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("                    AND a.orgName LIKE :orgName ");
        }
        sql.append("    GROUP BY");
        sql.append("        teacherCode");
        sql.append("        ,orgCode");
        sql.append("        ,teacherName");
        sql.append("        ,orgName ) b");
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getSchoolCourseActualize(Map<String, Object> param, Integer page, Integer rows) {
        String endDate = MapUtils.getInteger(param, "endDate").toString();
        String yearTermCode = MapUtils.getString(param, "yearTermCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String orgName = MapUtils.getString(param, "orgName");
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        orgCode");
        sql.append("        ,orgName");
        sql.append("        ,scs.name AS sectionName ");
        sql.append("        ,COUNT (DISTINCT lessonId) AS lessonNum");
        sql.append("        ,( SELECT");
        sql.append("            COUNT (DISTINCT lessonId) ");
        sql.append("        FROM");
        sql.append("            platform_lesson_implement_detail ");
        sql.append("        WHERE");
        sql.append("            lessonMode = 10 ");
        sql.append("            AND orgCode = a.orgCode ");
        sql.append("            AND orgName = a.orgName ");
        sql.append("            AND sectionCode = a.sectionCode ");
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("            AND yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", yearTermCode);
        }
        if (ObjectUtils.isNotNull(endDate)) {
            sql.append("            AND lessonCreateTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("            ) as flippedClassNum ");
        sql.append("        ,( SELECT");
        sql.append("            COUNT (DISTINCT lessonId) ");
        sql.append("        FROM");
        sql.append("            platform_lesson_implement_detail ");
        sql.append("        WHERE");
        sql.append("            lessonMode = 0 ");
        sql.append("            AND orgCode = a.orgCode ");
        sql.append("            AND orgName = a.orgName ");
        sql.append("            AND sectionCode = a.sectionCode ");
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("            AND yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", yearTermCode);
        }
        if (ObjectUtils.isNotNull(endDate)) {
            sql.append("            AND lessonCreateTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("            ) as normalClassNum ");
        sql.append("        ,( SUM ( CASE ");
        sql.append("            WHEN teachingClassCode IS NOT NULL ");
        sql.append("        THEN");
        sql.append("            1 ");
        sql.append("            ELSE 0 ");
        sql.append("        END ) ");
        sql.append("    ) as publishedClassNum , (");
        sql.append("        SUM ( CASE ");
        sql.append("            WHEN flagImplement = 1 ");
        sql.append("        THEN");
        sql.append("            1 ");
        sql.append("            ELSE 0 ");
        sql.append("        END ) ");
        sql.append("    ) as usedClassNum ");
        sql.append("FROM");
        sql.append("    platform_lesson_implement_detail a ");
        sql.append("");
        sql.append("LEFT JOIN");
        sql.append("    share_code_section scs ");
        sql.append("        ON scs.code = a.sectionCode");
        sql.append("    ");
        sql.append("    WHERE");
        sql.append(" 1 = 1 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND a.sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("            AND a.yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", yearTermCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND a.orgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (ObjectUtils.isNotNull(endDate)) {
            sql.append("            AND a.lessonCreateTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("GROUP BY");
        sql.append("    orgCode");
        sql.append("    ,orgName");
        sql.append("    ,sectionCode");
        sql.append("    ,scs.name");
        sql.append("    ORDER BY");
        sql.append("       orgName");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectSchoolCourseActualizeTotal(Map<String, Object> param) {
        String endDate = MapUtils.getInteger(param, "endDate").toString();
        String yearTermCode = MapUtils.getString(param, "yearTermCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String orgName = MapUtils.getString(param, "orgName");
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (orgCode) as schoolNum");
        sql.append("        ,SUM (totalNum) as lessonTotalNum");
        sql.append("        ,SUM (usedNum) as usedClassTotalNum ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            orgCode");
        sql.append("            ,orgName");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,COUNT (DISTINCT lessonId) AS totalNum");
        sql.append("            ,( SUM ( CASE ");
        sql.append("                WHEN flagImplement = 1 ");
        sql.append("            THEN");
        sql.append("                1 ");
        sql.append("                ELSE 0 ");
        sql.append("            END ) ");
        sql.append("        ) AS usedNum ");
        sql.append("    FROM");
        sql.append("        platform_lesson_implement_detail a ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = a.sectionCode ");
        sql.append("    WHERE");
        sql.append(" 1 = 1 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND a.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("            AND a.yearTermCode = :yearTermCode ");
            sqlParam.put("yearTermCode", yearTermCode);
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("            AND a.orgName LIKE :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (ObjectUtils.isNotNull(endDate)) {
            sql.append("            AND a.lessonCreateTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("            ");
        sql.append("    GROUP BY");
        sql.append("        orgCode");
        sql.append("        ,orgName");
        sql.append("        ,scs.name ) b");
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getSubjectCourseActualize(Map<String, Object> param, Integer page, Integer rows) {
        String endDate = MapUtils.getInteger(param, "endDate").toString();
        String yearTermCode = MapUtils.getString(param, "yearTermCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        subjectCode");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,scsu.name AS subjectName");
        sql.append("        ,COUNT (DISTINCT lessonId) AS lessonNum ");
        sql.append("        ,( SELECT");
        sql.append("            COUNT (DISTINCT lessonId) ");
        sql.append("        FROM");
        sql.append("            platform_lesson_implement_detail ");
        sql.append("        WHERE");
        sql.append("            lessonMode = 10 ");
        sql.append("            AND subjectCode = a.subjectCode ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("            AND yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", yearTermCode);
        }
        if (ObjectUtils.isNotNull(endDate)) {
            sql.append("            AND lessonCreateTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("  ) as flippedClassNum ");
        sql.append("        ,( SELECT");
        sql.append("            COUNT (DISTINCT lessonId) ");
        sql.append("        FROM");
        sql.append("            platform_lesson_implement_detail ");
        sql.append("        WHERE");
        sql.append("            lessonMode = 0 ");
        sql.append("            AND subjectCode = a.subjectCode ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("            AND yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", yearTermCode);
        }
        if (ObjectUtils.isNotNull(endDate)) {
            sql.append("            AND lessonCreateTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("  ) as normalClassNum ");
        sql.append("        ,( SUM ( CASE ");
        sql.append("            WHEN teachingClassCode IS NOT NULL ");
        sql.append("        THEN");
        sql.append("            1 ");
        sql.append("            ELSE 0 ");
        sql.append("        END ) ");
        sql.append("    ) as publishedClassNum, (");
        sql.append("        SUM ( CASE ");
        sql.append("            WHEN flagImplement = 1 ");
        sql.append("        THEN");
        sql.append("            1 ");
        sql.append("            ELSE 0 ");
        sql.append("        END ) ");
        sql.append("    ) as usedClassNum ");
        sql.append("FROM");
        sql.append("    platform_lesson_implement_detail a ");
        sql.append("    ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_section scs ");
        sql.append("        ON scs.code = a.sectionCode ");
        sql.append("        ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_subject scsu ");
        sql.append("        ON scsu.code = a.subjectCode ");
        sql.append("        ");
        sql.append("    WHERE");
        sql.append(" 1 = 1 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND a.sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("            AND a.yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", yearTermCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("              AND a.subjectCode =:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (ObjectUtils.isNotNull(endDate)) {
            sql.append("            AND a.lessonCreateTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("GROUP BY");
        sql.append("    subjectCode");
        sql.append("    ,scsu.name");
        sql.append("    ,scs.name ");
        sql.append("    ");
        sql.append("ORDER BY");
        sql.append("    subjectCode");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    @Override
    public Map<String, Object> selectSubjectCourseActualizeTotal(Map<String, Object> param) {
        String endDate = MapUtils.getInteger(param, "endDate").toString();
        String yearTermCode = MapUtils.getString(param, "yearTermCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (subjectCode) AS subjectNum");
        sql.append("        ,SUM (lessonNum) AS lessonTotalNum");
        sql.append("        ,SUM (usedClassNum) AS usedClassTotalNum ");
        sql.append("    FROM");
        sql.append("        ( SELECT");
        sql.append("            subjectCode");
        sql.append("            ,scs.name AS sectionName");
        sql.append("            ,scsu.name AS subjectName");
        sql.append("            ,COUNT (DISTINCT lessonId) AS lessonNum");
        sql.append("            ,( SUM ( CASE ");
        sql.append("                WHEN flagImplement = 1 ");
        sql.append("            THEN");
        sql.append("                1 ");
        sql.append("                ELSE 0 ");
        sql.append("            END ) ");
        sql.append("        ) AS usedClassNum ");
        sql.append("    FROM");
        sql.append("        platform_lesson_implement_detail a ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = a.sectionCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsu ");
        sql.append("            ON scsu.code = a.subjectCode ");
        sql.append("    WHERE");
        sql.append("        1 = 1 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("            AND a.sectionCode =:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("            AND a.yearTermCode =:yearTermCode ");
            sqlParam.put("yearTermCode", yearTermCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("              AND a.subjectCode =:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (ObjectUtils.isNotNull(endDate)) {
            sql.append("            AND a.lessonCreateTime < :endDate ");
            sqlParam.put("endDate", endDate);
        }
        sql.append("        ");
        sql.append("    GROUP BY");
        sql.append("        subjectCode");
        sql.append("        ,scsu.name");
        sql.append("        ,scs.name ) b");

        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public Page<Map<String, Object>> getSubjectCourseActualizeDetail(Map<String, Object> param, Integer page,
                    Integer rows) {
        String teacherCode = MapUtils.getString(param, "teacherCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String yearTermCode = MapUtils.getString(param, "yearTermCode");
        String startDate = MapUtils.getString(param, "startDate");
        String endDate = MapUtils.getString(param, "endDate");
        String publishStartDate = MapUtils.getString(param, "publishStartDate");
        String publishEndDate = MapUtils.getString(param, "publishEndDate");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String teacherName = MapUtils.getString(param, "teacherName");
        String orgName = MapUtils.getString(param, "orgName");
        if (StringUtils.isNotEmpty(publishStartDate)) {
            publishStartDate = publishStartDate + " 00:00:00";
        }
        if (StringUtils.isNotEmpty(publishEndDate)) {
            publishEndDate = publishEndDate + " 23:59:59";
        }
        if (StringUtils.isNotEmpty(startDate)) {
            startDate = startDate + " 00:00:00";
        }
        endDate = endDate + " 23:59:59";
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("teacherCode", teacherCode);
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("startDate", startDate);
        sqlParam.put("endDate", endDate);
        sqlParam.put("publishStartDate", publishStartDate);
        sqlParam.put("publishEndDate", publishEndDate);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("yearTermCode", yearTermCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("teacherName", teacherName);
        sqlParam.put("orgName", orgName);
        sql.append("SELECT");
        sql.append("        l.lessonId");
        sql.append("        ,l.lessonCode");
        sql.append("        ,le.lessonName");
        sql.append("        ,l.teacherCode");
        sql.append("        ,l.teacherName");
        sql.append("        ,( CASE l.lessonMode ");
        sql.append("            WHEN 10 ");
        sql.append("        THEN");
        sql.append("            '翻转课堂模式' ");
        sql.append("            ELSE '自主创建模式' ");
        sql.append("        END ");
        sql.append("    ) AS lessonMode, l.teachingClassCode, ISNULL(stc.teachingClassName, '--') as teachingClassName, l.orgCode, l.orgName, l.yearTermCode, l.sectionCode, l.subjectCode, scs.name AS subjectName, l.gradeCode, l.releaseTime as publishTime, l.starTime, l.pushTimes AS pushCount, l.pushTimesCompleted AS pushFinishCount, l.preTCR AS coverage, l.interactionMaxTCR, (");
        sql.append("        CASE flagImplement ");
        sql.append("            WHEN 1 ");
        sql.append("        THEN");
        sql.append("            '是' ");
        sql.append("            ELSE '否' ");
        sql.append("        END ");
        sql.append("    ) AS flagFinish, l.modifyTime, l.lessonCreateTime AS createTime ");
        sql.append("FROM");
        sql.append("    platform_lesson_implement_detail l ");
        sql.append("    ");
        sql.append("LEFT JOIN");
        sql.append("    share_teaching_class stc ");
        sql.append("        ON stc.teachingClassCode = l.teachingClassCode ");
        sql.append("        ");
        sql.append("LEFT JOIN");
        sql.append("    share_code_subject scs ");
        sql.append("        ON scs.code = l.subjectCode ");
        sql.append("        ");
        sql.append("LEFT JOIN");
        sql.append("    lesson le ");
        sql.append("        ON le.id = l.lessonId ");
        sql.append(" WHERE ");
        sql.append("    1 = 1 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    AND l.orgCode = :orgCode ");
        }
        if (StringUtils.isNotEmpty(teacherCode)) {
            sql.append("    AND l.teacherCode = :teacherCode ");
        }
        if (StringUtils.isNotEmpty(yearTermCode)) {
            sql.append("    AND l.yearTermCode = :yearTermCode ");
        }
        if (StringUtils.isNotEmpty(startDate)) {
            sql.append("    AND l.lessonCreateTime >= :startDate ");
        }
        if (StringUtils.isNotEmpty(endDate)) {
            sql.append("    AND l.lessonCreateTime <= :endDate ");
        }
        if (StringUtils.isNotEmpty(publishStartDate)) {
            sql.append(" AND l.starTime > :publishStartDate ");
        }
        if (StringUtils.isNotEmpty(publishEndDate)) {
            sql.append(" AND l.starTime < :publishEndDate ");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    AND l.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("    AND l.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(teacherName)) {
            sql.append("    AND l.teacherName = :teacherName ");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("    AND l.orgName = :orgName ");
        }
        sql.append("    ");
        sql.append("ORDER BY");
        sql.append("    lessonCreateTime DESC");
        sql.append("    ,l.subjectCode");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }
}
