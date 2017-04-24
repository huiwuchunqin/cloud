package com.baizhitong.resource.model.vo.report;

/**
 * 
 * 教师_日次_资源制作统计VO
 * 
 * @author creator zhangqiang 2016年7月18日 下午5:09:42
 * @author updater
 *
 * @version 1.0.0
 */
public class TeacherResMakeDailyVo {
    /** 作者 */
    private String  userName;
    /** 当前执教 */
    private String  currentTeaching;
    /** 学校 */
    private String  orgName;
    /** 资源数 */
    private Integer resTotal;
    /** 个人私有 */
    private Integer shareLevelPrivate;
    /** 校内共享 */
    private Integer shareLevelOrg;
    /** 区内共享 */
    private Integer shareLevelArea;
    /** 视频数 */
    private Integer mediaTotal;
    /** 文档数 */
    private Integer docTotal;
    /** 测验数 */
    private Integer exerciseTotal;
    /** 题目数 */
    private Integer questionTotal;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCurrentTeaching() {
        return currentTeaching;
    }

    public void setCurrentTeaching(String currentTeaching) {
        this.currentTeaching = currentTeaching;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Integer getResTotal() {
        return resTotal;
    }

    public void setResTotal(Integer resTotal) {
        this.resTotal = resTotal;
    }

    public Integer getShareLevelPrivate() {
        return shareLevelPrivate;
    }

    public void setShareLevelPrivate(Integer shareLevelPrivate) {
        this.shareLevelPrivate = shareLevelPrivate;
    }

    public Integer getShareLevelOrg() {
        return shareLevelOrg;
    }

    public void setShareLevelOrg(Integer shareLevelOrg) {
        this.shareLevelOrg = shareLevelOrg;
    }

    public Integer getShareLevelArea() {
        return shareLevelArea;
    }

    public void setShareLevelArea(Integer shareLevelArea) {
        this.shareLevelArea = shareLevelArea;
    }

    public Integer getMediaTotal() {
        return mediaTotal;
    }

    public void setMediaTotal(Integer mediaTotal) {
        this.mediaTotal = mediaTotal;
    }

    public Integer getDocTotal() {
        return docTotal;
    }

    public void setDocTotal(Integer docTotal) {
        this.docTotal = docTotal;
    }

    public Integer getExerciseTotal() {
        return exerciseTotal;
    }

    public void setExerciseTotal(Integer exerciseTotal) {
        this.exerciseTotal = exerciseTotal;
    }

    public Integer getQuestionTotal() {
        return questionTotal;
    }

    public void setQuestionTotal(Integer questionTotal) {
        this.questionTotal = questionTotal;
    }

}
