package com.nzion.view.component;


public class AutoCompletionItem{

	public AutoCompletionItem(Long id, String description) {
		this.id = id;
		this.description = description;
	}

	public AutoCompletionItem() {
	}

	private Long id;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "{id:\""+id.toString()+"\",desc:\""+description+"\"}";
	}
}
