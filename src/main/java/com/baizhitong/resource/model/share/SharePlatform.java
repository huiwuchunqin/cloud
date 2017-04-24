package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 平台实体
 * 
 * @author creator ZhangQiang 2016年8月30日 下午1:32:46
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_platform")
public class SharePlatform implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 系统ID */
    @Id
    private String            gid;
    /** 平台全局编码 */
    private String            codeGlobal;
    /** 平台名称 */
    private String            name;
    /** 平台识别码 */
    private String            identifier;
    /** 平台部署模式 */
    private Integer           deployType;
    /** 平台访问路径 */
    private String            webAccessPath;
    /** 平台部署级别 */
    private Integer           deployLevel;
    /** 平台显示logo */
    private String            platformLogo;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 备注 */
    private String            memo;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 资源是否允许评论 */
    private Integer           flagResAllowComment;
    /** 是否禁止临时账号登录 */
    private Integer           flagForbidLogin;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCodeGlobal() {
        return codeGlobal;
    }

    public void setCodeGlobal(String codeGlobal) {
        this.codeGlobal = codeGlobal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getDeployType() {
        return deployType;
    }

    public void setDeployType(Integer deployType) {
        this.deployType = deployType;
    }

    public String getWebAccessPath() {
        return webAccessPath;
    }

    public void setWebAccessPath(String webAccessPath) {
        this.webAccessPath = webAccessPath;
    }

    public Integer getDeployLevel() {
        return deployLevel;
    }

    public void setDeployLevel(Integer deployLevel) {
        this.deployLevel = deployLevel;
    }

    public String getPlatformLogo() {
        return platformLogo;
    }

    public void setPlatformLogo(String platformLogo) {
        this.platformLogo = platformLogo;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    public Integer getFlagResAllowComment() {
        return flagResAllowComment;
    }

    public void setFlagResAllowComment(Integer flagResAllowComment) {
        this.flagResAllowComment = flagResAllowComment;
    }

    public Integer getFlagForbidLogin() {
        return flagForbidLogin;
    }

    public void setFlagForbidLogin(Integer flagForbidLogin) {
        this.flagForbidLogin = flagForbidLogin;
    }

}
