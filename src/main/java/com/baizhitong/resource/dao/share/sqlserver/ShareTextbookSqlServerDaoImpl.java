package com.baizhitong.resource.dao.share.sqlserver;

import java.nio.channels.SelectableChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.CompositeEntityOperation;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareTextbookDao;
import com.baizhitong.resource.model.share.ShareTextbook;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 教材dao接口实现类
 * 
 * @author creator gaowei 2016年1月12日 上午9:59:49
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class ShareTextbookSqlServerDaoImpl extends BaseSqlServerDao<ShareTextbook> implements ShareTextbookDao {
    /**
     * 
     * 保存教材
     * 
     * @param textbook
     */
    public boolean saveShareTextbook(ShareTextbook textbook) throws Exception {
        return super.saveOne(textbook);
    }

    /**
     * 查询教材
     * 
     * @param gid 教材id
     * @return
     * @throws Exception
     */
    public ShareTextbook getShareTextbook(String gid) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gid", gid);
        return super.get(param);
    }

    /**
     * 查询教材
     * 
     * @param tbCode 教材编码
     * @return
     * @throws Exception
     */
    public ShareTextbook getShareTextbookByTbCode(String tbCode) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("tbCode", tbCode);
        qr.andEqual("flagDelete", 0);
        return super.findUnique(qr);
    }

    /**
     * 查询教材信息
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbook> getTextbookList(String sectionCode, String gradeCode, String subjectCode,
                    String textbookVerCode, String textbookCode, String termCode) throws Exception {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" select * from share_textbook ");
        sql.append(" where 1=1  and flagDelete=0 ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and sectionCode=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(gradeCode) && !"-1".equals(gradeCode) && !gradeCode.contains("00")) {
            sql.append(" and (gradeCode=:gradeCode or gradeCode is null or gradeCode='')");
            sqlParam.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" and  subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(textbookVerCode)) {
            sql.append(" and textbookVerCode=:textbookVerCode ");
            sqlParam.put("textbookVerCode", textbookVerCode);
        }
        if (StringUtils.isNotEmpty(textbookCode)) {
            sql.append(" and tbCode=:tbCode ");
            sqlParam.put("tbCode", textbookCode);
        }
        if (StringUtils.isNotEmpty(termCode)) {
            sql.append(" and termCode=:termCode ");
            sqlParam.put("termCode", termCode);
        }
        sql.append(" order by sectionCode asc ,gradeCode asc,subjectCode asc,textbookVerCode asc");
        return super.namedJdbcTemplateReadOnly().query(sql.toString(), sqlParam, this.op.rowMapper);

    }

    @Override
    public List<ShareTextbook> getSameName(String name, String subjectCode, String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("subjectCode", subjectCode);
        qr.andEqual("tbName", name);
        qr.andEqual("flagDelete", 0);
        if (StringUtils.isNotEmpty(gid)) {
            qr.andNotEqual("gid", gid);
        }
        return super.find(qr);
    }
}
