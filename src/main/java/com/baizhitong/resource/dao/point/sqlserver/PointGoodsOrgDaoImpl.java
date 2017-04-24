package com.baizhitong.resource.dao.point.sqlserver;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.point.PointGoodsOrgDao;
import com.baizhitong.resource.model.point.PointGoodsOrg;
import com.baizhitong.utils.RowMapperUtils;
import com.baizhitong.utils.TimeUtils;

/**
 * 
 * 可兑商品-机构数据接口实现
 * 
 * @author creator zhangqiang 2016年6月13日 下午2:44:39
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class PointGoodsOrgDaoImpl extends BaseSqlServerDao<PointGoodsOrg> implements PointGoodsOrgDao {

    /**
     * 
     * 获取机构上架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<PointGoodsOrg> selectShelvesGoodsPage(Map<String, Object> param) {
        // 从map中获取查询参数
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        Integer userRoleRequirement = MapUtils.getInteger(param, "userRoleRequirement");
        String orgCode = MapUtils.getString(param, "orgCode");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" select id,goodsThumbnailJson,validTimeStart,goodsName,points,dispOrder,userRoleRequirement ");
        sql.append(" from point_goods_org where flagDelete= 0 ");
        // 查询上架礼品时，即为查询下架时间为默认的数据
        sql.append(" and validTimeEnd = '99999999999999' ");
        // 机构编码
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and orgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        // 用户角色
        if (null != userRoleRequirement && 0 != userRoleRequirement.intValue()) {
            sql.append(" and ( userRoleRequirement = :userRoleRequirement or  userRoleRequirement=0) ");
            sqlParam.put("userRoleRequirement", userRoleRequirement);
        }
        // 根据显示顺序升序排序
        sql.append(" order by dispOrder asc ");
        // 执行SQL语句
        return super.queryDistinctPage(sql.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * 获取机构下架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<PointGoodsOrg> selectNextGoodsPage(Map<String, Object> param) {
        // 从map中获取查询参数
        Integer pageSize = MapUtils.getInteger(param, "pageSize");
        Integer pageNumber = MapUtils.getInteger(param, "pageNumber");
        Integer userRoleRequirement = MapUtils.getInteger(param, "userRoleRequirement");
        String orgCode = MapUtils.getString(param, "orgCode");
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" select id,userRoleRequirement,goodsThumbnailJson,validTimeEnd,goodsName,points ");
        sql.append(" from point_goods_org where flagDelete=0 ");
        // 查询下架礼品时，即为查询下架时间不为默认的数据
        sql.append(" and validTimeEnd != '99999999999999' ");
        // 机构编码
        if (StringUtils.isNotEmpty(orgCode)) {
            sql.append(" and orgCode = :orgCode ");
            sqlParam.put("orgCode", orgCode);
        }
        // 用户角色
        if (null != userRoleRequirement && 0 != userRoleRequirement.intValue()) {
            sql.append(" and (userRoleRequirement = :userRoleRequirement  or  userRoleRequirement=0) ");
            sqlParam.put("userRoleRequirement", userRoleRequirement);
        }
        sql.append(" order by id asc ");
        // 执行SQL语句
        return super.queryDistinctPage(sql.toString(), new RowMapperUtils(), sqlParam, pageNumber, pageSize);
    }

    /**
     * 
     * 礼品上架或者下架操作
     * 
     * @param id 主键id
     * @param type 操作类型（1：下架；2：上架）
     * @return
     */
    @Override
    public int updateValidTime(Integer id, Integer type) {
        StringBuffer sql = new StringBuffer();
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sql.append(" update point_goods_org ");
        // 下架操作
        if (1 == type.intValue()) {
            sql.append(" set validTimeEnd = :validTimeEnd ");
            sqlParam.put("validTimeEnd", Long.parseLong(TimeUtils.sysdate("yyyyMMddHHmmss")));
        }
        // 上架操作
        if (2 == type.intValue()) {
            sql.append(" set validTimeEnd ='99999999999999',validTimeStart = :validTimeStart ");
            sqlParam.put("validTimeStart", Long.parseLong(TimeUtils.sysdate("yyyyMMddHHmmss")));
        }
        sql.append(" where id = :id ");
        sqlParam.put("id", id);
        return super.update(sql.toString(), sqlParam);
    }

    /**
     * 
     * 新增或修改
     * 
     * @param entity 实体
     * @return
     */
    @Override
    public boolean add(PointGoodsOrg entity) {
        try {
            return super.saveOne(entity);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 
     * 根据主键id查询
     * 
     * @param id 主键
     * @return
     */
    @Override
    public PointGoodsOrg getById(Integer id) {
        QueryRule qr = QueryRule.getInstance();
        qr.andEqual("id", id);
        qr.andEqual("flagDelete", 0);
        return super.findUnique(qr);
    }

    /**
     * 
     * 删除机构礼品
     * 
     * @param id 主键id
     * @return
     */
    @Override
    public int delete(String id) {
        String sql = "update point_goods_org set flagDelete=1 where id=:id";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("id", id);
        return super.update(sql, sqlParam);
    }

}
