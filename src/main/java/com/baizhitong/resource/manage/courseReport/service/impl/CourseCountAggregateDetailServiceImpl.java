package com.baizhitong.resource.manage.courseReport.service.impl;

import java.util.Date;
import java.text.SimpleDateFormat;
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
import com.baizhitong.resource.dao.share.ShareTextbookChapterDao;
import com.baizhitong.resource.manage.courseReport.service.CourseCountAggregateDetailService;
import com.baizhitong.resource.model.share.ShareTextbookChapter;
import com.baizhitong.utils.DateUtils;
import com.baizhitong.utils.StringUtils;

/**
 * 数量汇总统计Service实现
 * 
 * @author creator zhangqd 2017年1月03日 上午10:56:11
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class CourseCountAggregateDetailServiceImpl implements CourseCountAggregateDetailService {

    /** 课程统计数据接口 */
    @Autowired
    private LessonReportDao lessonReportDao;
    /** 教材章节dao */
    @Autowired
    ShareTextbookChapterDao shareTextbookChapterDao;

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
    public Page queryCourseCountAggregateDetailReportInfo(Map<String, Object> param, Integer page, Integer rows) {
        if (StringUtils.isNotEmpty(MapUtils.getString(param, "chapterCode"))) {
            param.put("chapterCodes", getChapterCodes(MapUtils.getString(param, "chapterCode")));
        }
        return lessonReportDao.getCourseCountAggregateDetail(param, page, rows);
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
    public HSSFWorkbook getExcel(String baseDateStart, String baseDateEnd, String shareLevel, String lessonMode,
                    String sectionCode, String subjectCode, String gradeCode, String tbcCode, String orgName,
                    String chapterCode, String lessonName) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", baseDateStart);
        param.put("endDate", baseDateEnd);
        param.put("shareLevel", shareLevel);
        param.put("lessonMode", lessonMode);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("gradeCode", gradeCode);
        param.put("tbcCode", tbcCode);
        param.put("orgName", orgName);
        param.put("lessonName", lessonName);
        if (StringUtils.isNotEmpty(MapUtils.getString(param, "chapterCode"))) {
            param.put("chapterCodes", getChapterCodes(MapUtils.getString(param, "chapterCode")));
        } else {
            param.put("chapterCodes", null);
        }
        Page<Map<String, Object>> dataPage = this.queryCourseCountAggregateDetailReportInfo(param, 1,
                        Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("汇总详情统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "课程名称", "共享级别", "创建类型", "学段", "学科", "年级", "创建时间", "作者", "学校", "被引用数" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10,
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
                String createTime = MapUtils.getString(map, "createTime");
                createTime = DateUtils.formatDate(createTime, "yyyy-MM-dd");
                map.put("createTime", createTime);
                row = sheet.createRow(i);
                row.setHeight((short) 400);
                row.createCell((short) 0).setCellValue(MapUtils.getString(map, "lessonName"));
                HSSFCell cell = row.createCell((short) 1);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "shareLevel"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "lessonMode"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "sectionName"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "subjectName"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "gradeName"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "createTime"));
                cell = row.createCell((short) 7);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "teacherName"));
                cell = row.createCell((short) 8);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "orgName"));
                cell = row.createCell((short) 9);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "referCount"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 9, 9);
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
        if (StringUtils.isNotEmpty(MapUtils.getString(param, "chapterCode"))) {
            param.put("chapterCodes", getChapterCodes(MapUtils.getString(param, "chapterCode")));
        }
        return lessonReportDao.selectCourseCountAggregateDetailTotal(param);
    }

    @Override
    public String getChapterCodes(String code) {
        List<ShareTextbookChapter> chapters = shareTextbookChapterDao.getAllChildNode(code);
        String cha = "";
        for (ShareTextbookChapter shareTextbookChapter : chapters) {
            List<ShareTextbookChapter> a = shareTextbookChapterDao.getListInfo(shareTextbookChapter.getCode());
            if (a.size() < 1) {
                cha += shareTextbookChapter.getCode() + ",";
            }
        }
        return cha.substring(0, cha.length() - 1);
    }
}
