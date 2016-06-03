package com.nzion.zkoss.composer;

import com.nzion.domain.Patient;
import com.nzion.domain.PatientWithDraw;
import com.nzion.service.PatientService;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilDateTime;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;


import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Saikiran Chepuri on 24-May-16.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class PatientWithdrawViewModel {

    @WireVariable
    private CommonCrudService commonCrudService;

    @WireVariable
    private PatientService patientService;

    @Wire("#patientRefundWin")
    private Window patientRefundWin;

    private Date fromDate;

    private Date thruDate;

    private PatientWithDraw patientWithDraw;

    private List<PatientWithDraw> patientWithDraws;

    private Patient patient;

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component component){
        Selectors.wireComponents(component,this,true);
    }

    public void searchPatientWithdraw(){
      /*  if(fromDate == null)
            fromDate = new Date();
        if(thruDate == null)
            thruDate = new Date();
        if(UtilDateTime.getIntervalInDays(fromDate,thruDate) > 30) {
            UtilMessagesAndPopups.showError("Search Date range cannot be greater than 30 days");
            return;
        } */
        patientWithDraws = patientService.getPatientWithdrawByCriteria(patient,fromDate,thruDate);
    }

    @Command("cancel")
    public void cancel(PatientWithDraw patientWithDraw){
        patientWithDraw.setCancelNotes(patientWithDraw.getCancelNotes());
        patientWithDraw.setCancelReason(patientWithDraw.getCancelReason());
        patientWithDraw.setStatus("Cancelled");
        commonCrudService.save(patientWithDraw);
        Events.postEvent("onReloadRequest",patientRefundWin.getFellow("patientWithdrawListBox"),null);
        UtilMessagesAndPopups.showSuccess();
    }

    public List<PatientWithDraw> getPatientWithDraws() {
        if(patientWithDraws == null)
            patientWithDraws = new ArrayList<PatientWithDraw>();

        return patientWithDraws;
    }

    public void setPatientWithDraws(List<PatientWithDraw> patientWithDraws) {
        this.patientWithDraws = patientWithDraws;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getThruDate() {
        return thruDate;
    }

    public void setThruDate(Date thruDate) {
        this.thruDate = thruDate;
    }

    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    public PatientService getPatientService() {
        return patientService;
    }

    public void setPatientService(PatientService patientService) {
        this.patientService = patientService;
    }

    public Window getPatientRefundWin() {
        return patientRefundWin;
    }

    public void setPatientRefundWin(Window patientRefundWin) {
        this.patientRefundWin = patientRefundWin;
    }

    public PatientWithDraw getPatientWithDraw() {
        return patientWithDraw;
    }

    public void setPatientWithDraw(PatientWithDraw patientWithDraw) {
        this.patientWithDraw = patientWithDraw;
    }
}
