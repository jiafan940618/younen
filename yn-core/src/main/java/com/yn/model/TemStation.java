package com.yn.model;

import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class TemStation extends IDomain implements Serializable {

    @Column(columnDefinition = "int(11) comment '[电站id]'")
    private Long stationId;
    @Column(columnDefinition = "varchar(255) comment '[电站码]'")
    private String stationCode;
    @Column(columnDefinition = "int(11) comment '[服务商id]'")
    private Long serverId;
    @Column(columnDefinition = "varchar(255) comment '[采集器码]'")
    private String devConfCode;
    @Column(columnDefinition = "int(11) comment '[d_addr]'")
    private Long dAddr;
    @Column(columnDefinition = "int(11) comment '[d_type]'")
    private Integer dType;
    @Column(columnDefinition = "int(11) comment '[w_addr]'")
    private Integer wAddr;
    @Column(columnDefinition = "decimal(12,2) default 0 comment '[瞬时功率]'")
    private Double kw;
    @Column(columnDefinition = "decimal(12,2) default 0 comment '[电量]'")
    private Double kwh;
    @Column(columnDefinition = "int(1) comment '[类型]{1:发电,2:用电}'")
    private Integer type;
    @Column(columnDefinition = "varchar(255) NOT NULL comment'[记录时间,如:2017072109]'")
    private String recordTime;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getDevConfCode() {
        return devConfCode;
    }

    public void setDevConfCode(String devConfCode) {
        this.devConfCode = devConfCode;
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

    public Double getKw() {
        return kw;
    }

    public void setKw(Double kw) {
        this.kw = kw;
    }

    public Double getKwh() {
        return kwh;
    }

    public void setKwh(Double kwh) {
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
