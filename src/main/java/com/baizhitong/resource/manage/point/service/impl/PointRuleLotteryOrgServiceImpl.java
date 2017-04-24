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
import com.baizhitong.resource.dao.point.PointRuleLotteryOrgDao;
import com.baizhitong.resource.manage.point.service.IPointRuleLotteryOrgService;
import com.baizhitong.resource.model.point.PointRuleLotteryOrg;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.TimeUtils;

/**
 * 机构积分规则 PointRuleLotteryOrgServiceImpl TODO
 * 
 * @author creator gaow 2016年6月23日 下午5:12:27
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class PointRuleLotteryOrgServiceImpl extends BaseService implements IPointRuleLotteryOrgService {
    private @Autowired PointRuleLotteryOrgDao ruleOrgDao;

    /**
     * 查询积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    public Page getList(String orgCode, Integer page, Integer rows) {
        return ruleOrgDao.getList(orgCode, page, rows);
    }

    /**
     * 机构积分规则 ()<br>
     * 
     * @param ruleOrg
     * @return
     */
    public ResultCodeVo add(PointRuleLotteryOrg ruleOrg) {
       
        UserInfoVo user =getUserInfoVo();
        if (ruleOrg.getId() == null) { // 新增
            ruleOrg.setBizVersion(Integer.parseInt(TimeUtils.formatDate(new Date(), "YYYYMMDD")));
            ruleOrg.setDispOrder(1);
            ruleOrg.setExpireTime(Long.parseLong("99999999999999"));
            ruleOrg.setFlagDelete(0);
            ruleOrg.setModifier(user.getUserName());
            ruleOrg.setModifierIP(getIp());
            ruleOrg.setModifyTime(new Timestamp(new Date().getTime()));
            ruleOrg.setOrgCode(user.getOrgCode());
            ruleOrg.setStartTime(Long.parseLong(TimeUtils.sysdate("yyyyMMddHHmmss")));
            // 让之前的失效
            ruleOrgDao.makeExpire(user.getOrgCode());
            // 新增
            boolean success = ruleOrgDao.add(ruleOrg);
            return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
        } else {// 修改
            PointRuleLotteryOrg old = ruleOrgDao.getById(ruleOrg.getId());
            old.setPoint(ruleOrg.getPoint());
            old.setDayMaxTimes(ruleOrg.getDayMaxTimes());
            old.setFlagDelete(0);
            old.setMemo(ruleOrg.getMemo());
            old.setModifier(user.getUserName());
            old.setModifierIP(getIp());
            old.setModifyTime(new Timestamp(new Date().getTime()));
            boolean success = ruleOrgDao.add(old);
            return success ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
        }
    }

    /**
     * 查询机构积分规则 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRuleLotteryOrg getById(Integer id) {
        return ruleOrgDao.getById(id);
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    public ResultCodeVo delete(Integer id) {
        int count = ruleOrgDao.delete(id);
        return count > 0 ? ResultCodeVo.rightCode("删除成功") : ResultCodeVo.errorCode("删除失败");
    }
}
