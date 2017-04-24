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
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.res.ResAudioDao;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.res.service.AudioService;
import com.baizhitong.resource.manage.res.service.impl.DataFormatter;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.subject.service.SubjectService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointService;
import com.baizhitong.resource.model.vo.res.ResVo;
import com.baizhitong.utils.StringUtils;

@RequestMapping("/manage/audio")
@Controller
public class AudioAction extends BaseAction {
    private @Autowired SectionService                sectionService;   // 学段接口
    private @Autowired GradeService                  gradeService;     // 年级接口
    private @Autowired SubjectService                subjectService;   // 学科接口
    private @Autowired TextbookChapterService        chapterService;   // 章节接口
    private @Autowired TextbookKnowledgePointService knowledgeService; // 知识点接口

    private @Autowired ITeacherService               teacherService;
    private @Autowired AudioService                  audioService;

    private @Autowired ResAudioDao                   audioDao;

    /**
     * 音频tab页 ()<br>
     * 
     * @return
     */
    @RequestMapping(value = "/audioTabList.html")
    public String toMeidaTabList() {
        return "/manage/res/audioTabList.html";
    }

    /**
     * 跳转到音频审核列表资源
     * 
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月16日 下午3:46:21
     */
    @RequestMapping(value = "/toAudioList.html")
    public String toAudio(ModelMap map, String checkStatus, HttpServletRequest request) {
        try {
            UserInfoVo userInfoVo = getUserInfoVo();
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

            if (StringUtils.isNotEmpty(checkStatus)) {
                map.put("checkStatus", checkStatus);
                return "/manage/res/audioCheckList.html";
            } else {
                return "/manage/res/audioList.html";
            }
        } catch (Exception e) {
            log.error("查询学段错误", e);
            return "";
        }
    }

    /**
     * 
     * 音频列表
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
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping(value = "/audioList.html")
    public @ResponseBody Page<ResVo> getAudioPage(String orderType, String orgName, String userName, String sectionCode,
                    String gradeCode, String subjectCode, Integer shareCheckStatus, Integer shareCheckLevel,
                    String uploadTimeStart, String uploadTimeEnd, Integer resStatus, Integer shareLevel, String resName,
                    Integer tbcTagStatus, Integer kpTagStatus, Integer rows, Integer page, HttpServletRequest request,
                    String checkTimeStart, String checkTimeEnd, String shareTimeStart, String shareTimeEnd,
                    Integer resStatusSuccess) {
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        UserInfoVo userInfoVo = getUserInfoVo();
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
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("resStatusSuccess", resStatusSuccess);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        try {
            return audioService.getPage(param, page, rows);
        } catch (Exception e) {
            log.error("音频资源信息查询失败", e);
            return null;
        }
    }

    /**
     * 
     * 导出 音频()<br>
     * 
     * @return
     */
    @RequestMapping("/exportAudio.html")
    @ResponseBody
    public ResultCodeVo exportAudio(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String orgName, String userName, String sectionCode, String gradeCode, String subjectCode,
                    Integer shareCheckStatus, Integer shareCheckLevel, String uploadTimeStart, String uploadTimeEnd,
                    Integer resStatus, Integer shareLevel, String resName, Integer tbcTagStatus, Integer kpTagStatus,
                    String checkTimeStart, String checkTimeEnd, String shareTimeStart, String shareTimeEnd) {
        // 查询条件
        Map<String, Object> param = new HashMap<String, Object>();
        UserInfoVo userInfoVo = getUserInfoVo();
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
        param.put("shareCheckLevel", shareCheckLevel);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        ;
        try {
            page = audioService.getPage(param, 1, Integer.MAX_VALUE);
        } catch (Exception e2) {
            log.error("下载失败", e2);
            e2.printStackTrace();
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
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
                            "attachment;filename=" + new String("音频信息.xls".getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
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
     * 导出审核音频 ()<br>
     * 
     * @return
     */
    @RequestMapping("/exportAudioCheck.html")
    @ResponseBody
    public ResultCodeVo exportReport(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String orgName, String userName, String sectionCode, String gradeCode, String subjectCode,
                    Integer shareCheckStatus, Integer shareCheckLevel, String uploadTimeStart, String uploadTimeEnd,
                    Integer resStatus, Integer shareLevel, String resName, Integer tbcTagStatus, Integer kpTagStatus,
                    String checkTimeStart, String checkTimeEnd, String shareTimeStart, String shareTimeEnd) {
        // 查询条件
        Map<String, Object> sqlParam = new HashMap<String, Object>();
        UserInfoVo userInfoVo = getUserInfoVo();
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
        sqlParam.put("shareCheckLevel", shareCheckLevel);
        if (BeanHelper.isSchoolAdmin(request)) {
            sqlParam.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = audioService.getPage(sqlParam, 1, Integer.MAX_VALUE);
        } catch (Exception e2) {
            log.error("下载失败", e2);
            e2.printStackTrace();
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "resName");
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
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        response.setContentType("application/x-msdownload ");
        String fileName = "";
        if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
            fileName = "音频审核(待审核).xls";
        } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
            fileName = "音频审核(已通过).xls";
        } else {
            fileName = "音频审核(已退回).xls";
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
     * 跳转到音频资源修改页面
     * 
     * @param request
     * @param resId 资源id
     * @param check 是否能审核 0否 1是
     * @param map
     * @return
     * @author gaow
     * @date:2015年12月19日 下午2:44:33
     */
    @RequestMapping("/resAudioEdit.html")
    public ModelAndView toAudioResEdit(HttpServletRequest request, ModelMap map, Integer resId, Integer check) {
        try {
            if (resId == null)
                return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
            ResVo vo = audioService.getById(resId);
            map.put("check", check);
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList())); // 学段列表
            map.put("teacherList", JSONArray.toJSONString(teacherService.getTeacherList(vo.getOrgCode())));

            map.put("relateChapterList", JSONArray
                            .toJSONString(chapterService.getChapterZtree(resId, CoreConstants.RES_TYPE_AUDIO)));
            map.put("relateKnowledgeList", JSONArray
                            .toJSONString(knowledgeService.getKnowledgeZtree(resId, CoreConstants.RES_TYPE_AUDIO)));
            if (gradeService.getResRelateGrade(resId, CoreConstants.RES_TYPE_AUDIO) != null) {
                map.put("relateGrade",
                                JSON.toJSONString(gradeService.getResRelateGrade(resId, CoreConstants.RES_TYPE_AUDIO))); // 资源相关年级列表
            }
            if (subjectService.getResRelateSubject(resId, CoreConstants.RES_TYPE_AUDIO) != null) {
                map.put("relateSubject", JSON
                                .toJSONString(subjectService.getResRelateSubject(resId, CoreConstants.RES_TYPE_AUDIO)));// 资源相关学科列表
            }
            if (sectionService.getResRelateSection(resId, CoreConstants.RES_TYPE_AUDIO) != null) {
                map.put("relateSection", JSON
                                .toJSONString(sectionService.getResRelateSection(resId, CoreConstants.RES_TYPE_AUDIO)));// 资源相关学段列表
            }
            /*
             * if (versionService.getResRelateVersion(resId) != null) { map.put("relateVersion",
             * JSON.toJSONString(versionService.getResRelateVersion(resId)));// 资源相关版本列表 }
             */
            map.put("resource", vo);
            map.put("currentStatus", vo.getShareCheckStatus());
            map.put("res", JSON.toJSONString(vo));
            return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
        } catch (Exception e) {
            log.error("音频频源修改出错", e);
            return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
        }
    }

    /**
     * 更新音频信息
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
    @RequestMapping(value = "/audioUpdate.html")
    public @ResponseBody ResultCodeVo audioInfoUpdate(HttpServletRequest request, String makerOrgCode, Integer resId,
                    String resDesc, String resName, String resTypeL2, String kpCodes, String tbcCodes, String tbvCodes,
                    String gradeCodes, String subejctCodes, String sectionCodes, String userName, String makerOrgName,
                    String makerCode, Integer flagAllowDownload, String coverPath, String tags) {
        try {
            String ip = getIp();
            UserInfoVo userInfoVo = getUserInfoVo();
            if (userInfoVo == null)
                return ResultCodeVo.errorCode("请登录");
            /*
             * if (StringUtils.isNotEmpty(resDesc)) { Pattern p = Pattern.compile("\r|\n"); Matcher
             * m = p.matcher(resDesc); resDesc = m.replaceAll(""); }
             */
            return audioService.update(resId, resDesc, resName, resTypeL2, userInfoVo.getUserCode(),
                            userInfoVo.getUserName(), ip, kpCodes, tbcCodes, tbvCodes, gradeCodes, subejctCodes,
                            sectionCodes, userName, makerOrgCode, makerOrgName, makerCode, flagAllowDownload, coverPath,
                            tags);
        } catch (Exception e) {
            log.error("音频更新失败", e);
            return ResultCodeVo.errorCode("音频更新失败");
        }
    }

}
