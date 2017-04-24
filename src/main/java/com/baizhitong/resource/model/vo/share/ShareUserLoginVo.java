package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;

import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.utils.DataUtils;

/**
 * 登录用户vo ShareUserLoginVo TODO
 * 
 * @author creator BZT 2016年2月3日 下午7:21:23
 * @author updater
 *
 * @version 1.0.0
 */
public class ShareUserLoginVo {
    /** 系统ID */
    private String    gid;
    /** 机构编码 */
    private String    orgCode;
    /** 机构名称 */
    private String    orgName;
    /** 外部机构编码 */
    private String    OrgCodeOut;
    /** 证件类型 */
    private Integer   certificateType;
    /** 证件号码 */
    private String    certificateNo;
    /** 邮箱 */
    private String    mail;
    /** 手机号 */
    private String    mobilePhone;
    /** 用户代码 */
    private String    userCode;
    /** 用户姓名 */
    private String    userName;
    /** 机构内终身代码 */
    private String    uniqueCodeOrg;
    /** 用户身份 */
    private Integer   userRole;
    /** 登录账号 */
    private String    loginAccount;
    /** 登录密码 */
    private String    loginPwd;
    /** 状态 */
    private Integer   status;
    /** 性别 */
    private Integer   gender;
    /** 用户头像 */
    private String    avatarsImg;
    /** 连续登录出错次数 */
    private Integer   loginFailedCount;
    /** 下次登录允许时间 */
    private Timestamp nextLoginPermitTime;
    /** 不允许登录原因 */
    private String    loginDeniedReson;
    /** 修改程序 */
    private String    modifyPgm;
    /** 修改时间 */
    private Timestamp modifyTime;
    /** 修改者IP */
    private String    modifyIP;
    /** 唯一码 */
    private String    cd_guid;
    /** 备用账号 */
    private Integer   standbyAccount;

    public String getGid() {
        return gid;
    }

    public Integer getStandbyAccount() {
        return standbyAccount;
    }

    public void setStandbyAccount(Integer standbyAccount) {
        this.standbyAccount = standbyAccount;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getCd_guid() {
        return cd_guid;
    }

    public void setCd_guid(String cd_guid) {
        this.cd_guid = cd_guid;
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

    public String getOrgCodeOut() {
        return OrgCodeOut;
    }

    public void setOrgCodeOut(String orgCodeOut) {
        OrgCodeOut = orgCodeOut;
    }

    public Integer getCertificateType() {
        return certificateType;
    }

    public void setCertificateType(Integer certificateType) {
        this.certificateType = certificateType;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
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

    public String getUniqueCodeOrg() {
        return uniqueCodeOrg;
    }

    public void setUniqueCodeOrg(String uniqueCodeOrg) {
        this.uniqueCodeOrg = uniqueCodeOrg;
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

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatarsImg() {
        return avatarsImg;
    }

    public void setAvatarsImg(String avatarsImg) {
        this.avatarsImg = avatarsImg;
    }

    public Integer getLoginFailedCount() {
        return loginFailedCount;
    }

    public void setLoginFailedCount(Integer loginFailedCount) {
        this.loginFailedCount = loginFailedCount;
    }

    public Timestamp getNextLoginPermitTime() {
        return nextLoginPermitTime;
    }

    public void setNextLoginPermitTime(Timestamp nextLoginPermitTime) {
        this.nextLoginPermitTime = nextLoginPermitTime;
    }

    public String getLoginDeniedReson() {
        return loginDeniedReson;
    }

    public void setLoginDeniedReson(String loginDeniedReson) {
        this.loginDeniedReson = loginDeniedReson;
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

    /**
     * 
     * vo转实体
     * 
     * @param vo
     * @return
     */
    public static ShareUserLogin voToEntity(ShareUserLoginVo vo) {
        if (vo == null)
            return null;
        ShareUserLogin userLogin = new ShareUserLogin();
        DataUtils.copySimpleObject(vo, userLogin);
        return userLogin;
    }
}
