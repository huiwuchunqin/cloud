package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政班级
 * 
 * @author shancy 2015/12/02
 */
@Entity
@Table(name = "share_admin_class")
public class ShareAdminClass implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 系统ID */
    private @Id Integer       gid;
    /** 机构编码 */
    private String            orgCode;
    /** 年级编码 */
    private String            gradeCode;
    /** 班级编码 */
    private String            classCode;
    /** 班级名称 */
    private String            className;
    /** 入学年份 */
    private Integer           entryYear;
    /** 班级硬件号 */
    private String            classHardId;
    /** 入学年级ID */
    private Integer           startGradeID;
    /** 班级状态 */
    private Integer           classStatus;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 班主任 */
    private String            headTeacherCode;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getClassHardId() {
        return classHardId;
    }

    public void setClassHardId(String classHardId) {
        this.classHardId = classHardId;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getEntryYear() {
        return entryYear;
    }

    public void setEntryYear(Integer entryYear) {
        this.entryYear = entryYear;
    }

    public Integer getStartGradeID() {
        return startGradeID;
    }

    public void setStartGradeID(Integer startGradeID) {
        this.startGradeID = startGradeID;
    }

    public Integer getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(Integer classStatus) {
        this.classStatus = classStatus;
    }

    public String getHeadTeacherCode() {
        return headTeacherCode;
    }

    public void setHeadTeacherCode(String headTeacherCode) {
        this.headTeacherCode = headTeacherCode;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
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
