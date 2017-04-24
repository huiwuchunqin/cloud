package com.baizhitong.resource.manage.point.action;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.point.service.IPointGoodsPlatformService;
import com.baizhitong.resource.manage.point.service.IPointGoodsPutPlatformService;
import com.baizhitong.resource.model.point.PointGoodsPlatform;
import com.baizhitong.resource.model.point.PointGoodsPutPlatform;

/**
 * 平台商品入库
 */

@Controller
@RequestMapping("/manage/pointGoodsPutPlatform")
public class PointGoodsPutPlatformAction extends BaseAction {
    private @Autowired IPointGoodsPutPlatformService goodsPutPlatfromService;
    private @Autowired IPointGoodsPlatformService    pointGoodsPlatformService;

    /**
     * 跳转到平台积分商品入库详细 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toGoodsPut.html")
    public String toGoodsPut(ModelMap model, Integer id) {
        model.put("id", id);
        return "/manage/point/lotteryGoodPlatform/pointGoodsPutPlatform.html";
    }

    /**
     * 跳转到平台积分商品入库新增 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toGoodsPutAdd.html")
    public String toGoodsPutAdd(ModelMap model, Integer id) {
        PointGoodsPlatform pointGoodsPlatform = pointGoodsPlatformService.getPlatformGiftById(id);
        model.put("goodsName", pointGoodsPlatform.getGoodsName());
        model.put("goodsCode", pointGoodsPlatform.getGoodsCode());
        return "/manage/point/lotteryGoodPlatform/pointGoodsPutPlatformAdd.html";
    }

    /**
     * 查询列表 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page getPageList(String goodsName, Integer id, Integer page, Integer rows) {
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        PointGoodsPlatform pointGoodsPlatform = pointGoodsPlatformService.getPlatformGiftById(id);
        sqlParam.put("goodsCode", pointGoodsPlatform.getGoodsCode());
        sqlParam.put("goodsName", goodsName);
        return goodsPutPlatfromService.getList(sqlParam, page, rows);
    }

    /**
     * 平台积分商品入库保存 ()<br>
     * 
     * @param goodsPut
     * @return
     */
    @RequestMapping("/goodsPutPlatformAdd.html")
    @ResponseBody
    public ResultCodeVo add(PointGoodsPutPlatform goodsPut) {
        return goodsPutPlatfromService.saveOrupdate(goodsPut);
    }
}
