package com.baizhitong.resource.manage.point.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.point.service.IPointConvertDetailService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

/**
 * 商品兑换明细 PointConvertDetailAction TODO
 * 
 * @author creator gaow 2016年6月28日 上午10:41:46
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/pointConvertDetail")
public class PointConvertDetailAction extends BaseAction {
    /**
     * 商品兑换接口
     */
    private @Autowired IPointConvertDetailService pointConvertDetailService;

    /**
     * 跳转到商品兑换页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toConvertDetail.html")
    public String toPointConvertDetail() {
        return "/manage/point/pointConvert/pointConvert.html";
    }

    /**
     * 查询兑换列表 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page getPageList(HttpServletRequest request, String goodsName, Integer page, Integer rows) {
        HashMap<String, Object> param = new HashMap<String, Object>();
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        param.put("orgCode", companyInfoVo.getOrgCode());
        param.put("goodsName", goodsName);
        return pointConvertDetailService.getConvertDetail(param, page, rows);
    }

}
