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

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.share.ShareCodeDifficultyDao;
import com.baizhitong.resource.manage.questionType.service.IQuestionTypeService;
import com.baizhitong.resource.manage.res.service.QuestionService;
import com.baizhitong.resource.manage.res.service.impl.DataFormatter;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.utils.StringUtils;

@Controller
@RequestMapping("/manage/question")
public class QuestionAction extends BaseAction {

    private @Autowired QuestionService          questionService;
    private @Autowired SectionService           sectionService;
    private @Autowired IQuestionTypeService     questionTypeServie;
    private @Autowired ShareCodeDifficultyDao   shareCodeDifficultyDao;

    /**
     * 跳转到题库多面板信息页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toQuestionTab.html")
    public String toQuestionTabList(HttpServletRequest request, ModelMap model) {
        return "/manage/res/questionTabList.html";
    }

    /**
     * 跳转到题库审核信息页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toQuestionList.html")
    public String toQuestionList(ModelMap map, String checkStatus, HttpServletRequest request) {
        try {
            UserInfoVo userInfoVo =getUserInfoVo();
            // 非区域管理员
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            // 教研员
            if (userInfoVo != null && BeanHelper.isEduStaff(request)) {
                map.put("sectionList",
                                JSONArray.toJSONString(sectionService.queryUserSectionList(userInfoVo.getUserCode())));
            }
            // 学校管理员
            if (BeanHelper.isSchoolAdmin(request)) {
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
            }
            // 区域管理员
            if (BeanHelper.isAreaAdmin(request) || BeanHelper.isEduStaff(request)) {
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
                map.put("areaAdmin", 1);
            }
            
            map.put("questionTypeList", JSONArray.toJSONString(questionTypeServie.getQuestionTypeList()));
            map.put("difficultyList", JSONArray.toJSONString(shareCodeDifficultyDao.getDifficultyList()));
            if(StringUtils.isNotEmpty(checkStatus)){
                map.put("checkStatus", checkStatus);
                return "/manage/res/questionCheckList.html";
            }else{
                return "/manage/res/questionList.html"; 
            }

        } catch (Exception e) {
            log.error("查询学段错误", e);
            return "";
        }
    }

    /**
     * 题目修改 ()<br>
     * 
     * @param questionCode
     * @return
     */
    @RequestMapping(value = "/questionEdit.html")
    public String toQuestionEdit(String questionCode) {
        return "/manage/questionEdit.html";

    }

    /**
     * 
     * 查询题库列表
     * 
     * @param uploadTimeStart 上传开始时间
     * @param uploadTimeEnd 上传结束时间
     * @param checkTimeStart 审核开始时间
     * @param checkTimeEnd 审核结束时间
     * @param questionType 题型
     * @param difficultyType 难易度
     * @param shareLevel 分享级别
     * @param sectionCode 学段编码
     * @param orgName 机构名称
     * @param userName 用户姓名
     * @param gradeCode 年级
     * @param subjectCode 学科
     * @param shareCheckStatus 审核状态
     * @param shareCheckLevel 审核级别
     * @param tbcTagStatus 是否有章节标签
     * @param kpTagStatus 是否有知识点标签
     * @param rows
     * @param page
     * @param request
     * @return
     */
    @RequestMapping("/page.html")
    @ResponseBody
    public Page getQuestionPage(String orderType, String resName, String uploadTimeStart, String uploadTimeEnd,
                    String checkTimeStart, String checkTimeEnd, String shareTimeStart, String shareTimeEnd,
                    String questionType, String difficultyType, Integer shareLevel, String sectionCode, String orgName,
                    String userName, String gradeCode, String subjectCode, Integer shareCheckStatus,
                    Integer shareCheckLevel, Integer tbcTagStatus, Integer kpTagStatus, Integer rows, Integer page,
                    HttpServletRequest request) {
        UserInfoVo userInfoVo =getUserInfoVo();
        if (page == null)
            page = 1;
        if (rows == null)
            rows = 50;
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("questionType", questionType);
        param.put("difficultyType", difficultyType);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("orderType", orderType);
        param.put("orgName", orgName);
        param.put("resName", resName);
        param.put("userName", userName);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("kpTagStatus", kpTagStatus);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        return questionService.getQuestionPage(param, page, rows);
    }

    /**
     * 
     * 导出 题库()<br>
     * 
     * @return
     */
    @RequestMapping("/exportQuestion.html")
    @ResponseBody
    public ResultCodeVo exportQuestion(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String resName, String uploadTimeStart, String uploadTimeEnd, String checkTimeStart,
                    String checkTimeEnd, String shareTimeStart, String shareTimeEnd, String questionType,
                    String difficultyType, Integer shareLevel, String sectionCode, String orgName, String userName,
                    String gradeCode, String subjectCode, Integer shareCheckStatus, Integer shareCheckLevel,
                    Integer tbcTagStatus, Integer kpTagStatus) {
        UserInfoVo userInfoVo =getUserInfoVo();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("questionType", questionType);
        param.put("difficultyType", difficultyType);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("orderType", orderType);
        param.put("orgName", orgName);
        param.put("resName", resName);
        param.put("userName", userName);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("kpTagStatus", kpTagStatus);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = questionService.getQuestionPage(param, 1, Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询题库失败", e);
            return null;
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "questionCode");
        map.put("机构名称", "makerOrgName");
        map.put("题型", "questionName");
        map.put("难度", "difficultyName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
        map.put("当前级别", "shareLevelStr");
        map.put("共享审核级别", "shareCheckLevelStr");
        map.put("共享审核状态", "shareCheckStatusStr");
        map.put("作者", "makerName");
        map.put("上传时间", "makeTime");
        if (list == null || list.size() <= 0) {
            return ResultCodeVo.errorCode("没有数据");
        } else if (list.size() > 2000) {
            return ResultCodeVo.errorCode("数据有点多,加点查询条件限制一下吧");
        }
        response.setContentType("application/x-msdownload ");
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String("题库信息.xls".getBytes(), "ISO-8859-1"));
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
     * 导出 题库审核()<br>
     * 
     * @return
     */
    @RequestMapping("/exportQuestionCheck.html")
    @ResponseBody
    public ResultCodeVo exportQuestionCheck(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String resName, String uploadTimeStart, String uploadTimeEnd, String checkTimeStart,
                    String checkTimeEnd, String shareTimeStart, String shareTimeEnd, String questionType,
                    String difficultyType, Integer shareLevel, String sectionCode, String orgName, String userName,
                    String gradeCode, String subjectCode, Integer shareCheckStatus, Integer shareCheckLevel,
                    Integer tbcTagStatus, Integer kpTagStatus) {
        UserInfoVo userInfoVo =getUserInfoVo();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("questionType", questionType);
        param.put("difficultyType", difficultyType);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("orderType", orderType);
        param.put("orgName", orgName);
        param.put("resName", resName);
        param.put("userName", userName);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        param.put("kpTagStatus", kpTagStatus);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = questionService.getQuestionPage(param, 1, Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询题库失败", e);
            return null;
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "questionCode");
        map.put("机构名称", "makerOrgName");
        map.put("题型", "questionName");
        map.put("难度", "difficultyName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
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
            fileName = "题库审核(待审核).xls";
        } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
            fileName = "题库审核(已通过).xls";
        } else {
            fileName = "题库审核(已退回).xls";
        }
        try {
            response.setHeader("Content-Disposition",
                            "attachment;filename=" + new String(fileName.getBytes(), "ISO-8859-1"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            return null;
        }
    }

}
