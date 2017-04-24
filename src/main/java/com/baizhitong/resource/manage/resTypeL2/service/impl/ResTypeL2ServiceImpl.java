package com.baizhitong.resource.manage.resTypeL2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.share.ShareResTypeL2Dao;
import com.baizhitong.resource.manage.resTypeL2.service.IResTypeL2Service;
import com.baizhitong.resource.model.share.ShareResTypeL2;

/**
 * 二级分类接口 ResTypeL2ServiceImpl TODO
 * 
 * @author creator BZT 2016年9月27日 下午3:27:14
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class ResTypeL2ServiceImpl extends BaseService implements IResTypeL2Service {

    @Autowired
    ShareResTypeL2Dao shareResTypeL2Dao;

    /**
     * 查询二级分类列表 ()<br>
     * 
     * @param page
     * @param rows
     * @param resTypeL1Code
     * @return
     */
    public Page getResTypeL2List(Integer page, Integer rows, String resTypeL1Code) {
        return shareResTypeL2Dao.pageList(page, rows, resTypeL1Code);
    }

    /**
     * 更新二级分类名称 ()<br>
     * 
     * @param newName
     * @param code
     * @param modfifier
     * @param modifyIp
     * @return
     */
    public ResultCodeVo update(String newName, String description, String code, String modifyIp) {
        int count = shareResTypeL2Dao.update(newName, description, code, modifyIp);
        if (count > 0)
            return ResultCodeVo.rightCode("更新成功");
        return ResultCodeVo.errorCode("更新失败");
    }

    /**
     * 新增二级分类 ()<br>
     * 
     * @param resTypeL2
     */
    public ResultCodeVo add(ShareResTypeL2 resTypeL2) {
        try {
            shareResTypeL2Dao.add(resTypeL2);
            return ResultCodeVo.rightCode("新增成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultCodeVo.errorCode("新增失败");
        }
    }

    /**
     * 查询二级分类 ()<br>
     * 
     * @param code
     * @return
     */
    public ShareResTypeL2 getByCode(String code) {
        try {
            return shareResTypeL2Dao.selectByCode(code);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

}
