package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学段与学科关联实体类
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
@Entity
@Table(name = "share_rel_section_subject")
public class ShareRelSectionSubject implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 系统ID */
    private @Id String        gid;
    /** 学段编码 */
    private String            sectionCode;
    /** 学科编码 */
    private String            subjectCode;
    private String            modifyPgm;            // 修改程序
    private Timestamp         modifyTime;           // 修改时间
    private String            modifyIP;             // 修改ip
    private Integer           sysVer;               // 版本号

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

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Integer getSysVer() {
        return sysVer;
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

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    public ShareRelSectionSubject(String gid,
                                  String sectionCode,
                                  String subjectCode,
                                  String modifyPgm,
                                  Timestamp modifyTime,
                                  String modifyIP,
                                  Integer sysVer) {
        super();
        this.gid = gid;
        this.sectionCode = sectionCode;
        this.subjectCode = subjectCode;
        this.modifyPgm = modifyPgm;
        this.modifyTime = modifyTime;
        this.modifyIP = modifyIP;
        this.sysVer = sysVer;
    }

}
