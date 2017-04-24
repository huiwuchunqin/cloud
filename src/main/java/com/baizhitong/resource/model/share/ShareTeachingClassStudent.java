package com.baizhitong.resource.model.share;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政班级学生实体 ShareTeachingClassStudent TODO
 * 
 * @author creator BZT 2016年6月7日 上午10:11:22
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_teaching_class_student")
public class ShareTeachingClassStudent {
    @Id
    /** 系统ID */
    private String    gid;
    /** 机构编码 */
    private String    orgCode;
    /** 学科编码 */
    private String    subjectCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 学年编码 */
    private String    studyYearCode;
    /** 教学班级代码 */
    private String    teachingClassCode;
    /** 教学小组代码 */
    private String    teachingGroupCode;
    /** 学生代码 */
    private String    studentCode;
    /** 学生姓名 */
    private String    studentName;
    /** 学生小组角色 */
    private String    roleInGroup;
    /** 学生硬件编号 */
    private String    studentHardNo;
    /** 显示顺序 */
    private Integer   dispOrder;
    /** 备注 */
    private String    memo;
    /** 修改程序 */
    private String    modifyPgm;
    /** 修改时间 */
    private Timestamp modifyTime;
    /** 修改者IP */
    private String    modifyIP;
    /** 系统版本号 */
    private Integer   sysVer;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getTeachingClassCode() {
        return teachingClassCode;
    }

    public void setTeachingClassCode(String teachingClassCode) {
        this.teachingClassCode = teachingClassCode;
    }

    public String getTeachingGroupCode() {
        return teachingGroupCode;
    }

    public void setTeachingGroupCode(String teachingGroupCode) {
        this.teachingGroupCode = teachingGroupCode;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getRoleInGroup() {
        return roleInGroup;
    }

    public void setRoleInGroup(String roleInGroup) {
        this.roleInGroup = roleInGroup;
    }

    public String getStudentHardNo() {
        return studentHardNo;
    }

    public void setStudentHardNo(String studentHardNo) {
        this.studentHardNo = studentHardNo;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

}
