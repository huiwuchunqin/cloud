package com.baizhitong.resource.manage.domain.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareDomainDao;
import com.baizhitong.resource.manage.domain.service.IDomainService;
import com.baizhitong.resource.model.share.ShareDomain;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.StringUtils;

/**
 * 域名控制类 DomainAction TODO
 * 
 * @author creator gaow 2017年3月7日 下午2:36:35
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/domain")
public class DomainAction extends BaseAction {
    /**
     * 域名dao
     */
    private @Autowired ShareDomainDao domainDao;
    /**
     * 域名接口
     */
    private @Autowired IDomainService domainService;

    /**
     * 跳转到域名设置 ()<br>
     * 
     * @return url
     */
    @RequestMapping("/list.html")
    public String toDomain(HttpServletRequest request, ModelMap model) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        ShareDomain domain = domainDao.getDomain(companyInfoVo.getDomainURL());

        // 如果存在上级代理商 且代理商和自己指向同一个域 不能修改机构的logo
        if (companyInfoVo.getpDomainID() != null && companyInfoVo.getpDomainID() == companyInfoVo.getDomainID()) {
            model.put("logoEditEnable", false);
        } else {
            model.put("logoEditEnable", true);
        }

        model.put("domain", JSON.toJSONString(domain));
        return "/manage/domain/domain.html";
    }

    /**
     * 保存域名信息 ()<br>
     * 
     * @param domain
     * @return
     */
    @RequestMapping("/save.html")
    @ResponseBody
    public ResultCodeVo saveDomain(ShareDomain domain) {
        return domainService.saveDomain(domain);
    }

    /**
     * 查询域信息 ()<br>
     * 
     * @param url
     * @return
     */
    @RequestMapping("/getDomain.html")
    @ResponseBody
    public ShareDomain getDomain(String url) {
        return domainDao.getDomain(url);
    }

}
