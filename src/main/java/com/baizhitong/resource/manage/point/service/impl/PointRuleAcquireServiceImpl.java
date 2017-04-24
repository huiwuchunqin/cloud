package com.baizhitong.resource.manage.point.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.point.PointRuleAcquireDao;
import com.baizhitong.resource.dao.point.PointRuleAcquireOrgDao;
import com.baizhitong.resource.manage.point.service.IPointRuleAcquireService;
import com.baizhitong.resource.model.point.PointRuleAcquire;
import com.baizhitong.resource.model.point.PointRuleAcquireOrg;

@Service
public class PointRuleAcquireServiceImpl implements IPointRuleAcquireService {
    @Autowired
    PointRuleAcquireDao    pointRuleAcquireDao;
    @Autowired
    PointRuleAcquireOrgDao pointRuleAcquireOrgDao;

    /**
     * 查询积分规则列表 ()<br>
     * 
     * @param param
     * @param page
     * @param rows
     * @return
     */
    public Page getPage(Map<String, Object> param, Integer page, Integer rows) {
        return pointRuleAcquireDao.getAcquirePage(param, page, rows);
    }

    /**
     * 修改 ()<br>
     * 
     * @param pointOrg
     * @param pointPlat
     * @param actionCode
     * @return
     */
    public ResultCodeVo modify(String pointOrg, String pointPlat, String actionCode) {
        PointRuleAcquire acquire = pointRuleAcquireDao.getByActionCode(actionCode);
        acquire.setAlgorithmsJson(pointPlat);
        acquire.setAlgorithmsJsonOrg(pointOrg);
        boolean success = pointRuleAcquireDao.updatePointRuleAcquire(acquire);
        int count2 = pointRuleAcquireOrgDao.update(actionCode, pointOrg, pointPlat);
        if (success && count2 >= 0) {
            return ResultCodeVo.rightCode("修改成功");
        } else {
            return ResultCodeVo.errorCode("修改失败");
        }

    }

    /**
     * 通过code查询 ()<br>
     * 
     * @param actionCode
     * @return
     */
    public PointRuleAcquire getByCode(String actionCode) {
        return pointRuleAcquireDao.getByActionCode(actionCode);
    }
}
