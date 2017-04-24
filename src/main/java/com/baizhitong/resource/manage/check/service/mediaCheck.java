package com.baizhitong.resource.manage.check.service;

import org.springframework.stereotype.Component;

import com.baizhitong.check.servicecheck.service.CheckSetting;
import com.baizhitong.check.servicecheck.service.IServiceCheck;
import com.baizhitong.check.servicecheck.vo.CheckResult;
import com.baizhitong.utils.PropertieUtils;
import com.baizhitong.utils.StringUtils;

@CheckSetting(name = "媒体访问",
              settings = { "docHost", "imgHost", "videoHost", "videoConvertHost" })
@Component
public class mediaCheck implements IServiceCheck {

    @Override
    public CheckResult check() {
        StringBuffer msg = new StringBuffer();
        PropertieUtils propertieUtils = new PropertieUtils("/settings.properties");
        String docHost = propertieUtils.getString("docHost");
        String imgHost = propertieUtils.getString("imgHost");
        String videoHost = propertieUtils.getString("videoHost");
        String videoConvertHost = propertieUtils.getString("videoConvertHost");
        if (StringUtils.isEmpty(docHost)) {
            msg.append("docHost:文档访问地址为空，");
        }
        if (StringUtils.isEmpty(imgHost)) {
            msg.append("imgHost:图片访问地址为空，");
        }
        if (StringUtils.isEmpty(videoHost)) {
            msg.append("videoHost:视频访问地址为空，");
        }
        if (StringUtils.isEmpty(videoConvertHost)) {
            msg.append("videoConvertHost：视频转码地址为空，");
        }
        if (StringUtils.isNotEmpty(msg.toString())) {
            return CheckResult.unsuccess(msg.toString());
        } else {
            return CheckResult.success("媒体访问正常");
        }

    }

}
