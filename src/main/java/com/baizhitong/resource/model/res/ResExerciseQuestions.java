package com.baizhitong.resource.model.res;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lushunming 练习试题 实体类
 *
 */
@Entity
@Table(name = "res_exercise_questions")
public class ResExerciseQuestions implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 主键 */
    private @Id Integer       id;
    /** 学科编码 */
    private String            subjectCode;
    /** 练习 */
    private Integer           exerciseId;
    /** 所属练习结构 */
    private Integer           exerciseStructureId;
    /** 试题 */
    private Integer           questionId;
    /** 顺序号 */
    private Integer           orderInType;
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
    /** 答案不允许上传附件 */
    private Integer           flagNoAttachment;
    /** 答题只能上传图片 */
    private Integer           flagOnlyPhotos;
    /** 题目内可否乱序出题 */
    private Integer           canRandomInQuestion;

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

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Integer getExerciseStructureId() {
        return exerciseStructureId;
    }

    public void setExerciseStructureId(Integer exerciseStructureId) {
        this.exerciseStructureId = exerciseStructureId;
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

    public Integer getOrderInType() {
        return orderInType;
    }

    public void setOrderInType(Integer orderInType) {
        this.orderInType = orderInType;
    }

    public Integer getFlagNoAttachment() {
        return flagNoAttachment;
    }

    public void setFlagNoAttachment(Integer flagNoAttachment) {
        this.flagNoAttachment = flagNoAttachment;
    }

    public Integer getFlagOnlyPhotos() {
        return flagOnlyPhotos;
    }

    public void setFlagOnlyPhotos(Integer flagOnlyPhotos) {
        this.flagOnlyPhotos = flagOnlyPhotos;
    }

    public Integer getCanRandomInQuestion() {
        return canRandomInQuestion;
    }

    public void setCanRandomInQuestion(Integer canRandomInQuestion) {
        this.canRandomInQuestion = canRandomInQuestion;
    }

}
