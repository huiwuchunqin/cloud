package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 积分头衔等级 PointRank TODO
 * 
 * @author creator BZT 2016年6月24日 上午11:09:16
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "point_rank")
@Entity
public class PointRank implements Serializable {
    @Id
    @Column(insertable = false,
            updatable = false)
    /** 主键 */
    private Integer   id;
    /** 业务版本号 */
    private Integer   bizVersion;
    /** 版本开始日期 */
    private Long      startTime;
    /** 版本失效日期 */
    private Long      expireTime;
    /** 用户身份 */
    private Integer   userRole;
    /** 头衔代码 */
    private String    rankCode;
    /** 头衔名称 */
    private String    rankName;
    /** 奖励商品代码 */
    private String    goodsCode;
    /** 奖励商品名称 */
    private String    goodsName;
    /** 奖励商品数量 */
    private String    goodAmount;
    /** 奖励商品缩略图 */
    private String    thumbnailJson;
    /** 所需累计积分 */
    private Integer   totalPoint;
    /** 显示顺序 */
    private Integer   dispOrder;
    /** 备注 */
    private String    memo;
    /** 更新者 */
    private String    modifier;
    /** 更新时间 */
    private Timestamp modifyTime;
    /** 更新者IP */
    private String    modifierIP;
    /** 系统版本号 */
    private long      sysVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(Integer bizVersion) {
        this.bizVersion = bizVersion;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodAmount() {
        return goodAmount;
    }

    public void setGoodAmount(String goodAmount) {
        this.goodAmount = goodAmount;
    }

    public String getThumbnailJson() {
        return thumbnailJson;
    }

    public void setThumbnailJson(String thumbnailJson) {
        this.thumbnailJson = thumbnailJson;
    }

    public Integer getTotalPoint() {
        return totalPoint;
    }

    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
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

    public long getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(long sysVersion) {
        this.sysVersion = sysVersion;
    }

}
