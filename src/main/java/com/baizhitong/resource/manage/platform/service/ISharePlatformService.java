package com.baizhitong.resource.manage.platform.service;

import java.util.List;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;
import com.baizhitong.resource.model.share.SharePlatform;

/**
 * 
 * 平台Service
 * 
 * @author creator ZhangQiang 2016年8月30日 下午1:49:46
 * @author updater
 *
 * @version 1.0.0
 */
public interface ISharePlatformService {

    /**
     * 
     * (查询平台信息)<br>
     * 
     * @return
     * @throws Exception
     */
    SharePlatform queryPlatformInfo() throws Exception;

    /**
     * 
     * (保存或修改平台信息)<br>
     * 
     * @param entity 平台信息实体
     * @return
     * @throws Exception
     */
    ResultCodeVo addPlatformInfo(SharePlatform entity) throws Exception;

    /**
     * 
     * (查询所有学年列表)<br>
     * 
     * @return
     * @throws Exception
     */
    List<ShareCodeStudyYear> queryStudyYearList() throws Exception;

    /**
     * 
     * (检查平台学年学期表和机构学年学期表对应的开始时间是否需要更新)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @param currentDate 当前日期
     * @return 检测结果
     * @throws Exception
     */
    ResultCodeVo check(String yearTermCode, String currentDate) throws Exception;
}
