package com.baizhitong.resource.dao.share.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareCodeTextbookVerDao;
import com.baizhitong.resource.model.share.ShareCodeTextbookVer;
import com.baizhitong.resource.model.vo.share.ShareCodeTextbookVerVo;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 教材版本数据接口实现
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
@Service("ShareCodeTextbookVerDao")
public class ShareCodeTextbookVerSqlServerDaoImpl extends BaseSqlServerDao<ShareCodeTextbookVer>
                implements ShareCodeTextbookVerDao {

    /**
     * 获取教材版本集合
     * 
     * @param subjectCode 学科编码
     * @return 集合
     * @throws Exception 异常
     */
    @Override
    public List<ShareCodeTextbookVer> selectTextbookVerList(String subjectCode) throws Exception {
        QueryRule queryRule = QueryRule.getInstance();
        if (!StringUtils.isEmpty(subjectCode)) {
            queryRule.andEqual("subjectCode", subjectCode);
        }
        queryRule.addAscOrder("dispOrder");
        return super.find(queryRule);
    }

    /************************************************** |以上已确认| **************************************************/
    /**
     * 查询某些学科的教材版本
     * 
     * @param subjectCodes
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月17日 下午6:47:16
     */
    public List<ShareCodeTextbookVer> selectTextbookInSubjects(List<String> subjectCodes) throws Exception {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andIn("subjectCode", subjectCodes);
        queryRule.addAscOrder("dispOrder");
        return super.find(queryRule);
    }

    /**
     * 获取教材版本分页信息
     */
    @Override
    public Page<ShareCodeTextbookVerVo> queryTextbookVerPageInfo(String sectionCode, String subjectCode, String name,
                    Integer pageSize, Integer pageNo) throws Exception {
        StringBuilder sqlStr = new StringBuilder();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlStr.append(" select sctv.description, sctv.subjectCode,scs.name as subjectName,scsec.name as sectionName,sctv.code,sctv.name,scs.modifyTime ");
        sqlStr.append(" from share_code_textbook_ver sctv INNER JOIN share_code_subject scs ");
        sqlStr.append(" on sctv.subjectCode=scs.code ");
        sqlStr.append(" INNER JOIN share_code_section scsec ");
        sqlStr.append(" on scsec.code=SUBSTRING(scs.code,1,1) ");
        sqlStr.append(" where 1=1 ");
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sqlStr.append(" and sctv.subjectCode=:subjectcode ");
            sqlParam.put("subjectcode", subjectCode.trim());
        }
        if (!StringUtils.isEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sqlStr.append(" and scsec.code=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode.trim());
        }
        if (!StringUtils.isEmpty(name)) {
            sqlStr.append(" and sctv.name like '%" + name.trim() + "%'");
            sqlParam.put("name", name.trim());
        }
        sqlStr.append(" order by sctv.dispOrder asc ");
        return super.queryDistinctPage(sqlStr.toString(), new RowMapperUtils(), sqlParam, pageNo, pageSize);
    }

    /**
     * 获取资源相关教材版本
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateVersion(Integer resId) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" select sct.name,sct.code,sct.description from  ");
        buffer.append("	share_code_textbook_ver	 sct ");
        buffer.append("	inner join rel_res_tbv rst	");
        buffer.append("	on rst.tbvCode=sct.code ");
        buffer.append(" where rst.resId=:resId ");
        Map sqlParamMap = new HashMap();
        sqlParamMap.put("resId", resId);
        return super.findBySql(buffer.toString(), sqlParamMap);

    }

    /**
     * 查询教材版本 ()<br>
     * 
     * @param textbookVerCode
     * @return
     */
    public ShareCodeTextbookVer getTextbookVer(String textbookVerCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isEmpty(textbookVerCode))
            return null;
        qr.andEqual("code", textbookVerCode);
        return super.findUnique(qr);
    }

    /**
     * 查询最大编码 ()<br>
     * 
     * @return
     */
    public String getMaxCode() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("      top 1  b.ncode ");
        sql.append("    FROM");
        sql.append("        (   SELECT");
        sql.append("            SUBSTRING (CODE");
        sql.append("            ,4");
        sql.append("            ,7) AS ncode   ");
        sql.append("        FROM");
        sql.append("            share_code_textbook_ver  ) b ");
        sql.append("    WHERE");
        sql.append("        b.ncode LIKE '%[A-Z]%' ");
        sql.append("        ");
        sql.append("    ORDER BY");
        sql.append("        b.ncode DESC");
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> map = super.findUniqueBySql(sql.toString(), param);
        String code = MapUtils.getString(map, "ncode");
        return code;
    }

    /**
     * 保存教材版本 ()<br>
     * 
     * @param list
     */
    public void saveTextbookVer(List<ShareCodeTextbookVer> list) {
        try {
            super.saveAll(list);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 保存教材版本 ()<br>
     * 
     * @param textbookVer
     */
    public void save(ShareCodeTextbookVer textbookVer) {
        try {
            super.saveOne(textbookVer);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 查询教材版本 ()<br>
     * 
     * @param name 版本名称
     * @return
     */
    public List<Map<String, Object>> getTextbookVerByName(String name) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        a.*");
        sql.append("        ,(   SELECT");
        sql.append("            COUNT (*)   ");
        sql.append("        FROM");
        sql.append("            share_textbook   ");
        sql.append("        WHERE");
        sql.append("            textbookVerCode = a.code  and flagDelete=0) AS hasTextbook ");
        sql.append("        ,(   SELECT");
        sql.append("            COUNT (*)   ");
        sql.append("        FROM");
        sql.append("            share_rel_textbook_kps  b inner join  share_knowledge_point_serial c on b.kpSerialCode=c.code  and c.flagDelete=0  ");
        sql.append("        WHERE");
        sql.append("            textbookVerCode = a.code ) AS hasKps ");
        sql.append("    FROM");
        sql.append("        share_code_textbook_ver a  where 1=1 ");
        if (StringUtils.isNotEmpty(name)) {
            sql.append(" and a.name=:name");
            sqlParam.put("name", name);
        }
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询教材版本 ()<br>
     * 
     * @param name
     * @param sectionCode
     * @param subjectCode
     * @return
     */
    public List<Map<String, Object>> getTextbookVerName(String name, String sectionCode, String subjectCode) {
        StringBuilder sqlStr = new StringBuilder();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlStr.append(" select sctv.gid,sctv.description, sctv.subjectCode,scs.name as subjectName,scsec.name as sectionName,sctv.code,sctv.name,sctv.modifyTime ,sctv.code,  ");
        sqlStr.append(" (select count(*) from share_textbook where textbookVerCode=sctv.code ) as textbookCount ");
        sqlStr.append(" ,(select count(*) from share_rel_textbook_kps where textbookVerCode=sctv.code ) as kpsCount ");
        sqlStr.append(" from share_code_textbook_ver sctv INNER JOIN share_code_subject scs ");
        sqlStr.append(" on sctv.subjectCode=scs.code ");
        sqlStr.append(" INNER JOIN share_code_section scsec ");
        sqlStr.append(" on scsec.code=SUBSTRING(scs.code,1,1) ");
        sqlStr.append(" where 1=1 ");
        if (!StringUtils.isEmpty(subjectCode) && !"-1".equals(subjectCode)) {
            sqlStr.append(" and sctv.subjectCode=:subjectcode ");
            sqlParam.put("subjectcode", subjectCode.trim());
        }
        if (!StringUtils.isEmpty(sectionCode) && !"-1".equals(sectionCode)) {
            sqlStr.append(" and scsec.code=:sectionCode ");
            sqlParam.put("sectionCode", sectionCode.trim());
        }
        if (!StringUtils.isEmpty(name)) {
            sqlStr.append(" and sctv.name like :name ");
            sqlParam.put("name", "%" + name.trim() + "%");
        }
        sqlStr.append(" order by scsec.dispOrder  asc,scs.dispOrder asc,sctv.modifyTime  desc ");
        return super.findBySql(sqlStr.toString(), sqlParam);
    }

    /**
     * 更新版本名称 ()<br>
     * 
     * @param name 版本名称
     * @param gid id
     */
    public void update(String name, String gid) {
        String sql = "update share_code_textbook_ver set name=:name where gid=:gid";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("gid", gid);
        sqlParam.put("name", name);
        super.update(sql, sqlParam);

    }

    /**
     * 删除版本 ()<br>
     * 
     * @param gid id
     */
    public int delete(String gid) {
        String sql = "delete from share_code_textbook_ver where gid =:gid ";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("gid", gid);
        return super.update(sql, sqlParam);

    }

    /**
     * 删除 ()<br>
     * 
     * @param name
     * @return
     */
    public int deletebyName(String name) {
        String sql = "delete from share_code_textbook_ver where name =:name ";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("name", name);
        return super.update(sql, sqlParam);
    }

    /**
     * 查询某学科教材版本 ()<br>
     * 
     * @param subjectCode
     * @param name
     * @return
     */
    public Map<String, Object> selectSubjectVersion(String subjectCode, String name) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        b.name as subejctName ");
        sql.append("    FROM");
        sql.append("        share_code_textbook_ver a ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_code_subject b ");
        sql.append("            ON a.code = b.subjectCode ");
        sql.append("    WHERE");
        sql.append("        a.name =:name ");
        sql.append("        AND a.subjectCode = :subjectCode");
        sqlParam.put("name", name);
        sqlParam.put("subjectCode", subjectCode);
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

    /**
     * 查询 ()<br>
     * 
     * @param gid
     * @return
     */
    public ShareCodeTextbookVer get(String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("gid", gid);
        return super.findUnique(qr);
    }
}