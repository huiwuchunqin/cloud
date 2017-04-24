package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "share_admin_class_subject")
@Entity
public class ShareAdminClassSubject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String            gid;
    private String            orgCode;
    private String            subjectCode;
    private String            textbookVerCode;
    private String            gradeCode;
    private String            adminClassCode;
    private String            teacherCode;
    private String            teacherName;
    private Integer           teacherRole;
    private Timestamp         beginTime;
    private Timestamp         endTime;
    private String            memo;
    private String            modifyPgm;
    private Timestamp         modifyTime;
    private String            modifyIP;
    private Integer           sysVer;

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

    public String getTextbookVerCode() {
        return textbookVerCode;
    }

    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getAdminClassCode() {
        return adminClassCode;
    }

    public void setAdminClassCode(String adminClassCode) {
        this.adminClassCode = adminClassCode;
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

    public Integer getTeacherRole() {
        return teacherRole;
    }

    public void setTeacherRole(Integer teacherRole) {
        this.teacherRole = teacherRole;
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
