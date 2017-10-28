package com.yn.vo;

import java.math.BigDecimal;

public class NewServerPlan02 {
	
	private Integer id;
	private Integer serverId;
	
	private Integer batteryBoardId;

	 
	private Integer inverterId;

	
	private BigDecimal capacity;
	
	private String materialJson;
	
	private Integer minPurchase;
	private BigDecimal unitPrice;
	
	private BigDecimal warrantyYear;
	
	
	 private newSolarPanel newsolarPanel;


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getServerId() {
		return serverId;
	}


	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}


	public Integer getBatteryBoardId() {
		return batteryBoardId;
	}


	public void setBatteryBoardId(Integer batteryBoardId) {
		this.batteryBoardId = batteryBoardId;
	}


	public Integer getInverterId() {
		return inverterId;
	}


	public void setInverterId(Integer inverterId) {
		this.inverterId = inverterId;
	}


	public BigDecimal getCapacity() {
		return capacity;
	}


	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}


	public String getMaterialJson() {
		return materialJson;
	}


	public void setMaterialJson(String materialJson) {
		this.materialJson = materialJson;
	}


	public Integer getMinPurchase() {
		return minPurchase;
	}


	public void setMinPurchase(Integer minPurchase) {
		this.minPurchase = minPurchase;
	}


	public BigDecimal getUnitPrice() {
		return unitPrice;
	}


	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}


	public BigDecimal getWarrantyYear() {
		return warrantyYear;
	}


	public void setWarrantyYear(BigDecimal warrantyYear) {
		this.warrantyYear = warrantyYear;
	}


	public newSolarPanel getNewsolarPanel() {
		return newsolarPanel;
	}


	public void setNewsolarPanel(newSolarPanel newsolarPanel) {
		this.newsolarPanel = newsolarPanel;
	}


	

}
