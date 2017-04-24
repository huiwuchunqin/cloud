package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointGoodsPlatform;

/**
 * 
 * 可兑商品-平台Service
 * 
 * @author creator zhangqiang 2016年6月27日 下午8:22:59
 * @author updater
 *
 * @version 1.0.0
 */
public interface IPointGoodsPlatformService {

    /**
     * 
     * 查询平台上架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    Page<PointGoodsPlatform> getShelvesGoodsPage(Map<String, Object> param);

    /**
     * 
     * 查询平台下架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    Page<PointGoodsPlatform> queryNextGoodsPage(Map<String, Object> param);

    /**
     * 
     * 礼品上架或下架操作
     * 
     * @param id 主键id
     * @param type 操作类型（1：下架；2：上架）
     * @return
     */
    int operateGoodsUpOrDown(Integer id, Integer type);

    /**
     * 
     * 新增或修改平台礼品
     * 
     * @param pointGoodsOrg
     * @return
     */
    ResultCodeVo savePlatformGift(PointGoodsPlatform pointGoodsPlatform);

    /**
     * 
     * 根据id查询平台礼品
     * 
     * @param id 主键id
     * @return
     */
    PointGoodsPlatform getPlatformGiftById(Integer id);

    /**
     * 
     * 删除平台礼品
     * 
     * @param id 主键id
     * @return
     */
    int deletePlatformGift(String id);
}
