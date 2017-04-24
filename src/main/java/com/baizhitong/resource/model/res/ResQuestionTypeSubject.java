package com.baizhitong.resource.model.res;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lushunming 题目_学科题型实体类
 *
 */
@Entity
@Table(name = "res_question_type_subject")
public class ResQuestionTypeSubject implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 类型代码 */
    private @Id Integer       id;
    /** 学段编码 */
    private String            sectionCode;
    /** 学科编码 */
    private String            subjectCode;
    /** 基础题型编码 */
    private String            questionTypeCode;
    /** 学科题型编码 */
    private String            code;
    /** 学科题型名称 */
    private String            name;
    /** 是否为客观题型 */
    private Integer           flagObjective;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getQuestionTypeCode() {
        return questionTypeCode;
    }

    public void setQuestionTypeCode(String questionTypeCode) {
        this.questionTypeCode = questionTypeCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFlagObjective() {
        return flagObjective;
    }

    public void setFlagObjective(Integer flagObjective) {
        this.flagObjective = flagObjective;
    }

}
