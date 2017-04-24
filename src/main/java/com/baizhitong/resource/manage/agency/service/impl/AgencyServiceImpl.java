package com.baizhitong.resource.manage.agency.service.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.manage.agency.service.IAgencyService;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.resource.model.test.Test;

/**
 * 代理商接口 AgencyServiceImpl TODO
 * 
 * @author creator gaow 2017年2月20日 下午8:19:14
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class AgencyServiceImpl extends BaseService implements IAgencyService {

    /**
     * 机构dao
     */
    private @Autowired ShareOrgDao     shareOrgDao;

    /**
     * 机构机构
     */
    private @Autowired ICompanyService companyService;
    /**
     * 登录接口
     */
    @Autowired
    LoginService                       loginService;

    /**
     * 更新机构信息 ()<br>
     * 
     * @param org 机构信息
     * @return 操作结果
     */
    public ResultCodeVo updateAgency(ShareOrg org) {
       
        // 域名检测 不能重复
        /*
         * if(StringUtils.isNotEmpty(org.getDomainURL())){ ShareOrg
         * sameDomain=shareOrgDao.getOrgByDomain(org.getDomainURL());
         * if(sameDomain!=null&&!sameDomain.getOrgCode().equals(org.getOrgCode())){ return
         * ResultCodeVo.errorCode("域名已经被使用"); } }
         */
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
        int count = shareOrgDao.updateAgency(org, getIp(), "agencyService");
        return count > 0 ? ResultCodeVo.rightCode("更新成功") : ResultCodeVo.errorCode("更新失败");
    }

    /**
     * 新增学校 ()<br>
     * 
     * @param org 代理商
     * @return 操作结果
     */
    public ResultCodeVo addAgencySchool(HttpServletRequest request, ShareOrg org, String password,
                    String loginAccount) {
        String orgCode = getOrgCode() ;
        Integer domainID = BeanHelper.getDomianID(request);
        // 给学校补充属性
        org.setAgencyLevel(null); // 代理商级别
        org.setAgency(orgCode); // 代理商编码
        org.setFlagAgency(0); // 是否是机构代理商
        org.setDomainID(domainID);// 域名id
        return companyService.saveCompany(org, org.getSectionCode(), password, loginAccount,
                        CoreConstants.USER_ROLE_AGENCY_SCHOOL);
    }

    /**
     * 新增代理商 ()<br>
     * 
     * @param org 代理商
     * @return 操作结果
     */
    public ResultCodeVo addAgency(HttpServletRequest request, ShareOrg org, String password, String loginAccount) {
        String orgCode = getOrgCode() ;
        Map<String, Object> agencyInfo = shareOrgDao.getAgencyInfo(orgCode);

        // 当前代理商级别
        Integer agencyLevel = MapUtils.getInteger(agencyInfo, "agencyLevel");
        // 域名id
        Integer domainID = MapUtils.getInteger(agencyInfo, "domainID");
        // 防止当前机构没有初始化
        if (agencyLevel == null) {
            agencyLevel = 0;
        }
        // 给代理补充属性
        org.setAgencyLevel(++agencyLevel); // 代理商级别
        org.setAgency(orgCode); // 机构编码
        org.setFlagAgency(1); // 是否是机构代理商
        org.setDomainID(domainID);// 代理商域名id

        return companyService.saveCompany(org, org.getSectionCode(), password, loginAccount,
                        CoreConstants.USER_ROLE_AGENCY);
    }

    /**
     * 查询代理商信息 ()<br>
     * 
     * @param param 查询参数
     * @param page 页码
     * @param rows 每页记录数
     * @return page页
     */
    public Page list(Map<String, Object> param, Integer page, Integer rows) {
        // TODO Auto-generated method stub
        return shareOrgDao.getAgencyPage(param, page, rows);
    }

    /**
     * 查询学校 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page listSchool(Map<String, Object> param, Integer page, Integer rows) {
        return shareOrgDao.getAgencySchool(param, page, rows);
    }

    /**
     * 禁用或者启用机构 ()<br>
     * 
     * @param state 1启用 0禁用
     * @param orgCode 机构编码
     * @return 操作结果
     */
    public ResultCodeVo updateState(Integer state, String orgCode) {
        return shareOrgDao.updateState(state, orgCode) > 0 ? ResultCodeVo.rightCode("修改成功")
                        : ResultCodeVo.errorCode("修改失败");

    }

}
