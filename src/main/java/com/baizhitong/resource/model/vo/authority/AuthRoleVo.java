package com.baizhitong.resource.model.vo.authority;

import java.util.Date;

import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.utils.DataUtils;

/**
 * 角色信息VO
 * 
 * @author zhangqiang
 * @date 2015年12月17日 下午2:37:33
 */
public class AuthRoleVo {

    /** 角色ID */
    private Integer id;
    /** 角色编码 */
    private String  code;
    /** 角色名称 */
    private String  name;
    /** 是否系统默认角色 */
    private Integer sysRole;
    /** 备注 */
    private String  memo;
    /** 是否删除 */
    private Integer flagDelete;
    /** 创建者 */
    private String  creator;
    /** 创建时间 */
    private Date    createTime;
    /** 创建者IP */
    private String  creatorIP;
    /** 更新者 */
    private String  modifier;
    /** 更新时间 */
    private Date    modifyTime;
    /** 更新者IP */
    private String  modifierIP;
    /** 是否选中 */
    private Integer isCheck;

    public AuthRoleVo() {
        super();
    }

    /**
     * entity转vo
     * 
     * @param entity 实体
     */
    public AuthRoleVo(AuthRole entity) {
        DataUtils.copySimpleObject(entity, this);
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifierIP() {
        return modifierIP;
    }

    public void setModifierIP(String modifierIP) {
        this.modifierIP = modifierIP;
    }

    public Integer getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(Integer isCheck) {
        this.isCheck = isCheck;
    }

}
