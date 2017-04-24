package com.baizhitong.resource.dao.rel.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelResSubjectDao;
import com.baizhitong.resource.model.rel.RelResSubject;

/**
 * 资源与学科关系实现
 * 
 * @author shancy 2015/12/10
 */
@Service("relResSubjectDao")
public class RelResSubjectDaoImpl extends BaseSqlServerDao<RelResSubject> implements RelResSubjectDao {
    /**
     * 保存资源与学科关系
     * 
     * @param relResSubject 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelResSubject(RelResSubject relResSubject) {
        boolean success = false;
        try {
            success = super.saveOne(relResSubject);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存资源与学科关系
     * 
     * @param relResSubject 对象
     * @return 资源ID
     */
    @Override
    public int saveRelResSubjectAndReturnId(RelResSubject relResSubject) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relResSubject), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新资源与学科关系
     * 
     * @param relResSubject 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelResSubject(RelResSubject relResSubject) {
        int count = 0;
        try {
            count = super.update(relResSubject);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 删除资源学科关系
     * 
     * @param resId 资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 上午11:44:39
     */
    public int delResResSubject(Integer resId) {
        String sql = "delete from rel_res_subject where resId=:resId";
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resId", resId);
        return super.update(sql, param);
    }

    /**
     * 保存资源与学科关系
     * 
     * @param list
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public int saveRelResSubjectList(List<RelResSubject> list)
                    throws InvocationTargetException, IllegalAccessException {
        return super.saveAll(list);
    }

    /**
     * 删除资源与学科关系
     * 
     * @param id 资源id
     * @param subjectCodeList 学科代码集合
     */
    @Override
    public void deleteRelResSubjectByParam(Integer id, List<String> subjectCodeList) {
        StringBuffer sql = new StringBuffer("");
        sql.append("        DELETE");
        sql.append("                FROM");
        sql.append("        rel_res_subject");
        sql.append("                WHERE");
        sql.append("        resId = :resId");

        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("resId", id);
        if (subjectCodeList != null) {
            sql.append("        AND subjectCode IN (:subjectCodes)");
            mp.put("subjectCodes", StringUtils.join(subjectCodeList.toArray(), ","));

        }

        super.update(sql.toString(), mp);
    }

    /**
     * 获取资源与学科关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与学科关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResSubjectInfo(Integer resTypeL1, Integer resId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT scs.code AS subjectCode, scs.name AS subjectName ");
        sql.append(" FROM rel_res_subject rrs ");
        sql.append(" LEFT JOIN share_code_subject scs ON scs.code = rrs.subjectCode ");
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
}