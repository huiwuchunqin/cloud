package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * ResShareCheck 资源共享审核
 * 
 * @author creator gaow 2016年3月26日 上午11:24:13
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "res_share_check")
public class ResShareCheck implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 资源ID */
    @Id
    @Column(insertable = false,
            updatable = false)
    /** 资源ID */
    private Integer           id;
    /** 资源编码 */
    private String            resCode;
    /** 资源分类（一级） */
    private Integer           resTypeL1;
    /** 资源分类（二级） */
    private String            resTypeL2;
    /** 资源名称 */
    private String            resName;
    /** 资源描述 */
    private String            resDesc;

    /** 资源查看路径 */
    private String            resAccessPath;
    /** 学科编码 */
    private String            subjectCode;
    /** 学段编码 */
    private String            sectionCode;
    /** 年级编码 */
    private String            gradeCode;
    /** 分享级别 */
    private Integer           shareLevel;
    /** 分享审核中级别 */
    private Integer           shareCheckLevel;
    /** 分享审核中状态 */
    private Integer           shareCheckStatus;
    /** 申请人代码 */
    private String            applierCode;
    /** 申请人姓名 */
    private String            applierName;
    /** 申请人机构代码 */
    private String            applierOrgCode;
    /** 申请人机构名称 */
    private String            applierOrgName;
    /** 申请时间 */
    private Timestamp         applyTime;
    /** 申请理由 */
    private String            applyReason;

    /** 审核人代码 */
    private String            checkerCode;
    /** 审核人姓名 */
    private String            checkerName;
    /** 审核人机构代码 */
    private String            checkerOrgCode;
    /** 审核人机构名称 */
    private String            checkerOrgName;
    /** 审核时间 */
    private Timestamp         checkTime;
    /** 审核意见 */
    private String            checkComments;
    /** 备注 */
    private String            memo;
    /** 是否删除 */
    private Integer           flagDelete;
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

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public Integer getResTypeL1() {
        return resTypeL1;
    }

    public void setResTypeL1(Integer resTypeL1) {
        this.resTypeL1 = resTypeL1;
    }

    public String getResTypeL2() {
        return resTypeL2;
    }

    public void setResTypeL2(String resTypeL2) {
        this.resTypeL2 = resTypeL2;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
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

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public String getResAccessPath() {
        return resAccessPath;
    }

    public void setResAccessPath(String resAccessPath) {
        this.resAccessPath = resAccessPath;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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

    public String getApplierCode() {
        return applierCode;
    }

    public void setApplierCode(String applierCode) {
        this.applierCode = applierCode;
    }

    public String getApplierName() {
        return applierName;
    }

    public void setApplierName(String applierName) {
        this.applierName = applierName;
    }

    public String getApplierOrgCode() {
        return applierOrgCode;
    }

    public void setApplierOrgCode(String applierOrgCode) {
        this.applierOrgCode = applierOrgCode;
    }

    public String getApplierOrgName() {
        return applierOrgName;
    }

    public void setApplierOrgName(String applierOrgName) {
        this.applierOrgName = applierOrgName;
    }

    public Timestamp getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Timestamp applyTime) {
        this.applyTime = applyTime;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getCheckerCode() {
        return checkerCode;
    }

    public void setCheckerCode(String checkerCode) {
        this.checkerCode = checkerCode;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getCheckerOrgCode() {
        return checkerOrgCode;
    }

    public void setCheckerOrgCode(String checkerOrgCode) {
        this.checkerOrgCode = checkerOrgCode;
    }

    public String getCheckerOrgName() {
        return checkerOrgName;
    }

    public void setCheckerOrgName(String checkerOrgName) {
        this.checkerOrgName = checkerOrgName;
    }

    public Timestamp getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Timestamp checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckComments() {
        return checkComments;
    }

    public void setCheckComments(String checkComments) {
        this.checkComments = checkComments;
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
