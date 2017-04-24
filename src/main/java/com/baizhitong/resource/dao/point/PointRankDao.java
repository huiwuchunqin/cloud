package com.baizhitong.resource.dao.point;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.point.PointRank;

/**
 * 积分头衔等级 PointRankDao TODO
 * 
 * @author creator gaow 2016年6月24日 上午11:30:26
 * @author updater
 *
 * @version 1.0.0
 */
public interface PointRankDao {
    /**
     * 查询积分头衔等级列表 ()<br>
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
    boolean add(PointRank pointRank);
    /**
     * 角色积分规则数
     * ()<br>
     * @return
     */
    public long rankCount(Integer userRole);
    /**
     * 查询最大头衔编码 ()<br>
     * 
     * @return
     */
    public String getMaxCode();

    /**
     * 插入机构积分头衔等级 ()<br>
     * 
     * @param pointRank
     * @return
     */
    public int addOrgPointRank(PointRank pointRank);

    /**
     * 修改机构头衔积分等级信息 ()<br>
     * 
     * @param pointRank
     * @return
     */
    public int updateOrgPointRank(PointRank pointRank);

    /**
     * 查询积分头衔等级 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRank getById(Integer id);

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public int delete(Integer id);

    /**
     * 查找积分相同的头像 ()<br>
     * 
     * @param totalPoint
     * @return
     */
    public List<PointRank> getSamePointList(Integer totalPoint, Integer userRole);

}
