package com.baizhitong.resource.dao.rel.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelResSectionDao;
import com.baizhitong.resource.model.rel.RelResSection;

/**
 * Created by bzt-00 on 2015/12/19.
 */
@Service("relResSectionDao")
public class RelResSectionDaoImpl extends BaseSqlServerDao<RelResSection> implements RelResSectionDao {

    /**
     * 保存资源与学段关系liberty
     * 
     * @param list
     * @return
     */
    @Override
    public int saveRelResSectionList(List<RelResSection> list)
                    throws InvocationTargetException, IllegalAccessException {
        return super.saveAll(list);
    }

    /**
     * 删除资源与学段关系列表
     * 
     * @param id 资源id
     * @param sectionCodeList 学段code集合
     */
    @Override
    public void deleteRelResSectionByParam(Integer id, List<String> sectionCodeList) {
        StringBuffer sql = new StringBuffer("");
        sql.append("        DELETE");
        sql.append("                FROM");
        sql.append("        rel_res_section");
        sql.append("                WHERE");
        sql.append("        resId = :resId");

        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("resId", id);

        if (sectionCodeList != null) {
            sql.append("        AND sectionCode IN (:sectionCodes)");
            mp.put("sectionCodes", StringUtils.join(sectionCodeList.toArray(), ","));
        }

        super.update(sql.toString(), mp);
    }

    /**
     * 获取资源与学段关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与学段关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResSectionInfo(Integer resTypeL1, Integer resId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT scs.code AS sectionCode, scs.name AS sectionName ");
        sql.append(" FROM rel_res_section rrs ");
        sql.append(" LEFT JOIN share_code_section scs ON scs.code = rrs.sectionCode ");
        sql.append(" WHERE 1=1 ");

        // 判断资源分类（一级）是否为空
        if (resTypeL1 != null) {
            sql.append(" AND rrs.resTypeL1 = :resTypeL1 ");
        }
        // 判断资源ID是否为空
        if (resId != null) {
            sql.append(" AND rrs.resId = :resId ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resTypeL1", resTypeL1);
        map.put("resId", resId);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 保存学段资源关系
     * 
     * @param section
     * @return
     * @date:2015年12月23日 上午10:15:14
     */
    public boolean saveRelResSection(RelResSection section) {
        boolean success = false;
        try {
            return super.saveOne(section);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            success = false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    /**
     * 删除资源学科关系
     * 
     * @param resId 资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 上午11:44:39
     */
    public int delResResSection(Integer resId) {
        String sql = "delete from rel_res_section where resId=:resId";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", resId);
        return super.update(sql, param);
    }
}
