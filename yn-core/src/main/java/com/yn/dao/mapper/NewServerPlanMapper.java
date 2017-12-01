package com.yn.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.NewServerPlan;

@Mapper
public interface NewServerPlanMapper {
	
	
	void insert(NewServerPlan newServerPlan);
	

}
