package com.baizhitong.resource.dao.share.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareOrgYearTermDao;
import com.baizhitong.resource.model.share.ShareOrgYearTerm;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 机构学年学期接口实现 ShareOrgYearTermDaoImpl
 * 
 * @author creator BZT 2016年4月28日 下午5:24:20
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class ShareOrgYearTermDaoImpl extends BaseSqlServerDao<ShareOrgYearTerm> implements ShareOrgYearTermDao {
    /**
     * 查询机构学年学期列表 ()<br>
     * 
     * @param map
     * @return
     */
    public Page getOrgYearTermList(Map<String, Object> map) {
        Integer pageSize = MapUtils.getInteger(map, "rows");
        Integer pageNumber = MapUtils.getInteger(map, "page");
        String orgCode = MapUtils.getString(map, "orgCode");

        Map<String, Object> param = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("select soyt.*,scs.name as sectionName,scyt.yearTermName,so.orgName  from share_org_year_term soyt ");
        sql.append(" inner join share_org so on so.orgCode=soyt.orgCode ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append("  and so.orgCode=:orgCode ");
            param.put("orgCode", orgCode);
        }
        sql.append(" left join share_code_section scs on scs.code=soyt.sectionCode ");
        sql.append(" left join share_code_year_term scyt on soyt.yearTermCode=scyt.yearTermCode  order by soyt.startDate  desc ");
        return super.queryDistinctPage(sql.toString(), param, pageNumber, pageSize);
    }

    /**
     * 删除机构学期 ()<br>
     * 
     * @param yearTermCode
     * @return
     */
    public int deleteOrgYearTerm(String yearTermCode, String orgCode, String sectionCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        String sql = "delete from share_org_year_term  ";
        param.put("yearTermCode", yearTermCode);
        sql = sql + " where yearTermCode=:yearTermCode ";
        if (StringUtils.isNotEmpty(orgCode)) {
            sql = sql + " and orgCode = :orgCode ";
            param.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql = sql + " and sectionCode = :sectionCode ";
            param.put("sectionCode", sectionCode);
        }
        sql = sql + " and flagUpdByOrg = :flagUpdByOrg ";
        param.put("flagUpdByOrg", CoreConstants.FLAG_UPD_BY_ORG_NO);
        return super.update(sql, param);
    }

    /**
     * 新增或更新 ()<br>
     * 
     * @param shareOrgYearTerm
     * @return
     */
    public boolean updateOrAddYearTerm(ShareOrgYearTerm shareOrgYearTerm) {
        try {
            return super.saveOne(shareOrgYearTerm);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除 ()<br>
     * 
     * @param gid
     * @return
     */
    public int deleteYearTerm(String gid) {
        String sql = "delete from share_org_year_term where gid=?";
        return super.update(sql, gid);
    }

    /**
     * 
     * (批量更新机构学年学期)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @param sectionCode 学段编码
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param totalWeekNum 总周数
     * @return 更新记录数
     */
    public int updateOrgYearTerm(String yearTermCode, String sectionCode, Timestamp startDate, Timestamp endDate,
                    int totalWeekNum) {
        String sql = "update share_org_year_term set startDate=:startDate,endDate=:endDate,totalWeekNum=:totalWeekNum where yearTermCode=:yearTermCode and flagUpdByOrg = :flagUpdByOrg and sectionCode=:sectionCode";
        Map<String, Object> map = new HashMap<String, Object>(); 
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("sectionCode", sectionCode);
        map.put("yearTermCode", yearTermCode);
        map.put("totalWeekNum", totalWeekNum);
        map.put("flagUpdByOrg", CoreConstants.FLAG_UPD_BY_ORG_NO);
        return super.update(sql, map);
    }

    /**
     * 查询机构学期 ()<br>
     * 
     * @param yearTermCode
     * @param orgCode
     * @return
     */
    public ShareOrgYearTerm getTerm(String yearTermCode, String orgCode, String sectionCode) {
        QueryRule qRule = QueryRule.getInstance();
        qRule.andEqual("yearTermCode", yearTermCode);
        qRule.andEqual("orgCode", orgCode);
        if (StringUtils.isNotEmpty(sectionCode)) {
            qRule.andEqual("sectionCode", sectionCode);
        }
        return super.findUnique(qRule);
    }

    /**
     * 查询学年学期列表 ()<br>
     * 
     * @return
     */
    public List<Map<String, Object>> getOrgYearTermList(String orgCode, String sectionCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        String sql = "select soyt.*,scyt.yearTermName,so.orgName  from share_org_year_term soyt"
                        + "  inner join share_org so on so.orgCode=soyt.orgCode ";
        if (StringUtils.isNotEmpty(orgCode)) {
            sql = sql + " and so.orgCode=:orgCode";
            param.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(sectionCode)) {
            sql = sql + " and soyt.sectionCode=:sectionCode";
            param.put("sectionCode", sectionCode);
        }
        sql = sql + " left join share_code_year_term scyt on soyt.yearTermCode=scyt.yearTermCode order by soyt.yearTermCode";
        return super.findBySql(sql, param);
    }

    /**
     * 
     * (根据相关条件更新开始时间)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @param sectionCode 学段编码
     * @param currentDate 当前时间
     * @param userName 用户姓名
     * @param modifyIP 更新者IP
     * @return 更新记录数
     */
    @Override
    public int updateStartDateByYearTermCode(String yearTermCode, String sectionCode, Timestamp currentDate,
                    String userName, String modifyIP) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        sql.append("UPDATE");
        sql.append("        share_org_year_term ");
        sql.append("    SET");
        sql.append("        startDate = :startDate");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifyIP = :modifyIP");
        sql.append("        ,modifyPgm = 'updateStartDateByYearTermCode'");
        sql.append("        ,modifyTime = :currentTime");
        sql.append("        ,sysVer = sysVer + 1 ");
        sql.append("    WHERE");
        sql.append("        sectionCode = :sectionCode ");
        sql.append("        AND yearTermCode = :yearTermCode ");
        sqlParam.put("startDate", currentDate);
        sqlParam.put("modifier", userName);
        sqlParam.put("modifyIP", modifyIP);
        sqlParam.put("currentTime", currentTime);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("yearTermCode", yearTermCode);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * (根据gid查询实体)<br>
     * @param gid
     * @return 
     */
    @Override
    public ShareOrgYearTerm selectByGid(String gid) {
        QueryRule qr=QueryRule.getInstance();
        qr.andEqual("gid", gid);
        return super.findUnique(qr);
    }
    
    /**
     * 
     * (根据学段编码和学年学期编码等条件查询上一个或下一个学年学期)<br>
     * @param yearTermCode 学年学期编码
     * @param type 操作类型（1：查询上一个；2：查询下一个）
     * @param orgCode 机构编码
     * @return 符合条件的学年学期信息
     */
    @Override
    public Map<String, Object> selectLastOrNextYearTermBySectionCode(String yearTermCode, Integer type,
                    String orgCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("yearTermCode", yearTermCode);
        sqlParam.put("orgCode", orgCode);
        sql.append("SELECT");
        sql.append("        TOP 1 startDate");
        sql.append("        ,endDate ");
        sql.append("    FROM");
        sql.append("        share_org_year_term ");
        sql.append("    WHERE");
        sql.append("        orgCode = :orgCode ");
        if(1==type.intValue()){//查询上一个
            sql.append("        AND yearTermCode < :yearTermCode ");
            sql.append("    ORDER BY");
            sql.append("        yearTermCode DESC ");
        }else if(2==type.intValue()){//查询下一个
            sql.append("        AND yearTermCode > :yearTermCode ");
            sql.append("    ORDER BY");
            sql.append("        yearTermCode ASC ");
        }
        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

}
