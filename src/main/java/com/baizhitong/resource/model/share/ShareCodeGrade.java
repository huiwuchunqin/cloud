package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 年级实体类
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
@Entity
@Table(name = "share_code_grade")
public class ShareCodeGrade implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 系统ID */
    private @Id String        gid;
    /** 编码 */
    private String            code;
    /** 名称 */
    private String            name;
    /** 显示顺序 */
    private Integer           dispOrder;
    /** 描述 */
    private String            description;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改日期 */
    private Timestamp         modifyTime;
    /** 修改IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 园区年级编码 */
    private String            ioCode;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getIoCode() {
        return ioCode;
    }

    public void setIoCode(String ioCode) {
        this.ioCode = ioCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
