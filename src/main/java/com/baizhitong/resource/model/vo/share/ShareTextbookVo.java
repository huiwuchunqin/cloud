package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.baizhitong.resource.model.share.ShareTextbook;
import com.baizhitong.utils.DataUtils;

/**
 * 
 * 教材vo
 * 
 * @author creator gaow 2016年1月12日 上午11:03:42
 * @author updater
 *
 * @version 1.0.0
 */
public class ShareTextbookVo {
    /** 系统id */
    private String    gid;
    /** 学段编码 **/
    private String    sectionCode;
    /** 年级编码 **/
    private String    gradeCode;
    /** 学科编码 **/
    private String    subjectCode;
    /** 教材版本编码 **/
    private String    textbookVerCode;
    /** 教材编码 **/
    private String    textbookCode;
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

    // vo转实体
    public static ShareTextbook voToEntity(ShareTextbookVo vo) {
        ShareTextbook book = new ShareTextbook();
        DataUtils.copySimpleObject(vo, book);
        return book;

    }

    // 实体转vo
    public static ShareTextbookVo EntityToVo(ShareTextbook textbook) {
        ShareTextbookVo vo = new ShareTextbookVo();
        DataUtils.copySimpleObject(textbook, vo);
        return vo;
    }

    // 实体列表转vo列表
    public static List<ShareTextbookVo> EntityListToVoList(List<ShareTextbook> textbookList) {
        if (textbookList == null || textbookList.size() <= 0)
            return null;
        List<ShareTextbookVo> textbookVos = new ArrayList<ShareTextbookVo>();
        for (ShareTextbook textbook : textbookList) {
            textbookVos.add(EntityToVo(textbook));
        }
        return textbookVos;
    }

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

    public String getTextbookCode() {
        return textbookCode;
    }

    public void setTextbookCode(String textbookCode) {
        this.textbookCode = textbookCode;
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
