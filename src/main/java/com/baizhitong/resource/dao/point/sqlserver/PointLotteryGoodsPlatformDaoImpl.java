package com.baizhitong.resource.dao.point.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointLotteryGoodsPlatformDao;
import com.baizhitong.resource.model.point.PointLotteryGoodsPlatform;
import com.baizhitong.utils.StringUtils;

/**
 * 平台商品dao实现 PointLotteryGoodsPlatformDaoImpl TODO
 * 
 * @author creator BZT 2016年6月21日 下午7:29:07
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointLotteryGoodsPlatformDaoImpl extends BaseSqlServerDao<PointLotteryGoodsPlatform>
                implements PointLotteryGoodsPlatformDao {
    /**
     * 新增 ()<br>
     * 
     * @param goods
     * @return
     */
    public boolean add(PointLotteryGoodsPlatform goods) {
        try {
            return super.saveOne(goods);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查询商品列表 ()<br>
     * 
     * @param goodsName
     * @param role
     * @param page
     * @param rows
     * @return
     */
    public Page getList(String goodsName, Integer role, Integer page, Integer rows) {
        String sql = "select * from point_lottery_goods_Platform where 1=1 and flagDelete=0 ";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(goodsName)) {
            sql = sql + " and goodsName like:goodsName";
            sqlParam.put("goodsName", "%" + goodsName + "%");
        }
        if (role != null && role != 0) {
            sql = sql + " and userRoleRequirement =:userRoleRequirement";
            sqlParam.put("userRoleRequirement", role);
        }
        sql = sql + " order by dispOrder,goodsName";
        return super.queryDistinctPage(sql, sqlParam, page, rows);
    }

    /**
     * 查询商品 ()<br>
     * 
     * @param id
     * @return
     */
    public PointLotteryGoodsPlatform getPlatformGoods(Integer id) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", id);
        qr.andEqual("flagDelete", 0);
        return super.findUnique(qr);
    }

    /**
     * 删除商品 ()<br>
     * 
     * @param id
     * @return
     */
    public int deleteGood(Integer id) {
        String sql = " update  point_lottery_goods_Platform set flagDelete=1 where id=?";
        return super.update(sql, id);
    }

    /**
     * 查询剩余的概率 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public double getLeftProbality(Integer role, String orgCode) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("     (1 - SUM (probability)) as leftPro ");
        sql.append("    FROM");
        sql.append("        point_lottery_goods_platform ");
        sql.append("        ");
        sql.append(" where 1=1 and  flagDelete=0 and flagUsing=1  ");
        sql.append("    and  userRoleRequirement =:role ");
        sqlParam.put("role", role);
        Map<String, Object> map = super.findUniqueBySql(sql.toString(), sqlParam);
        String leftPro = MapUtils.getString(map, "leftPro");
        if (StringUtils.isNotEmpty(leftPro)) {
            return Double.valueOf(leftPro);
        } else {
            return 1.00;
        }
    }

    /**
     * 插叙某个角色的商品数量 ()<br>
     * 
     * @param role
     * @return
     */
    public int getCount(int role) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        COUNT (1) as count ");
        sql.append("    FROM");
        sql.append("        point_lottery_goods_platform ");
        sql.append("    WHERE 1=1 ");
        sql.append("    and    userRoleRequirement =:role ");
        sql.append("        AND flagUsing = 1 and flagDelete=0");
        sqlParam.put("role", role);
        Map<String, Object> map = super.findUniqueBySql(sql.toString(), sqlParam);
        if (map != null) {
            return MapUtils.getInteger(map, "count");
        } else {
            return 0;
        }

    }
}
