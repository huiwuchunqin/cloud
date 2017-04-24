package com.baizhitong.resource.manage.feedback.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.dao.res.ResAudioDao;
import com.baizhitong.resource.dao.res.ResDocDao;
import com.baizhitong.resource.dao.res.ResMediaDao;
import com.baizhitong.resource.dao.res.ResThumbnailDao;
import com.baizhitong.resource.manage.feedback.service.FeedbackService;
import com.baizhitong.resource.manage.grade.service.GradeService;
import com.baizhitong.resource.manage.res.service.AudioService;
import com.baizhitong.resource.manage.res.service.FlashService;
import com.baizhitong.resource.manage.res.service.ResService;
import com.baizhitong.resource.manage.res.service.ResTypeService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.resource.manage.subject.service.SubjectService;
import com.baizhitong.resource.manage.teacher.service.ITeacherService;
import com.baizhitong.resource.manage.textbook.service.TextbookChapterService;
import com.baizhitong.resource.manage.textbook.service.TextbookKnowledgePointService;
import com.baizhitong.resource.model.res.ResAudio;
import com.baizhitong.resource.model.res.ResDoc;
import com.baizhitong.resource.model.res.ResMedia;
import com.baizhitong.resource.model.vo.res.ResVo;
import com.baizhitong.utils.ObjectUtils;

/**
 * 
 * FeedbackAction 平台反馈
 * 
 * @author creator zhanglzh 2016年9月28日 上午9:59:38
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping("/manage/feedback")
public class FeedbackAction extends BaseAction {

    private @Autowired FeedbackService               feedbackService;
    private @Autowired SectionService                sectionService;   // 学段接口
    private @Autowired ResService                    resService;       // 资源接口
    private @Autowired GradeService                  gradeService;     // 年级接口
    private @Autowired SubjectService                subjectService;   // 学科接口
    private @Autowired TextbookChapterService        chapterService;   // 章节接口
    private @Autowired TextbookKnowledgePointService knowledgeService; // 知识点接口
    private @Autowired ResTypeService                resTypeService;   // 资源类型接口
    private @Autowired ITeacherService               teacherService;
    private @Autowired ResDocDao                     resDocDao;        // 文档资源数据接口
    private @Autowired ResMediaDao                   resMediaDao;
    private @Autowired ResThumbnailDao               resThumbnailDao;
    private @Autowired FlashService                  flashService;
    private @Autowired AudioService                  audioService;
    private @Autowired ResAudioDao                   audioDao;

    /**
     * 
     * (跳转到平台反馈页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/userFeedback.html")
    public String toAllOrgResMakeDaily(ModelMap model) {
        return "/manage/feedback/userFeedback.html";
    }

    /**
     * 
     * (获取用户平台反馈列表)<br>
     * 
     * @param startDate开始时间
     * @param endDate
     *        结束时间
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list.html")
    @ResponseBody
    public Page<Map<String, Object>> getUserFeedback(String moduleName, Integer disposeStatus, Integer startDate,
                    Integer endDate, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("moduleName", moduleName);
        param.put("disposeStatus", disposeStatus);
        return feedbackService.getUserFeedbackList(page, rows, param);
    }

    /**
     * 
     * (跳转到平台反馈页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/resourceErrorFixed.html")
    public String getGroupResErrorFeedback(ModelMap model) {
        return "/manage/feedback/resErrorFeedback.html";
    }

    /**
     * 
     * (跳转到用户反馈内容页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/toUserFeedbackDaily.html")
    public String toUserFeedbackDaily(ModelMap model) {
        model.put("frontUrl", SystemConfig.getWebUrl());
        return "/manage/feedback/userFeedbackDaily.html";
    }

    /**
     * 
     * (跳转到资源纠错错误描述)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/toResErrorFeedbackErrorDesc.html")
    public String totoResErrorFeedbackErrorDesc(ModelMap model) {
        return "/manage/feedback/resErrorFeedbackErrorDesc.html";
    }

    @RequestMapping("/search.html")
    @ResponseBody
    public Page getResFeedback(Integer startDate, Integer endDate, String sectionCode, String resName, String resTypeL1,
                    String subjectCode, String gradeCode, String disposeStatus, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("resName", resName);
        param.put("resTypeL1", resTypeL1);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("disposeStatus", disposeStatus);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return feedbackService.getresErrorFeedbackList(page, rows, param);
    }

    /**
     * 
     * (跳转到资源纠错详情页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/toResErrorFeedbackDaily.html")
    public String toResErrorFeedbackDaily(ModelMap model) {
        model.put("frontUrl", SystemConfig.getWebUrl());
        return "/manage/feedback/resErrorFeedbackDaily.html";
    }

    // /**
    // *
    // * (获取资源纠错详情)<br>
    // *
    // * @param startDate
    // * @param endDate
    // * @param page
    // * @param rows
    // * @return
    // */
    // @RequestMapping("/resErrorFeedbackDailylist.html")
    // @ResponseBody
    // public Page getResErrorFeedbackDaily(String resCode, String resName, Integer startDate,
    // Integer endDate,
    // Integer page, Integer rows) {
    // Map<String, Object> param = new HashMap<String, Object>();
    // param.put("resCode", resCode);
    // param.put("resName", resName);
    // param.put("startDate", startDate);
    // param.put("endDate", endDate);
    // return feedbackService.getUserFeedbackDailyList(page, rows, param);
    // }

    /**
     * 
     * (资源纠错，删除或恢复操作)<br>
     * 
     * @param ids
     * @param operateType
     *        操作类型：1，删除；2，恢复；
     * @return
     */
    @RequestMapping("/deleteResErrorFeedback.html")
    @ResponseBody
    public ResultCodeVo deleteResErrorFeedback(String ids, Integer operateType) {
        try {
            return feedbackService.deleteResErrorFeedback(ids, operateType);
        } catch (Exception e) {
            log.error("用户反馈，删除失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (用户反馈，删除或恢复操作)<br>
     * 
     * @param ids
     * @param operateType
     *        操作类型：1，删除；2，恢复；
     * @return
     */
    @RequestMapping("/deleteUserFeedback.html")
    @ResponseBody
    public ResultCodeVo deleteUserFeedback(String ids, Integer operateType) {
        try {
            return feedbackService.deleteUserFeedback(ids, operateType);
        } catch (Exception e) {
            log.error("资源纠错，删除失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (用户反馈，处理状态操作)<br>
     * 
     * @param ids
     * @param operateType
     *        操作类型：1，处理；2无需处理；
     * @return
     */
    @RequestMapping("/updateStatusUserFeedback.html")
    @ResponseBody
    public ResultCodeVo updateStatusUserFeedback(String ids, Integer operateType) {
        try {
            return feedbackService.updateStatusUserFeedback(ids, operateType);
        } catch (Exception e) {
            log.error("用户反馈，处理失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (资源纠错，处理描述操作)<br>
     * 
     * @param ids
     * @param operateType
     *        操作类型：1，处理；2无需处理；
     * @return
     */
    @RequestMapping("/updateDescResErrorFeedback.html")
    @ResponseBody
    public ResultCodeVo updateDescResErrorFeedback(String ids, String disposeDesc) {
        try {
            return feedbackService.updateDescResErrorFeedback(ids, disposeDesc);
        } catch (Exception e) {
            log.error("用户反馈，处理失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (用户反馈，处理描述操作)<br>
     * 
     * @param ids
     * @param operateType
     *        操作类型：1，处理；2无需处理；
     * @return
     */
    @RequestMapping("/updateDescUserFeedback.html")
    @ResponseBody
    public ResultCodeVo updateDescUserFeedback(String ids, String disposeDesc) {
        try {
            return feedbackService.updateDescUserFeedback(ids, disposeDesc);
        } catch (Exception e) {
            log.error("用户反馈，处理失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (资源纠错，处理状态操作)<br>
     * 
     * @param ids
     * @param operateType
     *        操作类型：1，处理；2无需处理；
     * @return
     */
    @RequestMapping("/updateStatusResErrorFeedback.html")
    @ResponseBody
    public ResultCodeVo updateStatusResErrorFeedback(String ids, Integer operateType) {
        try {
            return feedbackService.updateStatusResErrorFeedback(ids, operateType);
        } catch (Exception e) {
            log.error("资源纠错，处理失败！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (跳转到用户反馈处理描述页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/toUserFeedbackDisposeDesc.html")
    public String toUserFeedbackDisposeDesc(ModelMap model) {
        return "/manage/feedback/userFeedbackDisposeDesc.html";
    }

    /**
     * 
     * (跳转到资源纠错处理描述页面)<br>
     * 
     * @param model
     * @return
     */
    @RequestMapping("/toResErrorFeedbackDisposeDesc.html")
    public String toResErrorFeedbackDisposeDesc(ModelMap model) {
        return "/manage/feedback/resErrorFeedbackDisposeDesc.html";
    }

    /**
     * 
     * (获取资源纠错详情)<br>
     * 
     * @param startDate
     * @param endDate
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/resErrorFeedbackDaily.html")
    @ResponseBody
    public Map<String, Object> getResErrorFeedbackDaily(String id, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return feedbackService.getResErrorFeedback(page, rows, param);
    }

    /**
     * 
     * (获取用户反馈详情)<br>
     * 
     * @param startDate
     * @param endDate
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/getUserFeedbackDaily.html")
    @ResponseBody
    public Map<String, Object> getUserFeedbackDaily(String id, Integer page, Integer rows) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("id", id);
        return feedbackService.getUserFeedback(page, rows, param);
    }

    /**
     * 
     * (跳转到视频或文档查看和编辑页面)<br>
     * 
     * @param request
     *        请求
     * @param map
     *        map参数
     * @param resCode
     *        资源编码
     * @param resTypeL1
     *        资源一级分类
     * @param check
     *        审核标记
     * @return 视频或文档查看和编辑页面
     */
    @RequestMapping("/resEdit.html")
    public ModelAndView jumpToResEditPage(HttpServletRequest request, ModelMap map, String resCode, Integer resTypeL1,
                    Integer check) {
        ResVo vo = null;
        Integer resId = 0;// 资源主键id
        try {
            if (StringUtils.isEmpty(resCode)) {
                return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
            }
            if (CoreConstants.RES_TYPE_DOC.equals(resTypeL1)) {
                ResDoc resDoc = resDocDao.selectResDoc(resCode);
                if (ObjectUtils.isNotNull(resDoc)) {
                    resId = resDoc.getId();
                    vo = resService.getDocAllInfoById(resId);
                }
            } else if (CoreConstants.RES_TYPE_MEDIA.equals(resTypeL1)) {
                ResMedia resMedia = resMediaDao.selectResMedia(resCode);
                if (ObjectUtils.isNotNull(resMedia)) {
                    resId = resMedia.getId();
                    vo = resService.getMediaAllInfoById(resId);
                }
                map.put("thumbnailList", JSONArray.toJSONString(resThumbnailDao.getThumbnailList(resCode)));
            } else if (CoreConstants.RES_TYPE_AUDIO.equals(resTypeL1)) {
                ResAudio audio = audioDao.getAudio(resCode);
                resId = audio.getId();
                vo = audioService.getById(audio.getId());
            } else if (CoreConstants.RES_TYPE_FLASH.equals(resTypeL1)) {
                vo = flashService.getFlash(resCode);
                resId = vo.getId();
            }
            map.put("check", check);
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList())); // 学段列表
            map.put("typeList", JSONArray.toJSONString(resTypeService.getResTypeTwoByType1Code(resTypeL1)));// 类型列表
            map.put("teacherList", JSONArray.toJSONString(teacherService.getTeacherList(vo.getOrgCode())));
            map.put("relateChapterList", JSONArray.toJSONString(chapterService.getChapterZtree(resId, resTypeL1)));
            map.put("relateKnowledgeList",
                            JSONArray.toJSONString(knowledgeService.getKnowledgeZtree(resId, resTypeL1)));
            if (gradeService.getResRelateGrade(resId, resTypeL1) != null) {
                map.put("relateGrade", JSON.toJSONString(gradeService.getResRelateGrade(resId, resTypeL1))); // 资源相关年级列表
            }
            if (subjectService.getResRelateSubject(resId, resTypeL1) != null) {
                map.put("relateSubject", JSON.toJSONString(subjectService.getResRelateSubject(resId, resTypeL1)));// 资源相关学科列表
            }
            if (sectionService.getResRelateSection(resId, resTypeL1) != null) {
                map.put("relateSection", JSON.toJSONString(sectionService.getResRelateSection(resId, resTypeL1)));// 资源相关学段列表
            }
            map.put("currentStatus", vo.getShareCheckStatus());
            map.put("resource", vo);
            map.put("res", JSON.toJSONString(vo));
            return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
        } catch (Exception e) {
            log.error("跳转到文档或视频编辑页面出错！", e);
            e.printStackTrace();
            return super.jumpToViewWithDomainConfig("/manage/res/resEdit.html", request, map);
        }
    }

    /**
     * 
     * (校验资源是否已经被删除)<br>
     * @param request 请求
     * @param resCode 资源编码
     * @param resTypeL1 资源一级分类
     * @return 校验结果
     */
    @RequestMapping("/checkResFlagDelete.html")
    @ResponseBody
    public ResultCodeVo checkResFlagDelete(HttpServletRequest request,String resCode,Integer resTypeL1) {
        try {
            return feedbackService.checkResFlagDelete(resCode, resTypeL1);
        } catch (Exception e) {
            log.error("校验资源是否已经被删除异常！", e);
            e.printStackTrace();
            return ResultCodeVo.errorCode("系统异常！");
        }
    }
    
}
