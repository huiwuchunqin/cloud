package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelQuestionTbc;

/**
 * 试题与教材章节关系表
 * 
 * @author lusm 2015/12/10
 */
public interface RelQuestionTbcDao {
    /**
     * 保存试题与教材章节关系
     * 
     * @param relQuestionTbc 对象
     * @return 是否成功
     */
    public boolean saveRelQuestionTbc(RelQuestionTbc relQuestionTbc);

    /**
     * 更新试题与教材章节关系
     * 
     * @param relQuestionTbc 对象
     * @return 是否成功
     */
    public boolean updateRelQuestionTbc(RelQuestionTbc relQuestionTbc);

    /**
     * 保存试题与教材章节关系
     * 
     * @param relQuestionTbc 对象
     * @return id
     */
    int saveRelQuestionTbcAndReturnId(RelQuestionTbc relQuestionTbc);
}