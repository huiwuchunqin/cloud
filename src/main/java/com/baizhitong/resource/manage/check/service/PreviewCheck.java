package com.baizhitong.resource.manage.check.service;

import org.springframework.stereotype.Component;

import com.baizhitong.check.servicecheck.service.CheckSetting;
import com.baizhitong.check.servicecheck.service.IServiceCheck;
import com.baizhitong.check.servicecheck.vo.CheckResult;
import com.baizhitong.utils.PropertieUtils;
import com.baizhitong.utils.StringUtils;

@CheckSetting(name = "资源预览",
              settings = { "webUrl", "questionPreviewUrl", "exercisePreviewUrl", "lessonPreviewUrl", "mediaPreviewUrl",
                              "docPreviewUrl" })
@Component
public class PreviewCheck implements IServiceCheck {

    @Override
    public CheckResult check() {
        StringBuffer msg = new StringBuffer();
        PropertieUtils propertieUtils = new PropertieUtils("/settings.properties");
        String exercisePreviewUrl = propertieUtils.getProperty("exercisePreviewUrl");
        String questionPreviewUrl = propertieUtils.getProperty("questionPreviewUrl");
        String mediaPreviewUrl = propertieUtils.getProperty("mediaPreviewUrl");
        String docPreviewUrl = propertieUtils.getProperty("docPreviewUrl");
        String webUrl = propertieUtils.getProperty("webUrl");
        String lessonPreviewUrl = propertieUtils.getProperty("lessonPreviewUrl");
        if (StringUtils.isEmpty(lessonPreviewUrl)) {
            msg.append("lessonPreviewUrl:没有课程预览地址,");
        }
        if (StringUtils.isEmpty(exercisePreviewUrl)) {
            msg.append("exercisePreviewUrl:没有测验预览地址,");
        }
        if (StringUtils.isEmpty(questionPreviewUrl)) {
            msg.append("questionPreviewUrl:没有题目预览地址,");
        }
        if (StringUtils.isEmpty(mediaPreviewUrl)) {
            msg.append("mediaPreviewUrl:没有视频预览地址,");
        }
        if (StringUtils.isEmpty(docPreviewUrl)) {
            msg.append("docPreviewUrl:没有文档预览地址,");
        }
        if (StringUtils.isEmpty(webUrl)) {
            msg.append("webUrl:没有前台地址,");
        }
        if (StringUtils.isNotEmpty(msg.toString())) {
            return CheckResult.unsuccess(msg.toString());
        } else {
            return CheckResult.success("资源预览正常");
        }
    }

}
