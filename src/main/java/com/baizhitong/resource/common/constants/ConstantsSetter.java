package com.baizhitong.resource.common.constants;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Repository;

import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.utils.JsonUtils;

@Repository()

public class ConstantsSetter extends BaseService implements IViewPropSetter {

    /*
     * 读取配置文件的属性，并自动添加到freemarker范围 (non-Javadoc)
     * 
     * @see com.baizhitong.mvc.freemarker.IViewPropSetter#props()
     */
    @Override
    public Map<String, Object> props() {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/settings.properties"));
            // 静态文件库
            // properties.put("bztSyscommHost", properties.get("bzt.syscomm.host"));
            properties.put("videoHost", properties.get("videoHost"));
            properties.put("docHost", properties.get("docHost"));
            properties.put("imgHost", properties.get("imgHost"));
            // properties.put("videoConvertHost", properties.get("videoConvertHost"));
            // properties.put("datadepotHost", properties.get("datadepotHost"));
            // properties.put("convertHost", properties.get("convertHost"));
            UserInfoVo adminUser = BeanHelper.getAdminUserFromCookie(super.getRequest());
            if (adminUser != null) {
                properties.put("adminUser", adminUser);
            }
        } catch (IOException e) {
            // log.error("读取配置文件 db.properties 异常~",e);
        }

        return new HashMap<String, Object>((Map) properties);
    }

}
