package com.baizhitong.resource.dao.lessonreport.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.lessonreport.PlatformLessonCoverageTbDao;
import com.baizhitong.resource.model.report.PlatformLessonCoverageTb;
import com.baizhitong.utils.StringUtils;

@Service
public class PlatformLessonCoverageTbDaoImpl extends BaseSqlServerDao<PlatformLessonCoverageTb>
                implements PlatformLessonCoverageTbDao {

    /**
     * 查询教材覆盖率 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String tbvCode = MapUtils.getString(param, "tbvCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("tbvCode", tbvCode);
        sql.append("SELECT");
        sql.append("        scs.name AS sectionName");
        sql.append("        ,scsu.name AS subjectName");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,p.sectionCode");
        sql.append("        ,p.gradeCode");
        sql.append("        ,p.subjectCode");
        sql.append("        ,p.tbvCode");
        sql.append("        ,p.tbvName");
        sql.append("        ,p.tbCode");
        sql.append("        ,p.tbName");
        sql.append("        ,p.lessonNum");
        sql.append("        ,p.lessonCoverNum");
        sql.append("        ,p.lessonCoverage");
        sql.append("        ,p.chapterNum");
        sql.append("        ,p.updateType ");
        sql.append("    FROM");
        sql.append("        platform_lesson_coverage_tb AS p ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON scs.code = p.sectionCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scsu ");
        sql.append("            ON scsu.code = p.subjectCode ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON scg.code = p.gradeCode ");
        sql.append("    WHERE");
        sql.append("        1 = 1 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("        AND p.sectionCode = :sectionCode ");
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append("        AND p.subjectCode = :subjectCode ");
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append("        AND p.gradeCode = :gradeCode ");
        }
        if (StringUtils.isNotEmpty(tbvCode)) {
            sql.append("        AND p.tbvCode = :tbvCode ");
        }
        sql.append("        ");
        sql.append("    ORDER BY");
        sql.append("        scs.dispOrder");
        sql.append("        ,p.sectionCode");
        sql.append("        ,scg.dispOrder");
        sql.append("        ,p.gradeCode");
        sql.append("        ,scsu.dispOrder");
        sql.append("        ,p.subjectCode");

        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }
}
