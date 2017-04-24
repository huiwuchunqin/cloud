package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 学生行政班级小组
 * 
 * @author shancy 2015/12/02
 */
@Entity
@Table(name = "share_admin_class_group_student")
public class ShareAdminClassGroupStudent implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 系统ID */
    private String            gid;
    /** 机构编码 */
    private String            orgCode;
    /** 行政班级编码 */
    private String            adminClassCode;
    /** 行政小组代码 */
    private String            adminGroupCode;
    /** 学生代码 */
    private String            studentCode;
    /** 学生小组角色 */
    private Integer           roleInGroup;
    /** 备注 */
    private String            memo;
    /** 修改程序 */
    private String            modifyPgm;
    /** 修改日期 */
    private Timestamp         modifyTime;
    /** 修改IP */
    private String            modifyIP;
    /** 系统版本号 */
    private Integer           sysVer;

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

    public String getAdminClassCode() {
        return adminClassCode;
    }

    public void setAdminClassCode(String adminClassCode) {
        this.adminClassCode = adminClassCode;
    }

    public String getAdminGroupCode() {
        return adminGroupCode;
    }

    public void setAdminGroupCode(String adminGroupCode) {
        this.adminGroupCode = adminGroupCode;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public Integer getRoleInGroup() {
        return roleInGroup;
    }

    public void setRoleInGroup(Integer roleInGroup) {
        this.roleInGroup = roleInGroup;
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

}
