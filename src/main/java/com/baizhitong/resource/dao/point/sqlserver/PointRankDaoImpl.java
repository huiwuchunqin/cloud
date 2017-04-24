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
import com.baizhitong.resource.dao.point.PointRankDao;
import com.baizhitong.resource.model.point.PointRank;

/**
 * 平台积分头衔dao接口实现 PointRankDaoImpl TODO
 * 
 * @author creator gaow 2016年6月24日 上午11:27:38
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointRankDaoImpl extends BaseSqlServerDao<PointRank> implements PointRankDao {
    /**
     * 新增积分头衔等级 ()<br>
     * 
     * @param map
     * @param rows
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> map, Integer rows, Integer page) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT");
        sql.append("        pr.id");
        sql.append("        ,pr.bizVersion");
        sql.append("        ,pr.startTime");
        sql.append("        ,pr.expireTime");
        sql.append("        ,pr.userRole");
        sql.append("        ,pr.rankCode");
        sql.append("        ,pr.rankName");
        sql.append("        ,pr.goodsCode");
        sql.append("        ,pr.goodsName");
        sql.append("        ,pr.goodAmount");
        sql.append("        ,pr.thumbnailJson");
        sql.append("        ,pr.totalPoint");
        sql.append("        ,pr.dispOrder");
        sql.append("        ,pr.memo");
        sql.append("        ,pr.modifier");
        sql.append("        ,pr.modifyTime");
        sql.append("        ,pr.modifierIP");
        sql.append("        ,pr.sysVersion  ");
        sql.append("    FROM");
        sql.append("        point_rank pr ");
        sql.append("    WHERE");
        sql.append("        1=1 ");

        sql.append(" order by pr.userRole,pr.totalPoint asc ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        return super.queryDistinctPage(sql.toString(), sqlParam, page, rows);

    }

    /**
     * 新增积分头衔等级 ()<br>
     * 
     * @param pointRank
     * @return
     */
    public boolean add(PointRank pointRank) {
        try {
            return super.saveOne(pointRank);
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
    public String getMaxCode() {
        String sql = " select Max(rankCode)as max from point_rank ";
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> map = super.findUniqueBySql(sql, param);
        return MapUtils.getString(map, "max");
    }

    /**
     * 插入机构积分头衔等级 ()<br>
     * 
     * @param pointRank
     * @return
     */
    public int addOrgPointRank(PointRank pointRank) {
        StringBuffer sql = new StringBuffer();
        sql.append("INSERT ");
        sql.append("    INTO");
        sql.append("        point_rank_org");
        sql.append("        (       settingType,     orgCode,     bizVersion,     startTime,     expireTime,     userRole,     rankCode,     rankName,     goodsCode,     goodsName,     goodAmount,     thumbnailJson,     totalPoint,     dispOrder,     memo,     modifier,     modifyTime,     modifierIP,  orgRankCode,   sysVersion    )");

        sql.append("    		(select  	");
        sql.append("            :settingType");
        sql.append("            ,orgCode");
        sql.append("            ,bizVersion");
        sql.append("            ,startTime");
        sql.append("            ,expireTime");
        sql.append("            ,userRole");
        sql.append("            ,rankCode");
        sql.append("            ,rankName");
        sql.append("            ,goodsCode");
        sql.append("            ,goodsName");
        sql.append("            ,goodAmount");
        sql.append("            ,thumbnailJson");
        sql.append("            ,totalPoint");
        sql.append("            ,dispOrder");
        sql.append("            ,memo");
        sql.append("            ,:modifier");
        sql.append("            ,:modifyTime");
        sql.append("            ,:modifierIP");
        sql.append("            ,rankCode");
        // sql.append(" , (select
        // LTRIM(RTRIM(isnull(max(a.orgRankCode)+1,1000))) from point_rank_org a
        // where a.orgCode=share_org.orgCode )");
        sql.append("            ,1 ");
        sql.append("        FROM");
        sql.append("            point_rank ");
        sql.append("    inner join   ");
        sql.append("            share_org ");
        sql.append("            on 1=1 ");
        sql.append("	WHERE   ");
        sql.append("    orgCode NOT ");
        sql.append("    ");
        sql.append("IN (");
        sql.append("    SELECT");
        sql.append("        orgCode   FROM");
        sql.append("            point_rank_org   ");
        sql.append("        WHERE");
        sql.append("            ");
        sql.append("                settingType =:notExtend");
        sql.append("            ");
        sql.append("        GROUP BY");
        sql.append("            orgCode  ");
        sql.append("    )");
        sql.append("  and rankCode not in(");
        sql.append("SELECT");
        sql.append("        rankCode ");
        sql.append("    FROM");
        sql.append("        point_rank_org ");
        sql.append("    WHERE");
        sql.append("        orgCode =share_org.orgCode ");
        sql.append("  ))");

        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("settingType", CoreConstants.SETTING_TYPE_EXTEND);
        sqlParam.put("bizVersion", pointRank.getBizVersion());
        sqlParam.put("userRole", pointRank.getUserRole());
        sqlParam.put("notExtend", CoreConstants.SETTING_TYPE_NOT_EXTEND);
        sqlParam.put("startTime", pointRank.getStartTime());
        sqlParam.put("expireTime", pointRank.getExpireTime());
        sqlParam.put("userRole", pointRank.getUserRole());
        sqlParam.put("totalPoint", pointRank.getTotalPoint());
        sqlParam.put("rankName", pointRank.getRankName());
        sqlParam.put("goodsCode", pointRank.getGoodsCode());
        sqlParam.put("goodsName", pointRank.getGoodsName());
        sqlParam.put("goodAmount", pointRank.getGoodAmount());
        sqlParam.put("thumbnailJson", pointRank.getThumbnailJson());
        sqlParam.put("totalPoint", pointRank.getTotalPoint());
        sqlParam.put("dispOrder", pointRank.getDispOrder());
        sqlParam.put("memo", pointRank.getMemo());
        sqlParam.put("rankCode", pointRank.getRankCode());
        sqlParam.put("modifier", pointRank.getModifier());
        sqlParam.put("modifyTime", pointRank.getModifyTime());
        sqlParam.put("modifierIP", pointRank.getModifierIP());
        sqlParam.put("sysVersion", pointRank.getSysVersion());
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 修改机构头衔积分等级信息 ()<br>
     * 
     * @param pointRank
     * @return
     */
    public int updateOrgPointRank(PointRank pointRank) {
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE");
        sql.append("        point_rank_org   ");
        sql.append("    SET");
        sql.append("        startTime = :startTime");
        sql.append("        ,expireTime = :expireTime");
        sql.append("        ,userRole = :userRole");
        sql.append("        ,rankName = :rankName");
        sql.append("        ,goodsCode = :goodsCode");
        sql.append("        ,goodsName = :goodsName");
        sql.append("        ,goodAmount = :goodAmount");
        sql.append("        ,thumbnailJson = :thumbnailJson");
        sql.append("        ,totalPoint = :totalPoint");
        sql.append("        ,dispOrder = :dispOrder");
        sql.append("        ,memo = :memo");
        sql.append("        ,modifier = :modifier");
        sql.append("        ,modifyTime = :modifyTime");
        sql.append("        ,modifierIP = :modifierIP");
        sql.append("        ");
        sql.append("    WHERE");
        sql.append("        rankCode=:rankCode  and settingType=:settingType");
        sql.append(" and orgCode not in ");
        sql.append(" ( ");
        sql.append("SELECT");
        sql.append("        orgCode ");
        sql.append("    FROM");
        sql.append("        point_rank_org ");
        sql.append("    WHERE");
        sql.append("        settingType =:notExtend ");
        sql.append("        ");
        sql.append("    GROUP BY");
        sql.append("        orgCode");
        sql.append(" ) ");
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("startTime", pointRank.getStartTime());
        sqlParam.put("expireTime", pointRank.getExpireTime());
        sqlParam.put("userRole", pointRank.getUserRole());
        sqlParam.put("rankName", pointRank.getRankName());
        sqlParam.put("goodsCode", pointRank.getGoodsCode());
        sqlParam.put("goodsName", pointRank.getGoodsName());
        sqlParam.put("goodAmount", pointRank.getGoodAmount());
        sqlParam.put("thumbnailJson", pointRank.getThumbnailJson());
        sqlParam.put("totalPoint", pointRank.getTotalPoint());
        sqlParam.put("dispOrder", pointRank.getDispOrder());
        sqlParam.put("memo", pointRank.getMemo());
        sqlParam.put("modifier", pointRank.getModifier());
        sqlParam.put("modifyTime", pointRank.getModifyTime());
        sqlParam.put("modifierIP", pointRank.getModifierIP());
        sqlParam.put("rankCode", pointRank.getRankCode());
        sqlParam.put("settingType", CoreConstants.SETTING_TYPE_EXTEND);
        sqlParam.put("notExtend", CoreConstants.SETTING_TYPE_NOT_EXTEND);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 查询积分头衔等级 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRank getById(Integer id) {
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
        // TODO Auto-generated method stub
        String sql = "delete from point_rank where id=?";
        return super.update(sql, id);
    }
    /**
     * 角色积分规则数
     * ()<br>
     * @return
     */
    public long rankCount(Integer userRole){
        String sql="select count(*) from point_rank where userRole=:userRole";
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("userRole",userRole);
        return super.queryCount(sql, param);
    }
    /**
     * 查找积分相同的头像 ()<br>
     * 
     * @param totalPoint
     * @return
     */
    public List<PointRank> getSamePointList(Integer totalPoint, Integer userRole) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("totalPoint", totalPoint);
        qr.andEqual("userRole", userRole);
        return super.find(qr);
    }
}
