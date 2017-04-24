package com.baizhitong.resource.manage.res.action;

import java.io.ByteArrayOutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONArray;
import com.baizhitong.common.Page;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.action.BaseAction;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.lesson.LessonHomeSetDao;
import com.baizhitong.resource.manage.res.service.LessonHomeSetService;
import com.baizhitong.resource.manage.res.service.LessonService;
import com.baizhitong.resource.manage.section.service.SectionService;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 课时审核控制器
 * 
 * @author creator ZhangQiang 2016年8月23日 下午2:16:42
 * @author updater
 *
 * @version 1.0.0
 */
@Controller
@RequestMapping(value = "/manage/lesson")
public class LessonAction extends BaseAction {

    /** 课时审核Service */
    @Autowired
    private LessonService        lessonService;
    /** 学段Service */
    @Autowired
    private SectionService       sectionService;
    @Autowired
    private LessonHomeSetService lessonHomeSetService;
    @Autowired
    private LessonHomeSetDao     lessonHomeSetDao;

    /**
     * 
     * (跳转到课时审核管理主页面)<br>
     * 
     * @param request 请求
     * @param map
     * @return
     */
    @RequestMapping(value = "/main.html")
    public String jumpToMainPage(HttpServletRequest request, ModelMap map) {
        return "/manage/res/lesson/tabList.html";
    }

    /**
     * 
     * (分页查询课时审核全部信息)<br> 
     * 
     * @param request
     * @param page
     * @param rows
     * @param startDate
     * @param endDate
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param shareCheckStatus
     * @param lessonName
     * @param teacherName
     * @param orgName
     * @return
     */
    @RequestMapping(value = "/all/search.html")
    @ResponseBody
    public Page<Map<String, Object>> searchLessonAllInfoPage(HttpServletRequest request, Integer page, Integer rows,
                    String startDate, String endDate, String sectionCode, String subjectCode, String gradeCode,
                    Integer shareCheckStatus, String lessonName, String teacherName, String orgName,
                    Integer isSyncCourse,Integer shareLevel) {
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
            if (StringUtils.isNotEmpty(orgName)) {
                param.put("orgName", orgName);
            }
            if (userInfo != null && !BeanHelper.isAreaAdmin(request) && !BeanHelper.isEduStaff(request)) {
                param.put("orgCode", userInfo.getOrgCode());
            }
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("shareCheckStatus", shareCheckStatus);
            param.put("sectionCode", sectionCode);
            param.put("subjectCode", subjectCode);
            param.put("gradeCode", gradeCode);
            param.put("lessonName", lessonName);
            param.put("teacherName", teacherName);
            param.put("isSyncCourse", isSyncCourse);
            param.put("shareLevel", shareLevel);
            pageInfo = lessonService.queryLessonAllInfoPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * (跳转到待审核、已通过、已退回页面)<br>
     * 
     * @param request 请求
     * @param map
     * @param checkStatus 审核状态
     * @return
     */
    @RequestMapping(value = "/toLessonList.html")
    public String jumpToOtherPage(HttpServletRequest request, ModelMap map, Integer checkStatus) {
        UserInfoVo userInfo =getUserInfoVo();
        try {
            if (userInfo != null && BeanHelper.isAreaAdmin(request)) {
                map.put("isAreaAdmin", true);
                map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
            } else if (userInfo != null && BeanHelper.isEduStaff(request)) {
                map.put("isAreaAdmin", true);
                map.put("sectionList",
                                JSONArray.toJSONString(sectionService.queryUserSectionList(userInfo.getUserCode())));
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_TOWN);
            } else {
                map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
                map.put("shareCheckLevel", CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY);
            }
            if(checkStatus!=null){
                map.put("shareCheckStatus", checkStatus);
                return "/manage/res/lesson/check.html";
            }else{
                return "/manage/res/lesson/all.html"; 
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        
    }

    /**
     * 
     * (查询课时审核数据)<br>
     * 
     * @param request
     * @param page
     * @param rows
     * @param startDate
     * @param endDate
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param shareCheckStatus 审核状态
     * @param lessonName
     * @param teacherName
     * @param orgName
     * @param shareCheckLevel
     * @return
     */
    @RequestMapping(value = "/check/search.html")
    @ResponseBody
    public Page<Map<String, Object>> searchLessonCheckInfoPage(HttpServletRequest request, Integer page, Integer rows,
                    String startDate, String endDate, String sectionCode, String subjectCode, String gradeCode,
                    Integer shareCheckStatus, String lessonName, String teacherName, String orgName,
                    Integer shareCheckLevel, Integer isSyncCourse) {
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
            if (StringUtils.isNotEmpty(orgName)) {
                param.put("orgName", orgName);
            }
            if (userInfo != null && !BeanHelper.isAreaAdmin(request) && !BeanHelper.isEduStaff(request)) {
                param.put("orgCode", userInfo.getOrgCode());
            }
            param.put("startDate", startDate);
            param.put("endDate", endDate);
            param.put("shareCheckStatus", shareCheckStatus);
            param.put("sectionCode", sectionCode);
            param.put("subjectCode", subjectCode);
            param.put("gradeCode", gradeCode);
            param.put("lessonName", lessonName);
            param.put("teacherName", teacherName);
            param.put("shareCheckLevel", shareCheckLevel);
            param.put("isSyncCourse", isSyncCourse);
            pageInfo = lessonService.queryLessonCheckInfoPage(param);
            return pageInfo;
        } catch (Exception ex) {
            log.error("未查询到数据", ex);
            return null;
        }
    }

    /**
     * 
     * (课时审核)<br>
     * 
     * @param resCode 资源编码数组
     * @param shareCheckLevel 审核中共享级别数组
     * @param shareCheckStatus 共享审核状态
     * @param checkComments 审核意见
     * @return
     */
    @RequestMapping(value = "/check/saveExamine.html")
    @ResponseBody
    public ResultCodeVo saveExamine(@RequestParam("resCode[]") String[] resCode,
                    @RequestParam("shareCheckLevel[]") String[] shareCheckLevel, Integer shareCheckStatus,
                    String checkComments) {
        try {
            return lessonService.saveExamine(resCode, shareCheckLevel, shareCheckStatus, checkComments);
        } catch (Exception ex) {
            log.error("课时审核操作失败!", ex);
            ex.printStackTrace();
            return ResultCodeVo.errorCode("操作失败！");
        }
    }

    /**
     * 
     * (课时审核，全部页面数据导出)<br>
     * 
     * @param request
     * @param response
     * @param shareCheckStatus
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param startDate
     * @param endDate
     * @param lessonName
     * @param teacherName
     * @param orgName
     * @return
     */
    @SuppressWarnings({"deprecation"})
    @RequestMapping("/all/export.html")
    public ModelAndView exportAllData(HttpServletRequest request, HttpServletResponse response,
                    Integer shareCheckStatus, String sectionCode, String subjectCode, String gradeCode,
                    String startDate, String endDate, String lessonName, String teacherName, String orgName,
                    Integer isSyncCourse) {
        try {
            // 避免中文乱码
            if (StringUtils.isNotEmpty(lessonName)) {
                lessonName = URLDecoder.decode(lessonName);
            }
            if (StringUtils.isNotEmpty(teacherName)) {
                teacherName = URLDecoder.decode(teacherName);
            }
            if (StringUtils.isNotEmpty(orgName)) {
                orgName = URLDecoder.decode(orgName);
            }
            String fileName = "课程信息.xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = lessonService.getAllDataExcel(shareCheckStatus, sectionCode, subjectCode, gradeCode,
                            startDate, endDate, lessonName, teacherName, orgName, isSyncCourse);
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
     * (课时审核：待审核，已通过，已退回数据导出)<br>
     * 
     * @param request 请求
     * @param response 响应
     * @param shareCheckStatus 共享审核状态
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param lessonName 课程名称
     * @param teacherName 教师姓名
     * @param orgName 机构名称
     * @param shareCheckLevel 共享审核级别
     * @return
     */
    @SuppressWarnings({ "deprecation" })
    @RequestMapping("/check/export.html")
    public ModelAndView exportCheckData(HttpServletRequest request, HttpServletResponse response,
                    Integer shareCheckStatus, String sectionCode, String subjectCode, String gradeCode,
                    String startDate, String endDate, String lessonName, String teacherName, String orgName,
                    Integer shareCheckLevel, Integer isSyncCourse) {
        try {
            // 避免中文乱码
            if (StringUtils.isNotEmpty(lessonName)) {
                lessonName = URLDecoder.decode(lessonName);
            }
            if (StringUtils.isNotEmpty(teacherName)) {
                teacherName = URLDecoder.decode(teacherName);
            }
            if (StringUtils.isNotEmpty(orgName)) {
                orgName = URLDecoder.decode(orgName);
            }
            String fileName = "";
            if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
                fileName = "课程审核(待审核).xls";
            } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
                fileName = "课程审核(已通过).xls";
            } else {
                fileName = "课程审核(已退回).xls";
            }
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-disposition",
                            "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            HSSFWorkbook wb = lessonService.getCheckDataExcel(shareCheckStatus, sectionCode, subjectCode, gradeCode,
                            startDate, endDate, lessonName, teacherName, orgName, shareCheckLevel, isSyncCourse);
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
     * (跳转到课程首页设置管理页面)<br>
     * @param model 数据模型
     * @return
     */
    @RequestMapping("/toHomePage.html")
    public String toCourseHomePage(ModelMap model) {
        try {
            model.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return "/manage/res/lesson/lessonHomeSetting.html";
    }
    
    /**
     * 
     * (跳转到课程首页设置页面)<br>
     * @param model 数据模型
     * @return
     */
    @RequestMapping("/toSetting.html")
    public String toSettings(ModelMap map) {
        try {
            map.put("sectionList", JSONArray.toJSONString(sectionService.selectSectionList()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/manage/res/lesson/setLessonHome.html";
    }
    
    /**
     * 
     * (查询首页课程)<br>
     * @param lessonName 课程名称
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     */
    @RequestMapping("/homePage.html")
    @ResponseBody
    public Page<Map<String,Object>> homeSettingPage(String lessonName,String sectionCode,String subjectCode,String gradeCode,Integer page,Integer rows){
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("sectionCode", sectionCode);
        param.put("gradeCode", gradeCode);
        param.put("subjectCode", subjectCode);
        param.put("lessonName", lessonName);
        return lessonHomeSetService.selectPage(param, page, rows);
    }
    
    /**
     * (首页课程使用状态)<br>
     * 
     * @param id
     * @param available
     * @return
     */
    @RequestMapping("/avaliableUpdate.html")
    @ResponseBody
    public ResultCodeVo updateHomeSetting(Integer id, Integer available) {
        return lessonHomeSetService.update(id, available);
    }

    /**
     * 
     * 设置为课程首页
     * @param lessonCode    课程编码
     * @param lessonId      课程id
     * @param orgCode       机构编码
     * @param sectionCode   学段编码
     * @param gradeCode     年级编码
     * @param subjectCode   学科编码
     * @param coverPath     封面地址
     * @param thumbnailPath 缩略图地址
     * @param lessonName    课程名称
     */
    @RequestMapping("/set.html")
    @ResponseBody
    public ResultCodeVo setCourseHome(String lessonName,String lessonCode,Integer lessonId,String orgCode,String sectionCode,String gradeCode,String subjectCode,String coverPath,String thumbnailPath,boolean flagAvailable){
        return lessonHomeSetService.setCourseHome(lessonName,lessonCode, lessonId, orgCode, sectionCode, gradeCode, subjectCode, coverPath, thumbnailPath,flagAvailable);
    }
    
    /**
     * 
     * (删除)<br>
     * @param lessionId
     * @return
     */
    @RequestMapping("/deleteHomeCourse.html")
    @ResponseBody
    public ResultCodeVo delete(Integer lessonId){
        return lessonHomeSetService.delete(lessonId);
    }
    
    /**
     * 
     * (查询首页课程数)<br>
     * @return
     */
    @RequestMapping("/availableCount.html")
    @ResponseBody
    public long getAvailableCount(){
        return lessonHomeSetDao.selectAvailableCount();
    }
    
    /**
     * 
     * (查询可以设置在首页显示的课程信息)<br>
     * @param request 请求
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param gradeCode 年级编码
     * @param shareLevel 共享级别
     * @param orgName 机构名称
     * @param lessonName 课程名称
     * @param teacherName 作者名称
     * @param page 当前页码
     * @param rows 每页大小
     * @return 课程信息列表
     */
    @RequestMapping("/homeSetting/search.html")
    @ResponseBody
    public Page<Map<String, Object>> getLessonHomeSettingPage(HttpServletRequest request, String sectionCode,
                    String subjectCode, String gradeCode, Integer shareLevel, String orgName, String lessonName,
                    String teacherName, Integer page, Integer rows) {
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("shareLevel", shareLevel);
        param.put("orgName", orgName.trim());
        param.put("lessonName", lessonName.trim());
        param.put("teacherName", teacherName.trim());
        return lessonHomeSetService.queryLessonHomeSettingPage(param, page, rows);
    }
    
}
