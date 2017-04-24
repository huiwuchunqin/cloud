package com.baizhitong.resource.dao.share.sqlserver;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareQuestionTypeDao;
import com.baizhitong.resource.model.share.ShareQuestionType;

/**
 * 题型dao ShareQuestionTypeDaoImpl TODO
 * 
 * @author creator BZT 2016年5月11日 下午5:00:14
 * @author updater
 *
 * @version 1.0.0
 */
@Repository
public class ShareQuestionTypeDaoImpl extends BaseSqlServerDao<ShareQuestionType> implements ShareQuestionTypeDao {
    /**
     * 查询题型 ()<br>
     * 
     * @return
     */
    public List<ShareQuestionType> getAll() {
        return super.getAll();
    }
}
