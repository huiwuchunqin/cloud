package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 平台学年学期实体 SharePlatformYearTerm TODO
 * 
 * @author creator BZT 2016年4月28日 下午4:35:19
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_platform_year_term")
public class SharePlatformYearTerm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    /** 系统ID */
    private String            gid;
    /** 学段编码 */
    private String            sectionCode;
    /** 学年编码 */
    private String            studyYearCode;
    /** 学年学期编码 */
    private String            yearTermCode;
    /** 学期编码 */
    private String            termCode;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改者 */
    private String            modifier;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 开始日期 */
    private Timestamp         startDate;
    /** 结束日期 */
    private Timestamp         endDate;
    /** 总周数 */
    private Integer           totalWeekNum;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getYearTermCode() {
        return yearTermCode;
    }

    public void setYearTermCode(String yearTermCode) {
        this.yearTermCode = yearTermCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalWeekNum() {
        return totalWeekNum;
    }

    public void setTotalWeekNum(Integer totalWeekNum) {
        this.totalWeekNum = totalWeekNum;
    }

}
