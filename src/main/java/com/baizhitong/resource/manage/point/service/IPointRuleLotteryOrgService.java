package com.baizhitong.resource.manage.point.service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointRuleLotteryOrg;

public interface IPointRuleLotteryOrgService {
    /**
     * 查询积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    Page getList(String orgCode, Integer page, Integer rows);

    /**
     * 机构积分规则 ()<br>
     * 
     * @param ruleOrg
     * @return
     */
    ResultCodeVo add(PointRuleLotteryOrg ruleOrg);

    /**
     * 查询机构积分规则 ()<br>
     * 
     * @param id
     * @return
     */
    PointRuleLotteryOrg getById(Integer id);

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    ResultCodeVo delete(Integer id);

}
