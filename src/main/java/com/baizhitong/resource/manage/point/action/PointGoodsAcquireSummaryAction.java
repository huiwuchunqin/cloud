package com.baizhitong.resource.manage.point.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.manage.adminClass.service.IAdminClassService;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.point.service.IPointGoodsAcquireSummaryService;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;

@Controller
@RequestMapping("/manage/acquireSummary")
public class PointGoodsAcquireSummaryAction extends BaseAction {
    private @Autowired IPointGoodsAcquireSummaryService acquireSummaryService;

    private @Autowired IAdminClassService               adminClassService;
    private @Autowired GradeService                     GradeService;
    private @Autowired ICompanyService                  companyService;

    /**
     * 跳转到商品兑换页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toAcquireList.html")
    public String toaAcquireList(HttpServletRequest request, ModelMap model) {
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        try {
            model.put("gradeList", JSONArray.toJSONString(GradeService.selectGradeList("")));
            model.put("orgGradeList",
                            JSONArray.toJSONString(companyService.getCompanyGrade(getOrgCode() )));
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.put("adminClassList",
                        JSONArray.toJSONString(adminClassService.getAdminClassList(companyInfoVo.getOrgCode(), "")));
        return "/manage/point/pointGoodsAcquireSummary/pointGoodsAcquireSummary.html";
    }

    /**
     * 查询分页信息 ()<br>
     * 
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page getList(HttpServletRequest request, Integer status, String adminClassCode, String userName,
                    Integer goodsLevel, Integer userRole, String gradeCode, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        CompanyInfoVo companyInfoVo =  getCompanyInfo();
        param.put("status", status);
        param.put("orgCode", companyInfoVo.getOrgCode());
        param.put("adminClassCode", adminClassCode);
        param.put("userName", userName);
        param.put("goodsLevel", goodsLevel);
        param.put("userRole", userRole);
        param.put("gradeCode", gradeCode);
        return acquireSummaryService.getList(param, page, rows);
    }

    /**
     * 领取商品 ()<br>
     * 
     * @return
     */
    @RequestMapping("/exchange.html")
    @ResponseBody
    public ResultCodeVo exchange(Integer goodsLevel, String actionBatch, String adminClassCode, String userName,
                    String userRole, String ids) {
        return acquireSummaryService.exchange(goodsLevel, actionBatch, adminClassCode, userName, userRole, ids);
    }

}
