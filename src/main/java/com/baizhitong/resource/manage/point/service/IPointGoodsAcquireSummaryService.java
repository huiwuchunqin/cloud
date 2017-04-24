package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

public interface IPointGoodsAcquireSummaryService {
    Page getList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 领取 ()<br>
     * 
     * @return
     */
    ResultCodeVo exchange(Integer goodsLevel, String actionBatch, String adminClassCode, String userName,
                    String userRole, String ids);
}
