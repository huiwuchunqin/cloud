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
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.dao.res.ResRecommendDao;
import com.baizhitong.resource.manage.res.service.RecommendMediaResService;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.resource.model.res.ResRecommend;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 音视频推荐资源接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月22日 下午1:36:25
 */
@Service(value = "recommendMediaResService")
public class RecommendMediaResServiceImpl extends BaseService implements RecommendMediaResService {
    /** 推荐资源数据接口 */
    private @Autowired ResRecommendDao resRecommendDao;
    /** 音视频资源数据接口 */
    private @Autowired ResMediaDao     resMediaDao;

    /**
     * 查询已被推荐的音视频资源信息
     */
    @Override
    public Page<Map<String, Object>> getRecommendMediaInfo(Map<String, Object> param) throws Exception {
        return resRecommendDao.getRecommendMediaInfo(param);
    }

    /**
     * 取消音视频资源推荐
     */
    @Override
    public int deleteRecommendMediaRes(String resId) throws Exception {
        return resRecommendDao.deleteRecommendRes(resId, CoreConstants.RES_TYPE_MEDIA);
    }

    /**
     * 查询未被推荐的音视频资源信息
     */
    @Override
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception {
        return resMediaDao.getCanRecommendInfo(param);
    }

    /**
     * 推荐音视频资源
     */
    @Override
    public ResultCodeVo recommendMediaRes(String resId) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        ResMedia resMedia = resMediaDao.selectResMedia(Integer.parseInt(resId));
        if (StringUtils.isEmpty(resId)) {
            return ResultCodeVo.errorCode("请先选择一个音视频资源！");
        } else {
            ResRecommend oldEntity = resRecommendDao.getInfoByResIdAndResTypeL1(resId, CoreConstants.RES_TYPE_MEDIA);
            // 该音视频资源未被推荐过
            if (oldEntity == null) {
                ResRecommend entity = new ResRecommend();
                entity.setResId(Integer.parseInt(resId));
                entity.setResCode(resMedia.getResCode());
                entity.setResTypeL1(CoreConstants.RES_TYPE_MEDIA);
                entity.setResName(resMedia.getResName());
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
            // 该音视频资源之前已被推荐过
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
}
