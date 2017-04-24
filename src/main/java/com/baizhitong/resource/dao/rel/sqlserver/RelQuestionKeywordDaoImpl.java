package com.baizhitong.resource.dao.rel.sqlserver;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelQuestionKeywordDao;
import com.baizhitong.resource.model.rel.RelQuestionKeyword;

/**
 * 题目与关键词实现
 * 
 * @author lusm 2015/12/10
 *
 */
@Service("relQuestionKeywordDao")
public class RelQuestionKeywordDaoImpl extends BaseSqlServerDao<RelQuestionKeyword> implements RelQuestionKeywordDao {
    /**
     * 保存题目与关键字关系
     * 
     * @param relQuestionKeyword 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelQuestionKeyword(RelQuestionKeyword relQuestionKeyword) {
        boolean success = false;
        try {
            success = super.saveOne(relQuestionKeyword);
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
     * 更新题目与关键字关系
     * 
     * @param relQuestionKeyword 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelQuestionKeyword(RelQuestionKeyword relQuestionKeyword) {
        int count = 0;
        try {
            count = super.update(relQuestionKeyword);
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
