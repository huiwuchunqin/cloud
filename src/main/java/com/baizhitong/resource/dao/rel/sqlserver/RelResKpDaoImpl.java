package com.baizhitong.resource.dao.rel.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelResKpDao;
import com.baizhitong.resource.model.rel.RelResKp;

/**
 * 资源与知识点关系实现
 * 
 * @author lusm 2015/12/10
 */
@Service("relResKpDao")
public class RelResKpDaoImpl extends BaseSqlServerDao<RelResKp> implements RelResKpDao {
    /**
     * 保存资源与知识点关系
     * 
     * @param relResKp 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelResKp(RelResKp relResKp) {
        boolean success = false;
        try {
            success = super.saveOne(relResKp);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存资源与知识点关系
     * 
     * @param relResKp 对象
     * @return 资源ID
     */
    @Override
    public int saveRelResKpAndReturnId(RelResKp relResKp) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relResKp), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新资源与知识点关系
     * 
     * @param relResKp 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelResKp(RelResKp relResKp) {
        int count = 0;
        try {
            count = super.update(relResKp);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 删除资源知识点关系
     * 
     * @param resId 资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 下午12:01:41
     */
    public int delRelResKp(Integer resId) {
        String sql = "delete from rel_res_kp where resId=:resId";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", resId);
        return super.update(sql, param);
    }

    /**
     * 保存资源知识点关系列表
     * 
     * @param list
     * @return
     */
    @Override
    public int saveRelResKpList(List<RelResKp> list) throws InvocationTargetException, IllegalAccessException {
        return super.saveAll(list);
    }

    /**
     * 删除资源与知识点关系
     * 
     * @param id 资源id
     * @param kpCodeList 知识点集合
     */
    @Override
    public void deleteRelResKpByParam(Integer id, List<String> kpCodeList) {
        StringBuffer sql = new StringBuffer("");
        sql.append("        DELETE");
        sql.append("                FROM");
        sql.append("        rel_res_kp");
        sql.append("                WHERE");
        sql.append("        resId = :resId");

        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("resId", id);
        if (kpCodeList != null) {
            sql.append("        AND kpCode IN (:kpCodeList)");
            mp.put("kpCodeList", StringUtils.join(kpCodeList.toArray(), ","));

        }

        super.update(sql.toString(), mp);
    }

    /**
     * 获取资源与知识点关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与知识点关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResKpInfo(Integer resTypeL1, Integer resId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT skpt.code AS knowledgePointCode, skpt.name AS knowledgePointName ");
        sql.append(" FROM rel_res_kp rrk ");
        sql.append(" LEFT JOIN share_knowledge_point_textbook skpt ON skpt.code = rrk.kpCode ");
        sql.append(" WHERE 1=1 ");

        // 判断资源分类（一级）是否为空
        if (resTypeL1 != null) {
            sql.append(" AND rrk.resTypeL1 = :resTypeL1 ");
        }
        // 判断资源ID是否为空
        if (resId != null) {
            sql.append(" AND rrk.resId = :resId ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resTypeL1", resTypeL1);
        map.put("resId", resId);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 根据资源ID和知识点编码获取资源与知识点关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @param kpCode 知识点编码
     * @return 资源与知识点关系信息
     */
    @Override
    public Map<String, Object> selectRelResKpMap(Integer resTypeL1, Integer resId, String kpCode) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * ");
        sql.append(" FROM rel_res_kp rrk ");
        sql.append(" WHERE rrk.resTypeL1 = :resTypeL1 AND rrk.resId = :resId AND rrk.kpCode = :kpCode ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resTypeL1", resTypeL1);
        map.put("resId", resId);
        map.put("kpCode", kpCode);

        // SQL执行
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 删除资源和知识点关系 ()<br>
     * 
     * @param id
     * @param kpCode
     */
    public void deleteRelResKpByParam(Integer id, String kpCode) {
        StringBuffer sql = new StringBuffer("");
        sql.append("        DELETE");
        sql.append("                FROM");
        sql.append("        rel_res_kp");
        sql.append("                WHERE");
        sql.append("        resId = :resId");

        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("resId", id);
        if (StringUtils.isNotEmpty(kpCode)) {
            sql.append("        AND kpCode=:kpCode");
            mp.put("kpCode", kpCode);

        }

        super.update(sql.toString(), mp);

    }
}