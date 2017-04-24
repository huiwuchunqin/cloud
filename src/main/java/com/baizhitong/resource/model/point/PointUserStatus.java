package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "point_rule_lottery_platform")
@Entity
public class PointUserStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer           id;
    /** 机构编码 */
    private String            orgCode;
    /** 用户身份 */
    private Integer           userRole;
    /** 用户代码 */
    private String            userCode;
    /** 用户姓名 */
    private String            userName;
    /** 用户头像 */
    private String            avatarsImg;
    /** 年级编码 */
    private String            gradeCode;
    /** 最近签到日期 */
    private Integer           lastSignonDate;
    /** 签到连续天数 */
    private Integer           signonDays;
    /** 当前积分 */
    private Integer           currentPoints;
    /** 累计积分 */
    private Integer           totalPoints;
    /** 头衔代码 */
    private String            rankCode;
    /** 头衔名称 */
    private String            rankName;
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

    public String getAvatarsImg() {
        return avatarsImg;
    }

    public void setAvatarsImg(String avatarsImg) {
        this.avatarsImg = avatarsImg;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getLastSignonDate() {
        return lastSignonDate;
    }

    public void setLastSignonDate(Integer lastSignonDate) {
        this.lastSignonDate = lastSignonDate;
    }

    public Integer getSignonDays() {
        return signonDays;
    }

    public void setSignonDays(Integer signonDays) {
        this.signonDays = signonDays;
    }

    public Integer getCurrentPoints() {
        return currentPoints;
    }

    public void setCurrentPoints(Integer currentPoints) {
        this.currentPoints = currentPoints;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getRankCode() {
        return rankCode;
    }

    public void setRankCode(String rankCode) {
        this.rankCode = rankCode;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
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
