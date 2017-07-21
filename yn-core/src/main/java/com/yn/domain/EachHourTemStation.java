package com.yn.domain;

import java.util.Date;

public class EachHourTemStation {

	private Date time;
	private String timeStr;
	private Double kwh;
	private Double kw;
	
	
	public Double getKwh() {
		return kwh;
	}
	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}
	public Double getKw() {
		return kw;
	}
	public void setKw(Double kw) {
		this.kw = kw;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getTimeStr() {
		return timeStr;
	}
	public void setTimeStr(String timeStr) {
		this.timeStr = timeStr;
	}
}
