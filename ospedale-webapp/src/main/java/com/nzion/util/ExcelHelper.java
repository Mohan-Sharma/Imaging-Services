package com.nzion.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: NthDimenzion
 * @since 1.0
 */
public class ExcelHelper {


    public static HSSFWorkbook createWorkbook() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        return workbook;
    }

    public static HSSFSheet createWorksheet(String workSheetName, HSSFWorkbook hssfWorkbook) {
        HSSFSheet workSheet = hssfWorkbook.createSheet(workSheetName);
        return workSheet;
    }

    public static Row createRow(int rowNumber, HSSFSheet hssfSheet) {
        Row row = hssfSheet.createRow(rowNumber);
        return row;
    }

    public static Cell createStringCell(int cellNumber, String cellValue, Row row,HSSFWorkbook workbook) {
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        Cell cell = row.createCell(cellNumber);
        cell.setCellValue(cellValue);
        cell.setCellStyle(headerCellStyle);
        return cell;
    }


    public static Cell createNumberCell(int cellNumber, Number cellValue, Row row,HSSFWorkbook workbook) {
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        DataFormat dataFormat = workbook.createDataFormat();
        headerCellStyle.setDataFormat(dataFormat.getFormat("#,##0.000"));
        Cell cell = row.createCell(cellNumber);
        if (cellValue != null) {
            cell.setCellValue(cellValue.doubleValue());
        }
        cell.setCellStyle(headerCellStyle);
        return cell;
    }

    public static Cell createHeaderCell(int cellNumber, String headerLabel, Row row, HSSFWorkbook workbook) {
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);
        Cell cell = createStringCell(cellNumber, headerLabel, row,workbook);
        cell.setCellStyle(headerCellStyle);
        return cell;
    }

    public static Cell createNumberHeaderCell(int cellNumber, Number headerLabel, Row row, HSSFWorkbook workbook) {
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);
        Cell cell = createNumberCell(cellNumber, headerLabel, row,workbook);
        cell.setCellStyle(headerCellStyle);
        return cell;
    }

    public static Cell createReportHeaderCell(int cellNumber,String headerLabel,Row row,HSSFWorkbook workbook){
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerCellStyle.setAlignment(headerCellStyle.ALIGN_CENTER);
        Cell cell = row.createCell(cellNumber);
        cell.setCellValue(headerLabel);
        cell.setCellStyle(headerCellStyle);
        return cell;

    }

    public static Cell createCriteriaCell(int cellNumber,String headerLabel,Row row,HSSFWorkbook workbook){
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setWrapText(true);
        headerCellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
        headerCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        Cell cell = row.createCell(cellNumber);
        cell.setCellValue(headerLabel);
        cell.setCellStyle(headerCellStyle);
        return cell;

    }

    public static Cell createDateCell(int cellNumber,Date cellValue,Row row,HSSFWorkbook workbook){
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        DataFormat format = workbook.createDataFormat();
        headerCellStyle.setDataFormat(format.getFormat("dd/mm/yyyy"));
        headerCellStyle.setAlignment(headerCellStyle.ALIGN_LEFT);
        Cell cell = row.createCell(cellNumber);
        if(cellValue != null)
             cell.setCellValue(cellValue);
        cell.setCellStyle(headerCellStyle);
        return cell;

    }

    public static Cell createSubHeadingNumberCell(int cellNumber, Number cellValue,Row row,HSSFWorkbook workbook){
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        DataFormat format = workbook.createDataFormat();
        headerCellStyle.setDataFormat(format.getFormat("#,##0.000"));
        Cell cell = row.createCell(cellNumber);
        if (cellValue != null) {
            cell.setCellValue(cellValue.doubleValue());
        }
        cell.setCellStyle(headerCellStyle);
        return cell;
    }

    public static Cell createSubHeadingStringCell(int cellNumber, String cellValue, Row row,HSSFWorkbook workbook){
        HSSFFont headerFont = workbook.createFont();
        headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.BLACK.index);
        HSSFCellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);

        Cell cell = row.createCell(cellNumber);
        cell.setCellValue(cellValue);
        cell.setCellStyle(headerCellStyle);
        return cell;


    }

}
