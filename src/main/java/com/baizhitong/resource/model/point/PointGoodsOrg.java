package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 3051.可兑商品-机构
 * 
 * @author creator zhangqiang 2016年6月13日 下午1:40:50
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "point_goods_org")
public class PointGoodsOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @Column(insertable = true,
            updatable = false)
    private Integer           id;
    /** 面向的用户角色 */
    private Integer           userRoleRequirement;
    /** 机构编码 */
    private String            orgCode;
    /** 商品类型 */
    private Integer           goodsType;
    /** 商品代码 */
    private String            goodsCode;
    /** 商品名称 */
    private String            goodsName;
    /** 商品规格描述 */
    private String            goodsSpecification;
    /** 商品库存数量 */
    private Integer           goodsAmount;
    /** 商品总数量 */
    private Integer           goodsTotalAmount;
    /** 商品单位 */
    private String            goodsUnit;
    /** 商品缩略图 */
    private String            goodsThumbnailJson;
    /** 添加日期 */
    private Date              addDate;
    /** 上架时间 */
    private Long              validTimeStart;
    /** 下架时间 */
    private Long              validTimeEnd;
    /** 兑换所需积分 */
    private Integer           points;
    /** 显示顺序 */
    private Integer           dispOrder;
    /** 备注 */
    private String            memo;
    /** 是否删除 */
    private Integer           flagDelete;
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
    /** 系统版本号 */
    private Integer           sysVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserRoleRequirement() {
        return userRoleRequirement;
    }

    public void setUserRoleRequirement(Integer userRoleRequirement) {
        this.userRoleRequirement = userRoleRequirement;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
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

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    public Integer getGoodsTotalAmount() {
        return goodsTotalAmount;
    }

    public void setGoodsTotalAmount(Integer goodsTotalAmount) {
        this.goodsTotalAmount = goodsTotalAmount;
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

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Long getValidTimeStart() {
        return validTimeStart;
    }

    public void setValidTimeStart(Long validTimeStart) {
        this.validTimeStart = validTimeStart;
    }

    public Long getValidTimeEnd() {
        return validTimeEnd;
    }

    public void setValidTimeEnd(Long validTimeEnd) {
        this.validTimeEnd = validTimeEnd;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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

    public Integer getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(Integer sysVersion) {
        this.sysVersion = sysVersion;
    }

}
