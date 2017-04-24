package com.baizhitong.resource.model.lesson;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baizhitong.resource.common.base.BaseEntity;

/**
 * 
 * 3010.课时实体类
 * 
 * @author creator ZhangQiang 2016年8月23日 下午2:08:13
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "lesson")
@SuppressWarnings("serial")
@Entity
public class Lesson extends BaseEntity implements Serializable {

    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer   id;
    /** 机构编码 */
    private String    orgCode;
    /** 课程编码 */
    private String    courseCode;
    /** 课时编码 */
    private String    lessonCode;
    /** 课时业务编码 */
    private String    lessonBizCode;
    /** 课时名称 */
    private String    lessonName;
    /** 教学目标 */
    private String    lessonObjective;
    /** 重点难点 */
    private String    keyDifficultPoint;
    /** 学段编码 */
    private String    sectionCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 学科编码 */
    private String    subjectCode;
    /** 教材版本编码 */
    private String    textbookVerCode;
    /** 教材编码 */
    private String    tbCode;
    /** 学年代码 */
    private String    studyYearCode;
    /** 学期代码 */
    private String    termCode;
    /** 学年学期代码 */
    private String    yearTermCode;
    /** 教师代码 */
    private String    teacherCode;
    /** 教师代码全局 */
    private String    teacherCodeGlobal;
    /** 教师姓名 */
    private String    teacherName;
    /** 教师所在机构名称 */
    private String    orgName;
    /** 课堂模式 */
    private Integer   lessonMode;
    /** 任务单栏目模板 */
    private String    taskColTemplateCode;
    /** 分享时间 */
    private Timestamp shareTime;
    /** 分享级别 */
    private Integer   shareLevel;
    /** 分享审核中级别 */
    private Integer   shareCheckLevel;
    /** 分享审核中状态 */
    private Integer   shareCheckStatus;
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
    /** 来源类型 */
    private Integer   sourceType;
    /** 来源最初课时代码 */
    private String    topReferLessonCode;
    /** 来源最近课时代码 */
    private String    lastReferLessonCode;
    /** 来源信息 */
    private String    sourceInfo;
    /** 主负责人编码 */
    private String    headEditorCode;
    /** 主负责人机构名称 */
    private String    headEditorOrgName;
    /** 主负责人姓名 */
    private String    headEditorName;
    /** 主负责人头像 */
    private String    headEditorImg;
    /** 编辑锁定状态 */
    private Integer   editStatus;
    /** 编辑者所在机构 */
    private String    editorOrgCode;
    /** 编辑者所在机构名称 */
    private String    editorOrgName;
    /** 编辑者编码 */
    private String    editorCode;
    /** 编辑者姓名 */
    private String    editorName;
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
    /** 系统版本号 */
    private Integer   sysVer;
    /** 锁定状态 */
    private Integer   lockStatus;
    /** 显示顺序 */
    private Integer   dispOrder;
    /** 课时GUID */
    private String    guid;
    /** 审核时间 */
    private Timestamp shareCheckTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getLessonCode() {
        return lessonCode;
    }

    public void setLessonCode(String lessonCode) {
        this.lessonCode = lessonCode;
    }

    public String getLessonBizCode() {
        return lessonBizCode;
    }

    public void setLessonBizCode(String lessonBizCode) {
        this.lessonBizCode = lessonBizCode;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonObjective() {
        return lessonObjective;
    }

    public void setLessonObjective(String lessonObjective) {
        this.lessonObjective = lessonObjective;
    }

    public String getKeyDifficultPoint() {
        return keyDifficultPoint;
    }

    public void setKeyDifficultPoint(String keyDifficultPoint) {
        this.keyDifficultPoint = keyDifficultPoint;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTextbookVerCode() {
        return textbookVerCode;
    }

    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
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

    public String getYearTermCode() {
        return yearTermCode;
    }

    public void setYearTermCode(String yearTermCode) {
        this.yearTermCode = yearTermCode;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getTeacherCodeGlobal() {
        return teacherCodeGlobal;
    }

    public void setTeacherCodeGlobal(String teacherCodeGlobal) {
        this.teacherCodeGlobal = teacherCodeGlobal;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getLessonMode() {
        return lessonMode;
    }

    public void setLessonMode(Integer lessonMode) {
        this.lessonMode = lessonMode;
    }

    public String getTaskColTemplateCode() {
        return taskColTemplateCode;
    }

    public void setTaskColTemplateCode(String taskColTemplateCode) {
        this.taskColTemplateCode = taskColTemplateCode;
    }

    public Timestamp getShareTime() {
        return shareTime;
    }

    public void setShareTime(Timestamp shareTime) {
        this.shareTime = shareTime;
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

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getTopReferLessonCode() {
        return topReferLessonCode;
    }

    public void setTopReferLessonCode(String topReferLessonCode) {
        this.topReferLessonCode = topReferLessonCode;
    }

    public String getLastReferLessonCode() {
        return lastReferLessonCode;
    }

    public void setLastReferLessonCode(String lastReferLessonCode) {
        this.lastReferLessonCode = lastReferLessonCode;
    }

    public String getSourceInfo() {
        return sourceInfo;
    }

    public void setSourceInfo(String sourceInfo) {
        this.sourceInfo = sourceInfo;
    }

    public String getHeadEditorCode() {
        return headEditorCode;
    }

    public void setHeadEditorCode(String headEditorCode) {
        this.headEditorCode = headEditorCode;
    }

    public String getHeadEditorOrgName() {
        return headEditorOrgName;
    }

    public void setHeadEditorOrgName(String headEditorOrgName) {
        this.headEditorOrgName = headEditorOrgName;
    }

    public String getHeadEditorName() {
        return headEditorName;
    }

    public void setHeadEditorName(String headEditorName) {
        this.headEditorName = headEditorName;
    }

    public String getHeadEditorImg() {
        return headEditorImg;
    }

    public void setHeadEditorImg(String headEditorImg) {
        this.headEditorImg = headEditorImg;
    }

    public Integer getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(Integer editStatus) {
        this.editStatus = editStatus;
    }

    public String getEditorOrgCode() {
        return editorOrgCode;
    }

    public void setEditorOrgCode(String editorOrgCode) {
        this.editorOrgCode = editorOrgCode;
    }

    public String getEditorOrgName() {
        return editorOrgName;
    }

    public void setEditorOrgName(String editorOrgName) {
        this.editorOrgName = editorOrgName;
    }

    public String getEditorCode() {
        return editorCode;
    }

    public void setEditorCode(String editorCode) {
        this.editorCode = editorCode;
    }

    public String getEditorName() {
        return editorName;
    }

    public void setEditorName(String editorName) {
        this.editorName = editorName;
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

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Timestamp getShareCheckTime() {
        return shareCheckTime;
    }

    public void setShareCheckTime(Timestamp shareCheckTime) {
        this.shareCheckTime = shareCheckTime;
    }

}
