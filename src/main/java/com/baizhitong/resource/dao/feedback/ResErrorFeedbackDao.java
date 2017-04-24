package com.baizhitong.resource.dao.feedback;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.model.feedback.ResErrorFeedback;

/**
 * 
 * ResErrorFeedbackDao 资源纠错Dao接口
 * 
 * @author creator zhanglzh 2016年9月29日 上午9:19:18
 * @author updater
 *
 * @version 1.0.0
 */
public interface ResErrorFeedbackDao {
    /**
     * 
     * (获取资源纠错信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page getResErrorFeedbackList(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (获取资源纠错信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Map<String, Object> getResErrorFeedbackById(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (获取资源纠错详细信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page getResErrorFeedbackDailyList(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (删除资源纠错信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return 操作影响数
     */
    int updateFlag(String ids, Integer operateType, String userName, String ip);

    /**
     * 
     * (修改资源纠错信息处理状态)<br>
     * 
     * @param ids
     * @param operateType
     * @return 操作影响数
     */
    int updateDisposeStatus(String ids, Integer operateType, String userName, String ip);

    /**
     * 
     * (修改资源纠错信息处理描述)<br>
     * 
     * @param ids
     * @param disposeDesc
     * @return 操作影响数
     */
    int updateDisposeDesc(String ids, String disposeDesc, String userName, String ip);
}
