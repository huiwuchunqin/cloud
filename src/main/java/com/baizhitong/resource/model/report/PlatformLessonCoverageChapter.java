package com.baizhitong.resource.model.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 课程章节覆盖率 PlatformLessonCoverageChapter TODO
 * 
 * @author creator gaow 2017年1月5日 上午11:10:27
 * @author updater
 *
 * @version 1.0.0
 */
@SuppressWarnings("serial")
@Table(name = "platform_lesson_coverage_chapter")
@Entity
public class PlatformLessonCoverageChapter implements Serializable {

    @Id
    /**
     * 主键
     */
    private Integer    id;
    /**
     * 主键
     */
    private Integer    chapterLevel;
    /**
     * 主键
     */
    private String     chapterPCode;
    /**
     * 主键
     */
    private String     chapterCode;
    /**
     * 章节名称
     */
    private String     chapterName;
    /**
     * 课程覆盖数
     */
    private Integer    lessonCoverNum;
    /**
     * 主键
     */
    private Integer    nodeAmount;
    /**
     * 课程数
     */
    private Integer    lessonNum;
    /**
     * 课程覆盖率
     */
    private BigDecimal lessonCoverage;
    /**
     * 翻转课程覆盖数
     */
    private Integer    flipLessonCoverNum;
    /**
     * 翻转课程数
     */
    private Integer    flipLessonNum;
    /**
     * 翻转课程覆盖率
     */
    private BigDecimal filpLessonCoverage;
    /**
     * 自主课程覆盖数
     */
    private Integer    autonomousLessonCoverNum;
    /**
     * 自主课程数
     */
    private Integer    autonomousLessonNum;
    /**
     * 自主课程覆盖率
     */
    private BigDecimal autonomousLessonCoverage;
    /**
     * 更新类型
     */
    private Integer    updateType;
    /**
     * 修改时间
     */
    private Timestamp  modifyTime;
    /**
     * 修改者ip
     */
    private String     modifierIP;
    /**
     * 教材编码
     */
    private String     tbCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChapterLevel() {
        return chapterLevel;
    }

    public void setChapterLevel(Integer chapterLevel) {
        this.chapterLevel = chapterLevel;
    }

    public String getChapterPCode() {
        return chapterPCode;
    }

    public void setChapterPCode(String chapterPCode) {
        this.chapterPCode = chapterPCode;
    }

    public String getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(String chapterCode) {
        this.chapterCode = chapterCode;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getLessonCoverNum() {
        return lessonCoverNum;
    }

    public void setLessonCoverNum(Integer lessonCoverNum) {
        this.lessonCoverNum = lessonCoverNum;
    }

    public Integer getNodeAmount() {
        return nodeAmount;
    }

    public void setNodeAmount(Integer nodeAmount) {
        this.nodeAmount = nodeAmount;
    }

    public Integer getLessonNum() {
        return lessonNum;
    }

    public void setLessonNum(Integer lessonNum) {
        this.lessonNum = lessonNum;
    }

    public BigDecimal getLessonCoverage() {
        return lessonCoverage;
    }

    public void setLessonCoverage(BigDecimal lessonCoverage) {
        this.lessonCoverage = lessonCoverage;
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

    public String getTbCode() {
        return tbCode;
    }

    public void setTbCode(String tbCode) {
        this.tbCode = tbCode;
    }

    public Integer getFlipLessonCoverNum() {
        return flipLessonCoverNum;
    }

    public void setFlipLessonCoverNum(Integer flipLessonCoverNum) {
        this.flipLessonCoverNum = flipLessonCoverNum;
    }

    public Integer getFlipLessonNum() {
        return flipLessonNum;
    }

    public void setFlipLessonNum(Integer flipLessonNum) {
        this.flipLessonNum = flipLessonNum;
    }

    public BigDecimal getFilpLessonCoverage() {
        return filpLessonCoverage;
    }

    public void setFilpLessonCoverage(BigDecimal filpLessonCoverage) {
        this.filpLessonCoverage = filpLessonCoverage;
    }

    public Integer getAutonomousLessonCoverNum() {
        return autonomousLessonCoverNum;
    }

    public void setAutonomousLessonCoverNum(Integer autonomousLessonCoverNum) {
        this.autonomousLessonCoverNum = autonomousLessonCoverNum;
    }

    public Integer getAutonomousLessonNum() {
        return autonomousLessonNum;
    }

    public void setAutonomousLessonNum(Integer autonomousLessonNum) {
        this.autonomousLessonNum = autonomousLessonNum;
    }

    public BigDecimal getAutonomousLessonCoverage() {
        return autonomousLessonCoverage;
    }

    public void setAutonomousLessonCoverage(BigDecimal autonomousLessonCoverage) {
        this.autonomousLessonCoverage = autonomousLessonCoverage;
    }

}
