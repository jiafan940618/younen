package com.yn.dao.mapper;

import com.yn.model.Order;

//@Mapper
public interface OrderMapper {
	
//	@Transactional
//	@Modifying
	int updateByCondition(Order order);

}