package com.baizhitong.resource.model.company;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 机构年级学科
 * 
 * @author creator gaow 2016年1月21日 下午2:13:07
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_org_grade_subject")
public class ShareOrgGradeSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 系统ID **/
    private String            gid;
    /** 机构编码 **/
    private String            orgCode;
    /** 年级编码 **/
    private String            gradeCode;
    /** 学科编码 **/
    private String            subjectCode;
    /** 修改程序 **/
    private String            modifyPgm;
    /** 修改时间 **/
    private Timestamp         modifyTime;
    /** 修改者IP **/
    private String            modifyIP;
    /** 系统版本号 **/
    private Integer           sysVer;
    /** 排序 **/
    private Integer           dispOrder;

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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
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
