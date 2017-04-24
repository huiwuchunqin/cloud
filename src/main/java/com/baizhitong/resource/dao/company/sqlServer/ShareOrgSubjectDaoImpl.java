package com.baizhitong.resource.dao.company.sqlServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.company.ShareOrgSubjectDao;
import com.baizhitong.resource.model.company.ShareOrgSubject;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 机构学科业务接口实现
 * 
 * @author creator BZT 2016年1月21日 下午2:19:56
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ShareOrgSubjectDaoImpl extends BaseSqlServerDao<ShareOrgSubject> implements ShareOrgSubjectDao {

    /**
     * 保存机构年级学科关系 ()<br>
     * 
     * @param orgSubject
     * @throws Exception
     */
    public void saveOrgSubjects(List<ShareOrgSubject> orgSubject) throws Exception {
        super.saveAll(orgSubject);

    }

    /**
     * 删除机构年级学科关系 ()<br>
     * 
     * @param orgCode
     * @param subjectCode
     * @throws Exception
     */
    public void delOrgSubjects(String orgCode, String subjectCode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("delete ");
        sql.append("    FROM");
        sql.append("        share_org_subject ");
        sql.append("    WHERE");
        sql.append("        orgCode=:orgCode");
        sql.append("        AND subjectCode=:subjectCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("subjectCode", subjectCode);
        super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * 查询机构学科
     * 
     * @param orgCode 机构编码
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     */
    public Page getOrgSubject(String orgCode, String sectionCode, Integer pageSize, Integer pageNo) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        scs.name");
        sql.append("        ,scs.code");
        sql.append("        ,sos.modifyTime");
        sql.append("        ,scs.dispOrder ");
        sql.append("        ,scs.description ");
        sql.append("    FROM");
        sql.append("        share_org_subject sos ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            ON sos.subjectCode = scs.code ");
        /*
         * sql.append("    INNER JOIN"); sql.append("    share_rel_section_subject srss ");
         * sql.append("        ON srss.subjectCode = sos.subjectCode "); sql.append(
         * "        AND srss.sectionCode =:sectionCode ");
         */

        sql.append("    WHERE");
        sql.append("        scs.flagDelete = 0 ");
        sql.append("       and sos.orgCode =:orgCode");
        sql.append("       order by scs.dispOrder asc");
        // 查询参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("sectionCode", sectionCode);
        return super.queryDistinctPage(sql.toString(), new RowMapperUtils(), sqlParam, pageNo, pageSize);

    }

    /**
     * 查询机构学科列表 ()<br>
     * 
     * @param orgCode 机构编码
     * @return
     * @throws Exception
     */
    public List<Map<String, Object>> getOrgSubjectList(String orgCode, String sectionCode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        scs.name");
        sql.append("        ,scs.description ");
        sql.append("        ,scs.code ");
        sql.append("        ,sos.modifyTime");
        sql.append("        ,scs.dispOrder ");
        sql.append("    FROM");
        sql.append("        share_org_subject sos ");
        sql.append("        ");
        sql.append("    INNER JOIN");
        sql.append("        share_code_subject scs ");
        sql.append("            ON sos.subjectCode = scs.code ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append("    INNER JOIN ");
            sql.append("    share_rel_section_subject srss ");
            sql.append("        ON srss.subjectCode = sos.subjectCode   ");
            sql.append("    AND srss.sectionCode =:sectionCode ");
        }
        sql.append("    WHERE");
        sql.append("        scs.flagDelete = 0 ");
        sql.append("       and sos.orgCode =:orgCode");
        sql.append("       order by scs.dispOrder ");
        // 查询参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("sectionCode", sectionCode);
        return super.findBySql(sql.toString(), sqlParam);
    }
}
