package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

public class ShareTeachingClassSubjectVo {
    /** 系统ID */
    private String  gid;
    /** 机构编码 */
    private String  orgCode;
    /** 学科编码 */
    private String  subjectCode;
    /** 年级编码 */
    private String  gradeCode;
    /** 教材版本编码 */
    private String  textbookVerCode;
    /** 教学班级代码 */
    private String  teachingClassCode;
    /** 教师代码 */
    private String  teacherCode;
    /** 教师姓名 */
    private String  teacherName;
    /** 教师角色 */
    private String  teacherRole;
    /** 学年编码 */
    private String  studyYearCode;
    /** 学期编码 */
    private String  termCode;
    /** 任教开始时间 */
    private String  beginTime;
    /** 任教结束时间 */
    private String  endTime;
    /** 班级硬件识别号 */
    private Integer classHardId;
    /** 备注 */
    private String  memo;
    /** 修改程序 */
    private String  modifyPgm;
    /** 修改时间 */
    private String  modifyTime;
    /** 修改者IP */
    private String  modifyIP;
    /** 系统版本号 */
    private Integer sysVer;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getGradeCode() {
        return gradeCode;
    }

    public void setGradeCode(String gradeCode) {
        this.gradeCode = gradeCode;
    }

    public String getTextbookVerCode() {
        return textbookVerCode;
    }

    public void setTextbookVerCode(String textbookVerCode) {
        this.textbookVerCode = textbookVerCode;
    }

    public String getTeachingClassCode() {
        return teachingClassCode;
    }

    public void setTeachingClassCode(String teachingClassCode) {
        this.teachingClassCode = teachingClassCode;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherRole() {
        return teacherRole;
    }

    public void setTeacherRole(String teacherRole) {
        this.teacherRole = teacherRole;
    }

    public String getStudyYearCode() {
        return studyYearCode;
    }

    public void setStudyYearCode(String studyYearCode) {
        this.studyYearCode = studyYearCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getClassHardId() {
        return classHardId;
    }

    public void setClassHardId(Integer classHardId) {
        this.classHardId = classHardId;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyIP() {
        return modifyIP;
    }

    public void setModifyIP(String modifyIP) {
        this.modifyIP = modifyIP;
    }

    public Integer getSysVer() {
        return sysVer;
    }

    public void setSysVer(Integer sysVer) {
        this.sysVer = sysVer;
    }

    /**
     * map转vo ()<br>
     * 
     * @param map
     * @return
     */
    public static ShareTeachingClassSubjectVo mapToVo(Map<String, Object> map) {
        if (map == null)
            return null;
        ShareTeachingClassSubjectVo vo = new ShareTeachingClassSubjectVo();
        vo.setBeginTime(MapUtils.getString(map, "beginTime"));
        vo.setEndTime(MapUtils.getString(map, "endTime"));
        vo.setGid(MapUtils.getString(map, "gid"));
        vo.setGradeCode(MapUtils.getString(map, "gradeCode"));
        vo.setOrgCode(MapUtils.getString(map, "orgCode"));
        vo.setStudyYearCode(MapUtils.getString(map, "studyYearCode"));
        vo.setSubjectCode(MapUtils.getString(map, "subjectCode"));
        vo.setTeacherCode(MapUtils.getString(map, "teacherCode"));
        vo.setTeacherName(MapUtils.getString(map, "teacherName"));
        vo.setTeacherRole(MapUtils.getString(map, "teacherRole"));
        vo.setTeachingClassCode(MapUtils.getString(map, "teachingClassCode"));
        vo.setTermCode(MapUtils.getString(map, "termCode"));
        vo.setTextbookVerCode(MapUtils.getString(map, "textbookVerCode"));
        return vo;
    }

    /**
     * maplist转volist ()<br>
     * 
     * @param mapList
     * @return
     */
    public static List<ShareTeachingClassSubjectVo> mapListToVo(List<Map<String, Object>> mapList) {
        if (mapList == null && mapList.size() <= 0)
            return null;
        List<ShareTeachingClassSubjectVo> voList = new ArrayList<ShareTeachingClassSubjectVo>();
        for (Map<String, Object> map : mapList) {
            voList.add(mapToVo(map));
        }
        return voList;
    }
}
