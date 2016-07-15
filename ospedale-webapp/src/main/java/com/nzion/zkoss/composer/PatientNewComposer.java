package com.nzion.zkoss.composer;

import com.nzion.domain.Enumeration;
import com.nzion.domain.Patient;
import com.nzion.domain.Practice;
import com.nzion.domain.base.FieldRestriction;
import com.nzion.factory.PatientFactory;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.service.PatientService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.impl.FileBasedServiceImpl;
import com.nzion.util.*;
import com.nzion.view.PatientViewObject;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Span;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.InputElement;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

public class PatientNewComposer extends OspedaleAutowirableComposer {

    private static final long serialVersionUID = 1L;
    @Wire
    Window NewPatient;

    private PatientViewObject patientVO;
    private PatientService patientService;
    private CommonCrudRepository commonCrudRepository;
    private CommonCrudService commonCrudService;

    private FileBasedServiceImpl fileBasedServiceImpl;

    public void setCommonCrudRepository(CommonCrudRepository commonCrudRepository) {
        this.commonCrudRepository = commonCrudRepository;
    }

    public void onClick$Save(final Event evt) throws InterruptedException, IOException {
        System.out.println(patientVO.getPatient().getPatientType());
        if(commonCrudService == null){
            commonCrudService = Infrastructure.getSpringBean("commonCrudService");
        }
        Practice practice = commonCrudService.getAll(Practice.class)!= null ? commonCrudService.getAll(Practice.class).get(0):null;
        String newMobNo = patientVO.getPatient().getContacts().getMobileNumber();
        String mobNoWithISDCode = (newMobNo.length() > 8) ? newMobNo : "965"+newMobNo;
        patientVO.getPatient().getContacts().setMobileNumber(mobNoWithISDCode);
        patientVO.getPatient().setRegisteredFrom("LAB_MANUAL_ENTRY");
        String afyaId = RestServiceConsumer.checkIfPatientExistInPortalAndCreateIfNotExist(patientVO.getPatient(),practice != null? practice.getTenantId():null);
        if(afyaId != null) {
            patientVO.getPatient().setAfyaId(afyaId);
            if (UtilValidator.isEmpty(patientVO.getPatient().getPatientType())) {
                patientVO.getPatient().setPatientType("CASH PAYING");
            }
            final List<Patient> oldPatientList = commonCrudRepository.findByEquality(Patient.class, new String[]{"firstName",
                    "lastName", "dateOfBirth"}, new Object[]{patientVO.getPatient().getFirstName(),
                    patientVO.getPatient().getLastName(), patientVO.getPatient().getDateOfBirth()});
            if (oldPatientList.size() != 0) {
                patientVO.setPatient(oldPatientList.get(0));
                patientVO.setPropertiesToExistingPatient(oldPatientList.get(0), patientVO.getPatient());
            }
        /*final List<Patient> oldPatientList = commonCrudRepository.findByEquality(Patient.class, new String[] { "firstName",
                "lastName", "dateOfBirth" }, new Object[] { patientVO.getPatient().getFirstName(),
                patientVO.getPatient().getLastName(), patientVO.getPatient().getDateOfBirth() });
        if (oldPatientList.size() > 0) {
            UtilMessagesAndPopups.confirm("Already a Patient with same First Name,Last Name & Date Of Birth exists. Choose 'Yes' to create a new Patient.", "Information", Messagebox.YES | Messagebox.NO,
                    Messagebox.QUESTION, new EventListener() {
                        public void onEvent(Event event) {
                            if ("onYes".equals(event.getName()))
                                newPatientCreate(evt);
                            else if("onNo".equals(event.getName())){
                                populateExistingPatient(evt, oldPatientList);
                            }
                        }
                    });
        } else*/
            newPatientCreate(evt);
            UtilMessagesAndPopups.showSuccess();
        }
    }

    private void populateExistingPatient(Event evt, List<Patient> oldPatientList) {
        Patient existingPatient = oldPatientList.get(0);
        patientVO.setPatient(existingPatient);
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("entity", patientVO.getPatient());
        Executions.createComponents("/patient/patientView.zul", (Component) desktopScope.get("contentArea"), args);
        evt.getTarget().getFellow("NewPatient").detach();
    }

    private void newPatientCreate(Event evt){
        try {
            patientService.saveOrUpdate(patientVO);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        fileBasedServiceImpl.createDefaultFolderStructure(patientVO.getPatient());
        UtilMessagesAndPopups.displaySuccess();
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("entity", patientVO.getPatient());
        Executions.createComponents("/patient/patientView.zul", (Component) desktopScope.get("contentArea"), args);
        evt.getTarget().getFellow("NewPatient").detach();
    }

    public PatientViewObject getPatientVO() {
        return patientVO;
    }

    public void setPatientVO(PatientViewObject patientVO) {
        this.patientVO = patientVO;
    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public void doAfterCompose(Component component) throws Exception {
        super.doAfterCompose(component);
        if(commonCrudService == null)
            commonCrudService = Infrastructure.getSpringBean("commonCrudService");
        patientVO = PatientFactory.createPatientViewObject();
        if(patientVO.getPatient().getLanguage() == null){
            Enumeration enumEng = commonCrudService.getById(Enumeration.class,Long.valueOf("10303"));
            patientVO.getPatient().setLanguage(enumEng);
        }
        component.setAttribute("vo", patientVO);
     /*   String firstName = Executions.getCurrent().getParameter("fname");
        String lastName = Executions.getCurrent().getParameter("lname");
        String genderid = Executions.getCurrent().getParameter("genderid");
        String mrnum = Executions.getCurrent().getParameter("mrnum");
        String emailId = Executions.getCurrent().getParameter("emailId");
        String mobilePh = Executions.getCurrent().getParameter("mobilePh");
        String homePh = Executions.getCurrent().getParameter("homePh");
        patientVO.getPatient().setFirstName(firstName);
        patientVO.getPatient().setLastName(lastName);
        patientVO.getPatient().getContacts().setEmail(emailId);
        patientVO.getPatient().getContacts().setMobileNumber(mobilePh);
        patientVO.getPatient().getContacts().setHomePhone(homePh);
        if (UtilValidator.isNotEmpty(genderid)) {
            try {
                long genderIdentifier = Long.parseLong(genderid);
                Enumeration gender = ((CommonCrudRepository) Infrastructure.getSpringBean("commonCrudRepository"))
                        .findByPrimaryKey(Enumeration.class, genderIdentifier);
                patientVO.getPatient().setGender(gender);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } */
        setConstraints(component);
    }

    private void setConstraints(Component component) {
        List<FieldRestriction> mandatoryRestrictions = commonCrudRepository.findByEquality(FieldRestriction.class, new String[] { "entityName", "restrictionType"},
                new Object[] {com.nzion.domain.Patient.class.getSimpleName(), FieldRestriction.RESTRICTION_TYPE.MANDATORY});
        for (FieldRestriction restriction : mandatoryRestrictions) {
            if ("true".equalsIgnoreCase(restriction.getValue())) {
                System.out.println(restriction.getValue() + "------------------" + restriction.getField() + "\n\n\n\n\n\n\n\n");
                String componentName = UtilDisplay.uiStringToCamelCase(restriction.getDisplayName());
                Component comp = component.getFellowIfAny(componentName, true);
                if (comp instanceof InputElement) {
                    ((InputElement) comp).setConstraint(new SimpleConstraint(SimpleConstraint.NO_EMPTY));
                    Span mandatoryMarker = (Span) component.getFellowIfAny(Constants.LABEL_PREFIX + componentName + "Req", true);
                    mandatoryMarker.setVisible(true);
                }
            }
        }
    }

    public FileBasedServiceImpl getFileBasedServiceImpl() {
        return fileBasedServiceImpl;
    }

    @Resource
    public void setFileBasedServiceImpl(FileBasedServiceImpl fileBasedServiceImpl) {
        this.fileBasedServiceImpl = fileBasedServiceImpl;
    }

    public List<String> getBloodGroup(){
        List<String> bloodGroups = new ArrayList<String>(){{
            add("A");
            add("B");
            add("AB");
            add("O");
        }};
        return bloodGroups;
    }

    public List<String> getRh(){
        List<String> rhs = new ArrayList<String>(){{
            add("+");
            add("-");
        }};
        return rhs;
    }
}