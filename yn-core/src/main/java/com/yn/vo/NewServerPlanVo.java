package com.yn.vo;

import java.math.BigDecimal;

import com.yn.model.Inverter;
import com.yn.model.SolarPanel;

import javax.persistence.Column;

public class NewServerPlanVo {
	
	private Long id;
	private Long serverId;
	
	private Long batteryBoardId;
	private SolarPanel solarPanel;
	 
	private Long inverterId;
	private Inverter inverter;
	
	private BigDecimal capacity;
	
	private String materialJson;
	
	private Double minPurchase;
	private Double unitPrice;
	
	private Double warrantyYear;
	
	private BigDecimal warPeriod;
	
	private String planImgUrl;
	
	private Long factionId;
	
	private int planId;
	
	private Integer type;
	
	private int del;
	
	
	private String planName;

	private Double batteryBoardShelfLife;

	private Double batteryBoardWarrantyYear;


	private Double inverterShelfLife;

	private Double inverterWarrantyYear;

	private Double conversionEfficiency;

	
	public Double getConversionEfficiency() {
		return conversionEfficiency;
	}

	public void setConversionEfficiency(Double conversionEfficiency) {
		this.conversionEfficiency = conversionEfficiency;
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

	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public int getDel() {
		return del;
	}
	public void setDel(int del) {
		this.del = del;
	}
	public Long getFactionId() {
		return factionId;
	}
	public void setFactionId(Long factionId) {
		this.factionId = factionId;
	}
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public String getPlanImgUrl() {
		return planImgUrl;
	}
	public void setPlanImgUrl(String planImgUrl) {
		this.planImgUrl = planImgUrl;
	}
	
	public BigDecimal getWarPeriod() {
		return warPeriod;
	}
	public void setWarPeriod(BigDecimal warPeriod) {
		this.warPeriod = warPeriod;
	}
	public Double getMinPurchase() {
		return minPurchase;
	}
	public void setMinPurchase(Double minPurchase) {
		this.minPurchase = minPurchase;
	}
	public Double getWarrantyYear() {
		return warrantyYear;
	}
	public void setWarrantyYear(Double warrantyYear) {
		this.warrantyYear = warrantyYear;
	}
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
		
		public Double getUnitPrice() {
			return unitPrice;
		}
		public void setUnitPrice(Double unitPrice) {
			this.unitPrice = unitPrice;
		}
	
	
	
	

}
