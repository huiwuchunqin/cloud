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
import com.baizhitong.resource.dao.point.PointLotteryGoodsOrgDao;
import com.baizhitong.resource.manage.point.service.IPointLotteryGoodsOrgService;
import com.baizhitong.resource.model.point.PointLotteryGoodsOrg;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

/**
 * 平台商品控制器 PointLotteryGoodsOrgAction TODO
 * 
 * @author creator gaow 2016年6月21日 下午7:34:23
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/lotteryGoodsOrg")
public class PointLotteryGoodsOrgAction extends BaseAction {
    /**
     * 机构积分商品接口
     */
    private @Autowired IPointLotteryGoodsOrgService pointLotteryGoodsOrgService;
    private @Autowired PointLotteryGoodsOrgDao      pointLotteryGoodsOrgDao;

    /**
     * 跳转到机构积分商品页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toOrgLotteryGood.html")
    public String toOrgLotteryGood(ModelMap model) {
        model.put("imgHost", SystemConfig.getImgHost());
        return "/manage/point/lotteryGoodOrg/lotteryGoodOrg.html";
    }

    /**
     * 跳转到机构积分商品新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toOrgLotteryGoodAdd.html")
    public String toOrgLotteryGoodAdd(HttpServletRequest request, ModelMap model) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        return "/manage/point/lotteryGoodOrg/goodOrgAdd.html";
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
        return pointLotteryGoodsOrgDao.getLeftProbality(role, companyInfoVo.getOrgCode());
    }

    /**
     * 跳转到机构积分商品修改页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toOrgLotteryGoodEdit.html")
    public String toOrgLotteryGoodEdit(HttpServletRequest request, ModelMap model, Integer id) {
        PointLotteryGoodsOrg goodsOrg = pointLotteryGoodsOrgService.getOrgGoods(id);
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        model.put("goodsOrg", JSON.toJSONString(goodsOrg));
        model.put("imgHost", SystemConfig.getImgHost());
        return "/manage/point/lotteryGoodOrg/goodOrgAdd.html";
    }

    /**
     * 查询机构商品分页列表 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/getPageList.html")
    @ResponseBody
    public Page getOrgLotteryGood(HttpServletRequest request, String goodsName, Integer role, Integer page,
                    Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        return pointLotteryGoodsOrgService.getPageList(companyInfoVo.getOrgCode(), goodsName, role, page, rows);
    }

    /**
     * 保存机构奖品 ()<br>
     * 
     * @param goods
     * @return
     */
    @RequestMapping("/addOrgLotteryGood.html")
    @ResponseBody
    public ResultCodeVo addOrgLotteryGood(PointLotteryGoodsOrg goods) {
        return pointLotteryGoodsOrgService.add(goods);
    }

    /**
     * 删除商品 ()<br>
     * 
     * @param id
     * @return
     */
    @RequestMapping("/deleteOrgLotteryGood.html")
    @ResponseBody
    public ResultCodeVo deleteGood(Integer id) {
        return pointLotteryGoodsOrgService.deleteGood(id);
    }

}
