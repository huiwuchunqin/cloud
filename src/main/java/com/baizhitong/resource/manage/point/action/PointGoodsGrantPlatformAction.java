package com.baizhitong.resource.manage.point.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.company.ShareOrgDao;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.point.service.IPointGoodsGrantPlatfromService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

@Controller
@RequestMapping("/manage/pointGoodsGrantPlatform")
public class PointGoodsGrantPlatformAction extends BaseAction {
    private @Autowired IPointGoodsGrantPlatfromService pointGoodsGrantPlatfromService;
    private @Autowired ShareOrgDao                     shareOrgDao;

    /**
     * 跳转到商品发放概要 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toGrant.html")
    public String toGrantPlatform(ModelMap model) {
        return "/manage/point/pointGoodsGrantPlatfrom/pointGoodsGrantPlatfrom.html";
    }

    /**
     * 查询发放概要列表 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping("/list.html")
    public Page getList(HttpServletRequest request, String orgName, String goodsName, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgName", orgName);
        param.put("goodsName", goodsName);
        return pointGoodsGrantPlatfromService.getList(param, page, rows);
    }

    /**
     * 插入商品发放概要 ()<br>
     * 
     * @return
     */
    @RequestMapping("/insert.html")
    @ResponseBody
    public ResultCodeVo insert(String orgName, String goodsName) {
        return pointGoodsGrantPlatfromService.insert(orgName, goodsName);
    }

}
