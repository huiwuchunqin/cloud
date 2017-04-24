package com.baizhitong.resource.dao.rel.sqlserver;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelQuestionTbvDao;
import com.baizhitong.resource.model.rel.RelQuestionTbv;

/**
 * 试题与教材版本关系实现
 * 
 * @author lusm 2015/12/10
 */
@Service("relQuestionTbvDao")
public class RelQuestionTbvDaoImpl extends BaseSqlServerDao<RelQuestionTbv> implements RelQuestionTbvDao {

    /**
     * 保存试题与教材版本关系
     * 
     * @param relQuestionTbv 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelQuestionTbv(RelQuestionTbv relQuestionTbv) {
        boolean success = false;
        try {
            success = super.saveOne(relQuestionTbv);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存试题与教材版本关系
     * 
     * @param relQuestionTbv 对象
     * @return id
     */
    @Override
    public int saveRelQuestionTbvAndReturnId(RelQuestionTbv relQuestionTbv) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relQuestionTbv), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新试题与教材版本关系
     * 
     * @param relQuestionTbv 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelQuestionTbv(RelQuestionTbv relQuestionTbv) {
        int count = 0;
        try {
            count = super.update(relQuestionTbv);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}