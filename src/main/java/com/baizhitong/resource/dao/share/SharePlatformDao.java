package com.baizhitong.resource.dao.share;

import com.baizhitong.resource.model.share.SharePlatform;

public interface SharePlatformDao {
    /**
     * 根据平台编码查询平台信息 ()<br>
     * 
     * @return
     */
    public SharePlatform getByCodeGlobal();

    /**
     * 
     * (添加或修改平台信息)<br>
     * 
     * @param entity
     * @return
     */
    boolean add(SharePlatform entity);

}
