package com.baizhitong.resource.dao.res.sqlserver;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResExerciseQuestionsDao;
import com.baizhitong.resource.model.res.ResExerciseQuestions;

/**
 * 资源_练习结构数据SqlServer接口实现
 * 
 * @author creator shancy 2015/12/07
 * @author updater
 */
@Service("resExerciseQuestionsSqlServerDao")
public class ResExerciseQuestionsSqlServerDaoImpl extends BaseSqlServerDao<ResExerciseQuestions>
                implements ResExerciseQuestionsDao {
    /**
     * 根据练习ID、练习结构ID获取该练习的练习试题
     * 
     * @param exerciseId 练习ID
     * @param exerciseStructureId 练习结构ID
     * @return 该练习的练习试题
     * @throws Exception 异常
     */
    @Override
    public List<Map<String, Object>> selectExerciseQuestionList(Integer exerciseId, Integer exerciseStructureId)
                    throws Exception {
        return null;
    }

    /**
     * 根据练习ID、试题ID获取练习试题
     * 
     * @param exerciseId 练习ID
     * @param questionId 试题ID
     * @return 练习试题
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectExerciseQuestionMap(Integer exerciseId, Integer questionId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT * ");
        sql.append(" FROM res_exercise_questions req ");
        sql.append(" WHERE req.exerciseId = :exerciseId AND req.questionId = :questionId ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", exerciseId);
        map.put("questionId", questionId);

        // SQL执行
        return super.findUniqueBySql(sql.toString(), map);
    }

    /**
     * 创建练习试题
     * 
     * @param resTestpaper 练习试题对象
     * @return 练习试题ID
     * @throws Exception 异常
     */
    @Override
    public Integer saveExerciseQuestions(ResExerciseQuestions resExerciseQuestions) throws Exception {
        return MapUtils.getInteger(super.saveAndReturnId(resExerciseQuestions), "id");
    }

    /**
     * 更新练习试题
     * 
     * @param resExerciseQuestions 练习试题对象
     * @return 更新行数
     * @throws Exception 异常
     */
    public int updateExerciseStructure(ResExerciseQuestions resExerciseQuestions) throws Exception {
        return super.update(resExerciseQuestions);
    }

    /**
     * 根据练习ID、试题ID删除试卷试题
     * 
     * @param exerciseId 练习ID
     * @param questionId 试题ID
     * @throws Exception 异常
     */
    @Override
    public void deleteExerciseQuestions(Integer exerciseId, Integer questionId) throws Exception {
        // SQL语句
        StringBuffer sql = new StringBuffer();
        sql.append(" DELETE FROM res_exercise_questions ");
        sql.append(" WHERE exerciseId = :exerciseId AND questionid = :questionId ");

        // SQL参数
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("exerciseId", exerciseId);
        map.put("questionId", questionId);

        // SQL执行
        super.update(sql.toString(), map);
    }
}