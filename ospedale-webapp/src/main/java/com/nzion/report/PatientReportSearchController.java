package com.nzion.report;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.nzion.domain.Patient;
import com.nzion.report.search.view.LabResultSearchVo;
import com.nzion.report.search.view.PatientSearchVO;
import com.nzion.service.meaningful.ReportService;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilMisc;
import com.nzion.util.UtilValidator;

public class PatientReportSearchController extends OspedaleAutowirableComposer {

	private List<LabResultSearchVo> labResultVos;

	private boolean operatorRequired = false;

	//private ReportService reportService;


	Listbox labResultSearchListbox;

	//private PatientSearchVO patientSearchVO;



	
	private List<String> testNames = null;

	private TestNameCompartor testNameCompartor =null;
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent, ComponentInfo compInfo) {
	labResultVos = new ArrayList<LabResultSearchVo>();
	labResultVos.add(new LabResultSearchVo());
	//testNames = reportService.getAllObxTestNames();
	if(UtilValidator.isNotEmpty(testNames)){
		Set<String> testNames = new HashSet<String>();
		testNames.addAll(this.testNames);
		this.testNames = new ArrayList<String>();
		this.testNames.addAll(testNames);
		testNameCompartor = new TestNameCompartor();
		Collections.sort(this.testNames, testNameCompartor);
	}
	return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(Component component) throws Exception {
	super.doAfterCompose(component);
	//patientSearchVO = new PatientSearchVO();
	}


	/*public List<Patient> searchPatients() {
	return reportService.searchPatient(patientSearchVO);
	}
*/

	public void addLabResult() {
	if(UtilValidator.isEmpty(testNames))
		return;
	LabResultSearchVo lastItem = labResultVos.get(labResultVos.size()-1);
	if(lastItem!=null && UtilValidator.isEmpty(lastItem.getTestName())){
		UtilMessagesAndPopups.showError("Select test name before adding");
		return;
	}
	LabResultSearchVo labResultSearchVo = new LabResultSearchVo();
	labResultVos.add(labResultSearchVo);
	operatorRequired = labResultVos.size() > 1;
	}


	public void removeLabResult(LabResultSearchVo labResultVo) {
	labResultVos.remove(labResultVo);
	if(UtilValidator.isNotEmpty(labResultVo.getTestName())){
	testNames.add(labResultVo.getTestName());
	Collections.sort(testNames, testNameCompartor);
	Events.postEvent("onReloadRequest", root.getFellowIfAny("labResultSearchListbox"), null);
	}
	}

	public boolean isOperatorRequired() {
	return operatorRequired;
	}

	public void setOperatorRequired(boolean operatorRequired) {
	this.operatorRequired = operatorRequired;
	}

	public List<LabResultSearchVo> getLabResultVos() {
	return labResultVos;
	}

	public void setLabResultVos(List<LabResultSearchVo> labResultVos) {
	this.labResultVos = labResultVos;
	}


	public ListitemRenderer getListitemRenderer() {
	return new ListitemRenderer() {
		@Override
		public void render(final Listitem item, Object data,int index) throws Exception {
		LabResultSearchVo labResultSearchVo = (LabResultSearchVo) data;
		item.setValue(labResultSearchVo);
		Listcell testNameListCell = new Listcell();
		testNameListCell.setParent(item);
		Combobox combobox = new Combobox();
		combobox.setReadonly(true);
		combobox.setParent(testNameListCell);
		buildTestNameCombobox(combobox, labResultSearchVo);
		Listcell observationListcell = new Listcell();
		observationListcell.setParent(item);
		Hbox hbox = new Hbox();
		hbox.setParent(observationListcell);
		Combobox operatorCombobox = new Combobox();
		operatorCombobox.setReadonly(true);
		buildOperatorCombobox(operatorCombobox, labResultSearchVo);
		operatorCombobox.setParent(hbox);
		Intbox intbox = new Intbox();
		intbox.setParent(hbox);
		intbox.setValue(labResultSearchVo.getResultValue());
		Listcell conditionListcell = new Listcell();
		conditionListcell.setParent(item);
		Combobox conditionCombobox = new Combobox();
		conditionCombobox.setReadonly(true);
		conditionCombobox.setParent(conditionListcell);
		buildConditionCombobox(conditionCombobox, labResultSearchVo);
		conditionCombobox.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
			Combobox combobox = (Combobox) event.getTarget();
			LabResultSearchVo labResultSearchVo = (LabResultSearchVo) ((Listitem) combobox.getParent().getParent()).getValue();
			labResultSearchVo.setQuantifier((String) combobox.getSelectedItem().getValue());
			}
		});
		Listcell deleteListcell = new Listcell();
		deleteListcell.setParent(item);
		Button button = new Button("Remove");
		button.setParent(deleteListcell);
		button.setVisible(labResultVos.size() > 1);
		button.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
			Button button = (Button) event.getTarget();
			LabResultSearchVo labResultSearchVo = (LabResultSearchVo) ((Listitem) button.getParent().getParent()).getValue();
			removeLabResult(labResultSearchVo);
			Events.postEvent("onReloadRequest", labResultSearchListbox, null);
			}
		});
		intbox.addEventListener("onChange", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
			Intbox intbox = (Intbox) event.getTarget();
			LabResultSearchVo labResultSearchVo = (LabResultSearchVo) ((Listitem) intbox.getParent().getParent().getParent()).getValue();
			labResultSearchVo.setResultValue(intbox.getValue());
			}
		});
		operatorCombobox.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
			Combobox combobox = (Combobox) event.getTarget();
			LabResultSearchVo labResultSearchVo = (LabResultSearchVo) ((Listitem) combobox.getParent().getParent()
					.getParent()).getValue();
			labResultSearchVo.setOperator((String) combobox.getSelectedItem().getValue());
			}
		});
		combobox.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
			Combobox combobox = (Combobox) event.getTarget();
			LabResultSearchVo labResultSearchVo = (LabResultSearchVo) ((Listitem) combobox.getParent().getParent()).getValue();
			String previousTestName = labResultSearchVo.getTestName();
			String selectedTestName = (String) combobox.getSelectedItem().getValue();
			labResultSearchVo.setTestName(selectedTestName);
			testNames.remove(selectedTestName);
			if(UtilValidator.isNotEmpty(previousTestName))
				testNames.add(previousTestName);
			Collections.sort(testNames,testNameCompartor);
			Events.postEvent("onReloadRequest", root.getFellowIfAny("labResultSearchListbox"), null);
			}
		});
		}
	};
	}

	private void buildTestNameCombobox(Combobox combobox, LabResultSearchVo labResultSearchVo) {
	Comboitem selectedComboitem = null;
	if(UtilValidator.isNotEmpty(labResultSearchVo.getTestName()) && !testNames.contains(labResultSearchVo.getTestName()))
		testNames.add(labResultSearchVo.getTestName());
	if (UtilValidator.isNotEmpty(testNames)) {
		for (String testName : testNames) {
			Comboitem comboitem = new Comboitem();
			comboitem.setLabel(testName);
			comboitem.setValue(testName);
			comboitem.setParent(combobox);
			if (testName != null && testName.equals(labResultSearchVo.getTestName())) selectedComboitem = comboitem;
		}
	}
	if (selectedComboitem != null) combobox.setSelectedItem(selectedComboitem);
	testNames.remove(labResultSearchVo.getTestName());
	}

	private void buildOperatorCombobox(Combobox combobox, LabResultSearchVo labResultSearchVo) {
	Comboitem selectedComboitem = null;
	Comboitem comboitem1 = new Comboitem();
	comboitem1.setLabel("Equals");
	comboitem1.setValue("Equals");
	comboitem1.setParent(combobox);
	Comboitem comboitem2 = new Comboitem();
	comboitem2.setLabel("Less Than");
	comboitem2.setValue("Less");
	comboitem2.setParent(combobox);
	Comboitem comboitem3 = new Comboitem();
	comboitem3.setLabel("Greater Than");
	comboitem3.setValue("Greater");
	comboitem3.setParent(combobox);
	if ("Equals".equalsIgnoreCase(labResultSearchVo.getOperator())) selectedComboitem = comboitem1;
	if ("Less".equalsIgnoreCase(labResultSearchVo.getOperator())) selectedComboitem = comboitem2;
	if ("Greater".equalsIgnoreCase(labResultSearchVo.getOperator())) selectedComboitem = comboitem3;
	if (selectedComboitem != null) combobox.setSelectedItem(selectedComboitem);
	}

	private void buildConditionCombobox(Combobox combobox, LabResultSearchVo labResultSearchVo) {
	Comboitem selectedComboitem = null;
	Comboitem andComboitem = new Comboitem();
	andComboitem.setLabel("AND");
	andComboitem.setValue("AND");
	andComboitem.setParent(combobox);
	Comboitem orComboitem = new Comboitem();
	orComboitem.setLabel("OR");
	orComboitem.setValue("OR");
	orComboitem.setParent(combobox);
	if ("AND".equalsIgnoreCase(labResultSearchVo.getQuantifier())) selectedComboitem = andComboitem;
	if ("OR".equalsIgnoreCase(labResultSearchVo.getQuantifier())) selectedComboitem = orComboitem;
	if (selectedComboitem != null) combobox.setSelectedItem(selectedComboitem);
	}

	/*public void setReportService(ReportService reportService) {
	this.reportService = reportService;
	}


	public PatientSearchVO getPatientSearchVO() {
	return patientSearchVO;
	}

	public void setPatientSearchVO(PatientSearchVO patientSearchVO) {
	this.patientSearchVO = patientSearchVO;
	}*/


	private class TestNameCompartor implements Comparator<String>{
		@Override
		public int compare(String o1, String o2) {
		return o1.compareToIgnoreCase(o2);
		}
	}
	private static final long serialVersionUID = 1L;

}
