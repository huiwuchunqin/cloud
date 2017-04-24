package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学年 ShareCodeStudyYear TODO
 * 
 * @author creator BZT 2016年4月28日 下午4:35:47
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "share_code_study_year")
@Entity
public class ShareCodeStudyYear implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    /** 系统ID */
    private String            gid;
    /** 学年代码 */
    private String            studyYearCode;
    /** 学年名称 */
    private String            studyYearName;
    /** 学年（始） */
    private Integer           yearBegin;
    /** 学年（终） */
    private Integer           yearEnd;
    /** 描述 */
    private String            description;
    /** 显示顺序 */
    private Integer           dispOrder;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getStudyYearName() {
        return studyYearName;
    }

    public void setStudyYearName(String studyYearName) {
        this.studyYearName = studyYearName;
    }

    public Integer getYearBegin() {
        return yearBegin;
    }

    public void setYearBegin(Integer yearBegin) {
        this.yearBegin = yearBegin;
    }

    public Integer getYearEnd() {
        return yearEnd;
    }

    public void setYearEnd(Integer yearEnd) {
        this.yearEnd = yearEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
