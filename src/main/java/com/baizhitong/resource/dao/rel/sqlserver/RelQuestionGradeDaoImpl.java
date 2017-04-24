package com.baizhitong.resource.dao.rel.sqlserver;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelQuestionGradeDao;
import com.baizhitong.resource.model.rel.RelQuestionGrade;

/**
 * 试题与年级关系实现
 * 
 * @author lusm 2015/12/10
 */
@Service("relQuestionGradeDao")
public class RelQuestionGradeDaoImpl extends BaseSqlServerDao<RelQuestionGrade> implements RelQuestionGradeDao {
    /**
     * 保存试题与年级关系
     * 
     * @param relQuestionGrade 对象
     * @return 是否成功
     */
    public boolean saveRelQuestionGrade(RelQuestionGrade relQuestionGrade) {
        boolean success = false;
        try {
            success = super.saveOne(relQuestionGrade);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存试题与年级关系
     * 
     * @param relQuestionGrade 对象
     * @return id
     */
    public boolean saveRelQuestionGradeAndReturnId(RelQuestionGrade relQuestionGrade) {
        boolean success = false;
        try {
            success = super.saveOne(relQuestionGrade);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 更新试题与年级关系
     * 
     * @param relQuestionGrade 对象
     * @return 是否成功
     */
    public boolean updateRelQuestionGrade(RelQuestionGrade relQuestionGrade) {
        int count = 0;
        try {
            count = super.update(relQuestionGrade);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return count > 0;
    }

}
