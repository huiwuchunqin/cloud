package com.baizhitong.resource.model.report;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * PlatformLessonCoverageTb 课程覆盖率
 * 
 * @author creator Zhangqd 2017年1月5日 上午11:09:51
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "platform_lesson_coverage_tb")
public class PlatformLessonCoverageTb {
    /** 主键 */
    private Integer   id;
    /** 学段编码 */
    private String    sectionCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 学科编码 */
    private String    subjectCode;
    /** 教材版本编码 */
    private String    tbvCode;
    /** 教材版本名称 */
    private String    tbvName;
    /** 教材编码 */
    private String    tbCode;
    /** 教材名称 */
    private String    tbName;
    /** 课程数 */
    private Integer   lessonNum;
    /** 课程已覆盖节点数 */
    private Integer   lessonCoverNum;
    /** 课程已覆盖节点数 */
    private Integer   lessonCoverage;
    /** 章节节点数 */
    private Integer   chapterNum;
    /** 更新方式 */
    private Integer   updateType;
    /** 更新时间 */
    private Timestamp modifyTime;
    /** 更新者IP */
    private String    modifierIP;

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

    public String getTbvCode() {
        return tbvCode;
    }

    public void setTbvCode(String tbvCode) {
        this.tbvCode = tbvCode;
    }

    public String getTbvName() {
        return tbvName;
    }

    public void setTbvName(String tbvName) {
        this.tbvName = tbvName;
    }

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public String getTbName() {
        return tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public Integer getLessonCoverNum() {
        return lessonCoverNum;
    }

    public void setLessonCoverNum(Integer lessonCoverNum) {
        this.lessonCoverNum = lessonCoverNum;
    }

    public Integer getLessonCoverage() {
        return lessonCoverage;
    }

    public void setLessonCoverage(Integer lessonCoverage) {
        this.lessonCoverage = lessonCoverage;
    }

    public Integer getChapterNum() {
        return chapterNum;
    }

    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
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
}
