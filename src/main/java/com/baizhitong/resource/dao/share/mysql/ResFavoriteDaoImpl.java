package com.baizhitong.resource.dao.share.mysql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.dao.BaseMySqlDao;
import com.baizhitong.resource.dao.share.ResFavoriteDao;
import com.baizhitong.resource.model.res.ResFavorite;

/**
 * 资源评论接口
 * 
 * @author creator lushunming 2015/12/07
 * @author updater
 */
@Service("resFavoriteDao")
public class ResFavoriteDaoImpl extends BaseMySqlDao<ResFavorite, Integer> implements ResFavoriteDao {
    /**
     * 保存资源收藏
     * 
     * @param resFavorite 资源收藏对象
     * @return 是否成功
     */
    @Override
    public boolean saveResFavorite(ResFavorite resFavorite) {
        boolean success = false;
        try {
            success = super.saveOne(resFavorite);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    @Override
    public Page<Map<String, Object>> selectFavoritePage(Integer folderId, Integer pageNo, Integer pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int deleteFavorites(Integer folderId) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<ResFavorite> selectFavorites(Integer folderId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int deleteFavoriteList(List<ResFavorite> favoriteList) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean deleteFavorite(ResFavorite resFavorite) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ResFavorite selectFavorite(Integer folderId, Integer resId, Integer resTypeL1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getFavoriteCount(String userCode, Integer resID, Integer resTypeL1) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ResFavorite selectFavoriteById(Integer favoriteId) {
        // TODO Auto-generated method stub
        return null;
    }

}
