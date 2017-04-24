package com.baizhitong.resource.dao.share.sqlserver;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.common.dao.jdbc.QueryRule;
import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.share.ResFavoriteDao;
import com.baizhitong.resource.model.res.ResFavorite;

/**
 * 资源评论接口
 * 
 * @author creator lushunming 2015/12/07
 * @author updater
 */
@Service("resFavoriteSqlServerDao")
public class ResFavoriteSqlServerDaoImpl extends BaseSqlServerDao<ResFavorite> implements ResFavoriteDao {
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

    /**
     * 获取收藏资源
     * 
     * @param folderId 文件夹id
     * @param pageNo 页号
     * @return 资源page
     */
    @Override
    public Page<Map<String, Object>> selectFavoritePage(Integer folderId, Integer pageNo, Integer pageSize) {
        Map<String, Object> param = new HashMap<String, Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT ");
        sql.append(" rf.collectTime,");
        sql.append(" rf.folderId,");
        sql.append(" rf.id, ");
        sql.append(" rf.resId,");
        sql.append(" rf.resTypeL1,");
        sql.append(" rf.resTypeL2,");
        sql.append(" rf.userCode");
        sql.append(" FROM ");
        sql.append(" res_favorite rf");
        if (null != folderId) {
            sql.append(" WHERE folderId = :folderId");
            param.put("folderId", folderId);
        }
        sql.append(" order by resTypeL1 ");
        Page page = super.queryDistinctPage(sql.toString(), param, pageNo, pageSize);
        return page;
    }

    /**
     * 删除指定文件夹下的收藏资源
     * 
     * @param folderId 文件夹id
     */
    @Override
    public int deleteFavorites(Integer folderId) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("folderId", folderId);
        return super.doDelete(map);

    }

    @Override
    public List<ResFavorite> selectFavorites(Integer folderId) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("folderId", folderId);
        List<ResFavorite> list = super.find(queryRule);
        return list;

    }

    /**
     * 删除收藏list
     * 
     * @param favoriteList
     */
    @Override
    public int deleteFavoriteList(List<ResFavorite> favoriteList) {
        int count = 0;
        try {
            count = super.deleteAll(favoriteList);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 删除指定的收藏资源
     * 
     * @param resFavorite 收藏对象
     * @return 是否成功
     */
    @Override
    public boolean deleteFavorite(ResFavorite resFavorite) {
        boolean success = false;
        try {
            success = super.delete(resFavorite) > 0;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 查找指定的收藏资源
     * 
     * @param folderId 文件夹id
     * @param resId 资源id
     * @param resTypeL1 资源类型
     */

    @Override
    public ResFavorite selectFavorite(Integer folderId, Integer resId, Integer resTypeL1) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("folderId", folderId);
        queryRule.andEqual("resId", resId);
        queryRule.andEqual("resTypeL1", resTypeL1);
        ResFavorite resFavorite = super.findUnique(queryRule);
        return resFavorite;

    }

    /**
     * 根据用户userCode，资源id，资源类型获取这条资源收藏的条数
     * 
     * @param userCode 用户code
     * @param resID 资源id
     * @param resTypeL1 资源类型
     * @return 查询的条数
     */
    @Override
    public int getFavoriteCount(String userCode, Integer resID, Integer resTypeL1) {
        QueryRule queryRule = QueryRule.getInstance();
        queryRule.andEqual("userCode", userCode);
        queryRule.andEqual("resId", resID);
        queryRule.andEqual("resTypeL1", resTypeL1);
        int count = (int) super.getCount(queryRule);
        return count;
    }

    /**
     * 根据id获取收藏的资源
     * 
     * @param favoriteId 收藏的id
     * @return 返回对象
     */
    @Override
    public ResFavorite selectFavoriteById(Integer favoriteId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", favoriteId);
        return super.get(map);

    }

}
