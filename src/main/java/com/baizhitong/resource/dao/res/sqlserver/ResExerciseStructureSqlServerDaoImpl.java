package com.baizhitong.resource.dao.res.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResExerciseStructureDao;
import com.baizhitong.resource.model.res.ResExerciseStructure;

/**
 * 资源_练习结构数据SqlServer接口实现
 * 
 * @author creator shancy 2015/12/07
 * @author updater
 */
@Service("resExerciseStructureSqlServerDao")
public class ResExerciseStructureSqlServerDaoImpl extends BaseSqlServerDao<ResExerciseStructure>
                implements ResExerciseStructureDao {
    /**
     * 根据练习ID获取练习结构集合
     * 
     * @param exerciseId 练习ID
     * @return 练习结构集合
     */
    @Override
    public List<Map<String, Object>> selectExerciseStructureList(Integer exerciseId) {
        return null;
    }

    /**
     * 根据练习ID获取该练习的练习结构及该练习结构试题数量集合
     * 
     * @param exerciseId 练习ID
     * @return 该练习结构及该练习结构试题数量集合
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectExerciseQuestionStructureGroupCountList(Integer exerciseId)
                    throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT req.exerciseId, req.exerciseStructureId, rqt.typeCode, rqt.typeName, COUNT(req.questionId) AS questionCount ");
        sql.append(" FROM res_exercise_questions req ");
        sql.append(" INNER JOIN res_exercise_structure res ON res.id = req.exerciseStructureId ");
        sql.append(" INNER JOIN res_question_type_subject rqts ON rqts.id = res.questionTypeSubjectId ");
        sql.append(" INNER JOIN res_question_type rqt ON rqt.typeCode = rqts.code ");
        sql.append(" WHERE req.exerciseId = :exerciseId ");
        sql.append(" GROUP BY rqt.typeCode ");
        // sql.append(" ORDER BY rqt.typeCode ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", exerciseId);

        // SQL执行
        return super.findBySql(sql.toString(), map);
    }

    /**
     * 根据练习ID和基础题型编码获取练习结构
     * 
     * @param exerciseId 练习ID
     * @param typeCode 基础题型编码
     * @return 练习结构
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectExerciseStructureMap(Integer exerciseId, String typeCode) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT res.exerciseId, res.id AS exerciseStructureId, res.typeNum, rqt.typeCode, rqt.typeName ");
        sql.append(" FROM res_exercise_structure res ");
        sql.append(" INNER JOIN res_question_type_subject rqts ON rqts.id = res.questionTypeSubjectId ");
        sql.append(" INNER JOIN res_question_type rqt ON rqt.typeCode = rqts.code ");
        sql.append(" WHERE res.exerciseId = :exerciseId AND rqt.typeCode = :typeCode ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", exerciseId);
        map.put("typeCode", typeCode);

        // SQL执行
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 创建练习结构
     * 
     * @param resExerciseStructure 练习结构对象
     * @return 练习结构ID
     * @throws Exception 异常
     */
    public Integer saveExerciseStructure(ResExerciseStructure resExerciseStructure) throws Exception {
        return MapUtils.getInteger(super.saveAndReturnId(resExerciseStructure), "id");
    }

    /**
     * 更新练习结构
     * 
     * @param resExerciseStructure
     * @return 更新行数
     * @throws Exception 异常
     */
    @Override
    public int updateExerciseStructure(ResExerciseStructure resExerciseStructure) throws Exception {
        return super.update(resExerciseStructure);
    }
}