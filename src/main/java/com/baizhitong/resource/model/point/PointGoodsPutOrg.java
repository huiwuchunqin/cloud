package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * PointGoodsPutOrg 商品入库
 * 
 * @author creator gaow 2016年6月27日 下午1:28:47
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "point_goods_put_org")
@Entity
public class PointGoodsPutOrg implements Serializable {
    @Id
    @Column(insertable = false,
            updatable = false)
    /**
     * 主键
     */
    private Integer   id;
    /**
     * 机构编码
     */
    private String    orgCode;
    /**
     * 商品编码
     */
    private String    goodsCode;
    /**
     * 商品数量
     */
    private Integer   goodsAmount;
    /**
     * 修改者
     */
    private String    modifier;
    /**
     * 修改时间
     */
    private Timestamp modifyTime;
    /**
     * 修改ip
     */
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

    public String getGoodsCode() {
        return goodsCode;
    }

    public void setGoodsCode(String goodsCode) {
        this.goodsCode = goodsCode;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
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
