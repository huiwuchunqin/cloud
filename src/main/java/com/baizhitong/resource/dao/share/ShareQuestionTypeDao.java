package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareQuestionType;

/**
 * 题型接口 ShareQuestionTypeDao TODO
 * 
 * @author creator BZT 2016年5月11日 下午5:00:52
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareQuestionTypeDao {
    /**
     * 查询所有的题型 ()<br>
     * 
     * @return
     */
    List<ShareQuestionType> getAll();
}
