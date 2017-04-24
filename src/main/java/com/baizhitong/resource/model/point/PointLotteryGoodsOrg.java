package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * PointLotteryGoodsOrg 抽奖商品机构
 * 
 * @author creator BZT 2016年6月21日 下午7:18:48
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "point_lottery_goods_org")
@Entity
public class PointLotteryGoodsOrg implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(insertable = false,
            updatable = false)
    /** 主键 */
    private Integer           id;
    /** 机构编码 */
    private String            orgCode;
    /** 面向的用户角色 */
    private Integer           userRoleRequirement;
    /** 业务版本号 */
    private Integer           bizVersion;
    /** 版本开始日期 */
    private Long              startTime;
    /** 版本失效日期 */
    private Long              expireTime;
    /** 抽奖商品标识 */
    private Integer           lotteryGoodsType;
    /** 用于当前抽奖 */
    private Integer           flagUsing;
    /** 商品代码 */
    private String            goodsCode;
    /** 商品名称 */
    private String            goodsName;
    /** 商品规格描述 */
    private String            goodsSpecification;
    /** 商品单位 */
    private String            goodsUnit;
    /** 商品缩略图 */
    private String            goodsThumbnailJson;
    /** 抽中概率 */
    private Double            probability;
    /** 虚拟商品赠送积分 */
    private Integer           pointFeedback;
    /** 显示顺序 */
    private Integer           dispOrder;
    /** 备注 */
    private String            memo;
    /** 是否删除 */
    private Integer           flagDelete;
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

    public Integer getUserRoleRequirement() {
        return userRoleRequirement;
    }

    public void setUserRoleRequirement(Integer userRoleRequirement) {
        this.userRoleRequirement = userRoleRequirement;
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

    public Integer getLotteryGoodsType() {
        return lotteryGoodsType;
    }

    public void setLotteryGoodsType(Integer lotteryGoodsType) {
        this.lotteryGoodsType = lotteryGoodsType;
    }

    public Integer getFlagUsing() {
        return flagUsing;
    }

    public void setFlagUsing(Integer flagUsing) {
        this.flagUsing = flagUsing;
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

    public String getGoodsSpecification() {
        return goodsSpecification;
    }

    public void setGoodsSpecification(String goodsSpecification) {
        this.goodsSpecification = goodsSpecification;
    }

    public String getGoodsUnit() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit = goodsUnit;
    }

    public String getGoodsThumbnailJson() {
        return goodsThumbnailJson;
    }

    public void setGoodsThumbnailJson(String goodsThumbnailJson) {
        this.goodsThumbnailJson = goodsThumbnailJson;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    public Integer getPointFeedback() {
        return pointFeedback;
    }

    public void setPointFeedback(Integer pointFeedback) {
        this.pointFeedback = pointFeedback;
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

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
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
