package com.baizhitong.resource.model.lesson;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 课时-平台首页推荐设置实体
 * 
 * @author creator ZhangQiang 2017年4月14日 上午9:43:22
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "lesson_settings_home")
@Entity
@SuppressWarnings("serial")
public class LessonSettingsHome implements Serializable {
    
    /** 课时ID */
    @Id
    private Integer   lessonId;
    /** 课时编码 */
    private String    lessonCode;
    /** 课时名称 */
    private String    lessonName;
    /** 机构编码 */
    private String    orgCode;
    /** 学段编码 */
    private String    sectionCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 学科编码 */
    private String    subjectCode;
    /** 显示顺序 */
    private Integer   dispOrder;
    /** 缩略图地址 */
    private String    thumbnailPath;
    /** 封面图像路径 */
    private String    coverPath;
    /** 是否使用 */
    private Integer   flagAvailable;
    /** 设置时间 */
    private Timestamp settingTime;
    /** 设置人信息 */
    private String    settingUserName;
    
    public Integer getLessonId() {
        return lessonId;
    }
    public void setLessonId(Integer lessonId) {
        this.lessonId = lessonId;
    }
    public String getLessonCode() {
        return lessonCode;
    }
    public void setLessonCode(String lessonCode) {
        this.lessonCode = lessonCode;
    }
    public String getLessonName() {
        return lessonName;
    }
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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
    public Integer getDispOrder() {
        return dispOrder;
    }
    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }
    public String getThumbnailPath() {
        return thumbnailPath;
    }
    public void setThumbnailPath(String thumbnailPath) {
        this.thumbnailPath = thumbnailPath;
    }
    public String getCoverPath() {
        return coverPath;
    }
    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }
    public Integer getFlagAvailable() {
        return flagAvailable;
    }
    public void setFlagAvailable(Integer flagAvailable) {
        this.flagAvailable = flagAvailable;
    }
    public Timestamp getSettingTime() {
        return settingTime;
    }
    public void setSettingTime(Timestamp settingTime) {
        this.settingTime = settingTime;
    }
    public String getSettingUserName() {
        return settingUserName;
    }
    public void setSettingUserName(String settingUserName) {
        this.settingUserName = settingUserName;
    }
    
}
