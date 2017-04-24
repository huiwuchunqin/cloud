package com.baizhitong.resource.manage.platform.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.share.ShareCodeStudyYearDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.SharePlatformYearTermDao;
import com.baizhitong.resource.manage.platform.service.ISharePlatformService;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.SharePlatformYearTerm;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * 平台Service实现类
 * 
 * @author creator ZhangQiang 2016年8月30日 下午1:52:05
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "sharePlatformService")
public class SharePlatformServiceImpl extends BaseService implements ISharePlatformService {

    /** 平台数据接口 */
    @Autowired
    private SharePlatformDao         sharePlatformDao;
    /** 学年数据接口 */
    @Autowired
    private ShareCodeStudyYearDao    shareCodeStudyYearDao;
    /** 平台学年学期数据接口 */
    @Autowired
    private SharePlatformYearTermDao sharePlatformYearTermDao;

    /**
     * 
     * (查询平台信息)<br>
     * 
     * @return
     * @throws Exception
     */
    @Override
    public SharePlatform queryPlatformInfo() throws Exception {
        return sharePlatformDao.getByCodeGlobal();
    }

    /**
     * 
     * (保存或修改平台信息)<br>
     * 
     * @param entity
     * @return
     * @throws Exception
     */
    @Override
    public ResultCodeVo addPlatformInfo(SharePlatform entity) throws Exception {
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        entity.setModifyIP(getIp());
        entity.setModifyPgm("sharePlatformService");
        entity.setModifyTime(currentTime);
        boolean addFlag = sharePlatformDao.add(entity);
        if (addFlag) {
            String platformInfoVer = UUID.randomUUID().toString();
            // 将平台信息写入Cookie
            BeanHelper.writePlatformToCookie(platformInfoVer);
            return ResultCodeVo.rightCode("保存成功！");
        } else {
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (查询所有学年列表)<br>
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List<ShareCodeStudyYear> queryStudyYearList() throws Exception {
        return shareCodeStudyYearDao.selectList();
    }

    /**
     * 
     * (检查平台学年学期表和机构学年学期表对应的开始时间是否需要更新)<br>
     * 
     * @param yearTermCode 学年学期编码
     * @param currentDate 当前日期
     * @return 检测结果
     * @throws Exception
     */
    @Override
    public ResultCodeVo check(String yearTermCode, String currentDate) throws Exception {
        // 将当前日期格式化成时间戳
        Timestamp currentTime = new Timestamp(DateUtils.getDateTime(currentDate, "yyyy-MM-dd").getTime());
        List<SharePlatformYearTerm> dataList = sharePlatformYearTermDao.selectListByYearTermCode(yearTermCode,
                        currentTime);
        return ResultCodeVo.rightCode("查询成功！", dataList);
    }

}
