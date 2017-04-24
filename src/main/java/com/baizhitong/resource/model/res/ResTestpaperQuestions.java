package com.baizhitong.resource.model.res;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lushunming 试卷试题 实体类
 *
 */
@Entity
@Table(name = "res_testpaper_questions")
public class ResTestpaperQuestions implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    private @Id Integer       id;
    /** 学科编码 */
    private String            subjectCode;
    /** 试卷 */
    private Integer           testPaperId;
    /** 所属试卷结构 */
    private Integer           testPaperStructureId;
    /** 试题 */
    private Integer           questionId;
    /** 试题排序 */
    private Integer           sort;
    /** 答案防粘贴 */
    private Integer           flagPreventPaste;
    /** 最低字数限制 */
    private Integer           minChars;
    /** 分数 */
    private Float             score;
    /** 试题来源 */
    private Integer           source;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(Integer testPaperId) {
        this.testPaperId = testPaperId;
    }

    public Integer getTestPaperStructureId() {
        return testPaperStructureId;
    }

    public void setTestPaperStructureId(Integer testPaperStructureId) {
        this.testPaperStructureId = testPaperStructureId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getFlagPreventPaste() {
        return flagPreventPaste;
    }

    public void setFlagPreventPaste(Integer flagPreventPaste) {
        this.flagPreventPaste = flagPreventPaste;
    }

    public Integer getMinChars() {
        return minChars;
    }

    public void setMinChars(Integer minChars) {
        this.minChars = minChars;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

}
