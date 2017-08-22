package com.yn.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TT on 2017/1/17.
 */
@MappedSuperclass
public class IDomain implements ISuperModel {
	
    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) comment '[id]'")
    private Long id;
    @Column(insertable = false, updatable = false,columnDefinition = "int(1) default 0 comment '[逻辑删除]{0:未删;1:已删}'")
    private Integer del;
    @Column(insertable = false, updatable = false,columnDefinition = "datetime comment '[删除时间]'")
    private Date delDtm;
    @Column(insertable = false, updatable = false, columnDefinition = "datetime default CURRENT_TIMESTAMP comment '[创建时间]'")
    private Date createDtm;
    @Column(insertable = false, updatable = false, columnDefinition = "datetime ON UPDATE CURRENT_TIMESTAMP comment '[更新时间]'")
    private Date updateDtm;
    
    
    
    @Transient
    private String query; // 查询的内容
    @Transient
    private String queryStartDtm; // 查询的开始日期
    @Transient
    private String queryEndDtm; // 查询的结束日期

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDel() {
        return del;
    }

    @Override
    public void setDel(Integer del) {
        this.del = del;
    }

    public Date getDelDtm() {
        return delDtm;
    }

    public void setDelDtm(Date delDtm) {
        this.delDtm = delDtm;
    }

    public Date getCreateDtm() {
        return createDtm;
    }

    public void setCreateDtm(Date createDtm) {
        this.createDtm = createDtm;
    }

    public Date getUpdateDtm() {
        return updateDtm;
    }

    public void setUpdateDtm(Date updateDtm) {
        this.updateDtm = updateDtm;
    }

    @Override
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQueryStartDtm() {
        return queryStartDtm;
    }

    public void setQueryStartDtm(String queryStartDtm) {
        this.queryStartDtm = queryStartDtm;
    }

    public String getQueryEndDtm() {
        return queryEndDtm;
    }

    public void setQueryEndDtm(String queryEndDtm) {
        this.queryEndDtm = queryEndDtm;
    }
}
