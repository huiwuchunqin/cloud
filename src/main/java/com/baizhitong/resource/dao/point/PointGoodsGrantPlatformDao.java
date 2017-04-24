package com.baizhitong.resource.dao.point;

import java.util.List;
import java.util.Map;
import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointGoodsGrantPlatform;

public interface PointGoodsGrantPlatformDao {
    /**
     * 查询商品发放概要 ()<br>
     * 
     * @param actionBatch
     * @return
     */
    List<PointGoodsGrantPlatform> getSummaryList(String actionBatch);

    /**
     * 插入商品发放概要 ()<br>
     * 
     * @return
     */
    /**
     * 插入 ()<br>
     * 
     * @return
     */
    public int insert(String studyYearCode, String termCode, String yearTermCode, String actionBatch, String modifier,
                    String modifierIP, String orgName, String goodsName);

    /**
     * 查询商品发放概要 ()<br>
     * 
     * @return
     */
    public Page getList(Map<String, Object> param, Integer page, Integer rows);
}
