package com.baizhitong.syscode.vo;

import com.baizhitong.resource.common.vo.BaseVo;

/**
 * 
 * 全局code vo
 * 
 * @author creator jiayy 2016年1月14日 下午1:56:59
 * @author updater
 *
 * @version 1.0.0
 */
public class SysCodeVo extends BaseVo {
    /**
     * 字段名
     */
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
