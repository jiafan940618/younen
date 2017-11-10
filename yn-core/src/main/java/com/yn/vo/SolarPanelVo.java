package com.yn.vo;

import java.util.List;

public class SolarPanelVo {
	
	private Long s_id;
	
	private String companyName;
	
	private Integer type;
	
	private Double conversionEfficiency;
	
	private Double qualityAssurance;
	
	private Double boardYear;

	private String companyLogo;
	
	private String typeName;
	
	private int id; 
	
	
	
	private List<QualificationsVo> list;
	

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<QualificationsVo> getList() {
		return list;
	}

	public void setList(List<QualificationsVo> list) {
		this.list = list;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public Long getS_id() {
		return s_id;
	}

	public void setS_id(Long s_id) {
		this.s_id = s_id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
