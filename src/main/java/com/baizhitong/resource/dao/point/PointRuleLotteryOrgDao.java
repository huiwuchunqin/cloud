package com.baizhitong.resource.dao.point;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointRuleLotteryOrg;

/**
 * 机构积分规则dao接口 PointRuleLotteryOrgDao TODO
 * 
 * @author creator gaow 2016年6月23日 下午4:51:15
 * @author updater
 *
 * @version 1.0.0
 */
public interface PointRuleLotteryOrgDao {
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
    boolean add(PointRuleLotteryOrg ruleOrg);

    /**
     * 查询机构积分规则 ()<br>
     * 
     * @param id
     * @return
     */
    PointRuleLotteryOrg getById(Integer id);

    /**
     * 让之前的规则失效 ()<br>
     * 
     * @return
     */
    int makeExpire(String orgCode);

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    int delete(int id);

}