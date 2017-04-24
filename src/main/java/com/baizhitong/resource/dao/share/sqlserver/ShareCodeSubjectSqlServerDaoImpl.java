package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareCodeSubjectDao;
import com.baizhitong.resource.model.share.ShareCodeSubject;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 学科SqlServer数据接口实现
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
@Service("ShareCodeSubjectDao")
public class ShareCodeSubjectSqlServerDaoImpl extends BaseSqlServerDao<ShareCodeSubject>
                implements ShareCodeSubjectDao {

    /**
     * 获取学科集合
     * 
     * @param sectionCode 学段编码
     * @return 集合
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectSubjectList(String sectionCode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT scs.code, scs.name, scs.description, scs.modifyTime, scs.modifyIP ");
        sql.append(" FROM share_rel_section_subject srss ");
        sql.append(" INNER JOIN share_code_subject scs ON scs.code = srss.subjectCode  where 1=1  and scs.flagDelete=0 ");
        // 判断学段编码是否为空
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and  srss.sectionCode = :sectionCode ");
        }
        sql.append(" ORDER BY dispOrder asc ");

        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("sectionCode", sectionCode);

        // SQL执行
        return super.findBySql(sql.toString(), sqlParam);
    }

    /************************************************** |以上已确认| **************************************************/
    /**
     * 获取学科集合
     * 
     * @return 集合
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectSubjectWithPrefix(String prefix) throws Exception {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>();
        sql.append("select code,name,description,modifyTime,modifyIP,dispOrder from share_code_subject where 1=1 and flagDelete=0 ");
        if (StringUtils.isNotEmpty(prefix)) {
            sql.append(" and  code like :code");
            map.put("code", prefix + "%");
        }
        sql.append(" ORDER BY dispOrder asc ");
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 分页查询学科信息
     * 
     * @param param 查询条件
     * @param pageSize 每页记录数
     * @param pageNo 页码
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月14日 下午4:55:00
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> selectSubjectPage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception {
        StringBuffer sqlbf = new StringBuffer();
        sqlbf.append(" SELECT ");
        sqlbf.append(" 	code, ");
        sqlbf.append(" 	name, ");
        sqlbf.append(" 	dispOrder, ");
        sqlbf.append(" 	description, ");
        sqlbf.append(" 	sysVer ");
        sqlbf.append(" FROM ");
        sqlbf.append(" 	share_code_subject ");
        sqlbf.append(" WHERE ");
        sqlbf.append(" 	1 = 1 and flagDelete=0 ");
        /*
         * if(!StringUtils.isEmpty(subjectName)){ sqlbf.append(" and name like '%"
         * +subjectName+"%'"); }
         */
        sqlbf.append("order by dispOrder asc");
        return super.queryDistinctPage(sqlbf.toString(), new RowMapperUtils(), null, pageNo, pageSize);
    }

    /**
     * 获取资源相关学科
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateSubject(Integer resId, Integer resTypeL1) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" select scs.name,scs.code from  ");
        buffer.append("	share_code_subject	 scs ");
        buffer.append("	inner join rel_res_subject rrs 	");
        buffer.append("	on rrs.subjectCode=scs.code  and rrs.resTypeL1=:resTypeL1");
        buffer.append(" where rrs.resId=:resId  and scs.flagDelete=0 ");
        buffer.append(" ORDER BY scs.dispOrder asc ");
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        sqlParamMap.put("resId", resId);
        sqlParamMap.put("resTypeL1", resTypeL1);
        return super.findBySql(buffer.toString(), sqlParamMap);
    }

    /**
     * 查询学科 ()<br>
     * 
     * @param subjectCode 学科编码
     * @return
     */
    public ShareCodeSubject getSubject(String subjectCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isEmpty(subjectCode))
            return null;
        qr.andEqual("code", subjectCode);
        return super.findUnique(qr);
    }

    /**
     * 查询学段最大编码值 ()<br>
     * 
     * @param sectionCode
     * @return
     */
    public String getMaxCode(String sectionCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        MAX (code) AS maxCode ");
        sql.append("    FROM");
        sql.append("        share_code_subject  ");
        /*
         * sql.append("        "); sql.append("    INNER JOIN"); sql.append(
         * "        share_rel_section_subject srss "); sql.append(
         * "            ON scs.code = srss.subjectCode ");
         */
        sql.append("    WHERE 1=1 ");
        sql.append("    and  code like :code ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("code", sectionCode + "%");
        Map<String, Object> map = super.findUniqueBySql(sql.toString(), sqlParam);
        if (map == null)
            return "";
        return MapUtils.getString(map, "maxCode");

    }

    /**
     * 查询下一个排序序号 ()<br>
     * 
     * @return
     */
    public Integer getNextDispOrder() {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        MAX (dispOrder)+1 as nextOrder ");
        sql.append("    FROM");
        sql.append("        share_code_subject ");
        Map<String, Object> map = super.findUniqueBySql(sql.toString());
        if (map == null)
            return 1;
        return MapUtils.getInteger(map, "nextOrder");
    }

    /**
     * 保存学科 ()<br>
     * 
     * @param subject
     */
    public void saveSubject(ShareCodeSubject subject) {
        try {
            super.saveOne(subject);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 查询学段下同名的学科 ()<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectName 学科编码
     */
    public List<Map<String, Object>> getSameNameSubject(String sectionCode, String subjectName) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        scs.* ");
        sql.append("    FROM");
        sql.append("        share_code_subject scs ");
        sql.append("        ");
        sql.append("    LEFT JOIN");
        sql.append("        share_rel_section_subject srss ");
        sql.append("            ON scs.code = srss.subjectCode ");
        sql.append("    WHERE");
        sql.append("        scs.flagDelete = 0 ");
        sql.append("        AND scs.name =:name ");
        sql.append("        AND srss.sectionCode =:sectionCode");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("name", subjectName);
        sqlParam.put("sectionCode", sectionCode);
        return super.findBySql(sql.toString(), sqlParam);

    }

    /**
     * 查询所有学科 ()<br>
     * 
     * @return
     */
    public List<ShareCodeSubject> getSubjectList() {
        return super.getAll();
    }

}