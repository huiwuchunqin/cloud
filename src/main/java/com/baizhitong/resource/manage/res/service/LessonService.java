package com.baizhitong.resource.manage.res.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;

/**
 * 
 * 课时审核Service
 * 
 * @author creator ZhangQiang 2016年8月23日 下午2:18:15
 * @author updater
 *
 * @version 1.0.0
 */
public interface LessonService {

    /**
     * 
     * (分页查询课时审核全部信息)<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> queryLessonAllInfoPage(Map<String, Object> param) throws Exception;

    /**
     * 
     * (根据审核状态，分页查询课时审核信息)<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    Page<Map<String, Object>> queryLessonCheckInfoPage(Map<String, Object> param) throws Exception;

    /**
     * 
     * (课时审核)<br>
     * 
     * @param resCode
     * @param shareCheckLevel
     * @param shareCheckStatus
     * @param checkComments
     * @return
     * @throws Exception
     */
    ResultCodeVo saveExamine(String[] resCode, String[] shareCheckLevel, Integer shareCheckStatus, String checkComments)
                    throws Exception;

    /**
     * 
     * (课时审核全部数据导出)<br>
     * 
     * @param shareCheckStatus
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param startDate
     * @param endDate
     * @param lessonName
     * @param teacherName
     * @param orgName
     * @return
     */
    HSSFWorkbook getAllDataExcel(Integer shareCheckStatus, String sectionCode, String subjectCode, String gradeCode,
                    String startDate, String endDate, String lessonName, String teacherName, String orgName,
                    Integer isSyncCourse) throws Exception;

    /**
     * 
     * (课时审核：待审核，已通过，已退回数据导出)<br>
     * 
     * @param shareCheckStatus
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param startDate
     * @param endDate
     * @param lessonName
     * @param teacherName
     * @param orgName
     * @param shareCheckLevel
     * @return
     * @throws Exception
     */
    HSSFWorkbook getCheckDataExcel(Integer shareCheckStatus, String sectionCode, String subjectCode, String gradeCode,
                    String startDate, String endDate, String lessonName, String teacherName, String orgName,
                    Integer shareCheckLevel, Integer isSyncCourse) throws Exception;

}
