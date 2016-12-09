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

	/*com.nzion.domain.Location location = com.nzion.util.Infrastructure.getSelectedLocation();*/
			com.nzion.domain.Practice practice = com.nzion.util.Infrastructure.getPractice();

			//MGM
			String practiceName = practice.getPracticeName();
			//String practiceName = invoice.getSchedule().getLocation().getName();
			String clinicCity =  invoice.getLabOrderId().getLocation().getContacts().getPostalAddress().getCity();
			String clinicAddress1 = invoice.getLabOrderId().getLocation().getContacts().getPostalAddress().getAddress1();
			String clinicAddress2 = invoice.getLabOrderId().getLocation().getContacts().getPostalAddress().getAddress2();
			String clinicCountry =  invoice.getLabOrderId().getLocation().getContacts().getPostalAddress().getCountryGeo();
			String clinicPostalCode =  invoice.getLabOrderId().getLocation().getContacts().getPostalAddress().getPostalCode();
			String stateProvinceGeo = invoice.getLabOrderId().getLocation().getContacts().getPostalAddress().getStateProvinceGeo();
			String officePhone = invoice.getLabOrderId().getLocation().getContacts().getOfficePhone();
			String email = invoice.getLabOrderId().getLocation().getContacts().getEmail();

			String clinicAddress = practiceName+"\n"+clinicCity+"\n"+clinicAddress1+", "+clinicAddress2+"\n"+clinicPostalCode+", "+
					stateProvinceGeo+", "+clinicCountry+"\nTel:"+officePhone;

			//URL resourceUrl = Thread.currentThread().getContextClassLoader().getResource("/images/logo_256.png");
			//URL resourceUrl = new URL(url+"images/logo.png");
			String imgUrl = com.nzion.util.Infrastructure.getPractice().getImageUrl() != null ? com.nzion.util.Infrastructure.
					getPractice().getImageUrl().replaceFirst("/", "") : null;

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

			System.out.println("\n\t\t****===**** PDFGenerator(): " + map.toString()+"\n");

			if (UtilValidator.isNotEmpty(map)){
				//paymentMode = String.valueOf(map.get("payment_channel"));
				paymentMode = "Online Payment";
				onlinePaymentRef = String.valueOf(map.get("isys_tracking_ref"));
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
						float scaler = ((document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin() - indentation) / image.getWidth()) * 30;
						image.scalePercent(scaler);
						//image.scalePercent(10f);
					} else {
						image = null;
					}
				} catch (Exception e){
					e.printStackTrace();
				}
				Paragraph receipt = new Paragraph("Patient Receipt", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13.0f,
						com.lowagie.text.Font.UNDEFINED, java.awt.Color.black));
				receipt.setAlignment(Element.ALIGN_CENTER);

		/*Paragraph heading = new Paragraph("No : " + receiptNo + "    Date : " + invoiceDate);
		heading.setAlignment(Element.ALIGN_CENTER);*/

		String paymentGateway = "ISYS";
		String paymentChannel = "KNET/VISA";

				PdfPTable headerInfo = new PdfPTable(4); // 4 columns.
				headerInfo.setWidthPercentage(100); //Width 100%
				headerInfo.setSpacingBefore(10f); //Space before table
				headerInfo.setSpacingAfter(10f);

				PdfPTable serviceInfo = new PdfPTable(4); // 4 columns.
				serviceInfo.setWidthPercentage(100); //Width 100%
				serviceInfo.setSpacingBefore(10f); //Space before table
				serviceInfo.setSpacingAfter(10f);

				PdfPTable paymentDetails = new PdfPTable(5); // 5 columns.
				paymentDetails.setWidthPercentage(100); //Width 100%
				paymentDetails.setSpacingBefore(10f); //Space before table
				paymentDetails.setSpacingAfter(20f);

				PdfPCell cell1 = new PdfPCell();
				cell1.setBorder(Rectangle.NO_BORDER);
				cell1.enableBorderSide(Rectangle.LEFT);
				cell1.enableBorderSide(Rectangle.TOP);
				cell1.addElement(new Paragraph("Afya Id", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell2 = new PdfPCell();
				cell2.setBorder(Rectangle.NO_BORDER);
				cell2.enableBorderSide(Rectangle.TOP);
				cell2.addElement(new Paragraph(patient.getAfyaId(), FontFactory.getFont(FontFactory.HELVETICA, 10f)));

				PdfPCell cell3 = new PdfPCell();
				cell3.setBorder(Rectangle.NO_BORDER);
				cell3.enableBorderSide(Rectangle.TOP);
				cell3.enableBorderSide(Rectangle.LEFT);
				cell3.addElement(new Paragraph("Doctor", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell4 = new PdfPCell();
				cell4.setBorder(Rectangle.NO_BORDER);
				cell4.enableBorderSide(Rectangle.TOP);
				cell4.enableBorderSide(Rectangle.RIGHT);
				cell4.addElement(new Paragraph(invoice.getLabOrderId().getPhlebotomist().getSalutation()+" "+
						invoice.getLabOrderId().getPhlebotomist().getFirstName()+" "
						+invoice.getLabOrderId().getPhlebotomist().getLastName(), FontFactory.getFont(FontFactory.HELVETICA, 10f))); // how to get doctor name?

				PdfPCell cell5 = new PdfPCell();
				cell5.setBorder(Rectangle.NO_BORDER);
				cell5.enableBorderSide(Rectangle.LEFT);
				cell5.addElement(new Paragraph("Civil Id", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell6 = new PdfPCell();
				cell6.setBorder(Rectangle.NO_BORDER);
				cell6.addElement(new Paragraph(patient.getCivilId(), FontFactory.getFont(FontFactory.HELVETICA, 10f)));

				PdfPCell cell7 = new PdfPCell();
				cell7.setBorder(Rectangle.NO_BORDER);
				cell7.enableBorderSide(Rectangle.LEFT);
				cell7.addElement(new Paragraph("Invoice Date", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell8 = new PdfPCell();
				cell8.setBorder(Rectangle.NO_BORDER);
				cell8.enableBorderSide(Rectangle.RIGHT);
				cell8.addElement(new Paragraph(invoiceDate, FontFactory.getFont(FontFactory.HELVETICA, 10f)));


				PdfPCell cell9 = new PdfPCell();
				cell9.setBorder(Rectangle.NO_BORDER);
				cell9.enableBorderSide(Rectangle.LEFT);
				cell9.addElement(new Paragraph("Patient Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell10 = new PdfPCell();
				cell10.setBorder(Rectangle.NO_BORDER);
				cell10.addElement(new Paragraph(patient.getFirstName() + " " + patient.getLastName(), FontFactory.getFont(FontFactory.HELVETICA, 10f)));

				PdfPCell cell11 = new PdfPCell();
				cell11.setBorder(Rectangle.NO_BORDER);
				cell11.enableBorderSide(Rectangle.LEFT);
				cell11.addElement(new Paragraph("Invoice No", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell12 = new PdfPCell();
				cell12.setBorder(Rectangle.NO_BORDER);
				cell12.enableBorderSide(Rectangle.RIGHT);
				cell12.addElement(new Paragraph(invoice.getInvoiceNumber(), FontFactory.getFont(FontFactory.HELVETICA, 10f)));


				PdfPCell cell13 = new PdfPCell();
				cell13.setBorder(Rectangle.NO_BORDER);
				cell13.enableBorderSide(Rectangle.LEFT);
				cell13.addElement(new Paragraph("Patient Type", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell14 = new PdfPCell();
				cell14.setBorder(Rectangle.NO_BORDER);
				cell14.addElement(new Paragraph(patient.getPatientType(), FontFactory.getFont(FontFactory.HELVETICA, 10f)));

		/*PdfPCell cell5 = new PdfPCell();
		cell5.addElement(new Paragraph(""));

		PdfPCell cell6 = new PdfPCell();
		cell6.addElement(new Paragraph(""));*/

		/*PdfPCell cell7 = new PdfPCell();
		cell7.addElement(new Paragraph("Payment Mode"));

		PdfPCell cell8 = new PdfPCell();
		cell8.addElement(new Paragraph(paymentMode));*/

		/*PdfPCell cell5 = new PdfPCell();
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
		cell10.addElement(new Paragraph(amountPaid));*/

		/*PdfPCell cell15 = new PdfPCell();
		cell15.addElement(new Paragraph(""));

		PdfPCell cell16 = new PdfPCell();
		cell16.addElement(new Paragraph("kjhkjh"));*/

				PdfPCell cell15 = new PdfPCell();
				cell15.setBorder(Rectangle.NO_BORDER);
				cell15.enableBorderSide(Rectangle.LEFT);
				cell15.addElement(new Paragraph("Employer Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell16 = new PdfPCell();
				cell16.setBorder(Rectangle.NO_BORDER);
				cell16.enableBorderSide(Rectangle.RIGHT);
				cell16.addElement(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 10f))); // how to get employer name??

				PdfPCell cell17 = new PdfPCell();
				cell17.setBorder(Rectangle.NO_BORDER);
				cell17.enableBorderSide(Rectangle.LEFT);
				cell17.enableBorderSide(Rectangle.BOTTOM);
				cell17.addElement(new Paragraph("Age/Gender", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				PdfPCell cell18 = new PdfPCell();
				cell18.setBorder(Rectangle.NO_BORDER);
				cell18.enableBorderSide(Rectangle.BOTTOM);
				cell18.addElement(new Paragraph(patient.getAge() + "/" + patient.getGender(), FontFactory.getFont(FontFactory.HELVETICA, 10f)));

				PdfPCell cell19 = new PdfPCell();
				cell19.setBorder(Rectangle.NO_BORDER);
				cell19.enableBorderSide(Rectangle.BOTTOM);
				cell19.enableBorderSide(Rectangle.LEFT);
				cell19.addElement(new Paragraph(""));

				PdfPCell cell20 = new PdfPCell();
				cell20.setBorder(Rectangle.NO_BORDER);
				cell20.enableBorderSide(Rectangle.BOTTOM);
				cell20.enableBorderSide(Rectangle.RIGHT);
				cell20.addElement(new Paragraph(""));



				//MGM
		/*PdfPCell cell17 = new PdfPCell();
		cell17.addElement(new Paragraph("Reference ID", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
		PdfPCell cell18 = new PdfPCell();
		cell18.addElement(new Paragraph(invoicePayment.getReferenceId()));*/

		/*PdfPCell cell19 = new PdfPCell();
		cell19.addElement(new Paragraph("Payment ID", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
		PdfPCell cell20 = new PdfPCell();
		cell20.addElement(new Paragraph(invoice.getPaymentId()));*/

		/*PdfPCell cell19 = new PdfPCell();
		cell19.addElement(new Paragraph("Merchant TrackID", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
		PdfPCell cell20 = new PdfPCell();
		cell20.addElement(new Paragraph(invoicePayment.getMerchantTrackId()));*/


				headerInfo.addCell(cell1);
				headerInfo.addCell(cell2);
				headerInfo.addCell(cell3);
				headerInfo.addCell(cell4);
				headerInfo.addCell(cell5);
				headerInfo.addCell(cell6);
				headerInfo.addCell(cell7);
				headerInfo.addCell(cell8);
				headerInfo.addCell(cell9);
				headerInfo.addCell(cell10);
				headerInfo.addCell(cell11);
				headerInfo.addCell(cell12);
				headerInfo.addCell(cell13);
				headerInfo.addCell(cell14);
				headerInfo.addCell(cell15);
				headerInfo.addCell(cell16);

				headerInfo.addCell(cell17);
				headerInfo.addCell(cell18);
				headerInfo.addCell(cell19);
				headerInfo.addCell(cell20);
		/*table.addCell(cell17);
		table.addCell(cell18);
		table.addCell(cell19);
		table.addCell(cell20);
		table.addCell(cell21);
		table.addCell(cell22);
		table.addCell(cell23);
		table.addCell(cell24);*/
				//serviceInfo.setHeaderRows(1);
				PdfPCell cellOne = new PdfPCell();
				cellOne.addElement(new Paragraph("Service Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));
				PdfPCell cellTwo = new PdfPCell();

				Paragraph quantity = new Paragraph("Quantity", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				quantity.setAlignment(Element.ALIGN_CENTER);
				cellTwo.addElement(quantity);

				PdfPCell cellThree = new PdfPCell();
				Paragraph unitPrice = new Paragraph("Unit Price", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				unitPrice.setAlignment(Element.ALIGN_CENTER);
				//cellThree.setHorizontalAlignment(Element.ALIGN_MIDDLE);
				//cellThree.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cellThree.addElement(unitPrice);

				PdfPCell cellFour = new PdfPCell();
				Paragraph grossAmount = new Paragraph("Gross Amount", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				grossAmount.setAlignment(Element.ALIGN_CENTER);

				cellFour.addElement(grossAmount);

				PdfPCell cellFive = new PdfPCell();
				cellFive.addElement(new Paragraph(/*"PREMIUM CONSULTATION AFYA SMART SERVICE",*/ invoice.getInvoiceItems().get(0).
						getItemType().getDescription(),
						FontFactory.getFont(FontFactory.HELVETICA, 10f)));  // how to get service name??

				PdfPCell cellSix = new PdfPCell();
				//cellSix.setHorizontalAlignment(Element.ALIGN_CENTER);
				Paragraph fQuantity = new Paragraph(invoice.getInvoiceItems().get(0).getQuantity().toString(), FontFactory.
						getFont(FontFactory.HELVETICA, 10f));
				fQuantity.setAlignment(Element.ALIGN_CENTER);
				cellSix.addElement(fQuantity);   // how to get quantity??

				PdfPCell cellSeven = new PdfPCell();
				Paragraph fUnitPrice = new Paragraph("KD "+
						invoice.getInvoiceItems().get(0).getPrice().getAmount().toString(),
						FontFactory.getFont(FontFactory.HELVETICA, 10f));
				fUnitPrice.setAlignment(Element.ALIGN_RIGHT);
				cellSeven.addElement(fUnitPrice);   // how to get unit price??



				PdfPCell cellEight = new PdfPCell();
				Paragraph fGrossAmt = new Paragraph("KD "+invoice.getTotalAmount().getAmount().toString(), FontFactory.
						getFont(FontFactory.HELVETICA, 10f));
				fGrossAmt.setAlignment(Element.ALIGN_RIGHT);
				cellEight.addElement(fGrossAmt);   // how to get gross amount??

				PdfPCell cellNine = new PdfPCell();
				cellNine.setBorder(Rectangle.NO_BORDER);
				cellNine.enableBorderSide(Rectangle.LEFT); //BorderSide(Rectangle.RIGHT);
				//cellNine.disableBorderSide(Rectangle.BOTTOM);
				//cellNine.addElement(new Paragraph(""));
				PdfPCell cellTen = new PdfPCell();
				cellTen.setBorder(Rectangle.NO_BORDER);
				//cellTen.disableBorderSide(Rectangle.BOTTOM);
				cellTen.addElement(new Paragraph(""));

				PdfPCell cellEleven = new PdfPCell();
				Paragraph grossAmt = new Paragraph("Gross Amount", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				cellEleven.setFixedHeight(20f);
				//cellEleven.setHorizontalAlignment(Element.ALIGN_CENTER);
				//cellEleven.setVerticalAlignment(Element.ALIGN_MIDDLE);

				//cellEleven.setVerticalAlignment(Element.ALIGN_TOP);

				cellEleven.setPaddingBottom(3f);
				//cellEleven.setBorderWidthBottom(4f);
				//cellEleven.setBorderWidthBottom(5f);
				//cellEleven.setPaddingRight(10f);

				cellEleven.addElement(grossAmt);

				//cellEleven.setFixedHeight(20f);

				PdfPCell cellTwelve = new PdfPCell();
				Paragraph grossAmount2 = new Paragraph("KD "+invoice.getTotalAmount().getAmount().toString(), FontFactory.
						getFont(FontFactory.HELVETICA, 10f));
				grossAmount2.setAlignment(Element.ALIGN_RIGHT);
				cellTwelve.addElement(grossAmount2);   // how to get gross amount??*/

				PdfPCell cellThirteen = new PdfPCell();
				cellThirteen.setBorder(Rectangle.NO_BORDER);
				cellThirteen.enableBorderSide(Rectangle.LEFT);
				//cellThirteen.disableBorderSide(Rectangle.RIGHT);
				//cellThirteen.addElement(new Paragraph(""));
				PdfPCell cellFourteen = new PdfPCell();
				cellFourteen.setBorder(Rectangle.NO_BORDER);
				//cellFourteen.disableBorderSide(Rectangle.TOP);
				//cellFourteen.disableBorderSide(Rectangle.BOTTOM);
				cellFourteen.addElement(new Paragraph(""));

				PdfPCell cellFifteen = new PdfPCell();
				cellFifteen.addElement(new Paragraph("Discount", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));
				//cellFifteen.setFixedHeight(20f);

				PdfPCell cellSixteen = new PdfPCell();
				Paragraph discount = new Paragraph("",
						FontFactory.getFont(FontFactory.HELVETICA, 10f));
				discount.setAlignment(Element.ALIGN_RIGHT);
				cellSixteen.addElement(discount);  // how to get discount??

				PdfPCell cellSeventeen = new PdfPCell();
				cellSeventeen.setBorder(Rectangle.NO_BORDER);
				cellSeventeen.enableBorderSide(Rectangle.LEFT);
				cellSeventeen.enableBorderSide(Rectangle.BOTTOM);
				cellSeventeen.addElement(new Paragraph(""));
				PdfPCell cellEighteen = new PdfPCell();
				cellEighteen.setBorder(Rectangle.NO_BORDER);
				cellEighteen.enableBorderSide(Rectangle.BOTTOM);
				//cellEighteen.disableBorderSide(Rectangle.LEFT);
				cellEighteen.addElement(new Paragraph(""));



				PdfPCell cellNineteen = new PdfPCell();
				cellNineteen.addElement(new Paragraph("Net Amount", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));
				//cellNineteen.setFixedHeight(20f);

				PdfPCell cellTwenty = new PdfPCell();
				Paragraph netAmount = new Paragraph("KD "+invoice.getTotalAmount().getAmount().toString(),
						FontFactory.getFont(FontFactory.HELVETICA, 10f));
				netAmount.setAlignment(Element.ALIGN_RIGHT);
				cellTwenty.addElement(netAmount);  // how to get net amount??

				serviceInfo.addCell(cellOne);
				//serviceInfo.addCell(cellOne);
				serviceInfo.addCell(cellTwo);
				serviceInfo.addCell(cellThree);
				serviceInfo.addCell(cellFour);
				serviceInfo.addCell(cellFive);
				serviceInfo.addCell(cellSix);
				serviceInfo.addCell(cellSeven);
				serviceInfo.addCell(cellEight);
				serviceInfo.addCell(cellNine);
				serviceInfo.addCell(cellTen);
				serviceInfo.addCell(cellEleven);
				serviceInfo.addCell(cellTwelve);
				serviceInfo.addCell(cellThirteen);
				serviceInfo.addCell(cellFourteen);
				serviceInfo.addCell(cellFifteen);
				serviceInfo.addCell(cellSixteen);
				serviceInfo.addCell(cellSeventeen);
				serviceInfo.addCell(cellEighteen);
				serviceInfo.addCell(cellNineteen);
				serviceInfo.addCell(cellTwenty);

				PdfPCell lcellOne = new PdfPCell();
				Paragraph paymentType = new Paragraph("Payment Type/Mode", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				paymentType.setAlignment(Element.ALIGN_CENTER); ;
				lcellOne.addElement(paymentType);

				PdfPCell lcellTwo = new PdfPCell();
				Paragraph authNumber = new Paragraph("Cheque/CC - Auth. Number", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				authNumber.setAlignment(Element.ALIGN_CENTER);
				lcellTwo.addElement(authNumber);

				PdfPCell lcellThree = new PdfPCell();
				Paragraph lDate = new Paragraph("Date", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				lDate.setAlignment(Element.ALIGN_CENTER);
				lcellThree.addElement(lDate);

				PdfPCell lcellFour = new PdfPCell();
				Paragraph bankDetails = new Paragraph("Bank / Card Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				bankDetails.setAlignment(Element.ALIGN_CENTER);
				lcellFour.addElement(bankDetails);

				PdfPCell lcellFive = new PdfPCell();
				Paragraph amount = new Paragraph("Amount", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f));
				amount.setAlignment(Element.ALIGN_CENTER);
				lcellFive.addElement(amount);

				PdfPCell lcellSix = new PdfPCell();
				Paragraph fPaymentMode = new Paragraph(paymentMode, FontFactory.getFont(FontFactory.HELVETICA, 10f));
				fPaymentMode.setAlignment(Element.ALIGN_CENTER);
				lcellSix.addElement(fPaymentMode);    // ??

				PdfPCell lcellSeven = new PdfPCell();

				String referenceId = invoicePayment.getReferenceId() != null ? ", "+invoicePayment.getReferenceId() : "";
				String merchantTrackId = invoicePayment.getMerchantTrackId() != null ? ", "+invoicePayment.getMerchantTrackId() : "";
				String payId = invoicePayment.getPaymentId() != null ? invoicePayment.getPaymentId() : "";
				String transactionDet = payId + merchantTrackId + referenceId;

				Paragraph fAuthNumber = new Paragraph(transactionDet,
						FontFactory.getFont(FontFactory.HELVETICA, 10f));
				fAuthNumber.setAlignment(Element.ALIGN_CENTER);;
				lcellSeven.addElement(fAuthNumber);

				PdfPCell lcellEight = new PdfPCell();
				Paragraph fPaymentDt = new Paragraph(paymentDate, FontFactory.getFont(FontFactory.HELVETICA, 10f));
				fPaymentDt.setAlignment(Element.ALIGN_CENTER);
				lcellEight.addElement(fPaymentDt);

				PdfPCell lcellNine = new PdfPCell();
				Paragraph fBankDetails = new Paragraph(invoicePayment.getBankName(), FontFactory.getFont(FontFactory.HELVETICA, 10f));
				fBankDetails.setAlignment(Element.ALIGN_CENTER);
				lcellNine.addElement(fBankDetails);

				PdfPCell lcellTen = new PdfPCell();
				Paragraph fAmtPaid = new Paragraph("KD "+amountPaid, FontFactory.getFont(FontFactory.HELVETICA, 10f));
				fAmtPaid.setAlignment(Element.ALIGN_RIGHT);
				lcellTen.addElement(fAmtPaid);

				//document.add(new Paragraph("User: "));           // how to get user name??
				paymentDetails.addCell(lcellOne);
				paymentDetails.addCell(lcellTwo);
				paymentDetails.addCell(lcellThree);
				paymentDetails.addCell(lcellFour);
				paymentDetails.addCell(lcellFive);
				paymentDetails.addCell(lcellSix);
				paymentDetails.addCell(lcellSeven);
				paymentDetails.addCell(lcellEight);
				paymentDetails.addCell(lcellNine);
				paymentDetails.addCell(lcellTen);

				if (image != null){
					image.scaleAbsoluteHeight(75f);
					image.scaleAbsoluteWidth(75f);
					image.setAlignment(Image.ALIGN_LEFT);
					PdfPTable table = new PdfPTable(2);
					table.setWidthPercentage(100); //Width 100%
					table.setSpacingBefore(10f); //Space before table
					table.setSpacingAfter(10f);

					PdfPCell cellImg = new PdfPCell(image, false);
					cellImg.setBorder(Rectangle.NO_BORDER);
					cellImg.enableBorderSide(Rectangle.BOTTOM);

					//PdfPCell cellTxt1 = new PdfPCell();
					//cellTxt1.setBorder(Rectangle.NO_BORDER);
					//cellTxt1.addElement(new Paragraph(practiceName, FontFactory.getFont(FontFactory.HELVETICA, 13f)));     //???

					PdfPCell cellTxt = new PdfPCell();
					Paragraph clAddress = new Paragraph(clinicAddress, FontFactory.getFont(FontFactory.HELVETICA, 10f));
					//PdfPCell cellTxt = new PdfPCell();
					cellTxt.setBorder(Rectangle.NO_BORDER);
					cellTxt.enableBorderSide(Rectangle.BOTTOM);
					clAddress.setAlignment(Element.ALIGN_LEFT);
					cellTxt.addElement(clAddress);

					//Paragraph address = new Paragraph(clinicAddress, FontFactory.getFont(FontFactory.HELVETICA, 10f));
					//address.setAlignment(Element.ALIGN_LEFT);
					//cellTxt.addElement(address);

					//table.setWidthPercentage(50);
					table.setWidths(new int[]{1, 3});
					table.addCell(cellImg);
					table.addCell(cellTxt);
					document.add(table);

					//document.add(image);
				}
				//document.add(new Paragraph(clinicAddress));
				//document.add(new Paragraph(" "));
				document.add(receipt);
				//document.add(new Paragraph(" "));
				//document.add(heading);
				//document.add(new Paragraph(" "));
				document.add(headerInfo);
				document.add(serviceInfo);

				document.add(new Paragraph("Amount in words: " + currencyInWords, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));
				document.add(new Paragraph("Payment Details: ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10f)));

				document.add(paymentDetails);

				Paragraph info = new Paragraph("Powered by: Afya Arabia", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13.0f,
						com.lowagie.text.Font.UNDEFINED, new java.awt.Color(38, 171, 226)));
				info.setAlignment(Element.ALIGN_RIGHT);

				document.add(info);

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