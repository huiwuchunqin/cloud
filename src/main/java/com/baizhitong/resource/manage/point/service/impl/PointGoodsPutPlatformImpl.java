package com.baizhitong.resource.manage.point.service.impl;

import java.sql.Timestamp;
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
import com.baizhitong.resource.dao.point.PointGoodsPutPlatformDao;
import com.baizhitong.resource.manage.point.service.IPointGoodsPutPlatformService;
import com.baizhitong.resource.model.point.PointGoodsPutPlatform;

/**
 * 平台商品入库接口 PointGoodsPutPlatformImpl TODO
 * 
 * @author creator gaow 2016年6月27日 下午1:53:05
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointGoodsPutPlatformImpl extends BaseService implements IPointGoodsPutPlatformService {
    private @Autowired PointGoodsPutPlatformDao goodsPutPlatformDao;

    /**
     * 查询入库明细 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    public Page getList(Map<String, Object> param, Integer page, Integer rows) {
        // TODO Auto-generated method stub
        return goodsPutPlatformDao.getList(param, page, rows);
    }

    /**
     * 保存或新增 ()<br>
     * 
     * @param pointGoodsPut
     * @return
     */
    public ResultCodeVo saveOrupdate(PointGoodsPutPlatform pointGoodsPutPlatform) {
        // TODO Auto-generated method stub
        // TODO Auto-generated method stub
       
        UserInfoVo userInfoVo =getUserInfoVo();
        // PointGoodsPutPlatform
        // goodsPutPlatform=goodsPutPlatformDao.getByGoodsCode(pointGoodsPutPlatform.getGoodsCode());
        boolean success;
        /*
         * if(goodsPutPlatform!=null){ goodsPutPlatform.setModifier(userInfoVo.getUserName());
         * goodsPutPlatform.setModifierIP(getIp()); goodsPutPlatform.setModifyTime(new
         * Timestamp(new Date().getTime()));
         * goodsPutPlatform.setGoodsAmount(goodsPutPlatform.getGoodsAmount()+pointGoodsPutPlatform.
         * getGoodsAmount()); success=goodsPutPlatformDao.saveOrupdate(goodsPutPlatform); }else{
         */
        pointGoodsPutPlatform.setModifier(userInfoVo.getUserName());
        pointGoodsPutPlatform.setModifierIP(getIp());
        pointGoodsPutPlatform.setModifyTime(new Timestamp(new Date().getTime()));
        success = goodsPutPlatformDao.saveOrupdate(pointGoodsPutPlatform);
        /* } */
        goodsPutPlatformDao.updateGoodsPlatformGoodsAmout(pointGoodsPutPlatform.getGoodsAmount(),
                        pointGoodsPutPlatform.getGoodsCode());
        return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
    }

    /**
     * 根据id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public PointGoodsPutPlatform getByGoodsCode(String goodsCode) {
        return goodsPutPlatformDao.getByGoodsCode(goodsCode);
    }

}
