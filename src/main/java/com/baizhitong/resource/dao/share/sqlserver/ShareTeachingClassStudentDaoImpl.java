package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareTeachingClassStudentDao;
import com.baizhitong.resource.model.share.ShareTeachingClass;
import com.baizhitong.resource.model.share.ShareTeachingClassStudent;
import com.baizhitong.utils.StringUtils;

@Service
public class ShareTeachingClassStudentDaoImpl extends BaseSqlServerDao<ShareTeachingClassStudent>
                implements ShareTeachingClassStudentDao {

    /**
     * 新增行政班级学生 ()<br>
     * 
     * @param teachingClass
     * @return
     */
    @Override
    public int addShareTeachingClassStudent(ShareTeachingClass teachingClass) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_teaching_class_student");
        sql.append("        (gid,orgCode,teachingClassCode,teachingGroupCode,studentCode,studentName,roleInGroup,dispOrder,memo,modifyPgm,modifyTime,modifyIP,sysVer,gradeCode,subjectCode,studyYearCode,studentHardNo) select");
        sql.append("            newid()");
        sql.append("            ,:orgCode");
        sql.append("            ,:teachingClassCode");
        sql.append("            ,''");
        sql.append("            ,sus.studentCode");
        sql.append("            ,sus.userName");
        sql.append("            ,''");
        sql.append("            ,1");
        sql.append("            ,''");
        sql.append("            ,:modifyPgm");
        sql.append("            ,:modifyTime");
        sql.append("            ,:modifyIP");
        sql.append("            ,1");
        sql.append("            ,:gradeCode");
        sql.append("            ,:subjectCode");
        sql.append("            ,:studyYearCode");
        sql.append("            ,''   ");
        sql.append("        FROM");
        sql.append("            share_user_student sus ");
        sql.append("        WHERE");
        sql.append("            sus.adminClassCode = :adminClassCode ");// 没有查询到的学生不新增
        sql.append("        AND sus.status = :status ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", teachingClass.getOrgCode());
        sqlParam.put("teachingClassCode", teachingClass.getTeachingClassCode());
        sqlParam.put("modifyPgm", teachingClass.getModifyPgm());
        sqlParam.put("modifyTime", teachingClass.getModifyTime());
        sqlParam.put("modifyIP", teachingClass.getModifyIP());
        sqlParam.put("gradeCode", teachingClass.getGradeCode());
        sqlParam.put("subjectCode", teachingClass.getSubjectCode());
        sqlParam.put("studyYearCode", teachingClass.getStudyYearCode());
        sqlParam.put("adminClassCode", teachingClass.getAdminClassCode());
        sqlParam.put("status", CoreConstants.USER_STATUS_EFFECTIVE);// 状态：有效
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 跟新程序新增课程班级学生 ()<br>
     * 
     * @param teachingClass
     * @return
     */
    public int addShareTeachingClassStudentDataSynchronize(ShareTeachingClass teachingClass) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_teaching_class_student");
        sql.append("        (    gid,    orgCode,    teachingClassCode,    teachingGroupCode,    studentCode,    studentName,    roleInGroup,    dispOrder,    memo,    modifyPgm,    modifyTime,    modifyIP,    sysVer,    gradeCode,    subjectCode,    studyYearCode,    studentHardNo   )   select");
        sql.append("            newid()");
        sql.append("            ,:orgCode");
        sql.append("            ,:teachingClassCode");
        sql.append("            ,''");
        sql.append("            ,sus.studentCode");
        sql.append("            ,sus.userName");
        sql.append("            ,''");
        sql.append("            ,1");
        sql.append("            ,''");
        sql.append("            ,:modifyPgm");
        sql.append("            ,:modifyTime");
        sql.append("            ,:modifyIP");
        sql.append("            ,1");
        sql.append("            ,:gradeCode");
        sql.append("            ,:subjectCode");
        sql.append("            ,:studyYearCode");
        sql.append("            ,''   ");
        sql.append("        FROM");
        sql.append("            share_user_student sus ");
        sql.append("        WHERE");
        sql.append("            sus.adminClassCode=:adminClassCode and sus.modifyPgm !='Inited'");// 没有更新到的学生不新增
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", teachingClass.getOrgCode());
        sqlParam.put("teachingClassCode", teachingClass.getTeachingClassCode());
        sqlParam.put("modifyPgm", teachingClass.getModifyPgm());
        sqlParam.put("modifyTime", teachingClass.getModifyTime());
        sqlParam.put("modifyIP", teachingClass.getModifyIP());
        sqlParam.put("gradeCode", teachingClass.getGradeCode());
        sqlParam.put("subjectCode", teachingClass.getSubjectCode());
        sqlParam.put("studyYearCode", teachingClass.getStudyYearCode());
        sqlParam.put("adminClassCode", teachingClass.getAdminClassCode());
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 保存班级学生 ()<br>
     * 
     * @param teachingClass
     * @param userCode
     * @param userName
     * @return
     */
    public int addTeachingClassStudent(ShareTeachingClass teachingClass, String userCode, String userName) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_teaching_class_student");
        sql.append("        (    gid,    orgCode,    teachingClassCode,    teachingGroupCode,    studentCode,    studentName,    roleInGroup,    dispOrder,    memo,    modifyPgm,    modifyTime,    modifyIP,    sysVer,    gradeCode,    subjectCode,    studyYearCode,    studentHardNo   )   values");
        sql.append("    (        newid()");
        sql.append("            ,:orgCode");
        sql.append("            ,:teachingClassCode");
        sql.append("            ,''");
        sql.append("            ,:userCode ");
        sql.append("            ,:userName ");
        sql.append("            ,''");
        sql.append("            ,1");
        sql.append("            ,''");
        sql.append("            ,:modifyPgm");
        sql.append("            ,:modifyTime");
        sql.append("            ,:modifyIP");
        sql.append("            ,1");
        sql.append("            ,:gradeCode");
        sql.append("            ,:subjectCode");
        sql.append("            ,:studyYearCode");
        sql.append("            ,''   ");
        sql.append("        )");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", teachingClass.getOrgCode());
        sqlParam.put("teachingClassCode", teachingClass.getTeachingClassCode());
        sqlParam.put("modifyPgm", teachingClass.getModifyPgm());
        sqlParam.put("modifyTime", teachingClass.getModifyTime());
        sqlParam.put("modifyIP", teachingClass.getModifyIP());
        sqlParam.put("gradeCode", teachingClass.getGradeCode());
        sqlParam.put("subjectCode", teachingClass.getSubjectCode());
        sqlParam.put("studyYearCode", teachingClass.getStudyYearCode());
        sqlParam.put("adminClassCode", teachingClass.getAdminClassCode());
        sqlParam.put("userCode", userCode);
        sqlParam.put("userName", userName);
        return super.update(sql.toString(), sqlParam);

    }

    /**
     * 查询课程班级学生 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getTeachingClassStudent(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        String teachingClassCode = MapUtils.getString(param, "teachingClassCode");
        String orgCode = MapUtils.getString(param, "orgCode");
        String studentName = MapUtils.getString(param, "studentName");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        stu.teachingClassCode");
        sql.append("        ,stu.teachingGroupCode");
        sql.append("        ,stu.studentCode");
        sql.append("        ,stu.studentName");
        sql.append("        ,cls.teachingClassName");
        sql.append("        ,cls.endTime");
        sql.append("        ,cls.startTime");
        sql.append("        ,scg.name as gradeName");
        sql.append("        ,scs.name as subjectName ");
        sql.append("    FROM");
        sql.append("        dbo.share_teaching_class_student AS stu ");
        sql.append("        ");
        sql.append("    INNER JOIN");
        sql.append("        dbo.share_teaching_class AS cls ");
        sql.append("            ON stu.teachingClassCode = cls.teachingClassCode  and cls.orgCode=stu.orgCode");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            on scg.code=stu.gradeCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            on scs.code=stu.subjectCode ");
        sql.append("        where 1=1 ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and cls.orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(teachingClassCode)) {
            sql.append(" and cls.teachingClassCode=:teachingClassCode ");
            sqlParam.put("teachingClassCode", teachingClassCode);
        }
        if (StringUtils.isNotEmpty(studentName)) {
            sql.append(" and stu.studentName like:studentName ");
            sqlParam.put("studentName", "%" + studentName + "%");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and stu.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and stu.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        sql.append("  order by cls.teachingClassName,stu.studentName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 删除学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    public int deleteStudent(String userCode, String teachingClassCode) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String sql = "delete from share_teaching_class_student where studentCode=:userCode";
        sqlParam.put("userCode", userCode);
        if (StringUtils.isNotEmpty(teachingClassCode)) {
            sql = sql + " and teachingClassCode=:teachingClassCode ";
            sqlParam.put("teachingClassCode", teachingClassCode);
        }

        return super.update(sql, sqlParam);
    }

    /**
     * 
     * 删除教学班级学生
     * 
     * @param userCode 学生编码
     */
    public void deleteShareTeachingClassStudent(String userCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("DELETE  ");
        sql.append("    FROM");
        sql.append("        share_teaching_class_student  ");
        sql.append("    WHERE");
        sql.append("        studentCode =:studentCode");
        sqlParam.put("studentCode", userCode);
        super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * 把行政班级学生插入教学班级学生表
     * 
     * @param userCode 学生编码
     * @param userName 学生名称
     * @param adminClassCode 行政班级编码
     */
    public void insertTeachingClassStudent(String userCode, String userName, String adminClassCode, String ip) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_teaching_class_student");
        sql.append("        (    gid,    orgCode,    teachingClassCode,    teachingGroupCode,    studentCode,    studentName,    roleInGroup,    dispOrder,    memo,    modifyPgm,    modifyTime,    modifyIP,    sysVer,    gradeCode,    subjectCode,    studyYearCode,    studentHardNo,    groupDispOrder   ) SELECT");
        sql.append("            NEWID()");
        sql.append("            ,a.orgCode");
        sql.append("            ,a.teachingClassCode");
        sql.append("            ,''");
        sql.append("            ,:studentCode");
        sql.append("            ,:studentName");
        sql.append("            ,'0'");
        sql.append("            ,'1'");
        sql.append("            ,''");
        sql.append("            ,'teachingClassService'");
        sql.append("            ,GETDATE()");
        sql.append("            ,:ip");
        sql.append("            ,'1'");
        sql.append("            ,a.gradeCode");
        sql.append("            ,a.subjectCode");
        sql.append("            ,a.studyYearCode");
        sql.append("            ,'0'");
        sql.append("            ,'0'   ");
        sql.append("        FROM");
        sql.append("            (     SELECT");
        sql.append("                a.orgCode");
        sql.append("                ,a.teachingClassCode");
        sql.append("                ,a.gradeCode");
        sql.append("                ,a.subjectCode");
        sql.append("                ,a.studyYearCode");
        sql.append("            FROM");
        sql.append("                share_teaching_class_subject a");
        sql.append("                ,share_teaching_class b");
        sql.append("                ,share_admin_class_subject c");
        sql.append("            WHERE");
        sql.append("                a.teachingClassCode = b.teachingClassCode     ");
        sql.append("                AND b.adminClassCode = c.adminClassCode     ");
        sql.append("                AND c.adminClassCode = :adminClassCode     ");
        sql.append("                AND a.studyYearCode = :currentStudYearCode     ");
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                a.orgCode");
        sql.append("                ,a.teachingClassCode");
        sql.append("                ,a.gradeCode");
        sql.append("                ,a.subjectCode");
        sql.append("                ,a.studyYearCode    ) a");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("studentCode", userCode);
        sqlParam.put("studentName", userName);
        sqlParam.put("adminClassCode", adminClassCode);
        sqlParam.put("ip", ip);
        sqlParam.put("currentStudYearCode", BeanHelper.getStudyYearCode());
        super.update(sql.toString(), sqlParam);

    }

    /**
     * 删除所有学生 ()<br>
     * 
     * @param teachingClassCode
     * @return
     */
    public int deleteAllStudent(String teachingClassCode) {
        String sql = "delete from  share_teaching_class_student where teachingClassCode=?";
        return super.update(sql, teachingClassCode);
    }
    /**
     * 删除教学班级学生
     * ()<br>
     * @param adminClassCode
     */
    public void deleteByAdminClass(String adminClassCode){
        StringBuffer sql=new StringBuffer();
        sql.append("DELETE ");
        sql.append("    FROM");
        sql.append("        share_teaching_class_student ");
        sql.append("    WHERE");
        sql.append("        teachingClassCode ");
        sql.append("        ");
        sql.append("    IN (");
        sql.append("        SELECT");
        sql.append("            teachingClassCode FROM");
        sql.append("                share_teaching_class ");
        sql.append("            WHERE");
        sql.append("                adminClassCode =?)");
         this.update(sql.toString(),adminClassCode);
    }
}
