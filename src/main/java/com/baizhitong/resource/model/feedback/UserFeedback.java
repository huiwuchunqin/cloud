package com.baizhitong.resource.model.feedback;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * UserFeedback 用户反馈表
 * 
 * @author creator zhanglzh 2016年9月27日 下午6:16:29
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "user_feedback")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class UserFeedback implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    @Id
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
    /** 反馈内容 */
    private String            content;
    /** 反馈时间 */
    private Timestamp         actionTime;
    /** 模块描述 */
    private String            moduleDesc;
    /** 备注 */
    private String            memo;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
