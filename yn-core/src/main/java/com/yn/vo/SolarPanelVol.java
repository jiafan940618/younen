package com.yn.vo;

public class SolarPanelVol {
	
	private Long id;

	private Integer brandId;

	private String brandName;

	private String model;

	private int type;

	private int conversionEfficiency;

	private Double qualityAssurance;

	private Double boardYear;

	private String powerGeneration;

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

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public String getPowerGeneration() {
		return powerGeneration;
	}

	public void setPowerGeneration(String powerGeneration) {
		this.powerGeneration = powerGeneration;
	}

}
