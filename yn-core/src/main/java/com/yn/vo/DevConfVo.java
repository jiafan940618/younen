package com.yn.vo;

/**
 * 采集器
 */
public class DevConfVo {

	private Long id;
	private Integer rowId;
	private Integer cAddr;
	private Integer iAddr;
	private Long dAddr;
	private Integer dType;
	private String dName;
	private String dOpt;
	private String wMask;
	private String wConf;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getRowId() {
		return rowId;
	}
	public void setRowId(Integer rowId) {
		this.rowId = rowId;
	}
	public Integer getcAddr() {
		return cAddr;
	}
	public void setcAddr(Integer cAddr) {
		this.cAddr = cAddr;
	}
	public Integer getiAddr() {
		return iAddr;
	}
	public void setiAddr(Integer iAddr) {
		this.iAddr = iAddr;
	}
	public Long getdAddr() {
		return dAddr;
	}
	public void setdAddr(Long dAddr) {
		this.dAddr = dAddr;
	}
	public Integer getdType() {
		return dType;
	}
	public void setdType(Integer dType) {
		this.dType = dType;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getdOpt() {
		return dOpt;
	}
	public void setdOpt(String dOpt) {
		this.dOpt = dOpt;
	}
	public String getwMask() {
		return wMask;
	}
	public void setwMask(String wMask) {
		this.wMask = wMask;
	}
	public String getwConf() {
		return wConf;
	}
	public void setwConf(String wConf) {
		this.wConf = wConf;
	}
}
