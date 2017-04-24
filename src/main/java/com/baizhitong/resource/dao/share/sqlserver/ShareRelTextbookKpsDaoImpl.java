package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareRelTextbookKpsDao;
import com.baizhitong.resource.model.share.ShareRelTextbookKps;
import com.baizhitong.resource.model.share.ShareTextbook;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 教材与知识点体系关联dao实现
 * 
 * @author creator BZT 2016年1月8日 上午10:59:35
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ShareRelTextbookKpsDaoImpl extends BaseSqlServerDao<ShareRelTextbookKps>
                implements ShareRelTextbookKpsDao {

    /**
     * 查询某学科当前知识点体系体系编码最大的数据
     * 
     * @param subjectCode 学科
     * @return 学科知识点体系数量
     */
    public ShareRelTextbookKps getSubjectKps(String subjectCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("subjectCode", subjectCode);
        qr.addDescOrder("kpSerialCode");
        List<ShareRelTextbookKps> relTextBookKpsList = super.find(qr);
        if (relTextBookKpsList != null && relTextBookKpsList.size() > 0) {
            return relTextBookKpsList.get(0);
        } else {
            return null;
        }

    }

    /**
     * 保存知识点教材关系信息
     * 
     * @param knowledge
     * 
     * @exception
     */
    public void saveRelTextBookKps(ShareRelTextbookKps relTextbookKps) throws Exception {
        super.saveOne(relTextbookKps);
    }

    /**
     *
     * 查询知识体系关系
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param textbookCode 教材编码
     * @param termCode 学期编码
     * @return
     * @throws Exception
     */
    public List<ShareRelTextbookKps> getRelTextBookKps(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" select * from share_rel_textbook_kps  ");
        sql.append(" where 1=1   ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and (gradeCode=:gradeCode or gradeCode is null or gradeCode='')");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and  subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(textbookVerCode)) {
            sql.append(" and (textbookVerCode=:textbookVerCode or textbookVerCode is null or  textbookVerCode='')");
            sqlParam.put("textbookVerCode", textbookVerCode);
        }
        if (StringUtils.isNotEmpty(textbookCode)) {
            sql.append(" and (textbookCode=:tbCode or textbookCode is null or textbookCode='') ");
            sqlParam.put("tbCode", textbookCode);
        }
        if (StringUtils.isNotEmpty(termCode)) {
            sql.append(" and (termCode=:termCode or termCode is null or termCode='') ");
            sqlParam.put("termCode", termCode);
        }
        // sql.append(" order by sectionCode asc ,gradeCode asc,subjectCode asc,textbookVerCode
        // asc");
        return super.namedJdbcTemplateReadOnly().query(sql.toString(), sqlParam, this.op.rowMapper);

        /*
         * QueryRule qr=QueryRule.getInstance(); if(StringUtils.isNotEmpty(sectionCode)){
         * qr.andEqual("sectionCode", sectionCode); } if(StringUtils.isNotEmpty(gradeCode)){
         * qr.andEqual("gradeCode", gradeCode); } if(StringUtils.isNotEmpty(subjectCode)){
         * qr.andEqual("subjectCode", subjectCode); } if(StringUtils.isNotEmpty(textbookVerCode)){
         * qr.andEqual("textbookVerCode", textbookVerCode); }
         * if(StringUtils.isNotEmpty(textbookCode)){ qr.andEqual("textbookCode", textbookCode); }
         * if(StringUtils.isNotEmpty(termCode)){ qr.andEqual("termCode", termCode); } return
         * super.find(qr);
         */
    }

    /**
     * 
     * 查询知识体系关系
     * 
     * @param kpSerialCode 体系编码
     * @return
     * @throws Exception
     */
    public List<ShareRelTextbookKps> getRelTextBookKps(String kpSerialCode) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("kpSerialCode", kpSerialCode);
        return super.find(qr);
    }

    /**
     * 删除关系 @param relList
     * 
     * @exception
     */
    public void delRelTextBookKps(List<ShareRelTextbookKps> relList) throws Exception {
        super.deleteAll(relList);
    }

}
