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
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.lessonreport.LessonReportDao;
import com.baizhitong.resource.manage.courseReport.service.StudentUseService;

/**
 * 
 * StudentUseServiceImpl 学生使用情况统计实现
 * 
 * @author creator zhanglzh 2017年1月3日 下午2:04:20
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class StudentUseServiceImpl extends BaseService implements StudentUseService {
    @Autowired
    private LessonReportDao lessonReportDao;

    @Override
    public Map<String, Object> getStudentLessonUseTotal(Map<String, Object> param) {
        return lessonReportDao.selectStudentUsedTotal(param);
    }

    @Override
    public Page<Map<String, Object>> getStudentLessonUse(Integer page, Integer rows, Map<String, Object> param) {
        return lessonReportDao.selectPageStudentUsed(page, rows, param);
    }

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param userName 学生姓名
     * @param orgName 机构名称
     * @param studyYearTermCode 学年学期编码
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getExcel(String userName, String orgName, String studyYearTermCode) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("studyYearTermCode", studyYearTermCode);
        param.put("userName", userName);
        param.put("orgName", orgName);
        Page<Map<String, Object>> dataPage = this.getStudentLessonUse(1, Integer.MAX_VALUE, param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("学生使用情况统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 创建一个右对齐格式
        String[] name = { "姓名", "学校", "学习时长（小时）", "已完成课程数", "待完成课程数", "课程总数" };
        Integer[] width = { 256 * 10, 500 * 10, 300 * 10, 300 * 10, 300 * 10, 256 * 10 };
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
                cell.setCellValue(MapUtils.getString(map, "orgName"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                Double time = Double.parseDouble(MapUtils.getString(map, "studyTotalTime"));
                BigDecimal b = new BigDecimal(time);
                double studyTotalTime = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();// 四舍五入保留两位小数
                cell.setCellValue(studyTotalTime);
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "completedCoureseNum"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "uncompletedCoureseNum"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "lessonNum"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 3, 5);
        return wb;
    }

}
