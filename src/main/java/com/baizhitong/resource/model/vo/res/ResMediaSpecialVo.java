package com.baizhitong.resource.model.vo.res;

import java.sql.Timestamp;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 特色资源Vo
 * 
 * @author creator zhangqiang 2016年8月12日 上午10:51:55
 * @author updater
 *
 * @version 1.0.0
 */
public class ResMediaSpecialVo {

    /** 主键 */
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
    /** 特色资源分类（一级） */
    private String    resSpecialTypeL1;
    /** 特色资源分类（二级） */
    private String    resSpecialTypeL2;
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
    /** 缩略图地址 */
    private String    thumbnailPath;
    /** 低分辨率访问地址 */
    private String    lowPath;
    /** 高分辨率访问地址（默认） */
    private String    highPath;
    /** P2P访问地址 */
    private String    p2pPath;
    /** 封面图片地址 */
    private String    coverPath;
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
    /** 分享审核中状态 */
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
    private String    makeTime;
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
    /** 资源描述 */
    private String    resDesc;
    /** 是否显示在历史记录中 */
    private Integer   flagHistoryShow;
    /** 锁定状态 */
    private Integer   lockStatus;
    /** 是否删除 */
    private Integer   flagDelete;
    /** 系统版本号 */
    private Integer   sysVersion;
    /** 业务版本号 */
    private Integer   bizVersion;
    private String    resSpecialTypeL1Name;
    private String    resSpecialTypeL2Name;
    /** 标签 */
    private String[]  tags;

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

    public String getResSpecialTypeL1() {
        return resSpecialTypeL1;
    }

    public void setResSpecialTypeL1(String resSpecialTypeL1) {
        this.resSpecialTypeL1 = resSpecialTypeL1;
    }

    public String getResSpecialTypeL2() {
        return resSpecialTypeL2;
    }

    public void setResSpecialTypeL2(String resSpecialTypeL2) {
        this.resSpecialTypeL2 = resSpecialTypeL2;
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

    public String getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(String makeTime) {
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

    public String getResSpecialTypeL1Name() {
        return resSpecialTypeL1Name;
    }

    public void setResSpecialTypeL1Name(String resSpecialTypeL1Name) {
        this.resSpecialTypeL1Name = resSpecialTypeL1Name;
    }

    public String getResSpecialTypeL2Name() {
        return resSpecialTypeL2Name;
    }

    public void setResSpecialTypeL2Name(String resSpecialTypeL2Name) {
        this.resSpecialTypeL2Name = resSpecialTypeL2Name;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getShareLevelStr() {
        if ((CoreConstants.RESOURCE_SHARE_LEVEL_CITY).equals(this.shareLevel)) {
            return "地方";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY).equals(this.shareLevel)) {
            return "校内共享";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_OPEN).equals(this.shareLevel)) {
            return "公开";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE).equals(this.shareLevel)) {
            return "个人私有";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_PROVINCE).equals(this.shareLevel)) {
            return "省级";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_TOWN).equals(this.shareLevel)) {
            return "区内共享";
        } else {
            return "";
        }
    }

    /**
     * 
     * (map转vo)<br>
     * 
     * @param map
     * @return
     */
    public static ResMediaSpecialVo mapToVo(Map<String, Object> map) {
        ResMediaSpecialVo vo = new ResMediaSpecialVo();
        if (MapUtils.isNotEmpty(map)) {
            vo.setId(MapUtils.getInteger(map, "id"));
            vo.setResCode(MapUtils.getString(map, "resCode"));
            vo.setResName(MapUtils.getString(map, "resName"));
            vo.setResTypeL1(MapUtils.getInteger(map, "resTypeL1"));
            vo.setResSpecialTypeL1(MapUtils.getString(map, "resSpecialTypeL1"));
            vo.setResSpecialTypeL2(MapUtils.getString(map, "resSpecialTypeL2"));
            vo.setMakerCode(MapUtils.getString(map, "makerCode"));
            vo.setMakerName(MapUtils.getString(map, "makerName"));
            vo.setMakerOrgCode(MapUtils.getString(map, "makerOrgCode"));
            vo.setMakerOrgName(MapUtils.getString(map, "makerOrgName"));
            vo.setResDesc(MapUtils.getString(map, "resDesc"));
            vo.setShareLevel(MapUtils.getInteger(map, "shareLevel"));
            vo.setHighPath(MapUtils.getString(map, "highPath"));
            if (StringUtils.isEmpty(MapUtils.getString(map, "highPath"))) {
                vo.setHighPath(MapUtils.getString(map, "lowPath"));
            }
            vo.setP2pPath(MapUtils.getString(map, "p2pPath"));
            vo.setMakeTime(timeformatter(MapUtils.getString(map, "makeTime")));
            vo.setResStatus(MapUtils.getInteger(map, "resStatus"));
            vo.setShareCheckLevel(MapUtils.getInteger(map, "shareCheckLevel"));
            vo.setCoverPath(MapUtils.getString(map, "coverPath"));
            vo.setFlagAllowDownload(MapUtils.getInteger(map, "flagAllowDownload"));
            vo.setResSpecialTypeL1Name(MapUtils.getString(map, "resSpecialTypeL1Name"));
            vo.setResSpecialTypeL2Name(MapUtils.getString(map, "resSpecialTypeL2Name"));
            String tags = MapUtils.getString(map, "tags");
            if (StringUtils.isNotEmpty(tags)) {
                String[] tagArray = tags.split(",");
                vo.setTags(tagArray);
            }
        }
        return vo;
    }

    /**
     * 
     * (时间格式化)<br>
     * 
     * @param time
     * @return
     */
    public static String timeformatter(String time) {
        if (StringUtils.isEmpty(time))
            return "";
        return DateUtils.formatDate(time, "yyyy-MM-dd HH:mm");
    }
}
