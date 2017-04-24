package com.baizhitong.resource.dao.share.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareUserTeacherDao;
import com.baizhitong.resource.model.share.ShareUserTeacher;

@Component
public class ShareUserTeacherSqlServerDaoImpl extends BaseSqlServerDao<ShareUserTeacher>
                implements ShareUserTeacherDao {

    /**
     * 根据教师代码获取教师基本信息
     */
    @Override
    public Map<String, Object> getTeacherInfoByTeaCode(String teacherCode) throws Exception {
        // sql语句
        StringBuilder sql = new StringBuilder();
        sql.append(" select sut.userName,sut.gender,sut.workNo,sut.workFirstDay, ");
        sql.append(" sut.sectionCode,sut.subjectCode,sut.gradeCode, ");
        sql.append(" scs.name as sectionName, ");
        sql.append(" scs2.name as subjectName, ");
        sql.append(" scg.name as gradeName ");
        sql.append(" from share_user_teacher sut LEFT JOIN share_code_section scs ");
        sql.append(" on sut.sectionCode=scs.code ");
        sql.append(" LEFT JOIN share_code_subject scs2 on sut.subjectCode=scs2.code ");
        sql.append(" LEFT JOIN share_code_grade scg on sut.gradeCode=scg.code ");
        sql.append(" where 1=1 ");
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(teacherCode)) {
            sql.append(" and sut.teacherCode=:teacherCode ");
            sqlParam.put("teacherCode", teacherCode);
        }
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询教师信息 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param orgName 机构名称
     * @param userName 老师名称
     * @param loginAccount 登录账户
     * @param userRole 用户角色
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page getTeacherInfo(String sectionCode, String subjectCode, String orgName, String orgCode, String userName,
                    String loginAccount, Integer userRole, Integer pageSize, Integer pageNo, Integer type) {
        StringBuffer sql = new StringBuffer();
        // 查询参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        sut.userName");
        sql.append("        ,sut.sectionCode");
        sql.append("        ,sut.subjectCode");
        sql.append("        ,sut.gradeCode");
        sql.append("        ,sut.teacherCode");
        sql.append("        ,sut.workNo");
        sql.append("        ,sut.workFirstDay");
        sql.append("        ,sut.gid");
        sql.append("        ,sut.orgCode");
        sql.append("        ,sut.gender");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,subject.name AS subjectName");
        sql.append("        ,org.orgName");
        sql.append("        ,login.userRole");
        sql.append("        ,login.mail");
        sql.append("        ,login.mobilePhone");
        sql.append("        ,login.loginAccount ");
        sql.append("        ,login.loginPwd ");
        sql.append("    FROM");
        sql.append("        share_user_teacher AS sut ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade AS scg ");
        sql.append("            ON sut.gradeCode = scg.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject AS subject ");
        sql.append("            ON sut.subjectCode = subject.code ");
        sql.append("            AND subject.flagDelete = 0 ");
        sql.append("    INNER JOIN");
        sql.append("        share_org AS org ");
        sql.append("            ON sut.orgCode = org.orgCode ");
        /*
         * sql.append("    INNER JOIN"); sql.append( "        share_rel_section_subject srss ");
         * sql.append( "            ON srss.subjectCode = subject.code "); sql.append(
         * "            ");
         */
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section AS scs ");
        sql.append("            ON sut.sectionCode = scs.code ");
        sql.append("            AND scs.flagDelete = 0 ");
        sql.append("    INNER JOIN");
        sql.append("        share_user_login AS login ");
        sql.append("            ON sut.teacherCode = login.userCode ");
        if (type != null) {
            sql.append(" LEFT JOIN auth_user_role_ref aurr  ON aurr.userCode = sut.teacherCode ");
        }
        sql.append("    WHERE");
        sql.append("        sut.status = 1");
        if (StringUtils.isNotEmpty(userName)) {
            sql.append("  AND sut.userName like  :userName ");
            sqlParam.put("userName", "%" + userName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(loginAccount)) {
            sql.append("  AND login.loginAccount like :loginAccount ");
            sqlParam.put("loginAccount", "%" + loginAccount.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("  AND org.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName.trim() + "%");
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("  AND sut.orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("  AND sut.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("  AND sut.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (userRole != null) {
            sql.append("  AND login.userRole =  :userRole ");
            sqlParam.put("userRole", userRole);
        }
        if (type != null) {
            sql.append("  and aurr.userCode is null ");
        }
        sql.append("  order by sut.userName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);
    }

    /**
     * 查询老师列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserTeacher> getTeacherList(String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(orgCode)) {
            qr.andEqual("orgCode", orgCode);
        }
        qr.andEqual("status", 1);
        qr.addAscOrder("userName");
        return super.find(qr);
    }

    /**
     * 得到老师信息 ()<br>
     * 
     * @param userCode
     * @return
     */
    public ShareUserTeacher getTeacher(String userCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("teacherCode", userCode);
        qr.andEqual("status", CoreConstants.LOGIN_EFFECTIVE);
        return super.findUnique(qr);
    }

    /**
     * 保存老师 ()<br>
     * 
     * @param teacher
     * @return
     */
    public boolean saveOrgUpdate(ShareUserTeacher teacher) {
        try {
            return super.saveOne(teacher);
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
     * 删除老师 ()<br>
     * 
     * @param userCode
     * @return
     */
    public int deleteTeacher(String userCode) {
        String sql = "update share_user_teacher set status=0 where teacherCode=?";
        return super.update(sql, userCode);
    }

    /**
     * 通过guid查询老师 ()<br>
     * 
     * @param guid
     * @return
     */
    public ShareUserTeacher getTeacherByGuid(String guid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("cd_guid", guid);
        qr.andEqual("status", 1);
        return super.findUnique(qr);
    }

    /**
     * 新增或更新 ()<br>
     * 
     * @param teacherList
     * @return
     */
    public int saveTeacherList(List<ShareUserTeacher> teacherList) {
        try {
            return super.saveAll(teacherList);
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
     * 通过guid更新老师信息 ()<br>
     * 
     * @param teacher
     * @return
     */
    public int updateTeacherByGuid(ShareUserTeacher teacher) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_user_teacher  ");
        sql.append("    SET");
        sql.append("        orgCode =:orgCode");
        sql.append("        ,userName =:userName");
        sql.append("        ,teacherCode =:teacherCode");
        sql.append("        ,jobTitleCode =:jobTitleCode");
        sql.append("        ,positionCode =:positionCode");
        sql.append("        ,status =:status");
        sql.append("        ,gender =:gender");
        sql.append("        ,sectionCode =:sectionCode");
        sql.append("        ,subjectCode =:subjectCode");
        sql.append("        ,termCode =:termCode");
        sql.append("        ,workNo =:workNo");
        sql.append("        ,modifyPgm =:modifyPgm");
        sql.append("        ,modifyTime =:modifyTime");
        sql.append("        ,modifyIP =:modifyIP");
        sql.append("        ");
        sql.append("    WHERE");
        sql.append("        cd_guid =:cd_guid");
        sqlParam.put("gid", UUID.randomUUID().toString());
        sqlParam.put("orgCode", teacher.getOrgCode());
        sqlParam.put("teacherCode", teacher.getTeacherCode());
        sqlParam.put("userName", teacher.getUserName());
        sqlParam.put("jobTitleCode", teacher.getJobTitleCode());
        sqlParam.put("positionCode", teacher.getPositionCode());
        sqlParam.put("status", 1);
        sqlParam.put("gender", teacher.getGender());
        sqlParam.put("sectionCode", teacher.getSectionCode());
        sqlParam.put("subjectCode", teacher.getSubjectCode());
        sqlParam.put("gradeCode", teacher.getGradeCode());
        sqlParam.put("termCode", teacher.getTermCode());
        sqlParam.put("workNo", teacher.getWorkNo());
        sqlParam.put("modifyPgm", teacher.getModifyPgm());
        sqlParam.put("modifyTime", teacher.getModifyTime());
        sqlParam.put("modifyIP", teacher.getModifyIP());
        sqlParam.put("cd_guid", teacher.getCd_guid());
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 获取简单的老师列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getSimpleTeacherList(String orgCode) {
        String sql = "select cd_guid,teacherCode,userName from share_user_teacher";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(orgCode)) {
            sql = sql + "	where orgCode=:orgCode";
            sqlParam.put("orgCode", orgCode);
        }
        return super.findBySql(sql, sqlParam);
    }

    /**
     * 更新所有老师的年级学科 ()<br>
     * 
     * @param orgCode
     */
    public void updateSubjectGrade(String orgCode, String studyYearCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        share_user_teacher   ");
        sql.append("    SET");
        sql.append("        gradeCode = (    SELECT");
        sql.append("            TOP 1.gradeCode    ");
        sql.append("        FROM");
        sql.append("            share_teaching_class_subject    ");
        sql.append("        WHERE");
        sql.append("            studyYearCode =:studyYearCode    ");
        sql.append("            and teacherCode = share_user_teacher.teacherCode   )   ");
        sql.append("        ,subjectCode = (    SELECT");
        sql.append("            TOP 1.subjectCode    ");
        sql.append("        FROM");
        sql.append("            share_teaching_class_subject    ");
        sql.append("        WHERE");
        sql.append("            studyYearCode =:studyYearCode   ");
        sql.append("            and teacherCode = share_user_teacher.teacherCode)");
        sql.append("        where orgCode=:orgCode  ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("studyYearCode", studyYearCode);
        sqlParam.put("orgCode", orgCode);
        super.update(sql.toString(), sqlParam);

    }
}
