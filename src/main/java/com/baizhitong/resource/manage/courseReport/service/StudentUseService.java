package com.baizhitong.resource.manage.courseReport.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * StudentUseService 学生使用情况统计
 * 
 * @author creator zhanglzh 2017年1月3日 下午2:01:15
 * @author updater
 *
 * @version 1.0.0
 */
public interface StudentUseService {
    /**
     * 
     * (获取学生使用情况统计总数)<br>
     * 
     * @param param
     * @return
     */
    public Map<String, Object> getStudentLessonUseTotal(Map<String, Object> param);

    /**
     * 
     * (分页获取学生使用情况统计)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page<Map<String, Object>> getStudentLessonUse(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param userName 学生姓名
     * @param orgName 机构名称
     * @param studyYearTermCode 学年学期编码
     * @return
     * @throws Exception
     */
    public HSSFWorkbook getExcel(String userName, String orgName, String studyYearTermCode) throws Exception;

}
