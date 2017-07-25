package com.yn.model;

import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * 服务商选配项目
 */
@Entity
public class ApolegamyServer extends IDomain implements Serializable {

    @Column(columnDefinition = "int(11) NOT NULL comment'[服务商id]'")
    private Long serverId;
    @Column(columnDefinition = "int(11) NOT NULL comment'[选配项目id]'")
    private Long apolegamyId;


    /**
     * 选配项目
     */
    @ManyToOne
    @JoinColumn(name = "apolegamyId", insertable = false, updatable = false)
    private Apolegamy apolegamy;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Long getApolegamyId() {
        return apolegamyId;
    }

    public void setApolegamyId(Long apolegamyId) {
        this.apolegamyId = apolegamyId;
    }

    public Apolegamy getApolegamy() {
        return apolegamy;
    }

    public void setApolegamy(Apolegamy apolegamy) {
        this.apolegamy = apolegamy;
    }
}
