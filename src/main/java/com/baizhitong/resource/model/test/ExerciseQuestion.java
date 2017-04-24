package com.baizhitong.resource.model.test;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.baizhitong.resource.common.base.BaseEntity;

/**
 * 
 * 作业题目实体
 * 
 * @author creator ZhangQiang 2017年4月7日 上午10:04:41
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "exercise_question")
@SuppressWarnings("serial")
@Entity
public class ExerciseQuestion extends BaseEntity implements Serializable{

    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer   id;
    /** 机构编码 */
    private String    orgCode;
    /** 学科编码 */
    private String    subjectCode;
    /** 测试卷ID */
    private Integer   testId;
    /** 测试卷编码 */
    private String    testCode;
    /** 测试卷结构ID */
    private Integer   testStructureId;
    /** 测试卷结构编码 */
    private String    testStructureCode;
    /** 题目编码 */
    private String    questionCode;
    /** 测试卷中的顺序号 */
    private Integer   orderInTest;
    /** 学科题型编码 */
    private String    questionTypeSubject;
    /** 机构学科题型编码 */
    private String    questionTypeSubjectOrg;
    /** 题型中的顺序号 */
    private Integer   orderInType;
    /** 答案不允许上传附件 */
    private Integer   flagNoAttachment;
    /** 答题只能上传图片 */
    private Integer   flagOnlyImages;
    /** 答案防粘贴 */
    private Integer   flagPreventPaste;
    /** 题目内可否乱序出题 */
    private Integer   canRandomInQuestion;
    /** 最低字数限制 */
    private Integer   minChars;
    /** 题目分数 */
    private Float     score;
    /** 试题来源类型 */
    private Integer   sourceType;
    /** 是否删除 */
    private Integer   flagDelete;
    /** 更新者 */
    private String    modifier;
    /** 更新时间 */
    private Timestamp modifyTime;
    /** 更新者IP */
    private String    modifierIP;
    /** 系统版本号 */
    private Integer   sysVersion;
    
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
    public String getSubjectCode() {
        return subjectCode;
    }
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }
    public Integer getTestId() {
        return testId;
    }
    public void setTestId(Integer testId) {
        this.testId = testId;
    }
    public String getTestCode() {
        return testCode;
    }
    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }
    public Integer getTestStructureId() {
        return testStructureId;
    }
    public void setTestStructureId(Integer testStructureId) {
        this.testStructureId = testStructureId;
    }
    public String getTestStructureCode() {
        return testStructureCode;
    }
    public void setTestStructureCode(String testStructureCode) {
        this.testStructureCode = testStructureCode;
    }
    public String getQuestionCode() {
        return questionCode;
    }
    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }
    public Integer getOrderInTest() {
        return orderInTest;
    }
    public void setOrderInTest(Integer orderInTest) {
        this.orderInTest = orderInTest;
    }
    public String getQuestionTypeSubject() {
        return questionTypeSubject;
    }
    public void setQuestionTypeSubject(String questionTypeSubject) {
        this.questionTypeSubject = questionTypeSubject;
    }
    public String getQuestionTypeSubjectOrg() {
        return questionTypeSubjectOrg;
    }
    public void setQuestionTypeSubjectOrg(String questionTypeSubjectOrg) {
        this.questionTypeSubjectOrg = questionTypeSubjectOrg;
    }
    public Integer getOrderInType() {
        return orderInType;
    }
    public void setOrderInType(Integer orderInType) {
        this.orderInType = orderInType;
    }
    public Integer getFlagNoAttachment() {
        return flagNoAttachment;
    }
    public void setFlagNoAttachment(Integer flagNoAttachment) {
        this.flagNoAttachment = flagNoAttachment;
    }
    public Integer getFlagOnlyImages() {
        return flagOnlyImages;
    }
    public void setFlagOnlyImages(Integer flagOnlyImages) {
        this.flagOnlyImages = flagOnlyImages;
    }
    public Integer getFlagPreventPaste() {
        return flagPreventPaste;
    }
    public void setFlagPreventPaste(Integer flagPreventPaste) {
        this.flagPreventPaste = flagPreventPaste;
    }
    public Integer getCanRandomInQuestion() {
        return canRandomInQuestion;
    }
    public void setCanRandomInQuestion(Integer canRandomInQuestion) {
        this.canRandomInQuestion = canRandomInQuestion;
    }
    public Integer getMinChars() {
        return minChars;
    }
    public void setMinChars(Integer minChars) {
        this.minChars = minChars;
    }
    public Float getScore() {
        return score;
    }
    public void setScore(Float score) {
        this.score = score;
    }
    public Integer getSourceType() {
        return sourceType;
    }
    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }
    public Integer getFlagDelete() {
        return flagDelete;
    }
    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
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
    public Integer getSysVersion() {
        return sysVersion;
    }
    public void setSysVersion(Integer sysVersion) {
        this.sysVersion = sysVersion;
    }
    
}
