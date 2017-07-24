package com.yn.model;

import com.yn.domain.IDomain;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

/**
 * 资质
 */
@Entity
@Data
public class Qualifications extends IDomain implements Serializable {

    @Column(updatable = true, columnDefinition = "varchar(255) comment '[icon图片地址]'")
    private String imgUrl;
    @Column(updatable = true, columnDefinition = "varchar(255) comment '[文字说明]'")
    private String text;

    /**
     * 选配项目
     */
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="qualificationsId", insertable=false, updatable=true)
    @Where(clause = "del=0")
    private Set<Apolegamy> apolegamy;
}
