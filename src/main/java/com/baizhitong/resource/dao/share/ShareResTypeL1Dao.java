package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareResTypeL1;

/**
 * 资源一级分类数据接口
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public interface ShareResTypeL1Dao {
    /**
     * 获取资源一级分类集合
     * 
     * @return 集合
     * @throws Exception 异常
     */
    public List<ShareResTypeL1> selectResTypeL1List() throws Exception;
    /************************************************** |以上已确认| **************************************************/
}
