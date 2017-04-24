package com.baizhitong.resource.dao.point;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointRuleAcquire;

public interface PointRuleAcquireDao {
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
     * 根据code查询 ()<br>
     * 
     * @param actionCode
     * @return
     */
    public PointRuleAcquire getByActionCode(String actionCode);

    /**
     * 更新 ()<br>
     * 
     * @param ruleAcquire
     * @return
     */
    public boolean updatePointRuleAcquire(PointRuleAcquire ruleAcquire);
}
