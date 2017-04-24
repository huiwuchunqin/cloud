package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 资源设置-首页显示
 * 
 * @author creator zhangqiang 2016年7月27日 上午11:27:57
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "res_settings_home")
public class ResSettingsHome implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @Column(insertable = false,
            updatable = false)
    private Integer           id;
    /** 资源编码 */
    private String            resCode;
    /** 设置显示类别 */
    private String            setType;
    /** 资源名称 */
    private String            resName;
    /** 资源分类（一级） */
    private Integer           resTypeL1;
    /** 资源分类（二级） */
    private String            resTypeL2;
    /** 缩略图地址 */
    private String            thumbnailPath;
    /** 封面图像路径 */
    private String            coverPath;
    /** 显示顺序 */
    private Integer           dispOrder;
    /** 是否使用 */
    private Integer           flagAvailable;
    /** 设置时间 */
    private Timestamp         settingTime;
    /** 设置人信息 */
    private String            settingUserName;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Timestamp         modifyTime;
    /** 更新者IP */
    private String            modifierIP;
    /** 学段编码 */
    private String            sectionCode;
    /** 学科编码 */
    private String            subjectCode;
    /** 特色一级分类 */
    private String            resSpecialTypeL1;
    /** 特色二级分类 */
    private String            resSpecialTypeL2;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getSetType() {
        return setType;
    }

    public void setSetType(String setType) {
        this.setType = setType;
    }

    public Integer getResTypeL1() {
        return resTypeL1;
    }

    public void setResTypeL1(Integer resTypeL1) {
        this.resTypeL1 = resTypeL1;
    }

    public String getResTypeL2() {
        return resTypeL2;
    }

    public void setResTypeL2(String resTypeL2) {
        this.resTypeL2 = resTypeL2;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public Integer getFlagAvailable() {
        return flagAvailable;
    }

    public void setFlagAvailable(Integer flagAvailable) {
        this.flagAvailable = flagAvailable;
    }

    public Timestamp getSettingTime() {
        return settingTime;
    }

    public void setSettingTime(Timestamp settingTime) {
        this.settingTime = settingTime;
    }

    public String getSettingUserName() {
        return settingUserName;
    }

    public void setSettingUserName(String settingUserName) {
        this.settingUserName = settingUserName;
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

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResSpecialTypeL1() {
        return resSpecialTypeL1;
    }

    public void setResSpecialTypeL1(String resSpecialTypeL1) {
        this.resSpecialTypeL1 = resSpecialTypeL1;
    }

    public String getResSpecialTypeL2() {
        return resSpecialTypeL2;
    }

    public void setResSpecialTypeL2(String resSpecialTypeL2) {
        this.resSpecialTypeL2 = resSpecialTypeL2;
    }

}
