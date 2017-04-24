package com.baizhitong.resource.manage.courseReport.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * SchoolCourseActualizeService 学校课程实施统计
 * 
 * @author creator zhanglzh 2017年1月11日 上午11:04:39
 * @author updater
 *
 * @version 1.0.0
 */
public interface SchoolCourseActualizeService {

    /**
     * 
     * (分页查询学校课程实施统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page<Map<String, Object>> querySchoolCourseActualizeInfo(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param endDate 结束基准日
     * @return
     * @throws Exception
     */
    HSSFWorkbook getExcel(Integer endDate, String sectionCode, String yearTermCode, String orgName) throws Exception;

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    Map<String, Object> queryTotalInfo(Map<String, Object> param);

}
