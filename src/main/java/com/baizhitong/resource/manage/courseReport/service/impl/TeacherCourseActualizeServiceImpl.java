package com.baizhitong.resource.manage.courseReport.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
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
import com.baizhitong.resource.manage.courseReport.service.TeacherCourseActualizeService;
import com.baizhitong.utils.StringUtils;
import com.baizhitong.utils.TimeUtils;

/**
 * 
 * TeacherCourseActualizeServiceImpl 教师实施情况统计
 * 
 * @author creator Zhangqd 2017年1月10日 下午3:35:54
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class TeacherCourseActualizeServiceImpl implements TeacherCourseActualizeService {

    /** 课程统计数据接口 */
    @Autowired
    private LessonReportDao lessonReportDao;

    /**
     * 
     * (分页查询教师实施情况统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @Override
    public Page queryTeacherCourseActualizeInfo(Map<String, Object> param, Integer page, Integer rows) {
        Page<Map<String, Object>> teacherCourseActualize = lessonReportDao.getTeacherCourseActualize(param, page, rows);
        return teacherCourseActualize;
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
    public HSSFWorkbook getExcel(String startDate, String endDate, String publishStartDate, String publishEndDate,
                    String sectionCode, String subjectCode, String yearTermCode, String teacherName, String orgName)
                                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("publishStartDate", publishStartDate);
        param.put("publishEndDate", publishEndDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("yearTermCode", yearTermCode);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        Page<Map<String, Object>> dataPage = this.queryTeacherCourseActualizeInfo(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("教师课程实施统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "姓名", "学校", "课程数", "翻转课堂模式", "自主创建模式", "班级课程发布数", "班级课程实施数" };
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
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "teacherName"));
                HSSFCell cell = row.createCell((short) 1);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "orgName"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "lessonNum"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "flipLessonNum"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "autonomousLessonNum"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "publishNum"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "finishNum"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 2, 6);
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
        return lessonReportDao.selectTeacherCourseActualizeTotal(param);
    }

    /**
     * 
     * (分页查询教师实施情况统计信息<详情页面>)<br>
     * 
     * @param param 查询参数
     * @param page 当前页
     * @param rows 每页大小
     * @return
     */
    @Override
    public Page queryTeacherCourseActualizeDetailInfo(Map<String, Object> param, Integer page, Integer rows) {
        Page<Map<String, Object>> teacherDetail = lessonReportDao.getSubjectCourseActualizeDetail(param, page, rows);
        return teacherDetail;
    }

    /**
     * 
     * (导出Excel<详情页面>)<br>
     * 
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "deprecation" })
    @Override
    public HSSFWorkbook getExcelDetail(String teacherCode, String orgCode, String startDate, String endDate,
                    String publishStartDate, String publishEndDate, String sectionCode, String subjectCode,
                    String yearTermCode, String teacherName, String orgName) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("teacherCode", teacherCode);
        param.put("orgCode", orgCode);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("publishStartDate", publishStartDate);
        param.put("publishEndDate", publishEndDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("yearTermCode", yearTermCode);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        Page<Map<String, Object>> dataPage = this.queryTeacherCourseActualizeDetailInfo(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("教师课程实施统计详情");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "课程名", "学科", "创建类型", "创建(引用)时间", "发布时间", "发布班级", "是否实施", "课前任务单完成率", "课堂推送次数", "课堂推送完成次数",
                        "互动课堂最高完成率" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10,
                        256 * 10, 256 * 10 };
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
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "subjectName"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "lessonMode"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(TimeUtils.formatDate(MapUtils.getString(map, "createTime"), "yyyy-MM-dd HH:mm"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(TimeUtils.formatDate(MapUtils.getString(map, "publishTime"), "yyyy-MM-dd HH:mm"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "teachingClassName"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "flagFinish"));
                cell = row.createCell((short) 7);
                cell.setCellStyle(style2);
                NumberFormat num = NumberFormat.getPercentInstance();
                num.setMaximumFractionDigits(2);
                num.setMinimumFractionDigits(2);
                String coverage = MapUtils.getString(map, "coverage");
                if (StringUtils.isNotEmpty(coverage)) {
                    cell.setCellValue(num.format(new BigDecimal(coverage)));
                } else {
                    cell.setCellValue("0.00%");
                }
                cell = row.createCell((short) 8);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "pushCount"));
                cell = row.createCell((short) 9);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "pushFinishCount"));
                cell = row.createCell((short) 10);
                cell.setCellStyle(style2);
                String interactionMaxTCR = MapUtils.getString(map, "interactionMaxTCR");
                if (StringUtils.isNotEmpty(interactionMaxTCR)) {
                    cell.setCellValue(num.format(new BigDecimal(interactionMaxTCR)));
                } else {
                    cell.setCellValue("0.00%");
                }
                i++;
            }
        }
        // ExportUtils.generateTotalRow(wb, sheet, 2, 6);
        return wb;
    }
}
