package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 平台-资源中心设置
 * 
 * @author creator ZhangQiang 2016年9月19日 上午9:22:03
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_platform_settings_res")
public class SharePlatformSettingsRes implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 系统ID */
    @Id
    private String            gid;
    /** 上传时知识点必输-媒体 */
    private Integer           kpRequiredWhenMediaUpload;
    /** 上传时章节必输-媒体 */
    private Integer           chapterRequiredWhenMediaUpload;
    /** 上传时封面必输-媒体 */
    private Integer           coverRequiredWhenMediaUpload;
    /** 上传时知识点必输-文档 */
    private Integer           kpRequiredWhenDocUpload;
    /** 上传时章节必输-文档 */
    private Integer           chapterRequiredWhenDocUpload;
    /** 上传时封面必输-文档 */
    private Integer           coverRequiredWhenDocUpload;
    /** 共享时知识点必输-媒体 */
    private Integer           kpRequiredWhenMediaShare;
    /** 共享时知识点必输-文档 */
    private Integer           kpRequiredWhenDocShare;
    /** 共享时知识点必输-测试卷 */
    private Integer           kpRequiredWhenTestShare;
    /** 共享时知识点必输-题目 */
    private Integer           kpRequiredWhenQuestionShare;
    /** 保存时知识点必输-题目 */
    private Integer           kpRequiredWhenQuestionSave;
    /** 保存时教材章节必输-题目 */
    private Integer           chapterRequiredWhenQuestionSave;
    /** 保存时学段必输-题目 */
    private Integer           sectionRequiredWhenQuestionSave;
    /** 保存时学科必输-题目 */
    private Integer           subjectRequiredWhenQuestionSave;
    /** 保存时年级必输-题目 */
    private Integer           gradeRequiredWhenQuestionSave;
    /** 保存时难度必输-题目 */
    private Integer           difficultyRequiredWhenQuestionSave;
    /** 保存时维度监测点必输-题目 */
    private Integer           dpRequiredWhenQuestionSave;
    /** 保存时答案必输-题目 */
    private Integer           answerRequiredWhenQuestionSave;
    /** 保存时解析必输-题目 */
    private Integer           analysisRequiredWhenQuestionSave;
    /** 资源是否允许评论 */
    private Integer           flagResAllowComment;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改者 */
    private String            modifier;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Integer getKpRequiredWhenMediaUpload() {
        return kpRequiredWhenMediaUpload;
    }

    public void setKpRequiredWhenMediaUpload(Integer kpRequiredWhenMediaUpload) {
        this.kpRequiredWhenMediaUpload = kpRequiredWhenMediaUpload;
    }

    public Integer getChapterRequiredWhenMediaUpload() {
        return chapterRequiredWhenMediaUpload;
    }

    public void setChapterRequiredWhenMediaUpload(Integer chapterRequiredWhenMediaUpload) {
        this.chapterRequiredWhenMediaUpload = chapterRequiredWhenMediaUpload;
    }

    public Integer getCoverRequiredWhenMediaUpload() {
        return coverRequiredWhenMediaUpload;
    }

    public void setCoverRequiredWhenMediaUpload(Integer coverRequiredWhenMediaUpload) {
        this.coverRequiredWhenMediaUpload = coverRequiredWhenMediaUpload;
    }

    public Integer getKpRequiredWhenDocUpload() {
        return kpRequiredWhenDocUpload;
    }

    public void setKpRequiredWhenDocUpload(Integer kpRequiredWhenDocUpload) {
        this.kpRequiredWhenDocUpload = kpRequiredWhenDocUpload;
    }

    public Integer getChapterRequiredWhenDocUpload() {
        return chapterRequiredWhenDocUpload;
    }

    public void setChapterRequiredWhenDocUpload(Integer chapterRequiredWhenDocUpload) {
        this.chapterRequiredWhenDocUpload = chapterRequiredWhenDocUpload;
    }

    public Integer getCoverRequiredWhenDocUpload() {
        return coverRequiredWhenDocUpload;
    }

    public void setCoverRequiredWhenDocUpload(Integer coverRequiredWhenDocUpload) {
        this.coverRequiredWhenDocUpload = coverRequiredWhenDocUpload;
    }

    public Integer getKpRequiredWhenMediaShare() {
        return kpRequiredWhenMediaShare;
    }

    public void setKpRequiredWhenMediaShare(Integer kpRequiredWhenMediaShare) {
        this.kpRequiredWhenMediaShare = kpRequiredWhenMediaShare;
    }

    public Integer getKpRequiredWhenDocShare() {
        return kpRequiredWhenDocShare;
    }

    public void setKpRequiredWhenDocShare(Integer kpRequiredWhenDocShare) {
        this.kpRequiredWhenDocShare = kpRequiredWhenDocShare;
    }

    public Integer getKpRequiredWhenTestShare() {
        return kpRequiredWhenTestShare;
    }

    public void setKpRequiredWhenTestShare(Integer kpRequiredWhenTestShare) {
        this.kpRequiredWhenTestShare = kpRequiredWhenTestShare;
    }

    public Integer getKpRequiredWhenQuestionShare() {
        return kpRequiredWhenQuestionShare;
    }

    public void setKpRequiredWhenQuestionShare(Integer kpRequiredWhenQuestionShare) {
        this.kpRequiredWhenQuestionShare = kpRequiredWhenQuestionShare;
    }

    public Integer getKpRequiredWhenQuestionSave() {
        return kpRequiredWhenQuestionSave;
    }

    public void setKpRequiredWhenQuestionSave(Integer kpRequiredWhenQuestionSave) {
        this.kpRequiredWhenQuestionSave = kpRequiredWhenQuestionSave;
    }

    public Integer getChapterRequiredWhenQuestionSave() {
        return chapterRequiredWhenQuestionSave;
    }

    public void setChapterRequiredWhenQuestionSave(Integer chapterRequiredWhenQuestionSave) {
        this.chapterRequiredWhenQuestionSave = chapterRequiredWhenQuestionSave;
    }

    public Integer getSectionRequiredWhenQuestionSave() {
        return sectionRequiredWhenQuestionSave;
    }

    public void setSectionRequiredWhenQuestionSave(Integer sectionRequiredWhenQuestionSave) {
        this.sectionRequiredWhenQuestionSave = sectionRequiredWhenQuestionSave;
    }

    public Integer getSubjectRequiredWhenQuestionSave() {
        return subjectRequiredWhenQuestionSave;
    }

    public void setSubjectRequiredWhenQuestionSave(Integer subjectRequiredWhenQuestionSave) {
        this.subjectRequiredWhenQuestionSave = subjectRequiredWhenQuestionSave;
    }

    public Integer getGradeRequiredWhenQuestionSave() {
        return gradeRequiredWhenQuestionSave;
    }

    public void setGradeRequiredWhenQuestionSave(Integer gradeRequiredWhenQuestionSave) {
        this.gradeRequiredWhenQuestionSave = gradeRequiredWhenQuestionSave;
    }

    public Integer getDifficultyRequiredWhenQuestionSave() {
        return difficultyRequiredWhenQuestionSave;
    }

    public void setDifficultyRequiredWhenQuestionSave(Integer difficultyRequiredWhenQuestionSave) {
        this.difficultyRequiredWhenQuestionSave = difficultyRequiredWhenQuestionSave;
    }

    public Integer getDpRequiredWhenQuestionSave() {
        return dpRequiredWhenQuestionSave;
    }

    public void setDpRequiredWhenQuestionSave(Integer dpRequiredWhenQuestionSave) {
        this.dpRequiredWhenQuestionSave = dpRequiredWhenQuestionSave;
    }

    public Integer getAnswerRequiredWhenQuestionSave() {
        return answerRequiredWhenQuestionSave;
    }

    public void setAnswerRequiredWhenQuestionSave(Integer answerRequiredWhenQuestionSave) {
        this.answerRequiredWhenQuestionSave = answerRequiredWhenQuestionSave;
    }

    public Integer getAnalysisRequiredWhenQuestionSave() {
        return analysisRequiredWhenQuestionSave;
    }

    public void setAnalysisRequiredWhenQuestionSave(Integer analysisRequiredWhenQuestionSave) {
        this.analysisRequiredWhenQuestionSave = analysisRequiredWhenQuestionSave;
    }

    public Integer getFlagResAllowComment() {
        return flagResAllowComment;
    }

    public void setFlagResAllowComment(Integer flagResAllowComment) {
        this.flagResAllowComment = flagResAllowComment;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
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

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

}
