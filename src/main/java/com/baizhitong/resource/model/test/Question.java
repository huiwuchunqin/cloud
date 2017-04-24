package com.baizhitong.resource.model.test;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baizhitong.resource.common.base.BaseEntity;

/**
 * 题目实体类 Question TODO
 * 
 * @author creator gaow 2016年8月9日 下午3:37:26
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "question")
@SuppressWarnings("serial")
public class Question extends BaseEntity implements Serializable {
    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    /** 主键 */
    private Integer   id;
    /** 学科编码 */
    private String    subjectCode;
    /** 题目编码 */
    private String    questionCode;
    /** 题目业务编码 */
    private String    questionBizCode;
    /** 题型编码 */
    private String    questionType;
    /** 题目内容ID */
    private Integer   contentId;
    /** 带答案题目内容ID */
    private Integer   contentWithAnswerId;
    /** 难易度 */
    private Integer   difficultyCode;
    /** 试题初始来源 */
    private Integer   questionSource;
    /** 正确率 */
    private Float     correctRate;
    /** 区分度 */
    private Float     discrimination;
    /** 建议分值 */
    private Float     score;
    /** 预计答题时间（秒） */
    private Float     estimateAnswerSeconds;
    /** 支持的显示引擎 */
    private String    showableEngineCode;
    /** 继承关系 - 原题目编码 */
    private String    originalQuestionCode;
    /** 继承关系 - 最近题目编码 */
    private String    lastQuestionCode;
    /** 题目状态 */
    private Integer   status;
    /** 分享级别 */
    private Integer   shareLevel;
    /** 分享审核中级别 */
    private Integer   shareCheckLevel;
    /** 分享审核中状态 */
    private Integer   shareCheckStatus;
    /** 制作时间 */
    private Timestamp makeTime;
    /** 制作人机构代码 */
    private String    makerOrgCode;
    /** 制作人机构名称 */
    private String    makerOrgName;
    /** 制作人代码 */
    private String    makerCode;
    /** 制作人姓名 */
    private String    makerName;
    /** 下载总数 */
    private Integer   downloadCount;
    /** 浏览总数 */
    private Integer   browseCount;
    /** 引用总数 */
    private Integer   referCount;
    /** 收藏总数 */
    private Integer   favoriteCount;
    /** 点赞总数 */
    private Integer   goodCount;
    /** 点踩总数 */
    private Integer   badCount;
    /** 评论总数 */
    private Integer   commentCount;
    /** 答题次数 */
    private Integer   answerCount;
    /** 锁定状态 */
    private Integer   lockStatus;
    /** 是否删除 */
    private Integer   flagDelete;
    /** 创建者 */
    private String    creator;
    /** 创建时间 */
    private Timestamp createTime;
    /** 创建者IP */
    private String    creatorIP;
    /** 更新者 */
    private String    modifier;
    /** 更新时间 */
    private Timestamp modifyTime;
    /** 更新程序ip */
    private String    modifierIP;
    /** 业务版本号 */
    private Integer   bizVersion;
    /** 共享系统版本号 */
    private Integer   shareSysVer;
    /** 系统版本号 */
    private Integer   sysVersion;
    /** 学科题型 */
    private String    questionTypeSubject;
    /** 自动批阅 */
    private Integer   flagSysEvaluate;
    /** 机构学科题型编码 */
    private String    questionTypeSubjectOrg;
    /** 分享审核时间 */
    private Timestamp shareCheckTime;
    /** 分享时间 */
    private Timestamp shareTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getShareTime() {
        return shareTime;
    }

    public void setShareTime(Timestamp shareTime) {
        this.shareTime = shareTime;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Timestamp getShareCheckTime() {
        return shareCheckTime;
    }

    public void setShareCheckTime(Timestamp shareCheckTime) {
        this.shareCheckTime = shareCheckTime;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionBizCode() {
        return questionBizCode;
    }

    public void setQuestionBizCode(String questionBizCode) {
        this.questionBizCode = questionBizCode;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public Integer getContentWithAnswerId() {
        return contentWithAnswerId;
    }

    public void setContentWithAnswerId(Integer contentWithAnswerId) {
        this.contentWithAnswerId = contentWithAnswerId;
    }

    public Integer getDifficultyCode() {
        return difficultyCode;
    }

    public void setDifficultyCode(Integer difficultyCode) {
        this.difficultyCode = difficultyCode;
    }

    public Integer getQuestionSource() {
        return questionSource;
    }

    public void setQuestionSource(Integer questionSource) {
        this.questionSource = questionSource;
    }

    public Float getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(Float correctRate) {
        this.correctRate = correctRate;
    }

    public Float getDiscrimination() {
        return discrimination;
    }

    public void setDiscrimination(Float discrimination) {
        this.discrimination = discrimination;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getEstimateAnswerSeconds() {
        return estimateAnswerSeconds;
    }

    public void setEstimateAnswerSeconds(Float estimateAnswerSeconds) {
        this.estimateAnswerSeconds = estimateAnswerSeconds;
    }

    public String getShowableEngineCode() {
        return showableEngineCode;
    }

    public void setShowableEngineCode(String showableEngineCode) {
        this.showableEngineCode = showableEngineCode;
    }

    public String getOriginalQuestionCode() {
        return originalQuestionCode;
    }

    public void setOriginalQuestionCode(String originalQuestionCode) {
        this.originalQuestionCode = originalQuestionCode;
    }

    public String getLastQuestionCode() {
        return lastQuestionCode;
    }

    public void setLastQuestionCode(String lastQuestionCode) {
        this.lastQuestionCode = lastQuestionCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShareLevel() {
        return shareLevel;
    }

    public void setShareLevel(Integer shareLevel) {
        this.shareLevel = shareLevel;
    }

    public Integer getShareCheckLevel() {
        return shareCheckLevel;
    }

    public void setShareCheckLevel(Integer shareCheckLevel) {
        this.shareCheckLevel = shareCheckLevel;
    }

    public Integer getShareCheckStatus() {
        return shareCheckStatus;
    }

    public void setShareCheckStatus(Integer shareCheckStatus) {
        this.shareCheckStatus = shareCheckStatus;
    }

    public Timestamp getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Timestamp makeTime) {
        this.makeTime = makeTime;
    }

    public String getMakerOrgCode() {
        return makerOrgCode;
    }

    public void setMakerOrgCode(String makerOrgCode) {
        this.makerOrgCode = makerOrgCode;
    }

    public String getMakerOrgName() {
        return makerOrgName;
    }

    public void setMakerOrgName(String makerOrgName) {
        this.makerOrgName = makerOrgName;
    }

    public String getMakerCode() {
        return makerCode;
    }

    public void setMakerCode(String makerCode) {
        this.makerCode = makerCode;
    }

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public Integer getReferCount() {
        return referCount;
    }

    public void setReferCount(Integer referCount) {
        this.referCount = referCount;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getBadCount() {
        return badCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreatorIP() {
        return creatorIP;
    }

    public void setCreatorIP(String creatorIP) {
        this.creatorIP = creatorIP;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifierIP() {
        return modifierIP;
    }

    public void setModifierIP(String modifierIP) {
        this.modifierIP = modifierIP;
    }

    public Integer getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(Integer bizVersion) {
        this.bizVersion = bizVersion;
    }

    public Integer getShareSysVer() {
        return shareSysVer;
    }

    public void setShareSysVer(Integer shareSysVer) {
        this.shareSysVer = shareSysVer;
    }

    public Integer getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(Integer sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getQuestionTypeSubject() {
        return questionTypeSubject;
    }

    public void setQuestionTypeSubject(String questionTypeSubject) {
        this.questionTypeSubject = questionTypeSubject;
    }

    public Integer getFlagSysEvaluate() {
        return flagSysEvaluate;
    }

    public void setFlagSysEvaluate(Integer flagSysEvaluate) {
        this.flagSysEvaluate = flagSysEvaluate;
    }

    public String getQuestionTypeSubjectOrg() {
        return questionTypeSubjectOrg;
    }

    public void setQuestionTypeSubjectOrg(String questionTypeSubjectOrg) {
        this.questionTypeSubjectOrg = questionTypeSubjectOrg;
    }
}
