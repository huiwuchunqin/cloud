package com.baizhitong.resource.model.share;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 机构
 * 
 * @author shancy 2015/12/02
 */
@Entity
@Table(name = "share_orgnization")
public class ShareOrgnization implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 机构编码 */
    private String            orgCode;
    /** 机构名称 */
    private String            orgName;
    /** 机构简介 */
    private String            introduction;
    /** 机构LOGO */
    private String            logo;
    /** 机构性质 */
    private Integer           orgType;
    /** 机构首页地址 */
    private String            orgWebsite;
    /** 机构网址（域名） */
    private String            orgDomain;
    /** fav地址 */
    private String            orgFavicon;
    /** 创建时间 */
    private Date              buildTime;
    /** 是否有效 */
    private Integer           flagValid;
    /** 有效起始日期 */
    private Date              validDateStart;
    /** 有效结束日期 */
    private Date              validDateEnd;
    /** 备注 */
    private String            remark;
    /** 网站备案号 */
    private String            icp_no;
    /** 办学类型CODE */
    private String            schoolTypeCd;
    /** 行政区域编码 */
    private String            districtTypeCd;
    /** 机构国家标志CODE */
    private String            orgCdState;
    /** 机构全局CODE */
    private String            orgCdGlobal;
    /** 机构类别 */
    private Integer           orgCdType;
    /** 部署方式 */
    private Integer           deployType;
    /** 默认地区 */
    private String            defaultArea;
    /** 所属学段 */
    private String            sectionCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getOrgWebsite() {
        return orgWebsite;
    }

    public void setOrgWebsite(String orgWebsite) {
        this.orgWebsite = orgWebsite;
    }

    public String getOrgDomain() {
        return orgDomain;
    }

    public void setOrgDomain(String orgDomain) {
        this.orgDomain = orgDomain;
    }

    public String getOrgFavicon() {
        return orgFavicon;
    }

    public void setOrgFavicon(String orgFavicon) {
        this.orgFavicon = orgFavicon;
    }

    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }

    public Integer getFlagValid() {
        return flagValid;
    }

    public void setFlagValid(Integer flagValid) {
        this.flagValid = flagValid;
    }

    public Date getValidDateStart() {
        return validDateStart;
    }

    public void setValidDateStart(Date validDateStart) {
        this.validDateStart = validDateStart;
    }

    public Date getValidDateEnd() {
        return validDateEnd;
    }

    public void setValidDateEnd(Date validDateEnd) {
        this.validDateEnd = validDateEnd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIcp_no() {
        return icp_no;
    }

    public void setIcp_no(String icp_no) {
        this.icp_no = icp_no;
    }

    public String getSchoolTypeCd() {
        return schoolTypeCd;
    }

    public void setSchoolTypeCd(String schoolTypeCd) {
        this.schoolTypeCd = schoolTypeCd;
    }

    public String getDistrictTypeCd() {
        return districtTypeCd;
    }

    public void setDistrictTypeCd(String districtTypeCd) {
        this.districtTypeCd = districtTypeCd;
    }

    public String getOrgCdState() {
        return orgCdState;
    }

    public void setOrgCdState(String orgCdState) {
        this.orgCdState = orgCdState;
    }

    public String getOrgCdGlobal() {
        return orgCdGlobal;
    }

    public void setOrgCdGlobal(String orgCdGlobal) {
        this.orgCdGlobal = orgCdGlobal;
    }

    public Integer getOrgCdType() {
        return orgCdType;
    }

    public void setOrgCdType(Integer orgCdType) {
        this.orgCdType = orgCdType;
    }

    public Integer getDeployType() {
        return deployType;
    }

    public void setDeployType(Integer deployType) {
        this.deployType = deployType;
    }

    public String getDefaultArea() {
        return defaultArea;
    }

    public void setDefaultArea(String defaultArea) {
        this.defaultArea = defaultArea;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }
}