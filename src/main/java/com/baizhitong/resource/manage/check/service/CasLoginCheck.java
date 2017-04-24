package com.baizhitong.resource.manage.check.service;

import org.springframework.stereotype.Component;

import com.baizhitong.check.servicecheck.service.CheckSetting;
import com.baizhitong.check.servicecheck.service.IServiceCheck;
import com.baizhitong.check.servicecheck.vo.CheckResult;
import com.baizhitong.utils.PropertieUtils;
import com.baizhitong.utils.StringUtils;

@CheckSetting(name = "cas登录",
              settings = { "casEnable", "casHost", "plsServerUrl" })
@Component
public class CasLoginCheck implements IServiceCheck {

    @Override
    public CheckResult check() {
        PropertieUtils propertieUtils = new PropertieUtils("/settings.properties");
        Boolean casEnable = propertieUtils.getBoolean("casEnable");
        String casHost = propertieUtils.getString("casHost");
        String plsServerUrl = propertieUtils.getString("plsServerUrl");
        if (casEnable) {
            if (StringUtils.isEmpty(casHost)) {
                return CheckResult.unsuccess("cas地址不能为空");
            }
            if (StringUtils.isEmpty(plsServerUrl)) {
                return CheckResult.unsuccess("空间首页地址不能为空");
            }
        }
        return CheckResult.success("cas配置正常");
    }

}
