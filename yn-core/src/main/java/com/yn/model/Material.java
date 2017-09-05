package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 素材
 *
 */
@Entity
public class Material extends IDomain implements Serializable {

	@Column(columnDefinition = "varchar(10) comment '[素材类型]'")
	private String materialType;
	@Column(columnDefinition = "varchar(20) comment '[素材所属项目类型]'")
	private int projectType;
	@Column(columnDefinition = "varchar(255) comment '[素材路径]'")
	private String url;
	@Column(columnDefinition = "int(1) comment '[素材是否显示]'")
	private int display;
	@Column(columnDefinition = "varchar(255) comment '[素材描述]'")
	private String describe;

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public int getProjectType() {
		return projectType;
	}

	public void setProjectType(int projectType) {
		this.projectType = projectType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
