package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yn.domain.IDomain;

/** 
 * 电池板
 * */

@Entity
public class SolarPanel extends IDomain implements Serializable {
	@Column(columnDefinition = "int(3) comment '[品牌编号]'")
	private Integer brandId;
	@Column(columnDefinition = "varchar(255) comment '[品牌名]'")
	private String brandName;
	@Column(columnDefinition = "varchar(255) comment '[型号]'")
	private String model;
	@Column(columnDefinition = "int(1) comment '[类型:0:单晶硅  1：多晶硅]'")
	private int type;
	@Column(columnDefinition = "int(2) comment '[转换效率]'")
	private int conversionEfficiency;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[电池板质保期 年]'")
	private Double qualityAssurance;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[保修年限 年]'")
	private Double boardYear;

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
