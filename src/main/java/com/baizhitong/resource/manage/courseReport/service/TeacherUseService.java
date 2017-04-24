package com.baizhitong.resource.manage.courseReport.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * TeacherUseService 教师使用情况统计
 * 
 * @author creator zhanglzh 2017年1月3日 下午2:01:57
 * @author updater
 *
 * @version 1.0.0
 */
public interface TeacherUseService {
    /**
     * 
     * (获取教师使用情况统计总数)<br>
     * 
     * @param param
     * @return
     */
    public Map<String, Object> getTeacherLessonUseTotal(Map<String, Object> param);

    /**
     * 
     * (分页获取教师使用情况统计)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page<Map<String, Object>> getTeacherLessonUse(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param teacherName 教师姓名
     * @param orgName 机构名称
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @throws Exception
     */
    public HSSFWorkbook getExcel(String teacherName, String orgName, String startDate, String endDate) throws Exception;
}
