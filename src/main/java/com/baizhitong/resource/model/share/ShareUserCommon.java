package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户通用全表实体类
 * 
 * @author creator Cuidc 2015/12/11
 * @author updater
 */
@Entity
@Table(name = "share_user_common")
public class ShareUserCommon implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 系统ID */
    private @Id String        gid;
    /** 平台编码 */
    private String            platformCode;
    /** 机构编码 */
    private String            orgCode;
    /** 用户代码 */
    private String            userCode;
    /** 用户姓名 */
    private String            userName;
    /** 机构内终身代码 */
    private String            uniqueCodeOrg;
    /** 院系 */
    private String            academyCode;
    /** 专业 */
    private String            majorCode;
    /** 用户身份 */
    private Integer           userRole;
    /** 登录账号 */
    private String            loginAccount;
    /** 状态 */
    private Integer           status;

    /** 性别 */
    private Integer           gender;
    /** 个人简介 */
    private String            introduction;
    /** 用户头像 */
    private String            avatarsImg;
    /** 证件类型代码 */
    private String            idTypeCode;
    /** 证件号 */
    private String            idNo;
    /** 证件有效期 */
    private Timestamp         idValidDate;
    /** 出生年月 */
    private Timestamp         birthday;
    /** 出生地 */
    private String            birthPlace;
    /** 籍贯码 */
    private String            nativePlaceCode;
    /** 民族码 */
    private String            nationalityCode;
    /** 国籍地区码 */
    private String            countryCode;
    /** 婚姻状况码 */
    private String            marriageStatusCode;
    /** 政治面貌码 */
    private String            politicsStatusCode;
    /** 健康状况码 */
    private String            healthStatusCode;
    /** 信仰宗教码 */
    private String            religionCode;
    /** 血型码 */
    private String            bloodTypeCode;
    /** 家庭住址 */
    private String            address;
    /** 固定电话 */
    private String            phone;
    /** 紧急联系电话 */
    private String            urgentPhone;
    /** 户口所在地 */
    private String            addressResidence;
    /** 户口性质码 */
    private String            residenceTypeCode;
    /** 是否流动人口 */
    private String            flagFloatPopulation;
    /** 特长 */
    private String            strength;
    /** 通信地址 */
    private String            addressContact;
    /** 邮政编码 */
    private String            postcode;
    /** 主页地址 */
    private String            homepageUrl;
    /** 英文姓名 */
    private String            englishName;
    /** 曾用名 */
    private String            nameUsed;
    /** 邮箱 */
    private String            email;
    /** 手机号 */
    private String            mobile;
    /** QQ号 */
    private String            qq;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改日期 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 登录密码 */
    private String            password;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniqueCodeOrg() {
        return uniqueCodeOrg;
    }

    public void setUniqueCodeOrg(String uniqueCodeOrg) {
        this.uniqueCodeOrg = uniqueCodeOrg;
    }

    public String getAcademyCode() {
        return academyCode;
    }

    public void setAcademyCode(String academyCode) {
        this.academyCode = academyCode;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAvatarsImg() {
        return avatarsImg;
    }

    public void setAvatarsImg(String avatarsImg) {
        this.avatarsImg = avatarsImg;
    }

    public String getIdTypeCode() {
        return idTypeCode;
    }

    public void setIdTypeCode(String idTypeCode) {
        this.idTypeCode = idTypeCode;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Timestamp getIdValidDate() {
        return idValidDate;
    }

    public void setIdValidDate(Timestamp idValidDate) {
        this.idValidDate = idValidDate;
    }

    public Timestamp getBirthday() {
        return birthday;
    }

    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNativePlaceCode() {
        return nativePlaceCode;
    }

    public void setNativePlaceCode(String nativePlaceCode) {
        this.nativePlaceCode = nativePlaceCode;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMarriageStatusCode() {
        return marriageStatusCode;
    }

    public void setMarriageStatusCode(String marriageStatusCode) {
        this.marriageStatusCode = marriageStatusCode;
    }

    public String getPoliticsStatusCode() {
        return politicsStatusCode;
    }

    public void setPoliticsStatusCode(String politicsStatusCode) {
        this.politicsStatusCode = politicsStatusCode;
    }

    public String getHealthStatusCode() {
        return healthStatusCode;
    }

    public void setHealthStatusCode(String healthStatusCode) {
        this.healthStatusCode = healthStatusCode;
    }

    public String getReligionCode() {
        return religionCode;
    }

    public void setReligionCode(String religionCode) {
        this.religionCode = religionCode;
    }

    public String getBloodTypeCode() {
        return bloodTypeCode;
    }

    public void setBloodTypeCode(String bloodTypeCode) {
        this.bloodTypeCode = bloodTypeCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrgentPhone() {
        return urgentPhone;
    }

    public void setUrgentPhone(String urgentPhone) {
        this.urgentPhone = urgentPhone;
    }

    public String getAddressResidence() {
        return addressResidence;
    }

    public void setAddressResidence(String addressResidence) {
        this.addressResidence = addressResidence;
    }

    public String getResidenceTypeCode() {
        return residenceTypeCode;
    }

    public void setResidenceTypeCode(String residenceTypeCode) {
        this.residenceTypeCode = residenceTypeCode;
    }

    public String getFlagFloatPopulation() {
        return flagFloatPopulation;
    }

    public void setFlagFloatPopulation(String flagFloatPopulation) {
        this.flagFloatPopulation = flagFloatPopulation;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getAddressContact() {
        return addressContact;
    }

    public void setAddressContact(String addressContact) {
        this.addressContact = addressContact;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getHomepageUrl() {
        return homepageUrl;
    }

    public void setHomepageUrl(String homepageUrl) {
        this.homepageUrl = homepageUrl;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getNameUsed() {
        return nameUsed;
    }

    public void setNameUsed(String nameUsed) {
        this.nameUsed = nameUsed;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
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
