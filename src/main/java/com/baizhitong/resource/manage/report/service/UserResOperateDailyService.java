package com.baizhitong.resource.manage.report.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * 用户_日次_资源操作统计Service
 * 
 * @author creator zhangqiang 2016年7月20日 下午2:48:56
 * @author updater
 *
 * @version 1.0.0
 */
public interface UserResOperateDailyService {

    /**
     * 
     * (分页查询使用情况统计详情数据)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page queryUsageReportDetailInfo(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (导出excel数据)<br>
     * 
     * @param sectionCode 学段编码
     * @param orgCode 机构编码
     * @param userRole 用户身份
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     */
    HSSFWorkbook getExcel(String sectionCode, String orgCode, String baseDateStart, String baseDateEnd);

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    public Map<String, Object> queryTotalInfo(Map<String, Object> param);

}
