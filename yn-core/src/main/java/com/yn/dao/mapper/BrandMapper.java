package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.Brand;
import com.yn.model.Page;

@Mapper
public interface BrandMapper {
	
	/**查出品牌*/
	
	List<Brand> getBrand(Page<Brand> page);
	/** 查询出数目*/
	
	int getCount(Page<Brand> page);

}
