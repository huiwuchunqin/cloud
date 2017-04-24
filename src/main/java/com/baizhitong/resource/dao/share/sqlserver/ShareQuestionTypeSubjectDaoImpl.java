package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareQuestionTypeSubjectDao;
import com.baizhitong.resource.model.share.ShareQuestionTypeSubject;

/**
 * 学科题型dao实现类 ShareQuestionTypeSubjectDaoImpl
 * 
 * @author creator BZT 2016年5月12日 下午2:32:55
 * @author updater
 *
 * @version 1.0.0
 */
@Repository
public class ShareQuestionTypeSubjectDaoImpl extends BaseSqlServerDao<ShareQuestionTypeSubject>
                implements ShareQuestionTypeSubjectDao {

    /**
     * 查询所有的学科题型 ()<br>
     * 
     * @return
     */
    public List<ShareQuestionTypeSubject> getAll(String subjectCode) { 
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("dispOrder");
        // TODO Auto-generated method stub
        return super.find(qr);
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public int delete(String code) {
        String sql = "update share_question_type_subject set flagDelete=:flagDelete where code=:code";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_YES);
        sqlParam.put("code", code);
        return super.update(sql, sqlParam);
    }

    /**
     * 查询学科题型列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String,Object>> getPageList(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        String subjectCode = MapUtils.getString(param, "subjectCode");
        String sectionCode = MapUtils.getString(param, "sectionCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("flagDelete", CoreConstants.FLAG_DELETE_NO);
        sql.append("SELECT");
        sql.append("    a .*");
        sql.append("    ,b.name AS subjectName ");
        sql.append("    ,b.description ");
        sql.append("    FROM");
        sql.append("        share_question_type_subject a ");
        sql.append("    INNER JOIN");
        sql.append("        share_code_subject b ");
        sql.append("            ON a.subjectCode = b.code");
        sql.append(" where a.flagDelete=:flagDelete ");
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and a.subjectCode=:subjectCode");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and a.subjectCode like :sectionCode ");
            sqlParam.put("sectionCode", sectionCode + "%");
        }
        sql.append(" order by a.subjectCode,a.dispOrder,b.name ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 保存学科题型 ()<br>
     * 
     * @param shareQuestionSubject
     * @return
     */
    public boolean save(ShareQuestionTypeSubject shareQuestionSubject) {
        // TODO Auto-generated method stub
        try {
            return super.saveOne(shareQuestionSubject);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询机构没有选的学科题型 ()<br>
     * 
     * @param orgCode
     * @param subejctCode
     * @return
     */
    public List<Map<String, Object>> getNotSelected(String orgCode, String subjectCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        a.* ");
        sql.append("    FROM");
        sql.append("        share_question_type_subject a  ");
        sql.append("    WHERE");
        sql.append("        a.code NOT ");
        sql.append("        ");
        sql.append("    IN (");
        sql.append("        SELECT");
        sql.append("            b.code FROM");
        sql.append("                share_question_type_subject_org b   ");
        sql.append("            WHERE");
        sql.append("                b.orgCode =:orgCode   ");
        sql.append("                AND b.subjectCode =:subjectCode");
        sql.append("      )   ");
        sql.append(" and a.subjectCode=:subjectCode and  a.flagDelete=0  order by a.dispOrder asc ");
        param.put("orgCode", orgCode);
        param.put("subjectCode", subjectCode);
        return super.findBySql(sql.toString(), param);
    }

    /**
     * 查询最大排序 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public Integer getNextOrder(String subjectCode) {
        String sql = "select (Max(dispOrder)+1) as nextOrder from share_question_type_subject where subjectCode=:subjectCode";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("subjectCode", subjectCode);
        Map<String, Object> map = super.findUniqueBySql(sql, param);
        if (map != null) {
            Integer order = MapUtils.getInteger(map, "nextOrder");
            order = (order != null) ? order : 1;
            return order;
        } else {
            return 1;
        }
    }

}
