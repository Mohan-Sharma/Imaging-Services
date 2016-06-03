package com.nzion.zkoss.composer;

import com.nzion.domain.PatientWithDraw;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Window;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Nthdimenzion on 24-May-16.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class PatientWithdrawAddViewModel {

    @WireVariable
    private CommonCrudService commonCrudService;

    @Wire("#patientWithDrawAddWin")
    private Window patientWithDrawAddWin;

    private PatientWithDraw patientWithDraw;



    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW)Component component){
        Selectors.wireComponents(component,this,true);
        patientWithDraw = new PatientWithDraw();
    }

    @Command("save")
    public void save(){

        if(patientWithDraw.getPatient() == null) {
            UtilMessagesAndPopups.showError("Patient cannot be empty");
            return;
        }

        if(patientWithDraw.getWithdrawAmount() == null){
            UtilMessagesAndPopups.showError("Refund Amount is required");
            return;
        }

    /*    if(patientWithDraw.getWithdrawAmount().compareTo(BigDecimal.ZERO)<0){
            UtilMessagesAndPopups.showError("Amount must be greater than zero");
            return;
        } */

        patientWithDraw.setWithdrawDate(new Date());
        patientWithDraw.setCreatedPerson(Infrastructure.getLoggedInPerson());
        patientWithDraw.setStatus("Refunded");

        commonCrudService.save(patientWithDraw);
        UtilMessagesAndPopups.showSuccess("Action Completed Successfully");
        patientWithDrawAddWin.detach();


    }

    public PatientWithDraw getPatientWithDraw() {
        return patientWithDraw;
    }

    public void setPatientWithDraw(PatientWithDraw patientWithDraw) {
        this.patientWithDraw = patientWithDraw;
    }

    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    public Window getPatientWithDrawAddWin() {
        return patientWithDrawAddWin;
    }

    public void setPatientWithDrawAddWin(Window patientWithDrawAddWin) {
        this.patientWithDrawAddWin = patientWithDrawAddWin;
    }
}
