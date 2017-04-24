package com.baizhitong.resource.dao.company.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.company.ShareOrgGradeSubjectDao;
import com.baizhitong.resource.model.company.ShareOrgGradeSubject;

/**
 * 
 * 机构年级学科业务接口实现
 * 
 * @author creator BZT 2016年1月21日 下午2:20:41
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ShareOrgGradeSubjectDaoImpl extends BaseSqlServerDao<ShareOrgGradeSubject>
                implements ShareOrgGradeSubjectDao {
    /**
     * 查询机构的学科年级 ()<br>
     * 
     * @param subjectCode 学科
     * @param orgCode 机构
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOrgSubjectGrade(String subjectCode, String orgCode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        scg.name");
        sql.append("        ,scg.code");
        sql.append("        ,scg.description ");
        sql.append("    FROM");
        sql.append("        share_org_grade_subject AS sogs ");
        sql.append("        ");
        sql.append("    INNER JOIN");
        sql.append("        share_code_grade AS scg ");
        sql.append("            ON sogs.gradeCode = scg.code ");
        sql.append("    INNER JOIN  ");
        sql.append("    share_rel_grade_subject srgs ");
        sql.append("        ON srgs.subjectCode = sogs.subjectCode ");
        sql.append("        AND srgs.gradeCode = sogs.gradeCode ");
        sql.append("    WHERE");
        sql.append("        sogs.orgCode=:orgCode  AND scg.flagDelete=0 ");
        sql.append("   AND ");
        sql.append("       sogs.subjectCode =:subjectCode ");
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("subjectCode", subjectCode);
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 保存年级学科 ()<br>
     * 
     * @param gradeSubjectList
     * @throws Exception
     */
    public void saveOrgSubjectGrade(List<ShareOrgGradeSubject> gradeSubjectList) throws Exception {
        super.saveAll(gradeSubjectList);

    }

    /**
     * 删除机构学科所对应的年级 ()<br>
     * 
     * @param orgCode 机构编码
     * @param subjectCode 学科编码
     * @throws Exception
     */
    public void deleteOrgSubjectGrade(String orgCode, String subjectCode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE ");
        sql.append("    FROM");
        sql.append("        share_org_grade_subject ");
        sql.append("    WHERE");
        sql.append("        subjectCode = :subjectCode ");
        sql.append("        AND orgCode = :orgCode");
        // 查询参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("orgCode", orgCode);
        super.update(sql.toString(), sqlParam);
    }

}
