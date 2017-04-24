package com.baizhitong.resource.manage.res.service;

import java.util.List;

import com.baizhitong.resource.model.share.ShareResSpecialType;

/**
 * 
 * 特色资源分类Service
 * 
 * @author creator zhangqiang 2016年8月9日 下午4:22:16
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareResSpecialTypeService {

    /**
     * 
     * (根据第一层级查询分类列表)<br>
     * 
     * @return
     * @throws Exception
     */
    public List<ShareResSpecialType> queryListByLevel1() throws Exception;

    /**
     * 
     * (根据父编码查询子分类列表)<br>
     * 
     * @param pcode 父编码
     * @return
     * @throws Exception
     */
    public List<ShareResSpecialType> queryListByPCode(String pcode) throws Exception;
}
