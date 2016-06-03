package com.nzion.view.component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.ListModelExt;
import org.zkoss.zul.event.ListDataEvent;

import com.nzion.util.UtilValidator;

/**
 * @author Sandeep Prusty
 * Nov 23, 2010
 * 
 * This class facilitates both client side and server side paging.
 * The constructor with List as an argument is used for client side paging model and the constructor with PagedDataModelService
 * as an argument creates server side paging model. 
 */

public class OspedalePagingListModel extends AbstractListModel implements ListModelExt{
	
	private List<? extends Object> list;
	
	// Total number or records in the data store.
	private long totalSizeInDataStore;
	
	private int pageSize = 10;
	
	private int currentStartIndex;
	
	private int currentEndIndex;
	
	private PagedDataModelService pagedService;
	
	public OspedalePagingListModel(int pageSize, long totalSizeInDataStore, PagedDataModelService pagedService) {
		this.pageSize = pageSize;
		this.totalSizeInDataStore = totalSizeInDataStore;
		this.pagedService = pagedService;
		currentStartIndex = 0;
		list = pagedService.getData(currentStartIndex, pageSize);
		if(UtilValidator.isNotEmpty(list))
			currentEndIndex = list.size() - 1;
	}
	
	public OspedalePagingListModel(int pageSize, List<?> list) {
		this(pageSize, list.size(), new InMemoryPagedDataModelService(list));
	}	

	@Override
	public Object getElementAt(int index) {
	if(index < currentStartIndex || index > currentEndIndex){
		currentStartIndex = (index / pageSize) * pageSize;
		currentEndIndex = currentStartIndex + pageSize;
		list = pagedService.getData(currentStartIndex, pageSize);
	}
	return list.get(index % pageSize);
	}

	@Override
	public int getSize() {
	return (int)totalSizeInDataStore;
	}
	
	public interface PagedDataModelService {
		List<? extends Object> getData(int start, int pageSize);
	}

    @Override
    public String getSortDirection(Comparator cmpr) {
        return "";
    }

    @Override
	public void sort(Comparator cmpr, boolean ascending) {
		Collections.sort(list, cmpr);
		refresh();
	}
	
	public void refresh(){
	fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
	}
	
	public List<?> getAll(){
	return pagedService.getData(0, (int)totalSizeInDataStore);
	}
	
	private static class InMemoryPagedDataModelService implements PagedDataModelService {

		private List<?> list;
		
		public InMemoryPagedDataModelService(List<?> list) {
		this.list = list;
		}
		
		@Override
		public List<? extends Object> getData(int start, int pageSize) {
		int endIndex = start + pageSize;
		if(endIndex > list.size())
			endIndex = list.size();
		return list.subList(start, endIndex);
		}
	}
	
	private static final long serialVersionUID = 1L;
}