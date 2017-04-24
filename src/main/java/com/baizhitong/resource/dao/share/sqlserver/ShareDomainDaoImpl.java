package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareDomainDao;
import com.baizhitong.resource.model.share.ShareDomain;

/**
 * 机构域名信息dao ShareDomainDaoImpl TODO
 * 
 * @author creator gaow 2017年3月7日 上午10:32:35
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class ShareDomainDaoImpl extends BaseSqlServerDao<ShareDomain> implements ShareDomainDao {
    /**
     * 查询域名信息 ()<br>
     * 
     * @param domianURL 域名地址
     * @return
     */
    public ShareDomain getDomain(String domianURL) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        qr.andEqual("domainURL", domianURL);
        List<ShareDomain> domianList = super.find(qr);
        return (domianList != null && domianList.size() > 0) ? domianList.get(0) : null;
    }

    /**
     * 新增域名信息 ()<br>
     * 
     * @param shareDomain
     * @return
     */
    public Integer addDomain(ShareDomain shareDomain) {
        try {
            Map<String, Object> idMap = this.saveAndReturnId(shareDomain);
            return MapUtils.getInteger(idMap, "id");
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新域名信息 ()<br>
     * 
     * @param shareDomain
     * @return
     */
    public Integer updateDomain(ShareDomain shareDomain) {
        try {
            return super.update(shareDomain);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 查询域名是否存在 ()<br>
     * 
     * @param domainURL
     * @return
     */
    public long count(String domainURL) {
        String sql = "select count(*) from share_domain where domainURL=:domainURL";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("domainURL", domainURL);
        return super.queryCount(sql, param);
    }
}
