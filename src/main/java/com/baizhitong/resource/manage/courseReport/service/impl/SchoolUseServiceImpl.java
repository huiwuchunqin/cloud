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
import com.baizhitong.resource.manage.courseReport.service.SchoolUseService;

/**
 * 
 * SchoolUseServiceImpl 学校使用情况统计实现
 * 
 * @author creator zhanglzh 2017年1月3日 下午2:04:40
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class SchoolUseServiceImpl extends BaseService implements SchoolUseService {
    @Autowired
    private LessonReportDao lessonReportDao;

    @Override
    public Map<String, Object> getSchoolLessonUseTotal(Map<String, Object> param) {
        return lessonReportDao.selectOrgUsedTotal(param);
    }

    @Override
    public Page<Map<String, Object>> getSchoolLessonUse(Integer page, Integer rows, Map<String, Object> param) {
        return lessonReportDao.selectPageOrgUsed(page, rows, param);
    }

    /**
     * 
     * (导出Excel)<br>
     * 
     * @param sectionCode 学段编码
     * @param orgName 学校名称
     * @param baseDateStart 开始基准日
     * @param baseDateEnd 结束基准日
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getExcel(String sectionCode, String orgName, String baseDateStart, String baseDateEnd)
                    throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", baseDateStart);
        param.put("endDate", baseDateEnd);
        param.put("sectionCode", sectionCode);
        param.put("orgName", orgName);
        Page<Map<String, Object>> dataPage = this.getSchoolLessonUse(1, Integer.MAX_VALUE, param);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("学校使用情况统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "学校", "学段", "引用数" };
        Integer[] width = { 500 * 10, 256 * 10, 256 * 10 };
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
                cell.setCellValue(MapUtils.getInteger(map, "referedCount"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 2, 2);
        return wb;
    }
}
