package com.baizhitong.resource.model.vo.login;

import java.util.List;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.model.authority.AuthRole;

/**
 * 登录用户角色信息VO
 * 
 * @author zhangqiang
 * @date 2015年12月18日 下午5:33:08
 */
public class LoginUserRoleVo {

    private List<AuthRole> roleList;      // 用户角色列表(可以是一个角色也可以是多个角色)
    private UserInfoVo     userInfo;      // 登录用户信息
    private String         flagSuperAdmin;// 是否是超级管理员(0:否; 1:是)

    public LoginUserRoleVo() {
        super();
    }

    public LoginUserRoleVo(List<AuthRole> roleList, UserInfoVo userInfo) {
        super();
        this.roleList = roleList;
        this.userInfo = userInfo;
    }

    public List<AuthRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AuthRole> roleList) {
        this.roleList = roleList;
    }

    public UserInfoVo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoVo userInfo) {
        this.userInfo = userInfo;
    }

    public String getFlagSuperAdmin() {
        return flagSuperAdmin;
    }

    public void setFlagSuperAdmin(String flagSuperAdmin) {
        this.flagSuperAdmin = flagSuperAdmin;
    }

}
