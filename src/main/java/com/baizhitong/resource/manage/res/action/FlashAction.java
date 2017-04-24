package com.baizhitong.resource.manage.res.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.baizhitong.resource.dao.res.ResFlashDao;
import com.baizhitong.resource.dao.res.ResThumbnailDao;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.res.service.FlashService;
import com.baizhitong.resource.manage.res.service.ResTypeService;
import com.baizhitong.resource.manage.res.service.impl.DataFormatter;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.subject.service.SubjectService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointService;
import com.baizhitong.resource.manage.textbook.service.TextbookVersionService;
import com.baizhitong.resource.model.vo.res.ResVo;
import com.baizhitong.utils.StringUtils;

/**
 * flash控制类 FlashAction TODO
 * 
 * @author creator gaow 2016年12月20日 上午10:03:18
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/flash")
public class FlashAction extends BaseAction {
    private @Autowired FlashService                  flashService;
    private @Autowired SectionService                sectionService;
    private @Autowired GradeService                  gradeService;     // 年级接口
    private @Autowired SubjectService                subjectService;   // 学科接口
    private @Autowired TextbookChapterService        chapterService;   // 章节接口
    private @Autowired TextbookKnowledgePointService knowledgeService; // 知识点接口
    private @Autowired ITeacherService               teacherService;   // 老师接口
    private @Autowired ResFlashDao                   resFlashDao;

    /**
     * 
     * 跳转到flash页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toFlashTab")
    public String toFlahTab() {
        return "/manage/res/flashTabList.html";
    }

    /**
     * 跳转到flash查看页面
     * 
     * @param request
     * @param model
     * @param checkStatus 审核状态
     * @return
     */
    @RequestMapping("/toFlashList.html")
    public String toFlash(HttpServletRequest request, ModelMap model, String checkStatus) {
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            UserInfoVo userInfoVo =getUserInfoVo();
            // 非区域管理员
            if (userInfoVo != null && BeanHelper.isEduStaff(request)) {
                model.put("sectionList",
                                JSONArray.toJSONString(sectionService.queryUserSectionList(userInfoVo.getUserCode())));
            }
            // 学校管理员
            if (BeanHelper.isSchoolAdmin(request)) {
                model.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
            }
            // 区域管理员
            if (BeanHelper.isAreaAdmin(request) || BeanHelper.isEduStaff(request)) {
                model.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
                model.put("areaAdmin", 1);
            }
            if (StringUtils.isNotEmpty(checkStatus)) {
                model.put("checkStatus", checkStatus);
                return "/manage/res/flashCheckList.html";
            } else {
                return "/manage/res/flashList.html";
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 
     * 查询flash
     * 
     * @param resTypeL2 资源类型
     * @param shareLevel 分享级别
     * @param shareCheckLevel 分享审核级别
     * @param shareCheckStatus 分享审核状态
     * @param orgName 机构名称
     * @param resName 资源名称
     * @param makerName 作者
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param uploadTimeStart 上传开始时间
     * @param uploadTimeEnd 上传结束时间
     * @param checkTimeStart 审核开始时间
     * @param checkTimeEnd 审核结束时间
     * @param shareTimeStart 分享开始时间
     * @param shareTimeEnd 分享结束时间
     * @param kpTagStatus 知识点状态
     * @param tbcTagStatus 章节状态
     * @param orderType 排序状态
     * @param page 页码
     * @param rows 每页记录数
     * 
     * @return page
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page selectFlashPage(HttpServletRequest request, Integer resTypeL2, Integer shareLevel,
                    Integer shareCheckLevel, Integer shareCheckStatus, String orgName, String resName, String makerName,
                    String sectionCode, String subjectCode, String gradeCode, String uploadTimeStart,
                    String uploadTimeEnd, String checkTimeStart, String checkTimeEnd, String shareTimeStart,
                    String shareTimeEnd, Integer kpTagStatus, Integer tbcTagStatus, String orderType, Integer page,
                    Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("kpTagStatus", kpTagStatus);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("orgName", orgName);
        param.put("resName", resName);
        param.put("makerName", makerName);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("orderType", orderType);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", getOrgCode() );
        }
        return flashService.selectFlash(param, page, rows);
    }

    /**
     * 跳转到编辑页面 ()<br>
     * 
     * @param resCode 资源编码
     */
    @RequestMapping("/toEdit")
    public ModelAndView toEdit(HttpServletRequest request, Integer resId, String resCode, ModelMap model,
                    Integer check) {
        ResVo vo = flashService.getFlash(resCode);
        model.put("res", JSON.toJSONString(vo));
        model.put("resource", vo);
        try {
            model.put("check", check);
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList())); // 学段列表
            model.put("teacherList", JSONArray.toJSONString(teacherService.getTeacherList(vo.getOrgCode())));
            model.put("relateChapterList", JSONArray
                            .toJSONString(chapterService.getChapterZtree(resId, CoreConstants.RES_TYPE_FLASH))); 
            model.put("relateKnowledgeList", JSONArray
                            .toJSONString(knowledgeService.getKnowledgeZtree(resId, CoreConstants.RES_TYPE_FLASH)));
            if (gradeService.getResRelateGrade(resId, CoreConstants.RES_TYPE_FLASH) != null) {
                model.put("relateGrade",
                                JSON.toJSONString(gradeService.getResRelateGrade(resId, CoreConstants.RES_TYPE_FLASH))); // 资源相关年级列表
            }
            if (subjectService.getResRelateSubject(resId, CoreConstants.RES_TYPE_FLASH) != null) {
                model.put("relateSubject", JSON
                                .toJSONString(subjectService.getResRelateSubject(resId, CoreConstants.RES_TYPE_FLASH)));// 资源相关学科列表
            }
            if (sectionService.getResRelateSection(resId, CoreConstants.RES_TYPE_FLASH) != null) {
                model.put("relateSection", JSON
                                .toJSONString(sectionService.getResRelateSection(resId, CoreConstants.RES_TYPE_FLASH)));// 资源相关学段列表
            }
            model.put("currentStatus", vo.getShareCheckStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, model);
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
    @RequestMapping(value = "/flashUpdate.html")
    public @ResponseBody ResultCodeVo FlashInfoUpdate(HttpServletRequest request, String makerOrgCode, Integer resId,
                    String resDesc, String resName, String kpCodes, String tbcCodes, String gradeCodes,
                    String subejctCodes, String sectionCodes, String userName, String makerOrgName, String makerCode,
                    Integer flagAllowDownload, String coverPath, String tags) {
        try {
            String ip = getIp();
            UserInfoVo userInfoVo =getUserInfoVo();
            if (userInfoVo == null)
                return ResultCodeVo.errorCode("请登录");
            return flashService.flashInfoUpdate(resId, resDesc, resName, userInfoVo.getUserCode(),
                            userInfoVo.getUserName(), ip, kpCodes, tbcCodes, gradeCodes, subejctCodes, sectionCodes,
                            userName, makerOrgCode, makerOrgName, makerCode, flagAllowDownload, coverPath, tags);
        } catch (Exception e) {
            log.error("文档更新失败", e);
            return ResultCodeVo.errorCode("文档更新失败");
        }
    }

    /**
     * 
     * 导出 互动资源()<br>
     * 
     * @return
     */
    @RequestMapping("/exportFlash.html")
    @ResponseBody
    public ResultCodeVo exportFlash(HttpServletRequest request, HttpServletResponse response, Integer resTypeL2,
                    Integer shareLevel, Integer shareCheckLevel, Integer shareCheckStatus, String orgName,
                    String resName, String makerName, String sectionCode, String subjectCode, String gradeCode,
                    String uploadTimeStart, String uploadTimeEnd, String checkTimeStart, String checkTimeEnd,
                    String shareTimeStart, String shareTimeEnd, Integer kpTagStatus, Integer tbcTagStatus,
                    String orderType, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        UserInfoVo userInfoVo =getUserInfoVo();
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("kpTagStatus", kpTagStatus);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("orgName", orgName);
        param.put("resName", resName);
        param.put("makerName", makerName);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("orderType", orderType);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", getOrgCode() );
        }
        Page pageInfo = null;
        ;
        try {
            pageInfo = resFlashDao.selectFlash(param, 1, Integer.MAX_VALUE);
        } catch (Exception e2) {
            log.error("下载失败", e2);
            e2.printStackTrace();
        }
        List<Map<String, Object>> list = pageInfo.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
        map.put("机构名称", "orgName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
        map.put("当前级别", "shareLevelStr");
        map.put("共享审核级别", "shareCheckLevelStr");
        map.put("共享审核状态", "shareCheckStatusStr");
        map.put("知识点", "kpCountStr");
        map.put("教材章节", "tbcCountStr");
        map.put("可下载", "allowDownload");
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
                            "attachment;filename=" + new String("互动资源信息.xls".getBytes(), "ISO-8859-1"));
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
    @RequestMapping("/exportFlashCheck.html")
    @ResponseBody
    public ResultCodeVo exportReport(HttpServletRequest request, HttpServletResponse response, Integer resTypeL2,
                    Integer shareLevel, Integer shareCheckLevel, Integer shareCheckStatus, String orgName,
                    String resName, String makerName, String sectionCode, String subjectCode, String gradeCode,
                    String uploadTimeStart, String uploadTimeEnd, String checkTimeStart, String checkTimeEnd,
                    String shareTimeStart, String shareTimeEnd, Integer kpTagStatus, Integer tbcTagStatus,
                    String orderType, Integer page, Integer rows) {
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
        sqlParam.put("makerName", makerName);
        sqlParam.put("shareLevel", shareLevel);
        sqlParam.put("tbcTagStatus", tbcTagStatus);
        sqlParam.put("kpTagStatus", kpTagStatus);
        sqlParam.put("orderType", orderType);
        sqlParam.put("resTypeL2", resTypeL2);
        sqlParam.put("shareCheckLevel", shareCheckLevel);
        if (BeanHelper.isSchoolAdmin(request)) {
            sqlParam.put("orgCode", userInfoVo.getOrgCode());
        }
        Page pageInfo = null;
        try {
            pageInfo = resFlashDao.selectFlash(sqlParam, 1, Integer.MAX_VALUE);
        } catch (Exception e2) {
            log.error("下载失败", e2);
            e2.printStackTrace();
        }
        List<Map<String, Object>> list = pageInfo.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
        map.put("机构名称", "orgName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
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
        map.put("资源描述", "resDesc");
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        response.setContentType("application/x-msdownload ");
        String fileName = "";
        if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
            fileName = "互动资源审核(待审核).xls";
        } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
            fileName = "互动资源审核(已通过).xls";
        } else {
            fileName = "互动资源审核(已退回).xls";
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
        HSSFWorkbook excel = ExcelUtils.getWb(list, map, fontSizeMap);
        try {
            excel.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

}
