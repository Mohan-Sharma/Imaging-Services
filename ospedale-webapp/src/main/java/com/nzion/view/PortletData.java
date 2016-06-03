package com.nzion.view;

public class PortletData {

	private String url;

	private String title;

	private String imageUrl;

	private boolean refreshRequired;
	
	public PortletData(String title, String url, String imageUrl) {
	this.url = url;
	this.title = title;
	this.imageUrl = imageUrl;
	}
	
	public PortletData(String title, String url) {
	this.url = url;
	this.title = title;
	}
    	
	public PortletData(String title, String url, boolean refreshRequired) {
	this(title, url);
	this.refreshRequired = refreshRequired;
	}
	
	public PortletData(String title, String url, String imageUrl, boolean refreshRequired) {
	this(title, url, imageUrl);
	this.refreshRequired = refreshRequired;
	}

	public boolean isRefreshRequired() {
	return refreshRequired;
	}

	public String getUrl() {
	return url;
	}

	public String getTitle() {
	return title;
	}
	
	public String getImageUrl() {
	return imageUrl;
	}
}
