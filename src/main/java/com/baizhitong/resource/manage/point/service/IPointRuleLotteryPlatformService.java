package com.baizhitong.resource.manage.point.service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointRuleLotteryPlatform;

public interface IPointRuleLotteryPlatformService {
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
     * @param rulePlatform
     * @return
     */
    ResultCodeVo add(PointRuleLotteryPlatform rulePlatform);

    /**
     * 查询机构积分规则 ()<br>
     * 
     * @param id
     * @return
     */
    PointRuleLotteryPlatform getById(Integer id);

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    ResultCodeVo delete(Integer id);
}
