package com.baizhitong.resource.manage.platform.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.platform.service.ISharePlatformService;
import com.baizhitong.resource.model.share.ShareCodeStudyYear;
import com.baizhitong.resource.model.share.SharePlatform;

/**
 * 
 * 平台Action
 * 
 * @author creator ZhangQiang 2016年8月30日 下午1:47:10
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/platform")
public class SharePlatformAction extends BaseAction {

    /** 平台信息接口 */
    @Autowired
    private ISharePlatformService sharePlatformService;

    /**
     * 
     * (跳转到平台信息页面)<br>
     * 
     * @param request 请求
     * @param model
     * @return
     */
    @RequestMapping(value = "/info.html")
    public String jumpToPlatformInfoPage(HttpServletRequest request, ModelMap model) {
        try {
            SharePlatform platform = sharePlatformService.queryPlatformInfo();
            List<ShareCodeStudyYear> studyYearList = sharePlatformService.queryStudyYearList();
            model.put("platform", platform);
            model.put("studyYearList", studyYearList);
        } catch (Exception e) {
            log.error("获取平台信息出错！", e);
            e.printStackTrace();
        }
        return "/manage/platform/platform_info.html";
    }

    /**
     * 
     * (保存或修改平台信息)<br>
     * 
     * @param request 请求
     * @param entity 平台信息实体
     * @return 操作结果
     */
    @RequestMapping(value = "/save.html")
    @ResponseBody
    public ResultCodeVo savePlatformInfo(HttpServletRequest request, SharePlatform entity) {
        try {
            return sharePlatformService.addPlatformInfo(entity);
        } catch (Exception e) {
            log.error("保存平台信息失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (检查平台学年学期表和机构学年学期表对应的开始时间是否需要更新)<br>
     * 
     * @param request 请求
     * @param yearTermCode 学年学期编码
     * @param currentDate 当前日期
     * @return 检查结果
     */
    @RequestMapping(value = "/check.html")
    @ResponseBody
    public ResultCodeVo check(HttpServletRequest request, String yearTermCode, String currentDate) {
        try {
            return sharePlatformService.check(yearTermCode, currentDate);
        } catch (Exception e) {
            log.error("检测发生异常！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }

    }
}
