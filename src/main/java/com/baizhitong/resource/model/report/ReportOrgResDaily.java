package com.baizhitong.resource.model.report;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机构日资源统计 ReportOrgResDaily TODO
 * 
 * @author creator BZT 2016年7月6日 下午5:52:05
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "report_org_res_daily")
public class ReportOrgResDaily {
    @Id
    /** 主键 */
    private Integer   id;
    /** 机构编码 */
    private String    orgCode;
    /** 学段编码 */
    private String    sectionCode;
    /** 学年学期代码 */
    private String    yearTermCode;
    /** 学年代码 */
    private String    studyYearCode;
    /** 学期代码 */
    private String    termCode;
    /** 基准日 */
    private Integer   baseDate;
    /** 基准月 */
    private Integer   baseMonth;
    /** 所在周次 */
    private Integer   weekNum;
    /** 个人私有资源总数 */
    private Integer   share_level_10;
    /** 校内共享资源总数 */
    private Integer   share_level_20;
    /** 区域共享资源总数 */
    private Integer   share_level_30;
    /** 资源-媒体总数 */
    private Integer   res_media;
    /** 资源-特色总数 */
    private Integer   res_special;
    /** 资源-文档总数 */
    private Integer   res_doc;
    /** 资源-练习总数 */
    private Integer   res_exercise;
    /** 资源-题目总数 */
    private Integer   res_question;
    /** 资源-考试总数 */
    private Integer   res_exam;
    /** 资源-课时总数 */
    private Integer   res_lesson;
    /** 资源-课程总数 */
    private Integer   res_course;
    /** 资源-组合资源总数 */
    private Integer   res_composite;
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

    public String getYearTermCode() {
        return yearTermCode;
    }

    public void setYearTermCode(String yearTermCode) {
        this.yearTermCode = yearTermCode;
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

    public Integer getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Integer baseDate) {
        this.baseDate = baseDate;
    }

    public Integer getBaseMonth() {
        return baseMonth;
    }

    public void setBaseMonth(Integer baseMonth) {
        this.baseMonth = baseMonth;
    }

    public Integer getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(Integer weekNum) {
        this.weekNum = weekNum;
    }

    public Integer getShare_level_10() {
        return share_level_10;
    }

    public void setShare_level_10(Integer share_level_10) {
        this.share_level_10 = share_level_10;
    }

    public Integer getShare_level_20() {
        return share_level_20;
    }

    public void setShare_level_20(Integer share_level_20) {
        this.share_level_20 = share_level_20;
    }

    public Integer getShare_level_30() {
        return share_level_30;
    }

    public void setShare_level_30(Integer share_level_30) {
        this.share_level_30 = share_level_30;
    }

    public Integer getRes_media() {
        return res_media;
    }

    public void setRes_media(Integer res_media) {
        this.res_media = res_media;
    }

    public Integer getRes_special() {
        return res_special;
    }

    public void setRes_special(Integer res_special) {
        this.res_special = res_special;
    }

    public Integer getRes_doc() {
        return res_doc;
    }

    public void setRes_doc(Integer res_doc) {
        this.res_doc = res_doc;
    }

    public Integer getRes_exercise() {
        return res_exercise;
    }

    public void setRes_exercise(Integer res_exercise) {
        this.res_exercise = res_exercise;
    }

    public Integer getRes_question() {
        return res_question;
    }

    public void setRes_question(Integer res_question) {
        this.res_question = res_question;
    }

    public Integer getRes_exam() {
        return res_exam;
    }

    public void setRes_exam(Integer res_exam) {
        this.res_exam = res_exam;
    }

    public Integer getRes_lesson() {
        return res_lesson;
    }

    public void setRes_lesson(Integer res_lesson) {
        this.res_lesson = res_lesson;
    }

    public Integer getRes_course() {
        return res_course;
    }

    public void setRes_course(Integer res_course) {
        this.res_course = res_course;
    }

    public Integer getRes_composite() {
        return res_composite;
    }

    public void setRes_composite(Integer res_composite) {
        this.res_composite = res_composite;
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

}
