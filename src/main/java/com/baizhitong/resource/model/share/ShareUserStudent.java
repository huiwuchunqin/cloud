package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学生信息实体类
 * 
 * @author creator gaow 2015/12/11
 * @author updater
 */
@Entity
@Table(name = "share_user_student")
public class ShareUserStudent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    /** 系统ID */
    private String            gid;
    /** 机构编码 */
    private String            orgCode;
    /** 年级编码 */
    private String            gradeCode;
    /** 学期编码 */
    private String            termCode;
    /** 行政班级编号 */
    private String            adminClassCode;
    /** 学生代码 */
    private String            studentCode;
    /** 学号 */
    private String            studentNumber;
    /** 用户姓名 */
    private String            userName;
    /** 状态 */
    private Integer           status;
    /** 性别 */
    private Integer           gender;
    /** 用户头像 */
    private String            avatarsImg;
    /** 入学年月 */
    private Timestamp         enterSchoolDate;
    /** 学生类别码 */
    private String            studentTypeCode;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 学生唯一码 */
    private String            cd_guid;
    /** 学生硬件编号 */
    private String            studentHardNo;

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

    public String getStudentHardNo() {
        return studentHardNo;
    }

    public void setStudentHardNo(String studentHardNo) {
        this.studentHardNo = studentHardNo;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getAdminClassCode() {
        return adminClassCode;
    }

    public void setAdminClassCode(String adminClassCode) {
        this.adminClassCode = adminClassCode;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatarsImg() {
        return avatarsImg;
    }

    public void setAvatarsImg(String avatarsImg) {
        this.avatarsImg = avatarsImg;
    }

    public Timestamp getEnterSchoolDate() {
        return enterSchoolDate;
    }

    public void setEnterSchoolDate(Timestamp enterSchoolDate) {
        this.enterSchoolDate = enterSchoolDate;
    }

    public String getStudentTypeCode() {
        return studentTypeCode;
    }

    public void setStudentTypeCode(String studentTypeCode) {
        this.studentTypeCode = studentTypeCode;
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

    public String getCd_guid() {
        return cd_guid;
    }

    public void setCd_guid(String cd_guid) {
        this.cd_guid = cd_guid;
    }

}