package com.baizhitong.resource.manage.point.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.impl.tool.Extension.Param;
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
import com.baizhitong.resource.manage.point.service.IPointRuleAcquireOrgService;
import com.baizhitong.resource.manage.point.service.IPointRuleAcquireService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

@Controller
@RequestMapping("/manage/pointRuleAcquire")
public class PointRuleAcquireAction extends BaseAction {
    @Autowired
    IPointRuleAcquireService    acquireService;
    @Autowired
    IPointRuleAcquireOrgService acquireOrgService;

    /**
     * 跳转到积分规则页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toAcquire.html")
    public String toAcquire() {
        return "/manage/point/pointRuleAcquire/pointRuleAcquire.html";
    }

    /**
     * 跳转到积分规则修改页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toAcquireEdit.html")
    public String toAcquireEdit(ModelMap model, String actionCode) {
        model.put("acquire", JSON.toJSONString(acquireService.getByCode(actionCode)));
        return "/manage/point/pointRuleAcquire/pointRuleAcquireEdit.html";
    }

    /**
     * 跳转到机构积分规则页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toAcquireOrgEdit.html")
    public String toAcquireOrgEdit(ModelMap model, Integer id) {
        model.put("acquireOrg", JSON.toJSONString(acquireOrgService.getById(id)));
        return "/manage/point/pointRuleAcquire/pointRuleAcquireOrgEdit.html";
    }

    /**
     * 跳转到机构积分规则页面修改 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toAcquireOrg.html")
    public String toAcquireOrg(ModelMap model) {
        return "/manage/point/pointRuleAcquire/pointRuleAcquireOrg.html";
    }

    /**
     * 修改 ()<br>
     * 
     * @param point
     * @param actionCode
     * @return
     */
    @RequestMapping("/updateAcquireOrg.html")
    @ResponseBody
    public ResultCodeVo updateAcquire(String pointOrg, Integer id) {
        return acquireOrgService.modify(pointOrg, id);

    }

    /**
     * 修改 ()<br>
     * 
     * @param point
     * @param actionCode
     * @return
     */
    @RequestMapping("/updateAcquire.html")
    @ResponseBody
    public ResultCodeVo updateAcquire(String pointOrg, String pointPlt, String actionCode) {
        return acquireService.modify(pointOrg, pointPlt, actionCode);
    }

    /**
     * 积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/acquire.html")
    @ResponseBody
    public Page toPointRuleAcquire(Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        return acquireService.getPage(param, page, rows);
    }

    /**
     * 机构积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/acquireOrg.html")
    @ResponseBody
    public Page toPointRuleOrgAcquire(HttpServletRequest request, Integer page, Integer rows) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("orgCode", companyInfoVo.getOrgCode());
        return acquireOrgService.getPage(param, page, rows);
    }

    /**
     * 机构积分规则 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/importPlatToOrg.html")
    @ResponseBody
    public ResultCodeVo importPltToOrg(HttpServletRequest request) {
        CompanyInfoVo company =  getCompanyInfo();
        return acquireOrgService.importDefaultToOrg(company.getOrgCode());
    }
}
