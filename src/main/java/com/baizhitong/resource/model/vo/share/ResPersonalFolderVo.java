package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.persistence.Id;

import com.baizhitong.resource.model.res.ResPersonalFolder;
import com.baizhitong.utils.DateUtils;

/**
 * 个人文件夹表
 * 
 * @author lusm 2015/12/11
 *
 */
public class ResPersonalFolderVo {

    /** 主键 */
    private @Id Integer id;
    /** 机构编码 */
    private String      orgCode;
    /** 文件夹名称 */
    private String      folderName;
    /** 用户代码 */
    private String      userCode;

    /** 上级文件夹ID */
    private Integer     parentId;
    /** 文件夹图标相对路径 */
    private String      folderIcon;
    /** 文件夹读写权限 */
    private Integer     folderStatus;
    /** 创建者 */
    private String      creator;
    /** 创建时间 */
    private Timestamp   createTime;
    /** 创建时间文本 */
    private String      createTimeText;
    /** 创建者IP */
    private String      creatorIP;
    /** 更新者 */
    private String      modifier;
    /** 更新时间 */
    private Timestamp   modifyTime;
    /** 更新时间文本 */
    private String      modifyTimeText;
    /** 更新者IP */
    private String      modifierIP;
    /** 是否是父节点 */
    private Boolean     isParent;
    /** 是否系统文件夹 */
    private Integer     flagSysFolder;

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public ResPersonalFolderVo(ResPersonalFolder resPersonalFolder) {
        this.id = resPersonalFolder.getId();
        this.orgCode = resPersonalFolder.getOrgCode();
        this.folderName = resPersonalFolder.getFolderName();
        this.parentId = resPersonalFolder.getParentId();
        this.folderIcon = resPersonalFolder.getFolderIcon();
        this.folderStatus = resPersonalFolder.getFolderStatus();
        this.creator = resPersonalFolder.getCreator();
        this.createTime = resPersonalFolder.getCreateTime();
        this.creatorIP = resPersonalFolder.getCreatorIP();
        this.modifier = resPersonalFolder.getModifier();
        this.modifyTime = resPersonalFolder.getModifyTime();
        this.modifierIP = resPersonalFolder.getModifierIP();
        this.isParent = false;
        this.flagSysFolder = resPersonalFolder.getFlagSysFolder();
        this.userCode = resPersonalFolder.getUserCode();
    }

    public ResPersonalFolderVo() {
    }

    public String getCreateTimeText() {
        if (null != this.getCreateTime()) {
            return DateUtils.formatDate(this.getCreateTime());
        }
        return createTimeText;
    }

    public void setCreateTimeText(String createTimeText) {
        this.createTimeText = createTimeText;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getFlagSysFolder() {
        return flagSysFolder;
    }

    public void setFlagSysFolder(Integer flagSysFolder) {
        this.flagSysFolder = flagSysFolder;
    }

    public String getModifyTimeText() {
        if (null != this.getModifyTime()) {
            return DateUtils.formatDate(this.getModifyTime());
        }
        return modifyTimeText;
    }

    public void setModifyTimeText(String modifyTimeText) {
        this.modifyTimeText = modifyTimeText;
    }

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

    public List<ResPersonalFolderVo> getVoList(List<ResPersonalFolder> list) {
        List<ResPersonalFolderVo> voList = new ArrayList<ResPersonalFolderVo>();
        if (null != list && list.size() > 0) {
            for (ResPersonalFolder resPersonalFolder : list) {
                ResPersonalFolderVo vo = new ResPersonalFolderVo(resPersonalFolder);
                voList.add(vo);
            }
        }
        return voList;
    }

}
