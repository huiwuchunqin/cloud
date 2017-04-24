package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointGoodsPutPlatform;

public interface IPointGoodsPutPlatformService {

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
    ResultCodeVo saveOrupdate(PointGoodsPutPlatform pointGoodsPutPlatform);

    /**
     * 根据id查询 ()<br>
     * 
     * @param goodsCode
     * @return
     */
    PointGoodsPutPlatform getByGoodsCode(String goodsCode);
}
