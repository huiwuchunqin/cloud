package com.baizhitong.resource.manage.point.service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointLotteryGoodsPlatform;

public interface IPointLotteryGoodsPlatformService {
    /**
     * 添加商品 ()<br>
     * 
     * @param good
     * @return
     */
    ResultCodeVo add(PointLotteryGoodsPlatform good);

    /**
     * 查询商品列表 ()<br>
     * 
     * @param goodsName
     * @param PlatformCode
     * @param role
     * @param page
     * @param rows
     * @return
     */
    Page getPageList(String goodsName, Integer role, Integer page, Integer rows);

    /**
     * 查询商品 ()<br>
     * 
     * @param id
     * @return
     */
    PointLotteryGoodsPlatform getPlatformGoods(Integer id);

    /**
     * 删除商品 ()<br>
     * 
     * @param id
     * @return
     */
    public ResultCodeVo deleteGood(Integer id);
}
