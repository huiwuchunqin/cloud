package com.baizhitong.resource.dao.point;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointGoodsOrg;

/**
 * 
 * 可兑商品-机构数据接口
 * 
 * @author creator zhangqiang 2016年6月13日 下午2:31:40
 * @author updater
 *
 * @version 1.0.0
 */
public interface PointGoodsOrgDao {

    /**
     * 
     * 获取机构上架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    Page<PointGoodsOrg> selectShelvesGoodsPage(Map<String, Object> param);

    /**
     * 
     * 获取机构下架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    Page<PointGoodsOrg> selectNextGoodsPage(Map<String, Object> param);

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
    boolean add(PointGoodsOrg entity);

    /**
     * 
     * 根据主键id查询
     * 
     * @param id 主键
     * @return
     */
    PointGoodsOrg getById(Integer id);

    /**
     * 
     * 删除机构礼品
     * 
     * @param id 主键id
     * @return
     */
    int delete(String id);

}
