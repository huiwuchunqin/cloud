package com.baizhitong.resource.manage.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.log.LoginStatisticsDao;

@Service
public class LoginStatisticsServiceImpl implements com.baizhitong.resource.manage.log.service.LoginStatisticsService {

    @Autowired
    private LoginStatisticsDao loginStatisticsDao;

    @Override
    public Page getLoginStatistics(String orgName, Integer role, String startTime, String endTime, Integer pageNo,
                    Integer pageSize) {
        return loginStatisticsDao.getLoginStatistics(orgName, role, startTime, endTime, pageNo, pageSize);
    }

    @Override
    public Page getLoginStatisticsDetail(String orgCode, Integer role, String startTime, String endTime, Integer pageNo,
                    Integer pageSize) {
        return loginStatisticsDao.getLoginStatisticsDetail(orgCode, role, startTime, endTime, pageNo, pageSize);
    }

    @Override
    public Long getLoginStatisticsTotal(String orgName, Integer role, String startTime, String endTime) {
        return loginStatisticsDao.getLoginStatisticsTotal(orgName, role, startTime, endTime);
    }

    @Override
    public Long getLoginStatisticsDetailTotal(String orgCode, Integer role, String startTime, String endTime) {
        return loginStatisticsDao.getLoginStatisticsDetailTotal(orgCode, role, startTime, endTime);
    }

}
