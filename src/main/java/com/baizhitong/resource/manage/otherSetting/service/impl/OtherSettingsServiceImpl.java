package com.baizhitong.resource.manage.otherSetting.service.impl;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.clouddisk.CloudUserQuotaDao;
import com.baizhitong.resource.dao.share.SharePlatformCloudDiskParamDao;
import com.baizhitong.resource.dao.share.SharePlatformDao;
import com.baizhitong.resource.dao.share.SharePlatformSettingsResDao;
import com.baizhitong.resource.manage.otherSetting.service.OtherSettingsService;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.SharePlatformCloudDiskParam;
import com.baizhitong.resource.model.share.SharePlatformSettingsRes;
import com.baizhitong.utils.DateUtils;

/**
 * 
 * 后台其他设置Service实现类
 * 
 * @author creator ZhangQiang 2016年9月19日 上午10:03:45
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class OtherSettingsServiceImpl extends BaseService implements OtherSettingsService {

    /** 平台-资源中心设置数据接口 */
    @Autowired
    private SharePlatformSettingsResDao    sharePlatformSettingsResDao;
    /** 平台-云盘参数数据接口 */
    @Autowired
    private SharePlatformCloudDiskParamDao sharePlatformCloudDiskParamDao;
    /** 用户云盘配额数据接口 */
    @Autowired
    private CloudUserQuotaDao              cloudUserQuotaDao;
    /** 平台数据接口 */
    @Autowired
    private SharePlatformDao               sharePlatformDao;

    /**
     * 
     * (查询平台资源设置信息)<br>
     * 
     * @return
     * @throws Exception
     */
    @Override
    public SharePlatformSettingsRes query() throws Exception {
        return sharePlatformSettingsResDao.select();
    }

    /**
     * 
     * (保存资源评论设置的修改)<br>
     * 
     * @param entity 实体
     * @return
     * @throws Exception
     */
    @Override
    public ResultCodeVo saveChangeAllowResComment(SharePlatformSettingsRes entity) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        // 修改
        if (StringUtils.isNotEmpty(entity.getGid())) {
            entity.setModifier(userInfo.getUserName());
            entity.setModifyPgm("saveChangeAllowResComment");
            entity.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            entity.setModifyIP(getIp());
        } else {

        }
        boolean saveFlag = sharePlatformSettingsResDao.save(entity);
        if (saveFlag) {
            return ResultCodeVo.rightCode("保存成功！");
        } else {
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (根据用户身份查询云盘参数信息)<br>
     * 
     * @param userRole
     * @return
     * @throws Exception
     */
    @Override
    public SharePlatformCloudDiskParam queryCloudDiskInfoByUserRole(Integer userRole) throws Exception {
        SharePlatformCloudDiskParam info = sharePlatformCloudDiskParamDao.selectByUserRole(userRole);
        // 字节转换为G
        info.setCapacityQuota(info.getCapacityQuota() / 1024 / 1024 / 1024);
        return info;
    }

    /**
     * 
     * (保存云盘参数修改)<br>
     * 
     * @param request
     * @param entity
     * @return
     */
    @Override
    public ResultCodeVo saveCloudDiskParamModify(SharePlatformCloudDiskParam entity) throws Exception {
       
        String modifierIP = getIp();
        boolean saveFlag = false;
        int updateNum = 0;
        // 获取登录用户信息
        UserInfoVo userInfo =getUserInfoVo();
        if (StringUtils.isNotEmpty(entity.getGid())) {
            // 修改操作
            // 获取旧的云盘参数信息
            SharePlatformCloudDiskParam old = sharePlatformCloudDiskParamDao.selectByUserRole(entity.getUserRole());
            // GB转换为字节数
            Long newParam = entity.getCapacityQuota() * 1024 * 1024 * 1024;
            Long oldParam = old.getCapacityQuota();
            // 新设置的空间大小不能比原来的小，如果不符合给出错误提示
            if (newParam < oldParam) {
                return ResultCodeVo.errorCode("云盘空间大小不能小于原有设置！");
            }
            entity.setCapacityQuota(newParam);
            entity.setModifier(userInfo.getUserName());
            entity.setModifyIP(modifierIP);
            entity.setModifyPgm("saveCloudDiskParamModify");
            entity.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            entity.setSysVer(old.getSysVer() + 1);
            saveFlag = sharePlatformCloudDiskParamDao.insertOrUpdate(entity);
            updateNum = cloudUserQuotaDao.updateCapacitySizeByUserRole(entity.getUserRole(), newParam,
                            userInfo.getUserName(), modifierIP);
        } else {
            // 新增操作
        }
        if (saveFlag && 0 < updateNum) {
            return ResultCodeVo.rightCode("保存成功！");
        } else {
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (保存临时账号设置的修改)<br>
     * 
     * @param entity 实体
     * @return
     * @throws Exception
     */
    @Override
    public ResultCodeVo saveChangeAllowLogin(SharePlatform entity) throws Exception {
       
        // 修改
        if (StringUtils.isNotEmpty(entity.getGid())) {
            entity.setModifyPgm("saveChangeAllowLogin");
            entity.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            entity.setModifyIP(getIp());
        } else {

        }
        boolean saveFlag = sharePlatformDao.add(entity);
        String platformInfoVer = UUID.randomUUID().toString();
        // 将平台信息写入Cookie
        BeanHelper.writePlatformToCookie(platformInfoVer);
        if (saveFlag) {
            return ResultCodeVo.rightCode("保存成功！");
        } else {
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

}
