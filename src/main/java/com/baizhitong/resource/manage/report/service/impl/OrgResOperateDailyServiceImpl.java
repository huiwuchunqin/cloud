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
import com.baizhitong.resource.dao.report.OrgResOperateDailyDao;
import com.baizhitong.resource.manage.report.service.OrgResOperateDailyService;

/**
 * 机构每日资源操作统计Service实现
 * 
 * @author creator zhangqiang 2016年7月18日 下午1:53:11
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class OrgResOperateDailyServiceImpl implements OrgResOperateDailyService {

    /** 机构_日次_资源操作统计数据接口 */
    @Autowired
    private OrgResOperateDailyDao orgResOperateDailyDao;

    /**
     * 
     * (分页查询使用情况统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @Override
    public Page queryUsageReportInfo(Map<String, Object> param, Integer page, Integer rows) {
        return orgResOperateDailyDao.getOrgDaily(param, page, rows);
    }

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param sectionCode 学段编码
     * @param orgName 机构名称
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public HSSFWorkbook getExcel(String sectionCode, String orgName, String baseDateStart, String baseDateEnd)
                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", baseDateStart);
        param.put("endDate", baseDateEnd);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        Page<Map<String, Object>> dataPage = this.queryUsageReportInfo(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("使用情况统计-机构整体统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "学校", "学段", "查看数", "下载数", "引用数", "收藏数", "点赞数", "评论数" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10 };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "orgName"));
                HSSFCell cell = row.createCell((short) 1);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "sectionName"));
                cell = row.createCell((short) 2);
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
        return orgResOperateDailyDao.select(param);
    }
}
