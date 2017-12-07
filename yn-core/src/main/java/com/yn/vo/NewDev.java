package com.yn.vo;

import com.yn.model.Devide;

import java.util.List;

public class NewDev {
	
	
	private Long id;
	
	private String devideName;

    private List<NewDev> children;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDevideName() {
        return devideName;
    }

    public void setDevideName(String devideName) {
        this.devideName = devideName;
    }

    public List<NewDev> getChildren() {
        return children;
    }

    public void setChildren(List<NewDev> children) {
        this.children = children;
    }
}
