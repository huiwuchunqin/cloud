package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareKnowledgePointDao;
import com.baizhitong.resource.model.share.ShareKnowledgePoint;
import com.baizhitong.utils.StringUtils;

/**
 * 教材知识点数据接口实现
 * 
 * @author creator Cuidc 2015/12/10
 * @author updater
 */
@Service("ShareKnowledgePointTextbookDao")
public class ShareKnowledgePointSqlServerDaoImpl extends BaseSqlServerDao<ShareKnowledgePoint>
                implements ShareKnowledgePointDao {
    /**
     * 获取教材知识点集合
     * 
     * @param subjectCode 学科编码
     * @param textbookVerCode 教材版本编码
     * @param bizTypeNo 教材内分类
     * @param pcode 知识点父节点编码
     * @return 集合
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectKnowledgePointTextbookList(String subjectCode, String textbookVerCode,
                    Integer bizTypeNo, String pcode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT skpt.subjectCode, skpt.textbookVerCode, skpt.bizTypeNo, ");
        sql.append(" skpt.level, skpt.code, skpt.pcode, skpt.name, skpt.description, ");
        sql.append(" skpt.dispOrder, skpt.modifyTime, skpt.modifyIP ");
        sql.append(" FROM share_knowledge_point skpt ");
        sql.append(" WHERE flagDelete=0 ");
        // 判断学科编码是否为空
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" AND skpt.subjectCode = :subjectCode ");
        }
        // 判断教材版本编码是否为空
        if (StringUtils.isNotEmpty(textbookVerCode)) {
            sql.append(" AND skpt.textbookVerCode = :textbookVerCode ");
        }
        // 判断教材内分类是否为空
        if (bizTypeNo != null) {
            sql.append(" AND skpt.bizTypeNo = :bizTypeNo ");
        }
        // 判断知识点父节点编码是否为空
        if (StringUtils.isNotEmpty(pcode)) {
            sql.append(" AND skpt.pcode = :pcode ");
        } else {
            sql.append(" AND (skpt.pcode = '' OR skpt.pcode IS NULL) ");
        }
        // 排序
        sql.append(" ORDER BY skpt.bizTypeNo, skpt.dispOrder ");

        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("textbookVerCode", textbookVerCode);
        sqlParam.put("bizTypeNo", bizTypeNo);
        sqlParam.put("pcode", pcode);

        // SQL执行
        return super.findBySql(sql.toString(), sqlParam);
    }

    /************************************************** |以上已确认| **************************************************/

    /**
     * 获取教材知识点集合
     */
    @Override
    public List<Map<String, Object>> selectKnowledgePointTextbookList(String gradeCode, String subjectCode,
                    String textbookVerCode) throws Exception {
        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append("   skpt.* ");
        sql.append(" FROM ");
        sql.append("    share_knowledge_point skpt ");
        sql.append(" LEFT JOIN share_rel_textbook_kps srtk ON skpt.kpSerialCode = srtk.kpSerialCode ");
        sql.append(" WHERE ");
        sql.append("    flagDelete=0  ");
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" AND srtk.gradeCode =:gradeCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            sql.append(" AND srtk.subjectCode =:subjectCode ");
            sqlParam.put("subjectCode", subjectCode);
        }
        if (StringUtils.isNotEmpty(textbookVerCode)) {
            sql.append(" AND srtk.textbookVerCode =:textbookVerCode ");
            sqlParam.put("textbookVerCode", textbookVerCode);
        }
        // 排序
        sql.append(" ORDER BY skpt.dispOrder ");
        // SQL执行
        return super.findBySql(sql.toString(), sqlParam);
    }

    /**
     * 获取根知识点
     * 
     * @param subjectCode 学科编码 111,222,333,444
     * @param versionCode 版本编码 111,222,33,44
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月17日 下午8:13:05
     */
    public List<Map<String, Object>> getKnowledgeRootInSubjectAndVersion(String subjectCodes, String versionCodes)
                    throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" select code,pcode,name,subjectCode,textbookVerCode from share_knowledge_point where (pcode is null or pcode='') and flagDelete=0  ");
        if (!StringUtils.isEmpty(versionCodes)) {
            sql.append(" and textbookVerCode in (" + versionCodes + ") ");
        }
        if (!StringUtils.isEmpty(subjectCodes)) {
            sql.append(" and subjectCode in(" + subjectCodes + ")");
        }
        sql.append(" order by dispOrder ");
        Map map = new HashMap();
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 获取知识点内容节点
     * 
     * @param pcode 父节点
     * @param textbookVerCode 教材版本
     * @param subjectCode 学科
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月18日 上午10:01:37
     */
    public List<ShareKnowledgePoint> getContentKnowLedge(String pcode) throws Exception {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("pcode", pcode);
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    /**
     * 获取资源相关知识点
     * 
     * @param resId
     * @param sectionCode
     * @param subjectCode
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateKnowLedge(Integer resId, String sectionCode, String subjectCode,
                    Integer resTypeL1) {
        StringBuffer buffer = new StringBuffer();
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        buffer.append(" select skpt.name,skpt.code,skpt.description,skpt.pcode,skpt.kpSerialCode from  ");
        buffer.append("	share_knowledge_point skpt ");
        buffer.append("	inner join rel_res_kp rrk 	");
        buffer.append("	on rrk.kpCode=skpt.code  and resTypeL1=:resTypeL1");
        buffer.append(" where rrk.resId=:resId  ");
        sqlParamMap.put("resTypeL1", resTypeL1);
        if (StringUtils.isNotEmpty(sectionCode)) {
            buffer.append(" and skpt.sectionCode=:sectionCode ");
            sqlParamMap.put("sectionCode", sectionCode);
        }
        if (StringUtils.isNotEmpty(subjectCode)) {
            buffer.append(" and skpt.subjectCode=:subjectCode ");
            sqlParamMap.put("subjectCode", subjectCode);
        }
        buffer.append(" order by rrk.id ");
        sqlParamMap.put("resId", resId);
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
        String sqlString = "select count(*) as count  from share_knowledge_point where pcode=:pcode and flagDelete=0 ";
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pcode", code);
        return super.findUniqueBySql(sqlString, paramMap);
    }

    /**
     * 
     * @param code
     * @return
     * @author gaow
     * @date:2015年12月24日 下午4:35:03
     */
    public ShareKnowledgePoint getByCode(String code) {
        QueryRule qr = QueryRule.getInstance();
        if (!StringUtils.isEmpty(code)) {
            qr.andEqual("code", code);
            return super.findUnique(qr);
        }
        return null;
    }

    /**
     * 保存知识点信息
     * 
     * @param knowledge
     * 
     * @exception
     */
    public void saveShareKnowledgePoint(ShareKnowledgePoint knowledge) throws Exception {
        super.saveOne(knowledge);
    }

    /**
     * 删除知识点
     * 
     * @param gid 知识点id
     * @throws Exception
     * 
     */
    public void delKnowLedge(String gid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gid", gid);
        super.deleteByPK(map);

    }

    /**
     * 查询知识点
     * 
     * @param gid 知识点id
     * @return
     */
    public ShareKnowledgePoint getByGid(String gid) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("gid", gid);
        return super.get(map);
    }

    /**
     * 查询code最大的同级节点 @param code @return
     * 
     * @exception
     */
    public ShareKnowledgePoint getMaxCodeShareKnowLedgePoint(String code) {
        QueryRule qr = QueryRule.getInstance();
        if ("null".equals(code) || StringUtils.isEmpty(code)) {
            qr.andIsNull("pcode");
        } else {
            qr.andEqual("pcode", code);
        }
        qr.addDescOrder("code");
        qr.andEqual("flagDelete", 0);
        List<ShareKnowledgePoint> knowLedgeList = super.find(qr);
        if (knowLedgeList == null || knowLedgeList.size() <= 0) {
            return null;
        } else {
            return knowLedgeList.get(0);
        }
    }

    @Override
    public ShareKnowledgePoint getMaxCodeShareKnowLedgePointTextBook(String code) {
        QueryRule qr = QueryRule.getInstance();
        if ("null".equals(code) || StringUtils.isEmpty(code)) {
            qr.andIsNull("pcode");
        } else {
            qr.andEqual("pcode", code);
        }
        qr.addDescOrder("dispOrder");
        qr.andEqual("flagDelete", 0);
        List<ShareKnowledgePoint> knowLedgeList = super.find(qr);
        if (knowLedgeList == null || knowLedgeList.size() <= 0) {
            return null;
        } else {
            return knowLedgeList.get(0);
        }
    }

    /**
     * 查询知识点
     * 
     * @param kpSerialCode 知识点体系编码
     * @return
     */
    public List<ShareKnowledgePoint> getKnowledgeList(String kpSerialCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(kpSerialCode)) {
            qr.andEqual("kpSerialCode", kpSerialCode);
        }
        qr.andEqual("flagDelete", 0);
        // qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    /**
     * 
     * 查询知识点信息
     * 
     * @param kpSerialCode
     * @return
     */
    public List<ShareKnowledgePoint> getKnowledgeList(List<String> kpSerialCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andIn("kpSerialCode", kpSerialCode);
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    /**
     * 
     * 查询顶级知识点信息
     * 
     * @param kpSerialCode
     * @return
     */
    public List<ShareKnowledgePoint> getTopKnowledgeList(List<String> kpSerialCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andIn("kpSerialCode", kpSerialCode);
        qr.andEqual("flagDelete", 0);
        qr.andIsNull("pcode");
        qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    @Override
    public List<ShareKnowledgePoint> getNotTopKnowledgeList(List<String> kpSerialCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andIn("kpSerialCode", kpSerialCode);
        qr.andEqual("flagDelete", 0);
        qr.andIsNotNull("pcode");
        qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    /**
     * 查询知识点信息
     * 
     * @param pcode 父节点
     * @return
     */
    public List<ShareKnowledgePoint> getchildKnowledgeList(String pcode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isNotEmpty(pcode)) {
            qr.andEqual("pcode", pcode);
        } else {
            qr.andIsNull("pcode");
        }
        qr.andEqual("flagDelete", 0);
        qr.addAscOrder("dispOrder");
        qr.addAscOrder("code");
        return super.find(qr);
    }

    @Override
    public List<ShareKnowledgePoint> getSameNameKnowledgeList(String name, String kpSerialCode, String pcode,
                    String gid) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("name", name);
        qr.andEqual("kpSerialCode", kpSerialCode);
        qr.andEqual("flagDelete", 0);
        if (StringUtils.isNotEmpty(gid)) {
            qr.andNotEqual("gid", gid);
        }
        if (StringUtils.isEmpty(pcode)) {
            qr.andIsNull("pcode");
        } else {
            qr.andEqual("pcode", pcode);
        }
        return super.find(qr);
    }

    /**
     * 查询相邻的节点 ()<br>
     * 
     * @param gid 主键
     * @param kpSerialCode 体系编码
     * @return
     */
    public List<Map<String, Object>> getSiblingKnowledgeList(String kpSerialCode, String gid) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> param = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        c.* ");
        sql.append("    FROM");
        sql.append("        (   (    SELECT");
        sql.append("            b.*    ");
        sql.append("        FROM");
        sql.append("            share_knowledge_point b    ");
        sql.append("        WHERE");
        sql.append("            b.pcode = (");
        sql.append("                SELECT");
        sql.append("                    a.pcode      ");
        sql.append("                FROM");
        sql.append("                    share_knowledge_point a      ");
        sql.append("                WHERE");
        sql.append("                    gid =:gid     ");
        sql.append("            )  and  b.kpSerialCode=:kpSerialCode and b.flagDelete=0 ");
        sql.append("        )   ");
        sql.append("    UNION");
        sql.append("    (");
        sql.append("        SELECT");
        sql.append("            b.*     ");
        sql.append("        FROM");
        sql.append("            share_knowledge_point b     ");
        sql.append("        WHERE");
        sql.append("            b.pcode IS NULL  and b.flagDelete=0   ");
        sql.append("            AND kpSerialCode= (");
        sql.append("                SELECT");
        sql.append("                   kpSerialCode      ");
        sql.append("                FROM");
        sql.append("                    share_knowledge_point      ");
        sql.append("                WHERE");
        sql.append("                    gid =:gid  and pcode is null   ");
        sql.append("            )    ");
        sql.append("        )  ");
        sql.append(") c ");
        sql.append("");
        sql.append("  ORDER BY  ");
        sql.append("  c.dispOrder  ");
        param.put("gid", gid);
        param.put("kpSerialCode", kpSerialCode);
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
        sql.append("        share_knowledge_point  ");
        sql.append("    SET");
        sql.append("        dispOrder =:dispOrder  ");
        sql.append("    WHERE");
        sql.append("        gid =:gid");
        sqlParam.put("gid", gid);
        sqlParam.put("dispOrder", newOrder);
        super.update(sql.toString(), sqlParam);
    }

}
