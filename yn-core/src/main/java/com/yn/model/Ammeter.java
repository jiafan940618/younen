package com.yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 电表
 */
@Entity
public class Ammeter extends IDomain implements Serializable {

	@Column(columnDefinition = "int(11) NOT NULL comment '[采集器地址]'")
	private String cAddr;
	@Column(columnDefinition = "int(11) NOT NULL comment '[接口地址]'")
	private Integer iAddr;
	@Column(columnDefinition = "int(11) NOT NULL comment '[设备类型]'")
	private Integer dType;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[dName]'")
	private String dName;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[dOpt]'")
	private String dOpt;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[wMask]'")
	private String wMask;
	@Column(columnDefinition = "varchar(255) NOT NULL comment '[wConf]'")
	private String wConf;
	@Column(columnDefinition = "int(11) comment '[电站id]'")
	private Long stationId;
	@Column(insertable=false, columnDefinition = "int(11) default 0 comment '[工作总时长 分钟]'")
	private Integer workTotalTm;
	@Column(insertable=false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[累计发电/用电]'")
	private Double workTotalKwh;
	@Column(insertable = false, precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[当前功率]'")
	private Double nowKw;
	@Column(columnDefinition = "datetime comment '[开始发电日期]'")
	private Date workDtm;
	@Column(columnDefinition = "datetime comment '[出厂日期]'")
	private Date outfactoryDtm;
	@Column(columnDefinition = "varchar(255) comment '[sim卡]'")
	private String sim;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) NOT NULL comment '[初始电量]'")
	private Double initKwh;
	@Column(insertable=false, columnDefinition = "int(1) default 0 comment '[运行状态]{0:正常,1:不正常}'")
	private Integer status;
	@Column(columnDefinition = "varchar(255) comment '[电表状态码]'")
	private String statusCode;


    /**
     * 是否已读
     */
    @Transient
    private Integer isRead;

	
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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
