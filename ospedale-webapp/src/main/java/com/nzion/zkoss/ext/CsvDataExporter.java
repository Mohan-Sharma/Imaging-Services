package com.nzion.zkoss.ext;

import static com.nzion.util.Constants.COMMA;
import static com.nzion.util.Constants.NEWLINE;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import com.nzion.domain.PatientWithDraw;
import com.nzion.domain.Person;
import com.nzion.report.dto.CollectionReportDto;
import com.nzion.report.dto.CollectionReportItemDto;
import com.nzion.report.dto.LabReportDto;
import com.nzion.service.impl.EncryptionService;
import com.nzion.util.ExcelHelper;
import com.nzion.util.Infrastructure;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.util.media.AMedia;
import org.zkoss.zhtml.Filedownload;

import com.nzion.util.UtilReflection;
import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 *         Aug 17, 2010
 */
public class CsvDataExporter implements DataExporter {

    @Override
    public void export(List<?> entities, String[] fields, String[] labels, String filename) {
        StringBuilder content = new StringBuilder();
        for (String label : labels) {
            content.append(label).append(COMMA);
        }
        content.deleteCharAt(content.length() - 1);
        content.append(NEWLINE);
        if (UtilValidator.isNotEmpty(entities))
            for (Object entity : entities) {
                for (String field : fields) {
                    Object fieldValue = UtilReflection.getNestedFieldValue(entity, field);
                    content.append(fieldValue == null ? "" : "\"" + fieldValue.toString() + "\"").append(COMMA);
                }
                content.deleteCharAt(content.length() - 1);
                content.append(NEWLINE);
            }
        Filedownload.save(content.toString().getBytes(), "text/csv", filename);

    }

    @Override
    public void exportCollectionReport(List<?> entities, Set keySet, String[] labels, String filename) {
        List<Map> mapEntity = (List<Map>) entities;
        StringBuilder content = new StringBuilder();
        for (String label : labels) {
            content.append(label).append(COMMA);
        }
        content.deleteCharAt(content.length() - 1);
        content.append(NEWLINE);
        if (UtilValidator.isNotEmpty(entities))
            for (Map entity : mapEntity) {
                for (Object key : keySet) {
                    Object value = entity.get(key);
                    content.append(value == null ? "" : "\"" + value.toString() + "\"").append(COMMA);
                }
                content.deleteCharAt(content.length() - 1);
                content.append(NEWLINE);
            }
        Filedownload.save(content.toString().getBytes(), "text/csv", filename);
    }

    @Override
    public void exportIPDCollectionReport(List<CollectionReportDto> collectionReportDtos, String fileName) throws IOException {
        HSSFWorkbook hssfWorkbook = ExcelHelper.createWorkbook();
        HSSFSheet hssfSheet = ExcelHelper.createWorksheet(fileName, hssfWorkbook);
        int rowNumber = 0;
        Row topHeaderRow = ExcelHelper.createRow(rowNumber, hssfSheet);
        ExcelHelper.createHeaderCell(0, "Item Name", topHeaderRow, hssfWorkbook);
        ExcelHelper.createHeaderCell(1, "Item Count", topHeaderRow, hssfWorkbook);
        ExcelHelper.createHeaderCell(2, "Item Amount", topHeaderRow, hssfWorkbook);
        rowNumber = rowNumber + 1;
        for (CollectionReportDto collectionReportDto : collectionReportDtos) {
            Row headerRow = ExcelHelper.createRow(rowNumber, hssfSheet);
            ExcelHelper.createHeaderCell(0, collectionReportDto.description, headerRow, hssfWorkbook);
            ExcelHelper.createNumberHeaderCell(1, collectionReportDto.count, headerRow, hssfWorkbook);
            ExcelHelper.createNumberHeaderCell(2, collectionReportDto.amount, headerRow, hssfWorkbook);
            rowNumber = rowNumber + 1;
            if (UtilValidator.isNotEmpty(collectionReportDto.collectionReportItemDto)) {
                for (CollectionReportItemDto collectionReportItemDto : collectionReportDto.collectionReportItemDto) {
                    Row itemRow = ExcelHelper.createRow(rowNumber, hssfSheet);
                    ExcelHelper.createStringCell(0, collectionReportItemDto.description, itemRow,hssfWorkbook);
                    ExcelHelper.createNumberCell(1, collectionReportItemDto.count, itemRow,hssfWorkbook);
                    ExcelHelper.createNumberCell(2, collectionReportItemDto.amount, itemRow,hssfWorkbook);
                    rowNumber = rowNumber + 1;
                }
            }
        }
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        hssfWorkbook.write(fos);
        fos.close();
        Filedownload.save(fos.toByteArray(), "application/x-ms-excel", fileName);
    }

    @Override
    public void exportLabCollectionReport(List<LabReportDto> labCollectionReportDtos, String fileName) throws IOException {
        HSSFWorkbook hssfWorkbook = ExcelHelper.createWorkbook();
        HSSFSheet hssfSheet = ExcelHelper.createWorksheet(fileName, hssfWorkbook);
        int rowNumber = 0;
        Row topHeaderRow = ExcelHelper.createRow(rowNumber, hssfSheet);
        ExcelHelper.createHeaderCell(0, "Test Name", topHeaderRow, hssfWorkbook);
        ExcelHelper.createHeaderCell(1, "Count", topHeaderRow, hssfWorkbook);
        ExcelHelper.createHeaderCell(2, "Amount", topHeaderRow, hssfWorkbook);
        ExcelHelper.createHeaderCell(3, "Referred By", topHeaderRow, hssfWorkbook);
        rowNumber = rowNumber + 1;
        if (UtilValidator.isNotEmpty(labCollectionReportDtos)) {
            for (LabReportDto labReportDto : labCollectionReportDtos) {
                Row itemRow = ExcelHelper.createRow(rowNumber, hssfSheet);
                ExcelHelper.createStringCell(0, labReportDto.getLabTestName(), itemRow,hssfWorkbook);
                ExcelHelper.createNumberCell(1, labReportDto.getCount(), itemRow,hssfWorkbook);
                ExcelHelper.createNumberCell(2, labReportDto.getAmount(), itemRow,hssfWorkbook);
                ExcelHelper.createStringCell(3, labReportDto.getReferredBy(), itemRow,hssfWorkbook);
                rowNumber = rowNumber + 1;
            }
        }
        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        hssfWorkbook.write(fos);
        fos.close();
        Filedownload.save(fos.toByteArray(), "application/x-ms-excel", fileName);
    }

    @Override
    public void exportWithHeader(StringBuilder headers, List<?> entities, String[] fields, String[] labels, String filename, String reportName, Set keySet) throws IOException, InvalidFormatException {
        HSSFWorkbook hssfWorkbook = ExcelHelper.createWorkbook();
        HSSFSheet hssfSheet = ExcelHelper.createWorksheet(filename, hssfWorkbook);
        int rowNumber = 0;
        int colNumber = 0;
        Row reportNameRow = ExcelHelper.createRow(rowNumber,hssfSheet);
        ExcelHelper.createReportHeaderCell(0,reportName,reportNameRow,hssfWorkbook);
        hssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,(labels.length)-1));
        rowNumber = rowNumber+1;
        if(headers != null && headers.length()>0){
            Row topCriteriaRow = ExcelHelper.createRow(rowNumber,hssfSheet);
            colNumber = 0;
            ExcelHelper.createCriteriaCell(colNumber,headers.toString(),topCriteriaRow,hssfWorkbook);
            hssfSheet.addMergedRegion(new CellRangeAddress(1,1,0,(labels.length)-1));
            rowNumber = rowNumber+1;

        }

        Row topHeaderRow = ExcelHelper.createRow(rowNumber, hssfSheet);
        colNumber = 0;
        for(String label : labels){
            ExcelHelper.createHeaderCell(colNumber,label,topHeaderRow,hssfWorkbook);
            colNumber++;
        }

        rowNumber = rowNumber+1;
        if(UtilValidator.isNotEmpty(entities)) {


            if(UtilValidator.isNotEmpty(keySet)) {
                List<Map> mapEntity = (List<Map>) entities;
                for (Map entity : mapEntity) {
                    Row itemRow = ExcelHelper.createRow(rowNumber, hssfSheet);
                    colNumber = 0;
                    if ("CONSULATION".equals(entity.get("itemName")) || ("PROCEDURE".equals(entity.get("itemName"))) || "REGISTRATION".equals(entity.get("itemName"))) {
                        for (Object key : keySet) {
                            Object fieldValue = entity.get(key);
                            if (UtilValidator.isNotEmpty(fieldValue) && fieldValue instanceof BigDecimal) {
                                BigDecimal amount = (BigDecimal) fieldValue;
                                ExcelHelper.createSubHeadingNumberCell(colNumber, amount.doubleValue(), itemRow, hssfWorkbook);
                            } else
                                ExcelHelper.createSubHeadingStringCell(colNumber, fieldValue.toString(), itemRow, hssfWorkbook);
                            colNumber++;

                        }
                    }
                    else{
                        for (Object key : keySet) {
                            Object fieldValue = entity.get(key);
                            if(UtilValidator.isNotEmpty( fieldValue) && fieldValue instanceof BigDecimal){
                                BigDecimal amount = (BigDecimal)fieldValue;
                                ExcelHelper.createNumberCell(colNumber, amount.doubleValue(), itemRow, hssfWorkbook);
                            }
                            else
                                ExcelHelper.createStringCell(colNumber, UtilValidator.isNotEmpty( fieldValue)?fieldValue.toString():"", itemRow,hssfWorkbook);
                            colNumber++;
                        }
                    }
                    rowNumber = rowNumber + 1;
                }
            }

             else {
                for(Object entity : entities){
                    Row itemRow = ExcelHelper.createRow(rowNumber,hssfSheet);
                    colNumber = 0;
                    for(String field : fields){
                        Object fieldValue = UtilReflection.getNestedFieldValue(entity,field);
                        if(UtilValidator.isNotEmpty(fieldValue) && fieldValue instanceof BigDecimal){
                            BigDecimal amount = (BigDecimal) fieldValue;
                            ExcelHelper.createNumberCell(colNumber,amount.doubleValue(),itemRow,hssfWorkbook);
                        }
                        else if(UtilValidator.isNotEmpty(fieldValue) && fieldValue instanceof Date){
                               Date date = (Date) fieldValue;
                               ExcelHelper.createDateCell(colNumber,date,itemRow,hssfWorkbook);
                        }
                        else {
                            ExcelHelper.createStringCell(colNumber, UtilValidator.isNotEmpty( fieldValue)?fieldValue.toString():"",itemRow,hssfWorkbook);
                        }
                        colNumber++;

                    }
                    rowNumber++;
                }
            }

        }

        for( int colIndex = 0; colIndex<10; colIndex++){
            hssfSheet.autoSizeColumn(colIndex);
        }


        ByteArrayOutputStream fos = new ByteArrayOutputStream();
        hssfWorkbook.write(fos);
        fos.close();
        Filedownload.save(fos.toByteArray(), "application/x-ms-excel", filename);


    }


}
