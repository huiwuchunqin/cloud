package com.baizhitong.resource.manage.res.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.res.ResDocDao;
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.dao.res.ResRecommendDao;
import com.baizhitong.resource.manage.res.service.RecommendResService;
import com.baizhitong.resource.model.res.ResRecommend;
import com.baizhitong.resource.model.vo.res.ResVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

@Service(value = "recommendResService")
public class RecommendResServiceImpl extends BaseService implements RecommendResService {
    /** 推荐资源数据接口 */
    private @Autowired ResRecommendDao resRecommendDao;
    /** 文档资源数据接口 */
    private @Autowired ResDocDao       resDocDao;
    /** 文档资源数据接口 */
    private @Autowired ResMediaDao     resMediaDao;

    /**
     * 查询未被推荐的资源信息
     */
    @Override
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception {
        return resDocDao.getAllCanRecommendInfo(param);

    }

    /**
     * 查询资源推荐信息 ()<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public Page<Map<String, Object>> getRecommendInfo(Map<String, Object> param) throws Exception {
        return resRecommendDao.getRecommendResInfo(param);
    }

    /**
     * 删除推荐资源 ()<br>
     * 
     * @param resId
     * @return
     * @throws Exception
     */
    public int deleteRecommendRes(String resId, Integer resType) throws Exception {
        return resRecommendDao.deleteRecommendRes(resId, resType);
    }

    /**
     * 推荐资源 ()<br>
     * 
     * @param resId
     * @return
     * @throws Exception
     */
    public ResultCodeVo recommendRes(String resId, Integer resType) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        ResVo vo = new ResVo();
        if (resType == null)
            return ResultCodeVo.errorCode("资源类型为空无法推荐！");
        if (StringUtils.isEmpty(resId)) {
            return ResultCodeVo.errorCode("请先选择一个资源！");
        }
        if (resType == CoreConstants.RES_TYPE_DOC) {
            vo = ResVo.getResVoFromResDoc(resDocDao.selectResDoc(Integer.parseInt(resId)));
        } else if (resType == CoreConstants.RES_TYPE_MEDIA) {
            vo = ResVo.getResVoFromResMidea(resMediaDao.selectResMedia(Integer.parseInt(resId)));
        }
        ResRecommend oldEntity = resRecommendDao.getInfoByResIdAndResTypeL1(resId, resType);
        // 该文档资源未被推荐过
        if (oldEntity == null) {
            ResRecommend entity = new ResRecommend();
            entity.setResId(Integer.parseInt(resId));
            entity.setResCode(vo.getResCode());
            entity.setResTypeL1(resType);
            entity.setResName(vo.getResName());
            entity.setRecommendTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            entity.setRecommender(userInfo.getUserCode());
            entity.setFlagDelete(0);
            entity.setCreator(userInfo.getLoginAccount());
            entity.setCreateTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            entity.setCreatorIP(getIp());
            entity.setModifier(userInfo.getLoginAccount());
            entity.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            entity.setModifierIP(getIp());
            resRecommendDao.addResRecommend(entity);
        }
        // 该文档资源之前已被推荐过
        else {
            ResRecommend entity = new ResRecommend();
            entity.setId(oldEntity.getId());
            entity.setResId(oldEntity.getResId());
            entity.setResCode(oldEntity.getResCode());
            entity.setResTypeL1(oldEntity.getResTypeL1());
            entity.setResName(oldEntity.getResName());
            entity.setRecommendTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            entity.setRecommender(userInfo.getUserCode());
            entity.setFlagDelete(0);
            entity.setCreator(oldEntity.getCreator());
            entity.setCreateTime(oldEntity.getCreateTime());
            entity.setCreatorIP(oldEntity.getCreatorIP());
            entity.setModifier(userInfo.getLoginAccount());
            entity.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            entity.setModifierIP(getIp());
            resRecommendDao.addResRecommend(entity);
        }
        return ResultCodeVo.rightCode("推荐成功！");

    }
}
