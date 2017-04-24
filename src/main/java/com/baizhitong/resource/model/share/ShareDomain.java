package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 机构域信息 ShareDomain TODO
 * 
 * @author creator gaow 2017年3月7日 上午10:28:55
 * @author updater
 *
 * @version 1.0.0
 */
@Table(name = "share_domain")
@Entity
public class ShareDomain implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(insertable = true,
            updatable = false)
    /** 主键 */
    private Integer           id;
    /** 域名 */
    private String            domainURL;
    /** 域名logo */
    private String            domainLogo;
    /** 域名名称 */
    private String            domainName;
    /** 首页地址 */
    private String            indexPage;
    /** 备注 */
    private String            memo;
    /** 是否删除 */
    private Integer           flagDelete;
    /** 创建者 */
    private String            creator;
    /** 创建时间 */
    private Timestamp         createTime;
    /** 穿件id */
    private String            creatorIP;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改者 */
    private String            modifier;
    /** 修改时间 */
    private Timestamp         modifyTime;
    /** 修改ip */
    private String            modifyIP;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getDomainURL() {
        return domainURL;
    }

    public void setDomainURL(String domainURL) {
        this.domainURL = domainURL;
    }

    public String getDomainLogo() {
        return domainLogo;
    }

    public void setDomainLogo(String domainLogo) {
        this.domainLogo = domainLogo;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(String indexPage) {
        this.indexPage = indexPage;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getCreatorIP() {
        return creatorIP;
    }

    public void setCreatorIP(String creatorIP) {
        this.creatorIP = creatorIP;
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

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }
}
