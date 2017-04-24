package com.baizhitong.resource.dao.share.sqlserver;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ShareCodeDifficultyDao;
import com.baizhitong.resource.model.share.ShareCodeDifficulty;

@Service
public class ShareCodeDifficultyDaoImpl extends BaseSqlServerDao<ShareCodeDifficulty>
                implements ShareCodeDifficultyDao {

    /**
     * 查询题目难易度列表 ()<br>
     * 
     * @return
     */
    public List<ShareCodeDifficulty> getDifficultyList() {
        return super.getAll();
    }

}
