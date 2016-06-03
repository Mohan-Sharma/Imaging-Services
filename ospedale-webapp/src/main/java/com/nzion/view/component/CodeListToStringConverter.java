package com.nzion.view.component;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.nzion.domain.emr.lab.LabTestPanel;
import org.apache.commons.lang.StringUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;

import com.nzion.domain.docmgmt.AttachedItem;
import com.nzion.domain.docmgmt.PatientEducationDocument;
import com.nzion.domain.emr.soap.PatientLabOrder;
import com.nzion.enums.MATERIALCATEGORY;
import com.nzion.util.UtilValidator;

public class CodeListToStringConverter implements TypeConverter {

	@Override
	public Object coerceToBean(Object val, Component comp) {
	return null;
	}

	@Override
	public Object coerceToUi(Object val, Component comp) {
	PatientEducationDocument ped = (PatientEducationDocument) ((Listitem) comp.getParent()).getValue();
	/*PatientSoapNote psn = psnc.getSoapNote();
	String codeLabel = getAttachedItemCodes(ped, psnc.getSoapNoteIcds(psn), psnc.getSoapNoteCpts(psn), ((RxSection) psnc
			.getSoapSection(RxSection.class,false)).getPatientRxs(), psnc.getSoapNoteLabOrders(psn));
	*/
	//Label l = new Label(codeLabel);
	//l.setParent(comp);
	
	return null;
	}

	/*public String getAttachedItemCodes(PatientEducationDocument document, Set<PatientIcd> icds, Set<PatientCpt> cpts,
			Set<PatientRx> medications, Set<PatientLabOrder> labOrders) {
	StringBuilder builder = new StringBuilder();
	Set<String> attachedItemCodes = new HashSet<String>();
	if (MATERIALCATEGORY.ICD.equals(document.getPatientEducation().getMaterialCategory())) {
		for (AttachedItem attachedItem : document.getPatientEducation().getAttachedItems())
			attachedItemCodes.add(attachedItem.getCode());
		for (Iterator<PatientIcd> iter = icds.iterator(); iter.hasNext();) {
			PatientIcd icd = iter.next();
			if (attachedItemCodes.contains(icd.getIcdElement().getCode()))
				builder.append(icd.getIcdElement().getCode()).append(",");
		}
		return StringUtils.strip(builder.toString(), ",");
	}
	attachedItemCodes.clear();
	if (MATERIALCATEGORY.CPT.equals(document.getPatientEducation().getMaterialCategory())) {
		for (AttachedItem attachedItem : document.getPatientEducation().getAttachedItems())
			attachedItemCodes.add(attachedItem.getCode());
		for (Iterator<PatientCpt> iter = cpts.iterator(); iter.hasNext();) {
			PatientCpt cpt = iter.next();
			if (attachedItemCodes.contains(cpt.getCpt().getId())) {
				if (!builder.toString().endsWith(",") && builder.length() > 0) builder.append(",");
				builder.append(cpt.getCpt().getId());
			}
		}
	}
	attachedItemCodes.clear();
	if (MATERIALCATEGORY.MEDICATION.equals(document.getPatientEducation().getMaterialCategory())) {
		for (AttachedItem attachedItem : document.getPatientEducation().getAttachedItems())
			attachedItemCodes.add(attachedItem.getDescription());
		for (Iterator<String> i = attachedItemCodes.iterator(); i.hasNext();) {
			String drugName = i.next();
			for (PatientRx prx : medications)
				if (prx.getDrug().getTradeName().startsWith(drugName)) {
					if (!builder.toString().endsWith(",") && builder.length() > 0) builder.append(",");
					builder.append(prx.getDrug().getTradeName());
				}
		}
	}
	attachedItemCodes.clear();
	if (MATERIALCATEGORY.LABORDER.equals(document.getPatientEducation().getMaterialCategory())) {
		for (AttachedItem attachedItem : document.getPatientEducation().getAttachedItems())
			attachedItemCodes.add(attachedItem.getCode());
		for (Iterator<PatientLabOrder> iter = labOrders.iterator(); iter.hasNext();) {
			PatientLabOrder labOrder = iter.next();
			if (attachedItemCodes.contains(labOrder.getLabTestPanel().getCode())) {
				if (!builder.toString().endsWith(",") && builder.length() > 0) builder.append(",");
				builder.append(labOrder.getLabTestPanel().getPanelName());
			}
			if (UtilValidator.isNotEmpty(labOrder.getLabTestPanel().getLabTestPanels()))
				getAttachedItemsForInnerLabPanels(labOrder.getLabTestPanel().getLabTestPanels(), attachedItemCodes,
						builder);
		}
	}
	return builder.toString();
	}*/

	/*private String getAttachedItemsForInnerLabPanels(Set<LabTestPanel> labTestPanels, Set<String> attachedItemCodes,
			StringBuilder stringBuilder) {
	if (UtilValidator.isEmpty(labTestPanels)) return stringBuilder.toString();
	for (Iterator<LabTestPanel> iter = labTestPanels.iterator(); iter.hasNext();) {
		LabTestPanel labTestPanel = iter.next();
		if (attachedItemCodes.contains(labTestPanel.getCode()) && !stringBuilder.toString().contains(labTestPanel.getPanelName())) {
			if (stringBuilder.length() > 0) stringBuilder.append(",");
			if (!stringBuilder.toString().endsWith(",") && stringBuilder.length() > 0) stringBuilder.append(",");
			stringBuilder.append(labTestPanel.getPanelName());
		}
		getAttachedItemsForInnerLabPanels(labTestPanel.getLabTestPanels(), attachedItemCodes, stringBuilder);
	}
	return stringBuilder.toString();
	}*/
}