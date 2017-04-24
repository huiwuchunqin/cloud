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
import com.baizhitong.resource.manage.point.service.IPointRankService;
import com.baizhitong.resource.model.point.PointRank;

/**
 * 积分头衔控制类 PointRankAction TODO
 * 
 * @author creator gaow 2016年6月24日 下午1:15:17
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/pointRank")
public class PointRankAction extends BaseAction {
    /**
     * 平台积分头衔等级接口
     */
    private @Autowired IPointRankService pointRankService;

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
    @RequestMapping("/toPointRank.html")
    public String toPointRank() {
        return "/manage/point/lotteryGoodPlatform/pointRank.html";
    }

    /**
     * 跳转到积分头衔新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPointRankAdd.html")
    public String toPointRankAdd() {
        return "/manage/point/lotteryGoodPlatform/pointRankAdd.html";
    }

    /**
     * 跳转到积分头衔新增页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toPointRankEdit.html")
    public String toPointRankEdit(Integer id, ModelMap model) {
        model.put("pointRank", JSON.toJSONString(pointRankService.getById(id)));
        return "/manage/point/lotteryGoodPlatform/pointRankAdd.html";
    }

    /**
     * 新增 ()<br>
     * 
     * @param point
     * @return
     */
    @RequestMapping("/save.html")
    @ResponseBody
    public ResultCodeVo add(PointRank point) {
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
    public ResultCodeVo delete(Integer id, String rankCode) {
        return pointRankService.delete(id, rankCode);
    }

}
