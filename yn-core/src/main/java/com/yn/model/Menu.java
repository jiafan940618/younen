package com.yn.model;

import com.yn.domain.IDomain;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 *
 */
@Entity
public class Menu extends IDomain implements Serializable {
	
	@Column(columnDefinition = "int(11) comment '[父菜单id]'")
	private Long parentId;
	@Column(columnDefinition = "int(2) comment '[等级]'")
	private Integer zlevel;
	@Column(columnDefinition = "int(3) comment '[菜单优先级]'")
	private Integer rank;
	@Column(columnDefinition = "varchar(50) comment '[名称]'")
	private String menuName;
	@Column(columnDefinition = "varchar(200) comment '[图标]'")
	private String icon;
	@Column(columnDefinition = "varchar(200) comment '[菜单图片]'")
	private String imgUrl;
	@Column(columnDefinition = "varchar(200) comment '[权限界面地址]'")
	private String routeName;
	@Column(columnDefinition = "varchar(50) comment '[备注]'")
	private String remark;

	@OneToMany
	@JoinColumn(name = "parentId", insertable = false, updatable = true)
	@OrderBy("rank,id ASC")
	@Where(clause = "del=0")
	private List<Menu> children;

	@Transient
	private Menu parent;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getZlevel() {
		return zlevel;
	}

	public void setZlevel(Integer zlevel) {
		this.zlevel = zlevel;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}
}
