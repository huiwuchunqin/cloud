package com.baizhitong.resource.dao.share;

import com.baizhitong.resource.model.share.SharePlatformCloudDiskParam;

/**
 * 
 * 平台-云盘参数DAO接口
 * 
 * @author creator ZhangQiang 2016年9月19日 上午9:46:35
 * @author updater
 *
 * @version 1.0.0
 */
public interface SharePlatformCloudDiskParamDao {

    /**
     * 
     * (根据用户身份查询)<br>
     * 
     * @param userRole 用户身份
     * @return
     */
    SharePlatformCloudDiskParam selectByUserRole(Integer userRole);

    /**
     * 
     * (新增或修改一条记录)<br>
     * 
     * @param entity
     * @return
     */
    boolean insertOrUpdate(SharePlatformCloudDiskParam entity);
}
