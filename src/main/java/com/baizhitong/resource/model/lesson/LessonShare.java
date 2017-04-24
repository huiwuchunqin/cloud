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
 * 7012.课时共享实体
 * 
 * @author creator ZhangQiang 2017年4月19日 下午4:13:09
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "lesson_share")
@SuppressWarnings("serial")
@Entity
public class LessonShare extends BaseEntity implements Serializable{

    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer   id;
    /** 课程编码 */
    private String    courseCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 学科编码 */
    private String    subjectCode;
    /** 教材编码 */
    private String    tbCode;
    /** 教材版本编码 */
    private String    textbookVerCode;
    /** 课时编码 */
    private String    lessonCode;
    /** 课时名称 */
    private String    lessonName;
    /** 课时封面 */
    private String    lessonCoverPath;
    /** 课时描述（JSON格式） */
    private String    lessonDescJson;
    /** 业务版本号 */
    private Integer   bizVersion;
    /** 分享级别 */
    private Integer   shareLevel;
    /** 分享时间 */
    private Timestamp shareTime;
    /** 制作人机构代码 */
    private String    makerOrgCode;
    /** 制作人代码 */
    private String    makerCode;
    /** 制作人姓名 */
    private String    makerName;
    /** 制作人机构名称 */
    private String    makerOrgName;
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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
    public String getTbCode() {
        return tbCode;
    }
    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }
    public String getTextbookVerCode() {
        return textbookVerCode;
    }
    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
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
    public String getLessonCoverPath() {
        return lessonCoverPath;
    }
    public void setLessonCoverPath(String lessonCoverPath) {
        this.lessonCoverPath = lessonCoverPath;
    }
    public String getLessonDescJson() {
        return lessonDescJson;
    }
    public void setLessonDescJson(String lessonDescJson) {
        this.lessonDescJson = lessonDescJson;
    }
    public Integer getBizVersion() {
        return bizVersion;
    }
    public void setBizVersion(Integer bizVersion) {
        this.bizVersion = bizVersion;
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
    
}
