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
import com.baizhitong.resource.common.utils.UserHelper;
import com.baizhitong.resource.manage.res.service.ExerciseService;
import com.baizhitong.resource.manage.res.service.ResTypeService;
import com.baizhitong.resource.manage.res.service.impl.DataFormatter;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.utils.StringUtils;

@Controller
@RequestMapping("/manage/exercise")
public class ExerciseAction extends BaseAction {

    private @Autowired ExerciseService exerciseService;
    private @Autowired SectionService  sectionService;
    private @Autowired ResTypeService  resTypeService;

    /**
     * 跳转到练习多面板信息页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toExerciseTab.html")
    public String toExerciseTabList() {
        return "/manage/res/exerciseTabList.html";
    }

    /**
     * 跳转到练习审核信息页面 ()<br>
     * 
     * @return
     */
    @RequestMapping("/toExerciseList.html")
    public String toExerciseList(ModelMap map, String checkStatus, HttpServletRequest request) {
        try {
            UserInfoVo userInfoVo =getUserInfoVo();
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
            // 非区域管理员
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
            map.put("resTypeL2List", JSONArray
                            .toJSONString(resTypeService.getResTypeTwoByType1Code(CoreConstants.RES_TYPE_EXERCISE)));
            map.put("uid", UserHelper.encrypt(userInfoVo.getUserCode()));
            if(StringUtils.isNotEmpty(checkStatus)){
                map.put("checkStatus", checkStatus);
                return "/manage/res/exerciseCheckList.html";
            }else{
                return "/manage/res/exerciseList.html";
            }
           
        } catch (Exception e) {
            log.error("查询学段错误", e);
            return "";
        }
    }

    /**
     * 
     * 查询试卷列表
     * 
     * @param uploadTimeStart 上传开始时间
     * @param uploadTimeEnd 上传结束时间
     * @param checkTimeStart 审核开始时间
     * @param checkTimeEnd 审核结束时间
     * @param resTypeL2 试卷分类
     * @param shareLevel 分享级别
     * @param sectionCode 学段编码
     * @param orgName 机构名称
     * @param userName 用户姓名
     * @param gradeCode 年级
     * @param subjectCode 学科
     * @param shareCheckStatus 审核状态
     * @param shareCheckLevel 审核级别
     * @param tbcTagStatus 是否有章节标签
     * @param rows
     * @param page
     * @param request
     * @return
     */
    @RequestMapping("/exerciseList.html")
    @ResponseBody
    public Page getExercisePage(String orderType, String resName, String shareTimeStart, String shareTimeEnd,
                    String uploadTimeStart, String uploadTimeEnd, String checkTimeStart, String checkTimeEnd,
                    String resTypeL2, Integer shareLevel, String sectionCode, String orgName, String userName,
                    String gradeCode, String subjectCode, Integer shareCheckStatus, Integer shareCheckLevel,
                    Integer tbcTagStatus, Integer rows, Integer page, HttpServletRequest request) {
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
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        param.put("userName", userName);
        param.put("gradeCode", gradeCode);
        param.put("orderType", orderType);
        param.put("resName", resName);
        param.put("subjectCode", subjectCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        return exerciseService.getExercisePage(param, page, rows);
    }

    /**
     * 
     * 导出 测试()<br>
     * 
     * @return
     */
    @RequestMapping("/exportExercise.html")
    @ResponseBody
    public ResultCodeVo exportExercise(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String resName, String shareTimeStart, String shareTimeEnd, String uploadTimeStart,
                    String uploadTimeEnd, String checkTimeStart, String checkTimeEnd, String resTypeL2,
                    Integer shareLevel, String sectionCode, String orgName, String userName, String gradeCode,
                    String subjectCode, Integer shareCheckStatus, Integer shareCheckLevel, Integer tbcTagStatus) {
        UserInfoVo userInfoVo =getUserInfoVo();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        param.put("userName", userName);
        param.put("gradeCode", gradeCode);
        param.put("orderType", orderType);
        param.put("resName", resName);
        param.put("subjectCode", subjectCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = exerciseService.getExercisePage(param, 1, Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询文测试失败", e);
            return null;
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "testName");
        map.put("测验分类", "resTypeL2Name");
        map.put("机构名称", "makerOrgName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
        map.put("当前级别", "shareLevelStr");
        map.put("共享审核级别", "shareCheckLevelStr");
        map.put("共享审核状态", "shareCheckStatusStr");
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
                            "attachment;filename=" + new String("测验信息.xls".getBytes(), "ISO-8859-1"));
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
     * 导出 测试审核()<br>
     * 
     * @return
     */
    @RequestMapping("/exportExerciseCheck.html")
    @ResponseBody
    public ResultCodeVo exportExerciseCheck(HttpServletRequest request, HttpServletResponse response, String orderType,
                    String resName, String shareTimeStart, String shareTimeEnd, String uploadTimeStart,
                    String uploadTimeEnd, String checkTimeStart, String checkTimeEnd, String resTypeL2,
                    Integer shareLevel, String sectionCode, String orgName, String userName, String gradeCode,
                    String subjectCode, Integer shareCheckStatus, Integer shareCheckLevel, Integer tbcTagStatus) {
        UserInfoVo userInfoVo =getUserInfoVo();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("uploadTimeStart", uploadTimeStart);
        param.put("uploadTimeEnd", uploadTimeEnd);
        param.put("checkTimeStart", checkTimeStart);
        param.put("checkTimeEnd", checkTimeEnd);
        param.put("shareTimeStart", shareTimeStart);
        param.put("shareTimeEnd", shareTimeEnd);
        param.put("resTypeL2", resTypeL2);
        param.put("shareLevel", shareLevel);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        param.put("userName", userName);
        param.put("gradeCode", gradeCode);
        param.put("orderType", orderType);
        param.put("resName", resName);
        param.put("subjectCode", subjectCode);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("tbcTagStatus", tbcTagStatus);
        if (BeanHelper.isSchoolAdmin(request)) {
            param.put("orgCode", userInfoVo.getOrgCode());
        }
        Page page = null;
        try {
            page = exerciseService.getExercisePage(param, 1, Integer.MAX_VALUE);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询文测试失败", e);
            return null;
        }
        List<Map<String, Object>> list = page.getRows();
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("资源名称", "testName");
        map.put("测验分类", "resTypeL2Name");
        map.put("机构名称", "makerOrgName");
        map.put("学段", "sectionName");
        map.put("年级", "gradeName");
        map.put("学科", "subjectName");
        map.put("当前级别", "shareLevelStr");
        map.put("共享审核状态", "shareCheckStatusStr");
        map.put("作者", "makerName");
        if (10 == shareCheckStatus) {
            map.put("申请时间", "shareTime");
        } else {
            map.put("申请时间", "shareTime");
            map.put("审核人", "checkerName");
            map.put("审核时间", "shareCheckTime");
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
            fileName = "测验审核(待审核).xls";
        } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
            fileName = "测验审核(已通过).xls";
        } else {
            fileName = "测验审核(已退回).xls";
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
        if(10 == shareCheckStatus){
            fontSizeMap.put("checkComments", 50);  
        }
        fontSizeMap.put("shareCheckTime", 15);
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

}
