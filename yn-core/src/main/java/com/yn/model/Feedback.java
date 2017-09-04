package com.yn.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yn.domain.IDomain;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 用户反馈
 */
@Entity
public class Feedback extends IDomain implements Serializable {

    @Column(columnDefinition = "int(11) comment '[用户id]'")
    private Long userId;
    @Column(columnDefinition = "int(11) comment '[电站id]'")
    private Long stationId;
    @Column(insertable = false, updatable = true, columnDefinition = "varchar(500) comment '[内容]'")
    private String content;
    @Column(columnDefinition = "int(1) comment '[状态]{0:未确认,1:已确认,2:已回复,3:完成}'")
    private Integer status;
    @Column(columnDefinition = "int(11) comment '[父id]'")
    private Long parentId;
    @Column(columnDefinition = "int(1) comment '[等级]{1:层主发表的内容}'")
    private Integer zlevel;

    @OneToMany
    @JoinColumn(name = "parentId", insertable = false, updatable = true)
    @OrderBy("id ASC")
    @Where(clause = "del=0")
    private List<Feedback> children;


    /**
     * 是否已读
     */
    @Transient
    private Integer isRead;


    /**
     * 用户
     */
    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"password", "bankCard", "role"})
    private User user;

    /**
     * 电站
     */
    @ManyToOne
    @JoinColumn(name = "stationId", insertable = false, updatable = false)
    @JsonIgnoreProperties(value = {"ammeter", "server"})
    private Station station;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public List<Feedback> getChildren() {
        return children;
    }

    public void setChildren(List<Feedback> children) {
        this.children = children;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getZlevel() {
        return zlevel;
    }

    public void setZlevel(Integer zlevel) {
        this.zlevel = zlevel;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}
