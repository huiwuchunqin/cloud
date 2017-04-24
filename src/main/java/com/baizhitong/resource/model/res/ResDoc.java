package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 资源_文档模型
 * 
 * @author gaow 2015/12/02
 */
@Entity
@Table(name = "res_doc")
public class ResDoc implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 资源ID */
    @Id
    @Column(insertable = false,
            updatable = false)
    /** 主键 */
    private Integer           id;
    /** 资源编码 */
    private String            resCode;
    /** 资源名称 */
    private String            resName;
    /** 资源分类（一级） */
    private Integer           resTypeL1;
    /** 资源分类（二级） */
    private String            resTypeL2;
    /** 资源ID（MangoDB的） */
    private String            objectId;
    /** 文件唯一值 */
    private String            fileKey;
    /** 文件类型 */
    private Integer           fileType;
    /** 资源文件扩展名 */
    private String            suffix;
    /** 资源文件大小 */
    private Integer           resSize;
    /** 封面图片地址 */
    private String            coverPath;
    /** 页数 */
    private Integer           pages;
    /** 资源状态 */
    private Integer           resStatus;
    /** 转换后的访问地址（HTML） */
    private String            convertedPathHTML;
    /** 转换后的访问地址（静态图片序列） */
    private String            convertedPathImages;
    /** 转码完成时间 */
    private Timestamp         convertCompletedTime;
    /** PPT转换类型 */
    private Integer           pptConvertType;
    /** 分享级别 */
    private Integer           shareLevel;
    /** 分享审核中级别 */
    private Integer           shareCheckLevel;
    /** 分享审核中状态 */
    private Integer           shareCheckStatus;
    /** 机构编码 */
    private String            makerOrgCode;
    /** 制作人代码 */
    private String            makerCode;
    /** 制作人姓名 */
    private String            makerName;
    /** 制作人所在机构名称 */
    private String            makerOrgName;
    /** 制作时间 */
    private Timestamp         makeTime;
    /** 是否允许下载 */
    private Integer           flagAllowDownload;
    /** 下载点数 */
    private Float             downloadPoints;
    /** 是否允许浏览 */
    private Integer           flagAllowBrowse;
    /** 点击总数 */
    private Integer           clickCount;
    /** 浏览总数 */
    private Integer           browseCount;
    /** 下载总数 */
    private Integer           downloadCount;
    /** 引用总数 */
    private Integer           referCount;
    /** 分享审核时间 */
    private Timestamp         shareCheckTime;
    /** 收藏总数 */
    private Integer           favoriteCount;
    /** 点赞总数 */
    private Integer           goodCount;
    /** 点踩总数 */
    private Integer           badCount;
    /** 评论总数 */
    private Integer           commentCount;
    /** 资源描述 */
    private String            resDesc;
    /** 是否删除 */
    private Integer           flagDelete;
    /** 创建者 */
    private String            creator;
    /** 创建时间 */
    private Timestamp         createTime;
    /** 创建者IP */
    private String            creatorIP;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Timestamp         modifyTime;
    /** 更新者IP */
    private String            modifierIP;
    /** 系统版本号 */
    private Integer           sysVersion;
    /** 业务版本号 */
    private Integer           bizVersion;
    /** 分享审核时间 */
    private Timestamp         shareTime;
    /** 标签 */
    private String            tags;

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

    public Timestamp getShareTime() {
        return shareTime;
    }

    public void setShareTime(Timestamp shareTime) {
        this.shareTime = shareTime;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public Timestamp getShareCheckTime() {
        return shareCheckTime;
    }

    public void setShareCheckTime(Timestamp shareCheckTime) {
        this.shareCheckTime = shareCheckTime;
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

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
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

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getResStatus() {
        return resStatus;
    }

    public void setResStatus(Integer resStatus) {
        this.resStatus = resStatus;
    }

    public String getConvertedPathHTML() {
        return convertedPathHTML;
    }

    public void setConvertedPathHTML(String convertedPathHTML) {
        this.convertedPathHTML = convertedPathHTML;
    }

    public String getConvertedPathImages() {
        return convertedPathImages;
    }

    public void setConvertedPathImages(String convertedPathImages) {
        this.convertedPathImages = convertedPathImages;
    }

    public Timestamp getConvertCompletedTime() {
        return convertCompletedTime;
    }

    public void setConvertCompletedTime(Timestamp convertCompletedTime) {
        this.convertCompletedTime = convertCompletedTime;
    }

    public Integer getPptConvertType() {
        return pptConvertType;
    }

    public void setPptConvertType(Integer pptConvertType) {
        this.pptConvertType = pptConvertType;
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

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
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

}
