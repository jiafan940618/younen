package com.yn.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.yn.model.Page;
import com.yn.model.Push;
import com.yn.vo.PushVo;

@Mapper
public interface PushMapper {
	
	
	Push findByPush(Page<PushVo> page);
	
	
	
	

}
