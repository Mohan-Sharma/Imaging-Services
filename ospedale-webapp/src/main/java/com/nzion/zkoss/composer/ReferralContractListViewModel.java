package com.nzion.zkoss.composer;

import com.nzion.domain.ReferralContract;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.service.ReferralContractApproveReject;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.zkoss.ext.Navigation;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.cdi.DelegatingVariableResolver;
import org.zkoss.zk.ui.Component;
import java.util.List;

import java.awt.*;
import java.util.*;

/**
 * Created by Saikiran Chepuri on 02-May-16.
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ReferralContractListViewModel extends AutowirableComposer{

    private static final long serialVersionUID = 1L;

    @WireVariable
    private CommonCrudService commonCrudService;

    @Wire("#referralContractList")
    private Window referralContractList;

    private List<ReferralContract> initiatedReferralContractList = new ArrayList<>();

    private List<ReferralContract> requestedReferralContractList = new ArrayList<>();

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents(view, this, true);
        initiatedReferralContractList = commonCrudService.findByEquality(ReferralContract.class, new String[]{"status"}, new Object[]{"Initiated"});
        requestedReferralContractList = commonCrudService.findByEquality(ReferralContract.class, new String[]{"status"}, new Object[]{"Received"});

    }

    @Command("approve")
    public void approve(ReferralContract referralContract){

        ReferralContractApproveReject referralContractApproveReject = new ReferralContractApproveReject();

        referralContractApproveReject.referralApprove(referralContract.getId().toString(), referralContract.getReferralClinicId(), commonCrudService);

        TenantIdHolder.setTenantId(referralContract.getReferralClinicId());

        Navigation.navigate("referralContactList", null, "contentArea");
        UtilMessagesAndPopups.showSuccess();
    }

    @Command("rejected")
    public void rejected(ReferralContract referralContract){
        ReferralContractApproveReject referralContractApproveReject = new ReferralContractApproveReject();

        referralContractApproveReject.referralReject(referralContract.getId().toString(), referralContract.getReferralClinicId(), commonCrudService);

        TenantIdHolder.setTenantId(referralContract.getReferralClinicId());


        Navigation.navigate("referralContactList", null, "contentArea");
        UtilMessagesAndPopups.showSuccess();
    }

    public List<ReferralContract> getInitiatedReferralContractList() {
        Iterator<ReferralContract> referralContractIterator = initiatedReferralContractList.iterator();
        while (referralContractIterator.hasNext()){
            ReferralContract referralContract = referralContractIterator.next();
            String contractStatus = referralContract.getContractStatus().equals("SUBMIT") ? "VERIFIED" : referralContract.getContractStatus();
            contractStatus = contractStatus.equals("IN-PROGRESS") ? "CREATED" : contractStatus;
            referralContract.setContractStatusNewLabel(contractStatus);
        }
        return initiatedReferralContractList;
    }

    public void setInitiatedReferralContractList(List<ReferralContract> initiatedReferralContractList) {
        this.initiatedReferralContractList = initiatedReferralContractList;
    }

    public List<ReferralContract> getRequestedReferralContractList() {
        Iterator<ReferralContract> referralContractIterator = requestedReferralContractList.iterator();
        while (referralContractIterator.hasNext()){
            ReferralContract referralContract = referralContractIterator.next();
            String contractStatus = referralContract.getContractStatus().equals("SUBMIT") ? "VERIFIED" : referralContract.getContractStatus();
            contractStatus = contractStatus.equals("IN-PROGRESS") ? "CREATED" : contractStatus;
            referralContract.setContractStatusNewLabel(contractStatus);
        }
        return requestedReferralContractList;
    }

    public void setRequestedReferralContractList(List<ReferralContract> requestedReferralContractList) {
        this.requestedReferralContractList = requestedReferralContractList;
    }

}
