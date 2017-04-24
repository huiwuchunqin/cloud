package com.baizhitong.resource.common.base;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baizhitong.common.dao.jdbc.BasePageDaoSupport;

/**
 * baseDao, 数据库表结构为自增型结构的table对应的dao，需继承该方法
 * 
 * @author xuaihua
 *
 */
public class BaseDao<T extends Serializable, PK extends Serializable> extends BasePageDaoSupport<T, PK> {
    /**
     * 日志输出
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * (数据源设置)<br>
     * 目前全部使用默认参数名称，<br>
     * 如果各自项目组，有自己的需求，可以覆盖该方法
     * 
     * @param dataSource 数据源
     */
    @Resource(name = "dataSource")
    public void setDataSource(DataSource dataSource) {
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }

    @Override
    protected String getPKColumn() {
        return "id";
    }
}
