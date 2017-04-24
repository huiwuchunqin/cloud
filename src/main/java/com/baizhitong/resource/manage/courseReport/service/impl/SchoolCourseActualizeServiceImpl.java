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
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.lessonreport.LessonReportDao;
import com.baizhitong.resource.manage.courseReport.service.SchoolCourseActualizeService;

/**
 * 
 * SchoolCourseActualizeServiceImpl 学校实施情况统计
 * 
 * @author creator zhanglzh 2017年1月11日 上午11:17:39
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class SchoolCourseActualizeServiceImpl extends BaseService implements SchoolCourseActualizeService {

    /** 课程统计数据接口 */
    @Autowired
    private LessonReportDao lessonReportDao;

    /**
     * 
     * (分页查询学校实施情况统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @Override
    public Page<Map<String, Object>> querySchoolCourseActualizeInfo(Map<String, Object> param, Integer page,
                    Integer rows) {
        return lessonReportDao.getSchoolCourseActualize(param, page, rows);
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
        return lessonReportDao.selectSchoolCourseActualizeTotal(param);
    }

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getExcel(Integer endDate, String sectionCode, String yearTermCode, String orgName)
                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("yearTermCode", yearTermCode);
        param.put("orgName", orgName);
        Page<Map<String, Object>> dataPage = this.querySchoolCourseActualizeInfo(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("学校课程实施情况统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "学校", "学段", "课程数", "翻转课堂模式", "自主创建模式", "班级课程发布数", "班级课程实施数" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10 };
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
                cell.setCellValue(MapUtils.getInteger(map, "lessonNum"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "flippedClassNum"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "normalClassNum"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "publishedClassNum"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "usedClassNum"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 2, 6);
        return wb;
    }

}
