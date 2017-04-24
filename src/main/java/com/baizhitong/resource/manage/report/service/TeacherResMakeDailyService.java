package com.baizhitong.resource.manage.report.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * 教师_日次_资源制作统计Service
 * 
 * @author creator zhangqiang 2016年7月19日 下午12:42:42
 * @author updater
 *
 * @version 1.0.0
 */
public interface TeacherResMakeDailyService {

    /**
     * 
     * (查询作者统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    Page<Map<String, Object>> queryAuthorReportInfo(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 
     * (以Excel形式导出作者统计数据)<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param orgName 机构名称
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @param userName 作者姓名
     * @param shareLevel 共享级别
     * @return
     * @throws Exception
     */
    public HSSFWorkbook getExcel(String sectionCode, String subjectCode, String orgName, String baseDateStart,
                    String baseDateEnd, String userName, Integer shareLevel) throws Exception;

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    public Map<String, Object> queryTotalInfo(Map<String, Object> param);

}
