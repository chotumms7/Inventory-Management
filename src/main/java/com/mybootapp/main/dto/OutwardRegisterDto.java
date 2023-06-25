package com.mybootapp.main.dto;

import org.springframework.stereotype.Component;

@Component
public class OutwardRegisterDto {

	private String productTitle;
    private double productQuantity;
    private String godownLocation;
    private String godownManager;
    private String receiptno;
	public String getProductTitle() {
		return productTitle;
	}
	public void setProductTitle(String productTitle) {
		this.productTitle = productTitle;
	}
	public double getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(double productQuantity) {
		this.productQuantity = productQuantity;
	}
	public String getGodownLocation() {
		return godownLocation;
	}
	public void setGodownLocation(String godownLocation) {
		this.godownLocation = godownLocation;
	}
	public String getGodownManager() {
		return godownManager;
	}
	public void setGodownManager(String godownManager) {
		this.godownManager = godownManager;
	}
	public String getReceiptno() {
		return receiptno;
	}
	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

}
