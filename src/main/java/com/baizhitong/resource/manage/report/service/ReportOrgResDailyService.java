package com.baizhitong.resource.manage.report.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.storage.ListManagedBlock;

import com.baizhitong.common.Page;

public interface ReportOrgResDailyService {
    /**
     * 查询机构资源每日日报 ()<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page getReportOrgResDaily(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 查询资源分页列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getResPageList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询资源统计累加数据 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getSum(Map<String, Object> param);
}
