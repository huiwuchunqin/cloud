package com.baizhitong.resource.manage.res.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.dao.share.ShareResSpecialTypeDao;
import com.baizhitong.resource.manage.res.service.ShareResSpecialTypeService;
import com.baizhitong.resource.model.share.ShareResSpecialType;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 特色资源分类Service实现类
 * 
 * @author creator zhangqiang 2016年8月9日 下午4:25:26
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "shareResSpecialTypeService")
public class ShareResSpecialTypeServiceImpl extends BaseService implements ShareResSpecialTypeService {

    @Autowired
    private ShareResSpecialTypeDao shareResSpecialTypeDao;

    /**
     * 
     * (根据第一层级查询分类列表)<br>
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List<ShareResSpecialType> queryListByLevel1() throws Exception {
        return shareResSpecialTypeDao.selectListByLevel1();
    }

    /**
     * 
     * (根据父编码查询子分类列表)<br>
     * 
     * @param pcode 父编码
     * @return
     * @throws Exception
     */
    @Override
    public List<ShareResSpecialType> queryListByPCode(String pcode) throws Exception {
        if (StringUtils.isNotEmpty(pcode)) {
            return shareResSpecialTypeDao.selectListByPCode(pcode);
        } else {
            return new ArrayList<ShareResSpecialType>();
        }
    }

}
