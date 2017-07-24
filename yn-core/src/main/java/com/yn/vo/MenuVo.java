package com.yn.vo;

import com.yn.domain.QueryVo;
import lombok.Data;

@Data
public class MenuVo extends QueryVo{
	
    protected Long id;
    protected String icon;
    protected String imgUrl;
    protected String menuName;
    protected Long parentId;
    protected String routeName;
    protected Integer rank;
    protected String remake;
    protected Integer zlevel;
}
