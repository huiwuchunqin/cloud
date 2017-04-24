package com.baizhitong.resource.manage.log.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.dao.log.LoginLogDao;
import com.baizhitong.resource.manage.log.service.ILoginLogService;
import com.baizhitong.utils.TimeUtils;

/**
 * 登录接口 LoginLogServiceImpl TODO
 * 
 * @author creator BZT 2016年9月28日 下午3:11:55
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class LoginLogServiceImpl implements ILoginLogService {
    @Autowired
    LoginLogDao loginLogDao;

    /**
     * 插叙登录日志 ()<br>
     * 
     * @param sqlParam
     * @param page
     * @param row
     * @return
     */
    public Page getLoginLog(Map<String, Object> sqlParam, Integer page, Integer row) {
        return loginLogDao.getLoginLogPage(sqlParam, page, row);
    }

    /**
     * 格式化 ()<br>
     * 
     * @param list
     * @return
     */
    public void format(List<Map<String, Object>> list) {
        if (list != null && list.size() > 0) {
            for (Map map : list) {
                Integer deviceType = MapUtils.getInteger(map, "deviceType");
                String deviceTypeStr = "";
                switch (deviceType) {
                    case CoreConstants.DEVICE_TYPE_PC:
                        deviceTypeStr = "PC";
                        break;
                    case CoreConstants.DEVICE_TYPE_ANDROID:
                        deviceTypeStr = "安卓";
                        break;
                    case CoreConstants.DEVICE_TYPE_ANDROID_PAD:
                        deviceTypeStr = "安卓平板";
                        break;
                    case CoreConstants.DEVICE_TYPE_IPAD:
                        deviceTypeStr = "ipad";
                        break;
                    case CoreConstants.DEVICE_TYPE_IPHONE:
                        deviceTypeStr = "iphone";
                        break;
                }
                Integer loginType = MapUtils.getInteger(map, "loginType");
                String loginTypeStr = "";
                switch (loginType) {
                    case 10:
                        loginTypeStr = "用户账号+密码登录";
                        break;
                    case 11:
                        loginTypeStr = "手机号+密码登录";
                        break;
                    case 12:
                        loginTypeStr = "邮箱名+密码登录";
                        break;
                    case 20:
                        loginTypeStr = "手机APP扫码登录";
                        break;
                    case 30:
                        loginTypeStr = "手机验证码登录";
                        break;
                }
                String loginTime=MapUtils.getString(map, "loginTime");
                map.put("loginTime", TimeUtils.formatDate(loginTime, "yyyy-MM-dd HH:mm:ss"));
                map.put("deviceTypeStr", deviceTypeStr);
                map.put("loginTypeStr", loginTypeStr);
            }
        }
    }
}
