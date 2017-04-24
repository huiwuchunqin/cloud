package com.baizhitong.resource.manage.report.service;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * 学科统计Service
 * 
 * @author creator ZhangQiang 2016年9月6日 下午4:58:35
 * @author updater
 *
 * @version 1.0.0
 */
public interface SubjectReportService {

    /**
     * 
     * (查询学科统计信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> querySubjectReportInfo(Map<String, Object> param) throws Exception;

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Map<String, Object> queryTotalInfo(Map<String, Object> param);

    /**
     * 
     * (数据导出)<br>
     * 
     * @param sectionCode
     * @param subjectCode
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    HSSFWorkbook getExcel(String sectionCode, String subjectCode, String startDate, String endDate, String shareLevel)
                    throws Exception;

    /**
     * 
     * (查询学科使用情况统计信息)<br>
     * 
     * @param param 查询参数
     * @return 学科使用情况列表
     */
    List<Map<String, Object>> querySubjectUsageReportInfo(Map<String, Object> param);

    /**
     * 
     * (查询学科使用情况总数)<br>
     * 
     * @param param 查询参数
     * @return 学科使用情况总数
     */
    Map<String, Object> queryTotalUsageInfo(Map<String, Object> param);

    /**
     * 
     * (数据导出)<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     * @throws Exception
     */
    HSSFWorkbook getUsageExcel(String sectionCode, String subjectCode, String startDate, String endDate)
                    throws Exception;

}
