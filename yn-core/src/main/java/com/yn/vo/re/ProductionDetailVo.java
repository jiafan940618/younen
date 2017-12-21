package com.yn.vo.re;

import javax.persistence.Column;

/**
 * Created by engrossing on 2017/12/20.
 */
public class ProductionDetailVo {


    private Long id;

    private Integer serverId;

    private String content;

    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
