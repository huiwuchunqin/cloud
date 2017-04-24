package com.baizhitong.resource.dao.share;

import java.util.List;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareResTypeL2;

/**
 * 资源二级分类数据接口
 * 
 * @author creator Cuidc 2015/12/09
 * @author updater
 */
public interface ShareResTypeL2Dao {
    /**
     * 获取资源二级分类集合
     * 
     * @param codeL1 资源一级分类编码
     * @return 集合
     * @throws Exception 异常
     */
    public List<ShareResTypeL2> selectResTypeL2List(Integer codeL1) throws Exception;
    /************************************************** |以上已确认| **************************************************/

    /**
     * 
     * (根据code查询资源二级分类名称)<br>
     * 
     * @param code
     * @return
     * @throws Exception
     */
    public ShareResTypeL2 selectByCode(String code) throws Exception;

    /**
     * 更新 ()<br>
     * 
     * @param newName 资源名称
     * @param code 资源编码
     * @param modifyIp 更新ip
     * @return
     */
    public Integer update(String newName, String description, String code, String modifyIp);

    /**
     * 新增二级分类 ()<br>
     * 
     * @param resTypeL2
     */
    public void add(ShareResTypeL2 resTypeL2);

    /**
     * 查询二级分类列表 ()<br>
     * 
     * @param page
     * @param rows
     * @param resTypeL1Code
     * @return
     */
    public Page pageList(Integer page, Integer rows, String resTypeL1Code);
}
