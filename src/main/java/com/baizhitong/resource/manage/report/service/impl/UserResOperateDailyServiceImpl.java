package com.baizhitong.resource.manage.report.service.impl;

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
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.report.UserResOperateDailyDao;
import com.baizhitong.resource.manage.report.service.UserResOperateDailyService;

/**
 * 用户日次资源操作统计 UserResOperateDailyServiceImpl
 * 
 * @author creator zhangqiang 2016年7月18日 下午1:54:08
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class UserResOperateDailyServiceImpl implements UserResOperateDailyService {

    /** 用户日次资源操作统计数据接口 */
    @Autowired
    private UserResOperateDailyDao userResOperateDailyDao;

    /**
     * 
     * (分页查询使用情况统计详情数据)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @Override
    public Page queryUsageReportDetailInfo(Map<String, Object> param, Integer page, Integer rows) {
        return userResOperateDailyDao.select(param, page, rows);
    }

    /**
     * 
     * (导出excel数据)<br>
     * 
     * @param sectionCode 学段编码
     * @param orgCode 机构编码
     * @param userRole 用户身份
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public HSSFWorkbook getExcel(String sectionCode, String orgCode, String baseDateStart, String baseDateEnd) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", baseDateStart);
        param.put("endDate", baseDateEnd);
        param.put("sectionCode", sectionCode);
        param.put("orgCode", orgCode);
        Page<Map<String, Object>> dataPage = this.queryUsageReportDetailInfo(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("使用情况统计-机构详细统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "姓名", "学校", "查看数", "下载数", "引用数", "收藏数", "点赞数", "评论数" };
        Integer[] width = { 256 * 10, 500 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10 };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "userName"));
                HSSFCell cell = row.createCell((short) 1);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "orgName"));
                cell = row.createCell((short) 2);
                // cell.setCellStyle(style2);
                // String roleName="";
                // Integer role=MapUtils.getInteger(map, "userRole");
                // if(10==role.intValue()){
                // roleName="教师";
                // }
                // if(20==role.intValue()){
                // roleName="学生";
                // }
                // cell.setCellValue(roleName);
                // cell=row.createCell((short)3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "browseCount"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "downloadCount"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "referCount"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "favoriteCount"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "goodCount"));
                cell = row.createCell((short) 7);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "commentCount"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 2, 7);
        return wb;
    }

    /**
     * 
     * (查询总数)<br>
     * 
     * @param param 查询参数
     * @return
     */
    @Override
    public Map<String, Object> queryTotalInfo(Map<String, Object> param) {
        return userResOperateDailyDao.select(param);
    }
}
