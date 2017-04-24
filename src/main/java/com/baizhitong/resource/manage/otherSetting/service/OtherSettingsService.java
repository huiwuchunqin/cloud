package com.baizhitong.resource.manage.otherSetting.service;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.SharePlatformCloudDiskParam;
import com.baizhitong.resource.model.share.SharePlatformSettingsRes;

/**
 * 
 * 后台其他设置Service
 * 
 * @author creator ZhangQiang 2016年9月19日 上午10:01:46
 * @author updater
 *
 * @version 1.0.0
 */
public interface OtherSettingsService {

    /**
     * 
     * (查询平台资源设置信息)<br>
     * 
     * @return
     * @throws Exception
     */
    SharePlatformSettingsRes query() throws Exception;

    /**
     * 
     * (保存资源评论设置的修改)<br>
     * 
     * @param entity 实体
     * @return
     * @throws Exception
     */
    ResultCodeVo saveChangeAllowResComment(SharePlatformSettingsRes entity) throws Exception;

    /**
     * 
     * (根据用户身份查询云盘参数信息)<br>
     * 
     * @param userRole
     * @return
     * @throws Exception
     */
    SharePlatformCloudDiskParam queryCloudDiskInfoByUserRole(Integer userRole) throws Exception;

    /**
     * 
     * (保存云盘参数修改)<br>
     * 
     * @param request
     * @param entity
     * @return
     */
    ResultCodeVo saveCloudDiskParamModify(SharePlatformCloudDiskParam entity) throws Exception;

    /**
     * 
     * (保存临时账号设置的修改)<br>
     * 
     * @param entity 实体
     * @return
     * @throws Exception
     */
    ResultCodeVo saveChangeAllowLogin(SharePlatform entity) throws Exception;

}
