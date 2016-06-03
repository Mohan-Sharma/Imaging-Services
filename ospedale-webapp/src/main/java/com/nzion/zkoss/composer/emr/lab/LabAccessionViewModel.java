package com.nzion.zkoss.composer.emr.lab;

import com.nzion.domain.Patient;
import com.nzion.domain.emr.lab.LabRequisition;
import com.nzion.domain.emr.lab.LabSpecimenSource;
import com.nzion.domain.emr.lab.SpecimenModel;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.emr.lab.LabService;
import com.nzion.util.UtilValidator;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Nth
 * Date: 1/5/13
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */

@VariableResolver(DelegatingVariableResolver.class)
public class LabAccessionViewModel {
    @WireVariable
    private LabService labService;
    @WireVariable
    private LabRequisition labRequisition;
    @WireVariable
    private CommonCrudService commonCrudService;

    private String specimenLabel;
    private Patient patient;

    private List<SpecimenModel> specimenModelList;

    @Command("Search")
    @NotifyChange("specimenModelList")
    public void search() {
        SpecimenModel searchParam = new SpecimenModel();
        LabRequisition requisition = new LabRequisition();
        requisition.setPatient(patient);
        if (patient!=null)
            searchParam.setLabRequisition(requisition);
        if (UtilValidator.isNotEmpty(specimenLabel))
            searchParam.setSpecimenLabel(specimenLabel);
        specimenModelList = commonCrudService.searchByExample(searchParam);
    }

    public String getSpecimenLabel() {
        return specimenLabel;
    }

    public void setSpecimenLabel(String specimenLabel) {
        this.specimenLabel = specimenLabel;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public List<SpecimenModel> getSpecimenModelList() {
        return specimenModelList;
    }

    public void setSpecimenModelList(List<SpecimenModel> specimenModelList) {
        this.specimenModelList = specimenModelList;
    }
}
