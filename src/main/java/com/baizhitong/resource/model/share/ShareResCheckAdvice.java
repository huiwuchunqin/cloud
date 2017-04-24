package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源审核意见 ShareResCheckAdvice TODO
 * 
 * @author creator gaow 2017年1月13日 下午5:41:20
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "share_res_check_advice")
@Entity
public class ShareResCheckAdvice implements Serializable {
    @Id
    private Integer   id;
    private String    advice;
    private Integer   flagAvailable;
    private Integer   dispOrder;
    private String    modifier;
    private Timestamp modifyTime;
    private String    modifyIp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Integer getFlagAvailable() {
        return flagAvailable;
    }

    public void setFlagAvailable(Integer flagAvailable) {
        this.flagAvailable = flagAvailable;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
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

    public String getModifyIp() {
        return modifyIp;
    }

    public void setModifyIp(String modifyIp) {
        this.modifyIp = modifyIp;
    }

}
