package com.baizhitong.resource.model.vo.authority;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * 
 * 设置-管理员权限-学科VO
 * 
 * @author creator ZhangQiang 2016年9月26日 下午5:23:16
 * @author updater
 *
 * @version 1.0.0
 */
public class SettingUserPriviledgeSubjectVo {

    private Integer priviledgeSubjectId;
    /** 登录账号 */
    private String  loginAccount;
    /** 学段编码 */
    private String  sectionCode;
    /** 学段名称 */
    private String  sectionName;
    /** 学科编码 */
    private String  subjectCode;
    /** 学科名称 */
    private String  subjectName;
    /** 显示顺序 */
    private Integer dispOrder;
    /** 备注 */
    private String  memo;
    /** 用户编码 */
    private String  userCode;

    public Integer getPriviledgeSubjectId() {
        return priviledgeSubjectId;
    }

    public void setPriviledgeSubjectId(Integer priviledgeSubjectId) {
        this.priviledgeSubjectId = priviledgeSubjectId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * 
     * (map转VO)<br>
     * 
     * @param map
     * @return
     */
    public static SettingUserPriviledgeSubjectVo mapToVo(Map<String, Object> map) {
        SettingUserPriviledgeSubjectVo vo = new SettingUserPriviledgeSubjectVo();
        if (MapUtils.isNotEmpty(map)) {
            vo.setPriviledgeSubjectId(MapUtils.getInteger(map, "priviledgeSubjectId"));
            vo.setUserCode(MapUtils.getString(map, "userCode"));
            vo.setDispOrder(MapUtils.getInteger(map, "dispOrder"));
            vo.setMemo(MapUtils.getString(map, "memo"));
            vo.setSectionCode(MapUtils.getString(map, "sectionCode"));
            vo.setSectionName(MapUtils.getString(map, "sectionName"));
            vo.setSubjectCode(MapUtils.getString(map, "subjectCode"));
            vo.setSubjectName(MapUtils.getString(map, "subjectName"));
        }
        return vo;
    }
}
