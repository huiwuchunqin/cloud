package com.baizhitong.resource.manage.point.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.point.PointRuleAcquireOrgDao;
import com.baizhitong.resource.manage.point.service.IPointRuleAcquireOrgService;
import com.baizhitong.resource.model.point.PointRuleAcquireOrg;

@Service
public class PointRuleAcquireOrgServiceImpl implements IPointRuleAcquireOrgService {
    private @Autowired PointRuleAcquireOrgDao pointRuleAcquireOrgDao;

    /**
     * 查询积分规则列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows) {
        return pointRuleAcquireOrgDao.getAcquirePage(param, page, rows);
    }

    /**
     * 修改 ()<br>
     * 
     * @param pointOrg
     * @param id
     * @return
     */
    public ResultCodeVo modify(String pointOrg, Integer id) {
        PointRuleAcquireOrg ruleAcquireOrg = pointRuleAcquireOrgDao.getById(id);
        ruleAcquireOrg.setAlgorithmsJsonOrg(pointOrg);
        ruleAcquireOrg.setSettingType(CoreConstants.SETTING_TYPE_NOT_EXTEND);
        boolean success = pointRuleAcquireOrgDao.updatePointRuleAcquire(ruleAcquireOrg);
        return success ? ResultCodeVo.rightCode("修改成功") : ResultCodeVo.errorCode("修改失败");
    }

    /**
     * 通过code查询 ()<br>
     * 
     * @param id
     * @return
     */
    public PointRuleAcquireOrg getById(Integer id) {
        return pointRuleAcquireOrgDao.getById(id);
    }

    /**
     * 把平台导入机构 ()<br>
     * 
     * @param orgCode
     * @return
     */
    public ResultCodeVo importDefaultToOrg(String orgCode) {
        int count = pointRuleAcquireOrgDao.importDefaultToOrg(orgCode);
        return count >= 0 ? ResultCodeVo.rightCode("保存成功") : ResultCodeVo.errorCode("保存失败");
    }
}
