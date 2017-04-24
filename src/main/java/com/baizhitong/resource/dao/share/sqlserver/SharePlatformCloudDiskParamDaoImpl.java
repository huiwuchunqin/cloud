package com.baizhitong.resource.dao.share.sqlserver;

import org.springframework.stereotype.Component;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.SharePlatformCloudDiskParamDao;
import com.baizhitong.resource.model.share.SharePlatformCloudDiskParam;

/**
 * 
 * 平台-云盘参数DAO接口实现类
 * 
 * @author creator ZhangQiang 2016年9月19日 上午9:50:21
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class SharePlatformCloudDiskParamDaoImpl extends BaseSqlServerDao<SharePlatformCloudDiskParam>
                implements SharePlatformCloudDiskParamDao {

    /**
     * 
     * (根据用户身份查询)<br>
     * 
     * @param userRole 用户身份
     * @return
     */
    @Override
    public SharePlatformCloudDiskParam selectByUserRole(Integer userRole) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("userRole", userRole);
        return super.findUnique(qr);
    }

    /**
     * 
     * (新增或修改一条记录)<br>
     * 
     * @param entity
     * @return
     */
    @Override
    public boolean insertOrUpdate(SharePlatformCloudDiskParam entity) {
        try {
            return super.saveOne(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
