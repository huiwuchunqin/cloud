package com.baizhitong.resource.manage.check.service;

import java.io.IOException;
import java.net.SocketException;

import org.springframework.stereotype.Component;

import com.baizhitong.check.servicecheck.service.CheckSetting;
import com.baizhitong.check.servicecheck.service.IServiceCheck;
import com.baizhitong.check.servicecheck.vo.CheckResult;
import com.baizhitong.utils.PropertieUtils;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.ftp.FtpClient;

@CheckSetting(name = "FTP检测连接",
              settings = { "ftp.url", "ftp.port", "ftp.username", "ftp.password" })
@Component
public class FtpCheck implements IServiceCheck {

    @Override
    public CheckResult check() {
        // TODO Auto-generated method stub
        PropertieUtils propertieUtils = new PropertieUtils("/settings.properties");
        String ip = propertieUtils.getProperty("ftp.url");
        Integer port = propertieUtils.getInteger("ftp.port");
        String userName = propertieUtils.getProperty("ftp.username");
        String password = propertieUtils.getProperty("ftp.password");
        FtpClient client = new FtpClient();
        try {
            client.setConnectTimeout(500);
            client.connect(ip, port);
            boolean success = client.login(userName, password);
            if (!success) {
                return CheckResult.unsuccess(StringUtils.format("ftp用户名或密码错误,用户名{0}:密码{1}", userName, password));
            } else {
                return CheckResult.success("ftp连接正常");
            }
        } catch (SocketException e) {
            return CheckResult.unsuccess(StringUtils.format("ftp地址或者端口错误地址,{0}:端口{1}", ip, port));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            return CheckResult.unsuccess(StringUtils.format("ftp地址或者端口错误,地址{0}:端口{1}", ip, port));
        } finally {

        }
    }
}
