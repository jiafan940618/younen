package com.yn.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.yn.domain.IDomain;

@Entity
public class Brand extends IDomain implements Serializable {
	@Column(columnDefinition = "varchar(255) comment '[产品类型,1、电池板 3、逆变器]'")
	private String type;
	@Column(columnDefinition = "varchar(255) comment '[品牌名]'")
	private String brandName;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", insertable = false, updatable = true)
	private Set<SolarPanel> solar;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", insertable = false, updatable = true)
	private Set<Inverter> inverter;
	

	
	public Set<SolarPanel> getSolar() {
		return solar;
	}

	public void setSolar(Set<SolarPanel> solar) {
		this.solar = solar;
	}

	public Set<Inverter> getInverter() {
		return inverter;
	}

	public void setInverter(Set<Inverter> inverter) {
		this.inverter = inverter;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}
