package com.baizhitong.resource.model.res;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 个人文件夹表
 * 
 * @author lusm 2015/12/11
 *
 */
@Entity
@Table(name = "res_personal_folder")
public class ResPersonalFolder implements Serializable {
    private static final long                                          serialVersionUID = 1L;
    /** 主键 */
    private @Id @Column(insertable = false,
                        updatable = false) Integer                     id;
    /** 机构编码 */
    private String                                                     orgCode;
    /** 用户代码 */
    private String                                                     userCode;
    /** 文件夹名称 */
    private String                                                     folderName;
    /** 上级文件夹 */
    private Integer                                                    parentId;
    /** 文件夹图标相对路径 */
    private String                                                     folderIcon;
    /** 文件夹读写权限 */
    private Integer                                                    folderStatus;
    /** 是否系统文件夹 */
    private Integer                                                    flagSysFolder;
    /** 创建者 */
    private String                                                     creator;
    /** 创建时间 */
    private Timestamp                                                  createTime;
    /** 创建者 */
    private String                                                     creatorIP;
    /** 更新者 */
    private String                                                     modifier;
    /** 更新时间 */
    private Timestamp                                                  modifyTime;
    /** 更新者 */
    private String                                                     modifierIP;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getFolderIcon() {
        return folderIcon;
    }

    public void setFolderIcon(String folderIcon) {
        this.folderIcon = folderIcon;
    }

    public Integer getFolderStatus() {
        return folderStatus;
    }

    public void setFolderStatus(Integer folderStatus) {
        this.folderStatus = folderStatus;
    }

    public Integer getFlagSysFolder() {
        return flagSysFolder;
    }

    public void setFlagSysFolder(Integer flagSysFolder) {
        this.flagSysFolder = flagSysFolder;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCreatorIP() {
        return creatorIP;
    }

    public void setCreatorIP(String creatorIP) {
        this.creatorIP = creatorIP;
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
