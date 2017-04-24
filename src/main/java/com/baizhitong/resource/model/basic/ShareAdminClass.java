package com.baizhitong.resource.model.basic;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 行政班级
 * 
 * @author creator gaowei 2016年1月11日 下午5:47:29
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_admin_class")
public class ShareAdminClass implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 系统id */
    @Id
    private String            gid;
    /** 机构编码 */
    private String            orgCode;
    /** 当前年级编码 */
    private String            gradeCode;
    /** 班级编码 */
    private String            classCode;
    /** 班级名称 */
    private String            className;
    /** 入学年份 */
    private Integer           entryYear;
    /** 班主任 */
    private String            headTeacherCode;
    /** 班级硬件号 */
    private String            classHardId;
    /** 班级状态 */
    private Integer           classStatus;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 行政班级唯一码 */
    private String            cd_guid;

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

    public String getCd_guid() {
        return cd_guid;
    }

    public void setCd_guid(String cd_guid) {
        this.cd_guid = cd_guid;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
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

    public String getClassHardId() {
        return classHardId;
    }

    public void setClassHardId(String classHardId) {
        this.classHardId = classHardId;
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
