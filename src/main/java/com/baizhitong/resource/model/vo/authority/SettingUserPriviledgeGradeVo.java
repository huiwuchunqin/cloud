package com.baizhitong.resource.model.vo.authority;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

/**
 * 
 * 设置-管理员权限-年级VO
 * 
 * @author creator ZhangQiang 2016年9月26日 下午5:25:37
 * @author updater
 *
 * @version 1.0.0
 */
public class SettingUserPriviledgeGradeVo {

    /** 权限-年级ID */
    private Integer priviledgeGradeId;
    /** 登录帐号 */
    private String  loginAccount;
    /** 登录者编码 */
    private String  userCode;
    /** 年级编码 */
    private String  gradeCode;
    /** 年级名称 */
    private String  gradeName;
    /** 备注 */
    private String  memo;
    /** 显示顺序 */
    private Integer dispOrder;

    public Integer getPriviledgeGradeId() {
        return priviledgeGradeId;
    }

    public void setPriviledgeGradeId(Integer priviledgeGradeId) {
        this.priviledgeGradeId = priviledgeGradeId;
    }

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

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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
     * (map转VO)<br>
     * 
     * @param map
     * @return
     */
    public static SettingUserPriviledgeGradeVo mapToVo(Map<String, Object> map) {
        SettingUserPriviledgeGradeVo vo = new SettingUserPriviledgeGradeVo();
        if (MapUtils.isNotEmpty(map)) {
            vo.setPriviledgeGradeId(MapUtils.getInteger(map, "priviledgeGradeId"));
            vo.setUserCode(MapUtils.getString(map, "userCode"));
            vo.setGradeCode(MapUtils.getString(map, "gradeCode"));
            vo.setGradeName(MapUtils.getString(map, "gradeName"));
            vo.setDispOrder(MapUtils.getInteger(map, "dispOrder"));
            vo.setMemo(MapUtils.getString(map, "memo"));
        }
        return vo;
    }

}
