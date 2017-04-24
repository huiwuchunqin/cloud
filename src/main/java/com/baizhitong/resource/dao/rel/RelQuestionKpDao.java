package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelQuestionKp;

/**
 * 试题与知识点接口
 * 
 * @author lusm 2015/12/10
 *
 */
public interface RelQuestionKpDao {
    /**
     * 保存试题与知识点关系
     * 
     * @param relQuestionKp 对象
     * @return 是否成功
     */
    public boolean saveRelQuestionKp(RelQuestionKp relQuestionKp);

    /**
     * 更新试题与知识点关系
     * 
     * @param relQuestionKp 对象
     * @return 是否成功
     */
    public boolean updateRelQuestionKp(RelQuestionKp relQuestionKp);
}
