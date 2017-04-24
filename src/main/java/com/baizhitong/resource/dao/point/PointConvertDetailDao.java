package com.baizhitong.resource.dao.point;

import java.util.Map;

import com.baizhitong.common.Page;

/**
 * 
 * PointConvertDetailDao TODO
 * 
 * @author creator 商品兑换明细 2016年6月28日 上午10:18:01
 * @author updater
 *
 * @version 1.0.0
 */
public interface PointConvertDetailDao {
    /**
     * 查询兑换明细 ()<br>
     * 
     * @param param
     * @return
     */
    Page getConvertDetail(Map<String, Object> param, Integer page, Integer rows);
}
