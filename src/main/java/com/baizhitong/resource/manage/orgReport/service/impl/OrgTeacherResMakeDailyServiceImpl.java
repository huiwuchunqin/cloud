package com.baizhitong.resource.manage.orgReport.service.impl;

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
import com.baizhitong.resource.dao.report.TeacherResMakeDailyDao;
import com.baizhitong.resource.manage.orgReport.service.OrgTeacherResMakeDailyService;

/**
 * 
 * 机构作者统计Service实现类
 * 
 * @author creator ZhangQiang 2016年11月23日 上午10:23:09
 * @author updater
 *
 * @version 1.0.0
 */
@Service
public class OrgTeacherResMakeDailyServiceImpl extends BaseService implements OrgTeacherResMakeDailyService {

    @Autowired
    private TeacherResMakeDailyDao teacherResMakeDailyDao;

    /**
     * 
     * (分页查询作者统计信息)<br>
     * 
     * @param param 查询参数
     * @param page 当前页码
     * @param rows 每页大小
     * @return
     */
    @Override
    public Page<Map<String, Object>> queryAuthorReportInfo(Map<String, Object> param, Integer page, Integer rows) {
        return teacherResMakeDailyDao.selectByOrg(page, rows, param);
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
        return teacherResMakeDailyDao.selectByOrg(param);
    }

    /**
     * 
     * (以Excel形式数据导出)<br>
     * 
     * @param sectionCode 学段编码
     * @param subjectCode 学科编码
     * @param orgCode 机构编码
     * @param baseDateStart 开始日期
     * @param baseDateEnd 结束日期
     * @param userName 作者名称
     * @param shareLevel 共享级别
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "deprecation" })
    @Override
    public HSSFWorkbook getExcel(String sectionCode, String subjectCode, String orgCode, String baseDateStart,
                    String baseDateEnd, String userName, Integer shareLevel) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("startDate", baseDateStart);
        param.put("endDate", baseDateEnd);
        param.put("sectionCode", sectionCode);
        param.put("orgCode", orgCode);
        param.put("userName", userName);
        param.put("subjectCode", subjectCode);
        param.put("shareLevel", shareLevel);
        Page<Map<String, Object>> dataPage = this.queryAuthorReportInfo(param, 1, Integer.MAX_VALUE);
        List<Map<String, Object>> dataList = dataPage.getRows();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("作者统计(机构)");
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 300);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        style.setLocked(false);
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        String[] name = { "作者", "机构名称", "资源数", "视频数", "文档数", "测验数", "题目数" };
        Integer[] width = { 256 * 10, 600 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10, 256 * 10 };
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
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getString(map, "orgName"));
                cell = row.createCell((short) 2);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "resTotal"));
                cell = row.createCell((short) 3);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "mediaTotal"));
                cell = row.createCell((short) 4);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "docTotal"));
                cell = row.createCell((short) 5);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "exerciseTotal"));
                cell = row.createCell((short) 6);
                cell.setCellStyle(style2);
                cell.setCellValue(MapUtils.getInteger(map, "questionTotal"));
                i++;
            }
        }
        ExcelUtils.generateTotalRow(wb, sheet, 2, 6);
        return wb;
    }

}
