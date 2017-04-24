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
import com.baizhitong.resource.dao.point.PointLotteryGoodsOrgDao;
import com.baizhitong.resource.manage.point.service.IPointLotteryGoodsOrgService;
import com.baizhitong.resource.model.point.PointLotteryGoodsOrg;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.TimeUtils;

/**
 * 机构积分商品接口实现 PointLotterygoodssOrgServiceImpl TODO
 * 
 * @author creator gaow 2016年6月21日 下午8:06:09
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointLotteryGoodsOrgServiceImpl extends BaseService implements IPointLotteryGoodsOrgService {
    /**
     * 机构积分商品
     */
    private @Autowired PointLotteryGoodsOrgDao goodssOrgDao;
    private @Autowired ISysCodeService         sysCodeService;

    /**
     * 保存商品 ()<br>
     * 
     * @param goods
     * @return
     */
    public ResultCodeVo add(PointLotteryGoodsOrg goods) {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        if (goods.getId() == null) {
            String goodsCode = sysCodeService.getCode(SysCodeConstants.ORG_GOODS_CODE, "orgCode",
                            userInfoVo.getOrgCode().trim());
            goods.setBizVersion(Integer.parseInt(TimeUtils.formatDate(new Date(), "YYYYMMDD")));
            goods.setStartTime(new Date().getTime());
            goods.setExpireTime(Long.valueOf("99999999999999"));
            goods.setGoodsCode(goodsCode);
            goods.setOrgCode(userInfoVo.getOrgCode());
            goods.setModifier(userInfoVo.getUserName());
            goods.setModifierIP(getIp());
            goods.setModifyTime(new Timestamp(new Date().getTime()));
            goods.setFlagDelete(0);
            int count = goodssOrgDao.getCount(goods.getUserRoleRequirement(), goods.getOrgCode());
            // 新增的商品也是正在使用中
            if (goods.getFlagUsing() == 1) {
                if (count >= 11)
                    return ResultCodeVo.errorCode("该角色用于抽奖商品已经达到11个不能继续新增");
            }
        } else {
            PointLotteryGoodsOrg old = goodssOrgDao.getOrgGoods(goods.getId());
            int count = goodssOrgDao.getCount(goods.getUserRoleRequirement(), old.getOrgCode());
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
            old.setModifier(userInfoVo.getUserName());
            old.setModifierIP(getIp());
            old.setModifyTime(new Timestamp(new Date().getTime()));

        }
        return goodssOrgDao.add(goods) ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");

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
    public Page getPageList(String orgCode, String goodsName, Integer role, Integer page, Integer rows) {
        return goodssOrgDao.getList(orgCode, goodsName, role, page, rows);
    }

    /**
     * 查询商品 ()<br>
     * 
     * @param id
     * @return
     */
    public PointLotteryGoodsOrg getOrgGoods(Integer id) {
        return goodssOrgDao.getOrgGoods(id);
    }

    /**
     * 删除商品 ()<br>
     * 
     * @param id
     * @return
     */
    public ResultCodeVo deleteGood(Integer id) {
        int count = goodssOrgDao.deleteGood(id);
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }
}
