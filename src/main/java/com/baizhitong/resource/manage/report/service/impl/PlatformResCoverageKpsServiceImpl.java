package com.baizhitong.resource.manage.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.report.PlatformResCoverageKpsDao;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageKpsService;

/**
 * 知识体系资源覆盖率 PlatformResCoverageKpsServiceImpl TODO
 * 
 * @author creator BZT 2016年7月8日 上午11:12:24
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PlatformResCoverageKpsServiceImpl implements PlatformResCoverageKpsService {
    private @Autowired PlatformResCoverageKpsDao coverageKpsDao;

    /**
     * 查询知识体系资源覆盖率 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows) {
        return coverageKpsDao.getPage(param, page, rows);
    }

    /**
     * 根据只是体系编码查询 ()<br>
     * 
     * @param kpsCode
     * @return
     */
    public Map<String, Object> getByKpsCode(String kpsCode) {
        return coverageKpsDao.getByKpCode(kpsCode);
    }

    /**
     * id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public Map<String, Object> getById(Integer id) {
        return coverageKpsDao.getById(id);
    }

    /**
     * 查询列标 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getList(Map<String, Object> param) {
        // TODO Auto-generated method stub
        return coverageKpsDao.getList(param);
    }
}
