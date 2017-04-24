package com.baizhitong.resource.manage.orgReport.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.dao.report.OrgResTypeMakeDailyDao;
import com.baizhitong.resource.manage.orgReport.service.OrgResAggregateService;

/**
 * 机构资源汇总统计接口 OrgResGatherServiceImpl TODO
 * 
 * @author creator gaow 2016年11月22日 上午10:38:11
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class OrgResAggragateServiceImpl implements OrgResAggregateService {
    /**
     * 机构资源汇总
     */
    @Autowired
    private OrgResTypeMakeDailyDao resTypeMakeDailyDao;

    /**
     * ()<br>
     * 
     * @param param
     * @return list
     */
    public List<Map<String, Object>> getOrgResAggregate(Map<String, Object> sqlParam) {
        return resTypeMakeDailyDao.getAllCompanyResTypeMakeDaily(sqlParam);
    }
}
