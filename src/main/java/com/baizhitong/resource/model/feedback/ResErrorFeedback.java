package com.baizhitong.resource.model.feedback;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * ResErrorFeedback 资源纠错表
 * 
 * @author creator zhanglzh 2016年9月27日 下午6:16:29
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "res_error_feedback")
public class ResErrorFeedback implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @Column(insertable = false,
            updatable = false)
    private Integer           id;

    /** 机构编码 */
    private String            orgCode;
    /** 机构名称 */
    private String            orgName;
    /** 用户身份 */
    private Integer           userRole;
    /** 用户代码 */
    private String            userCode;
    /** 用户姓名 */
    private String            userName;
    /** 终端类型 */
    private Integer           deviceType;
    /** 浏览器信息 */
    private String            browserInfo;
    /** 移动终端-APP代号及版本号 */
    private String            appVerInfo;
    /** 移动终端-生产厂家 */
    private String            deviceBrand;
    /** 移动终端-操作系统版本号 */
    private String            deviceOsVer;
    /** 移动终端-其他信息 */
    private String            deviceOther;
    /** 地理位置信息 */
    private String            geoInfo;
    /** 资源分类（一级） */
    private Integer           resTypeL1;
    /** 资源分类（二级） */
    private String            resTypeL2;
    /** 资源编码 */
    private String            resCode;
    /** 资源名称 */
    private String            resName;
    /** 报告时间 */
    private Timestamp         feedbackTime;
    /** 错误类型 */
    private String            errorType;
    /** 错误描述 */
    private String            errorDesc;
    /** 模块描述 */
    private String            moduleDesc;
    /** 是否删除 */
    private Integer           flagDelete;
    /** 备注 */
    private String            memo;
    /** 创建者 */
    private String            creator;
    /** 创建时间 */
    private Timestamp         createTime;
    /** 创建者IP */
    private String            creatorIP;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Timestamp         modifyTime;
    /** 更新者IP */
    private String            modifierIP;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }

    public String getAppVerInfo() {
        return appVerInfo;
    }

    public void setAppVerInfo(String appVerInfo) {
        this.appVerInfo = appVerInfo;
    }

    public String getDeviceBrand() {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.deviceBrand = deviceBrand;
    }

    public String getDeviceOsVer() {
        return deviceOsVer;
    }

    public void setDeviceOsVer(String deviceOsVer) {
        this.deviceOsVer = deviceOsVer;
    }

    public String getDeviceOther() {
        return deviceOther;
    }

    public void setDeviceOther(String deviceOther) {
        this.deviceOther = deviceOther;
    }

    public String getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(String geoInfo) {
        this.geoInfo = geoInfo;
    }

    public Integer getResTypeL1() {
        return resTypeL1;
    }

    public void setResTypeL1(Integer resTypeL1) {
        this.resTypeL1 = resTypeL1;
    }

    public String getResTypeL2() {
        return resTypeL2;
    }

    public void setResTypeL2(String resTypeL2) {
        this.resTypeL2 = resTypeL2;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Timestamp getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Timestamp feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreatorIP() {
        return creatorIP;
    }

    public void setCreatorIP(String creatorIP) {
        this.creatorIP = creatorIP;
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

    public String getModifierIP() {
        return modifierIP;
    }

    public void setModifierIP(String modifierIP) {
        this.modifierIP = modifierIP;
    }
}
