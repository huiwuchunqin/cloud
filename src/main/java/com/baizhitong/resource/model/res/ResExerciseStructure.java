package com.baizhitong.resource.model.res;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lusm 练习结构实体类
 */

@Entity
@Table(name = "res_exercise_structure")
public class ResExerciseStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    private @Id Integer       id;
    /** 练习Id */
    private Integer           exerciseId;
    /** 本题型顺序号 */
    private Integer           orderInExercise;
    /** 学科题型编码 */
    private String            questionTypeSubjectCode;
    /** 题型总分 */
    private Double            typeScore;
    /** 题型内可否随机出题 */
    private Integer           canRandomInType;
    /** 题目内可否乱序出题 */
    private Integer           canRandomInQuestion;
    /** 题型小题数量 */
    private Integer           typeNumber;
    /** 题型说明 */
    private String            typeDesc;
    /** 题型排序 */
    private Integer           typeSort;
    /** 所属板块（不需要了吧？） */
    private String            plate;
    /** 难度系数描述 */
    private String            difficultyDescription;
    /** 答案不允许上传附件 */
    private Integer           flagNoAttachment;
    /** 答题只能上传图片 */
    private Integer           flagOnlyPhotos;
    /** 答题防粘贴 */
    private Integer           flagPreventPaste;
    /** 最低字数限 */
    private Integer           minChars;
    /** 音视频播放次数 */
    private Integer           audioPlayTimes;
    /** 音视频播放间隔 */
    private Integer           audioPlayInterval;
    /** 音视频播放方式 */
    private Integer           audioPlayType;
    /** 音频播放单词间的间隔时间 */
    private Integer           audioPlayWordInterval;
    /** 小题是连续序号还是独立序号 */
    private Integer           flagOrderSeriesOrIndep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getQuestionTypeSubjectCode() {
        return questionTypeSubjectCode;
    }

    public void setQuestionTypeSubjectCode(String questionTypeSubjectCode) {
        this.questionTypeSubjectCode = questionTypeSubjectCode;
    }

    public Double getTypeScore() {
        return typeScore;
    }

    public void setTypeScore(Double typeScore) {
        this.typeScore = typeScore;
    }

    public Integer getcanRandomInType() {
        return canRandomInType;
    }

    public void setcanRandomInType(Integer canRandomInType) {
        this.canRandomInType = canRandomInType;
    }

    public Integer getTypeNumber() {
        return typeNumber;
    }

    public void setTypeNumber(Integer typeNumber) {
        this.typeNumber = typeNumber;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Integer getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(Integer typeSort) {
        this.typeSort = typeSort;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getDifficultyDescription() {
        return difficultyDescription;
    }

    public void setDifficultyDescription(String difficultyDescription) {
        this.difficultyDescription = difficultyDescription;
    }

    public Integer getFlagOnlyPhotos() {
        return flagOnlyPhotos;
    }

    public void setFlagOnlyPhotos(Integer flagOnlyPhotos) {
        this.flagOnlyPhotos = flagOnlyPhotos;
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

    public Integer getAudioPlayTimes() {
        return audioPlayTimes;
    }

    public void setAudioPlayTimes(Integer audioPlayTimes) {
        this.audioPlayTimes = audioPlayTimes;
    }

    public Integer getAudioPlayInterval() {
        return audioPlayInterval;
    }

    public void setAudioPlayInterval(Integer audioPlayInterval) {
        this.audioPlayInterval = audioPlayInterval;
    }

    public Integer getAudioPlayType() {
        return audioPlayType;
    }

    public void setAudioPlayType(Integer audioPlayType) {
        this.audioPlayType = audioPlayType;
    }

    public Integer getAudioPlayWordInterval() {
        return audioPlayWordInterval;
    }

    public void setAudioPlayWordInterval(Integer audioPlayWordInterval) {
        this.audioPlayWordInterval = audioPlayWordInterval;
    }

    public Integer getFlagOrderSeriesOrIndep() {
        return flagOrderSeriesOrIndep;
    }

    public void setFlagOrderSeriesOrIndep(Integer flagOrderSeriesOrIndep) {
        this.flagOrderSeriesOrIndep = flagOrderSeriesOrIndep;
    }

    public Integer getOrderInExercise() {
        return orderInExercise;
    }

    public void setOrderInExercise(Integer orderInExercise) {
        this.orderInExercise = orderInExercise;
    }

    public Integer getCanRandomInQuestion() {
        return canRandomInQuestion;
    }

    public void setCanRandomInQuestion(Integer canRandomInQuestion) {
        this.canRandomInQuestion = canRandomInQuestion;
    }

    public Integer getFlagNoAttachment() {
        return flagNoAttachment;
    }

    public void setFlagNoAttachment(Integer flagNoAttachment) {
        this.flagNoAttachment = flagNoAttachment;
    }

}
