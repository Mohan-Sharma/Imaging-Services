package com.nzion.service;

import java.util.List;

import com.nzion.domain.docmgmt.FolderCategory;

public interface CategoryService {
	
	void addCategory(FolderCategory category);
	
	List<FolderCategory> getChildCategories(FolderCategory category);
}

