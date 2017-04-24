package com.baizhitong.resource.dao.res;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResExercise;
import com.baizhitong.resource.model.res.ResMedia;

/**
 * 资源_练习数据接口
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
public interface ResExerciseDao {
    /**
     * 获取练习分页集合
     * 
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param versionCode 版本编码
     * @param tbcCode 教材章节编码
     * @param exerciseName 练习名称
     * @param resTypeL2 资源分类(二级)
     * @param sort 排序方式(null:默认,10:最热,20:最新)
     * @param pageNo 当前页数
     * @param pageSize 每页条数
     * @return 练习分页集合
     * @throws Exception 异常
     */
    public Page<Map<String, Object>> selectExercisePage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String exerciseName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception;

    /**
     * 获取练习信息
     * 
     * @param exerciseId 练习ID
     * @return 信息
     * @throws Exception 异常
     */
    public Map<String, Object> selectExerciseInfo(Integer exerciseId) throws Exception;

    /**
     * 根据resID获取资源
     * 
     * @param resID 资源id
     * @return 返回资源对象
     */
    public ResExercise selectResExercise(Integer resID);

    /**
     * 更新资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    public boolean updateExerciseInfo(ResExercise resExercise);

    /**
     * 保存资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    public boolean saveExerciseInfo(ResExercise resExercise);
}
