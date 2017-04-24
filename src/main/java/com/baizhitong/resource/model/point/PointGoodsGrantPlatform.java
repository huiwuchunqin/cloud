package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 平台发放概要 PointGoodsGrantPlatform TODO
 * 
 * @author creator gaow 2016年6月28日 下午7:29:07
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "point_goods_grant_platform")
@Entity
public class PointGoodsGrantPlatform implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(insertable = false,
            updatable = false)
    /** 主键 */
    private Integer           id;
    /** 机构编码 */
    private String            orgCode;
    /** 学年代码 */
    private String            studyYearCode;
    /** 学期代码 */
    private String            termCode;
    /** 学年学期代码 */
    private String            yearTermCode;
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
    /** 发放批次 */
    private String            actionBatch;
    /** 发放日期 */
    private Date              actionDate;
    /** 发放数量 */
    private Integer           actionNum;
    /** 备注 */
    private String            memo;
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

    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getYearTermCode() {
        return yearTermCode;
    }

    public void setYearTermCode(String yearTermCode) {
        this.yearTermCode = yearTermCode;
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

    public String getActionBatch() {
        return actionBatch;
    }

    public void setActionBatch(String actionBatch) {
        this.actionBatch = actionBatch;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public Integer getActionNum() {
        return actionNum;
    }

    public void setActionNum(Integer actionNum) {
        this.actionNum = actionNum;
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
