package com.nzion.zkoss.composer;

import com.nzion.domain.Location;
import com.nzion.domain.Practice;
import com.nzion.domain.Roles;
import com.nzion.domain.UserLogin;
import com.nzion.domain.emr.lab.LabDepartment;
import com.nzion.domain.emr.lab.Laboratories;
import com.nzion.security.SecurityGroup;
import com.nzion.service.UserLoginService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.impl.EncryptionService;
import com.nzion.util.Infrastructure;
import com.nzion.util.PortalRestServiceConsumer;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import com.nzion.view.RolesValueObject;
import com.nzion.view.component.LookupBox;
import com.nzion.zkoss.dto.UserLoginDto;
import com.nzion.zkoss.ext.Navigation;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserLoginController extends AutowirableComposer {

    private UserLoginService userLoginService;

    private EncryptionService encryptionService;

    private Set<Location> locations = new HashSet<Location>();
    
    private Set<LabDepartment> labDepartments =  new HashSet<LabDepartment>();
    
    private Set<Laboratories> laboratories = new HashSet<Laboratories>();

    private Set<SecurityGroup> securityGroups = new HashSet<SecurityGroup>();

    private UserLogin userLogin;

    private RolesValueObject rolesVo;

    private String oldPassword;

    private String newPassword;

    private String newPassword2;

    private String secretQuestion;

    private String secretAnswer;

    private CommonCrudService commonCrudService;

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPassword2() {
        return newPassword2;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setNewPassword2(String newPassword2) {
        this.newPassword2 = newPassword2;
    }


    public UserLoginController() {
        userLogin = (UserLogin) Executions.getCurrent().getArg().get("entity");
        if (userLogin != null) {
            commonCrudService.refreshEntity(userLogin);
            locations.addAll(userLogin.getLocations());
            securityGroups.addAll(userLogin.getGrantedSecurityPermissionGroups());
            Collection<LabDepartment> labDepartmentlist = userLogin.getLabDepartments();
            labDepartments.addAll(labDepartmentlist);
           Collection<Laboratories> laboratorieslist = userLogin.getLaboratories();
            laboratories.addAll(laboratorieslist);
        }
        if (userLogin == null)
            userLogin = new UserLogin();
        rolesVo = new RolesValueObject(userLogin.getAuthorization());
    }

    public RolesValueObject getRolesVo() {
        return rolesVo;
    }

    public void save() {

        if (UtilValidator.isEmpty(userLogin.getUsername())) {
            UtilMessagesAndPopups.showError("Please enter userName");
            return;
        }
        if (userLogin.getPerson() == null) {
            UtilMessagesAndPopups.showError("Please select employee");
            return;
        }
        if (UtilValidator.isEmpty(locations)) {
            UtilMessagesAndPopups.showError("Please select atleast one location");
            return;
        }
        if (userLogin.getAuthorization().getRoles() == 0) {
            UtilMessagesAndPopups.showError("Please select atleast one role");
            return;
        }

        if (userLogin.getId() == null && userLogin.getPerson().getId() != null && userLoginService.getUserLogin(getUserLogin().getPerson()) != null) {
            UtilMessagesAndPopups.showError("User Login already exist for this user");
            return;
        }

        if (userLogin.getId() == null) {
            /*if (userLoginExists(userLogin.getUsername())) {
                UtilMessagesAndPopups.showError("User Name already exists");
                return;
            }
            createUser();
            Navigation.navigate("userLogin", null, "contentArea");*/
            //Map<String, Object> userLoginFromPortal = PortalRestServiceConsumer.getUserLoginDetailsForUserName(userLogin.getUsername());
            if (userLoginExists(userLogin.getUsername())) {
                UtilMessagesAndPopups.showError("User Name already exists");
                return;
            }
            if(saveUserLoginInPortal(userLogin, null)) {
                createUser();
                Navigation.navigate("userLogin", null, "contentArea");
            } else{
                UtilMessagesAndPopups.showError("User Login cannot be created");
            }
        } else {
            /*userLogin.getLocations().clear();
            userLogin.getLocations().addAll(locations);
            userLogin.getGrantedSecurityPermissionGroups().clear();
            userLogin.setGrantedSecurityPermissionGroups(securityGroups);
            
            if(userLogin.hasRole(Roles.PHLEBOTOMIST) || userLogin.hasRole(Roles.TECHNICIAN)){
            	if(UtilValidator.isEmpty(labDepartments)){
            		 UtilMessagesAndPopups.showError("Please select atleast one lab department");
                     return;
            	}    
            }

            if(userLogin.hasRole(Roles.PHLEBOTOMIST) || userLogin.hasRole(Roles.TECHNICIAN)){
            	if(UtilValidator.isEmpty(laboratories)){
            		 UtilMessagesAndPopups.showError("Please select atleast one laboratories");
                     return;
            	}    
            }
            
            userLogin.setLabDepartments(labDepartments)	;
            userLogin.setLaboratories(laboratories);
           
            userLoginService.save(userLogin);
            
            
            Navigation.navigate("userLogin", null, "contentArea");*/

            if(saveUserLoginInPortal(userLogin, null)) {
                userLogin.getLocations().clear();
                userLogin.getLocations().addAll(locations);
                userLogin.getGrantedSecurityPermissionGroups().clear();
                userLogin.setGrantedSecurityPermissionGroups(securityGroups);

                if(userLogin.hasRole(Roles.PHLEBOTOMIST) || userLogin.hasRole(Roles.TECHNICIAN)){
                    if(UtilValidator.isEmpty(labDepartments)){
                        UtilMessagesAndPopups.showError("Please select atleast one lab department");
                        return;
                    }
                }

                if(userLogin.hasRole(Roles.PHLEBOTOMIST) || userLogin.hasRole(Roles.TECHNICIAN)){
                    if(UtilValidator.isEmpty(laboratories)){
                        UtilMessagesAndPopups.showError("Please select atleast one laboratories");
                        return;
                    }
                }

                userLogin.setLabDepartments(labDepartments)	;
                userLogin.setLaboratories(laboratories);

                userLoginService.save(userLogin);
                Navigation.navigate("userLogin", null, "contentArea");
            } else{
                UtilMessagesAndPopups.showError("User Login cannot be created");
            }
        }
        // root.detach();
        UtilMessagesAndPopups.showSuccess();
    }

    public void createUser() {

        userLogin.getPerson().setLocations(locations);
        userLogin.setGrantedSecurityPermissionGroups(securityGroups);
        userLogin.setLabDepartments(labDepartments)	;
        userLogin.setLaboratories(laboratories);
       
        userLogin = userLoginService.createUserLogin(userLogin);
    }

    public UserLogin getUserLogin() {
        return userLogin;
    }

    public boolean userLoginExists(String userName) {
        try {
            userLoginService.getUserByUsername(userName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public LookupBox.Evaluator getPersonEvaluator() {
        return new LookupBox.Evaluator() {
            public List<?> doLookup(Class<?> klass, Map<String, Object> searchData) {
                return userLoginService.relevantPersonLookup(searchData);
            }
        };
    }

    public void setUserLoginService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }
    
     public Set<LabDepartment> getLabDepartments() {
		return labDepartments;
	}

	public void setLabDepartments(Set<LabDepartment> labDepartments) {
		this.labDepartments = labDepartments;
	}

	public Set<Laboratories> getLaboratories() {
		return laboratories;
	}

	public void setLaboratories(Set<Laboratories> laboratories) {
		this.laboratories = laboratories;
	}

	public void initializeUserLogin(Event event) {
        UserLogin userLogin = Infrastructure.getUserLogin();
        String password = userLogin.getPassword();
        Component comp = event.getTarget();
        oldPassword = oldPassword == null ? "" : oldPassword;
        newPassword = newPassword == null ? "" : newPassword;
        newPassword2 = newPassword2 == null ? "" : newPassword2;
        if (oldPassword.equals(newPassword))
            throw new WrongValueException(comp.getFellowIfAny("oldPassword", true), "Old Password And New Password Cannot Be Same");
        if (UtilValidator.isEmpty(newPassword))
            throw new WrongValueException(comp.getFellowIfAny("newPassword", true), "Cannot Be Empty");
        if (!newPassword.equals(newPassword2)) {
            throw new WrongValueException(comp.getFellowIfAny("newPassword2", true), "Password and confirm password do not match.");
        }
        if (!oldPassword.equals(password)) {
            throw new WrongValueException(comp.getFellowIfAny("oldPassword", true), "Password mismatch.");
        }
        if (UtilValidator.isEmpty(userLogin.getSecretQuestion()))
            throw new WrongValueException(comp.getFellowIfAny("setQuestion", true), "Cannot Be Empty");

        if (UtilValidator.isEmpty(userLogin.getSecretQuestionAnswer()))
            throw new WrongValueException(comp.getFellowIfAny("setAnswer", true), "Cannot Be Empty.");

        userLoginService.changePassword(userLogin, newPassword);
        Executions.sendRedirect("/logout");
    }

    public void changePassword(Event event) {
        UserLogin userLogin = Infrastructure.getUserLogin();
        String password = userLogin.getPassword();
        Component comp = event.getTarget();
        oldPassword = oldPassword == null ? "" : oldPassword;
        if (oldPassword.equals(newPassword))
            throw new WrongValueException(comp.getFellowIfAny("oldPassword", true), "Old Password And New Password Cannot Be Same");
        if (UtilValidator.isEmpty(newPassword))
            throw new WrongValueException(comp.getFellowIfAny("newPassword", true), "Cannot Be Empty");

        if (!newPassword.equals(newPassword2)) {
            throw new WrongValueException(comp.getFellowIfAny("newPassword2", true), "Password and confirm password do not match.");
        }
        if (!oldPassword.equals(password)) {
            throw new WrongValueException(comp.getFellowIfAny("oldPassword", true), "Password mismatch.");
        }
        userLoginService.changePassword(userLogin, newPassword);
    }

    private static final long serialVersionUID = 1L;


    public Set<SecurityGroup> getSecurityGroups() {
        return securityGroups;
    }

    public void setSecurityGroups(Set<SecurityGroup> securityGroups) {
        this.securityGroups = securityGroups;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    public void addOrRemoveSecurityGroups(SecurityGroup securityGroup, boolean add) {
        if (add)
            securityGroups.add(securityGroup);
        else
            securityGroups.remove(securityGroup);
    }

    public void addOrRemoveLocation(Location location, boolean add) {
        if (add)
            locations.add(location);
        else
            locations.remove(location);
    }

    public void addOrRemoveLabDepartment(LabDepartment  labDepartment, boolean add){
		if(add)
			labDepartments.add(labDepartment);
		else
			labDepartments.remove(labDepartment);
	}
	
	public void addOrRemoveLaboratories(Laboratories  laboratory, boolean add){
	if(add)
		laboratories.add(laboratory);
	else
		laboratories.remove(laboratory);
	}

    private boolean saveUserLoginInPortal(UserLogin userLogin, String newPassword) {
        if(UtilValidator.isNotEmpty(newPassword))
            userLogin.setPassword(newPassword);
        Practice practice = commonCrudService.getAll(Practice.class) != null ? commonCrudService.getAll(Practice.class).get(0) : null;
        UserLoginDto    userLoginDto = UserLoginDto.setPropertiesFromUserLoginToDto(userLogin);
        userLoginDto.setTenantId(practice.getTenantId());
        if(UtilValidator.isEmpty(userLoginDto.getRoles())) {
            return false;
        }

        //Raghu Bandi: Added the following logic to avoid double encryption when user role(s) are updated.
        Map<String, Object> userLoginMap = PortalRestServiceConsumer.getUserLoginDetailsForUserName(userLogin.getUsername());

        if (!UtilValidator.isEmpty(userLoginMap)) {
            try {
                String decryptedPass = encryptionService.getDecrypted(userLoginDto.getPassword());
                userLoginDto.setPassword(decryptedPass);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }

        String response = PortalRestServiceConsumer.persistUserLogin(userLoginDto);
        if(!response.equals("success"))
            return false;
        else{
            String res = PortalRestServiceConsumer.persistUserLoginFacilityAssociation(userLoginDto);
            if(!res.equals("success"))
                return false;
        }
        return true;
    }
    

}