package com.baizhitong.resource.model.vo.res;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.utils.DataUtils;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 资源vo
 * 
 * @author gaow
 * @date:2015年12月16日 下午1:37:04
 */
public class ResVo {

    /** 主键 */
    private Integer  id;
    /** 资源编码 */
    private String   resCode;
    /** 资源名称 */
    private String   resName;
    /** 资源分类（一级） */
    private Integer  resTypeL1;
    /** 资源分类（二级） */
    private String   resTypeL2;
    /** 资源ID（MangoDB的） */
    private String   objectId;
    /** 文件唯一值 */
    private String   fileKey;
    /** 文件类型 */
    private Integer  fileType;
    /** 资源文件扩展名 */
    private String   suffix;
    /** 资源文件大小 */
    private Integer  resSize;
    /** 封面图片地址 */
    private String   coverPath;
    /** 页数 */
    private Integer  pages;
    /** 资源状态 */
    private Integer  resStatus;
    /** 转换后的访问地址（HTML） */
    private String   convertedPathHTML;
    /** 转换后的访问地址（静态图片序列） */
    private String   convertedPathImages;
    /** 转码完成时间 */
    private String   convertCompletedTime;
    /** PPT转换类型 */
    private Integer  pptConvertType;
    /** 分享级别 */
    private Integer  shareLevel;
    /** 分享审核中级别 */
    private Integer  shareCheckLevel;
    /** 分享审核中状态 */
    private Integer  shareCheckStatus;
    /** 机构编码 */
    private String   orgCode;
    /** 制作人代码 */
    private String   userCode;
    /** 制作人姓名 */
    private String   userName;
    /** 制作人所在机构名称 */
    private String   userOrgName;
    /** 制作时间 */
    private String   makeTime;
    /** 是否允许下载 */
    private Integer  flagAllowDownload;
    /** 下载点数 */
    private Float    downloadPoints;
    /** 是否允许浏览 */
    private Integer  flagAllowBrowse;
    /** 点击总数 */
    private Integer  clickCount;
    /** 浏览总数 */
    private Integer  browseCount;
    /** 下载总数 */
    private Integer  downloadCount;
    /** 引用总数 */
    private Integer  referCount;
    /** 收藏总数 */
    private Integer  favoriteCount;
    /** 点赞总数 */
    private Integer  goodCount;
    /** 点踩总数 */
    private Integer  badCount;
    /** 评论总数 */
    private Integer  commentCount;
    /** 资源描述 */
    private String   resDesc;
    /** 是否删除 */
    private Integer  flagDelete;
    /** 创建者 */
    private String   creator;
    /** 创建时间 */
    private String   createTime;
    /** 创建者IP */
    private String   creatorIP;
    /** 更新者 */
    private String   modifier;
    /** 更新时间 */
    private String   modifyTime;
    /** 更新者IP */
    private String   modifierIP;
    /** 系统版本号 */
    private Integer  sysVersion;
    /** 业务版本号 */
    private Integer  bizVersion;
    /** 媒体时长 */
    private Integer  mediaDurationMS;
    /** 缩略图地址 */
    private String   thumbnailPath;
    /** 低分辨率访问地址 */
    private String   lowPath;
    /** 高分辨率访问地址（默认） */
    private String   highPath;
    /** P2P访问地址 */
    private String   p2pPath;
    /** 允许试看时长百分比 */
    private Float    trialTimeRate;
    /** 学段名称 */
    private String   sectionName;
    /** 提交审核时间 */
    private String   shareTime;
    /** 年级名称 */
    private String   gradeName;
    /** 学科名称 */
    private String   subjectName;
    /** 机构名称 */
    private String   orgName;
    /** 审核时间 */
    private String   shareCheckTime;
    private String   resTypeL2Name;
    private Integer  tbcCount;
    private Integer  kpCount;
    private String   checkComments;
    private String   checkerName;
    private String   resAccessPath;
    private String[] tags;
    private String   applyTime;
    private String audioPath;
    // mapList转VoList
    public static List<ResVo> getVoListFromMapList(List<Map<String, Object>> mapList) {
        List<ResVo> voList = new ArrayList<ResVo>();
        if (mapList == null || mapList.size() <= 0)
            return new ArrayList<ResVo>();
        for (int i = 0; i < mapList.size(); i++) {
            ResVo vo = new ResVo(mapList.get(i));
            voList.add(vo);
        }
        return voList;
    }

    public ResVo() {
    }

    /**
     * 文档实体转vo
     * 
     * @param doc
     * @return
     * @author gaow
     * @date:2015年12月19日 下午2:51:28
     */
    public static ResVo getResVoFromResDoc(ResDoc doc) {
        ResVo vo = new ResVo();
        DataUtils.copySimpleObject(doc, vo);
        if (doc.getDownloadPoints() != null) {
            vo.setDownloadPoints(doc.getDownloadPoints());
        }
        return vo;
    }

    /**
     * 视频实体转vo
     * 
     * @param media
     * @return
     * @author gaow
     * @date:2015年12月19日 下午2:51:41
     */
    public static ResVo getResVoFromResMidea(ResMedia media) {
        ResVo vo = new ResVo();
        DataUtils.copySimpleObject(media, vo);
        if (media.getDownloadPoints() != null) {
            vo.setDownloadPoints(media.getDownloadPoints());
        }
        if (media.getTrialTimeRate() != null) {
            vo.setTrialTimeRate(media.getTrialTimeRate());
        }
        return vo;
    }

    public ResVo(Map<String, Object> map) {
        if (MapUtils.isNotEmpty(map)) {
            this.id = MapUtils.getInteger(map, "id");
            this.resCode = MapUtils.getString(map, "resCode");
            this.resTypeL1 = MapUtils.getInteger(map, "resTypeL1");
            this.resTypeL2 = MapUtils.getString(map, "resTypeL2");
            this.resTypeL2Name = MapUtils.getString(map, "resTypeL2Name");
            this.objectId = MapUtils.getString(map, "objectId");
            this.suffix = MapUtils.getString(map, "suffix");
            this.resSize = MapUtils.getInteger(map, "resSize");
            this.coverPath = MapUtils.getString(map, "coverPath");
            this.thumbnailPath = MapUtils.getString(map, "thumbnailPath");
            this.lowPath = MapUtils.getString(map, "lowPath");
            this.highPath = MapUtils.getString(map, "highPath");
            if (StringUtils.isEmpty(this.highPath)) {
                this.highPath = this.lowPath;
            }
            this.p2pPath = MapUtils.getString(map, "p2pPath");
            this.resAccessPath = MapUtils.getString(map, "resAccessPath");
            if (MapUtils.getFloat(map, "trialTimeRate") != null) {
                this.trialTimeRate = MapUtils.getFloat(map, "trialTimeRate");
            }
            this.pages = MapUtils.getInteger(map, "pages");
            this.resStatus = MapUtils.getInteger(map, "resStatus");
            this.convertedPathHTML = MapUtils.getString(map, "convertedPathHTML");
            this.convertedPathImages = MapUtils.getString(map, "convertedPathImages");
            this.convertCompletedTime = MapUtils.getString(map, "convertCompletedTime");
            this.resName = MapUtils.getString(map, "resName");
            this.shareCheckStatus = MapUtils.getInteger(map, "shareCheckStatus");
            this.makeTime = timeformatter(MapUtils.getString(map, "makeTime"));
            this.userName = MapUtils.getString(map, "makerName");
            this.resTypeL2Name = MapUtils.getString(map, "resTypeL2Name");
            this.shareLevel = MapUtils.getInteger(map, "shareLevel");
            this.shareCheckLevel = MapUtils.getInteger(map, "shareCheckLevel");
            this.sectionName = MapUtils.getString(map, "sectionName");
            this.subjectName = MapUtils.getString(map, "subjectName");
            this.gradeName = MapUtils.getString(map, "gradeName");
            this.flagAllowDownload = MapUtils.getInteger(map, "flagAllowDownload");
            this.flagAllowBrowse = MapUtils.getInteger(map, "flagAllowBrowse");
            this.clickCount = MapUtils.getInteger(map, "clickCount");
            this.browseCount = MapUtils.getInteger(map, "browseCount");
            this.downloadCount = MapUtils.getInteger(map, "downloadCount");
            this.goodCount = MapUtils.getInteger(map, "goodCount");
            this.badCount = MapUtils.getInteger(map, "badCount");
            this.commentCount = MapUtils.getInteger(map, "commentCount");
            this.favoriteCount = MapUtils.getInteger(map, "favoriteCount");
            this.tbcCount = MapUtils.getInteger(map, "tbcCount");
            this.kpCount = MapUtils.getInteger(map, "kpCount");
            this.referCount = MapUtils.getInteger(map, "referCount");
            this.userCode = MapUtils.getString(map, "makerCode");
            this.pptConvertType = MapUtils.getInteger(map, "pptConvertType");
            String resDesc = MapUtils.getString(map, "resDesc");
            Pattern p = Pattern.compile("\r|\n");
            /*
             * if(StringUtils.isNotEmpty(resDesc)){ Matcher m = p.matcher(resDesc); resDesc =
             * m.replaceAll("<br>"); this.resDesc=resDesc; }
             */
            this.resDesc = resDesc;
            this.flagDelete = MapUtils.getInteger(map, "flagDelete");
            this.creator = MapUtils.getString(map, "creator");
            this.createTime = MapUtils.getString(map, "createTime");
            this.creatorIP = MapUtils.getString(map, "creatorIP");
            this.modifier = MapUtils.getString(map, "modifier");
            this.modifyTime = MapUtils.getString(map, "modifyTime");
            this.modifierIP = MapUtils.getString(map, "modifierIP");
            this.orgName = MapUtils.getString(map, "orgName");
            this.userOrgName = MapUtils.getString(map, "userOrgName");
            this.orgCode = MapUtils.getString(map, "orgCode");
            this.shareCheckTime = MapUtils.getString(map, "shareCheckTime");
            this.shareTime = MapUtils.getString(map, "shareTime");
            this.audioPath=MapUtils.getString(map, "audioPath");
            if (MapUtils.getFloat(map, "downloadPoints") != null) {
                this.downloadPoints = MapUtils.getFloat(map, "downloadPoints");
            }
            String tags = MapUtils.getString(map, "tags");
            if (StringUtils.isNotEmpty(tags)) {
                String[] tagArray = tags.split(",");
                this.tags = tagArray;
            }
        }
    }

    public String timeformatter(String time) {
        if (StringUtils.isEmpty(time))
            return "";
        return DateUtils.formatDate(time, "yyyy-MM-dd HH:mm");
    }

    public String getTbcCountStr() {
        if (this.tbcCount != null && tbcCount > 0) {
            return "已标注";
        } else {
            return "";
        }
    }

    public String getKpCountStr() {
        if (this.kpCount != null && kpCount > 0) {
            return "已标注";
        } else {
            return "";
        }
    }

    public String getAudioPath() {
        return audioPath;
    }

    public void setAudioPath(String audioPath) {
        this.audioPath = audioPath;
    }

    /**
     * 分享级别
     * 
     * @return
     * @author gaow
     * @date:2015年12月16日 下午2:43:53
     */
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
            return "区域共享";
        } else {
            return "";
        }
    }

    public String getShareCheckLevelStr() {
        if ((CoreConstants.RESOURCE_SHARE_LEVEL_CITY).equals(this.shareCheckLevel)) {
            return "地方";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY).equals(this.shareCheckLevel)) {
            return "校内共享";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_OPEN).equals(this.shareCheckLevel)) {
            return "公开";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE).equals(this.shareCheckLevel)) {
            return "个人私有";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_PROVINCE).equals(this.shareCheckLevel)) {
            return "省级";
        } else if ((CoreConstants.RESOURCE_SHARE_LEVEL_TOWN).equals(this.shareCheckLevel)) {
            return "区域共享";
        } else {
            return "";
        }
    }

    /**
     * 是否允许浏览
     * 
     * @return
     * @author gaow
     * @date:2015年12月19日 下午7:50:13
     */
    public String getAllowBrowser() {
        if ((CoreConstants.RESOURCE_ALLOW_BROWSER_NO).equals(this.flagAllowBrowse)) {
            return "否";
        } else if ((CoreConstants.RESOURCE_ALLOW_BROWSER_YES).equals(this.flagAllowBrowse)) {
            return "是";
        } else {
            return "";
        }
    }

    /**
     * 是否语序下载
     * 
     * @return
     * @author gaow
     * @date:2015年12月19日 下午7:50:05
     */
    public String getAllowDownLoad() {
        if ((CoreConstants.RESOURCE_ALLOW_DOWNLOAD_NO).equals(this.flagAllowDownload)) {
            return "否";
        } else if ((CoreConstants.RESOURCE_ALLOW_DOWNLOAD_YES).equals(this.flagAllowDownload)) {
            return "是";
        } else {
            return "";
        }
    }

    /**
     * 审核状态
     * 
     * @return
     * @author gaow
     * @date:2015年12月16日 下午2:46:23
     */
    public String getCheckStatusStr() {
        if (this.shareCheckStatus == null)
            return "未提交";
        if ((CoreConstants.CHECK_STATUS_CHECKING).equals(this.shareCheckStatus)) {
            return "待审核";
        } else if ((CoreConstants.CHECK_STATUS_CHECKED).equals(this.shareCheckStatus)) {
            return "已通过";
        } else if ((CoreConstants.CHECK_STATUS_UNAPPROVE).equals(this.shareCheckStatus)) {
            return "已退回";
        } else if ((CoreConstants.CHECK_STATUS_UNCOMMIT).equals(this.shareCheckStatus)) {
            return "未提交";
        } else {
            return "";
        }
    }

    /**
     * 资源状态
     * 
     * @return
     * @author gaow
     * @date:2015年12月16日 下午2:48:37
     */
    public String getResStatusStr() {
        if (this.resStatus == null)
            return "转码中";
        if ((CoreConstants.RESOURCE_STATE_CONVERTING).equals(this.resStatus)) {
            return "转码中";
        } else if ((CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS).equals(this.resStatus)) {
            return "转码成功";
        } else if ((CoreConstants.RESOURCE_STATE_CONVERT_FAIL).equals(this.resStatus)) {
            return "转码失败";
        } else {
            return "转码中";
        }
    }

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

    public String getConvertCompletedTime() {
        return convertCompletedTime;
    }

    public void setConvertCompletedTime(String convertCompletedTime) {
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

    public String getUserOrgName() {
        return userOrgName;
    }

    public void setUserOrgName(String userOrgName) {
        this.userOrgName = userOrgName;
    }

    public String getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(String makeTime) {
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getShareCheckTime() {
        return shareCheckTime;
    }

    public void setShareCheckTime(String shareCheckTime) {
        this.shareCheckTime = shareCheckTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
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

    public String getResTypeL2Name() {
        return resTypeL2Name;
    }

    public void setResTypeL2Name(String resTypeL2Name) {
        this.resTypeL2Name = resTypeL2Name;
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

    public String getCheckComments() {
        return checkComments;
    }

    public void setCheckComments(String checkComments) {
        this.checkComments = checkComments;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
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

    public Float getTrialTimeRate() {
        return trialTimeRate;
    }

    public void setTrialTimeRate(Float trialTimeRate) {
        this.trialTimeRate = trialTimeRate;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Integer getTbcCount() {
        return tbcCount;
    }

    public void setTbcCount(Integer tbcCount) {
        this.tbcCount = tbcCount;
    }

    public Integer getKpCount() {
        return kpCount;
    }

    public void setKpCount(Integer kpCount) {
        this.kpCount = kpCount;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getResAccessPath() {
        return resAccessPath;
    }

    public void setResAccessPath(String resAccessPath) {
        this.resAccessPath = resAccessPath;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

}
