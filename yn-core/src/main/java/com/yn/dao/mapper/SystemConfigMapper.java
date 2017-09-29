package com.yn.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.SystemConfig;

@Mapper
public interface SystemConfigMapper {

	SystemConfig select(Long id);

}