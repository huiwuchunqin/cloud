package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * ResAudio 音频资源
 * 
 * @author creator gaow 2017年3月29日 上午10:03:37
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "res_audio")
@SuppressWarnings("serial")
public class ResAudio implements Serializable {
    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer   id;
    /** 资源编码 */
    private String    resCode;
    /** 资源业务编码 */
    private String    resBizCode;
    /** 资源名称 */
    private String    resName;
    /** 资源分类（一级） */
    private Integer   resTypeL1;
    /** 资源分类（二级） */
    private String    resTypeL2;
    /** 资源ID（MangoDB的） */
    private String    objectId;
    /** 文件唯一值 */
    private String    fileKey;
    /** 资源文件扩展名 */
    private String    suffix;
    /** 资源文件大小 */
    private Integer   resSize;
    /** 媒体时长 */
    private Integer   mediaDurationMS;
    /** 文件地址类型 */
    private Integer   filePathType;
    /** 缩略图地址 */
    private String    thumbnailPath;
    /** 标清访问地址 */
    private String    lowPath;
    /** 高清访问地址 */
    private String    highPath;
    /** P2P访问地址 */
    private String    p2pPath;
    /** 封面图片地址 */
    private String    coverPath;
    /** 音频访问地址 */
    private String    audioPath;
    /** 允许试看时长百分比 */
    private Float     trialTimeRate;
    /** 资源状态 */
    private Integer   resStatus;
    /** 转码完成时间 */
    private Timestamp convertCompletedTime;
    /** 分享级别 */
    private Integer   shareLevel;
    /** 分享时间 */
    private Timestamp shareTime;
    /** 分享审核中级别 */
    private Integer   shareCheckLevel;
    /** 分享审核状态 */
    private Integer   shareCheckStatus;
    /** 分享审核中时间 */
    private Timestamp shareCheckTime;
    /** 制作人机构代码 */
    private String    makerOrgCode;
    /** 制作人代码 */
    private String    makerCode;
    /** 制作人姓名 */
    private String    makerName;
    /** 制作人机构名称 */
    private String    makerOrgName;
    /** 制作时间 */
    private Timestamp makeTime;
    /** 下载所需点数 */
    private Float     downloadPoints;
    /** 是否允许下载 */
    private Integer   flagAllowDownload;
    /** 是否允许浏览 */
    private Integer   flagAllowBrowse;
    /** 点击总数 */
    private Integer   clickCount;
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
    /** 资源认可指数 */
    private Integer   accepptableIndex;
    /** 资源描述 */
    private String    resDesc;
    /** 是否显示在历史记录中 */
    private Integer   flagHistoryShow;
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
    /** 更新者IP */
    private String    modifierIP;
    /** 系统版本号 */
    private Integer   sysVersion;
    /** 业务版本号 */
    private Integer   bizVersion;
    /** 标签 */
    private String    tags;
    /** 同步临时ID */
    private Integer   tempID;
    /** 审核访问路径 */
    private String    accessPath;
    /** 资源对学生的可见性 */
    private Integer   flagStudentVisual;

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

    public String getResBizCode() {
        return resBizCode;
    }

    public void setResBizCode(String resBizCode) {
        this.resBizCode = resBizCode;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getResSize() {
        return resSize;
    }

    public void setResSize(Integer resSize) {
        this.resSize = resSize;
    }

    public Integer getMediaDurationMS() {
        return mediaDurationMS;
    }

    public void setMediaDurationMS(Integer mediaDurationMS) {
        this.mediaDurationMS = mediaDurationMS;
    }

    public Integer getFilePathType() {
        return filePathType;
    }

    public void setFilePathType(Integer filePathType) {
        this.filePathType = filePathType;
    }

    public String getThumbnailPath() {
        return thumbnailPath;
    }

    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }

    public String getLowPath() {
        return lowPath;
    }

    public void setLowPath(String lowPath) {
        this.lowPath = lowPath;
    }

    public String getHighPath() {
        return highPath;
    }

    public void setHighPath(String highPath) {
        this.highPath = highPath;
    }

    public String getP2pPath() {
        return p2pPath;
    }

    public void setP2pPath(String p2pPath) {
        this.p2pPath = p2pPath;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    public Float getTrialTimeRate() {
        return trialTimeRate;
    }

    public void setTrialTimeRate(Float trialTimeRate) {
        this.trialTimeRate = trialTimeRate;
    }

    public Integer getResStatus() {
        return resStatus;
    }

    public void setResStatus(Integer resStatus) {
        this.resStatus = resStatus;
    }

    public Timestamp getConvertCompletedTime() {
        return convertCompletedTime;
    }

    public void setConvertCompletedTime(Timestamp convertCompletedTime) {
        this.convertCompletedTime = convertCompletedTime;
    }

    public Integer getShareLevel() {
        return shareLevel;
    }

    public void setShareLevel(Integer shareLevel) {
        this.shareLevel = shareLevel;
    }

    public Timestamp getShareTime() {
        return shareTime;
    }

    public void setShareTime(Timestamp shareTime) {
        this.shareTime = shareTime;
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

    public Timestamp getShareCheckTime() {
        return shareCheckTime;
    }

    public void setShareCheckTime(Timestamp shareCheckTime) {
        this.shareCheckTime = shareCheckTime;
    }

    public String getMakerOrgCode() {
        return makerOrgCode;
    }

    public void setMakerOrgCode(String makerOrgCode) {
        this.makerOrgCode = makerOrgCode;
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

    public String getMakerOrgName() {
        return makerOrgName;
    }

    public void setMakerOrgName(String makerOrgName) {
        this.makerOrgName = makerOrgName;
    }

    public Timestamp getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Timestamp makeTime) {
        this.makeTime = makeTime;
    }

    public Float getDownloadPoints() {
        return downloadPoints;
    }

    public void setDownloadPoints(Float downloadPoints) {
        this.downloadPoints = downloadPoints;
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

    public Integer getClickCount() {
        return clickCount;
    }

    public void setClickCount(Integer clickCount) {
        this.clickCount = clickCount;
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

    public Integer getAccepptableIndex() {
        return accepptableIndex;
    }

    public void setAccepptableIndex(Integer accepptableIndex) {
        this.accepptableIndex = accepptableIndex;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public Integer getFlagHistoryShow() {
        return flagHistoryShow;
    }

    public void setFlagHistoryShow(Integer flagHistoryShow) {
        this.flagHistoryShow = flagHistoryShow;
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

    public Integer getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(Integer sysVersion) {
        this.sysVersion = sysVersion;
    }

    public Integer getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(Integer bizVersion) {
        this.bizVersion = bizVersion;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getTempID() {
        return tempID;
    }

    public void setTempID(Integer tempID) {
        this.tempID = tempID;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public Integer getFlagStudentVisual() {
        return flagStudentVisual;
    }

    public void setFlagStudentVisual(Integer flagStudentVisual) {
        this.flagStudentVisual = flagStudentVisual;
    }

}
