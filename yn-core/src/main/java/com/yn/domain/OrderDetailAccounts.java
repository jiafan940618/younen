package com.yn.domain;

public class OrderDetailAccounts {

	private Double applyingPriceTol = 0d; // 申请中总金额
	private Double buildingPriceTol = 0d; // 施工中总金额
	private Double gridConnectedingPriceTol = 0d; // 并网申请中总金额
	private Double gridConnectedPriceTol = 0d; // 并网发电总金额
    private Double hadPayPriceTol = 0d; // 已支付总金额
    private Double notPayPriceTol = 0d; // 未支付项目金额


	private Integer orderNum = 0; // 订单数
	private Double priceTol = 0d; // 订单总金额
	private Double factoragePriceTol = 0d; // 优能服务费总金额
	private Double ynApolegamyPriceTol = 0d; // 优能选配项目总金额
    private Double serverApolegamyPriceTol = 0d; // 服务商选配项目总金额
    private Double profitTol = 0d; // 营业利润


    public Double getApplyingPriceTol() {
        return applyingPriceTol;
    }

    public void setApplyingPriceTol(Double applyingPriceTol) {
        this.applyingPriceTol = applyingPriceTol;
    }

    public Double getBuildingPriceTol() {
        return buildingPriceTol;
    }

    public void setBuildingPriceTol(Double buildingPriceTol) {
        this.buildingPriceTol = buildingPriceTol;
    }

    public Double getGridConnectedingPriceTol() {
        return gridConnectedingPriceTol;
    }

    public void setGridConnectedingPriceTol(Double gridConnectedingPriceTol) {
        this.gridConnectedingPriceTol = gridConnectedingPriceTol;
    }

    public Double getGridConnectedPriceTol() {
        return gridConnectedPriceTol;
    }

    public void setGridConnectedPriceTol(Double gridConnectedPriceTol) {
        this.gridConnectedPriceTol = gridConnectedPriceTol;
    }

    public Double getHadPayPriceTol() {
        return hadPayPriceTol;
    }

    public void setHadPayPriceTol(Double hadPayPriceTol) {
        this.hadPayPriceTol = hadPayPriceTol;
    }

    public Double getNotPayPriceTol() {
        return notPayPriceTol;
    }

    public void setNotPayPriceTol(Double notPayPriceTol) {
        this.notPayPriceTol = notPayPriceTol;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Double getPriceTol() {
        return priceTol;
    }

    public void setPriceTol(Double priceTol) {
        this.priceTol = priceTol;
    }

    public Double getFactoragePriceTol() {
        return factoragePriceTol;
    }

    public void setFactoragePriceTol(Double factoragePriceTol) {
        this.factoragePriceTol = factoragePriceTol;
    }

    public Double getYnApolegamyPriceTol() {
        return ynApolegamyPriceTol;
    }

    public void setYnApolegamyPriceTol(Double ynApolegamyPriceTol) {
        this.ynApolegamyPriceTol = ynApolegamyPriceTol;
    }

    public Double getServerApolegamyPriceTol() {
        return serverApolegamyPriceTol;
    }

    public void setServerApolegamyPriceTol(Double serverApolegamyPriceTol) {
        this.serverApolegamyPriceTol = serverApolegamyPriceTol;
    }

    public Double getProfitTol() {
        return profitTol;
    }

    public void setProfitTol(Double profitTol) {
        this.profitTol = profitTol;
    }
}
