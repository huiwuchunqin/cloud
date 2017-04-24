package com.baizhitong.resource.manage.feedback.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.dao.feedback.ResErrorFeedbackDao;
import com.baizhitong.resource.dao.feedback.UserFeedbackDao;
import com.baizhitong.resource.dao.res.ResAudioDao;
import com.baizhitong.resource.dao.res.ResDocDao;
import com.baizhitong.resource.dao.res.ResFlashDao;
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.manage.feedback.service.FeedbackService;
import com.baizhitong.resource.model.res.ResAudio;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.resource.model.res.ResFlash;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.utils.ObjectUtils;

/**
 * 
 * FeedbackServiceImpl 平台反馈service实现
 * 
 * @author creator zhanglzh 2016年9月29日 上午9:17:43
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class FeedbackServiceImpl extends BaseService implements FeedbackService {
    
    @Autowired
    private UserFeedbackDao     userFeedbackDao;
    @Autowired
    private ResErrorFeedbackDao resErrorFeedbackDao;
    @Autowired
    private ResAudioDao         resAudioDao;
    @Autowired
    private ResMediaDao         resMediaDao;
    @Autowired
    private ResDocDao           resDocDao;
    @Autowired
    private ResFlashDao         resFlashDao;

    /**
     * 
     * (获取用户反馈列表)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    public Page<Map<String, Object>> getUserFeedbackList(Integer page, Integer rows, Map<String, Object> param) {
        Page<Map<String, Object>> mapPage = userFeedbackDao.getUserFeedbackList(page, rows, param);
        return mapPage;
    }

    /**
     * 
     * (获取资源纠错信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    @Override
    public Page getresErrorFeedbackList(Integer page, Integer rows, Map<String, Object> param) {
        return resErrorFeedbackDao.getResErrorFeedbackList(page, rows, param);

    }

    /**
     * 
     * (获取资源纠错详细信息)<br>
     * 
     * @param page
     * @param rows
     * @param param
     * @return
     */
    @Override
    public Page getUserFeedbackDailyList(Integer page, Integer rows, Map<String, Object> param) {
        return resErrorFeedbackDao.getResErrorFeedbackDailyList(page, rows, param);
    }

    /**
     * 
     * (删除用户反馈详细信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public ResultCodeVo deleteUserFeedback(String ids, Integer operateType) throws Exception {
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        int count = 0;
        count = userFeedbackDao.updateFlag(ids, operateType, userInfo.getUserName(), ip);
        if (0 < count) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (删除资源纠错信息)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public ResultCodeVo deleteResErrorFeedback(String ids, Integer operateType) throws Exception {
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        int count = 0;
        count = resErrorFeedbackDao.updateFlag(ids, operateType, userInfo.getUserName(), ip);
        if (0 < count) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (更新用户反馈详细信息的处理状态)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public ResultCodeVo updateStatusUserFeedback(String ids, Integer operateType) throws Exception {
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        int count = 0;
        count = userFeedbackDao.updateDisposeStatus(ids, operateType, userInfo.getUserName(), ip);
        if (0 < count) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (更新资源纠错信息的处理状态)<br>
     * 
     * @param ids
     * @param operateType
     * @return
     */
    @Override
    public ResultCodeVo updateStatusResErrorFeedback(String ids, Integer operateType) throws Exception {
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        int count = 0;
        count = resErrorFeedbackDao.updateDisposeStatus(ids, operateType, userInfo.getUserName(), ip);
        if (0 < count) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (资源纠错，处理描述操作)<br>
     * 
     * @param ids
     * @param operateType 操作类型：1，处理；2无需处理；
     * @return
     */
    @Override
    public ResultCodeVo updateDescResErrorFeedback(String ids, String disposeDesc) throws Exception {
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        int count = 0;
        count = resErrorFeedbackDao.updateDisposeDesc(ids, disposeDesc, userInfo.getUserName(), ip);
        if (0 < count) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (用户反馈，处理描述操作)<br>
     * 
     * @param ids
     * @param operateType 操作类型：1，处理；2无需处理；
     * @return
     */
    @Override
    public ResultCodeVo updateDescUserFeedback(String ids, String disposeDesc) throws Exception {
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        int count = 0;
        count = userFeedbackDao.updateDisposeDesc(ids, disposeDesc, userInfo.getUserName(), ip);
        if (0 < count) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (获取资源纠错详情)<br>
     * 
     * @param startDate
     * @param endDate
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Map<String, Object> getResErrorFeedback(Integer page, Integer rows, Map<String, Object> param) {
        return resErrorFeedbackDao.getResErrorFeedbackById(page, rows, param);
    }

    /**
     * 
     * (获取用户反馈详情)<br>
     * 
     * @param startDate
     * @param endDate
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Map<String, Object> getUserFeedback(Integer page, Integer rows, Map<String, Object> param) {
        return userFeedbackDao.getUserFeedbackById(page, rows, param);
    }

    /**
     * 
     * (校验资源已经被删除)<br>
     * @param resCode 资源编码
     * @param resTypeL1 资源一级分类
     * @return 校验结果
     * @throws Exception
     */
    @Override
    public ResultCodeVo checkResFlagDelete(String resCode, Integer resTypeL1) throws Exception {
        boolean checkResult=false;
        if(CoreConstants.RES_TYPE_MEDIA.equals(resTypeL1)){
            ResMedia media=resMediaDao.selectResMedia(resCode);
            if(ObjectUtils.isNotNull(media)){
                checkResult=true;
            }
        }else if(CoreConstants.RES_TYPE_DOC.equals(resTypeL1)){
            ResDoc doc=resDocDao.selectResDoc(resCode);
            if(ObjectUtils.isNotNull(doc)){
                checkResult=true;
            }
        }else if(CoreConstants.RES_TYPE_AUDIO.equals(resTypeL1)){
            ResAudio audio=resAudioDao.getAudio(resCode);
            if(ObjectUtils.isNotNull(audio)){
                checkResult=true;
            }
        }else{
            ResFlash flash=resFlashDao.selectFlash(resCode);
            if(ObjectUtils.isNotNull(flash)){
                checkResult=true;
            }
        }
        if(checkResult){
            return ResultCodeVo.rightCode("资源未删除！");
        }else{
            return ResultCodeVo.errorCode("该资源已经被删除！");
        }
    }
    
}
