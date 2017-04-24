package com.baizhitong.syscode.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baizhitong.resource.common.base.BaseEntity;

/***
 * 
 * 全局Code
 * 
 * @author creator jiayy 2016年1月14日 下午1:54:19
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "sys_code")
@SuppressWarnings("serial")
public class SysCodeEntity extends BaseEntity implements Serializable {

    /**
     * 字段名
     */
    @Id
    private String  fieldName;
    /**
     * 字段名描述
     */
    private String  codeName;
    /**
     * 字段类型
     */
    private String  type;
    /**
     * 长度
     */
    private Integer len;
    /**
     * 描述
     */
    private String  explain;
    /**
     * 公式
     */
    private String  formula;
    /**
     * 流水号
     */
    private Integer swiftNumber;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public Integer getSwiftNumber() {
        return swiftNumber;
    }

    public void setSwiftNumber(Integer swiftNumber) {
        this.swiftNumber = swiftNumber;
    }

}
