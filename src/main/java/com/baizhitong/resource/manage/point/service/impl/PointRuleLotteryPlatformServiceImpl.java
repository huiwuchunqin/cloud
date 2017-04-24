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
import com.baizhitong.resource.dao.point.PointRuleLotteryPlatformDao;
import com.baizhitong.resource.manage.point.service.IPointRuleLotteryPlatformService;
import com.baizhitong.resource.model.point.PointRuleLotteryPlatform;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.TimeUtils;

@Service
public class PointRuleLotteryPlatformServiceImpl extends BaseService implements IPointRuleLotteryPlatformService {
    private @Autowired PointRuleLotteryPlatformDao rulePlatformDao;

    /**
     * 查询积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    public Page getList(Integer page, Integer rows) {
        return rulePlatformDao.getList(page, rows);
    }

    /**
     * 机构积分规则 ()<br>
     * 
     * @param rulePlatform
     * @return
     */
    public ResultCodeVo add(PointRuleLotteryPlatform rulePlatform) {
       
        UserInfoVo user =getUserInfoVo();
        if (rulePlatform.getId() == null) { // 新增
            rulePlatform.setBizVersion(Integer.parseInt(TimeUtils.formatDate(new Date(), "YYYYMMDD")));
            rulePlatform.setExpireTime(Long.parseLong("99999999999999"));
            rulePlatform.setFlagDelete(0);
            rulePlatform.setModifier(user.getUserName());
            rulePlatform.setModifierIP(getIp());
            rulePlatform.setModifyTime(new Timestamp(new Date().getTime()));
            rulePlatform.setStartTime(Long.parseLong(TimeUtils.sysdate("yyyyMMddHHmmss")));
            // 让之前的失效
            rulePlatformDao.makeExpire();
            // 新增
            boolean success = rulePlatformDao.add(rulePlatform);
            return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
        } else {// 修改
            PointRuleLotteryPlatform old = rulePlatformDao.getById(rulePlatform.getId());
            old.setPoint(rulePlatform.getPoint());
            old.setDayMaxTimes(rulePlatform.getDayMaxTimes());
            old.setFlagDelete(0);
            old.setMemo(rulePlatform.getMemo());
            old.setModifier(user.getUserName());
            old.setModifierIP(getIp());
            old.setModifyTime(new Timestamp(new Date().getTime()));
            boolean success = rulePlatformDao.add(old);
            return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
        }
    }

    /**
     * 查询机构积分规则 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRuleLotteryPlatform getById(Integer id) {
        return rulePlatformDao.getById(id);
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public ResultCodeVo delete(Integer id) {
        int count = rulePlatformDao.delete(id);
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }
}
