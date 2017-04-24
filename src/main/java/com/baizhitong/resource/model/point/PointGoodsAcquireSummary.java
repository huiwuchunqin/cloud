package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品兑换发放汇总 PointGoodsAcquireSummary TODO
 * 
 * @author creator gaowei 2016年6月28日 下午2:22:29
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "point_goods_acquire_summary")
@Entity
public class PointGoodsAcquireSummary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(insertable = true,
            updatable = true)
    /** 主键 */
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
    /** 用户头像 */
    private String            avatarsImg;
    /** 行政班级代码 */
    private String            adminClassCode;
    /** 行政班级名称 */
    private String            adminClassName;
    /** 商品级别 */
    private Integer           goodsLevel;
    /** 商品类型 */
    private Integer           goodsType;
    /** 商品代码 */
    private String            goodsCode;
    /** 商品名称 */
    private String            goodsName;
    /** 商品规格描述 */
    private String            goodsSpecification;
    /** 商品缩略图 */
    private String            goodsThumbnailJson;
    /** 已兑换商品数量 */
    private Integer           convertedAmount;
    /** 已领用商品数量 */
    private Integer           acquiredAmount;
    /** 待领用商品数量 */
    private Integer           acquiringAmount;
    /** 最近计算基准日 */
    private Timestamp         lastBaseTime;
    /** 最后领用批次 */
    private Integer           lastGrantBatch;
    /** 最后领用日期 */
    private Date              lastAcquireDate;
    /** 备注 */
    private String            memo;
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

    public String getAvatarsImg() {
        return avatarsImg;
    }

    public void setAvatarsImg(String avatarsImg) {
        this.avatarsImg = avatarsImg;
    }

    public String getAdminClassCode() {
        return adminClassCode;
    }

    public void setAdminClassCode(String adminClassCode) {
        this.adminClassCode = adminClassCode;
    }

    public String getAdminClassName() {
        return adminClassName;
    }

    public void setAdminClassName(String adminClassName) {
        this.adminClassName = adminClassName;
    }

    public Integer getGoodsLevel() {
        return goodsLevel;
    }

    public void setGoodsLevel(Integer goodsLevel) {
        this.goodsLevel = goodsLevel;
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

    public String getGoodsThumbnailJson() {
        return goodsThumbnailJson;
    }

    public void setGoodsThumbnailJson(String goodsThumbnailJson) {
        this.goodsThumbnailJson = goodsThumbnailJson;
    }

    public Integer getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(Integer convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    public Integer getAcquiredAmount() {
        return acquiredAmount;
    }

    public void setAcquiredAmount(Integer acquiredAmount) {
        this.acquiredAmount = acquiredAmount;
    }

    public Integer getAcquiringAmount() {
        return acquiringAmount;
    }

    public void setAcquiringAmount(Integer acquiringAmount) {
        this.acquiringAmount = acquiringAmount;
    }

    public Timestamp getLastBaseTime() {
        return lastBaseTime;
    }

    public void setLastBaseTime(Timestamp lastBaseTime) {
        this.lastBaseTime = lastBaseTime;
    }

    public Integer getLastGrantBatch() {
        return lastGrantBatch;
    }

    public void setLastGrantBatch(Integer lastGrantBatch) {
        this.lastGrantBatch = lastGrantBatch;
    }

    public Date getLastAcquireDate() {
        return lastAcquireDate;
    }

    public void setLastAcquireDate(Date lastAcquireDate) {
        this.lastAcquireDate = lastAcquireDate;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
}
