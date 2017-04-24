package com.baizhitong.resource.model.company;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 机构学科
 * 
 * @author creator gaow 2016年1月21日 下午2:09:18
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_org_subject")
public class ShareOrgSubject implements Serializable {
    private static final long serialVersionUID = 1L;
    /****** 系统ID *******/
    private String            gid;
    /****** 机构编码 *******/
    private String            orgCode;
    /****** 学科编码 *******/
    private String            subjectCode;
    /****** 修改程序 *******/
    private String            modifyPgm;
    /****** 修改时间 *******/
    private String            modifyIP;
    /****** 修改者IP *******/
    private Timestamp         modifyTime;
    /****** 系统版本号 *******/
    private Integer           sysVer;
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

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
