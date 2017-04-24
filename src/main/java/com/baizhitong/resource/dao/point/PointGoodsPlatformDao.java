package com.baizhitong.resource.dao.point;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointGoodsPlatform;

/**
 * 
 * 可兑商品-平台数据接口
 * 
 * @author creator zhangqiang 2016年6月13日 下午2:31:40
 * @author updater
 *
 * @version 1.0.0
 */
public interface PointGoodsPlatformDao {

    /**
     * 
     * 获取平台上架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    Page<PointGoodsPlatform> selectShelvesGoodsPage(Map<String, Object> param);

    /**
     * 
     * 获取平台下架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    Page<PointGoodsPlatform> selectNextGoodsPage(Map<String, Object> param);

    /**
     * 
     * 礼品上架或者下架操作
     * 
     * @param id 主键id
     * @param type 操作类型（1：下架；2：上架）
     * @return
     */
    int updateValidTime(Integer id, Integer type);

    /**
     * 
     * 新增或修改
     * 
     * @param entity 实体
     * @return
     */
    boolean add(PointGoodsPlatform entity);

    /**
     * 
     * 根据主键id查询
     * 
     * @param id 主键
     * @return
     */
    PointGoodsPlatform getById(Integer id);

    /**
     * 
     * 删除平台礼品
     * 
     * @param id 主键id
     * @return
     */
    int delete(String id);

}
