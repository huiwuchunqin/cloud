package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机构积分规则 PointRuleLotteryOrg TODO
 * 
 * @author creator BZT 2016年6月23日 下午4:26:19
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "point_rule_lottery_org")
@Entity
public class PointRuleLotteryOrg implements Serializable {
    @Id
    @Column(insertable = false,
            updatable = false)
    /** 主键 */
    private Integer   id;
    /** 业务版本号 */
    private Integer   bizVersion;
    /** 机构编码 */
    private String    orgCode;
    /** 版本开始日期 */
    private Long      startTime;
    /** 版本失效日期 */
    private Long      expireTime;
    /** 消费积分 */
    private Integer   point;
    /** 日最大抽奖次数 */
    private Integer   dayMaxTimes;
    /** 备注 */
    private String    memo;
    /** 显示顺序 */
    private Integer   dispOrder;
    /** 是否删除 */
    private Integer   flagDelete;
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

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Integer getDayMaxTimes() {
        return dayMaxTimes;
    }

    public void setDayMaxTimes(Integer dayMaxTimes) {
        this.dayMaxTimes = dayMaxTimes;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

}
