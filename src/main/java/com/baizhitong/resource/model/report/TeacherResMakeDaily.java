package com.baizhitong.resource.model.report;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 
 * 6020.教师_日次_资源制作统计
 * 
 * @author creator zhangqiang 2016年7月18日 上午11:38:50
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "teacher_res_make_daily")
@Entity
public class TeacherResMakeDaily implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Integer           id;
    /** 机构编码 */
    private String            orgCode;
    /** 机构名称 */
    private String            orgName;
    /** 用户代码 */
    private String            userCode;
    /** 用户姓名 */
    private String            userName;
    /** 学段编码 */
    private String            sectionCode;
    /** 学科编码 */
    private String            subjectCode;
    /** 基准日 */
    private Integer           baseDate;
    /** 是否月基准 */
    private Integer           flagBaseMonth;
    /** 基准月 */
    private Integer           baseMonth;
    /** 学年学期代码 */
    private String            yearTermCode;
    /** 学年代码 */
    private String            studyYearCode;
    /** 学期代码 */
    private String            termCode;
    /** 基准周 */
    private Integer           weekNum;
    /** 是否周基准 */
    private Integer           flagBaseWeek;
    /** 个人私有资源总数 */
    private Integer           shareLevelPrivate;
    /** 校内共享资源总数 */
    private Integer           shareLevelOrg;
    /** 区域共享资源总数 */
    private Integer           shareLevelArea;
    /** 资源-媒体总数-个人私有 */
    private Integer           resMediaNumPrivate;
    /** 资源-媒体总数-校内共享 */
    private Integer           resMediaNumOrg;
    /** 资源-媒体总数-区域共享 */
    private Integer           resMediaNumArea;
    /** 资源-文档总数-个人私有 */
    private Integer           resDocNumPrivate;
    /** 资源-文档总数-校内共享 */
    private Integer           resDocNumOrg;
    /** 资源-文档总数-区域共享 */
    private Integer           resDocNumArea;
    /** 资源-练习总数-个人私有 */
    private Integer           resExerciseNumPrivate;
    /** 资源-练习总数-校内共享 */
    private Integer           resExerciseNumOrg;
    /** 资源-练习总数-区域共享 */
    private Integer           resExerciseNumArea;
    /** 资源-题目总数-个人私有 */
    private Integer           resQuestionNumPrivate;
    /** 资源-题目总数-校内共享 */
    private Integer           resQuestionNumOrg;
    /** 资源-题目总数-区域共享 */
    private Integer           resQuestionNumArea;
    /** 资源-考试总数-个人私有 */
    private Integer           resExamNumPrivate;
    /** 资源-考试总数-校内共享 */
    private Integer           resExamNumOrg;
    /** 资源-考试总数-区域共享 */
    private Integer           resExamNumArea;
    /** 资源-课时总数-个人私有 */
    private Integer           resLessonNumPrivate;
    /** 资源-课时总数-校内共享 */
    private Integer           resLessonNumOrg;
    /** 资源-课时总数-区域共享 */
    private Integer           resLessonNumArea;
    /** 资源-课程总数-个人私有 */
    private Integer           resCourseNumPrivate;
    /** 资源-课程总数-校内共享 */
    private Integer           resCourseNumOrg;
    /** 资源-课程总数-区域共享 */
    private Integer           resCourseNumArea;
    /** 资源-组合资源总数-个人私有 */
    private Integer           resCompositeNumPrivate;
    /** 资源-组合资源总数-校内共享 */
    private Integer           resCompositeNumOrg;
    /** 资源-组合资源总数-区域共享 */
    private Integer           resCompositeNumArea;
    /** 更新方式 */
    private Integer           updateType;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Timestamp         modifyTime;
    /** 更新者IP */
    private String            modifierIP;

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getBaseDate() {
        return baseDate;
    }

    public void setBaseDate(Integer baseDate) {
        this.baseDate = baseDate;
    }

    public Integer getFlagBaseMonth() {
        return flagBaseMonth;
    }

    public void setFlagBaseMonth(Integer flagBaseMonth) {
        this.flagBaseMonth = flagBaseMonth;
    }

    public Integer getBaseMonth() {
        return baseMonth;
    }

    public void setBaseMonth(Integer baseMonth) {
        this.baseMonth = baseMonth;
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

    public Integer getShareLevelPrivate() {
        return shareLevelPrivate;
    }

    public void setShareLevelPrivate(Integer shareLevelPrivate) {
        this.shareLevelPrivate = shareLevelPrivate;
    }

    public Integer getShareLevelOrg() {
        return shareLevelOrg;
    }

    public void setShareLevelOrg(Integer shareLevelOrg) {
        this.shareLevelOrg = shareLevelOrg;
    }

    public Integer getShareLevelArea() {
        return shareLevelArea;
    }

    public void setShareLevelArea(Integer shareLevelArea) {
        this.shareLevelArea = shareLevelArea;
    }

    public Integer getResMediaNumPrivate() {
        return resMediaNumPrivate;
    }

    public void setResMediaNumPrivate(Integer resMediaNumPrivate) {
        this.resMediaNumPrivate = resMediaNumPrivate;
    }

    public Integer getResMediaNumOrg() {
        return resMediaNumOrg;
    }

    public void setResMediaNumOrg(Integer resMediaNumOrg) {
        this.resMediaNumOrg = resMediaNumOrg;
    }

    public Integer getResMediaNumArea() {
        return resMediaNumArea;
    }

    public void setResMediaNumArea(Integer resMediaNumArea) {
        this.resMediaNumArea = resMediaNumArea;
    }

    public Integer getResDocNumPrivate() {
        return resDocNumPrivate;
    }

    public void setResDocNumPrivate(Integer resDocNumPrivate) {
        this.resDocNumPrivate = resDocNumPrivate;
    }

    public Integer getResDocNumOrg() {
        return resDocNumOrg;
    }

    public void setResDocNumOrg(Integer resDocNumOrg) {
        this.resDocNumOrg = resDocNumOrg;
    }

    public Integer getResDocNumArea() {
        return resDocNumArea;
    }

    public void setResDocNumArea(Integer resDocNumArea) {
        this.resDocNumArea = resDocNumArea;
    }

    public Integer getResExerciseNumPrivate() {
        return resExerciseNumPrivate;
    }

    public void setResExerciseNumPrivate(Integer resExerciseNumPrivate) {
        this.resExerciseNumPrivate = resExerciseNumPrivate;
    }

    public Integer getResExerciseNumOrg() {
        return resExerciseNumOrg;
    }

    public void setResExerciseNumOrg(Integer resExerciseNumOrg) {
        this.resExerciseNumOrg = resExerciseNumOrg;
    }

    public Integer getResExerciseNumArea() {
        return resExerciseNumArea;
    }

    public void setResExerciseNumArea(Integer resExerciseNumArea) {
        this.resExerciseNumArea = resExerciseNumArea;
    }

    public Integer getResQuestionNumPrivate() {
        return resQuestionNumPrivate;
    }

    public void setResQuestionNumPrivate(Integer resQuestionNumPrivate) {
        this.resQuestionNumPrivate = resQuestionNumPrivate;
    }

    public Integer getResQuestionNumOrg() {
        return resQuestionNumOrg;
    }

    public void setResQuestionNumOrg(Integer resQuestionNumOrg) {
        this.resQuestionNumOrg = resQuestionNumOrg;
    }

    public Integer getResQuestionNumArea() {
        return resQuestionNumArea;
    }

    public void setResQuestionNumArea(Integer resQuestionNumArea) {
        this.resQuestionNumArea = resQuestionNumArea;
    }

    public Integer getResExamNumPrivate() {
        return resExamNumPrivate;
    }

    public void setResExamNumPrivate(Integer resExamNumPrivate) {
        this.resExamNumPrivate = resExamNumPrivate;
    }

    public Integer getResExamNumOrg() {
        return resExamNumOrg;
    }

    public void setResExamNumOrg(Integer resExamNumOrg) {
        this.resExamNumOrg = resExamNumOrg;
    }

    public Integer getResExamNumArea() {
        return resExamNumArea;
    }

    public void setResExamNumArea(Integer resExamNumArea) {
        this.resExamNumArea = resExamNumArea;
    }

    public Integer getResLessonNumPrivate() {
        return resLessonNumPrivate;
    }

    public void setResLessonNumPrivate(Integer resLessonNumPrivate) {
        this.resLessonNumPrivate = resLessonNumPrivate;
    }

    public Integer getResLessonNumOrg() {
        return resLessonNumOrg;
    }

    public void setResLessonNumOrg(Integer resLessonNumOrg) {
        this.resLessonNumOrg = resLessonNumOrg;
    }

    public Integer getResLessonNumArea() {
        return resLessonNumArea;
    }

    public void setResLessonNumArea(Integer resLessonNumArea) {
        this.resLessonNumArea = resLessonNumArea;
    }

    public Integer getResCourseNumPrivate() {
        return resCourseNumPrivate;
    }

    public void setResCourseNumPrivate(Integer resCourseNumPrivate) {
        this.resCourseNumPrivate = resCourseNumPrivate;
    }

    public Integer getResCourseNumOrg() {
        return resCourseNumOrg;
    }

    public void setResCourseNumOrg(Integer resCourseNumOrg) {
        this.resCourseNumOrg = resCourseNumOrg;
    }

    public Integer getResCourseNumArea() {
        return resCourseNumArea;
    }

    public void setResCourseNumArea(Integer resCourseNumArea) {
        this.resCourseNumArea = resCourseNumArea;
    }

    public Integer getResCompositeNumPrivate() {
        return resCompositeNumPrivate;
    }

    public void setResCompositeNumPrivate(Integer resCompositeNumPrivate) {
        this.resCompositeNumPrivate = resCompositeNumPrivate;
    }

    public Integer getResCompositeNumOrg() {
        return resCompositeNumOrg;
    }

    public void setResCompositeNumOrg(Integer resCompositeNumOrg) {
        this.resCompositeNumOrg = resCompositeNumOrg;
    }

    public Integer getResCompositeNumArea() {
        return resCompositeNumArea;
    }

    public void setResCompositeNumArea(Integer resCompositeNumArea) {
        this.resCompositeNumArea = resCompositeNumArea;
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
