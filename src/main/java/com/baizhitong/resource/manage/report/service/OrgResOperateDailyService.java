package com.baizhitong.resource.manage.report.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * 机构_日次_资源操作统计Service
 * 
 * @author creator zhangqiang 2016年7月19日 下午8:01:02
 * @author updater
 *
 * @version 1.0.0
 */
public interface OrgResOperateDailyService {

    /**
     * 
     * (分页查询使用情况统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page queryUsageReportInfo(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param sectionCode 学段编码
     * @param orgName 机构名称
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    public HSSFWorkbook getExcel(String sectionCode, String orgName, String baseDateStart, String baseDateEnd)
                    throws Exception;

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    public Map<String, Object> queryTotalInfo(Map<String, Object> param);

}
