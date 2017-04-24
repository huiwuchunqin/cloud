package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;

public interface IPointConvertDetailService {
    /**
     * 查询兑换明细 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getConvertDetail(Map<String, Object> param, Integer page, Integer rows);
}
