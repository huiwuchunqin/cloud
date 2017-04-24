package com.baizhitong.resource.model.report;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 章节资源覆盖率 PlatformResCoverageChapter TODO
 * 
 * @author creator BZT 2016年7月8日 上午10:46:44
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "platform_res_coverage_chapter")
@Entity
public class PlatformResCoverageChapter {
    @Id
    /** 主键 */
    private Integer   id;
    /** 基准日 */
    private Integer   baseDate;
    /** 学年代码 */
    private String    studyYearCode;
    /** 学年学期代码 */
    private String    yearTermCode;
    /** 学期代码 */
    private String    termCode;
    /** 基准月 */
    private Integer   baseMonth;
    /** 是否月基准 */
    private Integer   flagBaseMonth;
    /** 基准周 */
    private Integer   weekNum;
    /** 是否周基准 */
    private Integer   flagBaseWeek;
    /** 当前层数 */
    private Integer   chapterLevel;
    /** 教材编码 */
    private String    tbCode;
    /** 教材名称 */
    private String    tbName;
    /** 父章节编号 */
    private String    chapterPCode;
    /** 章节编号 */
    private String    chapterCode;
    /** 章节名称 */
    private String    chapterName;
    /** 视频资源数 */
    private Integer   resVideoNum;
    /** 视频资源已覆盖节点数 */
    private Integer   resVideoCoverNum;
    /** 视频资源覆盖率 */
    private Float     resVideoCoverage;
    /** 文档资源数 */
    private Integer   resDocNum;
    /** 文档资源已覆盖节点数 */
    private Integer   resDocCoverNum;
    /** 文档资源覆盖率 */
    private Float     resDocCoverage;
    /** 测验资源数 */
    private Integer   resTestNum;
    /** 测验资源已覆盖节点数 */
    private Integer   resTestCoverNum;
    /** 测验覆盖率 */
    private Float     resTestCoverage;
    /** 题目资源数 */
    private Integer   resQuestionNum;
    /** 题目资源已覆盖节点数 */
    private Integer   resQuestionCoverNum;
    /** 题目资源覆盖率 */
    private Float     resQuestionCoverage;
    /** 资源总数 */
    private Integer   resNum;
    /** 资源已覆盖节点总数 */
    private Integer   resCoverNum;
    /** 总覆盖率 */
    private Float     resCoverage;
    /** 更新方式 */
    private Integer   updateType;
    /** 更新者 */
    private String    modifier;
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

    public Integer getChapterLevel() {
        return chapterLevel;
    }

    public void setChapterLevel(Integer chapterLevel) {
        this.chapterLevel = chapterLevel;
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

    public Integer getResVideoNum() {
        return resVideoNum;
    }

    public void setResVideoNum(Integer resVideoNum) {
        this.resVideoNum = resVideoNum;
    }

    public Integer getResVideoCoverNum() {
        return resVideoCoverNum;
    }

    public void setResVideoCoverNum(Integer resVideoCoverNum) {
        this.resVideoCoverNum = resVideoCoverNum;
    }

    public Float getResVideoCoverage() {
        return resVideoCoverage;
    }

    public void setResVideoCoverage(Float resVideoCoverage) {
        this.resVideoCoverage = resVideoCoverage;
    }

    public Integer getResDocNum() {
        return resDocNum;
    }

    public void setResDocNum(Integer resDocNum) {
        this.resDocNum = resDocNum;
    }

    public Integer getResDocCoverNum() {
        return resDocCoverNum;
    }

    public void setResDocCoverNum(Integer resDocCoverNum) {
        this.resDocCoverNum = resDocCoverNum;
    }

    public Float getResDocCoverage() {
        return resDocCoverage;
    }

    public void setResDocCoverage(Float resDocCoverage) {
        this.resDocCoverage = resDocCoverage;
    }

    public Integer getResTestNum() {
        return resTestNum;
    }

    public void setResTestNum(Integer resTestNum) {
        this.resTestNum = resTestNum;
    }

    public Integer getResTestCoverNum() {
        return resTestCoverNum;
    }

    public void setResTestCoverNum(Integer resTestCoverNum) {
        this.resTestCoverNum = resTestCoverNum;
    }

    public Float getResTestCoverage() {
        return resTestCoverage;
    }

    public void setResTestCoverage(Float resTestCoverage) {
        this.resTestCoverage = resTestCoverage;
    }

    public Integer getResQuestionNum() {
        return resQuestionNum;
    }

    public void setResQuestionNum(Integer resQuestionNum) {
        this.resQuestionNum = resQuestionNum;
    }

    public Integer getResQuestionCoverNum() {
        return resQuestionCoverNum;
    }

    public void setResQuestionCoverNum(Integer resQuestionCoverNum) {
        this.resQuestionCoverNum = resQuestionCoverNum;
    }

    public Float getResQuestionCoverage() {
        return resQuestionCoverage;
    }

    public void setResQuestionCoverage(Float resQuestionCoverage) {
        this.resQuestionCoverage = resQuestionCoverage;
    }

    public Integer getResNum() {
        return resNum;
    }

    public void setResNum(Integer resNum) {
        this.resNum = resNum;
    }

    public Integer getResCoverNum() {
        return resCoverNum;
    }

    public void setResCoverNum(Integer resCoverNum) {
        this.resCoverNum = resCoverNum;
    }

    public Float getResCoverage() {
        return resCoverage;
    }

    public void setResCoverage(Float resCoverage) {
        this.resCoverage = resCoverage;
    }

    public Integer getUpdateType() {
        return updateType;
    }

    public void setUpdateType(Integer updateType) {
        this.updateType = updateType;
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

    public Integer getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Integer baseDate) {
        this.baseDate = baseDate;
    }

    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getYearTermCode() {
        return yearTermCode;
    }

    public void setYearTermCode(String yearTermCode) {
        this.yearTermCode = yearTermCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public Integer getBaseMonth() {
        return baseMonth;
    }

    public void setBaseMonth(Integer baseMonth) {
        this.baseMonth = baseMonth;
    }

    public Integer getFlagBaseMonth() {
        return flagBaseMonth;
    }

    public void setFlagBaseMonth(Integer flagBaseMonth) {
        this.flagBaseMonth = flagBaseMonth;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getFlagBaseWeek() {
        return flagBaseWeek;
    }

    public void setFlagBaseWeek(Integer flagBaseWeek) {
        this.flagBaseWeek = flagBaseWeek;
    }

}
