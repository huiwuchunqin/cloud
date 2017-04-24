package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResFavorite;

/**
 * 资源收藏接口
 * 
 * @author creator lushunming 2015/12/08
 * @author updater
 */
public interface ResFavoriteDao {

    /**
     * 保存资源收藏
     * 
     * @param resFavorite 资源收藏对象
     * @return 是否成功
     */
    public boolean saveResFavorite(ResFavorite resFavorite);

    /**
     * 获取收藏资源
     * 
     * @param folderId 文件夹id
     * @param pageNo 页号
     * @return 资源page
     */
    public Page<Map<String, Object>> selectFavoritePage(Integer folderId, Integer pageNo, Integer pageSize);

    /**
     * 删除指定文件夹下的收藏资源
     * 
     * @param folderId 文件夹id
     * @return 删除条数
     */
    public int deleteFavorites(Integer folderId);

    /**
     * 获取文件夹下的所有文件
     * 
     * @param folderId 文件夹id
     * @return 删除条数
     */
    public List<ResFavorite> selectFavorites(Integer folderId);

    /**
     * 删除收藏list
     * 
     * @param favoriteList 列表
     * @return 删除条数
     */
    public int deleteFavoriteList(List<ResFavorite> favoriteList);

    /**
     * 删除指定的收藏资源
     * 
     * @param resFavorite 收藏对象
     * @return 是否成功
     */

    public boolean deleteFavorite(ResFavorite resFavorite);

    /**
     * 查找指定的收藏资源
     * 
     * @param folderId 文件夹id
     * @param resId 资源id
     * @param resTypeL1 资源类型
     */

    public ResFavorite selectFavorite(Integer folderId, Integer resId, Integer resTypeL1);

    /**
     * 根据用户userCode，资源id，资源类型获取这条资源收藏的条数
     * 
     * @param userCode 用户code
     * @param resID 资源id
     * @param resTypeL1 资源类型
     * @return 查询的条数
     */
    public int getFavoriteCount(String userCode, Integer resID, Integer resTypeL1);

    /**
     * 根据id获取收藏的资源
     * 
     * @param favoriteId 收藏的id
     * @return 返回对象
     */
    public ResFavorite selectFavoriteById(Integer favoriteId);

}
