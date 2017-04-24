package com.baizhitong.resource.dao.point;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointRuleLotteryPlatform;

/**
 * 平台积分规则dao接口 PointRuleLotteryPlatformDao TODO
 * 
 * @author creator gaow 2016年6月23日 下午4:51:28
 * @author updater
 *
 * @version 1.0.0
 */
public interface PointRuleLotteryPlatformDao {
    /**
     * 查询积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    Page getList(Integer page, Integer rows);

    /**
     * 机构积分规则 ()<br>
     * 
     * @param ruleOrg
     * @return
     */
    boolean add(PointRuleLotteryPlatform ruleOrg);

    /**
     * 查询机构积分规则 ()<br>
     * 
     * @param id
     * @return
     */
    PointRuleLotteryPlatform getById(Integer id);

    /**
     * 让之前的规则失效 ()<br>
     * 
     * @return
     */
    int makeExpire();

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    int delete(int id);

}