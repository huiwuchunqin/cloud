package com.baizhitong.resource.dao.point;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointGoodsPutPlatform;

public interface PointGoodsPutPlatformDao {
    /**
     * 查询入库明细 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    Page getList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 保存或新增 ()<br>
     * 
     * @param pointGoodsPut
     * @return
     */
    boolean saveOrupdate(PointGoodsPutPlatform pointGoodsPutPlatform);

    /**
     * 根据id查询 ()<br>
     * 
     * @param goodsCode
     * @return
     */
    PointGoodsPutPlatform getByGoodsCode(String goodsCode);

    /**
     * 更新商品库存量 ()<br>
     * 
     * @param count
     * @param goodsCode
     * @return
     */
    int updateGoodsPlatformGoodsAmout(Integer count, String goodsCode);
}
