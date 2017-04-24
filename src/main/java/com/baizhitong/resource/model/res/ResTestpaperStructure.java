package com.baizhitong.resource.model.res;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lusm 试卷结构实体类
 */

@Entity
@Table(name = "res_testpaper_structure")
public class ResTestpaperStructure implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 主键 */
    private @Id Integer       id;

    /** 试卷 */
    private Integer           testPaperId;
    /** 学科题型编码 */
    private String            questionTypeSub;
    /** 题型总分 */
    private Double            typeScore;
    /** 题型内可否随机出题 */
    private Integer           canRandom;
    /** 题型小题数量 */
    private Integer           typeNumber;
    /** 题型说明 */
    private String            typeDesc;
    /** 题型排序 */
    private Integer           typeSort;
    /** 所属板块（ 不需要了吧？） */
    private String            plate;
    /** 难度系数描述 */
    private String            difficultyDes;
    /** 答题只能上传图片 */
    private Integer           lagOnlyPhot;
    /** 答题防粘贴 */
    private Integer           flagPreventP;
    /** 最低字数限制 */
    private Integer           minChars;
    /** 音视频播放次数 */
    private Integer           audioPlayTim;
    /** 音视频播放间隔 */
    private Integer           audioPlayInt;
    /** 音视频播放方式 */
    private Integer           audioPlayTyp;
    /** 音频播放单词间的间隔时间 */
    private Integer           audioPlayWord;
    /** 小题是连续序号还是独立序号 */
    private Integer           flagOrderSerie;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestPaperId() {
        return testPaperId;
    }

    public void setTestPaperId(Integer testPaperId) {
        this.testPaperId = testPaperId;
    }

    public String getQuestionTypeSub() {
        return questionTypeSub;
    }

    public void setQuestionTypeSub(String questionTypeSub) {
        this.questionTypeSub = questionTypeSub;
    }

    public Double getTypeScore() {
        return typeScore;
    }

    public void setTypeScore(Double typeScore) {
        this.typeScore = typeScore;
    }

    public Integer getCanRandom() {
        return canRandom;
    }

    public void setCanRandom(Integer canRandom) {
        this.canRandom = canRandom;
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

    public String getDifficultyDes() {
        return difficultyDes;
    }

    public void setDifficultyDes(String difficultyDes) {
        this.difficultyDes = difficultyDes;
    }

    public Integer getLagOnlyPhot() {
        return lagOnlyPhot;
    }

    public void setLagOnlyPhot(Integer lagOnlyPhot) {
        this.lagOnlyPhot = lagOnlyPhot;
    }

    public Integer getFlagPreventP() {
        return flagPreventP;
    }

    public void setFlagPreventP(Integer flagPreventP) {
        this.flagPreventP = flagPreventP;
    }

    public Integer getMinChars() {
        return minChars;
    }

    public void setMinChars(Integer minChars) {
        this.minChars = minChars;
    }

    public Integer getAudioPlayTim() {
        return audioPlayTim;
    }

    public void setAudioPlayTim(Integer audioPlayTim) {
        this.audioPlayTim = audioPlayTim;
    }

    public Integer getAudioPlayInt() {
        return audioPlayInt;
    }

    public void setAudioPlayInt(Integer audioPlayInt) {
        this.audioPlayInt = audioPlayInt;
    }

    public Integer getAudioPlayTyp() {
        return audioPlayTyp;
    }

    public void setAudioPlayTyp(Integer audioPlayTyp) {
        this.audioPlayTyp = audioPlayTyp;
    }

    public Integer getAudioPlayWord() {
        return audioPlayWord;
    }

    public void setAudioPlayWord(Integer audioPlayWord) {
        this.audioPlayWord = audioPlayWord;
    }

    public Integer getFlagOrderSerie() {
        return flagOrderSerie;
    }

    public void setFlagOrderSerie(Integer flagOrderSerie) {
        this.flagOrderSerie = flagOrderSerie;
    }

}
