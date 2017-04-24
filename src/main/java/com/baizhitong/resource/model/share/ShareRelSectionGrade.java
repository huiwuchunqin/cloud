package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 学段与年级关联实体类
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
@Entity
@Table(name = "share_rel_section_grade")
public class ShareRelSectionGrade implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 系统ID */
    private @Id String        gid;
    /** 学段编码 */
    private String            sectionCode;
    /** 年级编码 */
    private String            gradeCode;
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

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
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

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ShareRelSectionGrade(String gid,
                                String sectionCode,
                                String gradeCode,
                                String modifyPgm,
                                Timestamp modifyTime,
                                String modifyIP,
                                Integer sysVer) {
        super();
        this.gid = gid;
        this.sectionCode = sectionCode;
        this.gradeCode = gradeCode;
        this.modifyPgm = modifyPgm;
        this.modifyTime = modifyTime;
        this.modifyIP = modifyIP;
        this.sysVer = sysVer;
    }

}
