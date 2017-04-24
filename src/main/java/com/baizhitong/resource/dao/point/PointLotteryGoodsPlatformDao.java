package com.baizhitong.resource.dao.point;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointLotteryGoodsPlatform;

public interface PointLotteryGoodsPlatformDao {
    /**
     * 新增 ()<br>
     * 
     * @param goods
     * @return
     */
    boolean add(PointLotteryGoodsPlatform goods);

    /**
     * 查询商品列表 ()<br>
     * 
     * @param goodsName
     * @param role
     * @param page
     * @param rows
     * @return
     */
    Page getList(String goodsName, Integer role, Integer page, Integer rows);

    /**
     * 查询商品 ()<br>
     * 
     * @param id
     * @return
     */
    PointLotteryGoodsPlatform getPlatformGoods(Integer id);

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    int deleteGood(Integer id);

    /**
     * 查询剩余的概率 ()<br>
     * 
     * @param orgCode
     * @return
     */
    double getLeftProbality(Integer role, String orgCode);

    /**
     * 查询某个角色商品的数量 ()<br>
     * 
     * @param role
     * @return
     */
    int getCount(int role);
}
