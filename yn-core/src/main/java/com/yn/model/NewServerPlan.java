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
	private Double minPurchase;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[元/千瓦]'")
	private Double unitPrice;
		
	 /** 用来标识方案的，方便查询出该id下的所有方案*/
	@Column(columnDefinition = "int(11) comment '[标识id]'")
	private Long factionId;
	
	private int planId;

	private Double capacity;
	
	private Integer del;

	private String planName;
	
	private Integer type;

	/** 下面5个参数与电池板表与逆变器的表存在冲突，3.0以后以这个类为准来修改，电池表，逆变器的这几个参数全部去除*/
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[电池板质保期 年]'")
	private Double batteryBoardShelfLife;
	
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[电池板保修年限 年]'")
	private Double batteryBoardWarrantyYear;
	
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[逆变器质保期 年]'")
	private Double inverterShelfLife;
	
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[逆变器保修年限 年]'")
	private Double inverterWarrantyYear;

	@Column(columnDefinition = "int(2) comment '[转换效率]'")
	private Double conversionEfficiency;

	/** 电池板*/
	@OneToOne
	    @JoinColumn(name = "batteryboardId", insertable = false, updatable = false)
	    private SolarPanel solarPanel;
	  /** 逆变器 */ 
	   @OneToOne
	    @JoinColumn(name = "inverterId", insertable = false, updatable = false)
	    private Inverter inverter;
 
	public NewServerPlan() {}
	
	

	public NewServerPlan(Long serverId, Double minPurchase, Double unitPrice) {
		this.serverId = serverId;
		this.minPurchase = minPurchase;
		this.unitPrice = unitPrice;
	}

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



	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public int getPlanId() {
		return planId;
	}

	public void setPlanId(int planId) {
		this.planId = planId;
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

	public Double getMinPurchase() {
		return minPurchase;
	}



	public void setMinPurchase(Double minPurchase) {
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
