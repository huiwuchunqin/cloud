package com.baizhitong.resource.manage.courseReport.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * CourseCountAggregateService 数量汇总统计service
 * 
 * @author creator Zhangqd 2017年1月3日 上午10:20:27
 * @author updater
 *
 * @version 1.0.0
 */
public interface CourseCountAggregateDetailService {

    /**
     * 
     * (分页查询使用情况统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page queryCourseCountAggregateDetailReportInfo(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    public HSSFWorkbook getExcel(String baseDateStart, String baseDateEnd, String shareLevel, String lessonMode,
                    String sectionCode, String subjectCode, String gradeCode, String tbcCode, String orgName,
                    String chapterCode, String lessonName) throws Exception;

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    public Map<String, Object> queryTotalInfo(Map<String, Object> param);

    /**
     * 
     * (查询最后一层节点编码集合)<br>
     * 
     * @param code
     * @return
     */
    String getChapterCodes(String code);

}
