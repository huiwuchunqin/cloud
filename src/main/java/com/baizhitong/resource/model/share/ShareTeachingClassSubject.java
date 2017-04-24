package com.baizhitong.resource.model.share;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * ShareTeachingClassSubject 行政班级学科实体
 * 
 * @author creator BZT 2016年6月7日 上午10:10:42
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_teaching_class_subject")
public class ShareTeachingClassSubject {
    @Id
    /** 系统ID */
    private String    gid;
    /** 机构编码 */
    private String    orgCode;
    /** 学科编码 */
    private String    subjectCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 教材版本编码 */
    private String    textbookVerCode;
    /** 教学班级代码 */
    private String    teachingClassCode;
    /** 教师代码 */
    private String    teacherCode;
    /** 教师姓名 */
    private String    teacherName;
    /** 教师角色 */
    private String    teacherRole;
    /** 学年编码 */
    private String    studyYearCode;
    /** 学期编码 */
    private String    termCode;
    /** 任教开始时间 */
    private Timestamp beginTime;
    /** 任教结束时间 */
    private Timestamp endTime;
    /** 班级硬件识别号 */
    private Integer   classHardId;
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

    public String getTextbookVerCode() {
        return textbookVerCode;
    }

    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
    }

    public String getTeachingClassCode() {
        return teachingClassCode;
    }

    public void setTeachingClassCode(String teachingClassCode) {
        this.teachingClassCode = teachingClassCode;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherRole() {
        return teacherRole;
    }

    public void setTeacherRole(String teacherRole) {
        this.teacherRole = teacherRole;
    }

    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public Timestamp getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Timestamp beginTime) {
        this.beginTime = beginTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getClassHardId() {
        return classHardId;
    }

    public void setClassHardId(Integer classHardId) {
        this.classHardId = classHardId;
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
