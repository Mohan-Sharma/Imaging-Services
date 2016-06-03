package com.nzion.zkoss.composer;

import com.nzion.domain.Enumeration;
import com.nzion.domain.Patient;
import com.nzion.domain.Practice;
import com.nzion.domain.UserLogin;
import com.nzion.domain.base.FieldRestriction;
import com.nzion.repository.common.CommonCrudRepository;
import com.nzion.security.SecurityGroup;
import com.nzion.service.PatientService;
import com.nzion.service.UserLoginService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.*;
import com.nzion.view.RolesValueObject;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Image;
import org.zkoss.zul.SimpleConstraint;
import org.zkoss.zul.Span;
import org.zkoss.zul.impl.InputElement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PatientViewComposer extends OspedaleAutowirableComposer {

	private Patient patient;
	private PatientService patientService;
	private CommonCrudService commonCrudService;
	private CommonCrudRepository commonCrudRepository;
	private Long id;
	private AImage profilePicture;
	
	private String eventMessage;

    private String userName;

    private String password;

    private String emailId;

    private UserLoginService userLoginService;

    private RolesValueObject rolesVo;

    private Set<SecurityGroup> securityGroups = new HashSet<SecurityGroup>();
	
	
	public void setCommonCrudRepository(CommonCrudRepository commonCrudRepository) {
	this.commonCrudRepository = commonCrudRepository;
	}

	public AImage getProfilePicture() {
	return profilePicture;
	}

	public void setId(Long id) {
	this.id = id;
	}

	public Patient getPatient() {
	return patient;
	}

	public PatientService getPatientService() {
	return patientService;
	}

	public void setPatient(Patient patient) {
	this.patient = patient;
	}

	public void setPatientService(PatientService patientService) {
	this.patientService = patientService;
	}

	@Override
	public void doAfterCompose(Component component) throws Exception {
	super.doAfterCompose(component);
	setConstraints(component);
	if (id != null) {
		patient = commonCrudService.getById(Patient.class, id);
		if (patient.getProfilePicture() != null) {
			profilePicture = new org.zkoss.image.AImage("Front Image", patient.getProfilePicture().getResource().getBinaryStream());
			((Image)root.getFellow("frontImage")).setContent(profilePicture);
		}
	}
	}

	public Patient updatePatient(Event evt) {
	try {
		Enumeration language = patient.getLanguage();
		Practice practice = commonCrudService.getAll(Practice.class) != null? commonCrudService.getAll(Practice.class).get(0):null;
		String afyaId = RestServiceConsumer.checkIfPatientExistInPortalAndCreateIfNotExist(patient,practice != null? practice.getTenantId(): null);
		if(afyaId != null){
			patient.setAfyaId(afyaId);
			patientService.saveOrUpdate(patient,eventMessage);
		}
	} catch (Throwable t) {
		t.printStackTrace();
		UtilMessagesAndPopups.displayError(t.getMessage());
	}
	UtilMessagesAndPopups.showSuccess();
	return patient;
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

    public void createOrUpdateUser() {
        UserLogin userLogin = new UserLogin();
        if(UtilValidator.isEmpty(this.patient.getContacts().getEmail())){
            this.patient.getContacts().setEmail(this.emailId);
            commonCrudService.save(this.patient);
        }
        userLogin.setPerson(this.patient);
        userLogin.setUsername(this.userName);
        userLogin.setPassword(this.password);
        rolesVo = new RolesValueObject(userLogin.getAuthorization());
        if (userLoginExists(userLogin.getUsername())) {
            UtilMessagesAndPopups.showError("User Name already exists");
            return;
        }

        userLogin.getPerson().setLocations(patient.getLocations());
        rolesVo.setPatient(true);
        userLogin.setGrantedSecurityPermissionGroups(securityGroups);
        userLogin = userLoginService.createUserLoginForPatient(userLogin);
    }

    public boolean userLoginExists(String userName) {
        try {
            userLoginService.getUserByUsername(userName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

	private static final long serialVersionUID = 6362448991641314504L;

	public void setCommonCrudService(CommonCrudService commonCrudService) {
	this.commonCrudService = commonCrudService;
	}
	
	public String getEventMessage() {
	return eventMessage;
	}

	public void setEventMessage(String eventMessage) {
	this.eventMessage = eventMessage;
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public RolesValueObject getRolesVo() {
        return rolesVo;
    }

    public void setRolesVo(RolesValueObject rolesVo) {
        this.rolesVo = rolesVo;
    }

    public Set<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(Set<SecurityGroup> securityGroups) {
        this.securityGroups = securityGroups;
    }

}