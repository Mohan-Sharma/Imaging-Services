package com.nzion.view.practice;

import com.nzion.domain.*;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import org.zkoss.bind.annotation.*;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.cdi.DelegatingVariableResolver;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import com.nzion.domain.LabReschedulingPreference;
import com.nzion.domain.LabCancellationPreference;
import com.nzion.domain.PatientCancellationPreference;
import com.nzion.domain.PatientReschedulingPreference;
import com.nzion.domain.SchedulingPreference;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Saikiran Chepuri on 26-Apr-16.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class RCMPreferenceVM {

    @WireVariable
    private CommonCrudService commonCrudService;

    private RCMPreference rcmPreference;

    private Set<SchedulingPreference> schedulingPreference;

    private Set<PatientCancellationPreference> patientCancellationPreferences;

    private Set<LabCancellationPreference> labCancellationPreferences;

    private Set<PatientReschedulingPreference> patientReschedulingPreferences;

    private Set<LabReschedulingPreference> labReschedulingPreferences;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, true);
        rcmPreference = commonCrudService.getByPractice(RCMPreference.class);
        if(rcmPreference.getId() == null) {
            rcmPreference = new RCMPreference();
        }else {
            schedulingPreference = new HashSet<>(commonCrudService.findByEquality(SchedulingPreference.class, new String[]{"rcmPreference"}, new Object[]{rcmPreference}));//}
            patientCancellationPreferences = new HashSet<>(commonCrudService.findByEquality(PatientCancellationPreference.class,new String[]{"rcmPreference"},new Object[]{rcmPreference}));
            labCancellationPreferences = new HashSet<>(commonCrudService.findByEquality(LabCancellationPreference.class,new String[]{"rcmPreference"},new Object[]{rcmPreference}));
            patientReschedulingPreferences = new HashSet<>(commonCrudService.findByEquality(PatientReschedulingPreference.class,new String[]{"rcmPreference"}, new Object[]{rcmPreference}));
            labReschedulingPreferences = new HashSet<>(commonCrudService.findByEquality(LabReschedulingPreference.class, new String[]{"rcmPreference"},new Object[]{rcmPreference}));
        }
        if(UtilValidator.isEmpty(schedulingPreference))
            schedulingPreference = SchedulingPreference.getEmptyLineItem();
        if (UtilValidator.isEmpty(patientCancellationPreferences))
            patientCancellationPreferences = PatientCancellationPreference.getEmptyLineItem();
        if(UtilValidator.isEmpty(labCancellationPreferences))
            labCancellationPreferences = LabCancellationPreference.getEmptyLineItem();
        if (UtilValidator.isEmpty(patientReschedulingPreferences))
            patientReschedulingPreferences = PatientReschedulingPreference.getEmptyLineItem();
        if(UtilValidator.isEmpty(labReschedulingPreferences))
            labReschedulingPreferences = LabReschedulingPreference.getEmptyLineItem();
    }

    @Command
    public void save(){
        rcmPreference = commonCrudService.save(rcmPreference);
        commonCrudService.delete(commonCrudService.getAll(SchedulingPreference.class));
        for(SchedulingPreference sp : schedulingPreference) {
            sp.setId(null);
            sp.setRcmPreference(rcmPreference);
            commonCrudService.save(sp);
        }
        commonCrudService.delete(commonCrudService.getAll(PatientCancellationPreference.class));
        for(PatientCancellationPreference pcp : patientCancellationPreferences) {
            pcp.setId(null);
            pcp.setRcmPreference(rcmPreference);
            commonCrudService.save(pcp);
        }
        commonCrudService.delete(commonCrudService.getAll(LabCancellationPreference.class));
        for(LabCancellationPreference lcp : labCancellationPreferences){
            lcp.setId(null);
            lcp.setRcmPreference(rcmPreference);
            commonCrudService.save(lcp);
        }
        commonCrudService.delete(commonCrudService.getAll(PatientReschedulingPreference.class));
        for (PatientReschedulingPreference pR : patientReschedulingPreferences){
            pR.setId(null);
            pR.setRcmPreference(rcmPreference);
            commonCrudService.save(pR);
        }
        commonCrudService.delete(commonCrudService.getAll(LabReschedulingPreference.class));
        for(LabReschedulingPreference lRP : labReschedulingPreferences){
            lRP.setId(null);
            lRP.setRcmPreference(rcmPreference);
            commonCrudService.save(lRP);
        }
        UtilMessagesAndPopups.showSuccess();

    }

    public void setCommonCrudService(CommonCrudService commonCrudService){
        this.commonCrudService = commonCrudService;
    }
    public CommonCrudService getCommonCrudService(){
        return commonCrudService;
    }

    public void setRcmPreference(RCMPreference rcmPreference){
        this.rcmPreference = rcmPreference;
    }
    public RCMPreference getRcmPreference(){
        return rcmPreference;
    }

    public void setSchedulingPreference(Set<SchedulingPreference> schedulingPreference){
        this.schedulingPreference = schedulingPreference;
    }
    public Set<SchedulingPreference> getSchedulingPreference(){
        return schedulingPreference;
    }

    public Set<PatientCancellationPreference> getPatientCancellationPreferences() {
        return patientCancellationPreferences;
    }

    public void setPatientCancellationPreferences(Set<PatientCancellationPreference> patientCancellationPreferences) {
        this.patientCancellationPreferences = patientCancellationPreferences;
    }

    public Set<LabCancellationPreference> getLabCancellationPreferences() {
        return labCancellationPreferences;
    }

    public void setLabCancellationPreferences(Set<LabCancellationPreference> labCancellationPreferences) {
        this.labCancellationPreferences = labCancellationPreferences;
    }

    public Set<PatientReschedulingPreference> getPatientReschedulingPreferences() {
        return patientReschedulingPreferences;
    }

    public void setPatientReschedulingPreferences(Set<PatientReschedulingPreference> patientReschedulingPreferences) {
        this.patientReschedulingPreferences = patientReschedulingPreferences;
    }

    public Set<LabReschedulingPreference> getLabReschedulingPreferences() {
        return labReschedulingPreferences;
    }

    public void setLabReschedulingPreferences(Set<LabReschedulingPreference> labReschedulingPreferences) {
        this.labReschedulingPreferences = labReschedulingPreferences;
    }
}
