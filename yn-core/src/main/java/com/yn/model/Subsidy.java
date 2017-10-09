package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 地区模拟收益
 */
@Entity
public class Subsidy extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(11) comment '[省id]'")
	private Long provinceId;
	@Column(columnDefinition = "varchar(255) comment '[省名称]'")
	private String provinceText;
	@Column(columnDefinition = "int(11) comment '[城市id]'")
	private Long cityId;
	@Column(columnDefinition = "varchar(255) comment '[城市名称]'")
	private String cityText;
	@Column(columnDefinition = "int(1) comment '[类型]{1:居民,2:工业,3:商业,4:农业}'")
	private Integer type;
	@Column(columnDefinition = "int(11) comment '[城市日照量 小时/天]'")
	private Integer sunCount;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,4) comment '[用电价格 元/度]'")
	private Double usePrice;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,4) comment '[售电价格 元/度]'")
	private Double sellPrice; 
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[出售电量比]'")
	private Double sellProportion;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[地方补贴 元/度]'")
	private Double areaSubsidyPrice;
	@Column(columnDefinition = "int(11) comment '[地方补贴年限]'")
	private Integer areaSubsidyYear;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[优能补贴 元/度]'")
	private Double unSubsidyPrice;
	@Column(columnDefinition = "int(11) comment '[优能补贴年限]'")
	private Integer unSubsidyYear;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[初装补贴 元/kw]'")
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
