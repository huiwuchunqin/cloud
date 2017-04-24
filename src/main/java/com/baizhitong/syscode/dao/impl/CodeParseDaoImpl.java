package com.baizhitong.syscode.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.common.vo.ListVo;
import com.baizhitong.syscode.dao.ICodeParseDao;
import com.baizhitong.syscode.entity.EmptyEntity;
import com.baizhitong.utils.StringUtils;

@Component
public class CodeParseDaoImpl extends BaseSqlServerDao<EmptyEntity> implements ICodeParseDao {
    /**
     * 
     * (获取知识点编码)<br>
     * 
     * @param sectionCode
     * @param pcode
     */
    public ListVo<Map<String, Object>> getKpCode(String subjectCode, String pcode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" MAX (code) AS code "); // 最大的code
        sql.append(" FROM share_knowledge_point ");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND subjectCode = :subjectCode");// 学科code

        Map<String, String> sqlParam = new HashMap<String, String>();
        sqlParam.put("subjectCode", subjectCode);
        if (StringUtils.isNotEmpty(pcode)) {// 父code
            sql.append(" AND pcode = :pcode ");
            sqlParam.put("pcode", pcode);
        } else {
            sql.append(" AND (pcode is null OR pcode ='') ");
        }
        sql.append(" ORDER BY code ");
        return ListVo.asListVo(this.findBySql(sql.toString(), sqlParam));
    }

    /**
     * 
     * (获取教材编码)<br>
     * 
     * @param textbookVerCode
     * @param pcode
     */
    public ListVo<Map<String, Object>> getTextbookCode(String textbookVerCode, String gradeCode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" MAX (tbCode) AS tbCode "); // 最大的code
        sql.append(" FROM share_textbook ");
        sql.append(" WHERE 1=1 ");
        Map<String, String> sqlParam = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(gradeCode)) {// 父code
            gradeCode = gradeCode.substring(1, 3);
            sql.append(" AND tbCode like '" + textbookVerCode + gradeCode + "%'");// 学科code
        } else {
            sql.append(" AND (gradeCode is null OR gradeCode ='') ");
            sqlParam.put("tbCode", textbookVerCode);
        }
        sql.append(" ORDER BY tbCode ");
        return ListVo.asListVo(this.findBySql(sql.toString(), sqlParam));

    }

    /**
     * 
     * (获取教材章节编码)<br>
     * 
     * @param sectionCode
     * @param pcode
     */
    public ListVo<Map<String, Object>> getChapterCode(String tbCode, String pcode) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" MAX (code) AS code "); // 最大的code
        sql.append(" FROM share_textbook_chapter ");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND textbookCode = :tbCode");// 教材编码

        Map<String, String> sqlParam = new HashMap<String, String>();
        sqlParam.put("tbCode", tbCode);
        if (StringUtils.isNotEmpty(pcode)) {// 父code
            sql.append(" AND pcode = :pcode ");
            sqlParam.put("pcode", pcode);
        } else {
            sql.append(" AND (pcode is null OR pcode ='') ");
        }
        sql.append(" ORDER BY code ");
        return ListVo.asListVo(this.findBySql(sql.toString(), sqlParam));
    }

}
