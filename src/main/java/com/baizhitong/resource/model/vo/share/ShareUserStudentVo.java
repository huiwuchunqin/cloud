package com.baizhitong.resource.model.vo.share;

import java.security.KeyStore.PrivateKeyEntry;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.baizhitong.resource.model.share.ShareUserLogin;
import com.baizhitong.resource.model.share.ShareUserStudent;
import com.baizhitong.resource.model.vo.login.LoginUserVo;
import com.baizhitong.utils.DataUtils;

/**
 * 学生信息VO
 * 
 * @author gaow
 * @date 2015年12月16日 下午5:11:23
 */
public class ShareUserStudentVo {

    /** 系统ID */
    private String    gid;
    /** 机构编码 */
    private String    orgCode;
    /** 年级编码 */
    private String    gradeCode;
    /** 学期编码 */
    private String    termCode;
    /** 行政班级编号 */
    private String    adminClassCode;
    /** 学生代码 */
    private String    studentCode;
    /** 学生硬件编号 */
    private String    studentHardNo;
    /** 学号 */
    private String    studentNumber;
    /** 用户姓名 */
    private String    userName;
    /** 状态 */
    private Integer   status;
    /** 性别 */
    private Integer   gender;
    /** 用户头像 */
    private String    avatarsImg;
    /** 入学年月 */
    private String    enterSchoolDate;
    /** 学生类别码 */
    private String    studentTypeCode;
    /** 修改程序 */
    private String    modifyPgm;
    /** 修改时间 */
    private Timestamp modifyTime;
    /** 修改者IP */
    private String    modifyIP;
    /** 系统版本号 */
    private Integer   sysVer;
    /** 机构名 */
    private String    orgName;
    /** 学段名 */
    private String    sectionName;
    /** 年级名称 */
    private String    gradeName;
    /** 登录账号 */
    private String    loginAccount;
    /** 登录密码 */
    private String    loginPwd;
    /** 手机号 */
    private String    mobilePhone;
    /** 班级名称 */
    private String    className;
    /** 邮箱 */
    private String    mail;
    /** 唯一码 */
    private String    cd_guid;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCd_guid() {
        return cd_guid;
    }

    public void setCd_guid(String cd_guid) {
        this.cd_guid = cd_guid;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getStudentHardNo() {
        return studentHardNo;
    }

    public void setStudentHardNo(String studentHardNo) {
        this.studentHardNo = studentHardNo;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
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

    public String getAdminClassCode() {
        return adminClassCode;
    }

    public void setAdminClassCode(String adminClassCode) {
        this.adminClassCode = adminClassCode;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getAvatarsImg() {
        return avatarsImg;
    }

    public void setAvatarsImg(String avatarsImg) {
        this.avatarsImg = avatarsImg;
    }

    public String getEnterSchoolDate() {
        return enterSchoolDate;
    }

    public void setEnterSchoolDate(String enterSchoolDate) {
        this.enterSchoolDate = enterSchoolDate;
    }

    public String getStudentTypeCode() {
        return studentTypeCode;
    }

    public void setStudentTypeCode(String studentTypeCode) {
        this.studentTypeCode = studentTypeCode;
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

    /**
     * map转vo ()<br>
     * 
     * @param map
     * @return
     */
    public static ShareUserStudentVo mapToVo(Map<String, Object> map) {
        if (map == null)
            return null;
        ShareUserStudentVo vo = new ShareUserStudentVo();
        vo.gid = MapUtils.getString(map, "gid");
        vo.orgCode = MapUtils.getString(map, "orgCode");
        vo.gradeCode = MapUtils.getString(map, "gradeCode");
        vo.termCode = MapUtils.getString(map, "termCode");
        vo.adminClassCode = MapUtils.getString(map, "adminClassCode");
        vo.studentCode = MapUtils.getString(map, "studentCode");
        vo.studentNumber = MapUtils.getString(map, "studentNumber");
        vo.userName = MapUtils.getString(map, "userName");
        vo.status = MapUtils.getInteger(map, "status");
        vo.gender = MapUtils.getInteger(map, "gender");
        vo.avatarsImg = MapUtils.getString(map, "avatarsImg");
        vo.studentTypeCode = MapUtils.getString(map, "studentTypeCode");
        vo.orgName = MapUtils.getString(map, "orgName");
        vo.sectionName = MapUtils.getString(map, "sectionName");
        vo.gradeName = MapUtils.getString(map, "gradeName");
        vo.loginAccount = MapUtils.getString(map, "loginAccount");
        vo.loginPwd = MapUtils.getString(map, "loginPwd");
        vo.gender = MapUtils.getInteger(map, "gender");
        vo.mobilePhone = MapUtils.getString(map, "mobilePhone");
        vo.className = MapUtils.getString(map, "className");
        vo.studentHardNo = MapUtils.getString(map, "studentHardNo");
        vo.enterSchoolDate = MapUtils.getString(map, "enterSchoolDate");
        vo.sysVer = MapUtils.getInteger(map, "sysVer");
        vo.mail = MapUtils.getString(map, "mail");
        vo.cd_guid = MapUtils.getString(map, "cd_guid");
        return vo;
    }

    // 查询性别
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

    /**
     * mapList转voList ()<br>
     * 
     * @param mapList
     * @return
     */
    public static List<ShareUserStudentVo> mapListToVoList(List<Map<String, Object>> mapList) {
        if (mapList == null || mapList.size() <= 0)
            return null;
        List<ShareUserStudentVo> voList = new ArrayList<ShareUserStudentVo>();
        for (Map<String, Object> map : mapList) {
            voList.add(mapToVo(map));
        }
        return voList;
    }

    /**
     * 实体转vo ()<br>
     * 
     * @param student
     * @return
     */
    public static ShareUserStudentVo EntityToVo(ShareUserStudent student) {
        ShareUserStudentVo vo = new ShareUserStudentVo();
        DataUtils.copySimpleObject(student, vo);
        return vo;
    }

}
