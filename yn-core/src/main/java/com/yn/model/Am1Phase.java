package com.yn.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Am1Phase implements Serializable {

	@Id
    @GeneratedValue
	private Integer rowId;
	private Integer cAddr;
	private Integer iAddr;
	private Long dAddr;
	private Integer dType;
	private Integer wAddr;
	private Long meterTime;
	private String meterState;
	private Integer voltChange;
	private Integer currentChange;
	private Double frequency;
	private Double volt;
	private Double current;
	private Double kw;
	private Double kvar;
	private Double kva;
	private Double powerFactor;
	private Double kwhTotal;
	private Double kwh;
	private Double kwhRev;
	private Double kvarh1;
	private Double kvarh2;
	
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
	public Integer getwAddr() {
		return wAddr;
	}
	public void setwAddr(Integer wAddr) {
		this.wAddr = wAddr;
	}
	public Long getMeterTime() {
		return meterTime;
	}
	public void setMeterTime(Long meterTime) {
		this.meterTime = meterTime;
	}
	public String getMeterState() {
		return meterState;
	}
	public void setMeterState(String meterState) {
		this.meterState = meterState;
	}
	public Integer getVoltChange() {
		return voltChange;
	}
	public void setVoltChange(Integer voltChange) {
		this.voltChange = voltChange;
	}
	public Integer getCurrentChange() {
		return currentChange;
	}
	public void setCurrentChange(Integer currentChange) {
		this.currentChange = currentChange;
	}
	public Double getFrequency() {
		return frequency;
	}
	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}
	public Double getVolt() {
		return volt;
	}
	public void setVolt(Double volt) {
		this.volt = volt;
	}
	public Double getCurrent() {
		return current;
	}
	public void setCurrent(Double current) {
		this.current = current;
	}
	public Double getKw() {
		return kw;
	}
	public void setKw(Double kw) {
		this.kw = kw;
	}
	public Double getKvar() {
		return kvar;
	}
	public void setKvar(Double kvar) {
		this.kvar = kvar;
	}
	public Double getKva() {
		return kva;
	}
	public void setKva(Double kva) {
		this.kva = kva;
	}
	public Double getPowerFactor() {
		return powerFactor;
	}
	public void setPowerFactor(Double powerFactor) {
		this.powerFactor = powerFactor;
	}
	public Double getKwhTotal() {
		return kwhTotal;
	}
	public void setKwhTotal(Double kwhTotal) {
		this.kwhTotal = kwhTotal;
	}
	public Double getKwh() {
		return kwh;
	}
	public void setKwh(Double kwh) {
		this.kwh = kwh;
	}
	public Double getKwhRev() {
		return kwhRev;
	}
	public void setKwhRev(Double kwhRev) {
		this.kwhRev = kwhRev;
	}
	public Double getKvarh2() {
		return kvarh2;
	}
	public void setKvarh2(Double kvarh2) {
		this.kvarh2 = kvarh2;
	}
	public Double getKvarh1() {
		return kvarh1;
	}
	public void setKvarh1(Double kvarh1) {
		this.kvarh1 = kvarh1;
	}
}
