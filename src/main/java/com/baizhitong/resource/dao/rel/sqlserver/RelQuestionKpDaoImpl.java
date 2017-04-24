package com.baizhitong.resource.dao.rel.sqlserver;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelQuestionKpDao;
import com.baizhitong.resource.model.rel.RelQuestionKp;

/**
 * 试题与知识点接口实现
 * 
 * @author lusm 2015/12/10
 *
 */
@Service("relQuestionKpDao")
public class RelQuestionKpDaoImpl extends BaseSqlServerDao<RelQuestionKp> implements RelQuestionKpDao {
    /**
     * 保存试题与知识点关系
     * 
     * @param relQuestionKp 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelQuestionKp(RelQuestionKp relQuestionKp) {
        boolean success = false;
        try {
            success = super.saveOne(relQuestionKp);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 更新试题与知识点关系
     * 
     * @param relQuestionKp 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelQuestionKp(RelQuestionKp relQuestionKp) {

        int count = 0;
        try {
            count = super.update(relQuestionKp);
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
