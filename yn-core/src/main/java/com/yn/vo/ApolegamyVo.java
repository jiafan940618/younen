package com.yn.vo;

import com.yn.domain.QueryVo;

/**
 * 选配项目
 */
public class ApolegamyVo extends QueryVo {

    private Long id;
    private String apolegamyName;
    private Double price;
    private String imgUrl;
    private String iconUrl;
    private String unit;
    private Long qualificationsId;
    private Integer type;

    private Long serverId;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getQualificationsId() {
        return qualificationsId;
    }

    public void setQualificationsId(Long qualificationsId) {
        this.qualificationsId = qualificationsId;
    }
}
