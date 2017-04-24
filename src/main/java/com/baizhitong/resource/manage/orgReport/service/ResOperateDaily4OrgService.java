package com.baizhitong.resource.manage.orgReport.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * 机构使用情况统计Service
 * 
 * @author creator ZhangQiang 2016年11月23日 上午10:17:04
 * @author updater
 *
 * @version 1.0.0
 */
public interface ResOperateDaily4OrgService {

    /**
     * 
     * (查询机构使用情况统计)<br>
     * 
     * @param param
     * @return
     */
    List<Map<String, Object>> queryUsageReportInfo(Map<String, Object> param);

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param
     * @return
     */
    Map<String, Object> queryTotalInfo(Map<String, Object> param);

    /**
     * 
     * (数据导出)<br>
     * 
     * @param orgCode
     * @param baseDateStart
     * @param baseDateEnd
     * @return
     * @throws Exception
     */
    HSSFWorkbook getExcel(String orgCode, String baseDateStart, String baseDateEnd) throws Exception;

}
