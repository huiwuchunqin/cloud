package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.resource.model.share.ShareResSpecialType;

/**
 * 
 * 特色资源分类DAO
 * 
 * @author creator zhangqiang 2016年8月9日 下午2:35:27
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareResSpecialTypeDao {

    /**
     * 
     * (根据当前层数为1级，查询列表数据)<br>
     * 
     * @return
     * @throws Exception
     */
    public List<ShareResSpecialType> selectListByLevel1() throws Exception;

    /**
     * 
     * (根据父节点查询子分类列表)<br>
     * 
     * @param pcode 父编码
     * @return
     * @throws Exception
     */
    public List<ShareResSpecialType> selectListByPCode(String pcode) throws Exception;

}
