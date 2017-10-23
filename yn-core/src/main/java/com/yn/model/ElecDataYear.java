package com.yn.model;

import java.math.BigDecimal;
import java.util.Date;

public class ElecDataYear {
    private Integer id;

    private Date createDtm;

    private Integer del;

    private Date delDtm;

    private Date updateDtm;

    private Integer dAddr;

    private Integer dType;

    private String devConfCode;

    private BigDecimal kw;

    private BigDecimal kwh;

    private String recordTime;

    private String ammeterCode;

    private Integer type;

    private Integer wAddr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDtm() {
        return createDtm;
    }

    public void setCreateDtm(Date createDtm) {
        this.createDtm = createDtm;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public Date getDelDtm() {
        return delDtm;
    }

    public void setDelDtm(Date delDtm) {
        this.delDtm = delDtm;
    }

    public Date getUpdateDtm() {
        return updateDtm;
    }

    public void setUpdateDtm(Date updateDtm) {
        this.updateDtm = updateDtm;
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

    public String getDevConfCode() {
        return devConfCode;
    }

    public void setDevConfCode(String devConfCode) {
        this.devConfCode = devConfCode == null ? null : devConfCode.trim();
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

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime == null ? null : recordTime.trim();
    }

    public String getAmmeterCode() {
        return ammeterCode;
    }

    public void setAmmeterCode(String ammeterCode) {
        this.ammeterCode = ammeterCode == null ? null : ammeterCode.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getwAddr() {
        return wAddr;
    }

    public void setwAddr(Integer wAddr) {
        this.wAddr = wAddr;
    }
}