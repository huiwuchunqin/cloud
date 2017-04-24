package com.baizhitong.resource.model.rel;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源与教材章节关系表
 * 
 * @author shancy 2015/12/02
 */
@Entity
@Table(name = "rel_res_tbc")
public class RelResTbc implements Serializable {
    private static final long                                          serialVersionUID = 1L;

    /** 主键 */
    private @Id @Column(insertable = false,
                        updatable = false) Integer                     id;
    /** 资源分类（一级） */
    private Integer                                                    resTypeL1;
    /** 资源ID */
    private Integer                                                    resId;
    /** 资源编码 */
    private String                                                     resCode;
    /** 教材章节编码 */
    private String                                                     chapterCode;
    /** 更新者 */
    private String                                                     modifier;
    /** 更新时间 */
    private Timestamp                                                  modifyTime;
    /** 更新者IP */
    private String                                                     modifierIP;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResTypeL1() {
        return resTypeL1;
    }

    public void setResTypeL1(Integer resTypeL1) {
        this.resTypeL1 = resTypeL1;
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

    public String getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(String chapterCode) {
        this.chapterCode = chapterCode;
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

    public RelResTbc() {
    }

    public RelResTbc(Integer id,
                     Integer resTypeL1,
                     Integer resId,
                     String resCode,
                     String tbcCode,
                     String modifier,
                     Timestamp modifyTime,
                     String modifierIP) {
        super();
        this.id = id;
        this.resTypeL1 = resTypeL1;
        this.resId = resId;
        this.resCode = resCode;
        this.chapterCode = tbcCode;
        this.modifier = modifier;
        this.modifyTime = modifyTime;
        this.modifierIP = modifierIP;
    }

}
