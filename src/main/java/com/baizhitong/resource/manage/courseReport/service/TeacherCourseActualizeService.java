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
public interface TeacherCourseActualizeService {

    /**
     * 
     * (分页查询使用情况统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page queryTeacherCourseActualizeInfo(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    public HSSFWorkbook getExcel(String startDate, String endDate, String publishStartDate, String publishEndDate,
                    String sectionCode, String subjectCode, String yearTermCode, String teacherName, String orgName)
                                    throws Exception;

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
     * (分页查询教师实施情况统计信息<详情页面>)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page queryTeacherCourseActualizeDetailInfo(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (数据导出<详情界面>)<br>
     * 
     * @param teacherCode 教师编码
     * @param orgCode 机构编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param yearTermCode 学年学期编码
     * @param teacherName 教师名称
     * @param orgName 机构名称
     * @return
     * @throws Exception
     */
    HSSFWorkbook getExcelDetail(String teacherCode, String orgCode, String startDate, String endDate,
                    String publishStartDate, String publishEndDate, String sectionCode, String subjectCode,
                    String yearTermCode, String teacherName, String orgName) throws Exception;

}
