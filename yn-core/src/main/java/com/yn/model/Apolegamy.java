package com.yn.model;

import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * 优能选配项目
 */
@Entity
public class Apolegamy extends IDomain implements Serializable {
	
	@Column(columnDefinition = "varchar(255) comment '[项目名称]'")
	private String apolegamyName;
	@Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[价格 元]'")
	private Double price;
	@Column(columnDefinition = "varchar(255) comment '[展示图片地址]'")
	private String imgUrl;
	@Column(columnDefinition = "varchar(255) comment '[icon图片地址]'")
	private String iconUrl;
    @Column(columnDefinition = "varchar(255) comment '[单位]'")
	private String unit;
    @Column(columnDefinition = "int(11) comment '[资质id]'")
    private Long qualificationsId;
    @Column(columnDefinition = "int(1) NOT NULL comment '[类型]{0:优能的选配项目,1:服务商的选配项目}'")
    private Integer type;

    public String getApolegamyName() {
        return apolegamyName;
    }

    public void setApolegamyName(String apolegamyName) {
        this.apolegamyName = apolegamyName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Long getQualificationsId() {
        return qualificationsId;
    }

    public void setQualificationsId(Long qualificationsId) {
        this.qualificationsId = qualificationsId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
