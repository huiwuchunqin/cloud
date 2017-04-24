package com.baizhitong.resource.manage.res.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.common.Page;
import com.baizhitong.resource.common.config.SystemConfig;
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.UserHelper;
import com.baizhitong.resource.dao.lesson.LessonDao;
import com.baizhitong.resource.dao.res.ResShareCheckDao;
import com.baizhitong.resource.manage.authority.service.CommonService;
import com.baizhitong.resource.manage.messager.service.impl.MessageServiceImpl;
import com.baizhitong.resource.manage.res.service.LessonService;
import com.baizhitong.resource.model.lesson.Lesson;
import com.baizhitong.resource.model.res.ResShareCheck;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 课时审核Service实现类
 * 
 * @author creator ZhangQiang 2016年8月23日 下午2:20:41
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "lessonService")
public class LessonServiceImpl extends BaseService implements LessonService {

    /** 课时数据接口 */
    @Autowired
    private LessonDao          lessonDao;
    /** 资源共享审核数据接口 */
    @Autowired
    private ResShareCheckDao   resShareCheckDao;
    /** 消息服务 */
    @Autowired
    private MessageServiceImpl messageService;
    /** 资源共享审核数据接口 */
    @Autowired
    private ResShareCheckDao   shareCheckDao;
    /** 权限共通服务 */
    @Autowired
    private CommonService      commonService;

    /**
     * 
     * (分页查询课时审核全部信息)<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryLessonAllInfoPage(Map<String, Object> param) throws Exception {
        Page<Map<String, Object>> mapPage = null;
        UserInfoVo userInfoVo =getUserInfoVo();
        if (isEduStaff()) {
            String sectionCodes = commonService.queryUserAuthSectionCodes(userInfoVo.getUserCode());
            String subjectCodes = commonService.queryUserAuthSubjectCodes(userInfoVo.getUserCode());
            String gradeCodes = commonService.queryUserAuthGradeCodes(userInfoVo.getUserCode());
            mapPage = lessonDao.selectPageAll(param, sectionCodes, subjectCodes, gradeCodes);
        } else {
            mapPage = lessonDao.selectPageAll(param, null, null, null);
        }
        List<Map<String, Object>> mapList = mapPage.getRows();
        String commonUrl = SystemConfig.lessonPreviewUrl;
        Integer isSyncCourse = MapUtils.getInteger(param, "isSyncCourse");
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                String lessonCode = MapUtils.getString(map, "lessonCode");
                List<ResShareCheck> checkLists = shareCheckDao.getRes(lessonCode);
                if (checkLists != null && checkLists.size() > 0) {
                    String accessPath = checkLists.get(0).getResAccessPath();
                    if (StringUtils.isNotEmpty(accessPath) && accessPath.indexOf("http") < 0) {
                        accessPath = SystemConfig.getWebUrl() + accessPath;
                    }
                    if (StringUtils.isNotEmpty(accessPath)) {
                        map.put("resAccessPath", getCourseUrl(accessPath, isSyncCourse));
                    }
                } else {
                    Integer lessonId = MapUtils.getInteger(map, "id");
                    String path = commonUrl.replaceAll("(lessonId[^&]*)", "lessonId=" + lessonId);
                    String uid = UserHelper.encrypt(userInfoVo.getUserCode());
                    path = path + "&uid=" + uid;
                    map.put("resAccessPath", getCourseUrl(path, isSyncCourse));
                }
            }

        }
        mapPage.setRows(mapList);
        return mapPage;
    }

    public String getCourseUrl(String url, Integer isSyncCourse) {
        if (StringUtils.isEmpty(url))
            return "";
        if (isSyncCourse.intValue() == 1) {
            url = url.replace("/course/", "/courseTeam/");
        } else {
            url = url.replace("/courseTeam/", "/course/");
        }
        return url;
    }

    /**
     * 
     * (根据审核状态，分页查询课时审核信息)<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryLessonCheckInfoPage(Map<String, Object> param) throws Exception {
       
        UserInfoVo userInfoVo =getUserInfoVo();
        Page<Map<String, Object>> mapPage = null;
        if (isEduStaff()) {
            String sectionCodes = commonService.queryUserAuthSectionCodes(userInfoVo.getUserCode());
            String subjectCodes = commonService.queryUserAuthSubjectCodes(userInfoVo.getUserCode());
            String gradeCodes = commonService.queryUserAuthGradeCodes(userInfoVo.getUserCode());
            mapPage = lessonDao.selectPageCheck(param, sectionCodes, subjectCodes, gradeCodes);
        } else {
            mapPage = lessonDao.selectPageCheck(param, null, null, null);
        }
        Integer isSyncCourse = MapUtils.getInteger(param, "isSyncCourse");
        List<Map<String, Object>> mapList = mapPage.getRows();
        String commonUrl = SystemConfig.lessonPreviewUrl;
        if (mapList != null && mapList.size() > 0) {
            for (Map<String, Object> map : mapList) {
                String lessonCode = MapUtils.getString(map, "lessonCode");
                List<ResShareCheck> checkLists = shareCheckDao.getRes(lessonCode);
                // 获取最新的申请时间、审核人和审核意见信息
                Integer shareCheckStatus = MapUtils.getInteger(map, "shareCheckStatus");
                Map<String, Object> infoMap = resShareCheckDao.getRecentlyInfo(lessonCode, shareCheckStatus);
                map.put("checkerName", MapUtils.getString(infoMap, "checkerName"));
                map.put("checkComments", MapUtils.getString(infoMap, "checkComments"));
                map.put("applyTime", MapUtils.getString(infoMap, "applyTime"));

                if (checkLists != null && checkLists.size() > 0) {
                    map.put("resAccessPath", getCourseUrl(checkLists.get(0).getResAccessPath(), isSyncCourse));
                } else {
                    Integer lessonId = MapUtils.getInteger(map, "id");
                    String path = commonUrl.replaceAll("(lessonId[^&]*)", "lessonId=" + lessonId);
                    String uid = UserHelper.encrypt(userInfoVo.getUserCode());
                    path = path + "&uid=" + uid;
                    map.put("resAccessPath", getCourseUrl(path, isSyncCourse));
                }
            }
        }
        mapPage.setRows(mapList);
        return mapPage;
    }

    /**
     * 
     * (课时审核)<br>
     * 
     * @param resCode
     * @param shareCheckLevel
     * @param shareCheckStatus
     * @param checkComments
     * @return
     * @throws Exception
     */
    @Override
    public ResultCodeVo saveExamine(String[] resCode, String[] shareCheckLevel, Integer shareCheckStatus,
                    String checkComments) throws Exception {
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        int updateNum1 = 0;
        int updateNum2 = 0;
        for (int i = 0; i < resCode.length; i++) {
            updateNum1 += lessonDao.updateShareCheckStatus(resCode[i], Integer.parseInt(shareCheckLevel[i]),
                            userInfo.getUserName(), ip, shareCheckStatus);
            updateNum2 += resShareCheckDao.update(resCode[i], CoreConstants.RES_TYPE_COURSE, shareCheckStatus, userInfo,
                            ip, checkComments);
            // 审核通过，生成积分消息
            if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
                Integer checkLevel = Integer.parseInt(shareCheckLevel[i]);
                Lesson lesson = lessonDao.selectByLessonCode(resCode[i]);
                if (CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY.equals(checkLevel)) {
                    messageService.sendPointMessage(CoreConstants.ACTION_LESSON_CHECKED, lesson.getOrgCode(),
                                    lesson.getTeacherCode(), 1);
                } else {
                    messageService.sendPointMessage(CoreConstants.ACTION_LESSON_CHECKED, lesson.getOrgCode(),
                                    lesson.getTeacherCode(), 0);
                }
            } else {
                continue;
            }
        }
        if (updateNum1 > 0 && updateNum2 > 0) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.rightCode("操作失败！");
        }
    }

    /**
     * 
     * (课时审核全部数据导出)<br>
     * 
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
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getAllDataExcel(Integer shareCheckStatus, String sectionCode, String subjectCode,
                    String gradeCode, String startDate, String endDate, String lessonName, String teacherName,
                    String orgName, Integer isSyncCourse) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        UserInfoVo userInfo = BeanHelper.getAdminUserFromCookie(getRequest());
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("lessonName", lessonName);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("pageSize", Integer.MAX_VALUE);
        param.put("pageNumber", 1);
        param.put("isSyncCourse", isSyncCourse);
        if (userInfo != null && !BeanHelper.isAreaAdmin(getRequest()) && !BeanHelper.isEduStaff(getRequest())) {
            param.put("orgCode", userInfo.getOrgCode());
        }
        Page<Map<String, Object>> dataPage = this.queryLessonAllInfoPage(param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("课程审核(全部)");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "课程名称", "机构名称", "学段", "学科", "年级", "当前级别", "共享审核级别", "共享审核状态", "作者", "创建时间" };
        Integer[] width = { 500 * 10, 500 * 10, 256 * 10, 256 * 10, 256 * 10, 300 * 10, 350 * 10, 350 * 10, 350 * 10,
                        420 * 10 };
        for (int i = 0; i < name.length; i++) {
            HSSFCell cell = row.createCell((short) i);
            cell.setCellValue(name[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, width[i]);
        }
        if (dataList != null && dataList.size() > 0) {
            int i = 1;
            for (Map<String, Object> map : dataList) {
                row = sheet.createRow(i);
                row.setHeight((short) 400);
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "lessonName"));
                HSSFCell cell = row.createCell((short) 1);
                cell.setCellValue(MapUtils.getString(map, "orgName"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "sectionName"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "subjectName"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "gradeName"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style);
                Integer shareLevel = MapUtils.getInteger(map, "shareLevel");
                String shareLevelStr = "";
                if (CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE.equals(shareLevel)) {
                    shareLevelStr = "个人私有";
                } else if (CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY.equals(shareLevel)) {
                    shareLevelStr = "校内共享";
                } else {
                    shareLevelStr = "区域共享";
                }
                cell.setCellValue(shareLevelStr);
                cell = row.createCell((short) 6);
                cell.setCellStyle(style);
                Integer shareCheckLevel = MapUtils.getInteger(map, "shareCheckLevel");
                String shareCheckLevelStr = "";
                if (CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE.equals(shareCheckLevel)) {
                    shareCheckLevelStr = "个人私有";
                } else if (CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY.equals(shareCheckLevel)) {
                    shareCheckLevelStr = "校内共享";
                } else if (CoreConstants.RESOURCE_SHARE_LEVEL_TOWN.equals(shareCheckLevel)) {
                    shareCheckLevelStr = "区域共享";
                } else {
                    shareCheckLevelStr = "";
                }
                cell.setCellValue(shareCheckLevelStr);
                cell = row.createCell((short) 7);
                cell.setCellStyle(style);
                Integer shareCheckStatusData = MapUtils.getInteger(map, "shareCheckStatus");
                String checkStatusStr = "";
                if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatusData)) {
                    checkStatusStr = "待审核";
                } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatusData)) {
                    checkStatusStr = "已通过";
                } else if (CoreConstants.CHECK_STATUS_UNAPPROVE.equals(shareCheckStatusData)) {
                    checkStatusStr = "已退回";
                } else {
                    checkStatusStr = "未提交";
                }
                cell.setCellValue(checkStatusStr);
                cell = row.createCell((short) 8);
                cell.setCellValue(MapUtils.getString(map, "teacherName"));
                cell = row.createCell((short) 9);
                cell.setCellStyle(style);
                cell.setCellValue(timeformatter(MapUtils.getString(map, "createTime")));
                i++;
            }
        }
        return wb;
    }

    /**
     * 
     * (时间格式化)<br>
     * 
     * @param time 时间
     * @return
     */
    public String timeformatter(String time) {
        if (StringUtils.isEmpty(time)) {
            return "";
        } else {
            return DateUtils.formatDate(time, "yyyy/MM/dd HH:mm");
        }
    }

    /**
     * 
     * (课时审核：待审核，已通过，已退回数据导出)<br>
     * 
     * @param shareCheckStatus
     * @param sectionCode
     * @param subjectCode
     * @param gradeCode
     * @param startDate
     * @param endDate
     * @param lessonName
     * @param teacherName
     * @param orgName
     * @param shareCheckLevel
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getCheckDataExcel(Integer shareCheckStatus, String sectionCode, String subjectCode,
                    String gradeCode, String startDate, String endDate, String lessonName, String teacherName,
                    String orgName, Integer shareCheckLevel, Integer isSyncCourse) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        UserInfoVo userInfo = BeanHelper.getAdminUserFromCookie(getRequest());
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("lessonName", lessonName);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("pageSize", Integer.MAX_VALUE);
        param.put("pageNumber", 1);
        param.put("isSyncCourse", isSyncCourse);
        if (userInfo != null && !BeanHelper.isAreaAdmin(getRequest()) && !BeanHelper.isEduStaff(getRequest())) {
            param.put("orgCode", userInfo.getOrgCode());
        }
        Page<Map<String, Object>> dataPage = this.queryLessonCheckInfoPage(param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = null;
        if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
            sheet = wb.createSheet("课程审核(待审核)");
        } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
            sheet = wb.createSheet("课程审核(已通过)");
        } else {
            sheet = wb.createSheet("课程审核(已退回)");
        }
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = null;
        if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
            name = new String[] { "课程名称","机构名称", "学段", "学科", "年级", "当前级别", "共享审核状态","作者","申请时间" };
        } else {
            name = new String[] { "课程名称","机构名称", "学段", "学科", "年级", "当前级别", "共享审核状态", "作者", "申请时间","审核人","审核时间","审核意见" };
        }
        Integer[] width = { 500 * 10, 500 * 10, 256 * 10, 256 * 10, 300 * 10, 500 * 10, 300 * 10, 300 * 10, 400 * 10,400 * 10,400 * 10 ,400 * 10  };
        for (int i = 0; i < name.length; i++) {
            HSSFCell cell = row.createCell((short) i);
            cell.setCellValue(name[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, width[i]);
        }
        if (dataList != null && dataList.size() > 0) {
            int i = 1;
            for (Map<String, Object> map : dataList) {
                row = sheet.createRow(i);
                row.setHeight((short) 400);
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "lessonName"));
                HSSFCell  cell = row.createCell((short) 1);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "orgName"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "sectionName"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "subjectName"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "gradeName"));

                //当前级别
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                Integer shareLevel = MapUtils.getInteger(map, "shareLevel");
                String shareLevelStr = "";
                if (CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE.equals(shareLevel)) {
                    shareLevelStr = "个人私有";
                } else if (CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY.equals(shareLevel)) {
                    shareLevelStr = "校内共享";
                } else {
                    shareLevelStr = "区域共享";
                }
                cell.setCellValue(shareLevelStr);
                
                //审核状态
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                Integer shareCheckStatusData = MapUtils.getInteger(map, "shareCheckStatus");
                String checkStatusStr = "";
                if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatusData)) {
                    checkStatusStr = "待审核";
                } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatusData)) {
                    checkStatusStr = "已通过";
                } else if (CoreConstants.CHECK_STATUS_UNAPPROVE.equals(shareCheckStatusData)) {
                    checkStatusStr = "已退回";
                } else {
                    checkStatusStr = "未提交";
                }
                cell.setCellValue(checkStatusStr);
                
                //申请人
                cell = row.createCell((short) 7);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "teacherName"));
                //审核时间
                cell = row.createCell((short) 8);
                cell.setCellStyle(style2);
                cell.setCellValue(timeformatter(MapUtils.getString(map, "shareTime")));
                
                if (!CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
                    //审核人
                    cell = row.createCell((short)9);
                    cell.setCellStyle(style2);
                    cell.setCellValue(MapUtils.getString(map, "checkerName"));
                    //审核时间
                    cell = row.createCell((short) 10);
                    cell.setCellStyle(style2);
                    cell.setCellValue(timeformatter(MapUtils.getString(map, "shareCheckTime")));
                     //审核意见
                    cell = row.createCell((short) 11);
                    cell.setCellStyle(style2);
                    cell.setCellValue(MapUtils.getString(map, "checkComments"));
                }
                i++;
            }
        }
        return wb;
    }
}
