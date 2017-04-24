package com.baizhitong.resource.model.vo.authority;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * 
 * 设置-管理员权限-学段VO
 * 
 * @author creator ZhangQiang 2016年9月26日 下午5:29:13
 * @author updater
 *
 * @version 1.0.0
 */
public class SettingUserPriviledgeSectionVo {

    /** 权限-学段ID */
    private Integer priviledgeSectionId;
    /** 登录者编码 */
    private String  userCode;
    /** 登录账号 */
    private String  loginAccount;
    /** 学段编码 */
    private String  sectionCode;
    /** 学段名称 */
    private String  sectionName;
    /** 备注 */
    private String  memo;
    /** 显示顺序 */
    private Integer dispOrder;

    public Integer getPriviledgeSectionId() {
        return priviledgeSectionId;
    }

    public void setPriviledgeSectionId(Integer priviledgeSectionId) {
        this.priviledgeSectionId = priviledgeSectionId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getDispOrder() {
        return dispOrder;
    }

    public void setDispOrder(Integer dispOrder) {
        this.dispOrder = dispOrder;
    }

    /**
     * 
     * (Map转VO)<br>
     * 
     * @param map
     * @return
     */
    public static SettingUserPriviledgeSectionVo mapToVo(Map<String, Object> map) {
        SettingUserPriviledgeSectionVo vo = new SettingUserPriviledgeSectionVo();
        if (MapUtils.isNotEmpty(map)) {
            vo.setPriviledgeSectionId(MapUtils.getInteger(map, "priviledgeSectionId"));
            vo.setUserCode(MapUtils.getString(map, "userCode"));
            vo.setSectionCode(MapUtils.getString(map, "sectionCode"));
            vo.setSectionName(MapUtils.getString(map, "sectionName"));
            vo.setDispOrder(MapUtils.getInteger(map, "dispOrder"));
            vo.setMemo(MapUtils.getString(map, "memo"));
        }
        return vo;
    }
}
