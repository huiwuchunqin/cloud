package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 教材知识点实体类
 * 
 * @author creator Cuidc 2015/12/10
 * @author updater
 */
@Entity
@Table(name = "share_knowledge_point")
public class ShareKnowledgePoint implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 系统ID */
    private @Id String        gid;
    /** 学科编码 */
    private String            subjectCode;
    /** 学段编码 */
    private String            sectionCode;
    /** 知识点体系编码 */
    private String            kpSerialCode;
    /** 当前层数 */
    private Integer           level;
    /** 编码 */
    private String            code;
    /** 父节点编码 */
    private String            pcode;
    /** 名称 */
    private String            name;
    /** 描述 */
    private String            description;
    /** 显示顺序 */
    private Integer           dispOrder;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改日期 */
    private Timestamp         modifyTime;
    /** 修改IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;
    /** 是否删除 */
    private Integer           flagDelete;
    /** 知识点全名 */
    private String            fullName;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getKpSerialCode() {
        return kpSerialCode;
    }

    public void setKpSerialCode(String kpSerialCode) {
        this.kpSerialCode = kpSerialCode;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
