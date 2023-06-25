package com.mybootapp.main.dto;

import org.springframework.stereotype.Component;

	@Component
	public class InwardRegisterDto {
		private String productTitle;
		private int productQuantity;
		private double productPrice;
		public double getProductPrice() {
			return productPrice;
		}

		public void setProductPrice(double productPrice) {
			this.productPrice = productPrice;
		}

		private String supplierName;
		private String supplierCity;

		public String getProductTitle() {
			return productTitle;
		}

		public void setProductTitle(String productTitle) {
			this.productTitle = productTitle;
		}

		public int getProductQuantity() {
			return productQuantity;
		}

		public void setProductQuantity(int productQuantity) {
			this.productQuantity = productQuantity;
		}


		public String getSupplierName() {
			return supplierName;
		}

		public void setSupplierName(String supplierName) {
			this.supplierName = supplierName;
		}

		public String getSupplierCity() {
			return supplierCity;
		}

		public void setSupplierCity(String supplierCity) {
			this.supplierCity = supplierCity;
		}

	}
	
	

