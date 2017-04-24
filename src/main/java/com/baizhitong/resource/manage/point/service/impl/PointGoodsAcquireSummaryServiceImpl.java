package com.baizhitong.resource.manage.point.service.impl;

import java.util.Date;
import java.util.List;
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
import com.baizhitong.resource.dao.point.PointGoodsAcquireSummaryDao;
import com.baizhitong.resource.dao.point.PointGoodsGrantOrgDetailDao;
import com.baizhitong.resource.dao.point.PointGoodsGrantPlatformDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.manage.point.service.IPointGoodsAcquireSummaryService;
import com.baizhitong.resource.model.point.PointGoodsGrantPlatform;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

@Service
public class PointGoodsAcquireSummaryServiceImpl extends BaseService implements IPointGoodsAcquireSummaryService {
    /** 发放汇总dao */
    private @Autowired PointGoodsAcquireSummaryDao pointGoodsAcquireSummaryDao;
    /** 商品发放明细dao */
    private @Autowired PointGoodsGrantOrgDetailDao pointGoodsGrantOrgDetailDao;
    /** 平台dao */
    private @Autowired SharePlatformDao            sharePlatformDao;
    /** 商品发放概要平台 */
    private @Autowired PointGoodsGrantPlatformDao  pointGoodsGrantPlatformDao;

    /**
     * 查询 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getList(Map<String, Object> param, Integer page, Integer rows) {
        return pointGoodsAcquireSummaryDao.getPage(param, page, rows);
    }

    /**
     * 领取 ()<br>
     * 
     * @return
     */
    public ResultCodeVo exchange(Integer goodsLevel, String actionBatch, String adminClassCode, String userName,
                    String userRole, String ids) {
       
        CompanyInfoVo company =  getCompanyInfo();
        UserInfoVo userInfoVo =getUserInfoVo();
        if (CoreConstants.GOODS_LEVEL_AREA == goodsLevel) {
            if (actionBatch == null) {
                return ResultCodeVo.errorCode("请输入区域商品领取批次");
            }
            List<PointGoodsGrantPlatform> pointGoodsGrantPlatformList = pointGoodsGrantPlatformDao
                            .getSummaryList(actionBatch);
            if (pointGoodsGrantPlatformList == null || pointGoodsGrantPlatformList.size() <= 0) {
                return ResultCodeVo.errorCode("输入的商品批次不存在");
            }
        }
        // 机构领取不需要输入批次取当前时间作为批次
        if (StringUtils.isEmpty(actionBatch)) {
            actionBatch = TimeUtils.formatDate(new Date(), "YYYY-MM-dd");
        }
        // 插入机构商品兑换明细
        pointGoodsGrantOrgDetailDao.insert(company.getOrgCode(), BeanHelper.getStudyYearCode(),
                        BeanHelper.getTermCode(), BeanHelper.getTermCode(), actionBatch, userInfoVo.getUserName(),
                        getIp(), adminClassCode, userName, userRole, ids);
        int count = pointGoodsAcquireSummaryDao.exchange(company.getOrgCode(), goodsLevel, adminClassCode, userName,
                        userRole, ids);
        return count >= 0 ? ResultCodeVo.rightCode("领取成功") : ResultCodeVo.errorCode("领取失败");
    }
}
