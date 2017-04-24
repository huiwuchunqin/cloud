package com.baizhitong.resource.common.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;

import com.baizhitong.utils.StringUtils;

/**
 * 导出类 ExportUtils TODO
 * 
 * @author creator gaow 2016年11月22日 下午1:32:14
 * @author updater
 *
 * @version 1.0.0
 */
public class ExcelUtils {
    /**
     * 数据excel导出 ()<br>
     * 
     * @param dataList 数据列表
     * @param attrArry 属性列表
     * @param fontSizeMap 字体列表
     * @return excel
     */
    public static HSSFWorkbook getWb(List<Map<String, Object>> dataList, Map<String, String> attrArry,
                    Map<String, Integer> fontSizeMap) {
        HSSFWorkbook excel = new HSSFWorkbook();
        if (dataList == null || dataList.size() <= 0) {
            return excel;
        }

        HSSFSheet sheet = excel.createSheet();
        String[] keyArray = new String[attrArry.keySet().size()];
        attrArry.keySet().toArray(keyArray);
        String[] valueArray = new String[attrArry.values().size()];
        attrArry.values().toArray(valueArray);
        // 启用编辑
        sheet.setActive(true);
        sheet.setAlternativeFormula(true);
        sheet.setAutobreaks(true);
        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        HSSFRow rowTitle = sheet.createRow(0);
        // excel头的样式
        HSSFCellStyle style = excel.createCellStyle();
        style.setAlignment(style.ALIGN_CENTER);
        style.setLocked(false);
        // excel头字体样式
        Font font = excel.createFont();
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setColor(Font.COLOR_RED);
        style.setFont(font);
        rowTitle.setRowStyle(style);
        // 插入excel头部
        for (int j = 0; j < keyArray.length; j++) {
            HSSFCell cell = rowTitle.createCell(j, HSSFCell.CELL_TYPE_STRING);
            cell.setCellStyle(style);
            cell.setCellValue(keyArray[j]);
        }

        // 插入excel主体
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> map = dataList.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            for (int j = 0; j < valueArray.length; j++) {
                String variable = MapUtils.getString(map, valueArray[j]);
                Integer size = 0;

                /*------设置列宽start----------*/
                if (fontSizeMap != null) {
                    size = MapUtils.getInteger(fontSizeMap, valueArray[j]);
                }
                if (size != null && size > 0) {
                    sheet.setColumnWidth(j, 255 * (size * 2));
                } else {
                    int length = 6;
                    if (variable != null) {
                        length = variable.length() > 6 ? variable.length() : 6;
                    }
                    length = length > 110 ? 110 : length;
                    sheet.setColumnWidth(j, 255 * (length * 2));
                }
                /*------设置列宽end----------*/

                HSSFCell cell = row.createCell(j, HSSFCell.CELL_TYPE_STRING);
                if (StringUtils.isNotEmpty(variable) && variable.indexOf("http") >= 0) {
                    HSSFCellStyle hlinkStyle = excel.createCellStyle();
                    HSSFFont hlinkFont = excel.createFont();
                    hlinkFont.setUnderline(HSSFFont.U_SINGLE);
                    hlinkFont.setColor(HSSFColor.BLUE.index);
                    hlinkStyle.setFont(hlinkFont);
                    cell.setCellValue(variable);
                    cell.setCellStyle(hlinkStyle);
                    CreationHelper helper = excel.getCreationHelper();
                    Hyperlink link = helper.createHyperlink(HSSFHyperlink.LINK_URL);
                    link.setAddress(variable);
                    cell.setHyperlink(link);
                } else {
                    cell.setCellValue(variable);
                }
            }
        }
        return excel;
    }

    /**
     * 数据excel导出 ()<br>
     * 
     * @param dataList 数据列表
     * @param attrArry 属性列表
     * @param fontSizeMap 字体列表
     * @param countColumns 需要求和的列
     * @return excel
     */
    public static HSSFWorkbook getWb(List<Map<String, Object>> dataList, Map<String, String> attrArry,
                    Map<String, Integer> fontSizeMap, List<String> countColumns) {

        HSSFWorkbook excel = getWb(dataList, attrArry, fontSizeMap);

        if (countColumns == null || countColumns.size() <= 0) {
            return excel;
        }
        HSSFSheet sheet = excel.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();
        List<TotalCell> totalCellList = new ArrayList<TotalCell>();
        int position = 0;
        for (String key : attrArry.values()) {
            if (countColumns.contains(key)) {
                totalCellList.add(new TotalCell(position));
            }
            ++position;
        }
        Iterator<Row> rowIter = sheet.iterator();
        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            for (TotalCell cell : totalCellList) {
                cell.totalAdd(getValue(row.getCell(cell.getPositionX())));
            }
        }
        HSSFRow row = sheet.createRow(++rowCount);
        HSSFCell titleCell = row.createCell(0);
        titleCell.setCellValue("合计");
        for (TotalCell totalCell : totalCellList) {
            HSSFCell cell = row.createCell(totalCell.getPositionX());
            cell.setCellValue(totalCell.getTotal().toString());
        }
        return excel;
    }

    /**
     * 生成最后一列 ()<br>
     * 
     * @param sheet
     * @param startIndex 开始求和的列
     * @param endIndex 结束求和的列
     */
    public static void generateTotalRow(HSSFWorkbook wb, HSSFSheet sheet, Integer startIndex, Integer endIndex) {
        if (sheet == null) {
            return;
        }
        Integer rowCount = sheet.getLastRowNum() + 1;
        List<TotalCell> totalCellList = getTotalList(startIndex, endIndex);
        if (totalCellList == null) {
            return;
        }
        Iterator<Row> countRow = sheet.iterator();
        while (countRow.hasNext()) {
            Row itemRow = countRow.next();
            for (TotalCell cell : totalCellList) {
                cell.totalAdd(getValue(itemRow.getCell(cell.getPositionX())));
            }
        }
        HSSFCellStyle style2 = wb.createCellStyle();
        style2.setAlignment(CellStyle.ALIGN_RIGHT);
        HSSFRow row = sheet.createRow(rowCount);
        HSSFCell titleCell = row.createCell(0);
        titleCell.setCellValue("合计");
        for (TotalCell totalCell : totalCellList) {
            HSSFCell cell = row.createCell(totalCell.getPositionX());
            cell.setCellValue(totalCell.getTotal().toString());
            cell.setCellStyle(style2);
        }
    }

    /**
     * 获取求和列表 ()<br>
     * 
     * @param startIndex 开始求和的列
     * @param endIndex 结束求和的列
     * @return 求和列表
     */
    public static List<TotalCell> getTotalList(Integer startIndex, Integer endIndex) {
        if (endIndex >= startIndex && startIndex > 0) {
            List<TotalCell> totalCellList = new ArrayList<TotalCell>();
            for (; startIndex <= endIndex; startIndex++) {
                TotalCell cell = new TotalCell(startIndex);
                totalCellList.add(cell);
            }
            return totalCellList;
        } else {
            return null;
        }
    }

    /**
     * 取值 ()<br>
     * 
     * @param cell 单元格
     * @return 值
     */
    public static Integer getValue(Cell cell) {
        if (cell == null) {
            return 0;
        } else {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                    try {
                        return Integer.parseInt(cell.getStringCellValue());
                    } catch (NumberFormatException e) {
                        // TODO Auto-generated catch block
                        return 0;
                    }
                case HSSFCell.CELL_TYPE_NUMERIC:
                    return (int) cell.getNumericCellValue();
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    return 0;
                case HSSFCell.CELL_TYPE_BLANK:
                    return 0;
                default:
                    return 0;
            }
        }
    }

    // 表格数据格式化
    public static String changeString(Cell cell) {
        if (cell == null) {
            return "";
        } else {
            String strCell = "";
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_STRING:
                    strCell = cell.getStringCellValue().trim();
                    break;
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat strFormat = new DecimalFormat("0");
                    strCell = strFormat.format((cell.getNumericCellValue()));
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN:
                    strCell = String.valueOf(cell.getBooleanCellValue());
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    strCell = "";
                    break;
                default:
                    strCell = "";
                    break;
            }
            if (strCell.equals("") || strCell == null) {
                return "";
            }
            return strCell;
        }
    }

    // 检查是否为空行
    public static boolean checkRowNull(Row hssfRow) {
        if(hssfRow==null){
            return true;
        }
        int num = 0;
        Iterator<Cell> cellItr = hssfRow.cellIterator();
        while (cellItr.hasNext()) {
            Cell cell = cellItr.next();
            if (cell.getCellType() == HSSFCell.CELL_TYPE_BLANK || StringUtils.isEmpty(changeString(cell))) {
                continue;
            } else {
                num++;
            }
        }
        return num == 0;
    }
}
