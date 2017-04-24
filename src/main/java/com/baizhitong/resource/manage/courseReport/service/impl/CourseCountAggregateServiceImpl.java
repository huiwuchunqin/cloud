package com.baizhitong.resource.manage.courseReport.service.impl;

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
import com.baizhitong.resource.dao.lessonreport.LessonReportDao;
import com.baizhitong.resource.manage.courseReport.service.CourseCountAggregateService;

/**
 * 数量汇总统计Service实现
 * 
 * @author creator zhangqd 2017年1月03日 上午10:56:11
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class CourseCountAggregateServiceImpl implements CourseCountAggregateService {

    /** 课程统计数据接口 */
    @Autowired
    private LessonReportDao lessonReportDao;

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
    public Page queryCourseCountAggregateReportInfo(Map<String, Object> param, Integer page, Integer rows) {
        return lessonReportDao.getCourseCountAggregate(param, page, rows);
    }

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public HSSFWorkbook getExcel(String baseDateStart, String baseDateEnd) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", baseDateStart);
        param.put("endDate", baseDateEnd);
        Page<Map<String, Object>> dataPage = this.queryCourseCountAggregateReportInfo(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("数量汇总统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "创建类型", "个人私有数", "校内共享数", "区域共享数", "总数" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10 };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "lessonMode"));
                HSSFCell cell = row.createCell((short) 1);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "privateNum"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "orgNum"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "areaNum"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "totalNum"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 1, 4);
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
        return lessonReportDao.selectCourseCountAggregateTotal(param);
    }
}
