package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

public interface IPointGoodsGrantPlatfromService {
    /**
     * 查询发放概要 ()<br>
     * 
     * @return
     */
    Page getList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 插入发放概要 ()<br>
     * 
     * @return
     */
    ResultCodeVo insert(String orgCode, String goodsName);
}
