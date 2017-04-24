package com.baizhitong.resource.dao.lessonreport;

import java.util.Map;

import com.baizhitong.common.Page;

/**
 * 
 * LessonReportDao 课时统计dao
 * 
 * @author creator zhanglzh 2017年1月3日 上午9:50:33
 * @author updater
 *
 * @version 1.0.0
 */
public interface LessonReportDao {

    /**
     * 
     * (学科使用情况总数统计)<br>
     * 
     * @param param
     * @return
     */
    Map<String, Object> selectSubjectUsedTotal(Map<String, Object> param);

    /**
     * 
     * (学科使用情况统计)<br>
     * 
     * @param param
     * @return
     */
    Page<Map<String, Object>> selectPageSubjectUsed(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (学校使用情况总数统计)<br>
     * 
     * @param param
     * @return
     */
    Map<String, Object> selectOrgUsedTotal(Map<String, Object> param);

    /**
     * 
     * (学校使用情况统计)<br>
     * 
     * @param param
     * @return
     */
    Page<Map<String, Object>> selectPageOrgUsed(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (学生使用情况总数统计)<br>
     * 
     * @param param
     * @return
     */
    Map<String, Object> selectStudentUsedTotal(Map<String, Object> param);

    /**
     * 
     * (学生使用情况统计)<br>
     * 
     * @param param
     * @return
     */
    Page<Map<String, Object>> selectPageStudentUsed(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (教师使用情况总数统计)<br>
     * 
     * @param param
     * @return
     */
    Map<String, Object> selectTeacherUsedTotal(Map<String, Object> param);

    /**
     * 
     * (教师使用情况统计)<br>
     * 
     * @param param
     * @return
     */
    Page<Map<String, Object>> selectPageTeacherUsed(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (数量汇总统计列表)<br>
     * 
     * @param param
     * @return
     */
    Page<Map<String, Object>> getCourseCountAggregate(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (数量汇总统计总数)<br>
     * 
     * @param param
     * @return
     */
    Map<String, Object> selectCourseCountAggregateTotal(Map<String, Object> param);

    /**
     *
     * (作者统计列表)<br>
     *
     * @param param
     * @return
     */
    Page<Map<String, Object>> getCourseAuthorReport(Map<String, Object> param, Integer page, Integer rows);

    /**
     *
     * (作者统计总数)<br>
     *
     * @param param
     * @return
     */
    Map<String, Object> selectCourseAuthorReportTotal(Map<String, Object> param);

    /**
     *
     * (学校统计列表)<br>
     *
     * @param param
     * @return
     */
    Page<Map<String, Object>> getCourseSchoolReport(Map<String, Object> param, Integer page, Integer rows);

    /**
     *
     * (学校统计总数)<br>
     *
     * @param param
     * @return
     */
    Map<String, Object> selectCourseSchoolReportTotal(Map<String, Object> param);

    /**
     *
     * (学科统计列表)<br>
     *
     * @param param
     * @return
     */
    Page<Map<String, Object>> getCourseSubjectReport(Map<String, Object> param, Integer page, Integer rows);

    /**
     *
     * (学科统计总数)<br>
     *
     * @param param
     * @return
     */
    Map<String, Object> selectCourseSubjectReportTotal(Map<String, Object> param);

    /**
     *
     * (学科统计列表)<br>
     *
     * @param param
     * @return
     */
    Page<Map<String, Object>> getCourseCountAggregateDetail(Map<String, Object> param, Integer page, Integer rows);

    /**
     *
     * (学科统计总数)<br>
     *
     * @param param
     * @return
     */
    Map<String, Object> selectCourseCountAggregateDetailTotal(Map<String, Object> param);

    /**
     *
     * (教师课程实施情况统计列表)<br>
     *
     * @param param
     * @return
     */
    Page<Map<String, Object>> getTeacherCourseActualize(Map<String, Object> param, Integer page, Integer rows);

    /**
     *
     * (教师课程实施情况统计总数)<br>
     *
     * @param param
     * @return
     */
    Map<String, Object> selectTeacherCourseActualizeTotal(Map<String, Object> param);

    /**
     *
     * (学校课程实施情况统计列表)<br>
     *
     * @param param
     * @return
     */
    Page<Map<String, Object>> getSchoolCourseActualize(Map<String, Object> param, Integer page, Integer rows);

    /**
     *
     * (学校课程实施情况统计总数)<br>
     *
     * @param param
     * @return
     */
    Map<String, Object> selectSchoolCourseActualizeTotal(Map<String, Object> param);

    /**
     *
     * (学科课程实施情况统计列表)<br>
     *
     * @param param
     * @return
     */
    Page<Map<String, Object>> getSubjectCourseActualize(Map<String, Object> param, Integer page, Integer rows);

    /**
     *
     * (学科课程实施情况统计总数)<br>
     *
     * @param param
     * @return
     */
    Map<String, Object> selectSubjectCourseActualizeTotal(Map<String, Object> param);

    /**
     *
     * (学科课程实施情况统计详情列表)<br>
     *
     * @param param
     * @return
     */
    Page<Map<String, Object>> getSubjectCourseActualizeDetail(Map<String, Object> param, Integer page, Integer rows);

}
