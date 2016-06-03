package com.nzion.report.dto;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.nzion.domain.Person;
import com.nzion.domain.billing.AcctgTransactionEntry;

public class CollectionReportDto implements Comparable<CollectionReportDto> {

    public String description;
    public Integer count;
    public BigDecimal amount;
    public List<CollectionReportItemDto> collectionReportItemDto;


    public CollectionReportDto() {
    }

	public List<CollectionReportItemDto> getCollectionReportItemDto() {
		return collectionReportItemDto;
	}


	public CollectionReportDto(String description, Integer count, BigDecimal amount, List<CollectionReportItemDto> collectionReportItemDto) {
        this.description = description;
        this.count = count;
        this.amount = amount;
        this.collectionReportItemDto = collectionReportItemDto;
    }

    public static CollectionReportDto transformToCollectionReportDto(Map<String, List<AcctgTransactionEntry>> accTransEntryMap, String description) {
        if (accTransEntryMap == null)
            return new CollectionReportDto(description, 0, BigDecimal.ZERO, Collections.EMPTY_LIST);
        List<CollectionReportItemDto> collectionReportItemDtoList = CollectionReportItemDto.getCollectionReportItemDtoList(accTransEntryMap);
        CollectionReportDto collectionReportDto = new CollectionReportDto(description, totalCount(collectionReportItemDtoList), totalAmount(collectionReportItemDtoList), collectionReportItemDtoList);
        return collectionReportDto;
    }

    public static BigDecimal totalAmount(List<CollectionReportItemDto> collectionReportItemDtoList) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CollectionReportItemDto collectionReportItemDto : collectionReportItemDtoList)
            totalAmount = totalAmount.add(collectionReportItemDto.amount);
        return totalAmount;
    }

    public static Integer totalCount(List<CollectionReportItemDto> collectionReportItemDtoList) {
        Integer totalCount = 0;
        for (CollectionReportItemDto collectionReportItemDto : collectionReportItemDtoList)
            totalCount = totalCount + collectionReportItemDto.count;
        return totalCount;
    }

    @Override
    public int compareTo(CollectionReportDto collectionReportDto) {
        return this.description.compareToIgnoreCase(collectionReportDto.description);
    }
}
