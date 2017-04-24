package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lushunming 题目_大文本实体类
 */
@Entity
@Table(name = "res_question_big_text")
public class ResQuestionBigText implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    private @Id Integer       id;
    /** 学科全局分类编码 */

    private String            subjectGlobalCode;
    /** 机构CD */
    private String            orgCode;

    /** 课程编码 */
    private String            courseCode;

    /** 试题文本类型 */
    private Integer           textType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectGlobalCode() {
        return subjectGlobalCode;
    }

    public void setSubjectGlobalCode(String subjectGlobalCode) {
        this.subjectGlobalCode = subjectGlobalCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /** 文本内容 */
    private String content;
}
