package com.nzion.domain.docmgmt;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import com.nzion.domain.base.IdGeneratingBaseEntity;

@Entity
@Table(name = "DM_CATEGORY")
public class FolderCategory extends IdGeneratingBaseEntity {

	private static final long serialVersionUID = 1L;

	@ManyToOne(targetEntity = FolderCategory.class)
	@JoinColumn(name = "PARENT_ID")
	public FolderCategory getParent() {
		return parent;
	}

	public void setParent(FolderCategory parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private List<FolderCategory> children = new ArrayList<FolderCategory>();

	@OneToMany(mappedBy = "parent",fetch=FetchType.EAGER)
	public List<FolderCategory> getChildren() {
		return children;
	}

	public void setChildren(List<FolderCategory> children) {
		this.children = children;
	}

	public void addChild(FolderCategory child) {
		child.setParent(this);
		children.add(child);
	}

	private FolderCategory parent;
	private String name;
	private String description;
}
