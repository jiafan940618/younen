package com.yn.model;

import java.math.BigDecimal;

public class Pzw {
    private String ammeterCode;

    private BigDecimal kwhTotal;

    private Integer type;

    private Integer isOk;

    private String recordTime;

    private BigDecimal monthMaxKwh;

    public String getAmmeterCode() {
        return ammeterCode;
    }

    public void setAmmeterCode(String ammeterCode) {
        this.ammeterCode = ammeterCode == null ? null : ammeterCode.trim();
    }

    public BigDecimal getKwhTotal() {
        return kwhTotal;
    }

    public void setKwhTotal(BigDecimal kwhTotal) {
        this.kwhTotal = kwhTotal;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsOk() {
        return isOk;
    }

    public void setIsOk(Integer isOk) {
        this.isOk = isOk;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime == null ? null : recordTime.trim();
    }

    public BigDecimal getMonthMaxKwh() {
        return monthMaxKwh;
    }

    public void setMonthMaxKwh(BigDecimal monthMaxKwh) {
        this.monthMaxKwh = monthMaxKwh;
    }
}