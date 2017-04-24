package com.baizhitong.resource.manage.res.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.manage.res.service.ResTypeService;
import com.baizhitong.resource.model.share.ShareResTypeL2;
import com.baizhitong.resource.model.vo.base.NodeTreeVo;

/**
 * 资源类型控制器
 * 
 * @author zhangqiang
 * @date 2015年12月17日 上午11:02:31
 */

@Controller
@RequestMapping(value = "/manage/resType")
public class ShareResTypeAction extends BaseAction {
    /** 资源类型接口 */
    private @Autowired ResTypeService resTypeService;

    /**
     * 跳转到资源类型页面
     * 
     * @param request 请求
     * @param map
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 上午11:08:29
     */
    @RequestMapping(value = "/resType.html")
    public String jumpToResTypePage(HttpServletRequest request, ModelMap map) {
        return "/manage/res/resType.html";
    }

    /**
     * 
     * 查询二级分类列表
     * 
     * @param resTypel1 一级分类编码
     * @return
     */
    @RequestMapping(value = "/resTypeL2List.html")
    @ResponseBody
    public List<ShareResTypeL2> getResTypeL2List(Integer resTypel1) {
        try {
            return resTypeService.getResTypeTwoByType1Code(resTypel1);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取资源类型树状数据
     * 
     * @param request
     * @return
     * @author zhangqiang
     * @date 2015年12月17日 上午11:34:42
     */
    @RequestMapping(value = "/getResTypeTreeData.html")
    public @ResponseBody List<NodeTreeVo> getResTypeTreeList(HttpServletRequest request) {
        try {
            return resTypeService.getResTypeTreeData();
        } catch (Exception e) {
            log.error("获取资源类型数据失败！", e);
            return null;
        }
    }
}
