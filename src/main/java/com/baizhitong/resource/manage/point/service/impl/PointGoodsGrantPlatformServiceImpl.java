package com.baizhitong.resource.manage.point.service.impl;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.point.PointGoodsGrantPlatformDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.manage.point.service.IPointGoodsGrantPlatfromService;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.utils.TimeUtils;

@Service
public class PointGoodsGrantPlatformServiceImpl extends BaseService implements IPointGoodsGrantPlatfromService {
    private @Autowired PointGoodsGrantPlatformDao pointGoodsGrantPlatformDao;

    private @Autowired SharePlatformDao           sharePlatfromDao;

    /**
     * 查询发放概要 ()<br>
     * 
     * @return
     */
    public Page getList(Map<String, Object> param, Integer page, Integer rows) {
        return pointGoodsGrantPlatformDao.getList(param, page, rows);
    }

    /**
     * 插入发放概要 ()<br>
     * 
     * @return
     */
    public ResultCodeVo insert(String orgName, String goodsName) {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        SharePlatform platform = sharePlatfromDao.getByCodeGlobal();
        if (platform == null)
            return ResultCodeVo.errorCode("没有查询到平台当前学期");
        String actionBatch = TimeUtils.formatDate(new Date(), "YYYYMMDD");
        int count = pointGoodsGrantPlatformDao.insert(BeanHelper.getStudyYearCode(), BeanHelper.getTermCode(),
                        BeanHelper.getYearTermCode(), actionBatch, userInfoVo.getUserName(), getIp(), orgName,
                        goodsName);
        return count > 0 ? ResultCodeVo.rightCode("发放成功") : ResultCodeVo.errorCode("发放失败");
    }
}
