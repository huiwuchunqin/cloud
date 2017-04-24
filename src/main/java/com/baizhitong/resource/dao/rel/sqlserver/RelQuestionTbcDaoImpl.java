package com.baizhitong.resource.dao.rel.sqlserver;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelQuestionTbcDao;
import com.baizhitong.resource.model.rel.RelQuestionTbc;

/**
 * 试题与教材章节关系实现
 * 
 * @author lusm 2015/12/10
 */
@Service("relQuestionTbcDao")
public class RelQuestionTbcDaoImpl extends BaseSqlServerDao<RelQuestionTbc> implements RelQuestionTbcDao {
    /**
     * 保存试题与教材章节关系
     * 
     * @param relQuestionTbc 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelQuestionTbc(RelQuestionTbc relQuestionTbc) {
        boolean success = false;
        try {
            success = super.saveOne(relQuestionTbc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存试题与教材章节关系
     * 
     * @param relQuestionTbc 对象
     * @return id
     */
    @Override
    public int saveRelQuestionTbcAndReturnId(RelQuestionTbc relQuestionTbc) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relQuestionTbc), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新试题与教材章节关系
     * 
     * @param relQuestionTbc 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelQuestionTbc(RelQuestionTbc relQuestionTbc) {
        int count = 0;
        try {
            count = super.update(relQuestionTbc);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}