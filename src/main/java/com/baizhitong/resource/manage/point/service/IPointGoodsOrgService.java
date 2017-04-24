package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointGoodsOrg;

/**
 * 
 * 可兑商品-机构Service
 * 
 * @author creator zhangqiang 2016年6月13日 下午2:17:30
 * @author updater
 *
 * @version 1.0.0
 */
public interface IPointGoodsOrgService {

    /**
     * 
     * 查询机构上架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    Page<PointGoodsOrg> getShelvesGoodsPage(Map<String, Object> param);

    /**
     * 
     * 查询机构下架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    Page<PointGoodsOrg> queryNextGoodsPage(Map<String, Object> param);

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
     * 新增或修改机构礼品
     * 
     * @param pointGoodsOrg
     * @return
     */
    ResultCodeVo saveOrgGift(PointGoodsOrg pointGoodsOrg);

    /**
     * 
     * 根据id查询机构礼品
     * 
     * @param id 主键id
     * @return
     */
    PointGoodsOrg getOrgGiftById(Integer id);

    /**
     * 
     * 删除机构礼品
     * 
     * @param id 主键id
     * @return
     */
    int deleteOrgGift(String id);

}
