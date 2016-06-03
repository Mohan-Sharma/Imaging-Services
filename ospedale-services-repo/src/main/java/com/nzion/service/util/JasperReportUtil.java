package com.nzion.service.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;

import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.zkoss.zul.Filedownload;

import com.nzion.domain.base.BaseEntity;
import com.nzion.domain.billing.InvoiceItem;

public class JasperReportUtil {

	public static void buildPdfReport(String reportDesignFile, Map<String,Object> reportParameterMap,
			Collection<? extends BaseEntity> list, String outputFileName) throws JRException {
	JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
	byte[] buffer = JasperRunManager.runReportToPdf(buildJasperReport(reportDesignFile), reportParameterMap,dataSource);
	Filedownload.save(buffer, "application/pdf", outputFileName);
	}

	public static void buildCsvReport(String reportDesignFile, Map<String,Object> reportParameterMap,
			Collection<? extends BaseEntity> objects, String outputFileName) throws JRException {
	JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(objects);
	JasperPrint jasperPrint = JasperFillManager.fillReport(buildJasperReport(reportDesignFile), reportParameterMap,
			jrDataSource);
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	JRCsvExporter csvExporter = new JRCsvExporter();
	csvExporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jasperPrint);
	csvExporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, outputStream);
	JasperReportsUtils.render(csvExporter, jasperPrint, outputStream);
	Filedownload.save(outputStream.toByteArray(), "text/csv", outputFileName);
	}

	private static JasperReport buildJasperReport(String reportDesignFile) throws JRException {
	InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(reportDesignFile);
	JasperDesign jasperDesign = JasperManager.loadXmlDesign(reportStream);
	JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
	return jasperReport;
	}

}
