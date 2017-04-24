package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareTextbookChapterDao;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.resource.model.vo.share.ShareTextbookChapterVo;
import com.baizhitong.utils.StringUtils;

/**
 * 教材章节树数据接口实现
 * 
 * @author creator Cuidc 2015/12/10
 * @author updater
 */
@Service("ShareTextbookChapterDao")
public class ShareTextbookChapterSqlServerDaoImpl extends BaseSqlServerDao<ShareTextbookChapter>
                implements ShareTextbookChapterDao {

    /**
     * 获取教材章节树集合
     * 
     * @param subjectCode 学科编码
     * @param textbookCode 教材版本编码
     * @param pcode 父章节编号
     * @return 集合
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectTextbookChapterList(String subjectCode, String textbookCode, String pcode)
                    throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT stc.subjectCode, stc.textbookCode, stc.code, stc.name, stc.level, ");
        sql.append(" stc.pcode, stc.dispOrder, stc.description, stc.modifyTime, stc.modifyIP ");
        sql.append(" FROM share_textbook_chapter stc ");
        sql.append(" WHERE stc.flagDelete=0 ");
        // 判断学科编码是否为空
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" AND stc.subjectCode = :subjectCode ");
        }
        // 判断教材版本编码是否为空
        if (StringUtils.isNotEmpty(textbookCode)) {
            sql.append(" AND stc.textbookCode = :textbookCode ");
        }
        // 判断父章节编号是否为空
        if (StringUtils.isNotEmpty(pcode)) {
            sql.append(" AND stc.pcode = :pcode ");
        } else {
            sql.append(" AND (stc.pcode = '' OR stc.pcode IS NULL) ");
        }
        // 排序
        sql.append(" ORDER BY stc.dispOrder ");

        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("textbookCode", textbookCode);
        sqlParam.put("pcode", pcode);

        // SQL执行
        return super.findBySql(sql.toString(), sqlParam);
    }
    /************************************************** |以上已确认| **************************************************/

    /**
     * 获取教材章节树集合
     */
    public List<Map<String, Object>> selectTextbookChapterList(String subjectCode, String textbookCode,
                    String sectionCode, String gradeCode) throws Exception {
        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        // 拼接SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT stc.subjectCode, stc.textbookCode, stc.code, stc.name, stc.level, ");
        sql.append(" stc.pcode, stc.dispOrder, stc.description, stc.modifyTime, stc.modifyIP ");
        sql.append(" FROM share_textbook_chapter stc ");
        sql.append(" WHERE stc.flagDelete=0 ");
        // 判断学科编码是否为空
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" AND stc.subjectCode = :subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        // 判断教材版本编码是否为空
        if (StringUtils.isNotEmpty(textbookCode)) {
            sql.append(" AND stc.textbookCode = :textbookCode ");
            sqlParam.put("textbookCode", textbookCode);
        }
        // 判断学段编码是否为空
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" AND stc.sectionCode = :sectionCode ");
            sqlParam.put("sectionCode", sectionCode);
        }
        // 判断年级编码是否为空
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" AND stc.gradeCode = :gradeCode ");
            sqlParam.put("gradeCode", gradeCode);
        }
        // 排序
        sql.append(" ORDER BY stc.dispOrder asc ");

        // SQL执行
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 获取根教材章节
     * 
     * @param subjectCode 学科编码 111,222,333,444
     * @param versionCode 版本编码 111,222,33,44
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月17日 下午8:13:05
     */
    public List<Map<String, Object>> getVersionRootInSubjectAndVersion(String subjectCodes, String versionCodes)
                    throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" select code,pcode,name,subjectCode,textBookCode from share_textbook_chapter where (pcode is null or pcode='') and flagDelete=0  ");
        if (!StringUtils.isEmpty(versionCodes)) {
            sql.append(" and textBookCode in (" + versionCodes + ") ");
        }
        if (!StringUtils.isEmpty(subjectCodes)) {
            sql.append(" and subjectCode in(" + subjectCodes + ")");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        sql.append("order by dispOrder ");
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 获取教材章节内容节点
     * 
     * @param pcode 父节点
     * @param textbookVerCode 教材版本
     * @param subjectCode 学科
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 上午10:01:37
     */
    public List<Map<String, Object>> getContentVersion(String pcode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" select code,pcode,name,textbookCode from share_textbook_chapter where pcode=:pcode and flagDelete=0 ");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("pcode", pcode);
        sql.append("order by dispOrder ");
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 获取资源相关章节 ()<br>
     * 
     * @param resId 资源id
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param textbookVerCode 教材版本编码
     * @param textbookCode 教材编码
     * @return
     */
    public List<Map<String, Object>> getResRelateChapter(Integer resId, String sectionCode, String subjectCode,
                    String gradeCode, String textbookVerCode, String textbookCode, Integer resTypeL1) {
        StringBuffer buffer = new StringBuffer();
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        buffer.append(" select stc.name,stc.code,stc.description,stc.pcode,stc.textbookCode from  ");
        buffer.append("	share_textbook_chapter	 stc ");
        buffer.append("	inner join rel_res_tbc rrt	");
        buffer.append("	on rrt.chapterCode=stc.code   and rrt.resTypeL1=:resTypeL1");
        buffer.append(" inner join share_textbook st  on st.tbCode=stc.textbookCode");
        buffer.append(" where rrt.resId=:resId  ");
        if (StringUtils.isNotEmpty(sectionCode)) {
            buffer.append(" and st.sectionCode=:sectionCode ");
            sqlParamMap.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            buffer.append(" and st.subjectCode=:subjectCode ");
            sqlParamMap.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            buffer.append(" and st.gradeCode=:gradeCode ");
            sqlParamMap.put("gradeCode", gradeCode);
        }
        if (StringUtils.isNotEmpty(textbookVerCode)) {
            buffer.append(" and st.textbookVerCode=:textbookVerCode ");
            sqlParamMap.put("textbookVerCode", textbookVerCode);
        }
        if (StringUtils.isNotEmpty(textbookCode)) {
            buffer.append(" and st.textbookCode=:textbookCode");
            sqlParamMap.put("textbookCode", textbookCode.trim());
        }
        sqlParamMap.put("resId", resId);
        sqlParamMap.put("resTypeL1", resTypeL1);
        buffer.append(" order by rrt.id  ");
        return super.findBySql(buffer.toString(), sqlParamMap);

    }

    /**
     * 查询子节点数
     * 
     * @param code 父节点code
     * @return
     * @author gaow
     * @date:2015年12月24日 下午3:36:33
     */
    public Map<String, Object> getChildCount(String code) {
        if (code == null)
            return null;
        String sqlString = "select count(*) as count from share_textbook_chapter where pcode=:pcode and flagDelete=0 ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pcode", code);
        return super.findUniqueBySql(sqlString, paramMap);
    }

    /**
     * 通过code查询章节
     * 
     * @param code
     * @return
     * @author gaow
     * @date:2015年12月24日 下午4:40:03
     */

    public ShareTextbookChapter getByCode(String code) {
        QueryRule qr = QueryRule.getInstance();
        if (!StringUtils.isEmpty(code)) {
            qr.andEqual("code", code);
            return super.findUnique(qr);
        }
        return null;
    }

    /**
     * 
     * 根据父章节编号获取教材章节列表信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param pcode 父章节编号 @return 教材章节列表信息 @throws Exception @exception
     */
    @Override
    public List<ShareTextbookChapter> getListInfoByPcode(String pcode, String chapterName) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(pcode)) {
            qr.andEqual("pcode", pcode);
        }
        if (StringUtils.isNotEmpty(chapterName)) {
            qr.andLike("name", "%" + chapterName + "%");
        }
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    /**
     * 
     * 根据相关查询条件获取教材章节列表信息<br>
     * 按显示顺序降序排序
     * 
     * @param pcode 父章节编码 @return 教材章节列表信息 @throws Exception @exception
     */
    @Override
    public List<ShareTextbookChapter> getListInfo(String pcode) {
        QueryRule qr = QueryRule.getInstance();
        // 判断父节点编码是否为空
        if (StringUtils.isNotEmpty(pcode)) {
            qr.andEqual("pcode", pcode);
        } else {
            qr.andIsNull("pcode");
        }
        // 未删除
        qr.andEqual("flagDelete", 0);
        // 按显示顺序降序排列
        qr.addDescOrder("dispOrder");
        return super.find(qr);
    }

    /**
     * 
     * 添加教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param chapter 教材章节信息 @return @throws Exception @exception
     */
    @Override
    public boolean addChapterInfo(ShareTextbookChapter chapter) throws Exception {
        return super.saveOne(chapter);
    }

    /**
     * 
     * 根据相关条件查询所有父章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param subjectCode 学科编码 @param textbookCode 教材版本编码 @return 教材章节列表信息 @throws
     *        Exception @exception
     */
    @Override
    public List<Map<String, Object>> getListInfo(String subjectCode, String textbookCode) throws Exception {
        StringBuilder sqlStr = new StringBuilder();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlStr.append(" select code,name,pcode,dispOrder from share_textbook_chapter where flagDelete=0 ");
        if (StringUtils.isNotEmpty(subjectCode)) {
            sqlStr.append(" and subjectCode=:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(textbookCode)) {
            sqlStr.append(" and textbookCode=:textbookCode ");
            sqlParam.put("textbookCode", textbookCode);
        }
        sqlStr.append(" and (pcode='' or pcode is null) ");
        sqlStr.append(" order by dispOrder desc ");
        return super.findBySql(sqlStr.toString(), sqlParam);
    }

    /**
     * 
     * 根据相关查询条件获取教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param subjectCode 学科编码 @param textbookCode 教材版本编码 @param code 章节编码 @return 教材章节信息 @throws
     *        Exception @exception
     */
    @Override
    public ShareTextbookChapter getChapter(String subjectCode, String textbookCode, String code) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(subjectCode)) {
            qr.andEqual("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(textbookCode)) {
            qr.andEqual("textbookCode", textbookCode);
        }
        if (StringUtils.isNotEmpty(code)) {
            qr.andEqual("code", code);
        }
        qr.andEqual("flagDelete", 0);
        return super.findUnique(qr);
    }

    /**
     * 
     * 根据系统ID获取教材章节信息<br>
     * (这里描述这个方法适用条件 – 可选)
     * 
     * @param gid 系统ID @return 教材章节信息 @throws Exception @exception
     */
    @Override
    public ShareTextbookChapter getChapterByGid(String gid) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        // 系统ID
        qr.andEqual("gid", gid);
        qr.andEqual("flagDelete", 0);
        return super.findUnique(qr);
    }

    /**
     * 
     * 根据教材查询教材章节
     * 
     * @param textbookCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getChapterList(String textbookCode) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(textbookCode)) {
            qr.andEqual("textbookCode", textbookCode);
        }
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("pcode");
        qr.addAscOrder("dispOrder");

        qr.addAscOrder("code");
        return super.find(qr);
    }

    /**
     * 
     * 根据教材查询教材章节
     * 
     * @param textbookCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getNotTopChapterList(String textbookCode) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("textbookCode", textbookCode);
        qr.andEqual("flagDelete", 0);
        qr.andIsNotNull("pcode");
        qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    /**
     * 
     * 根据顶级教材查询教材章节
     * 
     * @param textbookCode 教材编码
     * @return
     * @throws Exception
     */
    public List<ShareTextbookChapter> getTopChapterList(String textbookCode, String chapterName) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("textbookCode", textbookCode.trim());
        qr.andEqual("flagDelete", 0);
        qr.andIsNull("pcode");
        if (StringUtils.isNotEmpty(chapterName)) {
            qr.andLike("name", "%" + chapterName + "%");
        }
        qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    @Override
    public int deleteTextbookChapter(String code, String subjectCode, String textbookCode) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<ShareTextbookChapter> getSameNameChapterList(String name, String textbookCode, String pcode, String gid)
                    throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("textbookCode", textbookCode);
        qr.andEqual("flagDelete", 0);
        qr.andEqual("name", name);
        if (StringUtils.isEmpty(pcode)) {
            qr.andIsNull("pcode");
        } else {
            qr.andEqual("pcode", pcode);
        }
        if (StringUtils.isNotEmpty(gid)) {
            qr.andNotEqual("gid", gid);
        }
        return super.find(qr);
    }

    /**
     * 查询兄弟章节 ()<br>
     * 
     * @param gid 主键
     * @param textbookCode 教材
     * @return
     */
    public List<Map<String, Object>> getTextBookSiblingList(String textbookCode, String gid) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        c.* ");
        sql.append("    FROM");
        sql.append("        (   (    SELECT");
        sql.append("            b.*    ");
        sql.append("        FROM");
        sql.append("            share_textbook_chapter b    ");
        sql.append("        WHERE");
        sql.append("            b.pcode = (");
        sql.append("                SELECT");
        sql.append("                    a.pcode      ");
        sql.append("                FROM");
        sql.append("                    share_textbook_chapter a      ");
        sql.append("                WHERE");
        sql.append("                    gid =:gid  and textbookCode=:textbookCode    ");
        sql.append("            ) and b.flagDelete=0   ");
        sql.append("        )   ");
        sql.append("    UNION");
        sql.append("    (");
        sql.append("        SELECT");
        sql.append("            b.*     ");
        sql.append("        FROM");
        sql.append("            share_textbook_chapter b     ");
        sql.append("        WHERE");
        sql.append("            b.pcode IS NULL   and b.flagDelete=0  ");
        sql.append("            AND textbookCode=(");
        sql.append("                SELECT");
        sql.append("                    textbookCode     ");
        sql.append("                FROM");
        sql.append("                    share_textbook_chapter      ");
        sql.append("                WHERE");
        sql.append("                    gid =:gid  and pcode is null     ");
        sql.append("            )    ");
        sql.append("        )  ");
        sql.append(") c ");
        sql.append(" ORDER BY ");
        sql.append(" c.dispOrder");

        param.put("gid", gid);
        param.put("textbookCode", textbookCode);
        return super.findBySql(sql.toString(), param);
    }

    /**
     * 更新排序 ()<br>
     * 
     * @param gid 主键
     * @param newOrder 新的顺序
     */
    public void updateDispOrder(String gid, Integer newOrder) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        share_textbook_chapter  ");
        sql.append("    SET");
        sql.append("        dispOrder =:dispOrder  ");
        sql.append("    WHERE");
        sql.append("        gid =:gid");
        sqlParam.put("gid", gid);
        sqlParam.put("dispOrder", newOrder);
        super.update(sql.toString(), sqlParam);
    }

    @Override
    public List<ShareTextbookChapter> getAllChildNode(String code) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(code)) {
            qr.andLike("code", code + "%");
        }
        qr.andEqual("flagDelete", 0);
        return super.find(qr);
    }

}
