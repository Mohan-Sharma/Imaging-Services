package com.nzion.report.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.nzion.domain.billing.AcctgTransTypeEnum;
import com.nzion.domain.billing.AcctgTransactionEntry;
import com.nzion.domain.billing.DebitCreditEnum;


public class CollectionReportItemDto {

    public String description;
    public Integer count;
    public BigDecimal amount;


    private CollectionReportItemDto(String description, Integer count, BigDecimal amount) {
        this.description = description;
        this.count = count;
        this.amount = amount;
    }

    public static List<CollectionReportItemDto> getCollectionReportItemDtoList(Map<String, List<AcctgTransactionEntry>> accTransEntryMap) {

        List<CollectionReportItemDto> collectionReportItemDtoList = new ArrayList<CollectionReportItemDto>();
        for (String item : accTransEntryMap.keySet()) {
            CollectionReportItemDto collectionReportItemDto = new CollectionReportItemDto(item,getCount(accTransEntryMap.get(item)), getAmount(accTransEntryMap.get(item)));
            collectionReportItemDtoList.add(collectionReportItemDto);
        }
        Collections.sort(collectionReportItemDtoList, new Comparator<CollectionReportItemDto>() {
            @Override
            public int compare(CollectionReportItemDto c1, CollectionReportItemDto c2) {
                    return c2.amount.compareTo(c1.amount);
                }
            });       
        return collectionReportItemDtoList;
    }


    public static Integer getCount(List<AcctgTransactionEntry> acctgTransactionEntries){
    	Integer count=0;
    	Integer transactionCount=0;
    	for(AcctgTransactionEntry acctgTransactionEntry: acctgTransactionEntries){
    		if(DebitCreditEnum.DEBIT.equals(acctgTransactionEntry.getTransactionType().getDebitCreditType()))
    			transactionCount=acctgTransactionEntry.getDebitOrCredit().equals(DebitCreditEnum.DEBIT)? 1 : -1;
    			else
                    transactionCount = acctgTransactionEntry.getDebitOrCredit().equals(DebitCreditEnum.CREDIT) ? 1 : -1;
               count = count+transactionCount;
            }
            return count;
        }
    
    
    public static BigDecimal getAmount(List<AcctgTransactionEntry> acctgTransactionEntries) {
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal transactionAmount = BigDecimal.ZERO;
        for (AcctgTransactionEntry acctgTransactionEntry : acctgTransactionEntries) {
            if (DebitCreditEnum.DEBIT.equals(acctgTransactionEntry.getTransactionType().getDebitCreditType()))
                transactionAmount = acctgTransactionEntry.getDebitOrCredit().equals(DebitCreditEnum.DEBIT) ? acctgTransactionEntry.getAmount() : (BigDecimal.valueOf(-1d).multiply(acctgTransactionEntry.getAmount()));
            else
                transactionAmount = acctgTransactionEntry.getDebitOrCredit().equals(DebitCreditEnum.CREDIT) ? acctgTransactionEntry.getAmount() : (BigDecimal.valueOf(-1d).multiply(acctgTransactionEntry.getAmount()));
            amount = amount.add(transactionAmount);
        }
        return amount;
    }
}
