package com.baizhitong.resource.model.log;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * LoginLog 用户登录日志表
 * 
 * @author creator BZT 2016年9月28日 下午2:21:27
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "login_log")
public class LoginLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer           id;
    /** 机构编码 */
    private String            orgCode;
    /** 用户角色 */
    private Integer           userRole;
    /** 登录账户 */
    private String            loginAccount;
    /** 登录类型 */
    private Integer           loginType;
    /** 用户编码 */
    private String            userCode;
    /** 用户姓名 */
    private String            userName;
    /** 学段编码 */
    private String            sectionCode;
    /** 年级编码 */
    private String            gradeCode;
    /** 登录时间 */
    private Timestamp         loginTime;
    /** 登录ip */
    private String            loginIP;
    /** 登录设备 */
    private Integer           deviceType;
    /** 浏览器信息 */
    private String            browserInfo;
    /** 应用版本信息 */
    private String            appVerInfo;
    /**       */
    private String            deviceBrand;
    /**       */
    private String            deviceOsVer;
    /**       */
    private String            deviceOther;
    /**       */
    private String            geoInfo;
    /** 是否成功 */
    private Integer           flagSuccessful;
    /** 备注 */
    private String            memo;
    /** 修改者 */
    private String            modifier;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改ip */
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

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
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

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
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

    public Integer getFlagSuccessful() {
        return flagSuccessful;
    }

    public void setFlagSuccessful(Integer flagSuccessful) {
        this.flagSuccessful = flagSuccessful;
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
