package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelQuestionKeyword;

/**
 * 题目与关键词接口
 * 
 * @author lusm 2015/12/10
 *
 */
public interface RelQuestionKeywordDao {
    /**
     * 保存题目与关键字关系
     * 
     * @param relQuestionKeyword 对象
     * @return 是否成功
     */
    public boolean saveRelQuestionKeyword(RelQuestionKeyword relQuestionKeyword);

    /**
     * 更新题目与关键字关系
     * 
     * @param relQuestionKeyword 对象
     * @return 是否成功
     */
    public boolean updateRelQuestionKeyword(RelQuestionKeyword relQuestionKeyword);
}
