package com.yn.model;

import com.yn.domain.IDomain;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 资质
 */
@Entity
public class Qualifications extends IDomain implements Serializable {

    @Column(updatable = true, columnDefinition = "varchar(255) comment '[icon图片地址]'")
    private String imgUrl;
    @Column(updatable = true, columnDefinition = "varchar(255) comment '[文字说明]'")
    private String text;

    /**
     * 选配项目
     */
    @OneToMany(cascade= CascadeType.ALL)
    @JoinColumn(name="qualificationsId", insertable=false, updatable=false)
    @Where(clause = "del=0")
    private Set<Apolegamy> apolegamy;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Apolegamy> getApolegamy() {
        return apolegamy;
    }

    public void setApolegamy(Set<Apolegamy> apolegamy) {
        this.apolegamy = apolegamy;
    }
}
