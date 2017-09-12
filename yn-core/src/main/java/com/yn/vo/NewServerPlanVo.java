package com.yn.vo;

import java.math.BigDecimal;

import com.yn.model.Inverter;
import com.yn.model.SolarPanel;

public class NewServerPlanVo {
	
	private Long id;
	private Long serverId;
	
	private Long batteryBoardId;
	private SolarPanel solarPanel;
	 
	private Long inverterId;
	private Inverter inverter;
	
	private BigDecimal capacity;
	
	private String materialJson;
	
	private Integer minPurchase;
	private Double unitPrice;
	
	
	
	public BigDecimal getCapacity() {
		return capacity;
	}
	public void setCapacity(BigDecimal capacity) {
		this.capacity = capacity;
	}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Long getServerId() {
			return serverId;
		}
		public void setServerId(Long serverId) {
			this.serverId = serverId;
		}
		public Long getBatteryBoardId() {
			return batteryBoardId;
		}
		public void setBatteryBoardId(Long batteryBoardId) {
			this.batteryBoardId = batteryBoardId;
		}
		public SolarPanel getSolarPanel() {
			return solarPanel;
		}
		public void setSolarPanel(SolarPanel solarPanel) {
			this.solarPanel = solarPanel;
		}
		public Long getInverterId() {
			return inverterId;
		}
		public void setInverterId(Long inverterId) {
			this.inverterId = inverterId;
		}
		public Inverter getInverter() {
			return inverter;
		}
		public void setInverter(Inverter inverter) {
			this.inverter = inverter;
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
		public Double getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(Double unitPrice) {
			this.unitPrice = unitPrice;
		}
	
	
	
	

}
