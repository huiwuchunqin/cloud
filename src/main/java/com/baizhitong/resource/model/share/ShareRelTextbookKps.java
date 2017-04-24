package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 教材与知识点体系关联表
 * 
 * @author creator gaow 2016年1月7日 下午4:43:07
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "share_rel_textbook_kps")
public class ShareRelTextbookKps implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 系统ID */
    private @Id String        gid;
    /** 学段编码 */
    private String            sectionCode;
    /** 年级编码 */
    private String            gradeCode;
    /** 学科编码 */
    private String            subjectCode;
    /** 材版本编码 */
    private String            textbookVerCode;
    /** 教材编码 */
    private String            textbookCode;
    /** 知识点体系编码 */
    private String            kpSerialCode;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改者IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 学期代码 */
    private String            termCode;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
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

    public String getTextbookCode() {
        return textbookCode;
    }

    public void setTextbookCode(String textbookCode) {
        this.textbookCode = textbookCode;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getTextbookVerCode() {
        return textbookVerCode;
    }

    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
    }

    public String getKpSerialCode() {
        return kpSerialCode;
    }

    public void setKpSerialCode(String kpSerialCode) {
        this.kpSerialCode = kpSerialCode;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
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
