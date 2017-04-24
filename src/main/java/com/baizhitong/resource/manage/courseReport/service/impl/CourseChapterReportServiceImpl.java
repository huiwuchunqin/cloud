package com.baizhitong.resource.manage.courseReport.service.impl;

import java.math.BigDecimal;
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
import com.baizhitong.resource.dao.lessonreport.PlatformLessonCoverageTbDao;
import com.baizhitong.resource.manage.courseReport.service.CourseAuthorReportService;
import com.baizhitong.resource.manage.courseReport.service.CourseChapterReportService;

/**
 * 
 * CourseChapterReportServiceImpl 章节体系统计
 * 
 * @author creator Zhangqd 2017年1月5日 上午11:23:12
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class CourseChapterReportServiceImpl implements CourseChapterReportService {

    /** 章节体系统计数据接口 */
    @Autowired
    private PlatformLessonCoverageTbDao platformLessonCoverageTbDao;

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
    public Page queryChapterReportInfo(Map<String, Object> param, Integer page, Integer rows) {
        return platformLessonCoverageTbDao.getPage(param, page, rows);
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
    public HSSFWorkbook getExcel(String sectionCode, String subjectCode, String gradeCode, String tbvCode)
                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("tbvCode", tbvCode);
        Page<Map<String, Object>> dataPage = this.queryChapterReportInfo(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        for (Map<String, Object> info : dataList) {
            BigDecimal lesson = new BigDecimal(info.get("lessonCoverage").toString());
            BigDecimal temp = new BigDecimal(Float.toString(100.00f));
            String lessonCoverage = lesson.multiply(temp).setScale(2, BigDecimal.ROUND_DOWN) + "" + "%";
            info.put("lessonCoverage", lessonCoverage);
        }
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("章节体系统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "学段", "学科", "年级", "教材名称", "教材版本", "章节节点数", "课程数", "课程覆盖率" };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "sectionName"));
                HSSFCell cell = row.createCell((short) 1);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "subjectName"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "gradeName"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "tbName"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "tbvName"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "chapterNum"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "lessonNum"));
                cell = row.createCell((short) 7);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "lessonCoverage"));
                i++;
            }
        }
        // ExportUtils.generateTotalRow(wb, sheet,5, 6);
        return wb;
    }
}
