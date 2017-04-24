package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareCodeDifficulty;

public interface ShareCodeDifficultyDao {
    /**
     * 查询题目难易度列表 ()<br>
     * 
     * @return
     */
    public List<ShareCodeDifficulty> getDifficultyList();
}
