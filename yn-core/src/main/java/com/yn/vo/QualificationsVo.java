package com.yn.vo;

import java.util.Set;

/**
 * 资质
 */
public class QualificationsVo {

    protected Long id;
    private String imgUrl;
    private String text;
    private Set<ApolegamyVo> apolegamy;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<ApolegamyVo> getApolegamy() {
        return apolegamy;
    }

    public void setApolegamy(Set<ApolegamyVo> apolegamy) {
        this.apolegamy = apolegamy;
    }
}
