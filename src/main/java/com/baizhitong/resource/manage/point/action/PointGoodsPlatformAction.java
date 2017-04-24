package com.baizhitong.resource.manage.point.action;

import java.util.HashMap;
import java.util.Map;

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
import com.baizhitong.resource.manage.point.service.IPointGoodsPlatformService;
import com.baizhitong.resource.model.point.PointGoodsPlatform;

/**
 * 
 * 可兑商品-平台Action
 * 
 * @author creator zhangqiang 2016年6月27日 下午8:20:19
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/point/platform")
public class PointGoodsPlatformAction extends BaseAction {

    @Autowired
    private IPointGoodsPlatformService pointGoodsPlatformService;

    /**
     * 
     * 跳转到礼品维护主页面
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/goods/main.html")
    public String jumpToGoodsMainPage(HttpServletRequest request, ModelMap map) {
        return "/manage/point/platform/goods_main.html";
    }

    /**
     * 
     * 跳转到平台上架礼品页面
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/goods/shelves.html")
    public String jumpToShelvesGoodsPage(HttpServletRequest request, ModelMap map) {
        return "/manage/point/platform/goods_shelves.html";
    }

    /**
     * 
     * 查询上架礼品分页信息
     * 
     * @param request 请求
     * @param page 当前页码
     * @param rows 每页记录数
     * @param userRoleRequirement 面向的用户角色
     * @return
     */
    @RequestMapping(value = "/goods/shelves/search.html")
    @ResponseBody
    public Page<PointGoodsPlatform> searchShelvesGoodsPage(HttpServletRequest request, Integer page, Integer rows,
                    Integer userRoleRequirement) {
        Page<PointGoodsPlatform> pageInfo = null;
        int size = 20;
        int number = 1;
        if (page == null) {
            page = number;
        }
        if (rows == null) {
            rows = size;
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("pageSize", rows);
            param.put("pageNumber", page);
            param.put("userRoleRequirement", userRoleRequirement);
            pageInfo = pointGoodsPlatformService.getShelvesGoodsPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * 跳转到平台下架礼品页面
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/goods/next.html")
    public String jumpToNextGoodsPage(HttpServletRequest request, ModelMap map) {
        return "/manage/point/platform/goods_next.html";
    }

    /**
     * 
     * 查询下架礼品分页信息
     * 
     * @param request 请求
     * @param page 当前页码
     * @param rows 每页记录数
     * @param userRoleRequirement 面向的用户角色
     * @return
     */
    @RequestMapping(value = "/goods/next/search.html")
    @ResponseBody
    public Page<PointGoodsPlatform> searchNextGoodsPage(HttpServletRequest request, Integer page, Integer rows,
                    Integer userRoleRequirement) {
        Page<PointGoodsPlatform> pageInfo = null;
        int size = 20;
        int number = 1;
        if (page == null) {
            page = number;
        }
        if (rows == null) {
            rows = size;
        }
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("pageSize", rows);
            param.put("pageNumber", page);
            param.put("userRoleRequirement", userRoleRequirement);
            pageInfo = pointGoodsPlatformService.queryNextGoodsPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * 礼品上架或下架操作
     * 
     * @param id 主键id
     * @param type 操作类型（1：下架；2：上架）
     * @return
     */
    @RequestMapping(value = "/goods/upOrDown.html")
    @ResponseBody
    public ResultCodeVo goodsUpOrDown(Integer id, Integer type) {
        ResultCodeVo vo = new ResultCodeVo();
        try {
            int operateNum = pointGoodsPlatformService.operateGoodsUpOrDown(id, type);
            if (1 == operateNum) {
                vo.setSuccess(true);
                vo.setMsg("操作成功！");
            }
        } catch (Exception ex) {
            log.error("礼品上架或下架操作失败!", ex);
            vo.setSuccess(false);
            vo.setMsg("操作失败！");
        }
        return vo;
    }

    /**
     * 
     * 跳转到新增礼品页面
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/goods/add.html")
    public String toGiftAddPage(HttpServletRequest request, ModelMap map) {
        return "/manage/point/platform/add_goods.html";
    }

    /**
     * 
     * 新增或修改平台礼品
     * 
     * @param request 请求
     * @param pointGoodsPlatform 实体
     * @return
     */
    @RequestMapping(value = "/addPlatformGift.html")
    @ResponseBody
    public ResultCodeVo savePlatformGift(HttpServletRequest request, PointGoodsPlatform pointGoodsPlatform) {
        return pointGoodsPlatformService.savePlatformGift(pointGoodsPlatform);
    }

    /**
     * 
     * 跳转到修改礼品信息页面
     * 
     * @param request 请求
     * @param id 主键id
     * @param map
     * @return
     */
    @RequestMapping(value = "/goods/edit.html")
    public String toEditPlatformGiftPage(HttpServletRequest request, Integer id, ModelMap map) {
        try {
            PointGoodsPlatform platformGift = new PointGoodsPlatform();
            platformGift = pointGoodsPlatformService.getPlatformGiftById(id);
            map.put("platformGift", JSON.toJSONString(platformGift));
        } catch (Exception ex) {
            log.error("获取平台礼品信息异常 ", ex);
        }
        return "/manage/point/platform/add_goods.html";
    }

    /**
     * 
     * 删除平台礼品
     * 
     * @param id 主键id
     * @return
     */
    @RequestMapping(value = "/goods/delete.html")
    public @ResponseBody ResultCodeVo deletePlatformGift(String id) {
        ResultCodeVo vo = new ResultCodeVo();
        try {
            int delSum = pointGoodsPlatformService.deletePlatformGift(id);
            if (1 == delSum) {
                vo.setSuccess(true);
                vo.setMsg("删除成功！");
            }
        } catch (Exception ex) {
            log.error("删除平台礼品失败!", ex);
            vo.setSuccess(false);
            vo.setMsg("删除失败！");
        }
        return vo;
    }
}
