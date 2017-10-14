package com.yn.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.yn.domain.IDomain;

@Entity
@Table(name="construction")
public class Construction extends IDomain implements Serializable {
	
	private Integer del;
	
	private String imgUrl;
	
	private String count;
	@Column(columnDefinition = "int(1) comment '[施工类别]{0:all,1:屋顶类,2:建筑一体化,3:公共设施}'")
	private Integer type;
	
	private String videoUrl;

	public Integer getDel() {
		return del;
	}

	public void setDel(Integer del) {
		this.del = del;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}
