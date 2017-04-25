package com.baizhitong.resource.manage.report.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.dao.report.SurveryDao;

/**
 * 概况 SurveryAction TODO
 * 
 * @author creator gaow 2017年4月25日 上午11:06:22
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/survery")
public class SurveryAction extends BaseAction {
    @Autowired
    private SurveryDao surveryDao;

    /**
     * 跳转到概况页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toSurvery.html")
    public String toSurvery() {
        return "/manage/report/survery.html";
    }
    
    /**
     * 查询概况
     * ()<br>
     * @param startDate
     * @param endDate
     * @param resTypeL1
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public List getSurveryList(String startDate,String endDate,String resTypeL1){
        Map<String,Object> param=new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("resTypeL1", resTypeL1);
        return surveryDao.getSurvery(param);
        
    }
}
