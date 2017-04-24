package com.baizhitong.resource.manage.point.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.point.PointLotteryGoodsPlatformDao;
import com.baizhitong.resource.manage.point.service.IPointLotteryGoodsPlatformService;
import com.baizhitong.resource.model.point.PointLotteryGoodsPlatform;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

/**
 * 平台积分商品控制类 PointLotteryGoodsPlatformAction TODO
 * 
 * @author creator gaow 2016年6月21日 下午7:35:57
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/lotteryGoodsPlatform")
public class PointLotteryGoodsPlatformAction extends BaseAction {
    /**
     * 平台积分商品接口
     */
    private @Autowired IPointLotteryGoodsPlatformService pointGoodsPlatformService;
    private @Autowired PointLotteryGoodsPlatformDao      PointLotteryGoodsPlatformDao;

    /**
     * 跳转到平台积分商品页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPlatformGood.html")
    public String toPlatformLotteryGood() {
        return "/manage/point/lotteryGoodPlatform/lotteryGoodPlatform.html";
    }

    /**
     * 查询平台商品分页列表 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/getPageList.html")
    @ResponseBody
    public Page getPlatformLotteryGood(HttpServletRequest request, String goodsName, Integer role, Integer page,
                    Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        return pointGoodsPlatformService.getPageList(goodsName, role, page, rows);
    }

    /**
     * 跳转到机构积分商品新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPlatformLotteryGoodAdd.html")
    public String toPlatformLotteryGoodAdd(HttpServletRequest request, ModelMap model) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        return "/manage/point/lotteryGoodPlatform/platformOrgAdd.html";
    }

    /**
     * 查询剩余概率 ()<br>
     * 
     * @param role
     * @return
     */
    @RequestMapping("/getLeftPro.html")
    @ResponseBody
    public Double getLeft(HttpServletRequest request, Integer role) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        return PointLotteryGoodsPlatformDao.getLeftProbality(role, companyInfoVo.getOrgCode());
    }

    /**
     * 跳转到机构积分商品修改页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPlatformLotteryGoodEdit.html")
    public String toPlatformLotteryGoodEdit(HttpServletRequest request, ModelMap model, Integer id) {
        PointLotteryGoodsPlatform goodsPlatform = pointGoodsPlatformService.getPlatformGoods(id);
        model.put("goodsPlatform", JSON.toJSONString(goodsPlatform));
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        model.put("imgHost", SystemConfig.getImgHost());
        return "/manage/point/lotteryGoodPlatform/platformOrgAdd.html";
    }

    /**
     * 保存机构奖品 ()<br>
     * 
     * @param goods
     * @return
     */
    @RequestMapping("/addPlatformLotteryGood.html")
    @ResponseBody
    public ResultCodeVo addPlatformLotteryGood(PointLotteryGoodsPlatform goods) {
        return pointGoodsPlatformService.add(goods);
    }

    /**
     * 删除商品 ()<br>
     * 
     * @param id
     * @return
     */
    @RequestMapping("/deletePlatformLotteryGood.html")
    @ResponseBody
    public ResultCodeVo deleteGood(Integer id) {
        return pointGoodsPlatformService.deleteGood(id);
    }

}
