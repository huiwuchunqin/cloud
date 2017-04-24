package com.baizhitong.resource.model.vo.basic;

import java.sql.Timestamp;

/**
 * 
 * 行政班级vo
 * 
 * @author creator gaow 2016年1月11日 下午6:48:09
 * @author updater
 *
 * @version 1.0.0
 */
public class AdminClassVo {
    private String    gid;
    /** 机构编码 */
    private String    orgCode;
    /** 当前年级编码 */
    private String    gradeCode;
    /** 班级编码 */
    private String    classCode;
    /** 班级名称 */
    private String    className;
    /** 入学年份 */
    private Integer   entryYear;
    /** 班级状态 */
    private Integer   classStatus;
    /** 修改程序 */
    private String    modifyPgm;
    /** 修改时间 */
    private Timestamp modifyTime;
    /** 修改者IP */
    private String    modifyIP;
    /** 系统版本号 */
    private Integer   sysVer;
    /** 年级名称 */
    private String    gradeName;
    /**需要升级到的年级*/
    private String gradeToUp;

    public String getGradeToUp() {
        return gradeToUp;
    }

    public void setGradeToUp(String gradeToUp) {
        this.gradeToUp = gradeToUp;
    }

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

    public Integer getClassStatus() {
        return classStatus;
    }

    public void setClassStatus(Integer classStatus) {
        this.classStatus = classStatus;
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

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }
    
}
