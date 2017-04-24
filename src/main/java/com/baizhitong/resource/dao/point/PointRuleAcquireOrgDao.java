package com.baizhitong.resource.dao.point;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointRuleAcquire;
import com.baizhitong.resource.model.point.PointRuleAcquireOrg;

public interface PointRuleAcquireOrgDao {
    /**
     * 查询积分规则 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getAcquirePage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 根据id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRuleAcquireOrg getById(Integer id);

    /**
     * 更新 ()<br>
     * 
     * @param ruleAcquire
     * @return
     */
    public boolean updatePointRuleAcquire(PointRuleAcquireOrg ruleAcquireOrg);

    /**
     * 更新积分获取规则 ()<br>
     * 
     * @param actionCode
     * @param pointOrg
     * @param pointPlt
     * @return
     */
    public int update(String actionCode, String pointOrg, String pointPlt);

    /**
     * 把平台导入机构 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public int importDefaultToOrg(String orgCode);
}
