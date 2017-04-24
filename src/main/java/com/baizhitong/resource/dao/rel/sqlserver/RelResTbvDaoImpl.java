package com.baizhitong.resource.dao.rel.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelResTbvDao;
import com.baizhitong.resource.model.rel.RelResTbv;

/**
 * 资源与教材版本关系接口实现
 * 
 * @author lusm 2015/12/10
 */
@Service("relResTbvDao")
public class RelResTbvDaoImpl extends BaseSqlServerDao<RelResTbv> implements RelResTbvDao {
    /**
     * 保存资源与教材版本关系
     * 
     * @param relResTbv 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelResTbv(RelResTbv relResTbv) {
        boolean success = false;
        try {
            success = super.saveOne(relResTbv);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存资源与教材版本关系
     * 
     * @param relResTbv 对象
     * @return 资源ID
     */
    @Override
    public int saveRelResTbvAndReturnId(RelResTbv relResTbv) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relResTbv), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新资源与教材版本关系
     * 
     * @param relResTbv 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelResTbv(RelResTbv relResTbv) {
        int count = 0;
        try {
            count = super.update(relResTbv);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 删除资源教材版本关系
     * 
     * @param resId 资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 上午11:48:27
     */
    public int deleteRelResTbv(Integer resId) {
        String sql = "delete from rel_res_tbv where resId=:resId";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resId", resId);
        return super.update(sql, sqlParam);
    }

    /**
     * 保存资源教材版本关系列表
     * 
     * @param list 对象试题
     * @return
     * @author guohao
     */
    @Override
    public int saveRelResTbvList(List<RelResTbv> list) throws InvocationTargetException, IllegalAccessException {
        return super.saveAll(list);
    }

    /**
     * 删除资源与教材版本关系
     * 
     * @param id 资源id
     * @param tbvCodeList 教材版本代码集合
     */
    @Override
    public void deleteRelResTbvByParam(Integer id, List<String> tbvCodeList) {
        StringBuffer sql = new StringBuffer("");
        sql.append("        DELETE");
        sql.append("                FROM");
        sql.append("        rel_res_tbv");
        sql.append("                WHERE");
        sql.append("        resId = :resId");

        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("resId", id);

        if (tbvCodeList != null) {
            sql.append("        AND tbvCode IN (:tbvCodeList)");
            mp.put("tbvCodeList", StringUtils.join(tbvCodeList.toArray(), ","));

        }

        super.update(sql.toString(), mp);
    }

    /**
     * 获取资源与教材版本关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与教材版本关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResTbvInfo(Integer resTypeL1, Integer resId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT sctv.code AS textbookVerCode, sctv.name AS textbookVerName ");
        sql.append(" FROM rel_res_tbv rrt ");
        sql.append(" LEFT JOIN share_code_textbook_ver sctv ON sctv.code = rrt.tbvCode ");
        sql.append(" WHERE 1=1 ");

        // 判断资源分类（一级）是否为空
        if (resTypeL1 != null) {
            sql.append(" AND rrt.resTypeL1 = :resTypeL1 ");
        }
        // 判断资源ID是否为空
        if (resId != null) {
            sql.append(" AND rrt.resId = :resId ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resTypeL1", resTypeL1);
        map.put("resId", resId);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }
}