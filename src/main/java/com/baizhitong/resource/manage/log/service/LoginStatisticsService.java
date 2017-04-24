package com.baizhitong.resource.manage.log.service;

import java.util.Map;

import com.baizhitong.common.Page;

/**
 * LoginStatisticsService 登录统计service
 * 
 * @author creator ZOUKAI 2017年2月27日 下午4:55:55
 * @author updater
 *
 * @version 1.0.0
 */
public interface LoginStatisticsService {

    /**
     * (查询登录统计)<br>
     * 
     * @param orgName 学校名称
     * @param role 角色
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNo 页码
     * @param pageSize 页大小
     * @return
     */
    Page getLoginStatistics(String orgName, Integer role, String startTime, String endTime, Integer pageNo,
                    Integer pageSize);

    /**
     * (查询登录统计详情)<br>
     * 
     * @param orgCode 学校编码
     * @param role 角色
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param pageNo 页码
     * @param pageSize 页大小
     * @return
     */
    Page getLoginStatisticsDetail(String orgCode, Integer role, String startTime, String endTime, Integer pageNo,
                    Integer pageSize);

    Long getLoginStatisticsTotal(String orgName, Integer role, String startTime, String endTime);

    Long getLoginStatisticsDetailTotal(String orgCode, Integer role, String startTime, String endTime);
}
