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
import com.baizhitong.resource.dao.point.PointGoodsPutOrgDao;
import com.baizhitong.resource.manage.point.service.IPointGoodsPutOrgService;
import com.baizhitong.resource.model.point.PointGoodsPutOrg;

/**
 * 
 * PointGoodsPutOrgImpl 机构商品入库接口
 * 
 * @author creator gaow 2016年6月27日 下午1:52:38
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointGoodsPutOrgImpl extends BaseService implements IPointGoodsPutOrgService {
    private @Autowired PointGoodsPutOrgDao goodsPutOrgDao;

    /**
     * 查询入库明细 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    public Page getList(Map<String, Object> param, Integer page, Integer rows) {
        // TODO Auto-generated method stub
       
        UserInfoVo userInfoVo =getUserInfoVo();
        return goodsPutOrgDao.getList(param, userInfoVo.getOrgCode(), page, rows);
    }

    /**
     * 保存或新增 ()<br>
     * 
     * @param pointGoodsPut
     * @return
     */
    public ResultCodeVo saveOrupdate(PointGoodsPutOrg pointGoodsPut) {
        // TODO Auto-generated method stub
       
        UserInfoVo userInfoVo =getUserInfoVo();
        // PointGoodsPutOrg goodsPutOrg=goodsPutOrgDao.getByGoodsCode(pointGoodsPut.getGoodsCode());
        boolean success;
        /*
         * if(goodsPutOrg!=null){//已经存在记录则为更新
         * goodsPutOrg.setGoodsAmount(goodsPutOrg.getGoodsAmount()+pointGoodsPut.getGoodsAmount());
         * goodsPutOrg.setModifier(userInfoVo.getUserName());
         * goodsPutOrg.setModifierIP(getIp()); goodsPutOrg.setModifyTime(new Timestamp(new
         * Date().getTime())); success= goodsPutOrgDao.saveOrupdate(goodsPutOrg); }else{
         */
        pointGoodsPut.setModifier(userInfoVo.getUserName());
        pointGoodsPut.setModifierIP(getIp());
        pointGoodsPut.setOrgCode(userInfoVo.getOrgCode());
        pointGoodsPut.setModifyTime(new Timestamp(new Date().getTime()));
        success = goodsPutOrgDao.saveOrupdate(pointGoodsPut);
        /* } */
        // 更新商品表的入库数量
        goodsPutOrgDao.updateGoodsOrgGoodsAmout(pointGoodsPut.getGoodsAmount(), pointGoodsPut.getGoodsCode());
        return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");

    }

    /**
     * 根据id查询 ()<br>
     * 
     * @param id
     * @return
     */
    public PointGoodsPutOrg getByGoodsCode(String goodsCode) {
        // TODO Auto-generated method stub
        return goodsPutOrgDao.getByGoodsCode(goodsCode);
    }

}
