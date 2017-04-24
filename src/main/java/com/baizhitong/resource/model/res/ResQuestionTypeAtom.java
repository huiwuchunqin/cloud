package com.baizhitong.resource.model.res;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lushunming 题目_原子题型实体类
 *
 */
@Entity
@Table(name = "res_question_type_atom")
public class ResQuestionTypeAtom implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 类型代码 */
    private @Id String        typeCode;
    /** 类型名称 */
    private String            typeName;
    /** 可用状态 */
    private Integer           validStatus;
    /** 是否为客观题型 */
    private Integer           flagObjective;
    /** 备注 */
    private String            memo;

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Integer validStatus) {
        this.validStatus = validStatus;
    }

    public Integer getFlagObjective() {
        return flagObjective;
    }

    public void setFlagObjective(Integer flagObjective) {
        this.flagObjective = flagObjective;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
