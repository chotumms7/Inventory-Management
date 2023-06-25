package com.mybootapp.main.dto;

import org.springframework.stereotype.Component;

@Component
public class GodownDto {
	private int godownId;
	public int getGodownId() {
		return godownId;
	}
	public void setGodownId(int godownId) {
		this.godownId = godownId;
	}
	public String getGodownLocation() {
		return godownLocation;
	}
	public void setGodownLocation(String godownLocation) {
		this.godownLocation = godownLocation;
	}
	public double getGodownCapacity() {
		return godownCapacity;
	}
	public void setGodownCapacity(double godownCapacity) {
		this.godownCapacity = godownCapacity;
	}
	public String getGodownManager() {
		return godownManager;
	}
	public void setGodownManager(String godownManager) {
		this.godownManager = godownManager;
	}
	private String godownLocation;
	private double godownCapacity;
	private String godownManager;

}
