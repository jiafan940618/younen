package com.yn.model;

import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 订单方案
 */
@Entity
public class OrderPlan extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(11) comment '[订单id]'")
	private Long orderId;
	
	@Column(columnDefinition = "int(11) comment '[电池板id]'")
	private Long batteryBoardId;
	@Column(columnDefinition = "varchar(255) comment '[电池板品牌]'")
	private String batteryBoardBrand;
	@Column(columnDefinition = "varchar(255) comment '[电池板名称]'")
	private String batteryBoardName;
	@Column(columnDefinition = "varchar(255) comment '[电池板型号]'")
	private String batteryBoardModel;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[电池板质保期 年]'")
	private Double batteryBoardShelfLife;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[电池板保修年限 年]'")
	private Double batteryBoardWarrantyYear;
	
	@Column(columnDefinition = "int(11) comment '[逆变器id]'")
	private Long inverterId;
	@Column(columnDefinition = "varchar(255) comment '[逆变器品牌]'")
	private String inverterBrand;
	@Column(columnDefinition = "varchar(255) comment '[逆变器名称]'")
	private String inverterName;
	@Column(columnDefinition = "varchar(255) comment '[逆变器型号]'")
	private String inverterModel;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[逆变器质保期 年]'")
	private Double inverterShelfLife;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[逆变器保修年限 年]'")
	private Double inverterWarrantyYear;
	
	@Column(columnDefinition = "text comment '[其他材料jsonText]'")
	private String otherMaterialJsonText;

	 @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[元/千瓦]'")
	    private Double unitPrice;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBatteryBoardId() {
        return batteryBoardId;
    }

    public void setBatteryBoardId(Long batteryBoardId) {
        this.batteryBoardId = batteryBoardId;
    }

    public String getBatteryBoardBrand() {
        return batteryBoardBrand;
    }

    public void setBatteryBoardBrand(String batteryBoardBrand) {
        this.batteryBoardBrand = batteryBoardBrand;
    }

    public String getBatteryBoardName() {
        return batteryBoardName;
    }

    public void setBatteryBoardName(String batteryBoardName) {
        this.batteryBoardName = batteryBoardName;
    }

    public String getBatteryBoardModel() {
        return batteryBoardModel;
    }

    public void setBatteryBoardModel(String batteryBoardModel) {
        this.batteryBoardModel = batteryBoardModel;
    }

    public Double getBatteryBoardShelfLife() {
        return batteryBoardShelfLife;
    }

    public void setBatteryBoardShelfLife(Double batteryBoardShelfLife) {
        this.batteryBoardShelfLife = batteryBoardShelfLife;
    }

    public Double getBatteryBoardWarrantyYear() {
        return batteryBoardWarrantyYear;
    }

    public void setBatteryBoardWarrantyYear(Double batteryBoardWarrantyYear) {
        this.batteryBoardWarrantyYear = batteryBoardWarrantyYear;
    }

    public Long getInverterId() {
        return inverterId;
    }

    public void setInverterId(Long inverterId) {
        this.inverterId = inverterId;
    }

    public String getInverterBrand() {
        return inverterBrand;
    }

    public void setInverterBrand(String inverterBrand) {
        this.inverterBrand = inverterBrand;
    }

    public String getInverterName() {
        return inverterName;
    }

    public void setInverterName(String inverterName) {
        this.inverterName = inverterName;
    }

    public String getInverterModel() {
        return inverterModel;
    }

    public void setInverterModel(String inverterModel) {
        this.inverterModel = inverterModel;
    }

    public Double getInverterShelfLife() {
        return inverterShelfLife;
    }

    public void setInverterShelfLife(Double inverterShelfLife) {
        this.inverterShelfLife = inverterShelfLife;
    }

    public Double getInverterWarrantyYear() {
        return inverterWarrantyYear;
    }

    public void setInverterWarrantyYear(Double inverterWarrantyYear) {
        this.inverterWarrantyYear = inverterWarrantyYear;
    }

    public String getOtherMaterialJsonText() {
        return otherMaterialJsonText;
    }

    public void setOtherMaterialJsonText(String otherMaterialJsonText) {
        this.otherMaterialJsonText = otherMaterialJsonText;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
