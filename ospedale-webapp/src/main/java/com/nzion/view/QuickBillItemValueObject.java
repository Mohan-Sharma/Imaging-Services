package com.nzion.view;

import java.math.BigDecimal;


public class QuickBillItemValueObject   { 
	
	public String miscellaneousChargeName;
	
    public Integer quantity = 1;
	
	public BigDecimal price;
	
	public BigDecimal totalAmount;
	
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getMiscellaneousChargeName() {
		return miscellaneousChargeName;
	}
	public void setMiscellaneousChargeName(String miscellaneousChargeName) {
		this.miscellaneousChargeName = miscellaneousChargeName;
	}
	public int getQuantity() {
		return quantity;
	}
	public BigDecimal getPrice() {
		return price;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
		computeTotalAmount();
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
		computeTotalAmount();
	}
	
	private void computeTotalAmount(){
		if(price!=null && quantity!=null)
		this.totalAmount =price.multiply(new BigDecimal(quantity)); 
	}
	
}
