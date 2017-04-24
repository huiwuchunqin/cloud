package com.baizhitong.resource.model.share;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "share_rel_grade_subject")
public class ShareRelGradeSubject {
    private String    gid;
    private String    subjectCode;
    private String    gradeCode;
    private String    modifyPgm;  // 修改程序
    private Timestamp modifyTime; // 修改时间
    private String    modifyIP;   // 修改ip
    private Integer   sysVer;     // 版本号

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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

    public ShareRelGradeSubject(String gid,
                                String gradeCode,
                                String subjectCode,
                                String modifyPgm,
                                Timestamp modifyTime,
                                String modifyIP,
                                Integer sysVer) {
        this.gid = gid;
        this.gradeCode = gradeCode;
        this.subjectCode = subjectCode;
        this.modifyPgm = modifyPgm;
        this.modifyTime = modifyTime;
        this.modifyIP = modifyIP;
        this.sysVer = sysVer;
    }

}
