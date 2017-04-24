package com.baizhitong.resource.dao.rel.sqlserver;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelQuestionSubjectDao;
import com.baizhitong.resource.model.rel.RelQuestionSubject;

/**
 * 试题与学科关系实现
 * 
 * @author lusm 2015/12/10
 */
@Service("relQuestionSubjectDao")
public class RelQuestionSubjectDaoImpl extends BaseSqlServerDao<RelQuestionSubject> implements RelQuestionSubjectDao {
    /**
     * 保存试题与学科关系
     * 
     * @param relQuestionSubject 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelQuestionSubject(RelQuestionSubject relQuestionSubject) {
        boolean success = false;
        try {
            success = super.saveOne(relQuestionSubject);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存试题与学科关系
     * 
     * @param relQuestionSubject 对象
     * @return id
     */
    @Override
    public int saveRelQuestionSubjectAndReturnId(RelQuestionSubject relQuestionSubject) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relQuestionSubject), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新试题与学科关系
     * 
     * @param relQuestionSubject 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelQuestionSubject(RelQuestionSubject relQuestionSubject) {
        int count = 0;
        try {
            count = super.update(relQuestionSubject);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}
