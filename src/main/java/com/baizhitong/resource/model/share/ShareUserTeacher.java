package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 教师信息实体类
 * 
 * @author creator Cuidc 2015/12/11
 * @author updater
 */
@Entity
@Table(name = "share_user_teacher")
public class ShareUserTeacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    /** 系统ID */
    private String            gid;
    /** 机构编码 */
    private String            orgCode;
    /** 教师代码 */
    private String            teacherCode;
    /** 用户姓名 */
    private String            userName;
    /** 职称编码 */
    private String            jobTitleCode;
    /** 职位编码 */
    private String            positionCode;
    /** 状态 */
    private Integer           status;
    /** 性别 */
    private Integer           gender;
    /** 默认学段编码 */
    private String            sectionCode;
    /** 默认学科编码 */
    private String            subjectCode;
    /** 默认年级编码 */
    private String            gradeCode;
    /** 默认学期编码 */
    private String            termCode;
    /** 默认教材版本编码 */
    private String            tbvCode;
    /** 默认地区编码 */
    private String            districtAreaCode;
    /** 工号 */
    private String            workNo;
    /** 最高学历 */
    private String            educationCode;
    /** 参加工作年月 */
    private Timestamp         workFirstDay;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 唯一号 */
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

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobTitleCode() {
        return jobTitleCode;
    }

    public void setJobTitleCode(String jobTitleCode) {
        this.jobTitleCode = jobTitleCode;
    }

    public String getCd_guid() {
        return cd_guid;
    }

    public void setCd_guid(String cd_guid) {
        this.cd_guid = cd_guid;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
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

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
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

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getTbvCode() {
        return tbvCode;
    }

    public void setTbvCode(String tbvCode) {
        this.tbvCode = tbvCode;
    }

    public String getDistrictAreaCode() {
        return districtAreaCode;
    }

    public void setDistrictAreaCode(String districtAreaCode) {
        this.districtAreaCode = districtAreaCode;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getEducationCode() {
        return educationCode;
    }

    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode;
    }

    public Timestamp getWorkFirstDay() {
        return workFirstDay;
    }

    public void setWorkFirstDay(Timestamp workFirstDay) {
        this.workFirstDay = workFirstDay;
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
