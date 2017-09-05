package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;

import com.yn.domain.IDomain;

public class SolarPanel extends IDomain implements Serializable {
	@Column(columnDefinition = "int(3) comment '[品牌编号]'")
	private Integer brandId;
	@Column(columnDefinition = "varchar(255) comment '[品牌名]'")
	private String brandName;
	@Column(columnDefinition = "varchar(255) comment '[型号]'")
	private String model;
	@Column(columnDefinition = "int(1) comment '[类型]'")
	private int type;
	@Column(columnDefinition = "int(2) comment '[转换效率]'")
	private int conversionEfficiency;
	@Column(columnDefinition = "int(2) comment '[质保年限]'")
	private int qualityAssurance;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getConversionEfficiency() {
		return conversionEfficiency;
	}

	public void setConversionEfficiency(int conversionEfficiency) {
		this.conversionEfficiency = conversionEfficiency;
	}

	public int getQualityAssurance() {
		return qualityAssurance;
	}

	public void setQualityAssurance(int qualityAssurance) {
		this.qualityAssurance = qualityAssurance;
	}

}
