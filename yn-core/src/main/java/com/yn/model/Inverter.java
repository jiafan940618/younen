package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

/**
 * 逆变器的表
 * */


@Entity
public class Inverter extends IDomain implements Serializable {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) comment '[id]'")
    private Long id;
	@Column(columnDefinition = "int(3) comment '[品牌编号]'")
	private Integer brandId;
	@Column(columnDefinition = "varchar(255) comment '[品牌名]'")
	private String brandName;
	@Column(columnDefinition = "varchar(255) comment '[型号]'")
	private String model;
	@Column(columnDefinition = "int(1) comment '[相数,1、单相 3、三相]'")
	private Integer phases;
	@Column(columnDefinition = "int(3) comment '[电压]'")
	private Integer voltage;
	@Column(columnDefinition = "int(3) comment '[频率]'")
	private Integer frequency;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[逆变器板质保期 年]'")
	private Double qualityAssurance;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[逆变器保修年限 年]'")
	private Double boardYear;
	
	private Integer type;


	public Inverter() {}

	public Inverter(Long id, String model,Double qualityAssurance) {
		this.id = id;
		this.model = model;
		this.qualityAssurance = qualityAssurance;
	}
	
	

	public Inverter(Long id, String brandName, String model,Integer brandId) {
		super();
		this.id = id;
		this.brandName = brandName;
		this.model = model;
		this.brandId = brandId;
	}

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Integer getPhases() {
		return phases;
	}

	public void setPhases(Integer phases) {
		this.phases = phases;
	}

	public Integer getVoltage() {
		return voltage;
	}

	public void setVoltage(Integer voltage) {
		this.voltage = voltage;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Double getQualityAssurance() {
		return qualityAssurance;
	}

	public void setQualityAssurance(Double qualityAssurance) {
		this.qualityAssurance = qualityAssurance;
	}

	public Double getBoardYear() {
		return boardYear;
	}

	public void setBoardYear(Double boardYear) {
		this.boardYear = boardYear;
	}
	
	

}
