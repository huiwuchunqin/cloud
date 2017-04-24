package com.baizhitong.resource.manage.point.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.point.service.IPointRuleLotteryPlatformService;
import com.baizhitong.resource.model.point.PointRuleLotteryPlatform;

@Controller
@RequestMapping("/manage/platfromPointRule")
public class PointRuleLotteryPlatformAction extends BaseAction {
    private @Autowired IPointRuleLotteryPlatformService ruleLotteryPlatformService;

    /**
     * 跳转到机构规则页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPlatformRule.html")
    public String toPlatformRule() {
        return "/manage/point/lotteryGoodPlatform/platformPointRule.html";
    }

    /**
     * 跳转到机构规则新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPlatformRuleAdd.html")
    public String toPlatformRuleAdd() {
        return "/manage/point/lotteryGoodPlatform/platformPointRuleAdd.html";
    }

    /**
     * 跳转到机构规则修改页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPlatformRuleEdit.html")
    public String toPlatformRuleEdit(ModelMap model, Integer id) {
        model.put("PlatformRule", JSON.toJSONString(ruleLotteryPlatformService.getById(id)));
        return "/manage/point/lotteryGoodPlatform/platformPointRuleAdd.html";
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
    public Page getPlatformRulePage(Integer page, Integer rows) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        return ruleLotteryPlatformService.getList(page, rows);
    }

    /**
     * 机构规则新增 ()<br>
     * 
     * @param PlatformRule
     * @return
     */
    @RequestMapping("/add.html")
    @ResponseBody
    public ResultCodeVo addPlatformPointRule(PointRuleLotteryPlatform PlatformRule) {
        return ruleLotteryPlatformService.add(PlatformRule);
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
        return ruleLotteryPlatformService.delete(id);
    }

}
