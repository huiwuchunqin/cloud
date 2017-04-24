package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointRuleAcquireOrg;

public interface IPointRuleAcquireOrgService {
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
     * @param id
     * @return
     */
    public ResultCodeVo modify(String pointOrg, Integer id);

    /**
     * 通过code查询 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRuleAcquireOrg getById(Integer id);

    /**
     * 把平台导入机构 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public ResultCodeVo importDefaultToOrg(String orgCode);
}
