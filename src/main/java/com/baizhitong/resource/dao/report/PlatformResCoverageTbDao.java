package com.baizhitong.resource.dao.report;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.report.PlatformResCoverageTb;

public interface PlatformResCoverageTbDao {
    /**
     * 查询教材资源覆盖率 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getPage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 查询教材资源覆盖率列表
     */
    List<Map<String, Object>> getList(Map<String, Object> param);

    /**
     * 根据教材章节查询最新 ()<br>
     * 
     * @param tbCode
     * @return
     */
    Map<String, Object> getByTbCode(String tbCode);

    /**
     * 查询章节覆盖情况 ()<br>
     * 
     * @param tbCode
     * @param baseDate
     * @return
     */
    PlatformResCoverageTb getByTbCode(String tbCode, Integer baseDate);

    /**
     * id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public Map<String, Object> getById(Integer id);
}
