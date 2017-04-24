package com.baizhitong.resource.model.authority;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色与资源关联实体
 * 
 * @author zhangqiang
 * @date 2015年12月15日 下午1:15:16
 */
@Entity
@Table(name = "auth_role_res_ref")
@SuppressWarnings("serial")
public class AuthRoleResRef implements Serializable {

    /** 主键 */
    @Id
    @Column(updatable = false,
            insertable = false)
    private Integer id;
    /** 角色ID */
    private Integer roleId;
    /** 资源ID */
    private Integer resId;
    /** 显示顺序 */
    private Integer dispOrder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getResId() {
        return resId;
    }

    public void setResId(Integer resId) {
        this.resId = resId;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

}
