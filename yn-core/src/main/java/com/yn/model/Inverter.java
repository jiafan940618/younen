package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yn.domain.IDomain;

/**
 * 逆变器的表
 * */


@Entity
public class Inverter extends IDomain implements Serializable {
	@Column(columnDefinition = "int(3) comment '[品牌编号]'")
	private Integer brandId;
	@Column(columnDefinition = "varchar(255) comment '[品牌名]'")
	private String brandName;
	@Column(columnDefinition = "varchar(255) comment '[型号]'")
	private String model;
	@Column(columnDefinition = "int(1) comment '[相数]'")
	private int phases;
	@Column(columnDefinition = "int(3) comment '[电压]'")
	private int voltage;
	@Column(columnDefinition = "int(3) comment '[频率]'")
	private int frequency;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[逆变器板质保期 年]'")
	private Double qualityAssurance;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[逆变器保修年限 年]'")
	private Double boardYear;
	
	
	

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getPhases() {
		return phases;
	}

	public void setPhases(int phases) {
		this.phases = phases;
	}

	public int getVoltage() {
		return voltage;
	}

	public void setVoltage(int voltage) {
		this.voltage = voltage;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

}
