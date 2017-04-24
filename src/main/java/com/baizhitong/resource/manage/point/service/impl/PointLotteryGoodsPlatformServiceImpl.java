package com.baizhitong.resource.manage.point.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.point.PointLotteryGoodsPlatformDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.manage.point.service.IPointLotteryGoodsPlatformService;
import com.baizhitong.resource.model.point.PointLotteryGoodsPlatform;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.TimeUtils;

/**
 * 平台积分商品接口实现 PointLotteryGoodsPlatformServiceImpl TODO
 * 
 * @author creator gaow 2016年6月21日 下午8:06:28
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointLotteryGoodsPlatformServiceImpl extends BaseService implements IPointLotteryGoodsPlatformService {
    /**
     * 平台积分商品dao
     */
    private @Autowired PointLotteryGoodsPlatformDao platfromGoodsDao;
    /**
     * 编码生成接口
     */
    private @Autowired ISysCodeService              sysCodeService;
    /**
     * 平台信息dao接口
     */
    private @Autowired SharePlatformDao             sharePlatformDao;

    /**
     * 保存商品 ()<br>
     * 
     * @param goods
     * @return
     */
    public ResultCodeVo add(PointLotteryGoodsPlatform goods) {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        if (goods.getId() == null) {
            SharePlatform platform = sharePlatformDao.getByCodeGlobal();
            if (platform == null)
                return ResultCodeVo.errorCode("平台信息为空");
            String goodsCode = sysCodeService.getCode(SysCodeConstants.PLATFROM_GOODS_CODE, "platformCode",
                            platform.getCodeGlobal(), "dateTime", TimeUtils.formatDate(new Date(), "YYYYMMdd"));
            goods.setBizVersion(Integer.parseInt(TimeUtils.formatDate(new Date(), "YYYYMMDD")));
            goods.setStartTime(new Date().getTime());
            goods.setExpireTime(Long.valueOf("99999999999999"));
            goods.setGoodsCode(goodsCode);
            goods.setDispOrder(1);
            goods.setModifier(userInfoVo.getUserName());
            goods.setModifierIP(getIp());
            goods.setModifyTime(new Timestamp(new Date().getTime()));
            goods.setFlagDelete(0);
            int count = platfromGoodsDao.getCount(goods.getUserRoleRequirement());
            // 新增的商品也是正在使用中
            if (goods.getFlagUsing() == 1) {
                if (count >= 11)
                    return ResultCodeVo.errorCode("该角色用于抽奖商品已经达到11个不能继续新增");
            }
        } else {
            PointLotteryGoodsPlatform old = platfromGoodsDao.getPlatformGoods(goods.getId());
            int count = platfromGoodsDao.getCount(goods.getUserRoleRequirement());
            if (old.getFlagUsing() == 0 && goods.getFlagUsing() == 1) {
                if (count >= 11)
                    return ResultCodeVo.errorCode("该角色用于抽奖商品已经达到11个不能修改为用于抽奖");
            }
            old.setFlagUsing(goods.getFlagUsing());
            old.setGoodsName(goods.getGoodsName());
            old.setGoodsThumbnailJson(goods.getGoodsThumbnailJson());
            old.setGoodsUnit(goods.getGoodsUnit());
            old.setLotteryGoodsType(goods.getLotteryGoodsType());
            old.setPointFeedback(goods.getPointFeedback());
            old.setProbability(goods.getProbability());
            old.setUserRoleRequirement(goods.getUserRoleRequirement());
            old.getGoodsSpecification();
            goods.setModifier(userInfoVo.getUserName());
            goods.setModifierIP(getIp());
            goods.setModifyTime(new Timestamp(new Date().getTime()));
        }
        return platfromGoodsDao.add(goods) ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");

    }

    /**
     * 查询商品列表 ()<br>
     * 
     * @param goodsName
     * @param role
     * @param page
     * @param rows
     * @return
     */
    public Page getPageList(String goodsName, Integer role, Integer page, Integer rows) {
        return platfromGoodsDao.getList(goodsName, role, page, rows);
    }

    /**
     * 查询商品 ()<br>
     * 
     * @param id
     * @return
     */
    public PointLotteryGoodsPlatform getPlatformGoods(Integer id) {
        return platfromGoodsDao.getPlatformGoods(id);
    }

    /**
     * 删除商品 ()<br>
     * 
     * @param id
     * @return
     */
    public ResultCodeVo deleteGood(Integer id) {
        int count = platfromGoodsDao.deleteGood(id);
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }
}
