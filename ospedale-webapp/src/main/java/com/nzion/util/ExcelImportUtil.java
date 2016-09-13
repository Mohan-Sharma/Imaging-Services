package com.nzion.util;

import com.nzion.domain.emr.lab.LabTest;
import com.nzion.domain.emr.lab.LabTestPanel;
import com.nzion.domain.emr.lab.LabTestProfile;
import com.nzion.repository.billing.impl.HibernateBillingRepository;
import com.nzion.service.common.CommonCrudService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.classic.Session;
import org.zkoss.zhtml.Filedownload;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Nthdimenzion on 26-Aug-16.
 */
public class ExcelImportUtil {

    public static List<Map<String, Object>> serviceMasterList = RestServiceConsumer.getAllServiceMaster();

    public static CommonCrudService commonCrudService = Infrastructure.getSpringBean("commonCrudService");
    public static HibernateBillingRepository hibernateBillingRepository = Infrastructure.getSpringBean("billingRepository");

    public static void uploadExcel(InputStream inputStream) throws Exception {
        try {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            int header = 0;
            String query = "INSERT into lab_tariff (SERVICE_MAIN_GROUP,SERVICE_SUB_GROUP,INS_SERVICE_ID,TARIF_CATEGORY,PATIENT_CATEGORY,LABORATORY_SHARE,DOCTOR_SHARE,TECHNICIAN_SHARE,TEST_COST,MARKUP_AMOUNT,LOCATION_ID,FROM_DATE,THRU_DATE,home_service,LAB_TEST,LAB_PANEL,LAB_PROFILE)" +
                    " VALUES ";

            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                if (header == 0) {
                    header = 1;
                    continue;
                }

                if (query.charAt(query.length() - 1) == ')') {
                    query = query + ",";
                }

                for (int i = 3; i >= 0; i--) {
                    if ((i == 2) || (i == 1)){
                        continue;
                    }

                    Cell cell = nextRow.getCell(i);
                    if ((i == 3) && ((cell == null) || (cell.getNumericCellValue() == 0))){
                        break;
                    }
                    //query = query + "('07','009','262',";
                    switch (i) {
                        case 3:
                            query = query + "('07','009','262','01','01',125.00,100.00,30.00,255.00,50.00,10001,'2016-01-01','2018-12-31',";
                            if (query.charAt(query.length() - 1) == ')') {
                                query = query + ",";
                            }
                            if (cell != null) {
                                BigDecimal billableAmount = new BigDecimal(cell.getNumericCellValue()).setScale(3, RoundingMode.HALF_UP);
                                query = query + billableAmount + ",";
                                break;
                            }
                            break;
                        case 0:
                            if (cell != null) {
                                String type = cell.getStringCellValue();
                                if (type.equals("Radiology Test")){
                                    Cell cell1 = nextRow.getCell(1);
                                    if (cell1.getCellType() == Cell.CELL_TYPE_STRING){
                                        query = query + "'" + cell1.getStringCellValue() + "'," + null + "," + null + ")";
                                    } else if (cell1.getCellType() == Cell.CELL_TYPE_NUMERIC){
                                        query = query + "'" + cell1.getNumericCellValue() + "'," + null + "," + null + ")";
                                    }
                                } else if (type.equals("Health Package")){
                                    query = query + null + ",";
                                    Cell cell1 = nextRow.getCell(1);
                                    if (cell1.getCellType() == Cell.CELL_TYPE_STRING){
                                        query = query + "'" + cell1.getStringCellValue() + "'," + null + ")";
                                    } else if (cell1.getCellType() == Cell.CELL_TYPE_NUMERIC){
                                        query = query + "'" + cell1.getNumericCellValue() + "'," + null + ")";
                                    }
                                } else if (type.equals("Profile")){
                                    query = query + null + "," + null + ",";
                                    Cell cell1 = nextRow.getCell(1);
                                    if (cell1.getCellType() == Cell.CELL_TYPE_STRING){
                                        query = query + "'" + cell1.getStringCellValue() + "')";
                                    } else if (cell1.getCellType() == Cell.CELL_TYPE_NUMERIC){
                                        query = query + "'" + cell1.getNumericCellValue() + "')";
                                    }
                                }
                            }
                            break;
                    }
                }
            }
            if(query.charAt(query.length()-1) == ',') {
                query = query.substring(0, query.length() - 1);
            }
            Session session = Infrastructure.getSessionFactory().openSession();
            session.createSQLQuery(query).executeUpdate();
            session.close();
            inputStream.close();
        } catch (Exception e) {
            UtilMessagesAndPopups.showError("Upload Failed");
            e.printStackTrace();
        }
        UtilMessagesAndPopups.showSuccess("Upload Successful");
    }

    public static String getServiceId(String name) {
        Iterator iterator = serviceMasterList.iterator();
        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();
            if (map.get("SERVICE_NAME").toString().equals(name)) {
                return map.get("ID").toString();
            }
        }
        return null;
    }

    public static String getServiceName(String id) {
        Iterator iterator = serviceMasterList.iterator();
        while (iterator.hasNext()) {
            Map map = (Map) iterator.next();
            if (map.get("ID").toString().substring(0, map.get("ID").toString().indexOf('.')).equals(id)) {
                return map.get("SERVICE_NAME").toString();
            }
        }
        return null;
    }

    public static void exportClinicTariff() throws Exception {
        String d = "";
        try {
			/*List<Cpt> cptList = commonCrudService.getAll(Cpt.class);
			List<SlotType> slotTypeList = commonCrudService.getAll(SlotType.class);
			List<Provider> providerList = commonCrudService.getAll(Provider.class);
			List<TariffCategory> tariffCategories = commonCrudService.getAll(TariffCategory.class);*/

			/*String[] columnNameArr = new String[]{"Procedure", "Visit Type", "Doctor", "Tariff Category", "Service Cost", "Billable Min Amount", "Billable amount", "Billable Max amount",
					"From Date \nyyyy-mm-dd", "Thru Date \nyyyy-mm-dd", "Service ID"};*/

            String[] columnNameArr = new String[]{"Type","Code","Display Name","Afya Amount"};

            XSSFWorkbook hssfWorkbook = ExcelHelper.createXSSFWorkbook();

            XSSFSheet realSheet = hssfWorkbook.createSheet("imaging tariff");
            createNamedRowWithCell(columnNameArr, realSheet);

            com.nzion.zkoss.ext.DataExporter exporter = new com.nzion.zkoss.ext.CsvDataExporter();
            com.nzion.service.utility.UtilityFinder utilityFinder = Infrastructure.getSpringBean("utilityFinder");
            List<Map<String, Object>> mapList = utilityFinder.getAllLabTariff();

            List<LabTest> labTests = commonCrudService.findByEquality(LabTest.class, new String[]{"active"}, new Object[]{Boolean.TRUE});
            List<LabTestPanel> labTestPanels = commonCrudService.findByEquality(LabTestPanel.class, new String[]{"active"}, new Object[]{Boolean.TRUE});
            List<LabTestProfile> labTestProfiles = commonCrudService.findByEquality(LabTestProfile.class, new String[]{"active"}, new Object[]{Boolean.TRUE});

            Map<String, LabTest> labTestMap = new HashMap<>();
            for (LabTest labTest : labTests){
                labTestMap.put(labTest.getTestCode(), labTest);
            }

            Map<String, LabTestPanel> testPanelMap = new HashMap<>();
            for (LabTestPanel labTest : labTestPanels){
                testPanelMap.put(labTest.getPanelCode(), labTest);
            }

            Map<String, LabTestProfile> testProfileMap = new HashMap<>();
            for (LabTestProfile labTest : labTestProfiles){
                testProfileMap.put(labTest.getProfileCode(), labTest);
            }

            int rownum = 1;
            for (Map map : mapList) {
                String desc = null;
                Row row = null;
                if (map.get("LAB_TEST") != null) {
                    desc = labTestMap.get(map.get("LAB_TEST")) != null ? labTestMap.get(map.get("LAB_TEST").toString()).getTestPneumonic() : null;
                } else if (map.get("LAB_PANEL") != null) {
                    desc = testPanelMap.get(map.get("LAB_PANEL")) != null ? testPanelMap.get(map.get("LAB_PANEL").toString()).getPanelPneumonic() : null;
                } else if (map.get("LAB_PROFILE") != null) {
                    desc = testProfileMap.get(map.get("LAB_PROFILE")) != null ? testProfileMap.get(map.get("LAB_PROFILE").toString()).getProfileNeumonic() : null;
                }
                if (desc != null) {
                    row = realSheet.createRow(rownum++);
                } else {
                    continue;
                }
                for (int i = 0; i <= 2; i++) {
                    //Cell cell = row.createCell(i);
                    switch (i) {
                        case 0:
                            String type = null;
                            if (map.get("LAB_TEST") != null) {
                                type = "Radiology Test";
                            } else if (map.get("LAB_PANEL") != null) {
                                type = "Health Package";
                            } else if (map.get("LAB_PROFILE") != null) {
                                type = "Profile";
                            }
                            ExcelHelper.createStringCellWithXssf(0, UtilValidator.isNotEmpty(type) ? type : "", row, hssfWorkbook);
                            break;
                        case 1:
                            String code = null;
                            String name = null;
                            if (map.get("LAB_TEST") != null) {
                                code = map.get("LAB_TEST").toString();
                                d = map.get("LAB_TEST").toString();
                                name = labTestMap.get(map.get("LAB_TEST").toString()).getTestPneumonic();
                                if ((name != null) && (name != "")) {
                                    ExcelHelper.createStringCellWithXssf(1, UtilValidator.isNotEmpty(code) ? code : "", row, hssfWorkbook);
                                    ExcelHelper.createStringCellWithXssf(2, UtilValidator.isNotEmpty(name) ? name : "", row, hssfWorkbook);
                                    labTestMap.remove(map.get("LAB_TEST").toString());
                                }
                            } else if (map.get("LAB_PANEL") != null) {
                                code = map.get("LAB_PANEL").toString();
                                name = testPanelMap.get(map.get("LAB_PANEL").toString()).getPanelPneumonic();
                                if ((name != null) && (name != "")) {
                                    ExcelHelper.createStringCellWithXssf(1, UtilValidator.isNotEmpty(code) ? code : "", row, hssfWorkbook);
                                    ExcelHelper.createStringCellWithXssf(2, UtilValidator.isNotEmpty(name) ? name : "", row, hssfWorkbook);
                                    testPanelMap.remove(map.get("LAB_PANEL").toString());
                                }
                            } else if (map.get("LAB_PROFILE") != null) {
                                code = map.get("LAB_PROFILE").toString();
                                name = testProfileMap.get(map.get("LAB_PROFILE").toString()).getProfileNeumonic();
                                if ((name != null) && (name != "")) {
                                    ExcelHelper.createStringCellWithXssf(1, UtilValidator.isNotEmpty(code) ? code : "", row, hssfWorkbook);
                                    ExcelHelper.createStringCellWithXssf(2, UtilValidator.isNotEmpty(name) ? name : "", row, hssfWorkbook);
                                    testProfileMap.remove(map.get("LAB_PROFILE").toString());
                                }
                            }
                            break;
                        case 2:
                            BigDecimal billableAmount = null;
                            if (map.get("home_service") != null) {
                                billableAmount = (BigDecimal) map.get("home_service");
                            }
                            ExcelHelper.createNumberCellWithXssf(3, UtilValidator.isNotEmpty(billableAmount) ? billableAmount : null, row, hssfWorkbook);
                            break;
                    }
                }
            }

            Iterator iterator = labTestMap.keySet().iterator();
            while (iterator.hasNext()){
                String labTest = (String)iterator.next();
                Row row = realSheet.createRow(rownum++);
                //Cell cell = row.createCell(0);
                String name = labTestMap.get(labTest).getTestPneumonic();
                String type = "Radiology Test";
                String code = labTestMap.get(labTest).getTestCode();
                if ((labTest != null) && (labTest != "")) {
                    ExcelHelper.createStringCellWithXssf(0, UtilValidator.isNotEmpty(type) ? type : "", row, hssfWorkbook);
                    ExcelHelper.createStringCellWithXssf(1, UtilValidator.isNotEmpty(code) ? code : "", row, hssfWorkbook);
                    ExcelHelper.createStringCellWithXssf(2, UtilValidator.isNotEmpty(name) ? name : "", row, hssfWorkbook);
                }
            }

            Iterator iterator1 = testPanelMap.keySet().iterator();
            while (iterator1.hasNext()){
                String labTestPanel = (String)iterator1.next();
                Row row = realSheet.createRow(rownum++);
                //Cell cell = row.createCell(1);
                String name = testPanelMap.get(labTestPanel).getPanelPneumonic();
                String type = "Health Package";
                String code = testPanelMap.get(labTestPanel).getPanelCode();
                if ((labTestPanel != null) && (labTestPanel != "")) {
                    ExcelHelper.createStringCellWithXssf(0, UtilValidator.isNotEmpty(type) ? type : "", row, hssfWorkbook);
                    ExcelHelper.createStringCellWithXssf(1, UtilValidator.isNotEmpty(code) ? code : "", row, hssfWorkbook);
                    ExcelHelper.createStringCellWithXssf(2, UtilValidator.isNotEmpty(name) ? name : "", row, hssfWorkbook);
                }
            }

            Iterator iterator2 = testProfileMap.keySet().iterator();
            while (iterator2.hasNext()){
                String labTestProfile = (String)iterator2.next();
                Row row = realSheet.createRow(rownum++);
                //Cell cell = row.createCell(2);
                String name = testProfileMap.get(labTestProfile).getProfileNeumonic();
                String type = "Profile";
                String code = testProfileMap.get(labTestProfile).getProfileCode();
                if ((labTestProfile != null) && (labTestProfile != "")) {
                    ExcelHelper.createStringCellWithXssf(0, UtilValidator.isNotEmpty(type) ? type : "", row, hssfWorkbook);
                    ExcelHelper.createStringCellWithXssf(1, UtilValidator.isNotEmpty(code) ? code : "", row, hssfWorkbook);
                    ExcelHelper.createStringCellWithXssf(2, UtilValidator.isNotEmpty(name) ? name : "", row, hssfWorkbook);
                }
            }

            ByteArrayOutputStream fos = new ByteArrayOutputStream();
            hssfWorkbook.write(fos);
            fos.close();
            Filedownload.save(fos.toByteArray(), "application/xlsx", "imaging tariff");
        } catch (Exception e){
            System.out.println("#####################"+d+"####################");
            e.printStackTrace();
            UtilMessagesAndPopups.showError("Failed to export imaging tariff");
        }
    }

    public static void truncateClinicTariff(){
        UtilMessagesAndPopups.showConfirmation("Are You Sure you want to delete the rates", new EventListener() {
            @Override
            public void onEvent(Event evt) throws Exception {
                if ("onYes".equalsIgnoreCase(evt.getName())) {
					/*try {
						Session session = Infrastructure.getSessionFactory().openSession();
						session.createSQLQuery("truncate table clinic_tariff").executeUpdate();
						session.close();
					} catch (Exception e){
						e.printStackTrace();
					}*/
                    UtilMessagesAndPopups.showConfirmation("Once you click on yes all the lab rates would be deleted. Do you want to proceed ?", new EventListener() {
                        @Override
                        public void onEvent(Event evt) throws Exception {
                            if ("onYes".equalsIgnoreCase(evt.getName())) {
                                try {
                                    Session session = Infrastructure.getSessionFactory().openSession();
                                    session.createSQLQuery("truncate table lab_tariff").executeUpdate();
                                    UtilMessagesAndPopups.showSuccess("Imaging Tariff truncated successfully");
                                    session.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if ("onNo".equalsIgnoreCase(evt.getName()))
                                return;
                        }
                    });
                }
                if ("onNo".equalsIgnoreCase(evt.getName()))
                    return;
            }
        });

    }
    private static void createNamedRowWithCell(String[] headerName, XSSFSheet dataSheet) {
        XSSFRow row = dataSheet.createRow(0);
        for (int count = 0; count < headerName.length; count++) {
            String name = headerName[count];
            XSSFCell cell = row.createCell(count);
            cell.setCellValue(name);
        }
    }
}
