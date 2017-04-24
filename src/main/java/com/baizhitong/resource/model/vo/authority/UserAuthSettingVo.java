package com.baizhitong.resource.model.vo.authority;

import java.util.List;

/**
 * 
 * 用户审核权限设置VO
 * 
 * @author creator ZhangQiang 2016年9月26日 下午4:30:01
 * @author updater
 *
 * @version 1.0.0
 */
public class UserAuthSettingVo {

    /** 登录账号 */
    private String                               loginAccount;
    /** 用户编码 */
    private String                               userCode;
    /** 审核学段列表 */
    private List<SettingUserPriviledgeSectionVo> priviledgeSectionVoList;
    /** 审核学科列表 */
    private List<SettingUserPriviledgeSubjectVo> priviledgeSubjectVoList;
    /** 审核年级列表 */
    private List<SettingUserPriviledgeGradeVo>   priviledgeGradeVoList;

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public List<SettingUserPriviledgeSectionVo> getPriviledgeSectionVoList() {
        return priviledgeSectionVoList;
    }

    public void setPriviledgeSectionVoList(List<SettingUserPriviledgeSectionVo> priviledgeSectionVoList) {
        this.priviledgeSectionVoList = priviledgeSectionVoList;
    }

    public List<SettingUserPriviledgeSubjectVo> getPriviledgeSubjectVoList() {
        return priviledgeSubjectVoList;
    }

    public void setPriviledgeSubjectVoList(List<SettingUserPriviledgeSubjectVo> priviledgeSubjectVoList) {
        this.priviledgeSubjectVoList = priviledgeSubjectVoList;
    }

    public List<SettingUserPriviledgeGradeVo> getPriviledgeGradeVoList() {
        return priviledgeGradeVoList;
    }

    public void setPriviledgeGradeVoList(List<SettingUserPriviledgeGradeVo> priviledgeGradeVoList) {
        this.priviledgeGradeVoList = priviledgeGradeVoList;
    }

}
