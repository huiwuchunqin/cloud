package com.baizhitong.resource.model.vo.feedback;

import java.sql.Timestamp;

import javax.persistence.Id;

/**
 * 
 * UserFeedbackVo 用户反馈Vo
 * 
 * @author creator zhanglzh 2016年9月27日 下午6:24:32
 * @author updater
 *
 * @version 1.0.0
 */
public class UserFeedbackVo {
    /** 用户代码 */
    private String    userCode;
    /** 用户姓名 */
    private String    userName;
    /** 地理位置信息 */
    private String    geoInfo;
    /** 反馈内容 */
    private String    content;
    /** 反馈时间 */
    private Timestamp actionTime;
    /** 模块描述 */
    private String    moduleDesc;

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGeoInfo() {
        return geoInfo;
    }

    public void setGeoInfo(String geoInfo) {
        this.geoInfo = geoInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }

    public void setActionTime(Timestamp actionTime) {
        this.actionTime = actionTime;
    }

    public String getModuleDesc() {
        return moduleDesc;
    }

    public void setModuleDesc(String moduleDesc) {
        this.moduleDesc = moduleDesc;
    }

}
