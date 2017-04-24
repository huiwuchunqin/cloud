package com.baizhitong.resource.demo.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.baizhitong.resource.common.core.vo.ResultCodeVo;

/**
 * 测试模块接口
 * 
 * @author creator cuidc 2015/12/01
 * @author updater
 */
public interface DemoService {

    /**
     * 获取测试信息
     * 
     * @param request http请求对象
     * @param map 数据模型
     * @return 信息
     */
    public ResultCodeVo getDemoInfo(HttpServletRequest request, ModelMap map);
}