package com.baizhitong.resource.manage.point.action;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.point.service.IPointRankOrgService;
import com.baizhitong.resource.model.point.PointRankOrg;

/**
 * 机构积分头衔控制类 PointRankOrgAction TODO
 * 
 * @author creator gaow 2016年6月24日 下午1:17:08
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/orgRank")
public class PointRankOrgAction extends BaseAction {
    /**
     * 平台积分头衔等级接口
     */
    private @Autowired IPointRankOrgService pointRankService;

    /**
     * 查询积分头衔等级列表 ()<br>
     * 
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/pageList.html")
    @ResponseBody
    public Page getPageList(Map<String, Object> param, Integer page, Integer rows) {
        return pointRankService.getPageList(param, page, rows);
    }

    /**
     * 跳转到积分头衔页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPointRankOrg.html")
    public String toPointRank() {
        return "/manage/point/lotteryGoodOrg/pointRankOrg.html";
    }

    /**
     * 跳转到积分头衔新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPointRankOrgAdd.html")
    public String toPointRankAdd() {
        return "/manage/point/lotteryGoodOrg/orgPointRankAdd.html";
    }

    /**
     * 跳转到积分头衔新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPointRankOrgEdit.html")
    public String toPointRankEdit(Integer id, ModelMap model) {
        model.put("pointRank", JSON.toJSONString(pointRankService.getById(id)));
        return "/manage/point/lotteryGoodOrg/orgPointRankAdd.html";
    }

    /**
     * 新增 ()<br>
     * 
     * @param point
     * @return
     */
    @RequestMapping("/save.html")
    @ResponseBody
    public ResultCodeVo add(PointRankOrg point) {
        return pointRankService.add(point);
    }

    /**
     * 删除 ()<br>
     * 
     * @param id
     * @return
     */
    @RequestMapping("/delete.html")
    @ResponseBody
    public ResultCodeVo delete(Integer id) {
        return pointRankService.delete(id);
    }
}
