package com.baizhitong.resource.dao.share;

import com.baizhitong.resource.model.share.SharePlatformSettingsRes;

/**
 * 
 * 平台-资源中心设置DAO接口
 * 
 * @author creator ZhangQiang 2016年9月19日 上午9:40:19
 * @author updater
 *
 * @version 1.0.0
 */
public interface SharePlatformSettingsResDao {

    /**
     * 
     * (查询平台-资源中心设置信息)<br>
     * 
     * @return
     */
    SharePlatformSettingsRes select();

    /**
     * 
     * (新增或修改)<br>
     * 
     * @param entity
     * @return
     */
    boolean save(SharePlatformSettingsRes entity);
}
