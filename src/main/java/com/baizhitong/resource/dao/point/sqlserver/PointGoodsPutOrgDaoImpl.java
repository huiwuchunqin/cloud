package com.baizhitong.resource.dao.point.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointGoodsPutOrgDao;
import com.baizhitong.resource.model.point.PointGoodsPutOrg;
import com.baizhitong.utils.StringUtils;

/**
 * 机构商品入库 PointGoodsPutOrgDaoImpl TODO
 * 
 * @author creator gaow 2016年6月27日 下午1:35:02
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointGoodsPutOrgDaoImpl extends BaseSqlServerDao<PointGoodsPutOrg> implements PointGoodsPutOrgDao {
    /**
     * 查询入库明细 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    public Page getList(Map<String, Object> param, String orgCode, Integer page, Integer rows) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        String goodsCode = MapUtils.getString(param, "goodsCode");
        String goodsName = MapUtils.getString(param, "goodsName");
        sql.append("SELECT");
        sql.append("        a.*");
        sql.append("        ,b.goodsName ");
        sql.append("    FROM");
        sql.append("        point_goods_put_org a ");
        sql.append("        ");
        sql.append("    inner JOIN");
        sql.append("        point_goods_org b ");
        sql.append("            on a.goodsCode=b.goodsCode");
        sql.append(" where a.orgCode =:orgCode  and b.flagDelete=0 ");

        if (StringUtils.isNotEmpty(goodsCode)) {
            sql.append(" and b.goodsCode = :goodsCode ");
            sqlParam.put("goodsCode", goodsCode);
        }
        if (StringUtils.isNotEmpty(goodsName)) {
            sql.append(" and b.goodsName like :goodsName ");
            sqlParam.put("goodsName", "%" + goodsName + "%");
        }
        sql.append("	order by b.goodsName  ");
        sqlParam.put("orgCode", orgCode);
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);
    }

    /**
     * 保存或新增 ()<br>
     * 
     * @param pointGoodsPut
     * @return
     */
    public boolean saveOrupdate(PointGoodsPutOrg pointGoodsPut) {
        try {
            return super.saveOne(pointGoodsPut);
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
     * @param getByGoodsCode
     * @return
     */
    public PointGoodsPutOrg getByGoodsCode(String goodsCode) {
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
    public int updateGoodsOrgGoodsAmout(Integer count, String goodsCode) {
        String sql = " update point_goods_org set goodsAmount=(goodsAmount+:count) where goodsCode=:goodsCode";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("count", count);
        sqlParam.put("goodsCode", goodsCode);
        return super.update(sql, sqlParam);
    }
}
