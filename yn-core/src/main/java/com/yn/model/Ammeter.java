package com.yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;

/**
 * 电表
 */
@Entity
public class Ammeter extends IDomain implements Serializable {

	@Column(columnDefinition = "int(11) NOT NULL comment '[cAddr]'")
	private String cAddr;
	@Column(columnDefinition = "int(11) NOT NULL comment '[iAddr]'")
	private Integer iAddr;
	@Column(columnDefinition = "int(11) NOT NULL comment '[dAddr]'")
	private Long dAddr;
	@Column(columnDefinition = "int(11) NOT NULL comment '[dType]'")
	private Integer dType;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[dName]'")
	private String dName;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[dOpt]'")
	private String dOpt;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[wMask]'")
	private String wMask;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[wConf]'")
	private String wConf;
	
	
	@Column(columnDefinition = "int(11) NOT NULL comment '[省id]'")
	private Long provinceId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[省名字]'")
	private String provinceText;
	@Column(columnDefinition = "int(11) NOT NULL comment '[市id]'")
	private Long cityId;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[市名字]'")
	private String cityText;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[地址]'")
	private String addressText;
	@Column(columnDefinition = "int(11) comment '[电站id]'")
	private Long stationId;
	@Column(insertable=false, columnDefinition = "int(11) default 0 comment '[工作总时长 分钟]'")
	private Integer workTotaTm;
	@Column(insertable=false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[累计发电/用电]'")
	private Double workTotaKwh;
	@Column(insertable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[当前功率]'")
	private Double nowKw;
	@Column(columnDefinition = "datetime comment '[开始发电日期]'")
	private Date workDtm;
	@Column(columnDefinition = "datetime comment '[出厂日期]'")
	private Date outfactoryDtm;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[sim卡]'")
	private String sim;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[初始电量]'")
	private Double initKwh;
	@Column(columnDefinition = "int(1) NOT NULL comment '[电表类型]{1:发电,2:用电}'")
	private Integer type;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[运行状态]{0:正常,1:不正常}'")
	private Integer status;
	@Column(columnDefinition = "varchar(255) comment '[电表状态码]'")
	private String statusCode;
	
	/**
	 * 电站
	 */
	@ManyToOne
    @JoinColumn(name = "stationId", insertable = false, updatable = false)
	@JsonIgnoreProperties(value = {"order","user","server","ammeter"})
    private Station station;

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

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }
}
