package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.Apolegamy;
import com.yn.model.Page;

@Mapper
public interface ApolegamyMapper {
	
	
	/** 根据服务商id显示分页数据*/
	List<Apolegamy> getPage(Page<Apolegamy> page);

	/** 找到查询数量*/
	int getCount(Page<Apolegamy> page);
	
	
	
	List<Apolegamy> FindApo(Long id);
	
	

}
