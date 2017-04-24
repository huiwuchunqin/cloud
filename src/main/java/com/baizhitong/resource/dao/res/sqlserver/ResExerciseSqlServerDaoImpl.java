package com.baizhitong.resource.dao.res.sqlserver;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.res.ResExerciseDao;
import com.baizhitong.resource.model.res.ResExercise;

/**
 * 资源_练习数据SqlServer接口实现
 * 
 * @author creator cuidc 2015/12/03
 * @author updater
 */
@Service("resExerciseSqlServerDao")
public class ResExerciseSqlServerDaoImpl extends BaseSqlServerDao<ResExercise> implements ResExerciseDao {

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
    @Override
    public Page<Map<String, Object>> selectExercisePage(String sectionCode, String gradeCode, String subjectCode,
                    String versionCode, String tbcCode, String exerciseName, String resTypeL2, String sort,
                    Integer pageNo, Integer pageSize) throws Exception {
        return null;
    }

    /**
     * 获取练习信息
     * 
     * @param exerciseId 练习ID
     * @return 信息
     * @throws Exception 异常
     */
    @Override
    public Map<String, Object> selectExerciseInfo(Integer exerciseId) throws Exception {
        return null;
    }

    /**
     * 根据resID获取资源
     * 
     * @param resID 资源id
     * @return 返回资源对象
     */
    @Override
    public ResExercise selectResExercise(Integer resID) {
        return null;
    }

    /**
     * 更新资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    @Override
    public boolean updateExerciseInfo(ResExercise resExercise) {
        return false;
    }

    /**
     * 保存资源信息
     * 
     * @param resDoc 资源对象
     * @return 是否成功
     */
    @Override
    public boolean saveExerciseInfo(ResExercise resExercise) {
        boolean success = false;
        try {
            success = super.saveOne(resExercise);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return success;
    }
}