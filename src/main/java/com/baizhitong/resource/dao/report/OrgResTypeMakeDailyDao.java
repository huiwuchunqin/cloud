package com.baizhitong.resource.dao.report;

import java.util.List;
import java.util.Map;

public interface OrgResTypeMakeDailyDao {
    /**
     * 查询机构资源类别统计 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getRestypeMakeDaily(Map<String, Object> param);

    /**
     * 资源汇总统计 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getAllCompanyResTypeMakeDaily(Map<String, Object> param);
}
