package com.nzion.zkoss.composer;

import com.nzion.domain.Referral;
import com.nzion.domain.ReferralContract;
import com.nzion.domain.ReferralContractService;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.Infrastructure;
import com.nzion.util.UtilMessagesAndPopups;
import com.nzion.util.UtilValidator;
import com.nzion.zkoss.ext.Navigation;
import org.apache.commons.io.IOUtils;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.*;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Window;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by Saikiran Chepuri on 29-Apr-16.
 */

    @VariableResolver(DelegatingVariableResolver.class)
    public class ReferralContractViewModel extends  AutowirableComposer {

        private static final long serialVersionUID = 1L;

        @WireVariable
        private CommonCrudService commonCrudService;

        @Wire("#referralContractWin")
        private Window referralContractWin;

        List referrals;

        List<Referral> referralsPharmacy = new ArrayList<Referral>();

        List<Referral> referralsClinic = new ArrayList<Referral>();

        List<Referral> referralsLab = new ArrayList<Referral>();

        List referees;

        boolean percentageRequired;

        boolean serviceDetailVisible;


        private ReferralContract referralContract;

        public CommonCrudService getCommonCrudService() {
            return commonCrudService;
        }

        public void setCommonCrudService(CommonCrudService commonCrudService) {
            this.commonCrudService = commonCrudService;
        }


        @Command("save")
        public void save() {
//if ((referralContract.getReferral() !=null) && (referralContract.getReferral().getTenantId() != null) && (Infrastructure.getPractice().getTenantId() != null)) {
    List<ReferralContract> existingRefContract = commonCrudService.findByEquality(ReferralContract.class,
            new String[]{"referralClinicId", "refereeClinicId"}, new Object[]{referralContract.getReferral().getTenantId(), Infrastructure.getPractice().getTenantId()});
    for (ReferralContract refContract : existingRefContract) {
        if (!"REJECTED".equals(refContract.getContractStatus())) {
            Date contractDate = refContract.getContractDate();
            Date expiryDate = refContract.getExpiryDate();
            if (contractDate.compareTo(referralContract.getExpiryDate()) <= 0 && expiryDate.compareTo(referralContract.getContractDate()) >= 0
                    && referralContract.getId() != null && !referralContract.getId().equals(refContract.getId())) {
                UtilMessagesAndPopups.showError("Contract already exists.");
                return;
            } else if (contractDate.compareTo(referralContract.getExpiryDate()) <= 0 && expiryDate.compareTo(referralContract.getContractDate()) >= 0
                    && referralContract.getId() == null) {
                UtilMessagesAndPopups.showError("Contract already exists.");
                return;
            }
        }
  //  }
}
            referralContract.setContractStatus("IN-PROGRESS");
            referralContract.setReferralClinicId(referralContract.getReferral().getTenantId());
            referralContract.setRefereeClinicId(Infrastructure.getPractice().getTenantId());

            Iterator<ReferralContractService> referralContractServiceIterator = referralContract.getReferralContractServices().iterator();
            while (referralContractServiceIterator.hasNext()){
                ReferralContractService referralContractService =  referralContractServiceIterator.next();
                if(referralContract.getPercentageOnBill() != null){
                    referralContractService.setPaymentPercentage(referralContract.getPercentageOnBill());
                }
            }

        /*    try {
                commonCrudService.save(referralContract);

                //communication loopback starts
                String adminUserName = Infrastructure.getPractice().getAdminUserLogin().getUsername();
                String userMiddleName = Infrastructure.getUserLogin().getPerson().getMiddleName() != null ? Infrastructure.getUserLogin().getPerson().getMiddleName()+" " : "";
                String user = Infrastructure.getUserLogin().getPerson().getFirstName()+" "+userMiddleName+Infrastructure.getUserLogin().getPerson().getLastName();
                Map<String, Object> details = AfyaServiceConsumer.getUserLoginByUserName(adminUserName);
                details.put("key",TemplateNames.REFERRAL_CONTRACT_SAVED_SMS.name());
                details.put("mobile", details.get("mobile_number"));
                details.put("user",user);
                SmsUtil.sendSmsForReferral(details);
                //communication loopback end

            } catch (Exception e) {
                try {
                    commonCrudService.merge(referralContract);
                } catch (Exception e2) {
                    // TODO: handle exception
                }
            } */

            try {
                commonCrudService.save(referralContract);
            }catch(Exception e) {
                try {
                    commonCrudService.merge(referralContract);
                } catch (Exception e2) {
                    // TODO: handle exception
                }
            }

            Navigation.navigate("referralContractList", null, "contentArea");
            UtilMessagesAndPopups.showSuccess();
        }

        @Command("submit")
        public void submit(){
            save();
            ReferralContract refcon = commonCrudService.getById(ReferralContract.class, referralContract.getId());
            refcon.setContractStatus("SUBMIT");
            commonCrudService.save(refcon);
            updateReferralDb();

            if("PERCENTAGE_OF_BILL".equals(referralContract.getPaymentMode())){
                Navigation.navigate("referralContractList", null, "contentArea");
                referralContractWin.detach();
            }

            UtilMessagesAndPopups.showSuccess();


        }


        private void updateReferralDb(){
            TenantIdHolder.setTenantId(referralContract.getReferralClinicId());

            ReferralContract refContract =  commonCrudService.findUniqueByEquality(ReferralContract.class, new String[]{"referralClinicId","refereeClinicId","expiryDate","contractDate"},
                    new Object[]{referralContract.getReferralClinicId(),referralContract.getRefereeClinicId(),referralContract.getExpiryDate(),referralContract.getContractDate()});
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

        @AfterCompose
        public void init(@ContextParam(ContextType.VIEW) Component view){
            Selectors.wireComponents(view, this, true);
            referralContract = (ReferralContract) referralContractWin.getAttribute("referralContract");
            percentageRequired = false;
            if(ReferralContract.PAYMENT_MODE_ENUM.PERCENTAGE_OF_BILL.toString().equals(referralContract.getPaymentMode())){
                percentageRequired = true;
            }
            referrals = commonCrudService.getAll(com.nzion.domain.Referral.class);
            for (Object referralObject : referrals){
                Referral referral = (Referral)referralObject;
                if ((referral.getReferralType() != null) && (referral.getReferralType().equals("CLINIC"))){
                    referralsClinic.add(referral);
                } else if ((referral.getReferralType() != null) && (referral.getReferralType().equals("PHARMACY"))){
                    referralsPharmacy.add(referral);
                }
                else if((referral.getReferralType() != null) && (referral.getReferralType().equals("LAB"))){
                    referralsLab.add(referral);
                }
            }
            referees = commonCrudService.getAll(com.nzion.domain.Employee.class);
        }


        @Command("uploadFile")
        @NotifyChange("referralContract")
        public void uploadFile(@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx) throws SerialException, SQLException, IOException {
            UploadEvent upEvent = null;
            Object objUploadEvent = ctx.getTriggerEvent();
            if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
                upEvent = (UploadEvent) objUploadEvent;
            }
            if (upEvent != null) {
                Media media = upEvent.getMedia();
                Blob blob = null;
                if("pdf".equals(media.getFormat())){
                    byte[] content = IOUtils.toByteArray(media.getStreamData());
                    blob = new SerialBlob(content);
                    referralContract.setDocumentName(media.getName());
                    referralContract.setDocument(blob);
                }else{
                    UtilMessagesAndPopups.showError("Please upload valid document");
                }
            }
        }

        @Command("downloadFile")
        public void downloadFile() throws SerialException, SQLException, IOException{
            if(referralContract == null)
                return;
            if(referralContract.getDocument() == null)
                return;
            Blob blob = referralContract.getDocument();
            if(blob != null)
                Filedownload.save(blob.getBytes(1, (int) blob.length()), "", referralContract.getDocumentName());

        }

        public ReferralContract getReferralContract() {
            return referralContract;
        }

        public void setReferralContract(ReferralContract referralContract) {
            this.referralContract = referralContract;
        }

        public List getReferrals() {
            return referrals;
        }

        public void setReferrals(List referrals) {
            this.referrals = referrals;
        }

        public List getReferees() {
            return referees;
        }

        public void setReferees(List referees) {
            this.referees = referees;
        }

        public boolean isServiceDetailVisible() {
            return serviceDetailVisible;
        }

        public void setServiceDetailVisible(boolean serviceDetailVisible) {
            this.serviceDetailVisible = serviceDetailVisible;
        }

        public boolean isPercentageRequired() {
            return percentageRequired;
        }

        public void setPercentageRequired(boolean percentageRequired) {
            this.percentageRequired = percentageRequired;
        }

        public List getReferralsPharmacy() {
            return referralsPharmacy;
        }

        public void setReferralsPharmacy(List referralsPharmacy) {
            this.referralsPharmacy = referralsPharmacy;
        }

        public List getReferralsClinic() {
            return referralsClinic;
        }

        public void setReferralsClinic(List referralsClinic) {
            this.referralsClinic = referralsClinic;
        }

        public List getReferralsLab() {return referralsLab;}
        public void setReferralsLab(List referralsLab){this.referralsLab = referralsLab;}


}

