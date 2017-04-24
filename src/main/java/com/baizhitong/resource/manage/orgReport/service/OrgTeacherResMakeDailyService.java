package com.baizhitong.resource.manage.orgReport.service;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.baizhitong.common.Page;

/**
 * 
 * 机构作者统计Service
 * 
 * @author creator ZhangQiang 2016年11月23日 上午10:21:51
 * @author updater
 *
 * @version 1.0.0
 */
public interface OrgTeacherResMakeDailyService {

    /**
     * 
     * (分页查询作者统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     */
    Page<Map<String, Object>> queryAuthorReportInfo(Map<String, Object> param, Integer page, Integer rows);

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
     * (以Excel形式数据导出)<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param orgCode 机构编码
     * @param baseDateStart 开始日期
     * @param baseDateEnd 结束日期
     * @param userName 作者名称
     * @param shareLevel 共享级别
     * @return
     * @throws Exception
     */
    HSSFWorkbook getExcel(String sectionCode, String subjectCode, String orgCode, String baseDateStart,
                    String baseDateEnd, String userName, Integer shareLevel) throws Exception;

}
