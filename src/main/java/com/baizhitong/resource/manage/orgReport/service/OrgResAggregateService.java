package com.baizhitong.resource.manage.orgReport.service;

import java.util.List;
import java.util.Map;

/**
 * 机构资源汇总 OrgResAggregateService TODO
 * 
 * @author creator gaow 2016年11月22日 上午11:41:18
 * @author updater
 *
 * @version 1.0.0
 */
public interface OrgResAggregateService {

    /**
     * 查询机构资源汇总 ()<br>
     * 
     * @param param
     * @return list
     */
    List<Map<String, Object>> getOrgResAggregate(Map<String, Object> param);

}