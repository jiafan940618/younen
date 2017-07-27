package com.yn.vo;

/**
 * 服务商资质
 */
public class QualificationsServerVo {

    private Long id;
    private Long serverId;
    private Long qualificationsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
