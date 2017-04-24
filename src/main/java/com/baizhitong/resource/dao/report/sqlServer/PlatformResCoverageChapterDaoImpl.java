package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.PlatformResCoverageChapterDao;
import com.baizhitong.resource.model.report.PlatformResCoverageChapter;

@Service
public class PlatformResCoverageChapterDaoImpl extends BaseSqlServerDao<PlatformResCoverageChapter>
                implements PlatformResCoverageChapterDao {
    /**
     * 查询章节资源覆盖率 ()<br>
     * 
     * @param tbCode
     * @return
     */
    public PlatformResCoverageChapter getChapterCoverage(String chapterCode, Integer baseDate) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("chapterCode", chapterCode);
        if (baseDate != null) {
            qr.andEqual("baseDate", baseDate);
        }
        return super.findUnique(qr);
    }

    @Override
    public Map<String, Object> getReportInfo(String code, String baseDate) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        sum(resVideoNum) AS mediaCount");
        sql.append("        ,sum(resDocNum) AS docCount");
        sql.append("        ,sum(resQuestionNum) AS questionCount");
        sql.append("        ,sum(resTestNum) AS testCount");
        sql.append("        ,sum(resQuestionNum)+sum(resDocNum)+sum(resVideoNum)+sum(resTestNum) AS totalCount FROM platform_res_coverage_chapter WHERE chapterCode = :chapterCode AND baseDate = :baseDate ");
        sqlParam.put("chapterCode", code);
        sqlParam.put("baseDate", baseDate);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }
}
