package com.yn.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yn.model.NewServerPlan;
import com.yn.model.Page;
import com.yn.model.Server;
import com.yn.vo.NewServer;

@Mapper
public interface ServerMapper {
	
	List<Server> find(Server server);
	
	List<NewServer> gitNewServerPlan(Page<NewServer> page);
	
}
