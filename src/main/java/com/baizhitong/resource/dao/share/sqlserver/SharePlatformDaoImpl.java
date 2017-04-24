package com.baizhitong.resource.dao.share.sqlserver;

import org.springframework.stereotype.Repository;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.model.share.SharePlatform;

@Repository
public class SharePlatformDaoImpl extends BaseSqlServerDao<SharePlatform> implements SharePlatformDao {
    /**
     * 查询当前平台信息 ()<br>
     * 
     * @return
     */
    public SharePlatform getByCodeGlobal() {
        QueryRule qr = QueryRule.getInstance();
        // qr.andEqual("codeGlobal", CoreConstants.CodeGlobal);
        return super.findUnique(qr);
    }

    /**
     * 
     * (添加或修改平台信息)<br>
     * 
     * @param entity
     * @return
     */
    @Override
    public boolean add(SharePlatform entity) {
        try {
            return super.saveOne(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
