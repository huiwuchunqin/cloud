package com.baizhitong.resource.dao.res;

import java.util.List;

import com.baizhitong.resource.model.res.ResThumbnail;

public interface ResThumbnailDao {
    /**
     * 查询资源缩略图 ()<br>
     * 
     * @param resCode
     * @return
     */
    public List<ResThumbnail> getThumbnailList(String resCode);
}
