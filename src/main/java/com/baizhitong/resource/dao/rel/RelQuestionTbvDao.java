package com.baizhitong.resource.dao.rel;

import java.util.List;

import com.baizhitong.resource.model.rel.RelQuestionTbv;

/**
 * 试题与教材版本关系表
 * 
 * @author lusm 2015/12/10
 */
public interface RelQuestionTbvDao {
    /**
     * 保存试题与教材版本关系
     * 
     * @param relQuestionTbv 对象
     * @return 是否成功
     */
    public boolean saveRelQuestionTbv(RelQuestionTbv relQuestionTbv);

    /**
     * 更新试题与教材版本关系
     * 
     * @param relQuestionTbv 对象
     * @return 是否成功
     */
    public boolean updateRelQuestionTbv(RelQuestionTbv relQuestionTbv);

    /**
     * 保存试题与教材版本关系
     * 
     * @param relQuestionTbv 对象
     * @return id
     */
    int saveRelQuestionTbvAndReturnId(RelQuestionTbv relQuestionTbv);
}