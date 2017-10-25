package com.yn.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.Page;
import com.yn.model.Push;
import com.yn.vo.PushVo;

@Mapper
public interface PushMapper {
	
	
	List<Push> findByPush(Page<Push> page);
	
	int FindBycount(Page<Push> page);
	
	
	
	

}
