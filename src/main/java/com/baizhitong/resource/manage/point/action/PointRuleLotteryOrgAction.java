package com.baizhitong.resource.manage.point.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.point.service.IPointRuleLotteryOrgService;
import com.baizhitong.resource.model.point.PointRuleLotteryOrg;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

/**
 * 机构积分规则控制类 PointRuleLotteryOrgAction TODO
 * 
 * @author creator gaow 2016年6月23日 下午5:22:34
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/orgPointRule")
public class PointRuleLotteryOrgAction extends BaseAction {
    private @Autowired IPointRuleLotteryOrgService ruleLotteryOrgService;

    /**
     * 跳转到机构规则页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toOrgRule.html")
    public String toOrgRule() {
        return "/manage/point/lotteryGoodOrg/orgPointRule.html";
    }

    /**
     * 跳转到机构规则新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toOrgRuleAdd.html")
    public String toOrgRuleAdd() {
        return "/manage/point/lotteryGoodOrg/orgPointRuleAdd.html";
    }

    /**
     * 跳转到机构规则修改页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toOrgRuleEdit.html")
    public String toOrgRuleEdit(ModelMap model, Integer id) {
        model.put("orgRule", JSON.toJSONString(ruleLotteryOrgService.getById(id)));
        return "/manage/point/lotteryGoodOrg/orgPointRuleAdd.html";
    }

    /**
     * 查询机构积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page getOrgRulePage(HttpServletRequest request, Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        return ruleLotteryOrgService.getList(companyInfoVo.getOrgCode(), page, rows);
    }

    /**
     * 机构规则新增 ()<br>
     * 
     * @param orgRule
     * @return
     */
    @RequestMapping("/add.html")
    @ResponseBody
    public ResultCodeVo addOrgPointRule(PointRuleLotteryOrg orgRule) {
        return ruleLotteryOrgService.add(orgRule);
    }

    /**
     * 删除 ()<br>
     * 
     * @param PlatformRule
     * @return
     */
    @RequestMapping("/delete.html")
    @ResponseBody
    public ResultCodeVo delete(Integer id) {
        return ruleLotteryOrgService.delete(id);
    }

}
