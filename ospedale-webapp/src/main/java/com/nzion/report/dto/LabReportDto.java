package com.nzion.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nzion.domain.Provider;
import com.nzion.domain.Referral;
import com.nzion.domain.billing.AcctgTransactionEntry;
import com.nzion.service.common.CommonCrudService;
import com.nzion.util.UtilValidator;

/**
 * @author: NthDimenzion
 * @since 1.0
 */
public class LabReportDto {

    private String labTestName;

    private int count;

    private BigDecimal amount;

    private String referredBy;

    public LabReportDto(String labTestName, int count, BigDecimal amount, String referredBy) {
        this.labTestName = labTestName;
        this.count = count;
        this.amount = amount;
        this.referredBy = referredBy;
    }

    public String getLabTestName() {
        return labTestName;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getReferredBy() {
        return referredBy;
    }
    
    /*public static LabReportDto transformToLabReportDto(List<AcctgTransactionEntry> acctgTransactionEntryList,CommonCrudService commonCrudService){
    	List<LabReportDto> labReportDtos=new ArrayList<LabReportDto>();
    	int count=0;
    	BigDecimal amount=BigDecimal.ZERO;
    	
    	
    	for(AcctgTransactionEntry acctgTransactionEntry:acctgTransactionEntryList){
    		amount=amount.add(acctgTransactionEntry.getAmount());
    	}
    	LabReportDto labReportDto=new LabReportDto(acctgTransactionEntryList.get(0).getDescription(),
    		acctgTransactionEntryList.size(),amount,"");
    		
    		
    		String doctorId=acctgTransactionEntry.getDoctorId();
    		String referralId=acctgTransactionEntry.getReferralId();
    		String referredBy=null;
    		if(doctorId!=null && UtilValidator.isNotEmpty(doctorId)){
			Provider doctor = commonCrudService.getById(Provider.class, new Long(doctorId.toString()));
			 referredBy=doctor.getFirstName()+" "+doctor.getLastName();
    		}else if(referralId!=null && UtilValidator.isNotEmpty(referralId)){
			Referral referralObj = commonCrudService.getById(Referral.class, new Long(referralId.toString()));
			 	referredBy=referralObj.getFirstName()+" "+referralObj.getLastName();
    		}else
			referredBy="";
    	
    		LabReportDto labReportDto=new LabReportDto(acctgTransactionEntry.getDescription(),1,acctgTransactionEntry.getAmount(),referredBy);
    		labReportDtos.add(labReportDto);
    		}
		return labReportDtos;
    }*/

    public static List<LabReportDto> transformToLabReportDtoList(Map<String,List<AcctgTransactionEntry>> acctgTransactionEntryListMap,String referredBy){
    	List<LabReportDto> labReportDtoList=new ArrayList<LabReportDto>();
    	for(String testName:acctgTransactionEntryListMap.keySet()){
    	
    		LabReportDto labReportDto=new LabReportDto(testName, acctgTransactionEntryListMap.get(testName).size(),
    				getAmount(acctgTransactionEntryListMap.get(testName)), referredBy);
    		labReportDtoList.add(labReportDto);
    	}
    	return labReportDtoList;
    	
    }
    
    
    public static BigDecimal getAmount(List<AcctgTransactionEntry> acctgTransactionEntryList){
    
    	BigDecimal amount=BigDecimal.ZERO;
    	for(AcctgTransactionEntry acctgTransactionEntry:acctgTransactionEntryList){
    		amount=amount.add(acctgTransactionEntry.getAmount());
    	}
    	return amount;
    }	
    	
    	
    	
    	
    	
    	

}
