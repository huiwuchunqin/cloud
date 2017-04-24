package com.baizhitong.resource.manage.company.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.encrypt.MD5;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CodeConstants;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.PinYinHelper;
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
import com.baizhitong.resource.manage.point.service.IPointRuleAcquireOrgService;
import com.baizhitong.resource.manage.questionType.service.impl.QuestionTypeServiceImpl;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.studyYear.service.IOrgYearTermService;
import com.baizhitong.resource.manage.studyYear.service.IPlatfromYearTermService;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.resource.model.company.ShareOrgSection;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.share.ShareCodeGradeVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSectionVo;
import com.baizhitong.resource.model.vo.share.ShareCodeSubjectVo;
import com.baizhitong.resource.model.vo.share.ShareUserLoginVo;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.TimeUtils;

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
    /** 学科题型 */
    private @Autowired QuestionTypeServiceImpl      questionTypeService;
    /** 登录dao */
    private @Autowired ShareUserLoginDao            userLoginDao;
    private @Autowired SharePlatformDao             sharePlatformDao;
    /** 错题原因dao */
    private @Autowired ShareWrongQuestionUpgradeDao wrongQuestionUpGradeDao;
    /** 角色云盘空间dao */
    private @Autowired ShareOrgCloudDiskParamDao    shareOrgCloudDiskParamDao;
    /** 积分取得规则 */
    private  @Autowired IPointRuleAcquireOrgService acquireOrgService;

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
     * 保存机构信息 ()<br>
     * 
     * @param org 机构信息
     * @param sectionCodes 学段信息
     * @param password 密码
     * @param loginAccount 登录账号
     * @param role 角色
     * @return
     */
    public ResultCodeVo saveCompany(ShareOrg org, String sectionCodes, String password, String loginAccount,
                    String role) {
       
        String ip = getIp();
        SharePlatform platform = sharePlatformDao.getByCodeGlobal();
        // 校验手机号是否存在或者已被他人绑定
        if (StringUtils.isNotEmpty(org.getPhone())) {
            long count = shareOrgDao.getSamePhoneOrg(org.getPhone(), org.getOrgCode());
            if (count > 0) {
                return ResultCodeVo.errorCode("该手机号已被使用，请重新输入！");
            }
        }
        // 校验邮箱是否存在或者已被他人绑定
        if (StringUtils.isNotEmpty(org.getMail())) {
            long count = shareOrgDao.getSameMailOrg(org.getMail(), org.getOrgCode());
            if (count > 0) {
                return ResultCodeVo.errorCode("该邮箱已被使用，请重新输入！");
            }
        }
        if (StringUtils.isNotEmpty(org.getOrgCode())) {
            ShareOrg oldOrg = shareOrgDao.getOrg(org.getOrgCode());
            oldOrg.setLogoUrl(org.getLogoUrl());
            oldOrg.setMail(org.getMail());
            oldOrg.setOrgCodeType(org.getOrgCodeType());
            oldOrg.setOrgName(org.getOrgName());
            oldOrg.setOrgNameShort(org.getOrgNameShort());
            oldOrg.setOrgType(org.getOrgType());
            oldOrg.setPhone(org.getPhone());
            oldOrg.setTopWorkNo(org.getTopWorkNo());
            oldOrg.setTopName(org.getTopName());
            oldOrg.setExerciseAutoSaveInterval(org.getExerciseAutoSaveInterval());
            oldOrg.setLearningAutoSaveInterval(org.getLearningAutoSaveInterval());
            oldOrg.setIcp_no(org.getIcp_no());
            oldOrg.setValidDateEnd(org.getValidDateEnd());
            oldOrg.setValidDateStart(org.getValidDateStart());
            oldOrg.setModifyIP(ip);
            oldOrg.setModifyPgm("companyService");
            oldOrg.setModifyTime(new Timestamp(new Date().getTime()));
            boolean success = shareOrgDao.saveOrUpdate(oldOrg);
            userLoginDao.updateSchoolName(org.getOrgCode(), org.getOrgName());
            if (success) {

                // 更新cookie
                updateCookieCompanyInfo(oldOrg);
                return ResultCodeVo.rightCode("保存成功");

            } else {
                return ResultCodeVo.errorCode("保存失败");
            }
        }
        List<Map<String, Object>> platfrmYearTerm = platformYearTermService.selectYearTerm(sectionCodes);
        if(platfrmYearTerm==null||platfrmYearTerm.size()<=0){
            return ResultCodeVo.errorCode("平台没有机构所属学段的学年学期信息,请先初始化数据!");
        }
        /********** 机构 *************/
        String orgCode = sysCodeService.getCode("orgCode", "platformCode", platform.getCodeGlobal());
        org.setOrgCode(orgCode);
        org.setModifyIP(ip);
        org.setModifyPgm("companyService");
        org.setModifyTime(new Timestamp(new Date().getTime()));
        org.setGid(UUID.randomUUID().toString());
        org.setCd_guid(orgCode);
        org.setOrgGroupNo(0);
        org.setFlagValid(CoreConstants.FLAG_COMPANY_VALIDAYE_YES);
        org.setSchoolTypeCode("--");
        org.setDistrictTypeCode("--");
        org.setSysVer(0);

        /********** 学校管理员 *************/
        ShareUserLoginVo user = new ShareUserLoginVo();
        user.setGid(UUID.randomUUID().toString());
        user.setOrgCode(orgCode);
        user.setOrgName(org.getOrgName());
        user.setUserName("管理员");
        if (StringUtils.isNotEmpty(loginAccount)) {
            user.setLoginAccount(loginAccount);
        } else {
            loginAccount = PinYinHelper.getCompanyAccount(org.getOrgNameShort()).toLowerCase();
            user.setLoginAccount(loginAccount);
        }

        if (StringUtils.isNotEmpty(password)) {
            user.setLoginPwd(MD5.calcMD5(password));
        } else {
            user.setLoginPwd(MD5.calcMD5(CoreConstants.DEFAULT_PWD));
        }

        user.setUserRole(CoreConstants.LOGIN_USER_ROLE_ADMIN);// 30这样不能登前台
        ResultCodeVo vo = loginService.addLoginUser(user, role);

        if (!vo.getSuccess()) {
            if (CodeConstants.FLAG_ACCOUNT_EXIST.equals(vo.getCode())) {
                vo.setMsg("登录名已被使用");
            }
            return vo;
        }
        // 答题器模式下需要额外新增一个备用管理员
        if (SystemConfig.agentEnable) {
            /********** 额外管理员给上级代理商用 *************/
            ShareUserLoginVo standbyAccount = new ShareUserLoginVo();
            standbyAccount.setGid(UUID.randomUUID().toString());
            standbyAccount.setOrgCode(orgCode);
            standbyAccount.setOrgName(org.getOrgName());
            standbyAccount.setUserName("管理员");
            standbyAccount.setLoginAccount("bzt_" + loginAccount);
            standbyAccount.setLoginPwd(MD5.calcMD5(CoreConstants.DEFAULT_PWD));
            standbyAccount.setUserRole(CoreConstants.LOGIN_USER_ROLE_ADMIN);// 30这样不能登前台
            standbyAccount.setStandbyAccount(CoreConstants.STANDBY_ACCOUNT_YES);
            ResultCodeVo vo2 = loginService.addLoginUser(standbyAccount, role);
            if (!vo2.getSuccess()) {
                if (CodeConstants.FLAG_ACCOUNT_EXIST.equals(vo.getCode())) {
                    vo2.setMsg("备用账号已存在");
                }
                return vo2;
            }
        }
        boolean success = shareOrgDao.saveOrUpdate(org);

        /********** 学年学期初始化 *************/
    
        
        if (platfrmYearTerm != null && platfrmYearTerm.size() > 0) {
            for (Map map : platfrmYearTerm) {
                orgYearTermService.addOrgTerm(MapUtils.getString(map, "gid"), orgCode);
            }
        }

        // 机构学年学期字段补充
        autoUpdateOrgYearTerm();

        // 代理商新增可以没有学段
        if (!StringUtils.isEmpty(sectionCodes)) {
            String code[] = sectionCodes.split(",");

            /********** 学段初始化 *************/
            for (String _code : code) {
                ShareOrgSection section = new ShareOrgSection();
                section.setOrgCode(orgCode);
                section.setSectionCode(_code);
                section.setFlagDelete(0);
                section.setModifyIP(ip);
                section.setGid(UUID.randomUUID().toString());
                section.setModifyPgm("companyService");
                section.setModifyTime(new Timestamp(new Date().getTime()));
                shareOrgSectionDao.addOrgSection(section);
            }
            /********** 学科初始化 *************/
            List<ShareCodeSubjectVo> allSubjectVo = new ArrayList<ShareCodeSubjectVo>();

            // 一个机构可能对应多个学段 查询每个学段下的学科
            for (String _code : code) {
                List<Map<String, Object>> list = sectionDao.getSectionSubject(_code);
                List<ShareCodeSubjectVo> subjectVoList = new ShareCodeSubjectVo().getMapToVoList(list);
                if (list == null || list.size() <= 0) {
                    continue;
                }
                allSubjectVo.addAll(subjectVoList);
            }
            String[] subjects = null;
            if (allSubjectVo != null && allSubjectVo.size() > 0) {
                subjects = new String[allSubjectVo.size()];
                for (int i = 0; i < allSubjectVo.size(); i++) {
                    subjects[i] = allSubjectVo.get(i).getCode();
                }
            }
            companySubjectService.saveCompanySubject(subjects, orgCode);

            /********** 学科题型初始化 *************/
            if (allSubjectVo != null && allSubjectVo.size() > 0) {
                for (ShareCodeSubjectVo subect : allSubjectVo) {
                    questionTypeService.addDefaultQuestionType(subect.getCode(), orgCode);
                }

            }

            /********** 学科错题原因 *************/
            wrongQuestionUpGradeDao.insert(orgCode);

            /********** 云盘 *************/
            shareOrgCloudDiskParamDao.insertOrgClod(orgCode);
            
            /********** 积分取得规则 *************/
            acquireOrgService.importDefaultToOrg(orgCode);
        }

        return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
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

}
