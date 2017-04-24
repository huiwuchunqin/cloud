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
import com.baizhitong.resource.dao.res.ResRecommendDao;
import com.baizhitong.resource.manage.res.service.RecommendDocResService;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.resource.model.res.ResRecommend;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 文档推荐资源接口实现
 * 
 * @author zhangqiang
 * @date 2015年12月21日 下午2:19:34
 */
@Service(value = "recommendDocResService")
public class RecommendDocResServiceImpl extends BaseService implements RecommendDocResService {
    /** 推荐资源数据接口 */
    private @Autowired ResRecommendDao resRecommendDao;
    /** 文档资源数据接口 */
    private @Autowired ResDocDao       resDocDao;

    /**
     * 查询已被推荐的文档资源信息
     */
    @Override
    public Page<Map<String, Object>> getRecommendDocInfo(Map<String, Object> param) throws Exception {
        return resRecommendDao.getRecommendDocInfo(param);
    }

    /**
     * 取消文档资源推荐
     */
    @Override
    public int deleteRecommendDocRes(String resId) throws Exception {
        return resRecommendDao.deleteRecommendRes(resId, CoreConstants.RES_TYPE_DOC);
    }

    /**
     * 查询未被推荐的文档资源信息
     */
    @Override
    public Page<Map<String, Object>> getCanRecommendInfo(Map<String, Object> param) throws Exception {
        return resDocDao.getCanRecommendInfo(param);
    }

    /**
     * 推荐文档资源
     */
    @Override
    public ResultCodeVo recommendDocRes(String docResId) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        ResDoc resDoc = resDocDao.selectResDoc(Integer.parseInt(docResId));
        if (StringUtils.isEmpty(docResId)) {
            return ResultCodeVo.errorCode("请先选择一个文档资源！");
        } else {
            ResRecommend oldEntity = resRecommendDao.getInfoByResIdAndResTypeL1(docResId, CoreConstants.RES_TYPE_DOC);
            // 该文档资源未被推荐过
            if (oldEntity == null) {
                ResRecommend entity = new ResRecommend();
                entity.setResId(Integer.parseInt(docResId));
                entity.setResCode(resDoc.getResCode());
                entity.setResTypeL1(CoreConstants.RES_TYPE_DOC);
                entity.setResName(resDoc.getResName());
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

}
