package com.yn.vo;

/**
 * 设备
 */
public class DevideVo {
	
	private Long id;
	private String devideName;
	private String devideBrand;
	private String devideModel;
	private Integer type;
	private Long parentId;
	private Integer zlevel;
	
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getZlevel() {
		return zlevel;
	}
	public void setZlevel(Integer zlevel) {
		this.zlevel = zlevel;
	}
}
