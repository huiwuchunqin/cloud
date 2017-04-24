package com.baizhitong.resource.manage.resTypeL2.service;

import java.util.List;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareResTypeL2;

public interface IResTypeL2Service {
    /**
     * 查询二级分类列表 ()<br>
     * 
     * @param page
     * @param rows
     * @param resTypeL1Code
     * @return
     */
    Page getResTypeL2List(Integer page, Integer rows, String resTypeL1Code);

    /**
     * 更新二级分类名称 ()<br>
     * 
     * @param newName
     * @param code
     * @param modfifier
     * @param modifyIp
     * @return
     */
    ResultCodeVo update(String newName, String description, String code, String modifyIp);

    /**
     * 新增二级分类 ()<br>
     * 
     * @param resTypeL2
     */
    ResultCodeVo add(ShareResTypeL2 resTypeL2);

    /**
     * 查询二级分类 ()<br>
     * 
     * @param code
     * @return
     */
    ShareResTypeL2 getByCode(String code);

}
