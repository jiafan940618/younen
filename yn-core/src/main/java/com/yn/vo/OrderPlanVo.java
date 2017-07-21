package com.yn.vo;

/**
 * 订单方案
 */
public class OrderPlanVo {
	
	private Long id;
	private Long orderId;
	
	private Long batteryBoardId;
	private String batteryBoardBrand;
	private String batteryBoardName;
	private String batteryBoardModel;
	private Double batteryBoardShelfLife;
	private Double batteryBoardWarrantyYear;
	
	private Long inverterId;
	private String inverterBrand;
	private String inverterName;
	private String inverterModel;
	private Double inverterShelfLife;
	private Double inverterWarrantyYear;
	
	private String otherMaterialJsonText;
	
	
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getBatteryBoardId() {
		return batteryBoardId;
	}
	public void setBatteryBoardId(Long batteryBoardId) {
		this.batteryBoardId = batteryBoardId;
	}
	public String getBatteryBoardBrand() {
		return batteryBoardBrand;
	}
	public void setBatteryBoardBrand(String batteryBoardBrand) {
		this.batteryBoardBrand = batteryBoardBrand;
	}
	public String getBatteryBoardName() {
		return batteryBoardName;
	}
	public void setBatteryBoardName(String batteryBoardName) {
		this.batteryBoardName = batteryBoardName;
	}
	public String getBatteryBoardModel() {
		return batteryBoardModel;
	}
	public void setBatteryBoardModel(String batteryBoardModel) {
		this.batteryBoardModel = batteryBoardModel;
	}
	public Double getBatteryBoardShelfLife() {
		return batteryBoardShelfLife;
	}
	public void setBatteryBoardShelfLife(Double batteryBoardShelfLife) {
		this.batteryBoardShelfLife = batteryBoardShelfLife;
	}
	public Double getBatteryBoardWarrantyYear() {
		return batteryBoardWarrantyYear;
	}
	public void setBatteryBoardWarrantyYear(Double batteryBoardWarrantyYear) {
		this.batteryBoardWarrantyYear = batteryBoardWarrantyYear;
	}
	public Long getInverterId() {
		return inverterId;
	}
	public void setInverterId(Long inverterId) {
		this.inverterId = inverterId;
	}
	public String getInverterBrand() {
		return inverterBrand;
	}
	public void setInverterBrand(String inverterBrand) {
		this.inverterBrand = inverterBrand;
	}
	public String getInverterName() {
		return inverterName;
	}
	public void setInverterName(String inverterName) {
		this.inverterName = inverterName;
	}
	public String getInverterModel() {
		return inverterModel;
	}
	public void setInverterModel(String inverterModel) {
		this.inverterModel = inverterModel;
	}
	public Double getInverterShelfLife() {
		return inverterShelfLife;
	}
	public void setInverterShelfLife(Double inverterShelfLife) {
		this.inverterShelfLife = inverterShelfLife;
	}
	public Double getInverterWarrantyYear() {
		return inverterWarrantyYear;
	}
	public void setInverterWarrantyYear(Double inverterWarrantyYear) {
		this.inverterWarrantyYear = inverterWarrantyYear;
	}
	public String getOtherMaterialJsonText() {
		return otherMaterialJsonText;
	}
	public void setOtherMaterialJsonText(String otherMaterialJsonText) {
		this.otherMaterialJsonText = otherMaterialJsonText;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
