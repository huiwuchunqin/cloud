package com.baizhitong.resource.model.share;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * ShareQuestionTypeSubject
 * 
 * @author creator BZT 2016年5月11日 下午4:58:26
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "share_question_type_subject")
@Entity
public class ShareQuestionTypeSubject {
    // id
    @Id
    private String    gid;
    // 题型
    private String    questionType;
    // 学科编吗
    private String    subjectCode;
    // 支持系统批阅
    private Integer   flagSysEvaluate;
    // 编码
    private String    code;
    // 名称
    private String    name;
    // 排序
    private Integer   dispOrder;
    // 修改程序
    private String    modifyPgm;
    // 修改时间
    private Timestamp modifyTime;
    // 修改地址
    private String    modifyIP;
    // 是否删除
    private Integer   flagDelete;
    // 系统版本号
    private Integer   sysVer;
    // 是否支持听力
    private Integer   flagSupportListening;
    // 主观题默认显示文本
    private Integer   textType;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getFlagSysEvaluate() {
        return flagSysEvaluate;
    }

    public void setFlagSysEvaluate(Integer flagSysEvaluate) {
        this.flagSysEvaluate = flagSysEvaluate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
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

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
    }

    public Integer getFlagSupportListening() {
        return flagSupportListening;
    }

    public void setFlagSupportListening(Integer flagSupportListening) {
        this.flagSupportListening = flagSupportListening;
    }

    public Integer getTextType() {
        return textType;
    }

    public void setTextType(Integer textType) {
        this.textType = textType;
    }
    
}
