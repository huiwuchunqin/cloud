package com.baizhitong.resource.dao.point;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointRankOrg;

/**
 * 机构积分头衔等级 PointRankOrgDao TODO
 * 
 * @author creator gaow 2016年6月24日 上午11:30:38
 * @author updater
 *
 * @version 1.0.0
 */
public interface PointRankOrgDao {
    /**
     * 查询积分等级列表 ()<br>
     * 
     * @param map
     * @param rows
     * @param page
     * @param rows
     * @return
     */
    Page getPage(Map<String, Object> map, Integer rows, Integer page);

    /**
     * 新增积分头衔等级 ()<br>
     * 
     * @param pointRank
     * @return
     */
    boolean add(PointRankOrg pointRankOrg);

    /**
     * 查询最大头衔编码 ()<br>
     * 
     * @return
     */
    public String getMaxCode(String orgCode);

    /**
     * 查询积分头衔 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRankOrg getById(Integer id);

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public int delete(Integer id);

    /**
     * 删除机构积分 ()<br>
     * 
     * @param rankCode
     * @return
     */
    public int deleteOrgRank(String rankCode);
    /**
     * 机构积角色积分规则数
     * ()<br>
     * @param orgCode
     * @param userRole
     * @return
     */
    public long orgRankCount(String orgCode,Integer userRole);
    /**
     * 
     * (根据机构编码、所需积分查询机构积分头衔信息)<br>
     * 
     * @param orgCode 机构编码
     * @param totalPoint 所需累计积分
     * @param userRole 用户身份
     * @author ZhangQiang
     * @return 机构积分头衔信息
     */
    public PointRankOrg selectByOrgCodeAndTotalPoint(String orgCode, Integer totalPoint, Integer userRole);
}
