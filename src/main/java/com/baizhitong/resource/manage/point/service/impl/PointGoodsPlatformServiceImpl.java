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
import com.baizhitong.resource.dao.point.PointGoodsPlatformDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.manage.point.service.IPointGoodsPlatformService;
import com.baizhitong.resource.model.point.PointGoodsPlatform;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.syscode.consts.SysCodeConstants;
import com.baizhitong.syscode.frontend.service.ISysCodeService;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.TimeUtils;

/**
 * 
 * 可兑商品-平台Service实现类
 * 
 * @author creator zhangqiang 2016年6月27日 下午8:27:30
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "pointGoodsPlatformService")
public class PointGoodsPlatformServiceImpl extends BaseService implements IPointGoodsPlatformService {

    @Autowired
    private PointGoodsPlatformDao pointGoodsPlatformDao;
    @Autowired
    private ISysCodeService       sysCodeService;
    @Autowired
    private SharePlatformDao      sharePlatformDao;

    /**
     * 
     * 查询平台上架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    @Override
    public Page<PointGoodsPlatform> getShelvesGoodsPage(Map<String, Object> param) {
        return pointGoodsPlatformDao.selectShelvesGoodsPage(param);
    }

    /**
     * 
     * 查询平台下架礼品分页信息
     * 
     * @param param 查询参数
     * @return
     */
    @Override
    public Page<PointGoodsPlatform> queryNextGoodsPage(Map<String, Object> param) {
        return pointGoodsPlatformDao.selectNextGoodsPage(param);
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
        return pointGoodsPlatformDao.updateValidTime(id, type);
    }

    /**
     * 
     * 新增或修改平台礼品
     * 
     * @param pointGoodsOrg
     * @return
     */
    @Override
    public ResultCodeVo savePlatformGift(PointGoodsPlatform pointGoodsPlatform) {
       
        UserInfoVo userInfo =getUserInfoVo();
        // 新增
        if (null == pointGoodsPlatform.getId()) {
            // 生成平台商品代码
            SharePlatform platform = sharePlatformDao.getByCodeGlobal();
            String goodsCode = sysCodeService.getCode(SysCodeConstants.PLATFROM_GOODS_CODE, "platformCode",
                            platform.getCodeGlobal(), "dateTime", TimeUtils.formatDate(new Date(), "YYYYMMdd"));
            pointGoodsPlatform.setGoodsCode(goodsCode);
            pointGoodsPlatform.setGoodsAmount(99999999);
            pointGoodsPlatform.setGoodsTotalAmount(99999999);
            pointGoodsPlatform.setAddDate(new Date());
            pointGoodsPlatform.setValidTimeStart(Long.parseLong(TimeUtils.sysdate("yyyyMMddHHmmss")));
            pointGoodsPlatform.setValidTimeEnd(Long.parseLong("99999999999999"));
            pointGoodsPlatform.setFlagDelete(0);
            pointGoodsPlatform.setCreator(userInfo.getUserName());
            pointGoodsPlatform.setCreateTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            pointGoodsPlatform.setCreatorIP(getIp());
            pointGoodsPlatform.setModifier(userInfo.getUserName());
            pointGoodsPlatform.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            pointGoodsPlatform.setModifierIP(getIp());
            pointGoodsPlatform.setSysVersion(0);
        } else {
            // 修改
            PointGoodsPlatform old = pointGoodsPlatformDao.getById(pointGoodsPlatform.getId());
            pointGoodsPlatform.setGoodsCode(old.getGoodsCode());
            pointGoodsPlatform.setGoodsAmount(old.getGoodsAmount());
            pointGoodsPlatform.setGoodsTotalAmount(old.getGoodsTotalAmount());
            pointGoodsPlatform.setAddDate(old.getAddDate());
            pointGoodsPlatform.setValidTimeStart(old.getValidTimeStart());
            pointGoodsPlatform.setValidTimeEnd(old.getValidTimeEnd());
            pointGoodsPlatform.setFlagDelete(old.getFlagDelete());
            pointGoodsPlatform.setCreator(old.getCreator());
            pointGoodsPlatform.setCreateTime(old.getCreateTime());
            pointGoodsPlatform.setCreatorIP(old.getCreatorIP());
            pointGoodsPlatform.setModifier(userInfo.getUserName());
            pointGoodsPlatform.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            pointGoodsPlatform.setModifierIP(getIp());
            pointGoodsPlatform.setSysVersion(old.getSysVersion() + 1);
        }
        return pointGoodsPlatformDao.add(pointGoodsPlatform) ? ResultCodeVo.rightCode("保存成功")
                        : ResultCodeVo.errorCode("保存失败");
    }

    /**
     * 
     * 根据id查询平台礼品
     * 
     * @param id 主键id
     * @return
     */
    @Override
    public PointGoodsPlatform getPlatformGiftById(Integer id) {
        return pointGoodsPlatformDao.getById(id);
    }

    /**
     * 
     * 删除平台礼品
     * 
     * @param id 主键id
     * @return
     */
    @Override
    public int deletePlatformGift(String id) {
        return pointGoodsPlatformDao.delete(id);
    }
}
