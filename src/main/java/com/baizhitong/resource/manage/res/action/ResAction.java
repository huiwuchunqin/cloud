package com.baizhitong.resource.manage.res.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.baizhitong.resource.common.core.service.ResourceService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.res.ResThumbnailDao;
import com.baizhitong.resource.manage.company.service.ICompanyService;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.res.service.ResMediaSpecialService;
import com.baizhitong.resource.manage.res.service.ResService;
import com.baizhitong.resource.manage.res.service.ResTypeService;
import com.baizhitong.resource.manage.res.service.impl.DataFormatter;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.subject.service.SubjectService;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointService;
import com.baizhitong.resource.manage.textbook.service.TextbookVersionService;
import com.baizhitong.resource.model.authority.AuthRole;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.resource.model.vo.company.CompanyInfoVo;
import com.baizhitong.resource.model.vo.res.DocumentReaderPlayListVO;
import com.baizhitong.resource.model.vo.res.ResMediaSpecialVo;
import com.baizhitong.resource.model.vo.res.ResVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 资源管理控制类
 * 
 * @author gaow
 * @date:2015年12月21日 上午9:52:06
 */
@Controller
@RequestMapping(value = "/manage/res")
public class ResAction extends BaseAction {
    private @Autowired SectionService                sectionService;        // 学段接口
    private @Autowired ResService                    resService;            // 资源接口
    private @Autowired GradeService                  gradeService;          // 年级接口
    private @Autowired SubjectService                subjectService;        // 学科接口
    private @Autowired TextbookChapterService        chapterService;        // 章节接口
    private @Autowired TextbookKnowledgePointService knowledgeService;      // 知识点接口
    private @Autowired TextbookVersionService        versionService;        // 版本接口
    private @Autowired ResourceService               resourceService;       // 资源服务接口
    private @Autowired ResTypeService                resTypeService;        // 资源类型接口
    @Autowired
    private ICompanyService                          companyService;
    private @Autowired ResThumbnailDao               resThumbnailDao; 
    private @Autowired ResMediaSpecialService        resMediaSpecialService;

    /**
     * 跳转到文档资源
     * 
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月16日 下午3:46:21
     */
    @RequestMapping(value = "/toDocList.html")
    public String toDocCheckList(ModelMap map, String checkStatus, HttpServletRequest request) {
        try {
            UserInfoVo userInfoVo =getUserInfoVo();
            // 非区域管理员
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            if (userInfoVo != null && BeanHelper.isEduStaff(request)) {
                map.put("sectionList",
                                JSONArray.toJSONString(sectionService.queryUserSectionList(userInfoVo.getUserCode())));
            }
            if (BeanHelper.isSchoolAdmin(request)) {
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
            }
            if (BeanHelper.isAreaAdmin(request) || BeanHelper.isEduStaff(request)) {
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
                map.put("areaAdmin", 1);
            }
            map.put("resTypeL2List", JSONArray.toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_DOC)));
            if(StringUtils.isNotEmpty(checkStatus)){
                map.put("checkStatus", checkStatus);
                return "/manage/res/docCheckList.html";
            }else{
                return "/manage/res/docList.html";
            }
        } catch (Exception e) {
            log.error("查询学段错误", e);
            return "";
        }

    }

    /**
     * 不正常文档资源 ()<br>
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/toUnUsualDocList.html")
    public String toUnUsualDoc(ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("查询学段错误", e);
            return "/manage/res/unUsualDocList.html";
        }
        return "/manage/res/unUsualDocList.html";

    }

    /**
     * 不正常视频资源 ()<br>
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/toUnUsualMediaList.html")
    public String toUnUsualMeda(ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("查询学段错误", e);
            return "/manage/res/unUsualMediaList.html";
        }
        return "/manage/res/unUsualMediaList.html";
    }

    /**
     * 
     * (跳转到资源回收站页面)<br>
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/toGarbageList.html")
    public String jumpToGarbageResList(ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            log.error("查询学段错误！", e);
            e.printStackTrace();
        }
        return "/manage/res/garbageList.html";
    }

    /**
     * 
     * (查询资源回收站资源)<br>
     * 
     * @param orgName
     * @param sectionCode
     * @param gradeCode
     * @param subjectCode
     * @param resTypeL1
     * @param rows
     * @param page
     * @param resName
     * @return
     */
    @RequestMapping(value = "/garbageRes/search.html")
    @ResponseBody
    public Page<ResVo> getGarbagePage(String orgName, String sectionCode, String gradeCode, String subjectCode,
                    String resTypeL1, Integer rows, Integer page, String resName) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("resTypeL1", resTypeL1);
        param.put("orgName", orgName);
        param.put("resName", resName);
        try {
            return resService.queryGarbageResPage(param, rows, page);
        } catch (Exception e) {
            log.error("查询资源回收站资源失败！", e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 
     * (回收站资源，删除或恢复操作)<br>
     * 
     * @param docIds 文档ID集合
     * @param mediaIds 视频ID集合
     * @param mediaSpecialIds 特色资源ID集合
     * @param lessonIds 课时ID集合
     * @param exerciseIds 测验ID集合
     * @param questionIds 题目ID集合
     * @param flashIds 互动资源ID集合
     * @param operateType 操作类型：1，删除；2，恢复；
     * @return
     */
    @RequestMapping(value = "/operateGarbageRes.html")
    @ResponseBody
    public ResultCodeVo operateGarbageRes(String docIds, String mediaIds, String mediaSpecialIds, String lessonIds,
                    String exerciseIds, String questionIds, String flashIds, Integer operateType) {
        try {
            return resService.operateGarbageRes(docIds, mediaIds, mediaSpecialIds, lessonIds, exerciseIds, questionIds,
                            flashIds, operateType);
        } catch (Exception e) {
            log.error("回收站资源，删除或恢复操作失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (跳转到特色资源预览页面)<br>
     * 
     * @param request
     * @param resId
     * @param model
     * @return
     */
    @RequestMapping(value = "/resMediaSpecialPreview.html")
    public ModelAndView jumpToSpecialMediaPreviewPage(HttpServletRequest request, Integer resId, ModelMap model) {
        if (resId == null)
            return super.jumpToViewWithDomainConfig("/manage/res/mediaSpecial/preview.html", request, model);
        try {
            ResMediaSpecialVo resource = resMediaSpecialService.querySpecialMediaById(resId);
            model.put("resource", resource);
            model.put("res", JSON.toJSONString(resource));
        } catch (Exception e) {
            log.error("特色资源查询出错！", e);
            e.printStackTrace();
        }
        return super.jumpToViewWithDomainConfig("/manage/res/mediaSpecial/preview.html", request, model);
    }

    /**
     * 跳转到视频审核列表资源
     * 
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月16日 下午3:46:21
     */
    @RequestMapping(value = "/toMediaList.html")
    public String toMedia(ModelMap map, String checkStatus, HttpServletRequest request) {
        try {
            UserInfoVo userInfoVo =getUserInfoVo();
            // 非区域管理员
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));

            if (BeanHelper.isEduStaff(request)) {
                map.put("sectionList",
                                JSONArray.toJSONString(sectionService.queryUserSectionList(userInfoVo.getUserCode())));
            }
            if (BeanHelper.isSchoolAdmin(request)) {
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
            }
            if (BeanHelper.isAreaAdmin(request) || BeanHelper.isEduStaff(request)) {
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
                map.put("areaAdmin", 1);
            }
         
            map.put("resTypeL2List", JSONArray.toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_MEDIA)));
               if(StringUtils.isNotEmpty(checkStatus)){
                   map.put("checkStatus", checkStatus);
                   return "/manage/res/mediaCheckList.html" ;
               }else{
                   return "/manage/res/mediaList.html";
               }
        } catch (Exception e) {
            log.error("查询学段错误", e);
            return "";
        }
    }

    /**
     * 跳转到视频信息和视频审核信息列表 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/mediaTabList.html")
    public String toMeidaTabList() {
        return "/manage/res/mediaTabList.html";
    }

    /**
     * 文档信息和文档信息审核列表 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/docTabList.html")
    public String toDocTabList() {
        return "/manage/res/docTabList.html";
    }

    /**
     * 
     * 查询文档资源信息
     * 
     * @param sectionCode 学段名
     * @param orgName 机构名
     * @param userName 作者
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param shareCheckStatus 分享级别
     * @param uploadTimeStart 上传时间开始
     * @param uploadTimeEnd 上传时间结束
     * @param resStatus 资源状态
     * @param shareLevel 分享级别
     * @param resName 资源名称
     * @param tbcTagStatus 章节标注情况
     * @param kpTagStatus 知识点标注情况
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping(value = "/docList.html")
    public @ResponseBody Page<ResVo> getDocPage(String orderType, String resType, String sectionCode, String orgName,
                    String userName, String gradeCode, String subjectCode, Integer shareCheckStatus,
                    String uploadTimeStart, String uploadTimeEnd, Integer resStatus, Integer shareLevel,
                    Integer shareCheckLevel, String resName, Integer tbcTagStatus, Integer kpTagStatus, Integer rows,
                    Integer page, HttpServletRequest request, String resTypeL2, String checkTimeStart,
                    String checkTimeEnd, String shareTimeStart, String shareTimeEnd, Integer resStatusSuccess) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        UserInfoVo userInfoVo =getUserInfoVo();
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("resStatusSuccess", resStatusSuccess);
        param.put("resName", resName);
        param.put("orgName", orgName);
        param.put("userName", userName);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("resStatus", resStatus);
        param.put("shareLevel", shareLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("kpTagStatus", kpTagStatus);
        param.put("orderType", orderType);
        param.put("resTypeL2", resTypeL2);
        param.put("resType", resType);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        try {
            return resService.getDocPageInfo(param, rows, page);
        } catch (Exception e) {
            log.error("文档资源信息查询失败", e);
            return null;
        }
    }

    /**
     * 
     * 导出 文档()<br>
     * 
     * @return
     */
    @RequestMapping("/exportDoc.html")
    @ResponseBody
    public ResultCodeVo exportDoc(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String resType, String sectionCode, String orgName, String userName, String gradeCode,
                    String subjectCode, Integer shareCheckStatus, String uploadTimeStart, String uploadTimeEnd,
                    Integer resStatus, Integer shareLevel, Integer shareCheckLevel, String resName,
                    Integer tbcTagStatus, Integer kpTagStatus, String resTypeL2, String checkTimeStart,
                    String checkTimeEnd, String shareTimeStart, String shareTimeEnd) {
        UserInfoVo userInfoVo =getUserInfoVo();
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("resName", resName);
        param.put("orgName", orgName);
        param.put("userName", userName);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("resStatus", resStatus);
        param.put("shareLevel", shareLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("kpTagStatus", kpTagStatus);
        param.put("orderType", orderType);
        param.put("resTypeL2", resTypeL2);
        param.put("resType", resType);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = resService.getDocPageInfo(param, Integer.MAX_VALUE,1);
        } catch (Exception e) {
            e.printStackTrace(); 
            log.error("查询文档失败", e);
            return null;
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
        map.put("文档类别", "resTypeL2Name");
        map.put("机构名称", "orgName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
        map.put("转码状态", "resStatusStr");
        map.put("当前级别", "shareLevelStr");
        map.put("共享审核级别", "shareCheckLevelStr");
        map.put("共享审核状态", "shareCheckStatusStr");
        map.put("知识点", "kpCountStr");
        map.put("教材章节", "tbcCountStr");
        map.put("可下载", "allowDownload");
        // map.put("可浏览","allowBrowser");
        map.put("作者", "makerName");
        map.put("上传时间", "makeTime");
        map.put("资源描述", "resDesc");
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        response.setContentType("application/x-msdownload ");
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String("文档信息.xls".getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        DataFormatter.format(list);
        Map<String, Integer> fontSizeMap = new HashMap<String, Integer>();
        fontSizeMap.put("resName", 50);
        fontSizeMap.put("orgName", 50);
        fontSizeMap.put("resDesc", 50);
        fontSizeMap.put("makeTime", 15);
        HSSFWorkbook excel = ExcelUtils.getWb(list, map, fontSizeMap);
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    /**
     * 
     * 导出 文档审核()<br>
     * 
     * @return
     */
    @RequestMapping("/exportDocCheck.html")
    @ResponseBody
    public ResultCodeVo exportDocCheck(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String resType, String sectionCode, String orgName, String userName, String gradeCode,
                    String subjectCode, Integer shareCheckStatus, String uploadTimeStart, String uploadTimeEnd,
                    Integer resStatus, Integer shareLevel, Integer shareCheckLevel, String resName,
                    Integer tbcTagStatus, Integer kpTagStatus, String resTypeL2, String checkTimeStart,
                    String checkTimeEnd, String shareTimeStart, String shareTimeEnd) {
        UserInfoVo userInfoVo =getUserInfoVo();
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("resName", resName);
        param.put("orgName", orgName);
        param.put("userName", userName);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("resStatus", resStatus);
        param.put("shareLevel", shareLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("kpTagStatus", kpTagStatus);
        param.put("orderType", orderType);
        param.put("resTypeL2", resTypeL2);
        param.put("resType", resType);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = resService.getDocPageInfo(param,Integer.MAX_VALUE, 1);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询文档失败", e);
            return null;
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
        map.put("文档类别", "resTypeL2Name");
        map.put("机构名称", "orgName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
        map.put("转码状态", "resStatusStr");
        map.put("当前级别", "shareLevelStr");
        map.put("共享审核状态", "shareCheckStatusStr");
        map.put("可下载", "allowDownload");
        // map.put("可浏览","allowBrowser");
        map.put("作者", "makerName");
        if (10==shareCheckStatus) {
            map.put("申请时间", "shareTime");
        } else {
            map.put("申请时间", "shareTime");
            map.put("审核时间", "shareCheckTime");
            map.put("审核人", "checkerName");
            map.put("审核意见", "checkComments");
        }

        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        response.setContentType("application/x-msdownload ");
        String fileName = "";
        if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
            fileName = "文档审核(待审核).xls";
        } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
            fileName = "文档审核(已通过).xls";
        } else {
            fileName = "文档审核(已退回).xls";
        }
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        Map<String, Integer> fontSizeMap = new HashMap<String, Integer>();
        fontSizeMap.put("resName", 50);
        fontSizeMap.put("orgName", 50);
        fontSizeMap.put("resDesc", 50);
        fontSizeMap.put("makeTime", 15);
        fontSizeMap.put("shareTime", 15);
        fontSizeMap.put("shareCheckTime", 15);
        fontSizeMap.put("checkComments", 80);
        DataFormatter.format(list);
        HSSFWorkbook excel = ExcelUtils.getWb(list, map, fontSizeMap);
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    /**
     * 
     * 视频列表
     * 
     * @param orgName 机构名称
     * @param userName 作者
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param shareCheckStatus 分享审核级别
     * @param uploadTimeStart 上传时间开始
     * @param uploadTimeEnd 上传时间结束
     * @param resStatus 资源状态
     * @param shareLevel 分享级别
     * @param resName 资源名称
     * @param tbcTagStatus 章节标注状态
     * @param kpTagStatus 知识点标注状态
     * @param resTypeL2 资源小分类
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping(value = "/mediaList.html")
    public @ResponseBody Page<ResVo> getMeidaPage(String orderType, String orgName, String userName, String sectionCode,
                    String gradeCode, String subjectCode, Integer shareCheckStatus, Integer shareCheckLevel,
                    String uploadTimeStart, String uploadTimeEnd, Integer resStatus, Integer shareLevel, String resName,
                    Integer tbcTagStatus, Integer kpTagStatus, String resTypeL2, Integer rows, Integer page,
                    HttpServletRequest request, String checkTimeStart, String checkTimeEnd, String shareTimeStart,
                    String shareTimeEnd, Integer resStatusSuccess) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        UserInfoVo userInfoVo =getUserInfoVo();
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gradeCode", gradeCode);
        param.put("orgName", orgName);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("resName", resName);
        param.put("resStatus", resStatus);
        param.put("shareLevel", shareLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("kpTagStatus", kpTagStatus);
        param.put("orderType", orderType);
        param.put("userName", userName);
        param.put("resTypeL2", resTypeL2);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("resStatusSuccess", resStatusSuccess);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        try {
            return resService.getMediaPageInfo(param, rows, page);
        } catch (Exception e) {
            log.error("视频资源信息查询失败", e);
            return null;
        }
    }

    /**
     * 
     * 导出 视频()<br>
     * 
     * @return
     */
    @RequestMapping("/exportMedia.html")
    @ResponseBody
    public ResultCodeVo exportMedia(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String orgName, String userName, String sectionCode, String gradeCode, String subjectCode,
                    Integer shareCheckStatus, Integer shareCheckLevel, String uploadTimeStart, String uploadTimeEnd,
                    Integer resStatus, Integer shareLevel, String resName, Integer tbcTagStatus, Integer kpTagStatus,
                    String resTypeL2, String checkTimeStart, String checkTimeEnd, String shareTimeStart,
                    String shareTimeEnd) {
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        UserInfoVo userInfoVo =getUserInfoVo();
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("orgName", orgName);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("uploadTimeEnd", uploadTimeEnd);
        sqlParam.put("uploadTimeStart", uploadTimeStart);
        sqlParam.put("checkTimeStart", checkTimeStart);
        sqlParam.put("checkTimeEnd", checkTimeEnd);
        sqlParam.put("shareTimeStart", shareTimeStart);
        sqlParam.put("shareTimeEnd", shareTimeEnd);
        sqlParam.put("resName", resName);
        sqlParam.put("resStatus", resStatus);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("tbcTagStatus", tbcTagStatus);
        sqlParam.put("kpTagStatus", kpTagStatus);
        sqlParam.put("orderType", orderType);
        sqlParam.put("userName", userName);
        sqlParam.put("resTypeL2", resTypeL2);
        sqlParam.put("shareCheckLevel", shareCheckLevel);
        if (BeanHelper.isSchoolAdmin(request)) {
            sqlParam.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = resService.getMediaPageInfo(sqlParam, Integer.MAX_VALUE,1 );
        } catch (Exception e2) {
            log.error("下载失败", e2);
            e2.printStackTrace();
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
        map.put("视频分类", "resTypeL2Name");
        map.put("机构名称", "orgName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
        map.put("转码状态", "resStatusStr");
        map.put("当前级别", "shareLevelStr");
        map.put("共享审核级别", "shareCheckLevelStr");
        map.put("共享审核状态", "shareCheckStatusStr");
        map.put("知识点", "kpCountStr");
        map.put("教材章节", "tbcCountStr");
        map.put("可下载", "allowDownload");
        // map.put("可浏览","allowBrowser");
        map.put("作者", "makerName");
        map.put("上传时间", "makeTime");
        map.put("资源描述", "resDesc");
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        response.setContentType("application/x-msdownload ");
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String("视频信息.xls".getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        Map<String, Integer> fontSizeMap = new HashMap<String, Integer>();
        fontSizeMap.put("resName", 50);
        fontSizeMap.put("orgName", 50);
        fontSizeMap.put("resDesc", 50);
        fontSizeMap.put("makeTime", 15);
        DataFormatter.format(list);
        HSSFWorkbook excel = ExcelUtils.getWb(list, map, fontSizeMap);
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    /**
     * 
     * 导出审核视频 ()<br>
     * 
     * @return
     */
    @RequestMapping("/exportMediaCheck.html")
    @ResponseBody
    public ResultCodeVo exportReport(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String orgName, String userName, String sectionCode, String gradeCode, String subjectCode,
                    Integer shareCheckStatus, Integer shareCheckLevel, String uploadTimeStart, String uploadTimeEnd,
                    Integer resStatus, Integer shareLevel, String resName, Integer tbcTagStatus, Integer kpTagStatus,
                    String resTypeL2, String checkTimeStart, String checkTimeEnd, String shareTimeStart,
                    String shareTimeEnd) {
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        UserInfoVo userInfoVo =getUserInfoVo();
        sqlParam.put("gradeCode", gradeCode);
        sqlParam.put("orgName", orgName);
        sqlParam.put("subjectCode", subjectCode);
        sqlParam.put("sectionCode", sectionCode);
        sqlParam.put("shareCheckStatus", shareCheckStatus);
        sqlParam.put("uploadTimeEnd", uploadTimeEnd);
        sqlParam.put("uploadTimeStart", uploadTimeStart);
        sqlParam.put("checkTimeStart", checkTimeStart);
        sqlParam.put("checkTimeEnd", checkTimeEnd);
        sqlParam.put("shareTimeStart", shareTimeStart);
        sqlParam.put("shareTimeEnd", shareTimeEnd);
        sqlParam.put("resName", resName);
        sqlParam.put("resStatus", resStatus);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("tbcTagStatus", tbcTagStatus);
        sqlParam.put("kpTagStatus", kpTagStatus);
        sqlParam.put("orderType", orderType);
        sqlParam.put("userName", userName);
        sqlParam.put("resTypeL2", resTypeL2);
        sqlParam.put("shareCheckLevel", shareCheckLevel);
        if (BeanHelper.isSchoolAdmin(request)) {
            sqlParam.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = resService.getMediaPageInfo(sqlParam, Integer.MAX_VALUE,1 );
        } catch (Exception e2) {
            log.error("下载失败", e2);
            e2.printStackTrace();
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
        map.put("视频分类", "resTypeL2Name");
        map.put("机构名称", "orgName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
        map.put("转码状态", "resStatusStr");
        map.put("当前级别", "shareLevelStr");
        map.put("共享审核状态", "shareCheckStatusStr");
        map.put("可下载", "allowDownload");
        // map.put("可浏览","allowBrowser");
        map.put("作者", "makerName");
        if (10 == shareCheckStatus) {
            map.put("申请时间", "shareTime");
        } else {
            map.put("申请时间", "shareTime");
            map.put("审核时间", "shareCheckTime");
            map.put("审核人", "checkerName");
            map.put("审核意见", "checkComments");
        }
      /*  map.put("资源描述", "resDesc");*/
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        response.setContentType("application/x-msdownload ");
        String fileName = "";
        if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
            fileName = "视频审核(待审核).xls";
        } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
            fileName = "视频审核(已通过).xls";
        } else {
            fileName = "视频审核(已退回).xls";
        }
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        DataFormatter.format(list);
        Map<String, Integer> fontSizeMap = new HashMap<String, Integer>();
        fontSizeMap.put("resName", 50);
        fontSizeMap.put("orgName", 50);
        fontSizeMap.put("resDesc", 50);
        fontSizeMap.put("makeTime", 15);
        fontSizeMap.put("shareTime", 15);
        fontSizeMap.put("shareCheckTime", 15);
        fontSizeMap.put("checkComments", 80);
        HSSFWorkbook excel = ExcelUtils.getWb(list, map, fontSizeMap);
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    /**
     * 
     * 查询视频非正常资源
     * 
     * @param rows
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param orgName 机构名称
     * @param page
     * @return
     */
    @RequestMapping(value = "/unUsualMediaList.html")
    public @ResponseBody Page<ResVo> getMeidaPage(Integer rows, String sectionCode, String gradeCode,
                    String subjectCode, String orgName, Integer page) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        try {
            return resService.getUnUsualMediaPageInfo(param, rows, page);
        } catch (Exception e) {
            log.error("不正常视频资源信息查询失败", e);
            return null;
        }
    }

    /**
     * 查询不正常文档信息 ()<br>
     * 
     * @param rows
     * @param page
     * @param sectionCode 学段编码
     * @param gradeCode 年级编码
     * @param subjectCode 学科编码
     * @param orgName 机构名称
     * @return
     */
    @RequestMapping(value = "/unUsualDocList.html")
    public @ResponseBody Page<ResVo> getDocPage(Integer rows, String sectionCode, String gradeCode, String subjectCode,
                    String orgName, Integer page) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        // 查询条件
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        try {
            return resService.getUnUsualDocPageInfo(param, rows, page);
        } catch (Exception e) {
            log.error("不正常文档资源信息查询失败", e);
            return null;
        }
    }

    /**
     * 跳转到文档资源新增页面
     * 
     * @return
     * @author gaow
     * @date:2015年12月17日 上午11:44:35
     */
    @RequestMapping(value = "/resDocAdd.html")
    public ModelAndView toDocResAdd(HttpServletRequest request, ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            map.put("typeList", JSONArray
                            .toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_DOC)));
            /*
             * map.put("gradeList", JSONArray.toJSONString(gradeService.selectGradeList()));
             * map.put("subjectList", JSONArray.toJSONString(subjectService.selectSubjectList()));
             */
        } catch (Exception e) {
            log.error("查询学段信息出错", e);
            return super.jumpToViewWithDomainConfig("/manage/res/resDocAdd.html", request, map);

        }
        return super.jumpToViewWithDomainConfig("/manage/res/resDocAdd.html", request, map);
    }

    /**
     * 跳转到视频资源新增页面
     * 
     * @return
     * @author gaow
     * @date:2015年12月17日 上午11:44:35
     */
    @RequestMapping(value = "/resMediaAdd.html")
    public ModelAndView toMeidaAdd(HttpServletRequest request, ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            map.put("typeList", JSONArray
                            .toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_MEDIA)));
            /*
             * map.put("gradeList", JSONArray.toJSONString(gradeService.selectGradeList()));
             * map.put("subjectList", JSONArray.toJSONString(subjectService.selectSubjectList()));
             */
        } catch (Exception e) {
            log.error("查询学段信息出错", e);
            return super.jumpToViewWithDomainConfig("/manage/res/resMediaAdd.html", request, map);

        }
        return super.jumpToViewWithDomainConfig("/manage/res/resMediaAdd.html", request, map);
    }

    /**
     * 跳转到文档资源修改页面
     * 
     * @param request
     * @param resId 资源id
     * @param check 是否能审核 0否 1是
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月19日 下午2:44:33
     */
    @RequestMapping("/resDocEdit.html")
    public ModelAndView toDocResEdit(HttpServletRequest request, ModelMap map, Integer resId, Integer check) {
        try {
            if (resId == null)
                return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
            ResVo vo = resService.getDocAllInfoById(resId);
            map.put("check", check);
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList())); // 学段列表
            map.put("typeList", JSONArray
                            .toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_DOC)));// 类型列表
            map.put("relateChapterList",
                            JSONArray.toJSONString(chapterService.getChapterZtree(resId, CoreConstants.RES_TYPE_DOC)));
            map.put("relateKnowledgeList", JSONArray
                            .toJSONString(knowledgeService.getKnowledgeZtree(resId, CoreConstants.RES_TYPE_DOC)));
            if (gradeService.getResRelateGrade(resId, CoreConstants.RES_TYPE_DOC) != null) {
                map.put("relateGrade",
                                JSON.toJSONString(gradeService.getResRelateGrade(resId, CoreConstants.RES_TYPE_DOC))); // 资源相关年级列表
            }
            if (subjectService.getResRelateSubject(resId, CoreConstants.RES_TYPE_DOC) != null) {
                map.put("relateSubject", JSON
                                .toJSONString(subjectService.getResRelateSubject(resId, CoreConstants.RES_TYPE_DOC)));// 资源相关学科列表
            }
            if (sectionService.getResRelateSection(resId, CoreConstants.RES_TYPE_DOC) != null) {
                map.put("relateSection", JSON
                                .toJSONString(sectionService.getResRelateSection(resId, CoreConstants.RES_TYPE_DOC)));// 资源相关学段列表
            }
            /*
             * if (versionService.getResRelateVersion(resId) != null) { map.put("relateVersion",
             * JSON.toJSONString(versionService.getResRelateVersion(resId)));// 资源相关版本列表 }
             */
            map.put("currentStatus", vo.getShareCheckStatus());
            map.put("resource", vo);
            map.put("res", JSON.toJSONString(vo));
            return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
        } catch (Exception e) {
            log.error("文档资源修改出错", e);
            return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
        }
    }

    /**
     * 跳转到视频资源修改页面
     * 
     * @param request
     * @param resId 资源id
     * @param check 是否能审核 0否 1是
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月19日 下午2:44:33
     */
    @RequestMapping("/resMediaEdit.html")
    public ModelAndView toMediaResEdit(HttpServletRequest request, ModelMap map, Integer resId, Integer check) {
        try {
            if (resId == null)
                return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
            ResVo vo = resService.getMediaAllInfoById(resId);
            map.put("check", check);
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList())); // 学段列表
            map.put("typeList", JSONArray
                            .toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_MEDIA)));// 类型列表

            map.put("relateChapterList", JSONArray
                            .toJSONString(chapterService.getChapterZtree(resId, CoreConstants.RES_TYPE_MEDIA)));
            map.put("relateKnowledgeList", JSONArray
                            .toJSONString(knowledgeService.getKnowledgeZtree(resId, CoreConstants.RES_TYPE_MEDIA)));
            if (gradeService.getResRelateGrade(resId, CoreConstants.RES_TYPE_MEDIA) != null) {
                map.put("relateGrade",
                                JSON.toJSONString(gradeService.getResRelateGrade(resId, CoreConstants.RES_TYPE_MEDIA))); // 资源相关年级列表
            }
            if (subjectService.getResRelateSubject(resId, CoreConstants.RES_TYPE_MEDIA) != null) {
                map.put("relateSubject", JSON
                                .toJSONString(subjectService.getResRelateSubject(resId, CoreConstants.RES_TYPE_MEDIA)));// 资源相关学科列表
            }
            if (sectionService.getResRelateSection(resId, CoreConstants.RES_TYPE_MEDIA) != null) {
                map.put("relateSection", JSON
                                .toJSONString(sectionService.getResRelateSection(resId, CoreConstants.RES_TYPE_MEDIA)));// 资源相关学段列表
            }
            /*
             * if (versionService.getResRelateVersion(resId) != null) { map.put("relateVersion",
             * JSON.toJSONString(versionService.getResRelateVersion(resId)));// 资源相关版本列表 }
             */
            map.put("thumbnailList", JSONArray.toJSONString(resThumbnailDao.getThumbnailList(vo.getResCode())));
            map.put("resource", vo);
            map.put("currentStatus", vo.getShareCheckStatus());
            map.put("res", JSON.toJSONString(vo));
            return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
        } catch (Exception e) {
            log.error("资视频源修改出错", e);
            return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
        }
    }

    /**
     * 跳转到文档资源预览页面
     * 
     * @param request
     * @param resId 资源id
     * @param isExamine 是否审核 1审核 0预览
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月19日 下午6:48:08
     */
    @RequestMapping("/resDocPreview.html")
    public ModelAndView resDocPreview(HttpServletRequest request, Integer resId, Integer isExamine, ModelMap map) {
        if (resId == null)
            return super.jumpToViewWithDomainConfig("/manage/res/resPreviewOrExamine.html", request, map);
        try {
            map.put("isExamine", isExamine);
            map.put("resource", resService.getDocAllInfoById(resId));
            map.put("res", JSON.toJSONString(resService.getDocAllInfoById(resId)));
            map.put("relateChapterList", chapterService.getResRelateChapterAndParent(resId, "", "", "", "", "",
                            CoreConstants.RES_TYPE_DOC));
            map.put("relateVersion", versionService.getResRelateVersion(resId));// 资源相关版本列表
            map.put("relateKnowledgeList",
                            knowledgeService.getResRelateKnowLedgeParent(resId, "", "", CoreConstants.RES_TYPE_DOC));
            return super.jumpToViewWithDomainConfig("/manage/res/resPreviewOrExamine.html", request, map);
        } catch (Exception e) {
            log.error("资源查询出错", e);
            return super.jumpToViewWithDomainConfig("/manage/res/resPreviewOrExamine.html", request, map);
        }
    }

    /**
     * 跳转到视频资源预览页面
     * 
     * @param request
     * @param resId 资源id
     * @param isExamine 是否审核 1审核 0预览
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月19日 下午6:48:08
     */
    @RequestMapping("/resMediaPreview.html")
    public ModelAndView resPreview(HttpServletRequest request, Integer resId, Integer isExamine, ModelMap map) {
        if (resId == null)
            return super.jumpToViewWithDomainConfig("/manage/res/resPreviewOrExamine.html", request, map);
        try {
            map.put("isExamine", isExamine);
            map.put("resource", resService.getMediaAllInfoById(resId));
            map.put("res", JSON.toJSONString(resService.getMediaAllInfoById(resId)));
            map.put("relateChapterList", chapterService.getResRelateChapterAndParent(resId, "", "", "", "", "",
                            CoreConstants.RES_TYPE_MEDIA));
            map.put("relateVersion", versionService.getResRelateVersion(resId));// 资源相关版本列表
            map.put("relateKnowledgeList",
                            knowledgeService.getResRelateKnowLedgeParent(resId, "", "", CoreConstants.RES_TYPE_MEDIA));
            return super.jumpToViewWithDomainConfig("/manage/res/resPreviewOrExamine.html", request, map);
        } catch (Exception e) {
            log.error("资源查询出错", e);
            return super.jumpToViewWithDomainConfig("/manage/res/resPreviewOrExamine.html", request, map);
        }
    }

    /**
     * 保存文档信息
     * 
     * @param res
     * @return
     * @author gaow
     * @date:2015年12月18日 下午3:58:14
     */
    @RequestMapping(value = "/docAdd.html")
    public @ResponseBody ResultCodeVo addDocRes(HttpServletRequest request, String res) {

        try {
            UserInfoVo user =getUserInfoVo();
            if (user == null)
                return ResultCodeVo.errorCode("未登录");
            String ip = getIp();
            ResDoc doc = JSON.parseObject(res, ResDoc.class);
            doc.setFlagDelete(0);
            doc.setModifier(user.getUserName());
            doc.setModifierIP(ip);
            doc.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            doc.setCreator(user.getUserName());
            doc.setMakerCode(user.getUserCode());
            doc.setCreateTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            doc.setCreatorIP(ip);
            /*
             * doc.setFlagAllowDownload(0);//是否允许下载 doc.setFlagAllowBrowse(0);//是否允许浏览
             */ doc.setMakeTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            doc.setShareLevel(CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE);
            // 分享级别默认私有
            /*
             * doc.setFlagDynamicPPT(0);//是否动态ppt
             */ doc.setShareCheckStatus(CoreConstants.CHECK_STATUS_CHECKING);// 后台新增默认审核通过
            doc.setResTypeL1(CoreConstants.RES_TYPE_DOC);// 资源类型
            doc.setResStatus(CoreConstants.RESOURCE_STATE_CONVERTING);// 资源状态
            doc.setClickCount(0);
            doc.setCommentCount(0);
            doc.setDownloadCount(0);
            doc.setFavoriteCount(0);
            doc.setGoodCount(0);
            doc.setReferCount(0);
            Integer id = resService.addDco(doc);
            resourceService.submitConvertByResId(id, CoreConstants.RES_TYPE_DOC, getHostByRequest(request));
            return ResultCodeVo.rightCode("保存成功", id);
        } catch (Exception e) {
            log.error("保存文档失败", e);
            return ResultCodeVo.errorCode("保存文档失败");
        }
    }

    /**
     * 保存文档信息
     * 
     * @param res
     * @return
     * @author gaow
     * @date:2015年12月18日 下午3:58:14
     */
    @RequestMapping(value = "/mediaAdd.html")
    public @ResponseBody ResultCodeVo addMediaRes(HttpServletRequest request, String res) {

        try {
            UserInfoVo user =getUserInfoVo();
            if (user == null)
                return ResultCodeVo.errorCode("未登录");
            String ip = getIp();
            ResMedia media = JSON.parseObject(res, ResMedia.class);
            media.setFlagDelete(0);
            media.setModifier(user.getUserName());
            media.setModifierIP(ip);
            media.setModifyTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            media.setCreator(user.getUserName());
            media.setCreateTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            media.setCreatorIP(ip);
            media.setMakerCode(user.getUserCode());
            /*
             * media.setFlagAllowDownload(0);//是否允许下载 media.setFlagAllowBrowse(0);//是否允许浏览
             */ media.setMakeTime(DateUtils.getTimestamp(DateUtils.formatDate(new Date())));
            media.setShareLevel(CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE);// 分享级别默认私有
            media.setShareCheckStatus(CoreConstants.CHECK_STATUS_CHECKING);// 后台新增默认审核通过
            media.setResTypeL1(CoreConstants.RES_TYPE_MEDIA);// 资源类型
            media.setResStatus(CoreConstants.RESOURCE_STATE_CONVERTING);// 资源状态
            media.setClickCount(0);
            media.setCommentCount(0);
            media.setDownloadCount(0);
            media.setFavoriteCount(0);
            media.setGoodCount(0);
            media.setReferCount(0);
            Integer id = resService.addMedia(media);
            resourceService.submitConvertByResId(id, CoreConstants.RES_TYPE_MEDIA, getHostByRequest(request));
            return ResultCodeVo.rightCode("保存成功", id);
        } catch (Exception e) {
            log.error("保存文档失败", e);
            return ResultCodeVo.errorCode("保存视频失败");
        }
    }

    /**
     * 更新文档信息
     * 
     * @param res 资源
     * @param kpCodes 知识点编码集合
     * @param tbcCodes 章节编码集合
     * @param tbvCodes 教材版本编码集合
     * @param gradeCodes 年级编码集合
     * @param subejctCodes 学科编码集合
     * @param sectionCodes 学段编码集合
     * @return
     * @author gaow
     * @date:2015年12月18日 下午4:01:47
     */
    @RequestMapping(value = "/docUpdate.html")
    public @ResponseBody ResultCodeVo docInfoUpdate(HttpServletRequest request, String makerOrgCode, Integer resId,
                    String resDesc, String resName, String resTypeL2, String kpCodes, String tbcCodes, String tbvCodes,
                    String gradeCodes, String subejctCodes, String sectionCodes, String userName, String makerOrgName,
                    String makerCode, Integer flagAllowDownload, String tags) {
        try {
            String ip = getIp();
            UserInfoVo userInfoVo =getUserInfoVo();
            if (StringUtils.isNotEmpty(resDesc)) {
                Pattern p = Pattern.compile("\r|\n");
                Matcher m = p.matcher(resDesc);
                resDesc = m.replaceAll("");
            }
            if (userInfoVo == null)
                return ResultCodeVo.errorCode("请登录");
            return resService.docInfoUpdate(resId, resDesc, resName, resTypeL2, userInfoVo.getUserCode(),
                            userInfoVo.getUserName(), ip, kpCodes, tbcCodes, tbvCodes, gradeCodes, subejctCodes,
                            sectionCodes, userName, makerOrgCode, makerOrgName, makerCode, flagAllowDownload, tags);
        } catch (Exception e) {
            log.error("文档更新失败", e);
            return ResultCodeVo.errorCode("文档更新失败");
        }
    }

    /**
     * 更新文档信息
     * 
     * @param res 资源
     * @param kpCodes 知识点编码集合
     * @param tbcCodes 章节编码集合
     * @param tbvCodes 教材版本编码集合
     * @param gradeCodes 年级编码集合
     * @param subejctCodes 学科编码集合
     * @param sectionCodes 学段编码集合
     * @return
     * @author gaow
     * @date:2015年12月18日 下午4:01:47
     */
    @RequestMapping(value = "/mediaUpdate.html")
    public @ResponseBody ResultCodeVo mediaInfoUpdate(HttpServletRequest request, String makerOrgCode, Integer resId,
                    String resDesc, String resName, String resTypeL2, String kpCodes, String tbcCodes, String tbvCodes,
                    String gradeCodes, String subejctCodes, String sectionCodes, String userName, String makerOrgName,
                    String makerCode, Integer flagAllowDownload, String coverPath, String tags) {
        try {
            String ip = getIp();
            UserInfoVo userInfoVo =getUserInfoVo();
            if (userInfoVo == null)
                return ResultCodeVo.errorCode("请登录");
            /*
             * if (StringUtils.isNotEmpty(resDesc)) { Pattern p = Pattern.compile("\r|\n"); Matcher
             * m = p.matcher(resDesc); resDesc = m.replaceAll(""); }
             */
            return resService.mediaInfoUpdate(resId, resDesc, resName, resTypeL2, userInfoVo.getUserCode(),
                            userInfoVo.getUserName(), ip, kpCodes, tbcCodes, tbvCodes, gradeCodes, subejctCodes,
                            sectionCodes, userName, makerOrgCode, makerOrgName, makerCode, flagAllowDownload, coverPath,
                            tags);
        } catch (Exception e) {
            log.error("文档更新失败", e);
            return ResultCodeVo.errorCode("文档更新失败");
        }
    }

    /**
     * 
     * 保存文档章节关系
     * 
     * @param resId 资源主键
     * @param chapterCodes 章节编码
     * @return
     */
    @RequestMapping("/saveDocChapterRelate.html")
    @ResponseBody
    public ResultCodeVo saveDocChpater(Integer resId, String chapterCodes) {
        return resService.saveDocChapterRelated(resId, chapterCodes);
    }

    /**
     * 
     * 保存视频章节关系
     * 
     * @param resId 资源主键
     * @param chapterCodes 章节编码
     * @return
     */
    @RequestMapping("/saveMediaChapterRelate.html")
    @ResponseBody
    public ResultCodeVo saveMediaChpater(Integer resId, String chapterCodes) {
        return resService.saveMediaChapterRelated(resId, chapterCodes);
    }

    /**
     * 
     * 保存文档 知识点关系
     * 
     * @param resId 资源主键
     * @param kpCodes 知识点编码
     * @return
     */
    @RequestMapping("/saveDocKnowledgeRelate.html")
    @ResponseBody
    public ResultCodeVo saveDocKnowledge(Integer resId, String kpCodes) {
        return resService.saveDocKnowledgeRelated(resId, kpCodes);
    }

    /**
     * 
     * 保存视频 知识点关系
     * 
     * @param resId 资源主键
     * @param kpCodes 知识点编码
     * @return
     */
    @RequestMapping("/saveMediaKnowledgeRelate.html")
    @ResponseBody
    public ResultCodeVo saveMediaKnowledge(Integer resId, String kpCodes) {
        return resService.saveMediaKnowledgeRelated(resId, kpCodes);
    }

    /**
     * 删除资源章节
     * 
     * @param resId
     * @param chapterCode
     * @return
     */
    @RequestMapping("/delResChapter.html")
    @ResponseBody
    public ResultCodeVo delResChapterId(Integer resId, String chapterCode) {
        try {
            resService.delResChapter(resId, chapterCode);
            return ResultCodeVo.rightCode("删除成功");
        } catch (Exception e) {
            log.error("删除资源章节", e);
            return ResultCodeVo.errorCode("删除失败");
        }
    }

    /**
     * 删除资源知识点
     * 
     * @param resId
     * @param chapterCode
     * @return
     */
    @RequestMapping("/delResKnowledge.html")
    @ResponseBody
    public ResultCodeVo delResKnowledgeId(Integer resId, String knowledgeCode) {
        try {
            resService.delResKnowledge(resId, knowledgeCode);
            return ResultCodeVo.rightCode("删除成功");
        } catch (Exception e) {
            log.error("删除资源章节", e);
            return ResultCodeVo.errorCode("删除失败");
        }
    }

    /**
     * 视频打标签
     * 
     * @param map
     * @param resId 资源主键
     * @return
     */
    @RequestMapping("/tag.html")
    public String toMediaTag(ModelMap map, Integer resId, String type) {
        try {
            map.put("sectionList", sectionService.selectSectionList());
            map.put("type", type);
            map.put("resId", resId);
            return "/manage/res/tag.html";
        } catch (Exception e) {
            log.error("查询学段出错", e);
            return "/manage/res/tag.html";
        }
    }

    /**
     * 审核
     * 
     * @param resId 资源id
     * @param status 状态
     * @param type 资源类型
     * @return
     * @author gaow
     * @date:2015年12月23日 下午1:49:57
     */
    @RequestMapping(value = "/examine.html")
    public @ResponseBody ResultCodeVo examine(Integer resId, String status, Integer type) {
        try {
            return resService.examine(resId, type, status);
        } catch (Exception e) {
            log.error("资源审核失败", e);
            return ResultCodeVo.errorCode("资源审核失败");
        }
    }

    /**
     * 
     * @param request
     * @param pageNo 页码
     * @param pageSize 记录数
     * @param resId 资源id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getJsonForRes.html")
    public DocumentReaderPlayListVO getJsonForRes(HttpServletRequest request, int pageNo, int pageSize, Integer resId) {
        DocumentReaderPlayListVO vo = new DocumentReaderPlayListVO();
        try {
            if (pageNo < 1)
                pageNo = 1;
            if (pageSize < 1)
                pageSize = 1;

            DocumentReaderPlayListVO res = resService.getPageListJsonForAnalyitcal(pageNo, pageSize, resId);
            return res;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return vo;
    }

    /**
     * 跳转到学校资源页面 ()<br>
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/toSchoolRes.html")
    public String toSchoolRes(HttpServletRequest request, ModelMap map) {
        CompanyInfoVo vo =  getCompanyInfo();
        if (vo == null)
            return "/manage/res/schoolRes.html";
        map.put("sectionList", JSONArray.toJSONString(companyService.getCompanySection(vo.getOrgCode())));
        /*
         * map.put("subjectList",
         * JSONArray.toJSONString(companySubjectService.getCompanySubjectList()) );
         */
        try {
            /*
             * map.put("gradeList", JSONArray.toJSONString(companyService.getCompanyGrade(vo.
             * getOrgCode())));
             */
        } catch (Exception e) {
            log.error("查询机构年级失败", e);
            return "/manage/res/schoolRes.html";
        }
        return "/manage/res/schoolRes.html";
    }

    /**
     * 跳转到学校资源页面 ()<br>
     * 
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/toAreaRes.html")
    public String toAreaRes(HttpServletRequest request, ModelMap map) {
        try {
            map.put("sectionList", sectionService.selectSectionList());
        } catch (Exception e) {
            log.error("查询学段失败", e);
            return "/manage/res/schoolRes.html";
        }
        return "/manage/res/areaRes.html";
    }

    /**
     * 
     * 删除资源
     * 
     * @param ids 资源id
     * @param resType 资源类型
     * @return
     */
    @RequestMapping(value = "/deleteRes.html")
    @ResponseBody
    public ResultCodeVo deleteMediaRes(String ids, Integer resType) {
        return resService.deleteRes(ids, resType);
    }

    /**
     * 
     * 删除视频资源
     * 
     * @param id 视频id
     * @return
     */
    @RequestMapping(value = "/recovery.html")
    @ResponseBody
    public ResultCodeVo recoveryMediaRes(String docIds, String mediaIds) {
        return resService.recoveryRes(docIds, mediaIds);
    }

    /**
     * 
     * 查询视频文档资源
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param shareCheckStatus 分享审核中级别先
     * @param orgName 机构名称
     * @param userName 上传人姓名
     * @param resTypeL2 资源类别
     * @param uploadStartTime 上传时间开始
     * @param uploadEndTime 上传时间结束
     * @param shareCheckLevel 分享审核中状态
     * @param shareCheckLevel 资源名称
     * @param page 页码
     * @param rows 每页记录数
     * @return
     */
    @RequestMapping(value = "/getAllRes.html")
    @ResponseBody
    public Page<List<Map<String, Object>>> getAllRes(String sectionCode, String subjectCode, String gradeCode,
                    Integer shareCheckStatus, String orgName, String userName, Integer resTypeL1,
                    String uploadStartTime, String uploadEndTime, Integer shareCheckLevel, Integer pageNo, Integer rows,
                    String resName) {
        if (pageNo == null)
            pageNo = 1;
        if (rows == null)
            rows = 50;
        return resService.getAllRes(sectionCode, subjectCode, gradeCode, shareCheckStatus, orgName, userName, resTypeL1,
                        uploadStartTime, uploadEndTime, shareCheckLevel, resName, pageNo, rows);
    }

    /**
     * 资源审核页面<br>
     * 
     * @param map
     * @return
     */
    @RequestMapping(value = "/toResShareCheck.html")
    public String toResShareCheck(HttpServletRequest request, ModelMap map) {
        UserInfoVo userInfoVo =getUserInfoVo();
        try {
            // 非区域管理员
            if (userInfoVo != null && BeanHelper.isAreaAdmin(request)) {
                map.put("areaAdmin", 1);
            }
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "/manage/res/resShareCheck.html";
    }

    /**
     * 
     * 资源审核列表
     * 
     * @param resTypeL1 资源一级分类
     * @param shareCheckLevel 分享审核中级别
     * @param resTypeL2 资源二级分类
     * @param resName 资源名称
     * @param shareCheckStatus 资源分享审核级别
     * @param rows 每页记录数
     * @param page 页码
     * @return
     */
    @RequestMapping("/resShareCheckList.html")
    @ResponseBody
    public Page getResShareCheckList(HttpServletRequest request, Integer shareCheckLevel, Integer resTypeL1,
                    String resTypeL2, String resName, Integer shareCheckStatus, Integer rows, Integer page,
                    String sectionCode, String gradeCode, String subjectCode, String userName, String orgName) {
        if (rows == null)
            rows = 50;
        if (page == null)
            page = 1;
        Map<String, Object> param = new HashMap<String, Object>();
        AuthRole role = BeanHelper.getAdminRoleFromSession(request);
        UserInfoVo userInfoVo =getUserInfoVo();
        if (CoreConstants.USER_ROLE_AREA_ADMIN_CODE.equals(role.getCode())) {
            param.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
        } else if (CoreConstants.USER_ROLE_SCHOOL_ADMIN_CODE.equals(role.getCode())) {
            param.put("applierOrgCode", userInfoVo.getOrgCode());
            param.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
        }
        param.put("resTypeL1", resTypeL1);
        param.put("resTypeL2", resTypeL2);
        param.put("resName", resName);
        param.put("userName", userName);
        param.put("orgName", orgName);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("rows", rows);
        param.put("page", page);
        param.put("sectionCode", sectionCode);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        return resService.getResShareCheckList(param);
    }

    /**
     * 
     * 资源共享审核
     * 
     * @param id 资源id
     * @param resCode 资源编码
     * @param resTypeL1 一级分享级别
     * @param status 状态
     * @param shareCheckLevel 分享审核中级别
     * @return
     */
    @RequestMapping("/updateResShareCheck.html")
    @ResponseBody
    public ResultCodeVo updateResShareCheck(String resCode, Integer id, Integer resTypeL1, Integer status,
                    Integer shareCheckLevel, String applierOrgCode, String applierCode, String checkComments) {
        return resService.updateResShareCheck(resCode, id, resTypeL1, status, shareCheckLevel, applierOrgCode,
                        applierCode, checkComments);
    }

    /**
     * 
     * 审核资源
     * 
     * @param resCodes 资源编码集合（可以一个或者多个）
     * @param checkCode 审核状态编码 05退回  20通过 
     * @param comment 审核意见
     * @param resType 资源类型 10视频；11特色资源；12音频资源；15互动资源；20文档； 30测验； 50题目；60课时
     * @return 审核结果
     */
    @RequestMapping(value = "/checkRes.html") 
    @ResponseBody
    public ResultCodeVo checkRes(@RequestParam(value = "resCodes[]") String resCodes[], String checkCode,
                    String comment, String resType) {
        return resService.checkRes(resCodes, checkCode, comment, resType);
    }

}
