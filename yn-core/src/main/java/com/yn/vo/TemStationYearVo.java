package com.yn.vo;

import com.yn.domain.QueryVo;

public class TemStationYearVo extends QueryVo{

	private Long id;
	private Long stationId;
	private String stationCode;
	private Long serverId;
	private String devConfCode;
	private Long dAddr;
	private Integer dType;
	private Integer wAddr;
	private Double kw;
	private Double kwh;
	private Integer type;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getDevConfCode() {
		return devConfCode;
	}
	public void setDevConfCode(String devConfCode) {
		this.devConfCode = devConfCode;
	}
	public Long getdAddr() {
		return dAddr;
	}
	public void setdAddr(Long dAddr) {
		this.dAddr = dAddr;
	}
	public Integer getdType() {
		return dType;
	}
	public void setdType(Integer dType) {
		this.dType = dType;
	}
	public Integer getwAddr() {
		return wAddr;
	}
	public void setwAddr(Integer wAddr) {
		this.wAddr = wAddr;
	}
	public Double getKw() {
		return kw;
	}
	public void setKw(Double kw) {
		this.kw = kw;
	}
	public Double getKwh() {
		return kwh;
	}
	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
}
