package com.baizhitong.resource.manage.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.dao.report.OrgResTypeMakeDailyDao;
import com.baizhitong.resource.manage.report.service.OrgResTypeMakeDailyService;

/**
 * OrgResTypeMakeDailyServiceImpl 机构日次资源类别统计接口
 * 
 * @author creator BZT 2016年7月16日 下午1:14:21
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class OrgResTypeMakeDailyServiceImpl implements OrgResTypeMakeDailyService {
    /**
     * 机构日次资源类别制作统计Dao
     */
    private @Autowired OrgResTypeMakeDailyDao resTypeMakeDailyDao;

    /**
     * 查询资源分类列表 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getResTypeMakeDaily(Map<String, Object> param) {
        List<Map<String, Object>> mapList = resTypeMakeDailyDao.getRestypeMakeDaily(param);
        // if(mapList!=null&&mapList.size()>0){
        // for(Map<String,Object> map: mapList){
        // Integer resTypeL1=MapUtils.getInteger(map, "resTypeL1");
        //// if(CoreConstants.RES_TYPE_QUESTION.equals(resTypeL1)){
        //// map.put("areaNum", "-");
        //// }
        // }
        // }
        return mapList;
    }

    /**
     * 查询所有机构资源分类汇总 ()<br>
     * 
     * @param param
     * @return
     */
    public List<Map<String, Object>> getAllCompanyResTypeMakeDaily(Map<String, Object> param) {
        return resTypeMakeDailyDao.getAllCompanyResTypeMakeDaily(param);

    }
}
