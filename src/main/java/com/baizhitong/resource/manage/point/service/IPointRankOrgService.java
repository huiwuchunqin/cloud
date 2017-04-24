package com.baizhitong.resource.manage.point.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.point.PointRankOrg;

/**
 * 机构积分头衔接口 IPointRankOrgService TODO
 * 
 * @author creator gaow 2016年6月24日 上午11:46:52
 * @author updater
 *
 * @version 1.0.0
 */
public interface IPointRankOrgService {
    /**
     * 查询积分头衔等级 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    Page getPageList(Map<String, Object> param, Integer page, Integer rows);

    /**
     * 新增头衔等级 ()<br>
     * 
     * @param rank
     * @return
     */
    ResultCodeVo add(PointRankOrg rank);

    /**
     * 根据id查询 ()<br>
     * 
     * @param id
     * @return
     */
    PointRankOrg getById(Integer id);

    /**
     * 删除头衔等级 ()<br>
     * 
     * @param id
     * @return
     */
    ResultCodeVo delete(Integer id);
}
