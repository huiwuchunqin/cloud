package com.baizhitong.resource.dao.point.sqlserver;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointGoodsGrantOrgDetailDao;
import com.baizhitong.resource.model.point.PointGoodsGrantOrgDetail;
import com.baizhitong.utils.StringUtils;

/**
 * 商品发放明细机构 PointGoodsGrantOrgDetailDaoImpl TODO
 * 
 * @author creator gaow 2016年6月28日 下午7:47:18
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointGoodsGrantOrgDetailDaoImpl extends BaseSqlServerDao<PointGoodsGrantOrgDetail>
                implements PointGoodsGrantOrgDetailDao {

    /**
     * 插入 ()<br>
     * 
     * @return
     */
    public int insert(String orgCode, String studyYearCode, String termCode, String yearTermCode, String actionBatch,
                    String modifier, String modifierIP, String adminClassCode, String userName, String userRole,
                    String ids) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        point_goods_grant_org_detail");
        sql.append("        (    orgCode,    studyYearCode,    termCode,    yearTermCode,    userCode,    userRole,    userName,    avatarsImg,    adminClassCode,    adminGroupCode,    goodsCode,    goodsName,    goodsSpecification,    goodsUnit,    goodsThumbnailJson,    actionBatch,    actionDate,    actionNum,    memo,    modifier,    modifyTime,    modifierIP   )   select");
        sql.append("            p.orgCode");
        sql.append("            ,:studyYearCode");
        sql.append("            ,:termCode");
        sql.append("            ,:yearTermCode");
        sql.append("            ,p.userCode");
        sql.append("            ,p.userRole");
        sql.append("            ,p.userName");
        sql.append("            ,p.avatarsImg");
        sql.append("            ,p.adminClassCode");
        sql.append("            ,''");
        sql.append("            ,p.goodsCode");
        sql.append("            ,p.goodsName");
        sql.append("            ,p.goodsSpecification");
        sql.append("            ,''                      ");
        sql.append("            ,p.goodsThumbnailJson");
        sql.append("            ,:actionBatch");
        sql.append("            ,:actionDate");
        sql.append("            ,p.acquiringAmount");
        sql.append("            ,''");
        sql.append("            ,:modifier");
        sql.append("            ,:modifyTime");
        sql.append("            ,:modifierIP   from  point_goods_acquire_summary p   ");
        sql.append("        WHERE");
        sql.append("            p.acquiringAmount>0 ");
        sql.append("            and orgCode=:orgCode ");
        if (StringUtils.isNotEmpty(adminClassCode)) {
            sql.append(" and p.adminClassCode=:adminClassCode");
            sqlParam.put("adminClassCode", adminClassCode);
        }
        if (StringUtils.isNotEmpty(userName)) {
            sql.append(" and p.userName like :userName");
            sqlParam.put("userName", "%" + userName + "%");
        }
        if (userRole != null) {
            sql.append(" and p.userRole=:userRole");
            sqlParam.put("userRole", userRole);
        }
        if (StringUtils.isNotEmpty(ids)) {
            sql.append(" and p.id in(" + ids + ")");
        }
        sqlParam.put("studyYearCode", studyYearCode);
        sqlParam.put("termCode", termCode);
        sqlParam.put("orgCode", orgCode);
        sqlParam.put("yearTermCode", yearTermCode);
        sqlParam.put("actionBatch", actionBatch);
        sqlParam.put("actionDate", new Date());
        sqlParam.put("modifier", modifier);
        sqlParam.put("modifyTime", new Timestamp(new Date().getTime()));
        sqlParam.put("modifierIP", modifierIP);
        return super.update(sql.toString(), sqlParam);
    }
}
