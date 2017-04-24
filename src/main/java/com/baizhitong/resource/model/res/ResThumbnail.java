package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源缩略图 ResThumbnail
 * 
 * @author creator gaow 2016年9月7日 下午3:17:54
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "res_thumbnail")
@Entity
public class ResThumbnail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(updatable = false,
            insertable = false)
    /** id */
    private Integer           id;
    /** 资源id */
    private Integer           resId;
    /** 资源编码 */
    private String            resCode;
    /** 资源类型 */
    private Integer           resTypeL1;
    /** 路径 */
    private String            path;
    /** 来源类型 */
    private Integer           sourceType;
    /** 拍寻 */
    private Integer           dispOrder;
    /** 修改者 */
    private String            modifier;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改ip */
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
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

    public String getModifierIP() {
        return modifierIP;
    }

    public void setModifierIP(String modifierIP) {
        this.modifierIP = modifierIP;
    }

}
