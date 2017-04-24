package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;
import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.PlatformResCoverageTbDao;
import com.baizhitong.resource.model.report.PlatformResCoverageTb;
import com.baizhitong.utils.StringUtils;

@Service
public class PlatformResCoverageTbDaoImpl extends BaseSqlServerDao<PlatformResCoverageTb>
                implements PlatformResCoverageTbDao {

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
        String verCode = MapUtils.getString(param, "verCode");
        String termCode = MapUtils.getString(param, "termCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        b.id");
        sql.append("        ,b.sectionCode");
        sql.append("        ,b.gradeCode");
        sql.append("        ,b.termCode");
        sql.append("        ,b.subjectCode");
        sql.append("        ,b.tbvCode");
        sql.append("        ,b.tbCode");
        sql.append("        ,b.tbName");
        sql.append("        ,b.tbvName");
        sql.append("        ,b.chapterNum");
        sql.append("        ,b.resVideoNum");
        sql.append("        ,b.resVideoCoverNum");
        sql.append("        ,b.resVideoCoverage");
        sql.append("        ,b.resDocNum");
        sql.append("        ,b.resDocCoverNum");
        sql.append("        ,b.resDocCoverage");
        sql.append("        ,b.resTestNum");
        sql.append("        ,b.resTestCoverNum");
        sql.append("        ,b.resTestCoverage");
        sql.append("        ,b.resQuestionNum");
        sql.append("        ,b.resQuestionCoverNum");
        sql.append("        ,b.resQuestionCoverage");
        sql.append("        ,b.resNum");
        sql.append("        ,b.resCoverNum");
        sql.append("        ,b.resCoverage");
        sql.append("        ,b.updateType");
        sql.append("        ,scs.name as sectionName");
        sql.append("        ,scg.name as gradeName");
        sql.append("        ,scsu.name as subjectName");
        sql.append("        ,sct.name as verName");
        sql.append("        ,b.baseDate ");
        sql.append("        ");
        sql.append("    FROM");
        sql.append("        platform_res_coverage_tb AS b  ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section AS scs ");
        sql.append("            ON scs.code= b.sectionCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade AS scg ");
        sql.append("            ON b.gradeCode = scg.code   ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject AS scsu ");
        sql.append("            ON b.subjectCode = scsu.code   ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_textbook_ver sct ");
        sql.append("            on sct.code=b.tbvCode");
        sql.append(" where 1=1 ");
        sql.append(" and b.baseDate=(select Max(baseDate) from  platform_res_coverage_tb)");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and b.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and b.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and b.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(verCode)) {
            sql.append(" and b.tbvCode=:tbvCode ");
            sqlParam.put("tbvCode", verCode);
        }
        if (StringUtils.isNotEmpty(termCode)) {
            sql.append(" and b.termCode=:termCode ");
            sqlParam.put("termCode", termCode);
        }
        sql.append(" order by scs.dispOrder,b.sectionCode,scg.dispOrder,b.gradeCode,scsu.dispOrder,b.subjectCode,b.baseDate");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 根据教材章节查询最新 ()<br>
     * 
     * @param tbCode
     * @return
     */
    public Map<String, Object> getByTbCode(String tbCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        b.id");
        sql.append("        ,b.sectionCode");
        sql.append("        ,b.gradeCode");
        sql.append("        ,b.termCode");
        sql.append("        ,b.subjectCode");
        sql.append("        ,b.tbvCode");
        sql.append("        ,b.tbCode");
        sql.append("        ,b.tbName");
        sql.append("        ,b.tbvName");
        sql.append("        ,b.chapterNum");
        sql.append("        ,b.resVideoNum");
        sql.append("        ,b.resVideoCoverNum");
        sql.append("        ,b.resVideoCoverage");
        sql.append("        ,b.resDocNum");
        sql.append("        ,b.resDocCoverNum");
        sql.append("        ,b.resDocCoverage");
        sql.append("        ,b.resTestNum");
        sql.append("        ,b.resTestCoverNum");
        sql.append("        ,b.resTestCoverage");
        sql.append("        ,b.resQuestionNum");
        sql.append("        ,b.resQuestionCoverNum");
        sql.append("        ,b.resQuestionCoverage");
        sql.append("        ,b.resNum");
        sql.append("        ,b.resCoverNum");
        sql.append("        ,b.resCoverage");
        sql.append("        ,b.updateType");
        sql.append("        ,b.baseDate ");
        sql.append("        ");
        sql.append("    FROM");
        sql.append("        platform_res_coverage_tb AS b  ");
        sql.append("        ");
        sql.append(" where b.tbCode=:tbCode ");
        sql.append(" and b.baseDate=(select Max(baseDate) from  platform_res_coverage_tb)");
        sqlParam.put("tbCode", tbCode);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询教材资源覆盖率列表
     */
    public List<Map<String, Object>> getList(Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String verCode = MapUtils.getString(param, "verCode");
        String termCode = MapUtils.getString(param, "termCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        b.id");
        sql.append("        ,b.sectionCode");
        sql.append("        ,b.gradeCode");
        sql.append("        ,b.termCode");
        sql.append("        ,b.subjectCode");
        sql.append("        ,b.tbvCode");
        sql.append("        ,b.tbCode");
        sql.append("        ,b.tbName");
        sql.append("        ,b.tbvName");
        sql.append("        ,b.chapterNum");
        sql.append("        ,b.resVideoNum");
        sql.append("        ,b.resVideoCoverNum");
        sql.append("        ,b.resVideoCoverage");
        sql.append("        ,b.resDocNum");
        sql.append("        ,b.resDocCoverNum");
        sql.append("        ,b.resDocCoverage");
        sql.append("        ,b.resTestNum");
        sql.append("        ,b.resTestCoverNum");
        sql.append("        ,b.resTestCoverage");
        sql.append("        ,b.resQuestionNum");
        sql.append("        ,b.resQuestionCoverNum");
        sql.append("        ,b.resQuestionCoverage");
        sql.append("        ,b.resNum");
        sql.append("        ,b.resCoverNum");
        sql.append("        ,b.resCoverage");
        sql.append("        ,b.updateType");
        sql.append("        ,scs.name as sectionName");
        sql.append("        ,scg.name as gradeName");
        sql.append("        ,scsu.name as subjectName");
        sql.append("        ,sct.name as verName");
        sql.append("        ,b.baseDate");
        sql.append("        ");
        sql.append("    FROM");
        sql.append("        platform_res_coverage_tb AS b  ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section AS scs ");
        sql.append("            ON scs.code= b.sectionCode  ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade AS scg ");
        sql.append("            ON b.gradeCode = scg.code   ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject AS scsu ");
        sql.append("            ON b.subjectCode = scsu.code   ");
        sql.append("            ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_textbook_ver sct ");
        sql.append("            on sct.code=b.tbvCode ");
        sql.append(" where 1=1 ");
        sql.append(" and b.baseDate=(select Max(baseDate) from  platform_res_coverage_tb)");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and b.sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and b.subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and b.gradeCode=:gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(verCode)) {
            sql.append(" and b.tbvCode=:tbvCode ");
            sqlParam.put("tbvCode", verCode);
        }
        if (StringUtils.isNotEmpty(termCode)) {
            sql.append(" and b.termCode=:termCode ");
            sqlParam.put("termCode", termCode);
        }
        sql.append(" order by b.sectionCode,b.gradeCode,b.subjectCode,b.baseDate");
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询章节覆盖情况 ()<br>
     * 
     * @param tbCode
     * @param baseDate
     * @return
     */
    public PlatformResCoverageTb getByTbCode(String tbCode, Integer baseDate) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("tbCode", tbCode);
        if (baseDate != null) {
            qr.andEqual("baseDate", baseDate);
        }
        return super.findUnique(qr);
    }

    /**
     * id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public Map<String, Object> getById(Integer id) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        prct.*");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,scg.name AS gradeName");
        sql.append("        ,scs2.name AS subjectName ");
        sql.append("    FROM");
        sql.append("        platform_res_coverage_tb prct ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON prct.sectionCode = scs.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_grade scg ");
        sql.append("            ON prct.gradeCode = scg.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs2 ");
        sql.append("            ON prct.subjectCode = scs2.code ");
        sql.append("    WHERE");
        sql.append("        prct.id = :id ");
        sqlParam.put("id", id);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

}
