package com.yn.model;

import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;

@Entity
public class AmmeterRecord extends IDomain implements Serializable {

	@Column(columnDefinition = "int(11) comment '[电站id]'")
	private Long stationId;
	@Column(columnDefinition = "varchar(255) comment '[电站码]'")
	private String stationCode;
	@Column(columnDefinition = "varchar(255) comment '[采集器码]'")
	private String cAddr;
	@Column(columnDefinition = "int(11) comment '[设备地址]'")
	private Long dAddr;
	@Column(columnDefinition = "int(11) comment '[设备类型]'")
	private Integer dType;
	@Column(columnDefinition = "int(11) comment '[回路地址]'")
	private Integer wAddr;
	@Column(columnDefinition = "varchar(255) comment '[电表状态码]'")
	private String statusCode;
	@Column(columnDefinition = "datetime comment '[收集时间]'")
	private Date recordDtm;
	@Column(columnDefinition = "int(1) comment '[电表类型]{1:发电,2:用电}'")
	private Integer type;

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

    public String getcAddr() {
        return cAddr;
    }

    public void setcAddr(String cAddr) {
        this.cAddr = cAddr;
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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Date getRecordDtm() {
        return recordDtm;
    }

    public void setRecordDtm(Date recordDtm) {
        this.recordDtm = recordDtm;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
