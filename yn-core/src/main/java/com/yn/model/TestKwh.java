package com.yn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestKwh {
	@Id
    @GeneratedValue
	@Column(columnDefinition = "int(11) comment '[id]'")
	private Integer id;
	@Column(columnDefinition = "int(11) comment '[id]'")
	private Integer time;
	@Column(insertable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[当前功率]'")
	private Double kwh;
	
	public TestKwh() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTime() {
		return time;
	}
	public void setTime(Integer time) {
		this.time = time;
	}
	public Double getKwh() {
		return kwh;
	}
	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}
	
	

}
