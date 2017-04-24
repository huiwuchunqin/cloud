package com.baizhitong.resource.dao.res;

import java.util.List;
import java.util.Map;

import com.baizhitong.resource.model.res.ResExerciseStructure;

/**
 * 资源_练习结构数据接口
 * 
 * @author creator shancy 2015/12/07
 * @author updater
 */
public interface ResExerciseStructureDao {
    /**
     * 根据练习ID获取练习结构集合
     * 
     * @param exerciseId 练习ID
     * @return 练习结构集合
     */
    public List<Map<String, Object>> selectExerciseStructureList(Integer exerciseId);

    /**
     * 根据练习ID获取该练习的练习结构及该练习结构试题数量集合
     * 
     * @param exerciseId 练习ID
     * @return 该练习结构及该练习结构试题数量集合
     * @throws Exception 异常
     */
    public List<Map<String, Object>> selectExerciseQuestionStructureGroupCountList(Integer exerciseId) throws Exception;

    /**
     * 根据练习ID和基础题型编码获取练习结构
     * 
     * @param exerciseId 练习ID
     * @param typeCode 基础题型编码
     * @return 练习结构
     * @throws Exception 异常
     */
    public Map<String, Object> selectExerciseStructureMap(Integer exerciseId, String typeCode) throws Exception;

    /**
     * 创建练习结构
     * 
     * @param resExerciseStructure 练习结构对象
     * @return 练习结构ID
     * @throws Exception 异常
     */
    public Integer saveExerciseStructure(ResExerciseStructure resExerciseStructure) throws Exception;

    /**
     * 更新练习结构
     * 
     * @param resExerciseStructure
     * @return 更新行数
     * @throws Exception 异常
     */
    public int updateExerciseStructure(ResExerciseStructure resExerciseStructure) throws Exception;
}
