package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品兑换明细 PointConvertDetail TODO
 * 
 * @author creator gaow 2016年6月28日 上午10:16:09
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "point_convert_detail")
@Entity
public class PointConvertDetail implements Serializable {
    /**
     * serialVersionUID:TODO（用一句话描述这个变量表示什么）
     *
     * @since 1.0.0
     */

    private static final long serialVersionUID = 1L;
    @Id
    @Column(insertable = false,
            updatable = false)
    /** 主键 */
    private Integer           id;
    /** 机构编码 */
    private String            orgCode;
    /** 用户代码 */
    private String            userCode;
    /** 用户身份 */
    private Integer           userRole;
    /** 用户姓名 */
    private String            userName;
    /** 商品类型 */
    private Integer           goodsType;
    /** 商品代码 */
    private String            goodsCode;
    /** 商品名称 */
    private String            goodsName;
    /** 兑换日期 */
    private Timestamp         convertDate;
    /** 兑换数量 */
    private Integer           convertAmount;
    /** 消耗积分 */
    private Integer           convertPoints;
    /** 备注 */
    private String            memo;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Timestamp         modifyTime;
    /** 更新者IP */
    private String            modifierIP;
    /** 商品业务类型 10：兑换商品； 20：抽奖商品 */
    private Integer           goodsBizType;

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

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGoodsBizType() {
        return goodsBizType;
    }

    public void setGoodsBizType(Integer goodsBizType) {
        this.goodsBizType = goodsBizType;
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

    public Timestamp getConvertDate() {
        return convertDate;
    }

    public void setConvertDate(Timestamp convertDate) {
        this.convertDate = convertDate;
    }

    public Integer getConvertAmount() {
        return convertAmount;
    }

    public void setConvertAmount(Integer convertAmount) {
        this.convertAmount = convertAmount;
    }

    public Integer getConvertPoints() {
        return convertPoints;
    }

    public void setConvertPoints(Integer convertPoints) {
        this.convertPoints = convertPoints;
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
