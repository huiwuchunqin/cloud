package com.baizhitong.resource.dao.point.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointRankOrgDao;
import com.baizhitong.resource.model.point.PointRankOrg;
import com.baizhitong.utils.StringUtils;

/**
 * 积分头衔等级dao实现 PointRankOrgDaoImpl TODO
 * 
 * @author creator gaow 2016年6月24日 上午11:29:38
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointRankOrgDaoImpl extends BaseSqlServerDao<PointRankOrg> implements PointRankOrgDao {
    /**
     * 查询积分等级列表 ()<br>
     * 
     * @param map
     * @param rows
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> map, Integer rows, Integer page) {
        StringBuffer sql = new StringBuffer();
        String orgCode = MapUtils.getString(map, "orgCode");

        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append("SELECT");
        sql.append("        pro.id");
        sql.append("        ,pro.settingType");
        sql.append("        ,pro.orgCode");
        sql.append("        ,pro.bizVersion");
        sql.append("        ,pro.startTime");
        sql.append("        ,pro.expireTime");
        sql.append("        ,pro.userRole");
        sql.append("        ,pro.rankCode");
        sql.append("        ,pro.rankName");
        sql.append("        ,pro.goodsCode");
        sql.append("        ,pro.goodsName");
        sql.append("        ,pro.goodAmount");
        sql.append("        ,pro.thumbnailJson");
        sql.append("        ,pro.totalPoint");
        sql.append("        ,pro.dispOrder");
        sql.append("        ,pro.memo");
        sql.append("        ,pro.modifier");
        sql.append("        ,pro.modifyTime");
        sql.append("        ,pro.modifierIP");
        sql.append("        ,pro.sysVersion");
        sql.append("        ,pro.orgRankCode  ");
        sql.append("    FROM");
        sql.append("        point_rank_org pro where 1=1 ");

        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and pro.orgCode=:orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        sql.append(" order by  pro.userRole,pro.totalPoint asc  ");
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 新增积分头衔等级 ()<br>
     * 
     * @param pointRank
     * @return
     */
    public boolean add(PointRankOrg pointRankOrg) {
        try {
            return super.saveOne(pointRankOrg);
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
     * 查询最大头衔编码 ()<br>
     * 
     * @return
     */
    public String getMaxCode(String orgCode) {
        String sql = " select Max(orgRankCode)as max from point_rank_org where orgCode=:orgCode ";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgCode", orgCode);
        Map<String, Object> map = super.findUniqueBySql(sql, param);
        return MapUtils.getString(map, "max");
    }

    /**
     * 查询积分头衔 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRankOrg getById(Integer id) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", id);
        return super.findUnique(qr);
    }

    /**
     * 删除积分规则 ()<br>
     * 
     * @param id
     * @return
     */
    public int delete(Integer id) {
        String sql = "delete from point_rank_org where id=?";
        return super.update(sql, id);
    }

    /**
     * 
     * (根据机构编码、所需积分查询机构积分头衔信息)<br>
     * 
     * @param orgCode 机构编码
     * @param totalPoint 所需累计积分
     * @param userRole 用户身份
     * @author ZhangQiang
     * @return 机构积分头衔信息
     */
    @Override
    public PointRankOrg selectByOrgCodeAndTotalPoint(String orgCode, Integer totalPoint, Integer userRole) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("orgCode", orgCode);
        qr.andEqual("totalPoint", totalPoint);
        qr.andEqual("userRole", userRole);
        List<PointRankOrg> list = super.find(qr);
        if (list != null && list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }
    /**
     * 机构积分规则数
     * ()<br>
     * @param orgCode
     * @return
     */
    public long orgRankCount(String orgCode,Integer userRole){
        String sql="select count(*) from point_rank_org where orgCode=:orgCode and userRole=:userRole ";
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("orgCode", orgCode);
        param.put("userRole", userRole);
        return super.queryCount(sql,param);
    }
    /**
     * 删除机构积分 ()<br>
     * 
     * @param rankCode
     * @return
     */
    public int deleteOrgRank(String rankCode) {
        StringBuffer sql = new StringBuffer();
        sql.append("DELETE  ");
        sql.append("    FROM");
        sql.append("        point_rank_org  ");
        sql.append("    WHERE");
        sql.append("        rankCode =:rankCode  ");
        sql.append("        AND settingType =:settingTypeExtend  ");
        sql.append("        AND orgCode NOT ");
        sql.append("        ");
        sql.append("    IN (");
        sql.append("        SELECT");
        sql.append("            orgCode   FROM");
        sql.append("                point_rank_org   ");
        sql.append("            WHERE");
        sql.append("                settingType =:notExtend   ");
        sql.append("                ");
        sql.append("            GROUP BY");
        sql.append("                orgCode  ");
        sql.append("        )");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("rankCode", rankCode);
        sqlParam.put("notExtend", CoreConstants.SETTING_TYPE_NOT_EXTEND);
        sqlParam.put("settingTypeExtend", CoreConstants.SETTING_TYPE_EXTEND);
        return super.update(sql.toString(), sqlParam);

    }
}
