package com.yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * 服务商资质
 */
@Entity
public class QualificationsServer extends IDomain implements Serializable {

    @Column(columnDefinition = "int(11) comment '[服务商id]'")
    private Long serverId;
    @Column(columnDefinition = "int(11) comment '[资质id]'")
    private Long qualificationsId;

    /**
     * 资质
     */
    @ManyToOne
    @JoinColumn(name = "qualificationsId", insertable = false, updatable = false)
//    @JsonIgnoreProperties(value = {"apolegamy"})
    private Qualifications qualifications;

    public QualificationsServer() {
    }

    public QualificationsServer(Long serverId, Long qualificationsId) {
        this.serverId = serverId;
        this.qualificationsId = qualificationsId;
    }

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public Long getQualificationsId() {
        return qualificationsId;
    }

    public void setQualificationsId(Long qualificationsId) {
        this.qualificationsId = qualificationsId;
    }

    public Qualifications getQualifications() {
        return qualifications;
    }

    public void setQualifications(Qualifications qualifications) {
        this.qualifications = qualifications;
    }
}
