package com.baizhitong.resource.manage.courseReport.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * SchoolUseService 学校使用情况统计service
 * 
 * @author creator zhanglzh 2017年1月3日 下午2:00:51
 * @author updater
 *
 * @version 1.0.0
 */
public interface SchoolUseService {
    /**
     * 
     * (获取学校使用情况统计总数)<br>
     * 
     * @param param
     * @return
     */
    public Map<String, Object> getSchoolLessonUseTotal(Map<String, Object> param);

    /**
     * 
     * (分页获取学校使用情况统计)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page<Map<String, Object>> getSchoolLessonUse(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param sectionCode 学段编码
     * @param orgName 学校名称
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    public HSSFWorkbook getExcel(String sectionCode, String orgName, String baseDateStart, String baseDateEnd)
                    throws Exception;
}
