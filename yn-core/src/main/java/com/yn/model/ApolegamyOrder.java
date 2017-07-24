package com.yn.model;

import com.yn.domain.IDomain;
import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 订单的选配项目
 */
@Entity
@Data
public class ApolegamyOrder extends IDomain implements Serializable {

    @Column(columnDefinition = "int(11) comment '[订单id]'")
    private Long orderId;
    @Column(updatable = true, columnDefinition = "varchar(255) comment '[项目名称]'")
    private String apolegamyName;
    @Column(precision = 12, scale = 2, columnDefinition = "decimal(12,2) comment '[价格 元]'")
    private Double price;
    @Column(updatable = true, columnDefinition = "varchar(255) comment '[展示图片地址]'")
    private String imgUrl;
    @Column(updatable = true, columnDefinition = "varchar(255) comment '[icon图片地址]'")
    private String iconUrl;
    @Column(columnDefinition = "varchar(255) comment '[单位]'")
    private String unit;
    @Column(columnDefinition = "int(1) comment '[类型]{0:优能的选配项目,1:服务商的选配项目}'")
    private Integer type;
}
