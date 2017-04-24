package com.baizhitong.resource.dao.share.sqlserver;

import org.springframework.stereotype.Component;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.SharePlatformSettingsResDao;
import com.baizhitong.resource.model.share.SharePlatformSettingsRes;

/**
 * 
 * 平台-资源中心设置 DAO接口实现
 * 
 * @author creator ZhangQiang 2016年9月19日 上午9:44:08
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class SharePlatformSettingsResDaoImpl extends BaseSqlServerDao<SharePlatformSettingsRes>
                implements SharePlatformSettingsResDao {

    /**
     * 
     * (查询平台-资源中心设置信息)<br>
     * 
     * @return
     */
    @Override
    public SharePlatformSettingsRes select() {
        QueryRule qr = QueryRule.getInstance();
        return super.findUnique(qr);
    }

    /**
     * 
     * (新增或修改)<br>
     * 
     * @param entity
     * @return
     */
    @Override
    public boolean save(SharePlatformSettingsRes entity) {
        try {
            return super.saveOne(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
