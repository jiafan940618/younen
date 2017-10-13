package com.yn.vo;

import java.util.Date;

public class WeatherVo {
	
	private Date createDtm;
	private String city;
	private int pm25;
	private int uv;
	private int tMax;
	private int tMin;
	private String wDay;
	private String wNight;
	public Date getCreateDtm() {
		return createDtm;
	}
	public void setCreateDtm(Date createDtm) {
		this.createDtm = createDtm;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getPm25() {
		return pm25;
	}
	public void setPm25(int pm25) {
		this.pm25 = pm25;
	}
	public int getUv() {
		return uv;
	}
	public void setUv(int uv) {
		this.uv = uv;
	}
	public int gettMax() {
		return tMax;
	}
	public void settMax(int tMax) {
		this.tMax = tMax;
	}
	public int gettMin() {
		return tMin;
	}
	public void settMin(int tMin) {
		this.tMin = tMin;
	}
	public String getwDay() {
		return wDay;
	}
	public void setwDay(String wDay) {
		this.wDay = wDay;
	}
	public String getwNight() {
		return wNight;
	}
	public void setwNight(String wNight) {
		this.wNight = wNight;
	}

}
