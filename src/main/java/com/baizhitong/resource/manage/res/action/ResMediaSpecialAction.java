package com.baizhitong.resource.manage.res.action;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.res.ResThumbnailDao;
import com.baizhitong.resource.manage.res.service.ResMediaSpecialService;
import com.baizhitong.resource.manage.res.service.ShareResSpecialTypeService;
import com.baizhitong.resource.model.res.ResMediaSpecial;
import com.baizhitong.resource.model.share.ShareResSpecialType;
import com.baizhitong.resource.model.vo.res.ResMediaSpecialVo;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 资源_特色资源管理Action
 * 
 * @author creator zhangqiang 2016年8月9日 上午11:19:33
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/resMediaSpecial")
public class ResMediaSpecialAction extends BaseAction {

    /** 特色资源管理Service */
    @Autowired
    private ResMediaSpecialService     resMediaSpecialService;
    /** 特色资源分类Service */
    @Autowired
    private ShareResSpecialTypeService shareResSpecialTypeService;
    @Autowired
    private ResThumbnailDao            resThumbnailDao;

    /**
     * 
     * (跳转到特色资源管理主页面)<br>
     * 
     * @param request 请求
     * @param map
     * @return
     */
    @RequestMapping(value = "/main.html")
    public String jumpToMainPage(HttpServletRequest request, ModelMap map) {
        return "/manage/res/mediaSpecial/main.html";
    }

    /**
     * 
     * (跳转到全部页面)<br>
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/all.html")
    public String jumpToAllPage(HttpServletRequest request, ModelMap map) {
        try {
            List<ShareResSpecialType> typeList = shareResSpecialTypeService.queryListByLevel1();
            map.put("typeList", typeList);
            // boolean isAreaAdmin=BeanHelper.isAreaAdmin(request);
            if (BeanHelper.isAreaAdmin(request) || BeanHelper.isEduStaff(request)) {
                map.put("isAreaAdmin", true);
            } else {
                map.put("isAreaAdmin", false);
            }
        } catch (Exception e) {
            log.error("获取类别列表出错！", e);
            e.printStackTrace();
        }
        return "/manage/res/mediaSpecial/all.html";
    }

    /**
     * 
     * (根据父编码获取子类别列表)<br>
     * 
     * @param request 请求
     * @param pcode 父编码
     * @return
     */
    @RequestMapping(value = "/getListByPCode.html")
    @ResponseBody
    public List<ShareResSpecialType> getChildListByPCode(HttpServletRequest request, String pcode) {
        try {
            return shareResSpecialTypeService.queryListByPCode(pcode);
        } catch (Exception e) {
            log.error("根据父编码获取子类别列表失败！", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * (查询特色资源全部信息)<br>
     * 
     * @param request 请求
     * @param page 当前页
     * @param rows 每页大小
     * @param shareLevel 分享级别
     * @param resSpecialTypeL1 类别
     * @param resSpecialTypeL2 项目
     * @param resStatus 转码状态
     * @param makerOrgName 制作学校名称
     * @param startDate 开始上传日期
     * @param endDate 结束上传日期
     * @return
     */
    @RequestMapping(value = "/all/search.html")
    @ResponseBody
    public Page<Map<String, Object>> searchSpecialAllInfoPage(HttpServletRequest request, Integer page, Integer rows,
                    Integer shareLevel, String resSpecialTypeL1, String resSpecialTypeL2, Integer resStatus,
                    String makerOrgName, String startDate, String endDate, String resName, String makerName,
                    Integer shareCheckStatus) {
        Page<Map<String, Object>> pageInfo = null;
        UserInfoVo userInfo =getUserInfoVo();
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
            param.put("shareLevel", shareLevel);
            param.put("resSpecialTypeL1", resSpecialTypeL1);
            param.put("resSpecialTypeL2", resSpecialTypeL2);
            param.put("resStatus", resStatus);
            if (StringUtils.isNotEmpty(makerOrgName)) {
                param.put("makerOrgName", makerOrgName);
            }
            if (userInfo != null && !BeanHelper.isAreaAdmin(request) && !BeanHelper.isEduStaff(request)) {
                param.put("makerOrgCode", userInfo.getOrgCode());
            }
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("resName", resName);
            param.put("makerName", makerName);
            param.put("shareCheckStatus", shareCheckStatus);
            pageInfo = resMediaSpecialService.querySpecialAllInfoPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * (删除特色资源)<br>
     * 
     * @param ids 特色资源ID，可以是一个或多个
     * @return
     */
    @RequestMapping(value = "/delete.html")
    @ResponseBody
    public ResultCodeVo deleteResMediaSpecial(@RequestParam("ids[]") String[] ids) {
        ResultCodeVo vo = new ResultCodeVo();
        int delSum = 0;
        try {
            for (int i = 0; i < ids.length; i++) {
                delSum += resMediaSpecialService.deleteResMediaSpecial(ids[i]);
            }
            if (0 < delSum) {
                vo.setSuccess(true);
                vo.setMsg("删除成功！");
            }
        } catch (Exception ex) {
            log.error("删除特色资源失败!", ex);
            vo.setSuccess(false);
            vo.setMsg("删除失败！");
        }
        return vo;
    }

    /**
     * 
     * (跳转到待审核页面)<br>
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/check.html")
    public String jumpToCheckPage(HttpServletRequest request, ModelMap map) {
        try {
            List<ShareResSpecialType> typeList = shareResSpecialTypeService.queryListByLevel1();
            map.put("typeList", typeList);
            // boolean isAreaAdmin=BeanHelper.isAreaAdmin(request);
            if (BeanHelper.isAreaAdmin(request) || BeanHelper.isEduStaff(request)) {
                map.put("isAreaAdmin", true);
            } else {
                map.put("isAreaAdmin", false);
            }
        } catch (Exception e) {
            log.error("获取类别列表出错！", e);
            e.printStackTrace();
        }
        return "/manage/res/mediaSpecial/check.html";
    }

    /**
     * 
     * (查询特色资源待审核数据)<br>
     * 
     * @param request 请求
     * @param page 当前页
     * @param rows 每页大小
     * @param resSpecialTypeL1 类别
     * @param resSpecialTypeL2 项目
     * @param makerOrgName 学校名称
     * @param startDate 开始申请时间
     * @param endDate 结束申请时间
     * @return
     */
    @RequestMapping(value = "/check/search.html")
    @ResponseBody
    public Page<Map<String, Object>> searchSpecialCheckInfoPage(HttpServletRequest request, Integer page, Integer rows,
                    String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName, String startDate,
                    String endDate, String resName, String makerName) {
        Page<Map<String, Object>> pageInfo = null;
        UserInfoVo userInfo =getUserInfoVo();
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
            param.put("resSpecialTypeL1", resSpecialTypeL1);
            param.put("resSpecialTypeL2", resSpecialTypeL2);
            if (StringUtils.isNotEmpty(makerOrgName)) {
                param.put("makerOrgName", makerOrgName);
            }
            if (userInfo != null && !BeanHelper.isAreaAdmin(request) && !BeanHelper.isEduStaff(request)) {
                param.put("makerOrgCode", userInfo.getOrgCode());
            }
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("resName", resName);
            param.put("makerName", makerName);
            pageInfo = resMediaSpecialService.querySpecialCheckInfoPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * (特色资源审核)<br>
     * 
     * @param resCode 资源编码数组
     * @param shareCheckLevel 审核中共享级别数组
     * @param shareCheckStatus 分享审核中状态
     * @param checkComments 审核意见
     * @return
     */
    @RequestMapping(value = "/check/saveExamine.html")
    @ResponseBody
    public ResultCodeVo saveExamine(@RequestParam("resCode[]") String[] resCode,
                    @RequestParam("shareCheckLevel[]") String[] shareCheckLevel, Integer shareCheckStatus,
                    String checkComments) {
        try {
            return resMediaSpecialService.saveExamine(resCode, shareCheckLevel, shareCheckStatus, checkComments);
        } catch (Exception ex) {
            log.error("特色资源审核操作失败!", ex);
            ex.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (跳转到特色资源已通过审核页面)<br>
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/checked.html")
    public String jumpToCheckedPage(HttpServletRequest request, ModelMap map) {
        try {
            List<ShareResSpecialType> typeList = shareResSpecialTypeService.queryListByLevel1();
            map.put("typeList", typeList);
            if (BeanHelper.isAreaAdmin(request) || BeanHelper.isEduStaff(request)) {
                map.put("isAreaAdmin", true);
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
            } else {
                map.put("isAreaAdmin", false);
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
            }

        } catch (Exception e) {
            log.error("获取类别列表出错！", e);
            e.printStackTrace();
        }
        return "/manage/res/mediaSpecial/checked.html";
    }

    /**
     * 
     * (查询特色资源已通过审核数据)<br>
     * 
     * @param request 请求
     * @param page 当前页
     * @param rows 每页大小
     * @param resSpecialTypeL1 类别
     * @param resSpecialTypeL2 项目
     * @param makerOrgName 学校名称
     * @param startDate 开始审核日期
     * @param endDate 结束审核日期
     * @return
     */
    @RequestMapping(value = "/checked/search.html")
    @ResponseBody
    public Page<Map<String, Object>> searchSpecialCheckedInfoPage(HttpServletRequest request, Integer page,
                    Integer rows, String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName,
                    String startDate, String endDate, String resName, String makerName, Integer shareCheckLevel) {
        Page<Map<String, Object>> pageInfo = null;
        UserInfoVo userInfo =getUserInfoVo();
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
            param.put("resSpecialTypeL1", resSpecialTypeL1);
            param.put("resSpecialTypeL2", resSpecialTypeL2);
            if (StringUtils.isNotEmpty(makerOrgName)) {
                param.put("makerOrgName", makerOrgName);
            }
            if (userInfo != null && !BeanHelper.isAreaAdmin(request) && !BeanHelper.isEduStaff(request)) {
                param.put("makerOrgCode", userInfo.getOrgCode());
            }
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("resName", resName);
            param.put("makerName", makerName);
            param.put("shareCheckLevel", shareCheckLevel);
            pageInfo = resMediaSpecialService.querySpecialCheckedInfoPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * (跳转到特色资源已退回页面)<br>
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping(value = "/backed.html")
    public String jumpToBackedPage(HttpServletRequest request, ModelMap map) {
        try {
            List<ShareResSpecialType> typeList = shareResSpecialTypeService.queryListByLevel1();
            map.put("typeList", typeList);
            if (BeanHelper.isAreaAdmin(request) || BeanHelper.isEduStaff(request)) {
                map.put("isAreaAdmin", true);
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
            } else {
                map.put("isAreaAdmin", false);
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
            }
        } catch (Exception e) {
            log.error("获取类别列表出错！", e);
            e.printStackTrace();
        }
        return "/manage/res/mediaSpecial/backed.html";
    }

    /**
     * 
     * (查询特色资源已退回数据)<br>
     * 
     * @param request 请求
     * @param page 当前页
     * @param rows 每页大小
     * @param resSpecialTypeL1 类别
     * @param resSpecialTypeL2 项目
     * @param makerOrgName 学校名称
     * @param startDate 开始审核日期
     * @param endDate 结束审核日期
     * @return
     */
    @RequestMapping(value = "/backed/search.html")
    @ResponseBody
    public Page<Map<String, Object>> searchSpecialBackedInfoPage(HttpServletRequest request, Integer page, Integer rows,
                    String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName, String startDate,
                    String endDate, String resName, String makerName, Integer shareCheckLevel) {
        Page<Map<String, Object>> pageInfo = null;
        UserInfoVo userInfo =getUserInfoVo();
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
            param.put("resSpecialTypeL1", resSpecialTypeL1);
            param.put("resSpecialTypeL2", resSpecialTypeL2);
            if (StringUtils.isNotEmpty(makerOrgName)) {
                param.put("makerOrgName", makerOrgName);
            }
            if (userInfo != null && !BeanHelper.isAreaAdmin(request) && !BeanHelper.isEduStaff(request)) {
                param.put("makerOrgCode", userInfo.getOrgCode());
            }
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("resName", resName);
            param.put("makerName", makerName);
            param.put("shareCheckLevel", shareCheckLevel);
            pageInfo = resMediaSpecialService.querySpecialBackedInfoPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * (特色资源全部页面数据导出)<br>
     * 
     * @param request
     * @param response
     * @param shareLevel
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param resStatus
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/all/export.html")
    public ModelAndView exportAllData(HttpServletRequest request, HttpServletResponse response, Integer shareLevel,
                    String resSpecialTypeL1, String resSpecialTypeL2, Integer resStatus, String makerOrgName,
                    String startDate, String endDate, String resName, String makerName, Integer shareCheckStatus) {
        try {
            // 避免中文乱码
            if (StringUtils.isNotEmpty(makerOrgName)) {
                makerOrgName = URLDecoder.decode(makerOrgName);
            }
            if (StringUtils.isNotEmpty(resName)) {
                resName = URLDecoder.decode(resName);
            }
            if (StringUtils.isNotEmpty(makerName)) {
                makerName = URLDecoder.decode(makerName);
            }
            String fileName = "特色资源(全部).xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = resMediaSpecialService.getAllDataExcel(shareLevel, resSpecialTypeL1, resSpecialTypeL2,
                            resStatus, makerOrgName, startDate, endDate, resName, makerName, shareCheckStatus);
            wb.write(stream);
            byte[] buffer = stream.toByteArray();
            response.getOutputStream().write(buffer);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 
     * (特色资源待审核数据导出)<br>
     * 
     * @param request
     * @param response
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/check/export.html")
    public ModelAndView exportCheckData(HttpServletRequest request, HttpServletResponse response,
                    String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName, String startDate,
                    String endDate, String resName, String makerName) {
        try {
            // 避免中文乱码
            if (StringUtils.isNotEmpty(makerOrgName)) {
                makerOrgName = URLDecoder.decode(makerOrgName);
            }
            if (StringUtils.isNotEmpty(resName)) {
                resName = URLDecoder.decode(resName);
            }
            if (StringUtils.isNotEmpty(makerName)) {
                makerName = URLDecoder.decode(makerName);
            }
            String fileName = "特色资源(待审核).xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = resMediaSpecialService.getCheckDataExcel(resSpecialTypeL1, resSpecialTypeL2, makerOrgName,
                            startDate, endDate, resName, makerName);
            wb.write(stream);
            byte[] buffer = stream.toByteArray();
            response.getOutputStream().write(buffer);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 
     * (特色资源已通过审核数据导出)<br>
     * 
     * @param request
     * @param response
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @param resName
     * @param makerName
     * @param shareCheckLevel
     * @return
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/checked/export.html")
    public ModelAndView exportCheckedData(HttpServletRequest request, HttpServletResponse response,
                    String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName, String startDate,
                    String endDate, String resName, String makerName, Integer shareCheckLevel) {
        try {
            // 避免中文乱码
            if (StringUtils.isNotEmpty(makerOrgName)) {
                makerOrgName = URLDecoder.decode(makerOrgName);
            }
            if (StringUtils.isNotEmpty(resName)) {
                resName = URLDecoder.decode(resName);
            }
            if (StringUtils.isNotEmpty(makerName)) {
                makerName = URLDecoder.decode(makerName);
            }
            String fileName = "特色资源(已通过审核).xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = resMediaSpecialService.getCheckedDataExcel(resSpecialTypeL1, resSpecialTypeL2,
                            makerOrgName, startDate, endDate, resName, makerName, shareCheckLevel);
            wb.write(stream);
            byte[] buffer = stream.toByteArray();
            response.getOutputStream().write(buffer);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 
     * (特色资源已退回数据导出)<br>
     * 
     * @param request
     * @param response
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @param resName
     * @param makerName
     * @param shareCheckLevel
     * @return
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/backed/export.html")
    public ModelAndView exportBackedData(HttpServletRequest request, HttpServletResponse response,
                    String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName, String startDate,
                    String endDate, String resName, String makerName, Integer shareCheckLevel) {
        try {
            // 避免中文乱码
            if (StringUtils.isNotEmpty(makerOrgName)) {
                makerOrgName = URLDecoder.decode(makerOrgName);
            }
            if (StringUtils.isNotEmpty(resName)) {
                resName = URLDecoder.decode(resName);
            }
            if (StringUtils.isNotEmpty(makerName)) {
                makerName = URLDecoder.decode(makerName);
            }
            String fileName = "特色资源(已退回).xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = resMediaSpecialService.getBackedDataExcel(resSpecialTypeL1, resSpecialTypeL2,
                            makerOrgName, startDate, endDate, resName, makerName, shareCheckLevel);
            wb.write(stream);
            byte[] buffer = stream.toByteArray();
            response.getOutputStream().write(buffer);
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 
     * (跳转到特色资源查看和修改页面)<br>
     * 
     * @param request
     * @param map
     * @param resId
     * @param check
     * @return
     */
    @RequestMapping("/edit.html")
    public ModelAndView jumpToMediaResEditPage(HttpServletRequest request, ModelMap map, Integer resId, Integer check) {
        try {
            if (resId == null)
                return super.jumpToViewWithDomainConfig("/manage/res/mediaSpecial/edit.html", request, map);
            ResMediaSpecialVo vo = resMediaSpecialService.querySpecialMediaById(resId);
            map.put("check", check);
            List<ShareResSpecialType> typeList = shareResSpecialTypeService.queryListByLevel1();// 类别列表
            map.put("typeList", JSONArray.toJSONString(typeList));
            map.put("resource", vo);
           
            map.put("thumbnailList", JSONArray.toJSONString(resThumbnailDao.getThumbnailList(vo.getResCode())));
            map.put("res", JSON.toJSONString(vo));
        } catch (Exception e) {
            log.error("获取资源信息失败！", e);
            e.printStackTrace();
        }
        return super.jumpToViewWithDomainConfig("/manage/res/mediaSpecial/edit.html", request, map);
    }

    /**
     * 
     * (保存或修改特色资源信息)<br>
     * 
     * @param request 请求
     * @param entity 实体
     * @return
     */
    @RequestMapping(value = "/updateBasicInfo.html")
    @ResponseBody
    public ResultCodeVo saveResMediaSpecial(HttpServletRequest request, ResMediaSpecial entity) {
        try {
            return resMediaSpecialService.saveResMediaSpecial(entity);
        } catch (Exception e) {
            log.error("保存或修改特色资源信息操作失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (跳转到查看审核详细信息页面)<br>
     * 
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/toCheckDetail.html")
    public String jumpToCheckDetailPage(HttpServletRequest request, ModelMap model, String resCode, Integer resTypeL1) {
        model.put("resCode", resCode);
        model.put("resTypeL1", resTypeL1);
        return "/manage/res/mediaSpecial/checkDetail.html";
    }

    /**
     * 
     * (查询资源审核情况明细数据)<br>
     * 
     * @param request
     * @param page
     * @param rows
     * @param resCode
     * @param resTypeL1
     * @return
     */
    @RequestMapping(value = "/checkDetail/search.html")
    @ResponseBody
    public Page<Map<String, Object>> searchCheckDetailInfoPage(HttpServletRequest request, Integer page, Integer rows,
                    String resCode, Integer resTypeL1) {
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
            param.put("resCode", resCode);
            param.put("resTypeL1", resTypeL1);
            pageInfo = resMediaSpecialService.queryCheckDetailInfoPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * (跳转到审核意见详细页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/detail.html")
    public String jumpToCheckCommentsDetailPage(ModelMap model) {
        return "/manage/res/mediaSpecial/detail.html";
    }
}
