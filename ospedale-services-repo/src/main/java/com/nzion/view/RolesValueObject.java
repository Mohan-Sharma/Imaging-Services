package com.nzion.view;

import com.nzion.domain.Authorization;
import com.nzion.domain.Roles;

public class RolesValueObject {
	
	public Authorization authorization;
	
	public boolean dirty;

	public RolesValueObject(Authorization authorization){
	this.authorization=authorization;
	}
	
	public boolean isSuperAdmin(){
	return authorization.hasRole(Roles.SUPER_ADMIN);
	}
	
	public void setSuperAdmin(boolean superAdmin){
	append(Roles.SUPER_ADMIN,superAdmin);
	}
	
	public boolean isAdmin(){
	return authorization.hasRole(Roles.ADMIN);
	}

	public void setAdmin(boolean admin){
	append(Roles.ADMIN, admin);
	}
	
	public boolean isStoreManagement(){
	return authorization.hasRole(Roles.STORE_MANAGEMENT);
	}

	public void setStoreManagement(boolean storeManagement){
	append(Roles.STORE_MANAGEMENT, storeManagement);
	}
	public boolean isProvider(){
	return authorization.hasRole(Roles.PROVIDER);
	}

	public void setProvider(boolean provider){
	append(Roles.PROVIDER, provider);
	}
	public boolean isMedicalAssistant(){
	return authorization.hasRole(Roles.MEDICAL_ASSISTANT);
	}

	public void setMedicalAssistant(boolean medicalAssistant){
	append(Roles.MEDICAL_ASSISTANT, medicalAssistant);
	}
	public boolean isNurse(){
	return authorization.hasRole(Roles.NURSE);
	}

	public void setNurse(boolean nurse){
	append(Roles.NURSE, nurse);
	}
	public boolean isTechnician(){
	return authorization.hasRole(Roles.TECHNICIAN);
	}

	public void setTechnician(boolean technician){
	append(Roles.TECHNICIAN, technician);
	}
	public boolean isBilling(){
	return authorization.hasRole(Roles.BILLING);
	}

	public void setBilling(boolean billing){
	append(Roles.BILLING, billing);
	}
	public boolean isReception(){
	return authorization.hasRole(Roles.RECEPTION);
	}

	public void setReception(boolean reception){
	append(Roles.RECEPTION, reception);
	}
	public boolean isCaseManager(){
	return authorization.hasRole(Roles.CASE_MANAGER);
	}

	public void setCaseManager(boolean caseManager){
	append(Roles.CASE_MANAGER, caseManager);
	}
	/*public boolean isAdjustor(){
	return authorization.hasRole(Roles.ADJUSTER);
	}

	public void setAdjustor(boolean adjustor){
	append(Roles.ADJUSTER, adjustor);
	}*/
	
	public boolean isPatient(){
	return authorization.hasRole(Roles.PATIENT);
	}

	public void setPatient(boolean patient){
	append(Roles.PATIENT, patient);
	}
	
	public boolean isEmergencyAccess() {
	return authorization.hasRole(Roles.EMERGENCY_ACCESS);
	}
	
	public void setEmergencyAccess(boolean emergencyAccess) {
	append(Roles.EMERGENCY_ACCESS, emergencyAccess);
	}
	
	private void append(long role, boolean add){
	Roles.appendRole(authorization, role, add);
	dirty = true;
	}
	
	public boolean isHouseKeeping(){
	return authorization.hasRole(Roles.HOUSE_KEEPING);
	}

	public void setHouseKeeping(boolean houseKeeping){
	append(Roles.HOUSE_KEEPING, houseKeeping);
	}
	public boolean isOrderManagement(){
	return authorization.hasRole(Roles.ORDER_MANAGEMENT);
	}
	
	public void setOrderManagement(boolean orderManagement){
	append(Roles.ORDER_MANAGEMENT, orderManagement);
	}
	
	public boolean isPhlebotomist(){
		return authorization.hasRole(Roles.PHLEBOTOMIST);
		}

	public void setPhlebotomist(boolean phlebotomist){
		append(Roles.PHLEBOTOMIST, phlebotomist);
		}
}
