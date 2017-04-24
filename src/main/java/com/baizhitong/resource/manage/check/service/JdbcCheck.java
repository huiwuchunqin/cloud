package com.baizhitong.resource.manage.check.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.baizhitong.check.servicecheck.service.CheckSetting;
import com.baizhitong.check.servicecheck.service.IServiceCheck;
import com.baizhitong.check.servicecheck.vo.CheckResult;
import com.baizhitong.utils.PropertieUtils;

@CheckSetting(name = "数据库连接",
              settings = { "jdbc.driverClassName", "jdbc.url", "jdbc.username", "jdbc.password" })
@Component
public class JdbcCheck implements IServiceCheck {

    @Override
    public CheckResult check() {
        PropertieUtils propertieUtils = new PropertieUtils("/settings.properties");
        /* String driverClassName=propertieUtils.getProperty("jdbc.driverClassName"); */
        String userName = propertieUtils.getProperty("jdbc.username");
        String password = propertieUtils.getProperty("jdbc.password");
        String url = propertieUtils.getProperty("jdbc.url");
        try {
            DriverManager.setLoginTimeout(1);
            Connection con = DriverManager.getConnection(url, userName, password);
            return CheckResult.success("数据库连接正常");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            return CheckResult.unsuccess("数据库连接配置错误");
        }
    }
}
