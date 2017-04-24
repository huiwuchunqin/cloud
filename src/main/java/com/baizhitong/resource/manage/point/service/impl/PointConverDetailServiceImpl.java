package com.baizhitong.resource.manage.point.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.point.PointConvertDetailDao;
import com.baizhitong.resource.manage.point.service.IPointConvertDetailService;

@Service
public class PointConverDetailServiceImpl implements IPointConvertDetailService {
    /**
     * 商品兑换明细
     */
    private @Autowired PointConvertDetailDao pointConvertDetailDao;

    /**
     * 查询兑换明细 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getConvertDetail(Map<String, Object> param, Integer page, Integer rows) {
        return pointConvertDetailDao.getConvertDetail(param, page, rows);
    }
}
