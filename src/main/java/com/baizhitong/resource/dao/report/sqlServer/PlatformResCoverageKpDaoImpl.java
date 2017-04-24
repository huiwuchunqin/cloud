package com.baizhitong.resource.dao.report.sqlServer;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.PlatformResCoverageKpDao;
import com.baizhitong.resource.model.report.PlatformResCoverageKp;

@Service
public class PlatformResCoverageKpDaoImpl extends BaseSqlServerDao<PlatformResCoverageKp>
                implements PlatformResCoverageKpDao {
    /**
     * 查询知识点资源覆盖率 ()<br>
     * 
     * @param tbCode
     * @return
     */
    public PlatformResCoverageKp getKpCoverage(String kpCode, Integer baseDate) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("kpCode", kpCode);
        if (baseDate != null) {
            qr.andEqual("baseDate", baseDate);
        }
        return super.findUnique(qr);
    }
}
