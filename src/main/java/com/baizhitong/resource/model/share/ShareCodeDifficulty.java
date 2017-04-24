package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 难度系数编码表
 * 
 * @author shancy 2015/12/02
 */
@Entity
@Table(name = "share_code_difficulty")
public class ShareCodeDifficulty implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 系统ID */
    private @Id String        gid;
    /** 编码 */
    private Integer           code;
    /** 名称 */
    private String            name;
    /** 正确率最小值 */
    private Float             minValue;
    /** 正确率最大值 */
    private Float             maxValue;
    /** 显示顺序 */
    private Integer           dispOrder;
    /** 修改日期 */
    private Timestamp         modifyTime;
    /** 修改IP */
    private String            modifyIP;
    /** 修改程序 */
    private String            modifyPgm;
    /** 系统版本号 */
    private Integer           sysVer;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMinValue() {
        return minValue;
    }

    public void setMinValue(Float minValue) {
        this.minValue = minValue;
    }

    public Float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Float maxValue) {
        this.maxValue = maxValue;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }

}
