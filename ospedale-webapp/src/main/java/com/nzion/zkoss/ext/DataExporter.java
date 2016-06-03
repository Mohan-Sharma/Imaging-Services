package com.nzion.zkoss.ext;

import com.nzion.domain.PatientWithDraw;
import com.nzion.report.dto.CollectionReportDto;
import com.nzion.report.dto.LabReportDto;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Sandeep Prusty
 *         Aug 17, 2010
 */
public interface DataExporter {

    void export(List<?> entities, String[] fields, String[] labels, String filename);

    void exportCollectionReport(List<?> entities, Set keySet, String[] labels, String filename);

    void exportIPDCollectionReport(List<CollectionReportDto> collectionReportDtos, String fileName) throws IOException;

    void exportLabCollectionReport(List<LabReportDto> labCollectionReportDtos, String fileName) throws IOException;

    void exportWithHeader(StringBuilder headers, List<?> entities, String[] fields, String[] labels, String filename, String reportName
            ,Set keySet) throws IOException, InvalidFormatException;

}
