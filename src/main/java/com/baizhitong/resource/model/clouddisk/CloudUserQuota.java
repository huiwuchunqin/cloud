package com.baizhitong.resource.model.clouddisk;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * 用户云盘配额实体
 * 
 * @author creator ZhangQiang 2016年9月19日 下午4:45:19
 * @author updater
 *
 * @version 1.0.0
 */
@Entity
@Table(name = "cloud_user_quota")
public class CloudUserQuota implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键 */
    @Id
    @Column(insertable = false,
            updatable = false)
    private Integer           id;
    /** 机构编码 */
    private String            orgCode;
    /** 用户代码 */
    private String            userCode;
    /** 许可的容量 */
    private Long              capacitySize;
    /** 已占用容量 */
    private Long              usedSize;
    /** 备注 */
    private String            memo;
    /** 是否删除 */
    private Integer           flagDelete;
    /** 创建者 */
    private String            creator;
    /** 创建时间 */
    private Timestamp         createTime;
    /** 创建者IP */
    private String            creatorIP;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Timestamp         modifyTime;
    /** 更新者IP */
    private String            modifierIP;

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

    public Long getCapacitySize() {
        return capacitySize;
    }

    public void setCapacitySize(Long capacitySize) {
        this.capacitySize = capacitySize;
    }

    public Long getUsedSize() {
        return usedSize;
    }

    public void setUsedSize(Long usedSize) {
        this.usedSize = usedSize;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getFlagDelete() {
        return flagDelete;
    }

    public void setFlagDelete(Integer flagDelete) {
        this.flagDelete = flagDelete;
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
