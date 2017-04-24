package com.baizhitong.resource.manage.report.service;

import java.util.List;
import java.util.Map;

public interface OrgResTypeMakeDailyService {
    /**
     * 查询资源分类列表 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getResTypeMakeDaily(Map<String, Object> param);

    /**
     * 查询所有机构资源分类汇总 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getAllCompanyResTypeMakeDaily(Map<String, Object> param);
}
