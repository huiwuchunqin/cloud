package com.baizhitong.resource.dao.share.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ntp.TimeStamp;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareUserStudentDao;
import com.baizhitong.resource.model.share.ShareUserStudent;
import com.baizhitong.resource.model.vo.share.ShareUserStudentVo;
import com.baizhitong.utils.StringUtils;

/**
 * 学生信息数据接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月16日 下午5:35:23
 */
@Component
public class ShareUserStudentSqlServerDaoImpl extends BaseSqlServerDao<ShareUserStudent>
                implements ShareUserStudentDao {

    /**
     * 根据学生代码获取学生基本信息
     */
    @Override
    public Map<String, Object> getStudentByStudentCode(String studentCode) throws Exception {
        // SQL语句
        StringBuilder sql = new StringBuilder();
        sql.append(" select sus.studentNumber ,sus.adminClassCode,sus.userName, scg.name as gradeName,sus.gender, ");
        sql.append(" sus.studentCode,sus.enterSchoolDate ");
        sql.append(" from share_user_student sus ");
        sql.append(" LEFT JOIN share_code_grade scg on sus.gradeCode=scg.code ");
        sql.append(" where 1=1 ");
        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(studentCode)) {
            sql.append(" and sus.studentCode=:studentCode ");
            sqlParam.put("studentCode", studentCode);
        }
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 根据编码查学生 ()<br>
     * 
     * @param studentCode
     * @return
     */
    public ShareUserStudent getStudentByCode(String studentCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("studentCode", studentCode);
        qr.andEqual("status", 1);
        return super.findUnique(qr);
    }

    /**
     * 查询学生分页信息 ()<br>
     * 
     * @param adminClassCode 行政班级名称
     * @param sectionCode 学段编码
     * @param type 1行政班级学生 0课程班级学生
     * @param gradeCode 年级编码
     * @param adminClassCode 行政班级名称
     * @param userName 学生姓名
     * @param orgName 机构名称
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page getStudentPageInfo(boolean noAdminClass, String studentNumber, String loginAccount, Integer type,
                    Integer studentChoose, String teachingClassCode, String adminClassCode, String sectionCode,
                    String gradeCode, String userName, String orgName, String orgCode, String adminClassStudentList,
                    Integer pageSize, Integer pageNo) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        sut.*");
        sql.append("        ,grade.name as gradeName");
        sql.append("        ,scs.name as sectionName");
        sql.append("        ,org.orgName");
        sql.append("        ,login.loginAccount ");
        sql.append("        ,login.mobilePhone ");
        sql.append("        ,login.mail ");
        sql.append("        ,login.loginPwd ");
        sql.append("        ,sac.className ");
        sql.append("    FROM");
        sql.append("        share_user_student sut ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade AS grade ");
        sql.append("            ON grade.code = sut.gradeCode ");
        sql.append("            AND grade.flagDelete = 0 ");
        sql.append("    left JOIN");
        sql.append("        share_org AS org ");
        sql.append("            ON org.orgCode = sut.orgCode ");
        sql.append("    left JOIN");
        sql.append("        share_rel_section_grade srsg ");
        sql.append("            ON srsg.gradeCode = grade.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = srsg.sectionCode and scs.flagDelete=0 ");
        sql.append("    LEFT JOIN");
        sql.append("        share_user_login AS login ");
        sql.append("            ON login.userCode = sut.studentCode and  login.orgCode=sut.orgCode ");
        sql.append("    LEFT JOIN");
        sql.append("        share_admin_class AS sac ");
        sql.append("            ON sac.classCode = sut.adminClassCode and sac.orgCode=sut.orgCode ");

        sql.append("    WHERE sut.status=1 and (sac.classStatus = :classStatus or sac.classStatus is null)");
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("classStatus", CoreConstants.CLASS_STATUS__NOT_GRADUATE);
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and sut.orgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(adminClassCode)) {
            if (studentChoose == 1 && 1 == type) {// 行政班级选择学生
                if (StringUtils.isNotEmpty(adminClassStudentList)) {
                    sql.append("and sut.studentCode not in(" + adminClassStudentList + ")");
                }
            } else {
                sql.append(" and sut.adminClassCode = :adminClassCode ");
                sqlParam.put("adminClassCode", adminClassCode);
            }
        }
        if (StringUtils.isNotEmpty(loginAccount)) {
            sql.append(" and login.loginAccount like :loginAccount ");
            sqlParam.put("loginAccount", "%" + loginAccount + "%");
        }
        // 没有行政班级
        if (noAdminClass) {
            sql.append(" and (sut.adminClassCode is  null or sut.adminClassCode = '') ");
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and sut.userName like :userName ");
            sqlParam.put("userName", "%" + userName + "%");
        }

        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and org.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName + "%");
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and srsg.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and sut.gradeCode = :gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(studentNumber)) {
            sql.append(" and sut.studentNumber  like '%" + studentNumber + "%'");
            sqlParam.put("studentNumber", studentNumber);
        }
        // 课程班级学生添加
        if (StringUtils.isNotEmpty(teachingClassCode)) {
            sql.append(" and sut.studentCode not in (select clstu.studentCode from share_teaching_class_student clstu where clstu.orgCode=sut.orgCode and  clstu.teachingClassCode=:teachingClassCode)");
            sqlParam.put("teachingClassCode", teachingClassCode);
        }
        sql.append(" order by sut.userName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, pageNo, pageSize);
    }

    /**
     * 保存学生信息 ()<br>
     * 
     * @param student
     * @return
     */
    public boolean addStudent(ShareUserStudent student) {
        try {
            return super.saveOne(student);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            return false;
        }
    }

    /**
     * 更新学生 ()<br>
     * 
     * @param student
     * @return
     */
    public int update(ShareUserStudent student) {
        try {
            return super.update(student);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询学生信息 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareUserStudent getStudent(String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("gid", gid);
        return super.findUnique(qr);
    }

    /**
     * 更新学生行政班级 ()<br>
     * 
     * @param adminClassCode
     */
    public int deleteStudentAdminClass(String adminClassCode) {
        String sql = "update share_user_student set adminClassCode='' where adminClassCode=?";
        return super.update(sql, adminClassCode);
    }

    /**
     * 删除学生 ()<br>
     * 
     * @param userCode
     * @return
     */
    public int delete(String userCode) {
        String sql = "update  share_user_student set status=0 where studentCode=?";
        return super.update(sql, userCode);
    }

    /**
     * 通过guid查询学生 ()<br>
     * 
     * @param guid
     * @return
     */
    public ShareUserStudent getStudentByGuid(String guid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("cd_guid", guid);
        return super.findUnique(qr);
    }

    /**
     * 查询机构学生信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareUserStudent> orgStudentList(String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(orgCode)) {
            qr.andEqual("orgCode", orgCode);
        }
        return super.find(qr);
    }

    /**
     * 新增或更新学生信息 ()<br>
     * 
     * @param studentList
     * @return
     */
    public int addStudentList(List<ShareUserStudent> studentList) {
        try {
            return super.saveAll(studentList);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int updateGradeInfo(String gradeCode, String classCode) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        share_user_student ");
        sql.append("    SET");
        sql.append("        gradeCode = :gradeCode");
        sql.append("    WHERE");
        sql.append("        adminClassCode = :adminClassCode");
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("adminClassCode", classCode);

        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 通过guid更新学生信息 ()<br>
     * 
     * @param studentVo
     * @return
     */
    public int updateStudentInfoByGuid(ShareUserStudentVo studentVo) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // sql语句
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        share_user_student ");
        sql.append("    SET");
        sql.append("        gradeCode = :gradeCode");
        sql.append("        ,orgCode = :orgCode");
        sql.append("        ,adminClassCode = :adminClassCode");
        sql.append("        ,studentNumber = :studentNumber");
        sql.append("        ,userName = :userName");
        sql.append("        ,studentCode = :studentCode");
        sql.append("        ,gender = :gender");
        sql.append("        ,avatarsImg = :avatarsImg");
        sql.append("        ,termCode = :termCode");
        sql.append("        ,modifyPgm = :modifyPgm");
        sql.append("        ,modifyTime = :modifyTime");
        sql.append("    WHERE");
        sql.append("        cd_guid = :cd_guid");
        sqlParam.put("gradeCode", studentVo.getGradeCode());
        sqlParam.put("studentCode", studentVo.getStudentCode());
        sqlParam.put("orgCode", studentVo.getOrgCode());
        sqlParam.put("adminClassCode", studentVo.getAdminClassCode());
        sqlParam.put("studentNumber", studentVo.getStudentNumber());
        sqlParam.put("userName", studentVo.getUserName());
        sqlParam.put("gender", studentVo.getGender());
        sqlParam.put("avatarsImg", studentVo.getAvatarsImg());
        sqlParam.put("modifyPgm", studentVo.getModifyPgm());
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("termCode", studentVo.getTermCode());
        sqlParam.put("cd_guid", studentVo.getCd_guid());
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 获取简单的学生列表 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<Map<String, Object>> getSimpleStudentList(String orgCode) {
        String sql = "select cd_guid,studentCode,userName from share_user_student";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(orgCode)) {
            sql = sql + " where orgCode=:orgCode";
            sqlParam.put("orgCode", orgCode);
        }
        return super.findBySql(sql, sqlParam);
    }

    /**
     * 
     * (根据相关条件查询学生信息列表)<br>
     * 
     * @param orgCode
     * @param studentNumber
     * @param studentCode
     * @return
     */
    @Override
    public List<Map<String, Object>> selectListByStudentNumber(String orgCode, String studentNumber,
                    String studentCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        *  ");
        sql.append("    FROM");
        sql.append("        share_user_student  ");
        sql.append("    WHERE");
        sql.append("   1=1 ");
        if (StringUtils.isNotEmpty(studentNumber)) {
            sql.append("       and  studentNumber=:studentNumber");
            param.put("studentNumber", studentNumber);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("    and orgCode=:orgCode");
            param.put("orgCode", orgCode);
        }

        if (StringUtils.isNotEmpty(studentCode)) {
            sql.append("    and studentCode!=:studentCode");
            param.put("studentCode", studentCode);
        }
        sql.append("    and status=:status");
        param.put("status", CoreConstants.USER_STATUS_EFFECTIVE);
        return super.findBySql(sql.toString(), param);
    }

    /**
     * 通过学号查询学生
     */
    public Integer existStudentStudentNo(String studentNumber, String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("studentNumber", studentNumber);
        if (StringUtils.isNotEmpty(orgCode)) {
            qr.andEqual("orgCode", orgCode);
        }
        qr.andEqual("status",CoreConstants.USER_STATUS_EFFECTIVE);
        return (int) super.getCount(qr);
    }

    /**
     * 
     * 判断学生硬件号是否存在
     * 
     * @param stuHardNo 硬件号
     * @param orgCode 机构
     * @return
     */
    public Integer existStudentHardNo(String stuHardNo, String orgCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("studentHardNo", stuHardNo);
        if (StringUtils.isNotEmpty(orgCode)) {
            qr.andEqual("orgCode", orgCode);
        }
        return (int) super.getCount(qr);
    }

    /**
     * 更新学生信息 ()<br>
     * 
     * @param studentName 学生姓名
     * @param studentCode 学生编码
     * @param studentNumber 学生学号
     * @param gradeCode 年级编码
     * @param termCode 学期编码
     * @param adminClassCode 行政班级编码
     * @param enterSchoolDate 入学日期
     * @param gender 性别
     * @return 更新条数
     */
    public int updateStudent(String studentName, String studentCode, String studentNumber, String gradeCode,
                    String termCode, String adminClassCode, Timestamp enterSchoolDate, Integer gender, String modifyIp,
                    Timestamp modifyTime, String modifyPgm) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_user_student  ");
        sql.append("    SET");
        sql.append("        userName = :userName");
        sql.append("        ,studentNumber = :studentNumber");
        sql.append("        ,gradeCode = :gradeCode");
        sql.append("        ,termCode = :termCode");
        sql.append("        ,adminClassCode = :adminClassCode");
        sql.append("        ,enterSchoolDate =:enterSchoolDate");
        sql.append("        ,gender = :gender");
        sql.append("        ,modifyPgm = :modifyPgm");
        sql.append("        ,modifyTime =:modifyTime");
        sql.append("        ,modifyIP =:modifyIP");
        sql.append("    WHERE");
        sql.append("        studentCode = :studentCode");
        param.put("userName", studentName);
        param.put("studentNumber", studentNumber);
        param.put("gradeCode", gradeCode);
        param.put("termCode", termCode);
        param.put("adminClassCode", adminClassCode);
        param.put("enterSchoolDate", enterSchoolDate);
        param.put("gender", gender);
        param.put("modifyPgm", modifyPgm);
        param.put("modifyTime", modifyTime);
        param.put("modifyIP", modifyIp);
        param.put("studentCode", studentCode);
        return super.update(sql.toString(), param);

    }
}
