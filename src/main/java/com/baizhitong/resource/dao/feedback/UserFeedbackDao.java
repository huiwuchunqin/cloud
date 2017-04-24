package com.baizhitong.resource.dao.feedback;

import java.util.Map;

import com.baizhitong.common.Page;

/**
 * 
 * UserFeedbackDao 用户反馈Dao接口
 * 
 * @author creator zhanglzh 2016年9月29日 上午9:18:41
 * @author updater
 *
 * @version 1.0.0
 */
public interface UserFeedbackDao {
    /**
     * 
     * (获取用户反馈信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page<Map<String, Object>> getUserFeedbackList(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (获取用户反馈信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Map<String, Object> getUserFeedbackById(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (删除用户反馈信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return 操作影响数
     */
    int updateFlag(String ids, Integer operateType, String userName, String ip);

    /**
     * 
     * (修改用户反馈信息处理状态)<br>
     * 
     * @param ids
     * @param operateType
     * @return 操作影响数
     */
    int updateDisposeStatus(String ids, Integer operateType, String userName, String ip);

    /**
     * 
     * (修改用户反馈信息处理描述)<br>
     * 
     * @param ids
     * @param disposeDesc
     * @return 操作影响数
     */
    int updateDisposeDesc(String ids, String disposeDesc, String userName, String ip);
}
