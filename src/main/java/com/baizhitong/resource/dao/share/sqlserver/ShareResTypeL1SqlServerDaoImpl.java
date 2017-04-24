package com.baizhitong.resource.dao.share.sqlserver;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareResTypeL1Dao;
import com.baizhitong.resource.model.share.ShareResTypeL1;

/**
 * 资源一级分类SqlServer数据接口实现
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
@Service("ShareResTypeL1Dao")
public class ShareResTypeL1SqlServerDaoImpl extends BaseSqlServerDao<ShareResTypeL1> implements ShareResTypeL1Dao {
    /**
     * 获取资源一级分类集合
     * 
     * @return 集合
     * @throws Exception 异常
     */
    @Override
    public List<ShareResTypeL1> selectResTypeL1List() throws Exception {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.addAscOrder("code");
        return super.find(queryRule);
    }
    /************************************************** |以上已确认| **************************************************/
}