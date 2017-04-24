package com.baizhitong.resource.dao.lesson;

import com.baizhitong.resource.model.lesson.LessonShare;

/**
 * 
 * 课时共享DAO
 * 
 * @author creator ZhangQiang 2017年4月19日 下午4:32:31
 * @author updater
 *
 * @version 1.0.0
 */
public interface LessonShareDao {
    
    /**
     * 
     * (新增一条记录)<br>
     * @param entity 实体
     * @return
     */
    boolean insert(LessonShare entity);
    
}
