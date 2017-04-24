package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareCodeGradeDao;
import com.baizhitong.resource.model.share.ShareCodeGrade;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 年级SqlServer数据接口实现
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
@Service("ShareCodeGradeDao")
public class ShareCodeGradeSqlServerDaoImpl extends BaseSqlServerDao<ShareCodeGrade> implements ShareCodeGradeDao {
    /**
     * 获取年级集合
     * 
     * @param sectionCode 学段编码
     * @return 集合
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectGradeList(String sectionCode) throws Exception {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT scg.code, scg.name, scg.modifyTime, scg.modifyIP ");
        sql.append(" FROM share_rel_section_grade srsg ");
        sql.append(" INNER JOIN share_code_grade scg ON scg.code = srsg.gradeCode  where 1=1 and scg.flagDelete=0 ");
        // 判断学段编码是否为空
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql.append(" and  srsg.sectionCode = :sectionCode ");
        }
        sql.append(" ORDER BY scg.dispOrder asc ");
        // SQL参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("sectionCode", sectionCode);

        // SQL执行
        return super.findBySql(sql.toString(), sqlParam);
    }

    /************************************************** |以上已确认| **************************************************/
    /**
     * 获取年级集合
     * 
     * @return
     * @throws Exception
     * @author gaow
     * @date:2015年12月15日 下午5:01:58
     */
    public List<Map<String, Object>> selectGradeWithPrefix(String prefix) throws Exception {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> map = new HashMap<String, Object>();
        sql.append("select code,name,modifyTime,modifyIP,description,dispOrder from share_code_grade where 1=1 and flagDelete=0 ");
        if (StringUtils.isNotEmpty(prefix)) {
            sql.append(" and  code like :code");
            map.put("code", prefix + "%");
        }
        sql.append(" order by dispOrder asc ");
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 分页查询年级信息
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
    public Page<Map<String, Object>> selectGradePage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception {
        StringBuffer sqlbf = new StringBuffer();
        sqlbf.append(" SELECT ");
        sqlbf.append(" 	code, ");
        sqlbf.append(" 	name, ");
        sqlbf.append(" 	dispOrder, ");
        sqlbf.append(" 	description, ");
        sqlbf.append(" 	sysVer ");
        sqlbf.append(" FROM ");
        sqlbf.append(" 	share_code_grade ");
        sqlbf.append(" WHERE ");
        sqlbf.append(" 	1 = 1 and flagDelete=0 ");
        /*
         * if(!StringUtils.isEmpty(gradeName)){ sqlbf.append(" and name like '%"+gradeName+"%'"); }
         */
        sqlbf.append(" 	order by dispOrder asc ");
        return super.queryDistinctPage(sqlbf.toString(), new RowMapperUtils(), null, pageNo, pageSize);
    }

    /**
     * 获取资源相关年级
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateGrade(Integer resId, Integer resTypeL1) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" select scg.name,scg.code,rrg.termCode from  ");
        buffer.append("	share_code_grade	 scg ");
        buffer.append("	inner join rel_res_grade rrg	");
        buffer.append("	on rrg.gradeCode=scg.code and resTypeL1=:resTypeL1");
        buffer.append(" where rrg.resId=:resId  and scg.flagDelete=0 ");
        buffer.append(" order by scg.dispOrder asc ");
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        sqlParamMap.put("resId", resId);
        sqlParamMap.put("resTypeL1", resTypeL1);
        return super.findBySql(buffer.toString(), sqlParamMap);

    }

    /**
     * 
     * 查询年级学科
     * 
     * @param gradeCode 年级
     * @return
     */
    public List<Map<String, Object>> getGradeSubject(String gradeCode) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer buffer = new StringBuffer();
        buffer.append(" select");
        buffer.append(" scs.* ");
        buffer.append(" , scg.name AS gradeName ");
        buffer.append(" FROM");
        buffer.append(" share_code_subject scs ");
        buffer.append(" INNER JOIN share_rel_grade_subject srgs ON scs.code = srgs.subjectCode ");
        buffer.append(" LEFT JOIN share_code_grade scg ON scg.code = srgs.gradeCode ");
        buffer.append(" WHERE 1=1 and scs.flagDelete=0  ");
        if (StringUtils.isNotEmpty(gradeCode)) {
            buffer.append(" AND srgs.gradeCode =:gradeCode");
            sqlParam.put("gradeCode", gradeCode);
        }
        buffer.append(" order by scs.dispOrder ");
        return super.findBySql(buffer.toString(), sqlParam);
    }

    /**
     * 查询年级学科分页信息
     * 
     * @param gradeCode
     * @param pageNo
     * @param pageSize
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getGradeSubjectPage(String gradeCode, Integer pageNo, Integer pageSize) {
        // sql参数
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer buffer = new StringBuffer();
        buffer.append(" select");
        buffer.append(" scs.* ");
        buffer.append(" , scg.name AS gradeName ");
        buffer.append(" FROM");
        buffer.append(" share_code_subject scs ");
        buffer.append(" INNER JOIN share_rel_grade_subject srgs ON scs.code = srgs.subjectCode ");
        buffer.append(" LEFT JOIN share_code_grade scg ON scg.code = srgs.gradeCode ");
        buffer.append(" WHERE 1=1 and scs.flagDelete=0  ");
        if (StringUtils.isNotEmpty(gradeCode)) {
            buffer.append(" AND srgs.gradeCode =:gradeCode");
            sqlParam.put("gradeCode", gradeCode);
        }
        buffer.append(" order by scs.dispOrder ");
        return super.queryDistinctPage(buffer.toString(), sqlParam, pageNo, pageSize);
    }

    /**
     * 查询年级 ()<br>
     * 
     * @param gradeCode
     * @return
     */
    public ShareCodeGrade getGrade(String gradeCode) {
        QueryRule qr = QueryRule.getInstance();
        if (StringUtils.isEmpty(gradeCode))
            return null;
        qr.andEqual("code", gradeCode);
        return super.findUnique(qr);
    }

    /**
     * 查询学科所对应的年级 ()<br>
     * 
     * @param subjectCode
     * @return
     */
    public List<Map<String, Object>> getSubjectGrade(String subjectCode) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer buffer = new StringBuffer();
        buffer.append("SELECT");
        buffer.append("        scg.* ");
        buffer.append("    FROM");
        buffer.append("        share_rel_grade_subject srgs ");
        buffer.append("        ");
        buffer.append("    INNER JOIN");
        buffer.append("        share_code_grade scg ");
        buffer.append("            ON srgs.gradeCode = scg.code ");
        buffer.append("            AND scg.flagDelete = 0 ");
        buffer.append("    WHERE");
        buffer.append("        srgs.subjectCode=:subjectCode");
        sqlParam.put("subjectCode", subjectCode);
        return super.findBySql(buffer.toString(), sqlParam);

    }

    /**
     * 查询所有年级 ()<br>
     * 
     * @return
     */
    public List<ShareCodeGrade> getAllGradeList() {
        return super.getAll();
    }
}