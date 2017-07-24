package com.yn.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by TT on 2017/1/17.
 */
@Data
@MappedSuperclass
public class IDomain implements ISuperModel {
	
    @Id
    @GeneratedValue
    @Column(columnDefinition = "int(11) comment '[id]'")
//    @Field(analyze= Analyze.NO)//用作搜索时排序
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
}
