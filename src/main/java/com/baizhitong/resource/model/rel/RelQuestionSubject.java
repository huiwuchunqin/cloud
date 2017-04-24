package com.baizhitong.resource.model.rel;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 试题与学科关系表
 * 
 * @author shancy 2015/12/02
 */
@Entity
@Table(name = "rel_question_subject")
public class RelQuestionSubject implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private @Id Integer       id;
    /** 资源ID */
    private Integer           resId;
    /** 资源编码 */
    private String            resCode;
    /** 学科编码 */
    private String            subjectCode;
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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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
