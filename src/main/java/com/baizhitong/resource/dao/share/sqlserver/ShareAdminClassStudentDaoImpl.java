package com.baizhitong.resource.dao.share.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.alibaba.druid.support.logging.Log;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareAdminClassStudentDao;
import com.baizhitong.resource.model.basic.ShareAdminClass;
import com.baizhitong.resource.model.share.ShareAdminClassStudent;
import com.baizhitong.utils.StringUtils;

@Service
public class ShareAdminClassStudentDaoImpl extends BaseSqlServerDao<ShareAdminClassStudent>
                implements ShareAdminClassStudentDao {
    /**
     * 保存行政班级学生 ()<br>
     * 
     * @param adminClassStudent
     * @return
     */
    public boolean saveOne(ShareAdminClassStudent adminClassStudent) {
        try {
            return super.saveOne(adminClassStudent);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量保存 ()<br>
     * 
     * @param adminClassStudent
     * @return
     */
    public int saveList(List<ShareAdminClassStudent> adminClassStudent) {
        try {
            return super.saveAll(adminClassStudent);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 删除行政班级学生 ()<br>
     * 
     * @param adminClassCode
     * @return
     */
    public int deleteAdminClassStudent(String adminClassCode) {
        String sql = "delete from share_admin_class_student where adminClassCode=?";
        return super.update(sql, adminClassCode);
    }

    /**
     * 
     * ()<br>
     * 
     * @param userCode
     * @return
     */
    public int deleteShareAdminClassStudent(String userCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("DELETE   ");
        sql.append("    FROM");
        sql.append("        share_admin_class_student   ");
        sql.append("    WHERE");
        sql.append("       1=1   ");
        sql.append("        AND studentCode =:studentCode   ");

        param.put("studentCode", userCode);
        return super.update(sql.toString(), param);
    }

    /**
     * 行政班级学生 ()<br>
     * 
     * @param adminClass
     * @param userCode
     * @return
     */
    public int addAdminClassStudent(ShareAdminClass adminClass, String userCode, String userName) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_admin_class_student");
        sql.append("        (    gid,    orgCode,    adminClassCode,    adminGroupCode,    studentCode,    studentName,    roleInGroup,    dispOrder,    memo,    modifyPgm,    modifyTime,    modifyIP,    sysVer,    cd_guid,    gradeCode   )   ");
        sql.append("    VALUES");
        sql.append("        (    NEWID(),    :orgCode,    :adminClassCode,    '',    :studentCode,    :studentName,    '',    1,    '',    :modifyPgm,    :modifyTime,    :modifyIP,    0,    '',    :gradeCode    );");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgCode", adminClass.getOrgCode());
        param.put("adminClassCode", adminClass.getClassCode());
        param.put("studentCode", userCode);
        param.put("studentName", userName);
        param.put("modifyPgm", "shareAdminClassStudentService");
        param.put("modifyTime", new Timestamp(new Date().getTime()));
        param.put("modifyIP", "127.0.0.1");
        param.put("gradeCode", adminClass.getGradeCode());
        return super.update(sql.toString(), param);

    }

    @Override
    public int updateGradeInfo(String gradeCode, String classCode) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        share_admin_class_student ");
        sql.append("    SET");
        sql.append("        gradeCode = :gradeCode");
        sql.append("    WHERE");
        sql.append("        adminClassCode = :adminClassCode");
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("adminClassCode", classCode);

        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 根据学生guid更新行政班级表 ()<br>
     * 
     * @param guid
     * @param gradeCode
     * @param modifyPgm
     * @return
     */
    public int updateShareAdminClassStudentByStuGuid(String stu_guid, String userName, String studentCode,
                    String adminClassCode, String gradeCode, String modifyPgm) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        share_admin_class_student  ");
        sql.append("    SET");
        sql.append("        gradeCode =:gradeCode");
        sql.append("        ,studentCode =:studentCode");
        sql.append("        ,modifyPgm=:modifyPgm");
        sql.append("        ,modifyTime=:modifyTime ");
        sql.append("        ,studentName=:userName");
        sql.append("        where cd_guid =:cd_guid ");
        sql.append(" and adminClassCode=:adminClassCode");
        sqlParam.put("userName", userName);
        sqlParam.put("studentCode", studentCode);
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("modifyPgm", modifyPgm);
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("cd_guid", stu_guid);
        sqlParam.put("adminClassCode", adminClassCode);
        return super.update(sql.toString(), sqlParam);

    }

    /**
     * 
     * 根据学生guid新增行政班级学生
     * 
     * @param shareAdminClassStudent
     */
    public void saveShareAdminClassStudent(ShareAdminClassStudent shareAdminClassStudent) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        share_admin_class_student");
        sql.append("        (     gid,     orgCode,     adminClassCode,     adminGroupCode,     studentCode,     studentName,     roleInGroup,     dispOrder,     memo,     modifyPgm,     modifyTime,     modifyIP,     sysVer,     cd_guid,     gradeCode    )    values( ");
        sql.append("            :gid");
        sql.append("            ,:orgCode");
        sql.append("            ,:adminClassCode");
        sql.append("            ,:adminGroupCode");
        sql.append("            ,:studentCode");
        sql.append("            ,:userName");
        sql.append("            ,:roleInGroup");
        sql.append("            ,:dispOrder");
        sql.append("            ,:memo");
        sql.append("            ,:modifyPgm");
        sql.append("            ,:modifyTime");
        sql.append("            ,:modifyIP");
        sql.append("            ,:sysVer");
        sql.append("            ,:cd_guid");
        sql.append("            ,:gradeCode     ");
        sql.append("        )");
        sqlParam.put("gid", UUID.randomUUID().toString());
        sqlParam.put("orgCode", shareAdminClassStudent.getOrgCode());
        sqlParam.put("adminClassCode", shareAdminClassStudent.getAdminClassCode());
        sqlParam.put("adminGroupCode", "");
        sqlParam.put("roleInGroup", 0);
        sqlParam.put("memo", "");
        sqlParam.put("dispOrder", 0);
        sqlParam.put("modifyPgm", shareAdminClassStudent.getModifyPgm());
        sqlParam.put("modifyTime", shareAdminClassStudent.getModifyTime());
        sqlParam.put("modifyIP", shareAdminClassStudent.getModifyIP());
        sqlParam.put("sysVer", 1);
        sqlParam.put("cd_guid", shareAdminClassStudent.getCd_guid());
        sqlParam.put("userName", shareAdminClassStudent.getStudentName());
        sqlParam.put("studentCode", shareAdminClassStudent.getStudentCode());
        sqlParam.put("gradeCode", shareAdminClassStudent.getGradeCode());
        super.update(sql.toString(), sqlParam);

    }

    /**
     * 查询行政班级学生 ()<br>
     * 
     * @param classCode
     * @return
     */
    public List<Map<String, Object>> getAdminClassStudent(String adminClassCode) {
        String sql = "select sacs.studentCode from share_admin_class_student sacs where sacs.adminClassCode=:adminClassCode";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("adminClassCode", adminClassCode);
        return super.findBySql(sql, sqlParam);
    }
}
