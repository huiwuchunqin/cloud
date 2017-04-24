package com.baizhitong.resource.dao.res;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.res.ResExerciseQuestions;

/**
 * 资源_练习试题数据接口
 * 
 * @author creator shancy 2015/12/07
 * @author updater
 */
public interface ResExerciseQuestionsDao {
    /**
     * 根据练习ID、练习结构ID获取该练习的练习试题
     * 
     * @param exerciseId 练习ID
     * @param exerciseStructureId 练习结构ID
     * @return 该练习的练习试题
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectExerciseQuestionList(Integer exerciseId, Integer exerciseStructureId)
                    throws Exception;

    /**
     * 根据练习ID、试题ID获取练习试题
     * 
     * @param exerciseId 练习ID
     * @param questionId 试题ID
     * @return 练习试题
     * @throws Exception 异常
     */
    public Map<String, Object> selectExerciseQuestionMap(Integer exerciseId, Integer questionId) throws Exception;

    /**
     * 创建练习试题
     * 
     * @param resTestpaper 练习试题对象
     * @return 练习试题ID
     * @throws Exception 异常
     */
    public Integer saveExerciseQuestions(ResExerciseQuestions resExerciseQuestions) throws Exception;

    /**
     * 更新练习试题
     * 
     * @param resExerciseQuestions 练习试题对象
     * @return 更新行数
     * @throws Exception 异常
     */
    public int updateExerciseStructure(ResExerciseQuestions resExerciseQuestions) throws Exception;

    /**
     * 根据练习ID、试题ID删除试卷试题
     * 
     * @param exerciseId 练习ID
     * @param questionId 试题ID
     * @throws Exception 异常
     */
    public void deleteExerciseQuestions(Integer exerciseId, Integer questionId) throws Exception;
}
