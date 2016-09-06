package com.nzion.zkoss.composer.emr.lab;

import java.util.*;

import com.nzion.domain.emr.lab.*;
import com.nzion.repository.impl.HibernateAccountNumberGenerator;
import com.nzion.util.Infrastructure;
import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;

import com.nzion.domain.Location;
import com.nzion.domain.Person;
import com.nzion.domain.Roles;
import com.nzion.domain.pms.Modifier;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;


public class LabProfileController extends OspedaleAutowirableComposer {

	//private Set<LabTest> labTests;
	
	private Set<Investigation> investigations;

	
	
	private LabTestProfile labTestPanel;
	
	private LabTest labTest;

	private LabService labService;

	private CommonCrudService commonCrudService;

	private Set<LabTestPanel> labTestPanels;
	
	private Set<LabTest> labTests;

	//private LabTestCpt labTestCpt;

	//private Set<PanelTechnician> panelTechnicianSet;

	private Location location;

	private Set<Person> technicians;

	@Override
	public void doAfterCompose(Component component) throws Exception {
	super.doAfterCompose(component);
	labTestPanel = null;
		Object obj = Executions.getCurrent().getArg().get("entity");
		if (obj != null){
			labTestPanel = (LabTestProfile) obj;
		}
	if (labTestPanel == null) {
		labTestPanel = new LabTestProfile();
		//labTestCpt = new LabTestCpt();
		//labTestPanel.setLabCpt(labTestCpt);
		//panelTechnicianSet = labTestPanel.getPanelTechnicians();
		return;
	}
	labTestPanel = commonCrudService.getById(LabTestProfile.class, labTestPanel.getId());
	//panelTechnicianSet = labTestPanel.getPanelTechnicians();
	labTests = labTestPanel.getTests();
	//labTests = labTest.getLabTestPanels();
	//labTestCpt = labTestPanel.getLabCpt();
	}

	public void saveNewLabTestPanel() {
		/*labTest.setInvestigations(labTests);
		labTest.setTestPneumonic(labTest.getTestDescription());*/
		//labService.saveLabTestPanel(labTestPanel);
		labTestPanel.setTests(labTests);
		LabTestProfile test = commonCrudService.save(labTestPanel);
		test.setProfileCode("Profile " + test.getId());
		commonCrudService.merge(test);
		root.getFellowIfAny("addLabTestProfileWindow", true).detach();
		UtilMessagesAndPopups.showSuccess();
	}

	/*public void saveLabTestPanel() {
	*//*if ("INHOUSE".equals(labTestPanel.getDestinationType()) && UtilValidator.isEmpty(panelTechnicianSet)) {
		UtilMessagesAndPopups.showError("Assign technician for this test panel");
		return;
	}*//*
	
	*//*if (UtilValidator.isEmpty(investigations) && UtilValidator.isEmpty(labTestPanels)) {
		UtilMessagesAndPopups.showError("Associate lab test for this test panel");
		return;
	}*//*
	//labTestPanel.setLabCpt(commonCrudService.save(labTestCpt));
	//labTestPanel.setPanelTechnicians(panelTechnicianSet);
	//labTestPanel.setLabTests(labTests);
	//labTestPanel.setLabTestPanels(labTestPanels);


	labTest.setInvestigations(labTests);
	labTest.setTestPneumonic(labTest.getTestDescription());
	//labService.saveLabTestPanel(labTestPanel);
	LabTest test = commonCrudService.save(labTest);
	test.setTestCode("Lab "+test.getId());
	commonCrudService.merge(labTest);
	root.getFellowIfAny("addLabTestPanelWindow", true).detach();
	UtilMessagesAndPopups.showSuccess();
	}*/

	/*public void buildPanelTechnicianSet() {
	PanelTechnician panelTechnician = null;
	for (PanelTechnician technician : panelTechnicianSet) {
		if (location.equals(technician.getLocation())) panelTechnician = technician;
	}
	if (panelTechnician == null) 
		panelTechnician = new PanelTechnician();
	panelTechnician.setLocation(location);
	panelTechnician.setTechnicians(technicians);
	panelTechnicianSet.add(panelTechnician);
	}*/

	/*public void removePanelTechnician(PanelTechnician panelTechnician) {
	panelTechnicianSet.remove(panelTechnician);
	}
*/
	/*public void getPanelTechnicianByLocation() {
	Set<Person> selectedTechnicians = new HashSet<Person>();
	for (PanelTechnician panelTechnician : panelTechnicianSet)
		if (location.equals(panelTechnician.getLocation()))
			selectedTechnicians.addAll(panelTechnician.getTechnicians());
	technicians = selectedTechnicians;
	}
*/
	public List<Person> getPersonForLocation(Location location) {
	List<Person> filteredPersons = new ArrayList<Person>();
	for (Person person : commonCrudService.getAll(Person.class))
		if (UtilValidator.isNotEmpty(person.getLocations()) && person.getLocations().contains(location)&& person.getUserLogin()!=null&& person.getUserLogin().getAuthorization().hasRole(Roles.TECHNICIAN))
			filteredPersons.add(person);
	return filteredPersons;
	}

	public boolean checkDuplicateModifier(Combobox combobox1, Combobox combobox2, Combobox combobox3, Combobox combobox4) {
	Modifier modifier1 = combobox1.getSelectedItem() != null ? (Modifier) combobox1.getSelectedItem().getValue()
			: new Modifier();
	Modifier modifier2 = combobox2.getSelectedItem() != null ? (Modifier) combobox2.getSelectedItem().getValue()
			: new Modifier();
	Modifier modifier3 = combobox3.getSelectedItem() != null ? (Modifier) combobox3.getSelectedItem().getValue()
			: new Modifier();
	Modifier modifier4 = combobox4.getSelectedItem() != null ? (Modifier) combobox4.getSelectedItem().getValue()
			: new Modifier();
	if (modifier1.equals(modifier2) || modifier1.equals(modifier3) || modifier1.equals(modifier4)
			|| modifier2.equals(modifier3) || modifier2.equals(modifier4) || modifier3.equals(modifier4)) {
		UtilMessagesAndPopups.showError("Modifier Already Selected For this CPT");
		return true;
	}
	return false;
	}

	/*public LabTestCpt getLabTestCpt() {
	return labTestCpt;
	}

	public void setLabTestCpt(LabTestCpt labTestCpt) {
	this.labTestCpt = labTestCpt;
	}

	public Set<PanelTechnician> getPanelTechnicianSet() {
	return panelTechnicianSet;
	}

	public Set<Person> getTechnicians() {
	return technicians;
	}

	public void setTechnicians(Set<Person> technicians) {
	this.technicians = technicians;
	}*/

	public Location getLocation() {
	return location;
	}

	public void setLocation(Location location) {
	this.location = location;
	}

	public Set<Investigation> getInvestigations() {
	return investigations;
	}

	public void setInvestigations(Set<Investigation> investigations) {
	this.investigations = investigations;
	}

	public LabTest getLabTest() {
	return labTest;
	}

	public void setLabTest(LabTest labTest) {
	this.labTest = labTest;
	}

	public void setLabService(LabService labService) {
	this.labService = labService;
	}

	public CommonCrudService getCommonCrudService() {
	return commonCrudService;
	}

	public Set<LabTestPanel> getLabTestPanels() {
	return labTestPanels;
	}

	public void setLabTestPanels(Set<LabTestPanel> labTestPanels) {
	this.labTestPanels = labTestPanels;
	}

	public Set<LabTest> getLabTests() {
		return labTests;
	}

	public void setLabTests(Set<LabTest> labTests) {
		this.labTests = labTests;
	}

	public LabTestProfile getLabTestPanel() {
		return labTestPanel;
	}

	public void setLabTestPanel(LabTestProfile labTestPanel) {
		this.labTestPanel = labTestPanel;
	}

	private static final long serialVersionUID = 1L;


}
