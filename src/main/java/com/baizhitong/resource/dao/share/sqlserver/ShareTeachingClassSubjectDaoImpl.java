package com.baizhitong.resource.dao.share.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareTeachingClassSubjectDao;
import com.baizhitong.resource.model.share.ShareTeachingClassSubject;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

@Component
public class ShareTeachingClassSubjectDaoImpl extends BaseSqlServerDao<ShareTeachingClassSubject>
                implements ShareTeachingClassSubjectDao {

    @Override
    public boolean add(ShareTeachingClassSubject shareTeachingClassSubject) {
        try {
            return super.saveOne(shareTeachingClassSubject);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询课程班级学科 ()<br>
     * 
     * @param subjectCode
     * @param gradeCode
     * @param teacherCode
     * @param orgCode
     * @param studyYearCode
     * @param termCode
     * @return
     */
    public List<Map<String, Object>> getTeachingClassSubject(String teachingClassCode, String subjectCode,
                    String gradeCode, String teacherCode, String orgCode, String studyYearCode, String termCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        * ");
        sql.append("    FROM");
        sql.append("        share_teaching_class_subject");
        sql.append(" where 1=1 ");
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and  subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and  gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and  orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(teachingClassCode)) {
            sql.append(" and  teachingClassCode=:teachingClassCode ");
            sqlParam.put("teachingClassCode", teachingClassCode);
        }
        if (StringUtils.isNotEmpty(teacherCode)) {
            sql.append(" and  teacherCode=:teacherCode ");
            sqlParam.put("teacherCode", teacherCode);
        }
        if (StringUtils.isNotEmpty(studyYearCode)) {
            sql.append(" and  studyYearCode=:studyYearCode ");
            sqlParam.put("studyYearCode", studyYearCode);
        }
        if (StringUtils.isNotEmpty(termCode)) {
            sql.append(" and  termCode=:termCode ");
            sqlParam.put("termCode", termCode);
        }
        return super.findBySql(sql.toString(), sqlParam);
    }

    @Override
    public List<Map<String, Object>> selectGradeSubjectByTeacher(String teacherCode, String orgCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" select DISTINCT stcs.gradeCode,stcs.subjectCode,scg.name as gradeName,scs.name as subjectName from share_teaching_class_subject stcs ");
        sql.append(" left join share_code_grade scg on stcs.gradeCode=scg.code ");
        sql.append(" LEFT JOIN share_code_subject scs on stcs.subjectCode=scs.code ");
        sql.append(" where stcs.orgCode=? and stcs.teacherCode=? ");
        return super.findBySql(sql.toString(), new Object[] { orgCode, teacherCode });
    }

    /**
     * 保存课程班级学科 ()<br>
     * 
     * @param teachingClassList
     * @return
     */
    public int saveTeacherClassSubject(List<ShareTeachingClassSubject> teachingClassList) {
        try {
            return super.saveAll(teachingClassList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return 0;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return 0;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 更新教学班级学科的老师 ()<br>
     * 
     * @param classCode 班级编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param teacherCode 老师编码
     * @param teacherName 老师姓名
     * @param modifyPgm 修改程序
     * @return
     */
    public int updateClassTeacher(String classCode, String subjectCode, String gradeCode, String teacherCode,
                    String teacherName, String modifyPgm) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_teaching_class_subject  ");
        sql.append("    SET");
        sql.append("        teacherCode =:teacherCode");
        sql.append("        ,teacherName =:teacherName");
        sql.append("        ,modifyPgm=:modifyPgm");
        sql.append("        ,modifyTime=:modifyTime");
        sql.append("    WHERE");
        sql.append("        teachingClassCode =:teachingClassCode  ");
        sql.append("        AND subjectCode =:subjectCode  ");
        sql.append("        AND gradeCode =:gradeCode");
        sqlParam.put("teacherCode", teacherCode);
        sqlParam.put("teacherName", teacherName);
        sqlParam.put("modifyPgm", modifyPgm);
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("teachingClassCode", classCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("gradeCode", gradeCode);
        return super.update(sql.toString(), sqlParam);

    }

    /**
     * 通过gid查询课程班级学科 ()<br>
     * 
     * @param gid 主键
     * @return
     */
    public ShareTeachingClassSubject getTeachingClassSubjectByGid(String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("gid", gid);
        return super.findUnique(qr);
    }

    /**
     * 
     * (更新教师信息)<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @param gradeCode
     * @param teachingClassCodes
     * @param teacherCode
     * @param teacherName
     * @param studyYearCode
     * @param termCode
     * @param ipAddress
     * @return
     */
    @Override
    public int updateTeacherInfo(String orgCode, String subjectCode, String gradeCode, String teachingClassCodes,
                    String teacherCode, String teacherName, String studyYearCode, String termCode, String ipAddress,
                    Timestamp beginTime, Timestamp endTime, String oldTeacherCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        share_teaching_class_subject  ");
        sql.append("    SET");
        sql.append("        teacherCode = :teacherCode");
        sql.append("        ,teacherName = :teacherName");
        sql.append("        ,modifyPgm ='updateTeacherInfo'");
        sql.append("        ,modifyTime = :modifyTime");
        sql.append("        ,modifyIP = :modifyIP ");
        sql.append("        ,sysVer=sysVer+1 ");
        sql.append("        ,beginTime = :beginTime ");
        sql.append("        ,endTime = :endTime ");
        sql.append("    WHERE 1=1 ");
        if (teachingClassCodes.contains(",")) {
            teachingClassCodes = "'" + teachingClassCodes.replace(",", "','") + "'";
            sql.append(" AND teachingClassCode IN (" + teachingClassCodes + ") ");
        } else {
            sql.append("    AND teachingClassCode = :teachingClassCode ");
            sqlParam.put("teachingClassCode", teachingClassCodes);
        }
        sql.append("        AND subjectCode = :subjectCode ");
        sql.append("        AND gradeCode = :gradeCode");
        sql.append("        AND orgCode = :orgCode");
        sql.append("        AND studyYearCode = :studyYearCode");
        /* sql.append("        AND termCode = :termCode "); */
        sql.append("        AND teacherCode = :oldTeacherCode ");
        sqlParam.put("teacherCode", teacherCode);
        sqlParam.put("teacherName", teacherName);
        sqlParam.put("modifyTime", currentTime);
        sqlParam.put("modifyIP", ipAddress);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("studyYearCode", studyYearCode);
        sqlParam.put("termCode", termCode);
        sqlParam.put("beginTime", beginTime);
        sqlParam.put("endTime", endTime);
        sqlParam.put("oldTeacherCode", oldTeacherCode);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 删除任课信息 ()<br>
     * 
     * @param adminClassCode 行政班级编码
     * @param subjectCode 学科编码
     * @param teacherCode 老师编码
     * @return
     */
    public int delete(String adminClassCode, String subjectCode, String teacherCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("DELETE  ");
        sql.append("    FROM");
        sql.append("        share_teaching_class_subject  ");
        sql.append("    WHERE");
        sql.append("        gid ");
        sql.append("        ");
        sql.append("    IN(");
        sql.append("        SELECT");
        sql.append("            a.gid  FROM");
        sql.append("                share_teaching_class_subject a  ");
        sql.append("                ");
        sql.append("            LEFT JOIN");
        sql.append("                share_teaching_class b ");
        sql.append("                    ON a.teachingClassCode = b.teachingClassCode  ");
        sql.append("            WHERE 1=1 ");
        if (StringUtils.isNotEmpty(adminClassCode)) {
            sql.append("                AND b.adminClassCode =:adminClassCode  ");
            sqlParam.put("adminClassCode", adminClassCode);
        }
        if (StringUtils.isNotEmpty(teacherCode)) {
            sql.append("                AND a.teacherCode =:teacherCode  ");
            sqlParam.put("teacherCode", teacherCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("                and a.subjectCode =:subjectCode  ");
            sqlParam.put("subjectCode", subjectCode);
        }
        sql.append("            GROUP BY");
        sql.append("                a.gid  ");
        sql.append("        )");

        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 查询教学班级学科信息 ()<br>
     * 
     * @param teachingClassCode
     * @return
     */
    public long getClassSubjectCount(String teachingClassCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        COUNT (1) ");
        sql.append("    FROM");
        sql.append("        share_teaching_class_subject ");
        sql.append("    WHERE");
        sql.append("        teachingClassCode =:teachingClassCode");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("teachingClassCode", teachingClassCode);
        return super.queryCount(sql.toString(), param);

    }
}
