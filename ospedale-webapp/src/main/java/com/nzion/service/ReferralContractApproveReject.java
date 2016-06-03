package com.nzion.service;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.nzion.domain.ReferralContract;
import com.nzion.hibernate.ext.multitenant.TenantIdHolder;
import com.nzion.service.common.CommonCrudService;

public class ReferralContractApproveReject extends HttpServlet{
	
	@Autowired
    CommonCrudService commonCrudService;
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }
	
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referralClinicId = request.getParameter("referralClinicId");
        if(referralClinicId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Referral Clinic Id cannot be null");
            return;
        }
        
        String referralContractId = request.getParameter("referralContractId");
        if(referralContractId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ReferralContractId cannot be null");
            return;
        }
        String status = request.getParameter("status");
        if(status == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Status cannot be null");
            return;
        }
        if("ACCEPTED".equals(status)){
        	referralApprove(referralContractId,referralClinicId,commonCrudService);
        	response.setStatus(HttpServletResponse.SC_OK, "Accepted successfully");
        }
        if("REJECTED".equals(status)){
        	referralReject(referralContractId,referralClinicId,commonCrudService);
        	response.setStatus(HttpServletResponse.SC_OK, "Rejected successfully");
        }
	}
	
	public void referralApprove(String referralContractId, String tenantId,CommonCrudService commCrudService){
		TenantIdHolder.setTenantId(tenantId);
		ReferralContract referralContract = commCrudService.getById(ReferralContract.class, Long.valueOf(referralContractId));
		referralContract.setContractStatus("ACCEPTED");
		commCrudService.save(referralContract);
    	
    	TenantIdHolder.setTenantId(referralContract.getRefereeClinicId());
    	ReferralContract refContract = commCrudService.findUniqueByEquality(ReferralContract.class, new String[]{"referralClinicId","refereeClinicId"},
    			new Object[]{referralContract.getReferralClinicId(),referralContract.getRefereeClinicId()});
    	refContract.setContractStatus("ACCEPTED");
    	commCrudService.save(refContract);

        //code start for communication loop
        /*final ReferralContract referralContract1 = referralContract;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> refereeClinicDet = RestServiceConsumer.getClinicDetailsByClinicId(referralContract1.getRefereeClinicId());
                Map<String, Object> userLoginMap = AfyaServiceConsumer.getUserLoginByTenantId(referralContract1.getRefereeClinicId());
                if((userLoginMap != null) && (userLoginMap.get("languagePreference") != null)) {
                    refereeClinicDet.put("languagePreference", userLoginMap.get("languagePreference").toString());
                }
                Map<String, Object> referralClinicDet = RestServiceConsumer.getClinicDetailsByClinicId(referralContract1.getReferralClinicId());
                refereeClinicDet.put("referralName", referralClinicDet.get("admin_first_name")+" "+referralClinicDet.get("admin_last_name"));
                refereeClinicDet.put("referralClinicName", referralClinicDet.get("clinic_name"));
                refereeClinicDet.put("key", TemplateNames.REFERRAL_CONTRACT_ACCEPTED_SMS.name());
                try {
                    SmsUtil.sendSmsForReferral(refereeClinicDet);
                }catch (Exception e){
                    e.printStackTrace();
                }
                refereeClinicDet.put("firstName", refereeClinicDet.get("admin_first_name"));
                refereeClinicDet.put("lastName", refereeClinicDet.get("admin_last_name"));
                refereeClinicDet.put("template", TemplateNames.REFERRAL_CONTRACT_ACCEPTED_MAIL.name());
                refereeClinicDet.put("subject", "Referral Contract Request Accepted");
                try {
                    EmailUtil.sendNetworkContractStatusMail(refereeClinicDet);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    //comunication to receipient
                    Map<String, Object> userLoginMap1 = AfyaServiceConsumer.getUserLoginByTenantId(referralContract1.getReferralClinicId());
                    if((userLoginMap1 != null) && (userLoginMap1.get("languagePreference") != null)) {
                        referralClinicDet.put("languagePreference", userLoginMap1.get("languagePreference").toString());
                    }
                    referralClinicDet.put("refereeName", refereeClinicDet.get("admin_first_name")+" "+refereeClinicDet.get("admin_last_name"));
                    referralClinicDet.put("key", TemplateNames.REFERRAL_CONTRACT_ACCEPTED_SMS_RECEIPIENT.name());
                    SmsUtil.sendSmsForReferral(referralClinicDet);

                    referralClinicDet.put("firstName", referralClinicDet.get("admin_first_name"));
                    referralClinicDet.put("lastName", referralClinicDet.get("admin_last_name"));
                    referralClinicDet.put("template", TemplateNames.REFERRAL_CONTRACT_ACCEPTED_MAIL_RECEIPIENT.name());
                    referralClinicDet.put("subject", "Referral Contract Request Accepted");

                    EmailUtil.sendNetworkContractStatusMail(referralClinicDet);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();*/
        //code end for communication loop
	}
	
	public void referralReject(String referralContractId, String tenantId,CommonCrudService commCrudService){
		TenantIdHolder.setTenantId(tenantId);
		ReferralContract referralContract = commCrudService.getById(ReferralContract.class, Long.valueOf(referralContractId));
		referralContract.setContractStatus("REJECTED");
		commCrudService.save(referralContract);
    	
    	TenantIdHolder.setTenantId(referralContract.getRefereeClinicId());
    	ReferralContract refContract = commCrudService.findUniqueByEquality(ReferralContract.class, new String[]{"referralClinicId","refereeClinicId"},
    			new Object[]{referralContract.getReferralClinicId(),referralContract.getRefereeClinicId()});
    	refContract.setContractStatus("REJECTED");
    	commCrudService.save(refContract);

        //code start for communication loop
        /*final ReferralContract referralContract1 = referralContract;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> refereeClinicDet = RestServiceConsumer.getClinicDetailsByClinicId(referralContract1.getRefereeClinicId());
                    Map<String, Object> userLoginMap = AfyaServiceConsumer.getUserLoginByTenantId(referralContract1.getRefereeClinicId());
                    if((userLoginMap != null) && (userLoginMap.get("languagePreference") != null)) {
                        refereeClinicDet.put("languagePreference", userLoginMap.get("languagePreference").toString());
                    }
                    Map<String, Object> referralClinicDet = RestServiceConsumer.getClinicDetailsByClinicId(referralContract1.getReferralClinicId());
                    refereeClinicDet.put("refereeName", referralClinicDet.get("admin_first_name")+" "+referralClinicDet.get("admin_last_name"));
                    refereeClinicDet.put("key", TemplateNames.REFERRAL_CONTRACT_REJECTED_SMS.name());
                    try {
                        SmsUtil.sendSmsForReferral(refereeClinicDet);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    refereeClinicDet.put("firstName", refereeClinicDet.get("admin_first_name"));
                    refereeClinicDet.put("lastName", refereeClinicDet.get("admin_last_name"));
                    refereeClinicDet.put("template", TemplateNames.REFERRAL_CONTRACT_REJECTED_MAIL.name());
                    refereeClinicDet.put("subject", "Referral Contract Request Rejected");
                    try {
                        EmailUtil.sendNetworkContractStatusMail(refereeClinicDet);
                    }catch (Exception e){
                        e.printStackTrace();
                }
            }
        }).start();*/
        //code end for communication loop
	}

}
