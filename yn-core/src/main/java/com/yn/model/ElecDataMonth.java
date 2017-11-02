package com.yn.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.yn.domain.IDomain;
@Entity
public class ElecDataMonth extends IDomain implements Serializable{

    @Column(updatable = true, columnDefinition = "varchar(255) comment '[电站码]'")
    private String ammeterCode;
    @Column(columnDefinition = "varchar(255) comment '[采集器码]'")
    private String devConfCode;
    @Column(columnDefinition = "int(11) comment '[设备地址]'")
    private Integer dAddr;
    @Column(columnDefinition = "int(11) comment '[设备类型]'")
    private Integer dType;
    @Column(columnDefinition = "int(11) comment '[回路地址]'")
    private Integer wAddr;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[瞬时功率]'")
    private BigDecimal kw;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) default 0 comment '[发/用电量]'")
    private BigDecimal kwh;
    @Column(columnDefinition = "int(1) comment '[类型]{1:发电,2:用电}'")
    private Integer type;
    @Column(columnDefinition = "varchar(255) NOT NULL comment'[记录时间,如:2017-07-20]'")
    private String recordTime;



    public String getAmmeterCode() {
		return ammeterCode;
	}

	public void setAmmeterCode(String ammeterCode) {
		this.ammeterCode = ammeterCode;  
	}

    public String getDevConfCode() {
        return devConfCode;
    }

    public void setDevConfCode(String devConfCode) {
        this.devConfCode = devConfCode;
    }

    public Integer getdAddr() {
        return dAddr;
    }

    public void setdAddr(Integer dAddr) {
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

    public BigDecimal getKw() {
        return kw;
    }

    public void setKw(BigDecimal kw) {
        this.kw = kw;
    }

    public BigDecimal getKwh() {
        return kwh;
    }

    public void setKwh(BigDecimal kwh) {
        this.kwh = kwh;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }
}