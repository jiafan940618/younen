package com.yn.vo;

import com.yn.domain.QueryVo;

import java.util.Date;

import javax.persistence.Column;

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
	private Integer workTotalTm;
	private Double workTotalKwh;
	private Double nowKw;
	private Date workDtm;
	private Date outfactoryDtm;
	private String sim;
	private Double initKwh;
	private Integer type;
	private Integer status;
	private String statusCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getWorkTotalTm() {
        return workTotalTm;
    }

    public void setWorkTotalTm(Integer workTotalTm) {
        this.workTotalTm = workTotalTm;
    }

    public Double getWorkTotalKwh() {
        return workTotalKwh;
    }

    public void setWorkTotalKwh(Double workTotalKwh) {
        this.workTotalKwh = workTotalKwh;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
