package com.yn.vo;

public class TemStationYearVo {

	private Long id;
	private Long stationId;
	private String stationCode;
	private Long serverId;
	private String devConfCode;
	private Long d_addr;
	private Integer d_type;
	private Integer w_addr;
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
	public Long getD_addr() {
		return d_addr;
	}
	public void setD_addr(Long d_addr) {
		this.d_addr = d_addr;
	}
	public Integer getD_type() {
		return d_type;
	}
	public void setD_type(Integer d_type) {
		this.d_type = d_type;
	}
	public Integer getW_addr() {
		return w_addr;
	}
	public void setW_addr(Integer w_addr) {
		this.w_addr = w_addr;
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
