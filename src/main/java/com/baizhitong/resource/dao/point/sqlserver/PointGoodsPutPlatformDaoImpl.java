package com.baizhitong.resource.dao.point.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointGoodsPutPlatformDao;
import com.baizhitong.resource.model.point.PointGoodsPutPlatform;
import com.baizhitong.utils.StringUtils;

/**
 * 平台商品入库 PointGoodsPutPlatformDaoImpl TODO
 * 
 * @author creator gaow 2016年6月27日 下午1:34:46
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointGoodsPutPlatformDaoImpl extends BaseSqlServerDao<PointGoodsPutPlatform>
                implements PointGoodsPutPlatformDao {

    /**
     * 查询分页列表 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    public Page getList(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        String goodsCode = MapUtils.getString(param, "goodsCode");
        String goodsName = MapUtils.getString(param, "goodsName");
        sql.append("SELECT");
        sql.append("        a.*");
        sql.append("        ,b.goodsName ");
        sql.append("    FROM");
        sql.append("        point_goods_put_platform a ");
        sql.append("        ");
        sql.append("    INNER JOIN");
        sql.append("        point_goods_platform b ");
        sql.append("            on a.goodsCode=b.goodsCode");
        sql.append("    where 1=1  and b.flagDelete=0 ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(goodsCode)) {
            sql.append("  and a.goodsCode=:goodsCode ");
            sqlParam.put("goodsCode", goodsCode);
        }
        if (StringUtils.isNotEmpty(goodsName)) {
            sql.append("  and b.goodsName like :goodsName ");
            sqlParam.put("goodsName", "%" + goodsName + "%");
        }
        sql.append(" order by b.goodsName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 保存新增 ()<br>
     * 
     * @param pointGoodsPutPlatform
     * @return
     */
    public boolean saveOrupdate(PointGoodsPutPlatform pointGoodsPutPlatform) {
        try {
            return super.saveOne(pointGoodsPutPlatform);
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
     * 根据id查询 ()<br>
     * 
     * @param goodsCode
     * @return
     */
    public PointGoodsPutPlatform getByGoodsCode(String goodsCode) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("goodsCode", goodsCode);
        return super.findUnique(qr);
    }

    /**
     * 更新商品表库存字段 ()<br>
     * 
     * @param count
     * @param goodsCode
     * @return
     */
    public int updateGoodsPlatformGoodsAmout(Integer count, String goodsCode) {
        String sql = " update point_goods_platform set goodsAmount=(goodsAmount+:count) where goodsCode=:goodsCode";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("count", count);
        sqlParam.put("goodsCode", goodsCode);
        return super.update(sql, sqlParam);
    }
}
