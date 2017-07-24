package com.yn.model;

import com.yn.domain.IDomain;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * 服务商选配项目
 */
@Entity
@Data
public class ApolegamyServer extends IDomain implements Serializable {

    @Column(columnDefinition = "int(11) NOT NULL comment'[优能选配id]'")
    private Long apolegamyId;
    @Column(columnDefinition = "int(11) NOT NULL comment'[服务商id]'")
    private Long serverId;

    /**
     * 选配项目
     */
    @ManyToOne
    @JoinColumn(name = "apolegamyId", insertable = false, updatable = false)
    private Apolegamy apolegamy;
}
