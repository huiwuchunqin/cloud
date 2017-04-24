package com.baizhitong.resource.manage.domain.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.dao.share.ShareDomainDao;
import com.baizhitong.resource.manage.domain.service.IDomainService;
import com.baizhitong.resource.model.share.ShareDomain;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

/**
 * 域名接口实现类 DomainServiceImpl TODO
 * 
 * @author creator gaow 2017年3月7日 下午2:44:25
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class DomainServiceImpl extends BaseService implements IDomainService {
    /**
     * 域名dao
     */
    @Autowired
    private ShareDomainDao domainDao;
    /**
     * 机构dao
     */
    @Autowired
    private ShareOrgDao    orgDao;

    /**
     * 保存域信息 ()<br>
     * 
     * @param domain
     * @return 操作结果
     */
    public ResultCodeVo saveDomain(ShareDomain domain) {

        // 修改
        if (domain.getId() != null) {
           
            String domainUrl = getDomainURL();
            // 域名和原来不相同
            if (!domainUrl.equals(domain.getDomainURL())) {

                long exit = domainDao.count(domain.getDomainURL());
                if (exit > 0) {
                    return ResultCodeVo.errorCode("域名已被使用");
                }

                // 查看有机构机构在一个域名下
                long sameDomainCount = orgDao.getSameDomainCount(domain.getId());

                // 存在相同的就做新增操作 避免子代理商机构 改变父的域名的情况
                if (sameDomainCount > 1) {
                    domain.setId(null);
                    ResultCodeVo result = addDomain(domain);

                    // 更新缓存的机构信息
                    updateCookieCompanyInfo((Integer) result.getData(), domain.getDomainURL());
                    return result;
                } else {
                    ResultCodeVo result = updateDomain(domain);
                    return result;
                }
            }
            // 直接修改就行了因为也不会影响别人
            else {
                return updateDomain(domain);
            }
        } else {
            long exit = domainDao.count(domain.getDomainURL());
            if (exit > 0) {
                return ResultCodeVo.errorCode("域名已被使用");
            } else {
                return addDomain(domain);
            }
        }
    }

    /**
     * 更新cookie里面的机构信息 ()<br>
     * 
     * @param domainID
     * @param domainURL
     */
    public void updateCookieCompanyInfo(Integer domainID, String domainURL) {
        HttpServletRequest request=getRequest();
        CompanyInfoVo company =  getCompanyInfo();
        company.setDomainID(domainID);
        company.setDomainURL(domainURL);
        BeanHelper.writeCompanyToAdminCookie(request, company);

    }

    /**
     * 修改 ()<br>
     * 
     * @param domain
     * @return
     */
    public ResultCodeVo updateDomain(ShareDomain domain) {
       
        UserInfoVo user =getUserInfoVo();
        domain.setModifier(user.getUserName());
        domain.setModifyIP(getIp());
        Integer count = domainDao.updateDomain(domain);
        return count > 0 ? ResultCodeVo.rightCode("更新成功") : ResultCodeVo.errorCode("更新失败");
    }

    /**
     * 新增域名 ()<br>
     * 
     * @param domain
     * @return 操作结果
     */
    public ResultCodeVo addDomain(ShareDomain domain) {
       
        String orgCode = getOrgCode() ;
        UserInfoVo user =getUserInfoVo();
        domain.setCreateTime(new Timestamp(new Date().getTime()));
        domain.setCreator(user.getUserName());
        domain.setCreatorIP(getIp());
        domain.setModifier(user.getUserName());
        domain.setModifyIP(getIp());
        domain.setModifyPgm("domainService");
        domain.setModifyTime(new Timestamp(new Date().getTime()));
        Integer id = domainDao.addDomain(domain);
        int count = orgDao.updateOrgDomain(orgCode, id);
        return count > 0 ? ResultCodeVo.rightCode("新增成功", id) : ResultCodeVo.errorCode("新增失败");
    }

}
