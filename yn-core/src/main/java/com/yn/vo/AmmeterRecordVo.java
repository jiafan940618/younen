package com.yn.vo;

import java.util.Date;

import com.yn.domain.QueryVo;

public class AmmeterRecordVo extends QueryVo{

	private Long id;
	private Long stationId;
	private String stationCode;
	private String cAddr;
	private Long dAddr;
	private Integer dType;
	private Integer wAddr;
	private String statusCode;
	private Date recordDtm;
	private Integer type;
	private String date;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public String getStationCode() {
		return stationCode;
	}
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}
	public String getcAddr() {
		return cAddr;
	}
	public void setcAddr(String cAddr) {
		this.cAddr = cAddr;
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
	public Date getRecordDtm() {
		return recordDtm;
	}
	public void setRecordDtm(Date recordDtm) {
		this.recordDtm = recordDtm;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
