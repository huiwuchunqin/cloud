package com.baizhitong.resource.dao.lesson;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.lesson.Lesson;

/**
 * 
 * 课时DAO
 * 
 * @author creator ZhangQiang 2016年8月23日 下午4:35:14
 * @author updater
 *
 * @version 1.0.0
 */
public interface LessonDao {

    /**
     * 
     * (查询课时审核全部信息)<br>
     * 
     * @param param
     * @return
     */
    Page<Map<String, Object>> selectPageAll(Map<String, Object> param, String sectionCodes, String subjectCodes,
                    String gradeCodes);

    /**
     * 
     * (根据审核状态，分页查询课时审核信息)<br>
     * 
     * @param param
     * @return
     */
    Page<Map<String, Object>> selectPageCheck(Map<String, Object> param, String sectionCodes, String subjectCodes,
                    String gradeCodes);

    /**
     * 
     * (课时审核)<br>
     * 
     * @param lessonCode 资源编码
     * @param shareCheckLevel 共享审核级别
     * @param modifier 更新者姓名
     * @param modifierIP 更新者IP
     * @param shareCheckStatus 共享审核状态
     * @return
     */
    int updateShareCheckStatus(String lessonCode, Integer shareCheckLevel, String modifier, String modifierIP,
                    Integer shareCheckStatus);

    /**
     * 
     * (批量操作课时资源)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    int updateFlagDeleteBatch(String ids, Integer operateType);

    /**
     * 
     * (根据课时编码查询课时信息)<br>
     * 
     * @param lessonCode 课时编码
     * @return 课时信息
     */
    Lesson selectByLessonCode(String lessonCode);
    
    /**
     * 
     * (查询可以设置在首页显示的课程信息)<br>
     * @param param 查询参数
     * @param page 当前页码
     * @param rows 每页大小
     * @return 课程信息列表
     */
    Page<Map<String, Object>> selectLessonHomeSettingPage(Map<String, Object> param, Integer page, Integer rows);
    
}
