package com.yn.vo;

import java.util.Date;

import com.yn.domain.QueryVo;

/**
 * 电表
 */
public class AmmeterVo extends QueryVo{

	private Long id;
	
	private String cAddr;
	private Integer iAddr;
	private Long dAddr;
	private Integer dType;
	private String dName;
	private String dOpt;
	private String wMask;
	private String wConf;
	
	private Long provinceId;
	private String provinceText;
	private Long cityId;
	private String cityText;
	private String addressText;
	private Long stationId;
	private Integer workTotaTm;
	private Double workTotaKwh;
	private Double nowKw;
	private Date workDtm;
	private Date outfactoryDtm;
	private String sim;
	private Double initKwh; 
	private Integer type;
	private Integer status;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceText() {
		return provinceText;
	}
	public void setProvinceText(String provinceText) {
		this.provinceText = provinceText;
	}
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getCityText() {
		return cityText;
	}
	public void setCityText(String cityText) {
		this.cityText = cityText;
	}
	public String getAddressText() {
		return addressText;
	}
	public void setAddressText(String addressText) {
		this.addressText = addressText;
	}
	public Long getStationId() {
		return stationId;
	}
	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	public Integer getWorkTotaTm() {
		return workTotaTm;
	}
	public void setWorkTotaTm(Integer workTotaTm) {
		this.workTotaTm = workTotaTm;
	}
	public Double getWorkTotaKwh() {
		return workTotaKwh;
	}
	public void setWorkTotaKwh(Double workTotaKwh) {
		this.workTotaKwh = workTotaKwh;
	}
	public Double getNowKw() {
		return nowKw;
	}
	public void setNowKw(Double nowKw) {
		this.nowKw = nowKw;
	}
	public Date getWorkDtm() {
		return workDtm;
	}
	public void setWorkDtm(Date workDtm) {
		this.workDtm = workDtm;
	}
	public Date getOutfactoryDtm() {
		return outfactoryDtm;
	}
	public void setOutfactoryDtm(Date outfactoryDtm) {
		this.outfactoryDtm = outfactoryDtm;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public Double getInitKwh() {
		return initKwh;
	}
	public void setInitKwh(Double initKwh) {
		this.initKwh = initKwh;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getcAddr() {
		return cAddr;
	}
	public void setcAddr(String cAddr) {
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
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
}
