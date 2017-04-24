package com.baizhitong.resource.manage.courseReport.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * CourseAuthorReportService 作者统计service
 * 
 * @author creator Zhangqd 2017年1月3日 下午5:51:08
 * @author updater
 *
 * @version 1.0.0
 */
public interface CourseAuthorReportService {

    /**
     * 
     * (分页查询使用情况统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page queryAuthorReportInfo(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    public HSSFWorkbook getExcel(String startDate, String endDate, String sectionCode, String subjectCode,
                    String shareLevel, String teacherName, String orgName) throws Exception;

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    public Map<String, Object> queryTotalInfo(Map<String, Object> param);

}
