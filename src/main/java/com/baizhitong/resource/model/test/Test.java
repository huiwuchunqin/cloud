package com.baizhitong.resource.model.test;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.baizhitong.resource.common.base.BaseEntity;

/**
 * 测验实体类 Test
 * 
 * @author creator gaow 2016年8月9日 下午3:37:11
 * @author updater 
 *
 * @version 1.0.0
 */
@Table(name = "exercise")
@SuppressWarnings("serial")
@Entity
public class Test extends BaseEntity implements Serializable {
    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer   id;
    /** 学段编码 */
    private String    sectionCode;
    /** 学科编码 */
    private String    subjectCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 学年学期代码 */
    private String    yearTermCode;
    /** 学年代码 */
    private String    studyYearCode;
    /** 学期代码 */
    private String    termCode;
    /** 资源编码 */
    private String    testCode;
    /** 资源名称 */
    private String    testName;
    /** 资源分类（一级） */
    private Integer   resTypeL1;
    /** 资源分类（二级） */
    private String    resTypeL2;
    /** 使用场景类型 */
    private Integer   scenarioType;
    /** 锁定状态 */
    private Integer   lockStatus;
    /** 资源描述 */
    private String    resDesc;
    /** 制作人类型 */
    private Integer   makerType;
    /** 制作人机构代码 */
    private String    makerOrgCode;
    /** 制作人机构名称 */
    private String    makerOrgName;
    /** 制作人代码 */
    private String    makerCode;
    /** 制作人姓名 */
    private String    makerName;
    /** 制作时间 */
    private Timestamp makeTime;
    /** 机构编码 */
    private String    orgCode;
    /** 用户代码 */
    private String    userCode;
    /** 用户姓名 */
    private String    userName;
    /** 作业状态 */
    private Integer   status;
    /** 待批阅份数 */
    private Integer   evaluatingCopies;
    /** 发布总份数 */
    private Integer   publishedCopies;
    /** 已提交总份数 */
    private Integer   submitedCopies;
    /** 最近发布时间 */
    private Timestamp lastPublishTime;
    /** 来源类型 */
    private Integer   sourceType;
    /** 来源说明 */
    private String    sourceDesc;
    /** 卷面总分 */
    private Float     score;
    /** 生成方式 */
    private Integer   generateType;
    /** 难易度 */
    private Integer   difficultyCode;
    /** 正确率 */
    private Float     correctRate;
    /** 原文档地址 */
    private String    originalDocAddress;
    /** 是否有效 */
    private Integer   flagValid;
    /** 分享级别 */
    private Integer   shareLevel;
    /** 分享审核中级别 */
    private Integer   shareCheckLevel;
    /** 分享审核中状态 */
    private Integer   shareCheckStatus;
    /** 是否允许浏览 */
    private Integer   flagAllowBrowse;
    /** 是否允许引用 */
    private Integer   flagAllowRefer;
    /** 是否允许下载 */
    private Integer   flagAllowDownload;
    /** 下载所需点数 */
    private Float     downloadPoints;
    /** 题目数 */
    private Integer   questionCount;
    /** 建议答题时间 */
    private Integer   answerMinutes;
    /** 题型内可否乱序出题 */
    private Integer   canRandomInType;
    /** 题目内可否乱序出题 */
    private Integer   canRandomInQuestion;
    /** 来源最初资源代码 */
    private String    topReferResCode;
    /** 来源最近资源代码 */
    private String    lastReferResCode;
    /** 媒体文件路径 */
    private String    mediaFilePath;
    /** 媒体文件播放方式 */
    private Integer   mediaPlayType;
    /** 开始播放媒体秒数 */
    private Integer   mediaPlayStartSeconds;
    /** 浏览总数 */
    private Integer   browseCount;
    /** 下载总数 */
    private Integer   downloadCount;
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
    /** 作答次数 */
    private Integer   answerCount;
    /** 解析资源 */
    private String    analyseResJson;
    /** 备注 */
    private String    memo;
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
    /** 更新者IP */
    private String    modifierIP;
    /** 业务版本号 */
    private Integer   bizVersion;
    /** 共享系统版本号 */
    private Integer   shareSysVer;
    /** 系统版本号 */
    private Integer   sysVersion;
    /** 提交审核时间 */
    private Timestamp shareCheckTime;
    /** 分享时间 */
    private Timestamp shareTime;

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

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getYearTermCode() {
        return yearTermCode;
    }

    public void setYearTermCode(String yearTermCode) {
        this.yearTermCode = yearTermCode;
    }

    public Timestamp getShareCheckTime() {
        return shareCheckTime;
    }

    public void setShareCheckTime(Timestamp shareCheckTime) {
        this.shareCheckTime = shareCheckTime;
    }

    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getResTypeL1() {
        return resTypeL1;
    }

    public void setResTypeL1(Integer resTypeL1) {
        this.resTypeL1 = resTypeL1;
    }

    public String getResTypeL2() {
        return resTypeL2;
    }

    public void setResTypeL2(String resTypeL2) {
        this.resTypeL2 = resTypeL2;
    }

    public Integer getScenarioType() {
        return scenarioType;
    }

    public void setScenarioType(Integer scenarioType) {
        this.scenarioType = scenarioType;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public Integer getMakerType() {
        return makerType;
    }

    public void setMakerType(Integer makerType) {
        this.makerType = makerType;
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

    public Timestamp getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Timestamp makeTime) {
        this.makeTime = makeTime;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getEvaluatingCopies() {
        return evaluatingCopies;
    }

    public void setEvaluatingCopies(Integer evaluatingCopies) {
        this.evaluatingCopies = evaluatingCopies;
    }

    public Integer getPublishedCopies() {
        return publishedCopies;
    }

    public void setPublishedCopies(Integer publishedCopies) {
        this.publishedCopies = publishedCopies;
    }

    public Integer getSubmitedCopies() {
        return submitedCopies;
    }

    public void setSubmitedCopies(Integer submitedCopies) {
        this.submitedCopies = submitedCopies;
    }

    public Timestamp getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(Timestamp lastPublishTime) {
        this.lastPublishTime = lastPublishTime;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceDesc() {
        return sourceDesc;
    }

    public void setSourceDesc(String sourceDesc) {
        this.sourceDesc = sourceDesc;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getGenerateType() {
        return generateType;
    }

    public void setGenerateType(Integer generateType) {
        this.generateType = generateType;
    }

    public Integer getDifficultyCode() {
        return difficultyCode;
    }

    public void setDifficultyCode(Integer difficultyCode) {
        this.difficultyCode = difficultyCode;
    }

    public Float getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(Float correctRate) {
        this.correctRate = correctRate;
    }

    public String getOriginalDocAddress() {
        return originalDocAddress;
    }

    public void setOriginalDocAddress(String originalDocAddress) {
        this.originalDocAddress = originalDocAddress;
    }

    public Integer getFlagValid() {
        return flagValid;
    }

    public void setFlagValid(Integer flagValid) {
        this.flagValid = flagValid;
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

    public Integer getFlagAllowBrowse() {
        return flagAllowBrowse;
    }

    public void setFlagAllowBrowse(Integer flagAllowBrowse) {
        this.flagAllowBrowse = flagAllowBrowse;
    }

    public Integer getFlagAllowRefer() {
        return flagAllowRefer;
    }

    public void setFlagAllowRefer(Integer flagAllowRefer) {
        this.flagAllowRefer = flagAllowRefer;
    }

    public Integer getFlagAllowDownload() {
        return flagAllowDownload;
    }

    public void setFlagAllowDownload(Integer flagAllowDownload) {
        this.flagAllowDownload = flagAllowDownload;
    }

    public Float getDownloadPoints() {
        return downloadPoints;
    }

    public void setDownloadPoints(Float downloadPoints) {
        this.downloadPoints = downloadPoints;
    }

    public Integer getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Integer questionCount) {
        this.questionCount = questionCount;
    }

    public Integer getAnswerMinutes() {
        return answerMinutes;
    }

    public void setAnswerMinutes(Integer answerMinutes) {
        this.answerMinutes = answerMinutes;
    }

    public Integer getCanRandomInType() {
        return canRandomInType;
    }

    public void setCanRandomInType(Integer canRandomInType) {
        this.canRandomInType = canRandomInType;
    }

    public Integer getCanRandomInQuestion() {
        return canRandomInQuestion;
    }

    public void setCanRandomInQuestion(Integer canRandomInQuestion) {
        this.canRandomInQuestion = canRandomInQuestion;
    }

    public String getTopReferResCode() {
        return topReferResCode;
    }

    public void setTopReferResCode(String topReferResCode) {
        this.topReferResCode = topReferResCode;
    }

    public String getLastReferResCode() {
        return lastReferResCode;
    }

    public void setLastReferResCode(String lastReferResCode) {
        this.lastReferResCode = lastReferResCode;
    }

    public String getMediaFilePath() {
        return mediaFilePath;
    }

    public void setMediaFilePath(String mediaFilePath) {
        this.mediaFilePath = mediaFilePath;
    }

    public Integer getMediaPlayType() {
        return mediaPlayType;
    }

    public void setMediaPlayType(Integer mediaPlayType) {
        this.mediaPlayType = mediaPlayType;
    }

    public Integer getMediaPlayStartSeconds() {
        return mediaPlayStartSeconds;
    }

    public void setMediaPlayStartSeconds(Integer mediaPlayStartSeconds) {
        this.mediaPlayStartSeconds = mediaPlayStartSeconds;
    }

    public Integer getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(Integer browseCount) {
        this.browseCount = browseCount;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
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

    public String getAnalyseResJson() {
        return analyseResJson;
    }

    public void setAnalyseResJson(String analyseResJson) {
        this.analyseResJson = analyseResJson;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

}
