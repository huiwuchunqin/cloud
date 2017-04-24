package com.baizhitong.resource.manage.resTypeL2.action;

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
import com.baizhitong.resource.dao.share.ShareResTypeL1Dao;
import com.baizhitong.resource.manage.resTypeL2.service.IResTypeL2Service;
import com.baizhitong.resource.model.share.ShareResTypeL2;

/**
 * 二级资源分类控制类 ResTypeL2Action TODO
 * 
 * @author creator BZT 2016年9月27日 下午4:11:19
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/resTypeL2")
public class ResTypeL2Action extends BaseAction {
    
    @Autowired
    private IResTypeL2Service resTypeL2Service;
    @Autowired
    private ShareResTypeL1Dao shareResTypeL1Dao;

    /**
     * 跳转到二级分类页面 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/toResTypeL2.html")
    public String toResTypeL2(ModelMap model) {
        try {
            model.put("resTypeL1List", JSON.toJSONString(shareResTypeL1Dao.selectResTypeL1List()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/manage/resTypeL2/resTypeL2.html";
    }

    /**
     * 跳转到二级分类修改页面 ()<br>
     * 
     * @param model
     * @param code
     * @return
     */
    @RequestMapping(value = "/toResTypeL2Edit.html")
    public String toResTypeL2Edit(ModelMap model, String code) {
        model.put("resTypeL2", JSON.toJSONString(resTypeL2Service.getByCode(code)));
        return "/manage/resTypeL2/resTypeL2Edit.html";
    }

    /**
     * 新增二级分类 ()<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/toResTypeL2Add.html")
    public String toResTypeL2Add(ModelMap model) {
        try {
            model.put("resTypeL1List", JSON.toJSONString(shareResTypeL1Dao.selectResTypeL1List()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/manage/resTypeL2/resTypeL2Add.html";
    }

    /**
     * 更新二级分类 ()<br>
     * 
     * @param request
     * @param newName
     * @param code
     * @return
     */
    @RequestMapping(value = "/update.html")
    @ResponseBody
    public ResultCodeVo update(HttpServletRequest request, String newName, String description, String code) {
        return resTypeL2Service.update(newName, description, code, getIp());
    }

    /**
     * 保存二级分类 ()<br>
     * 
     * @param resTypeL2
     * @return
     */
    @RequestMapping(value = "/add.html")
    @ResponseBody
    public ResultCodeVo add(HttpServletRequest request, ShareResTypeL2 resTypeL2) {
        resTypeL2.setModifyIP(getIp());
        resTypeL2.setModifyPgm("resTypeL2Service");
        return resTypeL2Service.add(resTypeL2);
    }

    /**
     * 查询资源二级分类列表 ()<br>
     * 
     * @param page
     * @param rows
     * @param resTypeL1Code
     * @return
     */
    @RequestMapping(value = "/list.html")
    @ResponseBody
    public Page pageList(Integer page, Integer rows, String resTypeL1Code) {
        return resTypeL2Service.getResTypeL2List(page, rows, resTypeL1Code);
    }
}
