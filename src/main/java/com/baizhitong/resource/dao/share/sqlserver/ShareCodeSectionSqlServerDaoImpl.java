package com.baizhitong.resource.dao.share.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareCodeSectionDao;
import com.baizhitong.resource.model.share.ShareCodeSection;
import com.baizhitong.utils.RowMapperUtils;

/**
 * 学段SqlServer数据接口实现
 * 
 * @author creator Cuidc 2015/12/03
 * @author updater
 */
@Service("ShareCodeSectionDao")
public class ShareCodeSectionSqlServerDaoImpl extends BaseSqlServerDao<ShareCodeSection>
                implements ShareCodeSectionDao {

    /**
     * 获取学段集合
     * 
     * @return 集合
     * @throws Exception 异常
     */
    @Override
    public List<ShareCodeSection> selectSectionList() throws Exception {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.addAscOrder("dispOrder");
        queryRule.andEqual("flagDelete", CoreConstants.FLAG_DELETE_NO);
        return super.find(queryRule);
    }

    /************************************************** |以上已确认| **************************************************/
    /**
     * 分页查询学段信息
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
    public Page<Map<String, Object>> selectSectionPage(Map<String, Object> param, Integer pageSize, Integer pageNo)
                    throws Exception {
        StringBuffer sqlbf = new StringBuffer();
        sqlbf.append(" SELECT ");
        sqlbf.append(" 	code, ");
        sqlbf.append(" 	name, ");
        sqlbf.append(" 	dispOrder, ");
        sqlbf.append(" 	description, ");
        sqlbf.append(" 	sysVer ");
        sqlbf.append(" FROM ");
        sqlbf.append(" 	share_code_section ");
        sqlbf.append(" WHERE ");
        sqlbf.append(" 	1 = 1  and flagDelete=0 ");
        /*
         * if(!StringUtils.isEmpty(sectionName)){ sqlbf.append(" and name like '%"
         * +sectionName+"%'"); }
         */
        sqlbf.append(" 	order by dispOrder asc ");
        return super.queryDistinctPage(sqlbf.toString(), new RowMapperUtils(), null, pageNo, pageSize);
    }

    /**
     * 查询学段学科
     * 
     * @param sectionCode
     * @return
     * @author gaow
     * @date:2015年12月15日 上午10:36:06
     */
    public List<Map<String, Object>> getSectionSubject(String sectionCode) {
        StringBuffer buf = new StringBuffer();
        buf.append(" SELECT ");
        buf.append(" 	scg.code, ");
        buf.append(" 	scg.name, ");
        buf.append(" 	scg.description, ");
        buf.append(" 	scg.dispOrder ");
        buf.append(" FROM ");
        buf.append(" 	share_code_subject scg ");
        buf.append(" INNER JOIN share_rel_section_subject srsg ON srsg.subjectCode = scg.code  where 1=1 and scg.flagDelete=0 ");
        if (!StringUtils.isEmpty(sectionCode)) {
            buf.append(" and  srsg.sectionCode=:sectionCode ");
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        buf.append(" order by scg.dispOrder asc ");
        return super.findBySql(buf.toString(), param);

    }

    /**
     * 查询学段年级
     * 
     * @param sectionCode
     * @return
     * @author gaow
     * @date:2015年12月15日 上午10:36:06
     */
    public List<Map<String, Object>> getSectionGrade(String sectionCode) {
        StringBuffer buf = new StringBuffer();
        buf.append(" SELECT ");
        buf.append(" 	scg.code, ");
        buf.append(" 	scg.name, ");
        buf.append(" 	scg.description, ");
        buf.append(" 	scg.dispOrder ");
        buf.append(" FROM ");
        buf.append(" 	share_code_grade scg ");
        buf.append(" INNER JOIN share_rel_section_grade srsg ON srsg.gradeCode = scg.code where 1=1 and scg.flagDelete=0  ");
        if (!StringUtils.isEmpty(sectionCode)) {
            buf.append(" and  srsg.sectionCode=:sectionCode ");
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        buf.append(" order by  scg.dispOrder asc ");
        return super.findBySql(buf.toString(), param);

    }

    /**
     * 查询学段学科
     * 
     * @param sectionCode 学段
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月15日 上午10:36:06
     */
    @SuppressWarnings("unchecked")
    public Page getSectionSubjectPage(String sectionCode, Integer pageNo, Integer pageSize) {
        StringBuffer buf = new StringBuffer();
        buf.append(" SELECT ");
        buf.append(" 	scg.code, ");
        buf.append(" 	scg.name, ");
        buf.append(" 	scg.description, ");
        buf.append(" 	scg.dispOrder, ");
        buf.append(" 	scs.name as sectionName ");
        buf.append(" FROM ");
        buf.append(" 	share_code_subject scg ");
        buf.append(" INNER JOIN share_rel_section_subject srsg ON srsg.subjectCode = scg.code ");
        buf.append(" LEFT JOIN  share_code_section scs ON scs.code = srsg.sectionCode where 1=1   and scg.flagDelete=0 ");
        if (!StringUtils.isEmpty(sectionCode)) {
            buf.append(" and  srsg.sectionCode=:sectionCode ");
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        buf.append(" order by  scg.dispOrder asc ");
        return super.queryDistinctPage(buf.toString(), param, pageNo, pageSize);

    }

    /**
     * 查询学段年级
     * 
     * @param sectionCode
     * @param pageNo 页码
     * @param pageSize 每页记录数
     * @return
     * @author gaow
     * @date:2015年12月15日 上午10:36:06
     */
    @SuppressWarnings("unchecked")
    public Page<Map<String, Object>> getSectionGradePage(String sectionCode, Integer pageSize, Integer pageNo) {
        StringBuffer buf = new StringBuffer();
        buf.append(" SELECT ");
        buf.append(" 	scg.code, ");
        buf.append(" 	scg.name, ");
        buf.append(" 	scg.description, ");
        buf.append(" 	scg.dispOrder, ");
        buf.append(" 	scs.name as sectionName ");
        buf.append(" FROM ");
        buf.append(" 	share_code_grade scg ");
        buf.append(" INNER JOIN share_rel_section_grade srsg ON srsg.gradeCode = scg.code ");
        buf.append(" LEFT JOIN  share_code_section scs ON scs.code = srsg.sectionCode where 1=1 and scg.flagDelete=0 ");
        if (!StringUtils.isEmpty(sectionCode)) {
            buf.append(" and  srsg.sectionCode=:sectionCode ");
        }
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        buf.append(" order by scg.dispOrder asc ");
        return super.queryDistinctPage(buf.toString(), param, pageNo, pageSize);

    }

    /**
     * 获取资源相关学段
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午3:00:04
     */
    public List<Map<String, Object>> getResRelateSection(Integer resId, Integer resTypeL1) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(" select scs.name,scs.code from  ");
        buffer.append("	share_code_section	 scs ");
        buffer.append("	inner join rel_res_section rrs	");
        buffer.append("	on rrs.sectionCode=scs.code and rrs.resTypeL1=:resTypeL1 ");
        buffer.append(" where rrs.resId=:resId  and scs.flagDelete=0 ");
        Map<String, Object> sqlParamMap = new HashMap<String, Object>();
        sqlParamMap.put("resId", resId);
        sqlParamMap.put("resTypeL1", resTypeL1);
        return super.findBySql(buffer.toString(), sqlParamMap);
    }

    /**
     * 查询学段 ()<br>
     * 
     * @param sectionCode 学段编码
     * @return
     */
    public ShareCodeSection getSection(String sectionCode) {
        if (StringUtils.isEmpty(sectionCode))
            return null;
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("code", sectionCode);
        return super.findUnique(qr);
    }
}