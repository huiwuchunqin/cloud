package com.baizhitong.resource.dao.rel;

import com.baizhitong.resource.model.rel.RelResKeyword;

/**
 * 资源与关键词实体类
 * 
 * @author lusm 2015/12/10
 *
 */
public interface RelResKeywordDao {
    /**
     * 保存资源与关键词
     * 
     * @param relResKeyword 对象
     * @return 是否成功
     */
    public boolean saveRelResKeyword(RelResKeyword relResKeyword);

    /**
     * 更新资源与关键词
     * 
     * @param relResKeyword 对象
     * @return 是否成功
     */
    public boolean updateRelResKeyword(RelResKeyword relResKeyword);

    /**
     * 保存资源与关键词
     * 
     * @param relResKeyword 对象
     * @return 资源ID
     */
    public int saveRelResKeywordAndReturnId(RelResKeyword relResKeyword);

}