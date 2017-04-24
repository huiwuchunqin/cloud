package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;

import com.baizhitong.resource.model.share.ShareRelTextbookKps;
import com.baizhitong.utils.DataUtils;

/**
 * 
 * 教材与知识体系关联表
 * 
 * @author creator gaow 2016年1月13日 下午5:19:24
 * @author updater
 *
 * @version 1.0.0
 */
public class ShareRelTextbookKpsVo {
    /** 主键 */
    private String    gid;
    /** 学科编码 */
    private String    subjectCode;
    /** 教材版本编码 */
    private String    textbookVerCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 学段编码 */
    private String    sectionCode;
    /** 知识点体系编码 */
    private String    kpSerialCode;
    /** 显示顺序 */
    private Integer   dispOrder;
    /** 修改日期 */
    private Timestamp modifyTime;
    /** 修改日期文本 */
    private String    modifyTimeText;
    /** 修改IP */
    private String    modifyIP;
    /** 学期 */
    private String    termCode;
    /** 修改程序 */
    private String    modifyPgm;
    /** 教材编码 */
    private String    textbookCode;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getTextbookVerCode() {
        return textbookVerCode;
    }

    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getKpSerialCode() {
        return kpSerialCode;
    }

    public void setKpSerialCode(String kpSerialCode) {
        this.kpSerialCode = kpSerialCode;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyTimeText() {
        return modifyTimeText;
    }

    public void setModifyTimeText(String modifyTimeText) {
        this.modifyTimeText = modifyTimeText;
    }

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public String getTextbookCode() {
        return textbookCode;
    }

    public void setTextbookCode(String textbookCode) {
        this.textbookCode = textbookCode;
    }

    /**
     * vo转实体 ()<br>
     * 
     * @param shareRelTextbookKpsVo
     * @return
     */
    public static ShareRelTextbookKps voToEntity(ShareRelTextbookKpsVo shareRelTextbookKpsVo) {
        ShareRelTextbookKps shareRelTextbookKps = new ShareRelTextbookKps();
        DataUtils.copySimpleObject(shareRelTextbookKpsVo, shareRelTextbookKps);
        return shareRelTextbookKps;
    }
}
