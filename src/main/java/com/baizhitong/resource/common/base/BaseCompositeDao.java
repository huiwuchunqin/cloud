package com.baizhitong.resource.common.base;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baizhitong.common.dao.jdbc.PageCompositeDaoSupport;

/**
 * 
 * 数据库表结构为复合主键（即主键有多个的table），需继承该方法
 * 
 * @author xuaihua
 */
public class BaseCompositeDao<T> extends PageCompositeDaoSupport<T> {

    /**
     * 日志输出
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }

}
