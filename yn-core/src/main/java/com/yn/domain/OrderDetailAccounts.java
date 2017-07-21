package com.yn.domain;

public class OrderDetailAccounts {
	
	private Integer orderNum = 0; // 订单数
	private Double priceTol = 0d; // 订单总金额
	private Double applyingPriceTol = 0d; // 申请中总金额
	private Double buildingPriceTol = 0d; // 施工中总金额
	private Double gridConnectedingPriceTol = 0d; // 并网申请中总金额
	private Double gridConnectedPriceTol = 0d; // 并网发电总金额
	private Double factoragePriceTol = 0d; // 优能服务费总金额
	private Double apolegamyPriceTol = 0d; // 选配项目总金额
	private Double hadPayPriceTol = 0d; // 已支付总金额
	
	
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	public Double getPriceTol() {
		return priceTol;
	}
	public void setPriceTol(Double priceTol) {
		this.priceTol = priceTol;
	}
	public Double getApplyingPriceTol() {
		return applyingPriceTol;
	}
	public void setApplyingPriceTol(Double applyingPriceTol) {
		this.applyingPriceTol = applyingPriceTol;
	}
	public Double getBuildingPriceTol() {
		return buildingPriceTol;
	}
	public void setBuildingPriceTol(Double buildingPriceTol) {
		this.buildingPriceTol = buildingPriceTol;
	}
	public Double getGridConnectedingPriceTol() {
		return gridConnectedingPriceTol;
	}
	public void setGridConnectedingPriceTol(Double gridConnectedingPriceTol) {
		this.gridConnectedingPriceTol = gridConnectedingPriceTol;
	}
	public Double getGridConnectedPriceTol() {
		return gridConnectedPriceTol;
	}
	public void setGridConnectedPriceTol(Double gridConnectedPriceTol) {
		this.gridConnectedPriceTol = gridConnectedPriceTol;
	}
	public Double getFactoragePriceTol() {
		return factoragePriceTol;
	}
	public void setFactoragePriceTol(Double factoragePriceTol) {
		this.factoragePriceTol = factoragePriceTol;
	}
	public Double getApolegamyPriceTol() {
		return apolegamyPriceTol;
	}
	public void setApolegamyPriceTol(Double apolegamyPriceTol) {
		this.apolegamyPriceTol = apolegamyPriceTol;
	}
	public Double getHadPayPriceTol() {
		return hadPayPriceTol;
	}
	public void setHadPayPriceTol(Double hadPayPriceTol) {
		this.hadPayPriceTol = hadPayPriceTol;
	}
}
