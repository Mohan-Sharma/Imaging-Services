package com.nzion.zkoss.composer;

import com.nzion.domain.Referral;
import com.nzion.domain.ReferralContract;
import com.nzion.domain.ReferralContractService;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.service.common.CommonCrudService;
import com.nzion.service.dto.ServiceMasterDto;
import com.nzion.util.Infrastructure;
import com.nzion.util.RestServiceConsumer;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import com.nzion.zkoss.ext.Navigation;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zk.ui.Component;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Saikiran Chepuri on 03-May-16.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class ReferralContractServiceViewModel {

    @WireVariable
    private CommonCrudService commonCrudService;

    private ReferralContract referralContract;
    private Set<ReferralContractService> referralContractServices = new HashSet<ReferralContractService>();
    private List<ServiceMasterDto> allservices = new ArrayList<>();
    private double paymentPercentage = 0;
    private double paymentAmount = 0;


    @Wire("#referralContractServiceWin")
    private Window referralContractServiceWin;

    @Command("search")
    public void search(){
        referralContractServices.clear();
        referralContractServices.addAll(referralContract.getReferralContractServices());
        String searchService = ((Textbox) referralContractServiceWin.getFellow("searchServiceTextBox")).getValue();
        if(UtilValidator.isEmpty(searchService) || searchService.length() < 3){
            UtilMessagesAndPopups.showError("Please provide atleast 3 characters for search");
            Events.postEvent("onReload", referralContractServiceWin.getFellow("serviceContractListBox"), null);
            return;
        }

        Iterator<ReferralContractService> iterator = referralContractServices.iterator();
        while(iterator.hasNext()){
            ReferralContractService referralContractService = iterator.next();
            if(! referralContractService.getServiceName().toLowerCase().contains(searchService.toLowerCase()) ){
                iterator.remove();
            }
        }

        Events.postEvent("onReload", referralContractServiceWin.getFellow("serviceContractListBox"), null);
    }

    @AfterCompose
    public void init(@ContextParam(ContextType.VIEW) Component view){
        Selectors.wireComponents( view, this, true);
        referralContract = (ReferralContract) referralContractServiceWin.getAttribute("referralContract");
        //int serviceMainGroupId = "PHARMACY".equals(referralContract.getReferralType()) ? 2 : 1;  (better add one more comboitem of "Lab"
        //int serviceMainGroupId = "CLINIC".equals(referralContract.getReferralType()) ? 3 : 1;
        int serviceMainGroupId = 3;
        allservices = RestServiceConsumer.getAllServiceMaster(serviceMainGroupId);
        if(UtilValidator.isNotEmpty(referralContract.getReferralContractServices())){
            referralContractServices.addAll(referralContract.getReferralContractServices());
        }else{
            for(ServiceMasterDto serviceMasterDto : allservices){
                ReferralContractService referralContractService = new ReferralContractService(0, 0,
                        serviceMasterDto.getServiceCode(), serviceMasterDto.getServiceName(), serviceMasterDto.getServiceSubGroupDescription(),
                        serviceMasterDto.getServiceSubGroupId(), serviceMasterDto.getServiceMainGroup(),
                        serviceMainGroupId, referralContract);
                referralContractServices.add(referralContractService);
            }
            referralContract.setReferralContractServices(referralContractServices);
        }

    }

    @Command("submit")
    public void submit(){
        save();
        referralContract.setContractStatus("SUBMIT");
        Infrastructure.getSessionFactory().getCurrentSession().clear();
        commonCrudService.save(referralContract);
        updateReferralDb();
        Component referralContractWin = referralContractServiceWin.getParent().getParent().getFellow("referralContractWin");
        Navigation.navigate("referralContractList", null, "contentArea");
        referralContractServiceWin.detach();
        referralContractWin.detach();
        UtilMessagesAndPopups.showSuccess();
    }

    private void updateReferralDb(){
        TenantIdHolder.setTenantId(referralContract.getReferralClinicId());

        ReferralContract refContract =  commonCrudService.findUniqueByEquality(ReferralContract.class, new String[]{"referralClinicId","refereeClinicId"},
                new Object[]{referralContract.getReferralClinicId(),referralContract.getRefereeClinicId()});
        Referral referral = commonCrudService.findUniqueByEquality(Referral.class, new String[]{"tenantId"}, new Object[]{referralContract.getRefereeClinicId()});
        if(refContract == null)
            refContract = new ReferralContract();
        refContract.setReferral(referral);
        refContract.setReferralType(referralContract.getReferralType());
        refContract.setExpiryDate(referralContract.getExpiryDate());
        refContract.setContractDate(referralContract.getContractDate());
        refContract.setPaymentMode(referralContract.getPaymentMode());
        refContract.setContractStatus(referralContract.getContractStatus());
        refContract.setPaypoint(referralContract.getPaypoint());
        refContract.setPercentageOnBill(referralContract.getPercentageOnBill());
        refContract.setDocument(referralContract.getDocument());
        refContract.setDocumentName(referralContract.getDocumentName());
        refContract.setReferralClinicId(referralContract.getReferralClinicId());
        refContract.setRefereeClinicId(referralContract.getRefereeClinicId());
        Infrastructure.getSessionFactory().getCurrentSession().clear();
        commonCrudService.save(refContract);
        if(UtilValidator.isEmpty(refContract.getReferralContractServices())){
            for(ReferralContractService reffContractService : referralContract.getReferralContractServices()){
                ReferralContractService contractService = new ReferralContractService(reffContractService.getPaymentPercentage(),
                        reffContractService.getPaymentAmount(),reffContractService.getServiceCode(),reffContractService.getServiceName(),
                        reffContractService.getServiceSubGroupDescription(),
                        reffContractService.getServiceSubGroupId(),reffContractService.getServiceMainGroup(),
                        reffContractService.getServiceMainGroupId(), refContract);
                commonCrudService.save(contractService);
            }
        }else{
            for(ReferralContractService reffContractService : refContract.getReferralContractServices()){
                commonCrudService.save(reffContractService);
            }
        }

        TenantIdHolder.setTenantId(referralContract.getRefereeClinicId());
    }


    @Command("save")
    public void save(){
        referralContract.setReferralContractServices(referralContractServices);
        commonCrudService.save(referralContract.getReferralContractServices());
        Events.postEvent("onReload", referralContractServiceWin.getFellow("serviceContractListBox"), null);
        Navigation.navigate("referralContractList", null, "contentArea");
        UtilMessagesAndPopups.showSuccess();

    }

    @Command("apply")
    public void apply(){
        Iterator<ReferralContractService> iterator = referralContractServices.iterator();
        while(iterator.hasNext()){
            ReferralContractService referralContractService = iterator.next();
            referralContractService.setPaymentPercentage(paymentPercentage);
            referralContractService.setPaymentAmount(paymentAmount);
        }
        Events.postEvent("onReload", referralContractServiceWin.getFellow("serviceContractListBox"), null);

    }

    public CommonCrudService getCommonCrudService() {
        return commonCrudService;
    }

    public void setCommonCrudService(CommonCrudService commonCrudService) {
        this.commonCrudService = commonCrudService;
    }

    public ReferralContract getReferralContract() {
        return referralContract;
    }

    public void setReferralContract(ReferralContract referralContract) {
        this.referralContract = referralContract;
    }

    public double getPaymentPercentage() {
        return paymentPercentage;
    }

    public void setPaymentPercentage(double paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Set<ReferralContractService> getReferralContractServices() {
        return referralContractServices;
    }

    public void setReferralContractServices(Set<ReferralContractService> referralContractServices) {
        this.referralContractServices = referralContractServices;
    }
}
