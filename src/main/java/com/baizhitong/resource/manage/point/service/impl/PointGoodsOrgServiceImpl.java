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
import com.baizhitong.resource.dao.point.PointGoodsOrgDao;
import com.baizhitong.resource.manage.point.service.IPointGoodsOrgService;
import com.baizhitong.resource.model.point.PointGoodsOrg;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.TimeUtils;

/**
 * 
 * 可兑商品-机构Service实现类
 * 
 * @author creator zhangqiang 2016年6月13日 下午2:17:30
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "pointGoodsOrgService")
public class PointGoodsOrgServiceImpl extends BaseService implements IPointGoodsOrgService {

    @Autowired
    private PointGoodsOrgDao pointGoodsOrgDao;
    @Autowired
    private ISysCodeService  sysCodeService;

    /**
     * 
     * 查询机构上架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    @Override
    public Page<PointGoodsOrg> getShelvesGoodsPage(Map<String, Object> param) {
        return pointGoodsOrgDao.selectShelvesGoodsPage(param);
    }

    /**
     * 
     * 查询机构下架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    @Override
    public Page<PointGoodsOrg> queryNextGoodsPage(Map<String, Object> param) {
        return pointGoodsOrgDao.selectNextGoodsPage(param);
    }

    /**
     * 
     * 礼品上架或下架操作
     * 
     * @param id 主键id
     * @param type 操作类型（1：下架；2：上架）
     * @return
     */
    @Override
    public int operateGoodsUpOrDown(Integer id, Integer type) {
        return pointGoodsOrgDao.updateValidTime(id, type);
    }

    /**
     * 
     * 新增或修改机构礼品
     * 
     * @param pointGoodsOrg
     * @return
     */
    @Override
    public ResultCodeVo saveOrgGift(PointGoodsOrg pointGoodsOrg) {
       
        UserInfoVo userInfo =getUserInfoVo();
        // 新增
        if (null == pointGoodsOrg.getId()) {
            pointGoodsOrg.setOrgCode(userInfo.getOrgCode());
            // 生成机构商品代码
            String goodsCode = sysCodeService.getCode(SysCodeConstants.ORG_GOODS_CODE, "orgCode",
                            userInfo.getOrgCode());
            pointGoodsOrg.setGoodsCode(goodsCode);
            pointGoodsOrg.setGoodsTotalAmount(99999999);
            pointGoodsOrg.setAddDate(new Date());
            pointGoodsOrg.setGoodsAmount(99999999);
            pointGoodsOrg.setValidTimeStart(Long.parseLong(TimeUtils.sysdate("yyyyMMddHHmmss")));
            pointGoodsOrg.setValidTimeEnd(Long.parseLong("99999999999999"));
            pointGoodsOrg.setFlagDelete(0);
            pointGoodsOrg.setCreator(userInfo.getUserName());
            pointGoodsOrg.setCreateTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            pointGoodsOrg.setCreatorIP(getIp());
            pointGoodsOrg.setModifier(userInfo.getUserName());
            pointGoodsOrg.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            pointGoodsOrg.setModifierIP(getIp());
            pointGoodsOrg.setSysVersion(0);
        } else {
            // 修改
            PointGoodsOrg old = pointGoodsOrgDao.getById(pointGoodsOrg.getId());
            pointGoodsOrg.setOrgCode(old.getOrgCode());
            pointGoodsOrg.setGoodsCode(old.getGoodsCode());
            pointGoodsOrg.setGoodsAmount(old.getGoodsAmount());
            pointGoodsOrg.setGoodsTotalAmount(old.getGoodsTotalAmount());
            pointGoodsOrg.setAddDate(old.getAddDate());
            pointGoodsOrg.setValidTimeStart(old.getValidTimeStart());
            pointGoodsOrg.setValidTimeEnd(old.getValidTimeEnd());
            pointGoodsOrg.setFlagDelete(old.getFlagDelete());
            pointGoodsOrg.setCreator(old.getCreator());
            pointGoodsOrg.setCreateTime(old.getCreateTime());
            pointGoodsOrg.setCreatorIP(old.getCreatorIP());
            pointGoodsOrg.setModifier(userInfo.getUserName());
            pointGoodsOrg.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            pointGoodsOrg.setModifierIP(getIp());
            pointGoodsOrg.setSysVersion(old.getSysVersion() + 1);
        }
        return pointGoodsOrgDao.add(pointGoodsOrg) ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
    }

    /**
     * 
     * 根据id查询机构礼品
     * 
     * @param id 主键id
     * @return
     */
    @Override
    public PointGoodsOrg getOrgGiftById(Integer id) {
        return pointGoodsOrgDao.getById(id);
    }

    /**
     * 
     * 删除机构礼品
     * 
     * @param id 主键id
     * @return
     */
    @Override
    public int deleteOrgGift(String id) {
        return pointGoodsOrgDao.delete(id);
    }

}
