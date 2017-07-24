package com.yn.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import com.yn.domain.IDomain;
import lombok.Data;
import org.hibernate.annotations.Where;

/**
 * 菜单
 *
 */
@Entity
@Data
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
}
