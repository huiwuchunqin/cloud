package com.baizhitong.resource.manage.report.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baizhitong.resource.common.constants.CoreConstants;
import com.baizhitong.resource.common.core.service.BaseService;
import com.baizhitong.resource.common.utils.BeanHelper;
import com.baizhitong.resource.common.utils.ExcelUtils;
import com.baizhitong.resource.dao.test.QuestionDao;
import com.baizhitong.resource.manage.report.service.SubjectReportService;
import com.baizhitong.utils.ObjectUtils;

/**
 * 
 * 学科统计Service实现类
 * 
 * @author creator ZhangQiang 2016年9月6日 下午5:06:54
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class SubjectReportServiceImpl extends BaseService implements SubjectReportService {

    @Autowired
    private QuestionDao questionDao;

    /**
     * 
     * (查询学科统计信息)<br>
     * 
     * @param param 查询参数
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> querySubjectReportInfo(Map<String, Object> param) throws Exception {
        return questionDao.selectSubjectReportInfo(param);
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
        return questionDao.selectSubjectReportTotalNum(param);
    }

    /**
     * 
     * (数据导出)<br>
     * 
     * @param sectionCode
     * @param subjectCode
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getExcel(String sectionCode, String subjectCode, String startDate, String endDate,
                    String shareLevel) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
       
        if (!CoreConstants.AREA_ORG_CODE.equals(getOrgCode() )) {
            param.put("orgCode", getOrgCode() );
        }
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        param.put("shareLevel", shareLevel);
        List<Map<String, Object>> dataList = this.querySubjectReportInfo(param);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("学科统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "学段", "学科", "分享等级", "视频数", "文档数", "测验数", "题目数", "资源总数" };
        Integer[] width = { 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10 };
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
                cell.setCellValue(MapUtils.getString(map, "shareLevelName"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "mediaCount"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "docCount"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "testCount"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "questionCount"));
                cell = row.createCell((short) 7);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "totalCount"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 3, 7);
        return wb;
    }

    @Override
    public List<Map<String, Object>> querySubjectUsageReportInfo(Map<String, Object> param) {
        return questionDao.selectSubjectUsageReportInfo(param);
    }

    @Override
    public Map<String, Object> queryTotalUsageInfo(Map<String, Object> param) {
        return questionDao.selectSubjectUsageReportTotalNum(param);
    }

    @Override
    public HSSFWorkbook getUsageExcel(String sectionCode, String subjectCode, String startDate, String endDate)
                    throws Exception {
       
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", startDate);
        if (!CoreConstants.AREA_ORG_CODE.equals(getOrgCode() )) {
            param.put("orgCode", getOrgCode() );
        }
        param.put("endDate", endDate);
        param.put("sectionCode", sectionCode);
        param.put("subjectCode", subjectCode);
        List<Map<String, Object>> dataList = this.querySubjectUsageReportInfo(param);
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("学科使用情况统计");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "学段", "学科", "查看数", "下载数", "引用数", "收藏数", "点赞数", "评论数" };
        Integer[] width = { 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10 };
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
                if (ObjectUtils.isNotNull(MapUtils.getInteger(map, "browseCount"))) {
                    cell.setCellValue(MapUtils.getInteger(map, "browseCount"));
                } else {
                    cell.setCellValue(0);
                }
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                if (ObjectUtils.isNotNull(MapUtils.getInteger(map, "downloadCount"))) {
                    cell.setCellValue(MapUtils.getInteger(map, "downloadCount"));
                } else {
                    cell.setCellValue(0);
                }
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);

                if (ObjectUtils.isNotNull(MapUtils.getInteger(map, "referCount"))) {
                    cell.setCellValue(MapUtils.getInteger(map, "referCount"));
                } else {
                    cell.setCellValue(0);
                }
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);

                if (ObjectUtils.isNotNull(MapUtils.getInteger(map, "favoriteCount"))) {
                    cell.setCellValue(MapUtils.getInteger(map, "favoriteCount"));
                } else {
                    cell.setCellValue(0);
                }
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);

                if (ObjectUtils.isNotNull(MapUtils.getInteger(map, "goodCount"))) {
                    cell.setCellValue(MapUtils.getInteger(map, "goodCount"));
                } else {
                    cell.setCellValue(0);
                }
                cell = row.createCell((short) 7);
                cell.setCellStyle(style2);

                if (ObjectUtils.isNotNull(MapUtils.getInteger(map, "commentCount"))) {
                    cell.setCellValue(MapUtils.getInteger(map, "commentCount"));
                } else {
                    cell.setCellValue(0);
                }
                cell = row.createCell((short) 8);
                cell.setCellStyle(style2);
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 2, 7);
        return wb;
    }

}
