package com.yn.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Am3Phase implements Serializable {

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
	private Double aVolt;
	private Double bVolt;
	private Double cVolt;
	private Double abVolt;
	private Double bcVolt;
	private Double caVolt;
	private Double aCurrent;
	private Double bCurrent;
	private Double cCurrent;
	private Double kw;
	private Double aKw;
	private Double bKw;
	private Double cKw;
	private Double kvar;
	private Double aKvar;
	private Double bKvar;
	private Double cKvar;
	private Double kva;
	private Double aKva;
	private Double bKva;
	private Double cKva;
	private Double powerFactor;
	private Double aPowerFactor;
	private Double bPowerFactor;
	private Double cPowerFactor;
	private Double currentZero;
	private Double kwhTotal;
	private Double kwh;
	private Double kwhRev;
	private Double kvarh1;
	private Double kvarh2;
	private Double aKwhTotal;
	private Double bKwhTotal;
	private Double cKwhTotal;

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

	public Double getaVolt() {
		return aVolt;
	}

	public void setaVolt(Double aVolt) {
		this.aVolt = aVolt;
	}

	public Double getbVolt() {
		return bVolt;
	}

	public void setbVolt(Double bVolt) {
		this.bVolt = bVolt;
	}

	public Double getcVolt() {
		return cVolt;
	}

	public void setcVolt(Double cVolt) {
		this.cVolt = cVolt;
	}

	public Double getAbVolt() {
		return abVolt;
	}

	public void setAbVolt(Double abVolt) {
		this.abVolt = abVolt;
	}

	public Double getBcVolt() {
		return bcVolt;
	}

	public void setBcVolt(Double bcVolt) {
		this.bcVolt = bcVolt;
	}

	public Double getCaVolt() {
		return caVolt;
	}

	public void setCaVolt(Double caVolt) {
		this.caVolt = caVolt;
	}

	public Double getaCurrent() {
		return aCurrent;
	}

	public void setaCurrent(Double aCurrent) {
		this.aCurrent = aCurrent;
	}

	public Double getbCurrent() {
		return bCurrent;
	}

	public void setbCurrent(Double bCurrent) {
		this.bCurrent = bCurrent;
	}

	public Double getcCurrent() {
		return cCurrent;
	}

	public void setcCurrent(Double cCurrent) {
		this.cCurrent = cCurrent;
	}

	public Double getKw() {
		return kw;
	}

	public void setKw(Double kw) {
		this.kw = kw;
	}

	public Double getaKw() {
		return aKw;
	}

	public void setaKw(Double aKw) {
		this.aKw = aKw;
	}

	public Double getbKw() {
		return bKw;
	}

	public void setbKw(Double bKw) {
		this.bKw = bKw;
	}

	public Double getcKw() {
		return cKw;
	}

	public void setcKw(Double cKw) {
		this.cKw = cKw;
	}

	public Double getKvar() {
		return kvar;
	}

	public void setKvar(Double kvar) {
		this.kvar = kvar;
	}

	public Double getaKvar() {
		return aKvar;
	}

	public void setaKvar(Double aKvar) {
		this.aKvar = aKvar;
	}

	public Double getbKvar() {
		return bKvar;
	}

	public void setbKvar(Double bKvar) {
		this.bKvar = bKvar;
	}

	public Double getcKvar() {
		return cKvar;
	}

	public void setcKvar(Double cKvar) {
		this.cKvar = cKvar;
	}

	public Double getKva() {
		return kva;
	}

	public void setKva(Double kva) {
		this.kva = kva;
	}

	public Double getaKva() {
		return aKva;
	}

	public void setaKva(Double aKva) {
		this.aKva = aKva;
	}

	public Double getbKva() {
		return bKva;
	}

	public void setbKva(Double bKva) {
		this.bKva = bKva;
	}

	public Double getcKva() {
		return cKva;
	}

	public void setcKva(Double cKva) {
		this.cKva = cKva;
	}

	public Double getPowerFactor() {
		return powerFactor;
	}

	public void setPowerFactor(Double powerFactor) {
		this.powerFactor = powerFactor;
	}

	public Double getaPowerFactor() {
		return aPowerFactor;
	}

	public void setaPowerFactor(Double aPowerFactor) {
		this.aPowerFactor = aPowerFactor;
	}

	public Double getbPowerFactor() {
		return bPowerFactor;
	}

	public void setbPowerFactor(Double bPowerFactor) {
		this.bPowerFactor = bPowerFactor;
	}

	public Double getcPowerFactor() {
		return cPowerFactor;
	}

	public void setcPowerFactor(Double cPowerFactor) {
		this.cPowerFactor = cPowerFactor;
	}

	public Double getCurrentZero() {
		return currentZero;
	}

	public void setCurrentZero(Double currentZero) {
		this.currentZero = currentZero;
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

	public Double getKvarh1() {
		return kvarh1;
	}

	public void setKvarh1(Double kvarh1) {
		this.kvarh1 = kvarh1;
	}

	public Double getKvarh2() {
		return kvarh2;
	}

	public void setKvarh2(Double kvarh2) {
		this.kvarh2 = kvarh2;
	}

	public Double getaKwhTotal() {
		return aKwhTotal;
	}

	public void setaKwhTotal(Double aKwhTotal) {
		this.aKwhTotal = aKwhTotal;
	}

	public Double getbKwhTotal() {
		return bKwhTotal;
	}

	public void setbKwhTotal(Double bKwhTotal) {
		this.bKwhTotal = bKwhTotal;
	}

	public Double getcKwhTotal() {
		return cKwhTotal;
	}

	public void setcKwhTotal(Double cKwhTotal) {
		this.cKwhTotal = cKwhTotal;
	}
}
