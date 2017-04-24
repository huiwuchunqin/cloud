package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.PlatformLessonCoverageChapterDao;
import com.baizhitong.resource.model.report.PlatformLessonCoverageChapter;
import com.baizhitong.utils.StringUtils;

/**
 * 课程章节覆盖率 PlatformLessonCoverageChapterDaoImpl TODO
 * 
 * @author creator gaow 2017年1月5日 上午11:26:24
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PlatformLessonCoverageChapterDaoImpl extends BaseSqlServerDao<PlatformLessonCoverageChapter>
                implements PlatformLessonCoverageChapterDao {
    /**
     * 查询课程教材章节覆盖率 ()<br>
     * 
     * @param chapterPCode 章节父编码
     * @param chapterCode 章节编码
     * @param tbCode 教材编码
     * @return list
     */
    public List<PlatformLessonCoverageChapter> getList(String chapterPCode, String chapterCode, String tbCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(chapterCode)) {
            qr.andLike("chapterCode", chapterCode + "%");
        }
        if (StringUtils.isNotEmpty(chapterPCode)) {
            qr.andEqual("chapterPCode", chapterPCode);
        }
        qr.andEqual("tbCode", tbCode);
        qr.addAscOrder("chapterPCode");
        qr.addAscOrder("chapterCode");
        return super.find(qr);
    }

    @Override
    public Map<String, Object> getReportInfo(String code) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        sum(lessonNum) AS lessonNum");
        sql.append("        ,sum(flipLessonNum) AS flipLessonNum");
        sql.append("        ,sum(autonomousLessonNum) AS autonomousLessonNum");
        sql.append("        FROM platform_lesson_coverage_chapter WHERE chapterCode = :chapterCode");
        sqlParam.put("chapterCode", code);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }
}
