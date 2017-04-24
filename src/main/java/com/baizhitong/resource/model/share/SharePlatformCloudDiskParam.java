package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 平台-云盘参数
 * 
 * @author creator ZhangQiang 2016年9月19日 上午9:32:18
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_platform_cloud_disk_param")
public class SharePlatformCloudDiskParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 系统ID */
    @Id
    private String            gid;
    /** 用户身份 */
    private Integer           userRole;
    /** 默认空间配额 */
    private Long              capacityQuota;
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

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public Long getCapacityQuota() {
        return capacityQuota;
    }

    public void setCapacityQuota(Long capacityQuota) {
        this.capacityQuota = capacityQuota;
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

}
