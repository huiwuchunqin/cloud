package com.baizhitong.resource.model.authority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户与角色关联实体
 * 
 * @author zhangqiang
 * @date 2015年12月15日 下午1:08:41
 */
@Entity
@Table(name = "auth_user_role_ref")
@SuppressWarnings("serial")
public class AuthUserRoleRef implements Serializable {

    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer id;
    /** 用户ID */
    private String  userCode;
    /** 角色ID */
    private Integer roleId;
    /** 显示顺序 */
    private Integer dispOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

}
