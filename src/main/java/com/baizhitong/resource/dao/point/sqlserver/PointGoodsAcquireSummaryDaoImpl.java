package com.baizhitong.resource.dao.point.sqlserver;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointGoodsAcquireSummaryDao;
import com.baizhitong.resource.model.point.PointGoodsAcquireSummary;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;
import com.mchange.v1.util.ArrayUtils;

/**
 * 商品兑换发放汇总 PointGoodsAcquireSummaryDaoImpl TODO
 * 
 * @author creator gaow 2016年6月28日 下午2:27:54
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointGoodsAcquireSummaryDaoImpl extends BaseSqlServerDao<PointGoodsAcquireSummary>
                implements PointGoodsAcquireSummaryDao {
    /**
     * 查询分页信息 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        Integer status = MapUtils.getInteger(param, "status"); // 1已兑换0未兑换
        Integer goodsLevel = MapUtils.getInteger(param, "goodsLevel");
        String adminClassCode = MapUtils.getString(param, "adminClassCode");
        String userName = MapUtils.getString(param, "userName");
        String orgCode = MapUtils.getString(param, "orgCode");
        String gradeCode = MapUtils.getString(param, "gradeCode");
        Integer userRole = MapUtils.getInteger(param, "userRole");
        sql.append("SELECT");
        sql.append("        a.id");
        sql.append("        ,a.orgCode");
        sql.append("        ,a.orgName");
        sql.append("        ,a.userRole");
        sql.append("        ,a.userCode");
        sql.append("        ,a.userName");
        sql.append("        ,a.avatarsImg");
        sql.append("        ,a.adminClassCode");
        sql.append("        ,a.goodsLevel");
        sql.append("        ,a.goodsType");
        sql.append("        ,a.goodsCode");
        sql.append("        ,a.goodsName");
        sql.append("        ,a.goodsSpecification");
        sql.append("        ,a.goodsThumbnailJson");
        sql.append("        ,a.convertedAmount");
        sql.append("        ,a.acquiredAmount");
        sql.append("        ,a.acquiringAmount");
        sql.append("        ,a.lastBaseTime");
        sql.append("        ,a.lastGrantBatch");
        sql.append("        ,a.lastAcquireDate");
        sql.append("        ,a.memo");
        sql.append("        ,a.creator");
        sql.append("        ,a.createTime");
        sql.append("        ,a.creatorIP");
        sql.append("        ,a.modifier");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.modifierIP  ");
        sql.append("        ,sac.className as adminClassName  ");
        sql.append("    FROM ");
        sql.append("        point_goods_acquire_summary a  ");
        sql.append(" left join share_admin_class sac on a.adminClassCode=sac.classCode ");
        sql.append(" where 1=1 ");
        if (status != null) {
            if (status == 0) {// 未兑换完
                sql.append(" and a.acquiringAmount>0 ");
            } else {// 兑换完
                sql.append(" and a.acquiringAmount=0 ");
            }
            ;
        }

        if (goodsLevel != null) {
            sql.append(" and a.goodsLevel=:goodsLevel");
            sqlParam.put("goodsLevel", goodsLevel);
        }
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and a.orgCode=:orgCode");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(adminClassCode)) {
            sql.append(" and a.adminClassCode=:adminClassCode");
            sqlParam.put("adminClassCode", adminClassCode);
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and a.userName like:userName");
            sqlParam.put("userName", "%" + userName + "%");
        }
        if (userRole != null) {
            sql.append(" and a.userRole =:userRole");
            sqlParam.put("userRole", userRole);
        }
        if (StringUtils.isNotEmpty(gradeCode)) {
            sql.append(" and sac.gradeCode =:gradeCode");
            sqlParam.put("gradeCode", gradeCode);
        }
        sql.append(" order by a.goodsName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 兑换 ()<br>
     * 
     * @param orgCode
     */
    public int exchange(String orgCode, Integer goodsLevel, String adminClassCode, String userName, String userRole,
                    String ids) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("UPDATE");
        sql.append("        point_goods_acquire_summary   ");
        sql.append("    SET");
        sql.append("        acquiredAmount = convertedAmount");
        sql.append("        ,acquiringAmount = 0");
        sql.append("        ,lastGrantBatch=:lastGrantBatch");
        sql.append("        ,lastAcquireDate=:lastAcquireDate   ");
        sql.append("    WHERE ");
        sql.append("        acquiringAmount > 0   ");
        sql.append("        AND orgCode =:orgCode");
        sql.append("        AND goodsLevel =:goodsLevel");
        if (StringUtils.isNotEmpty(adminClassCode)) {
            sql.append(" and adminClassCode=:adminClassCode");
            sqlParam.put("adminClassCode", adminClassCode);
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and userName like :userName");
            sqlParam.put("userName", "%" + userName + "%");
        }
        if (StringUtils.isNotEmpty(ids)) {
            sql.append(" and id in (" + ids + ")");
        }
        if (userRole != null) {
            sql.append(" and userRole=:userRole");
            sqlParam.put("userRole", userRole);
        }
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("goodsLevel", goodsLevel);
        sqlParam.put("lastGrantBatch", TimeUtils.formatDate(new Date(), "YYYYMMdd"));
        sqlParam.put("lastAcquireDate", new Date());
        return super.update(sql.toString(), sqlParam);
    }
}
