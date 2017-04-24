package com.baizhitong.resource.manage.res.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.manage.platform.service.ISharePlatformService;
import com.baizhitong.resource.manage.res.service.ResSettingsHomeService;
import com.baizhitong.resource.manage.res.service.ResTypeService;
import com.baizhitong.resource.manage.res.service.ShareResSpecialTypeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.model.res.ResSettingsHome;
import com.baizhitong.resource.model.share.SharePlatform;
import com.baizhitong.resource.model.share.ShareResSpecialType;
import com.baizhitong.resource.model.share.ShareResTypeL2;
import com.baizhitong.utils.ObjectUtils;

/**
 * 
 * 资源设置-首页显示Action
 * 
 * @author creator zhangqiang 2016年7月27日 下午1:27:08
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/resSettingsHome")
public class ResSettingsHomeAction extends BaseAction {

    /** 资源设置-首页显示接口 */
    @Autowired
    private ResSettingsHomeService     resSettingsHomeService;
    /** 资源类型接口 */
    @Autowired
    private ResTypeService             resTypeService;
    /** 学段接口 */
    @Autowired
    private SectionService             sectionService;
    /** 特色资源分类接口 */
    @Autowired
    private ShareResSpecialTypeService shareResSpecialTypeService;
    /** 平台信息接口 */
    @Autowired
    private ISharePlatformService      sharePlatformService;

    /**
     * 
     * (跳转到资源设置显示页面)<br>
     * 
     * @param request 请求
     * @param map
     * @return
     */
    @RequestMapping(value = "list.html")
    public String jumpToResSettingsHomeInfoPage(HttpServletRequest request, ModelMap map) {
        try {
            List<ShareResTypeL2> resTypeL2List = resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_MEDIA);
            ShareResTypeL2 removeEntity = null;
            if (resTypeL2List != null && resTypeL2List.size() > 0) {
                for (ShareResTypeL2 entity : resTypeL2List) {
                    if ("1099".equals(entity.getCode())) {
                        removeEntity = entity;
                    }
                }
            }
            resTypeL2List.remove(removeEntity);
            map.put("resTypeL2List", resTypeL2List);
        } catch (Exception e) {
            log.error("获取视频二级分类列表出错！", e);
            e.printStackTrace();
        }
        return "/manage/res/settingsHome/res_settings_home.html";
    }

    /**
     * 
     * (分页查询资源设置显示信息)<br>
     * 
     * @param request 请求
     * @param page 当前页
     * @param rows 每页大小
     * @param resName 资源名称
     * @param setType 显示类别
     * @return
     */
    @RequestMapping(value = "/search.html")
    public @ResponseBody Page<Map<String, Object>> searchResSettingsHomeInfoPage(HttpServletRequest request,
                    Integer page, Integer rows, String resName, String setType) {
        Page<Map<String, Object>> pageInfo = null;
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
            param.put("resName", resName);
            param.put("setType", setType);
            pageInfo = resSettingsHomeService.queryResSettingsHomeInfoPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * (跳转到资源设置页面)<br>
     * 
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/resSet.html")
    public String jumpToResSettingPage(ModelMap map, HttpServletRequest request) {
        /*
         * try { map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
         * map.put("resTypeL2List",
         * JSONArray.toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.
         * RES_TYPE_MEDIA))); } catch (Exception e) { log.error("查询学段错误！", e); } return
         * "/manage/res/settingsHome/addMedia.html";
         */
        try {
            SharePlatform platform = sharePlatformService.queryPlatformInfo();
            if (ObjectUtils.isNotNull(platform)) {
                map.put("deployLevel", platform.getDeployLevel());
            }
        } catch (Exception e) {
            log.error("获取平台部署级别失败！", e);
            e.printStackTrace();
        }
        return "/manage/res/settingsHome/addTab.html";
    }

    /**
     * 
     * (跳转到是视频资源页面)<br>
     * 
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/resSet/media.html")
    public String jumpToMediaResPage(ModelMap map, HttpServletRequest request) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            map.put("resTypeL2List", JSONArray
                            .toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_MEDIA)));
        } catch (Exception e) {
            log.error("查询学段错误！", e);
        }
        return "/manage/res/settingsHome/addMedia.html";
    }

    /**
     * 
     * (跳转到特色资源页面)<br>
     * 
     * @param map
     * @param request
     * @return
     */
    @RequestMapping(value = "/resSet/mediaSpecial.html")
    public String jumpToMediaSpecialResPage(ModelMap map, HttpServletRequest request) {
        try {
            List<ShareResSpecialType> typeList = shareResSpecialTypeService.queryListByLevel1();
            map.put("typeList", typeList);
        } catch (Exception e) {
            log.error("获取特色资源一级分类列表失败！", e);
            e.printStackTrace();
        }
        return "/manage/res/settingsHome/addMediaSpecial.html";
    }

    /**
     * 
     * (分页查询视频资源列表)<br>
     * 
     * @param orgName 机构名称
     * @param userName 作者姓名
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param resName 资源名称
     * @param resTypeL2 资源二级分类
     * @param rows 每页大小
     * @param page 当前页
     * @param request 请求
     * @return
     */
    @RequestMapping(value = "/media/search.html")
    public @ResponseBody Page<Map<String, Object>> getMediaPage(String orgName, String userName, String sectionCode,
                    String gradeCode, String subjectCode, String resName, String resTypeL2, Integer rows, Integer page,
                    HttpServletRequest request, Integer shareLevel) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 15;
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gradeCode", gradeCode);
        param.put("orgName", orgName);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("resName", resName);
        param.put("userName", userName);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        try {
            return resSettingsHomeService.queryMediaPageInfo(param, rows, page);
        } catch (Exception e) {
            log.error("视频资源信息查询失败！", e);
            return null;
        }
    }

    /**
     * 
     * (分页查询特色资源列表)<br>
     * 
     * @param rows
     * @param page
     * @param request
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param resName
     * @return
     */
    @RequestMapping(value = "/mediaSpecial/search.html")
    public @ResponseBody Page<Map<String, Object>> getMediaSpecialPage(Integer rows, Integer page,
                    HttpServletRequest request, String resSpecialTypeL1, String resSpecialTypeL2, String resName,
                    Integer shareLevel) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 15;
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("resName", resName);
        param.put("resSpecialTypeL1", resSpecialTypeL1);
        param.put("resSpecialTypeL2", resSpecialTypeL2);
        param.put("shareLevel", shareLevel);
        try {
            return resSettingsHomeService.queryMediaSpecialPageInfo(param, rows, page);
        } catch (Exception e) {
            log.error("特色资源信息查询失败！", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * (添加或修改资源首页设置显示)<br>
     * 
     * @param request 请求
     * @param entity 实体
     * @return
     */
    @RequestMapping(value = "/resSet/add.html")
    @ResponseBody
    public ResultCodeVo saveResSettingsHome(HttpServletRequest request, ResSettingsHome entity) {
        return resSettingsHomeService.addResSettingsHome(entity);
    }

    /**
     * 
     * 删除首页显示资源
     * 
     * @param id 主键id
     * @return
     */
    @RequestMapping(value = "/delete.html")
    @ResponseBody
    public ResultCodeVo deleteResSettingsHome(String id) {
        ResultCodeVo vo = new ResultCodeVo();
        try {
            int delSum = resSettingsHomeService.deleteResSettingsHome(id);
            if (1 == delSum) {
                vo.setSuccess(true);
                vo.setMsg("删除成功！");
            }
        } catch (Exception ex) {
            log.error("删除首页显示资源失败!", ex);
            vo.setSuccess(false);
            vo.setMsg("删除失败！");
        }
        return vo;
    }

    /**
     * 
     * (跳转到图片查看页面)<br>
     * 
     * @param request 请求
     * @param model
     * @param picUrl 图片ftp地址
     * @return
     */
    @RequestMapping(value = "showPic.html")
    public String jumpToShowPicPage(HttpServletRequest request, ModelMap model, String picUrl) {
        model.put("picUrl", picUrl);
        return "/manage/res/settingsHome/showPicture.html";
    }

    /**
     * 
     * (跳转到编辑页面)<br>
     * 
     * @param request 请求
     * @param model
     * @return
     */
    @RequestMapping("/edit.html")
    public String jumpToEditPicPage(HttpServletRequest request, ModelMap model) {
        try {
            List<ShareResTypeL2> resTypeL2List = resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_MEDIA);
            ShareResTypeL2 removeEntity = null;
            if (resTypeL2List != null && resTypeL2List.size() > 0) {
                for (ShareResTypeL2 entity : resTypeL2List) {
                    if ("1099".equals(entity.getCode())) {
                        removeEntity = entity;
                    }
                }
            }
            resTypeL2List.remove(removeEntity);
            model.put("resTypeL2List", resTypeL2List);
        } catch (Exception e) {
            log.error("获取视频二级分类列表出错！", e);
            e.printStackTrace();
        }
        return "/manage/res/settingsHome/edit.html";
    }

    /**
     * 
     * (跳转到特色资源设置编辑页面)<br>
     * 
     * @param request 请求
     * @param model
     * @return
     */
    @RequestMapping("/editSpec.html")
    public String jumpToEditSpecPicPage(HttpServletRequest request, ModelMap model) {
        return "/manage/res/settingsHome/editSpec.html";
    }

    /**
     * 
     * (修改资源在首页是否使用)<br>
     * 
     * @param id 主键id
     * @param setType 显示类别
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param flagAvailable 是否使用
     * @param resSpecialTypeL2 特色资源二级分类
     * @return
     */
    @RequestMapping(value = "/changeFlagAvailable.html")
    @ResponseBody
    public ResultCodeVo changeFlagAvailable(String id, String setType, String sectionCode, String subjectCode,
                    Integer flagAvailable, String resSpecialTypeL2) {
        try {
            return resSettingsHomeService.changeFlagAvailable(id, setType, sectionCode, subjectCode, flagAvailable,
                            resSpecialTypeL2);
        } catch (Exception e) {
            log.error("修改资源在首页是否使用出错！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }
}
