package com.baizhitong.resource.common.core.dao;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.baizhitong.common.dao.jdbc.PageCompositeDaoSupport;

/**
 * SqlServer数据基类
 * 
 * @author creator cuidc 2015/12/08
 * @author updater
 */
public class BaseSqlServerDao<T> extends PageCompositeDaoSupport<T> {
    /**
     * 设定数据源
     * 
     * @param dataSource 数据源
     */
    @Override
    @Resource(name = "dataSource_mooc")
    public void setDataSource(DataSource dataSource) {
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }
}