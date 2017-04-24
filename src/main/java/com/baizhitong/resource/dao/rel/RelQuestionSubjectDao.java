package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelQuestionSubject;

/**
 * 试题与学科关系表
 * 
 * @author lusm 2015/12/10
 */
public interface RelQuestionSubjectDao {
    /**
     * 保存试题与学科关系
     * 
     * @param relQuestionSubject 对象
     * @return 是否成功
     */
    public boolean saveRelQuestionSubject(RelQuestionSubject relQuestionSubject);

    /**
     * 更新试题与学科关系
     * 
     * @param relQuestionSubject 对象
     * @return 是否成功
     */
    public boolean updateRelQuestionSubject(RelQuestionSubject relQuestionSubject);

    /**
     * 保存试题与学科关系
     * 
     * @param relQuestionSubject 对象
     * @return id
     */
    int saveRelQuestionSubjectAndReturnId(RelQuestionSubject relQuestionSubject);
}
