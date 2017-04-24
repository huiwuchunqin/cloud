package com.baizhitong.resource.dao.share;

import org.apache.poi.hssf.record.formula.functions.Count;

import com.baizhitong.resource.model.share.ShareDomain;

/**
 * 域名信息接口 ShareDomainDao TODO
 * 
 * @author creator gaow 2017年3月7日 上午10:34:07
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareDomainDao {
    /**
     * 查询域名信息 ()<br>
     * 
     * @param domianURL
     * @return
     */
    public ShareDomain getDomain(String domianURL);

    /**
     * 新增域名信息 ()<br>
     * 
     * @param shareDomain
     * @return
     */
    public Integer addDomain(ShareDomain shareDomain);

    /**
     * 更新域名信息 ()<br>
     * 
     * @param shareDomain
     * @return
     */
    public Integer updateDomain(ShareDomain shareDomain);

    /**
     * 查询域名是否存在 ()<br>
     * 
     * @param domainURL
     * @return
     */
    public long count(String domainURL);

}
