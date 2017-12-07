package com.yn.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.yn.domain.IDomain;
import org.hibernate.annotations.Where;

/**
 * 设备
 */
@Entity
public class Devide extends IDomain implements Serializable {

	@Id
	@GeneratedValue
	@Column(columnDefinition = "int(11) comment '[id]'")
	private Long id;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[设备名称]'")
	private String devideName;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[设备品牌]'")
	private String devideBrand;
	@Column(updatable = true, columnDefinition = "varchar(255) comment '[设备型号]'")
	private String devideModel;
	@Column(insertable = true, updatable = true, columnDefinition = "int(1) comment '[设备类型]{0:电池板,1:逆变器,2:其他材料}'")
	private Integer type;
	@Column(columnDefinition = "int(11) comment '[父类id]'")
	private Long parentId;
	@Column(columnDefinition = "int(11) comment '[等级]'")
	private Integer zlevel;

	@OneToMany
	@JoinColumn(name = "parentId", insertable = false, updatable = true)
	@Where(clause = "del=0")
	private List<Devide> children;

	public Devide() {
		super();
	}

	public Devide(Long id, String devideBrand) {
		super();
		this.id = id;
		this.devideBrand = devideBrand;
	}

	public Devide(Long id, String devideModel, String devideBrand) {
		super();
		this.id = id;
		this.devideBrand = devideBrand;
		this.devideModel = devideModel;
	}


	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDevideName() {
		return devideName;
	}

	public void setDevideName(String devideName) {
		this.devideName = devideName;
	}

	public String getDevideBrand() {
		return devideBrand;
	}

	public void setDevideBrand(String devideBrand) {
		this.devideBrand = devideBrand;
	}

	public String getDevideModel() {
		return devideModel;
	}

	public void setDevideModel(String devideModel) {
		this.devideModel = devideModel;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getZlevel() {
		return zlevel;
	}

	public void setZlevel(Integer zlevel) {
		this.zlevel = zlevel;
	}

	public List<Devide> getChildren() {
		return children;
	}

	public void setChildren(List<Devide> children) {
		this.children = children;
	}
}
