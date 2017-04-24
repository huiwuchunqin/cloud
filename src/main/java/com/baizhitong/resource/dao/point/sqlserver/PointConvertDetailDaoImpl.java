package com.baizhitong.resource.dao.point.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointConvertDetailDao;
import com.baizhitong.resource.model.point.PointConvertDetail;
import com.baizhitong.utils.StringUtils;

@Service
public class PointConvertDetailDaoImpl extends BaseSqlServerDao<PointConvertDetail> implements PointConvertDetailDao {
    /**
     * 查询兑换明细 ()<br>
     * 
     * @param param
     * @return
     */
    public Page getConvertDetail(Map<String, Object> param, Integer page, Integer rows) {
        StringBuffer sql = new StringBuffer();
        String orgCode = MapUtils.getString(param, "orgCode");
        String goodsName = MapUtils.getString(param, "goodsName");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        a.id");
        sql.append("        ,a.orgCode");
        sql.append("        ,a.userCode");
        sql.append("        ,a.userRole");
        sql.append("        ,a.userName");
        sql.append("        ,a.goodsType");
        sql.append("        ,a.goodsCode");
        sql.append("        ,a.goodsName");
        sql.append("        ,a.convertDate");
        sql.append("        ,a.convertAmount");
        sql.append("        ,a.goodsBizType");
        sql.append("        ,a.convertPoints");
        sql.append("        ,a.memo");
        sql.append("        ,a.modifier");
        sql.append("        ,a.modifyTime");
        sql.append("        ,a.modifierIP  ");
        sql.append("    FROM");
        sql.append("        point_convert_detail a");
        sql.append(" where 1=1  ");
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and a.orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        if (StringUtils.isNotEmpty(goodsName)) {
            sql.append(" and a.goodsName like :goodsName ");
            sqlParam.put("goodsName", "%" + goodsName + "%");
        }
        sql.append(" order by a.goodsName ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }
}
