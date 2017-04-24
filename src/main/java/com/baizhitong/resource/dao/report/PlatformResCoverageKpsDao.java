package com.baizhitong.resource.dao.report;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;

public interface PlatformResCoverageKpsDao {
    /**
     * 查询知识体系资源覆盖率 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows);

    /**
     * id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public Map<String, Object> getById(Integer id);

    /**
     * 根据只是体系编码查询 ()<br>
     * 
     * @param kpsCode
     * @return
     */
    public Map<String, Object> getByKpCode(String kpsCode);

    /**
     * 查询列标 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getList(Map<String, Object> param);

    /**
     * 查询统计信息 ()<br>
     * 
     * @param kpCode 知识点编码
     * @return
     */
    public Map<String, Object> getReportInfo(String kpCode, String baseDate);
}
