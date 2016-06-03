package com.nzion.zkoss.composer.docmgmt;

import java.util.ArrayList;
import java.util.List;

import com.nzion.zkoss.composer.OspedaleAutowirableComposer;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.TreeModel;

import com.nzion.domain.docmgmt.FolderCategory;
import com.nzion.service.CategoryService;

public class CategoryComposer extends OspedaleAutowirableComposer {

	private CategoryService categoryService;

	public CategoryService getCategoryService() {
	return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
	this.categoryService = categoryService;
	}

	private TreeModel treeModel;
	private FolderCategory category;

	public FolderCategory getCategory() {
	return category;
	}

	public void setCategory(FolderCategory category) {
	this.category = category;
	}

	public TreeModel getTreeModel() {
	List<FolderCategory> categories = categoryService.getChildCategories(null);
	List<DefaultTreeNode> nodes = createTreeNodes(categories);
	treeModel = new DefaultTreeModel(new DefaultTreeNode("Categories", nodes));
	return treeModel;
	}

	public void setTreeModel(TreeModel treeModel) {
	this.treeModel = treeModel;
	}

	@Override
	public void doAfterCompose(Component component) throws Exception {
	super.doAfterCompose(component);
	}

	private List<DefaultTreeNode> createTreeNodes(List<FolderCategory> categories) {
	List<DefaultTreeNode> nodes = new ArrayList<DefaultTreeNode>();
	for (FolderCategory cat : categories) {
		nodes.add(new DefaultTreeNode(cat, createTreeNodes(cat.getChildren())));
	}
	return nodes;
	}

	public void addCategory() {
	categoryService.addCategory(category);
	}

}
