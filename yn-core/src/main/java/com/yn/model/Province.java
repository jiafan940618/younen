package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * 省
 *
 */
@Entity
public class Province extends IDomain implements Serializable{
	
	@Column(columnDefinition = "varchar(50) comment '[省名称]'")
	private String provinceText;
	
	@OneToMany
	@JoinColumn(name = "provinceId", insertable = false, updatable = false)
	private Set<City> city;

	public String getProvinceText() {
		return provinceText;
	}
	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}
	public Set<City> getCity() {
		return city;
	}
	public void setCity(Set<City> city) {
		this.city = city;
	}
}
