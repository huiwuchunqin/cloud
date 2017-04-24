package com.baizhitong.resource.dao.point.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointGoodsGrantPlatformDao;
import com.baizhitong.resource.model.point.PointGoodsGrantPlatform;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * PointGoodsGrantPlatformDaoImpl TODO
 * 
 * @author creator gaow 2016年6月28日 下午7:34:45
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointGoodsGrantPlatformDaoImpl extends BaseSqlServerDao<PointGoodsGrantPlatform>
                implements PointGoodsGrantPlatformDao {
    /**
     * 查询商品发放概要 ()<br>
     * 
     * @param actionBatch
     * @return
     */
    public List<PointGoodsGrantPlatform> getSummaryList(String actionBatch) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("actionBatch", actionBatch);
        return super.find(qr);
    }

    /**
     * 查询商品发放概要 ()<br>
     * 
     * @return
     */
    public Page getList(Map<String, Object> param, Integer page, Integer rows) {
        String orgName = MapUtils.getString(param, "orgName");
        String goodsName = MapUtils.getString(param, "goodsName");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        a.orgCode ");
        sql.append("        ,a.goodsCode");
        sql.append("        ,a.goodsName");
        sql.append("        ,a.goodsSpecification");
        sql.append("        ,a.goodsThumbnailJson");
        sql.append("        ,b.orgName ");
        sql.append("        ,SUM (a.acquiringAmount) as totalCount ");
        sql.append("    FROM");
        sql.append("        point_goods_acquire_summary a ");
        sql.append(" left join ");
        sql.append(" share_org b on a.orgCode=b.orgCode ");
        sql.append("    WHERE");
        sql.append("        a.acquiringAmount > 0  ");
        sql.append("        AND a.goodsLevel =:goodsLevel");
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append(" and a.orgName like :orgName ");
            sqlParam.put("orgName", "%" + orgName + "%");
        }
        if (StringUtils.isNotEmpty(goodsName)) {
            sql.append(" and a.goodsName like :goodsName ");
            sqlParam.put("goodsName", "%" + goodsName + "%");
        }
        sql.append("    GROUP BY");
        sql.append("        a.orgCode");
        sql.append("        ,a.goodsCode");
        sql.append("        ,a.goodsName");
        sql.append("        ,b.orgName");
        sql.append("        ,a.goodsSpecification");
        sql.append("        ,a.goodsThumbnailJson  ");
        sql.append(" order by a.goodsName ");
        sqlParam.put("goodsLevel", CoreConstants.GOODS_LEVEL_AREA);
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 插入 ()<br>
     * 
     * @return
     */
    public int insert(String studyYearCode, String termCode, String yearTermCode, String actionBatch, String modifier,
                    String modifierIP, String orgName, String goodsName) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        point_goods_grant_platform");
        sql.append("        (    orgCode,    studyYearCode,    termCode,    yearTermCode,    goodsCode,    goodsName,    goodsSpecification,    goodsUnit,    goodsThumbnailJson,    actionBatch,    actionDate,    actionNum,    memo,    modifier,    modifyTime,    modifierIP   ) SELECT");
        sql.append("            orgCode ");
        sql.append("            ,:studyYearCode ");
        sql.append("            ,:termCode ");
        sql.append("            ,:yearTermCode");
        sql.append("            ,goodsCode");
        sql.append("            ,goodsName");
        sql.append("            ,goodsSpecification");
        sql.append("            ,''");
        sql.append("            ,goodsThumbnailJson");
        sql.append("            ,:actionBatch");
        sql.append("            ,:actionDate");
        sql.append("            ,SUM (acquiringAmount)");
        sql.append("            ,''");
        sql.append("            ,:modifier");
        sql.append("            ,:modifyTime");
        sql.append("            ,:modifierIP   ");
        sql.append("        FROM");
        sql.append("            point_goods_acquire_summary   ");
        sql.append("            ");
        sql.append("        WHERE 1=1 ");
        sql.append("            AND goodsLevel = 30");
        if (StringUtils.isNotEmpty(orgName)) {
            sql.append("  and orgName like:orgName ");
            sqlParam.put("orgName", "%" + orgName + "%");
        }
        if (StringUtils.isNotEmpty(goodsName)) {
            sql.append("  and goodsName like:goodsName ");
            sqlParam.put("goodsName", "%" + goodsName + "%");
        }
        sql.append("        GROUP BY");
        sql.append("            orgCode");
        sql.append("            ,goodsCode");
        sql.append("            ,goodsName");
        sql.append("            ,goodsSpecification");
        sql.append("            ,goodsThumbnailJson   ");

        sqlParam.put("studyYearCode", studyYearCode);
        sqlParam.put("termCode", termCode);
        sqlParam.put("yearTermCode", yearTermCode);
        sqlParam.put("actionBatch", actionBatch);
        sqlParam.put("actionDate", new Date());
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifierIP", modifierIP);
        return super.update(sql.toString(), sqlParam);

    };
}
