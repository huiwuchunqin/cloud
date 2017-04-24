package com.baizhitong.resource.dao.lesson.sqlserver;

import org.springframework.stereotype.Component;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.lesson.LessonShareDao;
import com.baizhitong.resource.model.lesson.LessonShare;

/**
 * 
 * 课时共享DAO接口实现类
 * 
 * @author creator ZhangQiang 2017年4月19日 下午4:40:04
 * @author updater
 *
 * @version 1.0.0
 */
@Component
public class LessonShareDaoImpl extends BaseSqlServerDao<LessonShare> implements LessonShareDao{

    /**
     * 
     * (新增一条记录)<br>
     * @param entity 实体
     * @return
     */
    @Override
    public boolean insert(LessonShare entity) {
        try {
            return super.saveOne(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
    }
    
}
