package com.baizhitong.resource.model.rel;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目与关键词实体类
 * 
 * @author lusm 2015/12/10
 *
 */
public class RelQuestionKeyword implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 主键 */
    private Integer           id;
    /** 资源ID */
    private Integer           resId;
    /** 资源编码 */
    private String            resCode;
    /** 资源分类（一级） */
    private Integer           resTypeL1;
    /** 关键词 */
    private String            keyword;
    /** 设定方式 */
    private Integer           settingType;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Date              modifyTime;
    /** 更新者IP */
    private String            modifierIP;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public Integer getResTypeL1() {
        return resTypeL1;
    }

    public void setResTypeL1(Integer resTypeL1) {
        this.resTypeL1 = resTypeL1;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getSettingType() {
        return settingType;
    }

    public void setSettingType(Integer settingType) {
        this.settingType = settingType;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifierIP() {
        return modifierIP;
    }

    public void setModifierIP(String modifierIP) {
        this.modifierIP = modifierIP;
    }

}
