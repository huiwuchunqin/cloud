package com.baizhitong.resource.manage.otherSetting.action;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.otherSetting.service.OtherSettingsService;
import com.baizhitong.resource.manage.platform.service.ISharePlatformService;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.SharePlatformCloudDiskParam;
import com.baizhitong.resource.model.share.SharePlatformSettingsRes;

/**
 * 
 * 后台其他设置Action
 * 
 * @author creator ZhangQiang 2016年9月19日 上午9:58:07
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/otherSettings")
public class OtherSettingsAction extends BaseAction {

    /** 其他设置Service */
    @Autowired
    private OtherSettingsService  otherSettingsService;
    /** 平台信息Service */
    @Autowired
    private ISharePlatformService sharePlatformService;

    /**
     * 
     * (跳转到后台其他设置tab管理页面)<br>
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/main.html")
    public String jumpToMainPage(HttpServletRequest request, ModelMap map) {
        return "/manage/otherSettings/tabList.html";
    }

    /**
     * 
     * (跳转到资源评论设置页面)<br>
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/resComment.html")
    public String jumpToPlatformResCommentSetPage(HttpServletRequest request, ModelMap model) {
        try {
            SharePlatformSettingsRes resSetting = otherSettingsService.query();
            model.put("resSetting", resSetting);
        } catch (Exception e) {
            log.error("查询平台资源设置信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/otherSettings/resCommentSetting.html";
    }

    /**
     * 
     * (保存资源评论设置的修改)<br>
     * 
     * @param request
     * @param entity
     * @return
     */
    @RequestMapping(value = "/resComment/save.html")
    @ResponseBody
    public ResultCodeVo saveChangeAllowResComment(HttpServletRequest request, SharePlatformSettingsRes entity) {
        try {
            return otherSettingsService.saveChangeAllowResComment(entity);
        } catch (Exception e) {
            log.error("保存失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (跳转到云盘参数设置页面)<br>
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/cloudDisk.html")
    public String jumpToCloudDiskParamSetPage(HttpServletRequest request, ModelMap model) {
        try {
            SharePlatformCloudDiskParam teacher = otherSettingsService
                            .queryCloudDiskInfoByUserRole(CoreConstants.USER_ROLE_TEACHER);
            SharePlatformCloudDiskParam student = otherSettingsService
                            .queryCloudDiskInfoByUserRole(CoreConstants.USER_ROLE_STUDENT);
            model.put("teacher", teacher);
            model.put("student", student);
        } catch (Exception e) {
            log.error("查询云盘参数信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/otherSettings/cloudDisk.html";
    }

    /**
     * 
     * (跳转到云盘参数修改页面)<br>
     * 
     * @param request
     * @param model
     * @param userRole
     * @return
     */
    @RequestMapping(value = "/cloudDisk/edit.html")
    public String jumpToCloudDiskParamEditPage(HttpServletRequest request, ModelMap model, Integer userRole) {
        try {
            SharePlatformCloudDiskParam diskParam = otherSettingsService.queryCloudDiskInfoByUserRole(userRole);
            model.put("diskParam", diskParam);
        } catch (Exception e) {
            log.error("查询云盘参数信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/otherSettings/cloudDiskEdit.html";
    }

    /**
     * 
     * (保存云盘参数修改)<br>
     * 
     * @param request
     * @param entity
     * @return
     */
    @RequestMapping(value = "/cloudDisk/save.html")
    @ResponseBody
    public ResultCodeVo saveCloudDiskParamModify(HttpServletRequest request, SharePlatformCloudDiskParam entity) {
        try {
            return otherSettingsService.saveCloudDiskParamModify(entity);
        } catch (Exception e) {
            log.error("保存失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (跳转到平台临时账号设置页面)<br>
     * 
     * @param request 请求
     * @param model map
     * @return
     */
    @RequestMapping(value = "/accountSet.html")
    public String jumpToTemporaryAccountSettingPage(HttpServletRequest request, ModelMap model) {
        try {
            SharePlatform platformInfo = sharePlatformService.queryPlatformInfo();
            model.put("platformInfo", platformInfo);
        } catch (Exception e) {
            log.error("获取平台信息失败！", e);
            e.printStackTrace();
        }
        return "/manage/otherSettings/temporaryAccountSetting.html";
    }

    /**
     * 
     * (保存临时账号设置的修改)<br>
     * 
     * @param request 请求
     * @param entity 平台实体
     * @return 修改结果
     */
    @RequestMapping(value = "/accountSet/save.html")
    @ResponseBody
    public ResultCodeVo saveChangeAllowLogin(HttpServletRequest request, SharePlatform entity) {
        try {
            return otherSettingsService.saveChangeAllowLogin(entity);
        } catch (Exception e) {
            log.error("保存失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

}
