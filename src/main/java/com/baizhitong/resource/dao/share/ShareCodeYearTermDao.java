package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.share.ShareCodeYearTerm;

/**
 * 学期接口 ShareCodeYearTermDao TODO
 * 
 * @author creator BZT 2016年4月28日 下午5:09:22
 * @author updater
 *
 * @version 1.0.0
 */
public interface ShareCodeYearTermDao {
    /*
     * 查询学期列表
     */
    List<ShareCodeYearTerm> getTermList(Map<String, Object> map);

    /*
     * 查询学期分页信息 ()<br>
     * 
     * @param map
     * 
     * @return
     */
    Page getTermPageList(Map<String, Object> map);

    /*
     * 保存学期
     */
    boolean addOrUpdateTerm(ShareCodeYearTerm shareCodeYearTerm);

    /*
     * 查询学期
     */
    public ShareCodeYearTerm getCodeYearTerm(String yearTermCode);

    /*
     * 查询学期
     */
    public ShareCodeYearTerm getCodeYearTermByGid(String gid);

    /*
     * 删除学期
     */
    public void deleteCodeYearTerm(String gid);
}
