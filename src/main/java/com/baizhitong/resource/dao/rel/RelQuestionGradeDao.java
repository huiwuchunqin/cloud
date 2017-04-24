package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelQuestionGrade;

/**
 * 试题与年级关系接口
 * 
 * @author lusm 2015/12/10
 */
public interface RelQuestionGradeDao {
    /**
     * 保存试题与年级关系
     * 
     * @param relQuestionGrade 对象
     * @return 是否成功
     */
    public boolean saveRelQuestionGrade(RelQuestionGrade relQuestionGrade);

    /**
     * 更新试题与年级关系
     * 
     * @param relQuestionGrade 对象
     * @return 是否成功
     */
    public boolean updateRelQuestionGrade(RelQuestionGrade relQuestionGrade);

}
