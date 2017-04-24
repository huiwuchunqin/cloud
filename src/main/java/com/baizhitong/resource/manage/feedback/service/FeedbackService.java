package com.baizhitong.resource.manage.feedback.service;

import java.util.Map;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.feedback.ResErrorFeedback;
import com.baizhitong.resource.model.feedback.UserFeedback;

/**
 * 
 * FeedbackService 平台反馈service
 * 
 * @author creator zhanglzh 2016年9月29日 上午9:18:07
 * @author updater
 *
 * @version 1.0.0
 */
public interface FeedbackService {
    /**
     * 
     * (获取用户反馈列表)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page<Map<String, Object>> getUserFeedbackList(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (获取资源纠错信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page getresErrorFeedbackList(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (获取资源纠错详细信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page getUserFeedbackDailyList(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (删除用户反馈详细信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    public ResultCodeVo deleteUserFeedback(String ids, Integer operateType) throws Exception;

    /**
     * 
     * (更新处理状态用户反馈详细信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    public ResultCodeVo updateStatusUserFeedback(String ids, Integer operateType) throws Exception;

    /**
     * 
     * (删除资源纠错信息信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    public ResultCodeVo deleteResErrorFeedback(String ids, Integer operateType) throws Exception;

    /**
     * 
     * (更新处理状态资源纠错信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    public ResultCodeVo updateStatusResErrorFeedback(String ids, Integer operateType) throws Exception;

    /**
     * 
     * (更新处理状态资源纠错信息)<br>
     * 
     * @param ids
     * @param disposeDesc
     * @return
     */
    public ResultCodeVo updateDescResErrorFeedback(String ids, String disposeDesc) throws Exception;

    /**
     * (更新处理状态用户反馈信息)<br>
     * 
     * @param ids
     * @param disposeDesc
     * @return
     */
    public ResultCodeVo updateDescUserFeedback(String ids, String disposeDesc) throws Exception;

    /**
     * 
     * (获取资源纠错详细)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Map<String, Object> getResErrorFeedback(Integer page, Integer rows, Map<String, Object> param);

    /**
     * 
     * (获取用户反馈详细)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Map<String, Object> getUserFeedback(Integer page, Integer rows, Map<String, Object> param);
    
    /**
     * 
     * (校验资源已经被删除)<br>
     * @param resCode 资源编码
     * @param resTypeL1 资源一级分类
     * @return 校验结果
     * @throws Exception
     */
    public ResultCodeVo checkResFlagDelete(String resCode,Integer resTypeL1) throws Exception;
    
}
