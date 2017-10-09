package com.yn.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yn.model.Server;

@Mapper
public interface ServerMapper {
	
	List<Server> find(Server server);
	

}
