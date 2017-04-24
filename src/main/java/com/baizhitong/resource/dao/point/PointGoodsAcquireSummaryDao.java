package com.baizhitong.resource.dao.point;

import java.util.Map;
import java.util.concurrent.Exchanger;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointGoodsAcquireSummary;

public interface PointGoodsAcquireSummaryDao {
    /**
     * 查询分页信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getPage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 兑换 ()<br>
     * 
     * @param orgCode
     */
    public int exchange(String orgCode, Integer goodsLevel, String adminClassCode, String userName, String userRole,
                    String ids);
}
