package com.baizhitong.resource.manage.agency.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.manage.agency.service.IAgencyService;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.login.service.LoginService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.company.ShareOrg;
import com.baizhitong.utils.StringUtils;

/**
 * 代理商控制器 AgencyAction TODO
 * 
 * @author creator gaow 2017年2月22日 上午9:50:04
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping("/manage/agency")
@Controller
public class AgencyAction extends BaseAction {
    /**
     * 机构dao
     */
    private @Autowired ShareOrgDao     shareOrgDao;
    /**
     * 学段接口
     */
    private @Autowired SectionService  sectionService;
    /**
     * 代理商接口
     */
    private @Autowired IAgencyService  agencyService;
    /**
     * 机构机构
     */
    private @Autowired ICompanyService companyService;
    /**
     * 登录接口
     */
    private @Autowired LoginService    loginService;

    /**
     * 跳转到代理机构信息页面()<br>
     * 
     * @return 页面
     */
    @RequestMapping("/toAgencyInfo.html")
    public String toAgencyInfo(HttpServletRequest request, ModelMap model, String orgCode) {
        // 机构编码为空修改自己
        if (StringUtils.isEmpty(orgCode)) {
            orgCode = getOrgCode() ;
        }
        model.put("org", JSON.toJSONString(shareOrgDao.getAgencyInfo(orgCode)));
        model.put("noCancel", true);
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("查询学段出错", e);
            e.printStackTrace();
        }
        return "/manage/agency/agency.html";
    }

    /**
     * 跳转到代理机构信息修改页面 ()<br>
     * 
     * @return 页面
     */
    @RequestMapping("/toAgencyEdit.html")
    public String toAgencyEdit(HttpServletRequest request, ModelMap model, String orgCode) {
        // 机构编码为空修改自己
        if (StringUtils.isEmpty(orgCode)) {
            orgCode = getOrgCode() ;
        }
        model.put("org", JSON.toJSONString(shareOrgDao.getAgencyInfo(orgCode)));
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("查询学段出错", e);
            e.printStackTrace();
        }
        return "/manage/agency/agency.html";
    }

    /**
     * 跳转到代理商 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPage.html")
    public String toAgency(HttpServletRequest request, ModelMap model) {
        String orgCode = getOrgCode() ;
        Map<String, Object> agencyInfo = shareOrgDao.getAgencyInfo(orgCode);
        Integer currentLevel = MapUtils.getInteger(agencyInfo, "agencyLevel");

        // 五级代理商不能再新增
        if (currentLevel != null && currentLevel.equals(CoreConstants.AGENCY_LEVEL_FIVE)) {
            model.put("canAdd", false);
        } else {
            model.put("canAdd", true);
        }
        return "/manage/agency/agencyList.html";
    }

    /**
     * 跳转到代理商机构 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPageSchool.html")
    public String toAgencySchool(ModelMap model) {
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("查询学段出错", e);
            e.printStackTrace();
        }
        return "/manage/agency/agencySchoolList.html";
    }

    /**
     * 跳转到代理机构新增页面 ()<br>
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toAgencyAdd.html")
    public String toAgencyAdd(HttpServletRequest request, ModelMap model) {
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("查询学段出错", e);
            e.printStackTrace();
        }
        return "/manage/agency/agency.html";

    }

    /**
     * 跳转到代理机构学校新增页面 ()<br>
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("toAgencySchoolAdd.html")
    public String toAgencySchoolAdd(HttpServletRequest request, ModelMap model) {
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            model.put("schoolRelated", true);
        } catch (Exception e) {
            log.error("查询学段出错", e);
            e.printStackTrace();
        }
        return "/manage/agency/school.html";

    }

    /**
     * 跳转到代理机构信息修改页面 ()<br>
     * 
     * @return 页面
     */
    @RequestMapping("/toAgencySchoolEdit.html")
    public String toAgencySchoolEdit(HttpServletRequest request, ModelMap model, String orgCode) {
        model.put("schoolRelated", true);
        model.put("org", JSON.toJSONString(shareOrgDao.getAgencyInfo(orgCode)));
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("查询学段出错", e);
            e.printStackTrace();
        }
        return "/manage/agency/school.html";
    }

    /**
     * 查询代理商信息 ()<br>
     * 
     * @param request http请求
     * @param orgName 代理商名称
     * @param agencyLevel 代理商级别
     * @param page 页码
     * @param rows 每页记录数
     * @return page
     */
    @RequestMapping("/page.html")
    @ResponseBody
    public Page listAgency(HttpServletRequest request, String orgName, Integer agencyLevel, Integer page,
                    Integer rows) {
        String orgCode = getOrgCode() ;
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 50;
        }
        // 查询条件
        Map<String, Object> queryParam = new HashMap<String, Object>();

        queryParam.put("orgName", orgName);
        queryParam.put("agencyLevel", agencyLevel);
        queryParam.put("agency", orgCode); // 所属代理商

        return agencyService.list(queryParam, page, rows);
    }

    /**
     * 更新代理商信息 ()<br>
     * 
     * @param org 代理商信息
     * @return 操作结果
     */
    @RequestMapping("/updateAgency.html")
    @ResponseBody
    public ResultCodeVo updateAgency(ShareOrg org) {
        return agencyService.updateAgency(org);
    }

    /**
     * 新增代理商信息 ()<br>
     * 
     * @param request http请求
     * @param org 代理商信息
     * @param password 密码
     * @return 操作结果
     */
    @RequestMapping("/addAgency.html")
    @ResponseBody
    public ResultCodeVo addAgency(HttpServletRequest request, ShareOrg org, String password, String loginAccount) {
        return agencyService.addAgency(request, org, password, loginAccount);

    }

    /**
     * 新增代理商学校信息 ()<br>
     * 
     * @param request http请求
     * @param org 代理商信息
     * @param password 密码
     * @return 操作结果
     */
    @RequestMapping("/addSchool.html")
    @ResponseBody
    public ResultCodeVo addSchool(HttpServletRequest request, ShareOrg org, String password, String loginAccount) {
        return agencyService.addAgencySchool(request, org, password, loginAccount);

    }

    /**
     * 查询代理商学校信息 ()<br>
     * 
     * @param request http请求
     * @param orgName 机构名称
     * @param sectionCode 学段
     * @param page 页码
     * @param rows 每页记录数
     * @return page
     */
    @RequestMapping("/school/page.html")
    @ResponseBody
    public Page schoolList(HttpServletRequest request, String orgName, String sectionCode, Integer page, Integer rows) {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 50;
        }
        Map<String, Object> param = new HashMap<String, Object>();

        String orgCode = getOrgCode() ;
        param.put("orgCode", orgCode);
        param.put("orgName", orgName);
        param.put("sectionCode", sectionCode);
        return agencyService.listSchool(param, page, rows);
    }

    /**
     * 禁用或者启用学校
     * 
     * @param flagValid 1启用 0禁用
     * @param orgCode 机构编码
     * @return 操作结果
     */
    @RequestMapping("/updateState.html")
    @ResponseBody
    public ResultCodeVo updateSchoolState(Integer flagValid, String orgCode) {
        return agencyService.updateState(flagValid, orgCode);
    }

    /**
     * 跳转到代理商用户信息页面 ()<br>
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/toUserInfo.html")
    public String toAgencyUserInfo(HttpServletRequest request) {
        return "/manage/agency/agencyUserInfo.html";
    }

    /**
     * 查询代理商用户信息 ()<br>
     * 
     * @param request
     * @return
     */
    @Deprecated
    @RequestMapping(value = "/agencyUserInfo.html")
    @ResponseBody
    public Page getUserInfo(HttpServletRequest request, Integer page, Integer rows, String orgName, String loginAccount,
                    String userName) {
        if (page == null) {
            page = 1;
        }
        if (rows == null) {
            rows = 50;
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("loginAccount", loginAccount);
        param.put("userName", userName);
        param.put("orgName", orgName);
        String orgCode = getOrgCode() ;
        return loginService.getAgencyUserInfo(orgCode, page, rows, param);
    }

    /**
     * 获取代理商信息 ()<br>
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getAgencyInfo.html")
    @ResponseBody
    public ShareOrg getAgencyInfo(HttpServletRequest request, Integer domainID) {
        return shareOrgDao.getOrgByDomain(domainID);
    }
}
