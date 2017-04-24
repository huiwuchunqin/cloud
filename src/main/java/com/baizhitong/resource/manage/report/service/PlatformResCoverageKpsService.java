package com.baizhitong.resource.manage.report.service;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;

public interface PlatformResCoverageKpsService {
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
     * 查询知识体系资源覆盖率 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public List<Map<String, Object>> getList(Map<String, Object> param);

    /**
     * 根据只是体系编码查询 ()<br>
     * 
     * @param kpsCode
     * @return
     */
    public Map<String, Object> getByKpsCode(String kpsCode);

    /**
     * id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public Map<String, Object> getById(Integer id);
}
