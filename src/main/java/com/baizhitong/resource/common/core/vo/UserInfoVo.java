package com.baizhitong.resource.common.core.vo;

import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.share.ShareUserLogin;

/**
 * 用户信息VO实体类
 * 
 * @author creator Cuidc 2015/12/11
 * @author updater
 */
public class UserInfoVo {

    /** 部署级别 */
    private Integer  deployLevel;

    /** 当前学期编码 */
    private String   currentStudyYearCode;

    /** 当前学期 */
    private String   currentTermCode;

    /** 平台代码 */
    private String   platformCode;

    /** 机构名称 */
    private String   orgName;

    /** 机构简称 */
    private String   orgNameShort;

    /** 机构代码 */
    private String   orgCode;

    /** 用户名称 */
    private String   userName;

    /** 用户代码 */
    private String   userCode;

    /** 用户身份 */
    private Integer  userRole;

    /** 登录者IP */
    private String   userIP;

    /** 登录账号 */
    private String   loginAccount;

    /** 用户默认学段 */
    private String   userSectionCode;

    /** 用户默认学段 */
    private String   userSectionName;

    /** 用户默认年级 */
    private String   userGradeCode;

    /** 用户默认年级 */
    private String   userGradeCodeName;

    /** 用户默认学科 */
    private String   userSubjectCode;

    /** 用户默认学科 */
    private String   userSubjectName;

    /** 学生所在行政班级 */
    private String   studentAdminClassCode;

    /** 学生所在行政小组 */
    private String   studentAdminGroupCode;

    /** 教师默认学科 */
    private String   teacherSubjectCode;

    /** 教师默认学科 */
    private String   teacherSubjectName;

    /** 教师默认教材 */
    private String   teacherTextbookCode;

    /** 头像地址 */
    private String   avatarsImg;

    /** 当前人员登陆令牌（即sessionId) */
    private String   token;
    private AuthRole role;
    private String   loginType;
    private boolean  flagSuperAdmin;

    /*  *//** 学期学年 *//*
                      * private YearTermVo yearTerm;
                      */

    /** 学生学号 */
    private String   studentNumber;

    public Integer getDeployLevel() {
        return deployLevel;
    }

    public void setDeployLevel(Integer deployLevel) {
        this.deployLevel = deployLevel;
    }

    public boolean isFlagSuperAdmin() {
        return flagSuperAdmin;
    }

    public void setFlagSuperAdmin(boolean flagSuperAdmin) {
        this.flagSuperAdmin = flagSuperAdmin;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getCurrentStudyYearCode() {
        return currentStudyYearCode;
    }

    public void setCurrentStudyYearCode(String currentStudyYearCode) {
        this.currentStudyYearCode = currentStudyYearCode;
    }

    public String getCurrentTermCode() {
        return currentTermCode;
    }

    public void setCurrentTermCode(String currentTermCode) {
        this.currentTermCode = currentTermCode;
    }

    public String getPlatformCode() {
        return platformCode;
    }

    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgNameShort() {
        return orgNameShort;
    }

    public void setOrgNameShort(String orgNameShort) {
        this.orgNameShort = orgNameShort;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getUserSectionCode() {
        return userSectionCode;
    }

    public void setUserSectionCode(String userSectionCode) {
        this.userSectionCode = userSectionCode;
    }

    public String getUserSectionName() {
        return userSectionName;
    }

    public void setUserSectionName(String userSectionName) {
        this.userSectionName = userSectionName;
    }

    public String getUserGradeCode() {
        return userGradeCode;
    }

    public void setUserGradeCode(String userGradeCode) {
        this.userGradeCode = userGradeCode;
    }

    public String getUserGradeCodeName() {
        return userGradeCodeName;
    }

    public void setUserGradeCodeName(String userGradeCodeName) {
        this.userGradeCodeName = userGradeCodeName;
    }

    public String getUserSubjectCode() {
        return userSubjectCode;
    }

    public void setUserSubjectCode(String userSubjectCode) {
        this.userSubjectCode = userSubjectCode;
    }

    public String getUserSubjectName() {
        return userSubjectName;
    }

    public void setUserSubjectName(String userSubjectName) {
        this.userSubjectName = userSubjectName;
    }

    public String getStudentAdminClassCode() {
        return studentAdminClassCode;
    }

    public void setStudentAdminClassCode(String studentAdminClassCode) {
        this.studentAdminClassCode = studentAdminClassCode;
    }

    public String getStudentAdminGroupCode() {
        return studentAdminGroupCode;
    }

    public void setStudentAdminGroupCode(String studentAdminGroupCode) {
        this.studentAdminGroupCode = studentAdminGroupCode;
    }

    public String getTeacherSubjectCode() {
        return teacherSubjectCode;
    }

    public void setTeacherSubjectCode(String teacherSubjectCode) {
        this.teacherSubjectCode = teacherSubjectCode;
    }

    public String getTeacherSubjectName() {
        return teacherSubjectName;
    }

    public void setTeacherSubjectName(String teacherSubjectName) {
        this.teacherSubjectName = teacherSubjectName;
    }

    public String getTeacherTextbookCode() {
        return teacherTextbookCode;
    }

    public void setTeacherTextbookCode(String teacherTextbookCode) {
        this.teacherTextbookCode = teacherTextbookCode;
    }

    public String getAvatarsImg() {
        return avatarsImg;
    }

    public void setAvatarsImg(String avatarsImg) {
        this.avatarsImg = avatarsImg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    /**
     * 登录用户转vo ()<br>
     * 
     * @param loginUser
     * @return
     */
    public static UserInfoVo loginUserToVo(ShareUserLogin loginUser) {
        UserInfoVo vo = new UserInfoVo();
        vo.setAvatarsImg(loginUser.getAvatarsImg());
        vo.setLoginAccount(loginUser.getLoginAccount());
        vo.setOrgCode(loginUser.getOrgCode());
        vo.setOrgName(loginUser.getOrgName());
        vo.setUserCode(loginUser.getUserCode());
        vo.setUserRole(loginUser.getUserRole());
        vo.setUserName(loginUser.getUserName());
        return vo;
    }

}
