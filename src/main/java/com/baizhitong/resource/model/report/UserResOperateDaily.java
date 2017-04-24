package com.baizhitong.resource.model.report;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 用户资源操作统计 UserResOperateDaily TODO
 * 
 * @author creator BZT 2016年7月18日 下午12:57:17
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "user_res_operate_daily")
public class UserResOperateDaily {
    /** 主键 */
    private Integer   id;
    /** 机构编码 */
    private String    orgCode;
    /** 用户代码 */
    private String    userCode;
    /** 用户姓名 */
    private String    userName;
    /** 用户身份 */
    private Integer   userRole;
    /** 学段编码 */
    private String    sectionCode;
    /** 基准日 */
    private Integer   baseDate;
    /** 学年代码 */
    private Integer   studyYearCode;
    /** 学年学期代码 */
    private String    yearTermCode;
    /** 学期代码 */
    private String    termCode;
    /** 基准月 */
    private Integer   baseMonth;
    /** 是否月基准 */
    private Integer   flagBaseMonth;
    /** 基准周 */
    private Integer   weekNum;
    /** 是否周基准 */
    private Integer   flagBaseWeek;
    /** 浏览总数 */
    private Integer   browseCount;
    /** 下载总数 */
    private Integer   downloadCount;
    /** 引用总数 */
    private Integer   referCount;
    /** 收藏总数 */
    private Integer   favoriteCount;
    /** 点赞总数 */
    private Integer   goodCount;
    /** 点踩总数 */
    private Integer   badCount;
    /** 评论总数 */
    private Integer   commentCount;
    /** 更新方式 */
    private Integer   updateType;
    /** 更新者 */
    private String    modifier;
    /** 更新时间 */
    private Timestamp modifyTime;
    /** 更新者IP */
    private String    modifierIP;

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

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public Integer getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Integer baseDate) {
        this.baseDate = baseDate;
    }

    public Integer getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(Integer studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getYearTermCode() {
        return yearTermCode;
    }

    public void setYearTermCode(String yearTermCode) {
        this.yearTermCode = yearTermCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public Integer getBaseMonth() {
        return baseMonth;
    }

    public void setBaseMonth(Integer baseMonth) {
        this.baseMonth = baseMonth;
    }

    public Integer getFlagBaseMonth() {
        return flagBaseMonth;
    }

    public void setFlagBaseMonth(Integer flagBaseMonth) {
        this.flagBaseMonth = flagBaseMonth;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getFlagBaseWeek() {
        return flagBaseWeek;
    }

    public void setFlagBaseWeek(Integer flagBaseWeek) {
        this.flagBaseWeek = flagBaseWeek;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getReferCount() {
        return referCount;
    }

    public void setReferCount(Integer referCount) {
        this.referCount = referCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getBadCount() {
        return badCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
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
