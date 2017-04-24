package com.baizhitong.resource.model.vo.company;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.poifs.storage.ListManagedBlock;

import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.utils.DataUtils;

import freemarker.core.ReturnInstruction.Return;

/**
 * 机构信息vo CompanyInfoVo TODO
 * 
 * @author creator BZT 2016年1月22日 下午2:19:42
 * @author updater
 *
 * @version 1.0.0
 */
public class CompanyInfoVo {
    private String    gid;
    /** 机构编码 */
    private String    orgCode;
    /** 机构简称 */
    private String    orgNameShort;
    /** 机构名称 */
    private String    orgName;
    /** 组织机构代码 */
    private String    orgRegCode;
    /** 机构简介 */
    private String    introduction;
    /** 机构LOGO */
    private String    logoUrl;
    /** 机构性质 */
    private Integer   orgType;
    /** fav地址 */
    private String    orgFavicon;
    /** 所在组号 */
    private Integer   orgGroupNo;
    /** 创建时间 */
    private Timestamp buildTime;
    /** 是否有效 */
    private Integer   flagValid;
    /** 有效起始日期 */
    private String    validDateStart;
    /** 有效结束日期 */
    private String    validDateEnd;
    /** 备注 */
    private String    remark;
    /** 网站备案号 */
    private String    icp_no;
    /** 办学类型编码 */
    private String    schoolTypeCode;
    /** 行政区域编码 */
    private String    districtTypeCode;
    /** 机构国家标志编码 */
    private String    orgCodeState;
    /** 机构类别 */
    private Integer   orgCodeType;
    /** 默认地区 */
    private String    defaultAreaCode;
    /** 机构规模 */
    private String    scaleDesc;
    /** 机构特色 */
    private String    feature;
    /** 最高管理者工号 */
    private String    topWorkNo;
    /** 最高管理者姓名 */
    private String    topName;
    /** 联系电话 */
    private String    phone;
    /** 电子信箱 */
    private String    mail;
    /** 修改程序 */
    private String    modifyPgm;
    /** 修改时间 */
    private String    modifyTime;
    /** 修改者IP */
    private String    modifyIP;
    /** 系统版本号 */
    private Integer   sysVer;
    /** 机构唯一码 */
    private String    cd_guid;
    /** 当前学年编码 */
    private String    currentStudyYearCode;
    /** 当前学年学期编码 */
    private String    currentYearTermCode;
    /** 当前学期编码 */
    private String    currentTermCode;
    /** 学段名称 */
    private String    sectionName;
    /** 域名id */
    private Integer   domainID;
    /** 域名地址 */
    private String    domainURL;
    /** 上级代理商的域名id */
    private Integer   pDomainID;
    /** 上级代理商的code */
    private String    agency;
    /**
     * 学段编码
     */
    private String    sectionCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCurrentStudyYearCode() {
        return currentStudyYearCode;
    }

    public void setCurrentStudyYearCode(String currentStudyYearCode) {
        this.currentStudyYearCode = currentStudyYearCode;
    }

    public String getCurrentYearTermCode() {
        return currentYearTermCode;
    }

    public void setCurrentYearTermCode(String currentYearTermCode) {
        this.currentYearTermCode = currentYearTermCode;
    }

    public String getCurrentTermCode() {
        return currentTermCode;
    }

    public void setCurrentTermCode(String currentTermCode) {
        this.currentTermCode = currentTermCode;
    }

    public Integer getpDomainID() {
        return pDomainID;
    }

    public void setpDomainID(Integer pDomainID) {
        this.pDomainID = pDomainID;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getCd_guid() {
        return cd_guid;
    }

    public void setCd_guid(String cd_guid) {
        this.cd_guid = cd_guid;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getOrgNameShort() {
        return orgNameShort;
    }

    public void setOrgNameShort(String orgNameShort) {
        this.orgNameShort = orgNameShort;
    }

    public String getOrgRegCode() {
        return orgRegCode;
    }

    public void setOrgRegCode(String orgRegCode) {
        this.orgRegCode = orgRegCode;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public String getOrgFavicon() {
        return orgFavicon;
    }

    public void setOrgFavicon(String orgFavicon) {
        this.orgFavicon = orgFavicon;
    }

    public Integer getOrgGroupNo() {
        return orgGroupNo;
    }

    public void setOrgGroupNo(Integer orgGroupNo) {
        this.orgGroupNo = orgGroupNo;
    }

    public Timestamp getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Timestamp buildTime) {
        this.buildTime = buildTime;
    }

    public Integer getFlagValid() {
        return flagValid;
    }

    public void setFlagValid(Integer flagValid) {
        this.flagValid = flagValid;
    }

    public String getValidDateStart() {
        return validDateStart;
    }

    public void setValidDateStart(String validDateStart) {
        this.validDateStart = validDateStart;
    }

    public String getValidDateEnd() {
        return validDateEnd;
    }

    public void setValidDateEnd(String validDateEnd) {
        this.validDateEnd = validDateEnd;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
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

    public String getSchoolTypeCode() {
        return schoolTypeCode;
    }

    public void setSchoolTypeCode(String schoolTypeCode) {
        this.schoolTypeCode = schoolTypeCode;
    }

    public String getDistrictTypeCode() {
        return districtTypeCode;
    }

    public void setDistrictTypeCode(String districtTypeCode) {
        this.districtTypeCode = districtTypeCode;
    }

    public String getOrgCodeState() {
        return orgCodeState;
    }

    public void setOrgCodeState(String orgCodeState) {
        this.orgCodeState = orgCodeState;
    }

    public Integer getOrgCodeType() {
        return orgCodeType;
    }

    public void setOrgCodeType(Integer orgCodeType) {
        this.orgCodeType = orgCodeType;
    }

    public String getDefaultAreaCode() {
        return defaultAreaCode;
    }

    public void setDefaultAreaCode(String defaultAreaCode) {
        this.defaultAreaCode = defaultAreaCode;
    }

    public String getScaleDesc() {
        return scaleDesc;
    }

    public void setScaleDesc(String scaleDesc) {
        this.scaleDesc = scaleDesc;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getTopWorkNo() {
        return topWorkNo;
    }

    public void setTopWorkNo(String topWorkNo) {
        this.topWorkNo = topWorkNo;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getModifyPgm() {
        return modifyPgm;
    }

    public void setModifyPgm(String modifyPgm) {
        this.modifyPgm = modifyPgm;
    }

    public Integer getDomainID() {
        return domainID;
    }

    public void setDomainID(Integer domainID) {
        this.domainID = domainID;
    }

    public String getDomainURL() {
        return domainURL;
    }

    public void setDomainURL(String domainURL) {
        this.domainURL = domainURL;
    }

    public String getModifyTime() {
        return modifyTime;
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
     * 机构实体转vo ()<br>
     * 
     * @param org
     * @return
     */
    public static CompanyInfoVo orgToVo(ShareOrg org) {
        CompanyInfoVo orgVo = new CompanyInfoVo();
        DataUtils.copySimpleObject(org, orgVo);
        return orgVo;
    }

    /**
     * map转vo ()<br>
     * 
     * @param map
     * @return
     */
    public static CompanyInfoVo mapToVo(Map<String, Object> map) {
        if (map == null)
            return null;
        CompanyInfoVo vo = new CompanyInfoVo();
        vo.setMail(MapUtils.getString(map, "mail"));
        vo.setPhone(MapUtils.getString(map, "phone"));
        vo.setOrgCode(MapUtils.getString(map, "orgCode"));
        vo.setOrgName(MapUtils.getString(map, "orgName"));
        vo.setOrgNameShort(MapUtils.getString(map, "orgNameShort"));
        vo.setIntroduction(MapUtils.getString(map, "introduction"));
        vo.setSectionName(MapUtils.getString(map, "sectionName"));
        vo.setSectionCode(MapUtils.getString(map, "sectionCode"));
        vo.setModifyTime(MapUtils.getString(map, "modifyTime"));
        vo.setOrgRegCode(MapUtils.getString(map, "orgRegCode"));
        vo.setCd_guid(MapUtils.getString(map, "cd_guid"));
        vo.setLogoUrl(MapUtils.getString(map, "logoUrl"));
        vo.setTopName(MapUtils.getString(map, "topName"));
        vo.setDomainID(MapUtils.getInteger(map, "domainID"));
        vo.setDomainURL(MapUtils.getString(map, "domainURL"));
        vo.setpDomainID(MapUtils.getInteger(map, "pDomainID"));
        vo.setCurrentStudyYearCode(MapUtils.getString(map, "currentStudyYearCode"));
        vo.setCurrentTermCode(MapUtils.getString(map, "currentTermCode"));
        vo.setCurrentYearTermCode(MapUtils.getString(map, "currentYearTermCode"));
        vo.setpDomainID(MapUtils.getInteger(map, "pDomainID"));
        vo.setpDomainID(MapUtils.getInteger(map, "pDomainID"));
        return vo;
    }

    /**
     * 查询机构列表 ()<br>
     * 
     * @param mapList
     * @return
     */
    public static List<CompanyInfoVo> getCompanyInfoListVo(List<Map<String, Object>> mapList) {
        List<CompanyInfoVo> list = new ArrayList<CompanyInfoVo>();
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                list.add(mapToVo(map));
            }
        }
        return list;
    }

}
