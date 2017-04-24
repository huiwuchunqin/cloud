package com.baizhitong.resource.common.core.dao;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.sql.DataSource;

import com.baizhitong.common.dao.jdbc.BasePageDaoSupport;

/**
 * 资源网dao的基类
 * 
 * @author xuaihua
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseMySqlDao<T extends Serializable, PK extends Serializable> extends BasePageDaoSupport<T, PK> {

    @Resource(name = "dataSource_mooc")
    public void setDataSource(DataSource dataSource) {
        super.setDataSourceReadOnly(dataSource);
        super.setDataSourceWrite(dataSource);
    }

    @Override
    protected String getPKColumn() {
        return "id";
    }

}
