package com.baizhitong.resource.manage.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.dao.report.PlatformResCoverageTbDao;
import com.baizhitong.resource.manage.report.service.PlatformResCoverageTbService;
import com.baizhitong.resource.model.report.PlatformResCoverageTb;

/**
 * 教材资源覆盖率 PlatformResCoverageTbServiceImpl TODO
 * 
 * @author creator BZT 2016年7月8日 上午11:13:00
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PlatformResCoverageTbServiceImpl implements PlatformResCoverageTbService {
    private @Autowired PlatformResCoverageTbDao platformResCoverageTbDao;

    /**
     * 查询教材资源覆盖率 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows) {
        return platformResCoverageTbDao.getPage(param, page, rows);
    }

    /**
     * 查询教材资源覆盖率列表
     */
    public List<Map<String, Object>> getList(Map<String, Object> param) {
        return platformResCoverageTbDao.getList(param);
    }

    /**
     * 根据教材章节查询最新 ()<br>
     * 
     * @param tbCode
     * @return
     */
    public Map<String, Object> getByTbCode(String tbCode) {
        return platformResCoverageTbDao.getByTbCode(tbCode);
    }

    /**
     * 查询章节覆盖情况 ()<br>
     * 
     * @param tbCode
     * @param baseDate
     * @return
     */
    public PlatformResCoverageTb getByTbCode(String tbCode, Integer baseDate) {
        return platformResCoverageTbDao.getByTbCode(tbCode, baseDate);
    }

    /**
     * id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public Map<String, Object> getById(Integer id) {
        return platformResCoverageTbDao.getById(id);
    }
}
