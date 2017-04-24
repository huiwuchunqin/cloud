package com.baizhitong.resource.dao.rel.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelResGradeDao;
import com.baizhitong.resource.model.rel.RelResGrade;

/**
 * 资源与年级关系实现
 * 
 * @author lusm 2015/12/10
 */
@Service("relResGradeDao")
public class RelResGradeDaoImpl extends BaseSqlServerDao<RelResGrade> implements RelResGradeDao {
    /**
     * 保存资源与年级关系
     * 
     * @param relResGrade 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelResGrade(RelResGrade relResGrade) {
        boolean success = false;
        try {
            success = super.saveOne(relResGrade);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存资源与年级关系
     * 
     * @param relResGrade 对象
     * @return 资源ID
     */
    @Override
    public int saveRelResGradeAndReturnId(RelResGrade relResGrade) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relResGrade), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新资源与年级关系
     * 
     * @param relResGrade 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelResGrade(RelResGrade relResGrade) {
        int count = 0;
        try {
            count = super.update(relResGrade);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 删除资源年级关系
     * 
     * @param resId
     * @return
     * @author gaow
     * @date:2015年12月19日 下午12:11:27
     */
    public int delResResGrade(Integer resId) {
        String sql = "delete from rel_res_grade where resId=:resId";
        Map sqlParam = new HashMap();
        sqlParam.put("resId", resId);
        return super.update(sql, sqlParam);

    }

    /**
     * 保存资源与年级关系
     * 
     * @param list
     * @return
     */
    @Override
    public int saveRelResGradeList(List<RelResGrade> list) throws InvocationTargetException, IllegalAccessException {
        return super.saveAll(list);
    }

    /**
     * 删除资源与年级关系列表
     * 
     * @param id 资源id
     * @param gradeCodeList 年级集合
     */
    @Override
    public void deleteRelResGradeByParam(Integer id, List<String> gradeCodeList) {
        StringBuffer sql = new StringBuffer("");
        sql.append("        DELETE");
        sql.append("                FROM");
        sql.append("        rel_res_grade");
        sql.append("                WHERE");
        sql.append("        resId = :resId");

        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("resId", id);
        if (gradeCodeList != null) {
            sql.append("        AND gradeCode IN (:gradeCodes)");
            mp.put("gradeCodes", StringUtils.join(gradeCodeList.toArray(), ","));

        }

        super.update(sql.toString(), mp);
    }

    /**
     * 获取资源与年级关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与年级关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResGradeInfo(Integer resTypeL1, Integer resId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT scg.code AS gradeCode, scg.name AS gradeName ");
        sql.append(" FROM rel_res_grade rrg ");
        sql.append(" LEFT JOIN share_code_grade scg ON scg.code = rrg.gradeCode ");
        sql.append(" WHERE 1=1 ");

        // 判断资源分类（一级）是否为空
        if (resTypeL1 != null) {
            sql.append(" AND rrg.resTypeL1 = :resTypeL1 ");
        }
        // 判断资源ID是否为空
        if (resId != null) {
            sql.append(" AND rrg.resId = :resId ");
        }

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resTypeL1", resTypeL1);
        map.put("resId", resId);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }
}