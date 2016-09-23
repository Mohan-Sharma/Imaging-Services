package com.nzion.util;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nzion.domain.billing.Invoice;
import com.nzion.domain.billing.InvoiceItem;
import com.nzion.domain.billing.InvoicePayment;
import com.nzion.repository.billing.impl.HibernateBillingRepository;
import com.nzion.service.common.CommonCrudService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.math.MathContext;
import java.net.URL;
import java.util.*;
import java.util.List;


public class PDFGenerator {

	public static List serviceMasterList = RestServiceConsumer.getAllServiceMaster();

	public static CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");
	public static HibernateBillingRepository hibernateBillingRepository = Infrastructure.getSpringBean("billingRepository");

	public static InputStreamSource createWeeklyProviderRevenueReportPDFFile(Invoice invoice, String paymentId, String url){

		//**********************************************************
try {

	//com.nzion.zkoss.composer.BillingController billingController = new com.nzion.zkoss.composer.BillingController();
		/*if(com.nzion.util.UtilValidator.isNotEmpty(invId)){
			invoice= billingController.getInvoice();
		}*/
	com.nzion.domain.Patient patient = invoice.getPatient();
	Date createdTxTimestamp = invoice.getCreatedTxTimestamp();
	String invoiceDate = UtilDateTime.formatDateToDatetimeFormat(createdTxTimestamp);

	com.nzion.util.CurrencyConverter currencyConverter = new com.nzion.util.CurrencyConverter();
	String currencyInWords = "";
	Image image = null;
	BigDecimal totalAmount = invoice.getTotalAmount().getAmount();
	currencyInWords = currencyConverter.rupeesInWords(totalAmount.toString(), null, null);

	/*com.nzion.domain.UserLogin userLogin = com.nzion.util.Infrastructure.getUserLogin();
	String loggedInPersonFormattedName = com.nzion.util.ViewUtil.getFormattedName(userLogin.getPerson());*/
	String patientType = patient.getPatientType();
	boolean isInsurancePatient = false;
	if ("INSURANCE".equals(patientType))
		isInsurancePatient = true;

	/*com.nzion.domain.Location location = com.nzion.util.Infrastructure.getSelectedLocation();
	com.nzion.domain.Practice practice = com.nzion.util.Infrastructure.getPractice();*/

	//URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource("/images/logo_256.png");
	//URL resourceUrl = new URL(url+"images/logo.png");
	String imgUrl = com.nzion.util.Infrastructure.getPractice().getImageUrl() != null ? com.nzion.util.Infrastructure.getPractice().getImageUrl().replaceFirst("/", "") : null;
	URL resourceUrl = null;
	if (imgUrl != null){
		File file = new File(imgUrl);
		resourceUrl = file.toURI().toURL();
	}

	String heading1 = "Patient Invoice";
	/*if (billingController.isPaymentReceived())
		heading1 = heading1 + " and Receipt";*/

	List<InvoiceItem> invoiceItems = new ArrayList();
	List cancelledInvoiceItems = new ArrayList();
	boolean isCancelled = false;
	/*for (com.nzion.domain.billing.InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
		if (!"Cancel".equals(invoiceItem.getInvoiceItemStatus())) {
			invoiceItems.add(invoiceItem);
		} else {
			cancelledInvoiceItems.add(invoiceItem);
			isCancelled = true;
		}
	}*/

	/*java.math.BigDecimal totalDiscount = java.math.BigDecimal.ZERO.setScale(3, java.math.RoundingMode.HALF_UP);
	for (com.nzion.domain.billing.InvoiceItem invoiceItem : invoiceItems) {
		if (invoiceItem.getIsStatusCancel())
			continue;
		java.math.BigDecimal concessionAmount = invoiceItem.getConcessionAmount();
		java.math.BigDecimal grossAmount = invoiceItem.getGrossAmount();
		if ("PERCENTAGE".equals(invoiceItem.getConcessionType())) {
			concessionAmount = com.nzion.zkoss.composer.BillingController.percentage(grossAmount, invoiceItem.getConcessionAmount());
		}
		if (concessionAmount == null)
			concessionAmount = java.math.BigDecimal.ZERO;
		if (totalDiscount == null)
			totalDiscount = java.math.BigDecimal.ZERO;

		totalDiscount = totalDiscount.add(concessionAmount.setScale(3, java.math.RoundingMode.HALF_UP));
	}*/

	/*StringBuffer ageGender = new StringBuffer();
	if (patient != null && com.nzion.util.UtilValidator.isNotEmpty(patient.getAge()))
		ageGender.append(patient.getAge()).append("/");
	if (patient != null && com.nzion.util.UtilValidator.isNotEmpty(patient.getGender()))
		ageGender.append(patient.getGender());*/
	/*boolean isLogoWithAdd = com.nzion.util.Infrastructure.getPractice().isLogoWithAddress();
	boolean isLogoWithoutAdd = !isLogoWithAdd;*/
	boolean isInvoicePayments = false;
	InvoicePayment invoicePayment = null;
	String receiptNo = "";
	if (invoice.getInvoicePayments() != null) {
		isInvoicePayments = invoice.getInvoicePayments().size() > 0;

		Set<InvoicePayment> invoicePayments = invoice.getInvoicePayments();
		for (InvoicePayment payment : invoicePayments) {
			invoicePayment = payment;
			receiptNo = payment.getReceiptNumber();
		}
	}

			Map<String,Object> map = RestServiceConsumer.getPaymentGateWayTransactionDetByPaymentId(paymentId);
			String paymentMode = "";
			String onlinePaymentRef = "";
			String paymentDate = "";
			String amountPaid = "";
			if (UtilValidator.isNotEmpty(map)){
				paymentMode = map.get("payment_channel").toString();
				onlinePaymentRef = map.get("isys_tracking_ref").toString();
				//Date date = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(map.get("transaction_timestamp").toString().replace("T"," "));
				//paymentDate = UtilDateTime.formatDateToDatetimeFormat(date);
				paymentDate = map.get("transaction_timestamp").toString();
				amountPaid = new BigDecimal((Double)map.get("transaction_amount"), MathContext.DECIMAL64).setScale(3).toString();
			}

	//*********************************************************


	// get line itemsI
	// 39
	//List<Map<String, Object>> lineItems = portalFinder.getPackageTransctionLineItems(pgTransaction.getPaymentId());

	// output stream to hold the PDF under construction
	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	//URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource("static/images/logo.png");

	Document document = new Document(PageSize.A4, 36, 72, 50, 50);
	try {
		PdfWriter.getInstance(document, outputStream);
		document.open();
		try {
			if (resourceUrl != null) {
			image = Image.getInstance(resourceUrl);
			image.setAlignment(Element.ALIGN_CENTER);

			int indentation = 0;
			float scaler = ((document.getPageSize().getWidth() - document.leftMargin()- document.rightMargin() - indentation) / image.getWidth()) * 30;
			image.scalePercent(scaler);
			//image.scalePercent(10f);
			} else {
				image = null;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		Paragraph receipt = new Paragraph("Receipt", FontFactory.getFont(FontFactory.HELVETICA, 15.0f, Font.UNDEFINED, java.awt.Color.black));
		receipt.setAlignment(Element.ALIGN_CENTER);

		Paragraph heading = new Paragraph("No : " + receiptNo + "    Date : " + invoiceDate);
		heading.setAlignment(Element.ALIGN_CENTER);

		String paymentGateway = "ISYS";
		String paymentChannel = "KNET/VISA";

		PdfPTable table = new PdfPTable(4); // 4 columns.
		table.setWidthPercentage(100); //Width 100%
		table.setSpacingBefore(10f); //Space before table
		table.setSpacingAfter(10f);

		PdfPCell cell1 = new PdfPCell();
		cell1.addElement(new Paragraph("Member Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

		PdfPCell cell2 = new PdfPCell();
		cell2.addElement(new Paragraph(patient.getFirstName() + " " + patient.getLastName()));

		PdfPCell cell3 = new PdfPCell();
		cell3.addElement(new Paragraph("Payment Mode", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

		PdfPCell cell4 = new PdfPCell();
		cell4.addElement(new Paragraph(paymentMode));

		/*PdfPCell cell5 = new PdfPCell();
		cell5.addElement(new Paragraph(""));

		PdfPCell cell6 = new PdfPCell();
		cell6.addElement(new Paragraph(""));*/

		/*PdfPCell cell7 = new PdfPCell();
		cell7.addElement(new Paragraph("Payment Mode"));

		PdfPCell cell8 = new PdfPCell();
		cell8.addElement(new Paragraph(paymentMode));*/

		PdfPCell cell5 = new PdfPCell();
		cell5.addElement(new Paragraph("Online Payment Ref", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

		PdfPCell cell6 = new PdfPCell();
		cell6.addElement(new Paragraph(onlinePaymentRef));

		PdfPCell cell7 = new PdfPCell();
		cell7.addElement(new Paragraph("Payment Date", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

		PdfPCell cell8 = new PdfPCell();
		cell8.addElement(new Paragraph(paymentDate));

		PdfPCell cell9 = new PdfPCell();
		cell9.addElement(new Paragraph("Advance Amount (KD)", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

		PdfPCell cell10 = new PdfPCell();
		cell10.addElement(new Paragraph(amountPaid));

		/*PdfPCell cell15 = new PdfPCell();
		cell15.addElement(new Paragraph(""));

		PdfPCell cell16 = new PdfPCell();
		cell16.addElement(new Paragraph("kjhkjh"));*/

		PdfPCell cell11 = new PdfPCell();
		cell11.addElement(new Paragraph("Invoice Amount (KD)", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

		PdfPCell cell12 = new PdfPCell();
		cell12.addElement(new Paragraph(invoice.getTotalAmount().getAmount().toString()));

		/*PdfPCell cell19 = new PdfPCell();
		cell19.addElement(new Paragraph(""));

		PdfPCell cell20 = new PdfPCell();
		cell20.addElement(new Paragraph(""));*/

		PdfPCell cell13 = new PdfPCell();
		cell13.addElement(new Paragraph("Invoice Number", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

		PdfPCell cell14 = new PdfPCell();
		cell14.addElement(new Paragraph(invoice.getInvoiceNumber()));

		PdfPCell cell15 = new PdfPCell();
		cell15.addElement(new Paragraph("Invoice Date", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

		PdfPCell cell16 = new PdfPCell();
		cell16.addElement(new Paragraph(invoiceDate));

		table.addCell(cell1);
		table.addCell(cell2);
		table.addCell(cell3);
		table.addCell(cell4);
		table.addCell(cell5);
		table.addCell(cell6);
		table.addCell(cell7);
		table.addCell(cell8);
		table.addCell(cell9);
		table.addCell(cell10);
		table.addCell(cell11);
		table.addCell(cell12);
		table.addCell(cell13);
		table.addCell(cell14);
		table.addCell(cell15);
		table.addCell(cell16);
		/*table.addCell(cell17);
		table.addCell(cell18);
		table.addCell(cell19);
		table.addCell(cell20);
		table.addCell(cell21);
		table.addCell(cell22);
		table.addCell(cell23);
		table.addCell(cell24);*/
		if (image != null){
			document.add(image);
		}
		document.add(new Paragraph(" "));
		document.add(receipt);
		document.add(new Paragraph(" "));
		document.add(heading);
		document.add(new Paragraph(" "));
		document.add(table);

		document.close();
	} catch (Exception e) {
		e.printStackTrace();
	}

	// construct StreamSource for the PDF
	ByteArrayResource streamSource = new ByteArrayResource(outputStream.toByteArray());
	// return the full file Path for the PDF
	return streamSource;
}catch (Exception e){
	e.printStackTrace();
	return null;
}
	}
}