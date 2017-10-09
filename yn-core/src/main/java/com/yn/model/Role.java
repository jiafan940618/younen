package com.yn.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;

import com.yn.domain.IDomain;
import org.hibernate.annotations.Where;

/**
 * 角色
 */
@Entity
public class Role extends IDomain implements Serializable {
	@Column(columnDefinition = "varchar(50) comment '[角色名称]'")
	private String name;
	@Column(columnDefinition = "varchar(50) comment '[备注]'")
	private String remark;
	
	@ManyToMany(cascade=CascadeType.PERSIST)
	@OrderBy("rank,id ASC")
	@Where(clause="del=0")
//	@Where(clause="zlevel=1 and del=0")
	public Set<Menu> menu;
	
	
	public Set<Menu> getMenu() {
		return menu;
	}
	public void setMenu(Set<Menu> menu) {
		this.menu = menu;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
