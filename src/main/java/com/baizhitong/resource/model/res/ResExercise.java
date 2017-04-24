package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author lusm 资源_练习实体类
 */

@Entity
@Table(name = "res_exercise")
public class ResExercise implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    private @Id Integer       id;
    /** 资源编码 */
    private String            resCode;
    /** 资源名称 */
    private String            resName;
    /** 资源分类（一级） */
    private Integer           resTypeL1;
    /** 资源分类（二级） */
    private String            resTypeL2;
    /** 资源描述 */
    private String            resDesc;
    /** 总分 */
    private Integer           score;
    /** 生成方式 */
    private Integer           generateType;
    /** 难易度 */
    private Integer           difficultDegree;
    /** 正确率 */
    private Float             correctRate;
    /** 结构ID */
    private Integer           structureID;
    /** 原文档地址（？？？） */
    private String            originalDocAddress;
    /** 是否生成完毕 */
    private Integer           flagBuildFinished;
    /** 是否删除 */
    private Integer           flagDelete;
    /** 审核状态 */
    private Integer           checkStatus;
    /** 来源编码（？？？需要吗） */
    private String            sourceType;
    /** 来源名称 */
    private String            sourceName;
    /** 是否是联盟资源标识 */
    private Integer           flagUnionRes;
    /** 上传时间 */
    private Date              uploadTime;
    /** 分享级别 */
    private Integer           shareLevel;
    /** 适用学科编码 */
    private String            subjectCode;
    /** 是否允许下载 */
    private Integer           flagAllowDownload;
    /** 是否允许浏览 */
    private Integer           flagAllowBrowse;
    /** 建议答题时间 */
    private Integer           answerMinutes;
    /** 浏览次数 */
    private Integer           browseCount;
    /** 下载次数 */
    private Integer           downloadCount;
    /** 点赞总数 */
    private Integer           goodCount;
    /** 评论总数 */
    private Integer           commentCount;
    /** 收藏总数 */
    private Integer           favoriteCount;
    /** 引用总数 */
    private Integer           referCount;
    /** 点踩总数 */
    private Integer           badCount;

    /** 备注 memo */
    private String            memo;
    /** 创建者 */
    private String            creator;
    /** 创建时间 */
    private Date              createTime;
    /** 创建者I */
    private String            creatorIP;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Date              modifyTime;
    /** 更新者I */
    private String            modifierIP;
    /** 系统处理版本号 */
    private long              sysVersion;
    /** 业务处理版本号 */
    private long              bizVersion;
    /** 下载所需点数 */
    private Float             downloadPoints;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResTypeL2() {
        return resTypeL2;
    }

    public void setResTypeL2(String resTypeL2) {
        this.resTypeL2 = resTypeL2;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getGenerateType() {
        return generateType;
    }

    public void setGenerateType(Integer generateType) {
        this.generateType = generateType;
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

    public Integer getStructureID() {
        return structureID;
    }

    public void setStructureID(Integer structureID) {
        this.structureID = structureID;
    }

    public String getOriginalDocAddress() {
        return originalDocAddress;
    }

    public void setOriginalDocAddress(String originalDocAddress) {
        this.originalDocAddress = originalDocAddress;
    }

    public Integer getFlagBuildFinished() {
        return flagBuildFinished;
    }

    public void setFlagBuildFinished(Integer flagBuildFinished) {
        this.flagBuildFinished = flagBuildFinished;
    }

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
    }

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public Integer getFlagUnionRes() {
        return flagUnionRes;
    }

    public void setFlagUnionRes(Integer flagUnionRes) {
        this.flagUnionRes = flagUnionRes;
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getShareLevel() {
        return shareLevel;
    }

    public void setShareLevel(Integer shareLevel) {
        this.shareLevel = shareLevel;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getFlagAllowDownload() {
        return flagAllowDownload;
    }

    public void setFlagAllowDownload(Integer flagAllowDownload) {
        this.flagAllowDownload = flagAllowDownload;
    }

    public Integer getFlagAllowBrowse() {
        return flagAllowBrowse;
    }

    public void setFlagAllowBrowse(Integer flagAllowBrowse) {
        this.flagAllowBrowse = flagAllowBrowse;
    }

    public Integer getAnswerMinutes() {
        return answerMinutes;
    }

    public void setAnswerMinutes(Integer answerMinutes) {
        this.answerMinutes = answerMinutes;
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

    public Integer getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(Integer goodCount) {
        this.goodCount = goodCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifierIP() {
        return modifierIP;
    }

    public void setModifierIP(String modifierIP) {
        this.modifierIP = modifierIP;
    }

    public long getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(long sysVersion) {
        this.sysVersion = sysVersion;
    }

    public long getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(long bizVersion) {
        this.bizVersion = bizVersion;
    }

    public Float getDownloadPoints() {
        return downloadPoints;
    }

    public void setDownloadPoints(Float downloadPoints) {
        this.downloadPoints = downloadPoints;
    }

    public Integer getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(Integer favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public Integer getReferCount() {
        return referCount;
    }

    public void setReferCount(Integer referCount) {
        this.referCount = referCount;
    }

    public Integer getBadCount() {
        return badCount;
    }

    public void setBadCount(Integer badCount) {
        this.badCount = badCount;
    }

    public Integer getResTypeL1() {
        return resTypeL1;
    }

    public void setResTypeL1(Integer resTypeL1) {
        this.resTypeL1 = resTypeL1;
    }

}
