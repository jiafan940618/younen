package com.yn.vo;

import java.util.Date;

import com.yn.domain.QueryVo;

/**
 * 电站
 */
public class StationVo extends QueryVo{

	private Long id;
	private String stationName;
	private String stationCode;
	private Long orderId;
	private Long userId;
	private Long serverId;
	private String linkMan; 
	private String linkPhone;
	private Double capacity;
	private Long provinceId;
	private String provinceText;
	private Long cityId;
	private String cityText;
	private String addressText;
	private Integer workTotaTm;
	private Double workTotaKwh;
	private Double nowKw;
	private Date workDtm;
	private String devConfCode;
	private Integer status;
	private String privilegeCode;
	private Integer passageModel;
	private Integer type;
	
	private String remark;
	
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	public Double getCapacity() {
		return capacity;
	}
	public void setCapacity(Double capacity) {
		this.capacity = capacity;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceText() {
		return provinceText;
	}
	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityText() {
		return cityText;
	}
	public void setCityText(String cityText) {
		this.cityText = cityText;
	}
	public String getAddressText() {
		return addressText;
	}
	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}
	public Integer getWorkTotaTm() {
		return workTotaTm;
	}
	public void setWorkTotaTm(Integer workTotaTm) {
		this.workTotaTm = workTotaTm;
	}
	public Double getWorkTotaKwh() {
		return workTotaKwh;
	}
	public void setWorkTotaKwh(Double workTotaKwh) {
		this.workTotaKwh = workTotaKwh;
	}
	public Double getNowKw() {
		return nowKw;
	}
	public void setNowKw(Double nowKw) {
		this.nowKw = nowKw;
	}
	public Date getWorkDtm() {
		return workDtm;
	}
	public void setWorkDtm(Date workDtm) {
		this.workDtm = workDtm;
	}
	public String getDevConfCode() {
		return devConfCode;
	}
	public void setDevConfCode(String devConfCode) {
		this.devConfCode = devConfCode;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getPassageModel() {
		return passageModel;
	}
	public void setPassageModel(Integer passageModel) {
		this.passageModel = passageModel;
	}
	public String getLinkMan() {
		return linkMan;
	}
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}
	public String getLinkPhone() {
		return linkPhone;
	}
	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}
}
