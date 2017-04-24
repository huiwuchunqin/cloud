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
import com.baizhitong.resource.manage.courseReport.service.TeacherUseService;

/**
 * 
 * TeacherUseServiceImpl 教师使用情况统计实现
 * 
 * @author creator zhanglzh 2017年1月3日 下午2:07:20
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class TeacherUseServiceImpl extends BaseService implements TeacherUseService {
    @Autowired
    private LessonReportDao lessonReportDao;

    /**
     * 
     * (查询总数)<br>
     * 
     * @param request
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param teacherName 教师姓名
     * @param orgName 学校名称
     * @return
     */
    @Override
    public Map<String, Object> getTeacherLessonUseTotal(Map<String, Object> param) {
        return lessonReportDao.selectTeacherUsedTotal(param);
    }

    /**
     * 
     * (分页获取教师使用统计信息)<br>
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param teacherName 教师姓名
     * @param orgName 学校名称
     * @param page
     * @param rows
     * @return
     */
    @Override
    public Page<Map<String, Object>> getTeacherLessonUse(Integer page, Integer rows, Map<String, Object> param) {
        return lessonReportDao.selectPageTeacherUsed(page, rows, param);
    }

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param teacherName 教师姓名
     * @param orgName 机构名称
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getExcel(String teacherName, String orgName, String startDate, String endDate)
                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("teacherName", teacherName);
        param.put("orgName", orgName);
        Page<Map<String, Object>> dataPage = this.getTeacherLessonUse(1, Integer.MAX_VALUE, param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("教师使用情况统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "姓名", "登录账号", "学校", "引用数" };
        Integer[] width = { 256 * 10, 500 * 10, 256 * 10, 256 * 10 };
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
                cell.setCellValue(MapUtils.getString(map, "loginAccount"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "orgName"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "referedCount"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 3, 3);
        return wb;
    }
}
