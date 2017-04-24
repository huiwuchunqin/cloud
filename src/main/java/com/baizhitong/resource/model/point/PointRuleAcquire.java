package com.baizhitong.resource.model.point;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "point_rule_acquire")
@Entity
public class PointRuleAcquire implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(insertable = true,
            updatable = false)
    @Id
    /** 主键 */
    private Integer           id;
    /** 业务版本号 */
    private Integer           bizVersion;
    /** 版本开始日期 */
    private Long              startTime;
    /** 版本失效日期 */
    private Long              expireTime;
    /** 用户身份 */
    private Integer           userRole;
    /** 动作分类 */
    private String            actionType;
    /** 动作分类名称 */
    private String            actionTypeName;
    /** 动作代码 */
    private String            actionCode;
    /** 动作名称 */
    private String            actionName;
    /** 动作描述 */
    private String            actionDescription;
    /** 算法分级标志 */
    private Integer           flagGraded;
    /** 算法类型 */
    private Integer           algorithmsType;
    /** 参数与算法 */
    private String            algorithmsJson;
    /** 算法类型-机构 */
    private Integer           algorithmsTypeOrg;
    /** 参数与算法-机构 */
    private String            algorithmsJsonOrg;
    /** 更新者 */
    private String            modifier;
    /** 更新时间 */
    private Timestamp         modifyTime;
    /** 更新者IP */
    private String            modifierIP;
    /** 系统版本号 */
    private Integer           sysVersion;
    private Integer           flagOrgValid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBizVersion() {
        return bizVersion;
    }

    public void setBizVersion(Integer bizVersion) {
        this.bizVersion = bizVersion;
    }

    public Integer getFlagOrgValid() {
        return flagOrgValid;
    }

    public void setFlagOrgValid(Integer flagOrgValid) {
        this.flagOrgValid = flagOrgValid;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getActionTypeName() {
        return actionTypeName;
    }

    public void setActionTypeName(String actionTypeName) {
        this.actionTypeName = actionTypeName;
    }

    public String getActionCode() {
        return actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public Integer getFlagGraded() {
        return flagGraded;
    }

    public void setFlagGraded(Integer flagGraded) {
        this.flagGraded = flagGraded;
    }

    public Integer getAlgorithmsType() {
        return algorithmsType;
    }

    public void setAlgorithmsType(Integer algorithmsType) {
        this.algorithmsType = algorithmsType;
    }

    public String getAlgorithmsJson() {
        return algorithmsJson;
    }

    public void setAlgorithmsJson(String algorithmsJson) {
        this.algorithmsJson = algorithmsJson;
    }

    public Integer getAlgorithmsTypeOrg() {
        return algorithmsTypeOrg;
    }

    public void setAlgorithmsTypeOrg(Integer algorithmsTypeOrg) {
        this.algorithmsTypeOrg = algorithmsTypeOrg;
    }

    public String getAlgorithmsJsonOrg() {
        return algorithmsJsonOrg;
    }

    public void setAlgorithmsJsonOrg(String algorithmsJsonOrg) {
        this.algorithmsJsonOrg = algorithmsJsonOrg;
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

    public Integer getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(Integer sysVersion) {
        this.sysVersion = sysVersion;
    }

}
