package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * 省
 *
 */
@Entity
public class Province extends IDomain implements Serializable{
	
	 @Id
	    @GeneratedValue
	    @Column(columnDefinition = "int(11) comment '[id]'")
	    private Long id;
	@Column(columnDefinition = "varchar(50) comment '[省名称]'")
	private String provinceText;
	
	@OneToMany
	@JoinColumn(name = "provinceId", insertable = false, updatable = false)
	private Set<City> city;
	
	
	public Province() {}

	public Province(Long id,String provinceText){
		this.id = id;
		this.provinceText = provinceText;
	}

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
