package com.baizhitong.resource.dao.rel.sqlserver;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.dao.BaseSqlServerDao;
import com.baizhitong.resource.dao.rel.RelResKeywordDao;
import com.baizhitong.resource.model.rel.RelResKeyword;

/**
 * 资源与关键词实现
 * 
 * @author lusm 2015/12/10
 *
 */
@Service("relResKeywordDao")
public class RelResKeywordDaoImpl extends BaseSqlServerDao<RelResKeyword> implements RelResKeywordDao {
    /**
     * 保存资源与关键词
     * 
     * @param relResKeyword 对象
     * @return 是否成功
     */
    @Override
    public boolean saveRelResKeyword(RelResKeyword relResKeyword) {
        boolean success = false;
        try {
            success = super.saveOne(relResKeyword);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 保存资源与关键词
     * 
     * @param relResKeyword 对象
     * @return 资源ID
     */
    @Override
    public int saveRelResKeywordAndReturnId(RelResKeyword relResKeyword) {
        int id = 0;
        try {
            id = MapUtils.getInteger(super.saveAndReturnId(relResKeyword), "id");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 更新资源与关键词
     * 
     * @param relResKeyword 对象
     * @return 是否成功
     */
    @Override
    public boolean updateRelResKeyword(RelResKeyword relResKeyword) {
        int count = 0;
        try {
            count = super.update(relResKeyword);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
}