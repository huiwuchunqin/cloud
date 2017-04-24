package com.baizhitong.resource.dao.share;

import java.util.List;
import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.res.ResComment;

/**
 * 资源评论接口
 * 
 * @author creator lushunming 2015/12/07
 * @author updater
 */
public interface ResCommentDao {

    /**
     * 根据资源id和类型来查询资源评论page
     * 
     * @param resID 资源id
     * @param resTypeL1 资源类型
     * @param pageNo当前页号
     * @param pageSize 页面大小
     * @return page
     */
    Page<Map<String, Object>> selectResCommentPage(Integer resID, Integer resTypeL1, Integer pageNo, Integer pageSize);

    /**
     * 保存资源评论
     * 
     * @param resComment 资源评论对象
     * @return 是否成功
     */
    public boolean saveResComment(ResComment resComment);

    /**
     * 根据资源id和类型,repliedCommentID来查询资源评论
     * 
     * @param resID 资源id
     * @param resTypeL1 资源类型
     * @param repliedCommentID 所回复的评论id
     * @return list集合
     */

    public List<Map<String, Object>> selectResCommentList(Integer resID, Integer resTypeL1, Integer repliedCommentID);

}
