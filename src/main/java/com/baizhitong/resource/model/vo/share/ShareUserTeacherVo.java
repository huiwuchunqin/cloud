package com.baizhitong.resource.model.vo.share;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareUserStudent;
import com.baizhitong.resource.model.share.ShareUserTeacher;
import com.baizhitong.utils.DataUtils;

/**
 * 教师信息VO
 * 
 * @author gaow
 * @date 2015年12月16日 下午3:06:29
 */
public class ShareUserTeacherVo {

    /** 系统ID */
    private String    gid;
    /** 机构编码 */
    private String    orgCode;
    /** 教师代码 */
    private String    teacherCode;
    /** 用户姓名 */
    private String    userName;
    /** 职称编码 */
    private String    jobTitleCode;
    /** 职位编码 */
    private String    positionCode;
    /** 状态 */
    private Integer   status;
    /** 性别 */
    private Integer   gender;
    /** 默认学段编码 */
    private String    sectionCode;
    /** 默认学科编码 */
    private String    subjectCode;
    /** 默认年级编码 */
    private String    gradeCode;
    /** 默认学期编码 */
    private String    termCode;
    /** 默认教材版本编码 */
    private String    tbvCode;
    /** 默认地区编码 */
    private String    districtAreaCode;
    /** 工号 */
    private String    workNo;
    /** 最高学历 */
    private String    educationCode;
    /** 参加工作年月 */
    private String    workFirstDay;
    /** 修改程序 */
    private String    modifyPgm;
    /** 修改时间 */
    private Timestamp modifyTime;
    /** 修改者IP */
    private String    modifyIP;
    /** 系统版本号 */
    private Integer   sysVer;
    /** 学段名称 */
    private String    sectionName;
    /** 年级名称 */
    private String    gradeName;
    /** 学科名称 */
    private String    subjectName;
    /** 机构名称 */
    private String    orgName;
    /** 登录账号 */
    private String    loginAccount;
    /** 登录密码 */
    private String    loginPwd;
    /** 手机号 */
    private String    mobilePhone;
    /** 用户角色 */
    private Integer   userRole;
    /** 邮箱 */
    private String    mail;
    /** 老师唯一码 */
    private String    cd_guid;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getCd_guid() {
        return cd_guid;
    }

    public void setCd_guid(String cd_guid) {
        this.cd_guid = cd_guid;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getJobTitleCode() {
        return jobTitleCode;
    }

    public void setJobTitleCode(String jobTitleCode) {
        this.jobTitleCode = jobTitleCode;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
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

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public String getTbvCode() {
        return tbvCode;
    }

    public void setTbvCode(String tbvCode) {
        this.tbvCode = tbvCode;
    }

    public String getDistrictAreaCode() {
        return districtAreaCode;
    }

    public void setDistrictAreaCode(String districtAreaCode) {
        this.districtAreaCode = districtAreaCode;
    }

    public String getWorkNo() {
        return workNo;
    }

    public void setWorkNo(String workNo) {
        this.workNo = workNo;
    }

    public String getEducationCode() {
        return educationCode;
    }

    public void setEducationCode(String educationCode) {
        this.educationCode = educationCode;
    }

    public String getWorkFirstDay() {
        return workFirstDay;
    }

    public void setWorkFirstDay(String workFirstDay) {
        this.workFirstDay = workFirstDay;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
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

    // 性别
    public String getGenderStr() {
        if (this.gender == null)
            return "";
        if (this.gender == 2) {
            return "女";
        } else if (this.gender == 1) {
            return "男";
        }
        return "";
    }

    // 角色
    public String getUserRoleStr() {
        if (this.userRole == null)
            return "";
        if (this.userRole == 10) {
            return "教师";
        } else if (this.userRole == 30) {
            return "管理人员";
        } else if (this.userRole == 90) {
            return "系统管理员";
        }
        return "";
    }

    /**
     * map转vo ()<br>
     * 
     * @param map
     * @return
     */
    public static ShareUserTeacherVo mapToVo(Map<String, Object> map) {
        ShareUserTeacherVo vo = new ShareUserTeacherVo();
        vo.gid = MapUtils.getString(map, "gid");
        vo.orgCode = MapUtils.getString(map, "orgCode");
        vo.teacherCode = MapUtils.getString(map, "teacherCode");
        vo.userName = MapUtils.getString(map, "userName");
        vo.jobTitleCode = MapUtils.getString(map, "jobTitleCode");
        vo.positionCode = MapUtils.getString(map, "positionCode");
        vo.status = MapUtils.getInteger(map, "status");
        vo.gender = MapUtils.getInteger(map, "gender");
        vo.sectionCode = MapUtils.getString(map, "sectionCode");
        vo.subjectCode = MapUtils.getString(map, "subjectCode");
        vo.gradeCode = MapUtils.getString(map, "gradeCode");
        vo.termCode = MapUtils.getString(map, "termCode");
        vo.tbvCode = MapUtils.getString(map, "tbvCode");
        vo.districtAreaCode = MapUtils.getString(map, "districtAreaCode");
        vo.workNo = MapUtils.getString(map, "workNo");
        vo.educationCode = MapUtils.getString(map, "educationCode");
        vo.orgName = MapUtils.getString(map, "orgName");
        vo.sectionName = MapUtils.getString(map, "sectionName");
        vo.gradeName = MapUtils.getString(map, "gradeName");
        vo.subjectName = MapUtils.getString(map, "subjectName");
        vo.loginAccount = MapUtils.getString(map, "loginAccount");
        vo.loginPwd = MapUtils.getString(map, "loginPwd");
        vo.mobilePhone = MapUtils.getString(map, "mobilePhone");
        vo.userRole = MapUtils.getInteger(map, "userRole");
        vo.gender = MapUtils.getInteger(map, "gender");
        vo.workFirstDay = MapUtils.getString(map, "workFirstDay");
        vo.mail = MapUtils.getString(map, "mail");
        vo.cd_guid = MapUtils.getString(map, "cd_guid");
        return vo;
    }

    /**
     * mapList转voList ()<br>
     * 
     * @param mapList
     * @return
     */
    public static List<ShareUserTeacherVo> mapListToVoList(List<Map<String, Object>> mapList) {
        if (mapList == null || mapList.size() <= 0)
            return null;
        List<ShareUserTeacherVo> voList = new ArrayList<ShareUserTeacherVo>();
        for (Map<String, Object> map : mapList) {
            voList.add(mapToVo(map));
        }
        return voList;
    }

    /**
     * 实体转vo ()<br>
     * 
     * @param teacher
     * @return
     */
    public static ShareUserTeacherVo EntityToVo(ShareUserTeacher teacher) {
        ShareUserTeacherVo vo = new ShareUserTeacherVo();
        DataUtils.copySimpleObject(teacher, vo);
        return vo;
    }
}
