package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author lushunming 资源_题目实体类
 */
@Entity
@Table(name = "res_question")
public class ResQuestion implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /** 主键 */
    private @Id Integer       id;
    /** 题目编码 */
    private String            questionCode;
    /** 题目题型 */
    private String            typeCode;
    /** 题目题型（原子题型） */
    private String            typeAtomCode;
    /** 题目前置文本 */
    private Integer           preTextID;
    /** 题目内容（题干） */
    private Integer           bodyTextID;
    /** 题目选项 */
    private Integer           optionTextID;
    /** 难易度 */
    private Integer           difficultDegree;
    /** 正确率 */
    private Float             correctRate;
    /** 可组间乱序出题 */
    private Integer           canRandom;
    /** 分组顺序号 */
    private Integer           groupOrder;
    /** 分组说明 */
    private String            groupDesc;
    /** 分组内顺序号 */
    private Integer           orderInGroup;
    /** 可组内乱序出题 */
    private Integer           canRandomGroup;
    /** 可组内独立出题 */
    private Integer           canSeperate;
    /** 建议分值 */
    private Float             score;
    /** 有效状态 */
    private Integer           flagPublic;
    /** 预计答题时间（秒） */
    private Float             estimateAnswerSeconds;
    /** 使用次数 */
    private Integer           usedCount;
    /** 答题次数 */
    private Integer           answerCount;
    /** 客观填空答案共享 */
    private Integer           flagAnswerShare;
    /** 填空数量 */
    private Integer           blankNum;
    /** 答案文本 */
    private Integer           answerTextId;
    /** 解析文本 */
    private Integer           analysisTextId;
    /** 媒体文件路径 */
    private String            mediaObjectId;
    /** 媒体文件开始时间 */
    private Float             mediaStartTime;
    /** 媒体文件结束时间 */
    private Float             mediaEndTime;
    /** 支持的显示引擎 */
    private String            showableEngine;
    /** 创建者 */
    private String            creator;
    /** 创建时间 */
    private Date              createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeAtomCode() {
        return typeAtomCode;
    }

    public void setTypeAtomCode(String typeAtomCode) {
        this.typeAtomCode = typeAtomCode;
    }

    public Integer getPreTextID() {
        return preTextID;
    }

    public void setPreTextID(Integer preTextID) {
        this.preTextID = preTextID;
    }

    public Integer getBodyTextID() {
        return bodyTextID;
    }

    public void setBodyTextID(Integer bodyTextID) {
        this.bodyTextID = bodyTextID;
    }

    public Integer getOptionTextID() {
        return optionTextID;
    }

    public void setOptionTextID(Integer optionTextID) {
        this.optionTextID = optionTextID;
    }

    public Integer getDifficultDegree() {
        return difficultDegree;
    }

    public void setDifficultDegree(Integer difficultDegree) {
        this.difficultDegree = difficultDegree;
    }

    public Float getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(Float correctRate) {
        this.correctRate = correctRate;
    }

    public Integer getCanRandom() {
        return canRandom;
    }

    public void setCanRandom(Integer canRandom) {
        this.canRandom = canRandom;
    }

    public Integer getGroupOrder() {
        return groupOrder;
    }

    public void setGroupOrder(Integer groupOrder) {
        this.groupOrder = groupOrder;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public Integer getOrderInGroup() {
        return orderInGroup;
    }

    public void setOrderInGroup(Integer orderInGroup) {
        this.orderInGroup = orderInGroup;
    }

    public Integer getCanRandomGroup() {
        return canRandomGroup;
    }

    public void setCanRandomGroup(Integer canRandomGroup) {
        this.canRandomGroup = canRandomGroup;
    }

    public Integer getCanSeperate() {
        return canSeperate;
    }

    public void setCanSeperate(Integer canSeperate) {
        this.canSeperate = canSeperate;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getFlagPublic() {
        return flagPublic;
    }

    public void setFlagPublic(Integer flagPublic) {
        this.flagPublic = flagPublic;
    }

    public Float getEstimateAnswerSeconds() {
        return estimateAnswerSeconds;
    }

    public void setEstimateAnswerSeconds(Float estimateAnswerSeconds) {
        this.estimateAnswerSeconds = estimateAnswerSeconds;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getFlagAnswerShare() {
        return flagAnswerShare;
    }

    public void setFlagAnswerShare(Integer flagAnswerShare) {
        this.flagAnswerShare = flagAnswerShare;
    }

    public Integer getBlankNum() {
        return blankNum;
    }

    public void setBlankNum(Integer blankNum) {
        this.blankNum = blankNum;
    }

    public Integer getAnswerTextId() {
        return answerTextId;
    }

    public void setAnswerTextId(Integer answerTextId) {
        this.answerTextId = answerTextId;
    }

    public Integer getAnalysisTextId() {
        return analysisTextId;
    }

    public void setAnalysisTextId(Integer analysisTextId) {
        this.analysisTextId = analysisTextId;
    }

    public String getMediaObjectId() {
        return mediaObjectId;
    }

    public void setMediaObjectId(String mediaObjectId) {
        this.mediaObjectId = mediaObjectId;
    }

    public Float getMediaStartTime() {
        return mediaStartTime;
    }

    public void setMediaStartTime(Float mediaStartTime) {
        this.mediaStartTime = mediaStartTime;
    }

    public Float getMediaEndTime() {
        return mediaEndTime;
    }

    public void setMediaEndTime(Float mediaEndTime) {
        this.mediaEndTime = mediaEndTime;
    }

    public String getShowableEngine() {
        return showableEngine;
    }

    public void setShowableEngine(String showableEngine) {
        this.showableEngine = showableEngine;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
