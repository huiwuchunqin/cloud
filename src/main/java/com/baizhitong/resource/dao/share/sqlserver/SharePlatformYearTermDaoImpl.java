package com.baizhitong.resource.dao.share.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.SharePlatformYearTermDao;
import com.baizhitong.resource.model.share.SharePlatformYearTerm;
import com.baizhitong.utils.DateUtils;

/**
 * 平台学年学期dao实现 SharePlatformYearTermDaoImpl
 * 
 * @author creator BZT 2016年4月28日 下午4:57:16
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class SharePlatformYearTermDaoImpl extends BaseSqlServerDao<SharePlatformYearTerm>
                implements SharePlatformYearTermDao {

    /**
     * 查询学年学期分页列表 ()<br>
     * 
     * @param map
     * @return
     */
    public Page getYearTermList(Map<String, Object> map) {
        String sql = " select spyt.*,scyt.yearTermName,scs.name  from share_platform_year_term spyt"
                        + " left join share_code_section scs on scs.code=spyt.sectionCode"
                        + " left join share_code_year_term scyt on spyt.yearTermCode=scyt.yearTermCode order by spyt.sectionCode, spyt.startDate desc ";
        Integer pageSize = MapUtils.getInteger(map, "rows");
        Integer pageNumber = MapUtils.getInteger(map, "page");
        return super.queryDistinctPage(sql.toString(), map, pageNumber, pageSize);

    }

    /**
     * 新增或更新学年学期 ()<br>
     * 
     * @param yearTerm
     * @return
     */
    public int updateOrAddYearTerm(SharePlatformYearTerm yearTerm) {
        try {
            return super.saveOne(yearTerm) ? 1 : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 删除平台学年学期 ()<br>
     * 
     * @param gid
     * @return
     */
    public int deleteYearTerm(String gid) {
        String sql = "delete from share_platform_year_term where gid=?";
        return super.update(sql, gid);
    }

    /**
     * 查询学期列表 ()<br>
     * 
     * @param sectionCodeStr 学段列表
     * @return
     */
    public List<Map<String, Object>> selectPlatformList(String sectionCodeStr) {
        String sql = "select spyt.*,(scs.name+'-'+scyt.yearTermName) as yearTermName  from share_platform_year_term spyt"
                        + "  left join share_code_section scs on scs.code=spyt.sectionCode"
                        + " left join share_code_year_term scyt on spyt.yearTermCode=scyt.yearTermCode where 1=1 ";
        if (StringUtils.isNotEmpty(sectionCodeStr)) {
            sql = sql + " and scs.code in( " + sectionCodeStr + ")";
        }
        sql = sql + "order by spyt.startDate desc ";
        /*
         * Map<String,Object> param=new HashMap<String,Object>(); param.put("sectionCodeStr",
         * sectionCodeStr);
         */
        return super.findBySql(sql);
    }

    /**
     * 根据id查询 ()<br>
     * 
     * @param gid
     * @return
     */
    public SharePlatformYearTerm getPlatformYearTermById(String gid) {
        QueryRule qRule = QueryRule.getInstance();
        qRule.andEqual("gid", gid);
        return super.findUnique(qRule);
    }

    /*
     * 查询平台学期
     */
    public SharePlatformYearTerm getPlatformYearTerm(String yearTermCode, String sectionCode) {
        QueryRule qRule = QueryRule.getInstance();
        qRule.andEqual("yearTermCode", yearTermCode);
        qRule.andEqual("sectionCode", sectionCode);
        List<SharePlatformYearTerm> list = super.find(qRule);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /**
     * 
     * (根据学年学期编码和当前时间查询符合条件的记录)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @param currentDate 当前时间
     * @return 平台学年学期列表
     */
    @Override
    public List<SharePlatformYearTerm> selectListByYearTermCode(String yearTermCode, Timestamp currentDate) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("yearTermCode", yearTermCode);
        qr.andGreaterThan("startDate", currentDate);
        return super.find(qr);
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
        sql.append("        share_platform_year_term ");
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
     * (根据当前的学年学期编码获取上一个学年学期编码)<br>
     * 
     * @param yearTermCode 当前学年学期编码
     * @return 上一个学年学期编码
     */
    @Override
    public String selectPreviousYearTermCode(String yearTermCode) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        String previousYearTermCode = "";
        sql.append("SELECT");
        sql.append("        TOP 1 yearTermCode ");
        sql.append("    FROM");
        sql.append("        share_platform_year_term ");
        sql.append("    WHERE");
        sql.append("        yearTermCode < :yearTermCode ");
        sql.append("    ORDER BY");
        sql.append("        yearTermCode DESC");
        sqlParam.put("yearTermCode", yearTermCode);
        Map<String, Object> mapData = super.findUniqueBySql(sql.toString(), sqlParam);
        if (MapUtils.isNotEmpty(mapData)) {
            previousYearTermCode = MapUtils.getString(mapData, "yearTermCode");
        }
        return previousYearTermCode;
    }

    /**
     * 
     * (根据学年学期编码查询平台学年学期信息列表)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @return 平台学年学期信息列表
     */
    @Override
    public List<SharePlatformYearTerm> selectListByYearTermCode(String yearTermCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("yearTermCode", yearTermCode);
        qr.addAscOrder("sectionCode");// 根据学段升序排列
        return super.find(qr);
    }

    /**
     * 
     * (根据学段编码和学年学期编码等条件查询上一个或下一个学年学期)<br>
     * @param sectionCode 学段编码
     * @param yearTermCode 学年学期编码
     * @param type 操作类型（1：查询上一个；2：查询下一个）
     * @return 符合条件的学年学期信息
     */
    @Override
    public Map<String, Object> selectLastOrNextYearTermBySectionCode(String sectionCode, String yearTermCode,
                    Integer type) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("yearTermCode", yearTermCode);
        sql.append("SELECT");
        sql.append("        TOP 1 startDate");
        sql.append("        ,endDate ");
        sql.append("    FROM");
        sql.append("        share_platform_year_term ");
        sql.append("    WHERE");
        sql.append("        sectionCode = :sectionCode ");
        if(1==type.intValue()){//查询上一个
            sql.append("        AND yearTermCode < :yearTermCode ");
            sql.append("    ORDER BY");
            sql.append("        yearTermCode DESC ");
        }else if(2==type.intValue()){//查询下一个
            sql.append("        AND yearTermCode > :yearTermCode ");
            sql.append("    ORDER BY");
            sql.append("        yearTermCode ASC ");
        }        return super.findUniqueBySql(sql.toString(), sqlParam);
    }

}
