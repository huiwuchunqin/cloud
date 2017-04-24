package com.baizhitong.resource.manage.company.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.dao.share.ShareOrgCloudDiskParamDao;
import com.baizhitong.resource.dao.share.ShareOrgSectionDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.ShareUserLoginDao;
import com.baizhitong.resource.dao.share.ShareWrongQuestionUpgradeDao;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.companySubject.service.impl.CompanySubjectServiceImpl;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.studyYear.service.IOrgYearTermService;
import com.baizhitong.resource.manage.studyYear.service.IPlatfromYearTermService;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DateUtils;

/**
 * 机构接口实现类 CompanyServiceImpl TODO
 * 
 * @author creator BZT 2016年1月22日 上午11:32:31
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class CompanyServiceImpl extends BaseService implements ICompanyService {
    // 机构dao
    @Autowired
    private ShareOrgDao                             shareOrgDao;
    @Autowired
    private SectionService                          sectionService;
    @Autowired
    LoginService                                    loginService;
    /** 编码生成接口 */
    private @Autowired ISysCodeService              sysCodeService;
    /** 平台学年学期接口 */
    private @Autowired IPlatfromYearTermService     platformYearTermService;
    /** 机构学年学期 */
    private @Autowired IOrgYearTermService          orgYearTermService;
    /** 机构学段导 */
    private @Autowired ShareOrgSectionDao           shareOrgSectionDao;
    /** 学段导 */
    private @Autowired ShareCodeSectionDao          sectionDao;
    /** 机构学科 */
    private @Autowired CompanySubjectServiceImpl    companySubjectService;
    /** 登录dao */
    private @Autowired ShareUserLoginDao            userLoginDao;
    private @Autowired SharePlatformDao             sharePlatformDao;
    /** 错题原因dao */
    private @Autowired ShareWrongQuestionUpgradeDao wrongQuestionUpGradeDao;
    /** 角色云盘空间dao */
    private @Autowired ShareOrgCloudDiskParamDao    shareOrgCloudDiskParamDao;

    /**
     * 
     * 查询机构分页信息
     * 
     * @param orgName 机构名称
     * @param sectionCode 学段名称
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     */
    public Page getOrgPageInfo(String orgName, String sectionCode, Integer pageSize, Integer pageNo) {
        return shareOrgDao.getOgrPageInfo(orgName, sectionCode, pageNo, pageSize);
    }

    /**
     * 查询机构信息 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public CompanyInfoVo getCompanyInfo(String orgCode) {
        Map<String, Object> orgMap = shareOrgDao.getOrgInfo(orgCode);
        return CompanyInfoVo.mapToVo(orgMap);
    }

    /**
     * 查询机构学段 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public List<ShareCodeSectionVo> getCompanySection(String orgCode) {
        List<Map<String, Object>> sectionMapList = shareOrgDao.getOrgSection(orgCode);
        return new ShareCodeSectionVo().getMapToVoList(sectionMapList);
    }

    /**
     * 查询机构年级 ()<br>
     * 
     * @param orgCode
     * @return
     * @throws Exception
     */
    public List<ShareCodeGradeVo> getCompanyGrade(String orgCode) throws Exception {
        List<ShareCodeGradeVo> allGrade = new ArrayList<ShareCodeGradeVo>();
        List<ShareCodeSectionVo> sectionList = getCompanySection(orgCode);
        if (sectionList != null && sectionList.size() > 0) {
            for (ShareCodeSectionVo section : sectionList) {
                allGrade.addAll(sectionService.getSectionGrade(section.getCode()));
            }
        }
        return allGrade;
    }

    /**
     * 
     * 根据机构名称查询机构
     * 
     * @param orgName 机构名称
     * @param isLike 1 模糊查询 0全文匹配
     * @return
     * @throws Exception
     */
    public List<ShareOrg> getCompanyByName(String orgName, Integer isLike) throws Exception {
        if (isLike == 1) {
            orgName = "%" + orgName + "%";
        }
        return shareOrgDao.getOrgByName(orgName);
    }


    /**
     * 更新cookie里面的机构信息 ()<br>
     * 
     * @param org
     */
    public void updateCookieCompanyInfo(ShareOrg org) {
       HttpServletRequest request=getRequest();
        CompanyInfoVo company =  getCompanyInfo();
        company.setOrgName(org.getOrgName());
        BeanHelper.writeCompanyToAdminCookie(request, company);

    }

    /**
     * 根据学段查询机构 ()<br>
     * 
     * @param sectionCode
     * @return
     */
    public List<Map<String, Object>> getSectionCompany(String sectionCode) {
        return shareOrgDao.getOrgList(sectionCode);
    }

    /**
     * 更新机构信息 ()<br>
     * 
     * @param orgCode
     * @param orgName
     * @param mail
     * @param orgNameShort
     * @param phone
     * @param sectionCode
     * @param logoUrl
     * @param introduction
     * @return
     */
    public ResultCodeVo updateCompany(String orgCode, String orgName, String mail, String orgNameShort, String phone,
                    String sectionCode, String logoUrl, String introduction, String topName) {
        ShareOrg org = shareOrgDao.getOrg(orgCode);
        org.setOrgName(orgName);
        org.setOrgNameShort(orgNameShort);
        org.setPhone(phone);
        org.setMail(mail);
        org.setIntroduction(introduction);
        org.setLogoUrl(logoUrl);
        org.setModifyTime(new Timestamp(new Date().getTime()));
        org.setModifyIP(getIp());
        org.setModifyPgm("CompanyService");
        org.setSectionCode(sectionCode);
        org.setTopName(topName);
        boolean success = shareOrgDao.saveOrUpdate(org);
        shareOrgSectionDao.updateOrgSection(orgCode, sectionCode);
        userLoginDao.updateSchoolName(org.getOrgCode(), org.getOrgName());
        return success ? ResultCodeVo.rightCode("更新成功") : ResultCodeVo.errorCode("更新失败");
    }

    /**
     * 查询所有机构 ()<br>
     * 
     * @return
     */
    public List<CompanyInfoVo> getAllCompany() {
        List<Map<String, Object>> list = shareOrgDao.getOrgList();
        return CompanyInfoVo.getCompanyInfoListVo(list);
    }

    /**
     * 
     * (自动更新机构表的学年学期信息)<br>
     */
    @Override
    public void autoUpdateOrgYearTerm() {
        String date = DateUtils.getDate("yyyy-MM-dd");
        Timestamp currentDate = new Timestamp(DateUtils.getDateTime(date, "yyyy-MM-dd").getTime());
        shareOrgDao.updateOrgYearTermInfoBatch(currentDate);
    }

    @Override
    public ResultCodeVo saveCompany(ShareOrg org, String sectionCodes, String password, String loginAccount,
                    String role) {
        // TODO Auto-generated method stub
        return null;
    }

}
