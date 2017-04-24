package com.baizhitong.resource.manage.courseReport.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * SubjectCourseActualizeService 学科课程实施统计
 * 
 * @author creator zhanglzh 2017年1月11日 上午11:31:49
 * @author updater
 *
 * @version 1.0.0
 */
public interface SubjectCourseActualizeService {

    /**
     * 
     * (分页查询使用情况统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page<Map<String, Object>> querySubjectCourseActualizeInfo(Map<String, Object> param, Integer page, Integer rows);

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
     * (导出Excel)<br>
     * 
     * @param endDate 结束基准日
     * @return
     * @throws Exception
     */
    HSSFWorkbook getExcel(Integer endDate, String sectionCode, String yearTermCode, String subjectCode)
                    throws Exception;

}
