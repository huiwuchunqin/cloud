package com.baizhitong.resource.manage.res.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.core.vo.ResultCodeVo;
import com.baizhitong.resource.common.core.vo.UserInfoVo;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.dao.res.ResMediaSpecialDao;
import com.baizhitong.resource.dao.res.ResShareCheckDao;
import com.baizhitong.resource.manage.messager.service.impl.MessageServiceImpl;
import com.baizhitong.resource.manage.res.service.ResMediaSpecialService;
import com.baizhitong.resource.model.res.ResMediaSpecial;
import com.baizhitong.resource.model.res.ResShareCheck;
import com.baizhitong.resource.model.vo.res.ResMediaSpecialVo;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 
 * 资源_特色资源Service实现类
 * 
 * @author creator zhangqiang 2016年8月9日 上午11:25:15
 * @author updater
 *
 * @version 1.0.0
 */
@Service(value = "resMediaSpecialService")
public class ResMediaSpecialServiceImpl extends BaseService implements ResMediaSpecialService {

    /** 特色资源管理数据接口 */
    @Autowired
    private ResMediaSpecialDao resMediaSpecialDao;
    /** 资源共享审核数据接口 */
    @Autowired
    private ResShareCheckDao   resShareCheckDao;
    @Autowired
    private MessageServiceImpl messageService;

    /**
     * 
     * (查询特色资源全部信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> querySpecialAllInfoPage(Map<String, Object> param) throws Exception {
        return resMediaSpecialDao.selectSpecialAllInfoPage(param);
    }

    /**
     * 
     * (删除特色资源)<br>
     * 
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteResMediaSpecial(String id) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        String ipAddress = getIp();
        return resMediaSpecialDao.delete(id, userInfo.getUserName(), ipAddress);
    }

    /**
     * 
     * (查询特色资源待审核信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> querySpecialCheckInfoPage(Map<String, Object> param) throws Exception {
        return resMediaSpecialDao.selectSpecialCheckInfoPage(param);
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
     * @throws Exception
     */
    @Override
    public ResultCodeVo saveExamine(String[] resCode, String[] shareCheckLevel, Integer shareCheckStatus,
                    String checkComments) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        int updateNum1 = 0;
        int updateNum2 = 0;
        ResShareCheck resShareCheck = null;
        for (int i = 0; i < resCode.length; i++) {
            List<ResShareCheck> checkList = resShareCheckDao.getResByCodeAndType(CoreConstants.RES_TYPE_SPECIAL_MEDIA,
                            resCode[i]);
            updateNum1 += resMediaSpecialDao.updateShareCheckStatus(resCode[i], Integer.parseInt(shareCheckLevel[i]),
                            userInfo.getUserName(), ip, shareCheckStatus);
            updateNum2 += resShareCheckDao.update(resCode[i], CoreConstants.RES_TYPE_SPECIAL_MEDIA, shareCheckStatus,
                            userInfo, ip, checkComments);
            // 发送消息
            if (checkList != null && checkList.size() > 0) {
                resShareCheck = checkList.get(0);
            }
            if (resShareCheck == null)
                continue;
            messageService.sendMessage(shareCheckStatus, resCode[i], CoreConstants.RES_TYPE_SPECIAL_MEDIA,
                            Integer.parseInt(shareCheckLevel[i]), userInfo.getUserCode(),
                            resShareCheck.getApplierOrgCode(), resShareCheck.getApplierCode());
        }
        if (updateNum1 > 0 && updateNum2 > 0) {
            return ResultCodeVo.rightCode("操作成功！");
        } else {
            return ResultCodeVo.rightCode("操作失败！");
        }
    }

    /**
     * 
     * (查询特色资源已通过审核信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> querySpecialCheckedInfoPage(Map<String, Object> param) throws Exception {
        Page<Map<String, Object>> mapPage = resMediaSpecialDao.selectSpecialCheckedInfoPage(param);
        List<Map<String, Object>> mapList = mapPage.getRows();
        // 遍历，获取最新申请时间、审核人和审核意见
        for (int i = 0; i < mapList.size(); i++) {
            String resCode = mapList.get(i).get("resCode").toString();
            Integer shareCheckStatus = Integer.parseInt(mapList.get(i).get("shareCheckStatus").toString());
            Map<String, Object> infoMap = resShareCheckDao.getRecentlyInfo(resCode, shareCheckStatus);
            mapList.get(i).put("checkerName", MapUtils.getString(infoMap, "checkerName"));
            mapList.get(i).put("checkComments", MapUtils.getString(infoMap, "checkComments"));
            mapList.get(i).put("applyTime", MapUtils.getString(infoMap, "applyTime"));
        }
        mapPage.setRows(mapList);
        return mapPage;
    }

    /**
     * 
     * (查询特色资源已退回信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> querySpecialBackedInfoPage(Map<String, Object> param) throws Exception {
        Page<Map<String, Object>> mapPage = resMediaSpecialDao.selectSpecialBackedInfoPage(param);
        List<Map<String, Object>> mapList = mapPage.getRows();
        // 遍历，获取最新申请时间、审核人和审核意见
        for (int i = 0; i < mapList.size(); i++) {
            String resCode = mapList.get(i).get("resCode").toString();
            Integer shareCheckStatus = Integer.parseInt(mapList.get(i).get("shareCheckStatus").toString());
            Map<String, Object> infoMap = resShareCheckDao.getRecentlyInfo(resCode, shareCheckStatus);
            mapList.get(i).put("checkerName", MapUtils.getString(infoMap, "checkerName"));
            mapList.get(i).put("checkComments", MapUtils.getString(infoMap, "checkComments"));
            mapList.get(i).put("applyTime", MapUtils.getString(infoMap, "applyTime"));
        }
        mapPage.setRows(mapList);
        return mapPage;
    }

    /**
     * 
     * (特色资源全部数据导出)<br>
     * 
     * @param shareLevel
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param resStatus
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getAllDataExcel(Integer shareLevel, String resSpecialTypeL1, String resSpecialTypeL2,
                    Integer resStatus, String makerOrgName, String startDate, String endDate, String resName,
                    String makerName, Integer shareCheckStatus) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        UserInfoVo userInfo = BeanHelper.getAdminUserFromCookie(getRequest());
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("resName", resName);
        param.put("makerName", makerName);
        param.put("shareCheckStatus", shareCheckStatus);
        param.put("shareLevel", shareLevel);
        param.put("resSpecialTypeL1", resSpecialTypeL1);
        param.put("resSpecialTypeL2", resSpecialTypeL2);
        param.put("resStatus", resStatus);
        param.put("makerOrgName", makerOrgName);
        param.put("pageSize", Integer.MAX_VALUE);
        param.put("pageNumber", 1);
        if (userInfo != null && !BeanHelper.isAreaAdmin(getRequest()) && !BeanHelper.isEduStaff(getRequest())) {
            param.put("makerOrgCode", userInfo.getOrgCode());
        }
        Page<Map<String, Object>> dataPage = this.querySpecialAllInfoPage(param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("特色资源(全部)");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "资源名称", "共享审核状态", "共享审核级别", "当前级别", "类别", "项目", "作者", "机构名称", "上传时间", "转码状态" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 300 * 10, 256 * 10, 256 * 10, 256 * 10, 500 * 10, 256 * 10,
                        256 * 10 };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "resName"));
                HSSFCell cell = row.createCell((short) 1);
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
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                Integer shareCheckLevelData = MapUtils.getInteger(map, "shareCheckLevel");
                String shareCheckLevelStr = "";
                if (CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE.equals(shareCheckLevelData)) {
                    shareCheckLevelStr = "个人私有";
                } else if (CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY.equals(shareCheckLevelData)) {
                    shareCheckLevelStr = "校内共享";
                } else if (CoreConstants.RESOURCE_SHARE_LEVEL_TOWN.equals(shareCheckLevelData)) {
                    shareCheckLevelStr = "区内共享";
                }
                cell.setCellValue(shareCheckLevelStr);
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                Integer shareLevelData = MapUtils.getInteger(map, "shareLevel");
                String shareLevelStr = "";
                if (CoreConstants.RESOURCE_SHARE_LEVEL_PRIVATE.equals(shareLevelData)) {
                    shareLevelStr = "个人私有";
                } else if (CoreConstants.RESOURCE_SHARE_LEVEL_COMPANY.equals(shareLevelData)) {
                    shareLevelStr = "校内共享";
                } else if (CoreConstants.RESOURCE_SHARE_LEVEL_TOWN.equals(shareLevelData)) {
                    shareLevelStr = "区内共享";
                }
                cell.setCellValue(shareLevelStr);
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "resSpecialTypeL1Name"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "resSpecialTypeL2Name"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "makerName"));
                cell = row.createCell((short) 7);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "makerOrgName"));
                cell = row.createCell((short) 8);
                cell.setCellStyle(style2);
                cell.setCellValue(timeformatter(MapUtils.getString(map, "makeTime")));
                cell = row.createCell((short) 9);
                cell.setCellStyle(style2);
                Integer resStatusData = MapUtils.getInteger(map, "resStatus");
                String resStatusStr = "";
                if (CoreConstants.RESOURCE_STATE_CONVERTING.equals(resStatusData)) {
                    resStatusStr = "转码中";
                } else if (CoreConstants.RESOURCE_STATE_CONVERT_SUCCESS.equals(resStatusData)) {
                    resStatusStr = "转码成功";
                } else {
                    resStatusStr = "转码失败";
                }
                cell.setCellValue(resStatusStr);
                i++;
            }
        }
        return wb;
    }

    /**
     * 
     * (特色资源待审核数据导出)<br>
     * 
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getCheckDataExcel(String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName,
                    String startDate, String endDate, String resName, String makerName) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        UserInfoVo userInfo = BeanHelper.getAdminUserFromCookie(getRequest());
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("resName", resName);
        param.put("makerName", makerName);
        param.put("resSpecialTypeL1", resSpecialTypeL1);
        param.put("resSpecialTypeL2", resSpecialTypeL2);
        param.put("makerOrgName", makerOrgName);
        param.put("pageSize", Integer.MAX_VALUE);
        param.put("pageNumber", 1);
        if (userInfo != null && !BeanHelper.isAreaAdmin(getRequest()) && !BeanHelper.isEduStaff(getRequest())) {
            param.put("makerOrgCode", userInfo.getOrgCode());
        }
        Page<Map<String, Object>> dataPage = this.querySpecialCheckInfoPage(param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("特色资源(待审核)");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "资源名称", "类别", "项目", "机构名称", "当前级别", "共享审核状态", "作者", "申请时间" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 500 * 10, 400 * 10, 400 * 10, 400 * 10, 400 * 10 };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "resName"));
                HSSFCell cell = row.createCell((short) 1);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "resSpecialTypeL1Name"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "resSpecialTypeL2Name"));
                cell = row.createCell((short) 3);
                cell.setCellValue(MapUtils.getString(map, "makerOrgName"));
                cell = row.createCell((short) 4);
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
                cell = row.createCell((short) 5);
                cell.setCellStyle(style);
                Integer shareCheckStatus = MapUtils.getInteger(map, "shareCheckStatus");
                String checkStatusStr = "";
                if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
                    checkStatusStr = "待审核";
                } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
                    checkStatusStr = "已通过";
                } else if (CoreConstants.CHECK_STATUS_UNAPPROVE.equals(shareCheckStatus)) {
                    checkStatusStr = "已退回";
                } else {
                    checkStatusStr = "未提交";
                }
                cell.setCellValue(checkStatusStr);
                cell = row.createCell((short) 6);
                cell.setCellValue(MapUtils.getString(map, "makerName"));
                cell = row.createCell((short) 7);
                cell.setCellStyle(style);
                cell.setCellValue(timeformatter(MapUtils.getString(map, "shareTime")));
                i++;
            }
        }
        return wb;
    }

    /**
     * 
     * (特色资源已通过审核数据导出)<br>
     * 
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getCheckedDataExcel(String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName,
                    String startDate, String endDate, String resName, String makerName, Integer shareCheckLevel)
                                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        UserInfoVo userInfo = BeanHelper.getAdminUserFromCookie(getRequest());
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("resName", resName);
        param.put("makerName", makerName);
        param.put("resSpecialTypeL1", resSpecialTypeL1);
        param.put("resSpecialTypeL2", resSpecialTypeL2);
        param.put("makerOrgName", makerOrgName);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("pageSize", Integer.MAX_VALUE);
        param.put("pageNumber", 1);
        if (userInfo != null && !BeanHelper.isAreaAdmin(getRequest()) && !BeanHelper.isEduStaff(getRequest())) {
            param.put("makerOrgCode", userInfo.getOrgCode());
        }
        Page<Map<String, Object>> dataPage = this.querySpecialCheckedInfoPage(param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("特色资源(已通过审核)");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "资源名称", "当前级别", "共享审核状态", "类别", "项目", "审核时间", "作者", "机构名称" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 500 * 10 };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "resName"));
                HSSFCell cell = row.createCell((short) 1);
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
                cell = row.createCell((short) 2);
                cell.setCellStyle(style);
                Integer shareCheckStatus = MapUtils.getInteger(map, "shareCheckStatus");
                String checkStatusStr = "";
                if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
                    checkStatusStr = "待审核";
                } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
                    checkStatusStr = "已通过";
                } else if (CoreConstants.CHECK_STATUS_UNAPPROVE.equals(shareCheckStatus)) {
                    checkStatusStr = "已退回";
                } else {
                    checkStatusStr = "未提交";
                }
                cell.setCellValue(checkStatusStr);
                cell = row.createCell((short) 3);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "resSpecialTypeL1Name"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "resSpecialTypeL2Name"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style);
                cell.setCellValue(timeformatter(MapUtils.getString(map, "shareCheckTime")));
                cell = row.createCell((short) 6);
                cell.setCellValue(MapUtils.getString(map, "makerName"));
                cell = row.createCell((short) 7);
                cell.setCellValue(MapUtils.getString(map, "makerOrgName"));
                i++;
            }
        }
        return wb;
    }

    /**
     * 
     * (特色资源审核已退回数据导出)<br>
     * 
     * @param resSpecialTypeL1
     * @param resSpecialTypeL2
     * @param makerOrgName
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getBackedDataExcel(String resSpecialTypeL1, String resSpecialTypeL2, String makerOrgName,
                    String startDate, String endDate, String resName, String makerName, Integer shareCheckLevel)
                                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        UserInfoVo userInfo = BeanHelper.getAdminUserFromCookie(getRequest());
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("resName", resName);
        param.put("makerName", makerName);
        param.put("resSpecialTypeL1", resSpecialTypeL1);
        param.put("resSpecialTypeL2", resSpecialTypeL2);
        param.put("makerOrgName", makerOrgName);
        param.put("shareCheckLevel", shareCheckLevel);
        param.put("pageSize", Integer.MAX_VALUE);
        param.put("pageNumber", 1);
        if (userInfo != null && !BeanHelper.isAreaAdmin(getRequest()) && !BeanHelper.isEduStaff(getRequest())) {
            param.put("makerOrgCode", userInfo.getOrgCode());
        }
        Page<Map<String, Object>> dataPage = this.querySpecialBackedInfoPage(param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("特色资源(已退回)");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "资源名称", "当前级别", "共享审核状态", "类别", "项目", "审核时间", "作者", "机构名称" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 300 * 10, 300 * 10, 500 * 10 };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "resName"));
                HSSFCell cell = row.createCell((short) 1);
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
                cell = row.createCell((short) 2);
                cell.setCellStyle(style);
                Integer shareCheckStatus = MapUtils.getInteger(map, "shareCheckStatus");
                String checkStatusStr = "";
                if (CoreConstants.CHECK_STATUS_CHECKING.equals(shareCheckStatus)) {
                    checkStatusStr = "待审核";
                } else if (CoreConstants.CHECK_STATUS_CHECKED.equals(shareCheckStatus)) {
                    checkStatusStr = "已通过";
                } else if (CoreConstants.CHECK_STATUS_UNAPPROVE.equals(shareCheckStatus)) {
                    checkStatusStr = "已退回";
                } else {
                    checkStatusStr = "未提交";
                }
                cell.setCellValue(checkStatusStr);
                cell = row.createCell((short) 3);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "resSpecialTypeL1Name"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style);
                cell.setCellValue(MapUtils.getString(map, "resSpecialTypeL2Name"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style);
                cell.setCellValue(timeformatter(MapUtils.getString(map, "shareCheckTime")));
                cell = row.createCell((short) 6);
                cell.setCellValue(MapUtils.getString(map, "makerName"));
                cell = row.createCell((short) 7);
                cell.setCellValue(MapUtils.getString(map, "makerOrgName"));
                i++;
            }
        }
        return wb;
    }

    /**
     * 
     * (根据主键id获取特色资源)<br>
     * 
     * @param id 主键id
     * @return
     * @throws Exception
     */
    @Override
    public ResMediaSpecialVo querySpecialMediaById(Integer id) throws Exception {
        Map<String, Object> map = resMediaSpecialDao.selectById(id);
        return ResMediaSpecialVo.mapToVo(map);
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
     * (保存或修改特色资源信息)<br>
     * 
     * @param entity 实体
     * @return
     * @throws Exception
     */
    @Override
    public ResultCodeVo saveResMediaSpecial(ResMediaSpecial entity) throws Exception {
       
        UserInfoVo userInfo =getUserInfoVo();
        String ip = getIp();
        Timestamp currentTime = DateUtils.getTimestamp(DateUtils.formatDate(new Date()));
        if (entity.getId() == null) {
            // 新增
        } else {
            // 修改
            entity.setModifier(userInfo.getUserName());
            entity.setModifierIP(ip);
            entity.setModifyTime(currentTime);
        }
        boolean addResult = resMediaSpecialDao.add(entity);
        if (addResult) {
            return ResultCodeVo.rightCode("保存成功！");
        } else {
            return ResultCodeVo.errorCode("保存失败！");
        }
    }

    /**
     * 
     * (查询资源审核情况明细数据)<br>
     * 
     * @param param
     * @return
     * @throws Exception
     */
    @Override
    public Page<Map<String, Object>> queryCheckDetailInfoPage(Map<String, Object> param) throws Exception {
        return resShareCheckDao.selectByResCodeTypeL1(param);
    }

}
