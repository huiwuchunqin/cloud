package com.baizhitong.resource.manage.resAdvice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.dao.share.ShareResCheckAdviceDao;
import com.baizhitong.resource.model.share.ShareResCheckAdvice;

@Controller
@RequestMapping(value = "/manage/advice")
/**
 * 资源审核意见 ResAdviceAction TODO
 * 
 * @author creator gaow 2017年1月13日 下午6:01:51
 * @author updater
 *
 * @version 1.0.0
 */
public class ResAdviceAction extends BaseAction {
    @Autowired
    ShareResCheckAdviceDao resCheckAdviceDao; // 审核意见dao

    /**
     * 查询资源审核意见dao ()<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("list.html")
    @ResponseBody
    public List<ShareResCheckAdvice> getList(ModelMap model) {
        return resCheckAdviceDao.list();
    }

}
