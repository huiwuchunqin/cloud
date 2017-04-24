package com.baizhitong.resource.manage.dataSynchronization.service;

import javax.sql.DataSource;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;

public interface ISynchronizeService {
    /**
     * 数据同步 ()<br>
     * 
     * @param orgCode 机构编码
     * @param orgName 机构名称
     * @param org_cdguid 机构的guid
     * @param batch 更新批次
     * @return
     */
    public ResultCodeVo dataSynchronize(String orgCode, String orgName, String org_cdguid, Integer batch);

    public ResultCodeVo tempInsert(String orgCode, String orgGuid);

    public void updateTest(DataSource dataSource);
}
