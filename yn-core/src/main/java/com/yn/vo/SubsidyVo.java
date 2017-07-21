package com.yn.vo;

import javax.validation.constraints.NotNull;

/**
 * 地区模拟收益
 */
public class SubsidyVo {
	
	private Long id;
	@NotNull(message = "provinceId不能为空")
	private Long provinceId;
	private String provinceText;
	@NotNull(message = "cityId不能为空")
	private Long cityId;
	private String cityText;
	@NotNull(message = "type不能为空")
	private Integer type;
	@NotNull(message = "sunCount不能为空")
	private Integer sunCount;
	@NotNull(message = "usePrice不能为空")
	private Double usePrice;
	@NotNull(message = "sellPrice不能为空")
	private Double sellPrice; 
	@NotNull(message = "sellProportion不能为空")
	private Double sellProportion;
	@NotNull(message = "areaSubsidyPrice不能为空")
	private Double areaSubsidyPrice;
	@NotNull(message = "areaSubsidyYear不能为空")
	private Integer areaSubsidyYear;
	@NotNull(message = "unSubsidyPrice不能为空")
	private Double unSubsidyPrice;
	@NotNull(message = "unSubsidyYear不能为空")
	private Integer unSubsidyYear;
	@NotNull(message = "initSubsidyPrice不能为空")
	private Double initSubsidyPrice;
	
	
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSunCount() {
		return sunCount;
	}
	public void setSunCount(Integer sunCount) {
		this.sunCount = sunCount;
	}
	public Double getUsePrice() {
		return usePrice;
	}
	public void setUsePrice(Double usePrice) {
		this.usePrice = usePrice;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	public Double getSellProportion() {
		return sellProportion;
	}
	public void setSellProportion(Double sellProportion) {
		this.sellProportion = sellProportion;
	}
	public Double getAreaSubsidyPrice() {
		return areaSubsidyPrice;
	}
	public void setAreaSubsidyPrice(Double areaSubsidyPrice) {
		this.areaSubsidyPrice = areaSubsidyPrice;
	}
	public Integer getAreaSubsidyYear() {
		return areaSubsidyYear;
	}
	public void setAreaSubsidyYear(Integer areaSubsidyYear) {
		this.areaSubsidyYear = areaSubsidyYear;
	}
	public Double getUnSubsidyPrice() {
		return unSubsidyPrice;
	}
	public void setUnSubsidyPrice(Double unSubsidyPrice) {
		this.unSubsidyPrice = unSubsidyPrice;
	}
	public Integer getUnSubsidyYear() {
		return unSubsidyYear;
	}
	public void setUnSubsidyYear(Integer unSubsidyYear) {
		this.unSubsidyYear = unSubsidyYear;
	}
	public Double getInitSubsidyPrice() {
		return initSubsidyPrice;
	}
	public void setInitSubsidyPrice(Double initSubsidyPrice) {
		this.initSubsidyPrice = initSubsidyPrice;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProvinceText() {
		return provinceText;
	}
	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}
	public String getCityText() {
		return cityText;
	}
	public void setCityText(String cityText) {
		this.cityText = cityText;
	}
}
