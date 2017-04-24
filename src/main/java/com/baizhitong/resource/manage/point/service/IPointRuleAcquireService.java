package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointRuleAcquire;
import com.baizhitong.resource.model.point.PointRuleAcquireOrg;

public interface IPointRuleAcquireService {
    /**
     * 查询积分规则列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 修改 ()<br>
     * 
     * @param pointOrg
     * @param actionCode
     * @return
     */
    public ResultCodeVo modify(String pointOrg, String pointPlat, String actionCode);

    /**
     * 通过code查询 ()<br>
     * 
     * @param actionCode
     * @return
     */
    public PointRuleAcquire getByCode(String actionCode);
}
