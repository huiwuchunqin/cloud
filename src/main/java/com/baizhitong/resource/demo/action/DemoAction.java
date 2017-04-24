package com.baizhitong.resource.demo.action;

import javax.servlet.http.HttpServletRequest;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.utils.FtpHelper;
import com.baizhitong.utils.ftp.FtpClientProxy;
import com.baizhitong.utils.ftp.FtpConPoolManager;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.demo.service.DemoService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.zip.CRC32;

/**
 * 测试模块控制器
 * 
 * @author creator cuidc 2015/12/01
 * @author updater
 */
@Controller
@RequestMapping("/demo")
public class DemoAction extends BaseAction {
    /** 测试模块接口 */
    private @Autowired DemoService demoService;

    /**
     * 获取测试信息
     * 
     * @param request http请求对象
     * @param map 数据模型
     * @return 信息
     */
    @RequestMapping("/index.html")
    public @ResponseBody ResultCodeVo getDemoInfo(HttpServletRequest request, ModelMap map) {
        return demoService.getDemoInfo(request, map);
    }

    /**
     * webuploader demo页面
     * 
     * @return
     */
    @RequestMapping("/webuploader.html")
    public String jumpToWebUpload() {
        return "/demo/webuploader.html";
    }

    /**
     * webuploader 上传接口
     * 
     * @return
     */
    @RequestMapping(value = "/webuploader.html",
                    params = "do=upload")
    public @ResponseBody ResultCodeVo webuploader_upload(HttpServletRequest request, MultipartFile file) {
        return FtpHelper.ftpUploadFile(file, FtpHelper.DEMO);
    }
}