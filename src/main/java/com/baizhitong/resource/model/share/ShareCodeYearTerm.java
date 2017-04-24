package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学年学期实体
 * 
 * @author creator BZT 2016年4月28日 下午4:35:58
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "share_code_year_term")
@Entity
public class ShareCodeYearTerm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    /** 系统ID */
    private String            gid;
    /** 学年学期代码 */
    private String            yearTermCode;
    /** 学年学期名称 */
    private String            yearTermName;
    /** 学年（始） */
    private Integer           yearBegin;
    /** 学年（终） */
    private Integer           yearEnd;
    /** 学期类型 */
    private String            termCode;
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

    public String getYearTermCode() {
        return yearTermCode;
    }

    public void setYearTermCode(String yearTermCode) {
        this.yearTermCode = yearTermCode;
    }

    public String getYearTermName() {
        return yearTermName;
    }

    public void setYearTermName(String yearTermName) {
        this.yearTermName = yearTermName;
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

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
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
