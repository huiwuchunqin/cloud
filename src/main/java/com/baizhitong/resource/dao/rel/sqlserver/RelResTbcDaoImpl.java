package com.baizhitong.resource.dao.rel.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelResTbcDao;
import com.baizhitong.resource.model.rel.RelResTbc;

/**
 * 资源与教材章节实现
 * 
 * @author lusm 2015/12/10
 */
@Service("relResTbcDao")
public class RelResTbcDaoImpl extends BaseSqlServerDao<RelResTbc> implements RelResTbcDao {
    /**
     * 保存资源与教材章节关系
     * 
     * @param relResTbc 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelResTbc(RelResTbc relResTbc) {
        boolean success = false;
        try {
            success = super.saveOne(relResTbc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存资源与教材章节关系
     * 
     * @param relResTbc 对象
     * @return 资源ID
     */
    @Override
    public int saveRelResTbcAndReturnId(RelResTbc relResTbc) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relResTbc), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新资源与教材章节关系
     * 
     * @param relResTbc 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelResTbc(RelResTbc relResTbc) {
        int count = 0;
        try {
            count = super.update(relResTbc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }

    /**
     * 删除资源章节关系
     * 
     * @param resId 资源id
     * @return
     * @author gaow
     * @date:2015年12月19日 上午11:50:59
     */
    public int delRelResTbc(Integer resId) {
        String sql = "delete from rel_res_tbc where resId=:resId";
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        sqlParam.put("resId", resId);
        return super.update(sql, sqlParam);

    }

    /**
     * 保存资源与教材章节关系列表
     * 
     * @param list 对象列表
     * @return
     */
    @Override
    public int saveRelResTbcList(List<RelResTbc> list) throws InvocationTargetException, IllegalAccessException {
        return super.saveAll(list);
    }

    /**
     * 删除资源与教材章节关系
     * 
     * @param id 资源id
     * @param tbcCodeList
     */
    @Override
    public void deleteRelResTbcByParam(Integer id, List<String> tbcCodeList) {
        StringBuffer sql = new StringBuffer("");
        sql.append("        DELETE");
        sql.append("                FROM");
        sql.append("        rel_res_tbc");
        sql.append("                WHERE");
        sql.append("        resId = :resId");

        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("resId", id);

        if (tbcCodeList != null) {
            sql.append("        AND tbcCode IN (:tbcCodeList)");
            mp.put("tbcCodeList", StringUtils.join(tbcCodeList.toArray(), ","));
        }

        super.update(sql.toString(), mp);
    }

    /**
     * 获取资源与教材章节关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @return 资源与教材章节关系信息
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectRelResTbcInfo(Integer resTypeL1, Integer resId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT stc.code AS textbookChapterCode, stc.name AS textbookChapterName ");
        sql.append(" FROM rel_res_tbc rrt ");
        sql.append(" LEFT JOIN share_textbook_chapter stc ON stc.code = rrt.tbcCode ");
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

    /**
     * 根据资源ID和教材章节编码获取资源与教材章节关系信息
     * 
     * @param resTypeL1 资源分类（一级）
     * @param resId 资源ID
     * @param tbcCode 教材章节编码
     * @return 资源与教材章节关系信息
     */
    @Override
    public Map<String, Object> selectRelResTbcMap(Integer resTypeL1, Integer resId, String tbcCode) {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * ");
        sql.append(" FROM rel_res_tbc rrt ");
        sql.append(" WHERE rrt.resTypeL1 = :resTypeL1 AND rrt.resId = :resId AND rrt.tbcCode = :tbcCode ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("resTypeL1", resTypeL1);
        map.put("resId", resId);
        map.put("tbcCode", tbcCode);

        // SQL执行
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 删除资源教材章节关系 ()<br>
     * 
     * @param id
     * @param chapterCode
     */
    public void deleteRelResTbcByParam(Integer id, String chapterCode) {
        StringBuffer sql = new StringBuffer("");
        sql.append("        DELETE");
        sql.append("                FROM");
        sql.append("        rel_res_tbc");
        sql.append("                WHERE");
        sql.append("        resId = :resId");

        Map<String, Object> mp = new HashMap<String, Object>();
        mp.put("resId", id);

        if (StringUtils.isNotEmpty(chapterCode)) {
            sql.append("        AND tbcCode =:chapterCode");
            mp.put("chapterCode", chapterCode);

        }

        super.update(sql.toString(), mp);
    }

}