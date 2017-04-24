package com.baizhitong.resource.demo.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.demo.DemoDao;
import com.baizhitong.resource.demo.service.DemoService;
import com.baizhitong.resource.model.demo.Demo;

/**
 * 测试模块接口实现
 * 
 * @author creator cuidc 2015/12/01
 * @author updater
 */
@Service("demoService")
public class DemoServiceImpl implements DemoService {
    /** 测试数据接口 */
    private @Autowired DemoDao demoDao;

    /**
     * 获取测试信息
     * 
     * @param request http请求对象
     * @param map 数据模型
     * @return 信息
     */
    @Override
    public ResultCodeVo getDemoInfo(HttpServletRequest request, ModelMap map) {
        List<Demo> list = demoDao.select();
        return new ResultCodeVo(true, "", "", list);
    }
}