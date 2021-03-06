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
 * 电池板的
 * */

@Entity
public class SolarPanel extends IDomain implements Serializable {
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
	@Column(columnDefinition = "int(1) comment '[类型:0:单晶硅  1：多晶硅]'")
	private Integer type;
	@Column(columnDefinition = "int(2) comment '[转换效率]'")
	private Double conversionEfficiency;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[电池组件保期 年]'")
	private Double qualityAssurance;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[附件年限 年]'")
	private Double boardYear;
	@Column(columnDefinition = "varchar(255) comment '[电池板的每平米发电功率]'")
	private String powerGeneration;
	

	public SolarPanel() {}

	public SolarPanel(Long id, String model,Double conversionEfficiency,String powerGeneration,Double qualityAssurance) {
		this.id = id;
		this.model = model;
		this.conversionEfficiency = conversionEfficiency;
		this.powerGeneration = powerGeneration;
		this.qualityAssurance = qualityAssurance;
	}
	
	
	

	public SolarPanel(Long id, String model, String brandName,Integer brandId) {
		this.id = id;
		this.brandName = brandName;
		this.model = model;
		this.brandId = brandId;
	}

	public String getPowerGeneration() {
		return powerGeneration;
	}

	public void setPowerGeneration(String powerGeneration) {
		this.powerGeneration = powerGeneration;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	

	public Double getConversionEfficiency() {
		return conversionEfficiency;
	}

	public void setConversionEfficiency(Double conversionEfficiency) {
		this.conversionEfficiency = conversionEfficiency;
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
