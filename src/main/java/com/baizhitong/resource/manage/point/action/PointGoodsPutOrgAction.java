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
import com.baizhitong.resource.manage.point.service.IPointGoodsOrgService;
import com.baizhitong.resource.manage.point.service.IPointGoodsPutOrgService;
import com.baizhitong.resource.model.point.PointGoodsOrg;
import com.baizhitong.resource.model.point.PointGoodsPutOrg;

/**
 * 机构入库商品 PointGoodsPutOrgAction TODO
 * 
 * @author creator Gaow 2016年6月27日 下午1:47:14
 * @author updater
 *
 * @version 1.0.0
 */
@RequestMapping("/manage/pointGoodsPutOrg")
@Controller
public class PointGoodsPutOrgAction extends BaseAction {
    @Autowired
    private IPointGoodsPutOrgService pointGoodsPutOrgService;

    @Autowired
    private IPointGoodsOrgService    pointGoodsOrgService;

    /**
     * 跳转到平台积分商品入库详细 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toGoodsPut.html")
    public String toGoodsPut(ModelMap model, Integer id) {
        model.put("id", id);
        return "/manage/point/lotteryGoodOrg/pointGoodsPutOrg.html";
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
        PointGoodsOrg goods = pointGoodsOrgService.getOrgGiftById(id);
        sqlParam.put("goodsCode", goods.getGoodsCode());
        sqlParam.put("goodsName", goodsName);
        return pointGoodsPutOrgService.getList(sqlParam, page, rows);
    }

    /**
     * 跳转到平台积分商品入库详细 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toGoodsPutAdd.html")
    public String toGoodsPutAdd(ModelMap model, Integer id) {
        PointGoodsOrg goods = pointGoodsOrgService.getOrgGiftById(id);
        if (goods != null) {
            model.put("goodsName", goods.getGoodsName());
            model.put("goodsCode", goods.getGoodsCode());
        }
        return "/manage/point/lotteryGoodOrg/pointGoodsPutOrgAdd.html";
    }

    /**
     * 平台积分商品入库保存 ()<br>
     * 
     * @param goodsPut
     * @return
     */
    @RequestMapping("/goodsPutPlatformAdd.html")
    @ResponseBody
    public ResultCodeVo add(PointGoodsPutOrg goodsPut) {
        return pointGoodsPutOrgService.saveOrupdate(goodsPut);
    }
}
