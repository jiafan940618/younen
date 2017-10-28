package com.yn.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.yn.domain.IDomain;

@Entity
@Table(name="new_server_plan")
public class NewServerPlan extends IDomain implements Serializable {
	
	@Column(columnDefinition = "decimal(12,2) comment '[保修期]'")
	private BigDecimal warPeriod;
	
	@Column(columnDefinition = "int(11) comment '[服务商id]'")
	private Long serverId;
	
	@Column(columnDefinition = "int(11) comment '[电池板id]'")
	private Long batteryboardId;
	@Column(columnDefinition = "String(255) comment '[服务方案图片]'")
	private String planImgUrl;
	@Column(columnDefinition = "int(11) comment '[逆变器id]'")
	private Long inverterId;

	@Column(columnDefinition = "text comment '[其他材料jsonText]'")
	private String materialJson;
	
	@Column(columnDefinition = "int(11) comment '[最低购买千瓦数]'")
	private Integer minPurchase;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[元/千瓦]'")
	private Double unitPrice;
		
	 /** 用来标识方案的，方便查询出该id下的所有方案*/
	@Column(columnDefinition = "int(11) comment '[标识id]'")
	private Long factionId;
	
	private Double capacity;

	
	
	
	

	/** 电池板*/
	@OneToOne
	    @JoinColumn(name = "batteryboardId", insertable = false, updatable = false)
	    private SolarPanel solarPanel;
	  /** 逆变器 */ 
	   @OneToOne
	    @JoinColumn(name = "inverterId", insertable = false, updatable = false)
	    private Inverter inverter;
 
	public NewServerPlan() {}
	
	

	public NewServerPlan(Long serverId, Integer minPurchase, Double unitPrice) {
		this.serverId = serverId;
		this.minPurchase = minPurchase;
		this.unitPrice = unitPrice;
	}

	
	public Long getFactionId() {
		return factionId;
	}



	public void setFactionId(Long factionId) {
		this.factionId = factionId;
	}



	public Double getCapacity() {
		return capacity;
	}



	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}



	public BigDecimal getWarPeriod() {
		return warPeriod;
	}



	public void setWarPeriod(BigDecimal warPeriod) {
		this.warPeriod = warPeriod;
	}



	public String getPlanImgUrl() {
		return planImgUrl;
	}



	public void setPlanImgUrl(String planImgUrl) {
		this.planImgUrl = planImgUrl;
	}

	public Long getServerId() {
		return serverId;
	}

	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}


	public Long getBatteryboardId() {
		return batteryboardId;
	}

	public void setBatteryboardId(Long batteryboardId) {
		this.batteryboardId = batteryboardId;
	}

	public Long getInverterId() {
		return inverterId;
	}

	public void setInverterId(Long inverterId) {
		this.inverterId = inverterId;
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

	public SolarPanel getSolarPanel() {
		return solarPanel;
	}

	public void setSolarPanel(SolarPanel solarPanel) {
		this.solarPanel = solarPanel;
	}

	public Inverter getInverter() {
		return inverter;
	}

	public void setInverter(Inverter inverter) {
		this.inverter = inverter;
	}
	
	   
	   

}
