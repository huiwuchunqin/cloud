package com.baizhitong.resource.dao.report.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.report.PlatformResCoverageKpsDao;
import com.baizhitong.resource.model.report.PlatformResCoverageKps;
import com.baizhitong.utils.StringUtils;

@Service
public class PlatformResCoverageKpsDaoImpl extends BaseSqlServerDao<PlatformResCoverageKps>
                implements PlatformResCoverageKpsDao {
    /**
     * 查询知识体系资源覆盖率 ()<br>
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
        String kpsCode = MapUtils.getString(param, "kpsCode");
        String termCode = MapUtils.getString(param, "termCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        b.id");
        sql.append("        ,b.kpsCode");
        sql.append("        ,b.kpsName");
        sql.append("        ,b.sectionCode");
        sql.append("        ,b.gradeCode");
        sql.append("        ,b.subjectCode");
        sql.append("        ,b.tbvCode");
        sql.append("        ,b.kpNum");
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
        sql.append("        ,b.modifier");
        sql.append("        ,b.modifyTime");
        sql.append("        ,b.modifierIP  ");
        sql.append("        ,scs.name as sectionName  ");
        sql.append("        ,scg.name as gradeName  ");
        sql.append("        ,scsu.name as subjectName  ");
        sql.append("        ,b.baseDate  ");
        sql.append("    FROM");
        sql.append("        platform_res_coverage_kps b");
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
        sql.append("    INNER JOIN");
        sql.append("        share_knowledge_point_serial skps ");
        sql.append("            on skps.code=b.kpsCode and skps.flagDelete=0 ");
        sql.append(" where 1=1 ");
        sql.append(" and b.baseDate=(select Max(baseDate) from  platform_res_coverage_kps)");
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
        if (StringUtils.isNotEmpty(kpsCode)) {
            sql.append(" and b.kpsCode=:kpsCode ");
            sqlParam.put("kpsCode", kpsCode);
        }
        if (StringUtils.isNotEmpty(termCode)) {
            sql.append(" and b.termCode=:termCode ");
            sqlParam.put("termCode", termCode);
        }
        sql.append(" order by scs.dispOrder,b.sectionCode,scg.dispOrder,b.gradeCode,scsu.dispOrder,b.subjectCode,b.baseDate ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 根据只是体系编码查询 ()<br>
     * 
     * @param kpsCode
     * @return
     */
    public Map<String, Object> getByKpCode(String kpsCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        b.id");
        sql.append("        ,b.kpsCode");
        sql.append("        ,b.kpsName");
        sql.append("        ,b.sectionCode");
        sql.append("        ,b.gradeCode");
        sql.append("        ,b.subjectCode");
        sql.append("        ,b.tbvCode");
        sql.append("        ,b.kpNum");
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
        sql.append("        ,b.modifier");
        sql.append("        ,b.modifyTime");
        sql.append("        ,b.modifierIP  ");
        sql.append("        ,b.baseDate  ");
        sql.append("    FROM");
        sql.append("        platform_res_coverage_kps b");
        sql.append(" where 1=1 ");
        sql.append(" and b.baseDate=(select Max(baseDate) from  platform_res_coverage_kps)");
        sql.append(" and b.kpsCode=:kpsCode");
        sqlParam.put("kpsCode", kpsCode);
        return super.findUniqueBySql(sql.toString(), sqlParam);
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
        sql.append("        prck.*");
        sql.append("        ,scs.name AS sectionName");
        sql.append("        ,scs2.name AS subjectName ");
        sql.append("    FROM");
        sql.append("        platform_res_coverage_kps prck ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_section scs ");
        sql.append("            ON prck.sectionCode = scs.code ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs2 ");
        sql.append("            ON prck.subjectCode = scs2.code ");
        sql.append("    WHERE");
        sql.append("        id = :id ");
        sqlParam.put("id", id);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询统计信息 ()<br>
     * 
     * @param kpCode 知识点编码
     * @return
     */
    public Map<String, Object> getReportInfo(String kpCode, String baseDate) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        sum(resVideoNum) AS mediaCount");
        sql.append("        ,sum(resDocNum) AS docCount");
        sql.append("        ,sum(resQuestionNum) AS questionCount");
        sql.append("        ,sum(resQuestionNum)+sum(resDocNum)+sum(resVideoNum) AS totalCount FROM platform_res_coverage_kp WHERE kpCode = :kpCode AND baseDate = :baseDate ");
        sqlParam.put("kpCode", kpCode);
        sqlParam.put("baseDate", baseDate);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询列表 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getList(Map<String, Object> param) {
        StringBuffer sql = new StringBuffer();
        String sectionCode = MapUtils.getString(param, "sectionCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String kpsCode = MapUtils.getString(param, "kpsCode");
        String termCode = MapUtils.getString(param, "termCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        b.id");
        sql.append("        ,b.kpsCode");
        sql.append("        ,b.kpsName");
        sql.append("        ,b.sectionCode");
        sql.append("        ,b.gradeCode");
        sql.append("        ,b.subjectCode");
        sql.append("        ,b.tbvCode");
        sql.append("        ,b.kpNum");
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
        sql.append("        ,b.modifier");
        sql.append("        ,b.modifyTime");
        sql.append("        ,b.modifierIP  ");
        sql.append("        ,scs.name as sectionName  ");
        sql.append("        ,scg.name as gradeName  ");
        sql.append("        ,scsu.name as subjectName  ");
        sql.append("        ,b.baseDate   ");
        sql.append("    FROM");
        sql.append("        platform_res_coverage_kps b");
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
        sql.append("    INNER JOIN");
        sql.append("        share_knowledge_point_serial skps ");
        sql.append("            on skps.code=b.kpsCode and skps.flagDelete=0 ");
        sql.append(" where 1=1 ");
        sql.append(" and b.baseDate=(select Max(baseDate) from  platform_res_coverage_kps)");
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
        if (StringUtils.isNotEmpty(kpsCode)) {
            sql.append(" and b.kpsCode=:kpsCode ");
            sqlParam.put("kpsCode", kpsCode);
        }
        if (StringUtils.isNotEmpty(termCode)) {
            sql.append(" and b.termCode=:termCode ");
            sqlParam.put("termCode", termCode);
        }
        sql.append(" order by b.sectionCode,b.gradeCode,b.subjectCode,b.baseDate ");
        return super.findBySql(sql.toString(), sqlParam);
    }
}
