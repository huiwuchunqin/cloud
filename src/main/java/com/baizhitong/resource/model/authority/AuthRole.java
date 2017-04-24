package com.baizhitong.resource.model.authority;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色实体
 * 
 * @author zhangqiang
 * @date 2015年12月15日 上午11:25:59
 */
@Entity
@Table(name = "auth_role")
@SuppressWarnings("serial")
public class AuthRole implements Serializable {

    /** 角色ID */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer   id;
    /** 角色编码 */
    private String    code;
    /** 角色名称 */
    private String    name;
    /** 是否系统默认角色 */
    private Integer   sysRole;
    /** 备注 */
    private String    memo;
    /** 是否删除 */
    private Integer   flagDelete;
    /** 创建者 */
    private String    creator;
    /** 创建时间 */
    private Timestamp createTime;
    /** 创建者IP */
    private String    creatorIP;
    /** 更新者 */
    private String    modifier;
    /** 更新时间 */
    private Timestamp modifyTime;
    /** 更新者IP */
    private String    modifierIP;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSysRole() {
        return sysRole;
    }

    public void setSysRole(Integer sysRole) {
        this.sysRole = sysRole;
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
