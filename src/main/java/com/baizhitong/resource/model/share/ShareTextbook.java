package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 
 * 教材实体类
 * 
 * @author creator gaow 2016年1月12日 上午9:28:01
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "share_textbook")
@Entity
@SuppressWarnings("serial")
public class ShareTextbook implements Serializable {
    /** 系统id */
    @Id
    private String    gid;
    /** 学段编码 **/
    private String    sectionCode;
    /** 年级编码 **/
    private String    gradeCode;
    /** 学科编码 **/
    private String    subjectCode;
    /** 教材版本编码 **/
    private String    textbookVerCode;
    /** 学期编码 **/
    private String    termCode;
    /** 备注 **/
    private String    memo;
    /** 教材编码 **/
    private String    tbCode;
    /** 教材编码 **/
    private String    tbName;
    /** 修改程序 **/
    private String    modifyPgm;
    /** 修改时间 **/
    private Timestamp modifyTime;
    /** 修改者IP **/
    private String    modifyIP;
    /** 是否删除 **/
    private Integer   flagDelete;

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

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getTextbookVerCode() {
        return textbookVerCode;
    }

    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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

}
