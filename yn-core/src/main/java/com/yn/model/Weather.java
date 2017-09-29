package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yn.domain.IDomain;

@Entity
public class Weather extends IDomain implements Serializable {

	@Column(columnDefinition = "varchar(10) NOT NULL comment '[城市]'")
	private String city;
	/**
	 * pm2.5
	 */
	@Column(columnDefinition = "int(2) comment '[PM2.5]'")
	private int pm25;
	/**
	 * UV指数一般从0开始，一直到10(含大于10)为终，根据这些数值，气象局将紫外线指数的预报等级划分为五级。
	 * UV指数值0到2，一般为阴或雨天，此时紫外线强度最弱，预报等级为一级； UV指数值3到4，一般为多云天气，此时紫外线强度较弱，预报等级为二级；
	 * UV指数值5到6，一般为少云天气，此时紫外线强度较强，预报等级为三级； UV指数值7到9，一般为晴天无云，此时紫外线强度很强，预报等级为四级；
	 * UV指数值达到或超过10，多为夏季晴日，紫外线强度特别强，预报等级为五级。
	 */
	@Column(columnDefinition = "int(2) comment '[紫外线指数]'")
	private int uv;
	/**
	 * 最高温度
	 */
	@Column(columnDefinition = "int(2) comment '[最高温度]'")
	private int tMax;
	/**
	 * 最低温度
	 */
	@Column(columnDefinition = "int(2) comment '[最低温度]'")
	private int tMin;
	/**
	 * 日间天气 中国气象台站统一规定:白天是08－20时
	 */
	@Column(columnDefinition = "varchar(10) comment '[日间天气]'")
	private String wDay;
	/**
	 * 夜间天气 中国气象台站统一规定:夜间是20－08时
	 */
	@Column(columnDefinition = "varchar(10) comment '[夜间天气]'")
	private String wNight;
	
	

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
