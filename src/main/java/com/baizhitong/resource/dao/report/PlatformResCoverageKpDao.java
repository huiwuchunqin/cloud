package com.baizhitong.resource.dao.report;

import com.baizhitong.resource.model.report.PlatformResCoverageKp;

public interface PlatformResCoverageKpDao {
    /**
     * 查询知识点资源覆盖率 ()<br>
     * 
     * @param tbCode
     * @return
     */
    public PlatformResCoverageKp getKpCoverage(String kpCode, Integer baseDate);
}
