package com.yn.model;

import com.yn.domain.IDomain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 采集器
 */
@Entity
public class DevConf  implements Serializable {
	@Id
    @GeneratedValue
	@Column(columnDefinition = "int(11) comment '[rowId]'")
	private Long rowId;
	@Column(columnDefinition = "int(11) comment '[cAddr]'")
	private Integer cAddr;
	@Column(columnDefinition = "int(11) comment '[iAddr]'")
	private Integer iAddr;
	@Column(columnDefinition = "int(11) comment '[dAddr]'")
	private Long dAddr;
	@Column(columnDefinition = "int(11) comment '[dType]'")
	private Integer dType;
	@Column(columnDefinition = "varchar(255) comment '[dName]'")
	private String dName;
	@Column(columnDefinition = "varchar(255) comment '[dOpt]'")
	private String dOpt;
	@Column(columnDefinition = "varchar(255) comment '[wMask]'")
	private String wMask;
	@Column(columnDefinition = "varchar(255) comment '[wConf]'")
	private String wConf;
	
	
	public Long getRowId() {
		return rowId;
	}
	public void setRowId(Long rowId) {
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
