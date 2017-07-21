package com.yn.vo;

import com.yn.domain.QueryVo;

public class MenuVo extends QueryVo{
	
    protected Long id;
    protected String icon;
    protected String imgUrl;
    protected String menuName;
    protected Long parentId;
    protected String path;
    protected Integer rank;
    protected String remake;
    protected Integer zlevel;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getRemake() {
		return remake;
	}
	public void setRemake(String remake) {
		this.remake = remake;
	}
	public Integer getZlevel() {
		return zlevel;
	}
	public void setZlevel(Integer zlevel) {
		this.zlevel = zlevel;
	}
	
}
